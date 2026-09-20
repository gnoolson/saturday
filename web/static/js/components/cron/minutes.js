import common from "/static/js/components/cron/common.js"

const minutes = [
    {
        label: "every_minute",
        expression: "*"
    },
    {
        label: "every_even_minute",
        expression: "*/2"
    },
    {
        label: "every_odd_minute",
        expression: "1-59/2"
    },
    {
        label: "every_5_minutes",
        expression: "*/5"
    },
    {
        label: "every_10_minutes",
        expression: "*/10"
    },
    {
        label: "every_15_minutes",
        expression: "*/15"
    },
    {
        label: "every_20_minutes",
        expression: "*/20"
    },
    {
        label: "every_30_minutes",
        expression: "*/30"
    }
];



export default {

    template: `<s-common    :messages="messages" 
                            :title="messages.minutes" 
                            :index="1"
                            :options="options" 
                            :element="element" 
                            @changed="changed"><s-common>`,

    components: {
        "s-common": common
    },

    props: ["element", "messages"],

    mounted() {
        this.prepareTextOptions();
        this.prepareNumberOptions();
    },

    data() {
        return {
            options: []
        }
    },

    methods: {

        prepareTextOptions() {
            minutes.forEach(item => {
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