--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:ad501610045e7a4e418882dc812051b8:3ff458ea39c90b8d593b01e2522bca1d:cf8ab4992190eb44f97f06311ef326d7$
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
            x=327,
            y=127,
            width=104,
            height=108,

            sourceX = 553,
            sourceY = 154,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=389,
            y=237,
            width=32,
            height=40,

            sourceX = 429,
            sourceY = 313,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=433,
            y=173,
            width=40,
            height=42,

            sourceX = 707,
            sourceY = 331,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=433,
            y=217,
            width=36,
            height=28,

            sourceX = 542,
            sourceY = 524,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=389,
            y=279,
            width=30,
            height=30,

            sourceX = 609,
            sourceY = 525,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=189,
            y=579,
            width=264,
            height=290,

            sourceX = 453,
            sourceY = 239,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=313,
            y=311,
            width=162,
            height=134,

            sourceX = 43,
            sourceY = 538,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=1,
            y=1,
            width=324,
            height=262,

            sourceX = 0,
            sourceY = 359,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=423,
            y=247,
            width=50,
            height=40,

            sourceX = 80,
            sourceY = 546,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=313,
            y=447,
            width=152,
            height=130,

            sourceX = 80,
            sourceY = 141,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=1,
            y=579,
            width=186,
            height=346,

            sourceX = 0,
            sourceY = 50,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=189,
            y=871,
            width=246,
            height=70,

            sourceX = 219,
            sourceY = 196,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=327,
            y=237,
            width=60,
            height=68,

            sourceX = 201,
            sourceY = 177,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=433,
            y=127,
            width=44,
            height=44,

            sourceX = 255,
            sourceY = 127,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=327,
            y=1,
            width=150,
            height=124,

            sourceX = 184,
            sourceY = 111,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=545,
            width=20,
            height=22,

            sourceX = 320,
            sourceY = 99,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=1,
            y=265,
            width=310,
            height=278,

            sourceX = 217,
            sourceY = 16,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 478,
    sheetContentHeight = 942
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
