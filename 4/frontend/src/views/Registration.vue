<template>
  <div id="content">
    <form id="form" @sumbit.prevent="logIn">
      <div id="login">
        <input type="text"
               id="loginInput"
               required
               placeholder="Логин"
               v-model.trim="login"/>
      </div>
      <div id="password">
        <input type="password"
               id="passwordInput"
               required
               placeholder="Пароль"
               v-model.trim="password"/>
      </div>
      <div id="buttons">
        <div>
          <Button color="blue" label="Войти" @click.native="logIn"/>
        </div>
        <div>
          <Button color="white" style="color: black" label="Регистрация" @click.native="register"/>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import Button from "@/components/Button";
import qs from "qs";

export default {
  name: "Registration",
  components: {
    Button,
  },
  data() {
    return {
      login: "",
      password: ""
    }
  },
  methods: {
    logIn(e) {
      e.preventDefault()
      // this.$router.push({name: 'app-page'});
      this.$axios.post('http://localhost:16170/Web_Lab_4-1.0-SNAPSHOT/auth/login', qs.stringify({
        login: this.login,
        password: this.password
      })).then(response => {
        localStorage.setItem("token", response.data);
        this.$router.push({name: 'app-page'});
      }).catch(error => {
        this.AxiosErrorHandler(error.response.data);
      });
    },
    register(e) {
      e.preventDefault();
      this.$axios.post('http://localhost:16170/Web_Lab_4-1.0-SNAPSHOT/auth/register', qs.stringify({
        login: this.login,
        password: this.password
      })).then(() => {
        this.$notify({
          group: 'success',
          title: 'Регистрация',
          text: 'Вы теперь можете войти',
          type: 'success'
        });
      }).catch(error => {
        this.AxiosErrorHandler(error.response.data);
      });
    },
    AxiosErrorHandler(errorText) {
      this.$notify({
        group: 'error',
        title: 'Error',
        text: errorText,
        type: 'error'
      })
    }
  }
}
</script>

<style scoped>
#form button {
  margin: 20px 10px 10px 10px;
}

#login, #password {
  margin: 5px;
}

#form {
  position: relative;
  font-size: 20px;
  flex-direction: column;
  margin: 30px auto auto;
  background-color: ghostwhite;
  padding: 20px;
  display: table;
  text-align: center;
  /*box-shadow: 0 0 10px 1px black;*/
}

input {
  margin-left: 13px;
  padding: 5px 0 5px 2px;
}


</style>