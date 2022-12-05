<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/table.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/nav_style.css" type="text/css">
</head>
<body>
<jsp:include page="../../fragments/navbar.jsp"></jsp:include>

<img class="home-img" src="../../static/images/requests.jpg" alt=" Requests ">

<c:if test="${sessionScope.current_user.permissions.contains('EDIT_REQUEST')
              || sessionScope.current_user.permissions.contains('ADD_REQUEST') }">

    <div class="container">
        <div class="row">
            <div class="col text-center">
                <br>
                <br>
                <ol class="alert-danger">
                    <c:if test="${requestScope.containsKey('invalidEditing')}">
                        <div class="text-danger">
                                ${sessionScope.invalidEditing}
                        </div>
                    </c:if>
                </ol>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.name!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.name}
                        </div>
                    </c:if>
                </ol>

                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.from!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.from}
                        </div>
                    </c:if>
                </ol>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.to!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.to}
                        </div>
                    </c:if>
                </ol>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.range!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.range}
                        </div>
                    </c:if>
                </ol>
                <br>
                <br>

            </div>
        </div>
    </div>

</c:if>

<c:if test="${sessionScope.current_user.permissions.contains('EDIT_REQUEST') && sessionScope.editing!=null}">

    <div class="registerDiv" id="registerDiv">

        <h1 class="signup-title"> Edit request </h1>

        <form id="register_form"
              action="${pageContext.request.contextPath}/controller?command=${CommandType.FINISH_EDIT_REQUEST}&currentId=${sessionScope.editing.id}"
              class="add-request-content" method="post">

            <div class="form-item">
                <label for="name"></label><input type="text" class="form-control" id="name"
                                                 name="${AttributeParameterHolder.PARAMETER_REQUEST_NAME}"
                                                 value="${sessionScope.editing.name}"
                                                 placeholder=" Name of request ">
            </div>

            <div class="form-item">
                <label for="from"></label><input type="text" class="form-control" id="from"
                                                 name="${AttributeParameterHolder.PARAMETER_REQUEST_FROM}"
                                                 value="${sessionScope.editing.from}"
                                                 placeholder=" from ">
            </div>

            <div class="form-item">
                <label for="to"></label><input type="text" class="form-control" id="to"
                                               name="${AttributeParameterHolder.PARAMETER_REQUEST_TO}"
                                               value="${sessionScope.editing.to}"
                                               placeholder=" to ">
            </div>

            <div class="form-item">
                <label for="to"></label><input type="text" class="form-control" id="characteristics"
                                               name="${AttributeParameterHolder.PARAMETER_REQUEST_CHARAC}"
                                               value="${sessionScope.editing.characteristics}"
                                               placeholder=" Characteristics ">
            </div>

            <div class="form-item">
                <button type="submit" class="btn btn-block btn-primary">Edit</button>
            </div>
            <div class="form-item">
                <a href="${pageContext.request.contextPath}/controller?command=${CommandType.REQUESTS}&page=${sessionScope.resultPage.currentPage}"
                   class="btn btn-block btn-danger">Cancel</a>
            </div>

        </form>
    </div>

</c:if>

<c:if test="${sessionScope.current_user.permissions.contains('ADD_REQUEST')}">

    <jsp:include page="../../fragments/addRequestModal.jsp"></jsp:include>
</c:if>

<c:choose>
    <%-- SHOW_REQUESTS permission --%>
    <c:when test="${sessionScope.current_user.permissions.contains('SHOW_REQUESTS')}">

        <div class="container">
            <div class="row">
                <div class="col text-center">
                    <ol class="alert-danger">
                        <c:choose>
                            <c:when test="${sessionScope.invalidDeleting!=null}">
                                <div class="text-danger">
                                        ${sessionScope.invalidDeleting}
                                </div>
                            </c:when>
                        </c:choose>

                    </ol>
                </div>
            </div>
        </div>

        <div class="registerDiv" id="registerDiv">
            <div id="home" class="main-home">
                <div>
                    <div class="card">


                        <table class="table table-striped table-bordered" id="request-select-table">
                            <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Request</th>
                                <th>From</th>
                                <th>To</th>
                                <th>Characteristics</th>
                                <th>Status</th>
                                <th>Added at</th>
                                <th>Added by</th>
                                <th colspan="2">Action</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${sessionScope.resultPage.items}" var="request">

                                <tr>
                                    <td></td>
                                    <td><span> ${request.name} </span></td>
                                    <td><span> ${request.from} </span></td>
                                    <td><span> ${request.to} </span></td>
                                    <td><span> ${request.characteristics} </span></td>
                                    <td><span> ${request.status} </span></td>
                                    <td><span> ${request.addedAt} </span></td>
                                    <td><span> ${request.addedBy} </span></td>

                                    <td>
                                        <a class="btn btn-outline-primary"
                                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_REQUEST}&currentId=${request.id}">
                                            EDIT
                                        </a>
                                    </td>

                                    <td>

                                        <button class="btn btn-outline-danger"
                                                onclick="document.getElementById('id01').style.display='block'
                                                        document.getElementById('deleting').href ='${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_REQUEST}&currentId=${request.id}'">
                                            Delete
                                        </button>

                                        <div id="id01" class="modal">
                                            <div style="align-items: center">
                                                <h3 style="align-items: center"> Delete </h3>
                                                <p style="align-items: center"> Are you sure you want to delete? </p>

                                                <div class="column-row">
                                                    <button type="button" class="btn btn-close-white"> Cancel</button>
                                                    <a class="btn btn-outline-danger"
                                                       id="deleting"
                                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_REQUEST}&currentId=${request.id}">
                                                        DELETE
                                                    </a>
                                                </div>
                                            </div>
                                        </div>

                                    </td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>

            <ul class="pagination justify-content-center">

                <c:if test="${sessionScope.resultPage.hasPrev}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.REQUESTS}&page=${sessionScope.resultPage.prevPage}"
                           tabindex="-1">Previous</a>
                    </li>
                </c:if>

                <li class="page-item">
                    <a
                            class="page-link"
                            href="${pageContext.request.contextPath}/controller?command=${CommandType.REQUESTS}&page=1">
                        1
                    </a>
                </li>
                <li class="page-item active">
                    ...
                </li>
                <li class="page-item">
                    <a
                            class="page-link"
                            href="${pageContext.request.contextPath}/controller?command=${CommandType.REQUESTS}&page=${sessionScope.resultPage.totalPages}">${sessionScope.resultPage.totalPages}
                    </a>
                </li>
                <c:if test="${sessionScope.resultPage.hasNext}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.REQUESTS}&page=${sessionScope.resultPage.nextPage}">Next</a>
                    </li>
                </c:if>
            </ul>

        </div>
    </c:when>

    <c:otherwise>
        <h1> You have no permission to this page </h1>
    </c:otherwise>

</c:choose>

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

<jsp:include page="../../fragments/js.jsp"></jsp:include>
</body>
</html>
