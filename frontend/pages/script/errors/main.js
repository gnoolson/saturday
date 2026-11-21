import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import timeout from "@/timeout.js"


const app = {
    components: {

        "s-footer": Footer
    },
    mounted() {
        this.loadScript();
    },
    data() {
        return {
            script: {
                id: scriptId,
                name: ""
            },
            errors: [],
            maxNumberOfErrors,
            superAccess,
            stackDialog: {
                stack: "",
                date: null
            }
        }
    },
    methods: {
        loadErrorsLoop() {
            this.loadErrors();
            timeout.exec("load_errors_loop", this.loadErrorsLoop, 5000);
        },
        loadScript() {
            ScriptRestApi.get(scriptId, (data) => {
                this.script = data;
                this.loadErrorsLoop();
            });
        },
        loadErrors() {
            ScriptRestApi.getErrors(scriptId, (data) => {
                this.errors = data;
            });
        },
        clearErrors() {
            ScriptRestApi.clearErrors(scriptId, (data) => {
                this.loadErrors();
            });
        },
        goToEditPage() {
            window.location.href = "/script/" + this.script.id + "/edit";
        },
        goToCodePage() {
            window.location.href = "/script/" + this.script.id + "/code";
        },
        showStack(error) {
            this.stackDialog.stack = error.stack;
            this.stackDialog.date = this.toDate(error.time);
            this.$refs.dialog.showModal();

        },
        closeDialog() {
            this.stackDialog.stack = "";
            this.stackDialog.date = null;
            this.$refs.dialog.close();
        },
        toDate(timestamp) {
            const d = new Date(timestamp);
            const yyyy = d.getFullYear();
            const mm = String(d.getMonth() + 1).padStart(2, "0");
            const dd = String(d.getDate()).padStart(2, "0");
            const hh = String(d.getHours()).padStart(2, "0");
            const min = String(d.getMinutes()).padStart(2, "0");
            const ss = String(d.getSeconds()).padStart(2, "0");
            return `${yyyy}-${mm}-${dd} ${hh}:${min}:${ss}`;
        }
    },
    computed: {
        clearErrorsButtonDisabled() {
            return !this.errors.length;
        },
        scriptBlocked() {
            return this.maxNumberOfErrors <= this.errors.length;
        }
    }
}

createApp(app).mount("#app");