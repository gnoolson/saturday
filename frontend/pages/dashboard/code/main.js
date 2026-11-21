import { createApp } from "vue"
import CodeMirror from "@/components/CodeMirror.vue"
import timeout from "@/timeout.js"
import DashboardRestApi from "@/rest_api/DashboardRestApi";
import ErrorMessage from "@/components/ErrorMessage.vue"

const app = {
    components: {
        "s-code-mirror": CodeMirror,

        "s-error-message": ErrorMessage
    },
    mounted() {
        this.loadDashboard();
        this.addExitEventListener();
    },
    data() {
        return {
            dashboard: {
                id: dashboardId,
                html: ""
            },
            errors: [],
            htmlChanged: false,
            dashboardLoaded: false,
            saveInAction: false,
            beautifyButtonDisabled: false
        }
    },
    methods: {
        loadDashboard() {
            DashboardRestApi.get(dashboardId, (data) => {
                this.dashboard = data;
                this.dashboardLoaded = true;
            });
        },
        htmlChangedCallback(code) {
            if (this.dashboard.html === code)
                return;

            this.dashboard.html = code;
            this.htmlChanged = true;
        },
        beautifyHtml() {
            this.beautifyButtonDisabled = true;

            timeout.exec("beauty", () => {
                this.beautifyButtonDisabled = false;
            }, 1000);

            this.dashboard.html = html_beautify(this.dashboard.html, beautifyOptions);
            this.htmlChanged = true;
        },
        saveHtml() {
            this.saveInAction = true;
            timeout.exec("save", () => {
                this.saveInAction = false;
            }, 1000);

            let dto = {
                id: this.dashboard.id,
                html: this.dashboard.html
            }
            DashboardRestApi.updateCode(dto, (data) => {
                this.htmlChanged = false;
            }, (error) => {
                this.errors = error.response.data;
            });
        },
        addExitEventListener() {
            window.addEventListener("beforeunload", (e) => {
                if (this.htmlChanged) {
                    e.preventDefault();
                    e.returnValue = "";
                }
            });
        },
        openDashboard() {
            window.open("/dashboard/open/" + dashboardId, "_blank");
        },
        goToEditPage() {
            window.open("/dashboard/" + dashboardId + "/edit", "_blank");
        }
    },
    computed: {
        saveButtonDisabled() {
            return !this.htmlChanged || this.saveInAction;
        }
    }
};

createApp(app).mount("#app");


const beautifyOptions = {
    indent_size: 4,
    indent_char: " ",
    max_preserve_newlines: 2,
    preserve_newlines: true,
    brace_style: "collapse",
    indent_scripts: "normal",

    indent_inner_html: true,
    wrap_line_length: 300,
    wrap_attributes: "auto",

    css: {
        indent_size: 4,
        newline_between_rules: true
    },

    js: {
        indent_size: 4,
        space_after_anon_function: true
    }
};

