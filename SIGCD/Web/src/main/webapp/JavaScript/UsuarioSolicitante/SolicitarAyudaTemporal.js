var url = "http://localhost:8080/Web";

var lookup = {
    'San Pablo': ['Las Cruces', 'Las Joyas', 'María Auxiliadora', 'La Puebla', 'Las Quintanas', 'Uriche', 'La Amelia', 'Las Pastoras'],
    'Rincon Sabanilla': ['Rincón de Ricardo', 'Miraflores', 'Calle Cordero', 'Rinconada'],
};

$('#options').on('change', function () {
    var selectValue = $(this).val();
    $('#choices').empty();
    for (i = 0; i < lookup[selectValue].length; i++) {
        $('#choices').append("<option value='" + lookup[selectValue][i] + "'>" + lookup[selectValue][i] + "</option>");
    }
});

var solicitante = {
    cedula: "",
    nombre: "",
    primerApellido: "",
    segundoApellido: "",
    fechaNacimiento: "",
    edad: "",
    telefonoHabitacion: "",
    telefonoCelular: "",
    correoElectronico: ""
};

var direccion = {
    distrito: "",
    barrio: "",
    direccionExacta: ""
};

var ayudaTemporal = {
    idSolicitante: "",
    fechaCreacion: "",
    claveRecuperacion: "",
    motivoAyida: "",
    idDireccion: "",
    idEstado: ""
};

function crearDireccion() {
    var direccion = new Object();
    direccion.distrito = document.getElementById("options").value;
    direccion.barrio = document.getElementById("choices").value;
    direccion.direccionExacta = document.getElementById("direccionExactaInput").value;
    return direccion;
}

function crearSolicitante() {
    var solicitante = new Object();
    solicitante.cedula = document.getElementById("cedulaInput").value;
    solicitante.nombre = document.getElementById("nombreInput").value;
    solicitante.primerApellido = document.getElementById("primerApellidoInput").value;
    solicitante.segundoApellido = document.getElementById("segundoApellidoInput").value;
    solicitante.fechaNacimiento = document.getElementById("fechaNacimientoInput").value;
    solicitante.edad = document.getElementById("edadInput").value;
    solicitante.telefonoHabitacion = document.getElementById("telefonoHabitacionInput").value;
    solicitante.telefonoCelular = document.getElementById("telefonoCelularInput").value;
    solicitante.correoElectronico = document.getElementById("correoElectronicoInput").value;
    return solicitante;
}

function crearAyudaTemporal() {
    var ayudaTemporal = new Object();
    ayudaTemporal.idSolicitante = 0;
    ayudaTemporal.fechaCreacion = new Date();
    ayudaTemporal.claveRecuperacion = generarClaveRecuperacion();
    ayudaTemporal.motivoAyuda = document.getElementById("motivoAyudaInput").value;
    ayudaTemporal.idDireccion = 0;
    ayudaTemporal.idEstado = 1;
    return ayudaTemporal;
}

function crearSolicitudAT() {
    var solicitud = new Object();
    solicitud.direccion = crearDireccion();
    solicitud.solicitante = crearSolicitante();
    solicitud.ayudaTemporal = crearAyudaTemporal();
    return solicitud;
}

function limpiarObjetos() {
    solicitante = {
        cedula: "",
        nombre: "",
        primerApellido: "",
        segundoApellido: "",
        fechaNacimiento: "",
        edad: "",
        telefonoHabitacion: "",
        telefonoCelular: "",
        correoElectronico: ""
    };

    direccion = {
        distrito: "",
        barrio: "",
        direccionExacta: ""
    };

    ayudaTemporal = {
        idSolicitante: "",
        fechaCreacion: "",
        claveRecuperacion: "",
        motivoAyida: "",
        idDireccion: "",
        idEstado: ""
    };
}

function agregarSolicitud() {
    var object = new Object();
    object = crearSolicitudAT();
    swal({
        title: "¿Desea enviar su solicitud?",
        text: "¡Le recomendamos revisar la información antes de enviarla!",
        icon: "warning",
        buttons: true,
        dangerMode: true
    })
            .then((accepted) => {
                if (accepted) {
                    let request = new Request(
                            url + '/API/solicitud/ayuda_temporal', {
                                method: 'POST', headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify(object)
                            }
                    );
                    (async () => {
                        const response = await fetch(request);
                        if (response.ok) {
                            var clave = object.ayudaTemporal.claveRecuperacion;
                            swal("¡Su solicitud ha sido enviada!", "Guarde el siguiente código de referencia: " + clave, {
                                icon: "success"}).then(function () {
                                window.location = "index.html";
                            });
                            ;
                        }
                        if (!response.ok) {
                            console.log("Response incorrectly received");
                            return;
                        }
                        limpiarObjetos();
                    })();
                } else {
                    swal("Solicitud cancelada");
                }
            });
}

