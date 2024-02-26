<template>
    <footer>
      <p>Count of registered users: {{ countUsers }}</p>
      <p>Count of written posts: {{ countPosts }}</p>
      <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["countPosts", "countUsers"],
}
</script>

<style scoped>

</style>