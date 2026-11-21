import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import NameFilter from "@/components/NameFilter.vue";
import ProjectFilter from "@/components/ProjectFilter.vue";
import timeout from "@/timeout.js";
import ClientRestApi from "@/rest_api/ClientRestApi.js";
import ProjectRestApi from "@/rest_api/ProjectRestApi.js";
import CopyId from "@/components/CopyId.vue";

const app = {
    components: {

        "s-footer": Footer,
        "s-project-filter": ProjectFilter,
        "s-name-filter": NameFilter,
        "s-copy-id" : CopyId
    },
    mounted() {
        this.loadProjects();
        this.loadClientsLoop();
    },
    data() {
        return {
            resultAfterProjectFilter: [],
            resultAfterNameFilter: [],
            clients: [],
            projects: [],
            projectsLoaded: false,
            clientsLoaded: false,
            superAccess: SUPER_ACCESS,
            enableButtonsDisabled: false,
            connectionErrorDialog: {
                message: "",
                clientName: ""
            },
            expandedDescriptions: new Set(),
        }
    },
    methods: {
        loadClientsLoop() {
            this.loadClients();
            timeout.exec("load_clients", this.loadClientsLoop, 1000);
        },
        loadClients() {
            ClientRestApi.getAll((data) => {
                this.clients = data;
                this.clientsLoaded = true;
            });
        },
        goToCreateNewPage() {
            window.location.href = "/client/create";
        },
        goToEditPage(client) {
            window.location.href = "/client/" + client.id + "/edit";
        },
        goToEditSubscriptionsPage(client) {
            window.location.href = "/client/" + client.id + "/subscription/all";
        },
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
            });
        },
        setResultAfterProjectFilter(entities) {
            this.resultAfterProjectFilter = entities;
        },
        setResultAfterNameFilter(entities) {
            this.resultAfterNameFilter = entities;
        },
        getProjectNameById(projectId) {
            let result = "";
            this.projects.forEach(project => {
                if (project.id === projectId)
                    result = project.name;
            });
            return result;
        },
        control(clientId, enable) {
            this.enableButtonsDisabled = true;
            ClientRestApi.control(clientId, enable, (data) => {
                this.enableButtonsDisabled = false;
                this.loadClients();
            }, (error) => {
                console.error(error);
                this.enableButtonsDisabled = false;
            });
        },
        isDisconnectButtonAvailable(client) {
            return client.connectionStatus == "CONNECTED" || client.connectionStatus == "CONNECTION";
        },
        isConnectButtonAvailable(client) {
            return client.connectionStatus == "DISCONNECTED" || client.connectionStatus == "DISCONNECTION";
        },
        showConnectionErrorDialog(client) {
            this.connectionErrorDialog.message = client.connectionError;
            this.connectionErrorDialog.clientName = client.name;
            this.$refs.dialog.showModal();
        },
        closeConnectionErrorDialog() {
            this.connectionErrorDialog.message = "";
            this.$refs.dialog.close();
        },
        errorButtonDisabled(client) {
            return !client.connectionError;
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
        }
    },
    computed: {
        filteredClients() {
            return this.resultAfterNameFilter;
        },
        projectsAndClientsLoaded() {
            return this.projectsLoaded && this.clientsLoaded;
        }
    }
};

createApp(app).mount("#app");