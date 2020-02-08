<%-- 
    Document   : viewcardreports
    Created on : Dec 13, 2012, 3:34:31 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script >
         
            function invokeBack()
            {
              
                window.location = "${pageContext.request.contextPath}/SearchOnlineCardInfoServlet";
               
               
            }
         
        </script>


        <title>CMS MERCHANT MANAGEMENT</title>
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

                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center"><b> CARD DETAILS REPORT </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form name="allApplicationDetails" method="POST" action="">

                                    <table cellpadding="0" cellspacing="10"   >

                                        <tbody>
                                            <tr>
                                                <td>Card Number</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cardNum}</td>
                                            </tr>

                                            <tr>
                                                <td>Card Type</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cardTypeDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Card Domain</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cardDomainDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Currency</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.currencyCodeDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Expiry Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.expDate}</td>
                                            </tr>

                                            <tr>
                                                <td>Credit Limit</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.creditLimit}</td>
                                            </tr>

                                            <tr>
                                                <td>Cash Limit</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cashLimit}</td>
                                            </tr>

                                            <tr>
                                                <td>Pay Type</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.payTypeDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Risk Profile</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.riskProfDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Service Code</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.serviceCodeDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Effect Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.effrctDate}</td>
                                            </tr>

                                            <tr>
                                                <td>New Expiry Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.newExpiryDate}</td>
                                            </tr>

                                            <tr>
                                                <td>Pin Issue Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.pinIssueSDate}</td>
                                            </tr>

                                            <tr>
                                                <td>Pin Count</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.pinCount}</td>
                                            </tr>

                                            <tr>
                                                <td>Transaction Count</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.txnCount}</td>
                                            </tr>

                                            <tr>
                                                <td>Cash Transaction Count</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cashTxnCount}</td>
                                            </tr>

                                            <tr>
                                                <td>Total Transaction Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.totTxnAmount}</td>
                                            </tr>

                                            <tr>
                                                <td>Total Cash Transaction Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.totCashTxnAmount}</td>
                                            </tr>

                                            <tr>
                                                <td>OTB Credit</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.otdCredit}</td>
                                            </tr>

                                            <tr>
                                                <td>OTB Cash</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.otdCash}</td>
                                            </tr>

                                            <tr>
                                                <td>Temporary Credit Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCreditAmount}</td>
                                            </tr>

                                            <tr>
                                                <td>Temporary Cash Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCashAmount}</td>
                                            </tr>

                                            <tr>
                                                <td>Temporary Credit Limit Increment</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCreditLimitIncrment}</td>
                                            </tr>

                                            <tr>
                                                <td>Temporary Cash Limit Increment</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCashLimitIncrement}</td>
                                            </tr>

                                            <tr>
                                                <td>TLI Start Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tleStartDate}</td>
                                            </tr>

                                            <tr>
                                                <td>TLI End Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tleEndDate}</td>
                                            </tr>
                                            <tr>
                                                <td>TLI Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tleStatusDes}</td>
                                            </tr>
                                            <tr>
                                                <td>IBM Offset</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.ibmOffset}</td>
                                            </tr>
                                            <tr>
                                                <td>Pin Method</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.pinMethod}</td>
                                            </tr>
                                            <tr>
                                                <td>PVV</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.pvv}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Verification Method</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cardVerificationMethod}</td>
                                            </tr>
                                            <tr>
                                                <td>EMV Method</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.emvMethod}</td>
                                            </tr>
                                            <tr>
                                                <td>Create Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.createTime}</td>
                                            </tr>
                                            <tr>
                                                <td>Last Updated User</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.lastUpdatedUser}</td>
                                            </tr>
                                            <tr>
                                                <td>Last Updated Time</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.lastUpdatedTime}</td>
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cardStatusDes}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <input type="submit" name="back" value="Back" onclick="invokeBack()" style="width: 100px;"/>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>

                                </form>
                                <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->


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
