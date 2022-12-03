<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cars</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/table.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/nav_style.css" type="text/css">
</head>
<body>

<jsp:include page="../../fragments/navbar.jsp"></jsp:include>

<img class="home-img" src="../../static/images/cars.webp" alt=" Cars ">


<c:if test="${sessionScope.current_user.permissions.contains('EDIT_CAR') && sessionScope.editing!=null}">

    <div class="container">
        <div class="row">
            <div class="col text-center">
                <br>
                <br>
                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.carModel!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.carModel}
                        </div>
                    </c:if>
                </ol>

                <ol class="alert-danger">
                    <c:if test="${sessionScope.invalid_form.carNumber!=null}">
                        <div class="text-danger">
                                ${sessionScope.invalid_form.carNumber}
                        </div>
                    </c:if>
                </ol>
                <br>
                <br>

            </div>
        </div>
    </div>

    <div class="registerDiv" id="registerDiv">

        <h1 class="signup-title"> Edit car </h1>

        <form id="register_form"
              action="${pageContext.request.contextPath}/controller?command=${CommandType.FINISH_EDIT_CAR}&currentId=${sessionScope.editing.id}"
              class="add-request-content" method="post">

            <div class="form-item">
                <label for="carModel"></label>
                <input type="text" class="form-control"
                       id="carModel" name="${AttributeParameterHolder.PARAMETER_CAR_MODEL}"
                       value="${sessionScope.editing.carModel}"
                       placeholder=" car model ">
            </div>

            <div class="form-item">
                <label for="carNumber"></label>
                <input type="text" class="form-control"
                       id="carNumber" name="${AttributeParameterHolder.PARAMETER_CAR_NUMBER}"
                       value="${sessionScope.editing.carNumber}"
                       placeholder=" car number ">
            </div>


            <div class="form-item">
                <button type="submit" class="btn btn-block btn-primary">Edit</button>
            </div>

            <div class="form-item">
                <a href="${pageContext.request.contextPath}/controller?command=${CommandType.CARS}&page=${sessionScope.resultPage.currentPage}"
                   class="btn btn-block btn-danger">Cancel</a>
            </div>

        </form>
    </div>

</c:if>

<c:if test="${sessionScope.current_user.permissions.contains('ADD_CAR') && sessionScope.editing==null}">

    <div class="container">
        <div class="row">
            <div class="col text-center">
                <br>
                <br>
                <ol class="alert-danger">
                    <c:if test="${invalid_form.carModel!=null}">
                        <div class="text-danger">
                                ${invalid_form.carModel}
                        </div>
                    </c:if>
                </ol>

                <ol class="alert-danger">
                    <c:if test="${invalid_form.carNumber!=null}">
                        <div class="text-danger">
                                ${invalid_form.carNumber}
                        </div>
                    </c:if>
                </ol>
                <br>
                <br>

            </div>
        </div>
    </div>

    <jsp:include page="../../fragments/addCarModal.jsp"></jsp:include>
</c:if>

<c:choose>

    <c:when test="${sessionScope.current_user.permissions.contains('SHOW_CARS')}">

        <div class="container">
            <div class="row">
                <div class="col text-center">
                    <ol class="alert-danger">
                        <div class="text-danger">
                                ${sessionScope.invalidDeleting}
                        </div>
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
                            <tr class="column">
                                <td class="column-row">#</td>
                                <td class="column-row">Car Model</td>
                                <td class="column-row">Car number</td>
                                <td class="column-row">Condition</td>
                                <td class="column-row">Added at</td>
                                <td class="column-row">Added by</td>
                                <td colspan="2">Action</td>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${sessionScope.resultPage.items}" var="car">
                                <tr class="trHover">
                                    <td></td>
                                    <td class="column-1"><span> ${car.carModel} </span></td>
                                    <td class="column-1"><span> ${car.carNumber} </span></td>
                                    <td class="column-1"><span> ${car.condition} </span></td>
                                    <td class="column-1"><span> ${car.addedAt} </span></td>
                                    <td class="column-1"><span> ${car.addedBy} </span></td>

                                    <td class="column-row">
                                        <a class="btn btn-outline-primary"
                                           href="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_CAR}&currentId=${car.id}">
                                            Edit
                                        </a>
                                    </td>
                                    <td class="column-row">

                                        <button class="btn btn-outline-danger"
                                                onclick="document.getElementById('id01').style.display='block'
                                                        document.getElementById('deleting').href ='${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_CAR}&currentId=${car.id}'">
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
                                                       href="${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_CAR}&currentId=${car.id}">
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

                    </div>
                </div>
            </div>

            <ul class="pagination justify-content-center">
                <c:if test="${sessionScope.resultPage.hasPrev}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.CARS}&page=${sessionScope.resultPage.prevPage}"
                           tabindex="-1">Previous</a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.CARS}&page=1">1</a>
                </li>
                <li class="page-item active">
                    ...
                </li>
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.CARS}&page=${sessionScope.resultPage.totalPages}">
                            ${sessionScope.resultPage.totalPages}
                    </a>
                </li>
                <c:if test="${sessionScope.resultPage.hasNext}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.CARS}&page=${sessionScope.resultPage.nextPage}">Next</a>
                    </li>
                </c:if>
            </ul>

        </div>
    </c:when>

    <c:otherwise>

        <h1> You have no permission to this page </h1>

    </c:otherwise>


</c:choose>

<jsp:include page="../../fragments/js.jsp"></jsp:include>
</body>
</html>
