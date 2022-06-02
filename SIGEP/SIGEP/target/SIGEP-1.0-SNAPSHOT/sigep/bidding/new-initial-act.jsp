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
    <body>
        <jsp:directive.include file="../common/header.jsp"/>
        <main class="mt-5">
            <div class="card mx-auto mt-3 shadow-lg rounded" style="width: 40%;">
                <p class="card-header">Acto inicial de contratación</p>
                <div class="card-body">
                    <form id="form" enctype="multipart/form-data" action="${pageContext.request.contextPath}/NewInitialBiddingActRequest" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Funcionario: </label>
                            <input value= "<%=user.getOfficial().getName()%>  <%=user.getOfficial().getSurname()%>" type="text" class="form-control" id="name" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="depto" class="form-label">Departamento: </label>
                            <input value="<%=user.getOfficial().getDepartment().getDescription()%>" type="text" class="form-control" id="depto" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="csp" class="form-label">Código del certificado de saldo </label>
                            <input name="csp" class="form-control" list="datalistOptions" id="csp" placeholder="Ingrese el certificado">
                            <datalist id="datalistOptions">
                            </datalist>
                        </div>
                        <div class="mb-1 fbtn">
                            <div class="d-flex">
                                <label for="doc2" class="me-4">Adjuntar acto inicial de licitación + </label>
                                <input type="file" accept=".pdf" name="doc2" id="doc2">
                                <h6 id="nameDoc2"></h6>
                            </div> 
                        </div>
                        <div class="card-body text-center">
                            <button type="submit" class="btn btn-success me-2 mb-3" >Enviar</button>
                        </div>
                    </form>
                </div>

            </div>
        </main>
        <jsp:directive.include file="../common/footer.jsp"/>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>
        
        <script src="../assets/js/bidding/new-initial-act.js"></script>
    </body>
</html>
