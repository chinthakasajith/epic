<%-- 
    Document   : terminalmanufacturehome
    Created on : Jan 29, 2013, 2:20:53 PM
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
            
            function resetForm(){
                window.location = "${pageContext.request.contextPath}/LoadterminalManufacServlet";
            }
           
            function viewTerminalManuFact(value) {
                window.location = "${pageContext.request.contextPath}/ViewTerminalManufacServlet?id="+value;
            }
            function backForm() {
                window.location = "${pageContext.request.contextPath}/LoadterminalManufacServlet";
            }
            
            function updateTerminalInfo(value) {
                window.location = "${pageContext.request.contextPath}/UpdateTerminalManufacLoadServlet?id="+value;
            }
            //            function updateCardType(value){
            //                window.location = "${pageContext.request.contextPath}/UpdateBankBrachMgtLoadServlet?id="+value;
            //            }
            function deleteTerminalInfo(value){
                
                answer = confirm("Do you really want to delete "+value+" terminal manufacture code ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteTerminalManufacServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadterminalManufacServlet";
                }
            }
            
        </script>
        <script>
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
                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddTerminalManufacServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="terminalcode" maxlength="8" value="${terminalBean.manufactureCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="terminaldis" maxlength="64" value="${terminalBean.description}" /></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Add" style="width: 100px;"/>
                                                        <input type="reset" value="Reset" onclick="resetForm()" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateTerminalManufacServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="terminalcode" readonly="true" maxlength="8" value="${terminalBean.manufactureCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="terminaldis" maxlength="64" value="${terminalBean.description}" /></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>  
                                                
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="submit" value="Update" style="width: 100px;"/>
                                                        <input type="reset" value="Reset" onclick="updateTerminalInfo('${terminalBean.manufactureCode}')" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewTerminalManufacServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Code</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${terminalBean.manufactureCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${terminalBean.description}</td>
                                                </tr>
                                                
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="button" value="Back" style="width: 100px;" onclick="backForm()"/></td>

                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <br>            
                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Code</th>
                                            <th>Description</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${terminalList}" var="terminalBean">
                                            <tr> 
                                                <td>${terminalBean.manufactureCode}</td>
                                                <td>${terminalBean.description}</td>
                                                <td><a  href='#' onclick="viewTerminalManuFact('${terminalBean.manufactureCode}')">View</a></td>
                                                <td><a  href='#' onclick="updateTerminalInfo('${terminalBean.manufactureCode}')">Update</a></td>
                                                <td><a  href='#' onclick="deleteTerminalInfo('${terminalBean.manufactureCode}')">Delete</a></td>
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
