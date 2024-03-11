--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:106335eb3085dbb3e85bcd5e20c2efc5:07911607c8108731dce4cf687a22aeaf:cf8ab4992190eb44f97f06311ef326d7$
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
            x=753,
            y=1,
            width=194,
            height=134,

            sourceX = 242,
            sourceY = 17,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=753,
            y=137,
            width=158,
            height=124,

            sourceX = 260,
            sourceY = 83,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=263,
            width=272,
            height=238,

            sourceX = 206,
            sourceY = 178,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=561,
            y=263,
            width=196,
            height=124,

            sourceX = 223,
            sourceY = 385,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=913,
            y=137,
            width=22,
            height=34,

            sourceX = 209,
            sourceY = 415,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1,
            y=1,
            width=750,
            height=260,

            sourceX = 95,
            sourceY = 388,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=275,
            y=263,
            width=284,
            height=212,

            sourceX = 353,
            sourceY = 345,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=561,
            y=389,
            width=270,
            height=106,

            sourceX = 556,
            sourceY = 523,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=759,
            y=263,
            width=70,
            height=82,

            sourceX = 329,
            sourceY = 348,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=275,
            y=477,
            width=46,
            height=18,

            sourceX = 472,
            sourceY = 336,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 948,
    sheetContentHeight = 502
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
