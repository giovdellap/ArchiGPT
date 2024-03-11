
local json = require("json")
local zip = require("plugin.zip")
local buyProductPage = {}
local properties = {}

function buyProductPage:render(param)
  print("select operator")
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

  local pageTitle = display.newText(texts.operator.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
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

  local selectOperator = display.newText(texts.operator.selectOperator, centerX + 100, centerY - 130, fonts.lalezar, 39)
  selectOperator:setFillColor( 80 / 255, 80 / 255, 80 / 255 )
  table.insert(properties, selectOperator)

  local mci = display.newImage( "images/operators/mci.png")
  mci.x = centerX + 135
  mci.y = centerY + 40
  mci.name = "mci"
  table.insert(properties, mci)

  local irancell = display.newImage( "images/operators/irancell.png")
  irancell.x = centerX - 135
  irancell.y = centerY + 40
  irancell.name = "irancell"
  table.insert(properties, irancell)

  renderSoundButton()

  local hint = display.newImage("images/hint.png")
  hint.width, hint.height = 100, 100
  hint.x = centerX + 370
  hint.y = 260
  hint.isVisible = false
  ------------------------------------------------------------------------------
  -- Event Handelers
  ------------------------------------------------------------------------------


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
      if event.target.name == "mci" then
        appConfig:setTypeOperator(texts.typeOperator.mci)
        navigate("parentMCI")
      elseif event.target.name == "irancell" then
        appConfig:setTypeOperator(texts.typeOperator.irancel)
        navigate("parentIrancell")
      elseif event.target.name == "back" then
        back()
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  backButton:addEventListener("touch", buttonTouchHandlers)
  mci:addEventListener("touch", buttonTouchHandlers)
  irancell:addEventListener("touch", buttonTouchHandlers)
  ------------------------------------------------------------------------------
  -- Functions
  ------------------------------------------------------------------------------
  local blur = nil
  local text = nil
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------
  callBackFunc = function()
    if param then
      print("navigating")
      navigate(param.route, param.params)
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
      mobile.isVisible = true
      -- authenticationCode.isVisible = true
      if triggerCallBack then
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
    -- authenticationCode.isVisible = false
    transition.to(blur, {alpha = 1})
    blur:addEventListener("tap", function() return true end)
    blur:addEventListener("touch", function() return true end)
  end
  ------------------------------------------------------------------------------
  ------------------------------------------------------------------------------

  requestAuthenticationCodeFuc = function()
    local function networkListener( event )
      if ( event.isError ) then
        showTextOnBlur(texts.parent.problemInConnectingToServer)
        timer.performWithDelay( 1500, closeBlur() )

      else
        -------------------------------------------------------------------------- NEEDS CHANGING
        showTextOnBlur(texts.parent.successfulRequest)
        timer.performWithDelay(2000, closeBlur())
        navigate("submit")
      end
    end

    local headers = {}
    headers["Content-Type"] = "application/json"
    headers["phone"] = mobile.text
    local params = {}
    params.headers = headers
    showBlur()
    showTextOnBlur(texts.parent.pleaseWait)
    network.request( urls.requestAuthenticationCodeForParent, "POST", networkListener, params )

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
