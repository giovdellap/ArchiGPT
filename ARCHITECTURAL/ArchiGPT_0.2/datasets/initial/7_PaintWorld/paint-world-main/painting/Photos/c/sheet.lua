--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:450e8ee1201ea2bdf4e708ddbc5175c0:6280fb4e37805b2106f6ed8dfadda979:cf8ab4992190eb44f97f06311ef326d7$
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
            x=195,
            y=345,
            width=90,
            height=148,

            sourceX = 236,
            sourceY = 362,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=1,
            width=144,
            height=220,

            sourceX = 489,
            sourceY = 297,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=295,
            y=195,
            width=56,
            height=162,

            sourceX = 549,
            sourceY = 346,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=325,
            y=31,
            width=14,
            height=28,

            sourceX = 544,
            sourceY = 324,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=147,
            y=129,
            width=128,
            height=34,

            sourceX = 403,
            sourceY = 479,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=147,
            y=1,
            width=176,
            height=126,

            sourceX = 382,
            sourceY = 341,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=389,
            width=174,
            height=88,

            sourceX = 376,
            sourceY = 407,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=147,
            y=165,
            width=82,
            height=30,

            sourceX = 430,
            sourceY = 332,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=231,
            y=165,
            width=38,
            height=26,

            sourceX = 451,
            sourceY = 317,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=1,
            y=223,
            width=156,
            height=164,

            sourceX = 394,
            sourceY = 166,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=287,
            y=359,
            width=56,
            height=70,

            sourceX = 360,
            sourceY = 252,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=325,
            y=1,
            width=28,
            height=28,

            sourceX = 481,
            sourceY = 239,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=287,
            y=431,
            width=32,
            height=36,

            sourceX = 417,
            sourceY = 228,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=159,
            y=197,
            width=134,
            height=146,

            sourceX = 395,
            sourceY = 183,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=159,
            y=345,
            width=34,
            height=36,

            sourceX = 352,
            sourceY = 437,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=277,
            y=129,
            width=50,
            height=64,

            sourceX = 360,
            sourceY = 417,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 354,
    sheetContentHeight = 494
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
