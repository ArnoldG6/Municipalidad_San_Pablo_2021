var url = "http://localhost:8080/Web";
var ayudasTemporales = new Array();
var solicitudesAyudasTemporales = new Array();
var solicitudesBecasAcademicas = new Array();
var barriosDistritoSanPablo = ["Las Cruces", "Las Joyas", "María Auxiliadora", "La Puebla", "Las Quintanas", "Uriche", "La Amelia", "Las Pastoras"];
var barriosDistritoRinconSabanilla = ["Rincón de Ricardo", "Miraflores", "Calle Cordero", "Rinconada"];
var distritos = ["San Pablo", "Rincon Sabanilla"];

//Select de direccion
function cargarDistritos() {
    var opciones = document.getElementById("distritoSelect");
    var text = "";
    distritos.forEach(d => {
        text += `<option>${d}</option>`;
    });
    opciones.innerHTML = text;
}

function cargarBarrios() {
    var dis = document.getElementById("distritoSelect").value.replace(/ /g, " ");
    var barrios = document.getElementById("barrioSelect");
    var text = "";
    dis === "San Pablo" ?
            barriosDistritoSanPablo.forEach(b => {
                text += `<option>${b}</option>`;
            }) :
            barriosDistritoRinconSabanilla.forEach(b => {
                text += `<option>${b}</option>`;
            });
    console.log(dis);
    barrios.innerHTML = text;
}

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
    direccion.distrito = document.getElementById("distrito").value;
    direccion.barrio = document.getElementById("barrio").value;
    direccion.direccionExacta = document.getElementById("direccionExacta").value;
    return direccion;
}

function crearSolicitante() {
    var solicitante = new Object();
    solicitante.cedula = document.getElementById("cedula").value;
    solicitante.nombre = document.getElementById("nombre").value;
    solicitante.primerApellido = document.getElementById("primerApellido").value;
    solicitante.segundoApellido = document.getElementById("segundoApellido").value;
    solicitante.fechaNacimiento = document.getElementById("fechaNacimiento").value;
    solicitante.edad = document.getElementById("edad").value;
    solicitante.telefonoHabitacion = document.getElementById("telefonoHabitacion").value;
    solicitante.telefonoCelular = document.getElementById("telefonoCelular").value;
    solicitante.correoElectronico = document.getElementById("correoElectronico").value;
    return solicitante;
}

function crearAyudaTemporal() {
    var ayudaTemporal = new Object();
    ayudaTemporal.idSolicitante = document.getElementById("cedula").value;
    ayudaTemporal.fechaCreacion = new Date();
    ayudaTemporal.claveRecuperacion = generarClave();
    ayudaTemporal.motivoAyuda = document.getElementById("motivoAyuda").value;
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

function render() {
    $(add);
}

function reset() {
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

function add() {
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
                            console.log(response);
                            var clave = object.ayudaTemporal.claveRecuperacion;
                            swal("¡Su solicitud ha sido enviada!", "Guarde el siguiente código de referencia: " + clave, {
                                icon: "success"}).then(function () {
                                window.location = "AyudasTemporales_UsuarioExperto.html";
                            });
                            ;
                        }
                        if (!response.ok) {
                            console.log("Response incorrectly received");
                            return;
                        }
                        $('#add-modal-ayudas-temporales').modal('hide');
                        reset();
                    })();
                } else {
                    swal("Solicitud cancelada");
                }
            });
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
            $('#example').DataTable({
                scrollY: "500px",
                scrollX: true,
                scrollCollapse: true,
                columnDefs: [
                    {width: 200, targets: 0}
                ],
                fixedColumns: true,
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"},
                dom: 'Blfrtip',
                buttons: [{
                        extend: 'copyHtml5',
                        exportOptions: {
                            columns: [0, ':visible']
                        }
                    },
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

function consultarAyudasTemporalesPorUsuario(cedulaAux) {
    let request = new Request(url + '/API/solicitud/select_all_ayudasTemporales_por_solicitante?id=' + cedulaAux,
            {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        ayudasTemporales = await response.json();
        list2();
    })();
    console.log(ayudasTemporales);
}

function list2() {
    $("#listaAyudasPorSolicitante").html("");
    ayudasTemporales.forEach((at) => {
        row2($("#listaAyudasPorSolicitante"), at);
    });

}

function row2(listado, ayudaTemporal) {
    var tr = $("<tr />");
    tr.html(
            "<td>" + ayudaTemporal.idFormulario + "</td>" +
            "<td>" + ayudaTemporal.motivoAyuda + "</td>" +
            "<td>" + ayudaTemporal.idEstado + "</td>");
    listado.append(tr);
    document.getElementById('nombreSolicitanteTitle').innerHTML = 'Historial de ayudas temporales del habitante ' + ayudaTemporal.idFormulario;
}

//function list() {
//    $("#listado").html("");
//    ayudasTemporales.forEach((at) => {
//        row($("#listado"), at);
//    });
//}
//
//function row(listado, ayudaTemporal) {
//    var tr = $("<tr />");
//    tr.html(
//            //"<td" + 'class="bs-checkbox "><input data-index="0" name="btSelectItem" type="checkbox">' + "</td>" +
//            "<td>" + ayudaTemporal.idFormulario + "</td>" +
//            "<td>" + ayudaTemporal.idSolicitante + "</td>" +
//            "<td>" + ayudaTemporal.motivoAyuda + "</td>" +
//            "<td>" + ayudaTemporal.idEstado + "</td>" +
//            "<td id='edit'><img src='Imagenes/edit.png'></td>" +
//            "<td id='view'><img src='Imagenes/history.png'></td>");
//    tr.find("#view").on("click", () => {
//        consultarAyudasTemporalesPorUsuario(ayudaTemporal.idSolicitante);
//        $('#add-modal-ayudas-temporales-por-usuario').modal('show');
//    });
//    listado.append(tr);
//}

function consultarCedula(cedula) {
    let request = new Request(url + '/API/solicitud/consultar_cedula?id=' + cedula, {method: 'GET', headers: {}});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            return;
        }
        solicitante = await response.json();
        console.log(solicitante);
        rellenarCampos(solicitante);
    })();
}

