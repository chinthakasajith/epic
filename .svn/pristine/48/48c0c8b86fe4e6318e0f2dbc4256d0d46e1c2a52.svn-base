<%-- 
    Document   : applicationsearch
    Created on : Feb 23, 2012, 9:25:08 AM
    Author     : mahesh_m
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeSearch()
            {

                document.searchappassignform.action="${pageContext.request.contextPath}/SearchApplicationServlet";
                document.searchappassignform.submit();

            }
            
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet";

            }


    

        </script>
    <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPSEARCH%>'                                  
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

        <title>EPIC CMS APPLICATION CHECKING</title>

    </head>
    <body>



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchappassignform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> ${errorMsg}</font> </td>
                                            <td><font color="green"> ${successMsg}</font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td>Application ID</td>
                                                <td><input type="text"  value="${searchappbean.applicationId}" name="appliactionid" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Priority Level  </td>
                                                <td>
                                                    <select  name="prioritycode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="priority" items="${priorityLevelList}">
                                                            <c:if test="${searchappbean.priorityLevel==priority.key}">
                                                                <option value="${priority.key}" selected>${priority.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchappbean.priorityLevel != priority.key}">
                                                                <option value="${priority.key}" >${priority.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>




                                            <tr>
                                                <td>From</td>

                                                <td>
                                                    <input  name="fromdate" readonly maxlength="16" value="${searchappbean.fromDate}" key="fromdate" id="fromdate"  />
                                                    
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

                                            </tr>





                                            <tr>
                                                <td>To</td>

                                                <td>
                                                    <input  name="todate" readonly maxlength="16" value="${searchappbean.toDate}" key="todate" id="todate"  />
                                                    
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

                                            </tr>




                                            <tr>
                                                <td>Assigned User </td>
                                                <td>
                                                    <select  name="assignuser">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="user" items="${usersList}">
                                                            <c:if test="${searchappbean.assignUser==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${searchappbean.assignUser!=user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Card Category </td>
                                                <td>
                                                    <select  name="cardCategory">
                                                        <option value="" >--SELECT--</option>

                                                       
                                                        <c:forEach var="category" items="${cardCategoryList}">
                                                            <c:if test="${searchappbean.cardCategory==category.categoryCode}">
                                                                <option value="${category.categoryCode}" selected>${category.description}</option>
                                                            </c:if>
                                                            
                                                            <c:if test="${searchappbean.cardCategory!=category.categoryCode}">
                                                                <option value="${category.categoryCode}" >${category.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </form>

                                <%-- ------------------------- add form end -------------------------------- --%>


                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr >
                                            <th>Application ID </th>
                                            <th>Identification Type</th>
                                            <th>Identification No</th>
                                            <th>Priority Level</th>
                                            <th>Card Category</th>
                                            <th>Assigned User</th>
                                            <th>Application Status</th>
                                            <th>Check</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="assignapp" items="${searchList}">
                                            <tr>
                                                <td >${assignapp.applicationId}</td>
                                                <td >${assignapp.identityTypeName}</td>
                                                <td >${assignapp.identityNo}</td>
                                                <td >${assignapp.priorityDescription}</td>
                                                <td >${assignapp.cardCategoryDes}</td>
                                                <td >${assignapp.assignUser}</td>
                                                <td >${assignapp.assignStatusDescription}</td>
                                               
                                                <c:if test="${assignapp.cardCategory == 'M'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid=${assignapp.applicationId}'>Check</a></td>
                                                </c:if>
                                                <c:if test="${assignapp.cardCategory == 'S'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadSupllimentaryApplicationCheckingServlet1?applicationid=${assignapp.applicationId}'>Check</a></td>
                                                </c:if>
                                               <c:if test="${assignapp.cardCategory == 'C'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadCorporateApplicationCheckingServlet?applicationid=${assignapp.applicationId}'>Check</a></td>
                                                </c:if>
                                               <c:if test="${assignapp.cardCategory == 'F'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadCArdAgainstFDApplicationCheckingServlet?applicationid=${assignapp.applicationId}'>Check</a></td>
                                                </c:if> 
                                               <c:if test="${assignapp.cardCategory == 'E'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadEstablishmentApplicationCheckingServlet?applicationid=${assignapp.applicationId}'>Check</a></td>
                                                </c:if>                                                     
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

