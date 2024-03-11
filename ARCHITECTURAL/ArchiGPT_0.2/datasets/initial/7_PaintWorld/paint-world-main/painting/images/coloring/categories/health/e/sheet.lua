--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:34ac69a4ae3d6b4f968ab4c1ba36e303:0ef4ac297cef6dea50c1c067ac41af2f:cf8ab4992190eb44f97f06311ef326d7$
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
            x=143,
            y=435,
            width=84,
            height=130,

            sourceX = 429,
            sourceY = 500,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=143,
            y=567,
            width=80,
            height=78,

            sourceX = 442,
            sourceY = 428,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=185,
            width=176,
            height=248,

            sourceX = 394,
            sourceY = 191,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=1,
            y=435,
            width=140,
            height=138,

            sourceX = 414,
            sourceY = 68,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1,
            y=1,
            width=224,
            height=182,

            sourceX = 368,
            sourceY = 35,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=115,
            y=647,
            width=114,
            height=80,

            sourceX = 547,
            sourceY = 239,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=179,
            y=223,
            width=26,
            height=34,

            sourceX = 632,
            sourceY = 217,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=207,
            y=223,
            width=20,
            height=28,

            sourceX = 646,
            sourceY = 259,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=647,
            width=112,
            height=96,

            sourceX = 304,
            sourceY = 198,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=179,
            y=185,
            width=36,
            height=36,

            sourceX = 320,
            sourceY = 175,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=207,
            y=253,
            width=20,
            height=26,

            sourceX = 297,
            sourceY = 224,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 230,
    sheetContentHeight = 744
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
    ["9"] = 9,
    ["10"] = 10,
    ["11"] = 11,
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
