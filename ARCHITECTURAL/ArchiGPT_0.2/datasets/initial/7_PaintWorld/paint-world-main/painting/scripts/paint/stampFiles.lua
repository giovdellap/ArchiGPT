local numberOfStamps = 21
stampFiles = {}
local directoryAddress = "images/paint/stamps/"

for i = 1, numberOfStamps do
  stampFiles[i] = directoryAddress.."stamps ("..i..").png"
end
