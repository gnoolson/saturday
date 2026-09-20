import error_message from "/static/js/mixins/error-message.js"

export default {

  template: `<label>
                <span>
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-square-fill.svg" /></a> 
                </span>
                <textarea :placeholder="label" :aria-invalid="invalid?'true':null" @input="onInput" 
                  :rows="rows" v-model="value"></textarea>
                <small>
                    {{message}}
                </small>
            </label>
            `,

  props: ["errors", "field", "label", "value", "rows", "info"],

  mixins: [error_message],

  methods: {

    onInput(event) {
      this.$emit("update:value", event.target.value);
      this.$emit("changed", null);
    }

  }

};
