import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ClientRestApi from "@/rest_api/ClientRestApi.js"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import timeout from "@/timeout.js"

const app = {
    components: {

        "s-footer": Footer
    },
    mounted() {
        this.loadSubscription();
        this.loadClient();
        timeout.exec("wait", () => {
            this.deleteButtonDisabled = false;
            this.isWaiting = false;
        }, 3000);
    },
    data() {
        return {
            subscription: {
                id: subscriptionId
            },
            project: {
                name: ""
            },
            client: {
                id: clientId
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
        loadSubscription() {
            ClientRestApi.getSubscription(clientId, subscriptionId, (data) => {
                this.subscription = data;
            });
        },
        loadProject() {
            ProjectRestApi.get(this.client.projectId, (data) => {
                this.project = data;
            });
        },
        deleteSubscription() {
            this.deleteButtonDisabled = true;
            ClientRestApi.delSubscription(clientId, subscriptionId, (data) => {
                this.goToAllSubscriptionsPage();
            }, (error) => {
                console.error(error);
                this.deleteButtonDisabled = false;
            });
        },
        goToAllSubscriptionsPage() {
            window.location.href = "/client/" + this.client.id + "/subscription/all";
        }
    }
};

createApp(app).mount("#app");
