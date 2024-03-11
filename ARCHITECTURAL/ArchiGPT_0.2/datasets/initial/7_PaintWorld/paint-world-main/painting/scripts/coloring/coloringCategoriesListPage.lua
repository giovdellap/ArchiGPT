local coloring = {}
local properties = {}
local slider = require("scripts.general.slider")
require("scripts.coloring.coloringCategoriesCreator")
function coloring:render(params)
  local background = display.newImage( "images/mainBackGround.jpg")
  background.x = backgroundX
  background.y = backgroundY
  table.insert(properties, background)

  local pageTitleBackground = display.newImage("images/titleBackGround.png")
  pageTitleBackground.x = pageTitleBackgroundX
  pageTitleBackground.y = pageTitleBackgroundY
  table.insert(properties, pageTitleBackground)

  local pageTitle = display.newText(texts.coloring.pageTitle, pageTitleX, pageTitleY, fonts.lalezar, fonts.pageTitleSize)
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

  function callBackFunc(item)
    navigate("coloringCategory", item)
  end
  local categoriesSlider = slider:newSlider({
    numOfVisibleObjects = 3,
    selectedIndex = 2,
    x = 650,
    y = 400,
    width = 960,
    height = 500,
    callBack = callBackFunc,
    objects = coloringCategories,
    parent = display.currentStage,
    hasTitle = true
  })

  properties.slider = categoriesSlider

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


  -- cheetah:addEventListener("tap",categoriesSlider.foo)
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
      elseif event.target.name == "rightArrow" then
        categoriesSlider:scrollRight()
      elseif event.target.name == "leftArrow" then
        categoriesSlider:scrollLeft()
      end
      event.target.isFocus = false
      display.getCurrentStage():setFocus(nil)
    end
  end
  backButton:addEventListener("touch", buttonTouchHandlers)
  leftArrow:addEventListener("touch", buttonTouchHandlers)
  rightArrow:addEventListener("touch", buttonTouchHandlers)
end

function coloring:close()
  closeSoundButton()
  if #properties > 0 then
    for n = #properties, 1, - 1 do
      display.remove(properties[n])
      properties[n] = nil
    end
  end
  properties.slider:close()
end
return coloring
