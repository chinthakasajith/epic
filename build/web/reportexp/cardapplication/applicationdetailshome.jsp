<%-- 
    Document   : applicationsummeryhome
    Created on : Nov 20, 2012, 7:01:46 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script >
            
            function invokeReset(ele){
                tags = ele.getElementsByTagName('input');
                
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                        
                    }
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                        tags }
                }
                getSysDate();
                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';
            }
            function getSysDate()
            {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth()+1;
                var yyyy = today.getFullYear();
                
                if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} today = yyyy+'-'+mm+'-'+dd;
                
                searchApplicationSummery.elements["fromDate"].value = today;
                searchApplicationSummery.elements["toDate"].value = today;
                
                
            }
            function invokeSearch()
            {
              
                document.searchApplicationSummery.action="${pageContext.request.contextPath}/SearchApplicationDetailsServlet?id=0";
                document.searchApplicationSummery.submit();
                
            }
            
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadApplicationDetailsServlet";
            }
                
            function invokeGenerateReport()
            {
              
                document.searchApplicationSummery.action="${pageContext.request.contextPath}/ApplicationDetailsReportPdfServlet";
                document.searchApplicationSummery.submit();
                
            }
            
            function invokeHistory(value){

                $.post("${pageContext.request.contextPath}/ViewApplicationDetailsHistoryServlet", {applicationID:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/reportexp/cardapplication/applicationdetailsview.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }

                });
            }
            
            function invokeView(applicationID){
            
                window.location="${pageContext.request.contextPath}/ViewApplicationDetailsServlet?applicationID="+applicationID;
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPLICATION_DETAILS_RPT%>'                                  
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

        <title>CMS MERCHANT MANAGEMENT</title>
    </head>

    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header">
            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>

                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form name="searchApplicationSummery" method="POST" action="">

                                    <table cellpadding="0" cellspacing="10"   >

                                        <tr>
                                            <td style="width: 200px;">NIC</td>
                                            <td><input type="text" name="nic"  maxlength="16" value="${summeryBean.nic}"></td>

                                            <td style="width: 200px;">Branch</td>
                                            <td><select  name="branch"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="branch" items="${branchList}">
                                                        <c:if test="${summeryBean.branch==branch.key}">
                                                            <option value="${branch.key}" selected="true" >${branch.value}</option>
                                                        </c:if>
                                                        <c:if test="${summeryBean.branch!=branch.key}">
                                                            <option value="${branch.key}">${branch.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Passport</td>
                                            <td><input type="text" name="passport"  maxlength="16" value="${summeryBean.passport}"></td>

                                            <td style="width: 200px;">Priority Level</td>
                                            <td><select  name="priorityLevel"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="lvl" items="${priorityLevelList}">
                                                        <c:if test="${summeryBean.priorityLevel==lvl.key}">
                                                            <option value="${lvl.key}" selected="true" >${lvl.value}</option>
                                                        </c:if>
                                                        <c:if test="${summeryBean.priorityLevel!=lvl.key}">
                                                            <option value="${lvl.key}">${lvl.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Application ID</td>
                                            <td><input type="text" name="applicationID"  maxlength="16" value="${summeryBean.applicationID}"></td>

                                            <td style="width: 200px;">Domain</td>
                                            <td><select  name="domain"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="domain" items="${domainList}">
                                                        <c:if test="${summeryBean.domain==domain.key}">
                                                            <option value="${domain.key}" selected="true" >${domain.value}</option>
                                                        </c:if>
                                                        <c:if test="${summeryBean.domain!=domain.key}">
                                                            <option value="${domain.key}">${domain.value}</option>
                                                        </c:if>
                                                    </c:forEach>

                                                </select>
                                            </td>
                                        </tr>
                                         <tr>
<!--                                            <td style="width: 200px;">Driving Licence</td>
                                            <td><input type="text" name="drivingLicence"  maxlength="16" value="${summeryBean.drivingLicence}"></td>-->

                                            <td style="width: 200px;">Application Status</td>
                                            <td><select  name="applicationStatus"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="status" items="${applicationStatusList}">
                                                        <c:if test="${summeryBean.applicationStatus==status.key}">
                                                            <option value="${status.key}" selected="true" >${status.value}</option>
                                                        </c:if>
                                                        <c:if test="${summeryBean.applicationStatus!=status.key}">
                                                            <option value="${status.key}">${status.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>


                                        <tr>
                                            <td style="width: 200px;">From Date</td>
                                            <td><input  name="fromDate" readonly maxlength="16" value="${summeryBean.fromDate}" key="fromDate" id="fromDate"  />
                                                <script type="text/javascript">
                                                    $(function() {
                                                        $( "#fromDate" ).datepicker({
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

                                            <td style="width: 200px;">To Date</td>
                                            <td><input  name="toDate" readonly maxlength="16" value="${summeryBean.toDate}" key="toDate" id="toDate"  />
                                                <script type="text/javascript">
                                                    $(function() {
                                                        $( "#toDate" ).datepicker({
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
                                            <td style="width: 200px;"><p>&nbsp;</p></td>
                                            <td></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"></td>
                                            <td>
                                                <input type="submit" name="search" value="Search" onclick="invokeSearch()" style="width: 100px;"/>&nbsp;
<!--                                                <input type="button" onclick="invokeReset(this.form)" name="next" value="Reset" style="width: 100px;"/>&nbsp;-->
                                                <input type="button" onclick="invokeReset()" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                <input type="submit" name="generate" value="Generate" onclick="invokeGenerateReport()" style="width: 100px;"/>&nbsp;
                                            </td>

                                        </tr>

                                    </table>

                                </form>
                                <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->
                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">

                                            <th>Application ID</th>
                                            <th>Identification Type</th>
                                            <th>Identification No </th>
                                            <th>Priority Level </th>
                                            <th>Referential Emp No</th>
                                            <th>Branch</th>
                                            <th>Assign User</th>
                                            <th>Application Date</th>

                                            <th>View</th>
                                            <th>History</th>

                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="summery" items="${summeryList}">
                                            <tr>
                                                <td >${summery.applicationID}</td>
                                                <td >${summery.identificationTypeDes}</td>
                                                <td >${summery.identificationNo}</td>
                                                <td >${summery.priorityLevelDes}</td>
                                                <td >${summery.refEmpNo}</td>
                                                <td >${summery.branchDes}</td>
                                                <td >${summery.assignUser}</td>
                                                <td >${summery.applicationDate}</td>

                                                <td  ><a href='#' onclick="invokeView('${summery.applicationID}')">View</a></td>
                                                <td  ><a href='#' onclick="invokeHistory('${summery.applicationID}')">History</a></td>

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
