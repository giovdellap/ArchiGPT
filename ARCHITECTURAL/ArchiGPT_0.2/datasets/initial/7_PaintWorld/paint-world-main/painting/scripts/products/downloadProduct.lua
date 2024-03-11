local widget = require("widget")
local properties = {}

local function downloadProduct(url)
  local blur = nil
  local text = nil

  local function closeBlur()
    local function removeGroup()
      for i = 1, #properties do
        display.remove(properties[i])
        properties[i] = nil
      end
      display.remove(blur)
      blur = nil
      if narration then
        stopNarration()
        narration = nil
      end
    end
    transition.to(blur, {alpha = 0})
    transition.to(group, {y = centerY + screenHeight, alpha = 0, onComplete = removeGroup})
    return true
  end

  blur = display.captureScreen()
  blur.x, blur.y = centerX, centerY
  blur:setFillColor(.4, .4, .4)
  blur.fill.effect = "filter.blur"
  blur.alpha = 0
  transition.to(blur, {alpha = 1})

  blur:addEventListener("tap", function() return true end)
  blur:addEventListener("touch", function() return true end)


end

return downloadProduct
