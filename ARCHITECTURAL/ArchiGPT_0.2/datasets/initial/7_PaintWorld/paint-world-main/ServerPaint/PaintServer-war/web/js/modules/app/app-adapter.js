var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.App = Apora.Prototypes.App || {};

Apora.Prototypes.App.Adapter = function (options) {
    var self = this;
    this.motherboard = options.motherboard;
    var interface = Apora.Global.interface(self, {
        showIndexPage: function (containerElement) {
            self.indexView = new Apora.Prototypes.App.Index.View({
                model: self.model,
                adapter: interface,
                container: containerElement});
            Apora.Global.showPageViewProxy(self.indexView, self.model);
        },
        getUserModel:function(){
            return self.motherboard.module.userAdapter.getUserModel();
        },
        getPaintShortInfo:function(callBack){
          self.motherboard.module.paintAdapter.getPaintShortInfo(callBack);
        },
        showSidebar: function(containerElement) {
            self.motherboard.module.sidebarAdapter.showSidebar(containerElement);
        },
        showNavbarIndex:function (containerElement){
            self.motherboard.module.navbarAdapter.showNavbarIndex(containerElement);
        },
        showPaintTable:function (containerElement){
            self.motherboard.module.categoryAdapter.showPaintTable(containerElement);
        },
    });
    this.model = new Apora.Prototypes.App.Model({},{
        adapter: interface
    });
    return interface;
};
