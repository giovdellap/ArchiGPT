
local json = require("json")
local zip = require("plugin.zip")
local buyProductPage = {}
local properties = {}

function buyProductPage:render(param)
  print("submit mci in app page")
  ------------------------------------------------------------------------------
  -- Declarations
  ------------------------------------------------------------------------------
  local buttonTouchHandlers, textListener
  local requestIsValid = false
  local confirmIsValid = false
  local confirmAuthenticationCodeFunc, downloadProduct, uncompressProduct
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

  renderSoundButton()

  local authenticationCode = native.newTextField( centerX - 90, centerY - 90, 400, 70)
  authenticationCode.align = "right"
  authenticationCode.inputType = "number"
  local options = {
    text = texts.parent.authenticationCode,
    x = centerX + 210,
    y = authenticationCode.y,
    font = fonts.lalezar,
    fontSize = 33
  }
  local authenticationCodeTag = display.newText(options)
  authenticationCodeTag:setFillColor(80/255, 80/255, 80/255, 1)
  table.insert(properties, authenticationCode)
  table.insert(properties, authenticationCodeTag)

  local confirmAuthenticationCodeBackground = display.newRoundedRect( centerX , centerY + 25, 400, 80, 10)
  confirmAuthenticationCodeBackground:setFillColor(0 / 255, 196 / 255, 126 / 255)
  confirmAuthenticationCodeBackground.name = "confirmAuthenticationCode"
  confirmAuthenticationCodeBackground.fill.effect = "filter.grayscale"
  options = {
    text = texts.parent.confirmAuthenticationCode,
    x = confirmAuthenticationCodeBackground.x,
    y = confirmAuthenticationCodeBackground.y,
    font = fonts.lalezar,
    fontSize = 36
  }

  local confirmAuthenticationCode = display.newText(options)
  table.insert(properties, confirmAuthenticationCodeBackground)
  table.insert(properties, confirmAuthenticationCode)

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
      if authenticationCode.text ~= "" then
        confirmIsValid = true
        confirmAuthenticationCodeBackground.fill.effect = nil
      else
        confirmIsValid = false
        confirmAuthenticationCodeBackground.fill.effect = "filter.grayscale"
      end
    end
  end
  authenticationCode:addEventListener( "userInput", textListener )

  buttonTouchHandlers = function (event)
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
      elseif event.target.name == "back" then
        back()
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  backButton:addEventListener("touch", buttonTouchHandlers)
  confirmAuthenticationCodeBackground:addEventListener("touch", buttonTouchHandlers)

  ------------------------------------------------------------------------------
  -- Functions
  ------------------------------------------------------------------------------
  local blur = nil
  local text = nil
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  callBackFunc = function()
    if not appConfig:hasUser() then
      navigate("newUser")
    else
      navigate("home")
    end
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  showTextOnBlur = function(newText)
    if text then
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
    text:setFillColor(1, 1, 1, 1)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  closeBlur = function(triggerCallBack)
    local function removeGroup(cb1)
      display.remove(blur)
      blur = nil
      if text then
        display.remove(text)
        text = nil
      end
      authenticationCode.isVisible = true
      if triggerCallBack then
        print(triggerCallBack)
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
    authenticationCode.isVisible = false
    transition.to(blur, {alpha = 1})
    blur:addEventListener("tap", function() return true end)
    blur:addEventListener("touch", function() return true end)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  confirmAuthenticationCodeFunc = function()
    local function networkListener( event )
      print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
      print(type(event))
      print(type(event.response))
      local response = json.decode(event.response)
      referenceCode = response.reference_code
      appConfig:setReferenceCode(referenceCode);
      print(response.reference_code)
      print(response.result)
      print(response.status)
      print ( "RESPONSE: confirmAuthenticationCodeFunc" .. event.response )
      if ( event.isError ) then
        showTextOnBlur(texts.buyProduct.problemInConnectingToServer)
        timer.performWithDelay( 1000, closeBlur() )
      else
        -------------------------------------------------------------------------- NEEDS CHANGING
        if event.status == 200 then
          -- showTextOnBlur(texts.buyProduct.subscriptionConfirmed)
          getUserFromAporaServer();
          -- timer.performWithDelay( 2000, closeBlur(true))
        elseif event.status == 409 then
          showTextOnBlur(texts.parent.wrongInformation)
          timer.performWithDelay( 1500, closeBlur() )
        elseif ( response.status == 'POL0505' ) then
          showTextOnBlur(texts.parent.noAuthenticationCode)
          timer.performWithDelay( 1500, closeBlur() )
        elseif ( response.status == 'POL0501' ) then
          showTextOnBlur(texts.parent.notEnoughCredit)
          timer.performWithDelay( 1500, closeBlur() )
        else
          errorResponse(response)
          timer.performWithDelay( 1500, closeBlur() )
        end
      end
    end


    local tempUrl = { urls.confirmAuthenticationCodeForParent, "?user_number=", mobileNumber, urls.serviceCodeProductCode, "&pin=", authenticationCode.text}
    print( table.concat(tempUrl))
    showBlur()
    showTextOnBlur(texts.buyProduct.pleaseWait)
    network.request( table.concat(tempUrl), "POST", networkListener)
  end

  getUserFromAporaServer = function()
    local function networkGetUserListener( event )
      print("%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
      print(event.status)
      print(event.response)
      -- print(event.requestId.userdata)
      -- local response = json.decode(event.requestId)

      print("%%%%%%%%%%%%%%%%%%%%%")
      if ( event.isError ) then
        showTextOnBlur(texts.buyProduct.problemInConnectingToServer)
        timer.performWithDelay( 1000, closeBlur() )
      else
        -------------------------------------------------------------------------- NEEDS CHANGING
        if event.status == 201 then
          print("success ***********")
          showTextOnBlur(texts.buyProduct.subscriptionConfirmed)
          appConfig:setParentFromJSON(event.response)
          timer.performWithDelay( 2000, closeBlur(true), 0)
        elseif event.status == 409 then
          showTextOnBlur(texts.parent.wrongInformation)
          timer.performWithDelay( 2000, closeBlur() )
        else
          errorResponse(response)
          timer.performWithDelay( 2000, closeBlur() )
        end
      end
    end
    print(mobileNumber)
    local headers = {}
    headers["Content-Type"] = "application/json"
    headers["phone"] = mobileNumber
    local params = {}
    params.headers = headers
    print(params)
    for key, value in pairs(params) do print(key, value) end
    for key, value in pairs(headers) do print(key, value) end

    network.request( urls.loginAndRegisterWithoutSMS, "POST", networkGetUserListener, params )
  end


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
