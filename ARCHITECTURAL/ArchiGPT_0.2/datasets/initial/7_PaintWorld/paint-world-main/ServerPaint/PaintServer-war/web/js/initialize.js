//Initializer function which is only on android application
function initializeApp() {

    var offlineflag = false;
    document.addEventListener("offline", function () {
        if (!offlineflag) {
            offlineflag = true;
            myApp.addNotification({
                message: Messages.problem.internet
            });
            showPopup(4, Messages.problem.internet);
        }
        var interval = setInterval(function () {
            var networkState = navigator.connection.type;
            if (networkState !== Connection.NONE) {
                offlineflag = false;
                myApp.closeNotification($('.notification-item')[0]);
                clearInterval(interval);
                Backbone.history.navigate(rout.problem, true);
            }
        }, 1000);
    }, false);
    
//    ParsePushPlugin.subscribe("apora" + Apora.Config.appCodeGlobal, function (msg) {
//        ParsePushPlugin.getInstallationId(function (id) {}, function (e) {});
//        ParsePushPlugin.getInstallationObjectId(function (id) {}, function (e) {});
//    }, function (e) {});
//    ParsePushPlugin.getSubscriptions(function (subscriptions) {}, function (e) {});
//    if (window.ParsePushPlugin) {
//        ParsePushPlugin.on('receivePN', function (pn) {
//            showPopup(0, Messages.notification.new);
//        });
//    }
    FastClick.attach(document.body);
}

//general initializer function
function initialize() {
    document.addEventListener("deviceready", initializeApp, false);
    document.addEventListener("backbutton", function (e) {
        Backbone.history.navigate(rout.back, true);
    });
    Apora.Config = new Apora.Prototypes.Config();
    Apora.Global = new Apora.Prototypes.Global();
    var storage = new Apora.Prototypes.Storage();
    var router = new Apora.Prototypes.Router();
    var motherboard = new Apora.Prototypes.MotherBoard(router, storage);
    router.setMotherboard(motherboard);
    Apora.Config = new Apora.Prototypes.Config();
    Backbone.history.start();
}
//calling initializer
initialize();
