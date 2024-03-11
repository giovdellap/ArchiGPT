var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.App = Apora.Prototypes.App || {};
Apora.Prototypes.App.Index = Apora.Prototypes.App.Index || {};
Apora.Prototypes.App.Index.View = Backbone.View.extend({
  template: Handlebars.templates['app/app-index-view'],

  initialize: function(options) {
    this.adapter = options.adapter;
    this.container = options.container;
    this.model = options.model;
  },
  render: function() {
    var renderedContent = this.model.toJSON();
    this.$el.html(this.template(renderedContent));
    $(this.container).html(this.$el);
    this.adapter.showNavbarIndex('#navbar-index');
    this.adapter.showSidebar('#sidebar-index');
    $('body').removeClass("boxed");
    this.setActiveSidebar();
    return this;
  },
  show: function() {
    var self = this;
    self.render();
    var callBack = function(obj) {
      self.setPaintInfo(obj);
    }
    this.adapter.getPaintShortInfo(callBack);
  },
  setActiveSidebar: function() {
    $('#apora-user').removeClass("active");
    $('#apora-home').addClass("active");
  },
  setPaintInfo: function(obj) {
    this.model.paintShortInfo(obj);
    $('#today_paint').text(obj.today_paint);
    $('#confirmd_paint').text(obj.confirmd_paint);
    $('#ready_display_with_time').text(obj.ready_display_with_time);
    $('#ready_display_no_time').text(obj.ready_display_no_time);
    $('#all_paints').text(obj.all_paints);
  }
});
