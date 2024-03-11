--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:7c94857be75c0f75a496151c9353eb14:19a99b431586986a0cad3f11ae6a0225:cf8ab4992190eb44f97f06311ef326d7$
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
            x=363,
            y=131,
            width=82,
            height=100,

            sourceX = 38,
            sourceY = 446,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=447,
            y=131,
            width=46,
            height=86,

            sourceX = 311,
            sourceY = 364,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=667,
            width=192,
            height=152,

            sourceX = 362,
            sourceY = 243,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=195,
            y=777,
            width=310,
            height=120,

            sourceX = 267,
            sourceY = 345,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=203,
            y=475,
            width=200,
            height=188,

            sourceX = 532,
            sourceY = 286,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=821,
            width=176,
            height=68,

            sourceX = 534,
            sourceY = 506,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=475,
            width=200,
            height=190,

            sourceX = 2,
            sourceY = 281,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=239,
            width=358,
            height=234,

            sourceX = 184,
            sourceY = 418,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=1,
            width=360,
            height=236,

            sourceX = 83,
            sourceY = 36,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=363,
            y=233,
            width=72,
            height=42,

            sourceX = 404,
            sourceY = 208,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=363,
            y=1,
            width=120,
            height=128,

            sourceX = 525,
            sourceY = 31,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=203,
            y=665,
            width=282,
            height=110,

            sourceX = 523,
            sourceY = 78,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 506,
    sheetContentHeight = 898
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
