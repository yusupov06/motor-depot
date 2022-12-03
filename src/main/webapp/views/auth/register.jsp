
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uz.motordepot.controller.command.CommandType"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/main.css">
</head>
<body>


<div class="registerDiv" id="registerDiv">

    <br>
    <br>
    <br>

    <form action="${pageContext.request.contextPath}/auth/register" id="register_form" class="signup-content" method="post" >
        <h1 class="signup-title"> Registration form </h1>

        <div class="form-item">
            <label for="fullname"></label><input type="text" class="form-control" id="fullname" placeholder=" To'liq ism va sharifingiz ">
        </div>

        <div class="form-item">
            <label for="username"></label><input type="text" class="form-control" id="username" placeholder=" Login ">
        </div>

        <div class="form-item">
            <label for="password"></label><input type="password" class="form-control" id="password" placeholder=" Parol ">
        </div>

        <div class="form-item">
            <button type="submit" class="btn btn-block btn-primary"> Register </button>
            <small> Already registered </small>
            <div class="offer-register-container">
                <a class="btn btn-outline-custom" href="${pageContext.request.contextPath}/controller?command=${CommandType.SIGN_IN}"> Login </a>
            </div>
        </div>
    </form>

    <img class="signin-img" src="../../static/images/signin.svg" alt=" Signin ">

</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
