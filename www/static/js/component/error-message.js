import error_helper from "/static/js/mixins/error-helper.js"

export default {

    template: `<div :class="{'error-message-box' : box}" v-if="invalid" >
                    <div class="error-message">{{message}}</div>
               </div>`,

    props: {
        errors: {
            type: Array
        },
        field: {
            type: String
        },
        box : {
             type: Boolean,
             default: false
        }
    },

    mixins: [error_helper],


};
