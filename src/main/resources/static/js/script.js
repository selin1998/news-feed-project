function disfunction(button_id) {



    var n = button_id.toString();
    var btn = document.getElementById(n);

    if (btn.value == "dismiss") {
        btn.value = "enable";
        btn.innerText = "enable";
    }
    else {
        btn.value = "dismiss";
        btn.innerText = "dismiss";

    }

}