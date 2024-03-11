
local fingerPaint = require("scripts.paint.fingerPaint")
local colorPicker = require("scripts.paint.colorPicker")
local colors = require("scripts.paint.colors")
local widget = require("widget")
local coloringPage = {}
local properties = {}
local testName = nil
local imageSound = nil
local imagePoem = nil
--set variables related to color pencils
local penWidth = 14
local penHeight = 140
local initialX = 0
local initialY = 70
local selectedHeight = 0
local normalHeight = 30
local margin = 14
local penR = nil
local penG = nil
local penB = nil
local penA = nil
local darkerFactor = -0.1
local lighterFactor = 0.1
local pencils = {}
local pen = nil
local resumeSound = false
-- set variables for various screen positions
local centerX = display.contentCenterX
local centerY = display.contentCenterY
local screenTop = display.screenOriginY
local screenLeft = display.screenOriginX
local screenBottom = display.screenOriginY + display.actualContentHeight
local screenRight = display.screenOriginX + display.actualContentWidth
local screenWidth = screenRight - screenLeft
local screenHeight = screenBottom - screenTop
local selectedPencil = nil
local isDisplaying = false
function coloringPage:render(params)
  local showWaitingBlur, closeWaitingBlur, textOnWaitingBlur, waitingBlur
  if not params.bitmap then
    print("no")
    return
  end
  isDisplaying = true
  local directory
  if params.downloaded then
    directory = system.DocumentsDirectory
  else
    directory = nil
  end
  coloringPage.canvas = fingerPaint.newCanvas({
    width = canvasWidth,
    height = canvasHeight,
    fillObject = params,
    fillOnly = true
  })
  local canvas = coloringPage.canvas
  -- table.insert(properties, canvas)

  canvas.anchorX, canvas.anchorY = 0, 0
  canvas.x, canvas.y = 167, 0
  local menuBackground = display.newImageRect( "images/paint/Rounded Rectangle 7.png", 147, 790)
  menuBackground.x = display.contentCenterX - 550
  menuBackground.y = display.contentHeight + 15
  table.insert(properties, menuBackground)

  local selectedBackground = display.newRoundedRect(display.contentCenterX - 548.5, display.contentHeight - 285, 120, 130, 20)
  selectedBackground:setFillColor(.56875, .56875, .56875, .3)
  table.insert(properties, selectedBackground)

  local fill = display.newImageRect( "images/paint/tool.png", 71, 93)
  fill.x = display.contentCenterX - 555
  fill.y = display.contentHeight - 285
  fill.name = "fill"
  table.insert(properties, fill)

  local eraser = display.newImageRect( "images/paint/Layer-2.png", 92, 96)
  eraser.x = display.contentCenterX - 545
  eraser.y = display.contentHeight - 165
  eraser.name = "eraser"
  table.insert(properties, eraser)

  local pallet = display.newImageRect( "images/paint/rec.png", 84, 84)
  pallet.x = display.contentCenterX - 555
  pallet.y = display.contentHeight - 50
  pallet.name = "pallet"
  table.insert(properties, pallet)

  local palletOverlay = display.newRoundedRect( display.contentCenterX - 555, display.contentHeight - 50, 67, 69, 10)
  palletOverlay:setFillColor(canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA)
  palletOverlay.name = "palletOverlay"
  table.insert(properties, palletOverlay)

  local backButton = display.newImageRect( "images/paint/back.png", 125, 125)
  backButton.x = display.contentCenterX - 390
  backButton.y = display.contentHeight - 65
  backButton.name = "backButton"
  table.insert(properties, backButton)

  local colorPencilsBackground = display.newImageRect( "images/paint/Rounded Rectangle 8.png", 930, 42)
  colorPencilsBackground.x = display.contentCenterX + 150
  colorPencilsBackground.y = display.contentHeight - colorPencilsBackground.height / 2
  table.insert(properties, colorPencilsBackground)

  local picture = display.newImageRect( "images/paint/pic.png", 132, 152)
  picture.x = display.contentCenterX + 557
  picture.y = display.contentHeight - 710
  picture.name = "picture"
  table.insert(properties, picture)


  local pictureInner = nil
  pictureInner = display.newImage(appConfig:getAvatarAddress())
  pictureInner.x = picture.x
  pictureInner.y = picture.y
  pictureInner.width = picture.width * .9
  pictureInner.height = picture.height * .9
  table.insert(properties, pictureInner)

  local delete = display.newImageRect( "images/paint/delet copy.png", 126, 126)
  delete.x = display.contentCenterX + 555
  delete.y = display.contentHeight - 565
  delete.name = "delete"
  table.insert(properties, delete)

  local save = display.newImageRect( "images/paint/save copy.png", 126, 126)
  save.x = display.contentCenterX + 555
  save.y = display.contentHeight - 434
  save.name = "save"
  table.insert(properties, save)

  local undo = display.newImageRect( "images/paint/undo copy.png", 126, 126)
  undo.x = display.contentCenterX + 555
  undo.y = display.contentHeight - 304
  undo.name = "undo"
  table.insert(properties, undo)
  undo.isVisible = false

  local showCompleted, completed = nil
  if params.completed then
    showCompleted = display.newImageRect( "images/coloring/showCompleted.png", 126, 126)
    showCompleted.x = display.contentCenterX + 555
    -- showCompleted.y = display.contentHeight - 173
    showCompleted.y = display.contentHeight - 304
    showCompleted.name = "showCompleted"
    table.insert(properties, showCompleted)

    completed = display.newImage(params.completed, directory)
    completed.anchorX, completed.anchorY = 0, 0
    completed.x = canvas.x
    completed.y = canvas.y
    completed.isVisible = false
    table.insert(properties, completed)

  end

  local function changeSelectedPencil(pencil)
    if pencil then
      if selectedPencil then
        selectedPencil.y = normalHeight
      end
      selectedPencil = pencil
      selectedPencil.y = selectedHeight
    else
      if selectedPencil then
        selectedPencil.y = normalHeight
      end
      selectedPencil = nil
    end
  end

  local function changePaintColor(event)
    local function colorListener(r, g, b, a)
      -- canvas:setPaintColor(math.floor(r*10)/10,math.floor(g*10)/10,math.floor(b*10)/10,math.floor(a*10)/10)
      canvas:setPaintColor(r, g, b, a)
      palletOverlay:setFillColor(canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA)
      changeSelectedPencil()
    end
    colorPicker.show(colorListener, canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA)
  end

  local function setSelectedButtonBackground(target)
    selectedBackground.y = target.y
  end

  local function touchButtonHandler(event)
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.width = event.target.width * clickResizeFactor
      event.target.height = event.target.height * clickResizeFactor
      if event.target.name == "showCompleted" then
        completed.isVisible = true
      end
      display.getCurrentStage():setFocus( event.target )
      event.target.isFocus = true
    end
    if not event.target.isFocus then
      return false
    end
    if event.phase == "ended" then
      event.target.width = event.target.width / clickResizeFactor
      event.target.height = event.target.height / clickResizeFactor
      local buttonName = event.target.name
      local button = event.target
      if button.name == "pallet" then
        changePaintColor()
      elseif button.name == "palletOverlay" then
        changePaintColor()
      elseif button.name == "undo" then
        canvas.undo()
      elseif button.name == "delete" then
        alert({
          textImage = images.alerts.eraseCanvas,
          isYesNo = true,
          callBack = function(index)
            if index == 1 then
              canvas:erase()
            elseif index == 2 then
            end
          end})
        elseif button.name == "save" then
          audio.stop(imageSound)
          audio.stop(imagePoem)
          showWaitingBlur()
          alert({
            textImage = images.alerts.saveChanges,
            isYesNo = true,
            lockTouch = true,
            narration = sounds.saveChanges,
            callBack = function(index)
              if index == 1 then
                if params and params.isFromGallery then
                  showLoading()
                  timer.performWithDelay( 500, function() closeWaitingBlur() canvas:save() back() back() end )
                else
                  timer.performWithDelay( 500, function() canvas:save() closeWaitingBlur() end )
                end
              elseif index == 2 then
                closeWaitingBlur(true)
              end
            end})
          elseif button.name == "showCompleted" then
            completed.isVisible = false
          elseif button.name == "fill" then
            setSelectedButtonBackground(event.target)
            canvas.fill()
          elseif button.name == "eraser" then
            setSelectedButtonBackground(event.target)
            canvas.eraser()
          elseif button.name == "backButton" then
            alert({
              textImage = images.alerts.saveChanges,
              narration = sounds.saveChanges,
              isYesNo = true,
              callBack = function(index)
                if(index == 1) then
                  timer.performWithDelay( 500, function() canvas:save(event) closeWaitingBlur() back() end )
                elseif (index == 2) then
                  closeWaitingBlur()
                  back()
                end
              end
            })
          end
          event.target.isFocus = false
          display.getCurrentStage():setFocus(nil)
        end
      end

      palletOverlay:addEventListener( "touch", touchButtonHandler )
      undo:addEventListener( "touch", touchButtonHandler )
      delete:addEventListener("touch", touchButtonHandler )
      fill:addEventListener("touch", touchButtonHandler)
      eraser:addEventListener("touch", touchButtonHandler)
      backButton:addEventListener("touch", touchButtonHandler)
      save:addEventListener("touch", touchButtonHandler)
      if params.completed then
        showCompleted:addEventListener("touch", touchButtonHandler)
      end
      canvas:fill()

      -------------------------------------
      local function iconListener( event )
        if ( event.phase == "moved" ) then
          local dx = math.abs( event.x - event.xStart )
          if ( dx > 5 ) then
            scrollView:takeFocus( event )
          end
        elseif ( event.phase == "ended" ) then
          audio.stop(clickSoundCannel)
          audio.play(clickSound, clickPlayOptions)
          canvas:setPaintColor(event.target.color.r, event.target.color.g, event.target.color.b, event.target.color.a)
          palletOverlay:setFillColor(canvas.paintR, canvas.paintG, canvas.paintB, canvas.paintA)
          changeSelectedPencil(event.target)
        end
        return true
      end

      scrollView = widget.newScrollView
      {
        width = 900,
        height = 180,
        -- scrollWidth = 1860,
        scrollHeight = 180,
        verticalScrollDisabled = true,
        hideBackground = true,
        rightPadding = 60
      }
      scrollView.x = colorPencilsBackground.x
      scrollView.y = colorPencilsBackground.y
      --------------------------------------
      for i = 1, #colors, 1 do
        local penX = initialX + i * (margin + 3 * penWidth)
        penY = initialY
        penR = colors[i][1] / 255
        penG = colors[i][2] / 255
        penB = colors[i][3] / 255
        penA = 1
        pencils[i] = display:newGroup()
        pencils[i].color = {}
        pencils[i].color.r = penR
        pencils[i].color.g = penG
        pencils[i].color.b = penB
        pencils[i].color.a = penA
        local leftVertices = {penX, penY, penX + penWidth, penY, penX + penWidth, penY - penHeight, penX, penY - 0.98 * penHeight }
        local leftSide = display.newPolygon(penX - penWidth, penY, leftVertices )
        leftSide:setFillColor(penR + lighterFactor, penG + lighterFactor, penB + lighterFactor, penA)
        leftSide.strokeWidth = 0

        local middleVertices = {penX, penY, penX + penWidth * 1.6, penY, penX + penWidth * 1.6, penY - penHeight, penX, penY - penHeight }
        local middleOne = display.newPolygon(penX, penY, middleVertices )
        middleOne:setFillColor(penR, penG, penB, penA)
        middleOne.strokeWidth = 0

        local rightVertices = {penX, penY, penX + penWidth, penY, penX + penWidth, penY - 0.98 * penHeight, penX, penY - penHeight }
        local rightSide = display.newPolygon(penX + penWidth, penY, rightVertices )
        rightSide:setFillColor(penR + darkerFactor, penG + darkerFactor, penB + darkerFactor, penA)
        rightSide.strokeWidth = 0

        pencils[i]:insert(leftSide)
        pencils[i]:insert(middleOne)
        pencils[i]:insert(rightSide)
        pencils[i]:addEventListener("touch", iconListener)
        scrollView:insert(pencils[i])
        pencils[i].y = normalHeight
        table.insert(properties, pencils[i])
      end
      table.insert(properties, scrollView)
      table.insert(properties, pencils)

      if params.sound then
        if isSoundPlaying() then
          timer.performWithDelay( 2000, function() pauseSound() resumeSound = true end )

        else
          --nothing
        end

        local function poemFinished( event )
          if ( event.completed ) then
            if(resumeSound) then
              playSound()
              resumeSound = false
            end
          else
            --nothing
          end

        end

        local function narrationFinished( event )
          if ( event.completed ) then
            if params.poem then
              local soundFile = audio.loadSound( params.poem)
              imagePoem = audio.play(soundFile, { channel = 4, onComplete = poemFinished })
            else
              if(resumeSound) then
                playSound()
                resumeSound = false
              end
            end
          else
            --nothing
          end

        end
        local function playSound()
          if isDisplaying then
            local soundFile = audio.loadSound( params.sound, directory)
            imageSound = audio.play(soundFile, { channel = 4, onComplete = narrationFinished })
          end
        end
        timer.performWithDelay( 3000, playSound )
      end
      ------------------------------------------------------------------------------
      ------------------------------------------------------------------------------
      closeWaitingBlur = function(noDelay)
        local function removeGroup(cb1)
          display.remove(waitingBlur)
          waitingBlur = nil
          display.remove(textOnWaitingBlur)
          textOnWaitingBlur = nil
        end
        if noDelay then
          transition.to(waitingBlur, { alpha = 0, onComplete = removeGroup, time = 0})
        else
          transition.to(waitingBlur, { alpha = 0, onComplete = removeGroup})
        end
        transition.to(textOnWaitingBlur, { alpha = 0, onComplete = removeGroup})
        return true
      end

      ------------------------------------------------------------------------------
      ------------------------------------------------------------------------------
      showWaitingBlur = function()
        waitingBlur = display.captureScreen()
        waitingBlur.x, waitingBlur.y = centerX, centerY
        waitingBlur:setFillColor(.4, .4, .4)
        waitingBlur.fill.effect = "filter.blur"
        waitingBlur.alpha = 0
        textOnWaitingBlur = display.newText({
          text = texts.general.pleaseWait,
          x = centerX,
          y = centerY + screenHeight,
          font = fonts.lalezar,
          fontSize = 46,
        })

        transition.to(waitingBlur, {alpha = 1, delay = 500})
        transition.to(textOnWaitingBlur, {y = centerY, delay = 500})
        textOnWaitingBlur:setFillColor(1, 1, 1, 1)
        waitingBlur:addEventListener("tap", function() return true end)
        waitingBlur:addEventListener("touch", function() return true end)
      end
      -------------------------------------------------------------------------------
      -------------------------------------------------------------------------------
      closeLoading()
      ---------------------------------------
    end
    function coloringPage:close()
      isDisplaying = false
      if #properties > 0 then
        for n = #properties, 1, - 1 do
          display.remove(properties[n])
          properties[n] = nil
        end
      end
      coloringPage.canvas:close()
      coloringPage.canvas = nil
      canvas = nil
      audio.stop(imageSound)
      audio.stop(imagePoem)
      if resumeSound then
        playSound()
      end
      sound = nil
    end

    return coloringPage
