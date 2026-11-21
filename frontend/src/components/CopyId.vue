<template>

    <span class="id" @click="copyId()">
        {{ uuid }}
        <!-- <img src="../../public/img/id-id-svgrepo-com.svg" class="s-svg-icon s-pointer" @click="copyId()"/> -->
    </span>

</template>

<script>

export default {
    props: ["id"],
    methods: {
        copyId() {
            try {
                navigator.clipboard.writeText(this.id);
                alert('Id copied to clipboard!');
            } catch (err) {
                console.error('Failed to copy: ', err);
            }
        }
    },
    computed: {
        uuid() {
            return "id:" + this.id.slice(-4)
        }
    }
}

</script>


<style scoped>
.id {
    cursor: pointer;
    color: #201a12;
    border: 1px solid #bcbaba;
    margin-left: 10px;
    padding: 1px;
    font-size: 11px;
}
</style>