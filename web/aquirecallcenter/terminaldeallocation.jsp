<%-- 
    Document   : terminaldeallocation
    Created on : Dec 12, 2012, 2:07:29 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeDeallocate(terminalID)
            {
                window.location = "${pageContext.request.contextPath}/DeAllocateCallCenterTerminalServlet?terminalID="+terminalID;
            }
            
            function invokeBackToSearch(id,mid)
            {
                window.location = "${pageContext.request.contextPath}/ViewMerchantMgtServlet?section=ACCTER&id="+id+"&mid="+mid;
            }
           
        
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_TERMINAL_DEALLOCATION%>'                                  
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
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>
        <div class="container">
            <div class="header">             
            </div>
            <div class="main">

                <jsp:include page="/subheader.jsp"/>
                <div class="content" style="height: 500px">
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <%-- -------------------------Start Add Form -------------------------------- --%>

                                <form method="POST" action="" name="addterminalform">

                                    <table>
                                        <tr>
                                            <td colspan="3">
                                                <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>
                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Terminal ID</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.terminalID}</td>
                                            </tr>     
                                            <tr>
                                                <td>Terminal Name</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.name}</td>
                                            </tr>
                                            <tr>
                                                <td>Merchant ID</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <c:if test="${trmnlBean.merchantID == null}">
                                                    <td>--</td>
                                                </c:if>
                                                <c:if test="${trmnlBean.merchantID != null}">
                                                    <td>${trmnlBean.merchantID}</td>
                                                </c:if>

                                            </tr> 
                                            <tr>
                                                <td>Merchant</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>

                                                <c:if test="${trmnlBean.merchantDes == null}">
                                                    <td>--</td>
                                                </c:if>
                                                <c:if test="${trmnlBean.merchantDes != null}">
                                                    <td>${trmnlBean.merchantDes}</td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <td>MCC</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <c:if test="${trmnlBean.mcc == null}">
                                                    <td>--</td>
                                                </c:if>
                                                <c:if test="${trmnlBean.mcc != null}">
                                                    <td>${trmnlBean.mcc}</td>
                                                </c:if>

                                            </tr> 
                                            <tr>
                                                <td>Currency</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <c:if test="${trmnlBean.currency == null}">
                                                    <td>--</td>
                                                </c:if>
                                                <c:if test="${trmnlBean.currency != null}">
                                                    <td>${trmnlBean.currency}</td>
                                                </c:if>

                                            </tr> 
                                            <tr>
                                                <td>Serial Number</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.serialNo}</td>
                                            </tr>
                                            <tr>
                                                <td>Manufacturer</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.manufactDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Model</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.modelDes}</td>
                                            </tr><tr>
                                                <td>Installation Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.installationDate}</td>
                                            </tr>

                                            <tr>
                                                <td>Allocation Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.alloStatus}</td>
                                            </tr>

                                            <tr>
                                                <td>Terminal Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${trmnlBean.terminalStatusDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Transactions</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>

                                                <td>
                                                    <h4><b>Not Assigned Transactions</b></h4>
                                                    <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="notassign" items="${notAssignedTxn}">
                                                            <option value="${notassign.transactions}" >${notassign.transactionDes}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td><td></td>

                                                <td></td>


                                                <td>
                                                    <h4><b>Assigned Transactions</b></h4>
                                                    <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                        <c:forEach var="assign" items="${assignedTxn}">
                                                            <option value="${assign.transactions}" >${assign.transactionDes}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>

                                        </tbody>
                                    </table>
                                    <table border="0" cellpadding="0" cellspacing="10">
                                        <tbody>


                                        <td><input type="button" value="De-Allocate" name="deallocate" style=" width:100px " onclick="invokeDeallocate('${trmnlBean.terminalID}')"/></td>

                                        <td><input type="button" value="Back" name="back" style=" width:100px " onclick="invokeBackToSearch('${trmnlBean.terminalID}','${trmnlBean.merchantID}')"/></td>

                                        </tr>

                                        </tbody>
                                    </table>

                                </form>


                                <%--  ----------------------------------End ADD Form ------------------------- --%>


                            </div>
                        </div>
                    </div>
                    <div class="clearer"><span></span></div>
                </div>
            </div>
            <div class="footer"><jsp:include page="/footer.jsp"/></div>
        </div>
    </body>
</html>

