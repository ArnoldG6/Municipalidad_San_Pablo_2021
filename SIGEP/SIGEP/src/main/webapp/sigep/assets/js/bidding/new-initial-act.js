function deleteData() {
    $('#nameDoc').text('');
    $('#nameDoc2').text('');
}
// muestra el nombre del archivo en la pagina

let f2 = document.querySelector('#doc2');
f2.addEventListener('change', () => {
    document.querySelector('#nameDoc2').innerText = f2.files[0].name;
});

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/BudgetBalanceCertificateListService',
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
});

function successBudgetHandler(data) {
    let refBudgetDataList = document.getElementById('datalistOptions');

    if (refBudgetDataList) {
        refBudgetDataList.innerHTML = "";
        data.forEach((certificate) => {

            let d = new Date(certificate.id.date);
            let id = d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + certificate.id.consecutive;
            let option = document.createElement('OPTION');
            option.setAttribute("value", id);
            refBudgetDataList.appendChild(option);

        });


    }
}

function validateForm() {
    let csp = $("#csp").val();
    let doc2 = $("#doc2").val();

    var allowedExtensions = /(.pdf)$/i;

    if (csp == '' || csp == undefined || csp.length <= 8) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar un código de certificado de saldo válido',
            icon: 'error'
        });
        return false;
    }

    if (isNaN(csp)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'El código de certificado deben ser números',
            icon: 'error'
        });
        return false;
    }

    if (doc2 == '' || doc2 == undefined || !allowedExtensions.exec(doc2)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar el documento en PDF: acto inicial de licitación',
            icon: 'error'
        });
        return false;
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
                text: 'Acto inicial registrado con éxito.',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/bidding/initial-acts.jsp';
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