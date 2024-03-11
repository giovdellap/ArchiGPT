local widget = require("widget")
local json = require("json")
local zip = require("plugin.zip")
local slideSwiper0 = {}
local properties = {}
function slideSwiper0:render(params)
  local showBlur, closeBlur, callBackFunc, showTextOnBlur, showAlert

  local background = display.newImage( "images/slide/slide0.png")
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
        print("ended")
        local dx = event.x - event.xStart
        if dx < 0 then
          navigate("slidSwiper1")
        end
      end
    end
    return true
  end
  background:addEventListener( "touch", itemTouchHandler )
  renderSoundButton()
end

function slideSwiper0:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end
return slideSwiper0
