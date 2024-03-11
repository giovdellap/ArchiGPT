local json = require("json")
local slider = require("scripts.selected.selectedListSlider")

local selectedList = {}
local properties = {}
local previousPhotoList
local getSelectedList, downloadPhotos, renderSlider
local showBlur, closeBlur, changeText, callBackFunc

function selectedList:render(item)
  local blur = nil
  local text = nil
  local competitionTitle = item.competitionTitle
  local competitionId = item.competitionId

  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(competitionTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  table.insert(properties, pageTitle)

  local cheetah = display.newImage("images/cheetah.png")
  cheetah.x = cheetahX
  cheetah.y = cheetahY
  table.insert(properties, cheetah)

  local backButton = display.newImageRect( "images/paint/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  renderSoundButton()

  local leftArrow = display.newImage("images/arrowLeft.png")
  leftArrow.x = 120
  leftArrow.y = 400
  leftArrow.name = "leftArrow"
  table.insert(properties, leftArrow)

  local rightArrow = display.newImage("images/arrowRight.png")
  rightArrow.x = 1180
  rightArrow.y = 400
  rightArrow.name = "rightArrow"
  table.insert(properties, rightArrow)

  backButton:addEventListener("touch", buttonTouchHandlers)
  leftArrow:addEventListener("touch", buttonTouchHandlers)
  rightArrow:addEventListener("touch", buttonTouchHandlers)

  if previousPhotoList then
    renderSlider(previousPhotoList)
  else
    getSelectedList(competitionId, competitionTitle)
  end
end

------------------------------------------------------------------------------
-- Functions
------------------------------------------------------------------------------
callBackFunc = function(item)
  navigate("showSelectedPicture", item)
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
renderSlider = function(photoList)
  local categoriesSlider = slider:newSlider({
    numOfVisibleObjects = 3,
    selectedIndex = 2,
    x = 650,
    y = 400,
    width = 960,
    height = 500,
    callBack = callBackFunc,
    objects = photoList,
    parent = display.currentStage,
    hasTitle = true
  })
  previousPhotoList = photoList
  properties.slider = categoriesSlider
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
closeBlur = function()
  local function removeGroup(cb1)
    display.remove(blur)
    blur = nil
    if text then
      display.remove(text)
      text = nil
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
  transition.to(blur, {alpha = 1})
  blur:addEventListener("tap", function() return true end)
  blur:addEventListener("touch", function() return true end)
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
getSelectedList = function(competitionId, competitionTitle)
  local function networkListener( event )
    if event.status == 200 then
      downloadPhotos(json.decode(event.response))
    else
      showTextOnBlur(texts.selectedList.problemInDownloadingCompetition)
      timer.performWithDelay( 1500, closeBlur )
    end
  end

  local headers = {}
  headers["Content-Type"] = "application/json"
  local params = {}
  params.headers = headers
  showBlur()
  showTextOnBlur(texts.selectedList.pleaseWait)
  network.request( urls.getSelectedCategories.."/"..competitionId, "GET", networkListener, params )

end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
downloadPhotos = function(photoList)
  local photos = {}
  local allFilesDownloaded = false
  local problemFound = false
  ----------------------------------------------------
  local function checkForFile()
    local finishFlag = true
    for i = 1, #photos do
      if not photos[i].isDownloaded then
        finishFlag = false
      end
    end

    if finishFlag then
      print("all files are downloaded selected")
      closeBlur()
      renderSlider(photos)
    end

  end
  ----------------------------------------------------
  local function networkListener( event, id )
    if problemFound then
      return
    end
    if event.status == 200 then
      for i = 1, #photos do
        if photos[i].id == id then
          photos[i].isDownloaded = true
        end
      end
      checkForFile()
    else
      showTextOnBlur(texts.selectedList.problemInDownloadingCompetition)
      timer.performWithDelay( 1500, closeBlur )
      problemFound = true
    end
  end

  local pictureList = photoList["picture_list"]
  local competitionId = photoList.id -- from the response
  local competitionTitle = photoList.title --from the response
  for i = 1, #pictureList do
    if pictureList[i].title then
      local tempObject = {}
      tempObject["id"] = pictureList[i].id
      tempObject["title"] = pictureList[i].title
      tempObject["isDownloaded"] = false
      tempObject["main"] = competitionId..pictureList[i].id..".png"
      tempObject["baseDir"] = system.TemporaryDirectory
      tempObject["competitionTitle"] = competitionTitle
      tempObject["competitionId"] = competitionId
      table.insert(photos, tempObject)

      local params = {}
      params.timeout = 15
      network.download(
        pictureList[i]["url_main"],
        "GET",
        function(event)
          networkListener(event, pictureList[i].id)
        end,
        params,
        competitionId..pictureList[i].id..".png",
        system.TemporaryDirectory
      )
    end
  end


end

------------------------------------------------------------------------------
-- Event Handler
------------------------------------------------------------------------------
buttonTouchHandlers = function(event)
  if event.phase == "began" then
    audio.stop(clickSoundCannel)
    audio.play(clickSound, clickPlayOptions)
    event.target.width = event.target.width * clickResizeFactor
    event.target.height = event.target.height * clickResizeFactor
  elseif event.phase == "ended" then
    event.target.width = event.target.width / clickResizeFactor
    event.target.height = event.target.height / clickResizeFactor
    if event.target.name == "back" then
      previousPhotoList = nil
      back()
    elseif event.target.name == "rightArrow" then
      if properties.slider then
        properties.slider:scrollRight()
      end
    elseif event.target.name == "leftArrow" then
      if properties.slider then
        properties.slider:scrollLeft()
      end
    end
  end
end


function selectedList:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
  if properties.slider then
    properties.slider:close()
    properties.slider = nil
  end
end

return selectedList
