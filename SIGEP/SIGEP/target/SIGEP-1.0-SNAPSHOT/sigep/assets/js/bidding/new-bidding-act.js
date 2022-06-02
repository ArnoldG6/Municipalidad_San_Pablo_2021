$(document).ready(function () {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/InitialBiddingActRequestListService',
        success: successInitialBiddingActHandler,
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

function successInitialBiddingActHandler(data) {
    let refInitialBiddingActDataList = document.getElementById('datalistOptions');

    if (refInitialBiddingActDataList) {
        refInitialBiddingActDataList.innerHTML = "";
        data.forEach((initialBiddingActItem) => {

            let d = new Date(initialBiddingActItem.id.date);
            let id = d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + initialBiddingActItem.id.consecutive;
            let option = document.createElement('OPTION');
            option.setAttribute("value", id);
            refInitialBiddingActDataList.appendChild(option);

        });


    }
}

function validateForm() {
    let ail = $("#ail").val();
    let pdf = $("#pdf").val();

    var allowedExtensions = /(.pdf)$/i;

    if (ail == '' || pdf == undefined || ail.length <= 8) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar un código de acto inicial de licitación válido',
            icon: 'error'
        });
        return false;
    }
    
      if (isNaN(ail)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'El código de certificado deben ser números',
            icon: 'error'
        });
        return false;
    }

    if (ail == '' || pdf == undefined || !allowedExtensions.exec(pdf)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar el documento en PDF: acto de licitación',
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
                text: 'Acto de licitación registrado con éxito.',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/bidding/bidding-acts.jsp';
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