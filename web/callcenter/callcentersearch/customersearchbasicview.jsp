<%-- 
    Document   : customersearchbasicview
    Created on : Aug 6, 2012, 2:04:18 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Call Center Home</title>
        <!-- ------------------------------------------------------------------- -->

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/callcenter/!style.css"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-ui-1.8.2.custom.min.js"></script>

        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";


        </style>

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/KeyTable.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>


        <script>
            function invokeView(val1, val2, cardCategoryCode) {
                //window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+value+"&section="+val2;
               
                document.basicViewForm.action = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=" + val2;
                document.getElementById('id').value = val1;
                document.getElementById('cardCategoryCode').value = cardCategoryCode;
                
                document.basicViewForm.submit();

            }
            
//            function invokeView2(val1, cardCategoryCode, billingDate) {
//                //window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+value+"&section="+val2;
//                document.basicViewForm.action = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=" + val2;
//                document.getElementById('id').value = val1;
//                document.getElementById('billingDate').value = billingDate;
//                document.getElementById('cardCategoryCode').value = cardCategoryCode;
//                
//                document.basicViewForm.submit();
//
//            }

            function invokeBack()
            {
                window.location = "${pageContext.request.contextPath}/LoadCallCenterMgtServlet";
            }

            $(document).ready(function ()
            {





                var oTable = $('#tableview').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });

                var oTable = $('#tableview2').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#tableview3').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#tableview4').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#scoreltableview').dataTable({
                    "bJQueryUI": true,
                    "sScrollY": 600,
                    "sScrollX": "100%",
                    "sScrollXInner": "130%",
                    "bAutoWidth": false,
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });


            });
        </script>
        <script>

            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CALLCENTERSEARCH%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }
        </script>
        <style type="text/css">
/*            label {
                cursor: default;
                visibility: hidden;
            }*/
            .dataTables_info {
                width: 50%;
                float: left;
                visibility: hidden;
            }
            form {
                padding: 2px;
            }
            .dataTables_wrapper .ui-toolbar {
                visibility: hidden;
            }
             input[type=button]:hover{
                background-color: #7F6C5D;
                color: #ffffff;
                border: 1px solid #ffffff;

            }
            input[type=button]{
                background-color: #C8BDAE;
                border: 1px solid #7F6C5D;
                border-radius: 10px;

            }
        </style>
        


        <!-- ------------------------------------------------------------------- -->
    </head>
    <body>

        <div id="wrapper" >


            <div id="header">
                <img src="resources/images/topbanner.jpg" style="width: 100%">
                <div style="margin-right: 15px;">
                    <jsp:include page="/subheader.jsp"/>                    
                    <script> settitle();</script>
                </div>
            </div><!-- #header-->
            <div id="navigator">

            </div><!-- #header-->
            <div id="middle">
                <div class="sidebar" id="sidebar">
                    <div id="issure">
                        <div class="serchform">


                            <jsp:include page="/administrator/controlpanel/customersearch.jsp"/>                         



                        </div>
                    </div>
                    <div id="sidebar1">

                        <div style="padding-left:  10px; width: 100px;margin-top: 0px">
                            <!--<img  style="width: 248px; height: 180px;" src="resources/images/iStock_000019973485_Medium.jpg">-->
                        </div>
                    </div><!-- .sidebar#sideLeft2 -->            
                </div><!-- .sidebar#sideLeft1 -->
                <div id="container" style="height: 800px;">
                    <div id="maincontent">

                        <!--<table class="tit"> <tr> <td   class="center"> CALL CENTER SEARCH </td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
                        <table>
                            <tr>
                                <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                    <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                    </td>
                                </tr>
                            </table>

                            <!-- development area -->
                        <c:if test="${operationtype=='search'}" >
                            <center>  
                                <form method="POST" action="" name="basicViewForm">

                                    <table style="background: linear-gradient(to right, #DBD1C7, #FFFFFF);">
                                        <c:forEach  items="${cusList}" var="viewBean">
                                            <tr>
                                                <td style="color: #3F2C1C">ID Number:</td>
                                                <td style="color: #3F2C1C">${viewBean.idNumberCC}</td>
                                            </tr>
                                            <tr>
                                                <td style="color: #3F2C1C">Name:</td>
                                                <td style="color: #3F2C1C">${viewBean.titlepd} ${viewBean.customerName}</td>
                                            </tr>
                                            <tr>

                                                <td style="color: #3F2C1C">Address:</td>
                                                <td style="color: #3F2C1C">${viewBean.addressCC}</td>

                                            </tr>
                                            <tr>
                                                <td style="color: #3F2C1C">Status:</td>
                                                <td style="color: #3F2C1C">${viewBean.customerStatusCC}</td>
                                                <td><a  href='#' onclick="invokeView('${viewBean.custmerIdCC}', 'CCCUST', '${viewBean.cardCatCode}')">More...</a></td>
                                            </tr>
                                        </c:forEach>
                                    </table>


                                    <!--<b>Card</b>-->

                                    <table border="1" class="display" id="tableview4">
                                        <thead>
                                            <tr>
                                                <th>Card Number</th>
                                                <!--<th>Card Domain</th>-->
                                                <!--<th>Card Type</th>-->
                                                <th>Product</th>
                                                <th>Category</th>
                                                <th>Expiry</th>
                                                <!--                                                <th>Expiry Date </th>
                                                                                                <th>Activation Date</th>-->

                                                <th>Credit Limit</th>
                                                <th>OTB Credit</th>
                                                <th>Due Date</th>
                                                <th>Status</th>
                                                <!--                                                <th>OTB Credit</th>
                                                                                                <th>OTB Cash</th>-->
                                                <th>View</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${cardList}" var="viewBean">
                                                <tr>
                                                    <td>${viewBean.cardNumber}</td>
                                                    <!--<td>${viewBean.cardDomain}</td>-->
                                                    <!--<td>${viewBean.cardType}</td>-->
                                                    <td>${viewBean.cardProduct}</td>
                                                    <td>${viewBean.cardCatCodeDes}</td>
                                                    <td>${viewBean.expDate}</td>
                                                    <!--<td>${viewBean.activDate}</td>-->

                                                    <td>${viewBean.creditLimitC}</td>
                                                    <td>${viewBean.otbCredit}</td>
                                                    <td>${viewBean.dueDate}</td>
                                                    <td>${viewBean.cardStatus}</td>
