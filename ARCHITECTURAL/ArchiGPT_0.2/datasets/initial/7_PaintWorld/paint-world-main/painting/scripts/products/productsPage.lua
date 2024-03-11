local buyPage = {}
local properties = {}
local isClosed = true
local widget = require("widget")
function buyPage:render(params)
  isClosed = false
  local buttonTouchHandlers
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  pageTitleBackground.name = "pageTitle"
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.products.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  table.insert(properties, pageTitle)

  local backButton = display.newImageRect( "images/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  local listBackground = display.newRoundedRect( centerX, centerY, 900, 400, 15 )
  listBackground:setFillColor(65 / 255, 118 / 255, 204 / 255)
  listBackground:setStrokeColor(1, 1, 1, 1)
  listBackground.strokeWidth = 10

  local options = {
    text = texts.products.pleaseWait,
    x = centerX,
    y = centerY,
    fontSize = 60,
    font = fonts.lalezar
  }
  local pleaseWaitText = display.newText(options)
  table.insert(properties, pleaseWaitText)

  local function callBack(productList)
    if isClosed then
      return
    end
    display.remove(pleaseWaitText)
    if productList then
      local options = {
        x = listBackground.x,
        y = listBackground.y,
        width = listBackground.width * 0.95,
        height = listBackground.height * 0.95,
        horizontalScrollDisabled = true,
        verticalScrollDisabled = false,
        hideBackground = true,
        backgroundColor = {1, 0, 0, 1},
      }
      local scroll = widget.newScrollView( options )
      scroll.isHitTestable = false
      table.insert(properties, scroll)

      local priceX = 150
      local titleX = 660
      local iconX = 30
      local initialY = 75
      local margin = 100
      -------------------------------------------------------- NEEDS TO BE FIXED
      for i = 1, #productList do

        local productTitleText = display.newText({
          text = productList[i].title,
          x = titleX,
          y = initialY + (i - 1) * margin,
          font = fonts.lalezar,
          fontSize = 42,
          anchorX = 1

        })
        scroll:insert(productTitleText)
        table.insert(properties, productTitleText)

        local productPriceText = display.newText({
          parent = scroll,
          text = productList[i].price,
          x = priceX,
          y = initialY + (i - 1) * margin,
          font = fonts.lalezar,
          fontSize = 42
        })
        scroll:insert(productPriceText)
        table.insert(properties, productPriceText)

        local buyItemIcon = display.newImage(scroll, "images/buyItem.png")
        buyItemIcon.width, buyItemIcon.height = 64, 64
        buyItemIcon.x = iconX
        buyItemIcon.y = (initialY + (i - 1) * margin) - 10
        buyItemIcon.name = "buyItemIcon"
        buyItemIcon.item = productList[i]
        scroll:insert(buyItemIcon)
        table.insert(properties, buyItemIcon)
        buyItemIcon:addEventListener("touch", buttonTouchHandlers)
        --------------------------------------------------------------------------

      end

    else
      local options = {
        text = texts.products.problemInFetchingList,
        x = centerX,
        y = centerY,
        width = listBackground.width * .95,
        fontSize = 42,
        font = fonts.lalezar,
        align = "center"
      }
      local problemInFetchingText = display.newText(options)
      table.insert(properties, problemInFetchingText)
    end
  end

  products:fetchProdutlist(callBack)

  buttonTouchHandlers = function (event)
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.width = event.target.width * clickResizeFactor
      event.target.height = event.target.height * clickResizeFactor
      display.getCurrentStage():setFocus( event.target )
      event.target.isFocus = true
      return true
    end
    if not event.target.isFocus then
      return false
    end
    if event.phase == "ended" then
      event.target.width = event.target.width / clickResizeFactor
      event.target.height = event.target.height / clickResizeFactor
      if event.target.name == "back" then
        back()
      elseif event.target.name == "buyItemIcon" then
        navigate("buyProduct", event.target.item)
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end

  backButton:addEventListener("touch", buttonTouchHandlers)


end
function buyPage:close()
  isClosed = true
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end
return buyPage
