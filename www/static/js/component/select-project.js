import error_helper from "/static/js/mixins/error-helper.js"

export default {
    template: `<label>
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info"/></a> 
                </label>
                <select :placeholder="label" :aria-invalid="invalid?'true':null" :value="value" @change="onInput" :disabled="disabled" :class="[inputSize]">
                    <option disabled :value="emptyId">-</option>
                    <option v-for="project in projects" :value="project.id">
                        {{project.name}}
                    </option>
                </select>
                <span class="pure-form-message-inline">{{message}}</span>
              `,
    props: ["errors", "field", "label", "value", "projects", "emptyId", "entityId", "info", "size"],
    mixins: [error_helper],
    methods: {
        onInput(event) {
            this.$emit("update:value", event.target.value);
            this.$emit("changed");
        }
    },
    computed: {
        disabled() {
            return this.entityId !== this.emptyId;
        },
        inputSize() {
            if (this.size == 1) {
                return "pure-input-1";
            } else if (this.size == 2) {
                return "pure-input-1-2";
            } else {
                return "pure-input-1-4";
            }
        }
    }
};
