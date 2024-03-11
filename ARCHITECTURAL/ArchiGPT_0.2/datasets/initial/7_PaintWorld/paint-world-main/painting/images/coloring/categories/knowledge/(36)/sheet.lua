--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:be3985df685ce053a060cddff0e3d875:6ef975c90b503a771a0c32c6830b7f0a:cf8ab4992190eb44f97f06311ef326d7$
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
            x=635,
            y=163,
            width=136,
            height=168,

            sourceX = 85,
            sourceY = 153,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=799,
            y=1,
            width=168,
            height=174,

            sourceX = 210,
            sourceY = 132,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=265,
            width=394,
            height=228,

            sourceX = 38,
            sourceY = 243,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=693,
            y=347,
            width=188,
            height=94,

            sourceX = 266,
            sourceY = 431,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=635,
            y=333,
            width=56,
            height=102,

            sourceX = 592,
            sourceY = 432,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=545,
            y=427,
            width=66,
            height=26,

            sourceX = 487,
            sourceY = 465,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=773,
            y=177,
            width=174,
            height=168,

            sourceX = 499,
            sourceY = 104,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=437,
            y=163,
            width=196,
            height=262,

            sourceX = 557,
            sourceY = 88,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=1,
            width=434,
            height=262,

            sourceX = 524,
            sourceY = 253,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=397,
            y=427,
            width=146,
            height=72,

            sourceX = 742,
            sourceY = 512,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=437,
            y=1,
            width=360,
            height=160,

            sourceX = 364,
            sourceY = 490,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 968,
    sheetContentHeight = 500
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
