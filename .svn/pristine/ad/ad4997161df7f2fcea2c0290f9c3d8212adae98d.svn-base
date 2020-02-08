<%-- 
    Document   : onlinecardinfobasicveiw
    Created on : Dec 13, 2012, 3:40:23 PM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>
            function Backview(value){
                document.viewTableForm.action="${pageContext.request.contextPath}/SearchOnlineCardInfoServlet?adview=no";           
                document.getElementById('cardNum').value=value;    
                document.viewTableForm.submit();
            }
            
           
            
            
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.ONLINE_CARD_INFO%>'                                  
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


                                <form method="POST" action="" name="viewTableForm">
                                    <table border="0">


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
                                                    <input type="submit" name="back" value="Back" onclick="Backview('${cardBean.cardNum}')" style="width: 100px;"/>
                                                </td>
                                            </tr>

                                        </tbody>

                                            

                                    </table>
                                    <input type="hidden" id="cardNum"  name="cardNum" maxlength="20" />

                                </form>







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

