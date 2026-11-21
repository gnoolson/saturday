import { createApp } from "vue"
import Footer from "@/components/Footer.vue"
import ErrorMessage from "@/components/ErrorMessage.vue"
import RestApi from "./RestApi";

const app = {
    components: {
        "s-error-message": ErrorMessage,
        "s-footer": Footer
    },
    data() {
        return {
            plugins: [],
            waiting: false
        }
    },
    mounted() {
        this.loadPlugins();
    },
    methods: {
        enableButtonAvailable(plugin) {
            return plugin.status == 'DISABLED' || plugin.status == 'STOPPED';
        },
        disableButtonAvailable(plugin) {
            return plugin.status == 'STARTED';
        },
        loadPlugins() {
            RestApi.getAll((data) => {
                this.plugins = data;
            });
        },
        control(pluginId, flag) {
            this.waiting = true;

            RestApi.control(pluginId, flag, (data) => {
                this.loadPlugins();
                this.waiting = false;
            }, (error) => {
                this.waiting = false;
            });
        }
    }
};

createApp(app).mount("#app");