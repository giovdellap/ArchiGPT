local home = {}
local properties = {}
local json = require("json")
local zip = require("plugin.zip")


function home:render(params)
  ------------------------------------------------------------------------------
  -- Declarations
  ------------------------------------------------------------------------------
  local showBlur, closeBlur, showTextOnBlur, callBackFunc, getSubscription, getSubscribeIrancell

  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  pageTitleBackground.name = "pageTitle"
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.home.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  table.insert(properties, pageTitle)


  local apps = nil
  apps = display.newImage( "images/home/apps.png")
  apps.height, apps.width = 126, 126
  apps.x = centerX + 0.4 * screenWidth
  apps.y = centerY + (-0.1) * screenHeight
  apps.name = "apps"
  table.insert(properties, apps)

  local cheetah = display.newImage("images/cheetah.png")
  cheetah.x = cheetahX
  cheetah.y = cheetahY
  table.insert(properties, cheetah)

  local deer = display.newImage("images/deer.png")
  deer.x = 135
  deer.y = display.actualContentHeight - 145
  table.insert(properties, deer)

  local picture = display.newImage( "images/paint/pic.png")
  picture.x = display.contentCenterX - 480
  picture.y = screenTop + 0.14 * screenHeight
  picture.name = "picture"
  table.insert(properties, picture)

  local pictureInner = nil
  pictureInner = display.newImage(appConfig:getAvatarAddress())
  pictureInner.x = picture.x
  pictureInner.y = picture.y
  pictureInner.width = picture.width * .9
  pictureInner.height = picture.height * .9
  table.insert(properties, pictureInner)

  -- local productsIcon = nil
  -- productsIcon = display.newImage("images/buy.png")
  -- productsIcon.height, productsIcon.width = 126, 126
  -- productsIcon.x = centerX + 0.4 * screenWidth
  -- productsIcon.y = centerY - 0.165 * screenHeight
  -- productsIcon.name = "productsIcon"
  -- table.insert(properties, productsIcon)


  local competitionHistory = display.newImage( "images/cup.png")
  competitionHistory.x = centerX + 0.01 * screenWidth
  competitionHistory.y = centerY + 0.230 * screenHeight
  competitionHistory.name = "competitionHistory"
  table.insert(properties, competitionHistory)

  local painting = display.newImage( "images/home/painting.png")
  painting.x = centerX - 0.150 * screenWidth
  painting.y = centerY - 0.190 * screenHeight
  painting.name = "painting"
  table.insert(properties, painting)

  local coloring = display.newImage( "images/home/coloring.png")
  coloring.x = centerX + 0.170 * screenWidth
  coloring.y = centerY - 0.190 * screenHeight
  coloring.name = "coloring"
  table.insert(properties, coloring)

  local selected = display.newImage( "images/home/select.png")
  selected.x = centerX - 0.150 * screenWidth
  selected.y = centerY + 0.05 * screenHeight
  selected.name = "selected"
  table.insert(properties, selected)

  local gallery = display.newImage( "images/home/gallery.png")
  gallery.x = centerX + 0.175 * screenWidth
  gallery.y = centerY + 0.05 * screenHeight
  gallery.name = "gallery"
  table.insert(properties, gallery)

  local exit = display.newImage( "images/home/exit.png")
  exit.height, exit.width = 126, 126
  exit.x = display.contentCenterX - 480
  exit.y = centerY - 0.150 * screenHeight
  exit.name = "exit"
  table.insert(properties, exit)

  if not appConfig:isParentSet() then
    navigate("slidSwiper1")
  end

  native.showPopup( "requestAppPermission", {
    appPermission = { "Camera", "Storage"},
    urgency = "Normal",
    listener = permissionsListener,
  } )

  renderSoundButton()

  local function exitFunction()
    if isAlertRendered then
      return true
    else
      isExitAlertRendered = true
      alert({
        textImage = images.alerts.exit,
        isYesNo = true,
        callBack = function(index)
          if index == 1 then
            os.exit()
          elseif index == 2 then
          end
        end})
        return true
      end
    end -- end of exit function

    local function buttonTouchHandlers(event)
      if event.phase == "began" then
        audio.stop(clickSoundCannel)
        audio.play(clickSound, clickPlayOptions)

        event.target.width = event.target.width * clickResizeFactor
        event.target.height = event.target.height * clickResizeFactor
        if event.target.name == "picture" then
          pictureInner.width = pictureInner.width * clickResizeFactor
          pictureInner.height = pictureInner.height * clickResizeFactor
        end
        display.getCurrentStage():setFocus( event.target )
        event.target.isFocus = true
      end
      if not event.target.isFocus then
        return false
      end
      if event.phase then
        print("&&&&&$$$$$$$#@#########@@@@@")
        print(appConfig)
        print(appConfig:getFlagCheckStatus())
        print(appConfig:getTypeOperator())
        if not appConfig:getFlagCheckStatus() then
          if appConfig:getTypeOperator() == texts.typeOperator.mci then
            if appConfig:getReferenceCode() then
              -- getSubscription()
            else
              -- navigate("slidSwiper1")
            end
          else
            if appConfig:getToken() then
              getSubscribeIrancell()
            else
              navigate("slidSwiper1")
            end
          end
          appConfig:setFlagCheckStatus(true)
        end
      end
      if event.phase == "ended" then
        event.target.width = event.target.width / clickResizeFactor
        event.target.height = event.target.height / clickResizeFactor
        if event.target.name == "painting" then
          navigate("paint")
        end
        if event.target.name == "selected" then
          navigate("selected")
        end
        if event.target.name == "gallery" then
          navigate("gallery")
        end
        if event.target.name == "coloring" then
          navigate("coloringCategoriesList")
        end
        if event.target.name == "apps" then
          navigate("dorsa")
        end
        if event.target.name == "pageTitle" then
          navigate("about")
        end
        if event.target.name == "picture" then
          pictureInner.width = pictureInner.width / clickResizeFactor
          pictureInner.height = pictureInner.height / clickResizeFactor
          navigate("users")
        end
        -- if event.target.name == "productsIcon" then
        --   print('productsIcon')
        --   navigate("products")
        -- end
        if event.target.name == "exit" then
          exitFunction()
        end
        if event.target.name == "competitionHistory" then
          if appConfig:isParentSet() then
            alert({
              textImage = images.alerts.sendPainting,
              isCustomeBtn = true,
              isYesNo = true,
              callBack = function(index)
                if index == 1 then
                  navigate("gallery")
                elseif index == 2 then
                  loadimg()
                elseif index == 3 then
                  showLoading()
                  local function onComplete( event )
                    if event.completed then
                      local pictureName = saveImageFromGallery("tempCameraImage.jpg", system.DocumentsDirectory)
                      local params = {
                        directoryAddress = appConfig:getUserName().."/savedPictures/"..pictureName,
                        dir = system.DocumentsDirectory,
                        image = appConfig:getUserName().."/savedPictures/"..pictureName.."/main.png",
                        name = pictureName,
                        isFromGallery = true
                      }
                      navigate("showSavedPicture", params)
                      closeLoading()
                    else
                      closeLoading()
                    end

                  end
                  os.remove( system.pathForFile( "tempCameraImage.jpg", system.DocumentsDirectory ) )
                  local function permissionsListener( event )
                    print( "permissionsListener")
                  end
                  if media.hasSource( media.Camera ) then
                    media.capturePhoto( {
                      mediaSource = media.Camera,
                      listener = onComplete,
                      destination = { baseDir = system.DocumentsDirectory, filename = "tempCameraImage.jpg" }
                    } )
                  else
                    native.showAlert( "خطا", "این دستگاه فاقد دوربین می باشد", { "تایید" } )
                  end

                end
              end})
            else
              alert({
                textImage = images.alerts.parentNotSet,
                isYesNo = true,
                callBack = function(index)
                  if index == 1 then
                    navigate("parent", getCurrentRoute())
                  elseif index == 2 then
                  end
                end})
              end
            end
            event.target.isFocus = false
            display.getCurrentStage():setFocus(nil)
          end
        end
        painting:addEventListener("touch", buttonTouchHandlers)
        coloring:addEventListener("touch", buttonTouchHandlers)
        selected:addEventListener("touch", buttonTouchHandlers)
        gallery:addEventListener("touch", buttonTouchHandlers)
        picture:addEventListener("touch", buttonTouchHandlers)
        pageTitleBackground:addEventListener("touch", buttonTouchHandlers)
        -- productsIcon:addEventListener("touch", buttonTouchHandlers)
        apps:addEventListener("touch", buttonTouchHandlers)
        exit:addEventListener("touch", buttonTouchHandlers)
        competitionHistory:addEventListener("touch", buttonTouchHandlers)
        -------------------------------------------------------------------------------------------------------------------
        -------------------------------------------------------------------------------------------------------------------

        ------------------------------------------------------------------------------
        -- Functions
        ------------------------------------------------------------------------------
        local blur = nil
        local text = nil
        ------------------------------------------------------------------------------
        ------------------------------------------------------------------------------
        callBackFunc = function()
          if not appConfig:hasUser() then
            navigate("slidSwiper1")
          end
        end
        ------------------------------------------------------------------------------
        ------------------------------------------------------------------------------
        showTextOnBlur = function(newText)
          print("(showTextOnBlur&&&&&)")
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
          print("close blur &&&&&&    kjh")
          local function removeGroup(cb1)
            display.remove(blur)
            blur = nil
            if text then
              display.remove(text)
              text = nil
            end
            -- authenticationCode.isVisible = true
            if triggerCallBack then
              print("triggerCallBack")
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
          -- authenticationCode.isVisible = false
          transition.to(blur, {alpha = 1})
          blur:addEventListener("tap", function() return true end)
          blur:addEventListener("touch", function() return true end)
        end
        ------------------------------------------------------------------------------
        ------------------------------------------------------------------------------
        function getSubscription()
          print("&&&&&&&&&&&&&&&&&^^^^^^^^^^^^^^############")
          local function networkListener( event )
            print(event.response)
            local response = json.decode(event.response)
            print(event.response)
            print("homePage loooog")
            if ( event.isError ) then
              showTextOnBlur(texts.buyProduct.problemInConnectingToServer)
              timer.performWithDelay( 1000, closeBlur() )
            else
              -------------------------------------------------------------------------- NEEDS CHANGING
              print(event.status)
              if event.status == 200 then
                appConfig:setParentFromJSON(event.response)
                if response.data[1].product_status == 'INACTIVE' then
                  navigate("slidSwiper0")
                  appConfig:eraseUsers()
                  appConfig:eraseParent()
                end
              elseif event.status == 409 then
                showTextOnBlur(texts.parent.wrongInformation)
                timer.performWithDelay( 1500, closeBlur() )
              elseif event.status == 400 then
                showTextOnBlur(texts.parent.wrongInformation)
                timer.performWithDelay( 1500, closeBlur() )
              else
                errorResponse(response)
                timer.performWithDelay( 1500, closeBlur() )
              end
            end
          end
          print(referenceCode)
          local tempUrl = { urls.getSubscription, "?user_number=", appConfig:getParentPhone(), urls.serviceCodeProductCode, "&reference_code=", appConfig:getReferenceCode()}
          print(table.concat(tempUrl))
          network.request( table.concat(tempUrl), "GET", networkListener)
        end
        -----------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------
        function getSubscribeIrancell()
          local function netCheckSubscribeListener( event )
            -------------------------------------------------------------------------- NEEDS CHANGING
            if event.status == 200 then
              local response = json.decode(event.response)
              timer.performWithDelay( 2000, closeBlur())
              print("its okkkkkk")
              print(response.cancelReason)

              if response.cancelReason ~= nil then
                appConfig:eraseUsers()
                appConfig:eraseParent()
                navigate("slidSwiper0")
              end
            else
              showTextOnBlur(texts.parent.wrongInformation)
              timer.performWithDelay( 1500, closeBlur() )
            end
          end
          local tempUrl = { urls.serviceCheckSubscribIrancell, appConfig:getToken(), "?access_token=", accessToken}
          network.request( table.concat(tempUrl), "GET", netCheckSubscribeListener)
        end
      end
      function home:close()
        closeSoundButton()
        if #properties > 0 then
          for n = #properties, 1, - 1 do
            display.remove(properties[n])
            properties[n] = nil
          end
        end
      end
      return home
