var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.User = Apora.Prototypes.User || {};
Apora.Prototypes.User.Model = Backbone.Model.extend({
    initialize: function (attributes, options) {
        this.adapter = options.adapter || null;
        this.urlLogin = options.urlLogin || null;
    },
    setUserData: function (data) {
        for (var eachItem in data) {
            this.set(eachItem, data[eachItem]);
        }
    },
    loadUserModel: function () {
        return this.adapter.loadUserModel();
    },
    logout: function () {
        this.adapter.clearStorage();
        this.clear();
    },
    getUserModel: function () {
        return this.loadUserModel();
    },
    setUserModel: function (model) {
        this.set(model);
        Apora.Config.headers.user_code = this.get('user_code');
    },
    test:function(){
    },
    sendConfirmCode: function (userName, password, callBack) {
        var tempData = JSON.stringify({
            username: userName,
            password: password
        });
        var self = this;
        var successCallBack = function(respone){
            self.setUserModel(respone);
            self.adapter.saveUserModel(respone);
            Backbone.history.navigate(rout.index, true);
        };
        var errorCallBack = function(code){
            callBack(code);
        };
        postAjax(self.urlLogin, tempData, Apora.Config.URLs.loginMethod, successCallBack, errorCallBack);
    }
});
