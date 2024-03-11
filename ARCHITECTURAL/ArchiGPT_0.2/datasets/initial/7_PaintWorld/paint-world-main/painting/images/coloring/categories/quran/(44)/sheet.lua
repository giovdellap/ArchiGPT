--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:87557a29b4ddfed2dd937ba43af51795:dfa2924dfe1eae0ec8cb94ba24aab7d1:cf8ab4992190eb44f97f06311ef326d7$
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
            x=859,
            y=455,
            width=146,
            height=74,

            sourceX = 146,
            sourceY = 335,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=1,
            width=616,
            height=370,

            sourceX = 4,
            sourceY = 294,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=749,
            y=571,
            width=94,
            height=66,

            sourceX = 393,
            sourceY = 316,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=447,
            y=575,
            width=136,
            height=124,

            sourceX = 636,
            sourceY = 234,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=557,
            y=495,
            width=38,
            height=14,

            sourceX = 615,
            sourceY = 470,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=951,
            y=531,
            width=46,
            height=40,

            sourceX = 576,
            sourceY = 504,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=557,
            y=475,
            width=32,
            height=18,

            sourceX = 710,
            sourceY = 469,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=321,
            y=373,
            width=234,
            height=200,

            sourceX = 580,
            sourceY = 346,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=749,
            y=455,
            width=108,
            height=114,

            sourceX = 419,
            sourceY = 41,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=619,
            y=1,
            width=396,
            height=452,

            sourceX = 282,
            sourceY = 23,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=859,
            y=531,
            width=90,
            height=88,

            sourceX = 252,
            sourceY = 138,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=599,
            y=455,
            width=148,
            height=180,

            sourceX = 230,
            sourceY = 132,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=1,
            y=373,
            width=318,
            height=388,

            sourceX = 546,
            sourceY = 75,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=557,
            y=373,
            width=40,
            height=100,

            sourceX = 656,
            sourceY = 339,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=321,
            y=575,
            width=124,
            height=182,

            sourceX = 298,
            sourceY = 77,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 1016,
    sheetContentHeight = 762
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
