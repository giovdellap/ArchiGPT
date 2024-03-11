## API Documentation

# Input Url and Body Format

Url: endpoint/auth/signup
Method: POST
Request Body:
{
    "username" : String,
    "password" : String,
    "full_name" : String,
    "email" : String
}

Response Body:
{
    "jwt": TokenKey
}


Url: endpoint/auth/login
Method: POST
Request Body:
{
    "username" : String,
    "password" : String
}

Response Body:
{
    "jwt": TokenKey
}