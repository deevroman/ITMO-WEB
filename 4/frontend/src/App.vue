<template>
  <div id="app">
    <Header v-bind="headerParams"/>

<!--    <Loader id="load" v-if="loading"/>-->
    <div id="contentContainer">
      <router-view/>
      <notifications group="error" position="bottom right" max="5" />
      <notifications group="success" position="bottom right" max="5" />
    </div>


  </div>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: 'App',
  components: {
    Header,
  },
  data(){
    return {
      headerParams: {
        title: "Лабораторная работа №4",
        authorName: "Деев Роман",
      },
      loading: true
    }
  },
  mounted(){
    setTimeout(() => {this.loading = false}, 1000)
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

body{
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: whitesmoke;
}

#contentContainer{
  margin-top: 10px;
  margin-bottom: 90px;
}
#load{
  position: absolute;
  top: 50%;
  left: 50%;
  margin-right: -50%;
  transform: translate(-50%, -50%);
  color: grey;
}
</style>
