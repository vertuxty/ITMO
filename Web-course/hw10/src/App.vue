<template>
    <div id="app">
        <Header :userId="userId" :users="users"/>
        <Middle :posts="posts" :users="users" :comments="comments" :postId="postId" :user-id="userId"/>
        <Footer :countPosts="countPosts" :countUsers="countUsers"/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return this.$root.$data;
    },
    computed: {
        countPosts : function () {
            return Object.values(this.$root.$data.posts).length;
        },
        countUsers : function () {
            return Object.values(this.$root.$data.users).length;
        },
    },
    beforeCreate() {
        // alert(this.postId)
        // alert(this.userId)
      this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            const users = Object.values(this.users).filter(u => u.login === login);
            if (users.length === 0) {
                this.$root.$emit("onEnterValidationError", "No such user");
            } else {
                this.userId = users[0].id;
                this.$root.$emit("onChangePage", "Index");
            }
        });

        this.$root.$on("onLogout", () => this.userId = null);

        this.$root.$on("onRegister", (login, name) => {
            if (!this.userId) {
              // const regexp = new RegExp("[a-zA-Z]+");
              // alert(!login.match(/^[a-z]*$/i));
                if (!login || login === "" || login.length < 3 || login.length > 16) {
                  this.$root.$emit("onRegisterValidationError", "Login has contains form 3 to 16 symbols");
                  return;
                } else if (!name || name === "" || name.length > 32 || name.length < 1) {
                  this.$root.$emit("onRegisterValidationError", "Name has contains form 1 to 32 symbols")
                  return;
                } else if (!login.match(/^[a-z]*$/i)) {
                  this.$root.$emit("onRegisterValidationError", "Login should contains only latin letters!");
                  return;
                } else if (!name.match(/^[a-z]*$/i)) {
                  this.$root.$emit("onRegisterValidationError", "Name should contains only latin letters!");
                  return;
                }
                const users = Object.values(this.users).filter(u => u.login === login);

                // alert(users.length)
                if (users.length !== 0) {
                  // alert("AYA")
                    this.$root.$emit("onRegisterValidationError", "This user already registered!");
                } else {
                    const id = Math.max(...Object.keys(this.users)) + 1;
                        this.$root.$set(this.users, id, {
                          id, login, name, admin: false
                        });
                    this.$root.$emit("onChangePage", "Index");

                }
            }
        })

        this.$root.$on("onPost", (postId) => {
            // if (!this.postId) {
                this.$root.$emit("onChangePage", "Post");
                this.postId = postId;

            // } else {
            //   this.$root.$emit("onPostValidationError", "Error......");
            // }
        })

        this.$root.$on("onWritePost", (title, text) => {
            if (this.userId) {
                if (!title || title.length < 5) {
                    this.$root.$emit("onWritePostValidationError", "Title is too short");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onWritePostValidationError", "Text is too short");
                } else {
                    const id = Math.max(...Object.keys(this.posts)) + 1;
                    this.$root.$set(this.posts, id, {
                        id, title, text, userId: this.userId
                    });
                }
                this.$root.$emit("onChangePage", "Index");
            } else {
                this.$root.$emit("onWritePostValidationError", "No access");
            }
        });

        this.$root.$on("onWriteComment", (text, postId) => {
          if (this.userId) {
            if (!text || text === "" || text.length < 1) {
                this.$root.$emit("onWriteCommentValidationError", "Comment is not to be empty!");
            } else {
              const id = Math.max(...Object.keys(this.comments)) + 1;
              this.$root.$set(this.comments, id, {
                id, text, userId : this.userId, postId: postId
              });
            }
            this.$root.$emit("onChangePage", "Post");
          } else {
            this.$root.$emit("onWriteCommentValidationError", "No access");
          }

        })

        this.$root.$on("onEditPost", (id, text) => {
            if (this.userId) {
                if (!id) {
                    this.$root.$emit("onEditPostValidationError", "ID is invalid");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onEditPostValidationError", "Text is too short");
                } else {
                    let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                    if (posts.length) {
                        posts.forEach((item) => {
                            item.text = text;
                        });
                    } else {
                        this.$root.$emit("onEditPostValidationError", "No such post");
                    }
                }
            } else {
                this.$root.$emit("onEditPostValidationError", "No access");
            }
        });
    }
}
</script>

<style>
#app {

}
</style>
