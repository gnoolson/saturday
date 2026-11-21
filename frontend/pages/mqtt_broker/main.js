import { createApp } from "vue"
import Footer from "@/components/Footer.vue"
import Input from "@/components/Input.vue"
import ErrorMessage from "@/components/ErrorMessage.vue"
import timeout from "@/timeout.js"
import CB from "@/components/CB.vue"
import RestApi from "./RestApi.js";

const app = {
    components: {
        "s-input": Input,
        "s-cb": CB,
        "s-error-message": ErrorMessage,
        "s-footer": Footer
    },
    data() {
        return {
            superAccess: SUPER_ACCESS,
            broker: {
                started: false,
                anonymous: true,
                port: 0,
                useBrokerConfigurationOnly: false
            },
            errors: [],
            waiting: false,
            connectedClients: [],
            users: [],
            createUserDialog: {
                user: {
                    username: "",
                    password: ""
                }
            }
        }
    },
    mounted() {
        this.loadBroker();
        this.loadConnectedClients();
        this.loadUsers();
    },
    methods: {
        deleteUser(user) {
            if (!confirm(message_1))
                return;

            const dto = {
                username: user.username
            };

            this.waiting = true;
            RestApi.deleteUser(dto, (data) => {
                this.loadUsers();
            }, (error) => {
                this.errors = error.response.data;
                this.waiting = false;
            });
        },
        closeCreateUserDialog() {
            this.$refs.dialog.close();
        },
        showCreateUserDialog() {
            this.$refs.dialog.showModal();
            this.createUserDialog.user.username = "";
            this.createUserDialog.user.password = "";
        },
        createNewUser() {
            let dto = {
                username: this.createUserDialog.user.username,
                password: this.createUserDialog.user.password
            }
            this.waiting = true;
            RestApi.createNewUser(dto, (data) => {
                this.loadUsers();
                this.closeCreateUserDialog();
            }, (error) => {
                this.errors = error.response.data;
                this.waiting = false;
            });
        },
        loadUsers() {
            this.waiting = true;
            RestApi.getUsers((data) => {
                this.users = data;
                this.waiting = false;
            });
        },
        loadConnectedClients() {
            RestApi.getConnectedClients((data) => {
                this.connectedClients = data;
                timeout.exec("load_connected_clients_loop", this.loadConnectedClients, 1000);
            }, (error) => {
                console.error(error);
                timeout.exec("load_connected_clients_loop", this.loadConnectedClients, 5000);
            });
        },
        start() {
            this.waiting = true;
            const dto = {
                allowAnonymousConnections: this.broker.allowAnonymousConnections,
                port: this.broker.port
            };

            RestApi.startBroker(dto, (data) => {
                this.errors = [];
                this.loadBroker();
            }, (error) => {
                this.waiting = false;
                this.errors = error.response.data;
            });
        },
        stop() {
            this.waiting = true;
            RestApi.stopBroker((data) => {
                this.errors = [];
                this.loadBroker();
            }, (error) => {
                this.waiting = false;
                this.errors = error.response.data;
            });
        },
        loadBroker() {
            this.waiting = true;
            RestApi.getBroker((data) => {
                this.broker = data;
                this.waiting = false;
            });
        },
        tsToString(timestamp) {
            if (timestamp == 0)
                return "-";
            return new Date(timestamp).toLocaleString();
        },
    },
    computed: {
        controlButtonDisabled() {
            return this.waiting;
        }
    }
};

createApp(app).mount("#app");