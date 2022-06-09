function retrieveMovementsData() {
     var idModification = window.localStorage.getItem('modificationRequestIdItem');
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: `http://localhost:8080/home/ModificationMovementsListService?idModification=${idModification}`,
        success: loadTableData,
        error: function () {}
    });
}

function loadTableData(data) {
    let table = $("#movements-list").DataTable({
        "language": {

            url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

        },
        data: data,
        columns: [
            {title: "Código", data: "id"},
            {title: "Partida decreciente", data: "budgetA.description"},
            {title: "Partida creciente", data: "budgetB.description"},
            {title: "Monto", data: "amount"}
        ]
    });
}