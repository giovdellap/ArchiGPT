--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:53d52c196f01ffa8448432fdd2bbd9bd:7c6344bdf46afa9c4f4701cab054f926:cf8ab4992190eb44f97f06311ef326d7$
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
            x=353,
            y=395,
            width=110,
            height=86,

            sourceX = 28,
            sourceY = 151,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=569,
            y=395,
            width=90,
            height=68,

            sourceX = 412,
            sourceY = 365,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=455,
            width=70,
            height=56,

            sourceX = 286,
            sourceY = 254,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=145,
            y=455,
            width=56,
            height=42,

            sourceX = 310,
            sourceY = 298,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=225,
            y=455,
            width=30,
            height=24,

            sourceX = 330,
            sourceY = 357,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=203,
            y=455,
            width=20,
            height=36,

            sourceX = 256,
            sourceY = 241,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=1,
            width=256,
            height=452,

            sourceX = 124,
            sourceY = 206,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=465,
            y=395,
            width=102,
            height=86,

            sourceX = 380,
            sourceY = 420,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=259,
            y=395,
            width=92,
            height=116,

            sourceX = 375,
            sourceY = 492,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=843,
            y=1,
            width=134,
            height=168,

            sourceX = 34,
            sourceY = 229,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=843,
            y=171,
            width=112,
            height=232,

            sourceX = 44,
            sourceY = 381,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=73,
            y=455,
            width=70,
            height=54,

            sourceX = 103,
            sourceY = 234,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=259,
            y=1,
            width=582,
            height=392,

            sourceX = 266,
            sourceY = 0,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 978,
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
