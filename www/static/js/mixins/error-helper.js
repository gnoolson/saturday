export default {

    computed: {

        message() {
            let result = "";
            for (let i = 0; i < this.errors.length; i++) {
                const error = this.errors[i];
                if (error.field == this.field) {
                    result += error.message;

                    if (i < this.errors.length - 1)
                        result += "; ";
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