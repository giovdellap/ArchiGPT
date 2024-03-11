const router = require("express").Router();

const cartCTRL = require("../controllers/CartController");

const { isAuth } = require("../helpers/jwt");


router.get("/:cartId", cartCTRL.getCart);
router.post("/:cartId/:productId", cartCTRL.addProduct);
router.delete("/:cartId/:productId",  cartCTRL.removeProduct);
router.delete("/:cartId", cartCTRL.deleteCart);

module.exports = router;