<%@page import="java.util.Objects"%>
<%@page import="common.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession(true).getAttribute("user");
    if(Objects.isNull(user)) {
        request.getRequestDispatcher("../common/nouser.jsp").forward(request, response);
    }else if(!User.validateUserRol(user) && !User.validateSuperAdminRol(user)) {
        request.getRequestDispatcher("../common/invaliduser.jsp").forward(request, response);

    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>SIGEP</title>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.datatables.net/1.11.3/css/dataTables.semanticui.min.css" rel="stylesheet" type="text/css"/>

        <link href="../assets/vendor/bootstrap-5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/menu/menu.css" rel="stylesheet" type="text/css">
        <link href="../assets/vendor/bootstrap-icons-1.8.1/bootstrap-icons.css" rel="stylesheet" type="text/css">

    </head>
    <body onload="retrieveUserData();">
        <jsp:directive.include file="../common/header.jsp"/>
        <main class="mt-5">
            <!-- Wrapper container -->
            <div class="container py-4" style=" z-index:1;">
                <div class="card shadow-lg mb-3" style="max-width: 90rem;">
                    <div class="card-header">Solicitud de Certificado de Código Presupuestario</div>
                    <div class="card-body">
                        <!-- Form for requesting a budget balance certificate -->
                        <form id="form" action="${pageContext.request.contextPath}/NewBudgetCodeCertificateRequestService" name ="formularioContacto" method="post" >
                            <div class="container">
                                <div class="row">
                                    <div class="col">
                                        <!-- Name input -->
                                        <div class="mb-3">
                                            <label class="form-label" >Nombre</label>
                                            <input class="form-control" name="user" id="nombre" type="text" value="<%=user.getOfficial().getName() + " " + user.getOfficial().getSurname()%>" placeholder="Ingrese el nombre del funcionario" readonly/>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label" >Departamento</label>
                                            <input class="form-control" id="departamento" type="text" value="<%=user.getOfficial().getDepartment().getDescription()%>" placeholder="Ingrese el nombre de su departamento" readonly/>
                                        </div>
                                        <label class="form-label" > Monto</label>
                                        <div class="input-group mb-3">
                                            <span class="input-group-text">₡</span>
                                            <input type="number" name="amount" id="amount" class="form-control" placeholder="Monto" aria-label="Amount (to the nearest dollar)"">
                                            <span class="input-group-text">.00</span>
                                        </div>
                                        <div class="mb-3">
                                            <input name="budget" class="form-control" list="datalistOptions" id="budget" placeholder="Ingrese código o nombre del centro de costos">
                                            <datalist id="datalistOptions">
                                            </datalist>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group mb-3">
                                            <label for="descripcion">Descripción del código presupuestario</label>
                                            <textarea class="form-control" name="description" id="description" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="d-grid gap-2">
                                        <button type="submit" id="btnEnviar" class="btn btn-outline-success text-center">Enviar</button>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>


            </div>
        </main>
        <jsp:directive.include file="../common/footer.jsp"/>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>

        <script src="../assets/js/code-certifications/newBudgetCodeCR.js"></script>
    </body>
</html>