<!--                                                    <td>${viewBean.otbCredit}</td>
                                                    <td>${viewBean.otbCash}</td>-->
                                                    <td><a  href='#' onclick="invokeView('${viewBean.cardNumber}', 'CCCARD')">View</a></td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>


                                    <!--<b>Account</b>-->

                                    <table border="1" class="display" id="tableview3">
                                        <thead>
                                            <tr>
                                                <th>Account Number</th>
                                                <!--                                                <th>Customer Id</th>
                                                                                                <th>Billing Id</th>-->
                                                <th>Credit Limit</th>
                                                <!--<th>Cash Limit</th>-->
                                                <th>Min Payment Amt</th>
                                                <th>Available Balance</th>
                                                
                                                <th>Last Statement Date</th>
                                                <th>Last Payment Date</th>
                                                <th>Last Payment</th>


                                                <!--                                                <th>Currency Code</th>
                                                                                                <th>Risk Profile Code</th>-->
                                                <th>Status</th>
                                                <th>View</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${accList}" var="viewBean">
                                                <tr>
                                                    <td>${viewBean.accountNoCA}</td>
<!--                                                    <td>${viewBean.custmerIdCA}</td>
                                                    <td>${viewBean.billingId}</td>-->
                                                    <td>${viewBean.creditLimitCA}</td>
                                                    <!--<td>${viewBean.cashLimitCA}</td>-->

                                                    <td>Min Payment Amt</td>
                                                    <td>${viewBean.otbcreditonac}</td>
                                                    
                                                    <td>Last Statement Date</td>
                                                    <td>Last Payment Date</td>
                                                    <td>Last Payment</td>



