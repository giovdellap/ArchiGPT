--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:1092e872eaac3a51760eee27171e01dc:9a7f124d5c8b6c9c046c11fef840a690:cf8ab4992190eb44f97f06311ef326d7$
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
            width=294,
            height=560,

            sourceX = 193,
            sourceY = 24,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=365,
            y=477,
            width=52,
            height=146,

            sourceX = 466,
            sourceY = 101,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=767,
            width=234,
            height=372,

            sourceX = 384,
            sourceY = 193,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=395,
            y=179,
            width=20,
            height=20,

            sourceX = 578,
            sourceY = 297,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=297,
            y=455,
            width=66,
            height=72,

            sourceX = 546,
            sourceY = 270,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=297,
            y=375,
            width=82,
            height=48,

            sourceX = 458,
            sourceY = 273,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=297,
            y=39,
            width=112,
            height=138,

            sourceX = 491,
            sourceY = 339,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=279,
            y=745,
            width=34,
            height=18,

            sourceX = 673,
            sourceY = 561,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=395,
            y=201,
            width=16,
            height=10,

            sourceX = 696,
            sourceY = 579,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=297,
            y=425,
            width=72,
            height=28,

            sourceX = 579,
            sourceY = 563,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=297,
            y=1,
            width=120,
            height=36,

            sourceX = 569,
            sourceY = 526,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=371,
            y=447,
            width=44,
            height=28,

            sourceX = 644,
            sourceY = 479,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=381,
            y=409,
            width=34,
            height=36,

            sourceX = 701,
            sourceY = 481,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=297,
            y=529,
            width=38,
            height=50,

            sourceX = 683,
            sourceY = 326,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=381,
            y=375,
            width=36,
            height=32,

            sourceX = 633,
            sourceY = 270,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=297,
            y=295,
            width=88,
            height=78,

            sourceX = 607,
            sourceY = 179,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=279,
            y=625,
            width=130,
            height=118,

            sourceX = 612,
            sourceY = 151,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=237,
            y=943,
            width=166,
            height=108,

            sourceX = 593,
            sourceY = 247,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=237,
            y=767,
            width=180,
            height=174,

            sourceX = 605,
            sourceY = 313,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=1,
            y=563,
            width=276,
            height=202,

            sourceX = 41,
            sourceY = 455,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=297,
            y=179,
            width=96,
            height=114,

            sourceX = 384,
            sourceY = 61,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 418,
    sheetContentHeight = 1140
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
