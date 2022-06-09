function retrieveModificationRequestsData() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/BudgetModificationRequestListService',
        success: loadTableData,
        error: function (xhr, status, error) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
    });
}

//cargar tabla
function loadTableData(data) {
    var table = $('#requests-list').DataTable({
        "destroy": true,
        "language": {

            url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

        },
        data: data,
        columns: [

            {title: "Fecha", "render": function (data, type, row, meta) {
                    const date = new Date(row.id.date);
                    return date.toLocaleDateString("es");
                }},
            {title: "Código de solicitud", "render": function (data, tyoe, row, meta) {
                    let d = new Date(row.id.date);
                    return d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + row.id.consecutive;
                }
            },
            {title: "Solicitante", "render": function (data, type, row, meta) {
                    return row.applicant.name + " " + row.applicant.surname;
                }
            },
            {title: "Departamento", data: "applicant.department.description"},
            {title: "Estado", data: "status.description"},
            {title: "", "render": createMovementsButton},
            {title: "Solicitud", "render": function () {
                    return "<button class='responder btn btn-outline-primary btn-sm' data-bs-toggle='modal' data-bs-target='#contenedor-modal'>Responder</button>";
                }
            }
        ]
    });
    showing("#requests-list tbody", table);
}

function createMovementsButton(data, type, row, meta) {
    let date = new Date(row.id.date);
    let id = date.getFullYear() + "" + ("0" + (date.getMonth() + 1)).slice(-2) + "" + ("0" + date.getDate()).slice(-2) + "" + row.id.consecutive;
    return `<a type='button' id='${id}' class='btn btn-outline-primary btn-sm' onclick='movementsButtonClicked(this)'>Movimientos</a>`;
}

function movementsButtonClicked(element) {
    window.localStorage.setItem('modificationRequestIdItem', element.id);
    window.location.href = '/home/sigep/modifications/movements.jsp';
}

function approvedButtonClicked() {
    document.getElementById("date").value = $("#fecha").val();
    document.getElementById("consecutive").value = $("#consecutivo").val();
    document.getElementById("action").value = "approve";

    var frm = $("#form-approve");
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Solicitud aprobada con éxito',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/modifications/approved-requests.jsp';
            });

        },
        error: function (xhr, status, error) {
            console.log(status);
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
    });
}

function pdfWindowMCMOD(event) {
    let path = event.target.value;
    const result = path.split(".")[0].split("\\").slice(-1);
    document.getElementById("nombre-mc").value = result;
    document.getElementById("form-mc").submit();
}

function pdfWindowCSP(event) {
    let path = event.target.value;
    const result = path.split(".")[0].split("\\").slice(-1);

    document.getElementById("nombre-csp").value = result;
    document.getElementById("form-csp").submit();
}

function showing(tbody, table) {
    $(tbody).on("click", "button.responder", function (e) {

        let data = table.row(e.target.parentElement.parentElement).data();

        let d = new Date(data.id.date);
        let id = d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + data.id.consecutive;
        window.localStorage.setItem('mcRequest', id);

        const btnApprove = document.querySelector("#approveButton");
        const btnDenegate = document.querySelector("#denegateButton");

        const nombre = document.querySelector("#nombre");
        nombre.value = data.applicant.name + " " + data.applicant.surname;

        const departamento = document.querySelector("#departamento");
        departamento.value = data.applicant.department.description;

        if (data.status.idRequestStatus === 2 || data.status.idRequestStatus === 3) {
            btnApprove.disabled = true;
            btnDenegate.disabled = true;
        }

        const fecha = document.querySelector("#fecha");
        fecha.value = data.id.date;

        const estado = document.querySelector("#estado");
        estado.value = data.status.description;

        const consecutivo = document.querySelector("#consecutivo");
        consecutivo.value = id;

        certificado = data;
    });
}

function charCount() {
    var chars = document.getElementById('textArea').value.length;
    document.getElementById('textAreaCount').innerHTML = chars + "/100 (Máximo 100 caracteres) ";

}

function denegateRequest() {

    document.getElementById("secDate").value = $("#fecha").val();
    document.getElementById("secConsecutive").value = $("#consecutivo").val();
    document.getElementById("action").value = "deny";

    var frm = $("#form-denegate");
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Solicitud denegada con éxito',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/modifications/requests.jsp';
            });

        },
        error: function (xhr, status, error) {
            console.log(status);
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
    });
}