--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:24947556561a01c344c2d1b8a5b6b7df:bc796116e013c57cde5150a815ac411e:cf8ab4992190eb44f97f06311ef326d7$
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
            x=227,
            y=853,
            width=84,
            height=102,

            sourceX = 385,
            sourceY = 65,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=355,
            y=307,
            width=136,
            height=134,

            sourceX = 188,
            sourceY = 384,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=355,
            y=551,
            width=126,
            height=116,

            sourceX = 555,
            sourceY = 335,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=355,
            y=443,
            width=128,
            height=106,

            sourceX = 618,
            sourceY = 443,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=313,
            y=951,
            width=52,
            height=62,

            sourceX = 570,
            sourceY = 431,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=965,
            width=40,
            height=46,

            sourceX = 303,
            sourceY = 553,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=225,
            y=965,
            width=44,
            height=34,

            sourceX = 616,
            sourceY = 435,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=355,
            y=1,
            width=146,
            height=150,

            sourceX = 536,
            sourceY = 453,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=227,
            y=653,
            width=34,
            height=22,

            sourceX = 709,
            sourceY = 527,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=313,
            y=853,
            width=170,
            height=96,

            sourceX = 527,
            sourceY = 576,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=355,
            y=153,
            width=136,
            height=152,

            sourceX = 256,
            sourceY = 489,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=43,
            y=965,
            width=180,
            height=44,

            sourceX = 237,
            sourceY = 628,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=353,
            y=669,
            width=158,
            height=164,

            sourceX = 222,
            sourceY = 502,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=271,
            y=653,
            width=80,
            height=198,

            sourceX = 383,
            sourceY = 138,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=1,
            y=669,
            width=224,
            height=294,

            sourceX = 207,
            sourceY = 183,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=1,
            width=352,
            height=650,

            sourceX = 235,
            sourceY = 22,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=485,
            y=443,
            width=20,
            height=50,

            sourceX = 357,
            sourceY = 277,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 512,
    sheetContentHeight = 1014
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
