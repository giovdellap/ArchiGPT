local widget = require("widget")
local json = require("json")
local mime = require("mime")

------------------------------------------------------------------------------
-- Declarations
------------------------------------------------------------------------------
local competition = {}
local boxSize
local blur, group, scrollView, textObject, background
local renderList, renderText, closeSelectingPanel, getCompetitionList, createGroup, hideGroup, showGroup, createBlur, participate, getAdditionalInfo
local image, imageDir, isFromGallery
local infoForm
local panelIsRendered = false
local hold = false

------------------------------------------------------------------------------
-- RENDER FUNCTIONS
------------------------------------------------------------------------------
function competition:showSelectingPanel(imageParam, imageDirParam, isFromGalleryParam)
  panelIsRendered = true
  image = imageParam
  imageDir = imageDirParam
  isFromGallery = isFromGalleryParam
  createBlur()
  createGroup()
  renderText(texts.showSavedPicture.pleaseWait, function() showGroup(getCompetitionList) end)

end

------------------------------------------------------------------------------
-- FUNCTION
------------------------------------------------------------------------------
closeSelectingPanel = function()
  if not panelIsRendered then
    return
  end

  local function removeGroup()
    display.remove(group)
    group = nil
    display.remove(blur)
    blur = nil
    display.remove(scrollView)
    scrollView = nil
    display.remove(infoForm)
    infoForm = nil
  end
  transition.to(blur, {alpha = 0})
  transition.to(group, {y = centerY + screenHeight, alpha = 0, onComplete = removeGroup})
  transition.to(scrollView, {y = centerY + screenHeight, alpha = 0, onComplete = removeGroup})
  panelIsRendered = false
  return true
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
getCompetitionList = function()
  if not panelIsRendered then
    return
  end
  local function networkListener(event)
    print(event.status)
    if event.status == 200 then
      local list = json.decode(event.response)
      if #list > 0 then
        renderList(list)
      else
        renderText(texts.showSavedPicture.noCompetition)
      end
    else
      renderText(texts.showSavedPicture.problemInFetchingList)
    end
  end
  local parent = appConfig:getParent()
  local headers = {}
  headers["Content-Type"] = "application/json"
  headers["user_code"] = parent.user_code
  local params = {}
  params.headers = headers
  network.request( urls.getCompetitionList, "GET", networkListener, params )
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
renderList = function(list)
  if not panelIsRendered then
    return
  end
  if scrollView then
    display.remove(scrollView)
    scrollView = nil
  end
  if textObject then
    display.remove(textObject)
    textObject = nil
  end


  local function titleTouchListener(event)
    audio.stop(clickSoundCannel)
    audio.play(clickSound, clickPlayOptions)
    transition.to(scrollView, {y = centerY + screenHeight })
    getAdditionalInfo(event.target.id)
  end

  local options, competitionTitle
  local titleFontSize = 55
  local fontSize = 50
  local marginTop = 85
  local marginRight = 20
  local x = 0
  local initialY = 170


  scrollView = widget.newScrollView(
    {
      width = background.width * .96,
      height = background.height * .9,
      scrollWidth = background.width * 10,
      horizontalScrollDisabled = true,
      verticalScrollingDisabled = false,
      hideBackground = true,
  })
  scrollView.x = centerX
  scrollView.y = centerY + screenHeight

  local options = {
    text = texts.showSavedPicture.competitionList,
    anchorX = .5,
    x = scrollView.width / 2,
    y = 50,
    font = fonts.lalezar,
    fontSize = titleFontSize,
  }
  local listTitle = display.newText( options )
  scrollView:insert(listTitle)
  listTitle:setFillColor(235/255, 96/255, 9/255, 1)
  for i = 1, #list do
    options = {
      text = list[i].title,
      anchorX = 1,
      x = scrollView.width - 40,
      y = initialY + (i - 1) * marginTop,
      font = fonts.lalezar,
      fontSize = fontSize,
    }
    competitionTitle = display.newText( options )
    competitionTitle:setFillColor( 80 / 255, 80 / 255, 80 / 255, 1)
    competitionTitle.anchorX, competitionTitle.anchorY = 1, .5
    competitionTitle.id = list[i].id
    scrollView:insert(competitionTitle)

    -- competitionTitle:addEventListener("touch", titleTouchListener)
    competitionTitle:addEventListener("tap", titleTouchListener)

  end

  transition.to(scrollView, {y = centerY })

