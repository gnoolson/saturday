<template>
    <s-common :messages="messages" :title="messages.days_of_the_week" :index="5" :options="options" :element="element" @changed="changed"></s-common>
</template>

<script>

import Common from "@/components/cron/Common.vue"

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
    components: {
        "s-common": Common
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
        }
    }
}

</script>
