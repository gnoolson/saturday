import { createApp } from "vue"
import Input from "@/components/Input.vue"
import RestApi from "./RestApi.js";
import ErrorMessage from "@/components/ErrorMessage.vue"

const app = {
	components: {
		"s-input": Input,
		"s-error-message": ErrorMessage
	},
	data() {
		return {
			loginForm: {
				username: "",
				password: ""
			},
			errors: [],
			loginButtonDisabled: false
		}
	},
	mounted() {
		const form = document.getElementById("login-form");

		form.addEventListener("keydown", (e) => {
			if (e.key === "Enter") {
				e.preventDefault();
				this.loginAction();
			}
		});
	},
	methods: {
		closeDialog() {
			this.$refs.dialog.close();
		},
		showDialog() {
			this.$refs.dialog.showModal();
		},
		loginAction() {
			this.loginButtonDisabled = true;
			this.errors = []

			RestApi.auth(this.loginForm, (data) => {
				window.location.href = data.redirect;
			}, (error) => {
				this.errors = error.response.data;
				this.loginButtonDisabled = false;
			})
		}
	}
};

createApp(app).mount("#app");

