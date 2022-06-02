function retrieveBudgetLocksData() {
    const idBudget = window.localStorage.getItem('locksIdBudgetItem');
    window.localStorage.removeItem("locksIdBudgetItem");
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: `http://localhost:8086/home/BudgetLocksListService?idBudget=${idBudget}`,
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
    let table = $("#budget-locks-list").DataTable({
        "language": {

            url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

        },
        data: data,
        columns: [
            {title: "Código", data: "idLock"},
            {title: "Monto", data: "amount", render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {title: "Aplicante", "render": function (data, type, row, meta) {
                    return row.applicant.name + " " + row.applicant.surname;
                }
            },
            {title: "Fecha inicio", data: "initialDate"},
            {title: "Fecha fin", data: "endDate"}
        ]
    });
}