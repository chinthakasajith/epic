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


        <script  type="text/javascript">
            function invoke() {
                document.addpageform.action = "${pageContext.request.contextPath}/LoadPageMgtServlet";

                document.addpageform.submit();

            }

            function invokeAdd()
            {

                document.addpageform.action = "${pageContext.request.contextPath}/AddPageMgtServlet";
                document.addpageform.submit();

            }

            function selectAll(selectBox) {
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }
                invokeAdd();
            }

            function selectAllForUpdate(selectBox1, selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }
                invokeUpdate();
            }

            function invokeUpdate()
            {

                document.updatePageform.action = "${pageContext.request.contextPath}/UpdateConfiremedPageMgtServlet";
                document.updatePageform.submit();

            }

            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadPageMgtServlet";

            }


            function ConfirmDelete(pageCode)
            {
//                answer = confirm("Do you really want to delete this Page ?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeletePageServlet?pageCode="+pageCode;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadPageMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete this Page ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadPageMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeletePageServlet?pageCode=" + pageCode;
                        }
                    }
                });

            }

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
                        {pagecode: '<%= PageVarList.PAGE%>'
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
                                    <form action="" method="POST" name="addpageform">
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
                                                        <td>Page Code</td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagecode" class="inputfield-mandatory" maxlength="8" value='${pageBean.pageCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagedescription" class="inputfield-Description-mandatory" maxlength="64" value='${pageBean.description}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Url</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pageurl" class="inputfield-Description-mandatory" maxlength="60" value="${pageBean.url}"></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>



                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${pageBean.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${pageBean.statusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>


                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagesortkey" class="inputfield-mandatory" maxlength="5" value="${pageBean.sortKey}" ></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                        <br></br>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td colspan="3">Assign Tasks <B> <c:out value="${tasks}"/></B></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>All Tasks</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="task" items="${taskBean}">
                                                                <option value="${task.taskCode}" >${task.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>

                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <br></br>

                                        <table>
                                        </table>
                                        <tr>
                                            <td></td>
                                            <td><input type="submit" name="Submit" value="Add" class="" onclick="selectAll(assignsectionlist)" style="width: 100px;"/>
                                                <input type="submit" name="Submit2" value="Reset" class="" onclick="invoke()" style="width: 100px;"/>
                                            </td>
                                            <td></td>

                                        </tr>

                                        </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--///////////////////////////////////////////// End Default view  ///////////////////////////////////////////////////////////-->                        

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////--> 

                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewPageform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Page Code</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${page.pageCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${page.description}</td>
                                                </tr>
                                                <tr>
                                                    <td>URL</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${page.url}</td>
                                                </tr>
                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${page.sortKey}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${page.statusCode}</td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table>
                                            </tbody>
                                            <tr>

                                                <td>
                                                    <h4><b>Not Assigned Tasks</b></h4>
                                                    <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="task" items="${taskBean}">
                                                            <option value="${task.taskCode}" >${task.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>

                                                <td>
                                                    <h4><b>Assigned Tasks</b></h4>
                                                    <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                        <c:forEach var="assig" items="${assigned}">
                                                            <option value="${assig.description}" >${assig.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>    


                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->                      


                                <!--/////////////////////////////////////////////Start Update view  ///////////////////////////////////////////////////////////--> 
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updatePageform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Page Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagecode" readonly="" class="inputfield-mandatory" maxlength="8" value='${page.pageCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="taskdescription" class="inputfield-Description-mandatory" maxlength="64" value='${page.description}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>URL</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="url" class="inputfield-Description-mandatory" maxlength="60" value='${page.url}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="pagesortkey" class="inputfield-mandatory" value="${page.sortKey}" maxlength="5"></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>



                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${page.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${page.statusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>


                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>


                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td colspan="3"><h4><b>Assign Tasks </b></h4> <B> <c:out value="${tasks}"/></B></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>Not Assigned Tasks</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="task" items="${taskBean}">
                                                                <option value="${task.taskCode}" >${task.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach var="assig" items="${assigned}">
                                                                <option value="${assig.taskCode}" >${assig.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>  

                                        <table>
                                            <tbody>
                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" value="Update" name="update" onclick="selectAllForUpdate(assignsectionlist, notassignsectionlist)" style="width: 100px;">
                                                        <input type="button" value="Reset" name="reset" onclick="invokeReset()" style="width: 100px;"></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>            
                                    </form>
                                </c:if>


                                <!--/////////////////////////////////////////////End Update view  ///////////////////////////////////////////////////////////--> 

                                <!--/////////////////////////////////////////////    Start Table   //////////////////////////////////////////////////////////--> 
                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Page Code</th>
                                            <th>Description</th>
                                            <th>URL</th>
                                            <th>Sort key</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="page" items="${sessionScope.SessionObject.pageDetails}">
                                            <tr>

                                                <td >${page.pageCode}</td>
                                                <td >${page.description}</td>
                                                <td >${page.url}</td>
                                                <td >${page.sortKey}</td>
                                                <td >${page.statusDes}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewPageMgtServlet?pagecode=<c:out value="${page.pageCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdatePageMgtServlet?pageCode=<c:out value="${page.pageCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${page.pageCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <!--/////////////////////////////////////////////    End Table   //////////////////////////////////////////////////////////-->      

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
