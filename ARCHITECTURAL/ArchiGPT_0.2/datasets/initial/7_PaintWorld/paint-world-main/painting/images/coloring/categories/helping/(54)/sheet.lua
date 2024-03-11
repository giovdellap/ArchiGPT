--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:ab6ca680fd8a01910d6afa394b33feec:d5ae5cdf233b6e86c2e5f7a09175b812:cf8ab4992190eb44f97f06311ef326d7$
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
            x=583,
            y=761,
            width=144,
            height=120,

            sourceX = 182,
            sourceY = 326,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=583,
            y=511,
            width=226,
            height=248,

            sourceX = 163,
            sourceY = 424,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=243,
            y=859,
            width=80,
            height=44,

            sourceX = 446,
            sourceY = 441,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=673,
            y=197,
            width=34,
            height=22,

            sourceX = 487,
            sourceY = 467,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=811,
            y=573,
            width=112,
            height=110,

            sourceX = 511,
            sourceY = 396,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=895,
            y=1,
            width=62,
            height=96,

            sourceX = 576,
            sourceY = 417,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=243,
            y=813,
            width=48,
            height=44,

            sourceX = 688,
            sourceY = 517,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=811,
            y=445,
            width=134,
            height=126,

            sourceX = 670,
            sourceY = 308,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=913,
            y=685,
            width=70,
            height=98,

            sourceX = 711,
            sourceY = 107,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=729,
            y=761,
            width=124,
            height=130,

            sourceX = 511,
            sourceY = 9,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=895,
            y=99,
            width=74,
            height=76,

            sourceX = 505,
            sourceY = 310,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=1,
            y=813,
            width=240,
            height=90,

            sourceX = 369,
            sourceY = 284,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=855,
            y=785,
            width=96,
            height=118,

            sourceX = 628,
            sourceY = 421,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=811,
            y=685,
            width=100,
            height=70,

            sourceX = 696,
            sourceY = 531,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=673,
            y=1,
            width=220,
            height=194,

            sourceX = 661,
            sourceY = 370,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=231,
            width=298,
            height=580,

            sourceX = 604,
            sourceY = 92,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=301,
            y=511,
            width=280,
            height=344,

            sourceX = 410,
            sourceY = 116,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=1,
            y=1,
            width=670,
            height=228,

            sourceX = 227,
            sourceY = 444,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=301,
            y=231,
            width=428,
            height=278,

            sourceX = 0,
            sourceY = 293,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=731,
            y=197,
            width=218,
            height=246,

            sourceX = 0,
            sourceY = 426,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 984,
    sheetContentHeight = 904
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
    ["17"] = 17,
    ["18"] = 18,
    ["19"] = 19,
    ["20"] = 20,
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
