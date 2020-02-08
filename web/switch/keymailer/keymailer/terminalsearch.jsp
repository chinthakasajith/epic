<%-- 
    Document   : terminalsearch
    Created on : Oct 22, 2012, 10:18:16 AM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeTerminalSearch()
            {

                document.searchTerminal.action="${pageContext.request.contextPath}/SearchTerminalKeyMailerServlet";
                document.searchTerminal.submit();

            }
            
            function invokeReset()
            {
                
                window.location="${pageContext.request.contextPath}/LoadTerminalKeyMailerServlet";           

            }
           
            function invokeGenerate(terminalID){
             
                window.location="${pageContext.request.contextPath}/ViewTerminalKeyMailerDataServlet?terminalID="+terminalID;
            
            }


    

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.KEY_MAILER%>'                                  
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




                                <form method="POST" action="" name="searchTerminal">

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
                                                <td style="width: 200px;">Terminal ID(TID)</td>
                                                <td><input type="text"  value="${keyMailerBean.terminalID}" name="terminalID" maxlength="8"/></td>
                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><input type="text"  value="${keyMailerBean.merchantID}" name="merchantID" maxlength="15"/></td>
                                                <td></td>                                             
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Terminal Name</td>
                                                <td><input type="text"  value="${keyMailerBean.terminalName}" name="terminalName" maxlength="15"/></td>
                                                <td></td>
                                            </tr>




                                            <tr>
                                                <td style="width: 200px;">Serial Number</td>
                                                <td><input type="text"  value="${keyMailerBean.serialNo}" name="serialNo" maxlength="16"/></td>
                                                <td></td>

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Model</td>
                                                <td>
                                                    <select name="model" style="width: 160px;">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="model" items="${modelList}">
                                                            <c:if test="${keyMailerBean.model==model.key}">
                                                                <option value="${model.key}" selected>${model.value}</option>
                                                            </c:if>
                                                            <c:if test="${keyMailerBean.model != model.key}">
                                                                <option value="${model.key}" >${model.value}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td colspan="3">
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeTerminalSearch()"/>
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/></td>
                                            </tr>
                                        </tbody>
                                    </table>




                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>



                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Terminal ID(TID)</th>
                                            <th>Merchant ID</th>
                                            <th>Terminal Name</th>
                                            <th>Serial Number</th>
                                            <th>Model</th>

                                            <th>Generate</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchedTerminal" items="${searchList}">
                                            <tr>
                                                <td >${searchedTerminal.terminalID}</td>
                                                <td >${searchedTerminal.merchantID}</td>
                                                <td >${searchedTerminal.terminalName}</td>
                                                <td >${searchedTerminal.serialNo}</td>
                                                <td >${searchedTerminal.model}</td>


                                                <td ><a href='#' onclick="invokeGenerate('${searchedTerminal.terminalID}')">Generate</td>

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
