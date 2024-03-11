--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:4a02c4c03288f0564717b0ae40d13e6c:ef7817ccd94d502dc977e205720309f9:cf8ab4992190eb44f97f06311ef326d7$
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
            x=447,
            y=351,
            width=54,
            height=32,

            sourceX = 161,
            sourceY = 561,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=339,
            y=475,
            width=14,
            height=22,

            sourceX = 353,
            sourceY = 551,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=775,
            y=105,
            width=112,
            height=92,

            sourceX = 253,
            sourceY = 315,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=775,
            y=199,
            width=114,
            height=86,

            sourceX = 554,
            sourceY = 318,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=503,
            y=351,
            width=26,
            height=58,

            sourceX = 508,
            sourceY = 210,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=339,
            y=351,
            width=106,
            height=122,

            sourceX = 660,
            sourceY = 24,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=339,
            y=1,
            width=190,
            height=348,

            sourceX = 635,
            sourceY = 12,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=717,
            y=287,
            width=136,
            height=168,

            sourceX = 500,
            sourceY = 156,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=1,
            width=336,
            height=496,

            sourceX = 538,
            sourceY = 176,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=775,
            y=1,
            width=126,
            height=102,

            sourceX = 498,
            sourceY = 274,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=855,
            y=287,
            width=38,
            height=182,

            sourceX = 623,
            sourceY = 160,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=531,
            y=1,
            width=242,
            height=284,

            sourceX = 132,
            sourceY = 388,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=531,
            y=287,
            width=184,
            height=216,

            sourceX = 191,
            sourceY = 262,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 902,
    sheetContentHeight = 504
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
