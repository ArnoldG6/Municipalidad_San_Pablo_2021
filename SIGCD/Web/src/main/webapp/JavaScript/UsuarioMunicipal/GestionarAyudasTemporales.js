var url = "http://localhost:8080/Web";
var ayudasTemporales = new Array();
var solicitudesAyudasTemporales = new Array();
var idPersonaAux, idDireccionAux, idAyudaTemporalAux;
var lookup = {
    'San Pablo': ['Las Cruces', 'Las Joyas', 'María Auxiliadora', 'La Puebla', 'Las Quintanas', 'Uriche', 'La Amelia', 'Las Pastoras'],
    'Rincon Sabanilla': ['Rincón de Ricardo', 'Miraflores', 'Calle Cordero', 'Rinconada']
};

$('#options').on('change', function () {
    var selectValue = $(this).val();
    $('#choices').empty();
    for (i = 0; i < lookup[selectValue].length; i++) {
        $('#choices').append("<option value='" + lookup[selectValue][i] + "'>" + lookup[selectValue][i] + "</option>");
    }
});

var solicitante = {
    idPersona: "",
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
    idDireccion: "",
    distrito: "",
    barrio: "",
    direccionExacta: ""
};

var ayudaTemporal = {
    idFormulario: "",
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
    ayudaTemporal.idSolicitante = document.getElementById("cedulaInput").value;
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

function actualizarSolicitud() {
    var object = new Object();
    object = crearSolicitudAT();
    object.solicitante.idPersona = idPersonaAux;
    object.direccion.idDireccion = idDireccionAux;
    object.ayudaTemporal.idFormulario = idAyudaTemporalAux;
    console.log(object);
    swal({
        title: "¿Desea actualizar la solicitud?",
        text: "¡Le recomendamos revisar la información antes de enviarla!",
        icon: "warning",
        buttons: true,
        dangerMode: true
    })
            .then((accepted) => {
                if (accepted) {
                    let request = new Request(
                            url + '/API/solicitud/ayuda_temporal/actualizar', {
                                method: 'POST', headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify(object)
                            }
                    );
                    (async () => {
                        const response = await fetch(request);
                        if (response.ok) {
                            console.log(response);
                            var clave = object.ayudaTemporal.claveRecuperacion;
                            swal("¡La solicitud ha sido actualizada!", {
                                icon: "success"}).then(function () {
                                window.location = "GestionarAyudasTemporales.html";
                            });
                            ;
                        }
                        if (!response.ok) {
                            console.log(response);
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

function rellenarFormulario(solicitante, direccion, motivo) {
    document.getElementById("cedulaInput").value = solicitante.cedula;
    document.getElementById("nombreInput").value = solicitante.nombre;
    document.getElementById("primerApellidoInput").value = solicitante.primerApellido;
    document.getElementById("segundoApellidoInput").value = solicitante.segundoApellido;
    document.getElementById("fechaNacimientoInput").value = solicitante.fechaNacimiento;
    document.getElementById("edadInput").value = solicitante.edad;
    document.getElementById("telefonoHabitacionInput").value = solicitante.telefonoHabitacion;
    document.getElementById("telefonoCelularInput").value = solicitante.telefonoCelular;
    document.getElementById("correoElectronicoInput").value = solicitante.correoElectronico;
    document.getElementById("options").value = direccion.distrito;
    document.getElementById("choices").value = direccion.barrio;
    document.getElementById("direccionExactaInput").value = direccion.direccionExacta;
    document.getElementById("motivoAyudaInput").value = motivo;
}

function fetchAndList() {
    let request = new Request(url + '/API/solicitud/all-solicitudes-ayudas-temporales', {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        solicitudesAyudasTemporales = await response.json();
        var dataSet = solicitudesAyudasTemporales;
        console.log(dataSet);
        $(document).ready(function () {
            var table = $('#dataTable').DataTable({
                language: {
                    "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
                },
                scrollY: "500px",
                scrollX: true,
                scrollCollapse: true,
                columnDefs: [
                    {width: 200, targets: 0}
                ],
                fixedColumns: true,

                dom: 'Blfrtip',
                buttons: [
                    {
                        extend: 'excelHtml5',
                        exportOptions: {
                            columns: ':visible'
                        }
                    }
                    ,
                    {
                        extend: 'pdfHtml5',
                        exportOptions: {
                            columns: [0, 1, 2, 3, 4, 5]
                        }
                    }
                    ,
                    {
                        extend: 'copy',
                        text: "Copiar"
                    }
                    ,
                    {
                        extend: 'print',
                        text: "Imprimir"
                    }
                    ,
                    {
                        extend: 'colvis',
                        text: "Filtro columna"}
                ],

                "data": dataSet,
                "columns": [
                    {"data": 'ayudaTemporal.idFormulario'}
                    ,
                    {"data": 'solicitante.cedula'}
                    ,
                    {"data": 'solicitante.nombre'}
                    ,
                    {"data": 'ayudaTemporal.motivoAyuda'}
                    ,
                    {"data": 'ayudaTemporal.fechaCreacion'}
                    ,
                    {"data": 'direccion.direccionExacta'}
                    ,
                    {"data": 'ayudaTemporal.claveRecuperacion'}
                    ,
                    {"data": 'ayudaTemporal.idEstado'}
                    ,
                    {"defaultContent": '<input type="image" id="a" src="Imagenes/Edit.png">'}
                    ,
                    {"defaultContent": '<input type="image" id="b" src="Imagenes/History.png">'
                    }
                ]
            });

            $('#dataTable tbody').on('click', '#a', function () {
                var data = table.row(($(this).parents('tr'))).data();
                rellenarFormulario(data['solicitante'], data['direccion'], data['ayudaTemporal'].motivoAyuda);
                idPersonaAux = data['solicitante'].idPersona;
                idDireccionAux = data['direccion'].idDireccion;
                idAyudaTemporalAux = data['ayudaTemporal'].idFormulario;
                console.log(idPersonaAux);
                console.log(idDireccionAux);
                console.log(idAyudaTemporalAux);
                $('#add-modal-ayudas-temporales').modal('show');
            });

            $('#dataTable tbody').on('click', '#b', function () {
                var data = table.row(($(this).parents('tr'))).data();
                consultarAyudasTemporalesPorUsuario(data['ayudaTemporal'].idSolicitante, data['solicitante'].cedula);
                $('#add-modal-ayudas-temporales-por-usuario').modal('show');
            });
        });
    }
    )();
}

$('#consultarCedulaBtn').on('click', function () {
    consultarCedula();
});

function consultarAyudasTemporalesPorUsuario(id, cedulaSolicitante) {
    let request = new Request(url + '/API/solicitud/select_all_ayudasTemporales_por_solicitante?id=' + id,
            {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        ayudasTemporales = await response.json();
        console.log(ayudasTemporales);
        list(cedulaSolicitante);
    })();
}

function list(cedulaSolicitante) {
    $("#listaAyudasPorSolicitante").html("");
    ayudasTemporales.forEach((at) => {
        row($("#listaAyudasPorSolicitante"), at, cedulaSolicitante);
    });
}

function row(listado, ayudaTemporal, cedulaSolicitante) {
    var tr = $("<tr />");
    tr.html(
            "<td>" + ayudaTemporal.idFormulario + "</td>" +
            "<td>" + ayudaTemporal.motivoAyuda + "</td>" +
            "<td>" + ayudaTemporal.idEstado + "</td>");
    listado.append(tr);
    document.getElementById('nombreSolicitanteTitle').innerHTML = 'Historial de ayudas temporales del habitante ' + cedulaSolicitante;
}

function crear() {
    $(agregarSolicitud);
}

function actualizar() {
    $(actualizarSolicitud);
}

function cargar() {
    fetchAndList();
    $("#crearFormularioATBtn").click(crear);
    $("#actualizarFormularioATBtn").click(actualizar);
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
            "format": "MM/DD/YYYY",
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