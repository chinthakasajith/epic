<%-- 
    Document   : listnerkeymailing
    Created on : Feb 14, 2013, 3:30:10 PM
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

        <script >
            function settitle(){
               
                 $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.TERMINAL_MANUFACTURE%>'                                  
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

        <script type="text/javascript">
            
            function resetListnerKeyMail(){
                window.location = "${pageContext.request.contextPath}/LoadListnerKeyMailingServlet";
            }
           
            function invokeGenerateKeyMail(listenerId) {
                window.location = "${pageContext.request.contextPath}/ViewListenerKeyMailingServlet?listenerId="+listenerId;
            
            }
            
            function backForm() {
                window.location = "${pageContext.request.contextPath}/LoadterminalManufacServlet";
            }
            
            function updateTerminalInfo(value) {
                window.location = "${pageContext.request.contextPath}/UpdateTerminalManufacLoadServlet?id="+value;
            }
            function searchListnerKeyMail() {
                document.searchform.action="${pageContext.request.contextPath}/SearchListnerKeyMailingServlet";
                document.searchform.submit();
            }
        
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LISTNER_KEY_MAILING%>'                                  
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

                                <form method="POST" action="" name="searchform">

                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td>Listener Name</td>
                                                <td></td>
                                                <td><input type="text" name="listnername" maxlength="64" value="${listnerBean.listnerTypeDesc}"/></td>
                                            </tr>

                                            <tr>  
                                                <td>Listener Type</td>
                                                <td></td>
                                                <td>
                                                    <select name="listnerType" style="width: 163px">
                                                        <option value="">--SELECT--</option>

                                                        <c:forEach var="listner" items="${listnerList}">
                                                            <c:if test="${listnerBean.listnerTypeCode == listner.listnerTypeCode}">
                                                                <option value="${listner.listnerTypeCode}" selected="true">${listner.listnerTypeDesc}</option>
                                                            </c:if>
                                                            <c:if test="${listnerBean.listnerTypeCode != listner.listnerTypeCode}">
                                                                <option value="${listner.listnerTypeCode}">${listner.listnerTypeDesc}</option>
                                                            </c:if>    
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>  
                                                <td>Communication Key</td>
                                                <td></td>
                                                <td>
                                                    <select name="comkeyType" style="width: 163px">
                                                        <option value="">--SELECT--</option>

                                                        <c:forEach var="comkey" items="${comkeyList}">
                                                            <c:if test="${listnerBean.comKeyId == comkey.comKeyId}">
                                                                <option value="${comkey.comKeyId}" selected="true">${comkey.comKeyDesc}</option>
                                                            </c:if>
                                                            <c:if test="${listnerBean.comKeyId != comkey.comKeyId}">
                                                                <option value="${comkey.comKeyId}">${comkey.comKeyDesc}</option>
                                                            </c:if>    
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>                                                

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;">
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="searchListnerKeyMail()"/>
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="resetListnerKeyMail()"/></td>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </form>


                                <br>            
                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Listener ID</th>
                                            <th>Listener Name</th>
                                            <th>Listener Type</th>
                                            <th>Communication Key</th>
                                            <th>Generate</th>
                                        </tr>
                                    </thead>
                                    <tbody>   
                                        <c:forEach  items="${searchList}" var="searchedKeyMail">
                                            <tr> 
                                                <td>${searchedKeyMail.listenerId}</td>
                                                <td>${searchedKeyMail.listnerTypeDesc}</td>
                                                <td>${searchedKeyMail.listnerTypeCode}</td>
                                                <td>${searchedKeyMail.comKeyDesc}</td>
                                                <td><a href='#' onclick="invokeGenerateKeyMail('${searchedKeyMail.listenerId}')">Generate</a></td>
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

