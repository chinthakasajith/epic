<%-- 
    Document   : supplemantaryapplicationapproveview
    Created on : Apr 19, 2012, 4:32:08 PM
    Author     : mahesh_m
--%>
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


            function invokeSearch()
            {

                document.searchappassignform.action = "${pageContext.request.contextPath}/SearchApplicationServlet";
                document.searchappassignform.submit();

            }

            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet";

            }

            function invokeWindow(identityType) {

                var googlewin = dhtmlwindow.open("identitybox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp", identityType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal")
            }

            function downloadLetter(applicationId) {

                window.location = "${pageContext.request.contextPath}/DownloadLetterServlet?applicationId=" + applicationId;

            }

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
//                    document.confirmForm.action="${pageContext.request.contextPath}/LoadSupplementaryApproveDetailsServlet?applicationid="+applicationid;
//                }


                $('#errorMsg').text("");

//                answer = confirm("Are you sure you want to confirm this application?")
//                if (answer != 0)
//                {
//                    //document.confirmForm.action="${pageContext.request.contextPath}/ConfirmServlet?applicationid="+applicationid+"&currency="+currency;
//
//                    $.ajax({
//                        url: "${pageContext.request.contextPath}/ConfirmServlet",
//                        type: "POST",
//                        data: $("#confirmForm").serialize(),
//                        success: function (data) {
//                            var ar = data.split(",", 2);
//                            //                        ar=data;
//                            if (ar[0] == 'success') {
//                                $('#succMes').val(ar[1]);
//                                $('#succForm').submit();
//                            } else {
//                                $("#errorMsgcon").text(data);
//                            }
//
//                        }
//                    });
//
//
//
//
//                }
                $("#dialog-confirm").html("<p>Are you sure you want to confirm " + applicationid + " application ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog('close');
                        },
                        "Yes": function () {
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
                                        $('#dialog-confirm').dialog('close');
                                    }

                                }
                            });
                        }
                    }
                });

            }

            function resetConfirmation() {

                $("#creditLmt").val("");
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
                    document.confirmForm.action = "${pageContext.request.contextPath}/ApplicationRejectServlet?applicationid=" + applicationid;
                }
                else
                {
                    document.confirmForm.action = "${pageContext.request.contextPath}/LoadSupplementaryApproveDetailsServlet?applicationid=" + applicationid;
                }

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
        </script>

        <title>EPIC CMS APPLICATION CONFIRMATION</title>

    </head>
    <body>



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <form name="sucMessage" id="succForm" style="background: #FFFFFF;border: 0px;padding: 0px"action="${pageContext.request.contextPath}/LoadApplicationApproveTableServlet">
                                    <input type="hidden" name="successMsg" id="succMes"></input>
                                </form>

                                <table class="tit"> <tr> <td   class="center">  APPLICATION CONFIRMATION </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <form name="confirmForm" action="" method="POST" id="confirmForm">
                                    <input type="hidden" name="applicationid" value="${cardApplicationList.applicationId}" hidden="true"/>
                                    <input type="hidden" name="currency" value="${personalDetail.cardCurrency}" hidden="true"/>
                                    <!--Identification Number-->
                                    <input type="hidden" name="identificationNum" value="${cardApplicationList.identificationNumber}"/>
                                    <!--Identification Type-->
                                    <input type="hidden" name="identificationType" value="${cardApplicationList.identificationType}"/>

                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td style="width: 200px;"> Application ID</td>
                                            <td><b><font color="#000000"> ${cardApplicationList.applicationId}</font></b> </td>

                                            <td style="width: 240px;"></td>

                                            <td style="width: 200px;"> Card Category</td>
                                            <td><b><font color="#000000"> Supplementary</font></b> </td>
                                        </tr>



                                    </table>




                                    <c:if test="${primaryCard == '0'}">
                                        <font style="color: #999; "> <br /> Primary Card Application Details<br /></font><br />

                                        <table  border="0"  class="display" id="tableview1">
                                            <thead>
                                                <tr >
                                                    <th>Application Id </th>
                                                    <th>Card Type</th>
                                                    <th>Card Product</th>
                                                    <th>Credit Limit</th>
                                                    <th>Application Status</th>

                                                </tr>
                                            </thead>
                                            <tbody>

                                                <tr>
                                                    <td>${primaryAppDetails.applicationId}</td>
                                                    <td >${primaryAppDetails.cardType}</td>
                                                    <td >${primaryAppDetails.cardProduct}</td>
                                                    <td >${currencyAlphaCode}&nbsp;<fmt:formatNumber type="number" value="${primaryAppDetails.creditLimit}"/></td>
                                                    <td >${primaryAppDetails.appStatust}</td>

                                                </tr>

                                            </tbody>
                                        </table>
                                        <input type="hidden" value="false" name="hasPrimCard" />

                                    </c:if>

                                    <c:if test="${primaryCard == '1'}">
                                        <font style="color: #999"> <br />Cards of Primary Card Holder<br /></font><br />

                                        <table  border="0"  class="display" id="tableview1">
                                            <thead>
                                                <tr >
                                                    <th>Card Number </th>
                                                    <th>Card Type</th>
                                                    <th>Card Product</th>
                                                    <th>Credit Limit</th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="card" items="${primaryCardList}">
                                                    <tr>
                                                        <td>${card.cardNumber}
                                                        </td>
                                                        <td >${card.cardType}</td>
                                                        <td >${card.cardproduct}</td>
                                                        <td >
                                                            ${currencyAlphaCode}&nbsp;<fmt:formatNumber type="number" value="${card.creditLimit}"/>
                                                            <input type="hidden" value="${card.creditLimit}" name="mainCreditLimit" />
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                        <input type="hidden" value="${card.creditLimit}" name="mainCardNumber" />
                                        <input type="hidden" value="true" name="hasPrimCard" />
                                    </c:if>
                                    <input type="hidden" value="${primaryAppDetails.applicationId}" name="mainApplicationId" />
                                    <input type="hidden" value="${personalDetail.primaryCardNumber}" name="primaryCardNumber" />



                                    <br/>

                                    <div class="selector" id="tabs">
                                        <ul>
                                            <li><a href="#tabs-1">Personal Information </a></li>
                                            <li><a href="#tabs-2">Contact Information</a></li>
                                            <li><a href="#tabs-3">Applicant Requested</a></li>

                                        </ul>            

                                        <div id="tabs-1" >

                                            <table cellpadding="0" cellspacing="10"  >
                                                <tr></tr>


                                                <tr>
                                                    <td style="width: 200px;">Title</td>

                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.title}</font></b>
                                                    </td>

                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">Name with Initials</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.nameWithinitials}</font></b>
                                                    </td>
                                                    <td style="width: 100px;"></td> 

                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">First Name</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.firstName}</font></b>
                                                    </td>


                                                    <td style="width: 200px;"> Gender</td>    
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.gender}</font></b>
                                                    </td>
                                                    <td style="width: 100px;"></td> 
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Last Name</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.lastName}</font></b>
                                                    </td>


                                                    <td style="width: 200px;"> Middle Names</td>    
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.middleName}</font></b>
                                                    </td>
                                                    <td style="width: 100px;"></td> 
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Name on the Card</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.nameOncard}</font></b>
                                                    </td>



                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.birthday}</font></b>
                                                    </td>

                                                    <td style="width: 100px;"></td> 

                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">NIC</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.nic}</font></b>
                                                    </td>


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   
                                                    <td style="width: 200px;"> Nationality</td>    
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.nationality}</font></b>
                                                    </td>
                                                    <td style="width: 100px;"></td> 
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Passport No</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.passportNumber}</font></b>
                                                    </td>


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   
                                                    <td style="width: 200px;"> Relationship</td>    
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.relationShip}</font></b>
                                                    </td>
                                                    <td style="width: 100px;"></td> 
                                                </tr>
                                            </table>
                                        </div>   

                                        <div id="tabs-2" >

                                            <table cellpadding="0" cellspacing="10"  >         



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

                                                    <td style="width: 200px;"> Occupation</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.occupationTypeDes}</font></b>
                                                    </td>
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">City</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.cityDes}</font></b>
                                                    </td>


                                                </tr>

                                            </table>   
                                        </div>

                                        <div id="tabs-3" >
                                            <table cellpadding="0" cellspacing="10"  >  

                                                <tr>
                                                    <td style="width: 200px;">Credit Limit</td>
                                                    <td>
                                                        <b><font color="#000000"> ${currencyAlphaCode}&nbsp;<fmt:formatNumber type="number" value="${personalDetail.creditLimit}"/></font></b>
                                                    </td>
                                                    <c:if test="${personalDetail.percentageValue!=null}">
                                                        <td>
                                                            <b><font color="#000000">${personalDetail.percentageValue}%</font></b>&nbsp;<font style="color: #999">[As a percentage of primary card credit limit]</font>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Card Type</td>
                                                    <td>
                                                        <b><font color="#000000"> ${personalDetail.cardTypeDes}</font></b>
                                                    </td>

                                                </tr>


                                            </table>
                                        </div>
                                    </div>
                                    <!--                                    </table>-->



                                    <!--                                        <table>-->

                                    <table cellpadding="0" cellspacing="10"  >  
                                        <tr>   
                                            <td colspan="6" ><font style="color: #999"> <br />System Recommended</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Type</td>
                                            <td>
                                                <b><font color="#000000"> ${sysRecomendedDetails.cardTypeDes}</font></b>
                                            </td>
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
                                                <b><font color="#000000">${currencyAlphaCode}&nbsp; <fmt:formatNumber type="number" value="${sysRecomendedDetails.creditLimit}"/></font></b>
                                            </td>
                                        </tr>

                                        <tr>   
                                            <td colspan="6" ><font style="color: #999"> <br />Credit Officer Recommended</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Product</td>
                                            <c:forEach var="product" items="${products}">
                                                <c:if test="${product.productCode==cardApplicationList.cOfficerRecCardProduct}">
                                                    <td><font color="#000000"><b>${product.description}</b></font></td>
                                                        </c:if>
                                                    </c:forEach>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td><font color="#000000"><b>${currencyAlphaCode}&nbsp;<fmt:formatNumber type="number" value="${cardApplicationList.cOfficerRecCrditLimt}"/></b></font></td>
                                        </tr>


                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br/>Recommendation Letter</font></td>
                                        </tr>
                                        <tr>
                                            <!--                                    <FORM  ENCTYPE="multipart/form-data" name="download" METHOD=POST>-->

                                        <tr>
                                            <td>
                                                <table>
                                                    <!--                                                    <td><input type="button" value="View" name="next" style="width: 100px"></input></td>-->
                                                    <td><input type="button" value="Download" name="next" style="width: 100px" onclick="downloadLetter('${cardApplicationList.applicationId}')" ></input></td>
                                                </table>
                                            </td>
                                        </tr>



                                        </tr>

                                    </table>

                                    <table><td><b><font color="#FF0000" id="errorMsgcon"> ${errorMsgcon}</font></b> </td></table>
                                    <div class="outset" style="border-style:outset; background-color: #B8B8B8 ;  border-color: #999; width: 100%">
                                        <table cellpadding="0" cellspacing="10"  >      
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Confirm</font></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><font style="color: red;">* </font>Card Product</td>
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
                                                        <c:forEach var="pMode" items="${productionModeListt}">

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
                                                    <input type="hidden" id="cardCategoryCode" name="cardCategoryCode" value="S" hidden="true"></input>
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
                                    <div class="outset"   width: 100%">
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


                                <!--   ------------------------- end developer area  --------------------------------                      -->
                            </div>

                        </div>
                    </div>

                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

