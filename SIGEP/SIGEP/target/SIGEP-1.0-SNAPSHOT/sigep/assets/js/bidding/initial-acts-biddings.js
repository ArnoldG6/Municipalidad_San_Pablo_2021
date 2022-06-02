function retrieveAsAdmin() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/InitialBiddingActRequestListService',
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
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/InitialBiddingActRequestListService',
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
    var table = $('#initial-act-list').DataTable(
            {
                "destroy": true,
                "language": {
                    url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"
                },
//        paging: false,
//        searching: false,
//        info: false,
                data: data,
                columns: [

                     {title: "Fecha de Solicitud", "render": function (data, type, row, meta) {
                            const date = new Date(row.id.date);
                            return date.toLocaleDateString("es");
                        }
                    },
                    {title: "Código", "render": function (data, tyoe, row, meta) {
                            var d = new Date(row.id.date);
                            return d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + row.id.consecutive;
                        }
                    },
                    {title: "Solicitante", "render": function (data, type, row, meta) {
                            return row.applicant.name + " " + row.applicant.surname;
                        }
                    },
                    {title: "Departamento", data: "applicant.department.description"},
                    {title: "Estado", data: "status.description"},
                    {title: "Certificado", "render": function (data, type, row, meta) {
                            var r = row.certificate.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowCSP(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    },
                    {title: "Documento", "render": function (data, type, row, meta) {
                            var r = row.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowIA(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    }
                ]
            });
}

function pdfWindowIA(event) {
    let path = event.target.value;

    const result = path.split(".")[0].split("\\").slice(-1);

    document.getElementById("nombre-ia").value = result;
    document.getElementById("form-ia").submit();
}

function pdfWindowCSP(event) {
    let path = event.target.value;

    const result = path.split(".")[0].split("\\").slice(-1);

    document.getElementById("nombre-csp").value = result;
    document.getElementById("form-csp").submit();

}