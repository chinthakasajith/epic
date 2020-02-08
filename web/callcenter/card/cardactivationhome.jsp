<%-- 
    Document   : cardactivationhome
    Created on : Jul 24, 2012, 12:22:46 PM
    Author     : nalin
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />



        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";
        </style>

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>

        <title>EPIC_CMS_HOME</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <script>
            
            function invokeActivate()
            {

                document.cardActivationForm.action="${pageContext.request.contextPath}/ProceedCardActivationServlet";
                document.cardActivationForm.submit();

            }
            
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=" + "CCCARD";
            }
        </script>
         <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_ACTIVATION%>'                                  
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
        <!-- ------------------------------------------------------------------- -->
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!-- --------------------Start Developer Area  -------------------------------------------- -->

                                 <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>




                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <c:if test="${operationtype=='LOAD'}">
                                    <form action="" method="POST" name="cardActivationForm" >

                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>

                                                <tr>
                                                    <td>Card Number</td>
                                                    <td>: ${cardBean.cardNumber}<input type="hidden" name="cardNumber" class="inputfield-mandatory" maxlength="100" value='${cardBean.cardNumber}'>
                                                   
                                                    <input type="hidden" name="cardStatus"  class="inputfield-mandatory" maxlength="3" value='${cardBean.cardStatus}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td>: ${cardBean.cardTypeDes}<input  type="hidden" name="cardTypeDes" class="inputfield-mandatory" maxlength="100" value='${cardBean.cardTypeDes}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Category</td>
                                                    <td>: ${cardBean.cardCatDes}<input type="hidden" name="cardCatDes" hidden class="inputfield-mandatory" maxlength="100" value='${cardBean.cardCatDes}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Expiry Date</td>
                                                    <td>: ${cardBean.expDate}<input type="hidden" name="expDate" hidden class="inputfield-mandatory" maxlength="100" value='${cardBean.expDate}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Name of Card</td>
                                                    <td>: ${cardBean.nameofCard}<input type="hidden" name="nameofCard" hidden class="inputfield-mandatory" maxlength="100" value='${cardBean.nameofCard}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Credit Limit</td>
                                                    <td>: ${cardBean.creditLimit}<input type="hidden" name="creditLimit" hidden class="inputfield-mandatory" maxlength="100" value='${cardBean.creditLimit}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Remark</td>
                                                    <td><font style="color: white;">&nbsp;</font>&nbsp;<textarea   name="remark" class="inputfield-mandatory" maxlength="100" value='${cardBean.remark}'></textarea></td>
                                                </tr>



                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="activate" value="Activate" onclick="invokeActivate()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Cancel" onclick="invokeCancel()"/>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>   

                                <!-- --------------------End Developer Area  -------------------------------------------- -->
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