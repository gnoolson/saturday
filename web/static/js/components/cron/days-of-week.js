import common from "/static/js/components/cron/common.js"

const daysOfWeek = [
    {
        label: "every_weekday",
        expression: "*"
    },
    {
        label: "weekend",
        expression: "SAT,SUN"
    },
    {
        label: "weekdays",
        expression: "MON-FRI"
    },
    {
        label: "sunday",
        expression: "SUN"
    },
    {
        label: "monday",
        expression: "MON"
    },
    {
        label: "tuesday",
        expression: "TUE"
    },
    {
        label: "wednesday",
        expression: "WED"
    },
    {
        label: "thursday",
        expression: "THU"
    },
    {
        label: "friday",
        expression: "FRI"
    },
    {
        label: "saturday",
        expression: "SAT"
    }
];


export default {

    template: `<s-common    :messages="messages" 
                            :title="messages.days_of_the_week" 
                            :index="5" 
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
            daysOfWeek.forEach(item => {
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