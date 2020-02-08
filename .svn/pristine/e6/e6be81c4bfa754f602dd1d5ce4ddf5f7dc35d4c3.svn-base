<%-- 
    Document   : userroletaskassign
    Created on : Jan 20, 2012, 9:55:55 AM
    Author     : chanuka
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            
 
            function invokeAssignSections()
            {
                document.pagetaskassignform.action="${pageContext.request.contextPath}/GetsectionsByApplicationServlet?userrolecode=${userRole}&id=three";
                document.pagetaskassignform.submit();
            }
            function invokeAssignPages()
            {
                document.pagetaskassignform.action="${pageContext.request.contextPath}/GetpagesBySectionServlet?userrolecode=${userRole}&id=two";
                document.pagetaskassignform.submit();
            }
            function invokeAssignTasks()
            {
                document.pagetaskassignform.action="${pageContext.request.contextPath}/GettasksByPageServlet?userrolecode=${userRole}&id=one";
                document.pagetaskassignform.submit();
            }
 
 
            function selectAll(selectBox1,selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }
                document.pagetaskassignform.action="${pageContext.request.contextPath}/ManageUserRoleTaskServlet?userRoleCode=${userRole}";
                document.pagetaskassignform.submit();
            }
            
            function invokeBack(userRoleCode){
                
                window.location = "${pageContext.request.contextPath}/LoadUserRolePrivilegeMgtServlet?userrolecodefield="+userRoleCode;
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



        <title>EPIC CMS USER ROLE PRIVILEGES HOME</title>
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


                                <form name="pagetaskassignform" action="" method="POST">

                                    <table>
                                        <tr>

                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>

                                            <td></td>
                                        </tr>
                                    </table>
                                    <table  border="0">
                                        <tr>
                                            <td style="width: 80px">User Role </td><td> <B> <c:out value="${userRole}"/></B></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 80px">Application </td>
                                            <td>
                                                <select onchange="invokeAssignSections()"  style="width: 200px" name="applicationcode">
                                                    <option value="">--SELECT--</option>
                                                    <c:forEach var="application" items="${sessionScope.SessionObject.assignUserRoleApplications}">
                                                        <c:if test="${selectedapplication==application.applicationCode}">
                                                            <option value="${application.applicationCode}" selected>${application.description}</option>
                                                        </c:if>
                                                        <c:if test="${selectedapplication!=application.applicationCode}">
                                                            <option value="${application.applicationCode}" >${application.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>



                                        <tr>
                                            <td style="width: 80px">Section </td>
                                            <td>
                                                <select onchange="invokeAssignPages()"  style="width: 200px" name="sectioncode">
                                                    <option value="">--SELECT--</option>
                                                    <c:forEach var="section" items="${sessionScope.SessionObject.assignUserRoleSections}">
                                                        <c:if test="${selectedsection==section.sectionCode}">
                                                            <option value="${section.sectionCode}" selected>${section.description}</option>
                                                        </c:if>
                                                        <c:if test="${selectedsection!=section.sectionCode}">
                                                            <option value="${section.sectionCode}" >${section.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>


                                        <tr>
                                            <td style="width: 80px">Page </td>
                                            <td>
                                                <select onchange="invokeAssignTasks()"  style="width: 200px" name="pagecode">
                                                    <option value="">--SELECT--</option>
                                                    <c:forEach var="page" items="${sessionScope.SessionObject.assignUserRolePages}">
                                                        <c:if test="${selectedpage==page.pageCode}">
                                                            <option value="${page.pageCode}" selected>${page.description}</option>
                                                        </c:if>
                                                        <c:if test="${selectedpage!=page.pageCode}">
                                                            <option value="${page.pageCode}" >${page.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr> <td style="height:12px;"></td></tr>
                                    </table>


                                    <table border="0">

                                        <tr>
                                            <td>
                                                <select name="notassigntasklist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                    <c:forEach  var="task" items="${sessionScope.SessionObject.notAssignUserRoleTasks}">
                                                        <option value="${task.taskCode}" >${task.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td >
                                                <input type=button value=">" style="width: 30px" onclick=moveout()><br>
                                                <input type=button value="<" style="width: 30px" onclick=movein()><br>
                                                <input type=button value=">>" style="width: 30px" onclick=moveallout()><br>
                                                <input type=button value="<<" style="width: 30px" onclick=moveallin()>
                                            </td>
                                            <td>
                                                <select name="assigntasklist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                    <c:forEach  var="task" items="${sessionScope.SessionObject.assignUserRoleTasks}">
                                                        <option value="${task.taskCode}" >${task.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    <table>
                                        <tr>
                                            <td><input type="submit" onclick="selectAll(notassigntasklist,assigntasklist)" name="assigntask" value="Assign" style="width: 100px"></td>

                                            <td><input type="button"  name="back" value="Back" style="width: 100px" onclick="invokeBack('${userRole}')"></td>
                                        </tr>
                                    </table>
                                </form>



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





