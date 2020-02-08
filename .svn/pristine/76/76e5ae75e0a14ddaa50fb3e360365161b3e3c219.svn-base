<%-- 
    Document   : communicationkeyshome
    Created on : Jan 15, 2013, 2:36:51 PM
    Author     : asela
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            
            
            function invokeResetInUpdate(id,description)
            {
                
                window.location = "${pageContext.request.contextPath}/LoadUpdateCommunicationKeyServlet?id="+id+"&description="+description;
                
            }
           
            
            function invokeReset()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadCommunicationKeyServlet";
                
            }
            
            function invokeGoBack()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadCommunicationKeyServlet";
                
            } 
            function deleteCommunicationInfo(value){
                
                answer = confirm("Do you really want to delete  communication id " + value + "? ")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteCommunicationKeyServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCommunicationKeyServlet";
                }
            }             
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.COMMUNICATION_KEYS%>'                                  
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
                                    <!--add-->


                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" name="addform" action="${pageContext.request.contextPath}/AddCommunicationKeyServlets">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody> 
                                                <tr>
                                                    <td></td>
                                                    <td><input type="hidden"  value="${operationtype}" name="operationtype" /></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Communication Key ID</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="keyid" value="${keybean.id}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> Description</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="description" value="${keybean.description}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Acquire Mode</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="lcstype">
                                                            <c:forEach var="lcs" items="${lcsStatusBeans}">
                                                                <c:if test="${keybean.communicationType==lcs.code}">
                                                                    <option value="${lcs.code}" selected="true">${lcs.description}</option>
                                                                </c:if>
                                                                <c:if test="${keybean.communicationType!=lcs.code}">
                                                                    <option value="${lcs.code}">${lcs.description}</option>
                                                                </c:if>                                                                
                                                            </c:forEach>    
                                                        </select>
                                                    </td>
                                                </tr>                                               
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;" colspan="2">&nbsp;&nbsp;
                                                        <input type="submit" value="Add" class="defbutton" name="add" />
                                                        <input type="reset" value="Reset" class="defbutton" name="reset" onclick="invokeReset()"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" name="updateform" action="${pageContext.request.contextPath}/UpdateCommunicationKeyServlet">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody> 
                                                <tr>
                                                    <td></td>
                                                    <td><input type="hidden"  value="${operationtype}" name="operationtype" /></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Communication Key ID</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="keyid" value="${keybean.id}" readonly="true">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td > Description</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="description" value="${keybean.description}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Acquire Mode</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="lcstype">
                                                            <c:forEach var="lcs" items="${lcsStatusBeans}">
                                                                <c:if test="${keybean.communicationType==lcs.code}">
                                                                    <option value="${lcs.code}" selected="true">${lcs.description}</option>
                                                                </c:if>
                                                                <c:if test="${keybean.communicationType!=lcs.code}">
                                                                    <option value="${lcs.code}">${lcs.description}</option>
                                                                </c:if>                                                                
                                                            </c:forEach>    
                                                        </select>
                                                    </td>
                                                </tr>                                                  
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td colspan="2">&nbsp;&nbsp;
                                                        <input type="submit" value="Update" class="defbutton" name="update" />
                                                        <input type="reset" value="Reset" class="defbutton" name="reset" onclick="invokeReset()"/>
                                                        <input type="button" value="Cancel" class="defbutton" name="cancel" onclick="invokeGoBack()"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='view'}" >
                                    <form>
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody> 
                                                <tr>
                                                    <td>Communication Key ID</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${keybean.id}</td>
                                                </tr>
                                                <tr>
                                                    <td>Key Description</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${keybean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" name="back" value="Back" onclick="invokeGoBack()" style="width: 100px"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <!-- ----------------------------------------------------------------Start Data Table ---------------------------------------------------------------  -->            
                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Communication Key Id</th>
                                            <th>Key Description</th>
                                            <th>LCS Description</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="keybean" items="${keybeanList}">
                                            <tr>
                                                <td>${keybean.id}</td>
                                                <td >${keybean.description}</td> 
                                                <td>${keybean.communicationTypeDes}</td>
                                                <td><a href='${pageContext.request.contextPath}/ViewCommunicationKeyServlet?id=<c:out value="${keybean.id}"></c:out>'>View</a></td>
                                                <td><a href='${pageContext.request.contextPath}/LoadUpdateKeyServlet?id=<c:out value="${keybean.id}"></c:out>'/>Update</td>
                                                <td ><a  href="#" onclick="deleteCommunicationInfo('${keybean.id}')" > Delete</td>

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
