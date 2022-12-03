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

    <c:when test="${sessionScope.current_user.permissions.contains('ADD_CRUISE') && sessionScope.editing == null}">
        <div class="registerDiv" id="registerDiv">
            <br>
            <br>
            <br>
            <form action="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_CRUISE}&page=1"
                  id="register_form"
                  class="signup-content" method="post">
                <h1 class="signup-title"> Register Cruise </h1>

                <jsp:include page="fragments/requestsForCruise.jsp"></jsp:include>

                <jsp:include page="fragments/driversForCruise.jsp"></jsp:include>

                <div class="form-item">
                    <button type="submit" class="btn btn-block btn-primary">Register</button>
                </div>
            </form>
        </div>
    </c:when>

    <c:when test="${sessionScope.current_user.permissions.contains('EDIT_CRUISE') && sessionScope.editing != null}">
        <div class="registerDiv" id="registerDiv">
            <br>
            <br>
            <br>
            <form action="${pageContext.request.contextPath}/controller?command=${CommandType.FINISH_EDIT_CRUISE}&page=1&currentId=${sessionScope.editing.id}"
                  id="edit_cruise_form"
                  class="signup-content" method="post">
                <h1 class="signup-title"> Edit Cruise </h1>

                <jsp:include page="fragments/requestsForCruise.jsp"></jsp:include>

                <jsp:include page="fragments/driversForCruise.jsp"></jsp:include>

                <div class="form-item">
                    <button type="submit" class="btn btn-block btn-primary">Edit</button>
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
