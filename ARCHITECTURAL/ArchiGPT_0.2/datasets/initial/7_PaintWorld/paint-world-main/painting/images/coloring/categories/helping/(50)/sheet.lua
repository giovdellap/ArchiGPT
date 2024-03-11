--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:80bc6ce36d3999c1c60ff2ce47185aab:7a5167064ab93c186141cc0e0a95d05c:cf8ab4992190eb44f97f06311ef326d7$
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
            x=521,
            y=447,
            width=64,
            height=42,

            sourceX = 494,
            sourceY = 424,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=595,
            y=355,
            width=66,
            height=42,

            sourceX = 456,
            sourceY = 570,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=337,
            y=367,
            width=90,
            height=122,

            sourceX = 591,
            sourceY = 497,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=699,
            y=345,
            width=128,
            height=152,

            sourceX = 526,
            sourceY = 355,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=197,
            y=367,
            width=138,
            height=130,

            sourceX = 573,
            sourceY = 242,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=699,
            y=159,
            width=98,
            height=184,

            sourceX = 627,
            sourceY = 356,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=197,
            y=1,
            width=266,
            height=364,

            sourceX = 421,
            sourceY = 29,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=595,
            y=1,
            width=190,
            height=156,

            sourceX = 418,
            sourceY = 432,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=787,
            y=1,
            width=106,
            height=106,

            sourceX = 364,
            sourceY = 53,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=521,
            y=397,
            width=70,
            height=48,

            sourceX = 271,
            sourceY = 271,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=593,
            y=399,
            width=36,
            height=34,

            sourceX = 351,
            sourceY = 207,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=663,
            y=355,
            width=34,
            height=36,

            sourceX = 385,
            sourceY = 259,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=799,
            y=109,
            width=62,
            height=218,

            sourceX = 457,
            sourceY = 249,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=595,
            y=159,
            width=102,
            height=194,

            sourceX = 468,
            sourceY = 273,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=1,
            y=1,
            width=194,
            height=484,

            sourceX = 317,
            sourceY = 35,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=429,
            y=397,
            width=90,
            height=90,

            sourceX = 290,
            sourceY = 122,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=465,
            y=1,
            width=128,
            height=394,

            sourceX = 253,
            sourceY = 115,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 894,
    sheetContentHeight = 498
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
