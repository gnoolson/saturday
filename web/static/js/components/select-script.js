import error_message from "/static/js/mixins/error-message.js"

export default {

  template: `<label>
                <span class="row">
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-square-fill.svg" /></a>
                  <a :href="'/script/' + value + '/edit'"  :class="{'disabled' : goToSelectedScriptDisabled}"  target="_blank"><img src="/static/img/pencil.svg" /></a>
                </span>
                <select :placeholder="label" :aria-invalid="invalid?'true':null" :value="value" @change="onInput" :disabled="disabled">
                    <option disabled :value="emptyId">-</option>
                    <option v-for="script in availableScripts" :value="script.id">
                        {{script.name}}
                    </option>
                </select>
                <small>
                    {{message}}
                </small>
            </label>
              `,

  props: ["errors", "field", "label", "value", "scripts", "selectedProject", "emptyId", "info"],

  mixins: [error_message],

  methods: {

    onInput(event) {
      this.$emit("update:value", event.target.value);
      this.$emit("changed");
    }

  },

  computed: {

    availableScripts() {
      return this.scripts.filter(script => {
        return script.projectId === this.selectedProject;
      });
    },

    disabled() {
      return this.selectedProject === this.emptyId;
    },

    goToSelectedScriptDisabled(){
        return this.value === this.emptyId;
    }

  }

};
