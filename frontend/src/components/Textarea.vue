<template>

	<label>
		<span>
			{{ label }}
			<a :href="'/doc/' + info" v-if="info" target="_blank"><img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info" /></a>
		</span>
	</label>

	<textarea :placeholder="label" :aria-invalid="invalid ? 'true' : null" @input="onInput" :rows="rows" :value="value" :class="[sClasses]"></textarea>

	<span class="pure-form-message-inline">
		{{ message }}
	</span>

</template>

<script>
import ErrorHelperMixin from "@/mixins/ErrorHelperMixin.js";

export default {
	name: "Textarea",
	props: ["errors", "field", "label", "value", "rows", "info", "classes"],
	mixins: [ErrorHelperMixin],
	methods: {
		onInput(event) {
			this.$emit("update:value", event.target.value);
			this.$emit("changed", null);
		}
	},
	computed: {
		sClasses() {
			return this.classes ? this.classes : ["pure-u-1"];
		}
	}
}
</script>
