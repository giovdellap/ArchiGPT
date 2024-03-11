local widget = require("widget")
local campaign = {}
local properties = {}
function campaign:render(params)
  local background = display.newImage( "images/slide/slide2.png")
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
        local dx = event.x - event.xStart
        if dx > 0 then
          navigate("slidSwiper1")
        else
          navigate("slidSwiper3")
        end


      end
    end
    return true
  end
  background:addEventListener( "touch", itemTouchHandler )

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
