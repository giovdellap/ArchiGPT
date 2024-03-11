local lfs = require ("lfs")
local widget = require("widget")
local json = require("json")
local newUser = {}
local properties = {}


function newUser:render(params)
  print("new user")
  local image, imageInner = nil
  local groupMoved = false
  local tempAvatar = nil
  local photoSet = false
  local flags = nil
  local info = nil
  local isValid = false
  local editMode = false
  local gender = "boy"


  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = nil
  if params then
    pageTitle = display.newText(texts.newUser.edit, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  else
    pageTitle = display.newText(texts.newUser.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  end
  table.insert(properties, pageTitle)

  local cheetah = display.newImage("images/cheetah.png")
  cheetah.x = cheetahX
  cheetah.y = cheetahY
  table.insert(properties, cheetah)

  local confirmBackground = display.newRoundedRect( centerX, centerY-30 + screenHeight / 3.5, 220, 90, 10)
  confirmBackground:setFillColor(0 / 255, 169 / 255, 126 / 255)
  confirmBackground.name = "confirm"
  confirmBackground.fill.effect = "filter.grayscale"
  local options = {
    text = texts.newUser.confirm,
    x = confirmBackground.x,
    y = confirmBackground.y,
    font = fonts.lalezar,
    fontSize = 38
  }

  local confirm = display.newText(options)
  table.insert(properties, confirmBackground)
  table.insert(properties, confirm)

  local boyIcon = display.newImage("images/users/boy.png", centerX + 150, centerY - 180 )
  boyIcon.name = "boy"
  table.insert(properties, boyIcon)

  local girlIcon = display.newImage("images/users/girl.png", centerX - 50, centerY - 180 )
  girlIcon.name = "girl"
  table.insert(properties, girlIcon)

  local selectedGenderBackground = display.newCircle( boyIcon.x, boyIcon.y, 64 )
  selectedGenderBackground:setFillColor(0, 0, 0, 0)
  selectedGenderBackground.strokeWidth = 7
  selectedGenderBackground:setStrokeColor(0, 1, 0, 1)
  table.insert(properties, selectedGenderBackground)

  local selectedGenderMark = display.newImage( "images/users/accept.png" )
  selectedGenderMark.width = 50
  selectedGenderMark.height = 50
  selectedGenderMark.x = boyIcon.x + (boyIcon.width / 2 - selectedGenderMark.width / 2)
  selectedGenderMark.y = boyIcon.y + (boyIcon.height / 2 - selectedGenderMark.height / 2)
  table.insert(properties, selectedGenderMark)


  local textColor = {80 / 255, 80 / 255, 80 / 255, 1}
  local name = native.newTextField( centerX + 50, centerY - 70, 400, 70)
  name.align = "right"
  options = {
    text = texts.newUser.nameTag,
    x = centerX + 394,
    y = name.y,
    font = fonts.lalezar,
    fontSize = 36
  }
  name.isVisible = false
  local nameTag = display.newText(options)
  nameTag:setFillColor(80 / 255, 80 / 255, 80 / 255)
  table.insert(properties, name)
  table.insert(properties, nameTag)

  local lastName = native.newTextField( centerX + 50, centerY + 5, 400, 70)
  lastName.align = "right"
  options = {
    text = texts.newUser.lastNameTag,
    x = centerX + 337,
    y = lastName.y,
    font = fonts.lalezar,
    fontSize = 36
  }
  lastName.isVisible = false
  local lastNameTag = display.newText(options)
  lastNameTag:setFillColor(80 / 255, 80 / 255, 80 / 255)
  table.insert(properties, lastName)
  table.insert(properties, lastNameTag)

  local birthYear = native.newTextField( centerX - 150, centerY + 80, 188, 70)
  birthYear.anchorX = 0
  birthYear.align = "right"
  birthYear.inputType = "number"
  birthYear.placeholder = texts.newUser.year
  birthYear.name = "birthYear"
  birthYear.isVisible = false
  table.insert(properties, birthYear)

  local birthMonth = native.newTextField( centerX + 50, centerY + 80, 94, 70)
  birthMonth.anchorX = 0
  birthMonth.align = "right"
  birthMonth.inputType = "number"
  birthMonth.placeholder = texts.newUser.month
  birthMonth.name = "birthMonth"
  birthMonth.isVisible = false
  table.insert(properties, birthMonth)

  local birthDay = native.newTextField( centerX + 156, centerY + 80, 94, 70)
  birthDay.anchorX = 0
  birthDay.align = "right"
  birthDay.inputType = "number"
  birthDay.placeholder = texts.newUser.day
  birthDay.name = "birthDay"
  birthDay.isVisible = false
  table.insert(properties, birthDay)

  local options = {
    text = texts.newUser.birthdayTag,
    x = centerX + 345,
    y = birthYear.y,
    font = fonts.lalezar,
    fontSize = 36
  }
  local birthdayTag = display.newText(options)
  birthdayTag:setFillColor(80 / 255, 80 / 255, 80 / 255)
  table.insert(properties, birthdayTag)

  options = {
    text = texts.newUser.userNameDuplicationError,
    x = centerX + 50,
    y = centerY + 150,
    fontSize = 40,
    font = fonts.lalezar
  }
  local userNameDuplicationError = display.newText(options)
  userNameDuplicationError:setFillColor(1, 0, 0, 1)
  userNameDuplicationError.isVisible = false
  table.insert(properties, userNameDuplicationError)

  local chooseImage = display.newImage( "images/users/chooseImage.png")
  chooseImage.x = centerX - 300
  chooseImage.y = centerY - 40
  chooseImage.name = "chooseImage"
  table.insert(properties, chooseImage)

  local backButton = display.newImageRect( "images/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  local function setGender(newGender)
    if newGender == "boy" then
      gender = "boy"
      selectedGenderMark.x = boyIcon.x + (boyIcon.width / 2 - selectedGenderMark.width / 2)
      selectedGenderMark.y = boyIcon.y + (boyIcon.height / 2 - selectedGenderMark.height / 2)
      selectedGenderBackground.x = boyIcon.x
      selectedGenderBackground.y = boyIcon.y
    elseif newGender == "girl" then
      gender = "girl"
      selectedGenderMark.x = girlIcon.x + (girlIcon.width / 2 - selectedGenderMark.width / 2)
      selectedGenderMark.y = girlIcon.y + (girlIcon.height / 2 - selectedGenderMark.height / 2)
      selectedGenderBackground.x = girlIcon.x
      selectedGenderBackground.y = girlIcon.y
    end
  end

  if params then
    local tempUser = appConfig:getUserInfo(params)
    if not tempUser then
      navigate("users")
    else
      name.text = tempUser.name
      lastName.text = tempUser.lastName
      birthYear.text = tempUser.birthYear
      birthMonth.text = tempUser.birthMonth
      birthDay.text = tempUser.birthDay
      setGender(tempUser.gender)
      confirmBackground.fill.effect = nil
      isValid = true
      editMode = true

      image = display.newImage(params.."/avatar.png", system.DocumentsDirectory)
      image.x = chooseImage.x
      image.y = chooseImage.y
      image.width = chooseImage.width * .96
      image.height = chooseImage.height * .96
      table.insert(properties, image)
    end
  end
  renderSoundButton()

  local function loadimg()
    local function onComplete( event )
      if event.completed then
        if image then
          display.remove( image )
          image = nil
        end
        event.target.x = chooseImage.x
        event.target.y = chooseImage.y
        event.target.width = chooseImage.width * .96
        event.target.height = chooseImage.height * .96
        event.target:toFront()
        tempAvatar = event.target
        table.insert(properties, tempAvatar)
      end

    end
    if media.hasSource( media.PhotoLibrary ) then
      media.selectPhoto(
        {
          listener = onComplete
      })
    else
      native.showAlert( messages.alerts.title, messages.alerts.noPhotoLibrary, { messages.alerts.close } )
    end
  end

  local function textListener( event )
    local target = event.target
    if ( event.phase == "began" ) then
      -- User begins editing "defaultField"
    elseif ( event.phase == "ended" or event.phase == "submitted" ) then
      -- Output resulting text from "defaultField"

    elseif ( event.phase == "editing" ) then
      if target.name == "birthYear" or target.name == "birthMonth" or target.name == "birthDay" then
        -- if #target.text > 4 then
        --   target.text = target.oldText
        -- end

        if target.name == "birthYear" and target.text ~= "" then
          if string.len(target.text) > 4 then
            local s = target.text
            target.text = s:sub( 0, 4 )
          end
          if string.len(target.text) == 4 then
            local time = os.time() -- This is time in seconds
            local dateTime = os.date("*t", time) -- *t is the one of the, there are other formats too.
            if tonumber(target.text) < 1350 then
              target.text = ""
            elseif tonumber(dateTime.year-620) < tonumber(target.text) then
              target.text = ""
            end
          end
        end

        if target.name == "birthMonth" and target.text ~= "" then
          if tonumber(target.text) > 12 or string.len(target.text) == 3 then
            target.text = ""
          end
          if string.len(target.text) == 2 then
            if tonumber(target.text) == 00 then
              target.text = "1"
            end
          end
        end

        if target.name == "birthDay" and target.text ~= "" then
          if tonumber(target.text) > 31 or string.len(target.text) == 3 then
            target.text = ""
          end
          if string.len(target.text) == 2 then
            if tonumber(target.text) == 00 then
              target.text = "1"
            end
          end
        end
      end

      if name.text ~= "" and lastName.text ~= "" and birthYear.text ~= "" and birthMonth.text ~= "" and birthDay.text ~= "" and (not (appConfig:hasThisUser(name.text) and (not editMode))) then
        isValid = true
        confirmBackground.fill.effect = nil
      else
        isValid = false
        confirmBackground.fill.effect = "filter.grayscale"
      end

      if appConfig:hasThisUser(name.text) and not editMode then
        userNameDuplicationError.isVisible = true
      else
        userNameDuplicationError.isVisible = false
      end

    end
  end

  local function buttonTouchHandlers(event)
    if not isValid and event.target.name == "confirm" then
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
      if event.target.name == "chooseImage" then
        if image then
          image.width = image.width / clickResizeFactor
          image.height = image.height / clickResizeFactor
        end
        loadimg()
      elseif event.target.name == "confirm" then
        if editMode then
          appConfig:editUser(params, name.text, lastName.text, gender, birthYear.text, birthMonth.text, birthDay.text, tempAvatar)
          back()
        else
          appConfig:saveUser(name.text, lastName.text, gender, birthYear.text, birthMonth.text, birthDay.text, tempAvatar)
          navigate("home")
        end
      elseif event.target.name == "back" then
        back()
      elseif event.target.name == "boy" then
        setGender("boy")
      elseif event.target.name == "girl" then
        setGender("girl")
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)

    end
  end

  confirmBackground:addEventListener("touch", buttonTouchHandlers)
  chooseImage:addEventListener("touch", buttonTouchHandlers)
  name:addEventListener( "userInput", textListener )
  lastName:addEventListener( "userInput", textListener )
  birthYear:addEventListener( "userInput", textListener )
  birthMonth:addEventListener( "userInput", textListener )
  birthDay:addEventListener( "userInput", textListener )
  backButton:addEventListener("touch", buttonTouchHandlers)
  boyIcon:addEventListener("touch", buttonTouchHandlers)
  girlIcon:addEventListener("touch", buttonTouchHandlers)

  timer.performWithDelay(100,
    function()
      print("hello")
      name.isVisible = true
      lastName.isVisible = true
      birthDay.isVisible = true
      birthMonth.isVisible = true
      birthYear.isVisible = true
    end
  )
end


function newUser:close()
  if image then
    display.remove(image)
    display.remove(imageInner)
    image = nil
    imageInner = nil
  end
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end
return newUser
