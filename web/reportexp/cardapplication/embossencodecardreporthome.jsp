<%-- 
    Document   : embossencodecardreporthome
    Created on : Dec 3, 2012, 10:23:11 AM
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

        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.EMBOSS_ENCODE_CD%>'
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

        <script language="javascript">
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadEmbossAndEncodeCardReportServlet";

            }
            function invokeViewPdf() {

                document.searchform.action = "${pageContext.request.contextPath}/ApplicationEmboseEncodeReportPdfServlet";
                document.searchform.submit();
            }
            function invokeSearch()
            {

                document.searchform.action = "${pageContext.request.contextPath}/SearchEmbossAndEncodeCardReportServlet";
                document.searchform.submit();

            }

            function invokeView(value) {

                $.post("${pageContext.request.contextPath}/ViewEmbossAndEncodeCardReportServlet", {id: value},
                function (data) {
                    if (data == "success") {

                        $.colorbox({href: "${pageContext.request.contextPath}/reportexp/cardapplication/viewembossencodecardreport.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }

                                });
                            }

                            function loadCdProduct(value)
                            {

                                if ($("#domain").val() != '' && $("#type").val() != '') {

                                    document.searchform.action = "${pageContext.request.contextPath}/ChangeCardProductsServlet?report=" + value;
                                    document.searchform.submit();

                                } else if ($("#domain").val() == null || $("#type").val() == null) {

                                    alert("You should select both");
                                }

                            }




        </script>

        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.EMBOSS_ENCODE_CD%>'
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

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <form method="POST" action="" name="searchform">

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr>
                                                    <td>NIC</td>
                                                    <td><input type="text" name="nic" value="${appBean.nic}" maxlength="10" /></td>
                                                <td style="width: 30px"></td>  
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
                                            </tr>
                                            <tr>
                                                <td>Passport</td>
                                                <td><input type="text" name="passport" value="${appBean.passport}" maxlength="16"/></td> 
                                                <td style="width: 30px"></td>
                                                <td>User</td>
                                                <td><select  name="user" id="carddomain" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>  
                                                        <c:forEach var="user" items="${embossUser}">
                                                            <c:if test="${appBean.user==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.user!=user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <!--                                                <td>Driving License</td>
                                                                                                <td><input type="text" name="drivinglicense" value="${appBean.licence}" maxlength="16"/></td>
                                                                                                <td style="width: 30px"></td>-->
                                                <td>Card Number</td>
                                                <td><input type="text" name="cardnumber" value="${appBean.cardNo}" maxlength="20"/></td>
                                                <td style="width: 30px"></td>

                                                <td>Priority Level</td>
                                                <td><select style="width: 163px;" name="priority">
                                                        <option value="">--------------ALL--------------</option>  
                                                        <c:forEach var="priority" items="${sessionScope.SessionObject.priorityLevelList}">
                                                            <c:if test="${appBean.priorityLevelCode==priority.key}">
                                                                <option value="${priority.key}" selected>${priority.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.priorityLevelCode != priority.key}">
                                                                <option value="${priority.key}" >${priority.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>Application ID</td>
                                                <td><input type="text" name="appId" value="${appBean.applicationId}" maxlength="12"/></td>
                                                <td style="width: 30px"></td>
                                                <td>Application Domain</td>
                                                <td><select  name="domain" id="domain" onchange="loadCdProduct('ENCODE')" style="width: 163px">
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
                                            </tr>
                                            <tr>

                                                <td>Card Product</td>
                                                <td><select  name="product" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>  
                                                        <c:forEach var="product" items="${cardProductList}">
                                                            <c:if test="${appBean.productCode==product.key}">
                                                                <option value="${product.key}" selected>${product.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.productCode!=product.key}">
                                                                <option value="${product.key}" >${product.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                                <td>Card Type</td>
                                                <td><select  name="type" id="type" onchange="loadCdProduct('ENCODE')" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>  
                                                        <c:forEach var="type" items="${cardTypeList}">
                                                            <c:if test="${appBean.typeCode==type.key}">
                                                                <option value="${type.key}" selected>${type.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.typeCode!=type.key}">
                                                                <option value="${type.key}" >${type.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </tr>
                                            <tr><td></td>
                                                <td></td>
                                                <td style="width: 30px"></td>
                                                <!--                                                <td>Card Product</td>
                                                                                                <td><select  name="product" style="width: 163px">
                                                                                                        <option value="">--------------ALL--------------</option>  
                                                <c:forEach var="product" items="${cardProductList}">
                                                    <c:if test="${appBean.productCode==product.key}">
                                                        <option value="${product.key}" selected>${product.value}</option>
                                                    </c:if>
                                                    <c:if test="${appBean.productCode!=product.key}">
                                                        <option value="${product.key}" >${product.value}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td></td>-->
                                            </tr>
                                            <tr>
                                                <td >From Date</td>
                                                <td>
                                                    <input  name="fromdate" maxlength="16" readonly value="${appBean.fromDate}" key="fromdate" id="fromdate"  />
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
                                                <td style="width: 30px"></td>
                                                <td >To Date</td>
                                                <td>
                                                    <input  name="todate" maxlength="16" readonly value="${appBean.toDate}" key="todate" id="todate"  />
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
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()"/>
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/>
                                                    <input type="button" value="View Pdf" name="pdf" style="width: 100px" onclick="invokeViewPdf()"/>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>

                                <table  border="1"  class="display" id="tableview" >
                                    <thead>
                                        <tr>
                                            <th>Card Number</th>
                                            <th>Application Id</th>
                                            <th>Card Type</th>                                            
                                            <th>Card Product</th>  
                                            <th>Identification Type</th>
                                            <th>Identification No</th> 
                                            <th>Emboss Date</th> 
                                            <th>Emboss User</th>
                                            <th>View</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="app" items="${searchList}">
                                            <tr>
                                                <td >${app.cardNo}</td>
                                                <td >${app.applicationId}</td>
                                                <td >${app.typeDes}</td>                                                
                                                <td >${app.productDes}</td>
                                                <td >${app.idTypeDes}</td>
                                                <td >${app.idNo}</td>                                                
                                                <td >${app.embossDate}</td>
                                                <td >${app.user}</td>

                                                <td ><a  href='#' onclick="invokeView('${app.applicationId}')">View</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
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