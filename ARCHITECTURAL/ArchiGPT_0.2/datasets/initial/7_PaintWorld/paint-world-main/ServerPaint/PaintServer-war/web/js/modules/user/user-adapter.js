var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.User = Apora.Prototypes.User || {};

Apora.Prototypes.User.Adapter = function(options) {
    var self = this;
    this.motherboard = options.motherboard;
    ///////////////////////////////////////////////////////////////////////////
    var interface = Apora.Global.interface(self, {
        showLogin: function(containerElement) {
            self.userLoginView = new Apora.Prototypes.User.Login.View({
                model: self.model,
                adapter: interface,
                containerElement: containerElement
            });
            Apora.Global.showPageViewProxy(self.userLoginView, self.model);
        },
        logoutUser: function() {
            self.model.logout();
        },
        getUserModel: function() {
            return self.model.getUserModel();
        },
        getUserModelFromServer:function(callBack){
            self.model.getUser(callBack);
        },
        setUserModel: function(model) {
            return self.model.setUserModel(model);
        },
        saveUserModel: function(data) {
            if (data == null) {
                self.motherboard.save("userModel", null);
                Apora.Config.headers.user_code = null;
            } else {
                self.motherboard.save("userModel", data);
                Apora.Config.headers.user_code = data.user_code;

            }
        },
        loadUserModel: function() {
            var userModel = self.motherboard.load("userModel");
            self.model.setUserModel(userModel);

            return userModel;
        },
        clearStorage: function() {
            self.motherboard.clearStorage();
        }
    });
    ///////////////////////////////////////////////////////////////////////////
    this.model = new Apora.Prototypes.User.Model({}, {
        adapter: interface,
        urlLogin: Apora.Config.URLs.login,
    });
    return interface;
};
