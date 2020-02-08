<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
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

            function checkBoxValue() {
                var e = document.search.elements.length;


                var searchArray = [];

                for (var i = 0; i < e; i++) {
                    if (document.search.elements[i].checked) {
                        searchArray.push(document.search.elements[i].value);
                    }
                }
                //                alert(searchArray.length);
                if (searchArray.length > 0) {
                    //                    alert(searchArray.length);
                    window.location = "${pageContext.request.contextPath}/ProcessCalCreditScoreServlet?searchArray=" + searchArray;
                } else {
                    alert("Select at least one row to calculate credit score");

                }

            }
            function appCreditCalReset() {
                window.location = "${pageContext.request.contextPath}/LoadCalculateCreditScoreServlet";
            }
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CALCULATECREDITSCORE%>'
                        },
                function(data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

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



                <div class="content" style="height: 500px">

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" style="height: 500px;">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                             <td colspan="5"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/SearchCalculateCreditScoreServlate">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Application ID</td>
                                                    <td></td>
                                                    <td><input type="text" name="appID" value="${calBean.applicationID}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="prioLevel" style="width: 160px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.prioList}">
                                                                <c:if test="${vRule.prioCode==calBean.prioCode}" >
                                                                    <option selected="" value="${vRule.prioCode}">${vRule.description}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.prioCode!=calBean.prioCode}" >
                                                                    <option value="${vRule.prioCode}">${vRule.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>From Date</td>
                                                    <td></td>
                                                    <td>
                                                        <input  name="fromdate" readonly maxlength="16" value="${calBean.fromDate}" key="fromdate" id="fromdate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#fromdate").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>


                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>To Date</td>
                                                    <td></td>
                                                    <td>
                                                        <input  name="todate" readonly maxlength="16" value="${calBean.toDate}" key="todate" id="todate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#todate").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Assign User</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="user" style="width: 160px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="userVal" items="${requestScope.userList}">
                                                                <c:if test="${userVal==calBean.assignUser}" >
                                                                    <option selected="" value="${userVal}">${userVal}</option>
                                                                </c:if>
                                                                <c:if test="${userVal!=calBean.assignUser}" >
                                                                    <option  value="${userVal}">${userVal}</option>
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
                                                    <td><input type="submit" value="Search" name="Search" style="width: 100px"/><input  type="reset" onclick="appCreditCalReset()" value="Reset" style="width: 100px"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>


                                </c:if>

                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                <form method="POST" name="search" id="search">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Select</th>
                                                <th>Application ID</th>
                                                <th>Identification Type</th>
                                                <th>Identification Number</th>

                                                <th>Employee Number</th>
                                                <th>Assign User</th>



                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${requestScope.searchDataList}" var="dataList">
                                                <tr>
                                                    <td><input type="checkbox"  name ="ck" value="${dataList.applicationID}" ></td>
                                                    <td>${dataList.applicationID}</td>
                                                    <td>${dataList.identityTypeName}</td>
                                                    <td>${dataList.idNo}</td>
                                                    <td>${dataList.employeeNo}</td>
                                                    <td>${dataList.assignUser}</td>


                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <input type="button" value="Calculate" onclick="checkBoxValue()" />

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
