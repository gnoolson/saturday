import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ClientRestApi from "@/rest_api/ClientRestApi.js";
import ProjectRestApi from "@/rest_api/ProjectRestApi.js";
import ScriptRestApi from "@/rest_api/ScriptRestApi.js";

const app = {
    components: {
        "s-footer": Footer
    },
    mounted() {
        this.loadSubscriptions();
        this.loadProjects();
        this.loadClient();
        this.loadScripts();
    },
    data() {
        return {
            subscriptions: [],
            projects: [],
            client: {
                id: clientId,
                name: "",
                projectId: ""
            },
            scripts: [],
            projectsLoaded: false,
            subscriptionsLoaded: false,
            superAccess: SUPER_ACCESS,
            expandedDescriptions: new Set()
        }
    },
    methods: {
        getScriptName(subscription) {
            let scriptName = "";
            this.scripts.forEach((script) => {
                if (script.id === subscription.scriptId) {
                    scriptName = script.name;
                }
            });
            return scriptName;
        },
        loadScripts() {
            ScriptRestApi.getAll((data) => {
                this.scripts = data;
            });
        },
        loadClient() {
            ClientRestApi.get(this.client.id, (data) => {
                this.client = data;
            });
        },
        loadSubscriptions() {
            ClientRestApi.getSubscriptions(this.client.id, (data) => {
                this.subscriptions = data;
                this.subscriptionsLoaded = true;
            });
        },
        goToCreateNewPage() {
            window.location.href = "/client/" + this.client.id + "/subscription/create";
        },
        goToEditPage(subscription) {
            window.location.href = "/client/" + this.client.id + "/subscription/" + subscription.id + "/edit";
        },
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
            });
        },
        getDescription(entity) {
            if (!entity.description.length) {
                return "no description";
            }

            if (this.isDescriptionExpanded(entity.id)) {
                return entity.description;
            } else {
                let maxLength = 100;
                let description = entity.description.slice(0, maxLength);
                if (entity.description.length > maxLength) {
                    description += " ...";
                }
                return description;
            }
        },
        expandDescription(entityId) {
            this.expandedDescriptions.add(entityId);
        },
        collapseDescription(entityId) {
            this.expandedDescriptions.delete(entityId);
        },
        isDescriptionExpanded(entityId) {
            return this.expandedDescriptions.has(entityId);
        },
    },
    computed: {
        projectName() {
            let result = "";
            this.projects.forEach(project => {
                if (project.id === this.client.projectId)
                    result = project.name;
            });
            return result;
        }
    }
};

createApp(app).mount("#app");