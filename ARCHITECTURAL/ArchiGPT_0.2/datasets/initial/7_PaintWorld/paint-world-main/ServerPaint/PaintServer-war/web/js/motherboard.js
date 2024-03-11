var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};

Apora.Prototypes.MotherBoard = function(router, storage) {
    var self = this;
    this.router = router;
    this.storage = storage;
    this.navigate = function(route) {
        Backbone.history.navigate(route, true);
    };
    var interface = {
        navigate: function(route) {
            Backbone.history.navigate(route, true);
        },
        save: function(name, obj, lasting) {
            if (lasting) {

            } else {
                return self.storage.save(name, obj);
            }
        },
        load: function(name) {
            return self.storage.load(name);
        },
        clearStorage: function() {
            self.storage.clearStorage();
        },
        getCurrentRoute: function(){
            return self.router.getCurrentRoute();
        },
        log:function(module, content){
            if(Apora.Config.logger == Enum.log.debug){
                console.log(" **Module: ", module, " **Context: ", content);
            }
        },
        setMsgLang:function(appLang){
            self.router.setMsgLang(appLang);
        },
        module: self
    };
    this.appAdapter = new Apora.Prototypes.App.Adapter({
        motherboard: interface
    });
    this.navbarAdapter = new Apora.Prototypes.Navbar.Adapter({
        motherboard: interface
    });
    this.sidebarAdapter = new Apora.Prototypes.Sidebar.Adapter({
        motherboard: interface
    });
    this.userAdapter = new Apora.Prototypes.User.Adapter({
        motherboard: interface
    });
    this.paintAdapter = new Apora.Prototypes.PaintTable.Adapter({
        motherboard: interface
    });
    return this;
};
