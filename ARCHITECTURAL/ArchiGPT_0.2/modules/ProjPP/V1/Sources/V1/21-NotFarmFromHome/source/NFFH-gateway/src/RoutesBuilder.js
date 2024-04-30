export default class RoutesBuilder {
    
    constructor() {
        this.routes = [];
    }
    
    addRoutes(r, target) {
        r.forEach(x => {
            this.routes.push({
                url: x,
                auth: false,
                creditCheck: false,
                rateLimit: {
                    windowMs: 15 * 60 * 1000,
                    max: 5
                },
                proxy: {
                    target: target,
                    changeOrigin: false
                }
            },)
        });
    }

    exposeRoutes() {
        return this.routes;
    }
}