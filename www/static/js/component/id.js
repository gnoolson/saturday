export default {

	template: ` <label  v-if="show">
                    Id
                </label>
                <input :value="id" type="text" readonly class="pure-input-1 mb-3"  v-if="show"/>
              `,
	props: ["id", "emptyId"],
	mixins: [],
	methods: {
		// copyId() {
		// 	try {
		// 		navigator.clipboard.writeText(this.id);
		// 		alert('Id copied to clipboard!');
		// 	} catch (err) {
		// 		console.error('Failed to copy: ', err);
		// 	}
		// }
	},
	computed: {
		show() {
			return this.id !== this.emptyId;
		}
	}
};
