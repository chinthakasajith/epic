<%-- 
    Document   : temporarylimitincrementhome
    Created on : Aug 3, 2012, 1:59:45 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->



        <title>EPIC_CMS_HOME</title>

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <script  type="text/javascript" charset="utf-8">


            function invokeApproved(type)
            {

                if (type == 'CREDIT') {

                    document.tempCreditIncrementForm.action = "${pageContext.request.contextPath}/RequestTempLimitIncrementServlet?type=" + type;
                    document.tempCreditIncrementForm.submit();
                }
                if (type == 'CASH') {

                    document.tempCashIncrementForm.action = "${pageContext.request.contextPath}/RequestTempLimitIncrementServlet?type=" + type;
                    document.tempCashIncrementForm.submit();

                }


            }


            function invokeReject() {

                window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=" + "CCCARD";

            }


        </script>   
        <script>


            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.TEMP_CARD_INCREMANT%>'
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

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

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
                                                        <td>Increment/Decrement </td>

                                                        <td><font style="color: red;">*</font>&nbsp;
                                                            <select  name="incordec"  class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>

                                                                <option value="INC">Increment</option>
                                                                <option value="DEC">Decrement</option>


                                                            </select>

                                                        </td>
                                                    </tr>

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
                                                                    $("#fromdate1").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
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
                                                                    $("#fromdate2").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1
                                                                    });
                                                                });
                                                            </script>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td>Remark</td>
                                                        <td><font style="color: white;">&nbsp;</font>&nbsp;<textarea  name="remarks" class="inputfield-mandatory" maxlength="100" value='${tempBean.remarks}'></textarea></td>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td><input type="submit" style="width: 100px" name="approved" value="Request" onclick="invokeApproved('${"CREDIT"}')" />
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
                                                        <td>Increment/Decrement </td>

                                                        <td><font style="color: red;">*</font>&nbsp;
                                                            <select  name="incordec"  class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>

                                                                <option value="INC">Increment</option>
                                                                <option value="DEC">Decrement</option>


                                                            </select>

                                                        </td>
                                                    </tr>
                                                    
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
                                                                    $("#fromdate3").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
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
                                                                    $("#fromdate4").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
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
                                                        <td><input type="submit" style="width: 100px" name="approved" value="Request" onclick="invokeApproved('${"CASH"}')" />
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
    </body>
</html>