<!--                                                    <td>${viewBean.currencyCodeCA}</td>
<td>${viewBean.riskProfileCodeCA}</td>-->
                                                    <td>${viewBean.status}</td>
                                                    <td><a  href='#' onclick="invokeView('${viewBean.accountNoCA}', 'CCACCO')">View</a></td>
                                                </tr>
                                            </c:forEach>


                                        </tbody>
                                    </table>

                                    <!--<b>Application</b>-->

                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Application Id</th>
                                                <!--                                                <th>Identification Type</th>
                                                                                                <th>Identification Number</th>-->
                                                <!--                                                <th>Initials</th>-->
                                                <!--                                                <th>Last Name</th>
                                                                                                <th>Confirmed Credit Limit</th>
                                                                                                <th>Confirmed Card Product</th>-->
                                                <!--                                                <th>Staff Status</th>
                                                                                                <th>Mobile Number</th>
                                                                                                <th>City</th>-->
                                                <th>Staff Status</th>
                                                <th>Status</th>
                                                <th>View</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${appList}" var="viewBean">
                                                <tr>
                                                    <td>${viewBean.appId}</td>
<!--                                                    <td>${viewBean.idTypeCAP}</td>
                                                    <td>${viewBean.idNumberCAP}</td>-->
<!--                                                    <td>${viewBean.initials}</td>-->
<!--                                                    <td>${viewBean.lastName}</td>
                                                    <td>${viewBean.confCreditLimit}</td>
                                                    <td>${viewBean.confCardProduct}</td>-->
<!--                                                    <td>${viewBean.staffStatusCAP}</td>
                                                    <td>${viewBean.mobileNo}</td>
                                                    <td>${viewBean.city}</td>-->
                                                    <td>${viewBean.staffStatusCAP}</td>
                                                    <td>${viewBean.statusCAP}</td>
                                                    <td><a  href='#' onclick="invokeView('${viewBean.appId}', 'CCAPPL')">View</a></td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>    
                                    
                                    <!--                                    <b>Customer </b>
                                                                        <table border="1" class="display" id="tableview2">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Customer ID</th>
                                                                                    <th>Customer Name</th>
                                                                                    <th>Identification Type</th>
                                                                                    <th>Identification Number</th>
                                                                                                                                    <th>Credit Limit</th>
                                                                                                                                    <th>Cash Limit</th>
                                                                                                                                    <th>Currency Code </th>
                                                                                                                                    <th>Staff Status</th>
                                                                                    <th>Customer Status</th>
                                                                                                                                    <th>Risk Profile Code</th>
                                                                                    <th>View</th>
                                    
                                    
                                    
                                    
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                    <c:forEach  items="${cusList}" var="viewBean">
                                        <tr>
                                            <td>${viewBean.custmerIdCC}</td>
                                            <td>${viewBean.customerName}</td>
                                            <td>${viewBean.idTypeCC}</td>
                                            <td>${viewBean.idNumberCC}</td>
                                            <td>${viewBean.creditLimitCC}</td>
                                            <td>${viewBean.cashLimitCC}</td>
                                            <td>${viewBean.currencyCodeCC}</td>
                                            <td>${viewBean.staffStatusCC}</td>
                                            <td>${viewBean.customerStatusCC}</td>
                                            <td>${viewBean.riskProfileCodeCC}</td>
                                            <td><a  href='#' onclick="invokeView('${viewBean.custmerIdCC}','CCCUST')">View</a></td>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>-->






                                    <table>
                                        <tr> 
                                            <td style="width: 300px;"> 
                                                <!--  <input type="submit" value="verify" name="verify" style="width: 100px" /> -->
                                                <!-- verify kalama wenna ona mokadda??? -->
                                                <input type="button" value="Back" name="back" style="width: 100px" onclick="window.history.back()" /> 
                                            </td>
                                        </tr>
                                    </table>
                                    <input type="hidden" id="id"  name="id" />
                                    <input type="hidden" id="cardCategoryCode"  name="cardCategoryCode" />
                                    <input type="hidden" id="billingDate"  name="billingDate" />
                                </form> 
                            </center>
                        </c:if>

                    </div>

                </div><!-- #container-->


            </div><!-- #middle-->
            <div id="footer">

            </div><!-- #footer -->
        </div><!-- #wrapper -->



    </body>
</html>
