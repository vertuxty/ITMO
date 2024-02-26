<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :posts="posts"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            user: null,
            posts: []
        }
    },
    beforeMount() {
        if (localStorage.getItem("jwt") && !this.user) {
            this.$root.$emit("onJwt", localStorage.getItem("jwt"));
        }

        axios.get("/api/1/posts").then(response => {
            this.posts = response.data;
        });

    },
    beforeCreate() {

        this.$root.$on("onRegister", (login, name, password) => {
          if (password === "") {
              this.$root.$emit("onRegisterValidationError", "Password is required");
              return;
          }

          if (password.length < 4 || password.length > 255) {
              this.$root.$emit("onRegisterValidationError", "Password must contains from 4 to 255 symbols");
              return;
          }

          if (name === "") {
            this.$root.$emit("onRegisterValidationError", "Name is required");
            return;
          } else if (name.length < 2 || name.length > 24) {
            this.$root.$emit("onRegisterValidationError", "Name must contains from 2 to 24 symbols");
            return;
          }

          if (login === "") {
            this.$root.$emit("onRegisterValidationError", "Login is required");
            return;
          }

          if (login.length < 2 || login.length > 24) {
            this.$root.$emit("onRegisterValidationError", "Login must contains from 1 to 16 symbols");
            return;
          }


          // alert(login + " " + password + " " + name)
          axios.post("/api/1/users", {
            login, name, password
          }).then(response => {
            localStorage.setItem("jwt", response.data);
            this.$root.$emit("onJwt", response.data);
          }).catch(error => {
            this.$root.$emit("onRegisterValidationError", error.response.data);
          });
        })

        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwt", {
                    login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onJwt", (jwt) => {
            localStorage.setItem("jwt", jwt);

            axios.get("/api/1/users/auth", {
                params: {
                    jwt
                }
            }).then(response => {
                this.user = response.data;
                this.$root.$emit("onChangePage", "Index");
            }).catch(() => this.$root.$emit("onLogout"))
        });

        this.$root.$on("onLogout", () => {
            localStorage.removeItem("jwt");
            this.user = null;
        });

        this.$root.$on("onWritePost", (title, text) => {

            if (title === "") {
              this.$root.$emit("onWritePostValidationError", "Title should not be empty.");
              return;
            }

            if (text === "") {
              this.$root.$emit("onWritePostValidationError", "Text should not be empty.");
              return;
            }

            if (title.length < 1 || title.length > 24) {
              this.$root.$emit("onWritePostValidationError", "Text length must be from 1 to 24");
              return;
            }

            if (text.length < 1 || text.length > 65000) {
              this.$root.$emit("onWritePostValidationError", "Text length must be from 1 to 65000");
              return;
            }

            const jwt = localStorage.getItem("jwt");
            axios.post("/api/1/posts", {
                title, text, jwt
            }).then(response => {
              this.posts = response.data;
              this.$root.$emit("onChangePage", "Index");
            }).catch(error => {
              this.$root.$emit("onWritePostValidationError", error.response.data);
            })
        })
    }
}
</script>

<style>
#app {

}
</style>
