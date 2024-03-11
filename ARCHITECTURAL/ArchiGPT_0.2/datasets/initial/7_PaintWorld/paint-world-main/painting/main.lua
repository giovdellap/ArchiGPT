buildingCategories = false
---------------------------------------------------------
centerX = display.contentCenterX
centerY = display.contentCenterY
screenTop = display.screenOriginY
screenLeft = display.screenOriginX
screenBottom = display.screenOriginY + display.actualContentHeight
screenRight = display.screenOriginX + display.actualContentWidth
screenWidth = screenRight - screenLeft
screenHeight = screenBottom - screenTop
canvasWidth = 960
canvasHeight = 672
backgroundX = display.contentCenterX
backgroundY = display.contentCenterY
backButtonX = display.contentCenterX - 390
backButtonY = display.contentHeight - 100
cheetahX = display.actualContentWidth - 145
cheetahY = display.actualContentHeight - 145
soundButtonX = screenRight - 0.1 * screenWidth
soundButtonY = screenTop + 0.14 * screenHeight
pageTitleX = display.contentCenterX
pageTitleY = 67
pageTitleBackgroundX = display.contentCenterX
pageTitleBackgroundY = 80
clickResizeFactor = 0.95
------------------------------------------------------------
local lfs = require("lfs")
require("scripts.general.helpers")
require("scripts.general.alert")
require("scripts.general.sound")
require("scripts.general.appendix")
appConfig = require("scripts.general.appConfig")
products = require("scripts.products.products")
local paint = require("scripts.paint.paintPage")
local home = require("scripts.pages.homePage")
local selected = require("scripts.selected.selectedPage")
local selectedList = require("scripts.selected.selectedListPage")
local showSelectedPicture = require("scripts.selected.showSelectedPicturePage")
local gallery = require("scripts.gallery.galleryPage")
local showSavedPicture = require("scripts.gallery.showSavedPicturePage")
local coloringCategoriesList = require("scripts.coloring.coloringCategoriesListPage")
local coloringCategory = require("scripts.coloring.coloringCategoryPage")
local coloringPage = require("scripts.coloring.coloringPage")
local about = require("scripts.pages.aboutPage")
local competitionHistory = require("scripts.pages.competitionHistoryPage")
local parentIrancell = require("scripts.user.parentIrancellPage")
local parentMCI = require("scripts.user.parentMciPage")
local selectOperator = require("scripts.user.selectOperator")
local submitFromMessage = require("scripts.user.submitFromMessagePage")
local submitMciInApp = require("scripts.user.submitMciInAppPage")
local users = require("scripts.user.usersPage")
local newUser = require("scripts.user.newUserPage")
local buyProductPage = require("scripts.products.buyProductPage")
local productsPage = require("scripts.products.productsPage")
local dorsa = require("scripts.pages.dorsa")
local campaign = require("scripts.pages.campaignPage")
local slidSwiper0 = require("scripts.pages.slidSwiper0")
local slidSwiper1 = require("scripts.pages.slidSwiper1")
local slidSwiper2 = require("scripts.pages.slidSwiper2")
local slidSwiper3 = require("scripts.pages.slidSwiper3")

display.setDefault("background", 1, 1, 1)

-- hide status bar
display.setStatusBar( display.HiddenStatusBar )
native.setProperty( "androidSystemUiVisibility", "immersiveSticky" )

-----------------------------------------------------------------
-- NAVIGATION ROUTES SHOULD BE ADDED HERE
-----------------------------------------------------------------
local routes = {}
routes["home"] = home
routes["paint"] = paint
routes["selected"] = selected
routes["selectedList"] = selectedList
routes["showSelectedPicture"] = showSelectedPicture
routes["gallery"] = gallery
routes["showSavedPicture"] = showSavedPicture
routes["coloringCategoriesList"] = coloringCategoriesList
routes["coloringCategory"] = coloringCategory
routes["coloringPage"] = coloringPage
routes["login"] = login
routes["about"] = about
routes["parentIrancell"] = parentIrancell
routes["parentMCI"] = parentMCI
routes["selectOperator"] = selectOperator
routes["submitFromMessage"] = submitFromMessage
routes["submitMciInApp"] = submitMciInApp
routes["users"] = users
routes["newUser"] = newUser
routes["buyProduct"] = buyProductPage
routes["products"] = productsPage
routes["competitionHistory"] =competitionHistory
routes["dorsa"] = dorsa
routes["campaign"] = campaign
routes["slidSwiper0"] = slidSwiper0
routes["slidSwiper1"] = slidSwiper1
routes["slidSwiper2"] = slidSwiper2
routes["slidSwiper3"] = slidSwiper3
-----------------------------------------------------------------
-----------------------------------------------------------------
local historyRoutes = {}
local currentRoute = nil

function navigate(route, params)
  if currentRoute then
    routes[currentRoute.route].close()
  end
  local routeObject = {}
  routeObject.route = route
  routeObject.params = params
  currentRoute = routeObject
  table.insert(historyRoutes, routeObject)
  routes[route]:render(params)
  local foo = params or {}
  -- print(route, table.tostring(foo))
end

function back(params)
  if #historyRoutes > 1 then
    routes[historyRoutes[#historyRoutes].route]:close()
    table.remove(historyRoutes)
    currentRoute = historyRoutes[#historyRoutes]
    if params then
      for k,v in pairs( params ) do
        currentRoute.params[k] = v
      end
      for k,v in ipairs( params ) do
        currentRoute.params[k] = v
      end
    end
    routes[currentRoute.route]:render(currentRoute.params)
  end
end

function getCurrentRoute()
  return currentRoute
end
----------------------------------------------------------------------------

local function onKeyEvent( event )
  if "back" == event.keyName then
    return true
  end
end
Runtime:addEventListener( "key", onKeyEvent )
-----------------------------------------------------------------
-- CHECKING FOR USERS
-----------------------------------------------------------------
navigate("home")
-- navigate("showSavedPicture", {name="041718170421",image="041718170319/savedPictures/041718170421/main.png",directoryAddress="041718170319/savedPictures/041718170421",dir=system.DocumentsDirectory})
