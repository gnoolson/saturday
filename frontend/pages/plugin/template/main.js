import { createApp } from "vue"
import Footer from "@/components/Footer.vue"

const app = {
    components: {
        "s-footer": Footer
    },
    data() {
        return {
        }
    },
};

createApp(app).mount("#app");