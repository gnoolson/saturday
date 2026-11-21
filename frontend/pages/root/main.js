import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import NameFilter from "@/components/NameFilter.vue"
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import DashboardRestApi from "@/rest_api/DashboardRestApi.js"
import ProjectFilter from "@/components/ProjectFilter.vue"


const app = {
    components: {

        "s-footer": Footer,
        "s-name-filter": NameFilter,
        "s-project-filter": ProjectFilter,
    },
    data() {
        return {
            dashboards: [],
            projects: [],
            projectsLoaded: false,
            dashboardsLoaded: false,
            resultAfterProjectFilter: [],
            resultAfterNameFilter: []
        }
    },
    mounted() {
        this.loadDashboards();
        this.loadProjects();
    },
    methods: {
        loadDashboards() {
            DashboardRestApi.getAvailable((data) => {
                this.dashboards = data;
                this.dashboardsLoaded = true;
            });
        },
        loadProjects() {
            ProjectRestApi.getAvailable((data) => {
                this.projects = data;
                this.projectsLoaded = true;
            });
        },
        getProjectNameById(id) {
            let result = "";
            this.projects.forEach(project => {
                if (project.id === id)
                    result = project.name;
            });
            return result;
        },
        openDashboard(dashboard) {
            window.location.href = "/dashboard/open/" + dashboard.id;
        },
        setResultAfterProjectFilter(entities) {
            this.resultAfterProjectFilter = entities;
        },
        setResultAfterNameFilter(entities) {
            this.resultAfterNameFilter = entities;
        }
    },
    computed: {

        filteredDashboards() {
            return this.resultAfterNameFilter;
        },
        projectsAndDashboardsLoaded() {
            return this.projectsLoaded && this.dashboardsLoaded;
        },

    }
};

createApp(app).mount("#app");