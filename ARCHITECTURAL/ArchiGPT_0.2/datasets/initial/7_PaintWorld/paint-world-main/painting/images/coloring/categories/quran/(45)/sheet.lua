--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:2de63eb9b726a44ec0129ddafac848f2:778c7fc186ffd967bfccb73e703ce49c:cf8ab4992190eb44f97f06311ef326d7$
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
            x=103,
            y=359,
            width=118,
            height=136,

            sourceX = 603,
            sourceY = 67,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=359,
            width=100,
            height=142,

            sourceX = 647,
            sourceY = 285,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=689,
            y=1,
            width=624,
            height=240,

            sourceX = 190,
            sourceY = 432,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=223,
            y=359,
            width=278,
            height=130,

            sourceX = 196,
            sourceY = 411,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1039,
            y=341,
            width=68,
            height=82,

            sourceX = 76,
            sourceY = 290,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1039,
            y=243,
            width=92,
            height=96,

            sourceX = 106,
            sourceY = 158,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1109,
            y=341,
            width=84,
            height=92,

            sourceX = 272,
            sourceY = 171,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1007,
            y=431,
            width=56,
            height=36,

            sourceX = 155,
            sourceY = 398,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=581,
            y=355,
            width=58,
            height=102,

            sourceX = 472,
            sourceY = 280,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=647,
            y=421,
            width=32,
            height=26,

            sourceX = 510,
            sourceY = 380,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=883,
            y=431,
            width=62,
            height=70,

            sourceX = 695,
            sourceY = 226,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=647,
            y=355,
            width=36,
            height=64,

            sourceX = 264,
            sourceY = 266,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=589,
            y=459,
            width=56,
            height=44,

            sourceX = 329,
            sourceY = 314,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=503,
            y=463,
            width=84,
            height=46,

            sourceX = 127,
            sourceY = 264,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=503,
            y=355,
            width=76,
            height=106,

            sourceX = 275,
            sourceY = 318,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=689,
            y=243,
            width=192,
            height=268,

            sourceX = 2,
            sourceY = 283,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=883,
            y=243,
            width=154,
            height=186,

            sourceX = 93,
            sourceY = 236,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=947,
            y=431,
            width=58,
            height=70,

            sourceX = 136,
            sourceY = 428,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=403,
            y=1,
            width=284,
            height=352,

            sourceX = 183,
            sourceY = 152,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=1,
            y=1,
            width=400,
            height=356,

            sourceX = 463,
            sourceY = 179,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 1314,
    sheetContentHeight = 512
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
