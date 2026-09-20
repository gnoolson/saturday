export default {

    template:  `<label>
                    {{title}}
                    <select v-model="selectedExpression" @change="changed()">
                        <option :value="option.expression" v-for="option in options" >{{option.label}}</option>
                        <option v-if="unknownExpressionFlag" :value="unknownExpressionValue" >{{unknownExpressionValue}}</option>
                    </select>
                </label>`,

    props: ["element", "messages", "title", "index", "options"],

    data() {
        return {
            selectedExpression: "",
            unknownExpressionFlag : false
        }
    },

    methods: {

        changed() {
            this.$emit("changed", this.selectedExpression, this.index);
        },

         update() {
            let notFound = true;
            
            this.options.forEach(element => {
                if (element.expression == this.element) {
                    notFound = false;
                    this.selectedExpression = element.expression;
                }
            });

            if (notFound) {
                this.unknownExpressionValue = this.element;
                this.selectedExpression = this.element;
            }

            this.unknownExpressionFlag = notFound;
        },

    },

    watch: {

        element(value){
            if(!value.length)
                return;

            this.update();
        }

    }

};