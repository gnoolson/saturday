export default {
    template: `<form class="pure-form pure-form-aligned"> 
                    <div class="pure-control-group">
                        <div class="input-with-icon">
                           <img src="/static/img/funnel-sort-svgrepo-com.svg" class="s-svg-icon" title="filter"/>
                           <input type="text" v-model="query" @input="process()" :placeholder="placeholder" :aria-invalid="invalid?'true':null" class="s-finput">
                           <img src="/static/img/close-circle-svgrepo-com.svg" class="s-svg-icon pointer" v-if="query.length" @click="reset" title="clear"/>
                        </div>
                    </div>
               </form>
               `,
    props: ["entities", "page", "ready", "placeholder"],
    data() {
        return {
            query: "",
            invalid: false
        }
    },
    methods: {
        readSavedQuery() {
            let savedQuery = localStorage.getItem(this.page + ":query");
            if (savedQuery) {
                this.query = savedQuery;
            }
        },
        saveQuery() {
            localStorage.setItem(this.page + ":query", this.query);
        },
        process() {
            let result;
            if (this.query.length == 0) {
                result = this.entities;
            } else {
                result = this.entities.filter(item => {
                    let result = item.name.toLowerCase().indexOf(this.query.toLowerCase()) != -1;
                    return result
                });
            }

            this.saveQuery();
            this.invalid = !result.length && this.entities.length;
            this.$emit("result", result);
        },
        reset(){
                this.query = "";
                this.process();
        }
    },
    watch: {
        entities: {
            handler: function (newValue) {
                if (this.ready) {
                    this.readSavedQuery();
                    this.process();
                }
            },
            deep: true
        }
    }
};
