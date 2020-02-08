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
            
            function resetCardTypeForm(){
                window.location = "${pageContext.request.contextPath}/LoadCardTypeMgtServlet";
            }
            function viewCardType(value){
                window.location = "${pageContext.request.contextPath}/ViewCardTypeMgtServlet?id="+value;
            }
            function updateViewCardType(value){
                window.location = "${pageContext.request.contextPath}/UpdateCardTypeMgtViewServlet?id="+value;
            }
            //            function updateCardType(value){
            //                window.location = "${pageContext.request.contextPath}/UpdateBankBrachMgtLoadServlet?id="+value;
            //            }
            
            function ConfirmDelete(value)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+value+" Crad Type ?</p>");
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
                            window.location="${pageContext.request.contextPath}/DeleteCardTypeMgtServlet?id="+value;
                        }
                    }
                });

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDTYPEMGT%>'                                  
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

                    <jsp:include page="/leftmenu.jsp"/>

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
                                    <form method="POST" action="${pageContext.request.contextPath}/AddCardTypeMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Card Type Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="cardType" maxlength="20" value="${databean.cardTypeCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="cardDis" maxlength="20" value="${databean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.card}">
                                                                <c:if test="${vRule.statusCode ==databean.status}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>

                                                                <c:if test="${vRule.statusCode !=databean.status}">
                                                                    <option value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

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
                                                    <td style="width: 300px;"><input type="submit" value="Add" style="width: 100px;"/><input type="reset" value="Reset" onclick="resetCardTypeForm()" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateCardTypeMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Card Type Code</td>
                                                    <td></td>
                                                    <td><input type="text" maxlength="20" readonly="" name="cardType" value="${databean.cardTypeCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><input type="text" maxlength="20" name="cardDis" value="${databean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.card}">
                                                                <c:if test="${vRule.description ==databean.status}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>

                                                                <c:if test="${vRule.description !=databean.status}">
                                                                    <option value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

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
                                                    <td style="width: 300px;"><input type="submit" value="Update" style="width: 100px;"/><input type="reset" value="Reset" onclick="updateViewCardType('${databean.cardTypeCode}')" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/LoadCardTypeMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Card Type Code</td>
                                                    <td></td>
                                                    <td>: ${databean.cardTypeCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td>: ${databean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>: ${databean.status}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Back" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>



                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Card Type Code</th>
                                            <th>Description</th>
                                            <th>Status</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.cardAllList}" var="cardAllList">
                                            <tr>
                                                <td>${cardAllList.cardTypeCode}</td>
                                                <td>${cardAllList.description}</td>
                                                <td>${cardAllList.status}</td>
                                                <td><a  href='#' onclick="viewCardType('${cardAllList.cardTypeCode}')">View</a></td>
                                                <td><a  href='#' onclick="updateViewCardType('${cardAllList.cardTypeCode}')">Update</a></td>
                                                <td><a  href='#' onclick="ConfirmDelete('${cardAllList.cardTypeCode}')">Delete</a></td>
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
