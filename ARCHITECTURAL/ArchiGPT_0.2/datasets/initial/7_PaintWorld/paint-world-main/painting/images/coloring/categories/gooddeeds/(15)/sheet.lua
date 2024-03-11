--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:fd77340e0942046d409fd0bd80af5cdb:7afb7f0e15390bd9f7736378355158ea:cf8ab4992190eb44f97f06311ef326d7$
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
            y=335,
            width=148,
            height=150,

            sourceX = 392,
            sourceY = 508,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=179,
            y=115,
            width=276,
            height=182,

            sourceX = 467,
            sourceY = 486,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=457,
            y=279,
            width=84,
            height=74,

            sourceX = 494,
            sourceY = 434,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=445,
            y=355,
            width=32,
            height=28,

            sourceX = 487,
            sourceY = 414,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1,
            y=1,
            width=176,
            height=332,

            sourceX = 601,
            sourceY = 296,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=543,
            y=65,
            width=56,
            height=38,

            sourceX = 600,
            sourceY = 276,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=457,
            y=115,
            width=114,
            height=98,

            sourceX = 675,
            sourceY = 210,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=151,
            y=335,
            width=26,
            height=48,

            sourceX = 755,
            sourceY = 470,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=543,
            y=1,
            width=74,
            height=62,

            sourceX = 570,
            sourceY = 441,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=355,
            y=387,
            width=188,
            height=102,

            sourceX = 126,
            sourceY = 570,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=1,
            y=487,
            width=18,
            height=18,

            sourceX = 677,
            sourceY = 251,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=179,
            y=1,
            width=362,
            height=112,

            sourceX = 417,
            sourceY = 215,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=457,
            y=215,
            width=96,
            height=62,

            sourceX = 189,
            sourceY = 277,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=151,
            y=387,
            width=202,
            height=118,

            sourceX = 176,
            sourceY = 356,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=555,
            y=215,
            width=54,
            height=66,

            sourceX = 385,
            sourceY = 385,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=179,
            y=299,
            width=264,
            height=86,

            sourceX = 300,
            sourceY = 310,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 618,
    sheetContentHeight = 506
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
