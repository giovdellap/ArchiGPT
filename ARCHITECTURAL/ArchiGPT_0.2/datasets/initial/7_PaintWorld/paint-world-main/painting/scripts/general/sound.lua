local palyingSound = false
local soundStopped = true
local soundOn, soundOff
local isSoundButtonRendered = false
local sound
local wasSoundPlaying = nil
soundChannel = 1
clickSoundCannel = 2
narrationSoundChannel = 3
clickSound = audio.loadSound( "sounds/Click.ogg" )
clickPlayOptions = {
  channel = clickSoundCannel
}

local backgroundSound = audio.loadSound( "sounds/home/home.mp3" )
local playOptions = {
  channel = soundChannel,
  loops = -1
}

local narration = nil
local narrationFile = nil

function isSoundPlaying()
  return playingSound
end

function pauseSound()
  if playingSound then
    audio.pause(sound)
    playingSound = false
  end
end

function playSound()
  if playingSound then
    -- no op
  else
    if soundStopped then
      sound = audio.play(backgroundSound, playOptions)
      soundStopped = false
    else
      audio.resume(sound)
    end
  end
  playingSound = true
end

function stopSound()
  audio.stop(soundChannel)
  soundStopped = true
  playingSound = false
end

function stopNarration (narration)
  audio.stop(narrationSoundChannel)
  if wasSoundPlaying then
    playSound()
    wasSoundPlaying = false
  end
  narration = nil
  narrationFile = nil
end

function playNarration(narrationFile)
  if isSoundPlaying() then
    pauseSound()
    wasSoundPlaying = true
  end
  narrationFile = audio.loadSound( narrationFile )
  local narrationOptions = {
    channel = narrationSoundChannel,
    onComplete = stopNarration
  }
  narration = audio.play(narrationFile, narrationOptions)
end



function musicHandler(event)
  if event.phase == "began" then
    audio.stop(clickSoundCannel)
    audio.play(clickSound, clickPlayOptions)
    event.target.width = event.target.width * clickResizeFactor
    event.target.height = event.target.height * clickResizeFactor
    display.getCurrentStage():setFocus( event.target )
    event.target.isFocus = true
  end
  if not event.target.isFocus then
    return false
  end
  if event.phase == "ended" then
    event.target.width = event.target.width / clickResizeFactor
    event.target.height = event.target.height / clickResizeFactor
    if event.target.name == "soundOn" then
      pauseSound()
      if isSoundButtonRendered then
        soundOff.isVisible = true
        soundOn.isVisible = false
      end
    elseif event.target.name == "soundOff" then
      playSound()
      if isSoundButtonRendered then
        soundOn.isVisible = true
        soundOff.isVisible = false
      end
    end
    event.target.isFocus = false
    display.getCurrentStage():setFocus(nil)
  end
end

function renderSoundButton()
  soundOn = display.newImage( "images/home/sound on.png")
  soundOn.x = soundButtonX
  soundOn.y = soundButtonY
  soundOn.name = "soundOn"
  soundOn.isVisible = false
  soundOff = display.newImage( "images/home/sound off.png")
  soundOff.x = soundButtonX
  soundOff.y = soundButtonY
  soundOff.name = "soundOff"
  soundOff.isVisible = false
  if playingSound then
    soundOn.isVisible = true
  else
    soundOff.isVisible = true
  end
  soundOff:addEventListener("touch", musicHandler)
  soundOn:addEventListener("touch", musicHandler)
  isSoundButtonRendered = true
end

function closeSoundButton()
  display.remove(soundOn)
  display.remove(soundOff)
  soundOn = nil
  soundOff = nil
  isSoundButtonRendered = false
end

playSound()
