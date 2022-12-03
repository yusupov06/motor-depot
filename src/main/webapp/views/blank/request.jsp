<%--
  Created by IntelliJ IDEA.
  User: muhammadqodir
  Date: 13/11/22
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<!-- Extra large modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-xl">
    Add Cruise
</button>

<div class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog"
     aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"> Add Cruise </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"
                        data-target=".bd-example-modal-xl">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <br>
            <div class="main-home">

                <form id="register_form" class="add-report-content" method="post">

                    <label for="request-select-table"> Choose a request </label>

                    <table class="table table-striped table-bordered" id="request-select-table">
                        <thead class="thead-dark">
                        <tr class="column">
                            <td class="column-row"> Request</td>
                            <td class="column-row"> From</td>
                            <td class="column-row"> To range</td>
                            <td class="column-row"> Cruising Range</td>
                            <td class="column-row"> Select Request</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requests}" var="car">
                            <tr class="trHover">
                                <td class="column-1"><span> ${car.name} </span></td>
                                <td class="column-1"><span> ${car.from} </span></td>
                                <td class="column-1"><span> ${car.to} </span></td>
                                <td class="column-1"><span> ${car.cruisingRange} </span></td>
                                <td class="column-7"><label for="radio"></label><input type="radio"
                                                                                       id="radio" checked/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <table class="table table-striped table-bordered" id="driver-select-table">
                        <thead class="thead-dark">
                        <tr class="column">
                            <td class="column-row"> Car</td>
                            <td class="column-row"> Driver</td>
                            <td class="column-row"> Select Driver</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${drivers}" var="driver">
                            <tr class="trHover">
                                <td class="column-1"><span> ${driver.car.carModel} </span></td>
                                <td class="column-1"><span> ${driver.driverName} </span></td>
                                <td class="column-7"><label for="radio"></label><input type="radio"
                                                                                       id="radio2" />
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"> Back</button>
            </div>
        </div>
    </div>
</div>


<!--            List of reports -->

<table class="table table-striped table-bordered">
    <thead class="thead-dark">
    <tr class="column">
        <td class="column-row"> ID</td>
        <td class="column-row"> Status</td>
        <td class="column-row"> Cruising range</td>
        <td class="column-row"> Destination</td>
        <td class="column-row"> Driver</td>
        <td class="column-row"> Car</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cruises}" var="cruises">
        <tr class="column">
            <td>${cruises.id}</td>
            <td>${cruises.status}</td>
            <td>${cruises.request.cruisingRange}</td>
            <td>
                <li>
                    name : ${cruises.request.name}
                </li>
                <li>
                    from: ${cruises.request.from}
                </li>
                <li>
                    to: ${cruises.request.to}
                </li>
            </td>
            <td>
                    ${cruises.driver.driverName}
            </td>
            <td>
                <li>
                    model : ${cruises.driver.car.carModel}
                </li>
                <li>
                    number: ${cruises.driver.car.carNumber}
                </li>
                <li>
                    condition: ${cruises.driver.car.condition}
                </li>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</div>

</div>
</div>

</body>
</html>



<div class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog"
     aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"> Add Request </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"
                        data-target=".bd-example-modal-xl">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <br>
            <div class="main-home">

                <form id="register_form" class="add-report-content" method="post">

                    <div class="form-item">
                        <label for="name"></label><input type="text" class="form-control" id="name" name="name"
                                                         placeholder=" Name of request ">
                    </div>

                    <div class="form-item">
                        <label for="from"></label><input type="text" class="form-control" id="from" name="from"
                                                         placeholder=" from ">
                    </div>

                    <div class="form-item">
                        <label for="to"></label><input type="text" class="form-control" id="to" name="to"
                                                       placeholder=" to ">
                    </div>

                    <div class="form-item">
                        <label for="range"></label><input type="text" class="form-control" id="range" name="range"
                                                          placeholder=" cruising range ">
                    </div>

                    <div class="form-item">
                        <div class="offer-register-container">
                            <a class="btn btn-outline-custom"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_REQUEST}">
                                Add </a>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"> Back</button>
            </div>
        </div>
    </div>
</div>
