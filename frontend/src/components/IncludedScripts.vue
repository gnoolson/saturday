<template>

    <span @click="showDialog()" th:text="#{script.dev.button.include_scripts}" class="s-clickable-mark secondary">
        {{ messages.message_1 }}
    </span>

    <dialog ref="dialog">
        <div class="s-dialog-header">
            <h3 class="s-dialog-title">
                {{ messages.message_1 }}
                <a href="/doc/script#included-scripts" target="_blank">
                    <img src="/public/img/info-circle-svgrepo-com.svg" class="s-svg-icon" title="info" />
                </a>
            </h3>
            <button type="button" class="s-close-btn" aria-label="close" @click="closeDialog" autofocus>
                &times;
            </button>
        </div>
        <div class="s-dialog-body">
            <div class="pure-g" style="min-height: 250px">
                <div class="s-block">
                    <h3>
                        {{ messages.message_2 }}
                    </h3>
                    <div v-for="script in availableScripts" class="s-row">
                        <span class="s-max-row-side">
                            <a :href="'/script/' + script.id + '/edit'" target="_blank">{{ script.name }}</a>
                        </span>
                        <span class="s-min-row-side pr-2">
                            <img src="/public/img/plus-circle-svgrepo-com.svg" class="s-pointer s-svg-icon" @click="select(script)" title="add">
                        </span>
                    </div>
                    <div class="s-align-center" v-if="!availableScripts.length">
                        {{ messages.message_4 }}
                    </div>
                </div>
                <div class="s-block">
                    <h3>
                        {{ messages.message_3 }}
                    </h3>
                    <div v-for="script in selectedScripts" class="s-row">
                        <span class="s-max-row-side">
                            <a :href="'/script/' + script.id + '/edit'" target="_blank">{{ script.name }}</a>
                        </span>
                        <span class="s-min-row-side">
                            <img src="/public/img/arrow-down-circle-svgrepo-com.svg" class="s-pointer s-svg-icon pr-1" @click="moveDown(script)" title="move down">
                            <img src="/public/img/arrow-up-circle-svgrepo-com.svg" class="s-pointer s-svg-icon pr-1" @click="moveUp(script)" title="move up">
                            <img src="/public/img/minus-circle-svgrepo-com.svg" class="s-pointer s-svg-icon" @click="remove(script)" title="remove">
                        </span>
                    </div>
                    <div class="s-align-center" v-if="!selectedScripts.length">
                        {{ messages.message_4 }}
                    </div>
                </div>
            </div>
        </div>
    </dialog>

</template>

<script>

import ScriptRestApi from '@/rest_api/ScriptRestApi';

export default {
    name: "IncludedScripts",
    props: ["errors", "script", "messages"],
    mounted() {
        this.loadScripts();
    },
    data() {
        return {
            copyIncludedScripts: [],
            allScripts: []
        }
    },
    methods: {
        showDialog() {
            this.$refs.dialog.showModal();
        },
        closeDialog() {
            this.$refs.dialog.close();
        },
        loadScripts() {
            ScriptRestApi.getAll((data) => {
                this.allScripts = data;
            });
        },
        moveUp(script) {
            moveItem(this.copyIncludedScripts, script.id, "up");
            this.$emit("changed", this.copyIncludedScripts);
        },
        moveDown(script) {
            moveItem(this.copyIncludedScripts, script.id, "down");
            this.$emit("changed", this.copyIncludedScripts);
        },
        select(script) {
            this.copyIncludedScripts.push(script.id);
            this.$emit("changed", this.copyIncludedScripts);
        },
        remove(script) {
            const newArray = this.copyIncludedScripts.filter((id) => { return id !== script.id; });
            this.copyIncludedScripts = newArray;
            this.$emit("changed", this.copyIncludedScripts);
        },
        getLastCharacters(number, string) {
            return string.substring(string.length - number, string.length);
        },
    },
    computed: {
        availableScripts() {
            let result = [];
            this.allScripts.forEach(script => {
                if (script.projectId != this.script.projectId) {
                    return;
                }

                let foundFlag = false;
                this.copyIncludedScripts.forEach(id => {
                    if (script.id === id) {
                        foundFlag = true;
                    }
                });

                if (script.id == this.script.id) {
                    foundFlag = true;
                }

                if (!foundFlag) {
                    result.push(script);
                }
            });

            result.sort((a, b) => a.name.localeCompare(b.name));
            return result;
        },
        selectedScripts() {
            let result = [];
            this.copyIncludedScripts.forEach(id => {
                // let ghost = true;

                this.allScripts.forEach(script => {
                    if (id === script.id) {
                        result.push(script);
                        // ghost = false;
                    }
                });

                // if (ghost) {
                //     result.push({ id: id, name: this.getLastCharacters(4, id) + " - not found" });
                // }
            });
            return result;
        }
    },

    watch: {
        "script.includedScripts": {
            handler(value, oldValue) {
                if (value === this.copyIncludedScripts)
                    return;

                this.copyIncludedScripts = [...value];
            },
            deep: true
        }
    }
}

function moveItem(array, id, direction) {
    const index = array.findIndex(_id => _id === id);
    if (index === -1)
        return array;

    if (direction === "up" && index > 0) {
        [array[index - 1], array[index]] = [array[index], array[index - 1]];
    }

    if (direction === "down" && index < array.length - 1) {
        [array[index], array[index + 1]] = [array[index + 1], array[index]];
    }
}

</script>

<style>
.s-block {
    flex: 1;
}

.s-block+.s-block {
    border-left: 1px solid #dcd7d7;
    padding-left: 2rem;
}

.s-row {
    width: 100%;
    overflow: hidden;
}

.s-row .s-min-row-side {
    float: right;
}

.s-row .s-max-row-side {
    overflow: hidden;
}
</style>
