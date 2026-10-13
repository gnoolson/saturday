export default {
    template: `
                <div class="editor-container">
                    <a :href="'/docs/' + info" v-if="info" target="_blank" class="editor-icon"><img src="/static/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info"/></a> 
                    <textarea :id="id"></textarea> 
                </div>
                `,
    props: ["id", "code", "mode", "info"],
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
