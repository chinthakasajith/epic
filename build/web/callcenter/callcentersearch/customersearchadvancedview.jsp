<%-- 
    Document   : customersearchadvancedview
    Created on : Jul 17, 2012, 3:41:24 PM
    Author     : badrika
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

        <script lang="javascript">

            function invokeBackToSearch( )
            {
                window.location = "${pageContext.request.contextPath}/SearchCallCenterMgtServlet";
            }

        </script>

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CALLCENTERSEARCH%>'
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
                                        <td colspan="3">
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table> 

                                <c:if test="${operationtype=='cardView'}" >
                                    <form method="POST" action="">
                                        <table>
                                            <tr>
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > General </div>
                                                    <table border="0">                                        

                                                            
                                                        <tr>
                                                            <td>Card Number </td>
                                                            <c:if test="${cardBean.cardNumber == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardNumber != null}">
                                                                <td>:${cardBean.cardNumber}</td>
                                                            </c:if>

                                                        </tr> 
                                                        <tr>
                                                            <td>Account Number </td>
                                                            <c:if test="${cardBean.accountNoC == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.accountNoC != null}">
                                                                <td>:${cardBean.accountNoC}</td>
                                                            </c:if>

                                                        </tr>
                                                        <tr>
                                                            <td>Expiry Date </td>
                                                            <c:if test="${cardBean.expDate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.expDate != null}">
                                                                <td>:${cardBean.expDate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Main Card Number </td>
                                                            <c:if test="${cardBean.mainCardNumber == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.mainCardNumber != null}">
                                                                <td>:${cardBean.mainCardNumber}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Card Domain </td>
                                                            <c:if test="${cardBean.cardDomain == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardDomain != null}">
                                                                <td>:${cardBean.cardDomain}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Card Type </td>
                                                            <c:if test="${cardBean.cardType == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardType != null}">
                                                                <td>:${cardBean.cardType}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Card Product </td>
                                                            <c:if test="${cardBean.cardProduct == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardProduct != null}">
                                                                <td>:${cardBean.cardProduct}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Card Category</td>
                                                            <c:if test="${cardBean.cardCatCodeDes == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardCatCodeDes != null}">
                                                                <td>:${cardBean.cardCatCodeDes}</td>
                                                            </c:if>
                                                        </tr>

                                                        <tr>
                                                            <td>Activation Date </td>
                                                            <c:if test="${cardBean.activDate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.activDate != null}">
                                                                <td>:${cardBean.activDate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Name on Card </td>
                                                            <c:if test="${cardBean.nameonCard == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.nameonCard != null}">
                                                                <td>:${cardBean.nameonCard}</td>
                                                            </c:if>
                                                        </tr>                                          

                                                        <tr>
                                                            <td>Issue Date </td>
                                                            <c:if test="${cardBean.issuedDate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.issuedDate != null}">
                                                                <td>:${cardBean.issuedDate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Risk Profile Code </td>
                                                            <c:if test="${cardBean.riskProfileCodeC == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.riskProfileCodeC != null}">
                                                                <td>:${cardBean.riskProfileCodeC}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Currency Code </td>
                                                            <c:if test="${cardBean.curencyNoCode == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.curencyNoCode != null}">
                                                                <td>:${cardBean.curencyNoCode}</td>
                                                            </c:if>
                                                        </tr>

                                                        <tr>
                                                            <td>Priority Level </td>
                                                            <c:if test="${cardBean.priorityLevel == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.priorityLevel != null}">
                                                                <td>:${cardBean.priorityLevel}</td>
                                                            </c:if>
                                                        </tr>

                                                    </table>


                                                </td>
                                                
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > Limits </div>
                                                    <table>
                                                         
                                                        <tr>
                                                            <td>Credit Limit </td>
                                                            <c:if test="${cardBean.creditLimitC == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.creditLimitC != null}">
                                                                <td>:${cardBean.creditLimitC}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Cash Limit </td>
                                                            <c:if test="${cardBean.cashLimitC == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cashLimitC != null}">
                                                                <td>:${cardBean.cashLimitC}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>OTB Credit </td>
                                                            <c:if test="${cardBean.otbCredit == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.otbCredit != null}">
                                                                <td>:${cardBean.otbCredit}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>OTB Cash </td>
                                                            <c:if test="${cardBean.otbCash == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.otbCash != null}">
                                                                <td>:${cardBean.otbCash}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Temporary Credit Amount  </td>
                                                            <c:if test="${cardBean.tempCrediAmt == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.tempCrediAmt != null}">
                                                                <td>:${cardBean.tempCrediAmt}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Temporary Cash Amount </td>
                                                            <c:if test="${cardBean.tempCashAmt == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.tempCashAmt != null}">
                                                                <td>:${cardBean.tempCashAmt}</td>
                                                            </c:if>
                                                        </tr>
                                                        
                                                        <tr>
                                                            <td>Interest Rate </td>
                                                            <c:if test="${cardBean.interestRate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.interestRate != null}">
                                                                <td>:${cardBean.interestRate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Cash Advanced Interest Rate </td>
                                                            <c:if test="${cardBean.cashAdvancedInterestRate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cashAdvancedInterestRate != null}">
                                                                <td>:${cardBean.cashAdvancedInterestRate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Min Payment Due </td>
                                                            <c:if test="${cardBean.minPaymentDue == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.minPaymentDue != null}">
                                                                <td>:${cardBean.minPaymentDue}</td>
                                                            </c:if>
                                                        </tr>
                                                        
                                                        
                                                        
                                                        
                                                        

                                                    </table>   

                                                </td>                                               
                                                

                                            </tr>
                                            <tr style="height: 10px;"></tr>
                                            <tr>
                                                <td valign="top">
                                                    <div class="subsectionheader" > Status </div>
                                                    <table>
                                                        
                                                        <tr>
                                                            <td>Card Status </td>
                                                            <c:if test="${cardBean.cardStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardStatus != null}">
                                                                <td>:${cardBean.cardStatus}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Emboss Status </td>
                                                            <c:if test="${cardBean.embossStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.embossStatus != null}">
                                                                <td>:${cardBean.embossStatus}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Encode Status </td>
                                                            <c:if test="${cardBean.encodeStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.encodeStatus != null}">
                                                                <td>:${cardBean.encodeStatus}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>PIN Status </td>
                                                            <c:if test="${cardBean.pinStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.pinStatus != null}">
                                                                <td>:${cardBean.pinStatus}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>PIN Mail Status </td>
                                                            <c:if test="${cardBean.pinMailStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.pinMailStatus != null}">
                                                                <td>:${cardBean.pinMailStatus}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Card Distribution Status </td>
                                                            <c:if test="${cardBean.cardDisStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardDisStatus != null}">
                                                                <td>:${cardBean.cardDisStatus}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>PIN Distribution Status </td>
                                                            <c:if test="${cardBean.pinDisStatus == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.pinDisStatus != null}">
                                                                <td>:${cardBean.pinDisStatus}</td>
                                                            </c:if>
                                                        </tr>                                                

                                                    </table>
                                                    
                                                    
                                                </td>
                                                <td valign="top">
                                                    <div class="subsectionheader" > Last Statement </div>
                                                    <table>
                                                        
                                                        <tr >
                                                            <td>Opening Balance </td>
                                                            <c:if test="${cardBean.openingBalance == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.openingBalance != null}">
                                                                <td>:${cardBean.openingBalance}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Closing Balance </td>
                                                            <c:if test="${cardBean.closingBalance == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.closingBalance != null}">
                                                                <td>:${cardBean.closingBalance}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Min Amount </td>
                                                            <c:if test="${cardBean.minAmount == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.minAmount != null}">
                                                                <td>:${cardBean.minAmount}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Payment </td>
                                                            <c:if test="${cardBean.payment == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.payment != null}">
                                                                <td>:${cardBean.payment}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Due Date </td>
                                                            <c:if test="${cardBean.dueDate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.dueDate != null}">
                                                                <td>:${cardBean.dueDate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Statement Start Date </td>
                                                            <c:if test="${cardBean.statementStartDate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.statementStartDate != null}">
                                                                <td>:${cardBean.statementStartDate}</td>
                                                            </c:if>
                                                        </tr>
                                                        <tr>
                                                            <td>Statement End Date </td>
                                                            <c:if test="${cardBean.statementEndDate == null}">
                                                                <td>:-</td>
                                                            </c:if>
                                                            <c:if test="${cardBean.statementEndDate != null}">
                                                                <td>:${cardBean.statementEndDate}</td>
                                                            </c:if>
                                                        </tr>                                                         

                                                    </table>
                                                    
                                                    
                                                </td>
                                            </tr>
                                            <tr style="height: 10px;"></tr>
                                            
                                        </table>
                                            
                                            
                                        <table border="1" class="display" >                                                    
                                           <div class="subsectionheader" > Standing Orders </div>
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <!--<th>Description</th>-->
                                                <th>Start Date</th>                                               
                                                <th>End Date</th>
                                                <th>Amount</th>
                                                <th>Currency Type</th>
                                                <th>Frequency Type</th>
                                                <th>Next Date </th>
                                                <th>Status </th>
                                                


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${cardBean.stdorderList}" var="stdorder">
                                                <tr>
                                                    <td>${stdorder.id}</td>
                                                    <!--<td>${stdorder.description}</td>-->
                                                    <td>${stdorder.startDate}</td>
                                                    <td>${stdorder.endDate}</td>
                                                    <td>${stdorder.amount}</td>
                                                    <td>${stdorder.currencyType}</td>
                                                    <td>${stdorder.frequencyType}</td>
                                                    <td>${stdorder.nextDate}</td>
                                                    <td>${stdorder.status}</td>
                                                   
                                                    
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                        <tr style="height: 10px;"></tr>
                                    </table>
                                                  
                                    <table>
                                            <tr>                                                
                                                <td style="width: 300px;">
                                                    <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeBackToSearch()" />
                                                </td>
                                            </tr>
                                    </table>
                                        
                                    </form>
                                    <b>Last 5 Transactions-Online</b>
                                    <table border="1" class="display" id="scoreltableview">
                                        <thead>
                                            <tr>
                                                <th>TXN Type Code</th>
                                                <th>TXN Amount</th>
                                                <th>TXN Currency</th>
                                                <th>Billing Amount</th>
                                                <th>Billing Currency</th>
                                                <th>Settlement Amount</th>
                                                <th>Settlement Currency </th>
                                                <th>Settlement Date </th>
                                                <th>Country Code</th>
                                                <th>RRN</th>
                                                <th>Response Code</th>
                                                <th>Status</th>
                                                <th>MCC</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${cardBean.onlinetxnList}" var="onlinetxn">
                                                <tr>
                                                    <td>${onlinetxn.txnType}</td>
                                                    <td>${onlinetxn.txnAmount}</td>
                                                    <td>${onlinetxn.txnCurrency}</td>
                                                    <td>${onlinetxn.billingAmount}</td>
                                                    <td>${onlinetxn.billingCurrency}</td>
                                                    <td>${onlinetxn.settlementAmount}</td>
                                                    <td>${onlinetxn.settlementCurrency}</td>
                                                    <td>${onlinetxn.settlementDate}</td>
                                                    <td>${onlinetxn.countryCode}</td>
                                                    <td>${onlinetxn.rrn}</td>
                                                    <td>${onlinetxn.responseCode}</td>
                                                    <td>${onlinetxn.status}</td>
                                                    <td>${onlinetxn.mcc}</td>
                                                    
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                    <b>Last 5 Transactions-Backend</b>
                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>TXN Type Code</th>
                                                <th>TXN Amount</th>
                                                <th>TXN Currency</th>
                                                <th>Billing Amount</th>
                                                <th>Billing Currency</th>
                                                <th>Settlement Amount</th>
                                                <th>Settlement Currency </th>
                                                <th>Settlement Date </th>
                                                <th>Country Code</th>
                                                <th>RRN</th>
                                                <th>Response Code</th>
                                                <th>Status</th>
                                                <th>EOD Status</th>
                                                <th>MCC</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${cardBean.backendtxnList}" var="txn">
                                                <tr>
                                                    <td>${txn.txnType}</td>
                                                    <td>${txn.txnAmount}</td>
                                                    <td>${txn.txnCurrency}</td>
                                                    <td>${txn.billingAmount}</td>
                                                    <td>${txn.billingCurrency}</td>
                                                    <td>${txn.settlementAmount}</td>
                                                    <td>${txn.settlementCurrency}</td>
                                                    <td>${txn.settlementDate}</td>
                                                    <td>${txn.countryCode}</td>
                                                    <td>${txn.rrn}</td>
                                                    <td>${txn.responseCode}</td>
                                                    <td>${txn.status}</td>
                                                    <td>${txn.eodStatus}</td>
                                                    <td>${txn.mcc}</td>
                                                    
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                    
                                    
                                    
                                    
                                </c:if>
                                <c:if test="${operationtype=='cusView'}" >
                                    <form method="POST" action="">
                                        
                                        <table>
                                            <tr>
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > General </div>
                                                    
                                                    <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Customer ID </td>
                                                    <c:if test="${cusBean.custmerIdCC == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.custmerIdCC != null}">
                                                        <td>:${cusBean.custmerIdCC}</td>
                                                    </c:if>
                                                </tr>    
                                                
                                                <tr>
                                                    <td>Customer Name </td>
                                                    <c:if test="${cusBean.customerName == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.customerName != null}">
                                                        <td>:${cusBean.titlepd} ${cusBean.customerName}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Gender </td>
                                                    <c:if test="${cusBean.genderpd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.genderpd != null}">
                                                        <td>:${cusBean.genderpd}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Nationality </td>
                                                    <c:if test="${cusBean.nationality == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.nationality != null}">
                                                        <td>:${cusBean.nationality}</td>
                                                    </c:if>
                                                </tr>  
                                                
                                                <tr>
                                                    <td>Identification Type</td>
                                                    <c:if test="${cusBean.idTypeCC == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.idTypeCC != null}">
                                                        <td>:${cusBean.idTypeCC}</td>
                                                    </c:if>
                                                </tr>                                               
                                                <tr>
                                                    <td>Identification Number </td>
                                                    <c:if test="${cusBean.idNumberCC == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.idNumberCC != null}">
                                                        <td>:${cusBean.idNumberCC}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Passport Expiry </td>
                                                    <c:if test="${cusBean.passportexpiredate == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.passportexpiredate != null}">
                                                        <td>:${cusBean.passportexpiredate}</td>
                                                    </c:if>
                                                </tr>
<!--                                                <tr>
                                                    <td>Age </td>
                                                    <c:if test="${cusBean.age == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.age != null}">
                                                        <td>:${cusBean.age}</td>
                                                    </c:if>
                                                </tr>-->
                                                
                                                <tr>
                                                    <td>Date of Birth </td>
                                                    <c:if test="${cusBean.dateofbirthpd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.dateofbirthpd != null}">
                                                        <td>:${cusBean.dateofbirthpd}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Marital Status </td>
                                                    <c:if test="${cusBean.maritalstatuspd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.maritalstatuspd != null}">
                                                        <td>:${cusBean.maritalstatuspd}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Name on Card </td>
                                                    <c:if test="${cusBean.nameoncardpd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.nameoncardpd != null}">
                                                        <td>:${cusBean.nameoncardpd}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Mothers Maiden Name </td>
                                                    <c:if test="${cusBean.mothersmaidenname == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.mothersmaidenname != null}">
                                                        <td>:${cusBean.mothersmaidenname}</td>
                                                    </c:if>
                                                </tr> 
                                                
                                                <tr>
                                                    <td>Staff Status </td>
                                                    <c:if test="${cusBean.staffStatusCAP == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.staffStatusCAP != null}">
                                                        <td>:${cusBean.staffStatusCAP}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>E-mail </td>
                                                    <c:if test="${cusBean.emailpd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.emailpd != null}">
                                                        <td>:${cusBean.emailpd}</td>
                                                    </c:if>
                                                </tr>    
                                                
                                                <tr>
                                                    <td>Mobile </td>
                                                    <c:if test="${cusBean.mobilenopd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.mobilenopd != null}">
                                                        <td>:${cusBean.mobilenopd}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Emergency Contact NO </td>
                                                    <c:if test="${cusBean.emergencycontactno == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.emergencycontactno != null}">
                                                        <td>:${cusBean.emergencycontactno}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Home Tel. </td>
                                                    <c:if test="${cusBean.hometelephoneno == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.hometelephoneno != null}">
                                                        <td>:${cusBean.hometelephoneno}</td>
                                                    </c:if>
                                                </tr>  
                                                
                                                <tr>
                                                    <td>Office Tel.</td>
                                                    <c:if test="${cusBean.officephoneno == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.officephoneno != null}">
                                                        <td>:${cusBean.officephoneno}</td>
                                                    </c:if>
                                                </tr>
                                                

                                            </tbody>
                                        </table>
                                                    
                                                    
                                                    
                                                </td>
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > Address </div>
                                                    
                                                    <table border="0">
                                            <tbody>
                                                                                               
                                                <tr>
                                                    <td>Address </td>
                                                    <c:if test="${cusBean.address1 == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.address1 != null}">
                                                        <td>:${cusBean.address1}, ${cusBean.address2}, ${cusBean.address3}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>City </td>
                                                    <c:if test="${cusBean.citypd == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.citypd != null}">
                                                        <td>:${cusBean.citypd}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>District & Province </td>
                                                    <c:if test="${cusBean.residedistrict == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.residedistrict != null}">
                                                        <td>:${cusBean.residedistrict}, ${cusBean.resideprovince}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Billing Address </td>
                                                    <c:if test="${cusBean.billingaddress1 == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.billingaddress1 != null}">
                                                        <td>:${cusBean.billingaddress1}, ${cusBean.billingaddress2}, ${cusBean.billingaddress3}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Billing City </td>
                                                    <c:if test="${cusBean.billingcity == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.billingcity != null}">
                                                        <td>:${cusBean.billingcity}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Billing District & Province </td>
                                                    <c:if test="${cusBean.billingdistrict == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.billingdistrict != null}">
                                                        <td>:${cusBean.billingdistrict}, ${cusBean.billingprovince}</td>
                                                    </c:if>
                                                </tr> 
                                                
                                                
                                                <tr>
                                                    <td>Permanent Address </td>
                                                    <c:if test="${cusBean.permanentaddress1 == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.permanentaddress1 != null}">
                                                        <td>:${cusBean.permanentaddress1}, ${cusBean.permanentaddress2}, ${cusBean.permanantaddress3}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Permanent City </td>
                                                    <c:if test="${cusBean.permanaentcity == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.permanaentcity != null}">
                                                        <td>:${cusBean.permanaentcity}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Permanent District & Province </td>
                                                    <c:if test="${cusBean.permanentdistrict == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.permanentdistrict != null}">
                                                        <td>:${cusBean.permanentdistrict}, ${cusBean.permanentprovince}</td>
                                                    </c:if>
                                                </tr> 
                                                
                                                

                                            </tbody>
                                        </table>
                                                    
                                                    
                                                    
                                                </td>
                                                
                                            </tr>
                                            <tr style="height: 10px;"></tr>
                                            <tr>
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > Referee </div>
                                                    
                                                    <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Name </td>
                                                    <c:if test="${cusBean.relativename == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relativename != null}">
                                                        <td>:${cusBean.relativename}</td>
                                                    </c:if>
                                                </tr>    
                                                
                                                <tr>
                                                    <td>Mobile </td>
                                                    <c:if test="${cusBean.relmobileno == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relmobileno != null}">
                                                        <td>:${cusBean.relmobileno}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Home Tel. </td>
                                                    <c:if test="${cusBean.relresidancephone == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relresidancephone != null}">
                                                        <td>:${cusBean.relresidancephone}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Office Tel.</td>
                                                    <c:if test="${cusBean.relofficephone == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relofficephone != null}">
                                                        <td>:${cusBean.relofficephone}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>E-mail </td>
                                                    <c:if test="${cusBean.relemail == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relemail != null}">
                                                        <td>:${cusBean.relemail}</td>
                                                    </c:if>
                                                </tr>
                                                  
                                                <tr>
                                                    <td>Relationship </td>
                                                    <c:if test="${cusBean.relationship == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relationship != null}">
                                                        <td>:${cusBean.relationship}</td>
                                                    </c:if>
                                                </tr>
                                                                                              
                                                <tr>
                                                    <td>Address </td>
                                                    <c:if test="${cusBean.reladdress1 == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.reladdress1 != null}">
                                                        <td>:${cusBean.reladdress1}, ${cusBean.reladdress2}, ${cusBean.reladdress3}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>City </td>
                                                    <c:if test="${cusBean.relcity == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.relcity != null}">
                                                        <td>:${cusBean.relcity}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>District & Province </td>
                                                    <c:if test="${cusBean.reldistrict == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.reldistrict != null}">
                                                        <td>:${cusBean.reldistrict}, ${cusBean.relprovince}</td>
                                                    </c:if>
                                                </tr>                                                

                                            </tbody>
                                        </table>
                                                    
                                                    
                                                </td>
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > Spouse & Dependants </div>
                                                    
                                                    
                                                    <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Name </td>
                                                    <c:if test="${cusBean.spousename == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.spousename != null}">
                                                        <td>:${cusBean.spousename}</td>
                                                    </c:if>
                                                </tr>   
                                                
                                                <tr>
                                                    <td>Identification Number </td>
                                                    <c:if test="${cusBean.spousenic == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.spousenic != null}">
                                                        <td>:${cusBean.spousenic}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Passport Number </td>
                                                    <c:if test="${cusBean.spousepassportno == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.spousepassportno != null}">
                                                        <td>:${cusBean.spousepassportno}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Telephone </td>
                                                    <c:if test="${cusBean.spousephone == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.spousephone != null}">
                                                        <td>:${cusBean.spousephone}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>E-mail </td>
                                                    <c:if test="${cusBean.spouseemail == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.spouseemail != null}">
                                                        <td>:${cusBean.spouseemail}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Date of Birth </td>
                                                    <c:if test="${cusBean.spousedateofbirth == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.spousedateofbirth != null}">
                                                        <td>:${cusBean.spousedateofbirth}</td>
                                                    </c:if>
                                                </tr>  
                                                
                                                <tr>
                                                    <td>No of Dependants </td>
                                                    <c:if test="${cusBean.noofdependance == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.noofdependance != null}">
                                                        <td>:${cusBean.noofdependance}</td>
                                                    </c:if>
                                                </tr>                                               
                                                
                                                
                                                

                                            </tbody>
                                        </table>
                                                    
                                                </td>
                                                
                                            </tr>
                                                                                           
                                            
                                        </table>
                                        
                                        <table>
                                            <tr>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="Back" style="width: 100px" onclick="invokeBackToSearch()" />
                                                    </td>

                                            </tr>
                                        </table>
                                        
                                        

                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='accView'}" >
                                    <form method="POST" action="">
                                        
                                        <table>
                                            <tr>
                                                <td>
                                                    <table border="0">
                                            <tbody>
                                                
                                                
                                                <tr>
                                                    <div class="subsectionheader" > General </div>
                                                    <td>Account Number </td>
                                                    <c:if test="${accBean.accountNoCA == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.accountNoCA != null}">
                                                        <td>:${accBean.accountNoCA}</td>
                                                    </c:if>
                                                </tr>                                                
                                                <tr>
                                                    <td>Customer ID</td>
                                                    <c:if test="${accBean.custmerIdCA == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.custmerIdCA != null}">
                                                        <td>:${accBean.custmerIdCA}</td>
                                                    </c:if>
                                                </tr>                                               
                                                <tr>
                                                    <td>Billing ID </td>
                                                    <c:if test="${accBean.billingId == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.billingId != null}">
                                                        <td>:${accBean.billingId}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Currency Code</td>
                                                    <c:if test="${accBean.currencyCodeCA == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.currencyCodeCA != null}">
                                                        <td>:${accBean.currencyCodeCA}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Status</td>
                                                    <c:if test="${accBean.status == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.status != null}">
                                                        <td>:${accBean.status}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Account Type</td>
                                                    <c:if test="${accBean.accounttypeonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.accounttypeonac != null}">
                                                        <td>:${accBean.accounttypeonac}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Account Owner</td>
                                                    <c:if test="${accBean.accountowneronac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.accountowneronac != null}">
                                                        <td>:${accBean.accountowneronac}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Loyalty Points</td>
                                                    <c:if test="${accBean.loyaltypointsonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.loyaltypointsonac != null}">
                                                        <td>:${accBean.loyaltypointsonac}</td>
                                                    </c:if>
                                                </tr>                                                

                                            </tbody>
                                        </table>
                                                    
                                                </td>
                                            </tr>
                                            <tr style="height: 10px;"></tr>
                                            <tr>
                                                <td>
                                                    <table border="0">
                                            <tbody>                                               
                                                
                                                
                                                <tr>
                                                    <div class="subsectionheader" > Limits </div>
                                                    <td>Credit Limit </td>
                                                    <c:if test="${accBean.creditLimitCA == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.creditLimitCA != null}">
                                                        <td>:${accBean.creditLimitCA}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Cash Limit </td>
                                                    <c:if test="${accBean.cashLimitCA == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.cashLimitCA != null}">
                                                        <td>:${accBean.cashLimitCA}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>OTB Credit </td>
                                                    <c:if test="${accBean.otbcreditonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.otbcreditonac != null}">
                                                        <td>:${accBean.otbcreditonac}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td> OTB Cash</td>
                                                    <c:if test="${accBean.otbcashonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.otbcashonac != null}">
                                                        <td>:${accBean.otbcashonac}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>TXN Count </td>
                                                    <c:if test="${accBean.txncountonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.txncountonac != null}">
                                                        <td>:${accBean.txncountonac}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Cash TXN Count </td>
                                                    <c:if test="${accBean.cashtxncountonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.cashtxncountonac != null}">
                                                        <td>:${accBean.cashtxncountonac}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Total TXN Amount </td>
                                                    <c:if test="${accBean.totaltxnamountonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.totaltxnamountonac != null}">
                                                        <td>:${accBean.totaltxnamountonac}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Total Cash TXN Amount </td>
                                                    <c:if test="${accBean.totalcashtxnamountonac == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.totalcashtxnamountonac != null}">
                                                        <td>:${accBean.totalcashtxnamountonac}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                

                                            </tbody>
                                        </table>
                                                    
                                                </td>
                                            </tr>
                                            <tr style="height: 10px;"></tr>
                                            <tr>
                                                <td>
                                                    <table border="0">
                                            <tbody>                                                
                                                
                                                <tr>
                                                    <div class="subsectionheader" > Profiles </div>
                                                    <td>Risk Profile Code </td>
                                                    <c:if test="${accBean.riskProfileCodeCA == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${accBean.riskProfileCodeCA != null}">
                                                        <td>:${accBean.riskProfileCodeCA}</td>
                                                    </c:if>
                                                </tr>
                                                


                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeBackToSearch()" />
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>
                                                    
                                                </td>
                                            </tr>
                                            
                                            
                                        </table>  

                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='appView'}" >                                    
                                    
                                    <form method="POST" action="">
                                        
                                        <table>
                                            <tr>
                                                
                                            <td valign="top" style="width: 480px;">
                                                <div class="subsectionheader" > General </div>
                                                <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Application ID </td>
                                                    <c:if test="${appBean.appId == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.appId != null}">
                                                        <td>:${appBean.appId}</td>
                                                    </c:if>
                                                </tr>                                                
                                                <tr>
                                                    <td>Identification Type</td>                                                    
                                                    <c:if test="${appBean.idTypeCAP == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.idTypeCAP != null}">
                                                        <td>:${appBean.idTypeCAP}</td>
                                                    </c:if>
                                                </tr>                                               
                                                <tr>
                                                    <td>Identification Number </td> 
                                                    <c:if test="${appBean.idNumberCAP == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.idNumberCAP != null}">
                                                        <td>:${appBean.idNumberCAP}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Passport Expiry Date </td> 
                                                    <c:if test="${appBean.passportexpirydate == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.passportexpirydate != null}">
                                                        <td>:${appBean.passpo999}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Name </td>
                                                    <c:if test="${cusBean.customerName == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${cusBean.customerName != null}">
                                                        <td>:${cusBean.customerName}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Mobile Number </td>
                                                    <c:if test="${appBean.mobileNo == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.mobileNo != null}">
                                                        <td>:${appBean.mobileNo}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>E-mail </td>
                                                    <c:if test="${appBean.email == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.email != null}">
                                                        <td>:${appBean.email}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Staff Status </td>
                                                    <c:if test="${appBean.staffStatusCAP == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.staffStatusCAP != null}">
                                                        <td>:${appBean.staffStatusCAP}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Card Number</td>
                                                    <c:if test="${appBean.cardnumbercap == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.cardnumbercap != null}">
                                                        <td>:${appBean.cardnumbercap}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level Code </td>
                                                    <c:if test="${appBean.priorityLevelCode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.priorityLevelCode != null}">
                                                        <td>:${appBean.priorityLevelCode}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Assign Status </td>
                                                    <c:if test="${appBean.assignstatus == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.assignstatus != null}">
                                                        <td>:${appBean.assignstatus}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Assign User </td>
                                                    <c:if test="${appBean.assignuser == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.assignuser != null}">
                                                        <td>:${appBean.assignuser}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Branch Code </td>
                                                    <c:if test="${appBean.branchCode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.branchCode != null}">
                                                        <td>:${appBean.branchCode}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Referential Emp No </td>
                                                    <c:if test="${appBean.referancialempno == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.referancialempno != null}">
                                                        <td>:${appBean.referancialempno}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Net Expenses </td>
                                                    <c:if test="${appBean.netexpenses == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.netexpenses != null}">
                                                        <td>:${appBean.netexpenses}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Net Income </td>
                                                    <c:if test="${appBean.netincome == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.netincome != null}">
                                                        <td>:${appBean.netincome}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Net Profit </td>
                                                    <c:if test="${appBean.netprofit == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.netprofit != null}">
                                                        <td>:${appBean.netprofit}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>CRIB Report </td>
                                                    <c:if test="${appBean.cribreport == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.cribreport != null}">
                                                        <td>:${appBean.cribreport}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Recommend Letter</td>
                                                    <c:if test="${appBean.recommendletter == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.recommendletter != null}">
                                                        <td>:${appBean.recommendletter}</td>
                                                    </c:if>
                                                </tr>                                                

                                            </tbody>
                                        </table>
                                                </td>
                                                <td valign="top" style="width: 480px;">
                                                    <div class="subsectionheader" > Templates & Other </div>
                                                    
                                                    <table>
                                                    
                                                    <tr>
                                                    <td>Account Template Code </td>
                                                    <c:if test="${appBean.acctemplatecode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.acctemplatecode != null}">
                                                        <td>:${appBean.acctemplatecode}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>BIN Profile </td>
                                                    <c:if test="${appBean.binprofile == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.binprofile != null}">
                                                        <td>:${appBean.binprofile}</td>
                                                    </c:if>
                                                </tr>  
                                                
                                                <tr>
                                                    <td>Card Template Code </td>
                                                    <c:if test="${appBean.cardtemplatecode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.cardtemplatecode != null}">
                                                        <td>:${appBean.cardtemplatecode}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Customer Template Code </td>
                                                    <c:if test="${appBean.custemplatecode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.custemplatecode != null}">
                                                        <td>:${appBean.custemplatecode}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Production Mode </td>
                                                    <c:if test="${appBean.productionmode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.productionmode != null}">
                                                        <td>:${appBean.productionmode}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Card Category </td>
                                                    <c:if test="${appBean.cardcategory == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.cardcategory != null}">
                                                        <td>:${appBean.cardcategory}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Card Key ID </td>
                                                    <c:if test="${appBean.cardkeyid == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.cardkeyid != null}">
                                                        <td>:${appBean.cardkeyid}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Score Card </td>
                                                    <c:if test="${appBean.scorecard == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.scorecard != null}">
                                                        <td>:${appBean.scorecard}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Sys. Rec. Card Product </td>
                                                    <c:if test="${appBean.sysrecomendedcardproduct == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.sysrecomendedcardproduct != null}">
                                                        <td>:${appBean.sysrecomendedcardproduct}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Sys. Rec. Credit Limit </td>
                                                    <c:if test="${appBean.sysRecCreditLimit == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.sysRecCreditLimit != null}">
                                                        <td>:${appBean.sysRecCreditLimit}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Sys. Rec. Currency </td>
                                                    <c:if test="${appBean.sysrecomendedcurrency == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.sysrecomendedcurrency != null}">
                                                        <td>:${appBean.sysrecomendedcurrency}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Confirmed Card Product </td>
                                                    <c:if test="${appBean.confCardProduct == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.confCardProduct != null}">
                                                        <td>:${appBean.confCardProduct}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Confirmed Credit Limit </td>
                                                    <c:if test="${appBean.confCreditLimit == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.confCreditLimit != null}">
                                                        <td>:${appBean.confCreditLimit}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Confirmed Currency </td>
                                                    <c:if test="${appBean.confirmedcurrency == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.confirmedcurrency != null}">
                                                        <td>:${appBean.confirmedcurrency}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Credit Score </td>
                                                    <c:if test="${appBean.creditScore == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.creditScore != null}">
                                                        <td>:${appBean.creditScore}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Loyalty Required </td>
                                                    <c:if test="${appBean.loyaltyrequired == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.loyaltyrequired != null}">
                                                        <td>:${appBean.loyaltyrequired}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Status </td>
                                                    <c:if test="${appBean.statusCAP == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.statusCAP != null}">
                                                        <td>:${appBean.statusCAP}</td>
                                                    </c:if>
                                                </tr>
                                                
                                                </table>
                                                    
                                                </td>
                                            </tr>
                                            <tr style="height: 10px;"></tr>
                                            <tr>
                                                <td valign="top">
                                                    <div class="subsectionheader" > Signatures </div>
                                                    <table>
                                                    <tr>
                                                    <td>Signature </td>
                                                    <c:if test="${appBean.signature == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.signature != null}">
                                                        <td>:${appBean.signature}</td>
                                                    </c:if>
                                                </tr>                                                
                                                
                                                <tr>
                                                    <td>Primary Signature </td>
                                                    <c:if test="${appBean.primarysignature == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.primarysignature != null}">
                                                        <td>:${appBean.primarysignature}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Joint Signature </td>
                                                    <c:if test="${appBean.jointsignature == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.jointsignature != null}">
                                                        <td>:${appBean.jointsignature}</td>
                                                    </c:if>
                                                </tr>
                                             </table>
                                                    
                                                </td>
                                                
                                                <td valign="top">
                                                    <div class="subsectionheader" > Rejections </div>
                                                    <table>
                                                    <tr>
                                                    <td>Reject Reason Code </td>
                                                    <c:if test="${appBean.rejectreasoncode == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.rejectreasoncode != null}">
                                                        <td>:${appBean.rejectreasoncode}</td>
                                                    </c:if>
                                                </tr>                                                
                                                
                                                <tr>
                                                    <td>Reject Remarks </td>
                                                    <c:if test="${appBean.rejectremarks == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.rejectremarks != null}">
                                                        <td>:${appBean.rejectremarks}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Reject Verify Remarks </td>
                                                    <c:if test="${appBean.rejectverifyremarks == null}">
                                                        <td>:-</td>
                                                    </c:if>
                                                    <c:if test="${appBean.rejectverifyremarks != null}">
                                                        <td>:${appBean.rejectverifyremarks}</td>
                                                    </c:if>
                                                </tr>
                                             </table>
                                                    
                                                </td>
                                                
                                            </tr>
                                        </table>
                                        
                                        <table>
                                            <tr>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeBackToSearch()" />
                                                    </td>

                                                </tr>
                                        </table>
                                        
                                        
                                        
                                        

                                    </form>
                                </c:if>



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
