<%-- 
    Document   : templimitincrementdualauthinvoke
    Created on : Aug 17, 2012, 3:01:05 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <script language="javascript">
            

            
            function popUp(){
        
                $.colorbox({href:"${pageContext.request.contextPath}/backoffice/cardlimitincrement/templimitincrementdualauth.jsp", height:"65%", width:"53%",overlayClose:false});
            }
            
          
            
        </script>
    </head>
    <body onLoad="popUp()"  >

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>

            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 500px">
                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center"> TEMPORARY CARD LIMIT INCREMENT </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <div class="selector" id="tabs">
                                        <ul>
                                            <li><a href="#tabs-1">Credit Increment</a></li>
                                            <li><a href="#tabs-2">Cash Increment</a></li>
                                        </ul>

                                        <!--  ////////////////////////      Tab Number1        /////////////////////                            -->

                                        <div id="tabs-1" >
                                            <form action="" method="POST" name="tempCreditIncrementForm" >

                                                <table cellpadding="0" cellspacing="10">

                                                    <tbody>

                                                        <tr>
                                                            <td>Card Number</td>
                                                            <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.cardNumber}</label><input type="hidden" hidden name="cardNumber" class="inputfield-mandatory" maxlength="20" value='${tempBean.cardNumber}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Credit Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineCreditLimit}</label><input type="hidden" hidden name="creditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.creditLimit}'>
                                                            <input type="hidden" hidden name="onlineCreditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineCreditLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Cash Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineCashLimit}</label><input type="hidden" hidden name="cashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.cashLimit}'>
                                                            <input type="hidden" hidden name="onlineCashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineCashLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Available Credit Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineAvlCreditLimit}</label><input type="hidden" hidden name="avlCreditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.avlCreditLimit}'>
                                                            <input type="hidden" hidden name="onlineAvlCreditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineAvlCreditLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Available Cash Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineAvlCashLimit}</label><input type="hidden" hidden name="avlCashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.avlCashLimit}'>
                                                            <input type="hidden" hidden name="onlineAvlCashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineAvlCashLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Status</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.statusDes}</label><input type="hidden" hidden name="statusDes" class="inputfield-mandatory" maxlength="20" value='${tempBean.statusDes}'></td>
                                                    </tr>

                                                    <tr></tr>

                                                    <tr>
                                                        <td>Amount</td>
                                                        <td><font style="color: white;">&nbsp;&nbsp;</font>&nbsp;<input type="text" name="amount" class="inputfield-Description-mandatory" maxlength="20" value='${tempBean.amount}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Rate</td>
                                                        <td><font style="color: white;">&nbsp;&nbsp;</font>&nbsp;<input type="text" name="rate" class="inputfield-Description-mandatory" maxlength="20" value='${tempBean.rate}'>&nbsp;%
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>Start Date</td>
                                                        <td> <font style="color: red;">*</font>&nbsp;<input  name="fromdate1" readonly maxlength="16" value="${tempBean.startDate}" key="fromdate" id="fromdate1"  />
                                                            
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                $( "#fromdate1" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                            </script>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>End Date</td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input  name="fromdate2" readonly maxlength="16" value="${tempBean.endDate}" key="fromdate" id="fromdate2"  />
                                                            
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                $( "#fromdate2" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                            </script>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><font style="color: white;">&nbsp;</font>&nbsp;<textarea   name="remarks" class="inputfield-mandatory" maxlength="100" value='${tempBean.remarks}'></textarea></td>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td><input type="submit" style="width: 100px" name="approved" value="Approved" onclick="invokeApproved('${"CREDIT"}')" />
                                                            <input type="button" style="width: 100px" name="reject" value="Reject" onclick="invokeReject()"/>
                                                        </td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                        </form>
                                    </div>

                                    <!--    /////////////////    Tab Number2         ///////////////////// -->

                                    <div id="tabs-2">

                                        <form action="" method="POST" name="tempCashIncrementForm" >

                                            <table cellpadding="0" cellspacing="10">

                                                <tbody>

                                                    <tr>
                                                        <td>Card Number</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.cardNumber}</label><input type="hidden" hidden name="cardNumber" class="inputfield-mandatory" maxlength="20" value='${tempBean.cardNumber}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Credit Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineCreditLimit}</label><input type="hidden" hidden name="creditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.creditLimit}'>
                                                            <input type="hidden" hidden name="onlineCreditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineCreditLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Cash Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineCashLimit}</label><input type="hidden" hidden name="cashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.cashLimit}'>
                                                            <input type="hidden" hidden name="onlineCashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineCashLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Available Credit Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineAvlCreditLimit}</label><input type="hidden" hidden name="avlCreditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.avlCreditLimit}'>
                                                            <input type="hidden" hidden name="onlineAvlCreditLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineAvlCreditLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Available Cash Limit</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.onlineAvlCashLimit}</label><input type="hidden" hidden name="avlCashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.avlCashLimit}'>
                                                            <input type="hidden" hidden name="onlineAvlCashLimit" class="inputfield-mandatory" maxlength="20" value='${tempBean.onlineAvlCashLimit}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Status</td>
                                                        <td>:<font style="color: white;"></font>&nbsp;&nbsp;<label>${tempBean.statusDes}</label><input type="hidden" hidden name="statusDes" class="inputfield-mandatory" maxlength="20" value='${tempBean.statusDes}'></td>
                                                    </tr>

                                                    <tr></tr>

                                                    <tr>
                                                        <td>Amount</td>
                                                        <td><font style="color: white;">&nbsp;&nbsp;</font>&nbsp;<input type="text" name="amount" class="inputfield-Description-mandatory" maxlength="20" value='${tempBean.amount}'></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Rate</td>
                                                        <td><font style="color: white;">&nbsp;&nbsp;</font>&nbsp;<input type="text" name="rate" class="inputfield-Description-mandatory" maxlength="20" value='${tempBean.rate}'>&nbsp;%
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>Start Date</td>
                                                        <td> <font style="color: red;">*</font>&nbsp;<input  name="fromdate1" readonly maxlength="16" value="${tempBean.startDate}" key="fromdate" id="fromdate3"  />
                                                            
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                $( "#fromdate3" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                            </script>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>End Date</td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input  name="fromdate2" readonly maxlength="16" value="${tempBean.endDate}" key="fromdate" id="fromdate4"  />
                                                            
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                $( "#fromdate4" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                            </script>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><font style="color: white;">&nbsp;</font>&nbsp;<textarea   name="remarks" class="inputfield-mandatory" maxlength="100" value="'${tempBean.remarks}'"></textarea></td>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td><input type="submit" style="width: 100px" name="approved" value="Approved" onclick="invokeApproved('${"CASH"}')" />
                                                            <input type="button" style="width: 100px" name="reject" value="Reject" onclick="invokeReject()"/>
                                                        </td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                        </form>
                                    </div>
                                </div>

                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>
        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body >
</html>





