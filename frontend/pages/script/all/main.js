import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import NameFilter from "@/components/NameFilter.vue"
import ProjectFilter from "@/components/ProjectFilter.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import timeout from "@/timeout.js"
import CopyId from "@/components/CopyId.vue";

const app = {
    components: {
        "s-name-filter": NameFilter,
        "s-project-filter": ProjectFilter,
        "s-copy-id" : CopyId,
        "s-footer": Footer
    },
    mounted() {
        this.loadScriptsLoop();
        this.loadProjects();
    },
    data() {
        return {
            resultAfterProjectFilter: [],
            resultAfterNameFilter: [],
            scripts: [],
            projects: [],
            projectsLoaded: false,
            scriptsLoaded: false,
            superAccess: SUPER_ACCESS,
            controlButtonsDisabled: false,
            expandedDescriptions: new Set()
        }
    },
    methods: {
        loadScriptsLoop() {
            this.loadScripts();
            timeout.exec("load_scripts_loop", this.loadScriptsLoop, 2000);
        },
        goToErrorsPage(script) {
            window.location.href = "/script/" + script.id + "/errors";
        },
        goToLogPage(script) {
            window.location.href = "/script/" + script.id + "/log";
        },
        loadScripts() {
            ScriptRestApi.getAll((data) => {
                this.scripts = data;
                this.scriptsLoaded = true;
            });
        },
        goToCreateNewPage() {
            window.location.href = "/script/create";
        },
        goToEditPage(script) {
            window.location.href = "/script/" + script.id + "/edit";
        },
        goToCodePage(script) {
            window.location.href = "/script/" + script.id + "/code";
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
        hasErrors(script) {
            return script.errors > 0;
        },
        isEnableButtonAvailable(script) {
            if (!this.superAccess)
                return false;

            return !script.enabled;
        },
        isDisableButtonAvailable(script) {
            if (!this.superAccess)
                return false;

            return script.enabled;
        },
        isEnableButtonDisabled(script) {
            return script.blocked || this.controlButtonsDisabled;
        },
        isDisableButtonDisabled(script) {
            return false || this.controlButtonsDisabled;
        },
        control(script, flag) {
            this.controlButtonsDisabled = true;
            ScriptRestApi.enable(script.id, flag, (data) => {
                this.loadScripts();
                this.controlButtonsDisabled = false;
            }, () => {
                this.controlButtonsDisabled = false;
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
        }
    },
    computed: {
        filteredScripts() {
            return this.resultAfterNameFilter;
        },
        projectsAndScriptsLoaded() {
            return this.projectsLoaded && this.scriptsLoaded;
        },
        scriptsWithErrors() {
            let result = [];
            this.filteredScripts.forEach((script) => {
                if (this.hasErrors(script)) {
                    result.push(script);
                }
            });
            return result;
        }
    }
};

createApp(app).mount("#app");