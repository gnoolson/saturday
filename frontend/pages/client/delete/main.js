import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ClientRestApi from "@/rest_api/ClientRestApi.js"
import timeout from "@/timeout.js"

const app = {
    components: {

        "s-footer": Footer
    },
    mounted() {
        this.loadClient();
        timeout.exec("wait", () => {
            this.deleteButtonDisabled = false;
            this.isWaiting = false;
        }, 3000);
    },
    data() {
        return {
            client: {
                id: clientId
            },
            project: {
                name: ""
            },
            deleteButtonDisabled: true,
            isWaiting: true
        }
    },
    methods: {
        loadClient() {
            ClientRestApi.get(clientId, (data) => {
                this.client = data;
                this.loadProject();
            });
        },
        loadProject() {
            ProjectRestApi.get(this.client.projectId, (data) => {
                this.project = data;
            });
        },
        deleteClient() {
            this.deleteButtonDisabled = true;
            ClientRestApi.del(clientId, (data) => {
                this.goToAllClientsPage();
            }, (error) => {
                console.error(error);
                this.deleteButtonDisabled = false;
            });
        },
        goToAllClientsPage() {
            window.location.href = "/client/all";
        }
    }
};

createApp(app).mount("#app");