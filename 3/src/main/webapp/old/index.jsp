<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id="responses" scope="session" class="java.util.ArrayList"/>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <link rel="shortcut icon" href="favicon.png">
    <title>Roman Deev Web 2</title>

    <link rel="stylesheet index" href="<%= request.getContextPath() %>/css/index.css">
</head>
<body>
<header class="header">
    <div>
        Деев Роман P3211 Вариант 12009
    </div>
    <div id="time">

    </div>
</header>
<table class="main-table">
    <tr class="graphic">
        <td style="vertical-align: top;" id="svg-cell">
            <div id="svg-wrapper">
                <svg height="300" width="300" xmlns="http://www.w3.org/2000/svg" id="svg">
                    <!-- Стерлки и оси -->
                    <line stroke="black" x1="0" x2="300" y1="150" y2="150"></line>
                    <line stroke="black" x1="150" x2="150" y1="0" y2="300"></line>
                    <polygon fill="black" points="150,0 146,20 154,20" stroke="black"></polygon>
                    <polygon fill="black" points="300,150 280,155 280,145" stroke="black"></polygon>

                    <!-- Деления -->
                    <line stroke="black" x1="200" x2="200" y1="155" y2="145"></line>
                    <line stroke="black" x1="250" x2="250" y1="155" y2="145"></line>

                    <line stroke="black" x1="50" x2="50" y1="155" y2="145"></line>
                    <line stroke="black" x1="100" x2="100" y1="155" y2="145"></line>

                    <line stroke="black" x1="145" x2="155" y1="100" y2="100"></line>
                    <line stroke="black" x1="145" x2="155" y1="50" y2="50"></line>

                    <line stroke="black" x1="145" x2="155" y1="200" y2="200"></line>
                    <line stroke="black" x1="145" x2="155" y1="250" y2="250"></line>

                    <!-- Подписи к делениям и осям -->
                    <text class="svgText" fill="black" x="195" y="140">R/2</text>
                    <text class="svgText" fill="black" x="250" y="140">R</text>

                    <text class="svgText" fill="black" x="40" y="140">-R</text>
                    <text class="svgText" fill="black" x="85" y="140">-R/2</text>

                    <text class="svgText" fill="black" x="160" y="55">R</text>
                    <text class="svgText" fill="black" x="160" y="105">R/2</text>

                    <text class="svgText" fill="black" x="160" y="204">-R/2</text>
                    <text class="svgText" fill="black" x="160" y="255">-R</text>

                    <text class="svgText" fill="black" x="285" y="140">x</text>
                    <text class="svgText" fill="black" x="160" y="15">y</text>

                    <!-- Прямоугольник -->
                    <polygon fill="orange"
                             fill-opacity="0.48"
                             stroke="orange"
                             points="50,150 50,50 150,50 150,150"></polygon>

                    <!-- Треугольник -->
                    <polygon fill="orange"
                             fill-opacity="0.48"
                             stroke="orange"
                             points="150,150 200,150 150,100"></polygon>

                    <!-- Четверть круга -->
                    <path fill="orange"
                          fill-opacity="0.48"
                          stroke="orange"
                          d="M150 200 Q100,200 100,150 L 150,150 Z"></path>

                    <!-- Точки на графике -->
                    <%--                <circle id="point" r="4" cx="150" cy="150" fill="red" stroke="white" visibility="hidden"/>--%>

                    <c:forEach var="response" items="${responses}">
                    <circle r="3.5"
                            cx=${((300 / 2 + (response.x) / response.r * 100))}
                                    cy=${((300 / 2 + (response.y) / response.r * -100))}
                            id="target-dot"
                            fill=${response.atArea == "Попадание" ? "green" : "red"}>
                    </circle>
                    </c:forEach>
                </svg>
            </div>
        </td>
        <td style="vertical-align: top;">

            <form id="coordinates-form">
                <table>
                    <tr>
                        <td class="first-letter">
                            X:
                        </td>
                        <td>
                            <div class="x-checkboxes">
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="-3">
                                    <span>-4</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="-3">
                                    <span>-3</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="-2">
                                    <span>-2</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="-1">
                                    <span>-1</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="0" checked>
                                    <span>0</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="1">
                                    <span>1</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="2">
                                    <span>2</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="3">
                                    <span>3</span>
                                </label>
                                <label>
                                    <input class="x-checkbox" type="radio" name="x" value="4">
                                    <span>4</span>
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-letter">
                            Y:
                        </td>
                        <td>
                            <div class="Y-text" id="Y" align="left">
                                <label>
                                    <input id="Y-text" class="Y-text-input" type="text" name="y" placeholder="(-3; 3)"
                                           required oninput="this.setCustomValidity('')"
                                    />
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-letter">
                            <div class="first-letter">R:</div>
                        </td>
                        <td align="center">
                            <div class="R-text" id="R">
                                <label>
                                    <ul class="r-select" id="r-select">
                                        <li>
                                            <input type="radio" id="a1" name="r" value="1"/>
                                            <label for="a1">1</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="a2" name="r" value="2" checked="checked"/>
                                            <label for="a2">2</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="a3" name="r" value="3"/>
                                            <label for="a3">3</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="a4" name="r" value="4"/>
                                            <label for="a4">4</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="a5" name="r" value="5"/>
                                            <label for="a5">5</label>
                                        </li>
                                    </ul>
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
                <button id="submit-button" type="submit">Отправить</button>
                <button id="clear-button" type="reset">Очистить таблицу</button>
            </form>
        </td>
        <td style="vertical-align: top;">
            <table id="check" class="table-check">
                <tr class="table-header">
                    <th scope="col">R</th>
                    <th scope="col">X</th>
                    <th scope="col">Y</th>
                    <th scope="col">Результат</th>
                </tr>
                <c:forEach var="response" items="${responses}">
                    <tr class="table-row">
                        <td>${response.x}</td>
                        <td>${response.y}</td>
                        <td>${response.r}</td>
                            <%--                        <td>${entries.simpleDateFormat.format(response.queryTime)}</td>--%>
                        <td
                            ${response.atArea == "Попадание" ? " style=\"background-color: green\">Попадание" :
                                    " style=\"background-color: red\">Промах"}</td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>


<script type="text/javascript" src="<%= request.getContextPath() %>js/jquery.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>js/script.js"></script>
</body>
</html>