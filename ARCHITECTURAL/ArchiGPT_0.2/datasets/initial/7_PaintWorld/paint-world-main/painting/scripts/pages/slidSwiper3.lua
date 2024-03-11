local widget = require("widget")
local campaign = {}
local properties = {}
function campaign:render(params)
  local background = display.newImage( "images/slide/slide3.png")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local cheetah = display.newImage("images/cheetah.png")
  cheetah.x = cheetahX
  cheetah.y = cheetahY
  table.insert(properties, cheetah)

  local deer = display.newImage("images/deer.png")
  deer.x = 135
  deer.y = display.actualContentHeight - 145
  table.insert(properties, deer)

  local startCodeBackground = display.newRoundedRect( centerX, centerY + 260, 400, 80, 10)
  startCodeBackground:setFillColor(0 / 255, 169 / 255, 126 / 255)
  startCodeBackground.name = "startCode"
  options = {
    text = texts.slider.start,
    x = startCodeBackground.x,
    y = startCodeBackground.y,
    font = fonts.lalezar,
    fontSize = 36
  }
  local startCode = display.newText(options)
  table.insert(properties, startCodeBackground)
  table.insert(properties, startCode)

  local function itemTouchHandler(event)
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.isFocus = true
      print("began")
    end
    if event.target.isFocus then
      if event.phase == "moved" then
        -- print("moved")
      elseif event.phase == "ended" or event.phase == "cancelled" then
        local dx = event.x - event.xStart
        if dx > 0 then
          navigate("slidSwiper2")
        end
        print("ended")
      end
    end
    return true
  end
  local buttonTouchHandlers = function (event)
    if not requestIsValid and event.target.name == "requestAuthenticationCode" then
      return
    end

    if not confirmIsValid and event.target.name == "confirmAuthenticationCode" then
      return
    end
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.width = event.target.width * clickResizeFactor
      event.target.height = event.target.height * clickResizeFactor
      if event.target.name == "chooseImage" and image then
        image.width = image.width * clickResizeFactor
        image.height = image.height * clickResizeFactor
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
      if event.target.name == "confirmAuthenticationCode" then
        confirmAuthenticationCodeFunc()
      elseif event.target.name == "requestAuthenticationCode" then
        requestAuthenticationCodeFuc()
      elseif event.target.name == "startCode" then
        navigate("selectOperator")
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  background:addEventListener( "touch", itemTouchHandler )
  startCodeBackground:addEventListener("touch", buttonTouchHandlers)

  renderSoundButton()

end
function campaign:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end
return campaign
