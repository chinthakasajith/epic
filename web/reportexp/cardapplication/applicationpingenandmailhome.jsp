<%-- 
    Document   : pingenandmailreport
    Created on : Nov 29, 2012, 5:18:06 PM
    Author     : jeevan
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
            function invokeReset(ele) {
                tags = ele.getElementsByTagName('input');
                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                    }
                }

                tags = ele.getElementsByTagName('label');
                for (i = 0; i < tags.length; i++) {
                    tags[i].innerText = '';
                }

                tags = ele.getElementsByTagName('select');
                for (i = 0; i < tags.length; i++) {
                    if (tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for (j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }

            }

            function invokeSearch()
            {

                document.searchform.action = "${pageContext.request.contextPath}/SearchApplicationPinGenAndMailServlet";
                document.searchform.submit();

            }
            function invokeApplicationPinGenAndMailDetailsReport() {
                document.searchform.action="${pageContext.request.contextPath}/LoadPinGenAndMailReportServlet";
                document.searchform.submit();
            }

            function LoadCardProducts() {

                $('#cardProductsId').empty();
                var sval = $("#cardTypeId").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadCardProductForReplortsServlet",
                        {
                            cardType: sval

                        },
                function (jsonobject) {
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#cardProductsId').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
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

                                <table class="tit"> <tr> <td   class="center">PIN GENERATION AND MAILING REPORT</td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchform">

                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr>    
                                                <td>Card Number</td>
                                                <td><input type="text" name="cardNo" value="${appBean.cardNo}" maxlength="16"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>


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
                                                <td>NIC</td>
                                                <td><input type="text" name="nic" value="${appBean.nic}" maxlength="16" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>



                                                <td style="width: 150px;">Card Type</td>
                                                <td>
                                                    <select name="card_ty" style="width: 163px" id="cardTypeId" onchange="">
                                                        <option value="">--------------ALL--------------</option>                                                                
                                                        <c:forEach var="catype" items="${cardTypeList}">
                                                            <c:if test="${appBean.cardType == catype.key}">
                                                                <option value="${catype.key}" selected="true">${catype.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.cardType != catype.key}">
                                                                <option value="${catype.key}" >${catype.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td>Passport</td>
                                                <td><input type="text" name="passport" value="${appBean.passport}" maxlength="16"/></td>


                                                <td style="width: 150px;">Card Product</td>
                                                <td>
                                                    <select style="width: 163px" name="cardProduct" id="cardProductsId">
                                                        <option value="" >--------------ALL--------------</option>

                                                        <c:forEach var="cp" items="${bulkCProductList}">
                                                            <c:if test="${appBean.cardProduct==cp.productCode}">
                                                                <option value="${cp.productCode}" selected>${cp.description}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.cardProduct != cp.productCode}">
                                                                <option value="${cp.productCode}" >${cp.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td></td>


                                            </tr>
                                            <tr>
                                                <!--                                                <td>Driving License</td>
                                                                                                <td><input type="text" name="licence" value="${appBean.licence}" maxlength="16"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>-->

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

                                                <td style="width: 150px;">Pin Method</td>
                                                <td>  
                                                    <select name="card_pin" style="width: 163px">
                                                        <option value="">--------------ALL--------------</option>                                                                
                                                        <c:forEach var="card_pin" items="${bnkCardPinMethodList}">
                                                            <c:if test="${appBean.pinMethod == card_pin.key}">
                                                                <option value="${card_pin.key}" selected="true">${card_pin.value}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.pinMethod != card_pin.key}">
                                                                <option value="${card_pin.key}" >${card_pin.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td></td>


                                            </tr>
                                            <tr>

                                                <td style="width: 150px;">Pin Generated User</td>

                                                <td>
                                                    <select style="width: 163px"  name="generateduser">
                                                        <option value="" >--------------ALL--------------</option>

                                                        <c:forEach var="user" items="${usersList}">
                                                            <c:if test="${appBean.user==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${appBean.user!=user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                </td>

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
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                </td>
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


                                        <td></td>
                                        </tr>

                                        <tr>
                                            <td></td>
                                            <td>
                                                <input type="button" value="Search" name="search" style="width: 100px" onclick="invokeSearch()"/>
                                                <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset(this.form)"/>
<!--                                                <input type="button" value="View report" name="viewReport" style="width: 100px" onclick="invokeApplicationPinGenAndMailDetailsReport()"/></td>-->
                                        </tr>
                                        </tbody>
                                    </table>
                                </form>

                                <table  border="1"  class="display" id="tableview" >
                                    <thead>
                                        <tr>

                                            <th>Card No</th>
                                            <th>Card Type</th>
                                            <th>Card Product</th>
                                            <th>Branch</th>
                                            <th>Pin Method</th>
                                            <th>Priority Level</th>
                                            <th>Generated User</th>
                                            <th>Pin Generated Time</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="app" items="${searchList}">
                                            <tr>

                                                <td >${app.cardNo}</td>
                                                <td >${app.cardTypeDes}</td>
                                                <td >${app.cardProductDes}</td>
                                                <td >${app.branchName}</td>
                                                <td >${app.pinMethod}</td>
                                                <td >${app.priorityLevelDes}</td>
                                                <td >${app.user}</td>
                                                <td >${app.pinGeneratedDateTime}</td>

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



