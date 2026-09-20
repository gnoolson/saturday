import error_message from "/static/js/mixins/error-message.js"

export default {

    template: `
                <div class="error" v-if="invalid">{{message}}</div>
                <div class="editor-container">
                    <a :href="'/docs/' + info" v-if="info" target="_blank" class="editor-icon"><img src="/static/img/info-square-fill.svg" /></a> 
                    <textarea :id="id"></textarea> 
                </div>
                `,

    props: ["errors", "field", "id", "code", "mode", "info"],

    mixins: [error_message],

    data() {
        return {
            codeMirrorInstance: null,
        }
    },

    mounted() {
        this.initHtmlCodeMirror();
    },

    methods: {

        initHtmlCodeMirror() {
            this.codeMirrorInstance = Vue.markRaw(CodeMirror.fromTextArea(document.getElementById(this.id), {
                lineNumbers: true,
                autoRefresh: true,
                mode: this.mode,
                extraKeys: {
                    "Shift-Tab": "indentLess"
                }
            }));

            this.codeMirrorInstance.getDoc().on("change", (instance, changeObj) => {
                this.$emit("changed", instance.getValue());
            });

            this.codeMirrorInstance.setSize("100%", "100%");
        }

    },

    watch: {

        code(code) {
            const oldCode = this.codeMirrorInstance.getValue();
            if (oldCode === code)
                return;
            this.codeMirrorInstance.setValue(code);
        },

    }

};
