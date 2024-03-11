const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const OrderSchema = new Schema({
  userId: {
    type: Schema.Types.ObjectId,
  },
  fullname: String,
  address: String,
  city: String, 
  payment: String,
  cart: [
    {
      sellerId: Schema.Types.ObjectId,
      productId: {
        type: Schema.Types.ObjectId,
      },
      qty: Number,
      price: Number
    },
  ],
  total: {
    type: Number,
  },
  created_at: {
    type: Date,
    default: Date.now,
  },
  latitude: {
    type: Number,
    
  },
  longitude: {
      type: Number,
      
  },
  delivered: {
    type:Boolean,
    default: false
  },
  deliveryInProgress: {
    type:Boolean,
    default: false
  },
  ratingValue :{
    type: Number,
    default: 0
  }
});

module.exports = mongoose.model("Order", OrderSchema);