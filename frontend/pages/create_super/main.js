import { createApp } from "vue"
import Input from "@/components/Input.vue";
import RestApi from "./RestApi.js";
import ErrorMessage from "@/components/ErrorMessage.vue";

const app = {
    components: {
        "s-error-message": ErrorMessage,
        "s-input": Input
    },
    data() {
        return {
            user: {
                username: "",
                password: "",
                confirmPassword: ""
            },
            errors: []
        }
    },
    mounted() {
        const form = document.getElementById("create-super-form");
        form.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                this.createEditor();
            }
        });
    },
    methods: {
        createEditor() {
            this.errors = [];
            if (this.user.password != this.user.confirmPassword) {
                this.errors.push({ field: "service", message: message_1 });
                return;
            }

            const dto = {
                username: this.user.username,
                password: this.user.password
            }

            RestApi.createEditor(dto, (data) => {
                window.location.href = "/login";
            }, (error) => {
                this.errors = error.response.data;
            });
        }
    }
};

createApp(app).mount("#app");