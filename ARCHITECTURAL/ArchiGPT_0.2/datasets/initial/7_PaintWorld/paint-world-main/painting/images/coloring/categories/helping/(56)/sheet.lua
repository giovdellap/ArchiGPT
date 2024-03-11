--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:91016d75e82af9a93ae44cafca4bdcde:a3228881e6e79c6f78cfd4b123c4edfc:cf8ab4992190eb44f97f06311ef326d7$
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
            y=637,
            width=278,
            height=138,

            sourceX = 28,
            sourceY = 448,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=401,
            y=729,
            width=66,
            height=44,

            sourceX = 236,
            sourceY = 579,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=207,
            y=875,
            width=118,
            height=42,

            sourceX = 307,
            sourceY = 522,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=365,
            y=775,
            width=110,
            height=60,

            sourceX = 391,
            sourceY = 575,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1,
            y=777,
            width=204,
            height=156,

            sourceX = 156,
            sourceY = 251,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=265,
            y=379,
            width=192,
            height=114,

            sourceX = 203,
            sourceY = 323,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=281,
            y=627,
            width=118,
            height=108,

            sourceX = 164,
            sourceY = 148,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=389,
            y=495,
            width=104,
            height=96,

            sourceX = 195,
            sourceY = 175,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=389,
            y=593,
            width=66,
            height=30,

            sourceX = 263,
            sourceY = 429,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=327,
            y=875,
            width=60,
            height=50,

            sourceX = 361,
            sourceY = 394,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=1,
            y=379,
            width=262,
            height=256,

            sourceX = 281,
            sourceY = 98,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=265,
            y=495,
            width=122,
            height=130,

            sourceX = 325,
            sourceY = 11,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=207,
            y=777,
            width=156,
            height=96,

            sourceX = 316,
            sourceY = 316,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=413,
            y=837,
            width=64,
            height=48,

            sourceX = 470,
            sourceY = 345,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=401,
            y=625,
            width=66,
            height=52,

            sourceX = 439,
            sourceY = 396,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=457,
            y=593,
            width=30,
            height=12,

            sourceX = 491,
            sourceY = 393,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=365,
            y=837,
            width=46,
            height=34,

            sourceX = 520,
            sourceY = 348,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=281,
            y=737,
            width=62,
            height=22,

            sourceX = 143,
            sourceY = 396,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=401,
            y=679,
            width=78,
            height=48,

            sourceX = 326,
            sourceY = 298,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 20
            x=1,
            y=1,
            width=480,
            height=376,

            sourceX = 390,
            sourceY = 274,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 494,
    sheetContentHeight = 934
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
    ["18"] = 18,
    ["19"] = 19,
    ["20"] = 20,
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
