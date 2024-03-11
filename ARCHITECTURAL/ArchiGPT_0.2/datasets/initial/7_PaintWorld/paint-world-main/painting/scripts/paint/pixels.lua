local json = require("json")
local pixelsCreator = {}

--------------------------------------------------------------------------------
-- INITIALIZING THE PIXELS
--------------------------------------------------------------------------------
function pixelsCreator:init(widthParam, heightParam, pixelSizeParam, canvasRParam, canvasGParam, canvasBParam, canvasAParam, canvasParam, fillOnlyParam, editParam, imageParam)
  --------------------------------------------------------------------------------
  -- SET LOCAL VARIABLES
  --------------------------------------------------------------------------------
  local width, height = nil
  local undoNumber = 10
  local undoStartNumber = 1
  local undoCurrentNumber = 1
  local pixelSize = nil
  local canvas = nil
  local strokesMoreThanUndoNumber = false
  local undoEnable = false
  local pixels = {}
  local imagePixels = {}
  local canvasR, canvasG, canvasB, canvasA = nil
  local fillOnly, edit = false
  local image, imageAddress = nil


  width = widthParam
  height = heightParam
  pixelSize = pixelSizeParam
  canvasR = canvasRParam
  canvasG = canvasGParam
  canvasB = canvasBParam
  canvasA = canvasAParam
  canvas = canvasParam
  fillOnly = fillOnlyParam
  edit = editParam
  imageAddress = imageParam

  --------------------------------------------------------------------------------
  -- SETTING FOR FILL ONLY
  --------------------------------------------------------------------------------

  if edit or fillOnly then
    local path = imageAddress
    local file = io.open(path, "r")
    local contents = file:read( "*a" )
    imagePixels = json.decode( contents )
    io.close(file)
    --------------------------------------------------------------
    -- if imagePixels["-2"] then
    --   print("changing")
    --   local test = {}
    --   for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
    --   do
    --     local row = {}
    --     for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
    --     do
    --       if imagePixels[tostring(xIndex)][tostring(yIndex)] then
    --         table.insert(row, imagePixels[tostring(xIndex)][tostring(yIndex)])
    --       else
    --         print("no")
    --       end
    --     end
    --     table.insert(test,row)
    --   end
    --   local file = io.open(path, "w")
    --   file:write(json.encode(test))
    --   io.close(file)
    -- else
    --   print("already")
    -- end
    --------------------------------------------------------------
  end
  if fillOnly then
    undoNumber = 1
  else
    undoNumber = 10
  end
  --------------------------------------------------------------------------------
  -- SETTING EVERY PIXEL WITH CANVAS COLOR
  --------------------------------------------------------------------------------
  for i = 0, undoNumber, 1
  do
    pixels[i] = {}
    for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
    do
      pixels[i][xIndex] = {}
      for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
      do
        if edit then
          pixels[i][xIndex][yIndex] = {imagePixels[tostring(xIndex)][tostring(yIndex)][1] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][2] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][3] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][4] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][5]}
        elseif fillOnly then
          pixels[i][xIndex][yIndex] = {canvasR, canvasG, canvasB, canvasA, imagePixels[xIndex + (width / (2 * pixelSize)) + 1][yIndex + (height / (2 * pixelSize)) + 1 ]}
        else
          pixels[i][xIndex][yIndex] = {canvasR, canvasG, canvasB, canvasA, 0}
        end
      end
    end
  end
  --------------------------------------------------------------------------------
  -- CLONE FUNCTION FOR COPYING A PIXEL MATRICS TO THE NEXT ONE
  --------------------------------------------------------------------------------
  function pixels:clone()
    undoEnable = true
    local differecne = 0
    local last = undoCurrentNumber
    if(undoCurrentNumber > undoStartNumber) then
      difference = undoCurrentNumber - undoStartNumber
    else
      difference = (undoNumber - undoStartNumber) + undoCurrentNumber
    end
    undoCurrentNumber = undoCurrentNumber + 1
    if(undoCurrentNumber > undoNumber) then
      undoCurrentNumber = 1
    end
    if(difference == undoNumber - 1) then
      undoStartNumber = undoStartNumber + 1
      if(not strokesMoreThanUndoNumber) then
        strokesMoreThanUndoNumber = true
      end
      if(undoStartNumber > undoNumber) then
        undoStartNumber = 1
      end
    end
    for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
    do
      for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
      do
        pixels[undoCurrentNumber][xIndex][yIndex] = pixels[last][xIndex][yIndex]
      end
    end
  end

  --------------------------------------------------------------------------------
  -- FUNCTION TO CLEAR PIXEL ARRAYS
  --------------------------------------------------------------------------------
  function pixels:clear()
    for i = 0, undoNumber, 1
    do
      for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
      do
        for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
        do
          if edit then
            pixels[i][xIndex][yIndex] = {imagePixels[tostring(xIndex)][tostring(yIndex)][1] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][2] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][3] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][4] / 255, imagePixels[tostring(xIndex)][tostring(yIndex)][5]}
          elseif fillOnly then
            pixels[i][xIndex][yIndex] = {canvasR, canvasG, canvasB, canvasA, imagePixels[xIndex + (width / (2 * pixelSize)) + 1][yIndex + (height / (2 * pixelSize)) + 1 ]}
          else
            pixels[i][xIndex][yIndex] = {canvasR, canvasG, canvasB, canvasA, 0}
          end
        end
      end
    end
    undoStartNumber = 1
    undoCurrentNumber = 1
  end

  --------------------------------------------------------------------------------
  -- UNDO FUNCTION
  --------------------------------------------------------------------------------
  function pixels:undo()
    local differecne1 = 0
    if(undoCurrentNumber > undoStartNumber) then
      difference1 = undoCurrentNumber - undoStartNumber
    else
      difference1 = (undoNumber - undoStartNumber) + undoCurrentNumber
    end
    if(undoStartNumber == undoCurrentNumber) then
      if(undoStartNumber == 1) then
        return false
      end
    end
    if(difference1 < 2) then
      if strokesMoreThanUndoNumber then
        return false
      else
        pixels:clear()
        return true
      end
    end
    if pixels[undoCurrentNumber].changedStrokes then
      for i = 1, #pixels[undoCurrentNumber].changedStrokes do
        canvas:changeStrokeColor(pixels[undoCurrentNumber].changedStrokes[i][5], pixels[undoCurrentNumber].changedStrokes[i])
      end
    end
    undoCurrentNumber = undoCurrentNumber - 1
    if(undoCurrentNumber < 1) then
      undoCurrentNumber = undoNumber
    end
    local beforeCurrent = undoCurrentNumber - 1
    if(beforeCurrent < 1) then
      beforeCurrent = undoNumber
    end
    for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
    do
      for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
      do
        pixels[undoCurrentNumber][xIndex][yIndex] = pixels[beforeCurrent][xIndex][yIndex]
      end
    end
    return true
  end

  --------------------------------------------------------------------------------
  -- SETTING A PIXEL WITH A SPECIFIC COLOR
  --------------------------------------------------------------------------------
  function pixels:setColor(x, y, color)
    if x < - width / 2 or x > (width / 2 ) - 1 or y < - height / 2 or y > (height / 2) - 1 then
      return
    end
    if not color[6] then
      if not pixels[undoCurrentNumber][math.floor(x / pixelSize)][math.floor(y / pixelSize)][6] then
        pixels[undoCurrentNumber][math.floor(x / pixelSize)][math.floor(y / pixelSize)] = color
      else
        pixels[undoCurrentNumber][math.floor(x / pixelSize)][math.floor(y / pixelSize)] = color
        pixels[undoCurrentNumber][math.floor(x / pixelSize)][math.floor(y / pixelSize)][6] = true
      end
    else
      pixels[undoCurrentNumber][math.floor(x / pixelSize)][math.floor(y / pixelSize)] = color
    end
  end

  function pixels:getColor(x, y)
    if x < - width / 2 or x > (width / 2 ) - 1 or y < - height / 2 or y > (height / 2) - 1 then
      return
    end
    return pixels[undoCurrentNumber][math.floor(x / pixelSize)][math.floor(y / pixelSize)]
  end



  --------------------------------------------------------------------------------
  -- SETTING A PIXEL WITH A SPECIFIC COLOR
  --------------------------------------------------------------------------------
  function pixels:saveJSON(filePath)
    local test = {}
    for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
    do
      test[xIndex] = {}
      for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
      do
        local temp = pixels[undoCurrentNumber][xIndex][yIndex]
        test[xIndex][yIndex] = {math.floor(temp[1] * 255), math.floor(temp[2] * 255), math.floor(temp[3] * 255), math.floor(temp[4] * 255), temp[5]}
      end
    end
    local file = io.open(filePath, "w")
    file:write(json.encode(test))
    io.close(file)
    test = nil
  end

  function pixels:loadJSON(file, dir)

    local path = system.pathForFile( userName.."/foo.json", system.DocumentsDirectory )
    local file = io.open(path, "r")
    local contents = file:read( "*a" )
    local t = json.decode( contents )
    io.close(file)

    local canvass = display.newContainer(960, 672)
    canvass.anchorX, canvas.anchorY = 0.5, 0.5
    canvass.x = 640
    canvass.y = 400

    for xIndex = (-width / (2 * pixelSize)), (width / (2 * pixelSize)) - 1, 1
    do
      -- test[xIndex] = {}
      for yIndex = (-height / (2 * pixelSize)), (height / (2 * pixelSize)) - 1, 1
      do
        local temp = t[tostring(xIndex)][tostring(yIndex)]
        local pixel = display.newCircle(canvass, xIndex * 6, yIndex * 6, 6)
        pixel:setFillColor(temp[1] / 255, temp[2] / 255, temp[3] / 255, temp[4] / 255)
      end
    end
  end
  return pixels
end
return pixelsCreator
