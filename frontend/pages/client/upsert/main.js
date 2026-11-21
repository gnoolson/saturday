import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import Id from "@/components/Id.vue"
import Input from "@/components/Input.vue"
import Textarea from "@/components/Textarea.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ClientRestApi from "@/rest_api/ClientRestApi.js"
import ErrorMessage from "@/components/ErrorMessage.vue"
import SelectProject from "@/components/SelectProject.vue"
import SelectEventHandler from "@/components/SelectEventHandler.vue"
import CB from "@/components/CB.vue"


const app = {
    components: {

        "s-footer": Footer,
        "s-input": Input,
        "s-textarea": Textarea,
        "s-select-project": SelectProject,
        "s-select-event-handler": SelectEventHandler,
        "s-error-message": ErrorMessage,
        "s-cb": CB,
        "s-id": Id
    },
    mounted() {
        this.loadClient();
        this.loadScripts();
        this.loadProjects();
        this.addExitEventListener();
    },
    data() {
        return {
            client: {
                id: clientId,
                projectId: emptyId,
                scriptId: emptyId,
                auth: {
                    use: false,
                    username: "",
                    password: ""
                }
            },
            scripts: [],
            projects: [],
            errors: [],
            emptyId: emptyId,
            clientChanged: false,
            clientLoaded: false,
            projectsLoaded: false,
            scriptsLoaded: false,
            saveInAction: false
        }
    },
    methods: {
        loadClient() {
            ClientRestApi.get(clientId, (data) => {
                this.client = data;
                this.clientLoaded = true;
            });
        },
        loadScripts() {
            ScriptRestApi.getAll((data) => {
                this.scripts = data;
                this.scriptsLoaded = true;
                this.checkAvailableScripts();
            });
        },
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
                this.checkAvailableProjects();
            });
        },
        clientChangedCallback() {
            if (this.dataLoaded)
                this.clientChanged = true;
        },
        checkAvailableProjects() {
            if (this.projects.length == 0) {
                this.errors.push({ field: "projectId", message: message_1 });
            }
        },
        checkAvailableScripts() {
            if (this.scripts.length == 0) {
                this.errors.push({ field: "scriptId", message: message_2 });
            }
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.clientChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        saveClient() {
            this.saveInAction = true;
            ClientRestApi.save(this.client, (data) => {
                this.clientChanged = false;
                this.saveInAction = false;
                window.location.href = "/client/all";
            }, (error) => {
                this.errors = error.response.data;
                this.saveInAction = false;
            });
        },
        goToDeletePage() {
            window.location.href = "/client/" + this.client.id + "/delete";
        }
    },
    computed: {
        saveButtonDisabled() {
            return !this.clientChanged || this.saveInAction;
        },
        deleteButtonDisabled() {
            return this.client.id === this.emptyId;
        },
        subscriptionsButtonDisabled() {
            return this.client.id === this.emptyId;
        },
        dataLoaded() {
            return this.clientLoaded && this.projectsLoaded && this.scriptsLoaded;
        }
    }
};

createApp(app).mount("#app");