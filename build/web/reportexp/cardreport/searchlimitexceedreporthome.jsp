<%-- 
    Document   : searchlimitexceedreporthome
    Created on : Mar 14, 2013, 1:01:47 PM
    Author     : asitha_l
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadSearchLimitExceedReportServlet";

            }  
            
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LIMIT_EXCEED_REPORT%>'                                  
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

                    <jsp:include page="/leftmenu.jsp"/>

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


                                <form method="POST" action="${pageContext.request.contextPath}/SearchLimitExceedReportServlet">
                                    <table border="0">


                                        <tbody>
                                            <tr>
                                                <td style="width: 150px;">Card Number </td>
                                                <td></td>
                                                <td><input type="text" maxlength="20" name="cardNum" value="${limitExceedReportBean.cardNumber}" class="deftext"/></td>
                                            </tr>

                                            <tr style="height: 5px;"></tr>

                                            <tr>
                                                <td>Card Type </td>
                                                <td></td>
                                                <td>
                                                    <select  name="cardtype" style="width: 168px;">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="cardType" items="${cardTypeList}">
                                                            <c:if test="${limitExceedReportBean.cardType==cardType.key}">
                                                                <option value="${cardType.key}" selected>${cardType.value}</option>
                                                            </c:if>
                                                            <c:if test="${limitExceedReportBean.cardType!=cardType.key}">
                                                                <option value="${cardType.key}" >${cardType.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr style="height: 5px;"></tr>

                                            <tr>
                                                <td style="width: 150px;"></td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <input type="radio" name="cashCreditStat" value="CASH" <c:if test="${limitExceedReportBean.cashOrCredit=='CASH'}">checked="true"</c:if>/> Cash
                                                    <input type="radio" name="cashCreditStat" value="CREDIT" <c:if test="${limitExceedReportBean.cashOrCredit=='CREDIT'}">checked="true"</c:if>/> Credit 
                                                </td>
                                            </tr>

                                            <tr style="height: 5px;"></tr>

                                            <tr>
                                                <td style="width: 150px;">Usage Percentage </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="5" name="usagePercentage" value="${limitExceedReportBean.usagePercentage}" class="deftext"/></td>
                                            </tr>

                                            <tr style="height: 12px;"></tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> 
                                                    <input type="submit" value="Search" name="Search" class="defbutton"/>
                                                    <input type="button" value="Reset" name="reset" class="defbutton" onclick="invokeReset()"/>
                                                </td>

                                            </tr>

                                        </tbody>
                                    </table>

                                </form>
                                <br/><br/>

                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Card Number</th>
                                                <th>Name On Card</th>
                                                <th>ID Type</th>
                                                <th>ID Number</th>
                                                <c:if test="${limitExceedReportBean.cashOrCredit=='CREDIT'}">
                                                    <th>Credit Limit</th>
                                                    <th>OTB Credit</th>
                                                </c:if>
                                                <c:if test="${limitExceedReportBean.cashOrCredit=='CASH'}">
                                                    <th>Cash Limit</th>
                                                    <th>OTB Cash</th>
                                                </c:if>
                                                <th>Usage Percentage</th>

                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="limitExceedReport" items="${limitExceedReportList}">
                                                <tr>
                                                    <td >${limitExceedReport.cardNumber}</td>
                                                    <td >${limitExceedReport.nameOnCard}</td>
                                                    <td >${limitExceedReport.idType}</td>
                                                    <td >${limitExceedReport.idNumber}</td>
                                                    <c:if test="${limitExceedReportBean.cashOrCredit=='CREDIT'}">
                                                        <td >${limitExceedReport.creditLimit}</td>
                                                        <td >${limitExceedReport.otbCredit}</td>
                                                    </c:if>
                                                    <c:if test="${limitExceedReportBean.cashOrCredit=='CASH'}">
                                                        <td >${limitExceedReport.cashLimit}</td>
                                                        <td >${limitExceedReport.otbCash}</td>
                                                    </c:if>
                                                    <td >${limitExceedReport.usagePercentage}</td>                                                
                                                </tr>
                                            </c:forEach>     
                                        </tbody>
                                    </table>

                                </form>


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