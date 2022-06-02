<%@page import="java.util.Objects"%>
<%@page import="common.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession(true).getAttribute("user");
    if(Objects.isNull(user)) {
        request.getRequestDispatcher("./nouser.jsp").forward(request, response);
    } else if(!User.validateBudgetAdminRol(user) && !User.validateSuperAdminRol(user)) {
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
    <body>
        <jsp:directive.include file="../common/header.jsp"/>
        <main class="mt-5">
            <main class="mt-5">
                <div class="card mx-auto mt-3 shadow-lg rounded" style="width: 40%;">
                    <p class="card-header">Agregar Centros de Costo</p>
                    <div class="card-body">
                        <form id="form" enctype="multipart/form-data" action="${pageContext.request.contextPath}/XLSCostCenterRegisterService" method="post">
                            <div class="mb-1 fbtn">
                                <div class="d-flex">
                                    <label for="excel" class="me-4">Adjuntar archivo excel + </label>
                                    <input type="file" accept=".xlsx" name="excel" id="excel">
                                    <h6 id="nameDoc2"></h6>
                                </div>
                            </div>
                            <div id="loading" style="visibility: hidden" class="d-flex align-items-center">
                                <span>Cargando datos...</span>
                                <div class="spinner">
                                    <div class="rect1"></div>
                                    <div class="rect2"></div>
                                    <div class="rect3"></div>
                                    <div class="rect4"></div>
                                    <div class="rect5"></div>
                                </div>
                            </div>
                            <div class="card-body text-center">
                                <button type="submit" class="btn btn-success me-2 mb-3" >Enviar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </main>
        </main>
        <jsp:directive.include file="../common/footer.jsp"/>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>   

        <script src="../assets/js/budget/add-budget-massively.js"></script>
    </body>
</html>