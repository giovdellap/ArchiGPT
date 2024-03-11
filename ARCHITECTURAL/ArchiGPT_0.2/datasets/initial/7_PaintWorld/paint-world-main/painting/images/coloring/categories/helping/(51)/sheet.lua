--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:ebb2ff543d7f3f319f6a7ac4b641c3ff:7f2aa1ab12a55c8f6bef4decb2adc75f:cf8ab4992190eb44f97f06311ef326d7$
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
            x=237,
            y=1,
            width=320,
            height=320,

            sourceX = 66,
            sourceY = 189,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=459,
            width=48,
            height=24,

            sourceX = 385,
            sourceY = 203,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=673,
            y=317,
            width=102,
            height=92,

            sourceX = 598,
            sourceY = 246,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=725,
            y=1,
            width=66,
            height=68,

            sourceX = 543,
            sourceY = 44,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=673,
            y=411,
            width=88,
            height=88,

            sourceX = 535,
            sourceY = 136,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=725,
            y=71,
            width=34,
            height=34,

            sourceX = 577,
            sourceY = 224,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=515,
            y=423,
            width=92,
            height=74,

            sourceX = 729,
            sourceY = 296,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=411,
            y=323,
            width=102,
            height=182,

            sourceX = 679,
            sourceY = 316,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=673,
            y=211,
            width=118,
            height=104,

            sourceX = 457,
            sourceY = 367,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=559,
            y=1,
            width=164,
            height=208,

            sourceX = 373,
            sourceY = 450,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=559,
            y=211,
            width=112,
            height=210,

            sourceX = 565,
            sourceY = 287,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=237,
            y=323,
            width=172,
            height=132,

            sourceX = 466,
            sourceY = 238,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=1,
            y=1,
            width=234,
            height=456,

            sourceX = 422,
            sourceY = 25,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 792,
    sheetContentHeight = 506
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
