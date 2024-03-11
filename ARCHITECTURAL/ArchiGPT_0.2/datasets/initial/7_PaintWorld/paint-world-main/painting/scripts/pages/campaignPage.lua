local widget = require("widget")
local campaign = {}
local properties = {}
function campaign:render(params)
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)


  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.campaign.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
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

  local board = display.newImage("images/about/board.png")
  board.x = centerX
  board.y = centerY
  board.width = board.width * .4
  board.height = board.height * .4
  table.insert(properties, board)

  local options = {
    x = board.x,
    y = board.y - 10,
    width = board.width * 0.9,
    height = board.height * 0.85,
    horizontalScrollDisabled = true,
    verticalScrollDisabled = false,
    hideBackground = true,
    bottomPadding = 20,
    topPadding = 50
  }
  local scroll = widget.newScrollView( options )
  table.insert(properties, scroll)

  -- local logo = display.newImage("images/campaign/dorsa.png")
  -- logo.width = 280
  -- logo.height = 204
  -- logo.x = scroll.width/2 + 130
  -- logo.y = 50
  -- table.insert(properties, logo)
  -- scroll:insert(logo)

  -- local darman = display.newImage("images/campaign/darman.png")
  -- darman.width = 280
  -- darman.height = 210
  -- darman.x = scroll.width/2 - 130
  -- darman.y = 50
  -- table.insert(properties, darman)
  -- scroll:insert(darman)

  -- options ={
  -- text = texts.about.content,
  -- x = 0,
  -- y = 155,
  -- width = scroll.width,
  -- font = fonts.lalezar,
  -- fontSize = 32,
  -- align = "right"
  -- }
  local content = display.newImage("images/campaign/text.jpg")
  content.y = 0
  table.insert(properties, content)
  content.anchorX, content.anchorY = 0, 0
  scroll:insert(content)
  content:setFillColor(1, 1, 1, 1)


  renderSoundButton()

  local function buttonTouchHandlers(event)
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
  picture:addEventListener("touch", buttonTouchHandlers)
  backButton:addEventListener("touch", buttonTouchHandlers)

end
function campaign:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
end
return campaign
