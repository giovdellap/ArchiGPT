const Product = require("../models/Product");

const CTRL = {};

CTRL.getProducts = (req, res) => {
    Product.find({})
        
        .exec((err, product) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                product, //todo 
            });
        });
};

CTRL.getListofProducts = (req, res) => {
    const {products} =req.body;
    Product.find({'_id': products})
        
        .exec((err, products) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                products,//todo
            });
        });
};

// mongoose.find({sport_type: {$in: ['Cricket', 'Football']}})

CTRL.getSellersProducts = (req,res) => {
    const { sellerId } = req.params;
    Product.find({'sellerId': sellerId})
        .exec((err, product) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                product,//todo
            });
        });
}

CTRL.getProduct = (req, res) => {
    const { productId } = req.params;
    Product.findById(productId)
        .populate("category")
        .exec((err, product) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err
                })
            }
            res.json({
                ok: true,
                product,//todo
            });
        });
};

CTRL.createProduct = (req, res) => {
    const newProduct = new Product({
        sellerId: req.body.sellerId,
        sellerName : req.body.sellerName,
        name: req.body.name,
        latin: req.body.latin,
        description: req.body.description,
        category: req.body.category,
        water: req.body.water,
        oxygen: req.body.oxygen,
        sunlight: req.body.sunlight,
        price: req.body.price,
        picture : req.body.picture,
        quantityStock : req.body.quantity
    });

    console.log('%o', newProduct);

    newProduct.save((err, product) => {
        if (err) {
            console.log('%o',err)
            return res.status(500).json({
                ok: false,
                err,
            });
        }

        return res.status(201).json({
            ok: true,
            product,//todo
        });
    });
};

CTRL.updateProduct = (req, res) => {
    const { productId } = req.params;

    Product.findByIdAndUpdate(
        productId,
        req.body,
        { new: true },
        (err, product) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err,
                });
            }

            return res.status(201).json({
                ok: true,
                product,//todo
            });
        }
    );
};

CTRL.updateQuantity=(req, res) => {

    const { productId } = req.params;
    const quantity = parseInt(req.params.quantity)

    Product.findOne({ '_id': productId }).exec((err, product) => {
        if (err || product == null) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        else if (product.quantity + quantity > 0) {

            Product.updateOne(
                { '_id': productId },
                {
                    $inc: { "quantity": quantity }
                }).then(result => {
                    if(result['modifiedCount']){
                    res.json({
                        ok: true,
                        result
                    });
                }
                else res.json({
                    ok: false,
                    result
                });
                })
        }
        else {
            Product.findByIdAndRemove(productId, (err, product) => {
                if (err) {
                    return res.status(500).json({
                        ok: false,
                        err,
                    });
                }
        
                return res.status(201).json({
                    ok: true,
                    product,//todo
                });
            })

        }
    
    
    })


    
}

CTRL.deleteProduct = (req, res) => {
    const { productId } = req.params;
    Product.findByIdAndRemove(productId, (err, product) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err,
            });
        }

        return res.status(201).json({
            ok: true,
            product,
        });
    });
};

module.exports = CTRL;