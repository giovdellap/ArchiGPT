//Global methods for Apora namespace
var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};

Apora.Prototypes.Global = function() {
  this.beforePageView = null;
  this.beforeCollection = null;
  this.beforeNavbarView = null;
  this.beforeFooterView = null;
  this.beforeUserInfoView = null;
  this.beforeGalleryView = null;
  this.beforeSearchView = null;
  this.beforeSidebarView = null;
  this.showPageViewProxy = function(view, collection) {
    if (this.beforePageView) {
      this.beforePageView.remove();
      this.beforePageView.unbind();
      this.beforeCollection.unbind();
      if (this.beforePageView.onClose) {
        this.beforePageView.onClose();
      }
    }
    view.show();
    this.beforePageView = view;
    this.beforeCollection = collection;
  };
  this.showNavbarViewProxy = function(view) {
    if (this.beforeNavbarView) {
      this.beforeNavbarView.remove();
      this.beforeNavbarView.unbind();
    }
    view.show();
    this.beforeNavbarView = view;
  };
  this.showFooterViewProxy = function(view) {
    if (this.beforeFooterView) {
      this.beforeFooterView.remove();
      this.beforeFooterView.unbind();
    }
    view.show();
    this.beforeFooterView = view;
  };
  this.showGalleryViewProxy = function(view, collection) {
    if (this.beforeGalleryView) {
      this.beforeGalleryView.remove();
      this.beforeGalleryView.unbind();
      this.beforeCollection.unbind();
    }
    view.show();
    this.beforeGalleryView = view;
    this.beforeCollection = collection;
  };
  this.showUserInfoViewProxy = function(view) {
    if (this.beforeUserInfoView) {
      this.beforeUserInfoView.remove();
      this.beforeUserInfoView.unbind();
    }
    view.show();
    this.beforeUserInfoView = view;
  };
  this.showSearchViewProxy = function(view, collection) {
    if (this.beforeSearchView) {
      this.beforeSearchView.remove();
      this.beforeSearchView.unbind();
      this.beforeCollection.unbind();
    }
    view.show();
    this.beforeSearchView = view;
    this.beforeCollection = collection;
  };

  this.showSidebarViewProxy = function(view) {
    if (this.beforeSidebarView) {
      this.beforeSidebarView.remove();
      this.beforeSidebarView.unbind();
    }
    view.show();
    this.beforeSidebarView = view;
  };

  this.innerInterface = function(adapter, methods) {
    var innerInterface = Object.create(null);
    innerInterface.navigate = function(route) {
      adapter.motherboard.navigate(route);
    };
    innerInterface.save = function(name, obj, lating) {
      return adapter.motherboard.save(name, obj, lating);
    };
    innerInterface.load = function(name) {
      return adapter.motherboard.load(name);
    };
    for (var property in methods) {
      innerInterface[property] = methods[property];
    };
    return innerInterface;
  };

  this.interface = function(adapter, methods) {
    var innerInterface = Object.create(null);
    for (var property in methods) {
      innerInterface[property] = methods[property];
    }
    return innerInterface;
  };

};

String.prototype.toEnglishDigits = function() {
  var num_dic = {
    '۰': '0',
    '۱': '1',
    '۲': '2',
    '۳': '3',
    '۴': '4',
    '۵': '5',
    '۶': '6',
    '۷': '7',
    '۸': '8',
    '۹': '9',
  }

  return parseInt(this.replace(/[۰-۹]/g, function(w) {
    return num_dic[w]
  }));
}
