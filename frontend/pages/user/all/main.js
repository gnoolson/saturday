import { createApp } from "vue"
import Footer from "@/components/Footer.vue"
import Input from "@/components/Input.vue"
import ErrorMessage from "@/components/ErrorMessage.vue"
import RestApi from "./RestApi.js"
import timeout from "@/timeout.js"

const app = {
    components: {
        "s-input": Input,
        "s-footer": Footer,
        "s-error-message": ErrorMessage,
    },
    mounted() {
        this.loadUsers();
    },
    data() {
        return {
            errors: [],
            users: [],
            superAccess: SUPER_ACCESS,
            you: {
                username: "",
                role: ""
            },
            createUserDialog: {
                user: {},
            },
            editUserDialog: {
                user: {},
                disableActionButton: false,
                showSuccessMessage: false
            }
        }
    },
    methods: {
        deleteUser() {
            if (!confirm(message_1))
                return;

            let dto = {
                username: this.editUserDialog.user.username
            };

            RestApi.del(dto, (data) => {
                this.loadUsers();
                this.closeEditUserDialog();
            }, (error) => {
                this.errors = error.response.data;
            });
        },
        changeUserRole() {
            this.errors = [];
            this.editUserDialog.disableActionButton = true;
            this.editUserDialog.showSuccessMessage = false;

            let dto = {
                username: this.editUserDialog.user.username,
                role: this.editUserDialog.user.role
            }

            RestApi.setRole(dto, (data) => {
                timeout.exec("change_role", () => {
                    this.loadUsers();
                    this.closeEditUserDialog();
                }, 2000);
                this.editUserDialog.showSuccessMessage = true;
            }, (error) => {
                this.errors = error.response.data;
                this.editUserDialog.disableActionButton = false;
            });
        },
        changeUserPassword() {
            this.errors = [];

            if (this.editUserDialog.user.password != this.editUserDialog.user.confirmPassword) {
                this.errors = [{ field: "service", message: message_2 }];
                return;
            }

            this.editUserDialog.disableActionButton = true;
            this.editUserDialog.showSuccessMessage = false;

            let dto = {
                username: this.editUserDialog.user.username,
                password: this.editUserDialog.user.password
            }

            RestApi.setPassword(dto, (data) => {
                timeout.exec("change_password", () => {
                    this.loadUsers();
                    this.closeEditUserDialog();
                }, 2000);
                this.editUserDialog.showSuccessMessage = true;
            }, (error) => {
                this.errors = error.response.data;
                this.editUserDialog.disableActionButton = false;
            });
        },
        openEditUserDialog(user) {
            this.$refs.editUserDialog.showModal();
            this.editUserDialog.user = {
                username: user.username,
                password: "",
                confirmPassword: "",
                role: user.role
            }
        },
        closeEditUserDialog() {
            this.$refs.editUserDialog.close();
            this.editUserDialog.user = {};
            this.editUserDialog.showSuccessMessage = false;
            this.editUserDialog.disableActionButton = false;
            this.errors = [];
        },
        createNewUser() {
            this.errors = [];

            if (this.createUserDialog.user.password != this.createUserDialog.user.confirmPassword) {
                this.errors = [{ field: "service", message: message_2 }];
                return;
            }

            let dto = {
                username: this.createUserDialog.user.username,
                password: this.createUserDialog.user.password,
                role: this.createUserDialog.user.role
            }

            RestApi.create(dto, (data) => {
                this.loadUsers();
                this.closeCreateUserDialog();
            }, (error) => {
                this.errors = error.response.data;
            });
        },
        closeCreateUserDialog() {
            this.$refs.createUserDialog.close();
            this.editUserDialog.user = {};
            this.errors = [];
        },
        openCreateUserDialog() {
            this.$refs.createUserDialog.showModal();

            this.createUserDialog.user = {
                username: "",
                role: "SUPER",
                password: "",
                confirmPassword: ""
            }
        },
        loadUsers() {
            RestApi.getAll((data) => {
                this.users = data;
                this.initYou();
            });
        },
        initYou() {
            this.users.forEach((user) => {
                if (user.you) {
                    this.you.username = user.username;
                    this.you.role = user.role;
                }
            });
        },
        editButtonAvailable(user) {
            return this.superAccess || this.you.username == user.username
        }
    },
    computed: {
        deleteButtonDisabled() {
            return this.editUserDialog.user.username == this.you.username || !this.superAccess || this.editUserDialog.disableActionButton;
        },
        changeRoleDisabled() {
            return this.editUserDialog.user.username == this.you.username || !this.superAccess || this.editUserDialog.disableActionButton;
        }
    }
};

createApp(app).mount("#app");