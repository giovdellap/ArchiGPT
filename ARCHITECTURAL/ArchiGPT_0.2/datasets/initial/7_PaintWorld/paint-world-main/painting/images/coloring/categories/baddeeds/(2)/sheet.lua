--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:b17aeb32d9cef3f45dc27d310a942ac1:e51dad0b0201f657488379946220abfe:cf8ab4992190eb44f97f06311ef326d7$
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
            width=300,
            height=598,

            sourceX = 556,
            sourceY = 15,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=247,
            y=959,
            width=128,
            height=116,

            sourceX = 652,
            sourceY = 36,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=247,
            y=631,
            width=168,
            height=180,

            sourceX = 667,
            sourceY = 380,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=437,
            y=909,
            width=58,
            height=20,

            sourceX = 745,
            sourceY = 601,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=473,
            y=359,
            width=26,
            height=22,

            sourceX = 582,
            sourceY = 564,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=985,
            width=98,
            height=58,

            sourceX = 692,
            sourceY = 338,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=431,
            y=1031,
            width=78,
            height=38,

            sourceX = 705,
            sourceY = 381,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=417,
            y=631,
            width=92,
            height=174,

            sourceX = 663,
            sourceY = 135,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=247,
            y=813,
            width=118,
            height=144,

            sourceX = 87,
            sourceY = 235,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=303,
            y=359,
            width=168,
            height=270,

            sourceX = 441,
            sourceY = 183,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=377,
            y=931,
            width=90,
            height=98,

            sourceX = 362,
            sourceY = 85,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=101,
            y=985,
            width=80,
            height=74,

            sourceX = 255,
            sourceY = 88,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=183,
            y=985,
            width=52,
            height=50,

            sourceX = 303,
            sourceY = 173,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=377,
            y=1031,
            width=52,
            height=48,

            sourceX = 243,
            sourceY = 179,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=367,
            y=813,
            width=68,
            height=116,

            sourceX = 387,
            sourceY = 133,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=437,
            y=807,
            width=72,
            height=100,

            sourceX = 257,
            sourceY = 150,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=303,
            y=1,
            width=206,
            height=356,

            sourceX = 163,
            sourceY = 59,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=1,
            y=601,
            width=244,
            height=382,

            sourceX = 290,
            sourceY = 54,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=247,
            y=601,
            width=30,
            height=28,

            sourceX = 491,
            sourceY = 309,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=1,
            y=1045,
            width=54,
            height=30,

            sourceX = 354,
            sourceY = 428,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=183,
            y=1037,
            width=50,
            height=26,

            sourceX = 245,
            sourceY = 404,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 510,
    sheetContentHeight = 1080
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
