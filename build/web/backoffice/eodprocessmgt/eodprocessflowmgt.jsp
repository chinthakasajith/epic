<%-- 
    Document   : eodprocessflowmgt
    Created on : Oct 23, 2012, 10:56:00 AM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        
        <script type="text/javascript">
            
             function getProcessList(){                
                document.addprocessflowform.action="${pageContext.request.contextPath}/LoadStepIDForCategoryServlet";
                document.addprocessflowform.submit();                    
            }
            
            function getSelectedProcessList(){                
                document.addprocessflowform.action="${pageContext.request.contextPath}/GetSelectedProcessesByStepIDServlet";
                document.addprocessflowform.submit();                    
            }
            
            function selectAll(selectBox) {
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }       
                invokeAdd();
            }
            
            function invokeAdd()
            {

                document.addprocessflowform.action="${pageContext.request.contextPath}/AddEODProcessFlowMgtServlet";
                document.addprocessflowform.submit();

            }
            
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadEODProcessFlowMgtServlet";
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.EOD_PROCESS_FLOW%>'                                  
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
                                        <td><b><font color="Red"> ${errorMsg}</font> </b></td>
                                        <td><b><font color="green"> ${successMsg}</font> </b></td>
                                        <td></td>

                                    </tr>
                                </table>

                                <br/>
                                <%--  start add form --%>

                                <c:if test="${operationtype=='add'}" >

                                    <form action="" method="post" name="addprocessflowform" >
                                        <table border="0">
                                            
                                            <tr>
                                                <td>Process Category</td>
                                                <td>: </td>
                                                <td> <select id="proCat" name="proCat" onchange="getProcessList()">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="cat" items="${categoryList}">
                                                            
                                                            <c:if test="${cat.categoryCode==categoryIDxx}">
                                                                <option value="${cat.categoryCode}" selected>${cat.description}</option>
                                                            </c:if>
                                                            <c:if test="${cat.categoryCode!=categoryIDxx}">
                                                                <option value="${cat.categoryCode}" >${cat.description}</option>

                                                            </c:if>
                                                            
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Step ID </td>
                                                <td>: </td>
                                                <td> <select id="stepId" name="stepId" onchange="getSelectedProcessList()">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="count" items="${countList}">
                                                            
                                                            <c:if test="${count==stepIdxx}">
                                                                <option value="${count}" selected>${count}</option>
                                                            </c:if>
                                                            <c:if test="${count!=stepIdxx}">
                                                                <option value="${count}" >${count}</option>

                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr style="height: 5px;"></tr>
                                            <tr>
                                                <td>
                                                    <h4><b>All Processes</b></h4>
                                                    <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="pro" items="${processList}">
                                                            <option value="${pro.processId}" >${pro.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td align="center" >
                                                    <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                    <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                    <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                    <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                </td>
                                                <td>
                                                    <h4><b>Selected Processes</b></h4>


                                                    <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>

                                                        <c:forEach  var="ass" items="${selectedProList}">
                                                            <option value="${ass.processId}" >${ass.description}</option>
                                                        </c:forEach>

                                                    </select>


                                                </td>
                                            </tr>
                                            <tr>
                                                <td><input type="button" value="Save" name="Save" onclick="selectAll(assignlist)" class="defbutton"/> 
                                                <input onclick="invokeReset()" type="reset" value="Reset" class="defbutton"/> </td>
                                                <td> </td>
                                            </tr>

                                        </table>
                                    </form>
                                </c:if>

                                <%-- show table data --%>
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>                                                
                                                <th>Step</th>
                                                <th>Category</th>
                                                <th>Process</th>
                                                
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${allFlowList}" var="list">
                                                <tr>
                                                    
                                                    <td>${list.stepId}</td>
                                                    <td>${list.pocessCategoryDes}</td>
                                                    <td>${list.pocessDes}</td>

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

