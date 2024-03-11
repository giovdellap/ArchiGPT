local widget = require( "widget" )

local sliderThree = {}

function sliderThree:newSlider(params)
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
  local regularFontSize, selectedFontSize = 18, 28
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
      items[i].title.startMoveX = items[i].title.x
      items[i].nameFrame.startMoveX = items[i].nameFrame.x
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
    items[i].thumbnail = display.newRect(container, items[i].x, items[i].y + items[i].height * 0.00, items[i].width * 0.8, items[i].height * 0.83)
    items[i].object = objects[i]
    local paint = {
      type = "image",
      filename = objects[i].main,
      baseDir = system.TemporaryDirectory
    }
    items[i].thumbnail.fill = paint
    items[i].nameFrame = display.newImage(container, "images/selected/nameBackgroundFrame.png", items[i].x, items[i].y + items[i].height * 0.43)
    items[i].nameFrame.width = items[i].width * 0.8
    items[i].nameFrame.height = items[i].height * 0.2
    local options = {
      parent = container,
      text = objects[i].title,
      x = items[i].x,
      y = items[i].y + items[i].height * 0.435,
      width = items[i].width * 0.79,
      height = items[i].height * 0.12,
      font = fonts.bYekan,
      fontSize = fontSize,
      align = "center"
    }
    items[i].title = display.newText(options)
    items[i].title:setFillColor(0, 0, 0)
    startingPointX = startingPointX + items[i].width + marginWidth


  end
  items[index]:addEventListener("touch", itemTouchHandler)
  containerBackground:addEventListener("touch", containerTouchHandler)

  function changeIndex(newIndex)
    local temp
    local indexWidth, indexHeight = items[index].width, items[index].height
    local newWidth, newHeight = items[newIndex].width, items[newIndex].height
    local dWidth = math.abs(indexWidth - newWidth) / 2
    local startingPoint = items[1].x - items[1].width / 2
    items[index]:removeEventListener("touch", itemTouchHandler)
    if newIndex < index then
      items[index].x = items[index].x + dWidth
      items[index].startMoveX = items[index].startMoveX + dWidth
      items[index].title.startMoveX = items[index].title.startMoveX + dWidth
      items[index].thumbnail.startMoveX = items[index].thumbnail.startMoveX + dWidth
      items[index].nameFrame.startMoveX = items[index].nameFrame.startMoveX + dWidth
      items[newIndex].x = items[newIndex].x + dWidth
      items[newIndex].startMoveX = items[newIndex].startMoveX + dWidth
      items[newIndex].title.startMoveX = items[newIndex].title.startMoveX + dWidth
      items[newIndex].thumbnail.startMoveX = items[newIndex].thumbnail.startMoveX + dWidth
      items[newIndex].nameFrame.startMoveX = items[newIndex].nameFrame.startMoveX + dWidth
    else
      items[index].x = items[index].x - dWidth
      items[index].startMoveX = items[index].startMoveX - dWidth
      items[index].title.startMoveX = items[index].title.startMoveX - dWidth
      items[index].thumbnail.startMoveX = items[index].thumbnail.startMoveX - dWidth
      items[index].nameFrame.startMoveX = items[index].nameFrame.startMoveX - dWidth
      items[newIndex].x = items[newIndex].x - dWidth
      items[newIndex].startMoveX = items[newIndex].startMoveX - dWidth
      items[newIndex].title.startMoveX = items[newIndex].title.startMoveX - dWidth
      items[newIndex].thumbnail.startMoveX = items[newIndex].thumbnail.startMoveX - dWidth
      items[newIndex].nameFrame.startMoveX = items[newIndex].nameFrame.startMoveX - dWidth
    end
    items[index].width, items[index].height = newWidth, newHeight
    temp = items[index].title.startMoveX
    display.remove(items[index].title)
    local options = {
      parent = container,
      text = objects[index].title,
      x = items[index].x,
      y = items[index].y + items[index].height * 0.435,
      width = items[index].width * 0.79,
      height = items[index].height * 0.12,
      font = fonts.bYekan,
      fontSize = regularFontSize,
      align = "center"
    }
    items[index].title = display.newText(options)
    items[index].title:setFillColor(0, 0, 0)
    items[index].title.startMoveX = temp
    items[index].thumbnail.width, items[index].thumbnail.height = items[index].width * 0.8, items[index].height * 0.83
    items[index].thumbnail.x, items[index].thumbnail.y = items[index].x, items[index].y + items[index].height * 0.00
    items[index].nameFrame.width, items[index].nameFrame.height = items[index].width * 0.8, items[index].height * 0.2
    items[index].nameFrame.x, items[index].nameFrame.y = items[index].x, items[index].y + items[index].height * 0.43


    items[newIndex].width, items[newIndex].height = indexWidth, indexHeight
    temp = items[newIndex].title.startMoveX
    display.remove(items[newIndex].title)
    options = {
      parent = container,
      text = objects[newIndex].title,
      x = items[newIndex].x,
      y = items[newIndex].y + items[newIndex].height * 0.435,
      width = items[newIndex].width * 0.79,
      height = items[newIndex].height * 0.12,
      font = fonts.bYekan,
      fontSize = selectedFontSize,
      align = "center"
    }
    items[newIndex].title = display.newText(options)
    items[newIndex].title:setFillColor(0, 0, 0)
    items[newIndex].title.startMoveX = temp
    items[newIndex].thumbnail.width, items[newIndex].thumbnail.height = items[newIndex].width * 0.8, items[newIndex].height * 0.83
    items[newIndex].thumbnail.x, items[newIndex].thumbnail.y = items[newIndex].x, items[newIndex].y + items[newIndex].height * 0.00
    items[newIndex].nameFrame.width, items[newIndex].nameFrame.height = items[newIndex].width * 0.8, items[newIndex].height * 0.2
    items[newIndex].nameFrame.x, items[newIndex].nameFrame.y = items[newIndex].x, items[newIndex].y + items[newIndex].height * 0.43
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
        items[i].title.x = items[i].title.startMoveX + dx
        items[i].nameFrame.x = items[i].nameFrame.startMoveX + dx
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
        items[i].title.x = items[i].title.startMoveX + dx
        items[i].nameFrame.x = items[i].nameFrame.startMoveX + dx

      end
      if(items[index].x < selectedItemX - baseItemWidth / 2) then
        changeIndex(index + 1)
      end
    end
  end


  function container:close()
    for i = 1, #items do
      display.remove(items[i].thumbnail)
      items[i].thumbnail = nil
      display.remove(items[i])
      items[i] = nil
    end
    items = nil
    display.remove(container)
  end
  return container
end

return sliderThree
