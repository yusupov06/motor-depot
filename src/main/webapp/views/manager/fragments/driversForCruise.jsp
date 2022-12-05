<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 class="signup-title"> Choose a driver </h1>
<c:choose>
    <c:when test="${sessionScope.driversPage.size >0}">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr class="column">
                <td class="column-row">Car</td>
                <td class="column-row">Driver</td>
                <td class="column-row">Choose</td>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${sessionScope.driversPage.items}" var="car">
                <tr class="trHover">
                    <td class="column-1"><span>
                        <li>
                                ${car.car.carModel}
                        </li>
                        <li>
                                ${car.car.carNumber}
                        </li></span></td>
                    <td class="column-1"><span>
                        <li>
                                ${car.user.firstName}
                        </li></span></td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.editing!=null && sessionScope.editing.driver.id==car.id}">
                                <input class="form-check-input" type="radio"
                                       name="${AttributeParameterHolder.PARAMETER_CRUISE_DRIVER_ID}"
                                       value="${car.id}"
                                       id="driverId" checked>
                                <label class="form-check-label" for="driverId">
                                </label>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio"
                                       name="${AttributeParameterHolder.PARAMETER_CRUISE_DRIVER_ID}"
                                       value="${car.id}"
                                       id="driverId">
                                <label class="form-check-label" for="driverId">
                                </label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:choose>

            <c:when test="${sessionScope.editing!=null}">

                <ul class="pagination justify-content-center">
                    <c:if test="${sessionScope.driversPage.hasPrev}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.currentPage}&driversPagination=${sessionScope.driversPage.prevPage}"
                               tabindex="-1">Previous</a>
                        </li>
                    </c:if>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.currentPage}&driversPagination=1">
                            1
                        </a>
                    </li>
                    <li class="page-item active">
                        ...
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.currentPage}&driversPagination=${sessionScope.driversPage.totalPages}">
                                ${sessionScope.driversPage.totalPages}
                        </a>
                    </li>
                    <c:if test="${sessionScope.driversPage.hasNext}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.nextPage}}&driversPagination=${sessionScope.driversPage.currentPage}"
                            >
                                Next
                            </a>
                        </li>
                    </c:if>
                </ul>

            </c:when>

            <c:otherwise>

                <ul class="pagination justify-content-center">
                    <c:if test="${sessionScope.driversPage.hasPrev}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&reqsPagination=${sessionScope.requestsPage.currentPage}&driversPagination=${sessionScope.driversPage.prevPage}"
                               tabindex="-1">Previous</a>
                        </li>
                    </c:if>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&reqsPagination=${sessionScope.requestsPage.currentPage}&driversPagination=1">
                            1
                        </a>
                    </li>
                    <li class="page-item active">
                        ...
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&reqsPagination=${sessionScope.requestsPage.currentPage}&driversPagination=${sessionScope.driversPage.totalPages}}">
                                ${sessionScope.driversPage.totalPages}
                        </a>
                    </li>
                    <c:if test="${sessionScope.driversPage.hasNext}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&reqsPagination=${sessionScope.requestsPage.nextPage}}&driversPagination=${sessionScope.driversPage.currentPage}}"
                            >
                                Next
                            </a>
                        </li>
                    </c:if>
                </ul>

            </c:otherwise>

        </c:choose>


    </c:when>
    <c:otherwise>
        <h2 class="signup-title"> No drivers </h2>
    </c:otherwise>
</c:choose>

