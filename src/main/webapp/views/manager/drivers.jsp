<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Drivers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/table.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/nav_style.css" type="text/css">
</head>
<body>

<jsp:include page="../../fragments/navbar.jsp"></jsp:include>

<img class="home-img" src="../../static/images/drivers_page.png" alt=" Drivers ">

<c:if test="${sessionScope.current_user.permissions.contains('ADD_DRIVER') }">
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <br>
                <br>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.firstName!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.firstName}
                        </div>
                    </c:if>
                </ol>

                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.lastName!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.lastName}
                        </div>
                    </c:if>
                </ol>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.phoneNumber!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.phoneNumber}
                        </div>
                    </c:if>
                </ol>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.password!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.password}
                        </div>
                    </c:if>
                </ol>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.carId!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.carId}
                        </div>
                    </c:if>
                </ol>
                <br>
                <br>

            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <a type="button" style="text-align: center" class="btn btn-primary"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER_PAGE}&page=1">
                    Add Driver
                </a>
            </div>
        </div>
    </div>
</c:if>


<c:choose>
    <c:when test="${sessionScope.current_user.permissions.contains('SHOW_DRIVERS')}">
        <div class="registerDiv" id="registerDiv">
            <div id="home" class="main-home">
                <div>
                    <div class="card">


                        <table class="table table-striped table-bordered" id="request-select-table">
                            <thead class="thead-dark">
                            <tr class="column">
                                <td class="column-row">#</td>
                                <td class="column-row">Car</td>
                                <td class="column-row">Driver</td>
                                <td class="column-row">Added at</td>
                                <td class="column-row">Status</td>
                                <td class="column-row">Added by</td>
                                <td class="column-row" colspan="2"> Action</td>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${sessionScope.resultPage.items}" var="driver">
                                <tr class="trHover">
                                    <td></td>
                                    <td class="column-1"><span>
                                <li>${driver.car.carModel}</li>
                                <li>${driver.car.carNumber}</li>
                            </span></td>
                                    <td class="column-1"><span>
                                <li>${driver.user.firstName}</li>
                                <li>${driver.user.lastName}</li>
                                <li>${driver.user.phoneNumber}</li>
                            </span></td>
                                    <td class="column-1">${driver.addedAt}</td>
                                    <td class="column-1">${driver.status}</td>
                                    <td>${driver.addedBy}</td>
                                    <td class="column-row">
                                        <a class="btn btn-outline-primary"
                                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_DRIVER_PAGE}&currentId=${driver.id}&page=1">
                                            EDIT
                                        </a>
                                    </td>
                                    <td class="column-row">

                                        <button class="btn btn-outline-danger"
                                                onclick="document.getElementById('id01').style.display='block'
                                                        document.getElementById('deleting').href ='${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_DRIVER}&currentId=${driver.id}'">
                                            Delete
                                        </button>

                                        <div id="id01" class="modal">
                                            <div style="align-items: center">
                                                <h3 style="align-items: center"> Delete </h3>
                                                <p style="align-items: center"> Are you sure you want to delete? </p>

                                                <div class="column-row">
                                                    <button type="button" class="btn btn-close-white"> Cancel</button>
                                                    <a class="btn btn-outline-danger" id="deleting">
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
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <ul class="pagination justify-content-center">
                            <c:if test="${sessionScope.resultPage.hasPrev}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.DRIVERS}&page=${sessionScope.resultPage.prevPage}"
                                       tabindex="-1"> < </a>
                                </li>
                            </c:if>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.DRIVERS}&page=1">1</a>
                            </li>
                            <li class="page-item active">
                                ...
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=${CommandType.DRIVERS}&page=${sessionScope.resultPage.totalPages}">
                                        ${sessionScope.resultPage.totalPages+1}
                                </a>
                            </li>
                            <c:if test="${sessionScope.resultPage.hasNext}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.DRIVERS}&page=${sessionScope.resultPage.nextPage}">
                                        > </a>
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
