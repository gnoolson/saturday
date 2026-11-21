import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import Id from "@/components/Id.vue"
import Input from "@/components/Input.vue"
import Textarea from "@/components/Textarea.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ErrorMessage from "@/components/ErrorMessage.vue"
import SelectProject from "@/components/SelectProject.vue"
import SelectScript from "@/components/SelectScript.vue"
import CronExpression from "@/components/cron/CronExpression.vue"
import ScheduleRestApi from "@/rest_api/ScheduleRestApi.js"

const app = {
    components: {

        "s-footer": Footer,
        "s-error-message": ErrorMessage,
        "s-input": Input,
        "s-textarea": Textarea,
        "s-id": Id,
        "s-select-project": SelectProject,
        "s-select-script": SelectScript,
        "s-cron-expression": CronExpression
    },
    mounted() {
        this.loadSchedule();
        this.loadScripts();
        this.loadProjects();
        this.addExitEventListener();
    },
    data() {
        return {
            schedule: {
                id: scheduleId,
                projectId: emptyId,
                scriptId: emptyId
            },
            scripts: [],
            projects: [],
            errors: [],
            emptyId: emptyId,
            scheduleChanged: false,
            scheduleLoaded: false,
            projectsLoaded: false,
            scriptsLoaded: false,
            saveInAction: false,
            messages: messages
        }
    },
    methods: {
        loadSchedule() {
            ScheduleRestApi.get(scheduleId, (data) => {
                this.schedule = data;
                this.scheduleLoaded = true;
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
        scheduleChangedCallback() {
            if (this.dataLoaded)
                this.scheduleChanged = true;
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
                if (this.scheduleChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        save() {
            this.saveInAction = true;
            ScheduleRestApi.save(this.schedule, (data) => {
                this.scheduleChanged = false;
                this.saveInAction = false;
                window.location.href = "/schedule/all";
            }, (error) => {
                this.errors = error.response.data;
                this.saveInAction = false;
            });
        },
        goToDeletePage() {
            window.location.href = "/schedule/" + this.schedule.id + "/delete";
        },
        expressionChanged(newExpression) {
            this.schedule.cronExpression = newExpression;
            this.scheduleChanged = true;
        }
    },
    computed: {
        saveButtonDisabled() {
            return !this.scheduleChanged || this.saveInAction;
        },
        deleteButtonDisabled() {
            return this.schedule.id === this.emptyId;
        },
        dataLoaded() {
            return this.scheduleLoaded && this.projectsLoaded && this.scriptsLoaded;
        }
    }
};

createApp(app).mount("#app");