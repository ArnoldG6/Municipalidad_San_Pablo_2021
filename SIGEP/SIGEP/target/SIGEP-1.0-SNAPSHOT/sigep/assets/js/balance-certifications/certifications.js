function retrieveAsAdmin() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/BudgetBalanceCertificateListService',
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

function retrieveAsUser() {
    $("#last-column").remove();
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/BudgetBalanceCertificateListService',
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

function loadTableData(data) {
    var table = $('#certification-list').DataTable(
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
                    {title: "Código de certificado", "render": function (data, tyoe, row, meta) {
                            var d = new Date(row.id.date);
                            return d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + row.id.consecutive;
                        }
                    },
                    {title: "Código de solicitud", "render": function (data, tyoe, row, meta) {
                            let d = new Date(row.request.id.date);
                            return d.getFullYear() + "" + ("0" + d.getMonth()).slice(-2) + "" + ("0" + (d.getDate() + 1)).slice(-2) + "" + row.id.consecutive;
                        }
                    },
                    {title: "Código de partida", data: "budgetItem.idItem"},
                    {title: "Transmisor", "render": function (data, type, row, meta) {
                            return row.beneficiary.name + " " + row.beneficiary.surname;
                        }
                    },
                    {title: "Beneficiaro", "render": function (data, type, row, meta) {
                            return row.beneficiary.name + " " + row.beneficiary.surname;
                        }
                    },
                    {title: "Monto ", data: "amount"},

                    {title: "Bloqueo", "render": function (data, type, row, meta) {
                            if (row.budgetLock != null) {
                                return `<div class="form-check form-switch"><input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" disabled="disabled" checked></div>`;
                            } else {
                                return `<div class="form-check form-switch"><input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" disabled="disabled" readonly></div>`;
                            }
                        }
                    },
                    {title: "Documento", "render": function (data, type, row, meta) {
                            var r = row.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowCSP(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    }
                ]
            });

}

function pdfWindowCSP(event) {
    let path = event.target.value;

    const result = path.split(".")[0].split("\\").slice(-1);

    document.getElementById("nombre-csp").value = result;
    document.getElementById("form-csp").submit();

}