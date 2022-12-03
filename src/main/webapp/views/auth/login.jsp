<%@ page import="uz.motordepot.controller.command.CommandType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css">
</head>
<body>


<div class="registerDiv" id="registerDiv">

    <br>
    <br>
    <br>

    <form action="${pageContext.request.contextPath}/controller?command=${CommandType.FINISH_SIGN_IN}"
          id="register_form" class="signup-content" method="post">

        <h1 class="signup-title"> Login </h1>

        <c:if test="${sessionScope.invalid_form.phoneNumber!=null}">
            <div class="text-danger">
                    ${sessionScope.invalid_form.phoneNumber}
            </div>
        </c:if>
        <c:if test="${sessionScope.invalid_user!=null}">
            <div class="text-danger">
                    ${sessionScope.invalid_user}
            </div>
        </c:if>
        <c:if test="${sessionScope.invalid_form.password!=null}">
            <div class="text-danger">
                    ${sessionScope.invalid_form.password}
            </div>
        </c:if>

        <div class="form-item">
            <label for="phoneNumber"></label><input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                              placeholder="Phone number">
        </div>

        <div class="form-item">
            <label for="password"></label><input type="password" class="form-control" name="password" id="password"
                                                 placeholder="Password">
        </div>
        <div class="form-item">
            <button type="submit" class="btn btn-block btn-primary">Login</button>
        </div>
    </form>

    <img class="signin-img" src="../../static/images/signin.svg" alt=" Signin ">

</div>

<jsp:include page="../../fragments/js.jsp"></jsp:include>

</body>
</html>
