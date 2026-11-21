import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import Id from "@/components/Id.vue"
import Input from "@/components/Input.vue"
import Textarea from "@/components/Textarea.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ErrorMessage from "@/components/ErrorMessage.vue"

const app = {
    components: {
        "s-input": Input,
        "s-textarea": Textarea,
        "s-error-message": ErrorMessage,
        "s-id": Id,

        "s-footer": Footer
    },
    mounted() {
        this.loadProject();
        this.addExitEventListener();
    },
    data() {
        return {
            project: {
                id: projectId
            },
            errors: [],
            projectChanged: false,
            saveInAction: false,
            emptyId: emptyId
        }
    },
    methods: {
        loadProject() {
            ProjectRestApi.get(this.project.id, (data) => {
                this.project = data;
            });
        },
        saveProject() {
            this.saveInAction = true;
            ProjectRestApi.save(this.project, (data) => {
                this.projectChanged = false;
                this.saveInAction = false;
                window.location.href = "/project/all";
            }, (error) => {
                this.errors = error.response.data;
                this.saveInAction = false;
            });
        },
        projectChangedCallback() {
            this.projectChanged = true;
        },
        goToDeletePage() {
            window.location.href = "/project/" + this.project.id + "/delete";
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.projectChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        }
    },
    computed: {
        deleteButtonDisabled() {
            return this.project.id === emptyId;
        },
        saveButtonDisabled() {
            return !this.projectChanged || this.saveInAction;
        },
        copyIdAvailable() {
            return this.project.id !== emptyId;
        }
    }
};

createApp(app).mount("#app");
