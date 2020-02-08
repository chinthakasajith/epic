<%-- 
    Document   : userroleapplicationassign
    Created on : Jan 18, 2012, 4:40:23 PM
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
            
 
            function selectAll(selectBox1,selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }
                document.applicationpageform.action="${pageContext.request.contextPath}/ManageUserRoleApplicationServlet?userRoleCode=${userRole}";
                document.applicationpageform.submit();
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


                                <form name="applicationpageform" action="" method="POST">

                                    <table>
                                        <tr>

                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>

                                            <td></td>
                                        </tr>
                                    </table>
                                    <table  border="0">
                                        <tr>
                                            <td style="width: 80px" >User Role </td><td> <B> <c:out value="${userRole}"/></B></td>
                                        </tr>
                                        <tr> <td style="height:12px;"></td></tr>
                                    </table>
                                    <table border="0">
                                        <tr>
                                            <td>
                                                <select name="notassignapplicationlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                    <c:forEach  var="application" items="${sessionScope.SessionObject.notAssignUserRoleApplications}">
                                                        <option value="${application.applicationCode}" >${application.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type=button value=">" style="width: 30px" onclick=moveout()><br>
                                                <input type=button value="<" style="width: 30px" onclick=movein()><br>
                                                <input type=button value=">>" style="width: 30px" onclick=moveallout()><br>
                                                <input type=button value="<<" style="width: 30px" onclick=moveallin()>
                                            </td>
                                            <td>
                                                <select name="assignapplicationlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                    <c:forEach var="application" items="${sessionScope.SessionObject.assignUserRoleApplications}">
                                                        <option value="${application.applicationCode}" >${application.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    <table>
                                        <tr>
                                            <td><input type="submit" onclick="selectAll(assignapplicationlist,notassignapplicationlist)" name="assignapplication" value="Assign" style="width: 100px"></td>

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


