import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import NameFilter from "@/components/NameFilter.vue"
import ProjectFilter from "@/components/ProjectFilter.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import DashboardRestApi from "@/rest_api/DashboardRestApi"
import CopyId from "@/components/CopyId.vue";

const app = {
    components: {
        "s-footer": Footer,
        "s-project-filter": ProjectFilter,
        "s-name-filter": NameFilter,
        "s-copy-id" : CopyId
    },
    mounted() {
        this.loadDashboards();
        this.loadProjects();
    },
    data() {
        return {
            resultAfterProjectFilter: [],
            resultAfterNameFilter: [],
            dashboards: [],
            projects: [],
            projectsLoaded: false,
            dashboardsLoaded: false,
            superAccess: SUPER_ACCESS,
            expandedDescriptions: new Set()
        }
    },
    methods: {
        loadDashboards() {
            DashboardRestApi.getAll((data) => {
                this.dashboards = data;
                this.dashboardsLoaded = true;
            });
        },
        openDashboardPage(dashboard) {
            window.location.href = "/dashboard/open/" + dashboard.id;
        },
        goToCreateNewPage() {
            window.location.href = "/dashboard/create";
        },
        goToEditPage(dashboard) {
            window.location.href = "/dashboard/" + dashboard.id + "/edit";
        },
        goToCodePage(dashboard) {
            window.location.href = "/dashboard/" + dashboard.id + "/code";
        },
        loadProjects() {
            ProjectRestApi.getAll((data) => {
                this.projects = data;
                this.projectsLoaded = true;
            });
        },
        setResultAfterProjectFilter(entities) {
            this.resultAfterProjectFilter = entities;
        },
        setResultAfterNameFilter(entities) {
            this.resultAfterNameFilter = entities;
        },
        getProjectNameById(projectId) {
            let result = "";
            this.projects.forEach(project => {
                if (project.id === projectId)
                    result = project.name;
            });
            return result;
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
        },
        isOpenAvailable(dashboard) {
            if (this.superAccess)
                return true;

            if (dashboard.access == "VIEWER" || dashboard.access == "ANONYMOUS")
                return true;

            return false;
        }
    },
    computed: {
        filteredDashboards() {
            return this.resultAfterNameFilter;
        },
        projectsAndDashboardsLoaded() {
            return this.projectsLoaded && this.dashboardsLoaded;
        }
    }
};

createApp(app).mount("#app");