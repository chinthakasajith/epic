<%-- 
    Document   : applicationcreditscorebreakdownhome
    Created on : Nov 22, 2012, 4:20:08 PM
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

        <script>
            function settitle(){
        
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPLICATION_CREDIT_SCORE_BREAKDOWN_RPT%>'                                  
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
                
                searchApplicationCreditScore.elements["fromDate"].value = today;
                searchApplicationCreditScore.elements["toDate"].value = today;
                
                
            }
            function invokeSearch()
            {
              
                document.searchApplicationCreditScore.action="${pageContext.request.contextPath}/SearchApplicationCreditScoreBreakDownServlet?id=0";
                document.searchApplicationCreditScore.submit();
                
            }
            
            //            function invokeGenerateReport()
            //            {
            //              
            //                document.searchApplicationCreditScore.action="${pageContext.request.contextPath}/GenerateReportApplicationReportServlet";
            //                document.searchApplicationCreditScore.submit();
            //                
            //            }
            
            function invokeViewPdf() {
                document.searchApplicationCreditScore.action="${pageContext.request.contextPath}/ApplicationCreditScoreReportPdfServlet";
                document.searchApplicationCreditScore.submit();            
            }
            
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadApplicationCreditScoreBreakDownServlet";
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
                
                window.location="${pageContext.request.contextPath}/ViewApplicationCreditScoreBreakDownServlet?applicationID="+applicationID;
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

                                <form name="searchApplicationCreditScore" method="POST" action="">

                                    <table cellpadding="0" cellspacing="10"   >

                                        <tr>
                                            <td style="width: 200px;">NIC</td>
                                            <td><input type="text" name="nic"  maxlength="16" value="${creditScoreBean.nic}"></td>

                                            <td style="width: 200px;">Branch</td>
                                            <td><select  name="branch"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="branch" items="${branchList}">
                                                        <c:if test="${creditScoreBean.branch==branch.key}">
                                                            <option value="${branch.key}" selected="true" >${branch.value}</option>
                                                        </c:if>
                                                        <c:if test="${creditScoreBean.branch!=branch.key}">
                                                            <option value="${branch.key}">${branch.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Passport</td>
                                            <td><input type="text" name="passport"  maxlength="16" value="${creditScoreBean.passport}"></td>

                                            <td style="width: 200px;">Priority Level</td>
                                            <td><select  name="priorityLevel"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="lvl" items="${priorityLevelList}">
                                                        <c:if test="${creditScoreBean.priorityLevel==lvl.key}">
                                                            <option value="${lvl.key}" selected="true" >${lvl.value}</option>
                                                        </c:if>
                                                        <c:if test="${creditScoreBean.priorityLevel!=lvl.key}">
                                                            <option value="${lvl.key}">${lvl.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
<!--                                            <td style="width: 200px;">Driving License</td>
                                            <td><input type="text" name="drivingLicence"  maxlength="16" value="${creditScoreBean.drivingLicence}"></td>-->
                                            <td style="width: 200px;">Application ID</td>
                                            <td><input type="text" name="applicationID"  maxlength="16" value="${creditScoreBean.applicationID}"></td>
                                            
                                            <td style="width: 200px;">Credit Score User</td>
                                            <td><select  name="creditScoreUser"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="status" items="${csUserList}">
                                                        <c:if test="${creditScoreBean.creditScoreUser==status.key}">
                                                            <option value="${status.key}" selected="true" >${status.value}</option>
                                                        </c:if>
                                                        <c:if test="${creditScoreBean.creditScoreUser!=status.key}">
                                                            <option value="${status.key}">${status.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

<!--                                        <tr>
                                            <td style="width: 200px;">Application ID</td>
                                            <td><input type="text" name="applicationID"  maxlength="16" value="${creditScoreBean.applicationID}"></td>


                                        </tr>-->

                                        <tr>
                                            <td style="width: 200px;">Credit Score Date : From</td>
                                            <td><input  name="fromDate" readonly maxlength="16" value="${creditScoreBean.fromDate}" key="fromDate" id="fromDate"  />
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

                                            <td style="width: 200px;">Credit Score Date : To</td>
                                            <td><input  name="toDate" readonly maxlength="16" value="${creditScoreBean.toDate}" key="toDate" id="toDate"  />
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
                                                <input type="button" name="search" value="Search" onclick="invokeSearch()" style="width: 100px;"/>&nbsp;
<!--                                                <input type="button" onclick="invokeReset(this.form)" name="next" value="Reset" style="width: 100px;"/>&nbsp;-->
                                                <input type="button" onclick="invokeReset()" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                <input type="button" value="Generate Report" name="pdf" style="width: 100px" onclick="invokeViewPdf()"/>
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
                                            <th>Total Credit Score</th>
                                            <th>Credit Score User</th>
                                            <th>Credit Score Date</th>

                                            <th>View</th>
                                            <th>History</th>

                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="credit" items="${creditScoreList}">
                                            <tr>
                                                <td >${credit.applicationID}</td>
                                                <td >${credit.totalCreditScore}</td>
                                                <td >${credit.creditScoreUser}</td>
                                                <td >${credit.creditScoredDate}</td>


                                                <td  ><a href='#' onclick="invokeView('${credit.applicationID}')">View</a></td>
                                                <td  ><a href='#' onclick="invokeHistory('${credit.applicationID}')">History</a></td>

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
