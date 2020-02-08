<%-- 
    Document   : onlinebalance
    Created on : Jan 3, 2013, 2:53:01 PM
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
            function invokeBack(){

                window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=" + "CCCARD";

            }
            
             function invokeTransactionHistory(value){

                $.post("${pageContext.request.contextPath}/ViewCardTxnHistoryServlet", {txnId:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/callcenter/card/onlinebalancecardtxnview.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
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
                    "sAjaxSource": "${pageContext.servletContext.contextPath}/LoadCardTxnDataTableServlet",
                    "fnServerData": function ( sSource, aoData, fnCallback ) {
//                        aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
//                        aoData.push( { "name": "todate", "value":$('#todate').val() } );
//                        aoData.push( { "name": "cardno", "value":$('#cardno').val() } );
//                        aoData.push( { "name": "merchantid", "value":$('#merchantid').val() } );
//                        aoData.push( { "name": "terminalid", "value":$('#terminalid').val() } );
//                        aoData.push( { "name": "bin", "value":$('#bin').val() } );
//                        aoData.push( { "name": "txntype", "value":$('#txntype').val() } );
//                        aoData.push( { "name": "txnstatus", "value":$('#txnstatus').val() } );                                  
                                    
                        $.ajax( {
                            "dataType": 'json', 
                            "type": "GET", 
                            "url": "${pageContext.servletContext.contextPath}/LoadCardTxnDataTableServlet", 
                            "data": aoData, 
                            "success": fnCallback
                        } );
                    },
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
      
            
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_ONLINE_BALANCE%>'                                  
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

                                <table>
                                    <tr style="height: 25px;">
                                        <td>Credit Limit </td>
                                        <td>:</td>
                                        <td>${cBean.creditLimit}</td>
                                    </tr>
                                    <tr style="height: 25px;">
                                        <td>Cash Limit </td>
                                        <td>:</td>
                                        <td>${cBean.cashLimit}</td>
                                    </tr>
                                    <tr style="height: 25px;">
                                        <td>OTB Credit </td>
                                        <td>:</td>
                                        <td>${cBean.otbCredit}</td>
                                    </tr>
                                    <tr style="height: 25px;">
                                        <td>OTB Cash </td>
                                        <td>:</td>
                                        <td>${cBean.otbCash}</td>
                                    </tr>
                                   
                                    <tr style="height: 25px;">
                                        <td>Card Status </td>
                                        <td>:</td>
                                        <td>${cBean.statusDes}</td>
                                    </tr>
                                    <tr style="height: 25px;">
                                        <td>Expiry Date </td>
                                        <td>:</td>
                                        <td>${cBean.expDate}</td>
                                    </tr>

                                    <tr style="height: 25px;">
                                        <td> </td>
                                        <td> </td>
                                        <td><input type="button" name="back" value="Back" onclick="invokeBack()" /></td>
                                    </tr>

                                </table>
                                    <br/><br/>

                                        <%--transaction details --%>
                                        
                                         <table  border="1"  class="display" id="jsontable">
                                        <thead>
                                            <tr>
                                                <th>Txn ID</th>
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

