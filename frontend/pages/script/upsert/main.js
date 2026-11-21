import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import Id from "@/components/Id.vue"
import Input from "@/components/Input.vue"
import Textarea from "@/components/Textarea.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ErrorMessage from "@/components/ErrorMessage.vue"
import SelectProject from "@/components/SelectProject.vue"
import SelectEventHandler from "@/components/SelectEventHandler.vue"
import CB from "@/components/CB.vue"

const app = {
    components: {

        "s-footer": Footer,
        "s-error-message": ErrorMessage,
        "s-input": Input,
        "s-textarea": Textarea,
        "s-id": Id,
        "s-select-project": SelectProject,
        "s-select-event-handler": SelectEventHandler,
        "s-cb": CB
    },
    mounted() {
        this.loadScript();
        this.loadScripts();
        this.loadProjects();
        this.addExitEventListener();
    },
    data() {
        return {
            script: {
                id: scriptId,
                projectId: emptyId,
                errorEventHandler: emptyId
            },
            scripts: [],
            projects: [],
            errors: [],
            emptyId: emptyId,
            scriptChanged: false,
            scriptLoaded: false,
            projectsLoaded: false,
            scriptsLoaded: false,
            saveInAction: false,
        }
    },
    methods: {
        saveScript() {
            ScriptRestApi.upsert(this.script, (data) => {
                this.scriptChanged = false;
                window.location.href = "/script/all";
            }, (error) => {
                this.errors = error.response.data;
            });
        },
        checkAvailableProjects() {
            if (this.projects.length == 0) {
                this.errors.push({ field: "projectId", message: message_1 });
            }
        },
        goToDeletePage() {
            window.location.href = "/script/" + this.script.id + "/delete";
        },
        goToCodePage() {
            window.location.href = "/script/" + this.script.id + "/code";
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.scriptChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        scriptChangedCallback() {
            if (this.dataLoaded) {
                this.scriptChanged = true;
            }
        },
        loadScript() {
            ScriptRestApi.get(this.script.id, (data) => {
                this.script = data;
                this.scriptLoaded = true;
            });
        },
        loadScripts() {
            ScriptRestApi.getAll((data) => {
                this.scripts = data;
                this.scriptsLoaded = true;
            });
        },
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
                this.checkAvailableProjects();
            });
        },
        minutesToString(value) {
            if (value == 0)
                return "NO"

            const hours = Math.floor(value / 3600);
            const minutes = Math.floor((value % 3600) / 60);
            const result = hours.toString().padStart(2, "0") +
                message_2 +
                " " +
                minutes.toString().padStart(2, "0") +
                message_3;

            return result;
        }
    },
    computed: {
        saveButtonDisabled() {
            return !this.scriptChanged || this.saveInAction;
        },
        deleteButtonDisabled() {
            return this.script.id === this.emptyId;
        },
        filteredScripts() {
            return this.scripts.filter(script => {
                return script.id !== this.script.id;
            });
        },
        dataLoaded() {
            return this.scriptLoaded && this.projectsLoaded && this.scriptsLoaded;
        }
    }
};

createApp(app).mount("#app");