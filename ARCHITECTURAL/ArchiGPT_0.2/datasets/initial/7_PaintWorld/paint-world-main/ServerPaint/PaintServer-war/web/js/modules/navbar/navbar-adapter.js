var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.Navbar = Apora.Prototypes.Navbar || {};
Apora.Prototypes.Navbar.Adapter = function(options) {
    var self = this;
    this.motherboard = options.motherboard;
    var interface = Apora.Global.interface(self, {
        showNavbarIndex: function(containerElement) {
            this.navbarIndex = new Apora.Prototypes.App.NavbarIndex.View({
                containerElement: containerElement,
                adapter: interface
            });
            Apora.Global.showNavbarViewProxy(this.navbarIndex);
        }
    });
    return interface;
}
