const router = require("express").Router();

const chatCTRL = require("../controllers/ChatController");

const { isAuth } = require("../helpers/jwt");

router.get("/:userId", chatCTRL.getMessage);
router.post("/", chatCTRL.createChat);
router.put("/:userId", chatCTRL.updateMessage);
// router.put("/:orderId", isAuth, orderCTRL.updateOrder);

// router.get("/", categoryCTRL.getCategories);
// router.get("/:categoryId", categoryCTRL.getCategory);
// router.post("/", isAuth, categoryCTRL.createCategory);
// router.put("/:categoryId", isAuth, categoryCTRL.updateCategory);
// router.delete("/:categoryId", isAuth, categoryCTRL.deleteCategory);

module.exports = router;