end
------------------------------------------------------------------------------
------------------------------------------------------------------------------
getAdditionalInfo = function(competitionID)
  if not panelIsRendered then
    return
  end

  local titleFontSize = 55
  infoForm = display.newGroup()
  group:insert(infoForm)
  infoForm.x, infoForm.y = -centerX, centerY
  local options = {
    parent = infoForm,
    text = texts.showSavedPicture.additionalInfo,
    parent = infoForm,
    x = centerX,
    y = centerY - 215,
    font = fonts.lalezar,
    fontSize = titleFontSize,
  }
  local title = display.newText( options )
  title:setFillColor(255/255, 128/255, 55/255, 1)

  local textColor = { 80 / 255, 80 / 255, 80 / 255 , 1}
  local city = native.newTextField( centerX + 0, centerY - 110, 400, 70)
  city.align = "right"
  city.size = 28
  city.placeholder = texts.showSavedPicture.cityPlaceholder
  infoForm:insert(city)
  options = {
    parent = infoForm,
    text = texts.showSavedPicture.cityTag,
    x = centerX + 330,
    y = city.y,
    font = fonts.lalezar,
    fontSize = 35
  }
  local cityTag = display.newText(options)
  cityTag:setFillColor( 80 / 255, 80 / 255, 80 / 255)

  local tag = native.newTextField( centerX + 0, centerY - 35, 400, 70)
  tag.align = "right"
  tag.size = 28
  tag.placeholder = texts.showSavedPicture.tagPlaceholder
  infoForm:insert(tag)
  options = {
    parent = infoForm,
    text = texts.showSavedPicture.tagTag,
    x = centerX + 300,
    y = tag.y,
    font = fonts.lalezar,
    fontSize = 35
  }
  local tagTag = display.newText(options)
  tagTag:setFillColor( 80 / 255, 80 / 255, 80 / 255)

  local description = native.newTextBox( centerX + 0, centerY + 75, 400, 140)
  infoForm:insert(description)
  description.align = "right"
  description.isEditable = true
  description.isFontSizeScaled = true
  description.size = 28
  description.align = "right"
  description.placeholder = texts.showSavedPicture.descriptionPlaceholder
  options = {
    parent = infoForm,
    text = texts.showSavedPicture.descriptionTag,
    x = centerX + 300,
    y = description.y,
    font = fonts.lalezar,
    fontSize = 35
  }
  local descriptionTag = display.newText(options)
  descriptionTag:setFillColor( 80 / 255, 80 / 255, 80 / 255)

  local confirmBackground = display.newRoundedRect( infoForm, centerX, centerY + screenHeight / 4, 220, 90, 10)
  confirmBackground:setFillColor(0 / 255, 169 / 255, 126 / 255)
  confirmBackground.name = "confirm"
  confirmBackground.fill.effect = "filter.grayscale"
  local options = {
    parent = infoForm,
    text = texts.newUser.confirm,
    x = confirmBackground.x,
    y = confirmBackground.y,
    font = fonts.lalezar,
    fontSize = 35
  }
  local confirm = display.newText(options)

  local function textListener( event )
    local target = event.target
    if ( event.phase == "began" ) then
    elseif ( event.phase == "ended" or event.phase == "submitted" ) then
    elseif ( event.phase == "editing" ) then
      if city.text ~= "" and tag.text ~= "" and description.text ~= "" then
        isValid = true
        confirmBackground.fill.effect = nil
      else
        isValid = false
        confirmBackground.fill.effect = "filter.grayscale"
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
      display.getCurrentStage():setFocus( event.target )
      event.target.isFocus = true
    end
    if not event.target.isFocus then
      return false
    end
    if event.phase == "ended" then
      event.target.width = event.target.width / clickResizeFactor
      event.target.height = event.target.height / clickResizeFactor
      if event.target.name == "confirm" then
        participate(competitionID, city.text, tag.text, description.text)
        transition.to(infoForm, { y = centerY, alpha = 0})
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end

  city:addEventListener( "userInput", textListener )
  tag:addEventListener( "userInput", textListener )
  description:addEventListener( "userInput", textListener )
  confirmBackground:addEventListener("touch", buttonTouchHandlers)

  transition.to(infoForm, { y = -centerY + 30})
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
participate = function(competitionID, city, tag, description)
  if not panelIsRendered then
    return
  end
  local b64text
  local path = system.pathForFile( image, imageDir )
  local fileHandle = io.open( path, "rb" )
  if fileHandle then
    if isFromGallery then
      b64text = "data:image/jpg;base64,"..mime.b64( fileHandle:read( "*a" ))
    else
      b64text = "data:image/png;base64,"..mime.b64( fileHandle:read( "*a" ))
    end
    io.close( fileHandle )

    local user = appConfig:getUserInfo(appConfig:getUserName())
    local parent = appConfig:getParent()

    local params = {}
    params.headers = {}
    params.headers["Content-Type"] = "application/json"
    params.headers["user_code"] = parent.user_code
    params.headers["racing_id"] = competitionID
    params.timeout = 10
    print(parent.user_code)
    print(competitionID)
    local tempFilePath = system.pathForFile( "temp.json", system.TemporaryDirectory )
    local tempFile = io.open(tempFilePath, "w")
    tempFile:write(json.encode( {
      first_name = user.name,
      last_name = user.lastName,
      birth_year = tonumber(user.birthYear),
      birth_month = tonumber(user.birthMonth),
      birth_day = tonumber(user.birthDay),
      gender = user.gender,
      city = city,
      tag = tag,
      desciption = description,
      url = b64text
    }))
    io.close(tempFile)

    local function networkListener(event)
      print("^^^^^^^^^^^^^^^event.status")
      print(event.status)
      renderText(event.status)
      print("^^^^^^^^^^^^^^^event.status")
      print(event.status == -1)
      if (event.status == -1) then
        print('yutuyt')
        hold = false
        renderText(texts.showSavedPicture.internetError)
      elseif (event.isError) then
        print ("jygjgjhghuytuytuytuyt")
        hold = false
        renderText(texts.showSavedPicture.problemInParticipation)
        renderText(event.status)
      else
        if ( event.phase == "began" ) then
        elseif ( event.phase == "progress" ) then
        elseif ( event.phase == "ended" ) then
          hold = false
          if event.status == 201 then
            renderText(texts.showSavedPicture.successfulParticipation)

          else
            print(event.status)
            if (event.status == -1) then
              print('9999999')
              hold = false
              renderText(texts.showSavedPicture.internetError)
            else
              renderText(texts.showSavedPicture.problemInParticipation)
              renderText(event.status)
            end
          end
        end
      end
    end
    hold = true
    renderText(texts.showSavedPicture.pleaseWait)
    network.upload( urls.participateInCompetition, "POST", networkListener, params, "temp.json", system.TemporaryDirectory, "application/json")
  else
    renderText(texts.showSavedPicture.problemInSendingImage)
  end
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
renderText = function(text, callBack)
  if not panelIsRendered then
    return
  end
  if scrollView then
    display.remove(scrollView)
    scrollView = nil
  end
  if textObject then
    display.remove(textObject)
    textObject = nil
  end

  local options = {
    text = text,
    parent = group,
    fontSize = 46,
    x = background.x,
    font = fonts.lalezar
  }
  textObject = display.newText( options )
  textObject:setFillColor( 80 / 255, 80 / 255, 80 / 255)

  if callBack then
    callBack()
  end
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
createGroup = function()
  if not panelIsRendered then
    return
  end

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
  background = display.newRoundedRect(group, 0, 0, boxSize * 2.5, boxSize * 1.75, boxSize / 5)
  background:setFillColor(247 / 255, 247 / 255, 247 / 255)
  background.x, background.y = boxSize * .075, boxSize * .075
  background:setStrokeColor(0 / 255, 169 / 255, 126 / 255, 1)
  background.strokeWidth = boxSize * .04



  background:addEventListener("touch", function() return true end)
  background:addEventListener("tap", function() return true end)


end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
hideGroup = function(callBack)
  if (not panelIsRendered) or (group.y == centerY + screenHeight) then
    return
  end

  transition.to(group, {y = centerY + screenHeight, alpha = 1, onComplete = callBack})
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
showGroup = function(callBack)
  if (not panelIsRendered) or (group.y == centerY - boxSize * .075) then
    return
  end
  transition.to(group, {y = centerY - boxSize * .075, alpha = 1, onComplete = callBack})
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
createBlur = function()
  if not panelIsRendered then
    return
  end
  blur = display.captureScreen()
  blur.x, blur.y = centerX, centerY
  blur:setFillColor(.4, .4, .4)
  blur.fill.effect = "filter.blur"
  blur.alpha = 0
  transition.to(blur, {alpha = 1})
  blur:addEventListener("tap", function() if hold then return true else closeSelectingPanel () end end)
  blur:addEventListener("touch", function() return true end)

  transition.to(blur, {alpha = 1})
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------

return competition
