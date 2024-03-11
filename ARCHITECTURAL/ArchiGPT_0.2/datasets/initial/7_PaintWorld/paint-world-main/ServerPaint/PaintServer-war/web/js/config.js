var Apora = Apora || {};
Apora.Prototypes = Apora.Prototypes || {};

Apora.Prototypes.Config = function() {
    this.baseAddress = "185.211.56.72:8080";
//    this.baseAddress = "localhost:8080";
    this.baseUrl = "http://" + this.baseAddress;
    this.mainUrl = this.baseUrl + "/PaintServer-war/webresources/";
    this.appName = "نقاشی";
    this.appCodeGlobal = "Painting";
    this.userType = "gardeshgar";
    this.setReloadFlag = false;
    this.headers = {
        'Content-Type': 'application/json',
        'user_code': ''
    };
    this.mainUrls = {
        infoNumPaint: this.mainUrl + 'participants/admin',
        todayPaint: this.mainUrl + 'participants/today',
        sendNewStatus: this.mainUrl + 'participants/',
        login: this.mainUrl + 'judge-users/login-panel',
        loginMethod: 'POST',
    };
    this.localUrls = {
        infoNumPaint: '../data/paintInfo.json',
        todayPaint: '../data/paintInfo.json',
        sendNewStatus: this.mainUrl + 'participants/',
        login: '../data/users.json',
        loginMethod: 'GET',
    };
    //checking if application is running on PC or device
    if (window.isLocal) {
        this.URLs = this.localUrls;
        this.isLocal=true;
    } else {
        this.URLs = this.mainUrls;
        this.isLocal = false;
    }
};
