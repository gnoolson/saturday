import error_helper from "/static/js/mixins/error-helper.js"

export default {
	template: `<label :class="{'disabled' : disabled}">
                <span>
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info"/></a> 
                </span>
              </label>
              <textarea :placeholder="label" :aria-invalid="invalid?'true':null" @input="onInput" :rows="rows" v-model="value" :class="[textareaSize]"></textarea>
              <span class="pure-form-message-inline">
                  {{message}}
              </span>
            `,
	props: ["errors", "field", "label", "value", "rows", "info", "size"],
	mixins: [error_helper],
	methods: {
		onInput(event) {
			this.$emit("update:value", event.target.value);
			this.$emit("changed", null);
		}
	},
	computed: {
		textareaSize() {
			if (this.size == 1) {
				return "pure-input-1";
			} else if (this.size == 2) {
				return "pure-input-1-2";
			} else {
				return "pure-input-1-4";
			}
		}
	}
}
