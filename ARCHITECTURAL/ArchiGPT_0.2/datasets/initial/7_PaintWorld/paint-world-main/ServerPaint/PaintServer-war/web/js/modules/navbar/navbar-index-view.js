var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.App = Apora.Prototypes.App || {};
Apora.Prototypes.App.NavbarIndex = Apora.Prototypes.App.NavbarIndex || {};
Apora.Prototypes.App.NavbarIndex.View = Backbone.View.extend({
    template: Handlebars.templates['navbar/navbar-index-view'],
    events: {
        'click #show-sidebar': 'showSidebar'
    },
    initialize: function (options) {
        this.containerElement = options.containerElement;
        this.adapter = options.adapter;
    },
    render: function () {
        this.$el.html(this.template());
        $(this.containerElement).append(this.$el);
        return this;
    },
    show: function () {
        this.render();
    },

});
