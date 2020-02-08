<%-- 
    Document   : requestapprove
    Created on : Jul 27, 2012, 3:19:09 PM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
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
        <title>EPIC_CMS_HOME</title>


        <script type="text/javascript">
            function invokeSearch() {

                document.searchrequestapprove.action = "${pageContext.request.contextPath}/SearchRequestApproveServlet";
                document.searchrequestapprove.submit();
            }

            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadRequestApproveServlet";

            }

            function invokeCancel(form) {


                if (form == 'CDRP') {
                    document.cardreplace.action = "${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.cardreplace.submit();
                }
                if (form == 'CDRI') {
                    document.cardreissue.action = "${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.cardreissue.submit();
                }
                if (form == 'PIRI') {
                    document.pinreissue.action = "${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.pinreissue.submit();
                }


            }

            function invokeApproveCardReplace(status, operation) {
                document.cardreplace.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreplace.submit();
            }

            function invokeApproveCardReissue(status, operation) {
                document.cardreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreissue.submit();
            }

            function invokeApprovePINReissue(status, operation) {
                document.pinreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.pinreissue.submit();
            }

            function invokeActivate(operation) {
                document.cardactivate.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?operation=" + operation;
                document.cardactivate.submit();
            }

            function invokeRejectCardReplace(status, operation) {
                document.cardreplace.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreplace.submit();
            }

            function invokeRejectCardReissue(status, operation) {
                document.cardreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreissue.submit();
            }

            function invokeRejectPINReissue(status, operation) {
                document.pinreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.pinreissue.submit();
            }

            function invokeRejectActivate(status, operation) {
                document.cardactivate.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardactivate.submit();
            }

            function invokeHistory(value) {

                $.post("${pageContext.request.contextPath}/ViewHistoryRequestApproveServlet", {id: value},
                function(data) {
                    if (data == "success") {

                        $.colorbox({href: "${pageContext.request.contextPath}/cpmm/requestconfirm/cardhistory.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }
                                });
                            }
                            //----------------------------------------------------------------------------------  
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.REQUESTAPPROVE%>'
                        },
                function(data) {

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
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
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
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <c:if test="${operationtype=='search'}">

                                    <form method="POST" name="searchrequestapprove" >
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" value="${cardBean.cardNo}" name="cardno"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td>
                                                        <select style="width: 163px" name="prioritycode">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="approvestatus" style="width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="approve" items="${approveStatus}">  
                                                                <c:if test="${cardBean.status == approve.key}">
                                                                    <option value="${approve.key}" selected>${approve.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.status != approve.key}">
                                                                    <option value="${approve.key}" >${approve.value}</option>
                                                                </c:if>


                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Reason Code</td>
                                                    <td><font style="color: red">*</font></td>
                                                    <td>
                                                        <select name="reasoncode" style="width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="reason" items="${reasonCodeList}">  
                                                                <c:if test="${cardBean.reasonCode == reason.key}">
                                                                    <option value="${reason.key}" selected>${reason.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.reasonCode != reason.key}">
                                                                    <option value="${reason.key}" >${reason.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                    <td></td>
                                                </tr>   
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>   
                                                        <input type="submit" class="defbutton" name="search" value="Search" onclick="invokeSearch()"/>                                                         
                                                        <input type="reset" class="defbutton" name="reset" value="Reset" onclick="invokeReset()"/> 
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>   
                                </c:if>
                                <c:if test="${operationtype=='update'}" >
                                    <c:if test="${operation=='CDRP'}">

                                        <form method="POST" name="cardreplace" >
                                            <table border="0" cellspacing="10" cellpadding="0">

                                                <tbody >
                                                    <tr>
                                                        <td width="200px;">Card Number</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cardNo}" name="cardno" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Credit Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.creditLimit}" name="creditlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Cash Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cashLimit}" name="cashlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Expiry Date</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.expiryDate}" name="expirydate" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Card Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.statusDes}" name="status1" readonly=""/>                                                            
                                                            <input style="color: #999999" type="hidden" value="${cardBean.status}" name="status" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Priority Level</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.priorityLevel}" name="prioritycode1" readonly=""/>
                                                            
                                                            <select style="width: 100px" name="prioritycode" disabled="">
                                                                <option value="" >--SELECT--</option>

                                                                <c:forEach var="priority" items="${priorityLevelList}">
                                                                    <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                        <option value="${priority.key}" selected>${priority.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                        <option value="${priority.key}" >${priority.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    

                                                    <tr>
                                                        <td>Reason Code</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.reasonCode}" name="reasoncode1" readonly=""/>
                                                            <select name="reasoncode" style="width: 163px" disabled="">
                                                                <option value="">--SELECT--</option>
                                                                <c:forEach var="reason" items="${reasonCodeList}">  
                                                                    <c:if test="${cardBean.reasonCode == reason.key}">
                                                                        <option value="${reason.key}" selected>${reason.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.reasonCode != reason.key}">
                                                                        <option value="${reason.key}" >${reason.value}</option>
                                                                    </c:if>

                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td></td>
                                                    </tr> 
                                                    <tr>
                                                        <td>Renewal Confirmation</td>
                                                        <td><input style="color: #999999" type="text" name="renew" value="${cardBean.renew}" readonly="true"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><textarea id="remark1" style="" name="remarks" value="${cardBean.remark}" >${cardBean.remark}</textarea></td>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td>   
                                                            <input type="submit" class="defbutton" name="approve" value="Approve" onclick="invokeApproveCardReplace('NRPL', 'CDRP')"/>  
                                                            <input type="submit" class="defbutton" name="reject" value="Reject" onclick="invokeRejectCardReplace('REJ', 'CDRP')"/>    
                                                            <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('CDRP')"/></td>

                                                        <td><a  href="#"  onclick="invokeHistory('${cardBean.cardNo}')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                    </tr>
                                                </tbody>
                                            </table> 
                                        </form>
                                    </c:if>

                                    <c:if test="${operation=='CDRI'}">

                                        <form method="POST" name="cardreissue" >
                                            <table border="0" cellspacing="10" cellpadding="0">

                                                <tbody >
                                                    <tr>
                                                        <td width="200px;">Card Number</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cardNo}" name="cardno" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Credit Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.creditLimit}" name="creditlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Cash Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cashLimit}" name="cashlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Expiry Date</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.expiryDate}" name="expirydate" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Card Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.statusDes}" name="status1" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Priority Level</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.priorityLevel}" name="prioritycode1" readonly=""/>
                                                            
                                                            <select style="width: 100px" name="prioritycode" disabled="">
                                                                <option value="" >--SELECT--</option>

                                                                <c:forEach var="priority" items="${priorityLevelList}">
                                                                    <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                        <option value="${priority.key}" selected>${priority.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                        <option value="${priority.key}" >${priority.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    

                                                    <tr>
                                                        <td>Reason Code</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.reasonCode}" name="reasoncode1" readonly=""/>
                                                            
                                                            <select name="reasoncode" style="width: 163px" disabled="">
                                                                <option value="">--SELECT--</option>
                                                                <c:forEach var="reason" items="${reasonCodeList}">  
                                                                    <c:if test="${cardBean.reasonCode == reason.key}">
                                                                        <option value="${reason.key}" selected>${reason.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.reasonCode != reason.key}">
                                                                        <option value="${reason.key}" >${reason.value}</option>
                                                                    </c:if>

                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td></td>
                                                    </tr> 
                                                    <tr>
                                                        <td>Renewal Confirmation</td>
                                                        <td><input style="color: #999999" type="text" name="renew" value="${cardBean.renew}" readonly=""/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><textarea name="remarks" value="${cardBean.remark}" >${cardBean.remark}</textarea></td>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td>   
                                                            <input type="submit" class="defbutton" name="approve" value="Approve" onclick="invokeApproveCardReissue('NRPL', 'CDRI')"/>  
                                                            <input type="submit" class="defbutton" name="reject" value="Reject" onclick="invokeRejectCardReissue('REJ', 'CDRI')"/>    
                                                            <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('CDRI')"/></td>
                                                        <td><a  href="#"  onclick="invokeHistory('${cardBean.cardNo}')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                    </tr>
                                                </tbody>
                                            </table> 
                                        </form>
                                    </c:if>

                                    <c:if test="${operation=='PIRI'}">

                                        <form method="POST" name="pinreissue" >
                                            <table border="0" cellspacing="10" cellpadding="0">

                                                <tbody >
                                                    <tr>
                                                        <td width="200px;">Card Number</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cardNo}" name="cardno" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Priority Level</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.priorityLevel}" name="prioritycode1" readonly=""/>
                                                            
                                                            <select style="width: 100px" name="prioritycode" disabled="">
                                                                <option value="" >--SELECT--</option>

                                                                <c:forEach var="priority" items="${priorityLevelList}">
                                                                    <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                        <option value="${priority.key}" selected>${priority.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                        <option value="${priority.key}" >${priority.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    

                                                    <tr>
                                                        <td>Reason Code</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.reasonCode}" name="reasoncode1" readonly=""/>
                                                            
                                                            <select name="reasoncode" style="width: 163px" disabled="">
                                                                <option value="">--SELECT--</option>
                                                                <c:forEach var="reason" items="${reasonCodeList}">  
                                                                    <c:if test="${cardBean.reasonCode == reason.key}">
                                                                        <option value="${reason.key}" selected>${reason.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.reasonCode != reason.key}">
                                                                        <option value="${reason.key}" >${reason.value}</option>
                                                                    </c:if>

                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td></td>
                                                    </tr>                                                   
                                                    <tr>
                                                        <td>PIN Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.PINStatus}" name="status" readonly=""/>
                                                    </tr>
                                                    <tr>
                                                        <td>PIN Mail Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.PINMailStatus}" name="mailstatus" readonly=""/>
                                                    </tr>
                                                    <tr>
                                                        <td>PIN Distribution Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.PINStatus}" name="distributestatus" readonly=""/>
                                                    </tr>
                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><textarea style="" name="remarks" >${cardBean.remark}</textarea></td>
                                                    </tr>
                                                    <tr>
                                                        <td></td>
                                                        <td>   
                                                            <input type="submit" class="defbutton" name="approve" value="Approve" onclick="invokeApprovePINReissue('NRPL', 'PIRI')"/>  
                                                            <input type="submit" class="defbutton" name="reject" value="Reject" onclick="invokeRejectPINReissue('REJ', 'PIRI')"/>    
                                                            <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('PIRI')"/></td>
                                                        <td><a  href="#"  onclick="invokeHistory('${cardBean.cardNo}')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                    </tr>
                                                </tbody>
                                            </table> 
                                        </form>
                                    </c:if>

                                    <c:if test="${operation=='ACTI'}">

                                        <form method="POST" name="cardactivate" >
                                            <table border="0" cellspacing="10" cellpadding="0">

                                                <tbody >
                                                    <tr>
                                                        <td width="200px;">Card Number</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cardNo}" name="cardno" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Credit Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.creditLimit}" name="creditlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Cash Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cashLimit}" name="cashlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Expiry Date</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.expiryDate}" name="expirydate" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Card Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.statusDes}" name="status1" readonly=""/>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.status}" name="status" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Priority Level</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.priorityLevel}" name="prioritycode1" readonly=""/>
                                                            <select style="width: 100px" name="prioritycode" disabled="">
                                                                <option value="" >--SELECT--</option>

                                                                <c:forEach var="priority" items="${priorityLevelList}">
                                                                    <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                        <option value="${priority.key}" selected>${priority.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                        <option value="${priority.key}" >${priority.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>


                                                    <tr>
                                                        <td>Reason Code</td>
                                                        <td>
                                                            <input style="color: #999999" type="hidden" value="${cardBean.reasonCode}" name="reasoncode1" readonly=""/>
                                                            <select name="reasoncode" style="width: 163px" disabled="">
                                                                <option value="">--SELECT--</option>
                                                                <c:forEach var="reason" items="${reasonCodeList}">  
                                                                    <c:if test="${cardBean.reasonCode == reason.key}">
                                                                        <option value="${reason.key}" selected>${reason.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${cardBean.reasonCode != reason.key}">
                                                                        <option value="${reason.key}" >${reason.value}</option>
                                                                    </c:if>

                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td></td>
                                                    </tr> 
                                                    <!--                                                    <tr>
                                                                                                            <td>Renewal Confirmation</td>
                                                                                                            <td><input style="color: #999999" type="text" name="renew" value="${cardBean.renew}" readonly="true"/></td>
                                                                                                        </tr>-->
                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><textarea style="" name="remarks" value="${cardBean.remark}" >${cardBean.remark}</textarea></td>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td>   
                                                            <input type="submit" class="defbutton" name="approve" value="Approve" onclick="invokeActivate('ACTI')"/>  
                                                            <input type="submit" class="defbutton" name="reject" value="Reject" onclick="invokeRejectActivate('REJ', 'ACTI')"/>    
                                                            <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeReset()"/></td>

                                                        <td><a  href="#"  onclick="invokeHistory('${cardBean.cardNo}')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                    </tr>
                                                </tbody>
                                            </table> 
                                        </form>
                                    </c:if>

                                </c:if>

                                <!-- **************************************************start table view*******************************************************************************-->
                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Card No</th>
                                            <th>Priority Level</th>
                                            <th>Request Status</th>
                                            <th>Remark</th>
                                            <th>Reason</th>                                           

                                            <th>View</th>                                            

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="requests" items="${searchedList}">
                                            <tr>
                                                <td>${requests.cardNo}</td>
                                                <td>${requests.priorityDes}</td>
                                                <td>${requests.statusDes}</td>
                                                <td>${requests.remark}</td>
                                                <td><c:if test="${requests.reasonCode == 'CDRP'}">
                                                        Card Replace
                                                    </c:if>
                                                    <c:if test="${requests.reasonCode == 'CDRI'}">
                                                        Card Reissue
                                                    </c:if>
                                                    <c:if test="${requests.reasonCode == 'PIRI'}">
                                                        PIN Reissue
                                                    </c:if>

                                                </td>   

                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateRequestApproveServlet?cardNumber=<c:out value="${requests.cardNo}"></c:out>&reasonCode=<c:out value="${requests.reasonCode}"></c:out>'>View</a></td>

                                                </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <br />


                                <!--   ------------------------- end developer area  --------------------------------                      -->
                                <!--<font style="color: red;">*</font>&nbsp;-->



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
