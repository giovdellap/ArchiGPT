var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.PaintTable = Apora.Prototypes.PaintTable || {};
Apora.Prototypes.PaintTable.Collection = Backbone.Collection.extend({
  model: Apora.Prototypes.PaintTable.Model,
  initialize: function(models, options) {
    options = options || {};
    this.url = options.infoNumPaintUrl || null;
    this.title = options.title || null;
    this.adapter = options.adapter || null;
    this.sendNewStatusUrl = options.sendNewStatusUrl || null;
    this.infoNumPaintUrl = options.infoNumPaintUrl || null;
    this.todayPainUrl = options.todayPainUrl || null;
    this.allPaint = null;
    this.selectedStatus = null;
    this.selectedStatusName = null;
    this.confirmdPaint = null;
    this.readyDisplayWithTime = null;
    this.readyDisplayNoTime = null;
    this.shortInfo = {};
  },
  fetchCollection: function () {
      generalFetchCollection(this);
  },
  convertCreateTime: function(tempDate) {
    var defaultDate = new Date(tempDate);
    // Notice the months are zero based so you need to +1
    var month = defaultDate.getMonth() + 1;
    var day = defaultDate.getDate();
    if (month < 10) {
      var month = '0' + month;
    }
    if (day < 10) {
      var day = '0' + day;
    }
    var date = defaultDate.getFullYear() + '' + month + '' + day;
    return date;
  },
  getPaintShortInfo:function (callBack) {
    var self = this;
    var TodayPaintCB = function () {
      self.getTodayPaint(callBack);
    }
    this.getPaintInfo(TodayPaintCB)
  },
  saveStatus: function(tempModel) {
    var tempurl = this.sendNewStatusUrl + tempModel.get('id');
    var self = this;
    var bodyData = {};
    bodyData.status = tempModel.get('status');
    bodyData.conditionType = tempModel.get('conditionType');
    Apora.Config.headers.racing_id = tempModel.get('racing').id;
    console.log(bodyData);
    console.log(tempModel);
    console.log(Apora.Config.headers);
    console.log(tempurl);
    $.ajax({
      url: tempurl,
      method: 'PUT',
      headers: Apora.Config.headers,
      data: JSON.stringify(bodyData),
      success: function(arg) {
        console.log("success change********");
      },
      error: function(requestObject, error, errorThrown) {
        console.log("error change!!!!!!!!!!!");
      }
    });
  },
  setTodayPaint: function(result, callBack) {
    if (result != null) {
      this.shortInfo.today_paint = result.length;
      callBack(this.shortInfo);
    } else {
      this.shortInfo.today_paint = 0;
      callBack(this.shortInfo);
    }
  },
  setPaintInfo: function(result, todayPaintCb) {
    this.shortInfo = {};
    this.shortInfo.all_paints = result.length;
    this.confirmdPaint = [];
    this.readyDisplayWithTime = [];
    this.readyDisplayNoTime = [];
    for (var i = 0; i < result.length; i++) {
      if (result[i].conditionType == 1) {
        this.confirmdPaint.push(result[i]);
      }
      if (result[i].conditionType == 2) {
        this.readyDisplayWithTime.push(result[i]);
      }
      if (result[i].conditionType == 3) {
        this.readyDisplayNoTime.push(result[i]);
      }
      if (i == result.length - 1) {

        this.shortInfo.confirmd_paint = this.confirmdPaint.length;
        this.shortInfo.ready_display_with_time = this.readyDisplayWithTime.length;
        this.shortInfo.ready_display_no_time = this.readyDisplayNoTime.length;
        todayPaintCb()
      }
    }
  },
  getTodayPaint: function(callBack) {
    var self = this;
    $.ajax({
      url: this.todayPainUrl,
      method: 'GET',
      headers: Apora.Config.headers,
      success: function(result, status, xhr) {
        self.setTodayPaint(result, callBack);
      },
      error: function(requestObject, error, errorThrown) {}
    });
  },
  getPaintInfo: function(todayPaintCb) {
    var self = this;
    $.ajax({
      url: self.infoNumPaintUrl,
      method: 'GET',
      headers: Apora.Config.headers,
      success: function(result, status, xhr) {
        self.set(result);
        self.setPaintInfo(result, todayPaintCb);
      },
      error: function(requestObject, error, errorThrown) {
      }
    });
  }
});
