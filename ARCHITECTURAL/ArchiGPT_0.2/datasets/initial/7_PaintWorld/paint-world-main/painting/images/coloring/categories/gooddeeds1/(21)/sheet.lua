--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:4c669aed34ef9a2caf7f15966bbac323:97b9753f032669ad7d9dcf89307378fd:cf8ab4992190eb44f97f06311ef326d7$
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
            x=599,
            y=345,
            width=64,
            height=72,

            sourceX = 270,
            sourceY = 491,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=569,
            y=151,
            width=64,
            height=78,

            sourceX = 356,
            sourceY = 500,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=555,
            y=257,
            width=104,
            height=86,

            sourceX = 307,
            sourceY = 535,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=277,
            y=419,
            width=64,
            height=92,

            sourceX = 466,
            sourceY = 409,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=649,
            y=419,
            width=18,
            height=36,

            sourceX = 459,
            sourceY = 424,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=465,
            width=36,
            height=40,

            sourceX = 757,
            sourceY = 514,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=633,
            y=1,
            width=34,
            height=54,

            sourceX = 162,
            sourceY = 508,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=465,
            y=391,
            width=132,
            height=100,

            sourceX = 189,
            sourceY = 511,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=599,
            y=419,
            width=48,
            height=56,

            sourceX = 327,
            sourceY = 507,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=127,
            y=221,
            width=212,
            height=196,

            sourceX = 182,
            sourceY = 339,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=127,
            y=419,
            width=148,
            height=82,

            sourceX = 255,
            sourceY = 284,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=399,
            y=151,
            width=168,
            height=104,

            sourceX = 249,
            sourceY = 226,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=503,
            y=257,
            width=50,
            height=132,

            sourceX = 450,
            sourceY = 495,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=1,
            y=1,
            width=124,
            height=462,

            sourceX = 390,
            sourceY = 10,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=399,
            y=1,
            width=232,
            height=148,

            sourceX = 529,
            sourceY = 463,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=127,
            y=1,
            width=270,
            height=218,

            sourceX = 494,
            sourceY = 293,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=341,
            y=257,
            width=160,
            height=118,

            sourceX = 526,
            sourceY = 187,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=343,
            y=377,
            width=120,
            height=114,

            sourceX = 531,
            sourceY = 205,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 668,
    sheetContentHeight = 512
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
