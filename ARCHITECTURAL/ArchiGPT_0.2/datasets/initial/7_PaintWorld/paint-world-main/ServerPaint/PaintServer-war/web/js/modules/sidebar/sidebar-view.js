var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.App = Apora.Prototypes.App || {};
Apora.Prototypes.App.Sidebar = Apora.Prototypes.App.Navbar || {};
Apora.Prototypes.App.Sidebar.View = Backbone.View.extend({
    template: Handlebars.templates['sidebar/sidebar-view'],
    initialize: function(options) {
        this.containerElement = options.containerElement;
        this.adapter = options.adapter;
    },
    render: function() {
        this.$el.html(this.template());
        $(this.containerElement).html(this.$el);
        return this;
    },
    show: function() {
      var self = this;
      self.render();
    }
});