function modificarSolicitud() {
    var object = new Object();
    object = crearSolicitudAT();
    swal({
        title: "¿Desea enviar su solicitud?",
        text: "¡Le recomendamos revisar la información antes de enviarla!",
        icon: "warning",
        buttons: true,
        dangerMode: true
    })
            .then((accepted) => {
                if (accepted) {
                    let request = new Request(
                            url + '/API/solicitud/ayuda_temporal', {
                                method: 'UPDATE', headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify(object)
                            }
                    );
                    (async () => {
                        const response = await fetch(request);
                        if (response.ok) {
                            var clave = object.ayudaTemporal.claveRecuperacion;
                            swal("¡Su solicitud ha sido enviada!", "Guarde el siguiente código de referencia: " + clave, {
                                icon: "success"}).then(function () {
                                window.location = "index.html";
                            });
                            ;
                        }
                        if (!response.ok) {
                            console.log("Response incorrectly received");
                            return;
                        }
                        limpiarObjetos();
                    })();
                } else {
                    swal("Solicitud cancelada");
                }
            });
}

$('#consultarCedulaBtn').on('click', function () {
    consultarCedula();
});

function consultarCedula() {
    var ced = document.getElementById("cedulaInput").value;
    let request = new Request(url + '/API/solicitud/consultar_cedula?id=' + ced, {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        solicitante = await response.json();
        rellenarInformacionPersonal(solicitante);
    })();
}

function rellenarInformacionPersonal(solicitante) {
    document.getElementById("nombreInput").value = solicitante['value'].nombre;
    document.getElementById("primerApellidoInput").value = solicitante['value'].primerApellido;
    document.getElementById("segundoApellidoInput").value = solicitante['value'].segundoApellido;
    document.getElementById("fechaNacimientoInput").value = solicitante['value'].fechaNacimiento;
    document.getElementById("edadInput").value = solicitante['value'].edad;
}

function crear() {
    $(agregarSolicitud);
}

function cargar() {
    $("#crearFormularioATBtn").click(crear);
}

$(cargar);

//------------------------------------------------------------

var step = 1;
$(document).ready(function () {
    pasoProgreso(step);
});
$(".next").prop("disabled", false);
$(".next").on("click", function () {
    var siguientePaso = false;
    if (step === 1) {
        siguientePaso = validarFormulario("datosPersonales");
    } else if (step === 2) {
        siguientePaso = validarFormulario("datosContacto");
    } else if (step === 3) {
        siguientePaso = validarFormulario("datosDireccion");
    } else if (step === 4) {
        siguientePaso = validarTextArea("datosMotivo");
    } else {
        siguientePaso = true;
    }
    if (siguientePaso === true) {
        if (step < $(".step").length) {
            $(".step").show();
            $(".step")
                    .not(":eq(" + step++ + ")")
                    .hide();
            pasoProgreso(step);
        }
        ocultarBotones(step);
    }
});

$(".back").on("click", function () {
    if (step > 1) {
        step = step - 2;
        $(".next").trigger("click");
    }
    ocultarBotones(step);
});

pasoProgreso = function (currstep) {
    var percent = parseFloat(100 / $(".step").length) * (currstep);
    percent = percent.toFixed();
    $(".progress-bar")
            .css("width", percent + "%")
            .html(percent + "%");
};


ocultarBotones = function (step) {
    var limit = parseInt($(".step").length);
    $(".action").hide();
    if (step < limit) {
        $(".next").show();
    }
    if (step > 1) {
        $(".back").show();
    }
    if (step === limit) {
        $(".next").hide();
        $(".submit").show();
    }
};

function validarFormulario(val) {
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

function validarTextArea(val) {
    var valid = true;
    $("#" + val + " textarea:required").each(function () {
        if ($(this).val() === "") {
            $(this).addClass("is-invalid");
            valid = false;
        } else {
            $(this).removeClass("is-invalid");
        }
    });
    return valid;
}

function generarClaveRecuperacion() {
    var characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    var clave = "";
    for (i = 0; i < 6; i++) {
        clave += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return "AT" + clave;
}

$(function () {
    $('input[name="fechaNacimientoInput"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        minYear: 1901,
        maxYear: (parseInt(moment().format('YYYY'), 10) + 1),
        "locale": {
            "format": "DD/MM/YYYY",
            "separator": " - ",
            "applyLabel": "Aplicar",
            "cancelLabel": "Cancelar",
            "daysOfWeek": [
                "Do",
                "Lu",
                "Ma",
                "Mi",
                "Ju",
                "Vi",
                "Sa"
            ],
            "monthNames": [
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Agosto",
                "Setiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            ],
            "firstDay": 0
        }
    }, function (start) {
        var years = moment().diff(start, 'years');
        if (years >= 18) {
            document.getElementById("edadInput").value = years;
        } else {
            document.getElementById("edadInput").value = '';
        }
    });
});

const intro = introJS();

intro.setOptions({

    steps: [
        {
            intro: 'Bienvenidos'
        },
        {
            element: '#consultarCedulaBtn',
            intro: 'Presioname'
        }
    ]
});

intro.start();