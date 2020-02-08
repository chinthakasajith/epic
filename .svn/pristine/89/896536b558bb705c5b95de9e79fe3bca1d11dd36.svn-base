<%-- 
    Document   : carddomainhome
    Created on : Jan 24, 2012, 12:23:53 PM
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

                document.searchcarddomainform.action="${pageContext.request.contextPath}/LoadAddCardDomainFormServlet";
                document.searchcarddomainform.submit();

            }


                        
            function ConfirmDelete(templateCode)
            {
                answer = confirm("Do you really want to delete this card domain template?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteCardDomainServlet?templatecode="+templateCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCardDomainTempMgtServlet";
                }
               
            }
                       
           

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDDOMAINHOME%>'                                  
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


        <title>EPIC CMS CARD DOMAIN HOME</title>
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


                                <form method="POST" action="" name="searchcarddomainform">

                                    <table>
                                        <tr>
                                            <td><b><font color="Red"> ${errorMsg}</font> </b></td>
                                            <td><b><font color="green"> ${successMsg}</font> </b></td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table>

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td style="width: 300px"><input type="button" value="Create Template" name="createtemplate" style="width: 150px" onclick="invokeCreate()"></td>
                                                  <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDDOMAINHOME%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                            </tr>
                                            <tr> <td style="height:20px;"></td></tr>

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>





                                <table  border="1"  class="display" id="scoreltableview3">
                                    <thead>
                                        <tr>

                                            <th>Template Code</th>
                                            <th>Template Name</th>
                                            <th>Customer Template Name</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="carddomain" items="${searchList}">
                                            <tr>

                                                <td >${carddomain.templateCode}</td>
                                                <td >${carddomain.templateName}</td>
                                                <td >${carddomain.custemplateDes}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/ViewCardDomainServlet?templatecode=<c:out value="${carddomain.templateCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateCardDomainFormServlet?templatecode=<c:out value="${carddomain.templateCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${carddomain.templateCode}')">Delete</a></td>


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


