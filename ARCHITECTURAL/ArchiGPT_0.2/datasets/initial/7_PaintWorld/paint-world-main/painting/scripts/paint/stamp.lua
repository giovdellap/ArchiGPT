local lfs = require "lfs"
local widget = require("widget")
require("scripts.paint.stampFiles")

local stamps = {}
local centerX = display.contentCenterX
local centerY = display.contentCenterY
local screenTop = display.screenOriginY
local screenLeft = display.screenOriginX
local screenBottom = display.screenOriginY + display.actualContentHeight
local screenRight = display.screenOriginX + display.actualContentWidth
local screenWidth = screenRight - screenLeft
local screenHeight = screenBottom - screenTop
local group, background, list, scrollView
local images = {}
-- function to close the picker and return color values to the listener function
local function closePicker()
  local function removeGroup()
    display.remove(group)
    group = nil
    display.remove(blur)
    blur = nil
    if #images > 0 then
      for n = #images, 1, - 1 do
        display.remove(images[n])
        images[n] = nil
      end
    end
  end

  transition.to(blur, {alpha = 0})
  transition.to(group, {y = centerY + screenHeight, alpha = 0, onComplete = removeGroup})
  transition.to(scrollView, {y = centerY + screenHeight, alpha = 0, onComplete = removeGroup})

  return true
end

function stamps:showPicker(callBack)
  local function stampEventListener(event)
    if event.phase == "ended" then
      callBack(event.target.address)
      closePicker()
      return true
    end
    -- return true
  end
  local function stampEventListenerTap(event)
    callBack(event.target.address)
    closePicker()
    return true

    -- return true
  end
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

  -- create a display group to contain the picker
  group = display.newGroup()
  group.x, group.y = centerX - boxSize * .075, centerY + screenHeight
  group.alpha = 0

  -- create a bounding box for the picker
  background = display.newRoundedRect(group, 0, 0, boxSize * 2, boxSize * 1.65, boxSize / 5)
  background:setFillColor(1, 1, 1)
  background.x, background.y = boxSize * .075, boxSize * .075
  background:setStrokeColor(0, 0, 0, .6)
  background.strokeWidth = boxSize * .04


  local imageNames = stampFiles

  scrollView = widget.newScrollView
  {
    width = background.width,
    height = background.height * .95,
    scrollWidth = background.width * 10,
    horizontalScrollDisabled = true,
    verticalScrollingDisabled = false,
    hideBackground = true
  }
  scrollView.x = centerX
  scrollView.y = centerY + screenHeight

  local columnOneX = 160
  local columnTwoX = scrollView.width - 160
  local currentY = (background.height / 2 - background.height / 5 - background.height / 10)
  local marginY = background.height / 2.5 + background.height / 30

  for i = 1, #imageNames do
    images[i] = display.newImage(imageNames[i])
    local resizeFactor = images[i].height / (background.height / 3)
    images[i].width = images[i].width / resizeFactor
    images[i].height = images[i].height / resizeFactor


    if (i % 2 == 0) then
      images[i].x = columnTwoX
      images[i].y = currentY
      currentY = currentY + marginY
    else
      images[i].x = columnOneX
      images[i].y = currentY
    end
    scrollView:insert(images[i])
    images[i].address = imageNames[i]
    images[i]:addEventListener("touch", stampEventListener)
    images[i]:addEventListener("tap", stampEventListenerTap)
  end

  blur:addEventListener("tap", closePicker)
  blur:addEventListener("touch", function() return true end)
  background:addEventListener("touch", function() return true end)
  background:addEventListener("tap", function() return true end)

  transition.to(group, {y = centerY - boxSize * .075, alpha = 1})
  transition.to(blur, {alpha = 1})
  transition.to(scrollView, {y = centerY })
  transition.to(group, {y = centerY - boxSize * .075, alpha = 1})
  transition.to(blur, {alpha = 1})



end



return stamps
