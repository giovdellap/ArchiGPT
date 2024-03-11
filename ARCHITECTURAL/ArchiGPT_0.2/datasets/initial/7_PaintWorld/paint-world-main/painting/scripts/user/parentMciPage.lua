
local json = require("json")
local zip = require("plugin.zip")
local buyProductPage = {}
local properties = {}

function buyProductPage:render(param)
  print("parent mci page")
  print(appConfig:getTypeOperator())
  ------------------------------------------------------------------------------
  -- Declarations
  ------------------------------------------------------------------------------
  local buttonTouchHandlers, textListener
  local requestIsValid = false
  local confirmIsValid = false
  local requestAuthenticationCodeFuc, downloadProduct, uncompressProduct
  local showBlur, closeBlur, changeText, callBackFunc

  ------------------------------------------------------------------------------
  -- View Objects
  ------------------------------------------------------------------------------
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  pageTitleBackground.name = "pageTitle"
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.parent.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  table.insert(properties, pageTitle)

  local backButton = display.newImageRect( "images/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  local formBackground = display.newRoundedRect( centerX, centerY, 700, 450, 15 )
  formBackground:setFillColor(247 / 255, 247 / 255, 247 / 255)
  formBackground:setStrokeColor(0 / 255, 169 / 255, 126 / 255, 1)
  formBackground.strokeWidth = 10

  local mobile = native.newTextField( centerX - 90, centerY - 90, 400, 70)
  mobile.align = "right"
  mobile.inputType = "number"
  mobile.placeholder = "09---------"
  local options = {
    text = texts.parent.mobile,
    x = centerX + 210,
    y = mobile.y,
    font = fonts.lalezar,
    fontSize = 33
  }
  local mobileTag = display.newText(options)
  mobileTag:setFillColor( 80 / 255, 80 / 255, 80 / 255 )
  table.insert(properties, mobile)
  table.insert(properties, mobileTag)

  local requestAuthenticationCodeBackground = display.newRoundedRect( centerX, centerY +35, 400, 80, 10)
  requestAuthenticationCodeBackground:setFillColor(0 / 255, 169 / 255, 126 / 255)
  requestAuthenticationCodeBackground.name = "requestAuthenticationCode"
  requestAuthenticationCodeBackground.fill.effect = "filter.grayscale"
  options = {
    text = texts.parent.requestAuthenticationCode,
    x = requestAuthenticationCodeBackground.x,
    y = requestAuthenticationCodeBackground.y,
    font = fonts.lalezar,
    fontSize = 36
  }

  local requestAuthenticationCode = display.newText(options)
  table.insert(properties, requestAuthenticationCodeBackground)
  table.insert(properties, requestAuthenticationCode)


  local haveAuthenticationCodeBackground = display.newRoundedRect( centerX, centerY + 140, 400, 80, 10)
  haveAuthenticationCodeBackground:setFillColor(0 / 255, 169 / 255, 126 / 255)
  haveAuthenticationCodeBackground.name = "haveAuthenticationCode"
  haveAuthenticationCodeBackground.fill.effect = "filter.grayscale"
  options = {
    text = texts.parent.haveAuthenticationCode,
    x = haveAuthenticationCodeBackground.x,
    y = haveAuthenticationCodeBackground.y,
    font = fonts.lalezar,
    fontSize = 36
  }

  local haveAuthenticationCode = display.newText(options)
  table.insert(properties, haveAuthenticationCodeBackground)
  table.insert(properties, haveAuthenticationCode)

  local myText = display.newText( texts.support.content, 650, 230, fonts.lalezar, 25 )
  myText:setFillColor( 80 / 255, 80 / 255, 80 / 255)

  renderSoundButton()

  local hint = display.newImage("images/hint.png")
  hint.width, hint.height = 100, 100
  hint.x = centerX + 370
  hint.y = 260
  hint.isVisible = false
  ------------------------------------------------------------------------------
  -- Event Handelers
  ------------------------------------------------------------------------------
  textListener = function( event )
    if ( event.phase == "began" ) then

    elseif ( event.phase == "ended" or event.phase == "submitted" ) then

    elseif ( event.phase == "editing" ) then
      if mobile.text ~= "" then
        requestIsValid = true
        requestAuthenticationCodeBackground.fill.effect = nil
        haveAuthenticationCodeBackground.fill.effect = nil
      else
        requestIsValid = false
        requestAuthenticationCodeBackground.fill.effect = "filter.grayscale"
        haveAuthenticationCodeBackground.fill.effect = "filter.grayscale"
      end
    end
  end
  mobile:addEventListener( "userInput", textListener )

  buttonTouchHandlers = function (event)
    if not requestIsValid and event.target.name == "requestAuthenticationCode" then
      return
    end
    if not requestIsValid and event.target.name == "haveAuthenticationCode" then
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
      mobileNumber = mobile.text
      appConfig:setParentPhone(mobileNumber)
      print(mobileNumber)
      if event.target.name == "requestAuthenticationCode" then
        local s = mobile.text
        local tempPrefix = s:sub( 0, 2 )
        if string.len(mobile.text) ~= 11 and tempPrefix ~= "09" then
          showBlur()
          showTextOnBlur(texts.mobile.numChar)
          timer.performWithDelay(2000, delayCloseBlur, 1)
          mobile.text = ""
          return
        else
          requestAuthenticationCodeFuc()
        end
      elseif event.target.name == "haveAuthenticationCode" then
        navigate("submitFromMessage")
      elseif event.target.name == "back" then
        back()
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  backButton:addEventListener("touch", buttonTouchHandlers)
  requestAuthenticationCodeBackground:addEventListener("touch", buttonTouchHandlers)
  haveAuthenticationCodeBackground:addEventListener("touch", buttonTouchHandlers)
  ------------------------------------------------------------------------------
  -- Functions
  ------------------------------------------------------------------------------
  local blur = nil
  local text = nil
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  callBackFunc = function()
    print("navigating")
    navigate("submitMciInApp")
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  showTextOnBlur = function(newText)
    print(newText)
    if text then
      print("logging")
      display.remove(text)
      text = nil
    end
    text = display.newText({
      text = newText,
      x = centerX,
      y = centerY,
      font = fonts.lalezar,
      fontSize = 46,
    })
    print(text)
    text:setFillColor(1, 1, 1, 1)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  function delayCloseBlur()
      closeBlur()
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  function delayCloseBlurWithCB()
      closeBlur(true)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  closeBlur = function(triggerCallBack)
    print("closblur")
    local function removeGroup(cb1)
      display.remove(blur)
      blur = nil
      if text then
        display.remove(text)
        text = nil
      end
      mobile.isVisible = true
      if triggerCallBack then
        print("callback")
        callBackFunc()
      end
    end
    transition.to(blur, { alpha = 0, onComplete = removeGroup})
    return true
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  showBlur = function()
    if blur then
      return
    end
    blur = display.captureScreen()
    blur.x, blur.y = centerX, centerY
    blur:setFillColor(.1, .1, .1)
    blur.fill.effect = "filter.blur"
    blur.alpha = 0
    mobile.isVisible = false
    transition.to(blur, {alpha = 1})
    blur:addEventListener("tap", function() return true end)
    blur:addEventListener("touch", function() return true end)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  requestAuthenticationCodeFuc = function()
    local function networkListener( event )

      local response = json.decode(event.response)
      print(event)
      print(response)
      print ( "RESPONSE:33 " .. event.response )
      print ( "RESPONSE:55 " .. event.status )
      if ( event.isError ) then
        showTextOnBlur(texts.parent.problemInConnectingToServer)
        timer.performWithDelay( 1500, delayCloseBlur, 1)
      elseif ( response.status == 'POL0506' ) then
        print("status-POL0506")
        showTextOnBlur(texts.parent.sendRequstBefor)
        timer.performWithDelay( 1500, delayCloseBlur, 1)
      elseif ( response.status == 'POL0510' ) then
        print("POL0510")
        showTextOnBlur(texts.parent.shortTimeTwoRequst)
        timer.performWithDelay( 1500, delayCloseBlur, 1)
      elseif ( event.status == 200 ) then
        -------------------------------------------------------------------------- NEEDS CHANGING
        print("success     &&&&&&&&&&&&&&&")
        showTextOnBlur(texts.parent.successfulRequest)
        timer.performWithDelay(1500, delayCloseBlurWithCB, 1)
        print(timer)
      else
        errorResponse(response)
        timer.performWithDelay( 1500, delayCloseBlur, 1)
      end
    end

    local tempUrl = { urls.requestAuthenticationCodeForParent, "?user_number=", mobile.text, urls.serviceCodeProductCode}
    print("zzzzzzzzzz")
    print( table.concat(tempUrl))
    showBlur()
    showTextOnBlur(texts.parent.pleaseWait)
    network.request( table.concat(tempUrl), "POST", networkListener)

  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
end -- related to the render function
------------------------------------------------------------------------------
-- Close Method
------------------------------------------------------------------------------
function buyProductPage:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end

return buyProductPage
