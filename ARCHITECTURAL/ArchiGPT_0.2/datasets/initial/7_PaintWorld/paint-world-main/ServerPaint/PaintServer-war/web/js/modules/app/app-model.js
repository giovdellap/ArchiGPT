var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.App = Apora.Prototypes.App || {};
Apora.Prototypes.App.Model = Backbone.Model.extend({
  initialize: function(attributes, options) {
    this.adapter = options.adapter;
  },
  getUserModel: function() {
    return this.adapter.getUserModel();
  },
  paintShortInfo:function (obj) {
    this.set(obj);
  }

});
