import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import timeout from "@/timeout.js"

const app = {
    components: {

        "s-footer": Footer
    },
    mounted() {
        this.loadScript();
        timeout.exec("", () => {
            this.deleteButtonDisabled = false;
            this.isWaiting = false;
        }, 3000);
    },
    data() {
        return {
            script: {
                id: scriptId
            },
            project: {
                name: ""
            },
            deleteButtonDisabled: true,
            isWaiting: true
        }
    },
    methods: {
        loadScript() {
            ScriptRestApi.get(scriptId, (data) => {
                this.script = data;
                this.loadProject();
            });
        },
        loadProject() {
            ProjectRestApi.get(this.script.projectId, (data) => {
                this.project = data;
            });
        },
        deleteScript() {
            this.deleteButtonDisabled = true;
            ScriptRestApi.del(scriptId, (data) => {
                this.goToAllScriptsPage();
            }, (error) => {
                console.error(error);
                this.deleteButtonDisabled = false;
            });
        },
        goToAllScriptsPage() {
            window.location.href = "/script/all";
        }
    }
}

createApp(app).mount("#app");