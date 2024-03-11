--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:f4312e8d33ce9bbbe4d3f8e0e6c826a5:13513058d091c87812452bba94edf20b:cf8ab4992190eb44f97f06311ef326d7$
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
            y=515,
            width=196,
            height=182,

            sourceX = 254,
            sourceY = 367,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=967,
            y=441,
            width=42,
            height=18,

            sourceX = 229,
            sourceY = 354,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=835,
            y=393,
            width=36,
            height=30,

            sourceX = 426,
            sourceY = 506,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=835,
            y=425,
            width=30,
            height=38,

            sourceX = 257,
            sourceY = 540,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=641,
            y=515,
            width=102,
            height=86,

            sourceX = 395,
            sourceY = 339,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=339,
            y=629,
            width=86,
            height=62,

            sourceX = 197,
            sourceY = 304,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=873,
            y=393,
            width=92,
            height=50,

            sourceX = 478,
            sourceY = 332,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=967,
            y=393,
            width=54,
            height=46,

            sourceX = 463,
            sourceY = 235,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=427,
            y=629,
            width=44,
            height=56,

            sourceX = 536,
            sourceY = 274,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=549,
            y=515,
            width=90,
            height=92,

            sourceX = 282,
            sourceY = 291,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 11
            x=443,
            y=515,
            width=104,
            height=94,

            sourceX = 299,
            sourceY = 292,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 12
            x=473,
            y=611,
            width=398,
            height=86,

            sourceX = 324,
            sourceY = 556,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 13
            x=761,
            y=1,
            width=262,
            height=390,

            sourceX = 391,
            sourceY = 155,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 14
            x=745,
            y=515,
            width=88,
            height=84,

            sourceX = 502,
            sourceY = 168,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 15
            x=761,
            y=465,
            width=74,
            height=30,

            sourceX = 511,
            sourceY = 532,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 16
            x=1,
            y=1,
            width=758,
            height=512,

            sourceX = 202,
            sourceY = 21,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 17
            x=339,
            y=515,
            width=102,
            height=112,

            sourceX = 389,
            sourceY = 420,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 18
            x=761,
            y=393,
            width=38,
            height=20,

            sourceX = 394,
            sourceY = 513,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 19
            x=199,
            y=515,
            width=138,
            height=182,

            sourceX = 160,
            sourceY = 351,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 1024,
    sheetContentHeight = 698
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
}

function SheetInfo:getSheet()
    return self.sheet;
end

function SheetInfo:getFrameIndex(name)
    return self.frameIndex[name];
end

return SheetInfo
