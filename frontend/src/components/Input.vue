<template>

    <label class="s-form-label">
        {{ label }}
        <a :href="'/doc/' + info" v-if="info" target="_blank"><img src="../../public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info" /></a>
    </label>

    <input :placeholder="label" :aria-invalid="invalid ? 'true' : null" :value="value" @input="onInput" :type="inputType" :class="sClasses" :autocomplete="autocomplete == 'disabled' ? 'new-password' : null" :disabled="disabled"/>

    <span class="pure-form-message-inline">{{ message }}</span>

</template>

<script>

import ErrorHelperMixin from "@/mixins/ErrorHelperMixin.js";

export default {
    name: "Input",
    mixins: [ErrorHelperMixin],
    props: ["errors", "field", "label", "value", "type", "disabled", "info", "classes", "autocomplete"],
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
        sClasses() {
            return this.classes ? this.classes : ["pure-u-1"];
        }
    }
}

</script>

<style scoped></style>
