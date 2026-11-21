import { initDragMe, restoreBottomPanel, minimizeBottomPanel } from "./page.js";
import { createApp } from "vue"
    ;
import CodeMirror from "@/components/CodeMirror.vue"
import IncludedScripts from "@/components/IncludedScripts.vue"
import ExecuteScript from "@/components/ExecuteScript.vue"
import timeout from "@/timeout.js"
import ScriptRestApi from "@/rest_api/ScriptRestApi.js"
import luaUtils from "@/lua-utils.js";
import utils from "@/utils.js";

const app = {
    components: {
        "s-code-mirror": CodeMirror,
        "s-included-scripts": IncludedScripts,
        "s-execute-script": ExecuteScript,
    },
    mounted() {
        this.loadScript();
        this.addExitEventListener();
        this.addKeydownEventListener();
        this.getNumberOfErrors();
        this.isScriptEnabled();
        initDragMe(() => {
            this.output.show = false;
        }, () => {
            this.output.show = true;
        });
    },
    data() {
        return {
            script: {
                id: scriptId,
                code: "",
                includedScripts: []
            },
            output: {
                message: "--",
                show: false,
                autoHiding: true
            },
            luaChanged: false,
            scriptChanged: false,
            scriptLoaded: false,
            messagesForExecuteScript,
            messagesForIncludedScripts,
            beautifyButtonDisabled: false,
            saveInAction: false,
            numberOfErrors: 0,
            enabled: false
        }
    },
    methods: {
        addKeydownEventListener() {
            document.addEventListener("keydown", (event) => {
                if (event.ctrlKey && event.key.toLowerCase() === "enter") {
                    event.preventDefault();
                    this.$refs.executeScriptRef.execute();
                }
            })
        },
        control(flag) {
            timeout.cancel("script_state");
            ScriptRestApi.enable(this.script.id, flag, (data) => {
                this.isScriptEnabled();
                this.errors = [];
            }, (error) => {
                this.errors = error.response.data;
                this.isScriptEnabled();
            });
        },
        isScriptEnabled() {
            ScriptRestApi.isEnabled(this.script.id, (data) => {
                this.enabled = data;
                timeout.exec("script_state", this.isScriptEnabled, 2000);
            }, (error) => {
                console.error(error);
                timeout.exec("script_state", this.isScriptEnabled, 5000);
            });
        },
        getNumberOfErrors() {
            ScriptRestApi.getNumberOfErrors(this.script.id, (data) => {
                this.numberOfErrors = data;
                timeout.exec("get_number_of_errors", this.getNumberOfErrors, 2000);
            }, (error) => {
                console.error(error);
                timeout.exec("get_number_of_errors", this.getNumberOfErrors, 5000);
            });
        },
        goToErrorPage() {
            window.open("/script/" + this.script.id + "/errors", "_blank");
        },
        goToLogPage() {
            window.open("/script/" + this.script.id + "/log", "_blank");
        },
        goToDocsPage() {
            window.open("/doc/", "_blank");
        },
        hideOutputMessage() {
            this.output.show = false;
            minimizeBottomPanel();
        },
        showOutputMessage() {
            this.output.show = true;
            restoreBottomPanel();
        },
        toggleOutputMessage() {
            if (this.output.show)
                this.hideOutputMessage();
            else
                this.showOutputMessage();
        },
        saveScript() {
            this.saveInAction = true;

            let dto = {
                id: this.script.id,
                code: this.script.code,
                includedScripts: this.script.includedScripts
            };

            ScriptRestApi.updateCode(dto, (data) => {
                this.scriptChanged = false;
                this.luaChanged = false;
                timeout.exec("save", () => {
                    this.saveInAction = false;
                }, 1000);

                this.output.message = "-- " + utils.getFormattedTime(new Date()) + " saved";
                this.showOutputMessage();
            }, (error) => {
                this.output.message = "-- " + utils.getFormattedTime(new Date()) + " error\n\r" + errorMessage(error.response.data);
                this.showOutputMessage();
                timeout.exec("save", () => {
                    this.saveInAction = false;
                }, 1000);
            });
        },
        beautifyLua() {
            this.beautifyButtonDisabled = true;

            timeout.exec("beautify", () => {
                this.beautifyButtonDisabled = false;
            }, 1000);

            luaUtils.beautify(this.script.code, (data) => {
                this.luaChangedCallback(data);
                this.output.message = "-- " + utils.getFormattedTime(new Date()) + " formatted";
                this.showOutputMessage();
            }, (error) => {
                this.output.message = "-- " + utils.getFormattedTime(new Date()) + " error\n\r" + error.response.data[0].message;
                this.showOutputMessage();
            });
        },
        luaChangedCallback(code) {
            if (this.script.code === code)
                return;

            this.script.code = code;
            this.luaChanged = true;
        },
        loadScript() {
            ScriptRestApi.get(scriptId, (data) => {
                this.script = data;
                this.setOutputMessage();
                this.scriptLoaded = true;
            });
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.scriptChanged || this.luaChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        includedScriptsChangedCallback(value) {
            this.script.includedScripts = value;
            this.scriptChanged = true;
        },
        setExecutionResult(message) {
            this.showOutputMessage();
            this.output.message = message;
        },
        setOutputMessage() {
            this.output.message = "-- script: " + this.script.name;
        },
        clickOnCodeMirror() {
            if (this.output.autoHiding) {
                this.hideOutputMessage();
            }
        },
        setOutputAutoHiding(flag) {
            this.output.autoHiding = flag;
        },
        goToEditPage() {
            window.open("/script/" + scriptId + "/edit", "_blank");
        }
    },
    computed: {
        saveButtonDisabled() {
            return !this.luaChanged && !this.scriptChanged || this.saveInAction;
        }
    }
};

function errorMessage(errors) {
    let result = "";
    for (let i = 0; i < errors.length; i++) {
        const error = errors[i];
        result += error.message;

        if (i < errors.length - 1)
            result += "; ";
    }
    return result;
}


createApp(app).mount("#app");
