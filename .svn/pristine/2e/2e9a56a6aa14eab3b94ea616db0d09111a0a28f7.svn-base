<%-- 
    Document   : viewallsystemusers
    Created on : Jan 18, 2012, 10:58:18 AM
    Author     : janaka_h
--%>

<%@page import="com.epic.cms.system.util.variable.StatusVarList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.epic.cms.system.util.session.SessionVarName"%>
<%@page import="com.epic.cms.system.util.session.SessionUser"%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean"%>
<%@page import="java.util.List"%>
<%@page import="com.epic.cms.system.util.session.SessionVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <title>VIEW SYSTEM USER</title>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SYSTEMUSER%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                
           function setUserDetails(userName, role,roleCode,isEmailSent,status,email,dualAuthRoleCode){
              $("#_userName").val(userName); 
              $("#_userRole").val(role);
              $("#_userRoleCode").val(roleCode);
              $("#_dualUserRoleCode").val(dualAuthRoleCode);
              $("#_isEmailSent").val(isEmailSent);
              $("#_status").val(status);
              $("#_email").val(email);
              $( "#passwordChangeForm" ).submit();
           }
                                          
        </script>

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>

                                <table>
                                    <tr>

                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>

                                </table>
                                <script> settitle();</script>
                                <form name="adduserorm" action='<%=request.getContextPath()%>/ManageSystemUserServlet'>
                                    <input type="submit"  name="adduser"  value="Create User" class ="buttonsarea"/>
                                    <input type="hidden" name="operation" value="add">
                                    <p>&nbsp;</p>
                                </form>

                                <%
                                    List<SystemUserBean> userList = null;
                                    Set<SystemUserBean> uniqueList = null;
                                    SessionVarList sessionvarlist = null;
                                    SessionUser user = null;
                                    sessionvarlist = (SessionVarList) request.getSession().getAttribute(SessionVarName.SESSION_OBJ);
                                    if (sessionvarlist != null) {
                                        userList = sessionvarlist.getUserList();
                                        uniqueList=new HashSet<SystemUserBean>();
                                         for (int i = 0; i < userList.size(); i++) {
                                             SystemUserBean next=userList.get(i);
//                                            if(StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT.equals(next.getStatusCode())){
//                                                uniqueList.add(next);
//                                            }
                                            
                                            
                                            //in case of dual auth user level changed in the request
                                            //authorization have to do in privious auth role (systemuser table dualauth role)
                                            //get the systemuser table level id according to it's dual auth role
                                            if(StatusVarList.DA_REQUSET_INITIATE.equals(next.getStatusCode())){
                                                for (int y = 0; y < userList.size(); y++) {
                                                    SystemUserBean sysUser=userList.get(y);
                                                    if(next.getUserName().equals(sysUser.getUserName()) && !StatusVarList.DA_REQUSET_INITIATE.equals(next.getStatusCode())){
                                                        next.setLevelId(sysUser.getLevelId());
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        
                                        uniqueList.addAll(userList);
                                        userList.clear();
                                        userList.addAll(uniqueList);
                                        uniqueList.clear();
                                        user = sessionvarlist.getCMSSessionUser();
                                        for (int i = 0; i < userList.size(); i++) {
                                            SystemUserBean foundSysUser=userList.get(i);
                                                //if the requested user is the system user then remove the user from the list
                                                //filtering only level id is equal or greater than to login user
                                                if ((user.getLevelId() >= foundSysUser.getLevelId()) && !user.getUserName().equals(foundSysUser.getRequestedUser()) && (StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT.equals(foundSysUser.getStatusCode()) || StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT.equals(foundSysUser.getStatusCode()) || StatusVarList.DA_REQUSET_INITIATE.equals(foundSysUser.getStatusCode()) || StatusVarList.PASSWORD_RESET_REQUEST_SENT.equals(foundSysUser.getStatusCode()))) {
                                                    uniqueList.add(foundSysUser);
                                                }else{
                                                    //should not displayed
                                                }
                                        }
                                        
                                        //adding system user list except request approvals
                                        List<SystemUserBean> userListExceptReq = sessionvarlist.getUserList();
                                        for (int i = 0; i < userListExceptReq.size(); i++) {
                                            SystemUserBean foundSysUser=userListExceptReq.get(i);
                                                //getting only active and deactive system users 
                                                if (StatusVarList.ACTIVE_STATUS.equals(foundSysUser.getStatusCode()) || StatusVarList.DEACTIVE_STATUS.equals(foundSysUser.getStatusCode())) {
                                                    uniqueList.add(foundSysUser);
                                                }else{
                                                    //should not displayed
                                                }
                                        }
                                        
                                        
                                    }
                                %>   
                                <table  width="740px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th width="100px;" scope="col">User Name</th>
                                            <th width="100px;" scope="col">User Role</th>
                                            <th width="100px;" scope="col">Status</th>
                                            <th width="100px;" scope="col">Exp Date</th>
                                            <th width="150px;" scope="col">Full Name</th>
                                            <th width="100px;" scope="col">Email</th>
                                            <th width="80px;" scope="col">Update</th>
                                            <th width="80px;" scope="col">Delete</th>
                                            <th width="80px;" scope="col">Change Password</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            for (SystemUserBean sysUser : uniqueList) {
                                        %>
                                        <tr class="gradeC">
                                            <td  ><%if (sysUser.getUserName() == null) {
                                                 out.print("");
                                             } else {
                                                 out.print(sysUser.getUserName());
                                             }%></td>
                                            <td  ><%if (sysUser.getUserRoleDes() == null) {
                                                 out.print("");
                                             } else {
                                                 out.print(sysUser.getUserRoleDes());
                                             }%></td>
                                            <td  ><%if (sysUser.getStatusDes() == null) {
                                                 out.print("");
                                             } else {
                                                 out.print(sysUser.getStatusDes());
                                             }%></td>
                                            <td  ><%if (sysUser.getExpiryDateToString() == null) {
                                                 out.print("");
                                             } else {
                                                 out.print(sysUser.getExpiryDateToString());
                                             }%></td>
                                            <td  ><%if (sysUser.getFirstName() == null) {
                                                 out.print("");
                                             } else {
                                                 out.print(sysUser.getFirstName() + " " + sysUser.getLastName());
                                             }%></td>
                                            <td  ><%if (sysUser.getEmail()== null) {
                                                 out.print("");
                                             } else {
                                                 out.print(sysUser.getEmail());
                                             }%></td>
                                            <td  ><a class="link" href='<%=request.getContextPath()%>/ManageSystemUserServlet?username=<%=sysUser.getUserName()%>&status=<%=sysUser.getStatusCode()%>&operation=edit'>Update</a></td>
                                            <td  ><a class="link" href='<%=request.getContextPath()%>/ManageSystemUserServlet?username=<%=sysUser.getUserName()%>&operation=delete&status=<%=sysUser.getStatusDes()%>&userrole=<%=sysUser.getUserRoleDes()%>&statusCode=<%=sysUser.getStatusCode()%>'>Delete</a></td>
                                            <td  ><a class="link" href='javascript:void(0);' onclick="setUserDetails('<%=sysUser.getUserName()%>', '<%=sysUser.getUserRoleDes()%>','<%=sysUser.getUserRoleCode()%>','<%=sysUser.getIsEmailSent()%>','<%=sysUser.getStatusCode()%>','<%=sysUser.getEmail()%>','<%=sysUser.getDualUserRoleCode()%>')">Change Password</a></td>

                                        </tr>
                                        <% }%>

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
    
    <form action="ViewChangeUserPasswordServlet" method="POST" id="passwordChangeForm">      
        <input type="hidden" name="userRole" id="_userRole" />
        <input type="hidden" name="userRoleCode" id="_userRoleCode" />
        <input type="hidden" name="dualUserRoleCode" id="_dualUserRoleCode" />
        <input type="hidden" name="userName" id="_userName" />
        <input type="hidden" name="isEmailSent" id="_isEmailSent" />
        <input type="hidden" name="status" id="_status" />
        <input type="hidden" name="email" id="_email" />
    </form>
</html>
