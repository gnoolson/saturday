
export default {

  template: `
                <label :class="{'disabled' : disabled}" class="form-row">
                    <span> 
                        {{label}}
                        <a :href="'/docs/' + info" v-if="info" target="_blank">
                            <img src="/static/img/info-square-fill.svg" />
                        </a> 
                    </span>
                    <input v-model="value"
                        type="checkbox" 
                        role="switch" @input="onInput"/>
                </label>
              `,

  props: ["label", "value", "disabled", "info"],

  methods: {

    onInput(event) {
      this.$emit("update:value", event.target.checked);
      this.$emit("changed");
    }

  }

};
