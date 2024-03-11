local showSelectedPicture = {}
local properties = {}
function showSelectedPicture:render(params)
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(params.competitionTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
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

  local picture = display.newRect( display.contentCenterX, display.contentCenterY, display.actualContentWidth * 0.57, display.actualContentHeight * .57 )
  local paint = {
    type = "image",
    filename = params.main,
    baseDir = params.baseDir
  }
  picture.fill = paint
  table.insert(properties, picture)

  local nameFrame = display.newImage("images/selected/nameBackgroundFrame.png")
  nameFrame.x = display.contentCenterX
  nameFrame.y = display.contentCenterY + display.actualContentHeight * .6 / 2
  nameFrame.width = display.actualContentWidth * 0.3
  table.insert(properties, nameFrame)

  local options = {
    x = nameFrame.x,
    y = nameFrame.y + 14,
    width = nameFrame.width,
    height = nameFrame.height,
    font = fonts.bYekan,
    fontSize = fonts.selectedCompetitorNameSize,
    text = params.title,
    align = "center"
  }
  local competitorName = display.newText(options)
  competitorName:setFillColor(0, 0, 0)

  local save = display.newImageRect( "images/selected/save.png", 126, 126)
  save.x = display.contentCenterX + 480
  save.y = display.contentCenterY - 150
  save.name = "save"
  table.insert(properties, save)

  function saveFunction(event)
    local temp = display.newImage(params.main, params.baseDir)
    local combined = display.capture( temp, { saveToPhotoLibrary = true, captureOffscreenArea = true } )
    display.remove(temp)
    display.remove(combined)
  end

  -- cheetah:addEventListener("tap",categoriesSlider.foo)
  local function buttonTouchHandlers(event)
    if event.phase == "began" then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      event.target.width = event.target.width * clickResizeFactor
      event.target.height = event.target.height * clickResizeFactor
    elseif event.phase == "ended" then
      event.target.width = event.target.width / clickResizeFactor
      event.target.height = event.target.height / clickResizeFactor
      if event.target.name == "back" then
        back()
      elseif event.target.name == "save" then
        alert({
          textImage = images.alerts.savePhoto,
          isYesNo = true,
          callBack = function(index)
            if index == 1 then
              saveFunction()
            elseif index == 2 then
            end
          end})
        end
      end
    end
    backButton:addEventListener("touch", buttonTouchHandlers)
    save:addEventListener("touch", buttonTouchHandlers)
  end

  function showSelectedPicture:close()
    if #properties > 0 then
      for n = #properties, 1, - 1 do
        display.remove(properties[n])
        properties[n] = nil
      end
    end
  end
  return showSelectedPicture
