local strokeSize = {}
local properties = {}
strokeSize.group = nil
local margin = 20
local baseWidth, baseHeight = nil
local hiddenPointX = 0
local hiddenPointY = -200
local visiblePointX = 0
local visiblePointY = 0
local isSelecting = false
local blur = nil

function strokeSize.render(container, changeHandler)
  local choicesGroup = display.newGroup()
  container:insert(choicesGroup)
  choicesGroup.x = hiddenPointX
  choicesGroup.y = hiddenPointY
  local selectedGroup = display.newGroup()
  container:insert(selectedGroup)
  table.insert(properties, choicesGroup)
  table.insert(properties, selectedGroup)
  local choiceBackground = display.newImage(choicesGroup, "images/paint/strokeCircleHolder.png")
  choiceBackground.x = 240
  choiceBackground.y = -13

  local selectedStrokeBackground = display.newImage( container, "images/paint/strokeCircleBack.png")
  selectedStrokeBackground.name = "selectedStrokeBackground"

  local selectedStroke = display.newImage(container, "images/paint/strokeCircle.png")
  selectedStroke.y = -15
  selectedStroke.name = "selectedStroke"

  local choice1 = display.newImage( choicesGroup, "images/paint/strokeCircle.png")
  choice1.y = -15
  choice1.x = 120
  baseWidth = choice1.width
  baseHeight = choice1.height
  choice1.strokeSize = 12
  choice1.name = "choice"

  local choice2 = display.newImage( choicesGroup, "images/paint/strokeCircle.png")
  choice2.y = -15
  choice2.x = 195
  choice2.width = 1.15 * baseWidth
  choice2.height = 1.15 * baseHeight
  choice2.strokeSize = 18
  choice2.name = "choice"

  local choice3 = display.newImage( choicesGroup, "images/paint/strokeCircle.png")
  choice3.y = -15
  choice3.x = 275
  choice3.width = 1.3 * baseWidth
  choice3.height = 1.3 * baseHeight
  choice3.strokeSize = 24
  choice3.name = "choice"

  local choice4 = display.newImage( choicesGroup, "images/paint/strokeCircle.png")
  choice4.y = -15
  choice4.x = 365
  choice4.width = 1.45 * baseWidth
  choice4.height = 1.45 * baseHeight
  choice4.strokeSize = 30
  choice4.name = "choice"

  local function changeStrokeWidth(width, buttonWidth, buttonHeight)
    changeHandler(tonumber(width))
    selectedStroke.width = buttonWidth
    selectedStroke.height = buttonHeight
  end

  local function closeStrokeChanger()
    local function removeGroup()
      display.remove(blur)
      blur = nil
    end
    isSelecting = false
    transition.to(blur, {alpha = 0})
    transition.to(choicesGroup, {y = hiddenPointY, x = hiddenPointX, onComplete = removeGroup})
  end

  local function showStrokeChanger()
    if isSelecting then
      closeStrokeChanger()
    else
      blur = display.captureScreen()
      blur.x, blur.y = centerX, centerY
      blur:setFillColor(.4, .4, .4)
      blur.fill.effect = "filter.blur"
      blur.alpha = 0
      blur:addEventListener("tap", closeStrokeChanger)
      blur:addEventListener("touch", function() return true end)
      transition.to(blur, {alpha = 1})
      transition.to(choicesGroup, {x = visiblePointX, y = visiblePointY})
      container:toFront()
      isSelecting = true
    end
    end--

    local function touchButtonHandler(event)
      if event.phase == "began" then
        display.getCurrentStage():setFocus(event.target, event.id)
        audio.stop(clickSoundCannel)
        audio.play(clickSound, clickPlayOptions)
        event.target.width = event.target.width * clickResizeFactor
        event.target.height = event.target.height * clickResizeFactor
      elseif event.phase == "ended" then
        event.target.width = event.target.width / clickResizeFactor
        event.target.height = event.target.height / clickResizeFactor
        local buttonName = event.target.name
        local button = event.target
        if button.name == "selectedStroke" or button.name == "selectedStrokeBackground" then
          showStrokeChanger()
        elseif button.name == "choice" then
          changeStrokeWidth(button.strokeSize, button.width, button.height)
          closeStrokeChanger()
        end
        display.getCurrentStage():setFocus(nil, event.id)
      end
      return true
      end --
      selectedStroke:addEventListener( "touch", touchButtonHandler )
      selectedStrokeBackground:addEventListener( "touch", touchButtonHandler )
      choice1:addEventListener( "touch", touchButtonHandler )
      choice2:addEventListener( "touch", touchButtonHandler )
      choice3:addEventListener( "touch", touchButtonHandler )
      choice4:addEventListener( "touch", touchButtonHandler )

    end
    function strokeSize.close()
      if #properties > 0 then
        for n = #properties, 1, - 1 do
          display.remove(properties[n])
          properties[n] = nil
        end
      end
    end
    return strokeSize
