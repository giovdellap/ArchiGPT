local json = require("json")
local lfs = require("lfs")

local userName = nil
local parent = nil
local appConfig = nil
local users = {}
local products = {}
local typeOperator = nil
local token = nil
local referenceCode = nil
local appConfig = {}
local flagCheckStatus = false
--------------------------------------
-- SAVING DEFAULT AVATAR TO DOCUMENTS directory
--------------------------------------
local defaultAvatarPath = system.pathForFile( "defaultAvatar.png", system.DocumentsDirectory )
local defaultAvatarFile = io.open(defaultAvatarPath, "r")
if defaultAvatarFile then
  io.close(defaultAvatarFile)
else
  local temp = display.newImage("images/users/defaultAvatar.png")
  temp.x = -2 * screenWidth
  temp.y = -2 * screenHeight
  display.save(temp, { filename = "defaultAvatar.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory })
  display.remove(temp)
  temp = nil
end

-------------------------------------
-- CHECKING FOR CONFIG FILE
-------------------------------------
local configFilePath = system.pathForFile( "config.json", system.DocumentsDirectory )
local configFile = io.open(configFilePath, "r")
local contents = nil
if configFile then
  contents = configFile:read("*a")
  local temp = json.decode( contents )
  if temp then
    appConfig = temp
    if temp.parent then
      print (temp.parent)
      parent = appConfig.parent
    end

    if temp.users then
      users = appConfig.users
    end

    if temp.userName then
      userName = appConfig.userName
    end

    if temp.products then
      products = appConfig.products
    end

    if temp.typeOperator then
      typeOperator = appConfig.typeOperator
    end

    if temp.token then
      token = appConfig.token
    end

    if temp.referenceCode then
      referenceCode = appConfig.referenceCode
    end

    if temp.parentPhone then
      parentPhone = appConfig.parentPhone
    end
  end
  io.close(configFile)
end

----------------------------------
-- DELETING EXTERNAL FOLDERS
----------------------------------
local tempUsersId = {}
for i = 1, #users do
  tempUsersId[users[i].id] = true
end
local documentsDirectory = system.pathForFile( "", system.DocumentsDirectory )
for file in lfs.dir(documentsDirectory) do
  if file ~= "." and file ~= ".." and file ~= "config.json" and file ~= "defaultAvatar.png" and file ~= "newColoringCategories" then
    if not tempUsersId[file] then
      lfs.rmdir(documentsDirectory.."/"..file.."/savedPictures")
      local result, error = lfs.rmdir(documentsDirectory.."/"..file)
      print("Deleting garbage folder: "..file, result, error)
    end
  end
end

----------------------------------
--appConfig OBJECT METHODS
----------------------------------

function appConfig:eraseUsers()
  for i = 1, #users do
    users[i] = nil
  end
  users = nil
  users = {}
end
function appConfig:eraseParent()
  parent = nil
  appConfig:saveConfig()
end
function appConfig:saveConfig()
  appConfig.parent = parent
  appConfig.users = user
  appConfig.userName = userName
  appConfig.products = products
  appConfig.typeOperator = typeOperator
  appConfig.token = token
  appConfig.parentPhone = parentPhone
  appConfig.referenceCode = referenceCode
  local configFilePath = system.pathForFile( "config.json", system.DocumentsDirectory )
  local configFile = io.open(configFilePath, "w")
  configFile:write(json.encode( {
    parent = parent,
    users = users,
    userName = userName,
    products = products,
    typeOperator = typeOperator,
    token = token,
    referenceCode = referenceCode,
    parentPhone = parentPhone
  }))
  io.close(configFile)
end


function appConfig:isParentSet()
  if appConfig.parent then
    return true
  else
    return false
  end
end

function appConfig:hasUser()
  for i = 1, #users do
    if not users[i].isDefault then
      return true
    end
  end
  return false
end

function appConfig:hasThisUser(name)
  for i = 1, #users do
    if users[i].name == name then
      return true
    end
  end
  return false
end

function appConfig:editUser(userId, name, lastName, gender, birthYear, birthMonth, birthDay, avatar, isDefault)
  local index = nil
  for i = 1, #users do
    if users[i].id == userId then
      index = i
    end
  end
  if index then
    users[index] = {
      id = userId,
      name = name,
      lastName = lastName,
      gender = gender,
      birthYear = birthYear,
      birthMonth = birthMonth,
      birthDay = birthDay
    }

    if avatar then
      display.save( avatar, { filename = userId.."/avatar.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory } )
    end
    appConfig:saveConfig()
  else
    return false
  end
end

function appConfig:saveUser(name, lastName, gender, birthYear, birthMonth, birthDay, avatar, isDefault)
  if #users > 0 then
    local tempId
    for i = 1, #users do
      if users[i].isDefault then
        tempId = users[i].id
        users[i] = {
          id = tempId,
          name = name,
          lastName = lastName,
          gender = gender,
          birthYear = birthYear,
          birthMonth = birthMonth,
          birthDay = birthDay
        }

        if avatar then
          display.save( avatar, { filename = tempId.."/avatar.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory } )
        else
          local temp = display.newImage("defaultAvatar.png", system.DocumentsDirectory)
          temp.x = -2 * screenWidth
          temp.y = -2 * screenHeight
          display.save(temp, { filename = tempId.."/avatar.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory })
          display.remove(temp)
          temp = nil
        end
        userName = tempId
        appConfig:saveConfig()
        return
      end

    end
  end

  local userPath = os.date():gsub("/", "")
  userPath = userPath:gsub(" ", "")
  userPath = userPath:gsub(":", "")
  local docs_path = system.pathForFile( "", system.DocumentsDirectory )
  -- Change current working directory
  local success = lfs.chdir( docs_path ) -- Returns true on success
  userImageDirectory = system.DocumentsDirectory
  if ( success ) then
    lfs.mkdir( userPath )
    userImageDirectory = lfs.currentdir() .. "/" .. userPath
  end
  local success = lfs.chdir( userImageDirectory )
  if ( success ) then
    lfs.mkdir( "savedPictures" )
  end
  table.insert(users, {
    id = userPath,
    name = name,
    lastName = lastName,
    gender = gender,
    birthYear = birthYear,
    birthMonth = birthMonth,
    birthDay = birthDay,
    isDefault = isDefault
  })
  if avatar then
    display.save( avatar, { filename = userPath.."/avatar.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory } )
  else
    local temp = display.newImage("defaultAvatar.png", system.DocumentsDirectory)
    temp.x = -2 * screenWidth
    temp.y = -2 * screenHeight
    display.save(temp, { filename = userPath.."/avatar.png", captureOffscreenArea = true, baseDir = system.DocumentsDirectory })
    display.remove(temp)
    temp = nil
  end
  userName = userPath
  appConfig:saveConfig()
end
-----------------------------------------------------------
function appConfig:deleteUser(id, callBack)
  if appConfig:hasUser() then
    local index
    for i = 1, #users do
      if users[i].id == id then
        index = i
      end
    end
    if index then
      local picturesDirectoryPath = system.pathForFile( id.."/savedPictures", system.DocumentsDirectory )
      for picture in lfs.dir(picturesDirectoryPath) do
        if picture ~= "." and picture ~= ".." then

          local path = system.pathForFile( id.."/savedPictures/"..picture, system.DocumentsDirectory )
          for file in lfs.dir(path) do
            if file ~= "." and file ~= ".." then
              os.remove(path.."/"..file)
            end
          end
          lfs.rmdir(path)

        end
      end
      -- remove
      lfs.rmdir(picturesDirectoryPath)
      os.remove(system.pathForFile( id.."/avatar.png", system.DocumentsDirectory ))

      if userName == id then
        userName = nil
      end
      if #users < 2 then
        appConfig:eraseUsers()
        appConfig:saveUser("default", "default", "boy", 1, 1, 1, nil, true)
      else
        table.remove(users, index)
        if not userName then
          userName = users[#users].id
        end
        appConfig:saveConfig()
      end
      lfs.rmdir(system.pathForFile( id, system.DocumentsDirectory ))
      callBack()
    end
  end
end

function appConfig:savePurchasedProduct(productCode, response)
  products[productCode] = response
  appConfig:saveConfig()
end
---------------------------------------------------------------------
function appConfig:getParent()
  return parent
end


function appConfig:setParentFromJSON(newParent)
  print("%%%%%%%%%     pareny    %%%%%%%%%")
  print(newParent)
  parent = json.decode(newParent)
  appConfig:saveConfig()
end


function appConfig:getUserName()
  return userName
end


function appConfig:setUserName(newUserName)
  userName = newUserName
  appConfig:saveConfig()
end

function appConfig:getUsers()
  return users
end


function appConfig:getTypeOperator()
  return typeOperator
end


function appConfig:setTypeOperator(newTypeOperator)
  typeOperator = newTypeOperator
  appConfig:saveConfig()
end

function appConfig:getToken()
  return token
end


function appConfig:setToken(newToken)
  token = newToken
  appConfig:saveConfig()
end

function appConfig:getParentPhone()
  return parentPhone
end

function appConfig:setParentPhone(newParentPhone)
  parentPhone = newParentPhone
  appConfig:saveConfig()
end

function appConfig:getReferenceCode()
  return referenceCode
end

function appConfig:setReferenceCode(newReferenceCode)
  referenceCode = newReferenceCode
  appConfig:saveConfig()
end

function appConfig:getAvatarAddress()
  return userName.."/avatar.png", system.DocumentsDirectory
end

function appConfig:getFlagCheckStatus ()
  return flagCheckStatus
end

function appConfig:setFlagCheckStatus (flag)
  flagCheckStatus = flag
end

function appConfig:getUserInfo(userId)
  for i = 1, #users do
    if users[i].id == userId then
      return users[i]
    end
  end
  return nil
end

-------------------------------------------------------------
-- CHECKING FOR USERS
-------------------------------------------------------------
if #users < 1 then
  appConfig:saveUser("default", "default", "boy", 1, 1, 1, nil, true)
end

return appConfig
