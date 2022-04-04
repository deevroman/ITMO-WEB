<template>
  <section class="main">
    <div class="container">
      <div class="row">
        <div class="text-center">
          <div class="graph">
            <div class="svg-wrapper">
              <svg height="300" width="300" xmlns="http://www.w3.org/2000/svg" id="graph" @click="validateFromGraph">
                <!-- Стерлки и оси -->
                <line stroke="black" x1="0" x2="300" y1="150" y2="150"></line>
                <line stroke="black" x1="150" x2="150" y1="0" y2="300"></line>
                <polygon fill="black" points="150,0 144,15 156,15" stroke="black"></polygon>
                <polygon fill="black" points="300,150 285,156 285,144" stroke="black"></polygon>

                <!-- Деления -->
                <line stroke="black" x1="200" x2="200" y1="155" y2="145"></line>
                <line stroke="black" x1="250" x2="250" y1="155" y2="145"></line>

                <line stroke="black" x1="50" x2="50" y1="155" y2="145"></line>
                <line stroke="black" x1="100" x2="100" y1="155" y2="145"></line>

                <line stroke="black" x1="145" x2="155" y1="100" y2="100"></line>
                <line stroke="black" x1="145" x2="155" y1="50" y2="50"></line>

                <line stroke="black" x1="145" x2="155" y1="200" y2="200"></line>
                <line stroke="black" x1="145" x2="155" y1="250" y2="250"></line>

                <!-- Подписи к осям -->
                <text fill="black" x="285" y="140">X</text>
                <text fill="black" x="160" y="15">Y</text>

                <!-- Первая фигура (1 четверть) -->
                <path id="circle"
                      fill="orange"
                      fill-opacity="0.2"
                      stroke="orange"
                      d="M 100 150 A 50 50, 90, 0, 0, 150 200 L 150 150 Z"></path>

                <!-- Вторая фигура (2 четверть) -->
                <polygon id="rectangle"
                         fill="orange"
                         fill-opacity="0.2"
                         stroke="orange"
                         points="50,100 50,150 150,150 150,100"></polygon>

                <!-- Третья фигура (4 четверть) -->
                <polygon id="triangle"
                         fill="orange"
                         fill-opacity="0.2"
                         stroke="orange"
                         points="150,150 200,150 150,200"></polygon>

                <!-- Точки на графике -->

              </svg>
            </div>
          </div>
        </div>

        <div class="text-center">
          <table style="width: -moz-available;">
            <tr align="left">
              <th>X:</th>
              <th>
                <select class="input-field" id="input-x" v-model="x">
                  <option v-for="option in xOptions" :key="option.text">
                    {{ option.value }}
                  </option>
                </select>
              </th>
            </tr>
            <tr align="left">
              <th>Y:</th>
              <th>
                <input id="input-y" class="input-field" step="1" name="y" placeholder="(-5; 3)"
                       v-model="y"/>
              </th>
            </tr>
            <tr align="left">
              <th>R:</th>
              <th>
                <select id="input-r" class="input-field" v-model="r">
                  <option v-for="option in xOptions" :key="option.text">
                    {{ option.value }}
                  </option>
                </select>
              </th>
            </tr>
          </table>
        </div>
        <div class="special-button">
          <button id="submit-button" @click="validateForm" type="submit">Отправить</button>
          <button id="remove-button" @click="deleteDots">Удалить свои точки</button>
        </div>

      </div>
    </div>

    <div class="container-table">
      <div class="text-center">
        <p style="font-size: 20px" v-if="dots.length === 0"></p>
        <table v-else class="table-check">
          <thead class="thead">
          <tr class="table-header">
            <th scope="col">X</th>
            <th scope="col">Y</th>
            <th scope="col">R</th>
            <!--            <th scope="col">Время</th>-->
            <th scope="col">Результат попадания</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="dot in dots" :key="dot" class="table-row">
            <td>{{ dot.x }}</td>
            <td>{{ dot.y }}</td>
            <td>{{ dot.r }}</td>
            <!--            <td>{{ dot.time }}</td>-->
            <td>{{ dot.result ? "Попадание" : "Промах" }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>


<script>

// import Button from "@/components/Button";
import qs from "qs";
// import vSelect from 'vue-select'
// import 'vue-select/dist/vue-select.css';

export default {
  name: "Main",
  components: {
    // Button,
    // vSelect,
  },
  data() {
    return {
      x: "",
      y: "",
      r: "5", // максимальный размер графика
      xGraph: "", // Х из графика
      yGraph: "", // Y из графика
      dots: new Array(0), // Список всех точек пользователя
      xOptions: [
        {text: '-3', value: '-3',},
        {text: '-2', value: '-2',},
        {text: '-1', value: '-1',},
        {text: '0', value: '0',},
        {text: '1', value: '1',},
        {text: '2', value: '2',},
        {text: '3', value: '3',},
        {text: '4', value: '4',},
        {text: '5', value: '5',},
      ],
      rOptions: [
        {text: '-3', value: '-3',},
        {text: '-2', value: '-2',},
        {text: '-1', value: '-1',},
        {text: '0', value: '0',},
        {text: '1', value: '1',},
        {text: '2', value: '2',},
        {text: '3', value: '3',},
        {text: '4', value: '4',},
        {text: '5', value: '5',},
      ]
    }
  },
  watch: {
    x(val) {
      this.validateInput(-4, 6, 'input-x', val);
    },
    y(val) {
      this.validateInput(-5, 3, 'input-y', val);
    },
    r(val) {
      if (this.validateInput(-4, 6, 'input-r', val)) {
        let r = parseFloat(this.r);
        let circle = document.getElementById("circle");
        let rectangle = document.getElementById("rectangle");
        let triangle = document.getElementById("triangle");
        circle.setAttribute("d", `M ${150 - 10 * r} 150 A ${r * 10} ${r * 10}, 90, 0, 0, 150 ${150 + 10 * r} L 150 150 Z`);
        rectangle.setAttribute("points", `${150 - 20 * r},${150 - 10 * r} ${150 - 20 * r},150 150,150 150,${150 - 10 * r}`);
        triangle.setAttribute("points", `150,150 ${150 + 10 * r},150 150,${150 + 10 * r}`);
        this.drawDots();
      }
    }
  },
  methods: {
    // Взаимодействие с формой и графиком
    addDots(x, y) {
      this.$axios.put("http://localhost:16170/Web_Lab_4-1.0-SNAPSHOT/controller",
          qs.stringify({x: x, y: y, r: this.r, token: localStorage.getItem("token")}),
          {
            // headers: {"Authorization": "Bearer " + localStorage.getItem("token")}
          }).then(response => {
        this.dots = response.data;
        this.drawDots();
        this.$notify({
          group: 'success',
          title: 'Добавление точки',
          text: 'Успешно',
          type: 'success'
        });
      }).catch(() => {
        this.AxiosErrorHandler("Точку не удалось добавить")
      });
    },
    deleteDots() {
      this.$axios.delete("http://localhost:16170/Web_Lab_4-1.0-SNAPSHOT/controller?token=" + localStorage.getItem("token"),
          {
            // headers: {Authorization: "Bearer " + localStorage.getItem("jwt")}
          }).then(() => {
        this.loadDots();
        this.$notify({
          group: 'success',
          title: 'Удаление точек',
          text: 'Успешно',
          type: 'success'
        });
      }).catch(() => {
        this.AxiosErrorHandler("Точки не удалось удалить");
      });
    },
    logout() {
      this.$notify({
        group: 'success',
        title: 'Выход из аккаунта',
        text: 'Успешно',
        type: 'success'
      });
      this.$router.push({name: "auth-page"}, () => localStorage.clear());
    },

    // Загрузка и прорисовка точек на графике
    loadDots() {
      this.$axios.get("http://localhost:16170/Web_Lab_4-1.0-SNAPSHOT/controller?token=" + localStorage.getItem("token"),
          {}).then(response => {
        this.dots = response.data;
        this.drawDots();
      }).catch(() => {
        this.AxiosErrorHandler("Точки не удалось загрузить");
      });
    },
    drawDots() {
      let r = parseFloat(this.r);
      let svg = document.getElementById("graph")
      let oldDots = document.querySelectorAll("circle");
      oldDots.forEach(oldDot => oldDot.parentNode.removeChild(oldDot));

      if (this.dots.length !== 0) {
        this.dots.forEach(dot => {
          let newDot = document.createElementNS("http://www.w3.org/2000/svg", "circle");
          newDot.setAttribute("id", "target-dot");
          newDot.setAttribute("r", "3.5");
          if (r >= 0) {
            newDot.setAttribute("cx", `${dot.x / 5 * 100 + 150}`);
            newDot.setAttribute("cy", `${150 - dot.y / 5 * 100}`);
          } else {
            newDot.setAttribute("cx", `${(-1.8 * dot.x) / 5 * 100 + 150}`);
            newDot.setAttribute("cy", `${150 - (-1.8 * dot.y) / 5 * 100}`);
          }
          if (dot.r == r) {
            newDot.setAttribute("fill", dot.result === true ? "green" : "red");
            newDot.setAttribute("r", "4.5");
          } else {
            newDot.setAttribute("fill", "black");
            newDot.setAttribute("opacity", `${((r - 0.5 < dot.r) && (r + 0.5 > dot.r)) ? "0.5" : "0.1"}`);
          }
          svg.appendChild(newDot);
        })
      }
    },

    // Различные виды валидации
    validateForm() {
      if ((-3 <= this.x && this.x <= 5) &&
          (-5 < this.y && this.y < 3) &&
          (-3 <= this.r && this.r <= 5) &&
          (!isNaN(this.x) && !isNaN(this.y) && !isNaN(this.r)) &&
          (this.x.trim() !== '' && this.y.trim() !== '' && this.r.trim() !== '')) {
        this.addDots(this.x, this.y);
      } else {
        this.AxiosErrorHandler("Проверте введенные данные");
      }
    },
    validateFromGraph() {
      let position = getMousePosition(document.getElementById("graph"), event);
      this.xGraph = ((position.x - 150) / 100 * 5).toFixed(2);
      this.yGraph = ((150 - position.y) / 100 * 5).toFixed(2);
      if (this.r >= -3 && this.r <= 5) {
        this.addDots(this.xGraph, this.yGraph);
      } else {
        this.AxiosErrorHandler("Проверте значение R");
      }

      function getMousePosition(element, event) {
        let rect = element.getBoundingClientRect();
        return {
          x: event.clientX - rect.left,
          y: event.clientY - rect.top
        };
      }
    },
    validateInput(firstNumber, secondNumber, className, val) {
      let element = document.getElementById(className);
      let buttonSubmit = document.getElementById("submit-button")
      if (parseFloat(val) <= firstNumber || parseFloat(val) >= secondNumber || isNaN(val)) {
        element.classList.add("red");
        element.setAttribute("style", "color: red");
        buttonSubmit.setAttribute("disabled", "true");
        console.log(buttonSubmit)
        return false;
      } else {
        element.classList.remove("red");
        element.removeAttribute("style");
        buttonSubmit.removeAttribute("disabled");
        return true;
      }
    },

    // Вывод сообщений ошибок
    AxiosErrorHandler(errorText) {
      this.$notify({
        group: 'error',
        title: 'Error',
        text: errorText,
        type: 'error'
      })
    }
  },
  mounted() {
    this.loadDots();
    this.drawDots();
  }
}
</script>

<style scoped>
.container {
  margin: 20px auto auto;
  background-color: ghostwhite;
  padding: 20px;
  display: table;
  text-align: center;
  box-shadow: 0 0 10px 1px black;
}

/*
    График
*/
.svg-wrapper {
  background-color: white;
  border: 2px solid orange;
  /*box-shadow: 0 0 5px 0 aqua;*/
  /*border-radius: 20%;*/
  padding: 15px;
  margin-bottom: 10px;
}

/*
    Форма
*/
.inputs {
  background-color: gray;
  padding: 1px 50px 15px 50px;
  border-radius: 5%;
}

.text-center {
  vertical-align: top;
  margin: auto 20px;
  display: inline-block;
}

.input-field {
  text-align: center;
  width: -moz-available;
}

/*
    Кнопки
*/
.special-button button {
  color: white;
  margin: 20px 5px 0;
  letter-spacing: 1px;
  display: inline-block;
  text-decoration: none;
  background-color: black;
  border: 2px solid black;
  border-radius: 6px;
}

.special-button button:hover {
  color: black;
  transition: 0.5s;
  border: 2px solid gray;
  background-color: ghostwhite;
}


@media (min-width: 800px) {
  .table-check {
    color: white;
    margin: 20px auto 0;
    border: 1px solid black;
    background-color: black;
  }

  .table-header th {
    padding: 5px 25px 5px 25px;
    font-size: 1.2em;
    font-weight: lighter;
  }

  .table-row td {
    font-size: 1.2em;
    text-align: center;
    font-weight: lighter;
  }

  .table-row:nth-of-type(odd) {
    margin: 0;
    background-color: #1f1f1f;
  }

  .container-table {
    margin: 20px auto 0 auto;
    padding: 20px 10px 10px 5px;
    display: table;
    text-align: center;
  }
}


@media (max-width: 799px) {
  .table-check {
    color: white;
    margin: 20px auto 0;
    border: 1px solid black;
    background-color: black;
  }

  .table-header th {
    padding: 5px 10px 5px 10px;
    font-size: 1.2em;
    font-weight: lighter;
  }

  .table-row td {
    font-size: 1.2em;
    text-align: center;
    font-weight: lighter;
  }

  .table-row:nth-of-type(odd) {
    margin: 0;
    background-color: #1f1f1f;
  }

  .container-table {
    margin: 20px auto 0 auto;
    padding: 20px 10px 10px 5px;
    display: table;
    text-align: center;
  }
}

</style>