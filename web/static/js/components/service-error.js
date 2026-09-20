import error_message from "/static/js/mixins/error-message.js"

export default {

    template: `<div class="error" v-if="invalid">{{field}}: {{message}}</div>`,

    props: {
        errors: {
            type: Array
        },
        field: {
            type: String,
            default: "service"
        }
    },

    mixins: [error_message],


};
