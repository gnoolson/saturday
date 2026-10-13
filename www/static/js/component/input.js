import error_helper from "/static/js/mixins/error-helper.js"

export default {
	template: `
              <label :class="{'disabled' : disabled}">
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info"/></a> 
              </label>
              <input :placeholder="label" :aria-invalid="invalid?'true':null" :value="value" @input="onInput" :type="inputType" :class="[inputSize]" :autocomplete="autocomplete=='off'?'new-password':null"/>
              <span class="pure-form-message-inline">{{message}}</span>
              `,
	props: ["errors", "field", "label", "value", "type", "disabled", "info", "size", "autocomplete"],
	mixins: [error_helper],
	methods: {
		onInput(event) {
			this.$emit("update:value", event.target.value);
			this.$emit("changed");
		}
	},
	computed: {
		inputType() {
			if (!this.type)
				return "text";
			return this.type;
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
