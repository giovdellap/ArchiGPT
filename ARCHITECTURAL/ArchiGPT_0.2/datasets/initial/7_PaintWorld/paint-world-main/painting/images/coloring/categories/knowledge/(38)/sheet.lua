--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:2c041b147e87f006e07a6ca0aebe8694:70a21e2f8957219b4f9279be3eb82d9f:cf8ab4992190eb44f97f06311ef326d7$
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
            x=293,
            y=595,
            width=74,
            height=38,

            sourceX = 577,
            sourceY = 293,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=293,
            y=635,
            width=64,
            height=42,

            sourceX = 420,
            sourceY = 306,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=225,
            y=595,
            width=66,
            height=46,

            sourceX = 346,
            sourceY = 285,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=225,
            y=643,
            width=42,
            height=30,

            sourceX = 220,
            sourceY = 336,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=227,
            y=351,
            width=120,
            height=110,

            sourceX = 198,
            sourceY = 168,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=227,
            y=463,
            width=110,
            height=130,

            sourceX = 567,
            sourceY = 11,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=595,
            width=222,
            height=84,

            sourceX = 250,
            sourceY = 443,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=351,
            width=224,
            height=242,

            sourceX = 134,
            sourceY = 245,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=1,
            width=330,
            height=348,

            sourceX = 419,
            sourceY = 103,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 368,
    sheetContentHeight = 680
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
