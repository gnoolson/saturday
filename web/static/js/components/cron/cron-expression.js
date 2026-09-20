import error_message from "/static/js/mixins/error-message.js"

import seconds from "/static/js/components/cron/seconds.js"
import minutes from "/static/js/components/cron/minutes.js"
import hours from "/static/js/components/cron/hours.js"
import days_of_month from "/static/js/components/cron/days-of-month.js"
import days_of_week from "/static/js/components/cron/days-of-week.js"
import months from "/static/js/components/cron/months.js"


export default {

    template: `
                <small>
                  {{message}}
                </small>

                <div style="background-color: rgb(232, 236, 242); padding: 0.5rem">
                    <div class="mb-1">
                        {{messages.cron_expression}}
                        <a href="/docs/schedule#cron" target="_blank"><img src="/static/img/info-square-fill.svg" /></a>
                    </div>
                    <input :value="expressionCopy" @input="expressionChanged" type="text" />
                    <div class="grid">
                      <s-seconds :element="expressionLikeArray[0]" @changed="changed" :messages="messages"></s-seconds>
                      <s-minutes :element="expressionLikeArray[1]" @changed="changed" :messages="messages"></s-minutes>
                    </div>
                    <div class="grid">
                      <s-hours :element="expressionLikeArray[2]" @changed="changed" :messages="messages"></s-hours>
                      <s-days-of-month :element="expressionLikeArray[3]" @changed="changed" :messages="messages"></s-days-of-month>
                    </div>
                    <div class="grid">  
                      <s-months :element="expressionLikeArray[4]" @changed="changed" :messages="messages"></s-months>
                      <s-days-of-week :element="expressionLikeArray[5]" @changed="changed" :messages="messages"></s-days-of-week> 
                    </div>
                </div>
              `,

    components: {
        "s-seconds": seconds,
        "s-minutes":minutes,
        "s-hours": hours,
        "s-days-of-month": days_of_month,
        "s-months": months,
        "s-days-of-week": days_of_week,
    },

    props: ["errors", "expression", "messages"],

    mixins: [error_message],

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

};

function expressionToArrayOfElements(expression) {
    return expression.split(" ");
}