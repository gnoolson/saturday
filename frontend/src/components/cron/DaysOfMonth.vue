<template>
    <s-common :messages="messages" :title="messages.days_of_the_month" :index="3" :options="options" :element="element" @changed="changed"></s-common>
</template>

<script>
import Common from "@/components/cron/Common.vue"

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
    components: {
        "s-common": Common
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
        }
    }
};

</script>
