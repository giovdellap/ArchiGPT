--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:900dcbd39a7d29e9a7d812786afd4fdb:6f62beb55b9dcc6c5772e3413b9fee44:cf8ab4992190eb44f97f06311ef326d7$
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
            x=95,
            y=407,
            width=104,
            height=142,

            sourceX = 468,
            sourceY = 472,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=577,
            width=100,
            height=144,

            sourceX = 526,
            sourceY = 465,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=201,
            y=323,
            width=36,
            height=28,

            sourceX = 274,
            sourceY = 512,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=201,
            y=287,
            width=36,
            height=34,

            sourceX = 326,
            sourceY = 479,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=103,
            y=679,
            width=76,
            height=52,

            sourceX = 239,
            sourceY = 427,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=209,
            y=51,
            width=26,
            height=42,

            sourceX = 219,
            sourceY = 429,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=201,
            y=353,
            width=36,
            height=22,

            sourceX = 250,
            sourceY = 410,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=407,
            width=92,
            height=168,

            sourceX = 533,
            sourceY = 283,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=157,
            width=180,
            height=138,

            sourceX = 398,
            sourceY = 244,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=1,
            y=297,
            width=198,
            height=108,

            sourceX = 387,
            sourceY = 51,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=1,
            y=1,
            width=206,
            height=154,

            sourceX = 382,
            sourceY = 86,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=209,
            y=1,
            width=32,
            height=48,

            sourceX = 578,
            sourceY = 156,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=199,
            y=611,
            width=32,
            height=44,

            sourceX = 358,
            sourceY = 141,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=1,
            y=723,
            width=96,
            height=28,

            sourceX = 439,
            sourceY = 236,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=183,
            y=269,
            width=58,
            height=16,

            sourceX = 464,
            sourceY = 234,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=103,
            y=551,
            width=94,
            height=126,

            sourceX = 333,
            sourceY = 272,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=183,
            y=157,
            width=64,
            height=110,

            sourceX = 347,
            sourceY = 315,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=181,
            y=679,
            width=68,
            height=38,

            sourceX = 379,
            sourceY = 472,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=199,
            y=551,
            width=54,
            height=58,

            sourceX = 510,
            sourceY = 398,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 254,
    sheetContentHeight = 752
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
