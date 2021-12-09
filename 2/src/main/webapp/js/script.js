let X = 0, Y = 0, R = 3;
const GRAPH_WIDTH = 300;
const GRAPH_HEIGHT = 300;
const SVG_SIZE = 300;
const yTextField = document.getElementById("Y-text");

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("submit-button").addEventListener("click", submit);
    document.getElementById("clear-button").addEventListener("click", clearButton);
});

function checkY() {
    let Y_text = document.getElementById("Y-text");
    Y = Y_text.value.replace(",", ".");
    if (Y.trim() === "") {
        Y_text.setCustomValidity("Заполните поле");
        Y_text.reportValidity();
        return false;
    } else if (!isFinite(Y)) {
        Y_text.setCustomValidity("Должно быть число!");
        Y_text.reportValidity();
        return false;
    } else if (Y > 3 || Y < -3) {
        Y_text.setCustomValidity("Вы вышли за диапазон (-3; 3)!");
        Y_text.reportValidity();
        return false;
    } else {
        let dot_pos = Y.indexOf(".");
        if (Y < 3 && Y > -3 || Y.substr(0, dot_pos) == -2 || Y.substr(0, dot_pos) == 2) {
            Y_text.setCustomValidity("");
            return true;
        } else {
            Y_text.setCustomValidity("Вы вышли за диапазон (-3; 3)!");
            document.getElementById('Y-text').style.borderColor = "red";
            return false;
        }
    }
    Y_text.setCustomValidity("");
    return true;
}


function setX() {
    let formData = new FormData(document.getElementById("coordinates-form"));
    X = formData.get("x");
}

function setR() {
    R = $("input[name=r]:checked").val();
}

function calculateX(x, r) {
    return x / r * 100 + GRAPH_WIDTH / 2;
}

function calculateY(y, r) {
    return GRAPH_HEIGHT / 2 - y / r * 100;
}

function doRequest(x, y, r) {
    let request = ("check?x=" + x + "&y=" + y + "&r=" + r);
    fetch(request)
        .then(response => response.text())
        .then(response => addToTable(response));
}

const submit = function (e) {
    if (!checkY()) return;
    setX();
    setR();
    e.preventDefault();

    let point = $("#point");
    doRequest(X, Y, R)
    const xGraph = calculateX(X, R), yGraph = calculateY(Y, R);

    point.attr({
        cx: xGraph,
        cy: yGraph,
        visibility: "visible"
    });


};

$('svg').mousedown(function (e) {
    const position = $('#svg-wrapper').offset();
    const rowX = e.pageX - position.left;
    const rowY = e.pageY - position.top;

    const x = (((R / 50) * (SVG_SIZE / 2 - rowX) * -1) / 2).toFixed(2);
    const y = (((R / 50) * (SVG_SIZE / 2 - rowY)) / 2).toFixed(2);

    doRequest(x, y, R);

})

function addToTable(response) {
    let shotSound = new Audio('audio/bum.mp3');
    shotSound.play();
    response = JSON.parse(response);
    $('#time').text("Время на сервере: " + response['currentTime']
        + "  Скрипт отработал за: " + response['time'] + "ms");
    $("#check").find('tbody')
        .append($('<tr>')
            .append($('<td>')
                .text(response['r'])
            )
            .append($('<td>')
                .text(response['x'])
            )
            .append($('<td>')
                .text(response['y'])
            )
            .append($('<td style=\"background-color: ' +
                (response['atArea'] === "Попадание" ? "green;" : "red;") +
                "\">")
                .text(response['atArea'])
            )
        );
    let x = ((300 / 2 + (response.x) / response.r * 100));
    let y = ((300 / 2 + (response.y) / response.r * -100));
    let r = response['r'];
    let color = response['atArea'] === "Попадание" ? "green" : "red";

    function makeSVG(tag, attrs) {
        var el = document.createElementNS('http://www.w3.org/2000/svg', tag);
        for (var k in attrs)
            el.setAttribute(k, attrs[k]);
        return el;
    }

    var circle = makeSVG('circle', {cx: x, cy: y, r: 3.5, fill: color});

    document.getElementById('svg').appendChild(circle);

}

const clearButton = function (e) {
    e.preventDefault();
    $("#check  tr").remove();
    $("#check").find('tbody')
        .append($('<tr class="table-header">')
            .append($('<th scope="col">')
                .text("R")
            )
            .append($('<th scope="col">')
                .text("X")
            )
            .append($('<th scope="col">')
                .text("Y")
            )
            .append($('<th scope="col">')
                .text("Результат")
            )
        );
    while (document.getElementById('svg').lastChild.tagName === "circle") {
        document.getElementById('svg').removeChild(document.getElementById('svg').lastChild);
    }
    let request = ("clear");
    fetch(request)
        .then(response => response.text());
};

function changePoint() {
    let point = $("#point");
    let formData = new FormData(document.getElementById("coordinates-form"));
    X = formData.get("x").replace(",", ".");
    Y = formData.get("y").replace(",", ".");
    R = formData.get("r").replace(",", ".");
    const xGraph = calculateX(X, R), yGraph = calculateY(Y, R);

    point.attr({
        cx: xGraph,
        cy: yGraph,
        visibility: "visible"
    });
}

$("input:checkbox").click(function () {
    let group = "input:checkbox[name='" + $(this).prop("name") + "']";
    $(group).prop("checked", false);
    $(this).prop("checked", true);
}).on("change", e => {
    changePoint();
});

yTextField.addEventListener("change", e => {
    if (checkY()) {
        changePoint();
    }
});

var numberChecker = function (e) {
    let input = e.target.value;
    var dotsCount = 0;
    for (let i = 0; i < input.length; i++) if (input[i] === ".") dotsCount++;
    let valid = (input.match(/^-?\d*(\.\d+)?$/));
    if (valid || !valid && dotsCount === 1 && input[input.length - 1] === ".") {
        document.getElementById('Y-text').style.borderColor = "";
        return true;
    } else {
        document.getElementById('Y-text').style.borderColor = "red";
        return false;
    }
}
document.addEventListener("DOMContentLoaded", function () {
    var inputs = document.querySelectorAll('.Y-text-input');
    for (var phoneInput of inputs) {
        phoneInput.addEventListener('input', numberChecker, false);
    }
})