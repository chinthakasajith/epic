<%-- 
    Document   : acquirequestionpoolhome
    Created on : Nov 6, 2012, 2:36:08 PM
    Author     : janaka_h
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
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

                document.verifyform.action="${pageContext.request.contextPath}/SearchMerchantMgtServlet";
                document.verifyform.submit();

            } 
            
            function invokeBack()
            {

                document.verifyform.action="${pageContext.request.contextPath}/LoadAccquirCallCenterMgtServlet";
                document.verifyform.submit();

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


    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>



        <div id="wrapper">


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
                      <div style="padding-left:  10px; width: 100px;margin-top: 0px">
                            <img  style="width: 248px; height: 180px;" src="resources/images/iStock_000019973485_Medium.jpg">
                        </div>
                   
                </div><!-- .sidebar#sideLeft2 -->            
                </div><!-- .sidebar#sideLeft1 -->
                <div id="container" style="height: 800px;">
                    <div id="maincontent">

                        <form method="post" action="" name="verifyform" >

                            <table class="tit"> <tr> <td width="200px;"></td>
                                    <td   class="center">  QUESTION VERIFY MANAGEMENT </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                            <table>
                                <tr >
                                    <td style="width: 100px;"></td>
                                    <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                    <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                </tr>
                            </table>
                            <table cellpadding="0" cellspacing="10"  >
                                <tr><td></td>
                                    <td width="200px;">Merchant CustomerID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr><td width="100px;"></td>
                                    <td width="200px;">Merchant ID &nbsp;&nbsp;&nbsp;&nbsp;:</td>
                                    <td ></td>
                                    <td></td>
                                </tr>

                                <tr><td></td>
                                    <td width="200px;">Terminal ID </td>
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
                                <tr >
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
                                                    <tr class="gradeC">
                                                        <td>${fiveAnswerBeanList.question}</td>
                                                        <td>${fiveAnswerBeanList.answer}</td>

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
                                                    <tr class="gradeC">
                                                        <td>${answerBeanList.question}</td>
                                                        <td>${answerBeanList.answer}</td>

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
                                          <td>  <input type="button" value="Reject" style="width: 100px;"/></td>
                                        <td><input type="button" value="Back" style="width: 100px;" onclick="invokeBack()"/></td>
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
