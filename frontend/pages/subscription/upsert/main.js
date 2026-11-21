import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import Id from "@/components/Id.vue"
import Input from "@/components/Input.vue"
import Textarea from "@/components/Textarea.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import ClientRestApi from "@/rest_api/ClientRestApi.js"
import ErrorMessage from "@/components/ErrorMessage.vue"
import SelectScript from "@/components/SelectScript.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"

const app = {
    components: {

        "s-footer": Footer,
        "s-input": Input,
        "s-textarea": Textarea,
        "s-select-script": SelectScript,
        "s-error-message": ErrorMessage,
        "s-id": Id
    },
    mounted() {
        this.loadSubscription();
        this.loadScripts();
        this.loadClient();
        this.loadProjects();
        this.addExitEventListener();
    },
    data() {
        return {
            client: {
                id: clientId,
            },
            subscription: {
                id: subscriptionId
            },
            errors: [],
            scripts: [],
            projects: [],
            clientLoaded: false,
            projectsLoaded: false,
            scriptsLoaded: false,
            subscriptionLoaded: false,
            subscriptionChanged: false,
            saveInAction: false,
            emptyId: emptyId
        }
    },
    methods: {
        saveSubscription() {
            this.saveInAction = true;
            ClientRestApi.saveSubscription(clientId, this.subscription, (data) => {
                this.subscriptionChanged = false;
                this.saveInAction = true;
                window.location.href = "/client/" + clientId + "/subscription/all";
            }, (error) => {
                this.errors = error.response.data;
                this.saveInAction = false;
            })
        },
        subscriptionChangedCallback() {
            if (this.dataLoaded) {
                this.subscriptionChanged = true;
            }
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.subscriptionChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
            });
        },
        loadSubscription() {
            ClientRestApi.getSubscription(this.client.id, subscriptionId, (data) => {
                this.subscription = data;
                this.subscriptionLoaded = true;
            });
        },
        loadClient() {
            ClientRestApi.get(this.client.id, (data) => {
                this.client = data;
                this.clientLoaded = true;
            });
        },
        loadScripts() {
            ScriptRestApi.getAll((data) => {
                this.scripts = data;
                this.scriptsLoaded = true;
            });
        },
        goToDeletePage() {
            window.location.href = "/client/" + this.client.id + "/subscription/" + this.subscription.id + "/delete";
        }
    },
    computed: {
        projectName() {
            let result = "";
            this.projects.forEach(project => {
                if (project.id === this.client.projectId)
                    result = project.name;
            });
            return result;
        },
        dataLoaded() {
            return this.clientLoaded && this.projectsLoaded && this.scriptsLoaded && this.subscriptionLoaded;
        },
        saveButtonDisabled() {
            return !this.subscriptionChanged || this.saveInAction;
        },
        deleteButtonDisabled() {
            return this.subscription.id === this.emptyId;
        }
    }
};

createApp(app).mount("#app");