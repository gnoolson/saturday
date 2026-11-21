<template>

	<label>
		{{ label }}
		<a :href="'/doc/' + info" v-if="info" target="_blank"><img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info" /></a>
		<span class="mr-1"></span>
		<a :href="'/script/' + value + '/edit'" :class="{ 's-disabled': goToSelectedScriptDisabled }" target="_blank"><img src="/public/img/magnet-svgrepo-com.svg" class="s-svg-icon" title="go to" /></a>
	</label>

	<select :placeholder="label" :aria-invalid="invalid || selectedScriptNotFound ? 'true' : null" :value="value" @change="onInput" :disabled="disabled" :class="[sClasses]">
		<option :value="emptyId">-</option>
		<option v-for="script in availableScripts" :value="script.id">
			{{ script.name }}
		</option>
	</select>

	<span class="pure-form-message-inline">{{ message }}</span>
	<span class="pure-form-message-inline" v-if="selectedScriptNotFound">{{ errorMessage }}</span>

</template>

<script>

import ErrorHelper from "@/mixins/ErrorHelperMixin.js"

export default {
	props: ["errors", "field", "label", "value", "scripts", "selectedProject", "emptyId", "ready", "errorMessage", "info", "classes"],
	mixins: [ErrorHelper],
	data: function () {
		return {
			selectedScriptNotFound: false
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
		goToSelectedScriptDisabled() {
			return this.value === this.emptyId;
		},
		sClasses() {
			return this.classes ? this.classes : ["pure-u-1"];
		}
	}
}


</script>
