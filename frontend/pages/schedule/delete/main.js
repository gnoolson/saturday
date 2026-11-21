import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ScheduleRestApi from "@/rest_api/ScheduleRestApi.js"
import timeout from "@/timeout.js"

const app = {
    components: {

        "s-footer": Footer
    },
    mounted() {
        this.loadSchedule();
        timeout.exec("wait", () => {
            this.deleteButtonDisabled = false;
            this.isWaiting = false;
        }, 3000);
    },
    data() {
        return {
            schedule: {
                id: scheduleId
            },
            project: {
                name: ""
            },
            deleteButtonDisabled: true,
            isWaiting: true
        }
    },
    methods: {
        loadSchedule() {
            ScheduleRestApi.get(scheduleId, (data) => {
                this.schedule = data;
                this.loadProject();
            });
        },
        loadProject() {
            ProjectRestApi.get(this.schedule.projectId, (data) => {
                this.project = data;
            });
        },
        deleteSchedule() {
            this.deleteButtonDisabled = true;
            ScheduleRestApi.del(scheduleId, (data) => {
                this.goToAllSchedulesPage();
            }, (error) => {
                console.error(error);
                this.deleteButtonDisabled = false;
            });
        },
        goToAllSchedulesPage() {
            window.location.href = "/schedule/all";
        }
    }
};

createApp(app).mount("#app");