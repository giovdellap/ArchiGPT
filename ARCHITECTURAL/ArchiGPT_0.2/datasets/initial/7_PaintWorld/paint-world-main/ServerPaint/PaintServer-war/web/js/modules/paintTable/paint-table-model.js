var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.PaintTable = Apora.Prototypes.PaintTable || {};
Apora.Prototypes.PaintTable.Model = Backbone.Model.extend({
  initialize: function() {
    this.convertTime();
    if (this.get('id')) {
      var tempConditionType = this.get('conditionType');
      this.setPaintStatusInfo(tempConditionType);
      this.setRacingTitle();
    }
  },
  setRacingTitle: function() {
    console.log(this);
    console.log(this.get('racing').title);
    this.set('racing_title', this.get('racing').title);
  },
  setPaintStatusInfo: function(conditionType) {
    console.log("&&&&&&&&&&&&&&&&&");
    console.log(conditionType);
    if (conditionType == 0) {
      this.set('status_paint', 'درحال بررسی');
      this.set('status', 'درحال بررسی');
    }
    if (conditionType == 1) {
      this.set('status_paint', 'دریافت شده');
      this.set('status', 'دریافت شده');
    }
    if (conditionType == 2) {
      this.set('status_paint', 'در انتظار پخش');
      this.set('status', 'در انتظار پخش');
    }
    if (conditionType == 3) {
      this.set('status_paint', 'آماده پخش');
      this.set('status', 'آماده پخش');
    }
  },
  setConditionType:function (status) {
    this.set('conditionType',status);
    console.log(this);
  },
  convertTime: function() {
    var tempStartDate = new Date(this.get('update_at'));
    var day = parseInt(tempStartDate.getDate());
    var month = parseInt(tempStartDate.getMonth());
    var year = parseInt(tempStartDate.getFullYear());
    if (day && month && year) {
      var persianDate = new JDate(new Date(year, month, day));
      if (persianDate.date[1] < 10) {
        persianDate.date[1] = '0' + persianDate.date[1];
      }
      if (persianDate.date[2] < 10) {
        persianDate.date[2] = '0' + persianDate.date[2];
      }
      this.set('date', persianDate.date[0] + '-' + persianDate.date[1] + '-' + persianDate.date[2]);
    }
  },
});
