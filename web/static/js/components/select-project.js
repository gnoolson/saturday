import error_message from "/static/js/mixins/error-message.js"

export default {

    template: `<label>
                <span>
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-square-fill.svg" /></a> 
                </span>
                <select :placeholder="label" :aria-invalid="invalid?'true':null" :value="value" @change="onInput" :disabled="disabled">
                    <option disabled :value="emptyId">-</option>
                    <option v-for="project in projects" :value="project.id">
                        {{project.name}}
                    </option>
                </select>
                <small>
                    {{message}}
                </small>
            </label>
              `,

    props: ["errors", "field", "label", "value", "projects", "emptyId", "entityId", "info"],

    mixins: [error_message],

    methods: {

        onInput(event) {
            this.$emit("update:value", event.target.value);
            this.$emit("changed");
        }

    },

    computed: {

        disabled() {
            return this.entityId !== this.emptyId;
        }

    }

};
