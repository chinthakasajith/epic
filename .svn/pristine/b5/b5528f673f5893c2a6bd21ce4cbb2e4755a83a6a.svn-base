<%-- 
    Document   : acquiresearchbasicview
    Created on : Nov 29, 2012, 1:39:16 PM
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
            function invokeView(val1,val2){
                //window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+value+"&section="+val2;
                
                document.basicViewForm.action="${pageContext.request.contextPath}/ViewMerchantMgtServlet?section="+val2;           
                document.getElementById('id').value=val1;    
                document.basicViewForm.submit();
                
            }
            
            function invokeViewLoc(val1,val2,val3){
                //window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+value+"&section="+val2;
                
                document.basicViewForm.action="${pageContext.request.contextPath}/ViewMerchantMgtServlet?section="+val3+"&merCusNo="+val2;           
                document.getElementById('id').value=val1;
                document.basicViewForm.submit();
                
            }
            
             function invokeViewTer(val1,val2,val3){
                //window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+value+"&section="+val2;
                
                document.basicViewForm.action="${pageContext.request.contextPath}/ViewMerchantMgtServlet?section="+val3+"&mid="+val2;           
                document.getElementById('id').value=val1;
                document.basicViewForm.submit();
                
            }
            
            function invokeBack()
            {
                window.location = "${pageContext.request.contextPath}/LoadAcquireQuestionVerifyPageServlet";
            }
            
            $(document).ready(function()
            {
                 
                  
                
                 

                var oTable =  $('#tableview').dataTable({
                    "bJQueryUI" : true,
                    "sPaginationType" :"full_numbers"
                });
                
                var oTable =  $('#tableview2').dataTable({
                    "bJQueryUI" : true,
                    "sPaginationType" :"full_numbers"
                });
                var oTable =  $('#tableview3').dataTable({
                    "bJQueryUI" : true,
                    "sPaginationType" :"full_numbers"
                });
                var oTable =  $('#tableview4').dataTable({
                    "bJQueryUI" : true,
                    "sPaginationType" :"full_numbers"
                });
                var oTable =  $('#scoreltableview').dataTable({
                    "bJQueryUI" : true,
                    "sScrollY": 600,
                    "sScrollX": "100%",
                    "sScrollXInner": "130%",
                    "bAutoWidth":false,                   
                    "bScrollCollapse": true,
                    "sPaginationType" :"full_numbers"
                });
              
                 
            } );
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
        <style type="text/css">
            label {
                cursor: default;
                visibility: hidden;
            }
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
        </style>


        <!-- ------------------------------------------------------------------- -->
    </head>
    <body>

        <div id="wrapper" >


            <div id="header">
                <img src="resources/images/topbanner.jpg" style="width: 100%">
                <div style="margin-right: 15px;">
                    <jsp:include page="/subheader.jsp"/>                    
                    <!--<script> settitle();</script>-->
                </div>
            </div><!-- #header-->
            <div id="navigator">

            </div><!-- #header-->
            <div id="middle">
               <div class="sidebar" id="sidebar">
                       <div id="issure">
                        <div class="serchform">
                           
                            
                              <jsp:include page="/administrator/controlpanel/merchantsearch.jsp"/>                      
                            
                            
                            
                        </div>
                    </div>
                  <div id="sidebar1">
                      <div style="padding-left:  10px; width: 100px; margin-top: 0px">
                            <img  style="width: 248px; height: 180px;" src="resources/images/iStock_000019973485_Medium.jpg">
                        </div>
                   
                </div><!-- .sidebar#sideLeft2 -->            
                </div><!-- .sidebar#sideLeft1 -->
                <div id="container" style="height: 800px;">
                    <div id="maincontent">

                        <table class="tit"> <tr> <td   class="center"> ACQUIRE SERVICE </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                        <!-- development area -->
                        <c:if test="${operationtype=='search'}" >
                            <center>  
                                <form method="POST" action="" name="basicViewForm">

                                    <b>Merchant Customer</b>

                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Merchant Customer No</th>
                                                <th>Merchant Name</th>
                                                <th>City</th>
                                                <th>Country</th>
                                                <th>Contact Person</th>
                                                <th>Status</th>
                                                <th>View</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${cusList}" var="cus">
                                                <tr>
                                                    <td>${cus.merchantCusNo}</td>
                                                    <td>${cus.merchantName}</td>
                                                    <td>${cus.city}</td>
                                                    <td>${cus.country}</td>
                                                    <td>${cus.conPerLastName}</td>
                                                    <td>${cus.status}</td>
                                                    <td><a  href='#' onclick="invokeView('${cus.merchantCusNo}','ACCMCU')">View</a></td> 
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>                                   

                                    <br/>
                                    <b>Merchant Location </b>
                                    <table border="1" class="display" id="tableview2">
                                        <thead>
                                            <tr>
                                                <th>Merchant ID</th>
                                                <th>Merchant Customer No</th>
                                                <th>Location</th>
                                                <th>City</th>
                                                <th>Country</th>
                                                <th>Contact Person</th>
                                                <th>Status</th>
                                                <th>View</th>




                                            </tr>
                                        </thead>
                                        <tbody>
                                             <c:forEach  items="${locList}" var="loc">
                                                <tr>
                                                    <td>${loc.merchantId}</td>
                                                    <td>${loc.merchantCusNo}</td>
                                                    <td>${loc.description}</td>
                                                    <td>${loc.city}</td>
                                                    <td>${loc.country}</td>
                                                    <td>${loc.conPerLastName}</td>
                                                    <td>${loc.status}</td>
                                                    <td><a  href='#' onclick="invokeViewLoc('${loc.merchantId}','${loc.merchantCusNo}','ACCMLO')">View</a></td> 
                                                </tr>
                                            </c:forEach>


                                        </tbody>
                                    </table>


                                    <br/>
                                    <b>Terminal</b>

                                    <table border="1" class="display" id="tableview3">
                                        <thead>
                                            <tr>
                                                <th>Terminal Id</th>
                                                <th>Terminal Name</th>
                                                <th>Serial No</th>
                                                <th>Model</th>
                                                <th>Manufacturer</th>
                                                <th>Merchant Id</th>
                                                <th>View</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                             <c:forEach  items="${terList}" var="ter">
                                                <tr>
                                                    <td>${ter.terminalId}</td>
                                                    <td>${ter.terminalName}</td>
                                                    <td>${ter.serialNo}</td>
                                                    <td>${ter.model}</td>
                                                    <td>${ter.manufacturer}</td>
                                                    <td>${ter.merchantId}</td>
                                                    <td><a  href='#' onclick="invokeViewTer('${ter.terminalId}','${ter.merchantId}','ACCTER')">View</a></td> 
                                                </tr>
                                            </c:forEach>


                                        </tbody>
                                    </table>

                                    <br/>

                                    <table>
                                        <tr> 
                                            <td style="width: 300px;"> 
                                                <!--  <input type="submit" value="verify" name="verify" style="width: 100px" /> -->
                                                <!-- verify kalama wenna ona mokadda??? -->
                                                <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeBack()" /> 
                                            </td>
                                        </tr>
                                    </table>
                                    <input type="hidden" id="id"  name="id" />

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

