<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : mahesh_m
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

         <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>
       
        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addTaskform.action="${pageContext.request.contextPath}/AddTaskMgtServlet";
                document.addTaskform.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadTaskMgtServlet";

            }
            
            function invokeUpdate()
            {

                document.updateTaskform.action="${pageContext.request.contextPath}/UpdateConfiremedTaskMgtServlet";
                document.updateTaskform.submit();

            }
            
            function ConfirmDelete(taskCode)
            {
                answer = confirm("Do you really want to delete this Task ?")
                if (answer !=0)
                {
                    document.addTaskform.action="${pageContext.request.contextPath}/DeleteTaskServlet?taskCode="+taskCode;
                }
                else
                {
                    document.addTaskform.action="${pageContext.request.contextPath}/LoadTaskMgtServlet";
                }
                document.addTaskform.submit();
            }
            
            
           
        </script>  
         <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );

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



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">




                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="center">  PROFILE MANAGEMENT </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                
                                 <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='default'}">
                                    <form action="" method="POST" name="addProfileMgtForm" >
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
                                                    <td>Profile Category</td>
                                                    
                                                    <td>
                                                        <select  name="selectedProfile"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="profile" items="${sessionScope.SessionObject.profileCategoryDetails}">

                                                                <c:if test="${TaskBean.status==status.statusCode}">
                                                                    <option value="${profile.profileCategoryCode}" selected>${profile.profileCategoryname}</option>
                                                                </c:if>
                                                                <c:if test="${TaskBean.status!=status.statusCode}">
                                                                    <option value="${profile.profileCategoryCode}">${status.profileCategoryname}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Profile Code</td>
                                                    <td><input type="text" name="profileCode" class="inputfield-Description-mandatory" maxlength="6" value='${TaskBean.description}'></td>
                                                    <td></td>
                                                </tr>

                                                 <tr>
                                                    <td>Profile Name</td>
                                                    <td><input type="text" name="profileName" class="inputfield-mandatory" maxlength="64"  value='${TaskBean.sortKey}'></td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Status</td>



                                                    <td>
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

                                                <tr></tr><tr></tr><tr></tr>


                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" name="add" value="Add" class="defbutton" onclick="invokeAdd()" />
                                                        <input type="button" name="reset" value="Reset" class="defbutton" onclick="invokeReset()"/>
                                                    </td>
                                                    <td></td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          
                                
                                
                                
                                 <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Profile Category Code</th>
                                            <th>Profile Category Name</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="profile" items="${sessionScope.SessionObject.profileCategoryDetails}">
                                            <tr>

                                                <td >${profile.profileCategoryCode}</td>
                                                <td >${profile.profileCategoryname}</td>
                                                <td >${profile.status}</td>

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
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
