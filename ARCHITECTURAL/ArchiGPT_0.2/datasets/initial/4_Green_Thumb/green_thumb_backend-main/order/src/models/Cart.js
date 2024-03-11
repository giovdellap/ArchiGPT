const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const CartSchema = new Schema({
    cartId: {
        type: Schema.Types.ObjectId,
        unique: true,
    },
    cartItems: [
        {
            sellerId: {
                type: Schema.Types.ObjectId,
            },
            productId: {
                type: Schema.Types.ObjectId,
            },
            qty: Number,
            price: Number
        },
    ],
    total: Number,

    created_at: {
        type: Date,
        default: Date.now,
    },
});

module.exports = mongoose.model("Cart", CartSchema);