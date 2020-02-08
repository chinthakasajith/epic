<%-- 
    Document   : paymentjsp
    Created on : Apr 5, 2013, 10:38:38 AM
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

        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BO_PAYMENT%>'                                  
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
        <script >
                
            function enableChequeNum(){
                
               
                var val = $('#payid option:selected').val()
                 
                if(val == 'CHEQUE'){
                    
                    $(".field").attr("disabled", false);
                    
                } else if((val == 'CASH')) {
                     
                    $(".field").attr("disabled", true);
                    
                }    
            }
            
            function payreset2(){
                
                window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=resume";
            }
            
            
    function ClosBatch(val,id,tc,ta){
        
        answer = confirm("Do you really want to close the batch ID: " + id + "\n\nTotal transaction count: " + tc + "\n\nTotal transaction amount: " + ta)
        
        if (answer !=0)
        {
            window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param="+val;
        }
        else
        {
            window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=resume";
        }
        
        
    }
    
    function DoPayment(){
    
        ans = confirm("Do you really want to do the payment ?");
        
        if(ans != 0){
            document.paymentform.action="${pageContext.request.contextPath}/ResumePaymentServlet";
            document.paymentform.submit();
        }
        else{
            window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=resume";
        }
        
    
    }
    
    function VoidPayment(txnid,batid){          
        
        window.location="${pageContext.request.contextPath}/VoidPaymentServlet?txnid="+txnid+"&batid="+batid+"&param=payvoid";
    
    }
                             
        </script>  


    </head>
    <body >
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
                                <form name="paymentform" method="POST" action="${pageContext.request.contextPath}/ResumePaymentServlet" >

                                    <table border="0" cellpadding="0" cellspacing="10" >

                                        <tr>
                                            <td style="width: 200px;">Batch ID </td>
                                            <td > </td>
                                            <td><input type="text" readonly name="batchId" value="${bean1.batchId}" style="width: 168px;"/> </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Card Holder Name</td>
                                            <td ><font style="color: red;">*</font>&nbsp; </td>
                                            <td><input type="text"  name="cardHolder" value="${bean.cardHolderName}" style="width: 168px;" maxlength="32" /></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Card Number</td>
                                            <td ><font style="color: red;">*</font>&nbsp; </td>
                                            <td><input type="text"  name="cardNum" value="${bean.cardNumber}" style="width: 168px;" maxlength="23" /></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Payment Type</td>
                                            <td ><font style="color: red;">*</font>&nbsp; </td>

                                            <td><select name="payType" class="inputfield-mandatory" id="payid" onchange="enableChequeNum()" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:if test="${bean.paymentType=='CASH'}">
                                                        <option value="CASH" selected>Cash</option>
                                                    </c:if>
                                                    <c:if test="${bean.paymentType!='CASH'}">
                                                        <option value="CASH" >Cash</option>
                                                    </c:if>
                                                    <c:if test="${bean.paymentType=='CHEQUE'}">
                                                        <option value="CHEQUE" selected>Cheque</option>
                                                    </c:if>
                                                    <c:if test="${bean.paymentType!='CHEQUE'}">
                                                        <option value="CHEQUE" >Cheque</option>
                                                    </c:if>



                                                </select>
                                            </td>
                                        </tr>


                                        <tr >
                                            <td style="width: 100px;">Cheque Number</td>
                                            <td > </td>
                                            <td><input type="text"  name="chequeNum" value="${bean.chequeNumber}" style="width: 168px;" maxlength="32" class="field" disabled="true"/></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Cheque Bank</td>
                                            <td > </td>
                                            <td>
                                                <select name="cheqBank" class="field" disabled="true" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="bank" items="${banklist}">                                                    

                                                        <c:if test="${bean.chequeBank == bank.key}">
                                                            <option value="${bank.key}" selected>${bank.value}</option>
                                                        </c:if>
                                                        <c:if test="${bean.chequeBank != bank.key}">
                                                            <option value="${bank.key}" >${bank.value}</option>
                                                        </c:if>
                                                    </c:forEach>



                                                </select>
                                            </td>
                                        </tr>




                                        <tr>
                                            <td style="width: 100px;">Payment Date</td>
                                            <td > </td>
                                            <td><input type="text" readonly name="payDate" value="${date}" style="width: 168px;" /></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Currency Type</td>
                                            <td ><font style="color: red;">*</font>&nbsp; </td>
                                            <td>
                                                <select name="curType" class="inputfield-mandatory" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="currencyList" items="${currencyList}">                                                    

                                                        <c:if test="${bean.curencyType == currencyList.currencyCode}">
                                                            <option value="${currencyList.currencyCode}" selected>${currencyList.currencyDes}</option>
                                                        </c:if>
                                                        <c:if test="${bean.curencyType != currencyList.currencyCode}">
                                                            <option value="${currencyList.currencyCode}" >${currencyList.currencyDes}</option>
                                                        </c:if>
                                                    </c:forEach>



                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Amount</td>
                                            <td ><font style="color: red;">*</font>&nbsp; </td>
                                            <td><input type="text" name="amount" value="${bean.amount}" style="width: 168px;" maxlength="22" /></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Remark</td>
                                            <td > </td>
                                            <td><TEXTAREA id="remark" name="remark" ROWS="3" style="width: 350px;" maxlength="32"></TEXTAREA></td>
                                        </tr>


                                    </table>


                                    <table border="0" cellpadding="0" cellspacing="10">

                                        <tr style="height: 10px;"></tr>                                        
                                        <tr>
                                            <td style="width: 200px;"></td>
                                            <td >&nbsp;&nbsp;</td>
                                            <td><input type="button" value="Pay" name="pay" class="defbutton" onclick="DoPayment()"/></td>

                                            <td><input type="button" value="Reset" name="reset" class="defbutton" onclick="payreset2()"/></td>

                                            <td><input type="button" value="Batch Close" name="close" class="defbutton" 
                                                       onclick="ClosBatch('close','${bean1.batchId}','${batchbean.totalTxnCount}','${batchbean.totalTxnAmount}')"/></td>

                                        </tr>


                                    </table>
                                    <script>
                                        enableChequeNum();
                                    </script>

                                </form>
                                   
                               
                                                       

                                <br/>

                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>Batch ID</th>
                                                <th>Txn ID</th>                                                
                                                <th>Card Holder Name</th>                                
                                                <th>Card Number</th>
                                                <th>Payment Type</th>
                                                <th>Cheque Number</th>
                                                <th>Cheque Bank</th>                                
                                                <th>Payment Date</th>
                                                <th>Currency Type</th>                                
                                                <th>Amount</th>
                                                <th>Status</th>
                                                <th>Payment Status</th>
                                                <th>Void</th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach  items="${paymentList}" var="pay">
                                                <tr>
                                                    <td>${pay.batchId}</td>
                                                    <td>${pay.txnId}</td>                                                    
                                                    <td>${pay.cardHolderName}</td>
                                                    <td>${pay.cardNumber}</td>
                                                    <td>${pay.paymentType}</td>
                                                    <td>${pay.chequeNumber}</td>
                                                    <td>${pay.chequeBankName}</td>
                                                    <td>${pay.paymentDate}</td>
                                                    <td>${pay.curencyTypeDes}</td>
                                                    <td>${pay.amount}</td>
                                                    <td>${pay.statusDes}</td>
                                                    <td>${pay.payStatusDes}</td>
                                                    <td><a  href='#' onclick="VoidPayment('${pay.txnId}','${pay.batchId}')">Void</a></td>

                                                </tr>
                                            </c:forEach>

                                        </tbody>

                                    </table>
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


