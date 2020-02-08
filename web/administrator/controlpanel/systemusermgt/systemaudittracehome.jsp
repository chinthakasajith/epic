<%-- 
    Document   : systemaudittracehome
    Created on : Jan 30, 2012, 1:54:30 PM
    Author     : upul
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>





<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">







        <title>EPIC_CMS_HOME</title>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>



        <!--        include content.jsp for all js and css inclusion-->



        <script language="javascript">
               
            function invoke(){

                window.location = "${pageContext.request.contextPath}/LoadSystemAudittraceServlet";

            }
            
            
            function invokeSearch()
            {   
                oTable.fnDraw();    
                oTable = $('#jsontable').dataTable({
                    "bDestroy" : true,   
                    "bServerSide": true,
                    "sAjaxSource": "${pageContext.servletContext.contextPath}/ProcessSearchSystemAuditServlet",
                    "fnServerData": function ( sSource, aoData, fnCallback ) {
                        aoData.push( { "name": "isback", "value":$('#isback').val() } );
                        aoData.push( { "name": "userrole", "value":$('#userrole').val() } );
                        aoData.push( { "name": "username", "value":$('#username').val() } );
                        aoData.push( { "name": "description", "value":$('#description').val() } );
                        aoData.push( { "name": "application", "value":$('#application').val() } );
                        aoData.push( { "name": "section", "value":$('#section').val() } );
                        aoData.push( { "name": "page", "value":$('#page').val() } );                            
                        aoData.push( { "name": "task", "value":$('#task').val() } );
                        aoData.push( { "name": "remarks", "value":$('#remarks').val() } );
                        aoData.push( { "name": "ip", "value":$('#ip').val() } );
                        aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
                        aoData.push( { "name": "todate", "value":$('#todate').val() } );                                                  
                                    
                        $.ajax( {
                            "dataType": 'json', 
                            "type": "GET", 
                            "url": "${pageContext.servletContext.contextPath}/ProcessSearchSystemAuditServlet", 
                            "data": aoData, 
                            "success": fnCallback
                        } );
                    },
                    "bProcessing": true,
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "aoColumns": [
                        { "mDataProp": "userRoleCode","bVisible": true },
                        { "mDataProp": "description","bVisible": true },
                        { "mDataProp": "sectionCode","bVisible": true },
                        { "mDataProp": "pageCode","bVisible": true },
                        { "mDataProp": "taskCode","bVisible": true },
                        { "mDataProp": "ip","bVisible": true },
                        { "mDataProp": "lastUpdateduser","bVisible": true },
                        { "mDataProp": "lastUpdatedTime","bVisible": true },
                        { "mDataProp": "view","bVisible": true }
                    ]});             
                            
            } 
            
            
                        
            var oTable;
            $(document).ready(function() {  
                oTable = $('#jsontable').dataTable({
                    "bDestroy" : true,
                    "bServerSide": true,
                    "sAjaxSource": "${pageContext.servletContext.contextPath}/ProcessSearchSystemAuditServlet",
                    "fnServerData": function ( sSource, aoData, fnCallback ) {
                        aoData.push( { "name": "isback", "value":$('#isback').val() } );
                        aoData.push( { "name": "userrole", "value":$('#userrole').val() } );
                        aoData.push( { "name": "username", "value":$('#username').val() } );
                        aoData.push( { "name": "description", "value":$('#description').val() } );
                        aoData.push( { "name": "application", "value":$('#application').val() } );
                        aoData.push( { "name": "section", "value":$('#section').val() } );
                        aoData.push( { "name": "page", "value":$('#page').val() } );                            
                        aoData.push( { "name": "task", "value":$('#task').val() } );
                        aoData.push( { "name": "remarks", "value":$('#remarks').val() } );
                        aoData.push( { "name": "ip", "value":$('#ip').val() } );
                        aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
                        aoData.push( { "name": "todate", "value":$('#todate').val() } );                                                       
                                              
                        $.ajax( {
                            "dataType": 'json', 
                            "type": "GET", 
                            "url": "${pageContext.servletContext.contextPath}/ProcessSearchSystemAuditServlet", 
                            "data": aoData, 
                            "success": fnCallback
                        } );
                    },
                    "bProcessing": true,
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "aoColumns": [
                        { "mDataProp": "userRoleCode","bVisible": true },
                        { "mDataProp": "description","bVisible": true },
                        { "mDataProp": "sectionCode","bVisible": true },
                        { "mDataProp": "pageCode","bVisible": true },
                        { "mDataProp": "taskCode","bVisible": true },
                        { "mDataProp": "ip","bVisible": true },
                        { "mDataProp": "lastUpdateduser","bVisible": true },
                        { "mDataProp": "lastUpdatedTime","bVisible": true },
                        { "mDataProp": "view","bVisible": true } 
                                       
                    ]});
                          
            }); 
            
            function invokeView(auditId){
    
                document.search.action="${pageContext.request.contextPath}/ViewSystemAuditTraceServlet?auditId="+auditId;
                document.search.submit();
            }

        </script>
        <script >
            function settitle(){
               
                 $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SYSTEMAUDIT%>'                                  
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

                                <form name="search" method="POST" action="">

                                    <br>
                                    <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                    <font color= "green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        <br>
                                        <table cellspacing="10" cellpadding="0">
                                            <tr><td><input type="hidden" name="isback" id="isback" value="no"/></td></tr>
                                            <tr>
                                                <td style="width: 150px">User Role</td>
                                                <td>
                                                    <select name="userrole" id ="userrole" class="inputfeild">
                                                        <option value="">--SELECT--</option>
                                                    <c:forEach var="userole" items="${sessionScope.SessionObject.userRoleList}">
                                                        <c:if test="${param.userrole != null && userole.userRoleCode == param.userrole }">
                                                            <option value="${userole.userRoleCode}"selected>${userole.description}</option>
                                                        </c:if>
                                                        <c:if test="${userole.userRoleCode != param.userrole }">
                                                            <option value="${userole.userRoleCode}" > ${userole.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>

                                        <tr>
                                            <td>User Name</td>
                                            <td><input type="text" name="username" id="username" maxlength="255" class="inputfeild"value="${param.username}"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Description</td>
                                            <td><input type="text" name="description" id="description" maxlength="255" class="inputfeild"value="${param.description}"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Application</td>
                                            <td>
                                                <select name="application" id="application" class="inputfeild">
                                                    <option value="">--SELECT--</option>
                                                    <c:forEach var="application" items="${sessionScope.SessionObject.applicationModuleList}">
                                                        <c:if test="${param.application != null && application.applicationCode == param.application }">
                                                            <option value= "${application.applicationCode}" selected>${application.description} </option>>
                                                        </c:if>
                                                        <c:if test="${application.applicationCode != param.application}"> 
                                                            <option value="${application.applicationCode}" > ${application.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Section</td>
                                            <td>
                                                <select name="section" id="section" class="inputfeild">
                                                    <option value="">--SELECT--</option>
                                                    <c:forEach var="section" items="${sessionScope.SessionObject.assignUserRoleSections}">
                                                        <c:if test="${param.section != null && section.sectionCode == param.section }">
                                                            <option value= "${section.sectionCode}" selected>${section.description} </option>>
                                                        </c:if>
                                                        <c:if test="${section.sectionCode != param.section}">
                                                            <option value="${section.sectionCode}" > ${section.description}</option>
                                                        </c:if>>

                                                    </c:forEach>

                                                </select>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Page</td>
                                            <td>
                                                <select name="page" id="page" class="inputfeild">
                                                    <option value="">--SELECT--</option>

                                                    <c:forEach var="page" items="${sessionScope.SessionObject.pageDetails}">
                                                        <c:if test="${param.page != null && page.pageCode == param.page}">
                                                            <option value="${page.pageCode}" selected >${page.description}</option>>
                                                        </c:if>
                                                        <c:if test="${page.pageCode != param.page}">
                                                            <option value="${page.pageCode}" >${page.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Task</td>
                                            <td><select name="task" id="task" class="inputfeild">
                                                    <option value="">--SELECT--</option>
                                                    <c:forEach var="task" items="${sessionScope.SessionObject.taskDetails}">
                                                        <c:if test="${param.task != null && task.taskCode == param.task}">
                                                            <option value="${task.taskCode}" selected >${task.description}</option>>
                                                        </c:if>
                                                        <c:if test="${task.taskCode != param.task}">
                                                            <option value="${task.taskCode}" >${task.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Remarks</td>
                                            <td><input type="text" name="remarks" id="remarks" maxlength="255" class="inputfeild"value="${param.remarks}"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>IP</td>
                                            <td><input type="text" name="ip" id="ip" maxlength="64" class="inputfeild"value="${param.ip}"/></td>
                                            <td>&nbsp;</td>
                                        </tr>


                                        <tr>
                                            <td>From Date</td>
                                            <td>
                                                <input  name="fromdate" maxlength="16" readonly class="inputfeild"value="${param.fromdate}" key="fromdate" id="fromdate"  />
                                                <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                </script>

                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>To Date</td>
                                            <td>
                                                <input name="todate" maxlength="16"  readonly class="inputfeild"value="${param.todate}" key="todate" id="todate" />
                                                <script type="text/javascript">
                                                            $(function() {
                                                                $( "#todate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                </script>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                    <table>
                                        <tr>
                                            <td style="width: 150px"></td>
                                            <td>&nbsp;&nbsp;
                                                <input name="search" type="button" style="width: 100px" value="Search" onclick="invokeSearch()"/>
                                                <input name="Reset" type="Reset" value="Reset" style="width: 100px" onclick="invoke()" /> 
                                            </td> 
                                            <td> <a  href="#"  onclick="invokeHistory('<%=PageVarList.SYSTEMAUDIT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="0" border="1" class="display" id="jsontable" >
                                        <thead>
                                            <tr >
                                                <th style="width: 70px">User Role</th>
                                                <th >Description</th>
                                                <th style="width: 80px">Section</th>
                                                <th >Page</th>
                                                <th style="width: 70px">Task</th>
                                                <th >IP</th>
                                                <th style="width: 70px">User Name</th>
                                                <th style="width: 150px">Last Updated Time</th>
                                                <th style="width: 50px">View</th>

                                            </tr>
                                        </thead>
                                        <tbody>                                        
                                        </tbody>

                                    </table>
                                </form>
                                <br />
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
