
var url = "http://localhost:8080/Web";
var claveRecuperacion = "";

function getEstadoSolicitud() {
    var estado = 0;
    var respuesta = "";
    claveRecuperacion = document.getElementById("codigoSolicitudInput").value;
    let request = new Request(url + '/API/solicitud/consultar-estado-solicitud?claveRecuperacion=' + claveRecuperacion,
            {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (response.ok) {
            console.log(response);
        } else {
            return;
        }
        estado = await response.json();
        console.log("Estado:");
        console.log(estado);
        switch (estado) {
            case 1:
                respuesta = "En proceso de revisión";
                break;
            case 2:
                respuesta = "Aceptada";
                break;
            case 3:
                respuesta = "Denegada";
                break;
            default:
                respuesta = "No existe una solicitud asociada al código ingresado";
        }
        ;
        swal("El estado de su solicitud es:", respuesta).then(function () {
            window.location = "ConsultarEstadoSolicitud.html";
        });
    })();
}

function checkForm(val) {
    // CHECK IF ALL "REQUIRED" FIELD ALL FILLED IN
    var valid = true;
    $("#" + val + " input:required").each(function () {
        if ($(this).val() === "") {
            $(this).addClass("is-invalid");
            valid = false;
        } else {
            $(this).removeClass("is-invalid");
        }
    });
    return valid;
}

function consultarEstadoSolicitud() {

    $("#consultarEstadoSolicitudBtn").click(getEstadoSolicitud);
}

$(consultarEstadoSolicitud);