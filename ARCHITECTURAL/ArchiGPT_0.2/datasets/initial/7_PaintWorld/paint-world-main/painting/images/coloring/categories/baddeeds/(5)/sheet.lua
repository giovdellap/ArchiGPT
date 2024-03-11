--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:91e53f07b46d1bcf241058a09ae89e68:0fa02cc3818a124800a8802be7968c88:cf8ab4992190eb44f97f06311ef326d7$
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
            x=169,
            y=1,
            width=628,
            height=234,

            sourceX = 63,
            sourceY = 342,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=263,
            y=237,
            width=84,
            height=130,

            sourceX = 139,
            sourceY = 319,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=683,
            y=237,
            width=86,
            height=118,

            sourceX = 541,
            sourceY = 323,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=141,
            y=379,
            width=24,
            height=34,

            sourceX = 127,
            sourceY = 324,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=167,
            y=379,
            width=10,
            height=24,

            sourceX = 214,
            sourceY = 320,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=379,
            width=92,
            height=48,

            sourceX = 413,
            sourceY = 301,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=169,
            y=237,
            width=92,
            height=130,

            sourceX = 322,
            sourceY = 215,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=95,
            y=419,
            width=28,
            height=30,

            sourceX = 308,
            sourceY = 224,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=153,
            y=415,
            width=12,
            height=28,

            sourceX = 393,
            sourceY = 218,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=465,
            y=237,
            width=112,
            height=126,

            sourceX = 118,
            sourceY = 202,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=349,
            y=237,
            width=114,
            height=126,

            sourceX = 297,
            sourceY = 102,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=179,
            y=369,
            width=76,
            height=76,

            sourceX = 138,
            sourceY = 141,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=523,
            y=365,
            width=82,
            height=82,

            sourceX = 307,
            sourceY = 34,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=439,
            y=365,
            width=82,
            height=84,

            sourceX = 607,
            sourceY = 162,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=125,
            y=419,
            width=26,
            height=28,

            sourceX = 536,
            sourceY = 324,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=67,
            y=429,
            width=18,
            height=18,

            sourceX = 604,
            sourceY = 325,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=579,
            y=237,
            width=102,
            height=126,

            sourceX = 536,
            sourceY = 209,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=349,
            y=365,
            width=88,
            height=84,

            sourceX = 790,
            sourceY = 110,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=1,
            y=1,
            width=166,
            height=376,

            sourceX = 695,
            sourceY = 183,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=95,
            y=379,
            width=44,
            height=38,

            sourceX = 659,
            sourceY = 256,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=1,
            y=429,
            width=64,
            height=20,

            sourceX = 741,
            sourceY = 540,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 798,
    sheetContentHeight = 450
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
