
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error de Autenticación</title>

        <link href="../assets/vendor/bootstrap-5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/menu/menu.css" rel="stylesheet" type="text/css">
        <link href="../assets/vendor/bootstrap-icons-1.8.1/bootstrap-icons.css" rel="stylesheet" type="text/css">
    </head>

    <body onload="errorLogin();">
        <nav class="navbar navbar-dark" style="background:#1f3140">
            <div class="dropdown">
                <a href="#" class="d-flex align-items-center justify-content-center ps-3 link-light text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="bi-person-circle h2"></i>
                </a>
                <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser3">
                    <li><a class="dropdown-item text-dark" href="../../index.jsp">Regresar a la página anterior</a></li>
                </ul>
            </div>
        </nav>
        <div class="container-fluid">
            <div class="row">

                <div class="alert alert-danger col-12 my-3 py-2" role="alert">
                    <i class="bi bi-exclamation-triangle-fill fs-3"></i> PÁGINA DE ERROR (FALLO EN LOGGEO): Por favor regrese y verifique las credenciales
                </div>
                <div align="center">
                    <img class="col-lg-4 col-sm-12" src="../assets/img/logomuni.jpeg" alt="" style="height:415px; width:300px">

                </div>
            </div>
        </div>
        <div class="">

        </div>
        <footer class="text-center text-light" style="height:60px; line-height: 60px; background: #1f3140;">
            &COPY; 2022 Municipalidad San Pablo de Heredia. Todos los derechos reservados
        </footer>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script src="../assets/js/error.js" type="text/javascript"></script>

    </body>
</html>
