<%-- 
    Document   : standingordertypeshome
    Created on : Jan 31, 2013, 3:05:46 PM
    Author     : asitha_l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <script type="text/javascript">

            function backToLoadUpdate(orderID) {
                document.updatestandingordertypes.action = "${pageContext.request.contextPath}/LoadUpdateStandingOrderTypeServlet?id=" + orderID;
                document.updatestandingordertypes.submit();
            }

            function UpdateBranchesAndPaymode(commonParam, bankCode, branchCode) {
//                alert(commonParam);
//                alert(bankCode);
//                alert(branchCode);
                var optionVal = bankCode;
                $('#branchNames').empty();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadBankBranchServlet",
                        {
                            bankCode: optionVal,
                            commonParam: commonParam
                        },
                function (jsonobject) {
                    $.each(jsonobject.combo1, function (code, description) {

                        if (branchCode == code) {
                            $('#branchNames').append(
                                    $('<option selected></option>').val(code).html(description)
                                    );
                        }
                        else {
                            $('#branchNames').append(
                                    $('<option></option>').val(code).html(description)
                                    );
                        }

                    });
                });
            }

//            function LoadBranchesAndPaymode(commonParam) {
//                var optionVal = $("#banks").val();
//                $('#branchNames').empty();
//                $('#paymentType').empty();
//                $.getJSON("${pageContext.servletContext.contextPath}/LoadBankBranchServlet",
//                        {
//                            bankCode: optionVal,
//                            commonParam: commonParam
//                        },
//                function (jsonobject) {
//                    $("#branchNames").append("<option value=''>---------SELECT----------</option>");
//                    $.each(jsonobject.combo1, function (code, description) {
//                        $('#branchNames').append(
//                                $('<option></option>').val(code).html(description)
//                                );
//                    }), $.each(jsonobject.combo2, function (code, description) {
//                        $('#paymentType').append(
//                                $('<option></option>').val(code).html(description)
//                                );
//                    });
//                });
//            }
            //load dfcc bank branches
            $(document).ready(function () {

                $('#branchNames').empty();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadBankBranchServlet",
                        {
                            bankCode: $("#bankCode").val(),
                            commonParam: $("#bankCode").val()
                        },
                function (jsonobject) {
                    $("#branchNames").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#branchNames').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });
            });

            function backToLoad() {
                window.location = "${pageContext.request.contextPath}/LoadStandingOrderTypesServlet";
            }

            function invokeView(value) {

                $.post("${pageContext.request.contextPath}/ViewStandingOrderTypesServlet", {id: value},
                function (data) {
                    if (data == "success") {

                        $.colorbox({href: "${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/viewstandingordertypes.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }

                                });
                            }

                            function deleteStandingOrder(value) {

//                answer = confirm("Do you really want to delete Standing Order ID "+value+"?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteStandingOrderTypeServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadStandingOrderTypesServlet";
//                }
                                $("#dialog-confirm").html("<p>Do you really want to delete Standing Order ID " + value + "?</p>");
                                $("#dialog-confirm").dialog({
                                    resizable: false,
                                    height: 'auto',
                                    width: 500,
                                    modal: true,
                                    buttons: {
                                        "No": function () {
                                            window.location = "${pageContext.request.contextPath}/LoadStandingOrderTypesServlet";
                                        },
                                        "Yes": function () {
                                            window.location = "${pageContext.request.contextPath}/DeleteStandingOrderTypeServlet?id=" + value;
                                        }
                                    }
                                });
                            }

        </script>
        <script>
            function showHidedForm() {
                var cat = $("#category").val();


                if (cat == "UTILITY") {
                    //$("#utilityShow").css('display': 'inline-block');
                    $("#utilityShow").css({"display": "inline-block"});

                    $("#bankCode").val("${sessionScope.SessionObject.commonBankCode}");
                    $("#branchNames").val("${standingOrderTypesBean.branchCode}");
                    $("#utilityProvider").val("${standingOrderTypesBean.utilityProvider}");

                } else {
                    //$("#utilityShow").css('display': 'none');
                    $("#utilityShow").css({"display": "none"});

                    $("#utilityProvider").val("");
                    $("#accNum").val("");
                    $("#bankCode").val("");
                    $("#branchNames").val("");
                    $("#contactPerson").val("");
                    $("#contactNo").val("");

                }

            }
            $(document).ready(function () {
                var cat = $("#category").val();


                if (cat == "UTILITY") {
                    //$("#utilityShow").css('display': 'inline-block');
                    $("#utilityShow").css({"display": "inline-block"});

                    $("#bankCode").val("${sessionScope.SessionObject.commonBankCode}");

                } else {
                    $("#utilityShow").css({"display": "none"});
                }
            });
        </script>   
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.STANDING_ORDER_TYPES%>'
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

        <title>CMS STANDING ORDER TYPES</title>
    </head>
    <body <c:if test="${operationtype=='update' || addValidation=='fail'}"> onload="UpdateBranchesAndPaymode('${sessionScope.SessionObject.commonBankCode}', '${standingOrderTypesBean.bankCode}', '${standingOrderTypesBean.branchCode}')"</c:if> >
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

                                <!--                                <table class="tit"> <tr> <td   class="center"><b> STANDING ORDER TYPES </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <c:if test="${operationtype=='add'}" > 
                                    <form name="addstandingordertypes" method="POST" action="${pageContext.request.contextPath}/AddStandingOrderTypesServlet">

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Type ID</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="orderid"  maxlength="6" value="${standingOrderTypesBean.orderID}"></td>

                                                <!--                                                <td style="width: 200px;">Bank Name</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><select  name="banks" id="banks" onchange="LoadBranchesAndPaymode('${sessionScope.SessionObject.commonParam}')">
                                                                                                        <option value="" selected>----------SELECT----------</option>
                                                <c:forEach var="bank" items="${sessionScope.SessionObject.bankNames}">    
                                                    <c:if test="${standingOrderTypesBean.bankCode==bank.key}">
                                                        <option value="${bank.key}" selected="true">${bank.value}</option>    
                                                    </c:if>
                                                    <c:if test="${standingOrderTypesBean.bankCode!=bank.key}">
                                                        <option value="${bank.key}">${bank.value}</option>    
                                                    </c:if>        
                                                </c:forEach>                                                
                                            </select>
                                        </td>-->

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Description</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="orderName"  maxlength="16" value="${standingOrderTypesBean.orderName}"></td>

                                                <!--                                                <td style="width: 200px;">Branch Name</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td>
                                                                                                    <select id="branchNames" name="branchNames">
                                                                                                        <option value="" selected>----------SELECT----------</option>
                                                                                                    </select>
                                                                                                </td> -->
                                            </tr>
                                            <tr>
                                                <td style="width: 200px">Category</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <select id="category" name="category" onchange="showHidedForm()">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:if test="${standingOrderTypesBean.category==null || standingOrderTypesBean.category==''}">
                                                            <option value="CRDPYMNT">Card Payment</option>
                                                            <option value="UTILITY">Utility Payment</option>
                                                        </c:if>
                                                        <c:if test="${standingOrderTypesBean.category=='CRDPYMNT'}">
                                                            <option value="CRDPYMNT" selected="true">Card Payment</option>
                                                            <option value="UTILITY">Utility Payment</option>
                                                        </c:if>
                                                        <c:if test="${standingOrderTypesBean.category=='UTILITY'}">
                                                            <option value="CRDPYMNT">Card Payment</option>
                                                            <option value="UTILITY" selected="true">Utility Payment</option>
                                                        </c:if>
                                                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        <!--show if category==UTILITY-->
                                        <table cellpadding="0" cellspacing="10" id="utilityShow" style="display: none">
                                            <tr>
                                                <td style="width: 200px;">Utility Provider</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <select name="utilityProvider" id="utilityProvider">
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="utility" items="${sessionScope.SessionObject.utilityProviderList}">
                                                            <c:if test="${utility.providerId==standingOrderTypesBean.utilityProvider}">
                                                                <option value="${utility.providerId}" selected="true">${utility.description}</option>
                                                            </c:if>
                                                            <c:if test="${utility.providerId!=standingOrderTypesBean.utilityProvider}">
                                                                <option value="${utility.providerId}">${utility.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <!--                                                <td style="width: 200px;">Flat Fee</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><input type="text" name="flatFee"  maxlength="16" value="${standingOrderTypesBean.flatFee}"></td>-->
                                                <td style="width: 200px;">Account Number</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="accNum" id="accNum" maxlength="12" value="${standingOrderTypesBean.accNumber}"></td>

                                            </tr>
                                            <tr>
                                            <input type="hidden" id="bankCode" name="bank"  value="${sessionScope.SessionObject.commonBankCode}" />
                                            <td style="width: 200px;">Bank Branch</td>
                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                            <td>
                                                <select id="branchNames" name="branchNames">
                                                    <option value="" selected>----------SELECT----------</option>
                                                </select>
                                            </td> 
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Contact Person</td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="text" name="contactPerson" id="contactPerson" maxlength="32" value="${standingOrderTypesBean.contactPerson}"></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Contact Number</td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="text" name="contactNo" id="contactNo" maxlength="10" value="${standingOrderTypesBean.contactNumber}"></td>
                                            </tr>
                                        </table>
                                        <!--end-->
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px">Fee Type</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <select id="feeType" name="feeType">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="fee" items="${sessionScope.SessionObject.feeTypeList}">
                                                            <c:if test="${fee.feeTypeCode==standingOrderTypesBean.feeType}">
                                                                <option value="${fee.feeTypeCode}" selected="true">${fee.description}</option>
                                                            </c:if>
                                                            <c:if test="${fee.feeTypeCode!=standingOrderTypesBean.feeType}">
                                                                <option value="${fee.feeTypeCode}">${fee.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>                                           
                                                <td style="width: 200px;">Currency Type</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="currency"  >
                                                        <!--                                                        <option value="" selected>---------SELECT----------</option>-->
                                                        <c:forEach var="currency" items="${sessionScope.SessionObject.currencyMap}">
                                                            <c:if test="${sessionScope.SessionObject.commonCurrencyCode==currency.key}">
                                                                <option value="${currency.key}" selected="true">${currency.value}</option>
                                                            </c:if>
                                                            <!--                                                            <c:if test="${standingOrderTypesBean.currencyType!=currency.key}">
                                                                                                                            <option value="${currency.key}">${currency.value}</option>
                                                            </c:if>    -->
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <!--                                                <td style="width: 200px;">Address 2</td>
                                                                                                <td style="width: 20px;">&nbsp;</td>
                                                                                                <td><input type="text" name="address2"  maxlength="32" value="${standingOrderTypesBean.address2}"></td>-->

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Minimum Transaction </br>Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="minTranAmt"  maxlength="16" value="${standingOrderTypesBean.minTransactionAmt}"></td>

                                                <!--                                                <td style="width: 200px;">City</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><select  name="city"  >
                                                                                                        <option value="" selected>---------SELECT----------</option>
                                                <c:forEach var="area" items="${sessionScope.SessionObject.areaMap}">
                                                    <c:if test="${standingOrderTypesBean.city==area.key}">
                                                        <option value="${area.key}" selected="true">${area.value}</option>
                                                    </c:if>
                                                    <c:if test="${standingOrderTypesBean.city!=area.key}">
                                                        <option value="${area.key}">${area.value}</option>
                                                    </c:if>    
                                                </c:forEach>
                                            </select>
                                        </td>-->
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Maximum Transaction </br>Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="maxTranAmt"  maxlength="16" value="${standingOrderTypesBean.maxTransactionAmt}"></td>

                                                <!--                                                <td style="width: 200px;">Contact Person</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><input type="text" name="contactPerson"  maxlength="32" value="${standingOrderTypesBean.contactPerson}"></td>-->

                                            </tr>



                                            <!--                                            <tr>
                                                                                            <td style="width: 200px;">Flat Fee Percentage</td>
                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td><input type="text" name="percentage"  maxlength="8" value="${standingOrderTypesBean.flatFeePercentage}"></td>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                            <td style="width: 200px;">Payment Type</td>
                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td>
                                                                                                <select name="paymentType" value="" id="paymentType">
                                                                                                    <option value="">---------SELECT----------</option>
                                                                                                </select>
                                                                                            </td>
                                                                                        </tr>-->

                                            <!--                                            <tr>
                                                                                            <td style="width: 200px;">Fee Option</td>
                                                                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                                                            <td>
                                            <c:if test="${standingOrderTypesBean.feeOption == 'MIN'}">
                                                <input type="radio" name="feeOption" value="MIN" checked="true"/> Min                                                  
                                            </c:if>
                                            <c:if test="${standingOrderTypesBean.feeOption != 'MIN'}">
                                                <input type="radio" name="feeOption" value="MIN"/> Min
                                            </c:if>

                                            <c:if test="${standingOrderTypesBean.feeOption == 'MAX'}">
                                                <input type="radio" name="feeOption" value="MAX" checked="true"/> Max                                                  
                                            </c:if>
                                            <c:if test="${standingOrderTypesBean.feeOption != 'MAX'}">
                                                <input type="radio" name="feeOption" value="MAX" /> Max 
                                            </c:if>

                                            <c:if test="${standingOrderTypesBean.feeOption == 'BOT'}">
                                                <input type="radio" name="feeOption" value="BOT" checked="true"/> Both                                                
                                            </c:if>
                                            <c:if test="${standingOrderTypesBean.feeOption != 'BOT'}">
                                                <input type="radio" name="feeOption" value="BOT" /> Both
                                            </c:if>                                              
                                            </td>

                                            <td style="width: 200px;">Address 1</td>
                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                            <td><input type="text" name="address1"  maxlength="32" value="${standingOrderTypesBean.address1}"></td>

                                        </tr>-->

                                            <tr>                                           
                                                <td style="width: 200px;">Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="standingOrderStatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${standingOrderTypesBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected="true">${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${standingOrderTypesBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>
                                                            </c:if>        
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <!--                                                <td style="width: 200px;">Contact No</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><input type="text" name="contactNo"  maxlength="10" value="${standingOrderTypesBean.contactNumber}"></td>-->

                                            </tr>

                                            <tr></tr>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="submit" value="Add" name="Add" class="defbutton"/>
                                                    <input onclick="backToLoad()" type="reset" value="Reset" class="defbutton"/></td>                                               
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td ></td>
                                            </tr>

                                        </table>

                                    </form>
                                </c:if>  

                                <c:if test="${operationtype=='update'}" >
                                    <form name="updatestandingordertypes" method="POST" action="${pageContext.request.contextPath}/UpdateStandingOrderTypesServlet">

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Type ID</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="orderid"  maxlength="6" value="${standingOrderTypesBean.orderID}" readonly=""></td>

                                                <!--                                                <td style="width: 200px;">Bank Name</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><select  name="banks" id="banks" onchange="LoadBranchesAndPaymode('${sessionScope.SessionObject.commonParam}')">
                                                                                                        <option value="" selected>----------SELECT----------</option>
                                                <c:forEach var="bank" items="${sessionScope.SessionObject.bankNames}">    
                                                    <c:if test="${standingOrderTypesBean.bankCode==bank.key}">
                                                        <option value="${bank.key}" selected="true">${bank.value}</option>    
                                                    </c:if>
                                                    <c:if test="${standingOrderTypesBean.bankCode!=bank.key}">
                                                        <option value="${bank.key}">${bank.value}</option>    
                                                    </c:if>        
                                                </c:forEach>                                                
                                            </select>
                                        </td>-->
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Description</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="orderName"  maxlength="32" value="${standingOrderTypesBean.orderName}"></td>

                                                <!--                                                <td style="width: 200px;">Branch Name</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td>
                                                                                                    <select id="branchNames" name="branchNames">
                                                                                                        <option value="" selected>---------SELECT----------</option>
                                                                                                    </select>
                                                                                                </td> -->
                                            </tr>
                                            <tr>
                                                <td style="width: 200px">Category</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <select id="category" name="category" onchange="showHidedForm()">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:if test="${standingOrderTypesBean.category==null || standingOrderTypesBean.category==''}">
                                                            <option value="CRDPYMNT">Card Payment</option>
                                                            <option value="UTILITY">Utility Payment</option>
                                                        </c:if>
                                                        <c:if test="${standingOrderTypesBean.category=='CRDPYMNT'}">
                                                            <option value="CRDPYMNT" selected="true">Card Payment</option>
                                                            <option value="UTILITY">Utility Payment</option>
                                                        </c:if>
                                                        <c:if test="${standingOrderTypesBean.category=='UTILITY'}">
                                                            <option value="CRDPYMNT">Card Payment</option>
                                                            <option value="UTILITY" selected="true">Utility Payment</option>
                                                        </c:if>
                                                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        <!--show if category==UTILITY-->
                                        <table cellpadding="0" cellspacing="10" id="utilityShow" style="display: none">
                                            <tr>
                                                <td style="width: 200px;">Utility Provider</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <select name="utilityProvider" id="utilityProvider">
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="utility" items="${sessionScope.SessionObject.utilityProviderList}">
                                                            <c:if test="${utility.providerId==standingOrderTypesBean.utilityProvider}">
                                                                <option value="${utility.providerId}" selected="true">${utility.description}</option>
                                                            </c:if>
                                                            <c:if test="${utility.providerId!=standingOrderTypesBean.utilityProvider}">
                                                                <option value="${utility.providerId}">${utility.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Account Number</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="accNum" id="accNum" maxlength="12" value="${standingOrderTypesBean.accNumber}"></td>
                                            </tr>
                                            <tr>
                                            <input type="hidden" id="bankCode" name="bank"  value="${sessionScope.SessionObject.commonBankCode}" />
                                            <td style="width: 200px;">Bank Branch</td>
                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                            <td>
                                                <select id="branchNames" name="branchNames">
                                                    <option value="" selected>---------SELECT----------</option>
                                                </select>
                                            </td> 
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Contact Person</td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="text" name="contactPerson" id="contactPerson" maxlength="32" value="${standingOrderTypesBean.contactPerson}"></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Contact No</td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="text" name="contactNo" id="contactNo"  maxlength="10" value="${standingOrderTypesBean.contactNumber}"></td>
                                            </tr>
                                        </table>
                                        <!--end-->
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px">Fee Type</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <select id="feeType" name="feeType">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="fee" items="${sessionScope.SessionObject.feeTypeList}">
                                                            <c:if test="${fee.feeTypeCode==standingOrderTypesBean.feeType}">
                                                                <option value="${fee.feeTypeCode}" selected="true">${fee.description}</option>
                                                            </c:if>
                                                            <c:if test="${fee.feeTypeCode!=standingOrderTypesBean.feeType}">
                                                                <option value="${fee.feeTypeCode}">${fee.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <!--                                            <tr>
                                                                                            <td style="width: 200px;">Flat Fee</td>
                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td><input type="text" name="flatFee"  maxlength="16" value="${standingOrderTypesBean.flatFee}"></td>
                                            
                                                                                            <td style="width: 200px;">Account Number</td>
                                                                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td><input type="text" name="accNum"  maxlength="12" value="${standingOrderTypesBean.accNumber}"></td>
                                            
                                                                                        </tr>-->

                                            <!--                                            <tr>
                                                                                            <td style="width: 200px;">Flat Fee Percentage</td>
                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td><input type="text" name="percentage"  maxlength="8" value="${standingOrderTypesBean.flatFeePercentage}"></td>
                                            
                                                                                            <td style="width: 200px;">Payment Type</td>
                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td>
                                                                                                <select name="paymentType" value="" id="paymentType">
                                                                                                    <option value="">------------SELECT------------</option>
                                                                                                </select>
                                                                                            </td>
                                                                                        </tr>-->

                                            <!--                                            <tr>
                                                                                            <td style="width: 200px;">Fee Option</td>
                                                                                            <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                            <td>
                                            <c:if test="${standingOrderTypesBean.feeOption == 'MIN'}">
                                                <input type="radio" name="feeOption" value="MIN" checked="true"/> Min                                                  
                                            </c:if>
                                            <c:if test="${standingOrderTypesBean.feeOption != 'MIN'}">
                                                <input type="radio" name="feeOption" value="MIN"/> Min
                                            </c:if>

                                            <c:if test="${standingOrderTypesBean.feeOption == 'MAX'}">
                                                <input type="radio" name="feeOption" value="MAX" checked="true"/> Max                                                  
                                            </c:if>
                                            <c:if test="${standingOrderTypesBean.feeOption != 'MAX'}">
                                                <input type="radio" name="feeOption" value="MAX" /> Max 
                                            </c:if>

                                            <c:if test="${standingOrderTypesBean.feeOption == 'BOT'}">
                                                <input type="radio" name="feeOption" value="BOT" checked="true"/> Both                                                
                                            </c:if>
                                            <c:if test="${standingOrderTypesBean.feeOption != 'BOT'}">
                                                <input type="radio" name="feeOption" value="BOT" /> Both
                                            </c:if>                                              
                                        </td>

                                        <td style="width: 200px;">Address 1</td>
                                        <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                        <td><input type="text" name="address1"  maxlength="32" value="${standingOrderTypesBean.address1}"></td>

                                    </tr>-->

                                            <tr>                                           
                                                <td style="width: 200px;">Currency Type</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="currency"  >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="currency" items="${sessionScope.SessionObject.currencyMap}">
                                                            <c:if test="${standingOrderTypesBean.currencyType==currency.key}">
                                                                <option value="${currency.key}" selected="true">${currency.value}</option>
                                                            </c:if>
                                                            <c:if test="${standingOrderTypesBean.currencyType!=currency.key}">
                                                                <option value="${currency.key}">${currency.value}</option>
                                                            </c:if>    
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <!--                                                <td style="width: 200px;">Address 2</td>
                                                                                                <td style="width: 20px;">&nbsp;</td>
                                                                                                <td><input type="text" name="address2"  maxlength="32" value="${standingOrderTypesBean.address2}"></td>-->

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Minimum Transaction </br>Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="minTranAmt"  maxlength="16" value="${standingOrderTypesBean.minTransactionAmt}"></td>

                                                <!--                                                <td style="width: 200px;">City</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><select  name="city"  >
                                                                                                        <option value="" selected>---------SELECT----------</option>
                                                <c:forEach var="area" items="${sessionScope.SessionObject.areaMap}">
                                                    <c:if test="${standingOrderTypesBean.city==area.key}">
                                                        <option value="${area.key}" selected="true">${area.value}</option>
                                                    </c:if>
                                                    <c:if test="${standingOrderTypesBean.city!=area.key}">
                                                        <option value="${area.key}">${area.value}</option>
                                                    </c:if>    
                                                </c:forEach>
                                            </select>
                                        </td>-->
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Maximum Transaction </br>Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="maxTranAmt"  maxlength="16" value="${standingOrderTypesBean.maxTransactionAmt}"></td>

                                                <!--                                                <td style="width: 200px;">Contact Person</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><input type="text" name="contactPerson"  maxlength="32" value="${standingOrderTypesBean.contactPerson}"></td>-->

                                            </tr>

                                            <tr>                                           
                                                <td style="width: 200px;">Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="standingOrderStatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${standingOrderTypesBean.statusCode==status.statusCode}">
                                                                <option value="${status.statusCode}" selected="true">${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${standingOrderTypesBean.statusCode!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>
                                                            </c:if>        
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <!--                                                <td style="width: 200px;">Contact No</td>
                                                                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                                                                <td><input type="text" name="contactNo"  maxlength="10" value="${standingOrderTypesBean.contactNumber}"></td>-->

                                            </tr>

                                            <tr></tr>
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td> 
                                                    <input style="width: 95px" type="submit" value="Update" name="Update" class="defbutton"/>
                                                    <input style="width: 95px" onclick="backToLoadUpdate('${stateBean.stateCycleCode}')" type="reset" value="Reset" class="defbutton"/>
                                                    <input style="width: 95px" onclick="backToLoad()" type="button" value="Cancel" class="defbutton"/>
                                                </td>
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td ></td>
                                            </tr>

                                        </table>

                                    </form>
                                </c:if>     
                                <!-- show table data -->
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview">
                                        <thead class="gradeB">
                                            <tr >
                                                <th>Type ID</th>
                                                <th>Description</th>
<!--                                                <th>Branch Name</th>                                               -->
                                                <th>Category</th>
                                                <th>Status</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${standingOrderList}" var="standingOrder">
                                                <tr>
                                                    <td>${standingOrder.orderID}</td>                                                    
                                                    <td>${standingOrder.orderName}</td>                                                    
<!--                                                    <td>${standingOrder.branchCode}</td>                                                                                                  -->
                                                    <td>
                                                        <c:if test="${standingOrder.category=='UTILITY'}">
                                                            Utility Payment
                                                        </c:if>
                                                        <c:if test="${standingOrder.category=='CRDPYMNT'}">
                                                            Card Payment
                                                        </c:if>
                                                        
                                                    </td>                                                    
                                                    <td>${standingOrder.status}</td>

                                                    <td><a  href='#' onclick="invokeView('${standingOrder.orderID}')">View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateStandingOrderTypeServlet?id=<c:out value="${standingOrder.orderID}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteStandingOrder('${standingOrder.orderID}')">Delete</a></td>
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
        <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>