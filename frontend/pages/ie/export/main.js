import ProjectRestApi from "@/rest_api/ProjectRestApi.js";
import RestApi from "./RestApi.js";
import ErrorMessage from "@/components/ErrorMessage.vue";
import Footer from "@/components/Footer.vue";
import { createApp } from "vue"

const app = {
    components: {
        "s-error-message": ErrorMessage,
        "s-footer": Footer
    },
    data() {
        return {
            projects: [],
            errors: [],
            exportInAction: false,
            exportFileName: "",
            exportCompleted: false
        }
    },
    mounted() {
        this.loadProjects();
    },
    methods: {
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
            });
        },
        beginExport() {
            this.exportInAction = true;
            this.errors = [];

            let projectIdArray = [];
            this.projects.forEach(project => {
                if (project.selected)
                    projectIdArray.push(project.id);
            });

            RestApi.exportAction({ projectIdSet: projectIdArray }, (data) => {
                this.exportFileName = data.fileName;
                this.exportInAction = false;
                this.exportCompleted = true;
                this.unselectProjects();
            }, (error) => {
                this.errors = error.response.data;
                this.exportInAction = false;
            });
        },
        unselectProjects() {
            this.projects.forEach(project => {
                project.selected = false;
            });
        },
        download() {
            const link = document.createElement("a");
            link.href = "/api/ie/download?fileName=" + this.exportFileName;
            link.download = this.exportFileName;
            link.click();
        }
    },
    computed: {
        beginExportButtonDisabled() {
            let result = true;

            this.projects.forEach(project => {
                if (project.selected)
                    result = false;
            });

            return result || this.exportInAction;
        }
    }
};

createApp(app).mount("#app");