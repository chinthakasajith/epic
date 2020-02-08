<%-- 
    Document   : onlinebasehome
    Created on : Apr 17, 2012, 12:28:29 PM
    Author     : jeevan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            
            function BackBankForm(){
                window.location = "${pageContext.request.contextPath}/LoadBankMgtServlet";
            }
            function resetBankForm(){
                window.location = "${pageContext.request.contextPath}/LoadOnlineBaseServlet";
            }
            
            function saveData(){
                 
                document.onlineBaseForm.action="${pageContext.request.contextPath}/SaveOnlineBaseServlet";
                document.onlineBaseForm.submit();
            }
            
            
            function viewBank(value){
                window.location = "${pageContext.request.contextPath}/ViewBankMgtServlet?id="+value;
            }
            function updateBank(value){
                document.viewTableForm.action="${pageContext.request.contextPath}/UpdateBankMgtLoadServlet";           
                document.getElementById('id').value=value;    
                document.viewTableForm.submit();
                // window.location = "${pageContext.request.contextPath}/UpdateBankMgtLoadServlet?id="+value;
            }
            function deleteBank(value){
             
                answer = confirm("Do you really want to delete "+value+" Bank?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteBankMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadBankMgtServlet";
                }

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BASE_CONFIG%>'                                  
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

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------    -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form name="onlineBaseForm" method="POST" action="${pageContext.request.contextPath}/LoadOnlineBaseServlet">
                                        <table border="0">    


                                            <tbody>
                                                <tr> 
                                                    <td style="width: 100px;">Bank Currency </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>                                
                                                        <select  name="currencyCode"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${onlineCurrencyBeanList}">

                                                                <c:if test="${baseData.currencyCode==curr.currencyNCode}">
                                                                    <option value="${curr.currencyNCode}" selected>${curr.currenceDesc}</option>
                                                                </c:if>
                                                                <c:if test="${baseData.currencyCode!=curr.currencyNCode}">
                                                                    <option value="${curr.currencyNCode}">${curr.currenceDesc}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr> 
                                                    <td style="width: 100px;">Base Country </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="country"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="countryCode" items="${onLineCountryBeanList}">

                                                                <c:if test="${baseData.countryCode==countryCode.countryCode}">
                                                                    <option value="${countryCode.countryCode}" selected>${countryCode.countryCodeDesc}</option>
                                                                </c:if>
                                                                <c:if test="${baseData.countryCode!=countryCode.countryCode}">
                                                                    <option value="${countryCode.countryCode}">${countryCode.countryCodeDesc}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr> 

                                                <tr>
                                                    <td style="width: 100px;">Terminal ID</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input name="terminal_id" value="${baseData.terminalId}" maxlength="8"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Merchant ID</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input name="merchant_id" value="${baseData.merchantId}" maxlength="15"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="button" value="Save" name="Add" class="defbutton" onclick="saveData()"/>
                                                        <input onclick="resetBankForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>





                                <!-- show table data -->






                                <!--   ------------------------- end developer area  --------------------------------  -->

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