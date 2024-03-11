local lfs = require ("lfs")
local widget = require("widget")
local json = require("json")
local users = {}
local properties = {}
local imageUrls = images
local images = {}
local scrollView, usersList, userName, renderUsers, deleteUser
local showBlur, closeBlur, delayCloseBlur, showTextOnBlur

--------------------------------------------------------------------------------
-- lLISTENERS
--------------------------------------------------------------------------------
local function iconListener( event )
  if ( event.phase == "moved" ) then
    local dx = math.abs( event.x - event.xStart )
    if ( dx > 5 ) then
      scrollView:takeFocus( event )
    end
  elseif ( event.phase == "ended" ) then
    audio.stop(clickSoundCannel)
    audio.play(clickSound, clickPlayOptions)
    appConfig:setUserName(event.target.id)
    navigate("home")
  end
  return true
end

function cancelSubscribeIrancell()
  local function netCancelSubscribeListener( event )
    -------------------------------------------------------------------------- NEEDS CHANGING
    print(event.status)
    if event.status == 200 then
        appConfig:eraseUsers()
        appConfig:eraseParent()
        navigate("slidSwiper1")
    else
      showTextOnBlur(texts.parent.wrongInformation)
      timer.performWithDelay( 1500,delayCloseBlur, 0)
    end
  end
  local tempUrl = { urls.serviceCheckSubscribIrancell, appConfig:getToken(), ":cancel?access_token=", accessToken}
  print(table.concat(tempUrl))
  network.request( table.concat(tempUrl), "GET", netCancelSubscribeListener)
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
    if event.target.name == "newUser" then
      navigate("newUser")
    elseif event.target.name == "back" then
      back()
    elseif event.target.name == "cancelSub" then
      if appConfig:getTypeOperator() == texts.typeOperator.mci then
        native.showAlert( messages.alerts.title, messages.alerts.noCancelSub, { messages.alerts.close } )
      else
        print("irancellllllll")
        showBlur()
        showTextOnBlur(texts.parent.pleaseWait)
        cancelSubscribeIrancell()
      end
    elseif event.target.name == "editUser" then
      navigate("newUser", event.target.id)
    elseif event.target.name == "deleteUser" then
      local id = event.target.id
      alert({
        textImage = imageUrls.alerts.deleteUser,
        isYesNo = true,
        callBack = function(index)
          if(index == 1) then
            appConfig:deleteUser(id, renderUsers)
          elseif (index == 2) then
          end
        end
      }
    )
  end
  event.target.isFocus = false
  display.getCurrentStage():setFocus(nil)
end
return true
end

--------------------------------------------------------------------------------
-- DELETE USERS LIST
--------------------------------------------------------------------------------
deleteUsersList = function()
for i = 1, #images do
  display.remove(images[i].text)
  display.remove(images[i].delete)
  display.remove(images[i].edit)
  images[i].text = nil
  images[i].delete = nil
  images[i].edit = nil
  display.remove(images[i])
  images[i] = nil
end

images = {}
if scrollView then
  display.remove(scrollView)
  scrollView = nil
end
end

--------------------------------------------------------------------------------
-- RENDER USERS
--------------------------------------------------------------------------------
renderUsers = function()
usersList = appConfig:getUsers()
userName = appConfig:getUserName()
deleteUsersList()
if appConfig:hasUser() then

  scrollView = widget.newScrollView
  {
    width = 900,
    height = 320,
    verticalScrollDisabled = true,
    hideBackground = true
  }
  scrollView.x = display.contentCenterX
  scrollView.y = display.contentCenterY - 50

  local currentX = 143
  local width = 240
  local height = 200
  local y = 100
  local margin = 15
  for i = 1, #usersList do

    images[i] = display.newRect(currentX, y, width, height)
    images[i]:setStrokeColor(225 / 255, 224 / 255, 221 / 255)
    if usersList[i].id == userName then
      images[i]:setStrokeColor(0, 1, 0, 1)
    end
    images[i].strokeWidth = 5
    local paint = {
      type = "image",
      filename = usersList[i].id.."/avatar.png",
      baseDir = system.DocumentsDirectory
    }
    images[i].fill = paint
    local options = {
      text = usersList[i].name,
      width = images[i].width,
      height = 50,
      font = fonts.lalezar,
      fontSize = 40,
      x = images[i].x,
      y = images[i].y + images[i].height / 2 + 20,
      align = "center"
    }
    images[i].text = display.newText(options)
    images[i].text:setFillColor(255 / 255, 255 / 255, 225 / 255, 1)
    images[i].delete = display.newImageRect("images/users/delete.png", 50, 50)
    images[i].delete.x = images[i].x - 35
    images[i].delete.y = images[i].y + images[i].height / 2 + 75
    images[i].edit = display.newImageRect("images/users/edit.png", 50, 50)
    images[i].edit.x = images[i].x + 35
    images[i].edit.y = images[i].y + images[i].height / 2 + 75
    images[i].delete.id = usersList[i].id
    images[i].edit.id = usersList[i].id
    images[i].delete.name = "deleteUser"
    images[i].edit.name = "editUser"
    images[i].id = usersList[i].id
    currentX = currentX + images[i].width + margin
    scrollView:insert(images[i])
    scrollView:insert(images[i].text)
    scrollView:insert(images[i].delete)
    scrollView:insert(images[i].edit)
    images[i]:addEventListener("touch", iconListener)
    images[i].delete:addEventListener("touch", buttonTouchHandlers)
    images[i].edit:addEventListener("touch", buttonTouchHandlers)
  end
