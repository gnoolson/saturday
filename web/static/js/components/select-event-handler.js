import error_message from "/static/js/mixins/error-message.js"

export default {

  template: `
              <label>
                <span class="row">
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-square-fill.svg" /></a>
                  <a :href="'/script/' + value + '/edit'"  :class="{'disabled' : goToSelectedScriptDisabled}"  target="_blank"><img src="/static/img/pencil.svg" /></a>
                </span>
                <select :placeholder="label" :aria-invalid="invalid || selectedScriptNotFound?'true':null" :value="value" @change="onInput" :disabled="disabled">
                    <option :value="emptyId">-</option>
                    <option v-for="script in availableScripts" :value="script.id">
                        {{script.name}}
                    </option>
                </select>
                <small v-if="message.length">
                    {{message}}
                </small>
                <small v-if="selectedScriptNotFound">
                    {{errorMessage}}
                </small>
              </label>
            `,

  props: ["errors", "field", "label", "value", "scripts", "selectedProject", "emptyId", "ready", "errorMessage", "info"],

  mixins: [error_message],

  data: function(){
    return {
       selectedScriptNotFound : false
    }
  },

  methods: {

    onInput(event) {
      this.$emit("update:value", event.target.value);
      this.$emit("changed");
    }

  },

  computed: {

    availableScripts() {
      if (!this.ready)
        return [];

      let filteredByProject = this.scripts.filter(script => {
        return script.projectId === this.selectedProject;
      });

      if (this.value === this.emptyId)
        return filteredByProject;

      let found = false
      filteredByProject.forEach(script => {
        if (script.id === this.value) {
          found = true;
        }
      });

      this.selectedScriptNotFound = !found;

      if (!found) {
        this.$emit("update:value", this.emptyId);
        this.$emit("changed");
      }

      return filteredByProject;
    },

    disabled() {
      return this.selectedProject === this.emptyId;
    },

    goToSelectedScriptDisabled(){
        return this.value === this.emptyId;
    }

  }

};
