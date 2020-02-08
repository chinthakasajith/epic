<%-- 
    Document   : corporatecreditofficerreviewandconfirm
    Created on : Aug 1, 2016, 9:27:19 AM
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


            function invokeReset() {
                $("#control").replaceWith("<input type='file' id='control' />");
                $("#crdtOfficerRecCreditLimit").replaceWith("<input type='text' id='crdtOfficerRecCreditLimit' />");
                document.getElementById("crdtOfficerRecProduct").selectedIndex = 0;
                

            }

            function uploadLetters(applicationId)
            {

                document.recommendLetter.action = "${pageContext.request.contextPath}/UploadRcomondationLetterServlet?applicationId=" + applicationId + "&crdtOfficerRecCreditLimit=" + $("#crdtOfficerRecCreditLimit").val() + "&crdtOfficerRecProduct=" + $("#crdtOfficerRecProduct").val() + "&officerReviewAndConfirm=YES";
                document.recommendLetter.submit();

            }

            function resetLetterField()
            {

                $("#control").replaceWith("<input type='file' id='control' />");

            }


            function invokeWindow(identityType, applicationId, verificationCategory, documentType, fileName) {

                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId=" + applicationId + "&verificationCategory=" + verificationCategory + "&documentType=" + documentType + "&fileName=" + fileName, identityType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");

            }

        </script>
        <!--confirm-->
        <script type="text/javascript">
            function LoadBinProfiles(applicationId, currency) {
                $('#binProfile').empty();
                var sval = $("#productionMode").val();
                var cardProductVal = $("#confirmCardProduct").val();

                $.getJSON("${pageContext.servletContext.contextPath}/LoadBinProfilesServlet",
                        {
                            promode: sval,
                            cardProductVal: cardProductVal
                        },
                function (jsonobject) {
                    $("#binProfile").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#binProfile').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });
                loadCustomerTemplates(applicationId, currency);

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
            function loadCustomerTemplates(applicationId, currency) {
                $('#customerTemplate').empty();


                var sval = $('#staff').val();


                var pVal = $("#confirmCardProduct").val();

                $.getJSON("${pageContext.servletContext.contextPath}/LoadCustomerTemplatesServlet",
                        {sValue: sval,
                            pValue: pVal
                        },
                function (jsonobject) {
                    $("#customerTemplate").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
                        $('#customerTemplate').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });
                //loadAccountTemplates(applicationId ,currency);  
            }
            function loadAccountTemplates(applicationId, currency) {
                $('#accountTemplates').empty();


                var sval = $('#staff').val();
                var pVal = $("#confirmCardProduct").val();
                var cVal = $("#customerTemplate").val();
                var cardCategoryCode = $("#cardCategoryCode").val();

                $.getJSON("${pageContext.servletContext.contextPath}/LoadAccountTemplatesServlet",
                        {sValue: sval,
                            cValue: cVal,
                            pValue: pVal,
                            applicationId: applicationId,
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

                //loadCardTempList(applicationId,currency);

            }

            function loadCardTempList(applicationId, currency) {
                $('#cardtemplate').empty();

                var sval = $('#staff').val();
                var pVal = $("#confirmCardProduct").val();
                var cVal = $("#customerTemplate").val();
                var aVal = $("#accountTemplates").val();
                var cardCategoryCode = $("#cardCategoryCode").val();

                $.getJSON("${pageContext.servletContext.contextPath}/LoadCardTemplatesServlet",
                        {sValue: sval,
                            cValue: cVal,
                            aValue: aVal,
                            pValue: pVal,
                            currency: currency,
                            applicationId: applicationId,
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
            function Confirm(applicationid, currency)
            {
//                answer = confirm("Are you sure you want to confirm this application?")
//                if (answer !=0)
//                {
//                    document.confirmForm.action="${pageContext.request.contextPath}/ConfirmServlet?applicationid="+applicationid;
//                }
//                else
//                {
//                    document.confirmForm.action="${pageContext.request.contextPath}/LoadCorporateApproveDetailsServlet?applicationid="+applicationid;
//                }


                $('#errorMsg').text("");

                answer = confirm("Are you sure you want to confirm this application?")
                if (answer != 0)
                {
                    //document.confirmForm.action="${pageContext.request.contextPath}/ConfirmServlet?applicationid="+applicationid+"&currency="+currency;

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

            }
            function resetConfirmation() {

                $("#creditLmt").val("");
                $("#confirmCardProduct").val("");
                $("#productionMode").val("");
                $("#binProfile").val("");
                $("#customerTemplate").val("");
                $("#accountTemplates").val("");
                $("#cardtemplate").val("");
                $("#errorMsgcon").html("");
                $("#creditLmt").val(${cardApplicationList.cOfficerRecCrditLimt});

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
                    document.confirmForm.action = "${pageContext.request.contextPath}/LoadCorporateCreditOfficerDetailsAndConfirmServlet?applicationid=" + applicationid;
                }

            }
        </script>




        <title>EPIC CMS CREDIT OFFICER REVIEW AND CONFIRM</title>

    </head>
    <body>
        <div class="container">
            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!------------------------start  developer area  ---------------------------------------------------------------------->
                                <form name="sucMessage" id="succForm" style="background: #FFFFFF;padding: 0px;border: 0px"action="${pageContext.request.contextPath}/LoadCreditOfficerReviewAndConfirmServlet">
                                    <input type="hidden" name="successMsg" id="succMes"></input>
                                </form>
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
                                            <td colspan="6" ><font style="color: #999"> <br />Personal Information</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Company Name</td>
                                            <td><b><font color="#000000">${personalDetail.companyName}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;"> Establishment Credit Limit</td>
                                            <td><b><font color="#000000"> ${personalDetail.currencyTypeAlphaCode}&nbsp<fmt:formatNumber type="number" value="${esablishmentCard.creditLimit}"/></font></b> </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Designation</td>
                                            <td><b><font color="#000000">${personalDetail.designation}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Identification Type</td>
                                            <td><b><font color="#000000"> ${cardApplicationList.identificationType}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Identification Number</td>
                                            <td><b><font color="#000000"> ${cardApplicationList.identificationNumber}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Title</td>

                                            <td>
                                                <b><font color="#000000"> ${personalDetail.title}</font></b>
                                            </td>
                                        </tr>
                                        <tr>

                                            <td style="width: 200px;">Full Name</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.fullName}</font></b>
                                            </td>
                                            <td style="width: 200px;"> Nationality</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.nationality}</font></b>
                                            </td>
                                            <td style="width: 200px;"></td> 
                                        </tr>
                                        <!--                                        <tr>
                                        
                                                                                    <td style="width: 200px;">Last Name</td>
                                                                                    <td>
                                                                                        <b><font color="#000000"> ${personalDetail.lastName}</font></b>
                                                                                    </td>
                                        
                                        
                                                                                    <td style="width: 200px;"> Middle Names</td>    
                                                                                    <td>
                                                                                        <b><font color="#000000"> ${personalDetail.middleName}</font></b>
                                                                                    </td>
                                                                                    <td style="width: 100px;"></td> 
                                                                                </tr>-->
                                        <tr>
                                            <td style="width: 200px;">Name on the Card</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.nameOncard}</font></b>
                                            </td>
                                            <td style="width: 200px;"> Gender</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.gender}</font></b>
                                            </td>
                                            <td style="width: 200px;"></td> 

                                        </tr>
                                        <tr>

                                            <td style="width: 200px;">Date Of Birth</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.birthday}</font></b>
                                            </td>

                                            <td style="width: 200px;">Place of Birth</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.placeOfbirth}</font></b>
                                            </td>
                                            <td style="width: 200px;"></td> 
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Contact Information</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Address </td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.address1}</font></b>
                                            </td>

                                            <td style="width: 200px;"> Res. Phone No</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.homeTelNumber}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;"></td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.address2}</font></b>
                                            </td>

                                            <td style="width: 200px;"> Mobile No</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.mobileNumber}</font></b>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td style="width: 200px;"></td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.address3}</font></b>
                                            </td>

                                            <td style="width: 200px;"> </td>
                                            <td>
                                                <b><font color="#000000"> </font></b>
                                            </td>
                                        </tr>
                                        <tr>

                                            <td style="width: 200px;">City</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.city}</font></b>
                                            </td>


                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <c:if test="${isConfirmMode==0}">
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
                                                <td colspan="6" ><hr /></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>System Recommended</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Card Type</td>
                                            <td>
                                                <b><font color="#000000"> </font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Card Products</td>
                                            <td>
                                                <b><font color="#000000"></font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000"></font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Applicant Requested</font></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Card Type</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.cardTypeDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Card Product</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.cardProductDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${personalDetail.currencyTypeAlphaCode} ${requestedCreditLimit}</font></b>
                                            </td>
                                        </tr>
                                        <c:if test="${isConfirmMode==0}">
                                            <tr>
                                                <td colspan="6" ><hr /></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Credit Officer Recommendation</font></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Card Product</td>
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
                                                <td style="width: 100px;">Credit Limit (${personalDetail.currencyTypeAlphaCode})</td>
                                                <td><input type="text" name="crdtOfficerRecCreditLimit" id="crdtOfficerRecCreditLimit" value="${climit}"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Choose the letter To Upload</td>

                                                <td><input id="control" name="recommendationLetter" type="file"/></td>

                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td>
                                                    <table>
                                                        <td><input type="submit" value="Submit" name="next" style="width: 100px" onclick="uploadLetters('${personalDetail.applicationId}')"> </ input></td>
                                                        <td><input type="button" value="Reset" name="next" style="width: 100px" onclick="invokeReset()"> </ input></td>
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
                                                    <b><font color="#000000">${personalDetail.currencyTypeAlphaCode} <fmt:formatNumber type="number" value="${cardApplicationList.cOfficerRecCrditLimt}"/></font>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Card Product</td>
                                                <c:forEach var="products" items="${productList}">
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
                                        <input type="hidden" name="applicationid" value="${cardApplicationList.applicationId}" />
                                        <input type="hidden" name="currency" value="${personalDetail.cardCurrency}" />
                                        <input type="hidden" name="businessRegNo" value="${establishmentDetailsBean.businessRegNumber}" />
                                        <input type="hidden" name="establishmentCreditLimit" value="${esablishmentCard.creditLimit}" />
                                        <!--Identification Number-->
                                        <input type="hidden" name="identificationNum" value="${cardApplicationList.identificationNumber}"/>
                                        <!--Identification Type-->
                                        <input type="hidden" name="identificationType" value="${cardApplicationList.identificationType}"/>

                                        <div class="outset" style="border-style:outset; background-color: #B8B8B8 ;  border-color: #999; width: 100%">
                                            <table><td><b><font color="#FF0000" id="errorMsgcon"> ${errorMsgcon}</font></b> </td></table>
                                            <table cellpadding="0" cellspacing="10"  >      
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Confirm</font></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Card Products</td>
                                                    <td>
                                                        <select id="confirmCardProduct" name="confirmCardProduct" onchange="LoadBinProfiles('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')" class="inputfield-mandatory">
                                                            <option value="" style="width: 100px;" selected>--SELECT--</option>
                                                            <c:forEach var="card" items="${cardProductList}">

                                                                <c:if test="${card.cardType!='No'}">
                                                                    <option value="${card.cardProductCode}">${card.cardproductDescription}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Credit Limit</td>
                                                    <td>
                                                        <input type="text" id="creditLmt" name="creditLimit" style="width: 120px;" value="${cardApplicationList.cOfficerRecCrditLimt}"></input>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="hidden" id="staff" name="staff" value="${cardApplicationList.staffStatus}" hidden="true"></input>        
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Bin Profile</td>
                                                    <td>
                                                        <select id="binProfile" name="binProfile"  class="inputfield-mandatory" onchange="LoadproductionMode('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')">
                                                            <option value="" selected>--SELECT--</option>                                           
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Production Mode</td>

                                                    <td>
                                                        <select  name="productionMode" id="productionMode" class="inputfield-mandatory" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="pMode" items="${productionModeList}">

                                                                <c:if test="${pageBean.productionModeCode==pMode.productionModeCode}">
                                                                    <option value="${pMode.productionModeCode}" selected>${pMode.description}</option>
                                                                </c:if>
                                                                <c:if test="${pageBean.productionModeCode!=pMode.productionModeCode}">
                                                                    <option value="${pMode.productionModeCode}" >${pMode.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Customer Template</td>
                                                    <td>
                                                        <select id="customerTemplate" name="customerTemplate" onchange="loadAccountTemplates('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Account Template</td>
                                                    <td>
                                                        <select id="accountTemplates" name="accountTemplates"  onchange="loadCardTempList('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                        </select>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"><font style="color: red;">* </font>Card Template</td>
                                                    <td>
                                                        <select id="cardtemplate" name="cardtemplate"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>    
                                                        </select>
                                                        <input type="hidden" id="cardCategoryCode" name="cardCategoryCode" value="C" hidden="true"></input>
                                                    </td> 
                                                </tr>

                                                <tr>

                                                    <td>
                                                        <table>

                                                            <td><input type="button" value="Confirm"  onclick="Confirm('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')" style="width: 100px"></input></td>
                                                            <td><input type="button" value="Reset" style="width: 100px" onclick="resetConfirmation()"></input></td>

                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>

                                        <br/>
                                        <div class="outset"   style="width: 100%">
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
