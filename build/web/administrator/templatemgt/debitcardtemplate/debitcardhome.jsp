<%-- 
    Document   : debitcardhome
    Created on : Jan 31, 2012, 2:56:03 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            


            function invokeCreate()
            {

                document.createdebitform.action="${pageContext.request.contextPath}/LoadAddDebitCardTemplateFormServlet";
                document.createdebitform.submit();

            }

                        
            function ConfirmDelete(templateCode)
            {
                answer = confirm("Do you really want to delete this debit card template?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteDebitCardTemplateServlet?templatecode="+templateCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadDebitCardTemplateMgtServlet";
                }
                
            }
                       
           

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBITCARDHOME%>'                                  
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


        <title>EPIC CMS DEBIT CARD TEMPLATE HOME</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>


                                <form method="POST" action="" name="createdebitform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                        </tr>
                                    </table>

                                    <table>

                                        <tbody>
                                            
                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td style="width: 300px"><input type="button" value="Create Template" name="createtemplate" style="width: 150px" onclick="invokeCreate()"></td>
                                                 <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DEBITCARDHOME%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                            </tr>
                                            <tr> <td style="height:20px;"></td></tr>

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>





                                <table  border="1"  class="display" id="scoreltableview2">
                                    <thead>
                                        <tr>

                                            <th>Template Code</th>
                                            <th>Template Name</th>
                                            <th>Account Template</th>
                                            <th>Card Type</th>
                                            <th>Currency Type</th>
                                            <th>Expiry Period</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="card" items="${searchList}">
                                            <tr>

                                                <td >${card.templateCode}</td>
                                                <td >${card.templateName}</td>
                                                <td >${card.accTempName}</td>
                                                <td >${card.cardTypeDes}</td>
                                                <td >${card.currencyDes}</td>
                                                <td >${card.expPeriod}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/ViewBulkCardTemplateServlet?templatecode=<c:out value="${card.templateCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateDebitCardTemplateFormServlet?templatecode=<c:out value="${card.templateCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${card.templateCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
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




