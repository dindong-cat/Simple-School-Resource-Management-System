/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
let confirmPasswordMustSame = function() {
    if (document.getElementById("password").value != document.getElementById("confirmPassword").value) {
        document.getElementById("warningMsg").innerHTML = "The 2 passwords are different!";
        document.getElementById("warningMsg").style.color = "red";
        document.getElementById("submit").disabled = true;
        document.getElementById("submit").style.background = "gray";
    } else {
        document.getElementById("warningMsg").innerHTML = "";
        document.getElementById("submit").disabled = false;
        document.getElementById("submit").style.background = "#0A2558";
    }
}

