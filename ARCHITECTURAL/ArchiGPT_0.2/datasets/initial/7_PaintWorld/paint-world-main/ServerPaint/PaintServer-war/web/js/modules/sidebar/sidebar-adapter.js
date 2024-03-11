var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.Sidebar = Apora.Prototypes.Sidebar || {};
Apora.Prototypes.Sidebar.Adapter = function(options) {
    var self = this;
    this.motherboard = options.motherboard;
    var interface = Apora.Global.interface(self, {
        showSidebar: function(containerElement) {
            self.sidebarView = new Apora.Prototypes.App.Sidebar.View({
                containerElement: containerElement,
                adapter: interface,
                model: self.model,
            });
            Apora.Global.showSidebarViewProxy(self.sidebarView);
        },
        getCurrentRoute:function () {
            return self.motherboard.getCurrentRoute();
        },
        socketCloseApp:function(){
            socketCloseApp();
        }
    });
    this.model = new Apora.Prototypes.Sidebar.Model({},{
      adapter: interface,
    });
    return interface;
};
