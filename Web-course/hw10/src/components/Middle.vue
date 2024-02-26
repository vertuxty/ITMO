<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="viewAllPosts" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <UsersPage v-if="page === 'UsersPage'" :users="users"/>
            <Post v-if="page === 'Post'" :post="this.posts[postId]" :comments="comments" :user-id="this.userId"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "./page/Register";
import UsersPage from "./page/UsersPage";
import Post from "./page/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        Post,
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost,
        Register,
        UsersPage
    },
    props: ["posts", "users", "comments", "postId", "userId"],
    computed: {
        getPost : function () {
          return this.viewAllPosts.slice(0, 1);
        },
        viewPosts: function () {
          // alert(Object.values(this.posts)[0])

          return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        viewAllPosts : function () {
          return Object.values(this.posts).sort((a, b) => b.id - a.id)
        }

    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>
