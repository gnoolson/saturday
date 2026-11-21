import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import Id from "@/components/Id.vue"
import Input from "@/components/Input.vue"
import Textarea from "@/components/Textarea.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import DashboardRestApi from "@/rest_api/DashboardRestApi.js"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ErrorMessage from "@/components/ErrorMessage.vue"
import SelectProject from "@/components/SelectProject.vue"
import SelectScript from "@/components/SelectScript.vue"

const app = {

    components: {

        "s-footer": Footer,
        "s-error-message": ErrorMessage,
        "s-input": Input,
        "s-textarea": Textarea,
        "s-id": Id,
        "s-select-project": SelectProject,
        "s-select-script": SelectScript
    },
    mounted() {
        this.loadDashboard();
        this.loadScripts();
        this.loadProjects();
        this.addExitEventListener();
    },
    data() {
        return {
            dashboard: {
                id: dashboardId,
                projectId: emptyId,
                scriptId: emptyId
            },
            scripts: [],
            projects: [],
            errors: [],
            emptyId: emptyId,
            dashboardChanged: false,
            dashboardLoaded: false,
            projectsLoaded: false,
            scriptsLoaded: false,
            saveInAction: false
        }
    },
    methods: {
        loadDashboard() {
            DashboardRestApi.get(dashboardId, (data) => {
                this.dashboard = data;
                this.dashboardLoaded = true;
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
        dashboardChangedCallback() {
            if (this.dataLoaded)
                this.dashboardChanged = true;
        },
        checkAvailableScripts() {
            if (this.scripts.length == 0) {
                this.errors.push({ field: "scriptId", message: message_1 });
            }
        },
        checkAvailableProjects() {
            if (this.projects.length == 0) {
                this.errors.push({ field: "projectId", message: message_2 });
            }
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.dashboardChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        saveDashboard() {
            this.saveInAction = true;
            DashboardRestApi.upsert(this.dashboard, (data) => {
                this.dashboardChanged = false;
                this.saveInAction = false;
                window.location.href = "/dashboard/all";
            }, (error) => {
                this.errors = error.response.data;
                this.saveInAction = false;
            });
        },
        goToDeletePage() {
            window.location.href = "/dashboard/" + this.dashboard.id + "/delete";
        }
    },
    computed: {
        saveButtonDisabled() {
            return !this.dashboardChanged || this.saveInAction;
        },
        SUPERButtonDisabled() {
            return this.dashboard.id === this.emptyId;
        },
        deleteButtonDisabled() {
            return this.dashboard.id === this.emptyId;
        },
        dataLoaded() {
            return this.dashboardLoaded && this.projectsLoaded && this.scriptsLoaded;
        }
    }
};

createApp(app).mount("#app");