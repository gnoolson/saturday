<template>
    <hr class="s-hl">

    <div class="s-footer">
        <span><a href="?lang=en">EN</a> | <a href="?lang=uk">УКР</a></span>
        <span class="ml-2">ver: {{version}}</span>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                version: VERSION
            }
        }
    }
</script>

<style scoped>
.s-footer {
    font-size: 11px;
    color: #666;
}
</style>