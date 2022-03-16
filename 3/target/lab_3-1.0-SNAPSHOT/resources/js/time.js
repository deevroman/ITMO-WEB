
function displayCanvas(){
    var canvasHTML = document.getElementById('myCanvas');
    var contextHTML = canvasHTML.getContext('2d');
    contextHTML.strokeRect(0,0,canvasHTML.width, canvasHTML.height);

    //Расчет координат центра и радиуса часов
    var radiusClock = canvasHTML.width/2 - 10;
    var xCenterClock = canvasHTML.width/2;
    var yCenterClock = canvasHTML.height/2;

    //Очистка экрана. 
    contextHTML.fillStyle = "#ffffff";
    contextHTML.fillRect(0,0,canvasHTML.width,canvasHTML.height);

    //Рисуем контур часов
    contextHTML.strokeStyle =  "#000000";
    contextHTML.lineWidth = 1;
    contextHTML.beginPath();
    contextHTML.arc(xCenterClock, yCenterClock, radiusClock, 0, 2*Math.PI, true);
    contextHTML.moveTo(xCenterClock, yCenterClock);
    contextHTML.stroke();
    contextHTML.closePath();

    //Рисуем рисочки часов
    var radiusNum = radiusClock - 10; //Радиус расположения рисочек	
    var radiusPoint;
    for(var tm = 0; tm < 60; tm++){
        contextHTML.beginPath();
        if(tm % 5 == 0){radiusPoint = 5;}else{radiusPoint = 2;} //для выделения часовых рисочек
        var xPointM = xCenterClock + radiusNum * Math.cos(-6*tm*(Math.PI/180) + Math.PI/2);
        var yPointM = yCenterClock - radiusNum * Math.sin(-6*tm*(Math.PI/180) + Math.PI/2);
        contextHTML.arc(xPointM, yPointM, radiusPoint, 0, 2*Math.PI, true);
        contextHTML.stroke();
        contextHTML.closePath();
    }

    //Оцифровка циферблата часов
    for(var th = 1; th <= 12; th++){
        contextHTML.beginPath();
        contextHTML.font = 'bold 25px sans-serif';
        var xText = xCenterClock + (radiusNum - 30) * Math.cos(-30*th*(Math.PI/180) + Math.PI/2);
        var yText = yCenterClock - (radiusNum - 30) * Math.sin(-30*th*(Math.PI/180) + Math.PI/2);
        if(th <= 9){
            contextHTML.strokeText(th, xText - 5 , yText + 10);
        }else{
            contextHTML.strokeText(th, xText - 15 , yText + 10);
        }
        contextHTML.stroke();
        contextHTML.closePath();
    }


    //Рисуем стрелки
    var lengthSeconds = radiusNum - 10;
    var lengthMinutes = radiusNum - 15;
    var lengthHour = lengthMinutes / 1.5;
    var d = new Date();                //Получаем экземпляр даты
    var t_sec = 6*d.getSeconds();                           //Определяем угол для секунд
    var t_min = 6*(d.getMinutes() + (1/60)*d.getSeconds()); //Определяем угол для минут
    var t_hour = 30*(d.getHours() + (1/60)*d.getMinutes()); //Определяем угол для часов

    //Рисуем секунды
    contextHTML.beginPath();
    contextHTML.strokeStyle =  "#FF0000";
    contextHTML.moveTo(xCenterClock, yCenterClock);
    contextHTML.lineTo(xCenterClock + lengthSeconds*Math.cos(Math.PI/2 - t_sec*(Math.PI/180)),
        yCenterClock - lengthSeconds*Math.sin(Math.PI/2 - t_sec*(Math.PI/180)));
    contextHTML.stroke();
    contextHTML.closePath();

    //Рисуем минуты
    contextHTML.beginPath();
    contextHTML.strokeStyle =  "#000000";
    contextHTML.lineWidth = 3;
    contextHTML.moveTo(xCenterClock, yCenterClock);
    contextHTML.lineTo(xCenterClock + lengthMinutes*Math.cos(Math.PI/2 - t_min*(Math.PI/180)),
        yCenterClock - lengthMinutes*Math.sin(Math.PI/2 - t_min*(Math.PI/180)));
    contextHTML.stroke();
    contextHTML.closePath();

    //Рисуем часы
    contextHTML.beginPath();
    contextHTML.lineWidth = 5;
    contextHTML.moveTo(xCenterClock, yCenterClock);
    contextHTML.lineTo(xCenterClock + lengthHour*Math.cos(Math.PI/2 - t_hour*(Math.PI/180)),
        yCenterClock - lengthHour*Math.sin(Math.PI/2 - t_hour*(Math.PI/180)));
    contextHTML.stroke();
    contextHTML.closePath();

    //Рисуем центр часов
    contextHTML.beginPath();
    contextHTML.strokeStyle =  "#000000";
    contextHTML.fillStyle = "#ffffff";
    contextHTML.lineWidth = 3;
    contextHTML.arc(xCenterClock, yCenterClock, 5, 0, 2*Math.PI, true);
    contextHTML.stroke();
    contextHTML.fill();
    contextHTML.closePath();

    return;
}

window.setInterval(
    function(){
        var d = new Date();
        document.getElementById("clock").innerHTML =     d.toLocaleTimeString();
        displayCanvas();
    }
    , 7000);

$(document).ready(function() {
    var d = new Date();
    document.getElementById("clock").innerHTML =     d.toLocaleTimeString();
    displayCanvas();
});