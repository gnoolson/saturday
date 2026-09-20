export default {

  template: `<label v-if="show">
                <span>
                  Id <img src="/static/img/copy.svg" class="pointer" @click="copyId()" title="copy">
                </span>
                <input :value="id" :type="text" readonly />
            </label>
              `,

  props: ["id", "emptyId"],

  mixins: [],

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

  computed : {

    show (){
        return this.id !== this.emptyId;
    }

  }

};
