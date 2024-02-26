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
import SubUsersPage from "@/components/SubPageElements/SubUsersPage.vue";

export default {
  name: "UsersPage",
  components: {SubUsersPage},
  props: ["users"],
}
</script>


<style>

</style>