function disfunction(button_id) {



    var n = button_id.toString();
    var btn = document.getElementById(n);

    if (btn.name == "dismiss") {
        btn.name = "enable";
        btn.innerText = "enable";
    }
    else {
        btn.name = "dismiss";
        btn.innerText = "dismiss";

    }

}