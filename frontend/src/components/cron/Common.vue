<template>

    <div class="pure-control-group">
        <label>
            {{ title }}
        </label>

        <select v-model="selectedExpression" @change="changed()" class="pure-u-1 pure-u-sm-1-2">
            <option :value="option.expression" v-for="option in options">{{ option.label }}</option>
            <option v-if="unknownExpressionFlag" :value="unknownExpressionValue">{{ unknownExpressionValue }}</option>
        </select>
    </div>

</template>

<script>

export default {
    props: ["element", "messages", "title", "index", "options"],
    data() {
        return {
            selectedExpression: "",
            unknownExpressionFlag: false
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
        element(value) {
            if(value == undefined || !value.length)
                return;

            this.update();
        }
    }
};

</script>
