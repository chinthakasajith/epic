<%-- 
    Document   : questionpoolhome
    Created on : May 21, 2012, 10:57:51 AM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
            function invokeVerify()
            {

//                answer = confirm("Do you really want to verify the caller ? ")
//                if(answer != 0){

                document.verifyform.action = "${pageContext.request.contextPath}/SearchCallCenterMgtServlet";
                document.verifyform.submit();

//                }else {
//                    
//                }

            }

            function invokeBack()
            {

                document.verifyform.action = "${pageContext.request.contextPath}/LoadCallCenterMgtServlet";
                document.verifyform.submit();

            }

            function invokeReject()
            {
                answer = confirm("Do you really want to reject the caller ? ")
                if (answer != 0) {
                    document.verifyform.action = "${pageContext.request.contextPath}/LoadCallCenterMgtServlet?param=reject";
                    document.verifyform.submit();
                } else {

                }


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


    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>



        <div id="wrapper">

            <div id="header" >

                <img src="${pageContext.request.contextPath}/resources/images/topbanner.jpg" style="width: 100%">
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
                        <div style="padding-left:  10px; width: 100px; margin-top: 0px">
                            <!--<img  style="width: 248px; height: 180px;" src="resources/images/iStock_000019973485_Medium.jpg">-->
                        </div>

                    </div><!-- .sidebar#sideLeft2 -->            
                </div><!-- .sidebar#sideLeft1 -->
                <div id="container" style="height: 800px;">
                    <div id="maincontent">

                        <form method="post" action="" name="verifyform" >

                            <table class="tit"> <tr> <td width="200px;"></td>
<!--                                    <td   class="center">  QUESTION VERIFY MANAGEMENT </td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
                            <table>
                                <tr >
                                    <td style="width: 100px;"></td>
                                    <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                    <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                </tr>
                            </table>
                            <table cellpadding="0" cellspacing="10"  >
                                <tr><td width="100px;"></td>
                                    <!--                                    <td style="color: #3F2C1C;width: 200px">Card Number &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
                                                                        <td style="color: #3F2C1C">${cardNumber}</td>
                                                                        <td></td>-->
                                </tr>
                                <!--                                <tr><td width="100px;"></td>
                                                                    <td width="200px;">Application ID &nbsp;&nbsp;&nbsp;&nbsp;:</td>
                                                                    <td >${applicationId}</td>
                                                                    <td></td>
                                                                </tr>
                                
                                                                <tr><td></td>
                                                                    <td width="200px;">Card Category </td>
                                                                    <td></td>
                                                                    <td></td>
                                                                </tr>-->
                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td colspan="3"> <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview2">
                                            <thead >
                                                <tr  class="gradeB">
                                                    <th  scope="col">Question</th>
                                                    <th  scope="col">Answer</th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="fiveAnswerBeanList" items="${fiveAnswerBeanList}">
                                                    <tr class="gradeB">
                                                        <td style="color: #3F2C1C">${fiveAnswerBeanList.question}</td>
                                                        <td style="color: #3F2C1C">${fiveAnswerBeanList.answer}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </td>

                                </tr>
                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>

                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr><td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr><td></td>
                                    <td colspan="3"> <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview">
                                            <thead >
                                                <tr  class="gradeB">
                                                    <th  scope="col">Advanced Question</th>
                                                    <th  scope="col">Answer</th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="answerBeanList" items="${answerBeanList}">
                                                    <tr class="gradeB">
                                                        <td style="color: #3F2C1C">${answerBeanList.question}</td>
                                                        <td style="color: #3F2C1C">${answerBeanList.answer}</td>

                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>



                            </table>
                            <table>
                                <tr><td style="width: 110px;"></td>
                                    <td><input type="button" value="Verify" style="width: 100px;" onclick="invokeVerify()"/></td>
                                    <td>  <input type="button" value="Reject" style="width: 100px;" onclick="invokeReject()"/></td>
                                    <td><input type="button" value="Back" style="width: 100px;" onclick="window.history.back()"/></td>
                                </tr>

                            </table>        
                        </form>      

                    </div>

                </div><!-- #container-->


            </div><!-- #middle-->
            <div id="footer">

            </div><!-- #footer -->
        </div><!-- #wrapper -->
    </body>
</html>
