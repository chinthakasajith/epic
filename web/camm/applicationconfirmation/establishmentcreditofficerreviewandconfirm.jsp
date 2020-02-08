<%-- 
    Document   : establishmentcreditofficerreviewandconfirm
    Created on : Jul 29, 2016, 10:26:42 AM
    Author     : chinthaka_r
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/windowcss/dhtmlwindow.css" type="text/css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/dhtmlwindow.js"></script>

        <script language="javascript">

            function uploadLetters(applicationId)
            {

                document.recommendLetter.action = "${pageContext.request.contextPath}/UploadRcomondationLetterServlet?applicationId=" + applicationId + "&crdtOfficerRecCreditLimit=" + $("#crdtOfficerRecCreditLimit").val() + "&crdtOfficerRecProduct=" + $("#crdtOfficerRecProduct").val() + "&officerReviewAndConfirm=YES";
                document.recommendLetter.submit();

            }

            function resetLetterField()
            {

                $("#control").replaceWith("<input type='file' id='control' />");
                $("#crdtOfficerRecCreditLimit").val("");
                $("#crdtOfficerRecProduct").val("");

            }


            function invokeWindow(identityType, applicationId, verificationCategory, documentType, fileName) {

                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId=" + applicationId + "&verificationCategory=" + verificationCategory + "&documentType=" + documentType + "&fileName=" + fileName, identityType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");

            }
            function invokeBreakDown(value) {
                $.post("${pageContext.request.contextPath}/ViewBreakDownServlet", {breakDown: value},
                function (data) {
                    if (data == "success") {
                        $.colorbox({href: "${pageContext.request.contextPath}/camm/applicationconfirmation/breakdownview.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }

                                });

                            }

        </script>
        <!--confirm-->
        <script type="text/javascript">
            function recalCreditScore(applicationId) {

                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/RecalculateCreditScoreServlet",
                    type: "POST",
                    data: {applicationId: applicationId},
                    success: function (data) {
                        $("#creditScore").val(data);
                    }
                });

            }
            function LoadBinProfiles() {

                $('#binProfile').empty();
                var cVal = $("#confirmCardProduct").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadBinProfilesServlet",
                        {cardProductVal: cVal},
                function (jsonobject) {
                    $("#binProfile").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#binProfile').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });
            }

            function LoadproductionMode() {
                $('#productionMode').empty();
                var cardProductVal = $("#confirmCardProduct").val();
                var binProfileVal = $('#binProfile').val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadProductionModeServlet",
                        {cardProductVal: cardProductVal, binProfileVal: binProfileVal},
                function (jsonobject) {
                    $("#productionMode").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#productionMode').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });
            }
            function loadAccountTemplates(applicationid, currency, cardCategoryCode) {

                $('#accountTemplates').empty();
                var applicationId = applicationid;
                var cVal = $("#customerTemplate").val();
                var currency = currency;

                $.getJSON("${pageContext.servletContext.contextPath}/LoadAccountTemplatesServlet",
                        {
                            applicationId: applicationId,
                            cValue: cVal,
                            currency: currency,
                            cardCategoryCode: cardCategoryCode

                        },
                function (jsonobject) {
                    $("#accountTemplates").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#accountTemplates').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });

            }

            function loadCardTempList(applicationId, currency) {


                $('#cardtemplate').empty();

                var applicationId = applicationId;
                var pVal = $("#confirmCardProduct").val();
                var cVal = $("#customerTemplate").val();
                var aVal = $("#accountTemplates").val();
                var currency = currency;
                var cardCategoryCode = $("#cardCategoryCode").val();

                $.getJSON("${pageContext.servletContext.contextPath}/LoadCardTemplatesServlet",
                        {
                            cValue: cVal,
                            aValue: aVal,
                            applicationId: applicationId,
                            pValue: pVal,
                            currency: currency,
                            cardCategoryCode: cardCategoryCode

                        },
                function (jsonobject) {
                    $("#cardtemplate").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#cardtemplate').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });

            }
            function Confirm()
            {
                $('#errorMsg').text("");

                answer = confirm("Are you sure you want to confirm this application?")
                if (answer != 0)
                {
                    //                    document.confirmForm.action="${pageContext.request.contextPath}/ConfirmServlet?applicationid="+applicationid+"&currency="+currency;

                    $.ajax({
                        url: "${pageContext.request.contextPath}/ConfirmServlet",
                        type: "POST",
                        data: $("#confirmForm").serialize(),
                        success: function (data) {
                            var ar = data.split(",", 2);
                            //                        ar=data;
                            if (ar[0] == 'success') {
                                $('#succMes').val(ar[1]);
                                $('#succForm').submit();
                            } else {
                                $("#errorMsgcon").text(data);
                            }

                        }
                    });




                }
                else
                {

                }

            }

            function resetConfirmation() {

                $("#creditLmt").val(${cardApplicationList.cOfficerRecCrditLimt});
                $("#confirmCardProduct").val("");
                $("#productionMode").val("");
                $("#binProfile").val("");
                $("#customerTemplate").val("");
                $("#accountTemplates").val("");
                $("#cardtemplate").val("");
                $("#errorMsgcon").text("");

            }

            function resetReject() {

                $("#remark").val("");
                $("#rejectReason").val("");

            }


            function Reject(applicationid)
            {
                answer = confirm("Are you sure you want to Reject this application?")
                if (answer != 0)
                {
                    document.confirmForm.action = "${pageContext.request.contextPath}/ApplicationRejectServlet?applicationid=" + applicationid + "&officerReviewAndConfirm=YES";
                }
                else
                {
                    document.confirmForm.action = "${pageContext.request.contextPath}/LoadEstablishmentCreditOfficerDetailsAndConfirmServlet?applicationid=" + applicationid;
                }

            }
        </script>


        <title>EPIC CMS CREDIT OFFICER REVIEW</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>
                <form name="sucMessage" id="succForm" style="background: #FFFFFF;border-radius: 0px;padding: 0px;border: 0px;margin-bottom: 0px"action="${pageContext.request.contextPath}/LoadApplicationApproveTableServlet">
                    <input type="hidden" name="successMsg" id="succMes"></input>
                </form>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  ------------------------------------->
                                <table class="tit"> <tr> <td   class="center">  CREDIT OFFICER REVIEW AND CONFIRM</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td style="width: 200px;"> Application ID</td>
                                            <td><b><font color="#000000"> ${cardApplicationList.applicationId}</font></b> </td>

                                        <td style="width: 135px;"></td> 

                                        <td style="width: 200px;"> Application Category</td>
                                        <td><b><font color="#000000">${cardApplicationList.appTypeDes}</font></b> </td>
                                    </tr>

                                </table>
                                <form  ENCTYPE="multipart/form-data"  METHOD=POST name="recommendLetter">
                                    <table cellpadding="0" cellspacing="10"  >
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Company Information</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Identification Type</td>
                                            <td><b><font color="#000000">${cardApplicationList.identificationType}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Identification Number</td>
                                            <td><b><font color="#000000">${cardApplicationList.identificationNumber}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Name of the company</td>
                                            <td><b><font color="#000000">${establishmentDetailsList.companyName}</font></b></td> 
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Nature of Business</td>
                                            <td><b><font color="#000000">${establishmentDetailsList.natureOfTheBusiness}</font></b></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td style="width: 200px;">Annual turnover</td>
                                            <td><b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.annualTurnover}"/></font></b></td>
                                            <td></td>
                                            <td style="width: 100px;">Share capital</td>
                                            <td><b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.shareCapital}"/></font></b></td>
                                            <td></td>
                                            <td style="width: 100px;">Net profit</td>
                                            <td><b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.netProfit}"/></font></b></td>
                                        </tr>
                                        <tr>
                                            <td colspan="10" ><hr /></td>
                                        </tr>
                                    </table>
                                    <c:if test="${isConfirmMode==0}">
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br />Verify Documents</font></td>
                                            </tr>
                                            <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                <tr>
                                                    <td style="width: 300px">${verifypoint.description}</td>
                                                    <c:if test="${verifypoint.documentType !=null}">
                                                        <c:if test="${verifypoint.documentExist =='YES'}">
                                                            <td style="width: 25px"><a href="#" onClick="invokeWindow('${verifypoint.description}', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                    return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td>
                                                                </c:if>
                                                                <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></c:if>  
                                                        </c:if>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td colspan="10" ><hr /></td>
                                            </tr>
                                        </table>
                                    </c:if>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>Credit Score</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Score</td>
                                            <td>
                                                <b><font color="#000000">${cardApplicationList.creditScore}</font></b>
                                            </td>
                                            <c:if test="${isConfirmMode==1}">
                                                <td><input type="button" value="Re Calculate" name="next" style="width: 100px" onclick="recalCreditScore('${cardApplicationList.applicationId}')"></input></td>
                                                <td><input id="creditScore" type="text" name="recalValue" style="width: 100px;"></input></td>
                                                </c:if>
                                            <td><a  href="#"  onclick="invokeBreakDown('${establishmentDetailsList.applicationId}')"><img src="<%=request.getContextPath()%>/resources/images/Break_down.jpg" width="100" height="40" /></a></td>
                                        </tr>
                                        <tr>
                                            <td colspan="10" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>System Recommended</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Products</td>
                                            <td>
                                                <c:set var="counter" value="${listsize-1}"/>
                                                <select  name="cardProducts"  class="inputfield-mandatory">
                                                    <option value="" style="width: 100px;">--SELECT--</option>
                                                    <c:forEach var="card" items="${cardProductList}">
                                                        <c:if test="${counter != 0}">    
                                                            <option value="${card.cardProductCode}">${card.cardproductDescription}</option>
                                                        </c:if> 
                                                        <c:if test="${counter == 0}">    
                                                            <option value="${card.cardProductCode}" selected>${card.cardproductDescription}</option>
                                                        </c:if>   
                                                        <c:set var="counter" value="${counter - 1}"/>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${currencyAlphaCode} ${creditLimitFomatted}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>Applicant Requested</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Type</td>
                                            <td>
                                                <b><font color="#000000">${establishmentDetailsList.cardTypeDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Product</td>
                                            <td>
                                                <b><font color="#000000">${establishmentDetailsList.cardProductDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.creditLimit}"/></font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <c:if test="${isConfirmMode==0}">
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Credit Officer Recommendation</font></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Card Product</td>
                                                <td>
                                                    <select  name="crdtOfficerRecProduct" id="crdtOfficerRecProduct" class="inputfield-mandatory">
                                                        <option value="" style="width: 100px;">--SELECT--</option>
                                                        <c:forEach var="products" items="${cardProducts}">
                                                            <c:if test="${cproduct != products.productCode}">
                                                                <option value="${products.productCode}">${products.description}</option>
                                                            </c:if>
                                                            <c:if test="${cproduct == products.productCode}">
                                                                <option value="${products.productCode}" selected="true">${products.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Credit Limit (${currencyAlphaCode})</td>
                                                <td><input type="text" name="crdtOfficerRecCreditLimit" id="crdtOfficerRecCreditLimit" value="${climit}"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Choose the letter To Upload</td>
                                                <td><input id="control" name="recommendationLetter" type="file"/></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <table>
                                                        <td><input type="submit" value="Submit" name="next" style="width: 100px" onclick="uploadLetters('${establishmentDetailsList.applicationId}')"> </ input></td>
                                                        <td><input type="button" value="Reset" name="next" style="width: 100px" onclick="resetLetterField()"> </ input></td>
                                                    </table>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </table>
                                </form>
                                <c:if test="${isConfirmMode==1}">         
                                    <form method="POST" action="DownloadLetterServlet" target="downloadform">
                                        <input type="text" name="sysRecomendedCreditLimit" value="${creditLimit}"  style="visibility: hidden"/>
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br />Credit Officer Recommended</font></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Credit Limit</td>
                                                <td>
                                                    <b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${cardApplicationList.cOfficerRecCrditLimt}"/></font>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Card Product</td>
                                                <c:forEach var="products" items="${cardProducts}">
                                                    <c:if test="${products.productCode==cardApplicationList.cOfficerRecCardProduct}">
                                                        <td>
                                                            <b><font color="#000000">${products.description}</font>
                                                        </td>
                                                    </c:if>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Recommendation Letter</font></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <table>
                                                        <td><input type="hidden" value="${cardApplicationList.applicationId}" name="applicationId" style="width: 100px"  ></input></td>
                                                        <td><input type="submit" value="Download" name="next" style="width: 100px"  ></input></td>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </c:if>
                                <c:if test="${isConfirmMode==1}">
                                    <form name="confirmForm" action="" method="POST" id="confirmForm">
                                        <table><td><b><font color="#FF0000" id="errorMsgcon"> ${errorMsgcon}</font></b> </td></table>
                                        <div class="outset" style="border-style:outset; background-color: #B8B8B8 ;  border-color: #999; width: 100%">

                                            <table cellpadding="0" cellspacing="10">      
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Confirm</font></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Card Products</td>
                                                    <td>
                                                        <select id="confirmCardProduct" name="confirmCardProduct"  onchange="LoadBinProfiles()" class="inputfield-mandatory" >
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                            <c:forEach var="card" items="${cardProductList}">

                                                                <c:if test="${card.cardType!='No'}">
                                                                    <option value="${card.cardProductCode}">${card.cardproductDescription}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Credit Limit in ${currencyAlphaCode}</td>

                                                    <td>
                                                        <input type="text" id="creditLmt" name="creditLimit" style="width: 178px;" value="${cardApplicationList.cOfficerRecCrditLimt}"></input>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Bin Profile</td>
                                                    <td>
                                                        <select id="binProfile" name="binProfile"  class="inputfield-mandatory" onchange="LoadproductionMode()">
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                        </select>

                                                    </td> 
                                                </tr>                                            

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Production Mode</td>
                                                    <td>
                                                        <select id="productionMode" name="productionMode"  class="inputfield-mandatory">
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Customer Template</td>
                                                    <td>
                                                        <select id="customerTemplate" name="customerTemplate" onchange="loadAccountTemplates('${cardApplicationList.applicationId}', '${establishmentDetailsList.cardCurrency}', '${cardApplicationList.cardCategoryCode}')"  class="inputfield-mandatory">
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                            <c:forEach var="cusList" items="${staffCusList}">

                                                                <c:if test="${pageBean.templateCode==cusList.templateCode}">
                                                                    <option value="${cusList.templateCode}" selected>${cusList.templateName}</option>
                                                                </c:if>
                                                                <c:if test="${pageBean.templateCode!=cusList.templateCode}">
                                                                    <option value="${cusList.templateCode}" >${cusList.templateName}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Account Template</td>
                                                    <td>
                                                        <select id="accountTemplates" name="accountTemplates"  onchange="loadCardTempList('${cardApplicationList.applicationId}', '${establishmentDetailsList.cardCurrency}')" class="inputfield-mandatory">
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Card Template</td>
                                                    <td>
                                                        <select id="cardtemplate" name="cardtemplate"  class="inputfield-mandatory">
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                        </select>

                                                    </td> 

                                                    <td><input type="hidden" name="applicationid" value="${cardApplicationList.applicationId}" hidden="true"/></td>
                                                    <td><input type="hidden" name="currency" value="${establishmentDetailsBean.cardCurrency}" hidden="true"/></td>
                                                    <td><input type="hidden" id="cardCategoryCode" name="cardCategoryCode" value="E" hidden="true"/></td>
                                                    <!--Identification Number-->
                                                    <td><input type="hidden" name="identificationNum" value="${cardApplicationList.identificationNumber}"/></td>
                                                    <!--Identification Type-->
                                                    <td><input type="hidden" name="identificationType" value="${cardApplicationList.identificationType}"/></td>

                                                </tr>

                                                <tr>

                                                    <td>
                                                        <table>

                                                            <td><input type="button" value="Confirm"  onclick="Confirm()" style="width: 100px"></input></td>
                                                            <td><input type="reset" value="Reset" style="width: 100px"></input></td>

                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                        <br/>
                                        <div class="outset" style="width: 100%">
                                            <table cellpadding="0" cellspacing="10"  >     
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Reject</font></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Reject Reason</td>
                                                    <td>
                                                        <select id="rejectReason" name="rejectReason"  class="inputfield-mandatory">
                                                            <option value="" style="width: 100px;" selected>--SELECT--</option>
                                                            <c:forEach var="reject" items="${rejectReasons}">

                                                                <c:if test="${TaskBean.status==status.statusCode}">
                                                                    <option value="${reject.reasonCode}" >${reject.description}</option>
                                                                </c:if>
                                                                <c:if test="${TaskBean.status!=status.statusCode}">
                                                                    <option value="${reject.reasonCode}">${reject.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td> 
                                                </tr>
                                                <tr>
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA id="remark" NAME="remark" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                            </tr>


                                            </tr>
                                            <tr>

                                                <td>
                                                    <table>

                                                        <td><input type="submit" value="Reject" name="next" style="width: 100px" onclick="Reject('${cardApplicationList.applicationId}')"></input></td>
                                                        <td><input type="button" value="Reset" name="next" style="width: 100px" onclick="resetReject()"></input></td>
                                                        </form>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>

                                    </div>

                                </form>
                                </c:if>
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
