<%-- 
    Document   : viewBatchSummary
    Created on : May 3, 2013, 1:56:10 PM
    Author     : asitha_l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>

        <script>
            function ConfirmReconcile(batchId)
            {
                window.location ="${pageContext.request.contextPath}/ReconcileBatchServlet?batchId="+batchId;
                window.location.reload();
            }
            
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Reconcile Confirmation</title>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>
        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>
            <div class="main">
                <div class="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <form>
                                    <table class="tit"> <tr> <td   class="center"> <b>Reconcile Confirmation</b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
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
                                    <table border="1" class="display">
                                        <thead style="background: #0099FF">
                                            <tr>
                                                <th>Currency</th>
                                                <th style="width:100px">Payment Type</th>                                         
                                                <th>Amount</th>                                           
                                            </tr>
                                        </thead>
                                        <c:forEach var="rec" items="${batchSummary}">
                                            <tr>
                                                <td><c:out value="${rec.curreny}"/></td> 
                                                <td><c:out value="${rec.paymentType}"/></td>
                                                <td><c:out value="${rec.total}"/></td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                    
                                    <br/>
                                    <br/>
                                    
                                    <input onclick="javascript:parent.$.colorbox.close();ConfirmReconcile('${paymentBatchBean.batchId}')" type="button" value="Yes"  style="width: 150px ; height: 40px" />
                                    <input onClick="javascript:parent.$.colorbox.close()" type="button" value="No"  style="width: 150px ; height: 40px" />

                                </form>

                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>

                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>

    </body>
</html>