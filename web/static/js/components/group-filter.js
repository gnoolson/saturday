const ALL_GROUPS = "ALL_GROUPS";

export default {

    template: `
                <label>
                    <span>{{label}}</span>
                    <select v-model="selectedGroup">
                        <option :value="ALL_GROUPS">{{defaultValueLabel}}</option>
                        <option v-for="group in groups" :value="group">{{group}}</option>
                    </select>
                </label>
                `,

    props: ["entities", "type", "ready", "label", "defaultValueLabel"],

    data() {
        return {
            ALL_GROUPS: ALL_GROUPS,
            selectedGroup: ALL_GROUPS,
        }
    },

    methods: {

        readSavedGroup() {
            let selectedGroup = localStorage.getItem(this.type + ":group");
            if (this.isGroupAvailable(selectedGroup)) {
                this.selectedGroup = selectedGroup;
            } else {
                this.selectedGroup = this.ALL_GROUPS;
            }
        },

        isGroupAvailable(group) {
            let result = false;
            this.groups.forEach((availableGroup) => {
                if (availableGroup === group)
                    result = true;
            });
            return result;
        },

        saveSelectedGroup() {
            localStorage.setItem(this.type + ":group", this.selectedGroup);
        },

        process() {
            let result = this.entities.filter(item => {
                if (this.selectedGroup === ALL_GROUPS)
                    return true;
                else {
                    let group = getGroupFromName(item.name);
                    return this.selectedGroup === group;
                }
            });

            this.$emit("result", result);
        }

    },

    computed: {

        groups() {
            let set = new Set();
            this.entities.forEach(entity => {
                let group = getGroupFromName(entity.name);
                if (group.length)
                    set.add(group);
            });

            return set;
        }

    },

    watch: {

        selectedGroup(group) {
            this.process();
            this.saveSelectedGroup();
        },

        entities: {
            handler: function (newValue) {
                if (this.ready) {
                    this.readSavedGroup();
                    this.process();
                }
            },
            deep: true
        }
        
    }
};

function getGroupFromName(name) {
    return name.substring(name.indexOf("[") + 1, name.lastIndexOf("]"));
}
