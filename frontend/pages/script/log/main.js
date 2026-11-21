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
        this.loadDebugLevel();
    },
    data() {
        return {
            script: {
                id: scriptId,
                name: ""
            },
            superAccess: superAccess,
            log: "",
            debugEnabled: false,
            showRows: 1000
        }
    },
    methods: {
        loadScript() {
            ScriptRestApi.get(scriptId, (data) => {
                this.script = data;
                this.loadLog();
            });
        },
        loadDebugLevel() {
            ScriptRestApi.isDebugLogLevelAvailable(this.script.id, (data) => {
                this.debugEnabled = data;
            });
        },
        debugEnable(flag) {
            ScriptRestApi.setDebugLogLevel(this.script.id, flag, (data) => {
                this.debugEnabled = flag;
            });
        },
        loadRows(rows) {
            this.showRows = rows;
            this.loadLog();
        },
        loadLog() {
            ScriptRestApi.getLog(this.script.id, this.showRows, (data) => {
                this.log = data;
                this.scrollToBottom();
            });
        },
        scrollToBottom() {
            timeout.exec("scroll_to_bottom", () => {
                var objDiv = document.getElementById("log-pre");
                objDiv.scrollTop = objDiv.scrollHeight;
            }, 200);
        },
        clear() {
            if (!confirm(message_1))
                return;

            ScriptRestApi.clearLog(scriptId, (data) => {
                this.loadLog();
            }, (error) => {
                console.error(error);
                this.loadLog();
            });
        },
        goToEditPage() {
            window.location.href = "/script/" + this.script.id + "/edit";
        },
        goToCodePage() {
            window.location.href = "/script/" + this.script.id + "/code";
        }
    },

    computed: {
        clearButtonDisabled() {
            return !this.log.length;
        },
        editButtonAvailable() {
            return this.superAccess;
        },
        codeButtonAvailable() {
            return this.superAccess;
        },
    }
};

createApp(app).mount("#app");