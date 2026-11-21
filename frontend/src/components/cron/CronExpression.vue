<template>

    <div class="s-cron">
        <div class="s-error-message-box" v-if="invalid">
            <div class="s-error-message">{{ message }}</div>
        </div>
        <div class="s-align-right">
            <a href="/doc/schedule#cron" target="_blank"><img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" /></a>
        </div>
        <form class="pure-form pure-form-aligned">
            <fieldset>
                <div class="pure-control-group">
                    <label>
                        {{ messages.cron_expression }}
                    </label>
                    <input :value="expressionCopy" @input="expressionChanged" type="text" class="pure-u-1 pure-u-sm-1-2" />
                </div>
                <s-seconds :element="expressionLikeArray[0]" @changed="changed" :messages="messages"></s-seconds>
                <s-minutes :element="expressionLikeArray[1]" @changed="changed" :messages="messages"></s-minutes>
                <s-hours :element="expressionLikeArray[2]" @changed="changed" :messages="messages"></s-hours>
                <s-days-of-month :element="expressionLikeArray[3]" @changed="changed" :messages="messages"></s-days-of-month>
                <s-months :element="expressionLikeArray[4]" @changed="changed" :messages="messages"></s-months>
                <s-days-of-week :element="expressionLikeArray[5]" @changed="changed" :messages="messages"></s-days-of-week>
            </fieldset>
        </form>
    </div>

</template>

<script>

import ErrorHelperMixin from "@/mixins/ErrorHelperMixin.js";
import Seconds from "@/components/cron/Seconds.vue"
import Minutes from "@/components/cron/Minutes.vue"
import Hours from "@/components/cron/Hours.vue"
import DaysOfMonth from "@/components/cron/DaysOfMonth.vue"
import DaysOfWeek from "@/components/cron/DaysOfWeek.vue"
import Months from "@/components/cron/Months.vue"


export default {
    components: {
        "s-seconds": Seconds,
        "s-minutes": Minutes,
        "s-hours": Hours,
        "s-days-of-month": DaysOfMonth,
        "s-months": Months,
        "s-days-of-week": DaysOfWeek,
    },
    props: ["errors", "expression", "messages", "field"],
    mixins: [ErrorHelperMixin],
    data() {
        return {
            expressionLikeArray: [],
            expressionCopy: ""
        }
    },
    methods: {
        changed(value, index) {
            this.expressionLikeArray[index] = value;

            let result = "";
            this.expressionLikeArray.forEach((element, index) => {
                result += element;
                if (index < this.expressionLikeArray.length - 1) {
                    result += " ";
                }
            });
            this.expressionCopy = result;

            this.$emit("changed", this.expressionCopy);
        },
        expressionChanged(event) {
            this.expressionCopy = event.target.value;
            this.expressionLikeArray = expressionToArrayOfElements(this.expressionCopy);
            this.$emit("changed", this.expressionCopy);
        }
    },
    watch: {
        expression(value) {
            if (this.expressionCopy == value)
                return;

            this.expressionCopy = value;
            this.expressionLikeArray = expressionToArrayOfElements(this.expressionCopy);
        },
    }
}

function expressionToArrayOfElements(expression) {
    return expression.split(" ");
}

</script>

<style scoped>

.s-cron {
    background-color: #f9f1e6;
    padding: 0.5rem
}

</style>
