<template>
  <div class="comment">
    <div class="header">
      <div class="name">
        {{ comment.userId }}
      </div>
    </div>
    <div class="body">
      <ParagraphStructure v-for="text in comment.text.split('\n')" :text="text" :key="text"/>
    </div>
  </div>
</template>

<script>
import ParagraphStructure from "./supports/ParagraphStructure";

export default {
  name: "CommentsStructure",
  components: {ParagraphStructure},
  props: ["comment"],
}
</script>

<style scoped>

</style>