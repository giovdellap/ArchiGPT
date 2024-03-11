## API Documentation

# Input Url and Body Format

# ---------------------------------------------------------
Url: endpoint/recommendation/:projectName
Method: GET
Request Body:
Response Body:
{
    "recommendation": [
        {
            "_id": "61f0826cdb4a0b30fa40d47f",
            "Year_Birth": {
                "older_than_1960": 0.31045162527290654,
                "from_1960_to_1975": 0.22241787652995493,
                "younger_than_1975": 0.4671304981971386
            },
            "Education": {
                "Graduation": 0.5529769109308699,
                "PhD": 0.1595423865555678,
                "Master": 0.1364131315938201,
                "Basic": 0.1510675709197421
            },
            "Marital_Status": {
                "Married": 0.37019713452861575,
                "Together": 0.2126806387672272,
                "Single": 0.29491470948576215,
                "Divorced": 0.12220751721839489
            },
            "Income": {
                "less_than_30k": 0.23479667909362484,
                "from_30k_to_50k": 0.09093654813507908,
                "from_50k_to_75k": 0.26440621035277945,
                "above_75k": 0.40986056241851665
            },
            "Kids": {
                "have_kids": 0,
                "no_kids": 1
            },
            "purchase_method": {
                "Store_Purchases": 0.9539652849971999,
                "Online_Purchases": 0.046034715002800104
            },
            "name": "MntMeatProducts"
        },{}, ...
    ]

    ||

    "error": "Error: Results not found"

    ||

    "error": "Error: Project Not found"
}

# ---------------------------------------------------------
Url: endpoint/analytics/:projectName/customer/:id
Method: GET
Request Body:
Response Body:
{
    "customer": "9477",
    "purchases": [
        {
            "_id": "toyota",
            "Quantity": 6
        },
        {
            "_id": "saab",
            "Quantity": 3
        },
        {
            "_id": "plymouth",
            "Quantity": 3
        }]
}

# ---------------------------------------------------------
Url: endpoint/analytics/:projectName/stat/products
Method: GET
Request Body:
Response Body:
{
    "products_analytics": [
        {
            "_id": "isuzu",
            "Quantity": 18
        },
        {
            "_id": "audi",
            "Quantity": 14
        },]
}

# ---------------------------------------------------------
Url: endpoint/analytics/:projectName/stat/customers
Method: GET
Request Body:
Response Body:
{
    "customers_analytics": [
        {
            "_id": 9477,
            "Purchased": 18
        },
        {
            "_id": 4047,
            "Purchased": 16
        }]
}

# ---------------------------------------------------------
Url: endpoint/projects/
Method: GET
Request Body:
Response Body:
{
    "projects": [pr1, pr2, ..]
}

# ---------------------------------------------------------
Url: endpoint/projects/new
Method: POST
Request Body:
{
    "project": "my_proj_1"
}

Response Body:
{
    "error": "Error: Project name is already used"
    ||
    "message": "Operation Successful"
}

# ---------------------------------------------------------
Url: endpoint/likes/:projectName/:productName
Method: GET
Request Body:
Response Body:
{
    "likes": 4,
    "unLikes": 0
}

# ---------------------------------------------------------
Url: endpoint/likes/:projectName/:productName/like
Method: POST
Request Body:
Response Body:
{
    "message": "Operation Successful"
}

# ---------------------------------------------------------
Url: endpoint/likes/:projectName/:productName/unlike
Method: POST
Request Body:
Response Body:
{
    "message": "Operation Successful"
}
