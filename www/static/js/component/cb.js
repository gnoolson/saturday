
export default {
	template: `
				<label for="aligned-cb" class="pure-checkbox">
                	<input id="aligned-cb" type="checkbox" v-model="value" @input="onInput" /> 
					{{label}}
                  	<a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info"/></a> 
            	</label>
              `,
	props: ["label", "value", "disabled", "info", "size"],
	methods: {
		onInput(event) {
			this.$emit("update:value", event.target.checked);
			this.$emit("changed");
		}
	}
};
