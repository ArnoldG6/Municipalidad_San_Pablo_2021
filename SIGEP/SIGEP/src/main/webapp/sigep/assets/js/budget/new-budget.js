const amountElement = `    <td id="amount-data">
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text">₡</span>
                                                    <input type="number" name="amount" id="amount" class="form-control" placeholder="Monto" aria-label="Amount (to the nearest dollar)"">
                                                    <span class="input-group-text">.00</span>
                                                </div>
                                            </td>`;

$(document).ready(function () {
    var i = 1;
    $('#add').click(function () {

        tr = document.getElementById("dynamic_field").rows;
        
        const element = document.querySelector("#amount-data");
        element.remove();

        for (var j = 0; j < tr.length; j++) {
            if (tr[j].children[1].children[0].value == undefined || tr[j].children[1].children[0].value == "" || tr[j].children[2].children[0].disabled == undefined || tr[j].children[2].children[0].disabled == false) {
                Swal.fire({
                    confirmButtonColor: '#3085d6',
                    title: 'Error!',
                    text: 'Debe ingresar las partidas existentes en los niveles anteriores',
                    icon: 'error'
                });
                return;
            }
        }
        i++;
        $('#dynamic_field').append('<tr id="row' + i + '"><td><button type="button" name="remove" id="' + i + '" class="btn btn-danger btn_remove">X</button></td><td><input type="text" name="codigo" onkeyup="onchangeInput(this);" placeholder="Ingrese su codigo" class="form-control name_list" required /></td><td><input type="text" name="nombre" placeholder="Ingrese el nombre de partida" class="form-control name_list" required /></td>'+ amountElement + '</tr>');

    });
    $(document).on('click', '.btn_remove', function () {
        var button_id = $(this).attr("id");
        $('#row' + button_id + '').remove();
    });
});
function onchangeInput(element) {

    if (element.value.length > 2 || isNaN(element.value)) {
        element.value = "";
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'El código debe ser como mínimo, dos números.',
            icon: 'error'
        });
        return;
    }
    const numberOfRows = document.querySelectorAll("#dynamic_field tbody tr").length;



    var budget = getBudgetId();
    if (budget == "" || budget == undefined) {
        var parent = element.parentElement;
        var next = parent.nextElementSibling;
        var child = next.firstChild;
        child.value = "";
        child.disabled = false;
    } else {
        $.ajax({
            type: 'GET',
            data: {},
            dataType: 'json',
            url: `http://localhost:8080/home/BudgetItemInformationService?budget=${budget}`,
            success: function (data) {
                if (data != null) {
                    var parent = element.parentElement;
                    var next = parent.nextElementSibling;
                    var child = next.children[0];
                    child.value = data.description;
                    child.disabled = true;

                } else {
                    element.required = true;
                    var parent = element.parentElement;
                    var next = parent.nextElementSibling;
                    var child = next.children[0];
                    child.value = "";
                    child.disabled = false;
                    if (numberOfRows >= 2) {
                        if (element.value.length === 1) {
                            element.value = element.value;
                        }
                    }
                }
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
    }

}

function getBudgetId() {
    tr = document.getElementById("dynamic_field").rows;
    let id = "";
    for (var j = 0; j < tr.length; j++) {
        if (tr[j].children[1].children[0].value != undefined || tr[j].children[1].children[0].value != "") {
            if (j < tr.length - 1) {
                id += tr[j].children[1].children[0].value + ".";
            } else {
                id += tr[j].children[1].children[0].value;
            }
        }
    }
    return id;
}

function validateForm() {
    $("form").each(function () {
        var a = $(this).find(':input');
    });
}

function sendData() {
    var form = document.getElementById("formData");
    var data = new FormData(form);


}

frm = $("#formData");
frm.submit(function () {
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Partida registrada con éxito',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/budget/budget-list.jsp';
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