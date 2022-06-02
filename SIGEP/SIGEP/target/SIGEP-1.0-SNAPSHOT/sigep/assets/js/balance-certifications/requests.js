function retrieveAsAdmin() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/BudgetBalanceCertificateRequestListService',
        success: loadTableDataAsAdmin,
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

function retrieveAsUser() {
    $("#last-column").remove();
    $("#avaiable-column").remove();
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/BudgetBalanceCertificateRequestListService',
        success: loadTableDataAsUser,
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

function loadTableDataAsAdmin(data) {
    var table = $('#requests-list').DataTable(
            {
                "destroy": true,
                "language": {

                    url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

                },
                data: data,
                columns: [
                    {title: "Fecha", "render": function (data, type, row, meta) {
                            const date = new Date(row.id.date);
                            return date.toLocaleDateString("es");
                        }
                    },
                    {title: "Código de solicitud", "render": function (data, tyoe, row, meta) {
                            var d = new Date(row.id.date);
                            return d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + row.id.consecutive;
                        }
                    },
                    {title: "Solicitante", "render": function (data, type, row, meta) {
                            return row.applicant.name + " " + row.applicant.surname;
                        }
                    },
                    {title: "Departamento", data: "applicant.department.description"},
                    {title: "Código presupuestario", "render": function (data, type, row, meta) {
                            if (row.budgetItem) {
                                return row.budgetItem.idItem;
                            }
                            return "";
                        }
                    },
                    {title: "Presupuesto disponible", "render": function (data, type, row, meta) {
                            if (row.budgetItem) {
                                return $.fn.dataTable.render.number(',', '.', 2, "").display(row.budgetItem.availableAmount);
                            }
                            return "";
                        }
                    },
                    {title: "Descripción", data: "description"},
                    {title: "Estado", data: "status.description"},
                    {title: "Monto Solicitado", data: "amount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
                    {"defaultContent": "<button class='responder btn btn-outline-primary btn-sm' data-bs-toggle='modal' data-bs-target='#contenedor-modal'>Responder</button>"}
                ]

            });
    mostrar("#requests-list tbody", table);
}

function loadTableDataAsUser(data) {

    var table = $('#requests-list').DataTable(
            {
                "destroy": true,
                "language": {

                    url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

                },
                data: data,
                columns: [
                    {title: "Fecha", "render": function (data, type, row, meta) {
                            const date = new Date(row.id.date);
                            return date.toLocaleDateString("es");
                        }
                    },
                    {title: "Código de solicitud", "render": function (data, tyoe, row, meta) {
                            var d = new Date(row.id.date);
                            return d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + row.id.consecutive;
                        }
                    },
                    {title: "Nombre", data: "applicant.name"},
                    {title: "Departamento", data: "applicant.department.description"},
                    {title: "Código presupuestario", "render": function (data, type, row, meta) {
                            if (row.budgetItem) {
                                return row.budgetItem.idItem;
                            }
                            return "";
                        }
                    },
                    {title: "Descripción", data: "description"},
                    {title: "Estado", data: "status.description"},
                    {title: "Monto Solicitado", data: "amount", render: $.fn.dataTable.render.number(',', '.', 2, '')}
                ]

            });
}

function mostrar(tbody, table) {
    $(tbody).on("click", "button.responder", function (e) {

        let data = table.row(e.target.parentElement.parentElement).data();

        let d = new Date(data.id.date);
        let id = d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + data.id.consecutive;
        window.localStorage.setItem('cspRequest', id);

        const btnBuildCertificate = document.querySelector("#buildCertificateButton");
        const btnDenegateCertificate = document.querySelector("#denegateButton");

        const montoDis = document.querySelector("#montoDis");

        const nombre = document.querySelector("#nombre");
        nombre.value = data.applicant.name + " " + data.applicant.surname;

        const departamento = document.querySelector("#departamento");
        departamento.value = data.applicant.department.description;

        const codigo = document.querySelector("#codigo");

        if (data.budgetItem) {
            codigo.type = "text";
            codigo.disabled = true;
            codigo.classList.remove("responder");
            codigo.classList.remove("btn");
            codigo.classList.remove("btn-outline-primary");
            codigo.classList.remove("btn-sm");
            btnBuildCertificate.disabled = false;
            codigo.addEventListener("click", null);
            codigo.value = data.budgetItem.idItem;
            montoDis.value = String($.fn.dataTable.render.number(',', '.', 2, "").display(data.budgetItem.availableAmount));
        } else {
            codigo.type = "button";
            codigo.disabled = false;
            codigo.classList.toggle("responder");
            codigo.classList.toggle("btn");
            codigo.classList.toggle("btn-outline-primary");
            codigo.classList.toggle("btn-sm");
            btnBuildCertificate.disabled = true;
            codigo.addEventListener("click", selectBudget);
            codigo.value = "Seleccionar partida";
        }


        if (data.status.idRequestStatus === 2 || data.status.idRequestStatus === 3) {
            btnBuildCertificate.disabled = true;
            btnDenegateCertificate.disabled = true;
        }

        const descripcion = document.querySelector("#descripcion");
        descripcion.value = data.description;

        const monto = document.querySelector("#monto");
        monto.value = String($.fn.dataTable.render.number(',', '.', 2, "").display(data.amount));

        const consecutivo = document.querySelector("#consecutivo");
        consecutivo.value = id;

        certificado = data;
    });
}
function charCount() {
    var chars = document.getElementById('textArea').value.length;
    document.getElementById('textAreaCount').innerHTML = chars + "/100 (Máximo 100 caracteres) ";

}

function buildCertificate() {
    window.location.href = '/home/sigep/balance-certifications/csp-document.jsp';
}

function denegateRequest() {
    document.getElementById("reason").value = $("#textArea").val();
    document.getElementById("scsp").value = window.localStorage.getItem('cspRequest');
    var frm = $("#form-denegate");
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Solicitud denegada con éxio',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/balance-certifications/requests.jsp';
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

function selectBudget() {
    window.location.href = '/home/sigep/balance-certifications/select-budget.jsp';
}