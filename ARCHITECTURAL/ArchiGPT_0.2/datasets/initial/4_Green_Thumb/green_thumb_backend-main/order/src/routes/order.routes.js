const router = require("express").Router();

const orderCTRL = require("../controllers/OrderController");

const { isAuth } = require("../helpers/jwt");

router.get("/:orderId", orderCTRL.getOrder);
router.get("/user/:userId", orderCTRL.getUserOrders);
router.get("/seller/:sellerId", orderCTRL.getSellerOrders);
router.post("/:userId", orderCTRL.addOrder);
router.put("/:orderId", orderCTRL.updateOrder);
router.put("/delivered/:orderId", orderCTRL.deliveredOrder);
router.put("/deliveryInProgress/:orderId", orderCTRL.deliveryInProgress);
router.put("/rate/:orderId/:ratingValue", orderCTRL.rateOrder);

module.exports = router;