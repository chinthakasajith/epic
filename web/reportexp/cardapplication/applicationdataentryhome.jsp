<%-- 
    Document   : applicationdataentryhome
    Created on : Nov 23, 2012, 8:13:28 AM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            function invokeReset(ele){
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'password':
                            tags[i].value = '';
                            break;                
                    }
                }
                
                tags = ele.getElementsByTagName('label');
                for(i = 0; i < tags.length; i++) {                    
                    tags[i].innerText = '';                    
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
                    }
                }               
                
            }

            function invokeSearch()
            {

                document.searchform.action="${pageContext.request.contextPath}/SearchApplicationDataEntryServlet";
                document.searchform.submit();

            }            
            
            function invokeViewPdf(){
                document.searchform.action="${pageContext.request.contextPath}/ApplicationDataEntryReportPdfServlet";
                document.searchform.submit();            
            }
            
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadApplicationDataEntryServlet";
            } 
            
            function invokeCreate(){
                document.searchterminalform.action="${pageContext.request.contextPath}/LoadCreateTerminaDataServlet";
                document.searchterminalform.submit();
            }
            
            function deleteTerminal(value,status){
             
                answer = confirm("Do you really want to delete "+value+" Terminal?")
                   
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteTerminalDataCaptureServlet?id="+value+"&status="+status;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/SearchTerminalDataCaptureServlet?isBack="+"back";
                }

            }


    

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.APPLICATION_DATA_ENTRY%>'                                  
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

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>
                <div class="content" style="height: 500px">
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> ${errorMsg}</font> </td>
                                            <td><font color="green"> ${successMsg}</font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>

                                            <tr>
                                                <td style="width: 150px;">Branch</td>
                                                <td>
                                                    <select name="branch" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>                                                                
                                                        <c:forEach var="branch" items="${bnkBranchList}">
                                                            <c:if test="${appBean.branchCode == branch.key}">
                                                                <option value="${branch.key}" selected="true">${branch.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.branchCode != branch.key}">
                                                                <option value="${branch.key}" >${branch.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td>Application Domain</td>
                                                <td><select  name="domain" id="carddomain" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>  
                                                        <c:forEach var="domain" items="${cardDomainList}">
                                                            <c:if test="${appBean.domainCode==domain.key}">
                                                                <option value="${domain.key}" selected>${domain.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.domainCode!=domain.key}">
                                                                <option value="${domain.key}" >${domain.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td >Data Capturing User</td>
                                                <td><select name="user" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>  
                                                        <c:forEach var="user" items="${userList}">
                                                            <c:if test="${appBean.user==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.user != user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td >From Date</td>
                                                <td>
                                                    <input  name="fromdate" maxlength="16" readonly value="${appBean.fromDate}" key="fromdate" id="fromdate"  />
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
                                                <td >To Date</td>
                                                <td>
                                                    <input  name="todate" maxlength="16" readonly value="${appBean.toDate}" key="todate" id="todate"  />
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
                                                <td></td>
                                                <td colspan="2">
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()"/>
<!--                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset(this.form)"/>-->
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/>
                                                    <input type="button" value="Generate Report" name="pdf" style="width: 100px" onclick="invokeViewPdf()"/></td>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>                                    
                                </form>

                                <table  border="1"  class="display" id="tableview" >
                                    <thead>
                                        <tr>
                                            <th>Branch</th>
                                            <th>Application Domain</th>
                                            <th>Data Capturing User</th>                                                                                           
                                            <th>Total</th>                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="app" items="${searchList}">
                                            <tr>
                                                <td >${app.branchName}</td>
                                                <td >${app.domainDes}</td>
                                                <td >${app.user}</td>                                                    
                                                <td >${app.total}</td>   
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>



                                <%-- -------------------------add form end -------------------------------- --%>



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



