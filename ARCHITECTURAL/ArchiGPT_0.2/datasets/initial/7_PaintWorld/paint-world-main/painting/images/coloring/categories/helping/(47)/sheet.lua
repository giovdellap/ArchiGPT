--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:2316fec887f2c3781963939248357da6:25d09de8ce2e2d960634711b536eb781:cf8ab4992190eb44f97f06311ef326d7$
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
            x=535,
            y=175,
            width=148,
            height=168,

            sourceX = 167,
            sourceY = 41,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=481,
            y=877,
            width=64,
            height=66,

            sourceX = 346,
            sourceY = 194,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=623,
            y=345,
            width=60,
            height=124,

            sourceX = 402,
            sourceY = 127,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=535,
            y=345,
            width=86,
            height=132,

            sourceX = 648,
            sourceY = 212,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=319,
            y=773,
            width=160,
            height=170,

            sourceX = 546,
            sourceY = 41,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=535,
            y=1,
            width=138,
            height=172,

            sourceX = 608,
            sourceY = 285,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=613,
            y=877,
            width=52,
            height=28,

            sourceX = 512,
            sourceY = 559,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=547,
            y=877,
            width=64,
            height=40,

            sourceX = 680,
            sourceY = 559,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=595,
            width=316,
            height=356,

            sourceX = 429,
            sourceY = 208,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=535,
            y=479,
            width=120,
            height=84,

            sourceX = 621,
            sourceY = 281,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=319,
            y=595,
            width=194,
            height=176,

            sourceX = 519,
            sourceY = 112,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=515,
            y=573,
            width=160,
            height=302,

            sourceX = 470,
            sourceY = 289,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=321,
            y=1,
            width=212,
            height=570,

            sourceX = 596,
            sourceY = 20,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=1,
            y=1,
            width=318,
            height=592,

            sourceX = 97,
            sourceY = 13,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 684,
    sheetContentHeight = 952
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
