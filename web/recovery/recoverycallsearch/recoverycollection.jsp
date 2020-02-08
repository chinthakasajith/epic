<%-- 
    Document   : recoverycollection
    Created on : Jul 16, 2013, 1:44:23 PM
    Author     : chanuka
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <jsp:include page="/content.jsp"/>

        <script language="javascript">
                 
            
            function invokeRecoverySecurityQuestions(cardNumber){

                $.post("${pageContext.request.contextPath}/LoadRecoverySecurityServlet", {cardNumber:cardNumber},
                
                function(data) {
                    
                    if(data == "success"){
                       
                        $.colorbox({href:"${pageContext.request.contextPath}/recovery/recoverycallsearch/recoverycalltocustomer.jsp"
                            ,closeButton:false,open:true,escKey:false,iframe:true,height:"80%", width:"80%",overlayClose:false}); 
                   
//                        $(".callbacks").colorbox({href:"${pageContext.request.contextPath}/recovery/recoverycallsearch/recoverycalltocustomer.jsp"
//                            ,closeButton:false,open:true,escKey:false,iframe:true,height:"80%", width:"80%",overlayClose:false}); 
                            $.colorbox({href:"${pageContext.request.contextPath}/recovery/recoverycallsearch/recoverycalltocustomer.jsp"
                            ,closeButton:false,open:true,escKey:false,iframe:true,height:"80%", width:"80%",overlayClose:false});
                                        
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
            var oTable2;
            $(document).ready(function() {
                //load table
                oTable = $('#jsontable').dataTable({
                    "bServerSide": true,
                    "sAjaxSource": "${pageContext.servletContext.contextPath}/LoadRecoveryTransactionDataServlet",
                    "fnServerData": function ( sSource, aoData, fnCallback ) {
                        aoData.push( { "name": "cardnumber", "value":$('#cardnumber').val() } );
                        //                        aoData.push( { "name": "todate", "value":$('#todate').val() } );                                
                                    
                        $.ajax( {
                            "dataType": 'json', 
                            "type": "GET", 
                            "url": "${pageContext.servletContext.contextPath}/LoadRecoveryTransactionDataServlet", 
                            "data": aoData, 
                            "success": fnCallback
                        } );
                    },
                    "bProcessing": true,
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "aoColumns": [
                        { "mDataProp": "cardNumber","bVisible": true },
                        { "mDataProp": "mId","bVisible": true },
                        { "mDataProp": "txnTypeDes","bVisible": true },
                        { "mDataProp": "txnAmount","bVisible": true },
                        { "mDataProp": "txnCurrencyDes","bVisible": true },
                        { "mDataProp": "txnDate","bVisible": true },
                        { "mDataProp": "txnStatusDes","bVisible": false }
                        //                        ,{ "mDataProp": "view","bSortable": false}
                    ]});
                    


                oTable2 = $('#jsontable2').dataTable({
                    "bServerSide": true,
                    "sAjaxSource": "${pageContext.servletContext.contextPath}/LoadRecoveryPaymentDataServlet",
                    "fnServerData": function ( sSource, aoData, fnCallback ) {
                        aoData.push( { "name": "cardnumber", "value":$('#cardnumber').val() } );
                        //                        aoData.push( { "name": "todate", "value":$('#todate').val() } );                                
                                    
                        $.ajax( {
                            "dataType": 'json', 
                            "type": "GET", 
                            "url": "${pageContext.servletContext.contextPath}/LoadRecoveryPaymentDataServlet", 
                            "data": aoData, 
                            "success": fnCallback
                        } );
                    },
                    "bProcessing": true,
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "aoColumns": [
                        { "mDataProp": "cardNumber","bVisible": true },
                        { "mDataProp": "paymentTypeDes","bVisible": true },
                        { "mDataProp": "currencyTypeDes","bVisible": true },
                        { "mDataProp": "amount","bVisible": true },
                        { "mDataProp": "paymentDate","bVisible": true },
                        { "mDataProp": "paymentStatusDes","bVisible": true }
                        //                        ,{ "mDataProp": "view","bSortable": false}
                    ]});  
                    
                    
            });
    
    
    
    
    

        </script>
        <script>
            function settitle(){
                   
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.RECOVERY_CALL_CENTER%>'                                  
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


        <title>EPIC CMS SEARCH ASSIGNED CARD APPLICATIONS</title>
    </head>
    <body>



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height:500px">

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchuserassignform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                        </tr>
                                    </table>

                                    <table  cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr>
                                                <td width="200px">Account Number</td>
                                                <td width="200px"> ${recoveryDetailsBean.accNumber}</td>
                                                <td></td>
                                                <td width="200px">Contact Number</td>
                                                <td width="200px">${recoveryDetailsBean.contactNumber}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td width="200px">Card Number</td>
                                                <td width="200px">${recoveryDetailsBean.cardNumber}</td>
                                                <td><input type="hidden" value="${recoveryDetailsBean.cardNumber}" name="cardnumber" id="cardnumber"></td>
                                                <td width="200px">Address</td>
                                                <td width="200px">${recoveryDetailsBean.address}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td width="200px">Card Holder Name</td>
                                                <td width="200px">${recoveryDetailsBean.cardHolderName}</td>
                                                <td></td>
                                                <td width="200px">Mobile</td>
                                                <td width="200px">${recoveryDetailsBean.mobileNo}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td width="200px">Card Type</td>
                                                <td width="200px">${recoveryDetailsBean.cardTypeDes}</td>
                                                <td></td>
                                                <td width="200px">ID</td>
                                                <td width="200px">${recoveryDetailsBean.id}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td width="200px">Card Limit</td>
                                                <td width="200px">${recoveryDetailsBean.cardLimit}</td>
                                                <td></td>
                                                <td width="200px">Card Status</td>
                                                <td width="200px">${recoveryDetailsBean.cardStatusDes}</td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>

                                    <table>
                                        <tr>
                                            <td style="width: 300px;">
                                                
                                                <input style="width: 100px;" type="button" class="callbacks" value="Call" onclick="invokeRecoverySecurityQuestions('${recoveryDetailsBean.cardNumber}')"></input>   

                                            </td>
                                        </tr>
                                    </table>
                                </form>

                                <table cellspacing="10"><tr></tr>
                                    <tr><td><lable><b>Transaction Details</b></lable></td></tr>
                                    <tr style="height: 10px"></tr>
                                </table>


                                <table  border="1"  class="display" id="jsontable">
                                    <thead>
                                        <tr>

                                            <th>Card Number</th>
                                            <th>Merchant ID</th>
                                            <th>Transaction Type</th>
                                            <th>Transaction Amount</th>
                                            <th>Transaction Currency</th>
                                            <th>Transaction Date</th>
                                            <th>Transaction Status</th>


                                        </tr>
                                    </thead>
                                    <tbody>


                                    </tbody>
                                </table>

                                <table cellspacing="10"><tr></tr>
                                    <tr><td><lable><b>Payments</b></lable></td></tr>
                                    <tr style="height: 10px"></tr>
                                </table>

                                <table  border="1"  class="display" id="jsontable2">
                                    <thead>
                                        <tr>
                                            <th>Card Number</th>
                                            <th>Payment Type</th>
                                            <th>Currency Type</th>
                                            <th>Amount</th>
                                            <th>Payment Date</th>
                                            <th>Payment Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>


                                <table cellspacing="10"><tr></tr>
                                    <tr><td><lable><b>Statements</b></lable></td></tr>
                                    <tr style="height: 10px"></tr>
                                </table>



                                <table border="1"  class="display" id="tableview3">
                                    <thead>
                                        <tr>
                                            <th>Account Number</th>
                                            <th>Card Number</th>
                                            <th>Status</th>
                                            <th>Case Type</th>
                                            <th>Assign Status</th>
                                            <th>Collect</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="recovery" items="${searchList}">
                                            <tr>
                                                <td >${recovery.accNumber}</td>
                                                <td >${recovery.cardNumber}</td>
                                                <td >${recovery.statusDes}</td>
                                                <td >${recovery.caseDes}</td>
                                                <td >${recovery.assignStatusDes}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadRecoveryCollectionServlet?accNumber=${recovery.accNumber}'>Collect</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                          

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
