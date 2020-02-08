<%-- 
    Document   : numbergenerationreport
    Created on : Nov 29, 2012, 5:17:46 PM
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
                window.location = "${pageContext.request.contextPath}/LoadNumberGenerationReportServlet";
            } 
            function invokeSearch()
            {

                document.searchnumbergenerationform.action="${pageContext.request.contextPath}/SearchNumberGenerationReportServlet";
                document.searchnumbergenerationform.submit();

            }           

            function invokeVerificationReportDetails(){
                var cardType = $("#cardTypeId").val();
               
                
                if($.trim(cardType) != ""){
                      
                    //  var newWin = window.open('${pageContext.request.contextPath}/ViewNumberGenerationReportServlet?cardType='+cardType, "TxnReport","height=768,width=1024,resizable=yes,scrollbars=yes");
               
                    document.searchnumbergenerationform.action = "${pageContext.request.contextPath}/ViewNumberGenerationReportServlet?cardType="+cardType;
                }else{
                    //  var newWin = window.open('${pageContext.request.contextPath}/ViewNumberGenerationReportServlet', "TxnReport","height=768,width=1024,resizable=yes,scrollbars=yes");
               
                    document.searchnumbergenerationform.action = "${pageContext.request.contextPath}/ViewNumberGenerationReportServlet";                    
                }
                //  var form = window.opener.document.getElementById("searchnumbergenerationformid");
                document.searchnumbergenerationform.submit();

            }
         
            function cardTypechange(){
                
                var cardType = $("#cardTypeId").val();
                if($.trim(cardType) != ""){               
                    document.searchnumbergenerationform.action="${pageContext.request.contextPath}/SearchNumberGenerationReportServlet?cardType="+cardType;
                }else{
                    document.searchnumbergenerationform.action="${pageContext.request.contextPath}/SearchNumberGenerationReportServlet";                   
                }
                document.searchnumbergenerationform.submit();
            };
         
         
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.NUMBER_GENERATION_RPT%>'                                  
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

                                <form method="POST" action="" name="searchnumbergenerationform" id="searchnumbergenerationformid">

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
                                                    <c:forEach  var="branch" items="${branchListMap}">
                                                        <option value="${branch.key}" <c:if test="${branch.key == searchbean.branch}"> selected="true" </c:if> >${branch.value}</option>
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
                                            <td>Application Id </td>
                                            <td></td>
                                            <td><input type="text" name="applicationId" value="${searchbean.applicationId}"/></td>
                                            <td>Card Product</td>
                                            <td></td>
                                            <td>
                                                <select name="cardProduct" id="cardProductId">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="numberGenerationBean" items="${cardProductList}">
                                                        <option value="${numberGenerationBean.cardProduct}" <c:if test="${numberGenerationBean.cardProduct == searchbean.cardProduct}"> selected="true" </c:if> >${numberGenerationBean.cardProductDesc}</option>
                                                    </c:forEach>
                                                </select>    
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Card Number </td>
                                            <td></td>
                                            <td><input type="text" name="cardNumber" value="${searchbean.cardNumber}"/></td>
                                            <td>User</td>
                                            <td></td>
                                            <td>
                                                <select name="users">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="users" items="${userList}">
                                                        <option value="${users}" <c:if test="${users == searchbean.user}"> selected="true" </c:if> >${users}</option>
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
                                            <td>Card Type</td>
                                            <td></td>
                                            <td>
                                                <select name="cardType" id="cardTypeId" onchange="cardTypechange()">
                                                    <option value="" >--All--</option>
                                                    <c:forEach  var="cardTypes" items="${cardTypesMap}">
                                                        <option value="${cardTypes.key}" <c:if test="${cardTypes.key == searchbean.cardType}"> selected="true" </c:if> >${cardTypes.value}</option>
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
                                                <input  name="todate" readonly maxlength="16" value="${searchbean.todate}" key="todate" id="todate"  />
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


                                    <table  border="1"  class="display" id="scoreltableview4">
                                        <thead>
                                            <tr>
                                                <th>Card Number</th>
                                                <th>Card Status</th>
                                                <th>Card Type</th>
                                                <th>Card Domain</th>
                                                <th>Card Product</th>
                                                <th>Expiry date</th>
                                                <th>Application Id</th>
                                                <th>Id Type</th>
                                                <th>Id Number</th>
                                                <th>Number Generated User</th>
                                                <th>Number Generated Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="numbergenerationreport" items="${searchBeanList}">
                                                <tr>
                                                    <td >${numbergenerationreport.cardNumber}</td>
                                                    <td >${numbergenerationreport.cardStatusDes}</td>
                                                    <td >${numbergenerationreport.cardTypeDes}</td>
                                                    <td >${numbergenerationreport.cardDomainDes}</td>
                                                    <td >${numbergenerationreport.cardProductDesc}</td>                                                     
                                                    <td >${numbergenerationreport.expiryDate}</td> 
                                                    <td >${numbergenerationreport.applicationId}</td> 
                                                    <td >${numbergenerationreport.idTypeDes}</td> 
                                                    <td >${numbergenerationreport.idNumber}</td> 
                                                    <td >${numbergenerationreport.user}</td> 
                                                    <td >${numbergenerationreport.ganeratedDate}</td> 


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