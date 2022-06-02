function retrieveBudgetData() {
    document.querySelector("body").classList.add("loading");
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/BudgetListStaticService',
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
    let table = $("#budget-list").DataTable({
        "language": {

            url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

        },
        "createdRow": function (row, data, dataIndex) {

            if (data.receives) {
                $(row).addClass('table-info');
            }
        },
        data: data,
        columns: [
            {title: "Código", data: "idItem"},
            {title: "Descripción", data: "description"},
            {title: "Ordinario", data: "ordinaryAmount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Disponible", data: "availableAmount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "", "render": calculateAvailablePercentage},
            {title: "Extraordinario", data: "extraordinaryAmount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Aumentado", data: "increased", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Disminuido", data: "decreased", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Caja Chica", data: "pettyCash", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Licitaciones", data: "tenders", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Bloqueado", data: "blockedAmount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "", "render": createLocksButton}
        ]
    });

    setTimeout(() => {
        document.querySelector("body").classList.remove("loading");
    }, 500);

}

function createLocksButton(data, type, row, meta) {
    return `<a type='button' id='${row.idItem}' class='btn btn-outline-primary' onclick='locksButtonClicked("${row.idItem}")'>Bloqueos</a>`;
}

function locksButtonClicked(id) {
    window.localStorage.setItem('locksIdBudgetItem', id);
    window.location.href = '/home/sigep/budget/budget-locks.jsp';
}

function calculateAvailablePercentage(data, type, row, meta) {
    if (row.ordinaryAmount == 0) {
        return 0 + "%";
    }
    return ((row.availableAmount * 100) / row.ordinaryAmount).toFixed(2) + "%";
}