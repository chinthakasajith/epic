<%-- 
    Document   : debitverifyhome
    Created on : Aug 27, 2012, 4:15:54 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeSearch()
            {

                document.searchappassignform.action="${pageContext.request.contextPath}/SearchDebitVerifyServlet";
                document.searchappassignform.submit();

            }
            
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadDebitVerifySearchServlet";

            }


    

        </script>
     <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBITVERIFYHOME%>'                                  
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


        <title>EPIC CMS DEBIT CARD VERIFICATION</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

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
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                        </tr>
                                    </table>

                                    <table>
                                        <tr>
                                            <td style="width: 174px">Application Type </td>

                                            <c:if test="${searchdocbean.cardCategory =='P'}"><td style="width: 100px"><input  type="radio" name="cardcategory" checked="true" value="P" style="width: 25px"> Personal</td></c:if><c:if test="${searchdocbean.cardCategory !='P'}"> <td style="width: 100px"><input type="radio" name="cardcategory" value="P" style="width: 25px"/>Personal</td></c:if>
                                            <c:if test="${searchdocbean.cardCategory =='J'}"><td><input  type="radio" name="cardcategory" checked="true" value="J" style="width: 25px"> Joint</td></c:if><c:if test="${searchdocbean.cardCategory !='J'}"> <td><input type="radio" name="cardcategory" value="J" style="width: 25px" />Joint</td></c:if>



                                            </tr>
                                        </table>
                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td>Application ID</td>
                                                        <td><input type="text"  value="${searchdocbean.applicationId}" name="appliactionid" maxlength="16"></td>
                                                <td></td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>
                                            <tr>
                                                <td style="width: 180px">Identification No</td>
                                                <td><input type="text"  value="${searchdocbean.identityNo}" name="identityno" maxlength="16"></td>
                                                <td></td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>                                           
                                            <tr>
                                                <td>Priority Level </td>
                                                <td>
                                                    <select  name="prioritycode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="priority" items="${priorityLevelList}">

                                                            <c:if test="${searchdocbean.priorityLevel==priority.key}">
                                                                <option value="${priority.key}" selected>${priority.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchdocbean.priorityLevel != priority.key}">
                                                                <option value="${priority.key}" >${priority.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>      

                                            <tr>
                                                <td>Status </td>
                                                <td>
                                                    <select  name="statuscode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="status" items="${statusMap}">

                                                            <c:if test="${searchdocbean.status==status.key}">
                                                                <option value="${status.key}" selected>${status.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchdocbean.status != status.key}">
                                                                <option value="${status.key}" >${status.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>


                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>From</td>

                                                <td>
                                                    <input  name="fromdate" readonly maxlength="16" value="${searchdocbean.fromDate}" key="fromdate" id="fromdate"  />

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

                                            <tr><td style="height: 5px"></td></tr>



                                            <tr>
                                                <td>To</td>

                                                <td>
                                                    <input  name="todate" readonly maxlength="16" value="${searchdocbean.toDate}" key="todate" id="todate"  />

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


                                            <tr><td style="height: 5px"></td></tr>


                                            <tr>
                                                <td></td>
                                                <td style="width: 300px"><input type="button" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DEBITVERIFYHOME%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                <td></td>
                                            </tr>
                                            <tr> <td style="height:12px;"></td></tr>
                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>



                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Application ID </th>
                                            <th>Identification Type</th>
                                            <th>Identification No</th>
                                            <th>Priority Level</th>
                                            <th>Employee Number</th>
                                            <th>Branch Name</th>
                                            <th>Assign User</th>
                                            <th>Application Type</th>
                                            <!--                                            <th>Verification Status</th>-->
                                            <th>Verify</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="assignapp" items="${sessionScope.SessionObject.documentVerifyBeanList}">
                                            <tr>
                                                <td >${assignapp.applicationId}</td>
                                                <td >${assignapp.identityType}</td>
                                                <td >${assignapp.identityNo}</td>
                                                <td >${assignapp.priorityDescription}</td>
                                                <td >${assignapp.referralEmpNo}</td>
                                                <td >${assignapp.referralBranchName}</td>
                                                <td >${assignapp.assignUser}</td>
                                                <td >${assignapp.cardCategoryDes}</td>
<!--                                                <td >${assignapp.verificationStatus}</td>-->

                                                <c:if test="${assignapp.cardCategory =='P'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateDebitPrimaryVerifyFormServlet?applicationid=${assignapp.applicationId}&identificationNo=${assignapp.identityNo}'>Verify</a></td>
                                                </c:if>
                                                <c:if test="${assignapp.cardCategory =='J'}">
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateDebitSecondaryVerifyFormServlet?applicationid=${assignapp.applicationId}&identificationNo=${assignapp.identityNo}'>Verify</a></td>
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



