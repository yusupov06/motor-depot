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
    <link rel="stylesheet" href="../../static/css/nav_style.css" type="text/css">
</head>
<body>

<jsp:include page="../../fragments/navbar.jsp"></jsp:include>

<c:choose>

    <c:when test="${sessionScope.current_user.permissions.contains('ADD_DRIVER') && sessionScope.editing == null }">

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
                        <c:if test="${sessionScope.invalid_form.email!=null}">
                            <div class="text-danger">
                                    ${sessionScope.invalid_form.email}
                            </div>
                        </c:if>
                    </ol>
                    <br>
                    <br>

                </div>
            </div>
        </div>

        <div class="registerDiv" id="registerDiv">

            <br>
            <br>
            <br>

            <form action="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_DRIVER}"
                  id="register_form"
                  class="signup-content" method="post">
                <h1 class="signup-title"> Register driver </h1>

                <div class="form-item">
                    <label for="firstName"></label>
                    <input type="text" class="form-control" id="firstName"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_FIRSTNAME}"
                           placeholder=" First name ">
                </div>

                <div class="form-item">
                    <label for="lastName"></label>
                    <input type="text" class="form-control" id="lastName"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_LASTNAME}"
                           placeholder=" Last name ">
                </div>

                <div class="form-item">
                    <label for="phoneNumber"></label>
                    <input type="text" class="form-control" id="phoneNumber"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_PHONE_NUMBER}"
                           placeholder="Phone number">
                </div>

                <div class="form-item">
                    <label for="password"></label>
                    <input type="password" class="form-control" id="password"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_PASSWORD}"
                           placeholder=" Password ">
                </div>

                <h1 class="signup-title"> Choose a car </h1>

                <jsp:include page="fragments/carsForDriver.jsp"></jsp:include>

                <div class="form-item">
                    <button type="submit" class="btn btn-block btn-primary">Register</button>
                </div>
            </form>
        </div>

    </c:when>

    <c:when test="${sessionScope.current_user.permissions.contains('EDIT_DRIVER') && sessionScope.editing != null }">

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
                        <c:if test="${sessionScope.invalid_form.email!=null}">
                            <div class="text-danger">
                                    ${sessionScope.invalid_form.email}
                            </div>
                        </c:if>
                    </ol>
                    <br>
                    <br>

                </div>
            </div>
        </div>

        <div class="registerDiv" id="registerDiv">

            <br>
            <br>
            <br>

            <form action="${pageContext.request.contextPath}/controller?command=${CommandType.EDIT_DRIVER}&currentId=${sessionScope.editing.id}"

                  id="edit_form"
                  class="signup-content" method="post">
                <h1 class="signup-title"> Edit driver </h1>

                <div class="form-item">
                    <label for="firstName"></label>
                    <input type="text" class="form-control" id="firstName1"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_FIRSTNAME}"
                           value="${sessionScope.editing.user.firstName}"
                           placeholder=" First name ">
                </div>

                <div class="form-item">
                    <label for="lastName"></label>
                    <input type="text" class="form-control" id="lastName1"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_LASTNAME}"
                           value="${sessionScope.editing.user.lastName}"
                           placeholder=" Last name ">
                </div>

                <div class="form-item">
                    <label for="phoneNumber"></label>
                    <input type="text" class="form-control" id="phoneNumber1"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_PHONE_NUMBER}"
                           value="${sessionScope.editing.user.phoneNumber}"
                           placeholder="Phone number">
                </div>

                <div class="form-item">
                    <label for="password"></label>
                    <input type="password" class="form-control" id="password1"
                           name="${AttributeParameterHolder.PARAMETER_DRIVER_PASSWORD}"
                           value="OLD_PASSWORD"
                           placeholder="New Password">
                </div>

                <h1 class="signup-title"> Choose a car </h1>

                <jsp:include page="fragments/carsForDriver.jsp"></jsp:include>

                <div class="form-item">
                    <button type="submit" class="btn btn-block btn-primary">EDIT</button>
                </div>
            </form>
        </div>

    </c:when>

    <c:otherwise>
        You have no permission to this page
    </c:otherwise>

</c:choose>


<jsp:include page="../../fragments/js.jsp"></jsp:include>

</body>
</html>
