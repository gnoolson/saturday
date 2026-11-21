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
            errors: [],
            importFile: {
                data: null,
                ready: false
            },
            uploadResult: {
                filePath: "",
                fileName: "",
                projects: [],
            },
            fileUploadSuccess: false,
            importSuccess: false,
            importInAction: false,
            uploadInAction: false
        }
    },
    mounted() {
        this.addFileSelectedEventListener();
    },
    methods: {
        addFileSelectedEventListener() {
            let importFile = this.importFile;
            document.getElementById("real-input").addEventListener("change", function () {
                importFile.data = this;
                importFile.ready = true;
            });
        },
        uploadFile() {
            this.uploadInAction = true;
            this.fileUploadSuccess = false;

            let formData = new FormData();
            formData.append("file", this.importFile.data.files[0]);

            RestApi.upload(formData, (data) => {
                this.uploadResult = data;
                this.errors = [];
                this.fileUploadSuccess = true;
                this.uploadInAction = false;
            }, (error) => {
                this.errors = error.response.data;
                this.uploadInAction = false;
                this.fileUploadSuccess = false;
                this.importFile.ready = false;
            });
        },
        importAction() {
            this.importInAction = true;
            this.importSuccess = false;
            let selectedProjectIdList = [];
            this.uploadResult.projects.forEach(project => {
                if (project.selected)
                    selectedProjectIdList.push(project.id);
            });

            this.unselectProjects();

            let dto = {
                projectIdSet: selectedProjectIdList,
                path: this.uploadResult.path
            };

            RestApi.importAction(dto, (data) => {
                this.errors = [];
                this.importSuccess = true;
                this.importInAction = false;
            }, (error) => {
                this.errors = error.response.data;
                this.importSuccess = false;
                this.importInAction = false;
            });
        },
        cancelImport() {
            this.fileUploadSuccess = false;
            this.importFile.ready = false;
            this.importSuccess = false;
        },
        unselectProjects() {
            this.uploadResult.projects.forEach(project => {
                project.selected = false;
            });
        }
    },
    computed: {
        uploadButtonAvailable() {
            return this.importFile.ready;
        },
        importButtonDisabled() {
            let result = true;

            this.uploadResult.projects.forEach(project => {
                if (project.selected)
                    result = false;
            });

            return result || this.importInAction || this.importSuccess;
        },
        uploadButtonDisabled() {
            return this.uploadInAction;
        }
    }
};

createApp(app).mount("#app");


