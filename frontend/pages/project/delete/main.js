import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ErrorMessage from "@/components/ErrorMessage.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import timeout from "@/timeout.js"


const app = {
    components: {
        "s-error-message": ErrorMessage,

        "s-footer": Footer
    },
    mounted() {
        this.loadProject();
        timeout.exec("", () => {
            this.deleteButtonDisabled = false;
            this.isWaiting = false;
        }, 3000);
    },
    data() {
        return {
            project: {
                id: projectId
            },
            deleteButtonDisabled: true,
            isWaiting: true
        }
    },
    methods: {
        loadProject() {
            ProjectRestApi.get(this.project.id, (data) => {
                this.project = data;
            });
        },
        deleteProject() {
            this.deleteButtonDisabled = true;
            ProjectRestApi.del(projectId, (data) => {
                window.location.href = "/project/all";
            }, (error) => {
                console.error(error);
                this.deleteButtonDisabled = false;
            });
        },
        goToAllProjectsPage() {
            window.location.href = "/project/all";
        }
    }
};

createApp(app).mount("#app");