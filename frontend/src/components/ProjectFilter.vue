<template>

    <form class="pure-form pure-form-aligned">
        <div class="pure-control-group">
            <div class="s-input-with-icon">
                <img src="/public/img/funnel-sort-svgrepo-com.svg" class="s-svg-icon" title="filter" />
                <select v-model="selectedProjectId" class="s-filter-input" :aria-invalid="invalid ? 'true' : null">
                    <option :value="ALL_PROJECTS">{{ defaultValueLabel }}</option>
                    <option v-for="project in projects" :value="project.id">{{ project.name }}</option>
                </select>
                <img src="/public/img/close-circle-svgrepo-com.svg" class="s-svg-icon s-pointer" v-if="selectedProjectId != ALL_PROJECTS" @click="reset" title="clear" />
            </div>
        </div>
    </form>

</template>


<script>
const ALL_PROJECTS = "ALL_PROJECTS";

export default {
    name: "ProjectFilter",
    props: ["projects", "page", "entities", "ready", "defaultValueLabel"],
    data() {
        return {
            selectedProjectId: ALL_PROJECTS,
            ALL_PROJECTS: ALL_PROJECTS,
            invalid: false
        }
    },
    methods: {
        readSavedSelectedProjectId() {
            let selectedProjectId = localStorage.getItem(this.page + ":project_id");
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
            localStorage.setItem(this.page + ":project_id", this.selectedProjectId);
        },
        process() {
            let filteredResult = this.entities.filter(item => {
                if (this.selectedProjectId === ALL_PROJECTS) {
                    return true;
                } else {
                    return this.selectedProjectId === item.projectId;
                }
            });

            if (this.selectedProjectId === ALL_PROJECTS) {
                filteredResult.sort((a, b) => a.projectId - b.projectId);
            }

            this.invalid = !filteredResult.length && this.entities.length;
            this.$emit("result", filteredResult);
        },
        reset() {
            this.selectedProjectId = ALL_PROJECTS;
            this.process();
        }
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
}
</script>

<style scoped>
.s-filter-input {
    width: 100%;
    max-width: 250px;
    min-width: 150px;
    box-sizing: border-box;
}
</style>