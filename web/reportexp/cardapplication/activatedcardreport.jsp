<%-- 
    Document   : activatedcardreport
    Created on : Dec 3, 2012, 8:40:39 AM
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

            function invokeReset() {
                window.location = "${pageContext.request.contextPath}/LoadActivatedCardReportServlet";
            }
            function invokeSearch()
            {

                document.activatedcardreport.action = "${pageContext.request.contextPath}/SearchActivatedCardReportServlet";
                document.activatedcardreport.submit();

            }

            function invokeVerificationReportDetails() {
                var cardType = $("#cardTypeId").val();
                if ($.trim(cardType) != "") {
                    document.activatedcardreport.action = "${pageContext.request.contextPath}/ViewActivatedCardReportServlet?cardType=" + cardType;
                } else {
                    document.activatedcardreport.action = "${pageContext.request.contextPath}/ViewActivatedCardReportServlet";
                }
                document.activatedcardreport.submit();
            }

            function cardTypechange() {

                var cardType = $("#cardTypeId").val();
                if ($.trim(cardType) != "") {
                    document.activatedcardreport.action = "${pageContext.request.contextPath}/SearchActivatedCardReportServlet?cardType=" + cardType;
                } else {
                    document.activatedcardreport.action = "${pageContext.request.contextPath}/SearchActivatedCardReportServlet";
                }
                document.activatedcardreport.submit();
            }
            ;


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.ACTIVATED_CARD_RPT%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

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

                                <form method="POST" action="" name="activatedcardreport">

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
                                                    $(function () {
                                                        $("#fromdate").datepicker({
                                                            showOn: "button",
                                                            buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                            changeMonth: true,
                                                            changeYear: true,
                                                            buttonImageOnly: true,
                                                            dateFormat: "yy-mm-dd",
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
                                                    $(function () {
                                                        $("#todate").datepicker({
                                                            showOn: "button",
                                                            buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                            changeMonth: true,
                                                            changeYear: true,
                                                            buttonImageOnly: true,
                                                            dateFormat: "yy-mm-dd",
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


                                    <table  border="1"  class="display" id="scoreltableview2">
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
                                            <c:forEach var="activatedcardreport" items="${searchBeanList}">
                                                <tr>
                                                    <td >${activatedcardreport.cardNumber}</td>
                                                    <td >${activatedcardreport.cardStatusDes}</td>
                                                    <td >${activatedcardreport.cardTypeDes}</td>
                                                    <td >${activatedcardreport.cardDomainDes}</td>
                                                    <td >${activatedcardreport.cardProductDesc}</td>                                                     
                                                    <td >${activatedcardreport.expiryDate}</td> 
                                                    <td >${activatedcardreport.applicationId}</td> 
                                                    <td >${activatedcardreport.idTypeDes}</td> 
                                                    <td >${activatedcardreport.idNumber}</td> 
                                                    <td >${activatedcardreport.user}</td> 
                                                    <td >${activatedcardreport.ganeratedDate}</td> 


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
