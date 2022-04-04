<template>
  <header class="header">
    <div>
      <span class="lab-name">{{ title }}</span>
    </div>
    <div>
        <span>
          <a target="_blank" v-bind:href="authorUrl">{{ authorName }}</a>
           P3211
          Вариант
          <a target="_blank" v-bind:href="varUrl">951</a>
          <a v-if="this.$route.path != '/auth'" id="logout" href="/logout" color="blue" label="Выйти из аккаунта" @click.native="logout">Выйти</a>
        </span>
    </div>
  </header>
</template>

<script>
export default {
  name: "Header",
  props: {
    title: String,
    authorName: String,
    authorUrl: String,
    varUrl: String
  },
  methods: {
    logout() {
      this.$router.push({name: 'logout'});
    }
  }
}
</script>

<style scoped>

header {
  margin: 0;
  padding: 0;
}

.header {
  height: 25%;
  min-width: 440px;
  display: flex;
  background-color: gray;
  justify-content: space-between;
  box-shadow: 0 0 20px 5px gray;
  font-family: "MuseoModerno", cursive;
}

/*
  Название:
*/

.header .lab-name {
  color: whitesmoke;
  font-size: 1.5em;
  font-weight: bold;
  /*margin-top: 10px;*/
}

/*
  Имя, Фамилия, вариант лабы:
*/

.header > div {
  display: flex;
  align-items: center;
  margin: 5px 20px 5px;
  /*flex-direction: column;*/
}

.header span, .header a {
  color: white;
  /*font-size: 0.9em;*/
  font-weight: bold;
  text-decoration: none;
}

</style>