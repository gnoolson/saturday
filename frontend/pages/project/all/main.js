import { createApp } from "vue"

import Footer from "@/components/Footer.vue"

import NameFilter from "@/components/NameFilter.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"

const app = {
    components: {
        "s-name-filter": NameFilter,

        "s-footer": Footer
    },
    mounted() {
        this.loadProjects();
    },
    data() {
        return {
            projects: [],
            resultAfterNameFilter: [],
            superAccess: SUPER_ACCESS,
            projectsLoaded: false,
            expandedDescriptions: new Set()
        }
    },
    methods: {
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
            });
        },
        goToCreateNewPage() {
            window.location.href = "/project/create";
        },
        goToEditPage(project) {
            window.location.href = "/project/" + project.id + "/edit";
        },
        setResultAfterNameFilter(entities) {
            this.resultAfterNameFilter = entities;
        },

        getDescription(entity) {
            if (!entity.description.length) {
                return "no description";
            }

            if (this.isDescriptionExpanded(entity.id)) {
                return entity.description;
            } else {
                let maxLength = 100;
                let description = entity.description.slice(0, maxLength);
                if (entity.description.length > maxLength) {
                    description += " ...";
                }
                return description;
            }
        },
        expandDescription(entityId) {
            this.expandedDescriptions.add(entityId);
        },
        collapseDescription(entityId) {
            this.expandedDescriptions.delete(entityId);
        },
        isDescriptionExpanded(entityId) {
            return this.expandedDescriptions.has(entityId);
        }

    },
    computed: {
        filteredProjects() {
            return this.resultAfterNameFilter;
        },
    }
};

createApp(app).mount("#app");