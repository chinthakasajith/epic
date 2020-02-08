<%-- 
    Document   : callhistoryhome
    Created on : Jul 20, 2012, 1:45:23 PM
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

                document.searchcallhistoryform.action="${pageContext.request.contextPath}/SearchCallHistoryServlet";
                document.searchcallhistoryform.submit();

            }
            

            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCallHistorySearchServlet";

            }
            
            function invokeCallHistory(value){

                $.post("${pageContext.request.contextPath}/ViewCallHistoryServlet", {logId:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/reportexp/callhistory/callhistoryview.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }

                });
            }


        </script>
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALL_CENTER_HISTORY%>'                                  
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


        <title>EPIC CMS CALL CENTER HISTORY HOME</title>
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




                                <form method="POST" action="" name="searchcallhistoryform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                            <td></td>
                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td style="width: 180px">Caller type</td>
                                                <td><input type="text"  value="${searchhistorybean.callType}" name="callertype" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  
                                            <tr>
                                                <td style="width: 180px">Card Number</td>
                                                <td><input type="text"  value="${searchhistorybean.cardNumber}" name="cardno" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Application ID</td>
                                                <td><input type="text"  value="${searchhistorybean.applicationId}" name="applicationid" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px"> Merchant ID</td>
                                                <td><input type="text"  value="${searchhistorybean.mId}" name="merchantid" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  


                                            <tr>
                                                <td style="width: 180px"> Reference Number</td>
                                                <td><input type="text"  value="${searchhistorybean.referenceNo}" name="referenceno" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  

                                            <tr>
                                                <td style="width: 180px"> Terminal ID</td>
                                                <td><input type="text"  value="${searchhistorybean.tId}" name="terminalid" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     


                                            <tr>
                                                <td style="width: 180px"> Customer ID</td>
                                                <td><input type="text"  value="${searchhistorybean.customerId}" name="customerno" maxlength="16"></td>
                                                <td></td>
                                            </tr>


                                            <tr><td style="height: 5px"></td></tr>     


                                            <tr>
                                                <td style="width: 180px"> Account Number</td>
                                                <td><input type="text"  value="${searchhistorybean.accountNumber}" name="accountno" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>


                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td style="width: 180px"></td>
                                                <td style="width: 300px"><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CALL_CENTER_HISTORY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>


                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Caller Type</th>
                                                <th>Card Number</th>
                                                <th>Application ID</th>
                                                <th>Merchant ID</th>
                                                <th>Terminal ID</th>
                                                <th>Customer Id</th>
                                                <th>Account Number</th>
                                                <th>Reference Number</th>
                                                <th>View</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="history" items="${requestScope.callHistoryBeanList}">
                                                <tr>
                                                    <td >${history.callType}</td>
                                                    <td >${history.cardNumber}</td>
                                                    <td >${history.applicationId}</td>
                                                    <td >${history.mId}</td>
                                                    <td >${history.tId}</td>
                                                    <td >${history.customerId}</td>
                                                    <td >${history.accountNumber}</td>
                                                    <td >${history.referenceNo}</td>
                                                    <td ><a href="#" onclick="invokeCallHistory('${history.callLogId}')">View</a></td>
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


