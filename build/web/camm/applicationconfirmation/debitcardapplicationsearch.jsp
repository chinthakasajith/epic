<%-- 
    Document   : confirmationtableview
    Created on : Mar 12, 2012, 10:24:58 AM
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

                document.searchcreditofficerform.action="${pageContext.request.contextPath}/SearchDebitApplicationApproveServlet";
                document.searchcreditofficerform.submit();

            }
            
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadDebitApproveTableServlet";

            }


    

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBITAPPLICATONAPPROVE%>'                                  
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

        <title>EPIC CMS DEBIT APPLICATION CONFIRMATION</title>

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

                                <form method="POST" action="" name="searchcreditofficerform">
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>

                                        <table border="0">

                                            <tbody>
                                                <tr> <td style="height:20px;"></td></tr>
                                                <tr>
                                                    <td style="width: 168px;">Application Id</td>
                                                    <td><input type="text"  value="${searchappbean.applicationId}" name="appliactionid" maxlength="16"></td>
                                                <td></td>
                                            </tr>
                                            <tr> <td style="height:5px;"></td></tr>
                                            <tr>
                                                <td>Identification Number</td>
                                                <td><input type="text"  value="${searchappbean.idNumber}" name="idNumber" maxlength="16"></td>
                                                <td></td>
                                            </tr>
                                            <tr> <td style="height:5px;"></td></tr>

                                            <tr>
                                                <td>Application Type</td>

                                                <td>
                                                    <c:if test="${searchappbean.applicationType=='P'}">
                                                        <input type="radio" name="appType" value="P" checked="true"/> Personal
                                                    </c:if>

                                                    <c:if test="${searchappbean.applicationType!='P'}">
                                                        <input type="radio" name="appType" value="P" /> Personal
                                                    </c:if>

                                                    <c:if test="${searchappbean.applicationType=='J'}">
                                                        <input type="radio" name="appType" value="J" checked="true"/> Joint
                                                    </c:if>

                                                    <c:if test="${searchappbean.applicationType!='J'}">
                                                        <input type="radio" name="appType" value="J" /> Joint
                                                    </c:if>

                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr> <td style="height:5px;"></td></tr>

                                            <tr>
                                                <td>priority level </td>
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
                                            <tr> <td style="height:5px;"></td></tr>




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
                                            <tr> <td style="height:5px;"></td></tr>





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
                                            <tr> <td style="height:10px;"></td></tr>






                                            <tr>
                                                <td></td>
                                                <td><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td></td>
                                            </tr>
                                            <tr> <td style="height:5px;"></td></tr>

                                        </tbody>
                                    </table>
                                </form>        

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Application Id </th>
                                            <th>Identification Type</th>
                                            <th>Identification No</th>
                                            <th>Application Type</th>
                                            <th>Priority Level</th>
                                            <th>Application Status</th>

                                            <th>View</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="appDetails" items="${searchList}">
                                            <tr>
                                                <td >${appDetails.applicationId}</td>
                                                <td >${appDetails.identificationType}</td>
                                                <td >${appDetails.identificationNumber}</td>
                                                <td >${appDetails.applicationCategoryDes}</td>
                                                <td >${appDetails.priorityLevel}</td>
                                                <td >${appDetails.applicationStatus}</td>

                                                <td><a href='${pageContext.request.contextPath}/LoadDebitApproveDetailsServlet?applicationid=${appDetails.applicationId}'>View</a></td>


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

