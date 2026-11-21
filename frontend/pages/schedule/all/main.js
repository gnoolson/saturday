import { createApp } from "vue"

import Footer from "@/components/Footer.vue"
import NameFilter from "@/components/NameFilter.vue";
import ProjectFilter from "@/components/ProjectFilter.vue";
import ProjectRestApi from "@/rest_api/ProjectRestApi.js"
import ScheduleRestApi from "@/rest_api/ScheduleRestApi";

const app = {
    components: {

        "s-footer": Footer,
        "s-project-filter": ProjectFilter,
        "s-name-filter": NameFilter
    },
    mounted() {
        this.loadSchedules();
        this.loadProjects();
    },
    data() {
        return {
            resultAfterProjectFilter: [],
            resultAfterNameFilter: [],
            schedules: [],
            projects: [],
            projectsLoaded: false,
            schedulesLoaded: false,
            superAccess: SUPER_ACCESS,
            waitActionResult: false,
            expandedDescriptions: new Set()
        }
    },
    methods: {
        loadSchedules() {
            ScheduleRestApi.getAll((data) => {
                this.schedules = data;
                this.schedulesLoaded = true;
            });
        },
        goToCreateNewPage() {
            window.location.href = "/schedule/create";
        },
        goToEditPage(schedule) {
            window.location.href = "/schedule/" + schedule.id + "/edit";
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
        isEnableButtonAvailable(schedule) {
            return !schedule.enabled;
        },
        isDisableButtonAvailable(schedule) {
            return schedule.enabled;
        },
        control(scheduleId, flag) {
            this.waitActionResult = true;
            ScheduleRestApi.control(scheduleId, flag, (data) => {
                this.waitActionResult = false;
                this.loadSchedules();
            }, (error) => {
                this.waitActionResult = false;
                console.error(error);
            });
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
        filteredSchedules() {
            return this.resultAfterNameFilter;
        },
        projectsAndSchedulesLoaded() {
            return this.projectsLoaded && this.schedulesLoaded;
        },
        controlButtonsDisabled() {
            return this.waitActionResult;
        }
    }
}

createApp(app).mount("#app");