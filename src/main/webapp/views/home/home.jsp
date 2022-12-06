<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="uz.motordepot.controller.navigation.AttributeParameterHolder" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/nav_style.css" type="text/css">
</head>
<body>
<jsp:include page="../../fragments/navbar.jsp"></jsp:include>

<h3 style="text-align: center"> Welcome to Motor Depot </h3>

<img class="home-img" src="../../static/images/home.svg" alt=" Motor Depot ">

<div class="registerDiv" id="registerDiv">
    <div id="home" class="main-home">
        <div>
            <div class="card">
                <div class="card-body">
                    <h5> ${sessionScope.current_user.firstName} </h5>
                    <h5> ${sessionScope.current_user.lastName} </h5>
                    <h5> ${sessionScope.current_user.phoneNumber} </h5>
                    <h5> ${sessionScope.current_user.role} </h5>
                    <h5> ${sessionScope.current_user.permissions} </h5>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../../fragments/js.jsp"></jsp:include>
</body>
</html>
