<template>

	<label v-if="show">
		Id
	</label>
	<input :value="id" type="text" readonly class="pure-input-1 mb-3" v-if="show" />
	
</template>

<script>

export default {
	props: ["id", "emptyId"],
	computed: {
		show() {
			return this.id !== this.emptyId;
		}
	}
}

</script>