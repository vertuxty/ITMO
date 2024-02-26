<template>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <SubUsersPage v-for="user in users" :user="user" :key="user.id"/>
            </tbody>
        </table>
    </div>
</template>

<script>
import SubUsersPage from "@/components/structures/SubUsersPage.vue";
import axios from "axios";

export default {
  name: "UsersPage",
  components: {SubUsersPage},
  data: function () {
    return {
      users: []
    }
  },
  beforeMount() {
    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    })
  }
}
</script>


<style>

</style>