function retrieveUserData() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/CurrentUserDataService',
        success: successUserDataHandler,
        error: function (xhr, status, error) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
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
        url: 'http://localhost:8086/home/BudgetListStaticService',
        success: successBudgetHandler,
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
    let amount = $("#amount").val();
    let description = $("#description").val();
    let budgetItem = $("#budget").val();

    if ((amount == '' || amount > 150000 || amount == undefined) || ((budgetItem == '' || budgetItem == undefined) && (description == '' || description == undefined))) {
        if (amount == '' || amount > 150000 || amount == undefined) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: 'Error!',
                text: 'Debe ingresar un monto menor o igual a 150.000 colones',
                icon: 'error'
            });
            return false;
        } else if (((budgetItem == '' || budgetItem == undefined) && (description == '' || description == undefined))) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: 'Error!',
                text: 'Debe ingresar una descripción o la partida presupuestaria',
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
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Solicitud envíada con éxito.',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then((result) => {
                window.location.href = '/home/sigep/code-certifications/requests.jsp';
            });
        },
        error: function (xhr, status, error) {
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