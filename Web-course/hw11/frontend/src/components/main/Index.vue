<template>
    <div>
      <PostStructure v-for="post in posts" :post="post" :key="post.id"/>
    </div>
</template>

<script>
import PostStructure from "@/components/poststructure/PostStructure.vue";

export default {
    name: "Index",
    props: ["posts"],
  components: {PostStructure}
}
</script>

<style scoped>

</style>