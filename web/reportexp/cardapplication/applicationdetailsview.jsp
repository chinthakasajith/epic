<%-- 
    Document   : applicationdetailsview
    Created on : Nov 22, 2012, 10:22:15 AM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>EPIC_CMS_HOME</title>

        <jsp:include page="/content.jsp"/>
       
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
        
            </div>

            <div class="main">

                <div class="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <form>
                                    <table class="tit"> <tr> <td   class="center"> <b> APPLICATION HISTORY VIEW </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table><td>Application ID : ${applicationID} </td></table>
                                    <table cellpadding="0" cellspacing="0" border="1" class="display" id="tableview" >
                                        <thead>
                                            <tr class="gradeB">
                                                <th class="view-table-header">Application Stage</th>
                                                <th class="view-table-header">Status</th>
                                                <th class="view-table-header">Remarks</th>
                                                <th class="view-table-header">Last Updated User</th>
                                                <th class="view-table-header">Last Updated Time</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${historyList}" var="historyview">
                                                <tr class="gradeC">
                                                    <td class="view-table-tab">${historyview.applicationLevelDes}</td>
                                                    <td class="view-table-tab">${historyview.applicationStatusDes}</td>
                                                    <td class="view-table-tab">${historyview.remarks}</td>
                                                    <td class="view-table-tab">${historyview.lastUpdatedUser}</td>
                                                    <td class="view-table-tab">${historyview.lsatUpdaetdTime}</td>
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
