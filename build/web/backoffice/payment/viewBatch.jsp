<%-- 
    Document   : viewBatch
    Created on : Apr 17, 2013, 4:09:41 PM
    Author     : ruwan_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>

<!DOCTYPE html>
<html >
    <head>

        <script>

            function summaryView(batchId){
                $.post("${pageContext.request.contextPath}/ViewBatchSummaryServlet", {batchId:batchId},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/backoffice/payment/viewBatchSummary.jsp", iframe:true, height:"90%", width:"90%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }

                });
            }
            
            function invokeClose(batchId){
             
                window.location="${pageContext.request.contextPath}/CloseBatchServlet?batchId="+batchId;
            
            }
            
        </script>    

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >
            <div class="header">
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

                                <table class="tit"> <tr> <td   class="center"> Batch ${paymentBatchBean.batchId}</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <div id="data">
                                    <table>
                                        <tr>
                                            <td width="200px"><b>Batch ID</b></td>
                                            <td> : ${paymentBatchBean.batchId}</td>
                                            <td width="75px"></td>
                                            <td width="200px"><b>Status</b></td>
                                            <td> : ${paymentBatchBean.status}</td>

                                        </tr>
                                        <tr height="10px"></tr>
                                        <tr>
                                            <td width="200px"><b>Total Cash Txn Count</b></td>
                                            <td> : ${cashTxnCount}</td>  

                                            <td width="75px"></td>
                                            <td width="200px"><b>Total Cheque Txn Count</b></td>
                                            <td> : ${chequeTxnCount}</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <br/>
                                    
                                    <table border="0">
                                        <thead>
                                            <tr style="background-color: #7F6C5D;height: 30px;">
                                                <th style="color: #FFFFFF">Currency</th>
                                                <th style="color: #FFFFFF">Payment Type</th>                                         
                                                <th style="color: #FFFFFF">Amount</th>                                           
                                            </tr>
                                        </thead>
                                        <c:forEach var="rec" items="${batchSummary}">
                                            <tr style="background-color: #DBD1C7;">
                                                <td width="200px"><c:out value="${rec.curreny}"/></td> 
                                                <td><c:out value="${rec.paymentType}"/></td>
                                                <td><c:out value="${rec.total}"/></td>
                                            </tr>
                                        </c:forEach>
                                    </table>

                                </div>
                                <br/>
                                <br/>
                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Transaction Id</th>
                                            <th>Card Number</th>
                                            <th>Cardholder Name</th>
                                            <th>Currency Type</th>
                                            <th>Payment Type</th>
                                            <th>Amount</th>  
                                            <th>Status</th> 
                                            <th>EOD Status</th> 
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="payment" items="${paymentList}">                                         
                                            <tr>
                                                <td>${payment.txnId}</td>
                                                <td>${payment.cardNumber}</td>
                                                <td>${payment.cardHolderName}</td>
                                                <td>${payment.curencyType}</td>
                                                <td>${payment.paymentType}</td>
                                                <td>${payment.amount}</td>
                                                <td>${payment.payStatus}</td>
                                                <td>${payment.status}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <c:if test="${paymentBatchBean.status != 'Closed' && paymentBatchBean.status != 'Reconciled'}">
                                    <input onclick="invokeClose('${paymentBatchBean.batchId}')" type="button" value="Close Batch"  style="width: 100px" />
                                </c:if>
                                <c:if test="${paymentBatchBean.status != 'Reconciled'}">
                                    <input type="button" onclick="summaryView('${paymentBatchBean.batchId}')" value="Reconcile Batch" style="width: 100px"/>
                                </c:if>
                                <input onclick="window.history.back();" type="button" value="Back" style="width: 100px"/>


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
