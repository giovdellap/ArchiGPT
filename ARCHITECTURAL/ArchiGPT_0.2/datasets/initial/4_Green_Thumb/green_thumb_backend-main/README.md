# Authentication microservice
Nodejs server and mongodb database

## ports
server listen at port 3000 and db listen at port 27017

## register 
POST to localhost:3000/users/register
```
   seller: {
        type: Boolean,
        required: true,
    },
    fullname: {
        type: String,
        required: true,
    },

    birth: {
        type: Date,
        required: true,
    },
    fiscalcode: {
        type: String,
        required: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
    },
    password: {
        type: String,
        required: true,
    },
```
## login 
POST to localhost:3000/users/login
```
email: {
        
        type: String,
        required: true,
        
},

password: {
        
        type: String,
        required: true,
        
}
```
## build:
docker-compose up --build

## start / stop
docker-compose up

docker-compose down

# Inventory microservice
Nodejs server and mongodb database

### Category model
localhost:3002/categories
```
    name: {
        type: String,
        required: true,
        unique: true,
    },
    description: {
        type: String,
        required: false,
    },
    status: {
        type: Boolean,
        default: true,
    },
    created_at: {
        type: Date,
        default: Date.now,
    }
```

Category methods:

GET on localhost:3002/categories/ -> get categories
GET on localhost:3002/categories/:id -> get category
POST on localhost:3002/categories/ -> create new category
PUT on localhost:3002/categories/:id -> update category
DELETE on localhost:3002/categories/:id -> delete category


### Product model
localhost:3002/products
```
    code: {
    type: String,
    unique: true,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: false,
  },
  category: {
    type: Schema.Types.ObjectId,
    ref: "Category",
  },
  price: Number,
  stock: Number,
  image: {
    type: String,
    maxlength: 512,
  },
  status: {
    type: Boolean,
    default: true,
  },
  created_at: {
    type: Date,
    default: Date.now,
  },
```
Products methods:


GET on localhost:3002/products/ -> get products
GET on localhost:3002/products/:id -> get product
POST on localhost:3002/products/ -> create new product
PUT on localhost:3002/products/:id -> update product
DELETE on localhost:3002/products/:id -> delete product
