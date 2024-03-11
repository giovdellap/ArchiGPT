--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:caf28ab37c937d6fc33eb979cb5877f9:4dcab5c190115bd8fb11fe99c76eaa0a:cf8ab4992190eb44f97f06311ef326d7$
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
            y=481,
            width=162,
            height=558,

            sourceX = 633,
            sourceY = 107,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=213,
            width=214,
            height=266,

            sourceX = 160,
            sourceY = 136,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=1235,
            width=136,
            height=242,

            sourceX = 209,
            sourceY = 390,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=139,
            y=1235,
            width=112,
            height=132,

            sourceX = 136,
            sourceY = 374,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=217,
            y=213,
            width=36,
            height=46,

            sourceX = 178,
            sourceY = 355,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=165,
            y=963,
            width=54,
            height=20,

            sourceX = 227,
            sourceY = 628,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=165,
            y=779,
            width=76,
            height=18,

            sourceX = 283,
            sourceY = 620,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=209,
            y=985,
            width=40,
            height=42,

            sourceX = 347,
            sourceY = 344,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=165,
            y=481,
            width=86,
            height=116,

            sourceX = 330,
            sourceY = 365,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=139,
            y=1369,
            width=108,
            height=118,

            sourceX = 218,
            sourceY = 28,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=165,
            y=799,
            width=70,
            height=86,

            sourceX = 628,
            sourceY = 128,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=165,
            y=985,
            width=42,
            height=42,

            sourceX = 620,
            sourceY = 272,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=1,
            y=1041,
            width=158,
            height=192,

            sourceX = 510,
            sourceY = 270,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=1,
            y=1479,
            width=104,
            height=282,

            sourceX = 574,
            sourceY = 380,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=165,
            y=887,
            width=70,
            height=74,

            sourceX = 581,
            sourceY = 385,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=107,
            y=1489,
            width=134,
            height=182,

            sourceX = 566,
            sourceY = 165,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=107,
            y=1673,
            width=102,
            height=86,

            sourceX = 394,
            sourceY = 406,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=165,
            y=599,
            width=82,
            height=178,

            sourceX = 385,
            sourceY = 408,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=217,
            y=261,
            width=22,
            height=28,

            sourceX = 407,
            sourceY = 572,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=161,
            y=1041,
            width=92,
            height=54,

            sourceX = 423,
            sourceY = 606,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=1,
            y=1,
            width=250,
            height=210,

            sourceX = 354,
            sourceY = 456,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 254,
    sheetContentHeight = 1762
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
