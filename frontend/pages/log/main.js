import timeout from "@/timeout.js";
import { createApp } from "vue"
import Footer from "@/components/Footer.vue"
import RestApi from "./RestApi";

const app = {
    components: {
        "s-footer": Footer
    },
    mounted() {
        this.loadLog();
    },
    data() {
        return {
            log: "",
            showRows: 1000
        }
    },
    methods: {
        loadRows(rows) {
            this.showRows = rows;
            this.loadLog();
        },
        loadLog() {
            RestApi.load(this.showRows, (data) => {
                this.log = data;
                this.scrollToBottom();
            });
        },
        scrollToBottom() {
            timeout.exec("scroll_to_bottom", () => {
                var objDiv = document.getElementById("log-pre");
                objDiv.scrollTop = objDiv.scrollHeight;
            }, 200);
        }
    }
};

createApp(app).mount("#app");