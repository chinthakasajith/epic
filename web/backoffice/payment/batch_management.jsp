<%-- 
    Document   : batch_management
    Created on : Apr 4, 2013, 10:31:00 AM
    Author     : ruwan_e
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script type="text/javascript">
            var oTable;
            $(document).ready(function() {
                //load table
                oTable = $('#jsontable').dataTable({
                    
                    "bProcessing": true,
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true
                });
                
            
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
                   
                    document.getElementById('errorMsg').innerHTML = '';
                    document.getElementById('successMsg').innerHTML = '';
                    
                    $( "#fromDate" ).innerHTML='';
                    $( "#toDate" ).innerHTML='';
                }
               
        </script>
        
        <script>
                    
           function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BATCH_MANAGEMENT%>'                                  
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



                <div class="content" >
                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  ------------------------------------->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                            <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <form method="POST" action="${pageContext.request.contextPath}/SearchBatchesServlet">
                                    <table border="0">
                                        <tbody>
                                            <tr>
                                                <td style="width: 200px;">Batch ID : </td>
                                                <td>
                                                    <input type="text" name="batch_id" value="${batch_id}" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Branch : </td>
                                                <td>
                                                    <select name="branch">
                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="branch" items="${branches}">
                                                            <option value="${branch.key}" <c:if test="${branch.key==branch}">
                                                                    selected
                                                                    </c:if>>${branch.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Username : </td>
                                                <td>
                                                    <select name="username">
                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="username" items="${users}">
                                                            <option value="${username}">${username}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Status : </td>
                                                <td>
                                                    <select name="status">
                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="status" items="${batchManagenentStatus}">

                                                            <option value="${status.key}"<c:if test="${status.key==status}">
                                                                    selected
                                                                    </c:if>>${status.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Payment Date : From</td>
                                                <td><input  name="fromDate" readonly maxlength="16" value="${fromDate}" key="fromDate" id="fromDate"  />
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

                                                <td style="width: 200px;">Payment Date : To</td>
                                                <td><input  name="toDate" readonly maxlength="16" value="${toDate}" key="toDate" id="toDate"  />
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

                                            <tr style="height: 12px;"> </tr>
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td> <input type="submit" value="Search" style="width: 100px"/><input onclick="window.location = '${pageContext.request.contextPath}/LoadBatchManagementServlet'" type="reset" value="Reset" style="width: 100px"/></td>

                                            </tr>
                                        </tbody>
                                    </table>


                                </form>

                                <br>                                

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Batch Id</th>
                                            <th>Batch Created Date</th>
                                            <th>Batch Closed Date</th>
                                            <th>Created User</th>
                                            <th>IP</th>
                                            <th>Status</th>
                                            <th>Branch</th>
                                            <th>Total Transaction Count</th>
                                            <th>Total Transaction Amount</th>
                                            <th>Created Date</th>
                                            <th>View</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="batch" items="${allBatches}">                                         
                                            <tr>
                                                <td>${batch.batchId}</td>
                                                <td>${batch.batchCreatedDate}</td>
                                                <td>${batch.batchClosedDate}</td>
                                                <td>${batch.createdUser}</td>
                                                <td>${batch.ip}</td>
                                                <td>${batch.status}</td>
                                                <td>${batch.branch}</td>
                                                <td>${batch.totalTxnCount}</td>
                                                <td>${batch.totalTxnAmount}</td>
                                                <td>${batch.createdDate}</td>
                                                <td><a href="<%=request.getContextPath()%>/ViewBatchServlet?id=${batch.batchId}">view</a></td>
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

