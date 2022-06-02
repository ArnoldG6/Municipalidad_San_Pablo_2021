
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
    <jsp:directive.include file="../common/header.jsp"/>
    <body>
        <main class="container mt-5">
            <div class="text-center">
                <div class="card shadow-lg mb-3">
                    <div class="card-header">Registro de Partidas Presupuestarias</div>
                    <div class="card-body">
                        <form id="formData" method="post" action="${pageContext.request.contextPath}/NewCostCenterService">
                            <div class="row">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dynamic_field">
                                        <tr>
                                            <td>
                                                <button type="button" name="add" id="add" class="btn btn-success">+</button>
                                            </td>
                                            <td>
                                                <input required type="text" name="codigo" onkeyup="onchangeInput(this);" placeholder="Ingrese su codigo" class="form-control name_list" />
                                            </td>
                                            <td>
                                                <input required type="text" name="nombre" placeholder="Ingrese el nombre de partida" class="form-control name_list" />
                                            </td>
                                            <td id="amount-data">
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text">₡</span>
                                                    <input type="number" name="amount" id="amount" class="form-control" placeholder="Monto" aria-label="Amount (to the nearest dollar)"">
                                                    <span class="input-group-text">.00</span>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>

                                    <input type="submit" name="submit" id="submit" class="btn btn-success" value="Enviar" />
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>

        <script src="../assets/js/budget/new-budget.js"></script>

    </body>
</html>