var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.PaintTable = Apora.Prototypes.PaintTable || {};
Apora.Prototypes.PaintTable.View = Backbone.View.extend({
  template: Handlebars.templates['paintTable/paint-table-view'],
  events: {
    'click .show-info-event': 'showInfoEvent',
    'click .change-status-event': 'changeStatusEvent',
  },
  initialize: function(options) {
    this.collection = options.collection;
    this.adapter = options.adapter;
    this.containerElement = options.containerElement;
    this.itemSelected = [];
    this.selectedModel = null;
  },
  render: function() {
    var renderedContent = this.collection.toJSON();
    console.log(renderedContent);
    this.$el.html(this.template(renderedContent));
    $(this.containerElement).html(this.$el);
    this.adapter.showNavbarIndex('#navbar-index');
    this.adapter.showSidebar('#sidebar-index');
    $('body').removeClass("boxed");

    return this;
  },
  show: function() {
     this.collection.bind("reset", _.bind(this.render, this));
     this.collection.fetchCollection();
  },
  showInfoEvent: function(e) {
    e.preventDefault();
    var tempId = e.currentTarget.id;
    console.log(this.collection.get(tempId));
    this.selectedModel = this.collection.get(tempId);
    this.setDataInModal(this.selectedModel);
  },
  setDataInModal: function (newModel) {
    var modal = $('#modal-paint-info');
    var idStatus = 'status-'+newModel.get('conditionType');
    console.log(modal);
    console.log(idStatus);

    console.log('.modal-content #'+idStatus);
    modal.find('.modal-title').text(' نقاشی ' + newModel.get('first_name') +' ' + newModel.get('last_name'));
    modal.find('.modal-content #first_name').val(newModel.get('first_name'));
    modal.find('.modal-content #last_name').val(newModel.get('last_name'));
    modal.find('.modal-content #phone').val(newModel.get('phone'));
    modal.find('.modal-content #city').val(newModel.get('city'));
    modal.find('.modal-content #age').val(newModel.get('age'));
    modal.find('.modal-content #tag').val(newModel.get('tag'));
    modal.find('.modal-content #date').val(newModel.get('date'));
    modal.find('.modal-content #status_paint').text(newModel.get('status_paint'));
    modal.find('.modal-content #desciption').val(newModel.get('desciption'));
    modal.find('.modal-content #racing_title').val(newModel.get('racing_title'));
    modal.find('.modal-content #racing_title').val(newModel.get('racing_title'));
    modal.find('.modal-content .apora-anable-btn-event').prop("disabled",false);
    modal.find('.modal-content #'+idStatus).prop("disabled",true);
    modal.find('.modal-body #url_paint').attr('src',newModel.get('url'));
    $('#modal-paint-info').modal('show');
  },
  changeStatusEvent:function (e) {
    e.preventDefault();
    var tempId = e.currentTarget.id;
    var conditionType = tempId.substring(7, 8);
    this.selectedModel.setPaintStatusInfo(conditionType);
    this.selectedModel.setConditionType(conditionType);
    this.collection.saveStatus(this.selectedModel);
    $('#modal-paint-info').modal('hide');
    var tempStatus = this.selectedModel.get('status_paint');
    $('#status-paint-'+this.selectedModel.get('id')).text(tempStatus);
  },
});
