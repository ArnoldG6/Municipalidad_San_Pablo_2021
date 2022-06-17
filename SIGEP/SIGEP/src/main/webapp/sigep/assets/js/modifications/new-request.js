$(document).ready(function () {
    var i = 1;
    var num = 1;
    $('#add').click(function () {
        i++;
        $('#dynamic_field').append('<tr id="column' + i + '">' +
                '<td><label class="form-label">' + i + '</label></td>' +
                '<td><label class="form-label">Nombre del centro de costos por disminuir:</label>' +
                '<input name="decreaseBudget' + i + '" class="form-control mb-3" list="datalistOptions" id="decreaseBudget"' +
                'placeholder="Digite el codigo o nombre del centro de costos" onchange="retrieveBudgetDataBySearchParameter();">' +
                '<datalist id="datalistOptions"></datalist></td>' +
                '<td><label class="form-label" >Monto</label><br><div class="input-group mb-3"><span class="input-group-text">₡</span>' +
                '<input type="number" id="amount" name="amount' + i + '" class="form-control" min="100" placeholder="Monto" aria-label="Amount (to the nearest dollar)"><span class="input-group-text">.00</span></div>' +
                '</td></tr>' +
                '<tr id="column"><td><label class="form-label"></label></td>' +
                '<td><label class="form-label">Nombre del centro de costos por aumentar:</label>' +
                '<input name="increaseBudget' + i + '" class="form-control mb-3" list="datalistOptions" id="increaseBudget"' +
                'placeholder="Digite el codigo o nombre del centro de costos" onchange="retrieveBudgetDataBySearchParameter();">' +
                '<datalist id="datalistOptions"></datalist></td></tr>');
    });
    $(document).on('click', '.btn_remove', function () {
        var button_id = $(this).attr("id");
        $('#column' + button_id + '').remove();
    });
});


function retrieveUserData() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/CurrentUserDataService',
        success: successUserDataHandler,
        error: function () {}
    });
    retrieveBudgetDataBySearchParameter();
}

function successUserDataHandler(data) {
    $("#nombre").val(data.official.name + " " + data.official.surname);
    $("#departamento").val(data.official.department.description);
}

function retrieveBudgetDataBySearchParameter() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/BudgetListStaticService',
        success: successBudgetHandler,
        error: function () { }
    });
}

function successBudgetHandler(data) {
    let refBudgetDataList = document.getElementById('datalistOptions');
    const dataFilter = data.filter(item => item.receives);
    if (refBudgetDataList) {
        refBudgetDataList.innerHTML = "";
        dataFilter.forEach((budgetItem) => {
            let option = document.createElement('OPTION');
            option.setAttribute("value", budgetItem.description + "-" + budgetItem.idItem);
            refBudgetDataList.appendChild(option);
        });
    }
}

function validateForm() {
    let amount = $("#amount1").val();
    let decreaseBudget = $("#decreaseBudget").val();
    let increaseBudget = $("#increaseBudget").val();

    if ((amount == '' || amount == undefined) || ((decreaseBudget == '' || decreaseBudget == undefined) && (increaseBudget == '' || increaseBudget == undefined))) {
        if (amount == '' || amount == undefined) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: 'Error!',
                text: 'Debe ingresar un nuevo monto.',
                icon: 'error'
            });
            return false;
        } else if (((decreaseBudget == '' || decreaseBudget == undefined) && (increaseBudget == '' || increaseBudget == undefined))) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: 'Error!',
                text: 'Debe ingresar la partida presupuestaria',
                icon: 'error'
            });
            return false;
        }
    }
    return true;
}

frm = $("#form");
frm.submit(function () {
    if (!validateForm()) {
        return false;
    }

    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: new FormData(frm[0]),
        processData: false,
        dataType: 'text',
        contentType: false,
        cache: false,
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Solicitud enviada con éxito.',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then((result) => {
                window.location.href = '/home/sigep/modifications/requests.jsp';
            });
        },
        error: function (xhr, status, error) {
            console.log(xhr);
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
    });
    return false;
});
