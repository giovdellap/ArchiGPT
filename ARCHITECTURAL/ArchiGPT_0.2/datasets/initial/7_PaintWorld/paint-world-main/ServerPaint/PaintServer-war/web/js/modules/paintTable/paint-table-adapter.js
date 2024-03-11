var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.PaintTable = Apora.Prototypes.PaintTable || {};
Apora.Prototypes.PaintTable.Adapter = function(options) {
    var self = this;
    this.motherboard = options.motherboard;
    var interface = Apora.Global.interface(self, {
        showPaintTable: function(containerElement) {
            // myApp.showPreloader(Messages.general.please_wait);
            self.categoryMainListView = new Apora.Prototypes.PaintTable.View({
                collection: self.collection,
                adapter: interface,
                containerElement: containerElement
            });
            Apora.Global.showPageViewProxy(self.categoryMainListView, self.collection);
        },
        fetchCollectionCategory: function() {
            if (self.collection.length <= 1) {
                self.collection.fetchCollection();
            }
        },
        getPaintShortInfo:function(callBack){
          self.collection.getPaintShortInfo(callBack);
        },
        showNavbarIndex:function (containerElement){
            self.motherboard.module.navbarAdapter.showNavbarIndex(containerElement);
        },
        showSidebar: function(containerElement) {
            self.motherboard.module.sidebarAdapter.showSidebar(containerElement);
        },
    });

    this.collection = new Apora.Prototypes.PaintTable.Collection({}, {
        sendNewStatusUrl: Apora.Config.URLs.sendNewStatus,
        infoNumPaintUrl: Apora.Config.URLs.infoNumPaint,
        todayPainUrl: Apora.Config.URLs.todayPaint,
        adapter: interface
    });

    return interface;
};
