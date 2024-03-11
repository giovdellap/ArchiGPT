--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:3bf79eb9fa76f78cfc032f67e047506d:10d71067a1e4129def9187ce3ec0d0d4:cf8ab4992190eb44f97f06311ef326d7$
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
            x=395,
            y=323,
            width=70,
            height=98,

            sourceX = 215,
            sourceY = 115,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=1,
            width=218,
            height=574,

            sourceX = 119,
            sourceY = 98,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=209,
            y=577,
            width=166,
            height=382,

            sourceX = 214,
            sourceY = 168,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=377,
            y=527,
            width=78,
            height=146,

            sourceX = 313,
            sourceY = 526,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=343,
            y=323,
            width=50,
            height=202,

            sourceX = 365,
            sourceY = 470,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=283,
            y=1147,
            width=80,
            height=62,

            sourceX = 336,
            sourceY = 413,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=365,
            y=1147,
            width=34,
            height=38,

            sourceX = 425,
            sourceY = 471,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=283,
            y=1095,
            width=40,
            height=50,

            sourceX = 654,
            sourceY = 580,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=377,
            y=859,
            width=84,
            height=80,

            sourceX = 506,
            sourceY = 254,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=377,
            y=775,
            width=96,
            height=82,

            sourceX = 484,
            sourceY = 468,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=377,
            y=675,
            width=100,
            height=98,

            sourceX = 648,
            sourceY = 407,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=221,
            y=323,
            width=120,
            height=140,

            sourceX = 543,
            sourceY = 31,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=395,
            y=423,
            width=96,
            height=86,

            sourceX = 473,
            sourceY = 234,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=1,
            y=937,
            width=168,
            height=184,

            sourceX = 409,
            sourceY = 488,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=221,
            y=465,
            width=106,
            height=108,

            sourceX = 495,
            sourceY = 479,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=171,
            y=961,
            width=164,
            height=132,

            sourceX = 549,
            sourceY = 523,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=171,
            y=1095,
            width=110,
            height=94,

            sourceX = 661,
            sourceY = 383,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=337,
            y=961,
            width=156,
            height=184,

            sourceX = 608,
            sourceY = 488,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=221,
            y=1,
            width=248,
            height=320,

            sourceX = 476,
            sourceY = 140,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=1,
            y=1123,
            width=168,
            height=86,

            sourceX = 510,
            sourceY = 443,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 21
            x=1,
            y=577,
            width=206,
            height=358,

            sourceX = 340,
            sourceY = 222,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 494,
    sheetContentHeight = 1210
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
