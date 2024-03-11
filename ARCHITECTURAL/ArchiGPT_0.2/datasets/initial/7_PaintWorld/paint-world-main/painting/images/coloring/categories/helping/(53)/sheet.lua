--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:94cf139d30a5f77d97e6a8894689e013:9270dd8212168cf136dd049327cd18e2:cf8ab4992190eb44f97f06311ef326d7$
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
            x=1,
            y=1,
            width=680,
            height=276,

            sourceX = 0,
            sourceY = 396,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=279,
            width=494,
            height=188,

            sourceX = 93,
            sourceY = 365,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=979,
            y=349,
            width=80,
            height=122,

            sourceX = 66,
            sourceY = 286,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=871,
            y=207,
            width=174,
            height=140,

            sourceX = 339,
            sourceY = 28,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=817,
            y=431,
            width=50,
            height=54,

            sourceX = 422,
            sourceY = 206,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=869,
            y=453,
            width=50,
            height=28,

            sourceX = 528,
            sourceY = 213,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=921,
            y=453,
            width=40,
            height=40,

            sourceX = 612,
            sourceY = 178,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=871,
            y=419,
            width=74,
            height=32,

            sourceX = 794,
            sourceY = 308,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=871,
            y=349,
            width=106,
            height=68,

            sourceX = 755,
            sourceY = 320,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=691,
            y=275,
            width=178,
            height=154,

            sourceX = 670,
            sourceY = 24,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=683,
            y=189,
            width=166,
            height=84,

            sourceX = 497,
            sourceY = 138,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=691,
            y=431,
            width=124,
            height=44,

            sourceX = 696,
            sourceY = 450,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=929,
            y=1,
            width=146,
            height=128,

            sourceX = 683,
            sourceY = 336,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=683,
            y=1,
            width=244,
            height=186,

            sourceX = 618,
            sourceY = 164,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=497,
            y=279,
            width=192,
            height=196,

            sourceX = 349,
            sourceY = 141,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=929,
            y=131,
            width=146,
            height=74,

            sourceX = 371,
            sourceY = 314,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 1076,
    sheetContentHeight = 494
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
    ["12"] = 12,
    ["13"] = 13,
    ["14"] = 14,
    ["15"] = 15,
    ["16"] = 16,
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
