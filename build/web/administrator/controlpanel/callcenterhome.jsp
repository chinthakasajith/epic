<%-- 
    Document   : callcenterhome
    Created on : Jul 17, 2012, 10:11:49 AM
    Author     : janaka_h
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <title>Call Center Home</title>
        <!-- ------------------------------------------------------------------- -->

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/callcenter/!style.css"/>


        <script>


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


        <!-- ------------------------------------------------------------------- -->
    </head>
    <body>

        <div id="wrapper">


            <div id="header">
                <img src="resources/images/topbanner.jpg" style="width: 100%">
                <div style="">
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
                           
                            
                              <jsp:include page="customersearch.jsp"/>                         
                            
                            
                            
                        </div>
                    </div>
                  <div id="sidebar1">
                   
                      <div style="padding-left:  10px; width: 100px; margin-top: 20px">
                            <!--<img  style="width: 248px; height: 180px;" src="resources/images/iStock_000019973485_Medium.jpg">-->
                        </div>
                </div><!-- .sidebar#sideLeft2 -->            
                </div><!-- .sidebar#sideLeft1 -->
                
                <div id="container" >
                    <div id="maincontent">
                        
                        
                        <div style="padding-left:  10px; padding-top: 10px; width: 1000px;">
                            <img  style="width: 1040px; height: 248px;" src="resources/images/BannersCallCenterSectionEn1.jpg">
                        </div>
                        

                        <!-- development area -->
                        
                    </div>

                </div><!-- #container-->
                

            </div><!-- #middle-->
            <div id="footer">

            </div><!-- #footer -->
        </div><!-- #wrapper -->



    </body>
</html>
