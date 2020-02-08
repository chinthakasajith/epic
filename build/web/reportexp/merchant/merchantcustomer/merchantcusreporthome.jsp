<%-- 
    Document   : merchantcusreporthome
    Created on : Dec 18, 2012, 5:15:56 PM
    Author     : admin
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
              
                document.searchMerchantCustomerReport.action="${pageContext.request.contextPath}/SearchMerchantCusReportServlet";
                document.searchMerchantCustomerReport.submit();
                
            }
            function invokeGenerateReport()
            {
              
                document.searchMerchantCustomerReport.action="${pageContext.request.contextPath}/GenerateMerchantCustomerReportServlet";
                document.searchMerchantCustomerReport.submit();
                
            }
            
        </script>
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.MERCHANT_CUS_REPORT%>'                                  
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

                                <!--  ----------------------start  developer area  -----------------------------------                      -->
                                
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                               
                               <form name="searchMerchantCustomerReport" method="POST" action="">

                                    <table cellpadding="0" cellspacing="10"   >

                                        <tr>
                                            <td style="width: 200px;">Merchant Customer ID</td>
                                            <td><input type="text" name="mcid"  maxlength="16" value="${summeryBean.merchantCusID}"></td>
                                            
                                            <td style="width: 200px;">Merchant ID(MID)</td>
                                            <td><input type="text" name="mid"  maxlength="16" value="${summeryBean.merchantID}"></td>

                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Merchant Customer Name</td>
                                            <td><input type="text" name="mcname"  maxlength="16" value="${summeryBean.merchantCusName}"></td>
                                            
                                            <td style="width: 200px;">Terminal ID(TID)</td>
                                            <td><input type="text" name="tid"  maxlength="16" value="${summeryBean.terminalID}"></td>
                                            
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Merchant Customer Area</td>
                                            <td><select  name="mcarea"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="area" items="${areaList}">
                                                        <c:if test="${summeryBean.merchantCusArea==area.key}">
                                                            <option value="${area.key}" selected="true">${area.value}</option>
                                                        </c:if>
                                                        <c:if test="${summeryBean.merchantCusArea!=area.key}">
                                                            <option value="${area.key}">${area.value}</option>
                                                        </c:if>    
                                                    </c:forEach>
                                                </select>
                                            </td>

                                            <td style="width: 200px;">Merchant Status</td>
                                            <td><select  name="merchantStatus" >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${summeryBean.merchantCusStatus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true">${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${summeryBean.merchantCusStatus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                            </c:if>        
                                                     </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Activation Date : From</td>
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

                                            <td style="width: 200px;">Activation Date : To</td>
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
                                                <input type="button" onclick="invokeReset(this.form)" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                <input type="submit" name="generate" value="Generate" onclick="invokeGenerateReport()" style="width: 100px;"/>&nbsp;
                                            </td>

                                        </tr>

                                    </table>

                                </form>
                                <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->
                                
                                
                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////---->
                                
                                <br></br>

                                <table  border="1"  class="display" id="scoreltableview3">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Customer ID</th>
                                            <th>Name</th>
                                            <th>City</th>
                                            <th>Commission Profile</th>
                                            <th>Fee Profile</th>
                                            <th>Risk Profile</th>
                                            <th>Payment Mode</th>
                                            <th>Statement Cycle</th>
                                            <th>Currency</th>
                                            <th>Activation Date</th>
                                            <th>Account No</th>
                                            <th>Status</th>
                                            <th>Bank</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="summery" items="${summeryList}">
                                            <tr>
                                                <td >${summery.merchantCusID}</td>
                                                <td >${summery.merchantCusName}</td>
                                                <td >${summery.merchantCusArea}</td>
                                                <td >${summery.commissionProfile}</td>
                                                <td >${summery.feeProfile}</td>
                                                <td >${summery.riskProfile}</td>
                                                <td >${summery.paymentMode}</td>
                                                <td >${summery.statementCycle}</td>
                                                <td >${summery.currency}</td>
                                                <td >${summery.activationDate}</td>
                                                <td >${summery.accNumber}</td>
                                                <td >${summery.merchantCusStatus}</td>
                                                <td >${summery.bankName}</td>
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
