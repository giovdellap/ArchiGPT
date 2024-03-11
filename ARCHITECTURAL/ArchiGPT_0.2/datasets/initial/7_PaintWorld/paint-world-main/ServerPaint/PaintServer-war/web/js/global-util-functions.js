// generalFetchCollection
// showPopup

function generalFetchCollection(self) {
  self.fetch({
    reset: true,
    headers: Apora.Config.headers,
    success: function(data, xhr, response) {
        console.log(data);
    },
    error: function(data, response, xhr) {
      if (response.status !== 204 && response.status !== 200) {
        Backbone.history.navigate(rout.index, true);
        showPopup(response.status);
      } else {
        showPopup(response.status);
      }
    }
  });
}

function postAjax(url, data, method, successCallBack, errorCallBack) {
  $.ajax({
    url: url,
    method: method,
    headers: Apora.Config.headers,
    data: data,
    success: function(response) {
      successCallBack(response);
    },
    error: function(response, error, errorThrown) {
      if (response.status == Enum.code.c200_success) {
        successCallBack(JSON.parse(response.responseText));
      } else {
        // myApp.hidePreloader();
        errorCallBack(response.status);
      }
    }
  });
}

function showErrorPopup(code) {
  if (code === Enum.code.c200_success) {
    showPopup(0, Messages.general.c200_success_action);
  } else if (code === Enum.code.c401_unauthorized) {
    Backbone.history.navigate(rout.logout, true);
    showPopup(0, Messages.general.c401_unauthorized);
  } else if (code === Enum.code.c403_unRegister) {
    showPopup(0, Messages.general.c403_unRegister);
  } else if (code === Enum.code.c404_internet_connection_error) {
    showPopup(0, Messages.general.c404_internet_connection_error);
  } else if (code === Enum.code.c500_server_error) {
    showPopup(0, Messages.general.c500_server_error);
  } else {
    showPopup(0, Messages.general.unknown_error);
  }
}

function showPopup(code, customMessage, is_guest) {
  var title = null;
  var tempTitle = null;
  var flag = true;
  switch (code) {
    case 0:
      tempTitle = customMessage;
      break;
    case 1:
      tempTitle = customMessage;
      break;
    case 2:
      tempTitle = customMessage;
      break;
    case 3:
      tempTitle = customMessage;
      break;
    case 4:
      tempTitle = customMessage;
      break;
    case 5:
      tempTitle = customMessage;
      break;
    case 200:
      flag = false;
      break;
    case 401:
      tempTitle = Messages.general.c401_login_again;
      Backbone.history.navigate(rout.logout, true);
      break;
    case 404:
      tempTitle = Messages.general.c404_internet_connection_error;
      break;
    case 406:
      tempTitle = Messages.general.c406_login_again;
      Backbone.history.navigate(rout.logout, true);
      break;
    case 409:
      tempTitle = Messages.general.c409_try_again;
      break;
    case 500:
      tempTitle = Messages.general.c500_try_again_server_error;
      break;
    case 504:
      tempTitle = Messages.general.c504_login_again;
      Backbone.history.navigate(rout.logout, true);
      break;
    case 1001:
      tempTitle = Messages.general.c1001_phone_before_selected;
      break;
    case 1002:
      tempTitle = Messages.general.c1002_phone_condition;
      break;
    case 1003:
      tempTitle = Messages.general.c1003_email_before_selected;
      break;
    case 1004:
      tempTitle = Messages.general.c1004_email_mistake;
      break;
    case 1005:
      tempTitle = Messages.general.c1005_password_mistake;
      break;
    case 1006:
      tempTitle = Messages.general.c1006_email_not_found;
      break;
    default:
      if (code == "") {
        tempTitle = Messages.general.welcome;
        // myApp.hidePreloader();
      } else {
        tempTitle = Messages.general.otherCode(code);
      }
  }
  if (Apora.Config.AppLang == Enum.appLang.english) {
    title = '<div class="modal-text" style="direction:ltr;">' + tempTitle + '</div>';
  } else {
    title = tempTitle;
  }
  if (flag) {
    if (is_guest && code == 0) {
      myApp.modal({
        title: Messages.app.name,
        text: title,
        buttons: [{
          text: Messages.btn.login,
          bold: true,
          onClick: function() {
            Backbone.history.navigate(rout.logout, true);
            Backbone.history.navigate(rout.login, true);
          }
        }, {
          text: Messages.btn.register,
          bold: true,
          onClick: function() {
            Backbone.history.navigate(rout.logout, true);
            Backbone.history.navigate('user-register-Page1-view', true);
          }
        }, {
          text: Messages.btn.close,
          bold: false
        }]
      });
    } else {
      if (code == 1) {
        myApp.modal({
          title: Messages.app.name,
          text: title,
          buttons: [{
            text: 'برقراری تماس',
            bold: true,
            customClass: 'call_number',
            onClick: function() {
              window.plugins.CallNumber.callNumber(function() {}, function() {}, CONFIG.phone_number);
            }
          }, {
            text: Messages.btn.close,
            bold: false
          }]
        });
      } else if (code == 2) {

        myApp.modal({
          title: Messages.app.name,
          text: title,
          buttons: [{
            text: 'سابقه سفارشات',
            bold: true,
            onClick: function() {
              Backbone.history.navigate(rout.orderList, true);
            }
          }, {
            text: Messages.btn.close,
            bold: false
          }]
        });
      } else if (code == 3) {

        myApp.modal({
          title: Messages.app.name,
          text: title,
          buttons: [{
            text: 'صندوق پیام',
            bold: true,
            onClick: function() {
              Backbone.history.navigate('notification-list-view', true);
            }
          }, {
            text: Messages.btn.close,
            bold: false
          }]
        });
      } else if (code == 4) {

        myApp.modal({
          title: Messages.app.name,
          text: title,
          buttons: [{
            text: Messages.btn.try_again,
            bold: true,
            onClick: function() {
              Backbone.history.navigate(rout.blank, true);
            }
          }]
        });
      } else if (code == 5) {
        myApp.modal({
          title: Messages.app.name,
          text: title,
          buttons: [{
            text: Messages.btn.close,
            bold: false
          }]
        });
      } else {
        myApp.modal({
          title: Messages.app.name,
          text: title,
          buttons: [{
            text: Messages.btn.close,
            bold: false
          }]
        });
      }
    }

  }
}
