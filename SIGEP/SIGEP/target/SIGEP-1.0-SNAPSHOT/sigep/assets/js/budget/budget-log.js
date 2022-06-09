function retrieveBudgetLogData() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: `http://localhost:8080/home/BudgetLogListService`,
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
    let table = $("#budget-log-list").DataTable({
        "language": {

            url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

        },
        data: data,
        columns: [
            {title: "Código Movimiento", data: "id.consecutive"},
            {title: "Fecha", data: "id.date"},
            {title: "Partida Presupuestaria", data: "bugetItem.idItem"},
            {title: "Monto Inicial", data: "initialAmount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Monto Final", data: "finalAmount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Aumentado", data: "increased", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Disminuido", data: "decreased", render: $.fn.dataTable.render.number(',', '.', 2, '')}

        ]
    });
}
