--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:3947a7d28e857c481fbc9f3a1eb56465:f0a766a44921d3b826190ceb54ff1166:cf8ab4992190eb44f97f06311ef326d7$
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
            x=149,
            y=881,
            width=60,
            height=44,

            sourceX = 491,
            sourceY = 539,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=87,
            y=881,
            width=60,
            height=46,

            sourceX = 466,
            sourceY = 534,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=149,
            y=443,
            width=64,
            height=172,

            sourceX = 491,
            sourceY = 377,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=1,
            y=1,
            width=198,
            height=374,

            sourceX = 288,
            sourceY = 266,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1,
            y=805,
            width=84,
            height=112,

            sourceX = 491,
            sourceY = 206,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=657,
            width=110,
            height=76,

            sourceX = 478,
            sourceY = 303,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=87,
            y=805,
            width=70,
            height=74,

            sourceX = 310,
            sourceY = 190,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=443,
            width=146,
            height=92,

            sourceX = 414,
            sourceY = 199,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=131,
            y=617,
            width=74,
            height=86,

            sourceX = 497,
            sourceY = 139,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=1,
            y=735,
            width=102,
            height=64,

            sourceX = 410,
            sourceY = 200,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=1,
            y=377,
            width=158,
            height=64,

            sourceX = 302,
            sourceY = 239,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=1,
            y=537,
            width=128,
            height=118,

            sourceX = 456,
            sourceY = 45,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=113,
            y=705,
            width=88,
            height=98,

            sourceX = 476,
            sourceY = 61,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=187,
            y=377,
            width=18,
            height=20,

            sourceX = 485,
            sourceY = 92,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=161,
            y=377,
            width=24,
            height=24,

            sourceX = 521,
            sourceY = 94,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 214,
    sheetContentHeight = 928
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
