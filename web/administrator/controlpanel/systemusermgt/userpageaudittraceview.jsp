<%-- 
    Document   : userpageaudittraceview
    Created on : Jan 31, 2012, 1:23:55 PM
    Author     : upul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>EPIC_CMS_HOME</title>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <style type="text/css">
            body {
                background: url() repeat-x;
                font: normal 80% "Lucida Sans Unicode",sans-serif;
                margin: 0;
            }
        </style>

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">




                <div class="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <form>
                                    <table class="tit"> <tr> <td   class="center"> <b> USER PAGE AUDITTRACE </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="0" border="1" class="display" id="tableview" >
                                        <thead>
                                            <tr class="gradeB">
                                                <th class="view-table-header">User Role</th>
                                                <th class="view-table-header">Description</th>
                                                <th class="view-table-header">Section</th>
                                                <th class="view-table-header">Page</th>
                                                <th class="view-table-header">Task</th>
                                                <th class="view-table-header">Remarks</th>
                                                <th class="view-table-header">IP</th>
                                                <th class="view-table-header">Last Updated User</th>
                                                <th class="view-table-header">Last Updated Time</th>

                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${auditlist}" var="audit">
                                                <tr class="gradeC">
                                                    <td class="view-table-tab">${audit.userRoleCode}</td>
                                                    <td class="view-table-tab">${audit.description}</td>
                                                    <td class="view-table-tab">${audit.sectionCode}</td>
                                                    <td class="view-table-tab">${audit.pageCode}</td>
                                                    <td class="view-table-tab">${audit.taskCode}</td>
                                                    <td class="view-table-tab">${audit.remarks}</td>
                                                    <td class="view-table-tab">${audit.ip}</td>
                                                    <td class="view-table-tab">${audit.lastUpdateduser}</td>
                                                    <td class="view-table-tab">${audit.lastUpdatedTime}</td>
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

    </body>
</html>
