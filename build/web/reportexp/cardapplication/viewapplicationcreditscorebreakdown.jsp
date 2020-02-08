<%-- 
    Document   : viewapplicationcreditscorebreakdown
    Created on : Jan 10, 2013, 12:21:28 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script > 
             
            function invokeBack(){
                
                //window.location="${pageContext.request.contextPath}/LoadApplicationCreditScoreBreakDownServlet";
                window.location="${pageContext.request.contextPath}/SearchApplicationCreditScoreBreakDownServlet?id=1";
            }
            
        </script>
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPLICATION_CREDIT_SCORE_BREAKDOWN_RPT%>'                                  
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

        <title>CMS MERCHANT MANAGEMENT</title>
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
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                <br/>

                                <table><tr><td>Application Id : ${applicationID}</td></tr></table>
<!--                                <form method="POST" name="applicationIdForm">
                                    <input type="hidden" name="applicationID" value="${applicationID}"/>
                                </form>-->

                                <br/>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">

                                            <th>Credit Score Rule</th>
                                            <th>Credit Score</th>
                                            <th>Credit Score User</th>
                                            <th>Credit Score Date</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="credit" items="${creditScoreList}">
                                            <tr>
                                                <td >${credit.ruleDes}</td>
                                                <td >${credit.totalCreditScore}</td>
                                                <td >${credit.creditScoreUser}</td>
                                                <td >${credit.creditScoredDate}</td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table> 

                                <br/>

                                <table>
                                    <tr>
                                        <td>
<!--                                            <input type="button" name="generate" value="Generate" onclick="invokeGenerateReport()" style="width: 100px;"/>&nbsp;-->
                                            <input type="button" onclick="invokeBack()" name="back" value="Back" style="width: 100px;"/>&nbsp; 
                                        </td>
                                    </tr>
                                </table>

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