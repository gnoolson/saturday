<template>

	<!-- <div class="pure-checkbox"> -->
		<label>
			{{ label }}
			<a :href="'/doc/' + info" v-if="info" target="_blank"><img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon mr-3" title="info" /></a>
		</label>
		<input type="checkbox" v-model="flag" @input="onInput" :disabled="disabled" />
	<!-- </div> -->

</template>

<script>
export default {
	props: ["label", "value", "disabled", "info"],
	methods: {
		onInput(event) {
			this.$emit("update:value", event.target.checked);
			this.$emit("changed");
		}
	},
	computed: {
		flag() {
			return this.value;
		}
	}
};
</script>
