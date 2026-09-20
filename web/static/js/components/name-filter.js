
export default {

    template: `
                <label>
                    <span>
                        <img src="/static/img/filter-square-fill.svg" />
                        {{label}}
                    </span>
                    <input type="text" v-model="query" @input="process()" :placeholder="placeholder" :aria-invalid="invalid?'true':null">
                </label>
                `,

    props: ["entities", "type", "ready", "label", "placeholder"],

    data() {
        return {
            query: "",
            invalid: false
        }
    },

    methods: {

        readSavedQuery() {
            let savedQuery = localStorage.getItem(this.type + ":query");
            if (savedQuery) {
                this.query = savedQuery;
            }
        },

        saveQuery() {
            localStorage.setItem(this.type + ":query", this.query);
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
        }

    },

    computed: {

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
