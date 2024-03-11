const Order = require("../models/Order");
const ObjectId = require('mongoose').Types.ObjectId
const CTRL = {};

CTRL.getOrder = (req, res) => {
    const { orderId } = req.params;
    Order.findById(orderId).exec((err, order) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        res.json({
            ok: true,
            order,
        });
    });
};

CTRL.getUserOrders = (req, res) => {
    const { userId } = req.params;
    Order.find({'userId':userId}).exec((err, orders) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        res.json({
            ok: true,
            orders,
        });
    });
};

CTRL.getSellerOrders = (req, res) => {
    const { sellerId } = req.params;
    Order.aggregate([
    {
        $match: {"cart.sellerId": ObjectId(sellerId)}
    },
    {
    $addFields: {
      cart: {
        $filter: {
          input: "$cart",
          cond: {
            $eq: [
              "$$this.sellerId",
              ObjectId(sellerId)
            ]
          }
        }
      }
    }
  }
])
.exec((err, orders) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        res.json({
            ok: true,
            orders,
        });
    });
};

CTRL.addOrder = (req, res) => {
    const { userId, fullname,address,city,payment, cart, total } = req.body;

    const newOrder = new Order({
        userId: userId,
        fullname : fullname,
        address : address, 
        city: city,
        payment: payment, 
        cart: cart, 
        total: total,
        latitude: 41.89021,
        longitude: 12.49223
    })
    newOrder.save().then((err, order) => {
    if (err) {
        console.log('%o',err)
        return res.status(500).json({
            ok: false,
            err,
        });
    }
    return res.status(201).json({
        ok: true,
        order,
    })});
}

CTRL.updateOrder = (req, res) => {
    const { orderId } = req.params;
    Order.findByIdAndUpdate(orderId, { latitude: req.body.latitude, longitude: req.body.longitude })
        .exec((err, ord) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                ord,
            });
        });
}

CTRL.deliveredOrder = (req, res) => {
    const { orderId } = req.params;
    Order.findByIdAndUpdate(orderId, { delivered: true, deliveryInProgress: false  })
        .exec((err, ord) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                ord,
            });
        });
}

CTRL.deliveryInProgress = (req, res) => {
    const { orderId } = req.params;
    Order.findByIdAndUpdate(orderId, { deliveryInProgress: true })
        .exec((err, ord) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                ord,
            });
        });
}

CTRL.rateOrder = (req, res) => {
    const { orderId, ratingValue } = req.params;
   
    Order.findByIdAndUpdate(
        orderId,
        {
            $inc: { "ratingValue": ratingValue }
        },
        { new: true },
        (err, order) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err,
                })
            }
            console.log('%o',order);
            return res.status(201).json({
                ok: true,
                order,
            });
        }
    );
}

module.exports = CTRL;