<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <UsersPage v-if="page === 'UsersPage'"/>
            <WritePost v-if="page === 'WritePost' && getUser !== null"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import UsersPage from "@/components/page/UsersPage.vue";
import WritePost from "@/components/page/WritePost.vue";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        WritePost,
        UsersPage,
        Register,
        Enter,
        Index,
        Sidebar
    },
    props: ["posts"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    },
    methods: {
        getUser: function () {
            if (localStorage.getItem("jwt") && !this.user) {
                return localStorage.getItem("jwt");
            }
            return null;
        }
    }
}
</script>

<style scoped>

</style>
