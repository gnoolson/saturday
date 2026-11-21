<template>

	<label>
		<span>
			{{ label }}
			<a :href="'/doc/' + info" v-if="info" target="_blank"><img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" /></a>
			<span class="mr-1"></span>
			<a :href="'/script/' + value + '/edit'" :class="{ 's-disabled': goToSelectedScriptDisabled }" target="_blank"><img src="/public/img/magnet-svgrepo-com.svg" class="s-svg-icon" /></a>
		</span>
	</label>

	<select :placeholder="label" :aria-invalid="invalid ? 'true' : null" :value="value" @change="onInput" :disabled="lock" :class="[sClasses]">
		<option disabled :value="emptyId">-</option>
		<option v-for="script in availableScripts" :value="script.id">
			{{ script.name }}
		</option>
	</select>

	<span class="pure-form-message-inline">
		{{ message }}
	</span>

</template>

<script>

import ErrorHelperMixin from "@/mixins/ErrorHelperMixin.js";

export default {
	props: ["errors", "field", "label", "value", "scripts", "selectedProject", "emptyId", "info", "classes", "disabled"],
	mixins: [ErrorHelperMixin],
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
		lock() {
			return this.selectedProject === this.emptyId || this.disabled;
		},
		goToSelectedScriptDisabled() {
			return this.value === this.emptyId;
		},
		sClasses() {
			return this.classes ? this.classes : ["pure-u-1"];
		}
	}
}

</script>


<style></style>
