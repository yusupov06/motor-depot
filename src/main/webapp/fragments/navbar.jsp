<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="uz.motordepot.entity.enums.UserRole" %>
<%@ page import="uz.motordepot.controller.command.CommandType" %>

<nav class="top-nav navbar-expand-lg navbar-light bg-light">
    <!-- Container wrapper -->
    <div class="container-fluid">
        <!-- Toggle button -->
        <button
                class="navbar-toggler"
                type="button"
                data-mdb-toggle="collapse"
                data-mdb-target="#navbarCenteredExample"
                aria-controls="navbarCenteredExample"
                aria-expanded="false"
                aria-label="Toggle navigation"
        >
            <i class="fas fa-bars"></i>
        </button>

        <!-- Collapsible wrapper -->
        <div
                class="collapse navbar-collapse justify-content-center"
                id="navbarCenteredExample"
        >
            <!-- Left links -->
            <ul class="navbar-nav mb-2 mb-lg-0">


                <li class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.HOME}">Home</a>
                </li>

                <c:if test="${sessionScope.current_user == null}">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.SIGN_IN}">Sign in</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.current_user!=null}">

                    <li class="nav-item">
                        <a class="nav-link" href="/me">My profile</a>
                    </li>

                    <c:if test="${sessionScope.current_user.permissions.contains('SHOW_REQUESTS')}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.REQUESTS}&page=1">Requests</a>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.current_user.permissions.contains('SHOW_CRUISES')}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.CRUISES}&page=1">Cruises</a>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.current_user.permissions.contains('SHOW_CARS')}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.CARS}&page=1">Cars</a>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.current_user.permissions.contains('SHOW_DRIVERS')}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"

                               href="${pageContext.request.contextPath}/controller?command=${CommandType.DRIVERS}&page=1">Drivers</a>
                        </li>
                    </c:if>

                </c:if>

                <c:if test="${sessionScope.current_user != null}">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.LOG_OUT}">Log
                            out</a>
                    </li>
                </c:if>
            </ul>
            <!-- Left links -->
        </div>
        <!-- Collapsible wrapper -->
    </div>
    <!-- Container wrapper -->
</nav>


</nav>




















