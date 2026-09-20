import common from "/static/js/components/cron/common.js"

const daysOfMonth = [
    {
        label: "every_day_of_month",
        expression: "*"
    },
    {
        label: "even_day_of_month",
        expression: "*/2"
    },
    {
        label: "odd_day_of_month",
        expression: "1-31/2"
    }
];

export default {

    template: `<s-common    :messages="messages" 
                            :title="messages.days_of_the_month" 
                            :index="3" 
                            :options="options"
                            :element="element" @changed="changed"><s-common>`,

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
            daysOfMonth.forEach(item => {
                let newItem = {
                    label: this.messages[item.label],
                    expression: item.expression
                };
                this.options.push(newItem);
            });
        },

        prepareNumberOptions() {
            for (let i = 1; i <= 31; i++) {
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