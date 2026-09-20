import common from "/static/js/components/cron/common.js"

const seconds = [
    {
        label: "every_second",
        expression: "*"
    },
    {
        label: "every_even_second",
        expression: "*/2"
    },
    {
        label: "every_odd_second",
        expression: "1-59/2"
    }
];

export default {

    template: `<s-common    :messages="messages" 
                            :title="messages.seconds" 
                            :index="0" 
                            :options="options"
                            :element="element" 
                            @changed="changed"><s-common>`,

    components: {
        "s-common": common
    },

    props: ["element", "messages"],

    data() {
        return {
            options: []
        }
    },

    mounted() {
        this.prepareTextOptions();
        this.prepareNumberOptions();
    },

    methods: {

        prepareTextOptions() {
            seconds.forEach(item => {
                let newItem = {
                    label: this.messages[item.label],
                    expression: item.expression
                };
                this.options.push(newItem);
            });
        },

        prepareNumberOptions() {
            for (let i = 0; i < 60; i++) {
                this.options.push(
                    {
                        label: i.toString(),
                        expression: i.toString()
                    }
                );
            }
        },

        changed(value, index) {
            this.$emit("changed", value, index);
        },

    },


};