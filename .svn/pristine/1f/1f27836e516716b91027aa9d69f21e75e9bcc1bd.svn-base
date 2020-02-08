<%-- 
    Document   : channelkeymailinghome
    Created on : Feb 14, 2013, 3:34:53 PM
    Author     : asitha_l
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

        <script>
            
            function invokeGenerate(communicationKey){
             
                window.location="${pageContext.request.contextPath}/ViewChannelKeyMailingServlet?communicationKey="+communicationKey;
            
            }
            
            function invokeSearch()
            {
                document.channelkeymailing.action="${pageContext.request.contextPath}/SearchChannelKeyMailingServlet";
                document.channelkeymailing.submit();

            }
            
            function invokeReset()
            {                
                window.location="${pageContext.request.contextPath}/LoadChannelKeyMailingServlet";
            }
            
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CHANNEL_KEY_MAILING%>'                                  
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
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">             

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>
                <div class="content" style="height: 500px">
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>
                                <form method="POST" action="" name="channelkeymailing">

                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                                <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                                </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 200px;">Channel ID</td>
                                                    <td><input type="text"  value="${channelKeyMailingBean.channelID}" name="channelID" maxlength="8"/></td>
                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td style="width: 200px;">Channel Name</td>
                                                <td><input type="text"  value="${channelKeyMailingBean.channelName}" name="channelName" maxlength="32"/></td>
                                                <td></td>                                             
                                            </tr>                                                                                       
                                            <tr>
                                                <td style="width: 200px;">Channel Type</td>
                                                <td>
                                                    <select name="channelType" style="width: 160px;">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="channelType" items="${channelTypes}">
                                                            <c:if test="${channelKeyMailingBean.channelTypeCode==channelType.key}">
                                                                <option value="${channelType.key}" selected>${channelType.value}</option>
                                                            </c:if>
                                                            <c:if test="${channelKeyMailingBean.channelTypeCode != channelType.key}">
                                                                <option value="${channelType.key}">${channelType.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td> 
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Communication Key</td>
                                                <td>
                                                    <select name="communicationKey" style="width: 160px;">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="communicationKey" items="${communicationKeys}">
                                                            <c:if test="${channelKeyMailingBean.communicationKeyCode==communicationKey.key}">
                                                                <option value="${communicationKey.key}" selected>${communicationKey.value}</option>
                                                            </c:if>
                                                            <c:if test="${channelKeyMailingBean.communicationKeyCode != communicationKey.key}">
                                                                <option value="${communicationKey.key}">${communicationKey.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td> 
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td colspan="3">
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()"/>
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/></td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </form>
                                <%-- -------------------------add form end -------------------------------- --%>
                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Channel ID</th>
                                            <th>Channel Name</th>
                                            <th>Channel Type</th>
                                            <th>IP</th>
                                            <th>PORT</th>
                                            <th>Communication Key ID</th>
                                            <th>Generate</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchResult" items="${searchResultList}">
                                            <tr>
                                                <td >${searchResult.channelID}</td>
                                                <td >${searchResult.channelName}</td>
                                                <td >${searchResult.channelTypeDesc}</td>
                                                <td >${searchResult.ip}</td>
                                                <td >${searchResult.port}</td>
                                                <td >${searchResult.communicationKeyDesc}</td>
                                                <td ><a href='#' onclick="invokeGenerate('${searchResult.communicationKeyCode}')">Generate</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--  ------------------------- end developer area  --------------------------------                      -->
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
