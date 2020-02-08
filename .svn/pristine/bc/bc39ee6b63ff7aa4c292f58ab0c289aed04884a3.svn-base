<%-- 
    Document   : userroleprivilegeshome
    Created on : Jan 18, 2012, 10:29:11 AM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <jsp:include page="/content.jsp"/>


        <script language="javascript">
            
            
            function invokeTasks()
            {
                document.userroleprivilegeform.action="${pageContext.request.contextPath}/LoadUserRolePrivilegeMgtServlet";
                document.userroleprivilegeform.submit();
            }
 
            function invokeManagePrivileges()
            {
                document.userroleprivilegeform.action="${pageContext.request.contextPath}/ManageUserRolePrivilagesServlet";
                document.userroleprivilegeform.submit();
            }
            

        </script>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.USERPRIVILEGE%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                
           
                                            
        </script>



        <title>EPIC CMS USER ROLE HOME</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="userroleprivilegeform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                            <td></td>
                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td>User Role</td>
                                                <td>
                                                    <select onchange="invokeTasks()"  style="width: 200px" name="userrolecodefield" >
                                                        <option value="">--SELECT--</option>
                                                        <c:forEach var="userRole" items="${sessionScope.SessionObject.userRoleList}">
                                                            <c:if test="${userrolecode==userRole.userRoleCode}">
                                                                <option value="${userRole.userRoleCode}" selected>${userRole.description}</option>
                                                            </c:if>
                                                            <c:if test="${userrolecode!=userRole.userRoleCode}">
                                                                <option value="${userRole.userRoleCode}" >${userRole.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>




                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr><td><input type="radio" name="role" value="application">&nbsp;&nbsp;Applications</td></tr>
                                            <tr><td><input type="radio" name="role" value="section">&nbsp;&nbsp;Sections</td></tr>
                                            <tr><td><input type="radio" name="role" value="page">&nbsp;&nbsp;Pages</td></tr>
                                            <tr><td><input type="radio" name="role" value="task">&nbsp;&nbsp;Tasks</td></tr>

                                        </tbody>
                                    </table>

                                    <table>
                                        <tr>

                                            <td style="width: 300px"><input type="submit" value="Manage User Privileges" name="add" style="width: 200px" onclick="invokeManagePrivileges()">

                                            <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.USERPRIVILEGE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                        </tr>

                                    </table>

                                </form>


                                <c:if test="${sessionScope.SessionObject.appSecPageTaskList != null}" >

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Application</th>
                                                <th>Section</th>
                                                <th>Page</th>
                                                <th>Task</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="appSecPageTaskBean" items="${sessionScope.SessionObject.appSecPageTaskList}">
                                                <tr>

                                                    <c:if test="${appSecPageTaskBean.applicationCode != null}" > <td >${appSecPageTaskBean.applicationCode}</td></c:if><c:if test="${appSecPageTaskBean.applicationCode == null}" > <td > - </td></c:if>
                                                    <c:if test="${appSecPageTaskBean.sectionCode != null}" > <td >${appSecPageTaskBean.sectionCode}</td></c:if><c:if test="${appSecPageTaskBean.sectionCode == null}" > <td > - </td></c:if>
                                                    <c:if test="${appSecPageTaskBean.pageCode != null}" > <td >${appSecPageTaskBean.pageCode}</td></c:if><c:if test="${appSecPageTaskBean.pageCode == null}" > <td > - </td></c:if>
                                                    <c:if test="${appSecPageTaskBean.taskCode != null}" > <td >${appSecPageTaskBean.taskCode}</td></c:if><c:if test="${appSecPageTaskBean.taskCode == null}" > <td > - </td></c:if>

                                                    </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>



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

