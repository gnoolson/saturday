<template>
    <div class="l-editor-container">
        <select class="l-select-font" v-model="cmFont" @change="cmFontChanged">
            <option v-for="size in fontSizes" :value="size" :key="size">
                {{ size }}
            </option>
        </select>
        <a :href="'/doc/' + info" v-if="info" target="_blank" class="l-editor-icon"> <img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info" /></a>
        <textarea ref="editorTextarea"></textarea>
    </div>
</template>

<script>
import { markRaw } from "vue";

const fontSizes = [6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24];

export default {
    props: ["code", "mode", "info"],
    data() {
        return {
            codeMirrorInstance: null,
            fontSizes,
            cmFont: loadCMFontFromStorage()
        }
    },
    mounted() {
        this.initHtmlCodeMirror();
        this.applyCMFont();
    },
    methods: {
        cmFontChanged() {
            this.applyCMFont();
            saveCMFontInStorage(this.cmFont);
        },
        applyCMFont() {
            if (!this.codeMirrorInstance)
                return;

            const wrapper = this.codeMirrorInstance.getWrapperElement();
            wrapper.style.fontSize = this.cmFont + "px";
            wrapper.style.lineHeight = Math.round(this.cmFont * 1.5) + "px"
            this.codeMirrorInstance.refresh();
        },
        initHtmlCodeMirror() {
            const textarea = this.$refs.editorTextarea;
            if (!textarea) {
                console.error("CodeMirror: Textarea ref not found");
                return;
            }

            const cm = CodeMirror.fromTextArea(textarea, {
                lineNumbers: true,
                autoRefresh: true,
                mode: this.mode,
                tabSize: 4,
                indentUnit: 4,
                indentWithTabs: false,
                extraKeys: {
                    "Shift-Tab": "indentLess",
                    "Tab": function (cm) {
                        if (cm.somethingSelected()) {
                            cm.indentSelection("add");
                        } else {
                            cm.replaceSelection(" ".repeat(cm.getOption("indentUnit")), "end");
                        }
                    }
                }
            });

            this.codeMirrorInstance = markRaw(cm);

            if (this.code) {
                this.codeMirrorInstance.setValue(this.code);
            }

            this.codeMirrorInstance.getDoc().on("change", (instance, changeObj) => {
                this.$emit("changed", instance.getValue());
            });

            this.codeMirrorInstance.setSize("100%", "100%");
        }
    },
    watch: {
        code(code) {
            if (!this.codeMirrorInstance)
                return;

            const oldCode = this.codeMirrorInstance.getValue();
            if (oldCode === code)
                return;

            this.codeMirrorInstance.setValue(code);
        }
    }
}

function loadCMFontFromStorage() {
    const font = localStorage.getItem("cm");
    if (font) {
        return Number(font);
    } else {
        return 14;
    }
}

function saveCMFontInStorage(font) {
    localStorage.setItem("cm", font);
}

</script>

<style>
.CodeMirror {
    font-family: 'DroidSansMono';
}

.l-editor-container {
    min-height: 0;
    position: relative;
    height: 100%;
}

.l-editor-icon {
    position: absolute;
    top: 0.5rem;
    right: 1rem;
    z-index: 10;
}

.l-select-font {
    position: absolute;
    top: 0.2rem;
    right: 3rem;
    z-index: 10;
}
</style>