function cleanModal() {
//    document.getElementById("cedula").value = "";
//    document.getElementById("nombre").value = "";
//    document.getElementById("primerApellido").value = "";
//    document.getElementById("segundoApellido").value = "";
//    document.getElementById("fechaNacimiento").value = "";
//    document.getElementById("edad").value = "";
//    document.getElementById("telefonoHabitacion").value = "";
//    document.getElementById("telefonoCelular").value = "";
//    document.getElementById("correoElectronico").value = "";
}

function rellenarCampos(solicitante) {
    document.getElementById("cedula").value = solicitante['value'].cedula;
    document.getElementById("nombre").value = solicitante['value'].nombre;
    document.getElementById("primerApellido").value = solicitante['value'].primerApellido;
    document.getElementById("segundoApellido").value = solicitante['value'].segundoApellido;
    document.getElementById("fechaNacimiento").value = solicitante['value'].fechaNacimiento;
    document.getElementById("edad").value = solicitante['value'].edad;
    document.getElementById("telefonoHabitacion").value = solicitante['value'].telefonoHabitacion;
    document.getElementById("telefonoCelular").value = solicitante['value'].telefonoCelular;
    document.getElementById("correoElectronico").value = solicitante['value'].correoElectronico;
}


function makenew() {
    render();
}

function loaded() {
    fetchAndList();
    $("#crearFormularioAT").click(makenew);
}
$(cargarDistritos);
$(cargarBarrios);
$(loaded);
//------------------------------------------------------------

var step = 1;
$(document).ready(function () {
    stepProgress(step);
});
$(".next").prop("disabled", false);
$(".next").on("click", function () {
    var nextstep = false;
    if (step === 1) {
        nextstep = checkForm("datosPersonales");
    } else if (step === 2) {
        nextstep = checkForm("datosContacto");
    } else if (step === 3) {
        nextstep = checkForm("datosDireccion");
    } else if (step === 4) {
        nextstep = checkForm2("datosMotivo");
    } else {
        nextstep = true;
    }
    if (nextstep === true) {
        if (step < $(".step").length) {
            $(".step").show();
            $(".step")
                    .not(":eq(" + step++ + ")")
                    .hide();
            stepProgress(step);
        }
        hideButtons(step);
    }
});
// ON CLICK BACK BUTTON
$(".back").on("click", function () {
    if (step > 1) {
        step = step - 2;
        $(".next").trigger("click");
    }
    hideButtons(step);
});
// CALCULATE PROGRESS BAR
stepProgress = function (currstep) {
    var percent = parseFloat(100 / $(".step").length) * (currstep);
    percent = percent.toFixed();
    $(".progress-bar")
            .css("width", percent + "%")
            .html(percent + "%");
};
// DISPLAY AND HIDE "NEXT", "BACK" AND "SUMBIT" BUTTONS
hideButtons = function (step) {
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

function checkForm2(val) {
    // CHECK IF ALL "REQUIRED" FIELD ALL FILLED IN
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

function generarClave() {
    var characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    var clave = "";
    for (i = 0; i < 6; i++) {
        clave += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return "AT" + clave;
}

