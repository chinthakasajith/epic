<%-- 
    Document   : carddistributionreportservlet
    Created on : Dec 3, 2012, 11:02:31 AM
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
                
                searchCardDistribution.elements["fromDate"].value = today;
                searchCardDistribution.elements["toDate"].value = today;
                
                
            }
            
            function onchangeCardProduct(){
                $('#cardProduct').empty();
                var sval=$("#cardType").val();
                $.getJSON("${pageContext.servletContext.contextPath}/OnchangeCardProductServlet", 
                { key : sval},
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#cardProduct').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
            
            function invokeSearch()
            {
              
                document.searchCardDistribution.action="${pageContext.request.contextPath}/SearchCardDistributionReportServlet";
                document.searchCardDistribution.submit();
                
            }
            
            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadCardDistributionReportServlet";
            } 
            
            function invokeGenerateReport()
            {
              
                //                document.searchApplicationSummery.action="${pageContext.request.contextPath}/GenerateReportApplicationReportServlet";
                //                document.searchApplicationSummery.submit();
                document.searchCardDistribution.action="${pageContext.request.contextPath}/GenerateReportApplicationReportServlet";
                document.searchCardDistribution.submit();
                
            }
            
            
            function invokeView(applicationID){
            
                window.location="${pageContext.request.contextPath}/ViewApplicationDetailsServlet?applicationID="+applicationID;
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_DISTRIBUTION_REPORT%>'                                  
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

                                <form name="searchCardDistribution" method="POST" action="">

                                    <table cellpadding="0" cellspacing="10"   >

                                        <tr>
                                            <td style="width: 200px;">NIC</td>
                                            <td><input type="text" name="nic"  maxlength="16" value="${distributeBean.nic}"></td>

                                            <td style="width: 200px;">Branch</td>
                                            <td><select  name="branch"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="branch" items="${branchList}">
                                                        <c:if test="${distributeBean.branch==branch.key}">
                                                            <option value="${branch.key}" selected="true" >${branch.value}</option>
                                                        </c:if>
                                                        <c:if test="${distributeBean.branch!=branch.key}">
                                                            <option value="${branch.key}">${branch.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Passport</td>
                                            <td><input type="text" name="passport"  maxlength="16" value="${distributeBean.passport}"></td>

                                            <td style="width: 200px;">Priority Level</td>
                                            <td><select  name="priorityLevel"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="lvl" items="${priorityLevelList}">
                                                        <c:if test="${distributeBean.priorityLevel==lvl.key}">
                                                            <option value="${lvl.key}" selected="true" >${lvl.value}</option>
                                                        </c:if>
                                                        <c:if test="${distributeBean.priorityLevel!=lvl.key}">
                                                            <option value="${lvl.key}">${lvl.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

<!--                                        <tr>
                                            <td style="width: 200px;">Driving Licence</td>
                                            <td><input type="text" name="drivingLicence"  maxlength="16" value="${distributeBean.drivingLicence}"></td>

                                            
                                        </tr>-->

                                        <tr>
                                            <td style="width: 200px;">Application ID</td>
                                            <td><input type="text" name="applicationID"  maxlength="16" value="${distributeBean.applicationID}"></td>

                                            <td style="width: 200px;">Distributed User</td>
                                            <td><select  name="distributedUser"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="status" items="${csUserList}">
                                                        <c:if test="${distributeBean.distributedUser==status.key}">
                                                            <option value="${status.key}" selected="true" >${status.value}</option>
                                                        </c:if>
                                                        <c:if test="${distributeBean.distributedUser!=status.key}">
                                                            <option value="${status.key}">${status.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>

                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Card Number</td>
                                            <td><input type="text" name="cardNumber"  maxlength="16" value="${distributeBean.cardNumber}"></td>
                                            <td style="width: 200px;">Card Domain</td>
                                            <td><select  name="domain"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="domain" items="${domainList}">
                                                        <c:if test="${distributeBean.domain==domain.key}">
                                                            <option value="${domain.key}" selected="true" >${domain.value}</option>
                                                        </c:if>
                                                        <c:if test="${distributeBean.domain!=domain.key}">
                                                            <option value="${domain.key}">${domain.value}</option>
                                                        </c:if>
                                                    </c:forEach>

                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">From Date</td>
                                            <td><input  name="fromDate" readonly maxlength="16" value="${distributeBean.fromDate}" key="fromDate" id="fromDate"  />
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
                                            <td><input  name="toDate" readonly maxlength="16" value="${distributeBean.toDate}" key="toDate" id="toDate"  />
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

                                            <td style="width: 200px;">Card Type</td>
                                            <td><select  name="cardType" onchange="onchangeCardProduct()" id="cardType" >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="type" items="${cardTypeList}">
                                                        <c:if test="${distributeBean.cardType==type.key}">
                                                            <option value="${type.key}" selected="true" >${type.value}</option>
                                                        </c:if>
                                                        <c:if test="${distributeBean.cardType!=type.key}">
                                                            <option value="${type.key}">${type.value}</option>
                                                        </c:if>
                                                    </c:forEach>

                                                </select>
                                            </td>

                                            <td style="width: 200px;">Card Product</td>
                                            <td><select  name="cardProduct" id="cardProduct"  >
                                                    <option value="" selected>--ALL--</option>
                                                    <c:forEach var="product" items="${cardProductList}">
                                                        <c:if test="${distributeBean.cardProduct==product.key}">
                                                            <option value="${product.key}" selected="true" >${product.value}</option>
                                                        </c:if>
                                                        <c:if test="${distributeBean.cardProduct!=product.key}">
                                                            <option value="${product.key}">${product.value}</option>
                                                        </c:if>
                                                    </c:forEach>

                                                </select>
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
                                                <input type="button" name="generate" value="Generate" onclick="invokeGenerateReport()" style="width: 100px;"/>&nbsp;
                                            </td>

                                        </tr>

                                    </table>

                                </form>
                                <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->
                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                <br></br>

                                <table  border="1"  class="display" id="scoreltableview">
                                    <thead>
                                        <tr class="gradeB">

                                            <th>Card Number</th>
                                            <th>Card Type</th>
                                            <th>Card Product</th>
                                            <th>Expiry Date</th>
                                            <th>Application ID</th>
                                            <th>Identification Type</th>
                                            <th>Identification NO </th>
                                            <th>Distributed Date</th>
                                            <th>Distributed User</th>
                                            <th>Card Status</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="distribute" items="${distributeList}">
                                            <tr>

                                                <td >${distribute.cardNumber}</td>
                                                <td >${distribute.cardTypeDes}</td>
                                                <td >${distribute.cardProductDes}</td>
                                                <td >${distribute.expiryDate}</td>
                                                <td >${distribute.applicationID}</td>
                                                <td >${distribute.identificationTypeDes}</td>
                                                <td >${distribute.identificationNo}</td>
                                                <td >${distribute.distributedDate}</td>
                                                <td >${distribute.distributedUser}</td>
                                                <td >${distribute.cardStatusDes}</td>

<!--                                                <td  ><a href='#' onclick="invokeView('${distribute.applicationID}')">View</a></td>
                                                <td  ><a href='#' onclick="invokeHistory('${distribute.applicationID}')">History</a></td>-->

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
