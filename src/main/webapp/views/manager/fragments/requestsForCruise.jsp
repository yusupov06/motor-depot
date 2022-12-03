<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 class="signup-title"> Choose a request </h1>
<c:choose>
    <c:when test="${sessionScope.requestsPage.size > 0}">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr class="column">
                <td class="column-row">Name</td>
                <td class="column-row">From</td>
                <td class="column-row">To</td>
                <td class="column-row">Choose</td>
            </tr>
            </thead>

            <tbody>

            <c:forEach items="${sessionScope.requestsPage.items}" var="request">
                <tr class="trHover">
                    <td class="column-1"><span> ${request.name} </span></td>
                    <td class="column-1"><span> ${request.from} </span></td>
                    <td class="column-1"><span> ${request.to} </span></td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.editing!=null && sessionScope.editing.request.id == request.id}">
                                <input class="form-check-input" type="radio"
                                       name="${AttributeParameterHolder.PARAMETER_CRUISE_REQUEST_ID}"
                                       value="${request.id}"
                                       id="requestId" checked>
                                <label class="form-check-label" for="requestId">
                                </label>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio"
                                       name="${AttributeParameterHolder.PARAMETER_CRUISE_REQUEST_ID}"
                                       value="${request.id}"
                                       id="requestId">
                                <label class="form-check-label" for="requestId">
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
                    <c:if test="${sessionScope.requestsPage.hasPrev}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.prevPage}&driversPagination=${sessionScope.driversPage.currentPage}"
                               tabindex="-1">Previous</a>
                        </li>
                    </c:if>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=1&driversPagination=${sessionScope.driversPage.currentPage}">
                            1
                        </a>
                    </li>
                    <li class="page-item active">
                        ...
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.totalPages}&driversPagination=${sessionScope.driversPage.currentPage}">
                                ${sessionScope.requestsPage.totalPages}
                        </a>
                    </li>
                    <c:if test="${sessionScope.requestsPage.hasNext}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${sessionScope.editing.id}&reqsPagination=${sessionScope.requestsPage.nextPage}&driversPagination=${sessionScope.driversPage.currentPage}"
                            >
                                Next
                            </a>
                        </li>
                    </c:if>
                </ul>

            </c:when>

            <c:otherwise>

                <ul class="pagination justify-content-center">
                    <c:if test="${sessionScope.requestsPage.hasPrev}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_CRUISE_PAGE}&reqsPagination=${sessionScope.requestsPage.prevPage}&driversPagination=${sessionScope.driversPage.currentPage}"
                               tabindex="-1">Previous</a>
                        </li>
                    </c:if>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_CRUISE_PAGE}&reqsPagination=1&driversPagination=${sessionScope.driversPage.currentPage}">
                            1
                        </a>
                    </li>
                    <li class="page-item active">
                        ...
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_CRUISE_PAGE}&reqsPagination=${sessionScope.requestsPage.totalPages}&driversPagination=${sessionScope.driversPage.currentPage}">
                                ${sessionScope.requestsPage.totalPages}
                        </a>
                    </li>
                    <c:if test="${sessionScope.requestsPage.hasNext}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_CRUISE_PAGE}&reqsPagination=${sessionScope.requestsPage.nextPage}&driversPagination=${sessionScope.driversPage.currentPage}"
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
        <h2 class="signup-title"> No requests </h2>
    </c:otherwise>

</c:choose>
