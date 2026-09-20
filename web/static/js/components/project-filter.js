const ALL_PROJECTS = "ALL_PROJECTS";

export default {

    template: `
        
                <label>
                    <span>
                        <img src="/static/img/filter-square-fill.svg" />
                        {{label}}
                    </span>
                    <select v-model="selectedProjectId">
                        <option :value="ALL_PROJECTS">{{defaultValueLabel}}</option>
                        <option v-for="project in projects" :value="project.id">{{project.name}}</option>
                    </select>
                </label>
        `,

    props: ["projects", "type", "entities", "ready", "label", "defaultValueLabel"],

    data() {
        return {
            selectedProjectId: ALL_PROJECTS,
            ALL_PROJECTS: ALL_PROJECTS,
        }
    },

    methods: {

        readSavedSelectedProjectId() {
            let selectedProjectId = localStorage.getItem(this.type + ":project_id");
            if (this.isProjectAvailable(selectedProjectId)) {
                this.selectedProjectId = selectedProjectId;
            } else {
                this.selectedProjectId = this.ALL_PROJECTS;
            }
        },

        isProjectAvailable(projectId) {
            let result = false;
            this.projects.forEach((project) => {
                if (project.id === projectId)
                    result = true;
            });
            return result;
        },

        saveSelectedProjectId() {
            localStorage.setItem(this.type + ":project_id", this.selectedProjectId);
        },

        process() {
            let filteredResult = this.entities.filter(item => {
                if (this.selectedProjectId === ALL_PROJECTS)
                    return true;
                else
                    return this.selectedProjectId === item.projectId;
            });

            this.$emit("result", filteredResult);
        },

    },

    watch: {

        selectedProjectId() {
            this.process();
            this.saveSelectedProjectId();
        },

        ready() {
            this.readSavedSelectedProjectId();
            this.process();
        },

        entities: {
            handler: function (newValue) {
                this.process();
            },
            deep: true
        }

    }

};

