<%-- 
    Document   : breakdownview
    Created on : Apr 4, 2012, 3:34:45 PM
    Author     : mahesh_m
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
<!--                                                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">




                <div class="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <form>
                                    <table class="tit"> <tr> <td   class="center"> Credit Score Break Down </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="0" border="1" class="display" id="tableview" >
                                        <thead>
                                            <tr class="gradeB">
                                                <th class="view-table-header">Rule</th>
                                                <th class="view-table-header">Score</th>

                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${breakDownList}" var="credit">
                                                <tr class="gradeC">
                                                    <td class="view-table-tab">${credit.ruleCode}</td>
                                                    <td class="view-table-tab">${credit.score}</td>
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
