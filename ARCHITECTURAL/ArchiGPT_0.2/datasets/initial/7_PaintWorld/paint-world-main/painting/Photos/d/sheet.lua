--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:4c74dadf00c229b9d1ea77ba52d8cc0d:25e665f7a9bd74a4a5ad7b97239824c5:cf8ab4992190eb44f97f06311ef326d7$
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
            x=133,
            y=621,
            width=38,
            height=54,

            sourceX = 266,
            sourceY = 392,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=173,
            y=647,
            width=36,
            height=40,

            sourceX = 319,
            sourceY = 389,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=1,
            width=216,
            height=270,

            sourceX = 184,
            sourceY = 332,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=199,
            y=469,
            width=44,
            height=60,

            sourceX = 187,
            sourceY = 467,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=133,
            y=531,
            width=54,
            height=62,

            sourceX = 169,
            sourceY = 510,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=133,
            y=595,
            width=44,
            height=24,

            sourceX = 380,
            sourceY = 447,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=133,
            y=409,
            width=70,
            height=50,

            sourceX = 413,
            sourceY = 424,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=273,
            width=130,
            height=628,

            sourceX = 627,
            sourceY = 14,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=133,
            y=273,
            width=110,
            height=134,

            sourceX = 581,
            sourceY = 35,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=219,
            y=45,
            width=22,
            height=38,

            sourceX = 663,
            sourceY = 205,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=219,
            y=1,
            width=22,
            height=42,

            sourceX = 638,
            sourceY = 207,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=189,
            y=593,
            width=44,
            height=52,

            sourceX = 631,
            sourceY = 325,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=133,
            y=461,
            width=64,
            height=68,

            sourceX = 581,
            sourceY = 366,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=189,
            y=531,
            width=50,
            height=60,

            sourceX = 695,
            sourceY = 355,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=205,
            y=409,
            width=38,
            height=58,

            sourceX = 711,
            sourceY = 314,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 244,
    sheetContentHeight = 902
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
