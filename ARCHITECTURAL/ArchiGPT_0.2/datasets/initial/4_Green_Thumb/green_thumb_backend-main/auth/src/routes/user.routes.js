const router = require("express").Router();

const userCTRL = require("../controllers/UserController");

const { isAuth } = require("../helpers/jwt");

router.post("/login", userCTRL.login);
router.post("/register", userCTRL.register);
router.get("/:userId", userCTRL.getUser);
router.put("/:userId", userCTRL.updateUser);
router.put("/info/:userId", userCTRL.updateUserInfo);
router.put("/rate/:ratingValue", userCTRL.rateUsers);



module.exports = router;