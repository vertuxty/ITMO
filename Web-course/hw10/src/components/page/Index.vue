<template>
    <div class="main">
        <PostStructure v-for="post in posts" :post="post" :key="post.id" :commentsCount="commentsCount(post, comments)"/>
    </div>
</template>



<script>
import PostStructure from "@/components/poststructure/PostStructure";
import {commentsCount} from "@/fun"
export default {
    name: "Index",
    components: {PostStructure},
    props: ["posts", "comments"],
    methods : {
        commentsCount : commentsCount
    }
}
</script>

<style scoped>

</style>
