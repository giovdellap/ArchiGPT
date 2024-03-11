local widget = require("widget")
local json = require("json")
local competitionHistory = {}
local properties = {}
local getCompetitionHistoryList, downloadPhotos, renderSlider, buttonTouchHandlers
local showBlur, closeBlur, changeText, scrollView

function competitionHistory:render(params)
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)


  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.home.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
  table.insert(properties, pageTitle)

  local cheetah = display.newImage("images/cheetah.png")
  cheetah.x = cheetahX
  cheetah.y = cheetahY
  table.insert(properties, cheetah)

  local backButton = display.newImageRect( "images/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  local picture = display.newImageRect( "images/pic.png", 132, 152)
  picture.x = display.contentCenterX - 480
  picture.y = display.contentHeight - 680
  picture.name = "picture"
  table.insert(properties, picture)

  local pictureInner = display.newImage(appConfig:getAvatarAddress())
  pictureInner.x = picture.x
  pictureInner.y = picture.y
  pictureInner.width = picture.width * .9
  pictureInner.height = picture.height * .9
  table.insert(properties, pictureInner)

  picture:addEventListener("touch", buttonTouchHandlers)
  backButton:addEventListener("touch", buttonTouchHandlers)

  getCompetitionHistoryList()
  renderSoundButton()
end
------------------------------------------------------------------------------
-- Functions
------------------------------------------------------------------------------

renderSlider = function(photoList)
  closeBlur()
  scrollView = widget.newScrollView
  {
    width = 900,
    height = 320,
    verticalScrollDisabled = true,
    hideBackground = true
  }
  scrollView.x = display.contentCenterX
  scrollView.y = display.contentCenterY

  local currentX = 193
  local width = 340
  local height = 300
  local y = 100
  local margin = 15
  for i = 1, #photoList do

    images[i] = display.newRect(currentX, y, width, height)
    images[i]:setStrokeColor(58 / 255, 202 / 255, 190 / 255)
    local paint = {
      type = "image",
      filename = photoList[i].main,
      baseDir = photoList[i].baseDir
    }
    images[i].fill = paint
    print(photoList[i].status)
    local options = {
      text = photoList[i].status,
      width = images[i].width,
      height = 50,
      font = fonts.lalezar,
      fontSize = 30,
      x = images[i].x,
      y = images[i].y + images[i].height / 2 + 40,
      align = "center"
    }
    images[i].text = display.newText(options)
    images[i].text:setFillColor(80 / 255, 80 / 255, 80 / 255, 1)

    currentX = currentX + images[i].width + margin
    scrollView:insert(images[i])
    scrollView:insert(images[i].text)
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
closeBlur = function()
  local function removeGroup(cb1)
    display.remove(text)
    display.remove(blur)
    blur = nil
    text = nil
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
getCompetitionHistoryList = function()
  local function networkListener( event )
    if event.status == 200 then
      downloadPhotos(json.decode(event.response))
    elseif event.status == 204 then
      showTextOnBlur(texts.competitionHistory.noHistory)
      timer.performWithDelay( 1500, closeBlur )
    else
      showTextOnBlur(texts.competitionHistory.problemInDownloadingCompetition)
      timer.performWithDelay( 1500, closeBlur )
    end
  end
  local parent = appConfig:getParent()
  local headers = {}
  headers["Content-Type"] = "application/json"
  headers["user_code"] = parent.user_code
  local params = {}
  params.headers = headers
  showBlur()
  showTextOnBlur(texts.competitionHistory.pleaseWait)
  network.request( urls.getCompetitionHistoryList, "GET", networkListener, params )

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
      closeBlur()
      print("all files are downloaded")
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
      showTextOnBlur(texts.competitionHistory.problemInDownloadingCompetition)
      timer.performWithDelay( 1500, closeBlur )
      problemFound = true
    end
  end

  local pictureList = photoList
  for i = 1, #pictureList do
    local tempObject = {}
    tempObject["id"] = pictureList[i].id
    tempObject["isDownloaded"] = false
    if pictureList[i].conditionType == nil then
      pictureList[i].conditionType = 0
    end
    local lastStatus = "status"..pictureList[i].conditionType
    tempObject["status"] = texts.statusPaint[lastStatus]
    tempObject["main"] = "getCompetitionHistoryList"..pictureList[i].id..".png"
    tempObject["baseDir"] = system.TemporaryDirectory
    table.insert(photos, tempObject)
    local params = {}
    params.timeout = 15
    network.download(
      pictureList[i]["url"],
      "GET",
      function(event)
        networkListener(event, pictureList[i].id)
      end,
      params,
      "getCompetitionHistoryList"..pictureList[i].id..".png",
      system.TemporaryDirectory
    )
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
  if event.phase == "ended" then
    event.target.width = event.target.width / clickResizeFactor
    event.target.height = event.target.height / clickResizeFactor

    if event.target.name == "picture" then
      pictureInner.width = pictureInner.width / clickResizeFactor
      pictureInner.height = pictureInner.height / clickResizeFactor
      navigate("users")
    elseif event.target.name == "back" then
      back()
    end
    event.target.isFocus = false
    display.getCurrentStage():setFocus(nil)
  end
end

function competitionHistory:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
  display.remove(scrollView)
  scrollView = nil
end

return competitionHistory
