import timeout from "/dashboard/static/js/timeout.js"


const dashboard = {
    dashboardId: null,
    openDashboardId: null,
    onMessageCallback: null,
    errorCallback: null,
    setupCompleted: false,

    setup(dashboardId, openDashboardId, onMessageCallback, errorCallback) {
        if (!dashboardId)
            throw new Error("dashboardId is required");
        if (!openDashboardId)
            throw new Error("openDashboardId is required");
        if (!onMessageCallback)
            throw new Error("onMessageCallback is required");

        this.dashboardId = dashboardId;
        this.openDashboardId = openDashboardId;

        this.onMessageCallback = onMessageCallback;
        this.errorCallback = errorCallback;
        this.setupCompleted = true;

        this.sendGetRequest();
    },


    execute(message, successCallback, errorCallback) {
        if (!this.setupCompleted)
            throw new Error("Dashboard is not initialized. Call dashboard.setup(dashboardId, openDashboardId, onMessageCallback) before calling execute().");

        axios.post("/api/dashboard/" + this.dashboardId + "/" + this.openDashboardId + "/execute", message).then((response) => {
            if (successCallback)
                successCallback(response.data)
        }, (error) => {
             if (error.response && error.response.status === 401)
                window.location.href = "/login";

            if (errorCallback)
                errorCallback(error.response);
        });
    },


    sendGetRequest() {
        axios.get("/api/dashboard/" + this.dashboardId + "/" + this.openDashboardId + "/message").then((response) => {
            if (response.status == 200){
                response.data.forEach(item=>{
                   this.onMessageCallback(item);
                });
            }

            timeout.exec("long_polling", () => { this.sendGetRequest(); }, 0);
        }, (error) => {
            if (error.response && error.response.status === 401)
               window.location.href = "/login";

            if (this.errorCallback)
                this.errorCallback(error.response);

            timeout.exec("long_polling", () => { this.sendGetRequest(); }, 1000);
        });
    }
}

export default dashboard;