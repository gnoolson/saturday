import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import DashboardRestApi from "@/rest_api/DashboardRestApi"
import timeout from "@/timeout.js"

const app = {
    components: {

        "s-footer": Footer
    },
    mounted() {
        this.loadDashboard();
        timeout.exec("wait", () => {
            this.deleteButtonDisabled = false;
            this.isWaiting = false;
        }, 3000);
    },
    data() {
        return {
            dashboard: {
                id: dashboardId
            },
            project: {
                name: ""
            },
            deleteButtonDisabled: true,
            isWaiting: true
        }
    },
    methods: {
        loadDashboard() {
            DashboardRestApi.get(dashboardId, (data) => {
                this.dashboard = data;
                this.loadProject();
            })
        },
        loadProject() {
            ProjectRestApi.get(this.dashboard.projectId, (data) => {
                this.project = data;
            });
        },
        deleteDashboard() {
            this.deleteButtonDisabled = true;
            DashboardRestApi.del(dashboardId, (data) => {
                this.goToAllDashboardsPage();
            }, (error) => {
                this.deleteButtonDisabled = false;
                console.error(error);
            });
        },
        goToAllDashboardsPage() {
            window.location.href = "/dashboard/all";
        }
    }
};

createApp(app).mount("#app");