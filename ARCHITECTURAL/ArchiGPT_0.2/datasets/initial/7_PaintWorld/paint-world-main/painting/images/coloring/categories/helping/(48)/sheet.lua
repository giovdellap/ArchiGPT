--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:ce0174477c0bdb186c87b5dc8de6e13d:2f0c3f40494884a453f94a3770ca4063:cf8ab4992190eb44f97f06311ef326d7$
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
            x=257,
            y=867,
            width=114,
            height=100,

            sourceX = 160,
            sourceY = 12,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=293,
            y=201,
            width=104,
            height=96,

            sourceX = 271,
            sourceY = 61,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=1,
            y=813,
            width=132,
            height=122,

            sourceX = 312,
            sourceY = 271,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=203,
            y=597,
            width=202,
            height=200,

            sourceX = 300,
            sourceY = 307,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=135,
            y=867,
            width=120,
            height=104,

            sourceX = 348,
            sourceY = 485,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=203,
            y=799,
            width=186,
            height=66,

            sourceX = 230,
            sourceY = 137,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1,
            y=597,
            width=200,
            height=214,

            sourceX = 367,
            sourceY = 100,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=293,
            y=1,
            width=86,
            height=198,

            sourceX = 442,
            sourceY = 374,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=1,
            y=1,
            width=290,
            height=594,

            sourceX = 93,
            sourceY = 11,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 406,
    sheetContentHeight = 972
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
