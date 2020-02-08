<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>

<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>

<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            
            function resetReason(){
                window.location = "${pageContext.request.contextPath}/LoadApplicationRejectServlet";
            }
            function viewReason(value){
                window.location = "${pageContext.request.contextPath}/ViewApplicationRejectServlet?id="+value;
            }
            function updateReason(value){
                window.location = "${pageContext.request.contextPath}/UpdateApplicationRejectLoadServlet?id="+value;
            }
            function deleteReason(value)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+value+" Reject Reason ?</p>");
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
                            window.location="${pageContext.request.contextPath}/DeleteApplicationRejectServlet?id="+value;
                        }
                    }
                });

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPREJECT%>'                                  
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
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
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

                                    <form method="POST" action="${pageContext.request.contextPath}/AddApplicationrejectServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Reason Code</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="reason"  maxlength="4" value="${appDataBean.reasonCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="description" value="${appDataBean.description}" maxlength="128" /></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Add" name="Add" style="width: 100px"/><input onclick="resetReason()" type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPREJECT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>


                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateApplicationrejectServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Reason Code</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" readonly="" name="reason"  maxlength="4" value="${appDataBean.reasonCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="description" value="${appDataBean.description}" maxlength="128" /></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Update" name="Update" style="width: 100px"/><input onclick="updateReason(${appDataBean.reasonCode})" type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPREJECT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>


                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/LoadApplicationRejectServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Reason Code</td>
                                                    <td>:</td>
                                                    <td>${appDataBean.reasonCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>:</td>
                                                    <td>${appDataBean.description}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Back" name="Back" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPREJECT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>



                                <table border="1" class="display" id="scoreltableview2">
                                    <thead>
                                        <tr>
                                            <th>Reason Code</th>
                                            <th>Description</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach  items="${requestScope.appDataList}" var="appDataList">
                                            <tr>
                                                <td>${appDataList.reasonCode}</td>
                                                <td>${appDataList.description}</td>
                                                <td><a  href='#' onclick="viewReason('${appDataList.reasonCode}')">View</a></td>
                                                <td><a  href='#' onclick="updateReason('${appDataList.reasonCode}')">Update</a></td>
                                                <td><a  href='#' onclick="deleteReason('${appDataList.reasonCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>





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
