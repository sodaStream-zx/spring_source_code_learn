function test() {
    alert("hello js");
    console.info("hello js");
}

function getAjax() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp = new XMLHttpRequest();
    } else {
        // IE6, IE5 浏览器执行代码
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

function loadXMLDoc() {
    var theUrl = document.getElementById("url").value;
    var theMethod = document.getElementById("method").value;
    var ajax = getAjax();
    ajax.onreadystatechange = function () {
        if (ajax.readyState === 4 && ajax.status === 200) {
            var responseText = ajax.responseText;
            var elementById = document.getElementById("thepage");
            var parse = JSON.parse(responseText);
            var counter = parse.data.counter;
            var str = '';
            for (var x in counter) {
                str = str + x + ":" + counter[x] + "\n";
            }
            elementById.innerText = str;
        }
    };
    ajax.open(theMethod, theUrl, true);
    ajax.send();
}