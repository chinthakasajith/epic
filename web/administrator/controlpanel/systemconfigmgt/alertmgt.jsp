<%-- 
    Document   : accuntmgt
    Created on : Jun 9, 2016, 8:00:00 AM
    Author     : sajith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            
            function BackAlertForm(){
                window.location = "${pageContext.request.contextPath}/LoadAlertMgtServlet";
            }
            function resetAlertForm(){
                window.location = "${pageContext.request.contextPath}/LoadAlertMgtServlet";
            }
            
            
            function viewAlert(value){
                window.location = "${pageContext.request.contextPath}/ViewAlertMgtServlet?id="+value;
            }
            function updateAlert(value){
                document.viewTableForm.action="${pageContext.request.contextPath}/UpdateAlertMgtLoadServlet";           
                document.getElementById('id').value=value;    
                document.viewTableForm.submit();
                // window.location = "${pageContext.request.contextPath}/UpdateAlertMgtLoadServlet?id="+value;
            }
            
            function ConfirmDelete(value)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+value+" alert configuration ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                            window.location="${pageContext.request.contextPath}/DeleteAlertMgtServlet?id="+value;
                        }
                    }
                });

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.ALERT_MANAGEMENT%>'                                  
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

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>




                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------    -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form id="emailForm" name="emailForm" method="POST" action="${pageContext.request.contextPath}/AddAlertMgtServlet" style="float: left;min-height: 210px;">
                                        <table border="0">

                                            <h5>Email Server Configuration :</h5>
                                            <tbody>
                                                <tr>
                                                    <td><input type="hidden" name="alertType" value="Email" /></td>
                                                    <td><input type="hidden" name="description" value="Email Configuration" /></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">IP </td>
                                                    <td></td>
                                                    <td>                                                  
                                                        <c:if test="${alertBean.alertType == 'Email' }">
                                                            <input type="text" name="ip" value="${alertBean.ip}" class="deftext" style="width: 160px;"/>
                                                        </c:if>  
                                                        <c:if test="${alertBean.alertType != 'Email' }">
                                                            <input type="text" name="ip" value="" class="deftext" style="width: 160px;"/>
                                                        </c:if>                          
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Port</td>
                                                    <td></td>
                                                    <td>
                                                        <c:if test="${alertBean.alertType == 'Email' }">
                                                            <input type="text" name="port" value="${alertBean.port}" class="deftext" style="width: 160px;"/>
                                                        </c:if>  
                                                        <c:if test="${alertBean.alertType != 'Email' }">
                                                            <input type="text" name="port" value="" class="deftext" style="width: 160px;"/>
                                                        </c:if>  
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Username</td>
                                                    <td></td>
                                                    <td><input type="text" name="userName" value="${alertBean.userName}" class="deftext" style="width: 160px;"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td style="width: 100px;">Password</td>
                                                    <td></td>
                                                    <td><input type="password" name="password" value="${alertBean.password}" class="deftext" style="width: 160px;"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Sender email</td>
                                                    <td></td>
                                                    <td><input type="text" name="sender" value="${alertBean.sender}" class="deftext" style="width: 160px;"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td></td>
                                                    <td>
                                                        <c:if test="${alertBean.alertType == 'Email' }">
                                                            <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                    <c:if test="${alertBean.status==status.statusCode}">
                                                                        <option value="${status.statusCode}" selected>${status.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${alertBean.status!=status.statusCode}">
                                                                        <option value="${status.statusCode}">${status.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </c:if>  
                                                        <c:if test="${alertBean.alertType != 'Email' }">
                                                            <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                    <c:if test="${alertBean.status==status.statusCode}">
                                                                        <option value="${status.statusCode}">${status.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${alertBean.status!=status.statusCode}">
                                                                        <option value="${status.statusCode}">${status.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </c:if>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>

                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="resetAlertForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                                
                                    <form id="smsForm" name="smsForm" method="POST" action="${pageContext.request.contextPath}/AddAlertMgtServlet" style="float: right;min-height: 210px;">
                                        <table border="0">

                                            <h5>SMS Server Configuration :</h5>
                                            <tbody>
                                                <tr>
                                                    <td><input type="hidden" name="alertType" value="SMS" /></td>
                                                    <td><input type="hidden" name="description" value="SMS Configuration" /></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">IP </td>
                                                    <td></td>
                                                    <td>
                                                        <c:if test="${alertBean.alertType == 'SMS' }">
                                                            <input type="text" name="ip" value="${alertBean.ip}" class="deftext" style="width: 160px;"/>            
                                                        </c:if>  
                                                        <c:if test="${alertBean.alertType != 'SMS' }">
                                                            <input type="text" name="ip" value="" class="deftext" style="width: 160px;"/>
                                                        </c:if>   
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Port</td>
                                                    <td></td>
                                                    <td>
                                                        <c:if test="${alertBean.alertType == 'SMS' }">
                                                            <input type="text" name="port" value="${alertBean.port}" class="deftext" style="width: 160px;"/>            
                                                        </c:if>  
                                                        <c:if test="${alertBean.alertType != 'SMS' }">
                                                            <input type="text" name="port" value="" class="deftext" style="width: 160px;"/>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Port Timeout</td>
                                                    <td></td>
                                                    <td><input type="text" name="portTimeOut" value="${alertBean.portTimeOut}" class="deftext" style="width: 160px;"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Socket Timeout</td>
                                                    <td></td>
                                                    <td><input type="text" name="socketTimeOut" value="${alertBean.socketTimeOut}" class="deftext" style="width: 160px;"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td></td>
                                                    <td>
                                                        
                                                        <c:if test="${alertBean.alertType == 'SMS' }">
                                                            <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${alertBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${alertBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>           
                                                        </c:if>  
                                                        <c:if test="${alertBean.alertType != 'SMS' }">
                                                            <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${alertBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${alertBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>
                                                        </c:if>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>

                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="resetAlertForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateAlertMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 100px;">Alert Type </td>
                                                    <td></td>
                                                    <td>:${alertBean.alertType}</td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="alertType" value="${alertBean.alertType}" /></td>
                                                    <td><input type="hidden" name="description" value="${alertBean.description}" /></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Description </td>
                                                    <td></td>
                                                    <td>:${alertBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">IP </td>
                                                    <td></td>
                                                    <td><input type="text" name="ip" value="${alertBean.ip}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Port</td>
                                                    <td></td>
                                                    <td><input type="text" name="port" value="${alertBean.port}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td style="width: 100px;">Port Timeout</td>
                                                    <td></td>
                                                    <td><input type="text" name="portTimeOut" value="${alertBean.portTimeOut}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td style="width: 100px;">Socket Timeout</td>
                                                    <td></td>
                                                    <td><input type="text" name="socketTimeOut" value="${alertBean.socketTimeOut}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td style="width: 100px;">Username</td>
                                                    <td></td>
                                                    <td><input type="text" name="userName" value="${alertBean.userName}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td style="width: 100px;">Password</td>
                                                    <td></td>
                                                    <td><input type="password" name="password" value="${alertBean.password}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Sender email</td>
                                                    <td></td>
                                                    <td><input type="text" name="sender" value="${alertBean.sender}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td></td>
                                                    <td>
                                                        <select  name="status"  class="inputfield-mandatory" style="width: 160px;">
                                                            <option value="selected">--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                
                                                                <c:if test="${alertBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${alertBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>


                                        <table border="0">
                                            <tr>
                                                <td style="width: 113px;"></td>
                                                <td><input type="submit" value="Update" name="Update" class="defbutton"/></td>
                                                <td><input onclick="updateAlert('${alertBean.alertType}')" type="reset" value="Reset" class="defbutton"/></td>
                                                <td><input onclick="BackAlertForm()" type="reset" value="Back" class="defbutton"/></td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewAlertMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Alert Type</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${alertBean.alertType}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${alertBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>IP</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${alertBean.ip}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Port</td>
                                                    <td></td>
                                                    <td>:${alertBean.port}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Port Timeout</td>
                                                    <td></td>
                                                    <td>:${alertBean.portTimeOut}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td style="width: 100px;">Socket Timeout</td>
                                                    <td></td>
                                                    <td>:${alertBean.socketTimeOut}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Username</td>
                                                    <td></td>
                                                    <td>:${alertBean.userName}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                 <tr>
                                                    <td>Sender email</td>
                                                    <td></td>
                                                    <td>:${alertBean.sender}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>                                         
                                                    <td>:${alertBean.statusDescription}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>



                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="Back" class="defbutton" onclick="BackAlertForm()"/>
                                                    </td>

                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>


                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <!-- show table data -->
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>Alert Type</th>
                                                <th>Description</th>
                                                <th>IP</th>
                                                <th>Port</th>
                                                <th>Port Timeout</th>
                                                <th>Socket Timeout</th>
                                                <th>Username</th>
                                                <th>Sender email</th>
                                                <th>Status</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${alertlist}" var="alertBean">
                                                <tr>
                                                    <td>${alertBean.alertType}</td>
                                                    <td>${alertBean.description}</td>
                                                    <td>${alertBean.ip}</td>
                                                    <td>${alertBean.port}</td>
                                                    <td>${alertBean.portTimeOut}</td>
                                                    <td>${alertBean.socketTimeOut}</td>
                                                    <td>${alertBean.userName}</td>
                                                    <td>${alertBean.sender}</td>
                                                    <td>${alertBean.status}</td>
                                                    <td><a  href='#' onclick="viewAlert('${alertBean.alertType}')">View</a></td>
                                                    <td><a  href='#' onclick="updateAlert('${alertBean.alertType}')">Update</a></td>
                                                    <td><a  href='#' onclick="ConfirmDelete('${alertBean.alertType}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
                                    </table>
                                    <!--confirmation dialog -->
                                    <div id="dialog-confirm" title="Delete Confirmation"></div>
                                </form>




                                <!--   ------------------------- end developer area  --------------------------------  -->

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

