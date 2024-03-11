const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const ProductSchema = new Schema({
  sellerId: {
    type: Schema.Types.ObjectId,
    required: true,
  },
  sellerName:  {
    type: String,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  latin: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
 
  category: {
    type: String,
    // ref: "Category",
  },
  water: {
    type: String,
    required: true,
  },
  oxygen: {
    type: Number,
    required: true,
  },
  sunlight: {
    type: String,
    required: true,
  },
  price: Number,
  picture: {
    type: String,
    
  },
 quantityStock: {
  type: Number,
  default: 1
}, 
  created_at: {
    type: Date,
    default: Date.now,
  },
});

module.exports = mongoose.model("Product", ProductSchema);