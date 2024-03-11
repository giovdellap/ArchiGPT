--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:96d954d559e5836c54dce6fb403a3f5e:a497c94cb3d3b5051381ff4d7520dc1a:cf8ab4992190eb44f97f06311ef326d7$
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
            x=1,
            y=1,
            width=248,
            height=262,

            sourceX = 53,
            sourceY = 48,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=167,
            y=265,
            width=80,
            height=250,

            sourceX = 62,
            sourceY = 233,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=875,
            width=76,
            height=234,

            sourceX = 153,
            sourceY = 241,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=79,
            y=1071,
            width=54,
            height=30,

            sourceX = 83,
            sourceY = 480,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=149,
            y=517,
            width=88,
            height=26,

            sourceX = 170,
            sourceY = 469,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=609,
            width=130,
            height=140,

            sourceX = 248,
            sourceY = 45,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=185,
            y=997,
            width=60,
            height=64,

            sourceX = 286,
            sourceY = 271,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=423,
            width=146,
            height=184,

            sourceX = 654,
            sourceY = 468,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=751,
            width=130,
            height=122,

            sourceX = 673,
            sourceY = 355,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=133,
            y=625,
            width=106,
            height=112,

            sourceX = 588,
            sourceY = 191,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=79,
            y=875,
            width=22,
            height=18,

            sourceX = 673,
            sourceY = 633,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=133,
            y=739,
            width=104,
            height=136,

            sourceX = 576,
            sourceY = 290,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=149,
            y=545,
            width=86,
            height=78,

            sourceX = 581,
            sourceY = 415,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=103,
            y=877,
            width=120,
            height=118,

            sourceX = 409,
            sourceY = 248,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=1,
            y=265,
            width=164,
            height=156,

            sourceX = 361,
            sourceY = 305,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=79,
            y=997,
            width=104,
            height=72,

            sourceX = 392,
            sourceY = 444,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 250,
    sheetContentHeight = 1110
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
