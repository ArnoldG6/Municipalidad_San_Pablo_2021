<%@page import="java.util.Objects"%>
<%@page import="common.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession(true).getAttribute("user");
    if(Objects.isNull(user)) {
        request.getRequestDispatcher("../common/nouser.jsp").forward(request, response);
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
    <%
        if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user)) {
    %>
    <body onload="retrieveAsAdmin();">

        <% } else { %>
    <body onload="retrieveAsUser();">
        <% } %>
        <jsp:directive.include file="../common/header.jsp"/>
        <main class="mt-5">
            <h1 class="d-flex justify-content-center"><span>Certificados de Código</span></h1>
            <div class="container d-flex justify-content-center">
                <table id="certification-list" class="table table-bordered table-hover compact" style="width:100%">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Código de certificado</th>
                            <th>Código de solicitud</th> 
                            <th>Código de partida</th>
                            <th>Transmisor</th>
                            <th>Beneficiario</th> 
                            <th>Monto</th>
                            <th>Bloqueo</th>
                            <th>Documento</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </main>
        <form id="form-ccp" style="display:none;" action="${pageContext.request.contextPath}/BudgetCodeCertificateDocumentService" method="get" >
            <input name="nombre-ccp" id="nombre-ccp">
        </form>
        <jsp:directive.include file="../common/footer.jsp"/>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>

        <script src="../assets/js/code-certifications/certifications.js"></script>
    </body>
</html>
