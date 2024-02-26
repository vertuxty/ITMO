<template>
    <div class="comment form">
        <div class="header">Write Comment</div>
        <div class="body">
            <form ref="writeForm" @submit.prevent="onWriteComment">
                <div class="field">
                    <div class="name">
                        <label for="text">Text:</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" v-model.trim="text"></textarea>
                    </div>
                </div>
                <div class="error">{{ error }}</div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
  name: "WriteCommentStructure",
  props: ["postId"],
  data: function () {
    return {
      text : "",
      error: "",
    }
  },
  methods: {
    onWriteComment: function () {
      this.error = "";
      this.$root.$emit("onWriteComment", this.text, this.postId);
      this.$refs.writeForm.reset();
    }
  },
  beforeCreate() {
    const self = this;
    Object.keys(this.data).forEach(function (key) {
      self.data[key] = "";
    })
  },
  beforeMount() {
    this.$root.$on("onWriteCommentValidationError", error => this.error = error);
  }
}
</script>

<style scoped>

</style>