else
  local options = {
    text = texts.users.noUser,
    x = centerX,
    y = centerY,
    font = fonts.lalezar,
    fontSize = 56
  }
  local noUser = display.newText(options)
  table.insert(properties, noUser)
end
end


--------------------------------------------------------------------------------
-- RENDER
--------------------------------------------------------------------------------
function users:render()
local background = display.newImage( "images/mainBackGround.jpg")
background.x = backgroundX
background.y = backgroundY
table.insert(properties, background)

local pageTitleBackground = display.newImage("images/titleBackGround.png")
pageTitleBackground.x = pageTitleBackgroundX
pageTitleBackground.y = pageTitleBackgroundY
table.insert(properties, pageTitleBackground)

local pageTitle = display.newText(texts.users.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
table.insert(properties, pageTitle)

local cheetah = display.newImage("images/cheetah.png")
cheetah.x = cheetahX
cheetah.y = cheetahY
table.insert(properties, cheetah)

local newUserBackground = display.newRoundedRect( centerX - 150, centerY + screenHeight / 5, 220, 90, 10)
newUserBackground:setFillColor(0 / 255, 169 / 255, 126 / 255)
newUserBackground.name = "newUser"
options = {
  text = texts.users.newUser,
  x = newUserBackground.x,
  y = newUserBackground.y,
  font = fonts.lalezar,
  fontSize = 46
}

local newUser = display.newText(options)
table.insert(properties, newUserBackground)
table.insert(properties, newUser)

local cancelSubBackground = display.newRoundedRect( centerX + 150, centerY + screenHeight / 5, 220, 90, 10)
cancelSubBackground:setFillColor(205 / 255, 24 / 255, 71 / 255)
cancelSubBackground.name = "cancelSub"
options = {
  text = texts.users.cancelSub,
  x = cancelSubBackground.x,
  y = cancelSubBackground.y,
  font = fonts.lalezar,
  fontSize = 46
}

local cancelSub = display.newText(options)
table.insert(properties, cancelSubBackground)
table.insert(properties, cancelSub)

local backButton = display.newImageRect( "images/back.png", 125, 125)
backButton.x = backButtonX
backButton.y = backButtonY
backButton.name = "back"
table.insert(properties, backButton)


renderUsers()
renderSoundButton()
newUserBackground:addEventListener("touch", buttonTouchHandlers)
cancelSubBackground:addEventListener("touch", buttonTouchHandlers)
backButton:addEventListener("touch", buttonTouchHandlers)
end

---------------------------------------------------------------
function delayCloseBlur()
  closeBlur()
end
------------------------------------------------------------------------------
------------------------------------------------------------------------------
closeBlur = function(triggerCallBack)
  local function removeGroup(cb1)
    display.remove(blur)
    blur = nil
    if text then
      display.remove(text)
      text = nil
    end
    if triggerCallBack then
      callBackFunc()
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
showTextOnBlur = function(newText)
  print(newText)
  if text then
    print("logging")
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
  print(text)
  text:setFillColor(1, 1, 1, 1)
end
------------------------------------------------------------------------------
--------------------------------------------------------------------------
function users:close()
closeSoundButton()
deleteUsersList()
if #properties > 0 then
  for n = #properties, 1, - 1 do
    display.remove(properties[n])
    properties[n] = nil
  end
end
end
return users
