<%-- 
    Document   : callhistoryview
    Created on : Jul 24, 2012, 10:44:25 AM
    Author     : chanuka
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
                                    <table class="tit"> <tr> <td   class="center"> <b> CALL HISTORY VIEW </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="0" border="1" class="display" id="tableview" >
                                        <thead>
                                            <tr class="gradeB">
                                                <th class="view-table-header">Call History ID</th>
                                                <th class="view-table-header">Operation</th>
                                                <th class="view-table-header">Old Value</th>
                                                <th class="view-table-header">New Value</th>
                                                <th class="view-table-header">Remarks</th>
                                                <th class="view-table-header">Last Updated User</th>
                                                <th class="view-table-header">Last Updated Time</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${historyviewlist}" var="historyview">
                                                <tr class="gradeC">
                                                    <td class="view-table-tab">${historyview.callHistoryId}</td>
                                                    <td class="view-table-tab">${historyview.operation}</td>
                                                    <td class="view-table-tab">${historyview.oldValue}</td>
                                                    <td class="view-table-tab">${historyview.newValue}</td>
                                                    <td class="view-table-tab">${historyview.remarks}</td>
                                                    <td class="view-table-tab">${historyview.lastUpdatedUser}</td>
                                                    <td class="view-table-tab">${historyview.lastUpdatedTime}</td>
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

