
local json = require("json")
local zip = require("plugin.zip")
local buyProductPage = {}
local properties = {}

function buyProductPage:render(item)
  ------------------------------------------------------------------------------
  -- Declarations
  ------------------------------------------------------------------------------
  local serviceCode = item.serviceCode
  local type = item.type
  local title = item.title
  local price = item.price
  local productCode = item.productCode
  local url = item.url
  local buttonTouchHandlers, textListener
  local requestIsValid = false
  local confirmIsValid = false
  local requestAuthenticationCodeFuc, confirmAuthenticationCodeFunc, downloadProduct, uncompressProduct
  local showBlur, closeBlur, changeText

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

  local pageTitle = display.newText(texts.buyProduct.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  table.insert(properties, pageTitle)

  local backButton = display.newImageRect( "images/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  local formBackground = display.newRoundedRect( centerX, centerY + 30, 600, 450, 15 )
  formBackground:setFillColor(65 / 255, 118 / 255, 204 / 255)
  formBackground:setStrokeColor(1, 1, 1, 1)
  formBackground.strokeWidth = 10

  local mobile = native.newTextField( centerX - 90, centerY - 130, 400, 70)
  mobile.align = "right"
  mobile.inputType = "number"
  local options = {
    text = texts.buyProduct.mobile,
    x = centerX + 210,
    y = mobile.y,
    font = fonts.lalezar,
    fontSize = 36
  }
  local mobileTag = display.newText(options)
  mobileTag:setFillColor(1, 1, 1, 1)
  table.insert(properties, mobile)
  table.insert(properties, mobileTag)

  local requestAuthenticationCodeBackground = display.newRoundedRect( centerX - 90, centerY - 40, 400, 80, 10)
  requestAuthenticationCodeBackground:setFillColor(76 / 255, 171 / 255, 254 / 255)
  requestAuthenticationCodeBackground.name = "requestAuthenticationCode"
  requestAuthenticationCodeBackground.fill.effect = "filter.grayscale"
  options = {
    text = texts.buyProduct.requestAuthenticationCode,
    x = requestAuthenticationCodeBackground.x,
    y = requestAuthenticationCodeBackground.y,
    font = fonts.lalezar,
    fontSize = 36
  }

  local requestAuthenticationCode = display.newText(options)
  table.insert(properties, requestAuthenticationCodeBackground)
  table.insert(properties, requestAuthenticationCode)

  local authenticationCode = native.newTextField( centerX - 90, centerY + 100, 400, 70)
  authenticationCode.align = "right"
  authenticationCode.inputType = "number"
  local options = {
    text = texts.buyProduct.authenticationCode,
    x = centerX + 210,
    y = authenticationCode.y,
    font = fonts.lalezar,
    fontSize = 36
  }
  local authenticationCodeTag = display.newText(options)
  authenticationCodeTag:setFillColor(1, 1, 1, 1)
  table.insert(properties, authenticationCode)
  table.insert(properties, authenticationCodeTag)

  local confirmAuthenticationCodeBackground = display.newRoundedRect( centerX - 90, centerY + 190, 400, 80, 10)
  confirmAuthenticationCodeBackground:setFillColor(76 / 255, 171 / 255, 254 / 255)
  confirmAuthenticationCodeBackground.name = "confirmAuthenticationCode"
  confirmAuthenticationCodeBackground.fill.effect = "filter.grayscale"
  options = {
    text = texts.buyProduct.confirmAuthenticationCode,
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
      -- User begins editing "defaultField"
    elseif ( event.phase == "ended" or event.phase == "submitted" ) then
      -- Output resulting text from "defaultField"

    elseif ( event.phase == "editing" ) then
      if authenticationCode.text ~= "" then
        confirmIsValid = true
        confirmAuthenticationCodeBackground.fill.effect = nil
      else
        confirmIsValid = false
        confirmAuthenticationCodeBackground.fill.effect = "filter.grayscale"
      end

      if mobile.text ~= "" then
        requestIsValid = true
        requestAuthenticationCodeBackground.fill.effect = nil
      else
        requestIsValid = false
        requestAuthenticationCodeBackground.fill.effect = "filter.grayscale"
      end
    end
  end

  mobile:addEventListener( "userInput", textListener )
  authenticationCode:addEventListener( "userInput", textListener )

  buttonTouchHandlers = function (event)
    if not requestIsValid and event.target.name == "requestAuthenticationCode" then
      return
    end

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
      elseif event.target.name == "requestAuthenticationCode" then
        requestAuthenticationCodeFuc()

      elseif event.target.name == "back" then
        back()
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  backButton:addEventListener("touch", buttonTouchHandlers)
  requestAuthenticationCodeBackground:addEventListener("touch", buttonTouchHandlers)
  confirmAuthenticationCodeBackground:addEventListener("touch", buttonTouchHandlers)

  ------------------------------------------------------------------------------
  -- Functions
  ------------------------------------------------------------------------------
  local blur = nil
  local text = nil
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

  closeBlur = function()
    local function removeGroup()
      display.remove(blur)
      blur = nil
      if text then
        display.remove(text)
        text = nil
      end
      mobile.isVisible = true
      authenticationCode.isVisible = true
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
    authenticationCode.isVisible = false
    transition.to(blur, {alpha = 1})
    blur:addEventListener("tap", function() return true end)
    blur:addEventListener("touch", function() return true end)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  requestAuthenticationCodeFuc = function()
    local function networkListener( event )
      if ( event.isError ) then
        showTextOnBlur(texts.buyProduct.problemInConnectingToServer)
        timer.performWithDelay( 1000, closeBlur )

      else
        -------------------------------------------------------------------------- NEEDS CHANGING
        showTextOnBlur(texts.buyProduct.authenticationCodeRecieved)
        print(event.response.authenticationCode)
        local response = json.decode(event.response)
        authenticationCode.text = tostring(response.authenticationCode)
        confirmIsValid = true
        confirmAuthenticationCodeBackground.fill.effect = nil
        timer.performWithDelay(1000, closeBlur)
        print(event.response)
      end
    end

    local params = {}
    params.body = json.encode( {
      service_code = serviceCode,
      product_code = productCode,
      user_number = mobile.text
    })
    showBlur()
    showTextOnBlur(texts.buyProduct.pleaseWait)
    network.request( urls.requestAuthenticationCode, "POST", networkListener, params )

  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  confirmAuthenticationCodeFunc = function()
    local function networkListener( event )
      if ( event.isError ) then
        showTextOnBlur(texts.buyProduct.problemInConnectingToServer)
        timer.performWithDelay( 1000, closeBlur )
      else
        -------------------------------------------------------------------------- NEEDS CHANGING
        if event.status == 200 then
          showTextOnBlur(texts.buyProduct.subscriptionConfirmed)
          local response = json.decode(event.response)
          appConfig:savePurchasedProduct(productCode, response)
          downloadProduct(url)
        elseif event.status == 401 then
          showTextOnBlur(texts.buyProduct.badRequest)
          timer.performWithDelay( 1000, closeBlur )
        else
          showTextOnBlur(texts.buyProduct.problemInConnectingToServer)
          timer.performWithDelay( 1000, closeBlur )
        end
      end
    end

    local params = {}
    params.body = json.encode( {
      service_code = serviceCode,
      product_code = productCode,
      user_number = mobile.text,
      pin = authenticationCode.text
    })
    showBlur()
    showTextOnBlur(texts.buyProduct.pleaseWait)
    network.request( urls.confirmAuthenticationCode, "POST", networkListener, params )
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  downloadProduct = function(url)
    local function networkListener( event )
      if ( event.isError ) then
        print( "Network error - download failed: ", event.response )
        showTextOnBlur(texts.buyProduct.problemInDownloadingProducts)
        timer.performWithDelay( 1000, function() closeBlur() end )
      elseif ( event.phase == "began" ) then
        showTextOnBlur(texts.buyProduct.downloading.." - ".."0 "..texts.buyProduct.percentage)
      elseif ( event.phase == "progress" ) then
        showTextOnBlur(texts.buyProduct.downloading.." - "..tostring(math.floor((event.bytesTransferred / event.bytesEstimated) * 100)).." "..texts.buyProduct.percentage)
      elseif ( event.phase == "ended" ) then
        showTextOnBlur(texts.buyProduct.downloadCompleted)
        uncompressProduct()
      end
    end

    local params = {}
    params.progress = true
    network.download(
      url,
      "GET",
      networkListener,
      params,
      "newFiles.zip",
      system.TemporaryDirectory
    )
  end

  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  uncompressProduct = function()
    deleteDirectory(system.pathForFile("newColoringCategories", system.DocumentsDirectory))
    local function zipListener( event )
      if ( event.isError ) then
        print( "Error!" )
      else
        print ( event["type"] ) --> uncompress
        addNewFilesToColoringCategory()
        closeBlur()
        navigate("home")
      end
    end

    local zipOptions =
    {
      zipFile = "newFiles.zip",
      zipBaseDir = system.TemporaryDirectory,
      dstBaseDir = system.DocumentsDirectory,
      listener = zipListener
    }
    zip.uncompress( zipOptions )
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
