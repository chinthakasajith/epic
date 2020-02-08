<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : mahesh_m
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addTaskform.action = "${pageContext.request.contextPath}/AddTaskMgtServlet";
                document.addTaskform.submit();

            }
            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadTaskMgtServlet";

            }

            function invokeUpdateReset(taskCode) {

                window.location = "${pageContext.request.contextPath}/UpdatetaskMgtServlet?taskCode=" + taskCode;

            }

            function invokeUpdate()
            {

                document.updateTaskform.action = "${pageContext.request.contextPath}/UpdateConfiremedTaskMgtServlet";
                document.updateTaskform.submit();

            }

            function ConfirmDelete(taskCode)
            {
//                answer = confirm("Do you really want to delete this Task ?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteTaskServlet?taskCode="+taskCode;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadTaskMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete this Task ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "Yes": function () {
                            window.location="${pageContext.request.contextPath}/DeleteTaskServlet?taskCode="+taskCode;
                        },
                        Cancel: function () {
                            window.location="${pageContext.request.contextPath}/LoadTaskMgtServlet";
                        }
                    }
                });

            }



        </script>  
        <script>


            $(document).ready(function () {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });

        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.TASK%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }



        </script>

    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">




                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='default'}">
                                    <form action="" method="POST" name="addTaskform" >
                                        <table>
                                            <tr>
                                                <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                    <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table>


                                                <tbody>

                                                    <tr>
                                                        <td>Task Code</td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagecode" class="inputfield-mandatory" maxlength="8" value='${TaskBean.taskCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="taskdescription" class="inputfield-Description-mandatory" maxlength="20" value='${TaskBean.description}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>



                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${TaskBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${TaskBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagesortkey" class="inputfield-mandatory" maxlength="5"  value='${TaskBean.sortKey}'></td>
                                                    <td></td>
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" name="add" value="Add" onclick="invokeAdd()" style="width: 100px;"/>
                                                        <input type="button" name="reset" value="Reset" onclick="invokeReset()" style="width: 100px;"/>
                                                    </td>
                                                    <td></td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewtaskform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                                <td><font color="green"><b> ${successMsg}</b></font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Task Code</td>
                                                    <td></td>
                                                    <td> :</td>
                                                    <td></td>
                                                    <td>${task.taskCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> :</td>
                                                    <td></td>
                                                    <td>${task.description}</td>
                                                </tr>
                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${task.sortKey}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${task.statusDes}</td>
                                                </tr>

                                                <tr>
                                                    <td> </td>
                                                    <td></td>
                                                    <td> </td>
                                                    <td></td>
                                                    <td><input type="reset" value="Back" name="back" onclick="invokeReset()" style="width: 100px;"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateTaskform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                                <td><font color="green"><b> ${successMsg}</b></font> </td>
                                                <td></td>

                                            </tr>
                                        </table>
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Task Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagecode" readonly="true" class="inputfield-mandatory" maxlength="8" value='${TaskBean.taskCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="taskdescription" class="inputfield-Description-mandatory" maxlength="20" value='${TaskBean.description}'></td>
                                                    <td></td>
                                                </tr>


                                                <tr>
                                                    <td>Status</td>

                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${TaskBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${TaskBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>

                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagesortkey" class="inputfield-mandatory" value="${TaskBean.sortKey}" maxlength="5"></td>
                                                    <td></td>
                                                </tr>
                                            <td></td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td><input type="submit" value="Update" name="update" onclick="invokeUpdate()" style="width: 100px;">
                                                    <input type="button" value="Reset" name="reset" onclick="invokeUpdateReset('${task.taskCode}')" style="width: 100px;"/></td>
                                                    <%--    <input type="reset" value="Reset" name="reset"></td>
                                                    --%>
                                                <td></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Task Code</th>
                                            <th>Description</th>
                                            <th>Sort key</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="task" items="${sessionScope.SessionObject.taskDetails}">
                                            <tr>

                                                <td >${task.taskCode}</td>
                                                <td >${task.description}</td>
                                                <td >${task.sortKey}</td>
                                                <td >${task.statusDes}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewTaskMgtServlet?taskCode=<c:out value="${task.taskCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdatetaskMgtServlet?taskCode=<c:out value="${task.taskCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${task.taskCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 



                                <!--   ------------------------- end developer area  --------------------------------                      -->




                            </div>

                        </div>
                    </div>

                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
         <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
