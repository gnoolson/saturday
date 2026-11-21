<template>

    <label>
        {{ label }}
        <a :href="'/doc/' + info" v-if="info" target="_blank"><img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info" /></a>
    </label>

    <select :placeholder="label" :aria-invalid="invalid ? 'true' : null" :value="value" @change="onInput" :disabled="disabled" :class="[sClasses]">
        <option disabled :value="emptyId">-</option>
        <option v-for="project in projects" :value="project.id">
            {{ project.name }}
        </option>
    </select>

    <span class="pure-form-message-inline">{{ message }}</span>

</template>

<script>
import ErrorHelperMixin from "@/mixins/ErrorHelperMixin.js";

export default {
    props: ["errors", "field", "label", "value", "projects", "emptyId", "entityId", "info", "classes"],
    mixins: [ErrorHelperMixin],
    methods: {
        onInput(event) {
            this.$emit("update:value", event.target.value);
            this.$emit("changed");
        }
    },
    computed: {
        disabled() {
            return this.entityId !== this.emptyId;
        },
		sClasses() {
			return this.classes ? this.classes : ["pure-u-1"];
		}
    }
};

</script>

<style scoped></style>
