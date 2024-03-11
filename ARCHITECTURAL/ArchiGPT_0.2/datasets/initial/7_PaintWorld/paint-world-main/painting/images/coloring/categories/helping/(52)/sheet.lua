--
-- created with TexturePacker (http://www.codeandweb.com/texturepacker)
--
-- $TexturePacker:SmartUpdate:25ac7c08082770b7a67c16b33fda15cb:a35a9373a927c8b711e18b89dc442833:cf8ab4992190eb44f97f06311ef326d7$
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
            width=332,
            height=346,

            sourceX = 17,
            sourceY = 296,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 2
            x=1,
            y=641,
            width=478,
            height=284,

            sourceX = 382,
            sourceY = 388,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 3
            x=269,
            y=519,
            width=112,
            height=116,

            sourceX = 455,
            sourceY = 212,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 4
            x=269,
            y=349,
            width=216,
            height=168,

            sourceX = 408,
            sourceY = 266,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 5
            x=399,
            y=91,
            width=70,
            height=38,

            sourceX = 302,
            sourceY = 248,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 6
            x=383,
            y=519,
            width=98,
            height=92,

            sourceX = 263,
            sourceY = 97,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 7
            x=335,
            y=1,
            width=84,
            height=88,

            sourceX = 245,
            sourceY = 33,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 8
            x=335,
            y=141,
            width=66,
            height=46,

            sourceX = 273,
            sourceY = 272,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 9
            x=335,
            y=91,
            width=62,
            height=48,

            sourceX = 370,
            sourceY = 223,
            sourceWidth = 960,
            sourceHeight = 672
        },
        {
            -- 10
            x=1,
            y=349,
            width=266,
            height=290,

            sourceX = 199,
            sourceY = 147,
            sourceWidth = 960,
            sourceHeight = 672
        },
    },
    
    sheetContentWidth = 486,
    sheetContentHeight = 926
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
