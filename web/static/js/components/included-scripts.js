
export default {

    template: `

        <span @click="showIncludeScriptsDialog()" th:text="#{script.dev.button.include_scripts}" class="clickable-mark">
            {{messages.title}}
        </span>

        <dialog :open="show?'true':null">
            <article>
                <header>
                    <button aria-label="Close" rel="prev" @click="hideIncludeScriptsDialog()"></button>
                    <span>
                        {{messages.title}}
                        <a href="/docs/script#included-scripts" target="_blank"><img src="/static/img/info-square-fill.svg" /></a>
                    </span>
                </header>
                <div class="grid" style="min-height: 250px">
                    <div class="block">
                            <h5>
                                {{messages.availableScripts}}
                            </h5>
                            <div v-for="script in availableScripts" class="row">
                                <div class="title">
                                    <a :href="'/script/' + script.id + '/edit'" target="_blank">{{script.name}}</a> 
                                </div>
                                <img src="/static/img/plus-square-fill.svg" class="pointer" @click="select(script)">
                            </div>
                            <div class="center" v-if="!availableScripts.length">
                                {{messages.empty}}
                            </div>
                    </div>
                    <div  class="block">
                            <h5>
                                {{messages.includedScripts}}
                            </h5>
                            <div v-for="script in selectedScripts" class="row">
                                <div class="title">
                                    <a :href="'/script/' + script.id + '/edit'" target="_blank">{{script.name}}</a> 
                                </div>
                                <img src="/static/img/arrow-down-square-fill.svg" class="pointer" @click="moveDown(script)">
                                <img src="/static/img/arrow-up-square-fill.svg" class="pointer" @click="moveUp(script)">
                                <img src="/static/img/x-square-fill.svg" class="pointer" @click="remove(script)">
                            </div>
                            <div class="center" v-if="!selectedScripts.length">
                                {{messages.empty}}
                            </div>
                       
                    </div>
                </div>
            </article>
        </dialog>
    `,

    props: ["errors", "script", "messages"],

    mounted() {
        this.loadScripts();
    },

    data() {
        return {
            copyIncludedScripts: [],
            allScripts: [],
            show: false
        }
    },

    methods: {

        showIncludeScriptsDialog() {
            this.show = true;
        },

        hideIncludeScriptsDialog() {
            this.show = false;
        },

        loadScripts() {
            axios.get("/api/script/all").then((response) => {
                this.allScripts = response.data;
            }, (error) => {
                console.error(error);
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
            const newArray = this.copyIncludedScripts.filter(id => id !== script.id);
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

                if (script.projectId != this.script.projectId)
                    return;

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
            handler (value, oldValue) {
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
    if (index === -1) return array;

    if (direction === "up" && index > 0) {
        [array[index - 1], array[index]] = [array[index], array[index - 1]];
    }

    if (direction === "down" && index < array.length - 1) {
        [array[index], array[index + 1]] = [array[index + 1], array[index]];
    }
}