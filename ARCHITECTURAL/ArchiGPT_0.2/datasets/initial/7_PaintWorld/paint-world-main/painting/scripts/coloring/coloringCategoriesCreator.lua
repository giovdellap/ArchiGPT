local json = require("json")
coloringCategoriesNames = {
  {
    name = "baddeeds",
    title = "اعمال ناپسند"
  },
  {
    name = "quran",
    title = "نماز و قرآن",
  },
  {
    name = "gooddeeds",
    title = "اعمال نیکو 1"
  },
  {
    name = "gooddeeds1",
    title = "اعمال نیکو 2"
  },
  {
    name = "health",
    title = "سلامتی",
  },
  {
    name = "knowledge",
    title = "علم و دانش"
  },
  {
    name = "helping",
    title = "کمک کردن"
  },
  {
    name = "Photos",
    title = "تست"
  },
}

if buildingCategories then
  coloringCategories = {}
  local temp = {}
  local j = 1
  for i = 1, #coloringCategoriesNames do
    local doc_path = system.pathForFile( "", system.ResourceDirectory)
    if not doc_path then
      doc_path = ""
    end
    coloringCategories[j] = {}

    for file in lfs.dir(doc_path.."/images/coloring/categories/"..coloringCategoriesNames[i].name) do
      local check = true
      if file ~= "." and file ~= ".." then
        local thumbnailPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/thumbnail.png")
        local soundPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/sound.mp3")
        local poemPath = system.pathForFile( "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/poem.mp3" )
        local addressPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/main.png")
        local bitmapPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/bitmap.json")
        local completedPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/completed.png")
        local sheetObjectPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/sheet.lua")
        local sheetPath = system.pathForFile("images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/sheet.png")
        if not addressPath then
          addressPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/main.jpg"
        else
          addressPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/main.png"
        end
        if not thumbnailPath then
          thumbnailPath = addressPath
        else
          thumbnailPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/thumbnail.png"
        end
        if soundPath then
          soundPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/sound.mp3"
        end
        if poemPath then
          poemPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/poem.mp3"
        end
        if bitmapPath then
          bitmapPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/bitmap.json"
        end
        if completedPath then
          completedPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/completed.png"
        end
        if sheetObjectPath then
          sheetObjectPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/sheet"
        end
        if sheetPath then
          sheetPath = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file.."/sheet.png"
        end


        table.insert(coloringCategories[j], {
          address = "images/coloring/categories/"..coloringCategoriesNames[i].name.."/"..file,
          thumbnail = thumbnailPath,
          sound = soundPath,
          bitmap = bitmapPath,
          completed = completedPath,
          poem = poemPath,
          sheet = sheetPath,
          sheetObject = sheetObjectPath,
          name = file
        })
      end
    end

    if #coloringCategories[j] > 0 then
      coloringCategories[j].name = coloringCategoriesNames[j].name
      coloringCategories[j].title = coloringCategoriesNames[j].title
      if coloringCategories[j][2].thumbnail then
        coloringCategories[j].thumbnail = coloringCategories[j][2].thumbnail
      else
        coloringCategories[j].thumbnail = coloringCategories[j][1].thumbnail
      end
      j = j + 1
    else
      table.insert(temp, i)
      table.remove(coloringCategories, j)
    end
  end
  local text = json.encode(coloringCategories)
  local output = string.format("%s %q", "coloringCategoriesJSON = ", text)
  local path = system.pathForFile( "scripts/coloring/coloringCategoriesJSON.lua", system.ResourceDirectory)
  local file = io.open(path, "w")
  file:write(output)
  io.close(file)
else

  ------------------------------------------------------------------------------
  require("scripts.coloring.coloringCategoriesJSON")
  tempColoringCategories = json.decode( coloringCategoriesJSON )
  coloringCategories = {}
  for i = 1, #tempColoringCategories do
    local tempCategory = {}
    for k, v in pairs( tempColoringCategories[i] ) do
      if k == "thumbnail" or k == "title" or k == "name" then
        tempCategory[k] = v
      else

        tempCategory[tonumber( k )] = v
      end

    end
    table.insert(coloringCategories, tempCategory)
  end
end

--------------------------------------------------------------------------------
--otherFunctions
--------------------------------------------------------------------------------
function getColoringFile(category, file)
  for i = 1, #coloringCategories do
    if coloringCategories[i].name == category then
      for j = 1, #coloringCategories[i] do
        if coloringCategories[i][j].name == file then
          return coloringCategories[i][j]
        end
      end
    end
  end
  return nil
end

function setColoringFile(category, file, newData)
  for i = 1, #coloringCategories do
    if coloringCategories[i].name == category then
      for j = 1, #coloringCategories[i] do
        if coloringCategories[i][j].name == file then
          coloringCategories[i][j] = newData
          return true
        end
      end
    end
  end
  return false
end
local flag = false

function addNewFilesToColoringCategory()
  local doc_path = system.pathForFile("newColoringCategories", system.DocumentsDirectory)
  flag = lfs.chdir(doc_path)
  if flag then
    for category in lfs.dir(doc_path) do
      if category ~= "." and category ~= ".." then
        for file in lfs.dir(doc_path.."/"..category) do
          if file ~= "." and file ~= ".." then
            local bitmapPath = system.pathForFile( "newColoringCategories/"..category.."/"..file.."/bitmap.json", system.DocumentsDirectory )
            local bitmapFile = io.open(bitmapPath, "r")
            if bitmapFile then
              local address = "newColoringCategories/"..category.."/"..file.."/"
              setColoringFile(category, file, {
                address = "newColoringCategories/"..category.."/"..file,
                thumbnail = address.."thumbnail.png",
                sound = address.."sound.mp3",
                bitmap = address.."bitmap.json",
                completed = address.."completed.png",
                poem = address.."poem.mp3",
                sheet = address.."sheet.png",
                sheetObject = "images/coloring/categories/"..category.."/"..file.."/sheet",
                name = file,
                downloaded = true
              })
            end
          end

        end --second for
      end
    end --first for
  end --first flag

end

addNewFilesToColoringCategory()
