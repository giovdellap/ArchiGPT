var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};
Apora.Prototypes.User = Apora.Prototypes.User || {};
Apora.Prototypes.User.Login = Apora.Prototypes.User.Login || {};
Apora.Prototypes.User.Login.View = Backbone.View.extend({
    template: Handlebars.templates['user/user-login-view'],
    events: {
        'click .apora-submit-user-event': 'submitUserEvent',
    },
    initialize: function (options) {
        this.model = options.model;
        this.adapter = options.adapter;
        this.containerElement = options.containerElement;
    },
    render: function () {
        this.$el.html(this.template());
        $(this.containerElement).html(this.$el);
        $('body').addClass("boxed");
        return this;
    },
    show: function () {
        this.render();
    },
    submitUserEvent: function (e) {
        e.preventDefault();
        var data = Backbone.Syphon.serialize(this);
        var self = this;
        var respond = function (code) {
            if (code === Enum.code.c401_unauthorized) {
                self.showErrorSendPhone(Messages.general.c401_unauthorized);
            } else if (code === Enum.code.c403_unRegister) {
                self.showErrorSendPhone(Messages.general.c403_unRegister);
            } else if (code === Enum.code.c404_internet_connection_error) {
                self.showErrorSendPhone(Messages.general.c404_internet_connection_error);
            } else if (code === Enum.code.c500_server_error) {
                self.showErrorSendPhone(Messages.general.c500_server_error);
            } else {
                self.showErrorSendPhone(Messages.general.unknown_error);
            }
        };
        if (data.username) {
          if(data.password){
            this.model.sendConfirmCode(data.username,data.password,respond);
          } else {
              showPopup(0, Messages.user.verifyCode_require);
          }
        } else {
            showPopup(0, Messages.user.verifyCode_require);
        }
     },
    showErrorSendPhone: function (message) {
        this.$('.errors').attr('style', 'color: red');
        this.$('.errors').html("<img src='img/_error.png' class='error_logo' width='30px' align='center'>" + message);
        this.model.clear();
        this.$('.login-button').addClass('login');
    }
});
