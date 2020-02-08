<%-- 
    Document   : cardtemplatemgt
    Created on : Jan 31, 2012, 1:12:16 PM
    Author     : chanuka
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">

            function fromDateValidation(oldDate)
            {
                newdate = addcardform1.elements["validFrom"].value;
                today = new Date();
                yesterday = new Date(today.getTime() - (24 * 60 * 60 * 1000));

                if (Date.parse(newdate) < Date.parse(yesterday)) {
                    alert("Enter Valid Date");
                    addcardform1.elements["validFrom"].value = oldDate;
                }

                if (Date.parse(newdate) < Date.parse(oldDate)) {
                    alert("Enter Valid Date");
                    addcardform1.elements["validFrom"].value = oldDate;
                }
            }

            function toDateValidation(oldDate)
            {
                newdate = addcardform1.elements["validTo"].value;
                today = new Date();
                yesterday = new Date(today.getTime() - (24 * 60 * 60 * 1000));

                if (Date.parse(newdate) < Date.parse(yesterday)) {
                    alert("Enter Valid Date");
                    addcardform1.elements["validTo"].value = oldDate;
                }
                if (Date.parse(newdate) > Date.parse(oldDate)) {
                    alert("Enter Valid Date");
                    addcardform1.elements["validTo"].value = oldDate;
                }
            }

            function invokeAssignDetails()
            {
                document.addcardform1.action = "${pageContext.request.contextPath}/GetDetailsByAccountTemplateServlet?operation=add";
                document.addcardform1.submit();
            }

            function invokeAdd()
            {

                document.addcardform1.action = "${pageContext.request.contextPath}/AddCardTemplateProfileServlet";
                document.addcardform1.submit();

            }

            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardTemplateMgtServlet";

            }

            function saveAndNext()
            {

                document.addcardform1.action = "${pageContext.request.contextPath}/AddCardTemplateServlet";
                document.addcardform1.submit();

            }
            function invokeReset(ele)
            {

                tags = ele.getElementsByTagName('input');
                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;

                    }
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

                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';
            }

            function loadCurrency() {
                $('#currencytype').empty();
                var sval = $("#producttype").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadOnChangeCurrencyServlet",
                        {producttype: sval},
                function (jsonobject) {
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#currencytype').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });

            }


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CARDTEMPLATEHOME%>'
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


        <title>EPIC CMS CARD TEMPLATE MANAGEMENT</title>
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

                                <!--                                <div class="selector" id="tabs">-->
                                <!--                                    <ul>
                                                                        <li><a href="#tabs-1">General</a></li>
                                                                        <li><a href="#tabs-2">Profile</a></li>
                                                                             <li><a href="#tabs-3">Card Authentication</a></li>
                                                                                   <li><a href="#tabs-4">Embossing</a></li>        
                                                                    </ul>-->

                                <!-- -----------------------------General details start---------------------------------------->



                                <form method="POST" action="" name="addcardform1">
                                    <!--                                        <div id="tabs-1" >-->
                                    <table>
                                        <tr>
                                            <td><b><font color="Red"><label id="errorMsg"> ${errorMsg}</label></font></b> </td>
                                            <td><b><font color="green"><label id ="successMsg">${successMsg}</label></font> </b></td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>

                                            <tr>
                                                <td style="width: 200px;">Template Code</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text" name="templatecode" value="${cardBean.templateCode}" maxlength="6">
                                                </td>

                                                <td style="width: 200px;">Template Name</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text"  name="templatename" value="${cardBean.templateName}" maxlength="64">
                                                </td>
                                            </tr>   

                                            <tr>
                                                <td style="width: 200px;">Account Template Name</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  onchange=" invokeAssignDetails()" name="accounttemplatecode">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="template" items="${accountTemplateList}">
                                                            <c:if test="${cardBean.accounttemplateCode==template.key}">
                                                                <option value="${template.key}" selected>${template.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.accounttemplateCode!=template.key}">
                                                                <option value="${template.key}" >${template.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>                                                
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Valid From</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;"  name="validFrom" readonly maxlength="16" value="${cardBean.validFrom}" key="validTo" id="validFrom" onchange="fromDateValidation('${cardBean.validFrom}')" />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#validFrom").datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd",
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                            $('#validFrom').datepicker("setDate", "today");
                                                        });
                                                    </script>
                                                </td>

                                                <td style="width: 200px;">Valid To</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" name="validTo" readonly maxlength="16" value="${cardBean.validTo}" key="validTo" id="validTo" onchange="toDateValidation('${cardBean.validTo}')" />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#validTo").datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd",
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                            $('#validTo').datepicker("setDate", "today");
                                                        });
                                                    </script>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Total Credit Limit</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text" readonly name="totalcreditlimit" value="${cardBean.totalCreditLimit}" maxlength="22"></td>

                                                <td style="width: 200px;">Staff Status</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text" readonly name="staffStatus" value="${cardBean.staffStatus}" maxlength="22">
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Product Type</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="producttype" id="producttype" onchange="loadCurrency()">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="product" items="${sessionScope.SessionObject.cardProductMgtList}">
                                                            <c:if test="${cardBean.productCode==product.productCode}">
                                                                <option value="${product.productCode}" selected>${product.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.productCode!=product.productCode}">
                                                                <option value="${product.productCode}" >${product.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Card Category</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="cardCategory">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="cardcategory" items="${sessionScope.SessionObject.cardCategoryMap}">
                                                            <c:if test="${cardBean.cardCategoryCode==cardcategory.key}">
                                                                <option value="${cardcategory.key}" selected>${cardcategory.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.cardCategoryCode!=cardcategory.key}">
                                                                <option value="${cardcategory.key}" >${cardcategory.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Currency Type</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select name="currencytype" id="currencytype">
                                                        <option value="" >-----------SELECT-----------</option>

                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Fee Profile</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="feeProfileSelect">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="fee" items="${sessionScope.SessionObject.feeProfileList}">
                                                            <c:if test="${cardBean.feeProfCode==fee.feeProCode}">
                                                                <option value="${fee.feeProCode}" selected>${fee.feeProDes}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.feeProfCode!=fee.feeProCode}">
                                                                <option value="${fee.feeProCode}" >${fee.feeProDes}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Service Code</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="serviceCode">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="service" items="${sessionScope.SessionObject.serviceCodeList}">
                                                            <c:if test="${cardBean.serviceCode==service.serviceCode}">
                                                                <option value="${service.serviceCode}" selected>${service.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.serviceCode!=service.serviceCode}">
                                                                <option value="${service.serviceCode}" >${service.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Risk Profile</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="riskProfileDelect">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="risk" items="${sessionScope.SessionObject.riskProfileList}">
                                                            <c:if test="${cardBean.riskProfCode==risk.riskProfCode}">
                                                                <option value="${risk.riskProfCode}" selected>${risk.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.riskProfCode!=risk.riskProfCode}">
                                                                <option value="${risk.riskProfCode}" >${risk.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Expiry Period</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text"  name="expiryPeriod" value="${cardBean.expiryPeriod}" maxlength="10"></td>

                                                <td style="width: 200px;">Transaction Profile</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="txnProfileSelect">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="txn" items="${sessionScope.SessionObject.transactionProfileList}">
                                                            <c:if test="${cardBean.txnProfCode==txn.profileCode}">
                                                                <option value="${txn.profileCode}" selected>${txn.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.txnProfCode!=txn.profileCode}">
                                                                <option value="${txn.profileCode}" >${txn.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Renew Period</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text"  name="renewRequired" value="${cardBean.renewRequired}" maxlength="10"></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Re-Issue Threshold Period</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text"  name="reissuThrshPeriod" value="${cardBean.reissuThrshPeriod}" maxlength="10"></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Cash Advanced Rate</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input style="width: 163px;" type="text"  name="cashAdvanceRate" value="${cardBean.cashAdvanceRate}" maxlength="10"></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Status</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select  name="status">
                                                        <option value="" >-----------SELECT-----------</option>

                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${cardBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>                                               
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td><input type="button" value="Add" name="next" style="width: 100px" onclick="saveAndNext()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset(this.form)">
                                                    <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invokeCancel()">
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>

                                    <!--                                        </div>-->
                                </form>


                                <!-- -----------------------------Bank details start---------------------------------------->      
                                <!--      <div id="tabs-3">gdgd</div>
 
 
 
                                                     <div id="tabs-4"></div>             -->

                                <!-- -----------------------------Billing details end---------------------------------------->
                                <!--                                </div>-->




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




