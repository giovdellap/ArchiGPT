## API Documentation

# Input Url and Body Format

Url: endpoint/infer
Method: POST
Request Body:
{
    "sample":
        {
            "id": "pr1ID",
            "name": "pr1",
            "no_of_samples": 250,
            "Year_Birth": {
                "< 1960": 100,
                "1960_1975": 50,
                "> 1975" : 100
            },
            "Education": {
                "Graduation": 70,
                "PhD": 30,
                "Master": 50,
                "Basic" : 100
            },
            "Marital_Status": {
                "Married": 50,
                "Together": 50,
                "Single": 50,
                "Divorced" : 100
            },
            "Income": {
                "< 30k": 10,
                "30k_50k": 90,
                "50k_75k": 100,
                "> 75k": 50
            },
            "Kids": {
                "kids": 150,
                "No kids": 100
            },
            "purchase_method":{
                "Store": 150,
                "Online": 100
            }
        }
}

Response Body:
{
      "Education": {
          "Basic": 0.19156470343655355,
          "Graduation": 0.6089149285986072,
          "Master": 0.11753229768145974,
          "PhD": 0.08198807028337943
      },
      "Income": {
          "30k_50k": 0.3979007074486796,
          "50k_75k": 0.559300610181816,
          "< 30k": 0.007697666977272878,
          "> 75k": 0.03510101539223158
      },
      "Kids": {
          "No Kids": 0.7775215272336979,
          "kids": 0.22247847276630212
      },
      "Marital_Status": {
          "Divorced": 0.34314105441824094,
          "Married": 0.2933884681232333,
          "Single": 0.15235433354390784,
          "Together": 0.21111614391461783
      },
      "Year_Birth": {
          "1960_1975": 0.14574736975881994,
          "< 1960": 0.4637228080500425,
          "> 1975": 0.3905298221911376
      },
      "purchase_method": {
          "Online": 0.1058922580066764,
          "Store": 0.8941077419933237
      }
}