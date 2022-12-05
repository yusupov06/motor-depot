<%@ page import="uz.motordepot.controller.command.CommandType" %>

<div class="container">
    <div class="row">
        <div class="col text-center">
            <!-- Button trigger modal -->
            <button type="button" style="text-align: center" class="btn btn-primary" data-toggle="modal"
                    data-target="#exampleModal">
                Add Request
            </button>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"> Add request </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="register_form"
                      action="${pageContext.request.contextPath}/controller?command=${CommandType.ADD_REQUEST}"
                      class="add-request-content" method="post">

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
                        <label for="to"></label><input type="text" class="form-control" id="characteristics" name="characteristics"
                                                       placeholder=" characteristics ">
                    </div>

                    <div class="form-item">
                        <button type="submit" class="btn btn-block btn-primary">Add</button>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
