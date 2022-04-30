var url = "http://localhost:8080/Web";
var solicitudesBecasAcademicas = new Array();

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

var estudiante = {
    cedula: "",
    nombre: "",
    primerApellido: "",
    segundoApellido: "",
    fechaNacimiento: "",
    edad: "",
    gradoAcademico: ""
};

var direccion = {
    distrito: "",
    barrio: "",
    direccionExacta: ""
};

var becaAcademica = {
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
    direccion.distrito = document.getElementById("distritoInput").value;
    direccion.barrio = document.getElementById("barrioInput").value;
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
            $('#dataTable').DataTable({
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
                    {"defaultContent": '<button id="a">Modificar</button>'}
                    ,
                    {"defaultContent": '<button id="b">Historial</button>'
                    }
                ]
            });
//            $('#example tbody').on('click', '#a', function () {
//                $('#add-modal-ayudas-temporales').modal('show');
//                var data = table.row($(this).parents('tr')).data();
//                console.log(data['idSolicitante']);
//                consultarCedula(data['idSolicitante']);
//            });

//            $('#example tbody').on('click', '#b', function () {
//                $('#add-modal-ayudas-temporales-por-usuario').modal('show');
//                var data = table.row($(this).parents('tr')).data();
//                console.log(data['idSolicitante']);
//                consultarAyudasTemporalesPorUsuario(data['idSolicitante']);
//            });
        });
    }
    )();
}

function list() {
    $("#listaAyudasPorSolicitante").html("");
    becasAcademicas.forEach((at) => {
        row2($("#listaAyudasPorSolicitante"), at);
    });
}

function row(listado, becaAcademica) {
    var tr = $("<tr />");
    tr.html(
            "<td>" + becaAcademica.idFormulario + "</td>" +
            "<td>" + becaAcademica.idEstudiante + "</td>" +
            "<td>" + becaAcademica.idEstado + "</td>");
    listado.append(tr);
    document.getElementById('nombreSolicitanteTitle').innerHTML = 'Historial de becas académicas del habitante ' + becaAcademica.idFormulario;
}

function crear() {
    $(agregarSolicitud);
}

function cargar() {
    fetchAndList();
    $("#crearFormularioBABtn").click(crear);
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