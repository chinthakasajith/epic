<%-- 
    Document   : merchantlocreporthome
    Created on : Dec 18, 2012, 5:15:56 PM
    Author     : ruwan_e
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <jsp:include page="/content.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            
            function invokeSearch()
            {
                document.searchApplicationSummery.action="${pageContext.request.contextPath}/SearchMerchantLocationReportServlet";
                document.searchApplicationSummery.submit();
                
            }
            
            function isNumeric(value) {
                if (value == null || !value.toString().match(/^[-]?\d*\.?\d*$/)) return false;
                return true;
            }
            function invokeBack()
            {    
                window.location = "${pageContext.request.contextPath}/LoadMerchantLocationReportServlet";
                   
            }
            function invokeView(value){

                $.post("${pageContext.request.contextPath}/ViewMerchantLocationReportServlet", {id:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/reportexp/merchant/merchantlocation/viewmerchantlocationreport.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
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
                { pagecode:'<%= PageVarList.MERCHANT_LOC_REPORT%>'                                  
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
        <title>MERCHANT LOCATION MANAGEMENT</title>
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

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><input type="text" name="merchantId"  maxlength="16" value="" /></td>

                                                <td style="width: 200px;">Status</td>
                                                <td><select  name="status"  >
                                                        <option value="" selected>--ALL--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">                                           
                                                            <option value="${status.statusCode}">${status.description}</option>                                      
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Customer Name</td>
                                                <td><input type="text" name="customerName"  maxlength="16" value=""></td>

                                                <td style="width: 200px;">Payment Mode</td>
                                                <td><select  name="payment_mode"  >
                                                        <option value="" selected>--ALL--</option>
                                                        <c:forEach var="payment_mode" items="${paymentModeList}">                                           
                                                            <option value="${payment_mode.paymentMode}">${payment_mode.description}</option>                                      
                                                        </c:forEach>
                                                    </select>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Terminal ID</td>
                                                <td><input type="text" name="terminalID"  maxlength="16" value=""></td>
                                                <td style="width: 200px;">Area</td>
                                                <td><select  name="area"  >
                                                        <option value="" selected>--ALL--</option>
                                                        <c:forEach var="area" items="${areaList}">                                           
                                                            <option value="${area.areaCode}">${area.description}</option>                                      
                                                        </c:forEach>
                                                    </select>
                                                    </select>
                                                </td>
                                            </tr>                                        
                                            <tr>
                                                <td style="width: 200px;">Activation Date : From</td>
                                                <td><input  name="fromDate" readonly maxlength="16" value="${summaryBean.fromDate}" key="fromDate" id="fromDate"  />
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
                                                <td><input  name="toDate" readonly maxlength="16" value="${summaryBean.toDate}" key="toDate" id="toDate"  />
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
                                    <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                    <br></br>

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Merchant ID</th>
                                                <th>Terminal ID</th>
                                                <th>Payment Mode</th>
                                                <th>Status</th>
                                                <th>Merchant Name</th>
                                                <th>City</th>
                                                <th>Activation Date</th> 
                                                <th>View</th> 
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="summery" items="${summaryList}">
                                                <tr>
                                                    <td >${summery.merchantId}</td>
                                                    <td >${summery.terminalID}</td>
                                                    <td >${summery.paymentMode}</td>
                                                    <td >${summery.status}</td>
                                                    <td >${summery.merchantName}</td>
                                                    <td >${summery.area}</td>
                                                    <td >${summery.activationDate}</td>                                             
                                                    <td><a  href='#' onclick="invokeView('${summery.merchantId}')">View</a></td>
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
