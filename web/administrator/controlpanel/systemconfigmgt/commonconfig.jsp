<%-- 
    Document   : commonconfig
    Created on : Dec 18, 2012, 11:17:57 AM
    Author     : ruwan_e
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
            
            function resetReason(){
                window.location = "${pageContext.request.contextPath}/LoadCommonConfigurationsServlet";
            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.COMMON_CONFIG%>'                                  
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

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateCommonConfigurationServlet">
                                    <table border="0" cellspacing="10" cellpadding="0">
                                        <tbody>
                                            <tr>
                                                <td>Issuer Bank</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="bank">
                                                        
                                                        <c:forEach var="bank" items="${bankList}">
                                                            <c:if test="${bank.bankCode==commonConfiguration.bank}">                                                                
                                                                <option selected="true" value="${bank.bankCode}">${bank.bankName}</option>
                                                            </c:if>
                                                            <c:if test="${bank.bankCode !=commonConfiguration.bank}">                                                                
                                                                <option value="${bank.bankCode}">${bank.bankName}</option>
                                                            </c:if> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Bank Currency</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="currency">
                                                        
                                                        <c:forEach var="currency" items="${currencyList}">
                                                            <c:if test="${currency.currencyCode==commonConfiguration.baseCurrency}">
                                                                <option selected="true"  value="${currency.currencyCode}">${currency.currencyDes}</option>
                                                            </c:if>
                                                            <c:if test="${currency.currencyCode!=commonConfiguration.baseCurrency}">
                                                                <option value="${currency.currencyCode}">${currency.currencyDes}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Bank Country</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="country">
                                                        
                                                        <c:forEach var="country" items="${countryList}">
                                                            <c:if test="${commonConfiguration.country==country.countryCode}">
                                                                <option selected="true" value="${country.countryCode}">${country.description}</option>
                                                            </c:if>
                                                            <c:if test="${commonConfiguration.country!=country.countryCode}">
                                                                <option value="${country.countryCode}">${country.description}</option>
                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>   
                                            <tr>
                                                <td>Data Capture Role</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="dataCaptureRole">
                                                        <c:forEach var="userRole" items="${userRoleList}">
                                                            <c:if test="${commonConfiguration.userRole==userRole.userRoleCode}">
                                                                <option selected="true" value="${userRole.userRoleCode}">${userRole.description}</option>
                                                            </c:if>
                                                            <c:if test="${commonConfiguration.userRole!=userRole.userRoleCode}">
                                                                <option value="${userRole.userRoleCode}">${userRole.description}</option>
                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Accumulation Point Value</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input name="accumulationPointValue" value="${commonConfiguration.accumulationPointValue}" maxlength="23"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Redemption Point Value</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input name="redemptionPointValue" value="${commonConfiguration.redemptionPointValue}" maxlength="23"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Payment Batch Timeout</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input name="batchTimeout" value="${commonConfiguration.batchTimeout}" maxlength="2"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Maximum Amount Per Payment</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input name="maxPerPaymentAmount" value="${commonConfiguration.maxPerPaymentAmount}" maxlength="23"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Maximum Amount Per Batch</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input name="maxPerBatchAmount" value="${commonConfiguration.maxPerBatchAmount}" maxlength="23"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> <input type="submit" value="Save" name="Save" style="width: 100px"/><input onclick="resetReason()" type="reset" value="Reset" style="width: 100px"/></td>
    <!--                                            <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPREJECT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>-->
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>

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
