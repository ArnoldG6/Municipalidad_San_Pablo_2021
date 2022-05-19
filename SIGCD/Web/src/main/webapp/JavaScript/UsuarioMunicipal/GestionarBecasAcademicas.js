var url = "http://localhost:8080/Web";
var becasAcademicas = new Array();
var solicitudesBecasAcademicas = new Array();
var idPersonaAux, idDireccionAux, idAyudaTemporalAux, idEstudianteAux;
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

var estudiante = {
    idPersona: "",
    cedula: "",
    nombre: "",
    primerApellido: "",
    segundoApellido: "",
    fechaNacimiento: "",
    edad: "",
    gradoAcademico: ""
};

var direccion = {
    idDireccion: "",
    distrito: "",
    barrio: "",
    direccionExacta: ""
};

var becaAcademica = {
    idFormulario: "",
    idSolicitante: "",
    fechaCreacion: "",
    claveRecuperacion: "",
    motivoAyida: "",
    idEstudiante: "",
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

function crearEstudiante() {
    var estudiante = new Object();
    estudiante.cedula = document.getElementById("cedulaEstInput").value;
    estudiante.nombre = document.getElementById("nombreEstInput").value;
    estudiante.primerApellido = document.getElementById("primerApellidoEstInput").value;
    estudiante.segundoApellido = document.getElementById("segundoApellidoEstInput").value;
    estudiante.fechaNacimiento = document.getElementById("fechaNacimientoEstInput").value;
    estudiante.edad = document.getElementById("edadEstInput").value;
    estudiante.gradoAcademico = document.getElementById("gradoAcademicoEstSelect").value;
    return estudiante;
}

function crearBecaAcademica() {
    var becaAcademica = new Object();
    becaAcademica.idSolicitante = document.getElementById("cedulaEstInput").value;
    becaAcademica.fechaCreacion = new Date();
    becaAcademica.claveRecuperacion = generarClaveRecuperacion();
    becaAcademica.idEstudiante = document.getElementById("cedulaEstInput").value;
    becaAcademica.idDireccion = 0;
    becaAcademica.idEstado = 1;
    return becaAcademica;
}

function crearSolicitudBA() {
    var solicitud = new Object();
    solicitud.direccion = crearDireccion();
    solicitud.solicitante = crearSolicitante();
    solicitud.estudiante = crearEstudiante();
    solicitud.becaAcademica = crearBecaAcademica();
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

    estudiante = {
        cedula: "",
        nombre: "",
        primerApellido: "",
        segundoApellido: "",
        fechaNacimiento: "",
        edad: "",
        gradoAcademico: ""
    };

    direccion = {
        distrito: "",
        barrio: "",
        direccionExacta: ""
    };

    becaAcademica = {
        idSolicitante: "",
        fechaCreacion: "",
        claveRecuperacion: "",
        motivoAyida: "",
        idEstudiante: "",
        idDireccion: "",
        idEstado: ""
    };
}

function agregarSolicitud() {
    var object = new Object();
    object = crearSolicitudBA();
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
                            url + '/API/solicitud/beca_academica', {
                                method: 'POST', headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify(object)
                            }
                    );
                    (async () => {
                        const response = await fetch(request);
                        if (response.ok) {
                            var clave = object.becaAcademica.claveRecuperacion;
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
    object = crearSolicitudBA();
    object.solicitante.idPersona = idPersonaAux;
    object.direccion.idDireccion = idDireccionAux;
    object.estudiante.idPersona = idEstudianteAux;
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
                            url + '/API/solicitud/beca_academica/actualizar', {
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

function rellenarFormulario(solicitante, direccion, estudiante) {
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
    document.getElementById("cedulaEstInput").value = estudiante.cedula;
    document.getElementById("nombreEstInput").value = estudiante.nombre;
    document.getElementById("primerApellidoEstInput").value = estudiante.primerApellido;
    document.getElementById("segundoApellidoEstInput").value = estudiante.segundoApellido;
    document.getElementById("fechaNacimientoEstInput").value = estudiante.fechaNacimiento;
    document.getElementById("edadEstInput").value = estudiante.edad;
    document.getElementById("gradoAcademicoEstSelect").value = estudiante.gradoAcademico;
}

function fetchAndList() {
    let request = new Request(url + '/API/solicitud/all-solicitudes-becas-academicas', {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        solicitudesBecasAcademicas = await response.json();
        var dataSet = solicitudesBecasAcademicas;
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
                    },
                    {
                        extend: 'pdfHtml5',
                        exportOptions: {
                            columns: [0, 1, 2, 3, 4, 5]
                        }
                    },
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
                    {"data": 'becaAcademica.idFormulario'}
                    ,
                    {"data": 'solicitante.cedula'}
                    ,
                    {"data": 'solicitante.nombre'}
                    ,
                    {"data": 'estudiante.cedula'}
                    ,
                    {"data": 'estudiante.nombre'}
                    ,
                    {"data": 'becaAcademica.fechaCreacion'}
                    ,
                    {"data": 'direccion.direccionExacta'}
                    ,
                    {"data": 'becaAcademica.claveRecuperacion'}
                    ,
                    {"data": 'becaAcademica.idEstado'}
                    ,
                    {"defaultContent": '<input type="image" id="a" alt="Login" src="Imagenes/Edit.png">'}
                    ,
                    {"defaultContent": '<input type="image" id="b" alt="Login" src="Imagenes/History.png">'
                    }
                ]
            });
            $('#dataTable tbody').on('click', '#a', function () {
                var data = table.row(($(this).parents('tr'))).data();
                rellenarFormulario(data['solicitante'], data['direccion'], data['estudiante']);
                idPersonaAux = data['solicitante'].idPersona;
                idDireccionAux = data['direccion'].idDireccion;
                idEstudianteAux = data['estudiante'].idPersona;
                console.log(idPersonaAux);
                console.log(idDireccionAux);
                console.log(idEstudianteAux);
                $('#add-modal-becas-academicas').modal('show');
            });

            $('#dataTable tbody').on('click', '#b', function () {
                var data = table.row(($(this).parents('tr'))).data();
                consultarBecasAcademicasPorUsuario(data['becaAcademica'].idSolicitante, data['solicitante'].cedula);
                $('#add-modal-becas-academicas-por-usuario').modal('show');
            });
        });
    }
    )();
}

