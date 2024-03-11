const e = require("express");
const Cart = require("../models/Cart");

const CTRL = {};

CTRL.getCart = (req, res) => {
    const { cartId } = req.params;
    Cart.find({"cartId": cartId}).exec((err, cart) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        res.json({
            ok: true,
            cart,
        });
    });
};

CTRL.addProduct = (req, res) => {
    const { cartId, productId } = req.params;
    const qty = parseFloat(req.body.qty);
    const price = parseFloat(req.body.price);
    const {sellerId}= req.body;
    Cart.find({ 'cartId': cartId }).exec((err, cart) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        else if (cart.length > 0) {// cart exists
            found = false;
            cart[0].cartItems.forEach(p => {
                if (p.productId == productId) {//product exist in cart =>  add p.qty +=qty
                    found = true;
                    Cart.updateOne(
                        { 'cartId': cartId },
                        {
                            $set: { "cartItems.$[x].qty": p.qty + qty, "cartItems.$[x].price": p.price + price },
                            $inc: { "total": price }
                        },
                        { arrayFilters: [{ "x.productId": productId }] })
                        .then(result => {
                            res.json({
                                ok: true,
                                result
                            });
                        })
                }
            })
            if (!found) {
                Cart.updateOne({ 'cartId': cartId },
                    {
                        $push: { cartItems: [{ sellerId :sellerId, productId: productId, qty: qty, price: price }] },
                        $inc: { "total": price }
                    })
                    .then(result => {
                        res.json({
                            ok: true,
                            result
                        })
                    })
            }
        }
        else if (cart.length == 0) {
            const newCart = new Cart({
                cartId: cartId,
                cartItems: [{ sellerId :sellerId, productId: productId, qty: qty, price: price }],
                total: price
            })
            newCart.save().then((cart) => {
                res.json({
                    ok: true,
                    cart,
                });
            })
        }
    }
    )
}

CTRL.deleteCart = (req, res) => {
    const { cartId } = req.params
    Cart.findOneAndRemove({ 'cartId': cartId }, (err, cart) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err,
            });
        }
        return res.status(201).json({
            ok: true,
            cart,
        });
    });
};




CTRL.removeProduct = (req, res) => {
    const { cartId, productId } = req.params;
    Cart.find({ 'cartId': cartId }).exec((err, cart) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        else if (cart.length > 0) {// cart exists
            found = false;
            cart[0].cartItems.forEach(p => {
                if (p.productId == productId) {
                    found = true;
                    Cart.updateOne(
                        { 'cartId': cartId },
                        {
                            $pull: { cartItems: { sellerId: p.sellerId, productId: p.productId, qty: p.qty, price: p.price, _id: p._id } },
                            $inc: { "total": -p.price }
                        })
                        .then(result => {
                            res.json({
                                ok: true,
                                result
                            });
                        })
                }
            })
            if (!found) {
                res.json({
                    ok: 'Not found',
                })
            }
        }
        else if (cart.length == 0) {
            res.json({
                ok: 'Not found',
            })
        }
    }
    )
}

module.exports = CTRL;