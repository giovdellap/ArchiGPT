local competition = require("scripts.general.competition")

local showSavedPicture = {}
local properties = {}

function showSavedPicture:render(params)
  print("showSavedPicture page")

  local pictureAddress = system.pathForFile( params.image, params.dir)
  local pictureFile = io.open(pictureAddress, "r")
  if pictureFile then
    io.close(pictureFile)
  else
    print("no image")
    back()
    return
  end

  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local picture = display.newImage( params.image, params.dir )
  picture.x = display.contentCenterX
  picture.y = display.contentCenterY
  table.insert(properties, picture)

  local baseWidth = 800
  local baseHeight = 550
  if picture.width > picture.height then
    local resizeFactor = picture.width / baseWidth
    picture.width = picture.width / resizeFactor
    picture.height = picture.height / resizeFactor
  else
    local resizeFactor = picture.height / baseHeight
    picture.width = picture.width / resizeFactor
    picture.height = picture.height / resizeFactor
  end


  local cup = display.newImage( "images/gallery/cup.png")
  cup.x = display.contentCenterX + 310
  cup.y = display.contentCenterY + 290
  cup.name = "cup"
  table.insert(properties, cup)

  local backButton = display.newImageRect( "images/paint/back.png", 125, 125)
  backButton.x = backButtonX
  backButton.y = backButtonY
  backButton.name = "back"
  table.insert(properties, backButton)

  local save = display.newImageRect( "images/gallery/save.png", 126, 126)
  save.x = display.contentCenterX + 480
  save.y = display.contentCenterY - 175
  save.name = "save"
  table.insert(properties, save)

  local delete = display.newImageRect( "images/gallery/delete.png", 126, 126)
  delete.x = display.contentCenterX + 480
  delete.y = display.contentCenterY - 20
  delete.name = "delete"
  table.insert(properties, delete)

  local edit = display.newImageRect( "images/gallery/edit.png", 126, 126)
  edit.x = display.contentCenterX + 480
  edit.y = display.contentCenterY + 130
  edit.name = "edit"
  table.insert(properties, edit)


  if params.isFromGallery then
    -- save.isVisible = false
    -- delete.isVisible = false
    -- edit.y = display.contentCenterY - 36
  end

  if params.directoryAddress then
    local bitmapAddress = system.pathForFile( params.directoryAddress.."/bitmap.json", params.dir)
    local bitmap = io.open(bitmapAddress, "r")
    if bitmap then
      io.close(bitmap)
    else
      if not params.isFromGallery then
        edit.isVisible = false
      end
    end
  end

  --------------
  -- competition:showSelectingPanel(params.image, params.dir, params.isFromGallery)
  --------------
  local function saveFunction(event)
    local temp = display.newImage(params.image, params.dir)
    local combined = display.capture( temp, { saveToPhotoLibrary = true, captureOffscreenArea = true } )
    display.remove(temp)
    display.remove(combined)
    back()
  end

  local function deletePhoto(event)
    local path = system.pathForFile( params.directoryAddress, params.dir )
    for file in lfs.dir(path) do
      if file ~= "." and file ~= ".." then
        os.remove(path.."/"..file)
      end
    end
    lfs.rmdir(path)
    back()
  end

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

        elseif event.target.name == "edit" then
          showLoading()
          timer.performWithDelay( 100, function() navigate("paint", params) end )
        elseif event.target.name == "cup" then
          if appConfig:isParentSet() then
            if appConfig:hasUser() then
              competition:showSelectingPanel(params.image, params.dir, params.isFromGallery)
            else
              alert({
                textImage = images.alerts.noUser,
                isYesNo = true,
                callBack = function(index)
                  if index == 1 then
                    navigate("newUser")
                  elseif index == 2 then
                  end
                end})
              end
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

            elseif event.target.name == "delete" then
              alert({
                textImage = images.alerts.deletePhoto,
                isYesNo = true,
                callBack = function(index)
                  if index == 1 then
                    deletePhoto()
                  elseif index == 2 then
                  end
                end})

              end
              event.target.isFocus = false
              display.getCurrentStage():setFocus(nil)
            end
          end
          backButton:addEventListener("touch", buttonTouchHandlers)
          save:addEventListener("touch", buttonTouchHandlers)
          edit:addEventListener("touch", buttonTouchHandlers)
          cup:addEventListener("touch", buttonTouchHandlers)
          delete:addEventListener("touch", buttonTouchHandlers)
        end

        function showSavedPicture:close()
          if #properties > 0 then
            for n = #properties, 1, - 1 do
              display.remove(properties[n])
              properties[n] = nil
            end
          end


        end
        return showSavedPicture
