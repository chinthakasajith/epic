<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/> 
        <!--        include content.jsp for all js and css inclusion-->

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tablecss/ColumnFilterWidgets.css" type="text/css" />
        <link href="/media/dataTables/ColumnFilterWidgets.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/ColumnFilterWidgets.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {
                $("#companies").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "sDom": 'W<"clear">lfrtip',
                    "oColumnFilterWidgets": {
                        "aiExclude": [ 0, 1, 5, 6, 7]
                    },
                    "aoColumnDefs": [{'bSortable': false, 'aTargets': [0, 6, 7]}]
                })
            });
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LOG_VIEWER%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('?');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                             
        </script> 
        <title>Log Viewer</title>

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
                                <script> settitle();</script>
                                <!--  ----------------------start  developer area  ------------------------------------->
                                <div id="container">
                                    <div id="demo_jui">
                                        <table id="companies" class="display">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>Name</th>
                                                    <th>Date / Time</th>
                                                    <th>Type</th>
                                                    <th>Category</th>
                                                    <th>Size(Bytes)</th>
                                                    <th></th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="file" items="${logfiles}">
                                                    <c:set var="color" value="red"/> 
                                                    <c:if test="${file.type=='INFO'}">
                                                        <c:set var="color" value="green"/>
                                                    </c:if>
                                                    <tr>
                                                        <td><input type="checkbox" id="${file.logFile.name}" name="selected"  value="${file.logFile.path}"></td>
                                                        <td style="font-weight: bold">${file.logFile.name}</td>
                                                        <td><fmt:formatDate value="${file.date}" pattern="yyyy/MM/dd hh:mm:ss" /></td>
                                                        <td style="color:${color}; font-weight: bold">${file.type}</td>
                                                        <td>${file.logFileCategory}</td>
                                                        <td>${file.length}</td>
                                                        <td><a href="<%=request.getContextPath()%>/DownloadFile?file=${file.logFile.path}"><img src="<%=request.getContextPath()%>/backoffice/logmanagement/media/images/download.png" width="20" height="20" alt="Download Log File" title="Download Log File"/> </a></td>
                                                        <td><a href="<%=request.getContextPath()%>/ViewLogFile?file=${file.logFile.path}"><img src="<%=request.getContextPath()%>/backoffice/logmanagement/media/images/view.png" width="20" height="20" alt="View Log File" title="View Log File"/></a></td>
                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <!--------------------------- end developer area  --------------------------------                      -->

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


