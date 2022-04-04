<template>
<div @load="logout">

</div>
</template>

<script>
export default {
  name: "Logout",
  methods: {
    logout() {
      console.log("fffffffffffffffff")
      this.$notify({
        group: 'success',
        title: 'Выход из аккаунта',
        text: 'Успешно',
        type: 'success'
      });
      this.$router.push({name: "auth-page"}, () => localStorage.clear());
    }
  },
  mounted: function (){
    this.logout()
  }

}
</script>

<style scoped>

</style>