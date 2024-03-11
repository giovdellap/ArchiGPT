--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:9d734cfca0a20802f1a364df39875a59:56d551987f80ee61173803c0b7c79580:cf8ab4992190eb44f97f06311ef326d7$
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
            x=381,
            y=1,
            width=130,
            height=122,

            sourceX = 101,
            sourceY = 248,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=321,
            y=745,
            width=152,
            height=120,

            sourceX = 65,
            sourceY = 227,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=443,
            y=1075,
            width=62,
            height=64,

            sourceX = 164,
            sourceY = 441,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=321,
            y=619,
            width=156,
            height=124,

            sourceX = 119,
            sourceY = 548,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=293,
            y=867,
            width=198,
            height=206,

            sourceX = 79,
            sourceY = 358,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=1139,
            width=86,
            height=46,

            sourceX = 305,
            sourceY = 332,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=613,
            width=318,
            height=170,

            sourceX = 128,
            sourceY = 356,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=381,
            y=449,
            width=96,
            height=60,

            sourceX = 570,
            sourceY = 249,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=785,
            width=290,
            height=352,

            sourceX = 365,
            sourceY = 22,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=293,
            y=1075,
            width=148,
            height=86,

            sourceX = 571,
            sourceY = 191,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=381,
            y=125,
            width=122,
            height=112,

            sourceX = 604,
            sourceY = 134,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=455,
            y=511,
            width=50,
            height=50,

            sourceX = 617,
            sourceY = 189,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=381,
            y=239,
            width=116,
            height=86,

            sourceX = 636,
            sourceY = 125,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=365,
            y=511,
            width=88,
            height=106,

            sourceX = 545,
            sourceY = 43,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=381,
            y=327,
            width=96,
            height=120,

            sourceX = 620,
            sourceY = 16,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=1,
            width=378,
            height=476,

            sourceX = 448,
            sourceY = 196,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=1,
            y=479,
            width=362,
            height=132,

            sourceX = 31,
            sourceY = 63,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 512,
    sheetContentHeight = 1186
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
