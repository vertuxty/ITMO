<template>
    <div class="postPage">
      <PostStructure :post="post" :commentsCount="commentsCount"/>
      <div v-if="postComments.length > 0" class="comments">
          <CommentsStructure v-for="comment in postComments" :comment="comment" :key="comment.id"/>
      </div>
      <div ref="writeRef" class="writeComments">
          <WriteCommentStructure v-if="userId" :post-id="post.id"/>
      </div>
    </div>
</template>

<script>

import PostStructure from "@/components/poststructure/PostStructure.vue";
import CommentsStructure from "@/components/poststructure/CommentsStructure.vue";
import WriteCommentStructure from "@/components/poststructure/WriteCommentStructure.vue";

export default {
    props: ["post", "comments", "userId"],
    components: {WriteCommentStructure, CommentsStructure, PostStructure},
    computed : {
      postComments : function () {
         return Object.values(this.comments).filter(c => c.postId === this.post.id).slice().reverse();
      },
      commentsCount : function () {
        return this.postComments.length;
      }
    },
  // beforeCreate() {
  //
  // },
  beforeMount() {
        this.$root.$on("onPostValidationError")
    }
}
</script>


<style scoped>

</style>