function consultarBecasAcademicasPorUsuario(id, cedulaSolicitante) {
    let request = new Request(url + '/API/solicitud/select_all_becasAcademicas_por_solicitante?id=' + id,
            {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        becasAcademicas = await response.json();
        console.log(becasAcademicas);
        list(cedulaSolicitante);
    })();
}

function list(cedulaSolicitante) {
    $("#listaBecasPorSolicitante").html("");
    becasAcademicas.forEach((ba) => {
        row($("#listaBecasPorSolicitante"), ba, cedulaSolicitante);
    });
}

function row(listado, becaAcademica, cedulaSolicitante) {
    var tr = $("<tr />");
    tr.html(
            "<td>" + becaAcademica.idFormulario + "</td>" +
            "<td>" + becaAcademica.fechaCreacion + "</td>" +
            "<td>" + becaAcademica.idEstado + "</td>");
    listado.append(tr);
    document.getElementById('nombreSolicitanteTitle').innerHTML = 'Historial de becas académicas del habitante ' + cedulaSolicitante;
}

function crear() {
    $(agregarSolicitud);
}

function actualizar() {
    $(actualizarSolicitud);
}

function cargar() {
    fetchAndList();
    $("#crearFormularioBABtn").click(crear);
    $("#actualizarFormularioBABtn").click(actualizar);
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

function generarClaveRecuperacion() {
    var characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    var clave = "";
    for (i = 0; i < 6; i++) {
        clave += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return "BA" + clave;
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

$(function () {
    $('input[name="fechaNacimientoEst"]').daterangepicker({
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
        if (years < 18) {
            document.getElementById("edadEstInput").value = years;
        } else {
            document.getElementById("edadEstInput").value = '';
        }
    });
});