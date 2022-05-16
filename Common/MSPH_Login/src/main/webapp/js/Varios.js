/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var cont = 0;
function calcularAnios() {
    if (cont === 0) {
        var a = new Date(document.getElementById("inicio").value);
        var b = new Date(document.getElementById("final").value);
        var difference = Math.floor(a - b);
        var day = (1000 * 60 * 60 * 24);
        var days = Math.floor(difference / day);
        var months = Math.floor(days / 31);
        var years = Math.floor(months / 12);
        cont = Math.abs(years);
        document.getElementById("aniosLaborados").value = cont;
    } else {
        var a = new Date(document.getElementById("inicio").value);
        var b = new Date(document.getElementById("final").value);
        var difference = Math.floor(a - b);
        var day = (1000 * 60 * 60 * 24);
        var days = Math.floor(difference / day);
        var months = Math.floor(days / 31);
        var years = Math.floor(months / 12);
        cont = cont + Math.abs(years);
        document.getElementById("aniosLaborados").value = cont;
    }
}

function limpiar() {
    document.getElementById("aniosLaborados").value = "0";
    cont = 0;
}