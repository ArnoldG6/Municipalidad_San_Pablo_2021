$(document).ready(function () {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8080/home/BiddingActListService',
        success: successBiddingActHandler,
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

function successBiddingActHandler(data) {
    let refBiddingActDataList = document.getElementById('datalistOptions');

    if (refBiddingActDataList) {
        refBiddingActDataList.innerHTML = "";
        data.forEach((biddingActItem) => {

            let d = new Date(biddingActItem.id.date);
            let id = d.getFullYear() + "" + ("0" + (d.getMonth() + 1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2) + "" + biddingActItem.id.consecutive;
            let option = document.createElement('OPTION');
            option.setAttribute("value", id);
            refBiddingActDataList.appendChild(option);

        });


    }
}

function validateForm() {
    let al = $("#al").val();
    let pdf = $("#pdf").val();

    var allowedExtensions = /(.pdf)$/i;

    if (al == '' || pdf == undefined || al.length <= 8) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar un código de acto de licitación válido',
            icon: 'error'
        });
        return false;
    }
    
      if (isNaN(al)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'El código de certificado deben ser números',
            icon: 'error'
        });
        return false;
    }

    if (al == '' || pdf == undefined || !allowedExtensions.exec(pdf)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar el documento en PDF: órden de compra',
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
                text: 'Órden de compra registrada con éxito.',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/bidding/purchase-orders.jsp';
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