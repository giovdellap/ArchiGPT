// Storage object to work with storage api
var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};

Apora.Prototypes.Storage = function() {
    this.appCode = Apora.Config.appCodeGlobal;
    this.storage = JSON.parse(localStorage.getItem(this.appCode));
    var self = this;
    this.interface = {
        save: function(name, content) {
            if (name && content) {
                var contentInText = JSON.stringify(content);
                var safeContent = JSON.parse(contentInText);
                self.storage[name] = safeContent;
                localStorage.setItem(self.appCode, JSON.stringify(self.storage));
            } else {
                localStorage.setItem(self.appCode, JSON.stringify(self.storage));
            }

        },
        load: function(name) {
            var contentInText = JSON.stringify(self.storage[name]);
            if (contentInText) {
                var safeContent = JSON.parse(contentInText);
                return safeContent;
            } else {
                return null;
            }

        },
        clearStorage: function() {
              localStorage.removeItem(self.appCode);
        }
    };
    if (!this.storage) {
        this.storage = {};
        this.interface.save();
    }
    return this.interface;

};
