const router = require("express").Router();

const payCTRL = require("../controllers/PayController");

router.get("/create", payCTRL.createPayment);
router.get("/execute", payCTRL.executePayment);


module.exports = router;