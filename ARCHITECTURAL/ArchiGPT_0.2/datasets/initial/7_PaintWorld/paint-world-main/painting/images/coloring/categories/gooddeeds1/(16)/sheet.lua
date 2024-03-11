--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:fd30c537c96578862bac9f977c61590a:1cbf11a4b1049205578f015ca975cef1:cf8ab4992190eb44f97f06311ef326d7$
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
            x=189,
            y=1173,
            width=46,
            height=32,

            sourceX = 227,
            sourceY = 577,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=1063,
            width=130,
            height=82,

            sourceX = 25,
            sourceY = 518,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=219,
            y=221,
            width=34,
            height=30,

            sourceX = 268,
            sourceY = 519,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=157,
            y=743,
            width=94,
            height=76,

            sourceX = 60,
            sourceY = 430,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=213,
            y=437,
            width=40,
            height=32,

            sourceX = 153,
            sourceY = 368,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=233,
            y=1,
            width=12,
            height=24,

            sourceX = 227,
            sourceY = 353,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=1147,
            width=124,
            height=130,

            sourceX = 125,
            sourceY = 299,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=437,
            width=210,
            height=142,

            sourceX = 123,
            sourceY = 386,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=157,
            y=821,
            width=90,
            height=80,

            sourceX = 106,
            sourceY = 236,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=149,
            y=961,
            width=98,
            height=184,

            sourceX = 95,
            sourceY = 217,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=1,
            y=695,
            width=154,
            height=244,

            sourceX = 755,
            sourceY = 333,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=1,
            y=221,
            width=216,
            height=214,

            sourceX = 514,
            sourceY = 351,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=1,
            y=941,
            width=146,
            height=120,

            sourceX = 396,
            sourceY = 127,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=1,
            y=581,
            width=176,
            height=112,

            sourceX = 385,
            sourceY = 80,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=157,
            y=903,
            width=82,
            height=56,

            sourceX = 408,
            sourceY = 62,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=189,
            y=1207,
            width=44,
            height=42,

            sourceX = 467,
            sourceY = 340,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=213,
            y=471,
            width=40,
            height=12,

            sourceX = 551,
            sourceY = 349,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=179,
            y=581,
            width=66,
            height=160,

            sourceX = 336,
            sourceY = 369,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=127,
            y=1147,
            width=60,
            height=114,

            sourceX = 418,
            sourceY = 407,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=213,
            y=485,
            width=36,
            height=30,

            sourceX = 358,
            sourceY = 526,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=189,
            y=1147,
            width=52,
            height=24,

            sourceX = 436,
            sourceY = 517,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 22
            x=1,
            y=1,
            width=230,
            height=218,

            sourceX = 326,
            sourceY = 211,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 254,
    sheetContentHeight = 1278
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
    ["21"] = 21,
    ["22"] = 22,
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
