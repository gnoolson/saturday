import common from "/static/js/components/cron/common.js"

const hours = [
    {
        label: "every_hour",
        expression: "*"
    },
    {
        label: "every_even_hour",
        expression: "*/2"
    },
    {
        label: "every_odd_hour",
        expression: "1-23/2"
    },
    {
        label: "every_3_hours",
        expression: "*/3"
    },
    {
        label: "every_4_hours",
        expression: "*/4"
    },
    {
        label: "every_6_hours",
        expression: "*/6"
    }
];

export default {

    template: `<s-common    :messages="messages" 
                            :title="messages.hours" 
                            :index="2" 
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
            hours.forEach(item => {
                let newItem = {
                    label: this.messages[item.label],
                    expression: item.expression
                };
                this.options.push(newItem);
            });
        },

        prepareNumberOptions() {
            for (let i = 0; i < 24; i++) {
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