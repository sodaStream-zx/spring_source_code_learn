function test() {
    alert("hello js");
    console.info("hello js");
}

function loadXMLDoc() {
    var theUrl = document.getElementById("url").value;
    var theMethod = document.getElementById("method").value;
    var xmlhttp;
    if (window.XMLHttpRequest) {
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp = new XMLHttpRequest();
    } else {
        // IE6, IE5 浏览器执行代码
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var responseText = xmlhttp.responseText;
            var elementById = document.getElementById("thepage");
            elementById.innerText = responseText;
        }
    };
    xmlhttp.open(theMethod, theUrl, true);
    xmlhttp.send();
}