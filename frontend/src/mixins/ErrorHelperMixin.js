export default {
    props: {
        field: {
            type: String,
            default: ""
        },
        errors: {
            type: Array,
            default: []
        }
    },
    computed: {
        message() {
            let result = "";
            let counter = 0;
            for (let i = 0; i < this.errors.length; i++) {
                const error = this.errors[i];
                if (error.field == this.field) {
                    if (counter > 0)
                        result += "; ";

                    result += error.message;
                    counter++;
                }
            }
            return result;
        },
        invalid() {
            for (let i = 0; i < this.errors.length; i++) {
                const error = this.errors[i];
                if (error.field == this.field) {
                    return true;
                }
            }
            return false;
        }
    }
}