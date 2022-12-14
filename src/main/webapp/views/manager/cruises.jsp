<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ page import="uz.motordepot.entity.enums.CruiseStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Cruises</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/nav_style.css" type="text/css">
</head>
<body>

<jsp:include page="../../fragments/navbar.jsp"></jsp:include>


<c:if test="${sessionScope.current_user.permissions.contains('ADD_CRUISE')}">

    <%-- Add Cruise --%>
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <a type="button" style="text-align: center" class="btn btn-primary"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_CRUISE_PAGE}&reqsPagination=1&driversPagination=1">
                    Add Cruise
                </a>
            </div>
        </div>
    </div>
</c:if>

<c:choose>

    <c:when test="${sessionScope.current_user.permissions.contains('SHOW_CRUISES')}">


        <%--invalid form--%>
        <div class="container">
            <div class="row">
                <div class="col text-center">
                    <br>
                    <br>
                    <ol class="alert-danger">
                        <c:if test="${sessionScope.invalid_form.currentId!=null}">
                            <div class="text-danger">
                                    ${sessionScope.invalid_form.currentId}
                            </div>
                        </c:if>
                    </ol>

                    <ol class="alert-danger">
                        <c:if test="${sessionScope.invalid_form.status!=null}">
                            <div class="text-danger">
                                    ${sessionScope.invalid_form.status}
                            </div>
                        </c:if>
                    </ol>

                    <ol class="alert-danger">
                        <c:if test="${sessionScope.invalid_form.changedStatus!=null}">
                            <div class="text-danger">
                                    ${sessionScope.invalid_form.changedStatus}
                            </div>
                        </c:if>
                    </ol>
                    <ol class="alert-danger">
                        <c:if test="${sessionScope.invalid_form.canChange!=null}">
                            <div class="text-danger">
                                    ${sessionScope.invalid_form.canChange}
                            </div>
                        </c:if>
                    </ol>

                    <br>
                    <br>

                </div>
            </div>
        </div>


        <div class="registerDiv" id="registerDiv">
            <div id="home" class="main-home">
                <div>
                    <div class="card">
                        <table class="table table-striped table-bordered" id="request-select-table">
                            <thead class="thead-dark">
                            <tr class="column">
                                <td class="column-row">Driver</td>
                                <td class="column-row">Request</td>
                                <td class="column-row">Added At</td>
                                <td class="column-row">Status</td>
                                <td class="column-row">Note</td>
                                <td class="column-row" colspan="2"> Action</td>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${sessionScope.resultPage.items}" var="cruise">
                                <tr class="trHover">

                                    <td class="column-1"><span>
                                        <ol> Car </ol>
                                        <li>${cruise.driver.car.carModel}</li>
                                        <li>${cruise.driver.car.carNumber}</li>
                                        <li>${cruise.driver.car.characteristics}</li>
                                        <br>
                                        <ol> Driver </ol>
                                        <li>${cruise.driver.user.firstName}</li>
                                        <li>${cruise.driver.user.phoneNumber}</li></span>
                                    </td>

                                    <td class="column-1"><span>
                                        <li>${cruise.request.name}</li>
                                        <li>From: ${cruise.request.from}</li>
                                        <li>To: ${cruise.request.to}</li></span>
                                        <li>Characteristics: ${cruise.request.characteristics}</li>
                                        </span>
                                    </td>

                                    <td class="column-1"><span>
                                            ${cruise.addedAt}
                                    </td>

                                    <c:choose>

                                        <c:when test="${sessionScope.current_user.permissions.contains('EDIT_CRUISE_STATUS')}">
                                            <td class="column-row">
                                                <!-- Button trigger modal -->
                                                <button type="button" style="text-align: center" class="btn btn-primary"
                                                        data-toggle="modal"
                                                        data-target="#exampleModal"
                                                        onclick="
                                                                document.getElementById('created').href='${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.CREATED}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}'
                                                                document.getElementById('on_a_way').href='${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.ON_A_WAY}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}'
                                                                document.getElementById('finished').href='${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.FINISHED}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}'
                                                                document.getElementById('completed').href='${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.COMPLETED}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}'
                                                                "
                                                >
                                                        ${cruise.status}
                                                </button>

                                                <!-- Modal -->
                                                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                                                     aria-labelledby="exampleModalLabel"
                                                     aria-hidden="true">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-body">
                                                                <a class="btn btn-light"
                                                                   id="created"
                                                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.CREATED}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}"
                                                                >
                                                                        ${CruiseStatus.CREATED}
                                                                </a>
                                                                <a class="btn btn-light"
                                                                   id="on_a_way"
                                                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.ON_A_WAY}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}"
                                                                >
                                                                        ${CruiseStatus.ON_A_WAY}
                                                                </a>
                                                                <a class="btn btn-light"
                                                                   id="finished"
                                                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.FINISHED}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}">
                                                                        ${CruiseStatus.FINISHED}
                                                                </a>

                                                                <c:if test="${sessionScope.current_user.role == 'MANAGER'}">
                                                                    <a class="btn btn-light"
                                                                       id="completed"
                                                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_STATUS}&status=${CruiseStatus.COMPLETED}&currentId=${cruise.id}&page=${sessionScope.resultPage.currentPage}">
                                                                            ${CruiseStatus.COMPLETED}
                                                                    </a>
                                                                </c:if>

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary"
                                                                        data-dismiss="modal">Close
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </td>
                                        </c:when>

                                        <c:otherwise>
                                            <td class="column-row">
                                                    ${cruise.status}
                                            </td>
                                        </c:otherwise>

                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${sessionScope.current_user.permissions.contains('EDIT_CRUISE_NOTE')}">
                                            <td class="column-row">
                                                <label for="note${cruise.id}"></label>
                                                <input type="text" class="form-control" id="note${cruise.id}"
                                                       onchange="document.getElementById('sending${cruise.id}').href='${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE_NOTE}&currentId=${cruise.id}&note='+document.getElementById('note${cruise.id}').value"
                                                       name="${AttributeParameterHolder.PARAMETER_CRUISE_NOTE}"
                                                       value="${cruise.note}"
                                                       placeholder=" note ">
                                                <a class="btn btn-outline-danger"
                                                   id="sending${cruise.id}"
                                                   href="/"
                                                >
                                                    save
                                                </a>
                                            </td>
                                        </c:when>

                                        <c:otherwise>
                                            <td class="column-1">
                                                    ${cruise.note}
                                            </td>
                                        </c:otherwise>

                                    </c:choose>

                                    <c:if test="${sessionScope.current_user.permissions.contains('EDIT_CRUISE')}">
                                        <td class="column-row">
                                            <a class="btn btn-outline-primary"
                                               href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CRUISE}&currentId=${cruise.id}&reqsPagination=1&driversPagination=1">
                                                EDIT
                                            </a>
                                        </td>
                                    </c:if>

                                    <c:if test="${sessionScope.current_user.permissions.contains('DELETE_CRUISE')}">
                                        <td class="column-row">

                                            <button class="btn btn-outline-danger"
                                                    onclick="document.getElementById('id01').style.display='block'
                                                            document.getElementById('deleting').href ='${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_CRUISE}&currentId=${cruise.id}'">
                                                Delete
                                            </button>

                                            <div id="id01" class="modal">
                                                <div style="align-items: center">
                                                    <h3 style="align-items: center"> Delete </h3>
                                                    <p style="align-items: center"> Are you sure you want to
                                                        delete? </p>

                                                    <div class="column-row">
                                                        <button type="button" class="btn btn-close-white"> Cancel
                                                        </button>
                                                        <a class="btn btn-outline-danger"
                                                           id="deleting"
                                                           href="${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_CRUISE}&currentId=${cruise.id}">
                                                            DELETE
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>

                                            <script>
                                                // Get the modal
                                                var modal = document.getElementById('id01');

                                                // When the user clicks anywhere outside the modal, close it
                                                window.onclick = function (event) {
                                                    if (event.target === modal) {
                                                        modal.style.display = "none";
                                                    }
                                                }
                                            </script>

                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <ul class="pagination justify-content-center">
                            <c:if test="${sessionScope.resultPage.hasPrev}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.CRUISES}&page=${sessionScope.resultPage.prevPage}"
                                       tabindex="-1">Previous</a>
                                </li>
                            </c:if>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.CRUISES}&page=1">1</a>
                            </li>
                            <li class="page-item active">
                                ...
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.CRUISES}&page=${sessionScope.resultPage.totalPages}">
                                        ${sessionScope.resultPage.totalPages+1}
                                </a>
                            </li>
                            <c:if test="${sessionScope.resultPage.hasNext}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.CRUISES}&page=${sessionScope.resultPage.nextPage}">Next</a>
                                </li>
                            </c:if>
                        </ul>

                    </div>
                </div>
            </div>
        </div>
    </c:when>

    <c:otherwise>
        You have no permission to this page
    </c:otherwise>

</c:choose>

<jsp:include page="../../fragments/js.jsp"></jsp:include>

</body>
</html>
