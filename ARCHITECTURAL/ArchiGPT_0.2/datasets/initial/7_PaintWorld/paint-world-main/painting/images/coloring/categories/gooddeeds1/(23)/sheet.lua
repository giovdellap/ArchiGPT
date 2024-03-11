--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:3e4dfb612abf5ff74c76b59250b7c060:e392d232e28c270380a3947810d75185:cf8ab4992190eb44f97f06311ef326d7$
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
            x=775,
            y=447,
            width=196,
            height=166,

            sourceX = 160,
            sourceY = 110,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=775,
            y=779,
            width=152,
            height=148,

            sourceX = 154,
            sourceY = 93,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=411,
            y=853,
            width=56,
            height=72,

            sourceX = 345,
            sourceY = 251,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=287,
            y=685,
            width=34,
            height=22,

            sourceX = 437,
            sourceY = 159,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=307,
            y=875,
            width=102,
            height=32,

            sourceX = 33,
            sourceY = 475,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=307,
            y=909,
            width=30,
            height=20,

            sourceX = 520,
            sourceY = 282,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=247,
            y=783,
            width=92,
            height=90,

            sourceX = 531,
            sourceY = 371,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=565,
            y=685,
            width=208,
            height=214,

            sourceX = 0,
            sourceY = 408,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=775,
            y=615,
            width=174,
            height=162,

            sourceX = 551,
            sourceY = 91,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=341,
            y=685,
            width=222,
            height=166,

            sourceX = 537,
            sourceY = 46,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=287,
            y=447,
            width=334,
            height=236,

            sourceX = 498,
            sourceY = 204,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=1,
            y=783,
            width=244,
            height=146,

            sourceX = 586,
            sourceY = 413,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=1,
            y=447,
            width=284,
            height=334,

            sourceX = 114,
            sourceY = 201,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=339,
            y=909,
            width=28,
            height=20,

            sourceX = 302,
            sourceY = 404,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=247,
            y=875,
            width=58,
            height=48,

            sourceX = 394,
            sourceY = 188,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=1,
            width=960,
            height=444,

            sourceX = 0,
            sourceY = 228,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 972,
    sheetContentHeight = 930
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
