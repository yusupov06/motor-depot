<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>

    <c:when test="${sessionScope.current_user.permissions.contains('ADD_DRIVER') && sessionScope.editing == null }">

        <table class="table table-striped">
            <thead class="thead-dark">
            <tr class="column">
                <td class="column-row">Model</td>
                <td class="column-row">Number</td>
                <td class="column-row">Characteristics</td>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${sessionScope.resultPage.items}" var="car">
                <tr class="trHover">
                    <td class="column-1"><span> ${car.carModel} </span></td>
                    <td class="column-1"><span> ${car.carNumber} </span></td>
                    <td class="column-1"><span> ${car.charcateristics} </span></td>
                    <td>
                        <input class="form-check-input" type="radio"
                               name="${AttributeParameterHolder.PARAMETER_CAR_ID}"
                               value="${car.id}"
                               id="carId">
                        <label class="form-check-label" for="carId">
                        </label>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="pagination justify-content-center">
            <c:if test="${sessionScope.resultPage.hasPrev}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&page=${sessionScope.resultPage.prevPage}"
                       tabindex="-1"> < </a>
                </li>
            </c:if>
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&page=1">1</a>
            </li>
            <li class="page-item active">
                ...
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&page=${sessionScope.resultPage.totalPages}">
                        ${sessionScope.resultPage.totalPages+1}
                </a>
            </li>
            <c:if test="${sessionScope.resultPage.hasNext}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&page=${sessionScope.resultPage.nextPage}">
                        > </a>
                </li>
            </c:if>
        </ul>

    </c:when>

    <c:when test="${sessionScope.current_user.permissions.contains('EDIT_DRIVER') && sessionScope.editing != null }">

        <table class="table table-striped">
            <thead class="thead-dark">
            <tr class="column">
                <td class="column-row">Model</td>
                <td class="column-row">Number</td>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${sessionScope.resultPage.items}" var="car">
                <tr class="trHover">
                    <td class="column-1"><span> ${car.carModel} </span></td>
                    <td class="column-1"><span> ${car.carNumber} </span></td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.editing.car.id==car.id}">
                                <input class="form-check-input" type="radio"
                                       name="${AttributeParameterHolder.PARAMETER_CAR_ID}"
                                       value="${car.id}"
                                       id="carId2" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio"
                                       name="${AttributeParameterHolder.PARAMETER_CAR_ID}"
                                       value="${car.id}"
                                       id="carId2">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="carId">
                        </label>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="pagination justify-content-center">
            <c:if test="${sessionScope.resultPage.hasPrev}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_DRIVER_PAGE}&page=${sessionScope.resultPage.prevPage}"
                       tabindex="-1"> < </a>
                </li>
            </c:if>
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_DRIVER_PAGE}&page=1">1</a>
            </li>
            <li class="page-item active">
                ...
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_DRIVER_PAGE}&page=${sessionScope.resultPage.totalPages}">
                        ${sessionScope.resultPage.totalPages+1}
                </a>
            </li>
            <c:if test="${sessionScope.resultPage.hasNext}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_DRIVER_PAGE}&page=${sessionScope.resultPage.nextPage}">
                        > </a>
                </li>
            </c:if>
        </ul>

    </c:when>

</c:choose>

