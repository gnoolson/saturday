<template>
    <div :class="{ 's-error-message-box': box }" v-if="invalid">
        <div class="s-error-message">{{ message }}</div>
    </div>
</template>

<script>
import ErrorHelperMixin from "@/mixins/ErrorHelperMixin.js";

export default {
    props: {
        errors: {
            type: Array
        },
        field: {
            type: String
        },
        box: {
            type: Boolean,
            default: false
        }
    },
    mixins: [ErrorHelperMixin]
};
</script>
