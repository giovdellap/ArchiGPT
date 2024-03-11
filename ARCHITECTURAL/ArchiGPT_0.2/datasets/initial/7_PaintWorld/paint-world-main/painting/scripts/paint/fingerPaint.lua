local pixelsCreator = require("scripts.paint.pixels")
local stamp = require("scripts.paint.stamp")
local json = require("json")
------------------------------------------------------------------------------------
-- CREATE TABLE TO HOLD MODULE
------------------------------------------------------------------------------------
local fingerPaint = {}
------------------------------------------------------------------------------------
-- CREATE NEW fillFingerPaint CANVAS
------------------------------------------------------------------------------------
function fingerPaint.newCanvas(...)
  -- available params are: width, height, strokeWidth, canvasColor, paintColor, segmented, x, y, isActive
  local arguments = {...}
  local params = arguments[1]
  if params == nil then params = {} end

  --------------------------------------------------------------------------------
  -- BACKWARDS COMPATIBILITY WITH v1.0 SYNTAX
  --------------------------------------------------------------------------------
  if type(params) ~= "table" then
    params = {
      width = arguments[1],
      height = arguments[2],
      strokeWidth = arguments[3],
      paintColor = arguments[4],
      canvasColor = arguments[5],
    }
  end

  --------------------------------------------------------------------------------
  -- LOCALIZE PARAMS & SET DEFAULTS
  --------------------------------------------------------------------------------
  local width = params.width or screenWidth
  local height = params.height or screenHeight
  local strokeWidth = params.strokeWidth or 12 -- 8 --
  local canvasColor = params.canvasColor or {1, 1, 1, 1}
  local fillOnly = params.fillOnly
  local edit = params.edit
  local fillObject = params.fillObject
  local editObject = params.editObject
  local editObjectName = params.editObjectName
  local editObjectFill, editObjectCanvas = nil
  local backgroundImageFile = params.backgroundImageFile
  local galleryFileAddress = params.galleryFileAddress
  local galleryFileName = params.galleryFileName
  local isFromGallery = params.isFromGallery
  if canvasColor[4] == nil then canvasColor[4] = 1 end
  local canvasR = canvasColor[1]
  local canvasG = canvasColor[2]
  local canvasB = canvasColor[3]
  local canvasA = canvasColor[4]
  local paintColor = params.paintColor or {0, 0, 0, 1}
  if paintColor[4] == nil then paintColor[4] = 1 end
  local paintR = paintColor[1]
  local paintG = paintColor[2]
  local paintB = paintColor[3]
  local paintA = paintColor[4]
  local segmented = params.segmented or false
  local x = params.x or centerX
  local y = params.y or centerY
  local isActive = params.isActive or true
  local circleRadius = strokeWidth * .5
  local fillMode = false
  local drawMode = false
  local sprayMode = false
  local eraseMode = false
  local pixelSize = 6
  local fillRadius = pixelSize
  local precision = 10
  local basicCircleRadius = 12
  local stampDelete, stampDeleteBackground, stampDeleteRed, stampOverlay, stampDeleteGroup
  local isTouchingStamp = false
  local hasChannged = false
  local sheet, sheetObject
  local directory

  --------------------------------------------------------------------------------
  -- CREATE CANVAS CONTAINER OBJECT
  --------------------------------------------------------------------------------
  local canvasHolder = display.newGroup(display.currentStage, width, height)
  local canvas = display.newContainer( canvasHolder, width, height)
  canvas.x, canvas.y = x, y
  canvas.isActive = isActive
  canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA = paintR, paintG, paintB, paintA
  canvas.canvasR, canvas.canvasG, canvas.canvasB, canvas.canvasA = canvasR, canvasG, canvasB, canvasA

  local tempCanvas = display.newContainer(canvasHolder, width, height)
  tempCanvas.x, tempCanvas.y = x + 6, y - 64

  local fillCanvas = display.newContainer(canvasHolder, width, height)
  fillCanvas.x, fillCanvas.y = x + 7, y - 64

  local stampCanvas = display.newContainer(canvasHolder, width, height)
  stampCanvas.x, stampCanvas.y = x + 7, y - 64 -- got the numbers by testing

  --------------------------------------------------------------------------------
  -- CREATE FILL OBJECT OVERLAY
  --------------------------------------------------------------------------------
  if fillOnly then
    if fillObject.downloaded then
      directory = system.DocumentsDirectory
    else
      directory = system.ResourceDirectory
    end
    local fillObjectOverlay = display.newImage(fillObject.address.."/main.png", directory)
    fillObjectOverlay.width = width
    fillObjectOverlay.height = height
    stampCanvas:insert(fillObjectOverlay)
  end

  --------------------------------------------------------------------------------
  -- CREATE CANVAS BACKGROUND RECT
  --------------------------------------------------------------------------------
  local background = display.newRect(fillCanvas, screenLeft - screenWidth, screenTop - screenHeight, screenWidth * 3, screenHeight * 3)
  background:setFillColor(canvasR, canvasG, canvasB)
  background.isHitTestable = true
  if(backgroundImageFile ) then
    local backgroundImage = display.newImage(backgroundImageFile, system.DocumentsDirectory)
    if backgroundImage.width < backgroundImage.height then
      local resizeFactor = backgroundImage.width / width
      backgroundImage.width = backgroundImage.width / resizeFactor
      backgroundImage.height = backgroundImage.height / resizeFactor
    else
      local resizeFactor = backgroundImage.height / height
      backgroundImage.width = backgroundImage.width / resizeFactor
      backgroundImage.height = backgroundImage.height / resizeFactor
    end
    fillCanvas:insert(backgroundImage)
  end
  canvas:toFront()
  stampCanvas:toFront()

  --------------------------------------------------------------------------------
  -- foooo
  --------------------------------------------------------------------------------


  --------------------------------------------------------------------------------
  -- CREATE TABLE TO HOLD PAINT STROKES
  --------------------------------------------------------------------------------
  canvas.strokes = {}
  canvas.stampStrokes = {}
  local strokes = canvas.strokes
  local stampStrokes = canvas.stampStrokes

  --------------------------------------------------------------------------------
  -- CREATE TABLE TO HOLD UNDONE PAINT STROKES
  --------------------------------------------------------------------------------
  canvas.undone = {}
  local undone = canvas.undone

  --------------------------------------------------------------------------------
  -- INITIALIZING PIXELS
  --------------------------------------------------------------------------------
  local tempObject = nil
  if fillOnly then
    tempObject = system.pathForFile(fillObject.address.."/bitmap.json", directory)
  elseif edit then
    tempObject = system.pathForFile(editObject.."/bitmap.json", system.DocumentsDirectory)
  end
  local pixels = pixelsCreator:init(width, height, pixelSize, canvasR, canvasG, canvasB, canvasA, canvas, fillOnly, edit, tempObject)

  --------------------------------------------------------------------------------
  -- CREATE PIXEL ARRAY AND ITS METHODS
  --------------------------------------------------------------------------------
  canvas.colorSample = {}
  local colorSample = canvas.colorSample
  colorSample.r = canvasR
  colorSample.g = canvasG
  colorSample.b = canvasB
  colorSample.a = canvasA

  --------------------------------------------------------------------------------
  -- FUNCTION TO CHECK COLORS EQUALITY
  --------------------------------------------------------------------------------
  function equalColor(obj1, obj2)
    if obj1.r == obj2.r and obj1.g == obj2.g and obj1.b == obj2.b and obj1.a == obj1.a then
      return true
    else
      return false
    end
  end

  --------------------------------------------------------------------------------
  -- SET VARIABLE TO TEST IF TOUCHES BEGAN ON CANVAS
  --------------------------------------------------------------------------------
  local touchBegan = false
  local foo = true

  --------------------------------------------------------------------------------
  -- TOUCH EVENT HANDLER FUNCTION
  --------------------------------------------------------------------------------
  local function drawTouchListener(event)
    if(drawMode) then
      -- set local variables
      hasChanged = true
      local phase = event.phase
      local target = event.target
      local stroke = strokes[#strokes]
      -- recalculate touch cooridnates, taking into account canvas position
      local canvasX, canvasY = canvas:localToContent(canvas.anchorX, canvas.anchorY)
      local x = event.x - canvasX
      local y = event.y - canvasY
      local xStart = event.xStart - canvasX
      local yStart = event.yStart - canvasY

      local function getDistance(x1, y1, x2, y2)
        return math.sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2)
      end

      if target.lastX == nil then
        target.lastX, target.lastY = x, y
      end

      local distance = getDistance(x, y, target.lastX, target.lastY)

      -- check for event phase & start, update, or end stroke accordingly
      if phase == "began" and canvas.isActive then
        -- empty undone table
        for i = #undone, 1, - 1 do
          display.remove(undone[i])
          undone[i] = nil
        end
        -- start stroke
        display.getCurrentStage():setFocus(target, event.id)
        touchBegan = true
        strokes[#strokes + 1] = display.newGroup()
        stroke = strokes[#strokes]
        stroke.type = "draw"
        stroke.radius = circleRadius
        canvas:insert(stroke)
        stroke.firstCircle = display.newCircle( stroke, x, y, circleRadius)
        stroke.firstCircle :setFillColor(paintR, paintG, paintB, paintA)
        pixels :setColor(x, y, {paintR, paintG, paintB, paintA, #strokes, true})
        target.lastX, target.lastY = x, y
      elseif phase == "moved" and touchBegan == true and distance > circleRadius * .5 then
        -- append to stroke
        if segmented then
          stroke.line = display.newLine(stroke, target.lastX, target.lastY, x, y)
          local circle = display.newRect( stroke, x, y, circleRadius, circleRadius)
          circle:setFillColor(paintR, paintG, paintB, paintA)
        else
          if stroke.line == nil then
            stroke.line = display.newLine(stroke, xStart, yStart, x, y)
            stroke.circle = display.newCircle( stroke, x, y, circleRadius)
            stroke.circle:setFillColor(paintR, paintG, paintB, paintA)
            pixels:setColor(x, y, {paintR, paintG, paintB, paintA, #strokes, true})
          else
            stroke.line:append(x, y)
            stroke.circle.x, stroke.circle.y = x, y
            pixels:setColor(x, y, {paintR, paintG, paintB, paintA, #strokes, true})
          end
        end
        local xAdd = (target.lastX - x) / distance
        local yAdd = (target.lastY - y) / distance

        for i = 1, distance do
          pixels:setColor(x + xAdd * i, y + yAdd * i, {paintR, paintG, paintB, paintA, #strokes, false})
        end
        stroke.line:setStrokeColor(paintR, paintG, paintB, paintA)
        stroke.line.strokeWidth = strokeWidth
        target.lastX, target.lastY = x, y
      elseif (phase == "cancelled" or phase == "ended") and touchBegan == true then
        -- end stroke
        display.getCurrentStage():setFocus(nil, event.id)
        touchBegan = false
        stroke.lastCircle = display.newCircle( stroke, x, y, circleRadius)
        stroke.lastCircle:setFillColor(paintR, paintG, paintB, paintA)
        pixels:setColor(x, y, {paintR, paintG, paintB, paintA, #strokes, true})
        target.lastX, target.lastY = nil, nil
        pixels:clone()
        return true
      end
    end
  end

  --------------------------------------------------------------------------------
  -- TOUCH EVENT HANDLER FUNCTION FOR SPRAY
  --------------------------------------------------------------------------------
  local function sprayTouchListener(event)
    if(sprayMode) then
      hasChanged = true
      -- set local variables
      local firstShade = .2
      local secondShade = .1
      local thirdShade = .1
      local firstResize = 1.2
      local secondResize = 2
      local thirdResize = 2
      local randomLower = 0
      local randomUpper = 3
      local phase = event.phase
      local target = event.target
      local stroke = strokes[#strokes]

      -- recalculate touch cooridnates, taking into account canvas position
      local canvasX, canvasY = canvas:localToContent(canvas.anchorX, canvas.anchorY)
      local x = event.x - canvasX
      local y = event.y - canvasY
      local xStart = event.xStart - canvasX
      local yStart = event.yStart - canvasY

      local function getDistance(x1, y1, x2, y2)
        return math.sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2)
      end

      if target.lastX == nil then
        target.lastX, target.lastY = x, y
      end

      local distance = getDistance(x, y, target.lastX, target.lastY)

      -- check for event phase & start, update, or end stroke accordingly
      if phase == "began" and canvas.isActive then

        -- empty undone table
        for i = #undone, 1, - 1 do
          display.remove(undone[i])
          undone[i] = nil
        end

        -- start stroke
        display.getCurrentStage():setFocus(target, event.id)
        touchBegan = true
        strokes[#strokes + 1] = display.newGroup()
        stroke = strokes[#strokes]
        stroke.type = "spray"
        stroke.radius = circleRadius
        canvas:insert(stroke)
        target.lastX, target.lastY = x, y
      elseif phase == "moved" and touchBegan == true and distance > circleRadius * 2 then

        -- append to stroke
        if segmented then
          stroke.line = display.newLine(stroke, target.lastX, target.lastY, x, y)
          local circle = display.newCircle( stroke, x, y, circleRadius)
          circle:setFillColor(paintR, paintG, paintB, paintA)
        else
          if stroke.line == nil then
            stroke.line = display.newLine(stroke, xStart + math.random(randomLower, randomUpper), yStart + math.random(randomLower, randomUpper), x, y)
          else
            stroke.line:append(x, y)
          end
        end
        local xAdd = (target.lastX - x) / distance
        local yAdd = (target.lastY - y) / distance

        for i = 1, distance do
          pixels:setColor(x + xAdd * i, y + yAdd * i, {paintR, paintG, paintB, paintA, #strokes})
        end
        stroke.line:setStrokeColor(paintR, paintG, paintB, paintA * firstShade)
        stroke.line.strokeWidth = strokeWidth * firstResize
        target.lastX, target.lastY = x, y
      elseif (phase == "cancelled" or phase == "ended") and touchBegan == true then
        -- end stroke
        display.getCurrentStage():setFocus(nil, event.id)
        touchBegan = false
        target.lastX, target.lastY = nil, nil
        pixels:clone()
        return true
      end
    end
  end

  --------------------------------------------------------------------------------
  -- COLOR SAMPLE HANDLER
  --------------------------------------------------------------------------------
  local function onColorSample( event )
    colorSample.r = math.floor( (event.r * precision) + 0.5) / precision
    colorSample.g = math.floor( (event.g * precision) + 0.5) / precision
    colorSample.b = math.floor( (event.b * precision) + 0.5) / precision
    colorSample.a = math.floor( (event.a * precision) + 0.5) / precision
  end

  --------------------------------------------------------------------------------
  -- FLOODFILL FUNCTION
  --------------------------------------------------------------------------------
  local function floodFill(event)
    local canvasX, canvasY = canvas:localToContent(canvas.anchorX, canvas.anchorY)
    local x = event.x - canvasX
    local y = event.y - canvasY
    -- display.colorSample( event.x, event.y, onColorSample)
    local firstTempColor = pixels:getColor(x, y) -- Checking if first tempColor exist
    if not firstTempColor then
      return
    end
    colorSample.r = firstTempColor[1]
    colorSample.g = firstTempColor[2]
    colorSample.b = firstTempColor[3]
    colorSample.a = firstTempColor[4]
    if fillOnly then
      if firstTempColor[5] == 0 then
        background:setFillColor(paintR, paintG, paintB, paintA)
      else
        strokes[firstTempColor[5]]:setFillColor(paintR, paintG, paintB, paintA)
      end
      return
    end
    if colorSample.r == paintR and colorSample.g == paintG and colorSample.b == paintB and colorSample.a == paintA then
      display.getCurrentStage():setFocus(nil, event.id)
      touchBegan = false
      return
    end
    hasChanged = true
    local baseColor = {}
    baseColor.r = colorSample.r
    baseColor.g = colorSample.g
    baseColor.b = colorSample.b
    baseColor.a = colorSample.a
    -- set local variables
    -- recalculate touch cooridnates, taking into account canvas position
    strokes[#strokes + 1] = display.newGroup()
    local stroke = strokes[#strokes]
    stroke.line = display.newLine(stroke, x, y, x, y)
    stroke.line:setStrokeColor(paintR, paintG, paintB, paintA)
    stroke.line.strokeWidth = basicCircleRadius * 1.4
    stroke.type = "fill"
    fillCanvas:insert(stroke)
    local queue = {}
    local processed = {}
    local surrounding = {}
    local changedStrokes = {}
    table.insert(queue, {x, y})
    local lastNodeX, lastNodeY = x, y
    while #queue > 0 do
      local topNode = table.remove(queue, #queue)
      local gridX, gridY = topNode[1], topNode[2]
      local f1 = true
      if(processed[gridX] ~= nil) then
        if(processed[gridX][gridY] ~= nil) then
          if(processed[gridX][gridY]) then
            f1 = false
          end
        end
      end

      if(gridX < (width / 2) - 1) then
        if(gridX > - width / 2) then
          if(f1) then
            if( gridY > - height / 2 and gridY < (height / 2) - 1) then
              processed[gridX] = processed[gridX] or {}
              processed[gridX][gridY] = true
              local tempColor = pixels:getColor(gridX, gridY)
              baseColor.r = tempColor[1]
              baseColor.g = tempColor[2]
              baseColor.b = tempColor[3]
              baseColor.a = tempColor[4]
              local strokeNumber = tempColor[5]
              if equalColor(colorSample, baseColor) and strokeNumber ~= -1 then
                -- print(math.sqrt(math.pow((lastNodeX-gridX),2)+math.pow((lastNodeY - gridY),2)))
                if strokeNumber == 0 then
                  if math.sqrt(math.pow((lastNodeX - gridX), 2) + math.pow((lastNodeY - gridY), 2)) > 2 * fillRadius then
                    -- print("no")
                  else
                    stroke.line:append(gridX, gridY)
                    -- local circle = display.newCircle( stroke, gridX, gridY, basicCircleRadius*.5 )
                    -- circle:setFillColor(paintR, paintG, paintB, paintA)
                  end

                  pixels:setColor(gridX, gridY, {paintR, paintG, paintB, paintA, 0})
                else
                  -- canvas:changeStrokeColor(strokeNumber, {paintR, paintG, paintB, strokeNumber} )
                  -- print(strokeNumber)
                  pixels:setColor(gridX, gridY, {paintR, paintG, paintB, paintA, strokeNumber})
                  -- print(strokeNumber)
                  if tempColor[6] then
                    -- print("yes")
                    surrounding[strokeNumber] = surrounding[strokeNumber] or {}
                    table.insert(surrounding[strokeNumber], {gridX, gridY})
                  end
                end
                lastNodeX, lastNodeY = gridX, gridY
                if (not processed[gridX - fillRadius] or not processed[gridX - fillRadius][gridY]) then
                  table.insert(queue, {gridX - fillRadius, gridY})
                end
                if (not processed[gridX] or not processed[gridX][gridY - fillRadius]) then
                  table.insert(queue, {gridX, gridY - fillRadius})
                end
                if (not processed[gridX + fillRadius] or not processed[gridX + fillRadius][gridY]) then
                  table.insert(queue, {gridX + fillRadius, gridY})
                end
                if (not processed[gridX] or not processed[gridX][gridY + fillRadius]) then
                  table.insert(queue, {gridX, gridY + fillRadius})
                end
              else
                -- print("not accepted")
              end
            end
          end
          -----
        end
      end
    end
    -- for k,v in pairs(surrounding) do
    -- 	local foo = display.newLine(canvas,surrounding[k][1][1],surrounding[k][1][2],surrounding[k][1][1],surrounding[k][1][2])
    -- 	for i =2, #surrounding[k] do
    -- 		foo:append(surrounding[k][i][1],surrounding[k][i][2])
    -- 	end
    -- 	foo:setStrokeColor(.5,.3,.2,1)
    -- 	foo.strokeWidth = 12
    -- end
    -- sorrounding = nill

    pixels:clone()
  end

  --------------------------------------------------------------------------------
  -- ADD TOUCH LISTENER FOR DRAWING TO CANVAS
  --------------------------------------------------------------------------------
  local function fillTouchListener(event)
    if(fillMode) then
      if(event.phase == "ended") then
        local foo = {}
        foo.x = event.x
        foo.y = event.y
        floodFill(foo)
      end
    end
  end

  --------------------------------------------------------------------------------
  -- ADD TOUCH LISTENER FOR DRAWING TO CANVAS
  --------------------------------------------------------------------------------
  function canvas:draw()
    paintR, paintG, paintB, paintA = canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA
    fillMode = false
    sprayMode = false
    eraseMode = false
    drawMode = true

  end

  --------------------------------------------------------------------------------
  -- ADD TOUCH LISTENER FOR FILL TO CANVAS
  --------------------------------------------------------------------------------
  function canvas:fill()
    paintR, paintG, paintB, paintA = canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA
    drawMode = false
    sprayMode = false
    eraseMode = false
    fillMode = true
  end

  --------------------------------------------------------------------------------
  -- ADD TOUCH LISTENER FOR SPRAY TO CANVAS
  --------------------------------------------------------------------------------
  function canvas:spray()
    paintR, paintG, paintB, paintA = canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA
    eraseMode = false
    drawMode = false
    sprayMode = true
    fillMode = false
  end

  --------------------------------------------------------------------------------
  -- ADD TOUCH LISTENERS
  --------------------------------------------------------------------------------
  canvasHolder:addEventListener("touch", drawTouchListener)
  canvasHolder:addEventListener("touch", fillTouchListener)
  canvasHolder:addEventListener("touch", sprayTouchListener)

  --------------------------------------------------------------------------------
  -- ERASER FUNCTION
  --------------------------------------------------------------------------------
  function canvas:eraser()
    if fillOnly then
      canvas:fill()
    else
      canvas:draw()
    end
    eraseMode = true
    paintR, paintG, paintB, paintA = canvasR, canvasG, canvasB, canvasA
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO CHANGE PAINT COLOR
  --------------------------------------------------------------------------------
  function canvas:setPaintColor(r, g, b, a)
    canvas.paintR = r
    canvas.paintG = g
    canvas.paintB = b
    canvas.paintA = a or canvas.paintA
    if eraseMode then
      canvas:eraser()
    elseif drawMode then
      canvas:draw()
    elseif fillMode then
      canvas:fill()
    elseif sprayMode then
      canvas:spray()
    end
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO CHANGE CANVAS COLOR
  --------------------------------------------------------------------------------
  function canvas:setCanvasColor(r, g, b, a)
    background:setFillColor(r, g, b, a)
    canvasR = r
    canvasG = g
    canvasB = b
    canvasA = a or canvasA
    canvas.canvasR, canvas.canvasG, canvas.canvasB, canvas.canvasA = canvasR, canvasG, canvasB, canvasA
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO CHANGE STROKE WIDTH
  --------------------------------------------------------------------------------
  function canvas:setStrokeWidth(newWidth)
    strokeWidth = newWidth
    circleRadius = newWidth * .5
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO UNDO PAINT STROKES
  --------------------------------------------------------------------------------
  function canvas:undo()
    if pixels:undo() then
      if #strokes > 0 then
        local n = #strokes
        local stroke = strokes[n]
        table.remove(strokes, n)
        strokes[n] = nil
        stroke:removeSelf()
      end
    end
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO REDO PAINT STROKES
  --------------------------------------------------------------------------------
  function canvas:redo()
    if #undone > 0 then
      local n = #undone
      local stroke = undone[n]
      table.remove(undone, n)
      undone[n] = nil
      strokes[#strokes + 1] = stroke
      stroke.isVisible = true
    end
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO REDO PAINT STROKES
  --------------------------------------------------------------------------------
  function canvas:hasChanged()
    return hasChanged
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO ERASE ALL PAINT STROKES
  --------------------------------------------------------------------------------
  function canvas:erase(isClosing)
    if #strokes > 0 then
      for n = #strokes, 1, - 1 do
        local stroke = strokes[n]
        table.remove(strokes, n)
        strokes[n] = nil
        display.remove(stroke)
      end
    end
    if #stampStrokes > 0 then
      for n = #stampStrokes, 1, - 1 do
        local stroke = stampStrokes[n]
        table.remove(stampStrokes, n)
        stampStrokes[n] = nil
        display.remove(stroke)
      end
    end
    strokes = nil
    stampStrokes = nil
    if isClosing then
      pixels = nil
      pixels = {}
      collectgarbage()
    else
      pixels:clear()
    end

    strokes = {}
    stampStrokes = {}
    -- display.remove(stampCanvas)
    -- stampCanvas = display.newContainer(width, height)
    if edit then
      hasChanged = true
      display.remove( editObjectFill )
      display.remove(editObjectCanvas)
      editObjectFill = nil
      editObjectCanvas = nil
    end
    if fillOnly then
      for i = 1, #sheetObject:getSheet().frames do
        local temp = display.newImage( sheet, i )
        table.insert(strokes, temp)
        canvas:insert(temp)
      end
      background:setFillColor(canvasR, canvasG, canvasB)
      hasChanged = false
    end
  end

  ---------------------------------------------------------------------------------
  -- SAVE IMAGE FUNCTION
  ---------------------------------------------------------------------------------
  function canvas:save()
    local userName = appConfig:getUserName()
    if edit then
      pictureDirectory = editObjectName
    else
      if isFromGallery then
        print(params.galleryFileAddress)
        local path = system.pathForFile( params.galleryFileAddress, system.DocumentsDirectory )
        for file in lfs.dir(path) do
          if file ~= "." and file ~= ".." then
            os.remove(path.."/"..file)
          end
        end
        lfs.rmdir(path)
      end
      pictureDirectory = os.date():gsub("/", "")
      pictureDirectory = pictureDirectory:gsub(" ", "")
      pictureDirectory = pictureDirectory:gsub(":", "")
      local docs_path = system.pathForFile( userName.."/savedPictures", system.DocumentsDirectory )
      local success = lfs.chdir( docs_path )
      if ( success ) then
        lfs.mkdir( pictureDirectory )
      end
    end

    if fillOnly then
      display.save( canvasHolder, { filename = userName.."/savedPictures/"..pictureDirectory.."/main.png", captureOffscreenArea = false, baseDir = system.DocumentsDirectory } )
      return
    end

    pixels:saveJSON(system.pathForFile( userName.."/savedPictures/"..pictureDirectory.."/bitmap.json", system.DocumentsDirectory ))
    display.save( canvasHolder, { filename = userName.."/savedPictures/"..pictureDirectory.."/main.png", captureOffscreenArea = false, baseDir = system.DocumentsDirectory } )
    display.save( fillCanvas, { filename = userName.."/savedPictures/"..pictureDirectory.."/fill.png", captureOffscreenArea = false, baseDir = system.DocumentsDirectory } )
    display.save( canvas, { filename = userName.."/savedPictures/"..pictureDirectory.."/canvas.png", captureOffscreenArea = false, baseDir = system.DocumentsDirectory } )
    local strokeArray = {}
    for i = 1, #strokes do
      table.insert(strokeArray, {type = strokes[i].type, radius = strokes[i].radius})
    end
    local stampArray = {}
    for i = 1, #stampStrokes do
      table.insert(stampArray, {stampStrokes[i].address, stampStrokes[i].x, stampStrokes[i].y})
    end
    local config = {}
    config.strokes = strokeArray
    config.stamps = stampArray
    local configPath = system.pathForFile( userName.."/savedPictures/"..pictureDirectory.."/config.json", system.DocumentsDirectory )
    local configFile = io.open(configPath, "w")
    configFile:write(json.encode(config))
    io.close(configFile)
    stampArray = nil
    config = nil
    hasChanged = false
  end

  ---------------------------------------------------------------------------------
  -- STAMP TOUCH LISTENER
  ---------------------------------------------------------------------------------
  local function stampTouchListener(event)
    local platformTouched = event.target
    local stampCanvasX, stampCanvasY = stampCanvas:localToContent(stampCanvas.anchorX, stampCanvas.anchorY)

    if (event.phase == "began") then
      display.getCurrentStage():setFocus( platformTouched )
      platformTouched.isFocus = true

      stampDeleteGroup = display.newGroup()
      stampDeleteGroup.x = 150
      transition.to(stampDeleteGroup, {x = 0, delay = 0, time = 400, transition = easing.inCubic })

      stampDeleteBackground = display.newRect(stampDeleteGroup, screenRight - 75, centerY, 150, screenHeight )
      stampDeleteBackground.fill.effect = "filter.blur"
      stampDeleteBackground:setFillColor(.4, .4, .4, .6)

      stampDelete = display.newImage(stampDeleteGroup, "images/paint/stampDelete.png")
      stampDelete.x = stampDeleteBackground.x
      stampDelete.y = stampDeleteBackground.y
      stampDelete.isVisible = true

      stampDeleteRed = display.newImage(stampDeleteGroup, "images/paint/stampDeleteRed.png")
      stampDeleteRed.x = stampDeleteBackground.x
      stampDeleteRed.y = stampDeleteBackground.y
      stampDeleteRed.isVisible = false

      stampOverlay = display.newImage(event.target.address)
      stampOverlay.x = event.target.x + stampCanvasX
      stampOverlay.y = event.target.y + stampCanvasY
      stampOverlay.isVisible = false

      hasChanged = true

      -- here the first position is stored in x and y
      platformTouched.startMoveX = platformTouched.x
      platformTouched.startMoveY = platformTouched.y
    end
    if platformTouched.isFocus and stampOverlay then -- I added "stampOverlay" for a bug that I did not know the reason
      if (event.phase == "moved") then

        platformTouched.x = (event.x - event.xStart) + platformTouched.startMoveX
        platformTouched.y = (event.y - event.yStart) + platformTouched.startMoveY
        stampOverlay.x = (event.x - event.xStart) + platformTouched.startMoveX + stampCanvasX
        stampOverlay.y = (event.y - event.yStart) + platformTouched.startMoveY + stampCanvasY
        if event.x > stampCanvas.contentBounds.xMax + stampDeleteBackground.width / 3 then
          stampDeleteRed.isVisible = true
          stampDelete.isVisible = false
        else
          stampDeleteRed.isVisible = false
          stampDelete.isVisible = true
        end
        if event.x > stampCanvas.contentBounds.xMax - stampOverlay.width / 2 - 50 then
          stampOverlay.isVisible = true
        else
          stampOverlay.isVisible = false
        end

      elseif event.phase == "ended" or event.phase == "cancelled" then
        local function deleteBackgroundPlatform(itemDeleted)
          local function onComplete()
            platformTouched.isFocus = flase
            display.remove(stampDeleteGroup)
            stampDeleteGroup = nil
            if stampOverlay then
              stampOverlay:removeSelf()
              stampOverlay = nil

            end
          end

          if not itemDeleted then
            stampOverlay:removeSelf()
            stampOverlay = nil
          else
            stampDeleteGroup:insert(stampOverlay)
          end
          transition.to(stampDeleteGroup, {x = 150, delay = 0, time = 400, transition = easing.inCubic, onComplete = onComplete })
        end

        if event.x > stampCanvas.contentBounds.xMax + stampDeleteBackground.width / 3 then
          display.remove(event.target)
          table.remove(stampStrokes, table.indexOf(stampStrokes, event.target))
          deleteBackgroundPlatform(true)
        else
          deleteBackgroundPlatform(false)
        end
        platformTouched.isFocus = false
        display.getCurrentStage():setFocus( nil )
      end---
    end

    return true
  end


  ---------------------------------------------------------------------------------
  -- STAMP LOADER
  ---------------------------------------------------------------------------------
  function canvas:loadStamp(address)
    hasChanged = true
    local img = display.newImage( address)
    img.address = address
    img:addEventListener( "tap", function() return true end )
    img:addEventListener( "touch", stampTouchListener )
    table.insert(stampStrokes, img)
    stampCanvas:insert(img)
  end

  ---------------------------------------------------------------------------------
  -- CLOSE FUNCTION
  ---------------------------------------------------------------------------------
  function canvas:close(address)
    canvas:erase(true)
    display.remove(background)
    display.remove(canvas)
    display.remove(fillCanvas)
    display.remove(stampCanvas)
    display.remove(canvasHolder)
    canvas = nil
    background = nil
    fillCanvas = nil
    stampCanvas = nil
    canvasHolder = nil
    if sheetObject then
      local module = (string.gsub((fillObject.address.."/sheet"), "/", "."))
      package.loaded[module] = nil
    end
  end

  ---------------------------------------------------------------------------------
  -- CHANGE STROKE COLOR
  ---------------------------------------------------------------------------------
  function canvas:changeStrokeColor(strokeNumber, color)
    strokes[strokeNumber].line:setStrokeColor(color[1], color[2], color[3], color[4])
    if strokes[strokeNumber].firstCircle then
      strokes[strokeNumber].firstCircle:setFillColor(color[1], color[2], color[3], color[4])
      strokes[strokeNumber].lastCircle:setFillColor(color[1], color[2], color[3], color[4])
    end
  end

  --------------------------------------------------------------------------------
  -- SETTING CANVAS FOR EDIT
  --------------------------------------------------------------------------------
  if edit then
    local configPath = system.pathForFile( editObject.."/config.json", system.DocumentsDirectory )
    local configFile = io.open(configPath)
    local configContent = configFile:read( "*a" )
    local config = json.decode(configContent)
    io.close(configFile)
    editObjectFill = display.newImage(editObject.."/fill.png", system.DocumentsDirectory)
    editObjectFill.width = width
    editObjectFill.height = height
    fillCanvas:insert(editObjectFill)
    editObjectCanvas = display.newImage(editObject.."/canvas.png", system.DocumentsDirectory)
    editObjectCanvas.width = width
    editObjectCanvas.height = height
    canvas:insert(editObjectCanvas)
    for i = 1, #config.strokes do
      local temp = display.newGroup()
      temp.type = config.strokes[i].type
      temp.radius = config.strokes[i].radius
      table.insert(strokes, temp)
    end

    for i = 1, #config.stamps do
      canvas:loadStamp(config.stamps[i][1])
      stampStrokes[#stampStrokes].x = config.stamps[i][2]
      stampStrokes[#stampStrokes].y = config.stamps[i][3]
    end
    hasChanged = false
  end

  --------------------------------------------------------------------------------
  -- SETTING CANVAS FOR FILL
  --------------------------------------------------------------------------------
  if fillOnly then
    sheetObject = require(string.gsub(fillObject.sheetObject, "/", "."))
    sheet = graphics.newImageSheet(fillObject.sheet, directory, sheetObject:getSheet() )
    for i = 1, #sheetObject:getSheet().frames do
      local temp = display.newImage( sheet, i )
      table.insert(strokes, temp)
      canvas:insert(temp)
    end
    hasChanged = false
  end

  ---------------------------------------------------------------------------------
  -- TEST AREA
  ---------------------------------------------------------------------------------
  function canvas:foo1()
    local screenBounds =
    {
      xMin = 167,
      xMax = 1127,
      yMin = 0,
      yMax = 672
    }
    background = display.capture( canvasHolder, {saveToPhotoLibrary = true} )
    background.x = canvas.x + width / 2
    background.y = canvas.y + height / 2
    -- canvas:insert(combined,true)
    canvas:erase()

    canvas:toBack()

  end

  function canvas:foo()
    pixels:loadJSON()
  end

  ---------------------------------------------------------------------------------
  -- END OF TEST AREA
  ---------------------------------------------------------------------------------
  return canvas
end

return fingerPaint
