var defaultProfile = 
{
    "name": "",
    "no_of_samples": 0,
    "infered_samples": 0,
    "Year_Birth": {
         "< 1960": 0,
         "1960_1975": 0,
         "> 1975" : 0
     },
     "Education": {
         "Graduation": 0,
         "PhD": 0,
         "Master": 0,
         "Basic" : 0
     },
     "Marital_Status": {
         "Married": 0,
         "Together": 0,
         "Single": 0,
         "Divorced" : 0
     },
     "Income": {
           "< 30k": 0,
           "30k_50k": 0,
           "50k_75k": 0,
           "> 75k": 0
       },
     "Kids": {
         "kids": 0,
         "No kids": 0
     },
    "purchase_method":{
        "Store": 0,
        "Online": 0
    }
}

module.exports = defaultProfile