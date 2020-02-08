<%-- 
    Document   : eodtransactionreport
    Created on : Dec 4, 2012, 6:27:28 PM
    Author     : asela
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script type="text/javascript" > 
          
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadEodTransactionReportServlet";
            } 
            function invokeSearch()
            {

                document.searchnumbergenerationform.action="${pageContext.request.contextPath}/SearchEodTransactionReportServlet";
                document.searchnumbergenerationform.submit();

            }           

            function invokeVerificationReportDetails(){
                document.searchnumbergenerationform.action = "${pageContext.request.contextPath}/ViewEodTransactionReportServlet";                    
                document.searchnumbergenerationform.submit();
            }
         
         
         
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.EOD_TRANSACTION_RPT%>'                                  
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

                                <form method="POST" action="" name="searchnumbergenerationform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> <b>${errorMsg}</b></font> </td>
                                            <td><font color="green"><b> ${successMsg}</b></font> </td>
                                            <td></td>

                                        </tr>
                                    </table>


                                    <table cellpadding="0" cellspacing="10">
                                        <tr>          
                                            <td style="width: 180px">EOD Date </td>
                                            <td></td>
                                            <td>
                                                <input  name="eoddate" readonly maxlength="16" value="${searchbean.eodDate}" key="eoddate" id="eoddate"  />
                                                <script type="text/javascript">
                                                    $(function() {
                                                        $( "#eoddate" ).datepicker({
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


                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Transaction Id</th>
                                                <th>Card Number</th>
                                                <th>From Account Number</th>
                                                <th>To Account Number</th>
                                                <th>MID</th>
                                                <th>TID</th>
                                                <th>Credit/Debit</th>
                                                <th>Transaction Type</th>
                                                <th>Currency Type</th>
                                                <th>Transaction Amount</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="bean" items="${searchList}">
                                                <tr>
                                                    <td >${bean.transactionId}</td>
                                                    <td >${bean.cardNumber}</td>
                                                    <td >${bean.fromAccountNumber}</td>
                                                    <td >${bean.toAccountNumber}</td>
                                                    <td >${bean.mid}</td>                                                     
                                                    <td >${bean.tid}</td> 
                                                    <td >${bean.crdr}</td> 
                                                    <td >${bean.transactionType}</td> 
                                                    <td >${bean.currencyType}</td> 
                                                    <td >${bean.transactionAmount}</td> 
                                                </tr>
                                            </c:forEach>
                                            <c:forEach var="map" items="${totalCountMap}">
                                                <tr>      
                                                    <c:if test="${map.key == 0}">  
                                                        <td></td>
                                                    </c:if> 
                                                    <c:if test="${map.key != 0}">  
                                                        <td  style="font-weight: bold ;">Total Count</td>
                                                    </c:if>                                                        
                                                    <td >&nbsp;</td>
                                                    <td >&nbsp;</td>
                                                    <td >&nbsp;</td>
                                                    <td >&nbsp;</td>                                                     
                                                    <td >&nbsp;</td> 
                                                    <td >&nbsp;</td> 
                                                    <c:if test="${map.key != 0}">  
                                                        <td>${map.key}</td>
                                                    </c:if>
                                                    <c:if test="${map.key == 0}">  
                                                        <td></td>
                                                    </c:if>                                                       
                                                    <c:if test="${map.value == null}">  
                                                        <td></td>
                                                    </c:if>
                                                    <c:if test="${map.value != null}">  
                                                        <td>Amount</td>
                                                    </c:if>
                                                    <c:if test="${map.value == null}">  
                                                        <td></td>
                                                    </c:if>
                                                    <c:if test="${map.value != null}">  
                                                        <td>${map.value}</td>
                                                    </c:if>
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