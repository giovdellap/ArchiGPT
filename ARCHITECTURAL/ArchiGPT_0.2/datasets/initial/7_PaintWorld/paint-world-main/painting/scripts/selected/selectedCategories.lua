selectedCategories = {
  {
    name = "animals",
    title = "حیوانات",
    numberOfPictures = 3,
  },
  {
    name = "war",
    title = "دفاع مقدس",
    numberOfPictures = 4,
  },
  {
    name = "family",
    title = "خانواده",
    numberOfPictures = 3,
  }
}

selectedCategories.directoryAddress = "images/selected/categories/"

for i = 1, #selectedCategories do
  selectedCategories[i].address = selectedCategories.directoryAddress..selectedCategories[i].name.."/"
  if selectedCategories[i].numberOfPictures ~= 0 then
    selectedCategories[i].thumbnail = selectedCategories[i].address..selectedCategories[i].name.." (1).png"
  end
end

selectedNames = {"ایمان 5 ساله از کرمانشاه", "حسین 6 ساله از قم", "مهدی 4 ساله از رشت", "محسن 7 ساله از اردبیل", "افشین 8 ساله از تهران"}
