import error_message from "/static/js/mixins/error-message.js"

export default {

  template: `<label :class="{'disabled' : disabled}">
                <span>
                  {{label}}
                  <a :href="'/docs/' + info" v-if="info" target="_blank"><img src="/static/img/info-square-fill.svg" /></a> 
                </span>
                <input :placeholder="label" :aria-invalid="invalid?'true':null" :value="value" @input="onInput" 
                  :type="inputType" />
                <small>
                  {{message}}
                </small>
            </label>
              `,

  props: ["errors", "field", "label", "value", "type", "disabled", "info"],

  mixins: [error_message],

  methods: {

    onInput(event) {
      this.$emit("update:value", event.target.value);
      this.$emit("changed");
    }

  },

  computed : {

    inputType (){
      if(!this.type)
          return "text";
      return this.type;
    }
    
  }

};
