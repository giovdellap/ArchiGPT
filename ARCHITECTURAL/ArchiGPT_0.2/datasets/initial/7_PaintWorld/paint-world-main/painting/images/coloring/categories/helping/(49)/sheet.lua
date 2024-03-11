--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:41c461cd568b327e4704c5adb66150b5:4560f0d47febf234a7d3a4d1ed2deeb3:cf8ab4992190eb44f97f06311ef326d7$
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
            x=531,
            y=267,
            width=116,
            height=136,

            sourceX = 297,
            sourceY = 47,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=481,
            y=589,
            width=106,
            height=122,

            sourceX = 587,
            sourceY = 51,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=507,
            y=799,
            width=76,
            height=62,

            sourceX = 463,
            sourceY = 242,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=641,
            y=405,
            width=6,
            height=10,

            sourceX = 463,
            sourceY = 345,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=257,
            y=547,
            width=40,
            height=28,

            sourceX = 439,
            sourceY = 340,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=585,
            y=799,
            width=54,
            height=62,

            sourceX = 568,
            sourceY = 247,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=481,
            y=547,
            width=36,
            height=26,

            sourceX = 593,
            sourceY = 319,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=531,
            y=405,
            width=108,
            height=182,

            sourceX = 596,
            sourceY = 142,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=507,
            y=925,
            width=66,
            height=56,

            sourceX = 485,
            sourceY = 232,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=81,
            y=957,
            width=50,
            height=46,

            sourceX = 423,
            sourceY = 315,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=303,
            y=815,
            width=168,
            height=192,

            sourceX = 302,
            sourceY = 164,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=589,
            y=589,
            width=46,
            height=108,

            sourceX = 386,
            sourceY = 148,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=1,
            y=957,
            width=78,
            height=48,

            sourceX = 373,
            sourceY = 558,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=507,
            y=863,
            width=64,
            height=60,

            sourceX = 599,
            sourceY = 573,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=257,
            y=1,
            width=272,
            height=544,

            sourceX = 239,
            sourceY = 38,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=581,
            width=300,
            height=374,

            sourceX = 51,
            sourceY = 199,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=481,
            y=713,
            width=118,
            height=84,

            sourceX = 417,
            sourceY = 187,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=1,
            y=1,
            width=254,
            height=578,

            sourceX = 528,
            sourceY = 29,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=473,
            y=815,
            width=32,
            height=140,

            sourceX = 572,
            sourceY = 155,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=303,
            y=547,
            width=176,
            height=266,

            sourceX = 691,
            sourceY = 308,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=531,
            y=1,
            width=116,
            height=264,

            sourceX = 454,
            sourceY = 298,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 648,
    sheetContentHeight = 1008
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
