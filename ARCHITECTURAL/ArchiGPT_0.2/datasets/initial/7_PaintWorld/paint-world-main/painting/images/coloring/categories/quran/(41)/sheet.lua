--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:88d18586e639a0e546f56ac11332627c:45a71d2e4d810c765df13bc3f154fad7:cf8ab4992190eb44f97f06311ef326d7$
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
            width=728,
            height=170,

            sourceX = 61,
            sourceY = 476,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=731,
            y=1,
            width=212,
            height=112,

            sourceX = 113,
            sourceY = 528,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=831,
            y=115,
            width=70,
            height=20,

            sourceX = 488,
            sourceY = 499,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=731,
            y=115,
            width=98,
            height=22,

            sourceX = 544,
            sourceY = 510,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=1281,
            y=1,
            width=152,
            height=174,

            sourceX = 509,
            sourceY = 354,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=1435,
            y=1,
            width=134,
            height=188,

            sourceX = 500,
            sourceY = 183,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=1115,
            y=1,
            width=164,
            height=138,

            sourceX = 509,
            sourceY = 51,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=945,
            y=1,
            width=168,
            height=110,

            sourceX = 512,
            sourceY = 33,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=945,
            y=113,
            width=66,
            height=62,

            sourceX = 499,
            sourceY = 173,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 1570,
    sheetContentHeight = 190
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
