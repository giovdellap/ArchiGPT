local widget = require("widget")
local json = require("json")
local zip = require("plugin.zip")
local slideSwiper1 = {}
local properties = {}
function slideSwiper1:render(params)
  local showBlur, closeBlur, callBackFunc, showTextOnBlur, showAlert

  local background = display.newImage( "images/slide/slide1.png")
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

  -- local exit = display.newImage( "images/home/exit.png")
  -- exit.height, exit.width = 126, 126
  -- exit.x = display.contentCenterX - 450
  -- exit.y = centerY + 0.08 * screenHeight
  -- exit.name = "exit"
  -- table.insert(properties, exit)

  -- local function exitFunction()
  --   print("exit")
  --   if isAlertRendered then
  --     return true
  --   else
  --     isExitAlertRendered = true
  --     alert({
  --       textImage = images.alerts.exit,
  --       isYesNo = true,
  --       callBack = function(index)
  --         if index == 1 then
  --           os.exit()
  --         elseif index == 2 then
  --         end
  --       end
  --     })
  --     return true
  --   end
  -- end -- end of exit function


  local function itemTouchHandler(event)
    -- if event.target.name == "exit" then
    --   exitFunction()
    -- end
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.isFocus = true
      print("began")
    end

    if event.target.isFocus then
      if event.phase == "moved" then
        print("moved")
      elseif event.phase == "ended" or event.phase == "cancelled" then
        print("ended")
        local dx = event.x - event.xStart
        if dx < 0 then
          navigate("slidSwiper2")
        end
      end
    end
    return true
  end
  background:addEventListener( "touch", itemTouchHandler )
  -- exit:addEventListener("touch", itemTouchHandler)

  renderSoundButton()
end


function slideSwiper1:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end
return slideSwiper1
