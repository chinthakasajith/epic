<%-- 
    Document   : merchantcustomertxnexplorer
    Created on : Feb 13, 2013, 9:47:49 AM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            
             function invokeTransactionHistory(value){

                $.post("${pageContext.request.contextPath}/ViewTxnExpHistoryServlet", {txnId:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/reportexp/transactionexp/transactionhistoryview.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }

                });
            }
            
            var oTable;
            $(document).ready(function() {
            
                //load table
                oTable = $('#jsontable').dataTable({
                    "bServerSide": true,
                    "sAjaxSource": "${pageContext.servletContext.contextPath}/LoadMerchantCustomerTxnExpServlet",
                    "bProcessing": true,
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "aoColumns": [
                        { "mDataProp": "txnid","bVisible": true },
                        { "mDataProp": "txntype","bVisible": true },
                        { "mDataProp": "bin","bVisible": true },
                        { "mDataProp": "cardno","bVisible": true },
                        { "mDataProp": "expirydate","bVisible": true },
                        { "mDataProp": "txncurrency","bVisible": false },
                        { "mDataProp": "txnamount","bVisible": true },
                        { "mDataProp": "settledate","bVisible": false },
                        { "mDataProp": "terminalid","bVisible": false },
                        { "mDataProp": "merchantid","bVisible": false },
                        { "mDataProp": "authcode","bVisible": false },
                        { "mDataProp": "traceno","bVisible": false },
                        { "mDataProp": "txndate","bVisible": true },
                        { "mDataProp": "statusdes","bVisible": true },
                        { "mDataProp": "txntime","bVisible": true },
                        { "mDataProp": "view","bSortable": false}
                    ]});
            });
            //

        </script>

        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_MERCHANT_CUSTOMER_TXN_EXPLORER%>'                                  
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
        <title>EPIC CMS TRANSACTION EXPLORER</title>
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
                <div class="content" style="height: 400px">
                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>

                                <form method="POST" action="" name="searchcallhistoryform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                            <td></td>
                                        </tr>
                                    </table>

                                    <table  border="1"  class="display" id="jsontable">
                                        <thead>
                                            <tr><th>Txn Id </th>
                                                <th>Txn Type</th>
                                                <th>BIN</th>
                                                <th>Card Number</th>
                                                <th>Expiry Date</th>
                                                <th>Txn Currency</th>
                                                <th>Txn Amount</th>
                                                <th>Settlement Date</th>
                                                <th>Terminal Id</th>
                                                <th>Merchant Id</th>
                                                <th>Auth Code</th>
                                                <th>Trace No</th>
                                                <th>Txn Date</th>
                                                <th>Status</th>
                                                <th>Txn Time</th>
                                                <th>View</th>


                                            </tr>
                                        </thead>
                                        <tbody>


                                        </tbody>
                                    </table>

                                </form>
                                <table> 

                                    <tr><td style="height: 15px"><td/></tr>

                                    <tr><td><B>Color Legend For Transaction Status</B><td/></tr>

                                    <tr><td style="height: 15px"><td/></tr>
                                </table>

                                <table > 

                                    <tr><td><B>Color</B></td><td style="width: 10px"></td><td><B>Description</B></td>
                                        <td style="width: 10px">
                                        <td><B>Color</B></td><td style="width: 10px"></td><td><B>Description</B></td>
                                    <tr>

                                    <tr>
                                        <td><font style="background-color: #B3FF91;color: #B3FF91;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction  received from listener</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #E8FFDE;color: #E8FFDE;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Batch upload</font></td>
                                    <tr>
                                    <tr>
                                        <td><font style="background-color: #c8fcf8;color: #c8fcf8;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction send to chanel</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #EEF64E;color: #EEF64E;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction settled</font></td>
                                    <tr>
                                    <tr>
                                        <td><font style="background-color: #E4BDB6;color: #E4BDB6;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction received from chanel</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #9999FF;color: #9999FF;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction sent to security module</font></td>
                                    <tr>
                                    <tr>
                                        <td><font style="background-color: #FFFFAA;color: #FFFFAA;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction send listener</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #F294C8;color: #F294C8;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction received from security module</font></td>
                                    <tr>
                                    <tr>
                                        <td><font style="background-color: #FF4964;color: #FF4964;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction reversal</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #FF9999;color: #FF9999;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Pin try exceed</font></td>
                                    <tr>
                                    <tr>
                                        <td><font style="background-color: #A5FF56;color: #A5FF56;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction void</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #00FFFF;color: #00FFFF;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction dispute</font></td>
                                    <tr>
                                    <tr>
                                        <td><font style="background-color: #42E4E8;color: #42E4E8;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Settlement fail</font></td>
                                        <td style="width: 10px">
                                        <td><font style="background-color: #8849FF;color: #8849FF;">OOO</font></td><td style="width: 10px"></td><td><font style="font-size: 11px">Transaction Complete</font></td>
                                    <tr>


                                </table>

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
