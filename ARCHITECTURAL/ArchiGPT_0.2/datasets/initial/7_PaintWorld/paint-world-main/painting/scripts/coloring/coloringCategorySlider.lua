local widget = require( "widget" )

local sliderTwo = {}

function sliderTwo:newSlider(params)
  local numOfVisibleObjects = params.numOfVisibleObjects or 3
  local selectedIndex = params.selectedIndex or 1
  local objects = params.objects
  local height = params.height
  local width = params.width
  local x = params.x or 0
  local y = params.y or 0
  local callBack = params.callBack or nil
  local parent = params.parent or display.currentStage
  local hasTitle = params.hasTitle
  local resizeRatio = 14 / 20
  local marginRatio = 3 / 20
  local baseItemWidth = width / numOfVisibleObjects
  local smallerItemWidth = baseItemWidth * resizeRatio
  local marginWidth = baseItemWidth * marginRatio
  local regularFontSize, selectedFontSize = 22, 42
  local centerX = x + width / 2
  local centerY = y + height / 2
  local startingPointX = -width / 2 + marginWidth
  local startingPointY = -height / 35
  local items = {}
  local thumbnails = {}
  local startingIndex, index = 1, selectedIndex
  local slidingDuration = 1

  local isScrolling = false
  local selectedItemX
  ---------------------------------------------------------------
  local container = display.newContainer(width, height)
  container.anchorX = 0.5
  container.anchorY = 0.5
  container.x = x
  container.y = y


  local containerBackground = display.newRect(0, 0, 3000, 3000)
  containerBackground:setFillColor(.2, .4, .1, 0)
  containerBackground.isHitTestable = true
  container:insert(containerBackground)

  function setItemsStartMoveX()
    for i = 1, #items do
      items[i].isFocus = false
      items[i].startMoveX = items[i].x
      items[i].thumbnail.startMoveX = items[i].thumbnail.x
      items[i].thumbnailBackground.startMoveX = items[i].thumbnailBackground.x
      items[i].lock.startMoveX = items[i].lock.x
    end
  end

  local function containerTouchHandler(event)
    if event.phase == "began" then
      setItemsStartMoveX()
      isScrolling = true
      display.getCurrentStage():setFocus( containerBackground )
    end
    if isScrolling then
      if event.phase == "moved" then
        local dx = event.x - event.xStart
        if dx > 0 then
          container:scrollRight(dx)
        else
          container:scrollLeft(dx)
        end
      elseif event.phase == "ended" or event.phase == "cancelled" then
        isScrolling = false
        display.getCurrentStage():setFocus( nil )
      end
    end
  end

  local function itemTouchHandler(event)
    if event.phase == "began" then
      setItemsStartMoveX()
      display.getCurrentStage():setFocus( event.target )
      event.target.isFocus = true
    end
    if event.target.isFocus then
      if event.phase == "moved" then
        local dx = math.abs( event.x - event.xStart )
        if ( dx > 10 ) then
          isScrolling = true
          event.target.isFocus = false
          display.getCurrentStage():setFocus(containerBackground)
          containerTouchHandler( event )
        end

      elseif event.phase == "ended" or event.phase == "cancelled" then
        if isScrolling then
          isScrolling = false
        else
          event.target.isFocus = false
          display.getCurrentStage():setFocus(nil)
          callBack(event.target.object)
        end
      end
    end
    return true
  end

  for i = 1, #objects do
    local directory
    if objects[i].downloaded then
      directory = system.DocumentsDirectory
    else
      directory = nil
    end
    items[i] = display.newImage(container, "images/imageFrame.png")
    local resizeFactor = items[i].width / smallerItemWidth
    local fontSize = regularFontSize
    if i == selectedIndex then
      resizeFactor = items[i].width / baseItemWidth
      fontSize = selectedFontSize
    end
    items[i].width = items[i].width / resizeFactor
    items[i].height = items[i].height / resizeFactor
    items[i].x = startingPointX + items[i].width / 2
    items[i].y = startingPointY

    if i == selectedIndex then
      selectedItemX = items[i].x
    end

    items[i].thumbnailBackground = display.newRect(container, items[i].x, items[i].y + items[i].height * 0.00, items[i].width * 0.8, items[i].height * 0.83)
    items[i].thumbnailBackground:setFillColor(1, 1, 1, 1)
    items[i].thumbnail = display.newRect(container, items[i].x, items[i].y + items[i].height * 0.00, items[i].width * 0.8, items[i].height * 0.83)
    items[i].object = objects[i]

    local paint = {
      type = "image",
      filename = objects[i].thumbnail,
      baseDir = directory
    }
    items[i].thumbnail.fill = paint
    -- items[i].thumbnail.fill = paint
    items[i].lock = display.newImage(container, "images/coloring/lock.png")
    items[i].lock.x = items[i].thumbnail.x + .35 * items[i].thumbnail.width
    items[i].lock.y = items[i].thumbnail.y - .35 * items[i].thumbnail.height
    items[i].lock.width = items[i].thumbnail.width * .3
    items[i].lock.height = items[i].thumbnail.width * .3
    if objects[i].bitmap then
      items[i].lock.isVisible = false
    end
    startingPointX = startingPointX + items[i].width + marginWidth


  end
  items[index]:addEventListener("touch", itemTouchHandler)
  containerBackground:addEventListener("touch", containerTouchHandler)

  function changeIndex(newIndex)
    local temp
    local indexWidth, indexHeight = items[index].width, items[index].height
    local newWidth, newHeight = items[newIndex].width, items[newIndex].height
    local dWidth = math.abs(indexWidth - newWidth) / 2
    items[index]:removeEventListener("touch", itemTouchHandler)
    if newIndex < index then
      items[index].x = items[index].x + dWidth
      items[index].startMoveX = items[index].startMoveX + dWidth
      items[index].thumbnail.startMoveX = items[index].thumbnail.startMoveX + dWidth
      items[index].thumbnailBackground.startMoveX = items[index].thumbnailBackground.startMoveX + dWidth
      items[index].lock.startMoveX = items[index].lock.startMoveX + dWidth
      items[newIndex].x = items[newIndex].x + dWidth
      items[newIndex].startMoveX = items[newIndex].startMoveX + dWidth
      items[newIndex].thumbnail.startMoveX = items[newIndex].thumbnail.startMoveX + dWidth
      items[newIndex].thumbnailBackground.startMoveX = items[newIndex].thumbnailBackground.startMoveX + dWidth
      items[newIndex].lock.startMoveX = items[newIndex].lock.startMoveX + dWidth
    else
      items[index].x = items[index].x - dWidth
      items[index].startMoveX = items[index].startMoveX - dWidth
      items[index].thumbnail.startMoveX = items[index].thumbnail.startMoveX - dWidth
      items[index].thumbnailBackground.startMoveX = items[index].thumbnailBackground.startMoveX - dWidth
      items[index].lock.startMoveX = items[index].lock.startMoveX - dWidth
      items[newIndex].x = items[newIndex].x - dWidth
      items[newIndex].startMoveX = items[newIndex].startMoveX - dWidth
      items[newIndex].thumbnail.startMoveX = items[newIndex].thumbnail.startMoveX - dWidth
      items[newIndex].thumbnailBackground.startMoveX = items[newIndex].thumbnailBackground.startMoveX - dWidth
      items[newIndex].lock.startMoveX = items[newIndex].lock.startMoveX - dWidth
    end
    items[index].width, items[index].height = newWidth, newHeight
    items[index].thumbnail.width, items[index].thumbnail.height = items[index].width * 0.8, items[index].height * 0.83
    items[index].thumbnail.x, items[index].thumbnail.y = items[index].x, items[index].y + items[index].height * 0.00
    items[index].thumbnailBackground.x, items[index].thumbnailBackground.y = items[index].x, items[index].y + items[index].height * 0.00
    items[index].thumbnailBackground.width, items[index].thumbnailBackground.height = items[index].width * 0.8, items[index].height * 0.83
    items[index].lock.x, items[index].lock.y = items[index].thumbnail.x + .35 * items[index].thumbnail.width, items[index].thumbnail.y - 0.35 * items[index].thumbnail.height
    items[index].lock.width, items[index].lock.height = items[index].thumbnail.width * 0.3, items[index].thumbnail.width * 0.3

    items[newIndex].width, items[newIndex].height = indexWidth, indexHeight
    items[newIndex].thumbnail.width, items[newIndex].thumbnail.height = items[newIndex].width * 0.8, items[newIndex].height * 0.83
    items[newIndex].thumbnail.x, items[newIndex].thumbnail.y = items[newIndex].x, items[newIndex].y + items[newIndex].height * 0.00
    items[newIndex].thumbnailBackground.x, items[newIndex].thumbnailBackground.y = items[newIndex].x, items[newIndex].y + items[newIndex].height * 0.00
    items[newIndex].thumbnailBackground.width, items[newIndex].thumbnailBackground.height = items[newIndex].width * 0.8, items[newIndex].height * 0.83
    items[newIndex].lock.x, items[newIndex].lock.y = items[newIndex].thumbnail.x + .35 * items[newIndex].thumbnail.width, items[newIndex].thumbnail.y - 0.35 * items[newIndex].thumbnail.height
    items[newIndex].lock.width, items[newIndex].lock.height = items[newIndex].thumbnail.width * 0.3, items[newIndex].thumbnail.width * 0.3
    index = newIndex
    items[index]:addEventListener("touch", itemTouchHandler)
  end

  function container:scrollRight(dx)
    if dx == nil then
      setItemsStartMoveX()
      if index > 1 then
        dx = selectedItemX - items[index - 1 ].x
      end
    end
    if items[1].x > - width / 2 + marginWidth + baseItemWidth / 2 + smallerItemWidth then
      return
    else
      for i = 1, #items do
        items[i].x = items[i].startMoveX + dx
        items[i].thumbnail.x = items[i].thumbnail.startMoveX + dx
        items[i].thumbnailBackground.x = items[i].thumbnailBackground.startMoveX + dx
        items[i].lock.x = items[i].lock.startMoveX + dx
      end
      if(items[index].x > selectedItemX + baseItemWidth / 2) then
        changeIndex(index - 1)
      end
    end
  end

  function container:scrollLeft(dx)
    if dx == nil then
      setItemsStartMoveX()
      if index < #items then
        dx = selectedItemX - items[index + 1 ].x
      end
    end
    if items[#items].x < width / 2 - marginWidth - baseItemWidth / 2 - smallerItemWidth then
      return
    else
      for i = 1, #items do
        items[i].x = items[i].startMoveX + dx
        items[i].thumbnail.x = items[i].thumbnail.startMoveX + dx
        items[i].thumbnailBackground.x = items[i].thumbnailBackground.startMoveX + dx
        items[i].lock.x = items[i].lock.startMoveX + dx

      end
      if(items[index].x < selectedItemX - baseItemWidth / 2) then
        changeIndex(index + 1)
      end
    end
  end

  function container:close()
    for i = 1, #items do
      display.remove(items[i].lock)
      items[i].lock = nil
      display.remove(items[i].thumbnail)
      items[i].thumbnail = nil
      display.remove(items[i].thumbnailBackground)
      items[i].thumbnailBackground = nil
      display.remove(items[i])
      items[i] = nil
    end
    items = nil
    display.remove(container)
  end
  return container
end

return sliderTwo
