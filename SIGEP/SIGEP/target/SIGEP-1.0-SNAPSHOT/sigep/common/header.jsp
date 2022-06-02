<header>
    <div class="navbar navbar-light bg-light pt-0 pb-0">
        <div class="container-fluid">
            <a class="navbar-brand" href="../common/home.jsp"><img src="../assets/img/logo2.jpg" alt="alt"/></a>
            <div class="d-flex nav">
                <li class="nav-item dropdown">
                    <a id="btnLogout" class="nav-link dropdown-toggle bg-light" href="#" id="dropd1" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle"></i> <%=user.getOfficial().getName() + " " + user.getOfficial().getSurname()%> 
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropd1">
                        <li><button class="dropdown-item" onclick="logout()">Cerrar Sesión</button></li>
                    </ul>
                </li>
            </div>
        </div>
    </div>
    <nav class="navbar navbar-expand-lg navbar-dark custom-menu">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#enlaces" aria-controls="enlaces" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="justify-content-center collapse navbar-collapse" id="enlaces">
                <ul class="navbar-nav mb-3 mb-lg-0 p-2 px-2">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="../common/home.jsp">Inicio</a>
                    </li>
                    <% 
                        if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user)) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Presupuesto Municipal
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="../budget/new-budget.jsp">Registrar Partida</a></li>
                            <li><a class="dropdown-item" href="../budget/add-budget-massively.jsp">Registro Masivo</a></li>
                            <li><a class="dropdown-item" href="../budget/budget-list.jsp">Partidas Presupuestarias</a></li>
                            <li><a class="dropdown-item" href="../budget/budget-log.jsp">Registro de Movimientos</a></li>
                        </ul>
                    </li>
                    <% } %>

                    <% 
                        if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user) || User.validateUserRol(user)) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Saldo Presupuestario
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="../balance-certifications/requests.jsp">Solicitudes</a></li>
                            <li><a class="dropdown-item" href="../balance-certifications/certifications.jsp">Certificados</a></li>
                                <% if(User.validateUserRol(user) || User.validateSuperAdminRol(user)) { %>
                            <li><a class="dropdown-item" href="../balance-certifications/create-request.jsp">Nueva Solicitud</a></li>
                                <% } %>
                        </ul>
                    </li>
                    <% } %>
                    <% 
                       if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user) || User.validateUserRol(user)) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Código Presupuestario
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="../code-certifications/requests.jsp">Solicitudes</a></li>
                            <li><a class="dropdown-item" href="../code-certifications/certifications.jsp">Certificados</a></li>
                                <% if(User.validateUserRol(user) || User.validateSuperAdminRol(user)) { %>
                            <li><a class="dropdown-item" href="../code-certifications/create-request.jsp">Nueva Solicitud</a></li>
                                <% } %>
                        </ul>
                    </li>
                    <% } %>

                    <% 
                        if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user) || User.validateUserRol(user)) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Modificaciones
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="../modifications/requests.jsp">Solicitudes</a></li>
                            <li><a class="dropdown-item" href="../modifications/approved-requests.jsp">Aprobadas</a></li>
                                <% if(User.validateUserRol(user) || User.validateSuperAdminRol(user)) { %>
                            <li><a class="dropdown-item" href="../modifications/new-request.jsp">Nueva Solicitud</a></li>
                                <% } %>
                        </ul>
                    </li>
                    <% } %>

                    <% 
                       if(User.validateTenderAdminRol(user) || User.validateSuperAdminRol(user) || User.validateUserRol(user)) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Licitaciones
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="../bidding/initial-acts.jsp">Actos Iniciales</a></li>
                            <li><a class="dropdown-item" href="../bidding/bidding-acts.jsp">Actos de Licitación</a></li>
                            <li><a class="dropdown-item" href="../bidding/purchase-orders.jsp">Órdenes de Compra</a></li>
                                <% 
                           if(User.validateUserRol(user) || User.validateSuperAdminRol(user)) {
                                %>
                            <li><a class="dropdown-item" href="../bidding/new-initial-act.jsp">Nuevo acto inicial</a></li>
                            <li><a class="dropdown-item" href="../bidding/new-purchase-order.jsp">Nueva órden de compra</a></li>
                                <% } %>
                                <% 
                               if(User.validateTenderAdminRol(user) || User.validateSuperAdminRol(user)) {
                                %>
                            <li><a class="dropdown-item" href="../bidding/new-bidding-act.jsp">Nuevo acto de licitación</a></li>
                                <% } %>
                        </ul>
                    </li>

                    <% } %>
                    <% 
                       if(User.validateSuperAdminRol(user)) {
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="../user-roles/user-roles.jsp">
                            Permiso de usuarios
                        </a>
                        <% } %>
                        <form id="logout" style="display: none;" method="post" action="${pageContext.request.contextPath}/LogoutService"></form>
                </ul>
            </div>
        </div>
    </nav>
</header>