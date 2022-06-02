function logout() {

    Swal.fire({
        title: '¿Desea cerrar sesión?',
        text: "Si presiona OK saldrá de la sesión automaticamente!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'OK'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("logout").submit();
        }
    });

}