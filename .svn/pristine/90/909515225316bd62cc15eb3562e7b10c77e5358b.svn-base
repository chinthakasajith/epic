<%-- 
    Document   : applicationverificationreport
    Created on : Nov 22, 2012, 8:49:16 AM
    Author     : asela
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script type="text/javascript" > 
          
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadApplicationVerificationReportServlet";
            } 
            function invokeSearch()
            {

                document.searchverificationreportform.action="${pageContext.request.contextPath}/SearchApplicationVerificationReportServlet";
                document.searchverificationreportform.submit();

            }           

            function invokeVerificationReportDetails(){
                document.searchverificationreportform.action = "${pageContext.request.contextPath}/ViewApplicationVerificationReportServlet";
                document.searchverificationreportform.submit();
            }
         
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPLICATION_VERIFICATION_RPT%>'                                  
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


        <title>EPIC_CMS_HOME</title>

    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">




                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <form method="POST" action="" name="searchverificationreportform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> <b>${errorMsg}</b></font> </td>
                                            <td><font color="green"><b> ${successMsg}</b></font> </td>
                                            <td></td>

                                        </tr>
                                    </table>


                                    <table cellpadding="0" cellspacing="10">
                                        <tr>          
                                        </tr>
                                        <tr>
                                            <td>NIC</td>
                                            <td></td>
                                            <td><input type="text" name="nic" id="nicId" value="${searchbean.nic}"/></td>
                                            <td>Branch</td>
                                            <td></td>
                                            <td>
                                                <select name="branch">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="branch" items="${bankBranchList}">
                                                        <option value="${branch.branchCode}" <c:if test="${branch.branchCode == searchbean.branchCode}"> selected="true" </c:if> >${branch.description}</option>
                                                    </c:forEach>
                                                </select>    
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Passport</td>
                                            <td></td>
                                            <td><input type="text" name="passport" id="passportId" value="${searchbean.passport}"/></td>
                                            <td>Priority Level Status</td>
                                            <td></td>
                                            <td>
                                                <select name="priorityLevel">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="priority" items="${priorityLevelMap}">
                                                        <option value="${priority.key}" <c:if test="${priority.key == searchbean.priorityLevel}"> selected="true" </c:if>>${priority.value}</option>
                                                    </c:forEach>
                                                </select>    
                                            </td>
                                        </tr> 
                                        <tr>
<!--                                            <td>Driving Licence</td>
                                            <td></td>
                                            <td><input type="text" name="drivingLicence" id="drivingLicenceId" value="${searchbean.drivingLicence}"/></td>-->
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Verification Status</td>
                                            <td></td>
                                            <td>
                                                <select name="verificationStatus">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="status" items="${sessionScope.SessionObject.statusList}">
                                                        <option value="${status.statusCode}" <c:if test="${status.statusCode == searchbean.verificationStatus}"> selected="true" </c:if> >${status.description}</option>
                                                    </c:forEach>
                                                </select>    
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>Application Id </td>
                                            <td></td>
                                            <td><input type="text" name="applicationId" value="${searchbean.applicationId}"/></td>
                                            <td>Application Domain Status</td>
                                            <td></td>
                                            <td>
                                                <select name="applicationDomain">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="applicationDomain" items="${applicationDomainMap}">
                                                        <option value="${applicationDomain.key}" <c:if test="${applicationDomain.key == searchbean.domain}"> selected="true" </c:if> >${applicationDomain.value}</option>
                                                    </c:forEach>
                                                </select>    
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 180px">From</td>
                                            <td></td>
                                            <td>
                                                <input  name="fromdate" readonly maxlength="16" value="${searchbean.fromDate}" key="fromdate" id="fromdate"  />
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


                                            <td style="width: 180px">To</td>
                                            <td></td>
                                            <td>
                                                <input  name="todate" readonly maxlength="16" value="${searchbean.toDate}" key="todate" id="todate"  />
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

                                    </table>

                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td style="width: 180px"></td>
                                                <td style="width: 350px">
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()">
                                                    <input type="button" value="View Report" name="viewReport" style="width: 100px" onclick="invokeVerificationReportDetails()">
                                                </td>
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>


                                    <table  border="1"  class="display" id="scoreltableview">
                                        <thead>
                                            <tr>
                                                <th>Application ID</th>
                                                <th>Branch Name</th>
                                                <th>Priority Level</th>
                                                <th>Domain</th>
                                                <th>ID Type</th>
                                                <th>Number</th>
                                                <th>Verification Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="verificationReport" items="${searchBeanList}">
                                                <tr>
                                                    <td >${verificationReport.applicationId}</td>
                                                    <td >${verificationReport.branchDescription}</td>
                                                    <td >${verificationReport.priorityLevelDes}</td>
                                                    <td >${verificationReport.domain}</td>
                                                    <td >${verificationReport.idType}</td>
                                                    <td >${verificationReport.idNumber}</td>
                                                    <td >${verificationReport.verificationStatusDes}</td> 
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
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>