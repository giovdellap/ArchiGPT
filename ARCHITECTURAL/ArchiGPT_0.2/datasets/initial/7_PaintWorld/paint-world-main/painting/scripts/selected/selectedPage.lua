local json = require("json")
local slider = require("scripts.selected.selectedSlider")

local selected = {}
local properties = {}
local previousCompetitionList
local getSelectedCategories, downloadCompetitions, renderSlider
local showBlur, closeBlur, changeText, callBackFunc, buttonTouchHandlers

function selected:render(params)
  local blur = nil
  local text = nil

  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.selected.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
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

  if previousCompetitionList then
    renderSlider(previousCompetitionList)
  else
    getSelectedCategories()
  end
end

------------------------------------------------------------------------------
-- Functions
------------------------------------------------------------------------------
callBackFunc = function(item)
  navigate("selectedList", item)
end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
renderSlider = function(competitionList)
  local categoriesSlider = slider:newSlider({
    numOfVisibleObjects = 3,
    selectedIndex = 2,
    x = 650,
    y = 400,
    width = 960,
    height = 500,
    callBack = callBackFunc,
    objects = competitionList,
    parent = display.currentStage,
    hasTitle = true
  })
  previousCompetitionList = competitionList
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
getSelectedCategories = function()
  local function networkListener( event )
    if event.status == 200 then
      downloadCompetitions(json.decode(event.response))
    elseif event.status == 204 then
      showTextOnBlur(texts.selected.noCompetition)
      timer.performWithDelay( 1500, closeBlur )
      timer.performWithDelay( 2000, function() back() end )
    else
      print("problem in fetching catgories")
      showTextOnBlur(texts.selected.problemInFetchingCompetitionList)
      timer.performWithDelay( 1500, closeBlur )
    end
  end

  local headers = {}
  headers["Content-Type"] = "application/json"
  local params = {}
  params.headers = headers
  showBlur()
  showTextOnBlur(texts.selected.pleaseWait)
  network.request( urls.getSelectedCategories, "GET", networkListener, params )

end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
downloadCompetitions = function(competitionList)
  local competitions = {}
  local allFilesDownloaded = false
  local problemFound = false
  ----------------------------------------------------
  local function checkForFile()
    local finishFlag = true
    for i = 1, #competitions do
      if not competitions[i].isDownloaded then
        finishFlag = false
      end
    end

    if finishFlag then
      closeBlur()
      renderSlider(competitions)
    end

  end
  ----------------------------------------------------
  local function networkListener( event, id )
    if problemFound then
      return
    end
    if event.status == 200 then
      for i = 1, #competitions do
        if competitions[i].competitionId == id then
          competitions[i].isDownloaded = true
        end
      end
      checkForFile()
    else
      print("proble in downloading competetions data")
      showTextOnBlur(texts.selected.problemInDownloadingCompetitions)
      timer.performWithDelay( 2000, closeBlur )
      problemFound = true
    end
  end

  for i = 1, #competitionList do
    if #competitionList[i]["picture_list"] > 0 and competitionList[i].title then
      local tempObject = {}
      tempObject["competitionId"] = competitionList[i].id
      tempObject["competitionTitle"] = competitionList[i].title
      tempObject["title"] = competitionList[i].title -- for the slider
      tempObject["isDownloaded"] = false
      tempObject["thumbnailPath"] = competitionList[i].id..".png"
      tempObject["baseDir"] = system.TemporaryDirectory
      table.insert(competitions, tempObject)

      local params = {}
      params.timeout = 10
      network.download(
        competitionList[i]["picture_list"][1]["url_thumbnail"],
        "GET",
        function(event)
          networkListener(event, competitionList[i].id)
        end,
        params,
        competitionList[i].id..".png",
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
      previousCompetitionList = nil
      back()
    elseif event.target.name == "rightArrow" then
      if (categoriesSlider ~= nil) then
        categoriesSlider:scrollRight()
      end
    elseif event.target.name == "leftArrow" then
      if (categoriesSlider ~= nil) then
        categoriesSlider:scrollLeft()
      end
    end
  end
end


function selected:close()
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

return selected
