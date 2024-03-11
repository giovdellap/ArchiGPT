var Apora = Apora || {};
Apora.Prototypes.Router = Backbone.Router.extend({
    containerElement: '#pages',
    initialize: function () {
        this.clearRoute();
        this.listenTo(this, 'route', this.saveRoute);
        this.boot = true;
        this.view = null;
        this.flagBack = false;
    },
    routes: {
        "back": "goToPrevPage",
        "": "bootLogin",
        "index-view": "indexView",
        "login-view": "loginView",
        "logout-view": "logoutView",
        "paint-table-view": "paintTableView",
        "problem": "problem",
    },
    setMotherboard: function (motherboard) {
        this.motherboard = motherboard;
    },
    saveRoute: function () {
        this.historyRoutes.push(Backbone.history.fragment);
    },
    clearRoute: function () {
        this.historyRoutes = [];
    },
    getCurrentRoute: function () {
        return this.historyRoutes[this.historyRoutes.length - 1];
    },
    popRoute: function () {
        return this.historyRoutes.pop();
    },
    goToPrevPage: function () {
        this.flagBack = true;
        var currentRoute = this.getCurrentRoute();
        if (currentRoute === "" || currentRoute === "login-view") {
            navigator.app.exitApp();
        } else {
            this.popRoute(); // pop current route
            var prevRoute = this.popRoute(); //pop prevRoute and save it
            Backbone.history.navigate(prevRoute, true);
            this.popRoute(); //pop duplicate of new route
        }
    },
    bootLogin: function () {
        var tempUserModel = this.motherboard.userAdapter.loadUserModel();
        if (tempUserModel) {
            var self = this;
            Backbone.history.navigate(rout.index, true);
        } else {
            Backbone.history.navigate(rout.login, true);
        }
    },
    problem: function () {
        this.motherboard.appAdapter.showSplash(this.containerElement);
        myApp.hideIndicator();
        var networkState = navigator.connection.type;
        if (networkState !== Connection.NONE) {
            showPopup(4, Messages.problem.internet);
        }
    },
    indexView: function () {
        var tempUserModel = this.motherboard.userAdapter.loadUserModel();
        if (tempUserModel) {
            this.clearRoute();
            this.motherboard.appAdapter.showIndexPage(this.containerElement);
        } else {
            Backbone.history.navigate(rout.login, true);
        }
    },
    loginView: function () {
        this.clearRoute();
        this.motherboard.userAdapter.showLogin(this.containerElement);
    },
    logoutView: function () {
        this.motherboard.userAdapter.logoutUser();
        Backbone.history.navigate(rout.login, true);
    },
    paintTableView: function () {
        var tempUserModel = this.motherboard.userAdapter.loadUserModel();
        if (tempUserModel) {
            this.clearRoute();
            this.motherboard.paintAdapter.showPaintTable(this.containerElement);
        } else {
            Backbone.history.navigate(rout.login, true);
        }
    },
});
