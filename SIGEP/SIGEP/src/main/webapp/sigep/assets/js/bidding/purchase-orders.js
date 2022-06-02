function retrieveAsAdmin() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/PurchaseOrderRequestListService',
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
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/PurchaseOrderRequestListService',
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

function loadTableDataAsUser(data) {
    $("#last-column").remove();
    var table = $('#purchase-order-list').DataTable(
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
                    {
                        title: "Solicitante", "render": function (data, type, row, meta) {
                            return row.transmitter.name + " " + row.transmitter.surname;
                        }
                    },
                    {title: "Departamento", data: "transmitter.department.description"},
                    {title: "Estado", data: "status.description"},
                    {
                        title: "Acto de Licitación", "render": function (data, type, row, meta) {
                            var r = row.biddingAct.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowBA(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    },
                    {
                        title: "Documento", "render": function (data, type, row, meta) {
                            var r = row.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowOC(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    }
                ]
            });
}

function loadTableDataAsAdmin(data) {
    var table = $('#purchase-order-list').DataTable(
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
                    {
                        title: "Solicitante", "render": function (data, type, row, meta) {
                            return row.transmitter.name + " " + row.transmitter.surname;
                        }
                    },
                    {title: "Departamento", data: "transmitter.department.description"},
                    {title: "Estado", data: "status.description"},
                    {
                        title: "Acto de Licitación", "render": function (data, type, row, meta) {
                            var r = row.biddingAct.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowBA(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    },
                    {
                        title: "Documento", "render": function (data, type, row, meta) {
                            var r = row.documentPath;
                            return "<button type='button' id='blank' class='btn btn-outline-warning btn-sm ' value=" + r + " onclick=pdfWindowOC(event)>Descargar <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    },
                    {
                        title: "Disminuir", "render": function (data, type, row, meta) {
                            let d = new Date(row.id.date);
                            let id = d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + row.id.consecutive;
                            return "<button type='button' id='" + id + "' class='btn btn-outline-primary btn-sm' onclick=decreaseAmount(event)>Disminuir <i class='fa fa-solid fa-file-arrow-down'></i></button>";
                        }
                    }
                ]
            });
}

function pdfWindowOC(event) {
    let path = event.target.value;

    const result = path.split(".")[0].split("\\").slice(-1);

    document.getElementById("nombre-oc").value = result;
    document.getElementById("form-oc").submit();

}

function pdfWindowBA(event) {
    let path = event.target.value;

    const result = path.split(".")[0].split("\\").slice(-1);

    document.getElementById("nombre-al").value = result;
    document.getElementById("form-al").submit();

}

function decreaseAmount(event) {
    let id = event.target.id;
    document.getElementById("id-po").value = id;

    var frm = $("#form-decrease");
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Monto disminuido con éxito',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/bidding/purchase-orders.jsp';
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