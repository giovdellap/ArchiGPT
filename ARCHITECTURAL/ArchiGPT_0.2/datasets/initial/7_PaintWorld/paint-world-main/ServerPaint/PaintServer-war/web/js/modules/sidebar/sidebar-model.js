var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.Sidebar = Apora.Prototypes.Sidebar || {};
Apora.Prototypes.Sidebar.Model = Backbone.Model.extend({
  initialize: function (models, options) {
      options = options || {};
      this.title = options.title || null;
      this.adapter = options.adapter || null;
  }
});
