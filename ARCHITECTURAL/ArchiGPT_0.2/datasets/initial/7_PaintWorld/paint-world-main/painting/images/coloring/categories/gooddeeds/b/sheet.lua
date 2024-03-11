--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:4343e35bd992daa42b9c5411cc7c9c4d:5a8d8142cde4887da4445cd7c47a6d2a:cf8ab4992190eb44f97f06311ef326d7$
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
            x=701,
            y=1,
            width=160,
            height=142,

            sourceX = 226,
            sourceY = 67,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=481,
            y=247,
            width=172,
            height=182,

            sourceX = 236,
            sourceY = 427,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=255,
            y=1,
            width=222,
            height=268,

            sourceX = 210,
            sourceY = 177,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=651,
            y=203,
            width=72,
            height=24,

            sourceX = 258,
            sourceY = 603,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=651,
            y=179,
            width=94,
            height=22,

            sourceX = 333,
            sourceY = 593,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=651,
            y=147,
            width=142,
            height=30,

            sourceX = 582,
            sourceY = 618,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=1,
            width=252,
            height=246,

            sourceX = 480,
            sourceY = 191,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=159,
            y=249,
            width=82,
            height=66,

            sourceX = 412,
            sourceY = 292,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=159,
            y=317,
            width=54,
            height=42,

            sourceX = 580,
            sourceY = 421,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=1,
            y=249,
            width=156,
            height=202,

            sourceX = 577,
            sourceY = 431,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=479,
            y=147,
            width=170,
            height=98,

            sourceX = 542,
            sourceY = 419,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=479,
            y=1,
            width=220,
            height=144,

            sourceX = 178,
            sourceY = 23,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=159,
            y=361,
            width=18,
            height=32,

            sourceX = 405,
            sourceY = 412,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=243,
            y=271,
            width=236,
            height=166,

            sourceX = 551,
            sourceY = 29,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=655,
            y=229,
            width=112,
            height=94,

            sourceX = 570,
            sourceY = 106,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 960,
    sheetContentHeight = 672
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
