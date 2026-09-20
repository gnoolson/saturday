import common from "/static/js/components/cron/common.js"

const months = [
    {
        label: "every_month",
        expression: "*"
    },
    {
        label: "every_even_month",
        expression: "*/2"
    },
    {
        label: "every_odd_month",
        expression: "1-11/2"
    },
    {
        label: "every_4_months",
        expression: "*/4"
    },
    {
        label: "every_6_months",
        expression: "*/6"
    },
    {
        label: "january",
        expression: "1"
    },
    {
        label: "february",
        expression: "2"
    },
    {
        label: "march",
        expression: "3"
    },
    {
        label: "april",
        expression: "4"
    },
    {
        label: "may",
        expression: "5"
    },
    {
        label: "june",
        expression: "6"
    },
    {
        label: "july",
        expression: "7"
    },
    {
        label: "august",
        expression: "8"
    },
    {
        label: "september",
        expression: "9"
    },
    {
        label: "october",
        expression: "10"
    },
    {
        label: "november",
        expression: "11"
    },
    {
        label: "december",
        expression: "12"
    }
];

export default {

    template: `<s-common    :messages="messages" 
                            :title="messages.months" 
                            :index="4" 
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
    },

    methods: {

        prepareTextOptions() {
            months.forEach(item => {
                let newItem = {
                    label: this.messages[item.label],
                    expression: item.expression
                };
                this.options.push(newItem);
            });
        },

        changed(value, index) {
            this.$emit("changed", value, index);
        },

    },


};