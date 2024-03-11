const express = require('express');
const router = express.Router();
const eventController = require('../controllers/eventController')

const Role = require('../config/role');
//------------ Welcome Route ------------//


router.get('/events', eventController.showEventsHandle);
router.post('/createEvent', eventController.createEventHandle);
router.post('/single_event', eventController.singleEventHandle);
router.get('/eventi_manager', eventController.showEventiManager);

module.exports = router;