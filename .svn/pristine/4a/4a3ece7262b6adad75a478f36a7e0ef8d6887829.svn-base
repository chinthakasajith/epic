<%-- 
    Document   : advancedsearch
    Created on : Jul 21, 2016, 4:01:58 PM
    Author     : Badrika
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
            function invokeView(val1, val2) {
                //window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+value+"&section="+val2;

                document.basicViewForm.action = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=" + val2;
                document.getElementById('id').value = val1;
                document.basicViewForm.submit();

            }

            function invokeBack()
            {
                window.location = "${pageContext.request.contextPath}/LoadCallCenterMgtServlet";
            }

            function goToSearch(val, type)
            {
//                document.basicViewForm.action = "${pageContext.request.contextPath}/LoadQuestionVerifyPageServlet";
                if (type == 'card') {

                document.basicViewForm.action = "${pageContext.request.contextPath}/LoadQuestionVerifyPageServlet";
                    document.getElementById('serachtype').value = 'card';
                    document.getElementById('searchvalue').value = val;
                    document.getElementById('idtype').value = "";
                    document.getElementById('idnumber').value = "";
                    document.basicViewForm.submit();
//                    window.location = "${pageContext.request.contextPath}/LoadQuestionVerifyPageServlet?serachtype=card&searchvalue=" + val;

                }
                if (type == 'nic') {
                    document.basicViewForm.action = "${pageContext.request.contextPath}/LoadQuestionVerifyPageServlet";
                    document.getElementById('serachtype').value = "";
                    document.getElementById('searchvalue').value = "";
                    document.getElementById('idtype').value = 'nic';
                    document.getElementById('idnumber').value = val;
                    document.basicViewForm.submit();

                }
                if (type == 'passport') {
                    document.basicViewForm.action = "${pageContext.request.contextPath}/LoadQuestionVerifyPageServlet";
                    document.getElementById('serachtype').value = "";
                    document.getElementById('searchvalue').value = "";
                    document.getElementById('idtype').value = 'passport';
                    document.getElementById('idnumber').value = val;
                    document.basicViewForm.submit();

                }
//                document.basicViewForm.submit();
            }

            $(document).ready(function()
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
            /*            label {
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
                        }*/
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

                                    <table>
                                        <tr style="height: 15px;">
                                            <td>
                                                <input type="hidden" id="serachtype" name="serachtype"  />
                                                <input type="hidden" id="searchvalue"  name="searchvalue" />
                                                <input type="hidden" id="idtype"  name="idtype" />
                                                <input type="hidden" id="idnumber"  name="idnumber" />
                                            
                                            </td>
                                        </tr>
                                    </table>

                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr >
                                                <th>Name on Card</th>
                                                <th>Card Number</th>
                                                <th>NIC</th>
                                                <th>Passport</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${advancedList}" var="viewBean">
                                                <tr>
                                                    <td>${viewBean.nameoncard}</td>                                                    
                                                    <td><a  href='#' onclick="goToSearch('${viewBean.cardnumber}', 'card')">${viewBean.cardnumber}</a></td>
                                                    <td><a  href='#' onclick="goToSearch('${viewBean.nic}', 'nic')">${viewBean.nic}</a></td>
                                                    <td><a  href='#' onclick="goToSearch('${viewBean.passport}', 'passport')">${viewBean.passport}</a></td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>



                                    <table>
                                        <tr> 
                                            <td style="width: 300px;"> 
                                                <!--  <input type="submit" value="verify" name="verify" style="width: 100px" /> -->
                                                <!-- verify kalama wenna ona mokadda??? -->
                                                <input type="button" value="Back" name="back" style="width: 100px" onclick="window.history.back()"/> 
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

