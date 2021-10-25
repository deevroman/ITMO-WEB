let X, Y, R;
const GRAPH_WIDTH = 300;
const GRAPH_HEIGHT = 300;
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
    } else if (Y > 3 || Y < -5) {
        Y_text.setCustomValidity("Вы вышли за диапазон [-5; 3]!");
        Y_text.reportValidity();
        return false;
    } else {
        let dot_pos = Y.indexOf(".");
        if(Y.substr(0, dot_pos) == -5 || Y.substr(0, dot_pos) == 3){
            Y_text.setCustomValidity("Вы вышли за диапазон [-5; 3]!");
            document.getElementById('Y-text').style.borderColor = "red";
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

const submit = function (e) {
    if (!checkY()) return;
    setX();
    setR();
    e.preventDefault();

    let point = $("#point");
    let request = ("?x=" + X + "&y=" + Y + "&r=" + R);
    const xGraph = calculateX(X, R), yGraph = calculateY(Y, R);

    point.attr({
        cx: xGraph,
        cy: yGraph,
        visibility: "visible"
    });

    fetch("api/check.php" + request)
        .then(response => response.text())
        .then(response => addToTable(response));
};


function addToTable(response) {
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
    if (valid || !valid && dotsCount === 1 && input[input.length-1] === ".") {
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