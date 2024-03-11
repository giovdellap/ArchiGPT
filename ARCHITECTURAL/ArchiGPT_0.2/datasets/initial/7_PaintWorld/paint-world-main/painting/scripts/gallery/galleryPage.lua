local lfs = require ("lfs")
local widget = require("widget")

local gallery = {}
local properties = {}
local images = {}
local scrollView
function gallery:render(params)
  print("gallery page")
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.gallery.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
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

  local history = display.newImage( "images/gallery/history.png")
  history.x = centerX
  history.y = centerY + 150
  history.name = "history"
  table.insert(properties, history)

  renderSoundButton()

  local doc_dir = system.DocumentsDirectory;
  local doc_path = system.pathForFile("", doc_dir);
  local resultOK, errorMsg;
  local files = {}
  for file in lfs.dir(doc_path.."/"..appConfig:getUserName().."/savedPictures") do
    if file ~= "." and file ~= ".." then
      table.insert(files, file)
    end
  end


  -------------------------------------
  local function iconListener( event )
    if ( event.phase == "moved" ) then
      local dx = math.abs( event.x - event.xStart )
      if ( dx > 5 ) then
        scrollView:takeFocus( event )
      end
    elseif ( event.phase == "ended" ) then
      audio.stop(clickSoundCannel)
      audio.play(clickSound, clickPlayOptions)
      navigate("showSavedPicture", {directoryAddress = event.target.address, dir = system.DocumentsDirectory, image = event.target.address.."/main.png", name = event.target.name, isFromGallery = event.target.isFromGallery})
    end
    return true
  end
  if #files > 0 then
    scrollView = widget.newScrollView
    {
      width = 900,
      height = 280,
      verticalScrollDisabled = true,
      hideBackground = true
    }
    scrollView.x = display.contentCenterX
    scrollView.y = display.contentCenterY - 50

    local currentX = 143
    local width = 275
    local height = 196
    local y = 100
    local margin = 15
    for i = 1, #files do

      images[i] = display.newRect(currentX, y, width, height)
      images[i]:setStrokeColor(58 / 255, 202 / 255, 190 / 255)
      images[i].strokeWidth = 5
      local paint = {
        type = "image",
        filename = appConfig:getUserName().."/savedPictures/"..files[i].."/main.png",
        baseDir = system.DocumentsDirectory
      }
      images[i].fill = paint
      images[i].address = appConfig:getUserName().."/savedPictures/"..files[i]
      images[i].name = files[i]
      images[i].isFromGallery = false
      if string.sub(tostring( images[i].name ), 1, 1) == "g" then
        images[i].isFromGallery = true
      end
      currentX = currentX + images[i].width + margin
      scrollView:insert(images[i])
      images[i]:addEventListener("touch", iconListener)
    end
  else
    local noPicture = display.newImage("images/gallery/noPicture.jpg")
    noPicture.x = centerX
    noPicture.y = centerY - 75
    table.insert(properties, noPicture)
  end
  --------------------------------------



  local function buttonTouchHandlers(event)
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
      if event.target.name == "back" then
        back()
      elseif event.target.name == "history" then
        navigate("competitionHistory")
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  backButton:addEventListener("touch", buttonTouchHandlers)
  history:addEventListener("touch", buttonTouchHandlers)
  closeLoading()
end


function gallery:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
  if #images > 0 then
    for n = #images, 1, - 1 do
      display.remove(images[n])
      images[n] = nil
    end
  end
  if scrollView then
    scrollView:removeSelf()
    scrollView = nil
  end
end
return gallery
