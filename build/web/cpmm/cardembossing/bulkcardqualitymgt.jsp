<%-- 
    Document   : bulkcardqualitymgt
    Created on : Sep 26, 2012, 9:56:11 AM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
          
           
        </script>  
        <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );
                
            
                
                
                function invokeReset(){

                    window.location = "${pageContext.request.contextPath}/LoadBulkCardQualityMgtServlet";

                }
            
                function invokeSearch()
                {
              
                    document.searchQualityManagementForm.action="${pageContext.request.contextPath}/SearchBulkCardQualityMgtServlet";
                    document.searchQualityManagementForm.submit();

                }
            
                function invokeQualityFail()
                {
                    answer = confirm("Quality fail confirmed?")
                    
                    if (answer !=0)
                    {
                        document.updateQualityManagementForm.action="${pageContext.request.contextPath}/UpdateBulkCardQualityMgtServlet";
                        document.updateQualityManagementForm.submit();

                    }
                    else
                    {
                        window.location="${pageContext.request.contextPath}/LoadBulkCardQualityMgtServlet";
                    }

                   
                }
                
                
            
            
                

        </script>
     <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULKCARD_QUALITY_MGT%>'                                  
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
                                <!--this will shown when the user given value is invalid  -->

                                <c:if test="${operationType=='SEARCH'}" > 

                                    <form action="" method="POST" name="searchQualityManagementForm">

                                        <table border="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 200px;">Card Number</td>
                                                    <td><input type="text" name="cardNumber" value="${cardBean.cardNumber}" maxlength="100"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><input type="text" name="cardType" disabled value="${cardBean.cardType}" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td><input type="text" name="cardProduct" disabled value="${cardBean.cardProduct}" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td><input type="text" name="cardDomain" disabled value="${cardBean.cardDomain}" maxlength="100"/></td>

                                                </tr>

                                                <tr>
                                                    <td>Card Expiry Date</td>
                                                    <td><input type="text" name="cardExpDate" disabled value="${cardBean.cardExpDate}" maxlength="100"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Remarks</td>
                                                    <td><input type="text" name="remarks" disabled value="${cardBean.remarks}" maxlength="100"/></td>
                                                </tr>



                                            </tbody>
                                        </table>
                                        <table border="0" cellspacing="10">
                                            <tbody>
                                                <tr>  
                                                    <td></td>
                                                    <td>
                                                        <input type="button" value="Search" name="search" style=" width:100px" onclick="invokeSearch()"/>

                                                    </td>
                                                    <td>
                                                        <input type="button" value="Quality Fail" disabled name="qualityFail" style=" width:100px" onclick="invokeQualityFail()"/>
                                                    </td>
                                                    <td>
                                                        <input type="button" value="Reset" name="reset" style=" width:100px" onclick="invokeReset()"/>

                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <c:if test="${operationType=='UPDATE'}" >

                                    <form action="" method="POST" name="updateQualityManagementForm">

                                        <table border="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 200px;">Card Number</td>
                                                    <td><input type="text" readonly name="cardNumber" value="${cardBean.cardNumber}" maxlength="100"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><input type="text" name="cardType" readonly   value="${cardBean.cardType}" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td><input type="text" name="cardProduct" readonly  value="${cardBean.cardProduct}" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td><input type="text" name="cardDomain" readonly  value="${cardBean.cardDomain}" maxlength="100"/></td>

                                                </tr>

                                                <tr>
                                                    <td>Card Expiry Date</td>
                                                    <td><input type="text" name="cardExpDate" readonly  value="${cardBean.cardExpDate}" maxlength="100"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Remarks</td>
                                                    <td><input type="text" name="remarks" value="${cardBean.remarks}" maxlength="100"/></td>
                                                </tr>



                                            </tbody>
                                        </table>
                                        <table border="0" cellspacing="10">
                                            <tbody>
                                                <tr>  
                                                    <td></td>
                                                    <td>
                                                        <input type="button" value="Search" name="search" style=" width:100px" onclick="invokeSearch()"/>

                                                    </td>
                                                    <td>
                                                        <input type="button" value="Quality Fail" name="qualityFail" style=" width:100px" onclick="invokeQualityFail()"/>
                                                    </td>
                                                    <td>
                                                        <input type="button" value="Reset" name="reset" style=" width:100px" onclick="invokeReset()"/>

                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>







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
