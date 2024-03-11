--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:80ae98218e3dce71ec7fd4648f7eaf58:afb1d8732b27fc26f8de0154b74b9e95:cf8ab4992190eb44f97f06311ef326d7$
--
-- local sheetInfo = require("mysheet")
-- local myImageSheet = graphics.newImageSheet( "mysheet.png", sheetInfo:getSheet() )
-- local sprite = display.newSprite( myImageSheet , {frames={sheetInfo:getFrameIndex("sprite")}} )
--

local SheetInfo = {}

SheetInfo.sheet =
{
    frames = {
    
        {
            -- 1
            x=405,
            y=307,
            width=28,
            height=26,

            sourceX = 449,
            sourceY = 367,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=557,
            y=243,
            width=202,
            height=132,

            sourceX = 383,
            sourceY = 333,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=405,
            y=243,
            width=150,
            height=62,

            sourceX = 383,
            sourceY = 210,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=235,
            y=243,
            width=168,
            height=94,

            sourceX = 372,
            sourceY = 242,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1,
            y=1,
            width=232,
            height=358,

            sourceX = 523,
            sourceY = 49,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=235,
            y=1,
            width=524,
            height=240,

            sourceX = 207,
            sourceY = 372,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=361,
            width=246,
            height=128,

            sourceX = 477,
            sourceY = 315,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=249,
            y=339,
            width=238,
            height=142,

            sourceX = 211,
            sourceY = 305,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 760,
    sheetContentHeight = 490
}

SheetInfo.frameIndex =
{

    ["1"] = 1,
    ["2"] = 2,
    ["3"] = 3,
    ["4"] = 4,
    ["5"] = 5,
    ["6"] = 6,
    ["7"] = 7,
    ["8"] = 8,
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
