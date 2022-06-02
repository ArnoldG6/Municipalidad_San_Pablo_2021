function validateForm() {
    let excel = $("#excel").val();
    var allowedExtensions = /(.xlsx)$/i;

    if (excel == undefined || !allowedExtensions.exec(excel)) {
        Swal.fire({
            confirmButtonColor: '#3085d6',
            title: 'Error!',
            text: 'Debe ingresar el documento en xlsx',
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
    const loading = document.querySelector("#loading");
    loading.style.visibility = "visible";
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: new FormData(frm[0]),
        processData: false,
        dataType: 'text',
        contentType: false,
        cache: false,
        success: function () {
              loading.style.visibility = "hidden";
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Centros de costos registrados con éxito.',
                icon: 'success'
            }).then((result) => {
                window.location.href = '/home/sigep/budget/budget-list.jsp';
            });
        },
        error: function (xhr, status, error) {
              loading.style.visibility = "hidden";
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