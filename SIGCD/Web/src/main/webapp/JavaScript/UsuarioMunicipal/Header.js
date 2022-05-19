var barraNavegacion =
        `
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #02315f;">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <a class="navbar-brand" href="MenuPrincipal.html">
                    <img src="Imagenes/LogoMini.png" alt="" width="30" height="30" class="d-inline-block align-text-top">
                </a>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Ayudas temporales
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="GestionarAyudasTemporales.html">Gestionar solicitudes</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#add-modal-ayudas-temporales">Agregar solicitud</a></li>
                    </ul>
                </li>
                <a class="nav-link active" aria-current="page"></a>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Becas académicas
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                        <li><a class="dropdown-item" href="GestionarBecasAcademicas.html">Gestionar solicitud</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#add-modal-becas-academicas">Agregar solicitud</a></li>
                    </ul>
                </li>
                <a class="nav-link active" aria-current="page"></a>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Reportes
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Generar resportes</a></li>
                    </ul>
                </li>
                <a class="nav-link active" aria-current="page"></a>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.html">Salir</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
`;
        function cargarBarraNavegacion() {
        $('body').prepend(barraNavegacion);
        }

$(cargarBarraNavegacion);