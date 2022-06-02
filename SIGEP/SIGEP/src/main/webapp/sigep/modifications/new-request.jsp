<%@page import="java.util.Objects"%>
<%@page import="common.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession(true).getAttribute("user");
    if(Objects.isNull(user)) {
        request.getRequestDispatcher("./nouser.jsp").forward(request, response);
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
        <div class="container py-4" style=" z-index:1;">
            <div class="card shadow-lg mb-3" style="max-width: 95rem;">
                <div class="card-header">Solicitar modificación presupuestaria</div>
                <div class="card-body">
                    <form  id="form" action="${pageContext.request.contextPath}/NewBudgetModificationRequestService" method="post">
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <!-- Name input -->
                                    <div class="mb-3">
                                        <label class="form-label" >Funcionario</label>
                                        <input class="form-control" name="user" id="nombre" type="text" value= "<%=user.getOfficial().getName()%>  <%=user.getOfficial().getSurname()%>" placeholder="Ingrese el nombre del funcionario" readonly/>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label" >Departamento</label>
                                        <input class="form-control" id="departamento" type="text" value="<%=user.getOfficial().getDepartment().getDescription()%>" placeholder="Ingrese el nombre de su departamento" readonly />
                                    </div>
                                </div>

                                <!-- Columna de centros de costos por aumentar/disminuir -->
                                <div class="col">
                                    <!-- INCREASE -->
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="dynamic_field">
                                            <tr>
                                                <td>
                                                    <button type="button" name="add" id="add" class="btn btn-success">+</button>
                                                </td>
                                                <td>
                                                    <label class="form-label">Nombre del centro de costos por disminuir:</label>
                                                    <input name="decreaseBudget1" class="form-control mb-3" list="datalistOptions" id="decreaseBudget" placeholder="Digite el codigo o nombre del centro de costos" onchange="retrieveBudgetDataBySearchParameter();">
                                                    <datalist id="datalistOptions">
                                                    </datalist>
                                                </td>
                                                <td>
                                                    <label class="form-label" >Monto</label>
                                                    <br>
                                                    <div class="input-group mb-3">
                                                        <span class="input-group-text">₡</span>
                                                        <input type="number" id="amount1" name="amount1" class="form-control" min="100" placeholder="Monto" aria-label="Amount (to the nearest dollar)">
                                                        <span class="input-group-text">.00</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label class="form-label"></label>
                                                </td>
                                                <td>                                        
                                                    <label class="form-label">Nombre del centro de costos por aumentar:</label>
                                                    <input name="increaseBudget1" class="form-control mb-3" list="datalistOptions" id="increaseBudget" placeholder="Digite el codigo o nombre del centro de costos" onchange="retrieveBudgetDataBySearchParameter();">
                                                    <datalist id="datalistOptions">
                                                    </datalist>
                                                </td>

                                            </tr> 
                                        </table>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <input type="submit" name="submit" id="submit" class="btn btn-success mb-3" value="Enviar" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>
        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>                            

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>
        <script src="../assets/js/modifications/new-request.js" type="text/javascript"></script>
    </body>
</html>