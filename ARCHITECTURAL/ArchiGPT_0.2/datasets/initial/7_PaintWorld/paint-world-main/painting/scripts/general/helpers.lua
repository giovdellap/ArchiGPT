function table.val_to_str ( v )
  if "string" == type( v ) then
    v = string.gsub( v, "\n", "\\n" )
    if string.match( string.gsub(v, "[^'\"]",""), '^" + $' ) then
      return "'" .. v .. "'"
    end
    return '"' .. string.gsub(v, '"', '\\"' ) .. '"'
  else
    return "table" == type( v ) and table.tostring( v ) or
    tostring( v )
  end
end

function table.key_to_str ( k )
  if "string" == type( k ) and string.match( k, "^[_%a][_%a%d]*$" ) then
    return k
  else
    return "[" .. table.val_to_str( k ) .. "]"
  end
end

function table.tostring( tbl )
  local result, done = {}, {}
  for k, v in ipairs( tbl ) do
    table.insert( result, table.val_to_str( v ) )
    done[ k ] = true
  end
  for k, v in pairs( tbl ) do
    if not done[ k ] then
      table.insert( result,
      table.key_to_str( k ) .. "=" .. table.val_to_str( v ) )
    end
  end
  return "{" .. table.concat( result, "," ) .. "}"
end


table.indexOf = function( t, object )
  local result

  if "table" == type( t ) then
    for i = 1, #t do
      if object == t[i] then
        result = i
        break
      end
    end
  end

  return result
end


local blur, group, text
function closeLoading()
  local function removeGroup()
    display.remove(blur)
    display.remove(text)
    blur = nil
    text = nil
  end
  removeGroup()
end

function showLoading()
  blur = display.newRect( centerX, centerY, screenWidth, screenHeight)
  blur:setFillColor(98 / 255, 244 / 255, 66 / 255)
  blur:addEventListener("tap", function() return true end)
  blur:addEventListener("touch", function() return true end)
  group = display.newGroup()
  group.y = centerY + screenHeight
  local options = {
    text = texts.general.loading,
    x = centerX,
    y = centerY,
    fontSize = 54,
    font = fonts.lalezar
  }
  text = display.newText( options )
  text:setFillColor(1, 1, 1, 1)

  end--


  function deleteDirectory(dir)
    local resultOK, errorMsg, flag;
    flag = lfs.chdir(dir)
    if flag then
      --remove files from directory
      for file in lfs.dir(dir) do
        if ((file == ".") or (file == "..")) then -- skip system files
          -- do nothing
        else
          local theFile = dir.."/"..file;

          if (lfs.attributes(theFile, "mode") == "directory") then
            deleteDirectory(theFile);
          else
            resultOK, errorMsg = os.remove(theFile);

            if (not resultOK) then
              print("Error removing file: "..file..":"..errorMsg);
            else print("Removed file: " .. file)
            end
          end
        end
      end
    end

    -- remove directory
    resultOK, errorMsg = os.remove(dir);

    if (not resultOK) then
      print("Error removing directory: "..dir.."--->>>"..errorMsg);
    else print("Removed directory: " .. dir)
    end
  end

  function saveImageFromGallery(img, baseDir)
    local userName = appConfig:getUserName()
    local pictureDirectory = os.date():gsub("/", "")
    pictureDirectory = pictureDirectory:gsub(" ", "")
    pictureDirectory = pictureDirectory:gsub(":", "")
    pictureDirectory = "g"..pictureDirectory
    local docs_path = system.pathForFile( userName.."/savedPictures", system.DocumentsDirectory )
    local success = lfs.chdir( docs_path )
    if ( success ) then
      lfs.mkdir( pictureDirectory )
    end
		local tempGroup = display.newGroup()
    local tempImage = display.newImage(tempGroup, img, baseDir)
    tempImage.x = 3 * screenWidth
		if tempImage.width > tempImage.height then
      local resizeFactor = tempImage.width / canvasWidth
      tempImage.width = tempImage.width / resizeFactor
      tempImage.height = tempImage.height / resizeFactor
    else
      local resizeFactor = tempImage.height / canvasHeight
      tempImage.width = tempImage.width / resizeFactor
      tempImage.height = tempImage.height / resizeFactor
    end
    display.save( tempGroup, { filename = userName.."/savedPictures/"..pictureDirectory.."/main.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory } )
		display.remove(tempImage)
    display.remove( tempGroup )
    return pictureDirectory
  end

  function loadimg()
		showLoading()
    local function onComplete( event )
      if event.completed then
        -- display.remove(event.target)
        local pictureName = saveImageFromGallery("tempGalleryImage.jpg", system.DocumentsDirectory)
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
    os.remove( system.pathForFile( "tempGalleryImage.jpg", system.DocumentsDirectory ) )
    if media.hasSource( media.PhotoLibrary ) then
      media.selectPhoto(
        {
          mediaSource = media.PhotoLibrary,
          listener = onComplete,
          destination = { baseDir = system.DocumentsDirectory, filename = "tempGalleryImage.jpg" }
        })
    else
        native.showAlert( messages.alerts.title, messages.alerts.noPhotoLibrary, { messages.alerts.close } )
    end
  end

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

function errorResponse(response)
  print("errors")
  if ( response.status == 'SVC0001' ) then
    showTextOnBlur(login.SVC0001)
  elseif ( response.status == 'SVC0002' ) then
    showTextOnBlur(login.SVC0002)
  elseif ( response.status == 'SVC0004' ) then
    showTextOnBlur(login.SVC0004)
  elseif ( response.status == 'POL0103' ) then
    showTextOnBlur(login.POL0103)
  elseif ( response.status == 'POL0104' ) then
    showTextOnBlur(login.POL0104)
  elseif ( response.status == 'POL0105' ) then
    showTextOnBlur(login.POL0105)
  elseif ( response.status == 'POL0106' ) then
    showTextOnBlur(login.POL0106)
  elseif ( response.status == 'POL0401' ) then
    showTextOnBlur(login.POL0401)
  elseif ( response.status == 'POL0502' ) then
    showTextOnBlur(login.POL0502)
  elseif ( response.result == 'charged') then
    showTextOnBlur(login.charged)
  elseif ( response.result == 'auto-charge enabled') then
    showTextOnBlur(login.autoCharge)
  elseif ( response.result == 'subscription already exists') then
    showTextOnBlur(login.subscriptionAlreadyExists)
  else
    showTextOnBlur(texts.parent.problemInConnectingToServer)
  end

end
