<%-- 
    Document   : terminalmodelmgthome
    Created on : Jan 30, 2013, 2:35:18 PM
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
                { pagecode:'<%= PageVarList.TERMINAL_MODEL%>'                                  
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
            
            function resetTerModelForm(){
                window.location = "${pageContext.request.contextPath}/LoadTerminalModelMgtServlet";
            }
           
            function viewTerminalModel(value) {
                window.location = "${pageContext.request.contextPath}/ViewTerminalModelServlet?id="+value;
            }
            function backForm() {
                window.location = "${pageContext.request.contextPath}/LoadTerminalModelMgtServlet";
            }
            
            function updateTerminalInfo(value) {
                window.location = "${pageContext.request.contextPath}/UpdateTerminalModelMgtLoadServlet?id="+value;
            }
            //            function updateCardType(value){
            //                window.location = "${pageContext.request.contextPath}/UpdateBankBrachMgtLoadServlet?id="+value;
            //            }
            function deleteTerminalModelInfo(value){
                
                answer = confirm("Do you really want to delete "+value+" terminal model code ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteTerminalModelMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadTerminalModelMgtServlet";
                }
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
                                <!--                                <table class="tit"> <tr> <td   class="center">  TERMINAL MODEL </td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
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
                                    <form method="POST" action="${pageContext.request.contextPath}/AddTerminalModelMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Model Code</td>
                                                    <td style="width: 33px"></td>
                                                    <td><input type="text" name="modelcode" maxlength="8" value="${modelBean.modelCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td style="width: 33px"></td>
                                                    <td><input type="text" name="modeldis" maxlength="64" value="${modelBean.description}" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="statusType" style="width: 163px">
                                                            <option value="">--SELECT--</option>

                                                            <c:forEach var="model" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${modelBean.status == model.statusCode}">
                                                                    <option value="${model.statusCode}" selected="true">${model.description}</option>
                                                                </c:if>
                                                                <c:if test="${modelBean.status != model.statusCode}">
                                                                    <option value="${model.statusCode}">${model.description}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>  
                                                    <td>Manufacture</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="manufacType" style="width: 163px">
                                                            <option value="">--SELECT--</option>

                                                            <c:forEach var="manufac" items="${manufacList}">
                                                                <c:if test="${modelBean.manufactureCode == manufac.manufactureCode}">
                                                                    <option value="${manufac.manufactureCode}" selected="true">${manufac.description}</option>
                                                                </c:if>
                                                                <c:if test="${modelBean.manufactureCode != manufac.manufactureCode}">
                                                                    <option value="${manufac.manufactureCode}">${manufac.description}</option>
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
                                                    <td style="width: 300px;"><input type="submit" value="Add" style="width: 100px;"/>
                                                        <input type="reset" value="Reset" onclick="resetTerModelForm()" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateTerminalModelMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Model Code</td>
                                                    <td style="width: 33px"></td>
                                                    <td><input type="text" name="modelcode" readonly="true" maxlength="8" value="${modelBean.modelCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td style="width: 33px"></td>
                                                    <td><input type="text" name="modeldis" maxlength="64" value="${modelBean.description}" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="statusType" style="width: 163px">
                                                            <option value="">--SELECT--</option>

                                                            <c:forEach var="model" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${modelBean.status == model.description}">
                                                                    <option value="${model.statusCode}" selected="true">${model.description}</option>
                                                                </c:if>
                                                                <c:if test="${modelBean.status != model.description}">
                                                                    <option value="${model.statusCode}">${model.description}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>  
                                                    <td>Manufacture</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="manufacType" style="width: 163px">
                                                            <option value="">--SELECT--</option>

                                                            <c:forEach var="manufac" items="${manufacList}">
                                                                <c:if test="${modelBean.manufactureCode == manufac.manufactureCode}">
                                                                    <option value="${manufac.manufactureCode}" selected="true">${manufac.description}</option>
                                                                </c:if>
                                                                <c:if test="${modelBean.manufactureCode != manufac.manufactureCode}">
                                                                    <option value="${manufac.manufactureCode}">${manufac.description}</option>
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

                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr> 

                                            <td></td>
                                            <td></td>
                                            <td style="width: 300px;">
                                                <input type="submit" value="Update" style="width: 100px;"/>
                                                <input type="reset" value="Reset" onclick="updateTerminalInfo('${modelBean.modelCode}')" style="width: 100px;"/>
                                            </td>
                                            </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewTerminalModelServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Model Code</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${modelBean.modelCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Model Description</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${modelBean.modelDesc}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${modelBean.status}</td>
                                                </tr>
                                                <tr>
                                                    <td>Manufacture</td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${modelBean.description}</td>
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
                                            <th>Model Code</th>
                                            <th>Model Description</th>
                                            <th>Status</th>
                                            <th>Manufacture</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${terminalModelList}" var="modelBean">
                                            <tr> 
                                                <c:if test="${modelBean.modelCode==null || modelBean.modelCode == ''}">
                                                    <td></td>
                                                </c:if>
                                                <c:if test="${modelBean.modelCode!=null}">
                                                    <td>${modelBean.modelCode}</td>
                                                </c:if>
                                                <c:if test="${modelBean.modelDesc==null || modelBean.modelDesc == ''}">
                                                    <td></td>
                                                </c:if>
                                                <c:if test="${modelBean.modelDesc!=null}">
                                                    <td>${modelBean.modelDesc}</td>
                                                </c:if>
                                                <c:if test="${modelBean.status==null || modelBean.status == ''}">
                                                    <td></td>
                                                </c:if>
                                                <c:if test="${modelBean.status!=null}">
                                                    <td>${modelBean.status}</td>
                                                </c:if>
                                                <c:if test="${modelBean.description==null || modelBean.description == ''}">
                                                    <td></td>
                                                </c:if>
                                                <c:if test="${modelBean.description!=null}">
                                                    <td>${modelBean.description}</td>
                                                </c:if>

                                                <td><a  href='#' onclick="viewTerminalModel('${modelBean.modelCode}')">View</a></td>
                                                <td><a  href='#' onclick="updateTerminalInfo('${modelBean.modelCode}')">Update</a></td>
                                                <td><a  href='#' onclick="deleteTerminalModelInfo('${modelBean.modelCode}')">Delete</a></td>
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

