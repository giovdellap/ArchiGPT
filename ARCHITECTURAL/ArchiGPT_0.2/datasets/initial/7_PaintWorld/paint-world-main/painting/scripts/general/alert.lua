local lfs = require "lfs"
local widget = require("widget")
local properties = {}

local numberOfButtonsInRow = 3
local narration = nil
isAlertRendered = false
function closeAlert()
  if not isAlertRendered then
    return
  end
  local function removeGroup()
    for i = 1, #properties do
      display.remove(properties[i])
      properties[i] = nil
    end
    display.remove(group)
    group = nil
    display.remove(blur)
    blur = nil
    if narration then
      stopNarration()
      narration = nil
    end
  end
  isAlertRendered = false
  transition.to(blur, {alpha = 0})
  transition.to(group, {y = centerY + screenHeight, alpha = 0, onComplete = removeGroup})
  return true
end

function alert(object)
  local text = object.text
  local buttons = object.buttons
  local callBack = object.callBack
  local isYesNo = object.isYesNo
  local isCustomeBtn = object.isCustomeBtn
  narration = object.narration
  local textImage = object.textImage
  local lockTouch = object.lockTouch

  local function stampEventListener(event)
    if event.phase == "ended" then
      closeAlert()
      return true
    end
  end

  local function stampEventListenerTap(event)
    closeAlert()
    return true
  end

  local function buttonTouchHandlers(event)
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.width = event.target.width * clickResizeFactor
      event.target.height = event.target.height * clickResizeFactor
    elseif event.phase == "ended" then
      event.target.width = event.target.width / clickResizeFactor
      event.target.height = event.target.height / clickResizeFactor
      callBack(event.target.index)
      closeAlert()
    end
  end
  -- function openCamera()
  --   local function onComplete( event )
  --      local photo = event.target
  --      print( "photo w,h = " .. photo.width .. "," .. photo.height )
  --   end
  -- end
  --
  -- if media.hasSource( media.Camera ) then
  --     media.capturePhoto( { listener=onComplete } )
  -- else
  --     native.showAlert( "Corona", "This device does not have a camera.", { "OK" } )
  -- end

  blur = display.captureScreen()
  blur.x, blur.y = centerX, centerY
  blur:setFillColor(.4, .4, .4)
  blur.fill.effect = "filter.blur"
  blur.alpha = 0
  transition.to(blur, {alpha = 1})

  if screenWidth < screenHeight then
    boxSize = screenWidth * .4
  else
    boxSize = screenHeight * .4
  end


  group = display.newGroup()
  group.x, group.y = centerX - boxSize * .075, centerY + screenHeight
  group.alpha = 0

  background = display.newRoundedRect(group, 0, 0, boxSize * 2.2, boxSize * 1.3, boxSize / 10)
  background:setFillColor(1, 1, 1)
  background.x, background.y = boxSize * .075, boxSize * .075
  background:setStrokeColor(0, 0, 0, .6)
  background.strokeWidth = boxSize * .04

  blur:addEventListener("tap", function() if not lockTouch then closeAlert() end end )
  blur:addEventListener("touch", function() return true end)
  background:addEventListener("touch", function() return true end)
  background:addEventListener("tap", function() return true end)

  local numberOfRows = 1
  if buttons then
    if #buttons < numberOfButtonsInRow then
      numberOfButtonsInRow = #buttons
    end
    numberOfRows = math.ceil(#buttons / numberOfButtonsInRow)
  end
  local availableRowWidth = background.width * 0.9
  local buttonsHeight = 75
  local marginTop = 30
  local marginButtons = (background.width - availableRowWidth) / 3
  local buttonsWidth = (availableRowWidth - ((numberOfButtonsInRow - 1) * marginButtons)) / numberOfButtonsInRow
  local marginColumns = marginButtons + buttonsWidth
  local marginRows = buttonsHeight / 5 + buttonsHeight
  local columnsDistance = buttonsWidth / 2 + marginColumns
  local rowsDistance = buttonsHeight / 2 + marginRows
  if numberOfRows == 1 then
    marginTop = 80
  end
  local messageText
  if textImage then
    messageText = display.newImage(group, textImage)
  else
    local options = {
      parent = group,
      text = text,
      width = 650,
      fontSize = 40,
      font = fonts.lalezar,
      align = "center"
    }
    messageText = display.newText(options)
    messageText:setFillColor(0, 0, 0, 1)
  end
  messageText.y = background.y - background.height / 2 + messageText.height / 2 + marginTop
  table.insert(properties, messageText)

  local okimg = ""
  local cancelimg = ""
  local camera = ""
  local okx, oky, cancelx, cancely, camerax, cameray
  if isCustomeBtn then
    okimg = "images/gallery.png"
    cancelimg = "images/memory.png"
    cameraimg = "images/camera.png"
    okx = -170
    oky = 100
    cancelx = 30
    cancely = oky
    camerax = 220
    cameray = oky
    local camera = display.newImage(group, cameraimg)
    camera.width = camera.width * 1.3
    camera.height = camera.height * 1.3
    camera.x = camerax
    -- camera.y = messageText.y + messageText.height / 2 + marginTop + camera.height / 2
    camera.y = cameray
    camera.index = 3
    camera:addEventListener("touch", buttonTouchHandlers)
    table.insert( properties, camera )

  else
    okimg = "images/ok.png"
    cancelimg = "images/cancel.png"
    okx = -120
    oky = 150
    cancelx = 170
    cancely = oky
  end
  if isYesNo then
    local ok = display.newImage(group, okimg)
    ok.width = ok.width * 1.3
    ok.height = ok.height * 1.3
    ok.x = okx
    -- ok.y = messageText.y + messageText.height / 2 + marginTop + ok.height / 2
    ok.y = oky
    ok.index = 1
    ok:addEventListener("touch", buttonTouchHandlers)
    table.insert( properties, ok )

    local cancel = display.newImage(group, cancelimg)
    cancel.width = cancel.width * 1.3
    cancel.height = cancel.height * 1.3
    cancel.x = cancelx
    -- cancel.y = messageText.y + messageText.height / 2 + marginTop + cancel.height / 2
    cancel.y = ok.y
    cancel.index = 2
    cancel:addEventListener("touch", buttonTouchHandlers)
    table.insert( properties, cancel )

  elseif buttons then
    local baseX = background.x - background.width / 2 + (background.width - availableRowWidth) / 2 + buttonsWidth / 2
    local baseY = messageText.y + messageText.height / 2 + marginTop + buttonsHeight / 2
    for i = 1, #buttons do
      local row = math.floor((i - 1) / numberOfButtonsInRow)
      local column = math.fmod(i - 1, numberOfButtonsInRow)
      local x = baseX + column * marginColumns
      local y = baseY + row * marginRows
      local buttonBackground = display.newRoundedRect( group, x, y, buttonsWidth, buttonsHeight, 5 )
      buttonBackground.index = i
      buttonBackground:setFillColor(120 / 255, 120 / 255, 120 / 255, 1)
      local options = {
        parent = group,
        text = buttons[i],
        x = buttonBackground.x,
        y = buttonBackground.y,
        width = buttonBackground.width,
        height = buttonBackground.height,
        font = fonts.lalezar,
        fontSize = 36,
        align = "center"
      }
      local buttonText = display.newText( options )
      buttonText:setFillColor(1, 1, 1, 1)
      table.insert(properties, buttonText)
      buttonBackground:addEventListener("touch", buttonTouchHandlers)
    end
  end
  local function narrationPlayer()
    if narration then
      playNarration(narration)
    end
  end
  transition.to(group, {y = centerY - boxSize * .075, alpha = 1})
  transition.to(blur, {alpha = 1, onComplete = narrationPlayer})
  isAlertRendered = true

end
