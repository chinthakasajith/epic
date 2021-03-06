<%-- 
    Document   : establishmentInputCaptureData
    Created on : Jun 29, 2016, 3:49:45 PM
    Author     : prageeth_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>O
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            $(function() {

                $("#tabs2").tabs();

                $(".selector").tabs({selected: ${selectedtab}});
                $(".nexttab").click(function() {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected + 1);
                });
                $(".previoustab").click(function() {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected - 1);
                });

                $("#tabs2").tabs("option", "disabled", [${loadTabIndex}]);
            });

        </script>
        <script language="javascript">


            function invokeCancel() {
                window.location = "${pageContext.request.contextPath}/SearchUserAssignDataServlet";

            }
            function invokeReset(applicationId) {
                window.location = "${pageContext.request.contextPath}/LoadDefaultDataServlet?appliactionid=" + applicationId;

            }
            function invokeRemoveDocumentData(filename) {

                window.location = "${pageContext.request.contextPath}/RemoveDocumentDataFromSessionServlet?filename=" + filename;
            }
            function setResAddress() {

                var isCheck = $('#checkRes:checked').val();

                if (isCheck == 'on') {

                    $("#resAddress1").val($("#address1").val());
                    $("#resAddress2").val($("#address2").val());
                    $("#resAddress3").val($("#address3").val());
                    $('#ResCity option').each(function() {
                        if ($(this).val() == $("#city option:selected").val()) {
                            $(this).attr("selected", true);
                        }
                    });

                    $("#resDistrict").val($("#district").val());
                    $("#resProvince").val($("#province").val());
                } else {

                    $("#resAddress1").val("");
                    $("#resAddress2").val("");
                    $("#resAddress3").val("");
                    $('#ResCity option').each(function() {
                        if ($(this).val() == "") {
                            $(this).attr("selected", true);
                        }
                    });
                    $("#resDistrict").val("");
                    $("#resProvince").val("");
                }


            }

            function setBillAddress() {

                var isCheck = $('#checkBill:checked').val();

                if (isCheck == 'on') {

                    $("#billAddress1").val($("#address1").val());
                    $("#billAddress2").val($("#address2").val());
                    $("#billAddress3").val($("#address3").val());
                    $('#billCity option').each(function() {
                        if ($(this).val() == $("#city option:selected").val()) {
                            $(this).attr("selected", true);
                        }
                    });

                    $("#billDistrict").val($("#district").val());
                    $("#billProvince").val($("#province").val());
                } else {

                    $("#billAddress1").val("");
                    $("#billAddress2").val("");
                    $("#billAddress3").val("");
                    $('#billCity option').each(function() {
                        if ($(this).val() == "") {
                            $(this).attr("selected", true);
                        }
                    });
                    $("#billDistrict").val("");
                    $("#billProvince").val("");
                }

            }

            function setDistrictAndProvince(value) {


                if (value == 'res') {


                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                            {area: $('#ResCity option:selected').val()

                            },
                    function(data) {
                        if (data != '') {

                            var array = data.split(',', 2)

                            $("#resDistrict").val(array[0]);
                            $("#resProvince").val(array[1]);

                        }


                    });
                }
                if (value == 'bill') {
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                            {area: $('#billCity option:selected').val()

                            },
                    function(data) {
                        if (data != '') {

                            var array = data.split(',', 2)

                            $("#billDistrict").val(array[0]);
                            $("#billProvince").val(array[1]);

                        }


                    });
                }
                if (value == 'perm') {
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                            {area: $('#city option:selected').val()

                            },
                    function(data) {
                        if (data != '') {

                            var array = data.split(',', 2)


                            $("#district").val(array[0]);
                            $("#province").val(array[1]);

                        }


                    });
                }
                if (value == 'rel') {
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                            {area: $('#relcity option:selected').val()

                            },
                    function(data) {
                        if (data != '') {

                            var array = data.split(',', 2)


                            $("#relDistrict").val(array[0]);
                            $("#relProvince").val(array[1]);

                        }


                    });
                }

            }


            function invorkSetDocumentTypes(varifyCategory, cardCategory) {

                //window.location = "${pageContext.request.contextPath}/SetDocumentTypesOnchangeServlet?category=" + category;

                $('#_dType').empty();
                $.getJSON("${pageContext.request.contextPath}/LoadIdentityTypeServlet",
                        {
                            varifyCategory: varifyCategory,
                            cardCategory: cardCategory
                        },
                function(jsonobject) {
                    $("#_dType").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#_dType').append(
                                $('<option></option>').val(code).html(description)
                                );
                    });
                });

            }




            function changeProducts(selected)
            {

                $.post("${pageContext.request.contextPath}/SetProductDropDownServlet",
                        {cardType: $('#cardType option:selected').val()

                        },
                function(data) {
                    if (data != '') {

                        var array = data.split('|', 2)
                        var codes = array[0].split(',')
                        var descriptions = array[1].split(',')



                        $('#cardProduct option').each(function() {
                            $(this).remove()
                        });

                        $('#cardProduct').append(
                                $('<option></option>').val('').html('--SELECT--')
                                );
                        for (var x = 0; x < codes.length; x++) {
                            if (codes[x] == selected) {
                                $('#cardProduct').append(
                                        $('<option selected=""></option>').val(codes[x]).html(descriptions[x])
                                        );
                            } else {
                                $('#cardProduct').append(
                                        $('<option ></option>').val(codes[x]).html(descriptions[x])
                                        );
                            }

                        }

                        //                       

                    }


                });



            }


            function checkForDocSelect(ob, fileElementName) {
                if ($('#' + fileElementName).val() == null || $('#' + fileElementName).val() == "") {
                    $("#" + fileElementName + "_error").empty();
                    $("#" + fileElementName + "_error").append("Select a file");
                } else {
                    $("#" + fileElementName + "_error").empty();
                    $(ob).closest("form").submit();
                }
            }

            function setHiddenDropDownSelectValue(ob, target_element_id) {

                var element_id = $(ob).attr('id');
                var selectedVal = $("#" + element_id + " option:selected").text();
                $("#" + target_element_id).val(selectedVal);

            }



            function invokeRemoveEstablishmentAssetLiabilityList(assetCode, assetTypeCode, type) {

                window.location = "${pageContext.request.contextPath}/RemoveEstablishmentAssetLiabilityFromSessionServlet?assetCode=" + assetCode + "&assetTypeCode=" + assetTypeCode + "&type=" + type;
            }


            function changeBranches()
            {


                $.post("${pageContext.request.contextPath}/SetBankBranchDropDownServlet",
                        {bankcode: $('#bankName option:selected').val()

                        },
                function(data) {
                    if (data != '') {

                        var array = data.split('|', 2)
                        var codes = array[0].split(',')
                        var descriptions = array[1].split(',')



                        $('#brachName option').each(function() {
                            $(this).remove()
                        });

                        $('#brachName').append(
                                $('<option></option>').val('').html('--SELECT--')
                                );
                        for (var x = 0; x < codes.length; x++) {

                            $('#brachName').append(
                                    $('<option ></option>').val(codes[x]).html(descriptions[x])
                                    );


                        }

                        //                       

                    } else {
                        $('#brachName option').each(function() {
                            $(this).remove()
                        });

                        $('#brachName').append(
                                $('<option></option>').val('').html('--SELECT--')
                                );
                    }


                });

            }

        </script>
        <!--automati settlement-->
        <script type="text/javascript">

            $(document).ready(function() {
                var isCheck = $('#_chbAutoSettle:checked').val();
                if (isCheck == 'on') {
                    $("#_inptAutoSettleData").css('display', 'inline-block');
                } else {
                    $("#_inptAutoSettleData").css('display', 'none');
                }
            });

            function showSettleForm() {
                var isCheck = $('#_chbAutoSettle:checked').val();
                if (isCheck == 'on') {
                    $("#_inptAutoSettleData").css('display', 'inline-block');
                } else {
                    $("#_inptAutoSettleData").css('display', 'none');
                }
            }

            $(document).ready(function() {
                var len = $("#_settleAccNum").children('option').length;
                if (len > 1) {
                    $("#_autoSettle").css('display', 'inline-block');

                }
            });

        </script>


        <style type="text/css">
            input,  textarea {
                /*	float:left;*/
                font-family:Arial, Helvetica, sans-serif;
                font-size: 12px;
                padding:2px;
                border: 1px solid #DBD1C7;
                -moz-border-radius: 5px;
                -webkit-border-radius: 5px;
                margin: 3px 5px 0px 0px;
                text-transform: uppercase;
                //background-color: #FFFFFF;

            }
        </style>

    </head>
    <body >

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>


        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main"  >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  APPLICATION INITIATE </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr >
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <table cellpadding="0" cellspacing="10">
                                    <tr>
                                        <td style="width: 110px;"> Application ID :</td>
                                        <td><label> ${sessionScope.SessionObject.applicationId} </label> </td>
                                        <td style="width: 100px;"></td> 
                                        <td style="width: 110px;"> Card Category :</td>
                                        <td><label> ${sessionScope.SessionObject.cardCategory} </label> </td>
                                    </tr>
                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Establishment Details </a></li>
                                        <li><a href="#tabs-2">Assets and Liabilities</a></li>
                                        <li><a href="#tabs-3">Bank</a></li>
                                        <li><a href="#tabs-4">Document</a></li>                                        
                                        <li><a href="#tabs-5">Signature/Recommendation</a></li>

                                    </ul>
                                    <br>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form name="customerpersonalinfoform" method="POST" action="<%=request.getContextPath()%>/AddEstablishmentDetailServlet">

                                            <br /><font style="color: #999">Company Information</font>   
                                            <table cellpadding="0" cellspacing="10">


                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp;<select name="idType" disabled="true">
                                                            <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                                <c:if test="${establishmentDetailsBean.identificationType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${establishmentDetailsBean.identificationType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>        


                                                        </select></td>





                                                    <td style="width: 50px;" > <label ></label></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Number
                                                    </td>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp; <label name="identificationNo" >${establishmentDetailsBean.identificationNumber}</label></td>

                                                    <td style="width: 50px;" > <label style="color: red">${invalidMsgBean.identificationNumber} </label></td>



                                                </tr>
                                                <!--new-->
                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Name of the company</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="companyName" style="width: 300px;" maxlength="32" value="${establishmentDetailsBean.companyName}" tabindex="7"></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.companyName}</label>
                                                    </td> 
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Nature of Business</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="natureOfTheBusiness" style="width: 300px;" maxlength="32" value="${establishmentDetailsBean.natureOfTheBusiness}" tabindex="7"></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.natureOfTheBusiness}</label>
                                                    </td> 
                                                </tr>
                                            </table>                                                    

                                            <table cellpadding="0" cellspacing="10"> 

                                                <tr>

                                                    <td style="width: 200px;">Annual turnover</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" onkeypress="allowOnlyNumbers(event)" name="annualTurnover"  maxlength="32" value="${establishmentDetailsBean.annualTurnover}" tabindex="60"></td>
                                                    <td style="width: 50px;"> 
                                                        <label style="color: red">${invalidMsgBean.annualTurnover}</label>
                                                    </td>

                                                    <td style="width: 200px;">Share capital</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="shareCapital" onkeypress="allowOnlyNumbers(event)"  maxlength="32" value="${establishmentDetailsBean.shareCapital}" tabindex="60"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.shareCapital}</label> 
                                                    </td>


                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Net profit</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input onkeypress="allowOnlyNumbers(event)" type="text" name="netProfit"  maxlength="32" value="${establishmentDetailsBean.netProfit}" tabindex="60"></td>
                                                    <td style="width: 50px;" colspan="5"> 
                                                        <label style="color: red">${invalidMsgBean.netProfit}</label>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>

                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td colspan="6" ><hr /></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br />Contact Details</font></td>
                                                </tr>                                               
                                                <tr>
                                                    <td style="width: 200px;">Contact Person Full Name</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="contactPersonFullName" style="width: 300px;" maxlength="32" value="${establishmentDetailsBean.contactPersonFullName}" tabindex="7"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.contactPersonFullName}</label> 
                                                    </td>    
                                                    <td style="width: 100px;"></td> 
                                                    <td></td>
                                                    <td></td>  
                                                    <td style="width: 50px;" >

                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td style="width: 200px;">Business Address</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="address1" id="address1" maxlength="32" value="${establishmentDetailsBean.address1}" tabindex="14"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.address1}</label> 
                                                    </td>    
                                                    <td style="width: 200px;"> Contact Person Land No</td> 
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="contactPersLandTelNumber"  maxlength="20" value="${establishmentDetailsBean.contactPersLandTelNumber}" tabindex="18"></td>  
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.contactPersLandTelNumber}</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td><input type="text" name="address2" id="address2" maxlength="32" value="${establishmentDetailsBean.address2}" tabindex="15"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.address2}</label>  
                                                    </td>    
                                                    <td style="width: 200px;"> Contact Person Mobile No</td>    
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="contactPersMobileNumber"  maxlength="20" value="${establishmentDetailsBean.contactPersMobileNumber}" tabindex="19"></td>  
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.contactPersMobileNumber}</label>
                                                    </td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td><input type="text" name="address3" id="address3"  maxlength="32" value="${establishmentDetailsBean.address3}" tabindex="16"></td>

                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.address3}</label>   
                                                    </td>  
                                                    <td style="width: 200px;"> </td>   
                                                    <td></td>
                                                    <td></td>  
                                                    <td style="width: 50px;" > 
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="email" style="width: 300px;" maxlength="32" value="${establishmentDetailsBean.email}" tabindex="21"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.email}</label>
                                                    </td>
                                                    <td style="width: 200px;">Fax</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="faxNo"  maxlength="32" value="${establishmentDetailsBean.faxNo}" tabindex="21"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.faxNo}</label>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table cellpadding="0" cellspacing="10"> 
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br />Card Details</font></td>
                                                </tr>   
                                                <tr>

                                                    <td style="width: 200px;">Requested Card Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="cardType" id ="cardType" tabindex="57" onchange="changeProducts('${establishmentDetailsBean.cardProduct}')">

                                                            <c:if test="${establishmentDetailsBean.cardType != null}">
                                                                <c:forEach var="cardTypeList" items="${sessionScope.SessionObject.cardTypeList}">
                                                                    <c:if test="${establishmentDetailsBean.cardType == cardTypeList.cardTypeCode}">
                                                                        <option value="${cardTypeList.cardTypeCode}" selected="true">${cardTypeList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="cardTypeList" items="${sessionScope.SessionObject.cardTypeList}">
                                                                <c:if test="${establishmentDetailsBean.cardType != cardTypeList.cardTypeCode}">
                                                                    <option value="${cardTypeList.cardTypeCode}">${cardTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach>    

                                                        </select></td>
                                                    <td style="width: 50px;"> </td>
                                                    <td style="width: 200px;">Requested Currency</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="cardCurency"  tabindex="59">
                                                            <c:if test="${establishmentDetailsBean.cardCurrency != null}">
                                                                <c:forEach var="currencyList" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                    <c:if test="${establishmentDetailsBean.cardCurrency == currencyList.currencyCode}">
                                                                        <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="currencyList" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${establishmentDetailsBean.cardCurrency != currencyList.currencyCode}">
                                                                    <option value="${currencyList.currencyCode}">${currencyList.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>  
                                                        </select></td>



                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Requested Card Product</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="cardProduct" id="cardProduct" tabindex="58"></select></td>
                                                    <td style="width: 50px;"> 
                                                        <label style="color: red">${invalidMsgBean.cardProduct}</label>
                                                    </td>

                                                    <td style="width: 200px;">Requested Credit Limit</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="creditLimit" onkeypress="allowOnlyNumbers(event)" maxlength="32" value="${establishmentDetailsBean.creditLimit}" tabindex="60"></td>
                                                    <td style="width: 50px;" >
                                                        <label  style="color: red">${invalidMsgBean.creditLimit}</label> 
                                                    </td>


                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>

                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td colspan="2"><input type="submit" name="savenext" value="Save & Next"  style="width: 100px;" tabindex="61"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;" tabindex="62"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;" tabindex="63"/></td>
                                                    <td>
                                                        <input type="hidden" value="${establishmentDetailsBean.identificationType}" name="hidIdType">
                                                        <input type="hidden" value="${establishmentDetailsBean.identificationNumber}" name="hidIdNumber">
                                                    </td>

                                                </tr>

                                            </table>

                                            <script>

                                                changeProducts('${establishmentDetailsBean.cardProduct}');



                                            </script>    


                                        </form>

                                    </div>
                                    <!--   tab 2 Assets and Liabilities -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                    <div id="tabs-2">
                                        <form name="assetsAndLiabilities" method="POST" action="<%=request.getContextPath()%>/SetAssetDataToSessionServlet">
                                            <font style="color: #999">Asset Information</font>
                                            <table  cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Type </td>
                                                    <td><select name="assetTypeCode" id="assetTypeCode" onchange="setHiddenDropDownSelectValue(this, 'assetCodeDes')">
                                                            <option value="">--Select Asset--</option>
                                                            <c:forEach var="assetsLiabilityTypeList" items="${sessionScope.SessionObject.assetsLiabilityTypeList}">

                                                                <option value="${assetsLiabilityTypeList.typeCode}">${assetsLiabilityTypeList.description}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;">
                                                        <label style="color: red">${invalidEstablishmentAssetsBean.assetTypeCode} </label>
                                                        <input type="hidden"  name="assetCodeDes" id="assetCodeDes">
                                                    </td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Asset </td>
                                                    <td><select name="assetCode" id="assetCode" onchange="setHiddenDropDownSelectValue(this, 'assetTypeCodeDes')">
                                                            <option value="">--Select Asset--</option>
                                                            <c:forEach var="assetsList" items="${sessionScope.SessionObject.assetsList}">

                                                                <option value="${assetsList.assetCode}">${assetsList.description}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;">
                                                        <label style="color: red">${invalidEstablishmentAssetsBean.assetCode} </label>
                                                        <input type="hidden"  name="assetTypeCodeDes" id="assetTypeCodeDes">
                                                    </td>

                                                </tr> 

                                                <tr>
                                                    <td style="width: 200px;">Value</td>
                                                    <td><input type="text" name="assetValue"  onkeypress="allowOnlyNumbers(event)" maxlength="16"  value="${establishmentAssetsBean.assetValue}">
                                                    </td>
                                                    <td style="width: 100px;"> <label style="color: red">${invalidEstablishmentAssetsCommonError} </label>

                                                    </td>



                                                </tr> 

                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="submit" name="add"  style="width: 100px;"  value="Add" >
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                            </table>
                                        </form>

                                        <form name="assetGridForm" method="POST" action="">
                                            <table>
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr> 
                                                <tr>

                                                    <td colspan="3">

                                                        <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview">
                                                            <thead >
                                                                <tr  class="gradeB">
                                                                    <th width="100px;" scope="col">Asset Type</th>
                                                                    <th width="100px;" scope="col">Name of the Asset</th>
                                                                    <th width="100px;" scope="col">Value</th>         
                                                                    <th width="100px;" scope="col">Delete</th>

                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="establishmentAssetList" items="${sessionScope.SessionObject.establishmentAssetList}">
                                                                    <tr class="gradeC">                                                                       

                                                                        <td >
                                                                            ${establishmentAssetList.typeDes} 
                                                                        </td>
                                                                        <td >
                                                                            ${establishmentAssetList.nameDes}
                                                                        </td>
                                                                        <td >
                                                                            ${establishmentAssetList.assetValue}
                                                                        </td>
                                                                        <td >
                                                                            <input type="button" value="Delete" onclick="invokeRemoveEstablishmentAssetLiabilityList('${establishmentAssetList.assetCode}', '${establishmentAssetList.assetTypeCode}', 'asset')">
                                                                        </td>

                                                                    </tr>
                                                                </c:forEach>


                                                            </tbody>


                                                        </table>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>
                                            </table>
                                        </form>

                                        <form name="liabilitysAndLiabilities" method="POST" action="<%=request.getContextPath()%>/SetLiabilityDataToSessionServlet">
                                            <font style="color: #999">Liability Information</font>
                                            <table  cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Type </td>
                                                    <td><select name="liabilityTypeCode" id="liabilityTypeCode" onchange="setHiddenDropDownSelectValue(this, 'liabilityCodeDes')">
                                                            <option value="">--Select Liability--</option>
                                                            <c:forEach var="assetsLiabilityTypeList" items="${sessionScope.SessionObject.assetsLiabilityTypeList}">

                                                                <option value="${assetsLiabilityTypeList.typeCode}">${assetsLiabilityTypeList.description}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;">
                                                        <label style="color: red">${invalidEstablishmentLiabilityBean.liabilityTypeCode} </label>
                                                        <input type="hidden"  name="liabilityCodeDes" id="liabilityCodeDes">
                                                    </td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Liability </td>
                                                    <td><select name="liabilityCode" id="liabilityCode" onchange="setHiddenDropDownSelectValue(this, 'liabilityTypeCodeDes')">
                                                            <option value="">--Select Liability--</option>
                                                            <c:forEach var="liabilityList" items="${sessionScope.SessionObject.liabilityList}">

                                                                <option value="${liabilityList.liabilityCode}">${liabilityList.description}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;">
                                                        <label style="color: red">${invalidEstablishmentLiabilityBean.liabilityCode} </label>
                                                        <input type="hidden"  name="liabilityTypeCodeDes" id="liabilityTypeCodeDes">
                                                    </td>

                                                </tr> 

                                                <tr>
                                                    <td style="width: 200px;">Value</td>
                                                    <td><input type="text" name="liabilityValue" onkeypress="allowOnlyNumbers(event)"  maxlength="16"  value="${EstablishmentLiabilityBean.liabilityValue}">
                                                    </td>
                                                    <td style="width: 100px;"> <label style="color: red">${invalidEstablishmentLiabilityCommonError} </label>

                                                    </td>



                                                </tr> 

                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="submit" name="add"  style="width: 100px;"  value="Add" >
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                            </table>
                                        </form>
                                        <form name="liabilityGridForm" method="POST" action="">
                                            <table>
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr> 
                                                <tr>

                                                    <td colspan="3">

                                                        <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview2">
                                                            <thead >
                                                                <tr  class="gradeB">
                                                                    <th width="100px;" scope="col">Liability Type</th>
                                                                    <th width="100px;" scope="col">Name of the Liability</th>
                                                                    <th width="100px;" scope="col">Value</th>         
                                                                    <th width="100px;" scope="col">Delete</th>

                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="establishmentLiabilityList" items="${sessionScope.SessionObject.establishmentLiabilityList}">
                                                                    <tr class="gradeC">                                                                       

                                                                        <td >
                                                                            ${establishmentLiabilityList.typeDes} 
                                                                        </td>
                                                                        <td >
                                                                            ${establishmentLiabilityList.nameDes}
                                                                        </td>
                                                                        <td >
                                                                            ${establishmentLiabilityList.liabilityValue}
                                                                        </td>
                                                                        <td >
                                                                            <input type="button" value="Delete" onclick="invokeRemoveEstablishmentAssetLiabilityList('${establishmentLiabilityList.liabilityCode}', '${establishmentLiabilityList.liabilityTypeCode}', 'liability')">
                                                                        </td>

                                                                    </tr>
                                                                </c:forEach>


                                                            </tbody>


                                                        </table>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>
                                            </table>
                                        </form>
                                        <form name="assetsAndLiabilitiesForm" method="POST" action="<%=request.getContextPath()%>/AddAssetAndLiabilityDetailsServlet">
                                            <table>    
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Save & Next" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        </form>  


                                    </div>

                                    <!--   tab 3 Bank -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                    <div id="tabs-3">
                                        <form name="banksessionform" method="POST" action="<%=request.getContextPath()%>/SetAccountDataToSessionServlet">

                                            <table  cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Name of the Bank </td>
                                                    <td><select name="bankName" id="bankName" onchange="changeBranches()">
                                                            <option>--Select Bank Name--</option>
                                                            <c:forEach var="bankList" items="${sessionScope.SessionObject.bankList}">

                                                                <option value="${bankList.bankCode}">${bankList.bankName}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Branch Name </td>
                                                    <td><select name="brachName" id="brachName">

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Account Type </td>
                                                    <td><select name="accType">
                                                            <option value="">--Select Account--</option>
                                                            <option value="Current Account">Current Account</option>
                                                            <option value="Savings Account">Savings Account</option>
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Account Number  </td>
                                                    <td><input type="text" name="accNumber"  maxlength="16"  value="${bankDetailBean.accountNumber}">
                                                    </td>
                                                    <td style="width: 100px;"> <label style="color: red">${invalidAccount} </label></td>



                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Account Since  </td>
                                                    <td>years <select name ="year">
                                                            <!--                                                       <option value="">years</option>-->
                                                            <c:forEach var="years" begin="0" end="50" step="1">

                                                                <option value="${years}">${years}</option>

                                                            </c:forEach> 



                                                        </select>
                                                        &nbsp;months <select name="month">
                                                            <!--                                                         <option value="">months</option>-->
                                                            <c:forEach var="mounth" begin="0" end="12" step="1">

                                                                <option value="${mounth}">${mounth}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="submit" name="add"  style="width: 100px;"  value="Add" >
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                            </table>
                                        </form>
                                        <form name="bankform" method="POST" action="<%=request.getContextPath()%>/AddBankDetailsServlet">

                                            <table  border="1" class="display" id="tableview3">
                                                <thead>
                                                    <tr  class="gradeB">
                                                        <th>Name of the bank</th>
                                                        <th>Branch Name</th>
                                                        <th>Account Type</th>
                                                        <th>Account Number</th>
                                                        <th>Account Since</th>
                                                        <th>Delete</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="bankDetailList" items="${sessionScope.SessionObject.sessionBankDetailList}">
                                                        <tr class="gradeC">
                                                            <td >
                                                                ${bankDetailList.bankNameDes} 
                                                            </td>
                                                            <td >
                                                                ${bankDetailList.branchName}
                                                            </td>
                                                            <td >
                                                                ${bankDetailList.accountType}
                                                            </td>
                                                            <td >
                                                                ${bankDetailList.accountNumber}
                                                            </td>
                                                            <td >
                                                                ${bankDetailList.accountSince}
                                                            </td>
                                                            <td>
                                                                <input type="button" value="Delete" onclick="invokeRemoveBankData('${bankDetailList.bankName}', '${bankDetailList.accountNumber}')">
                                                            </td>

                                                        </tr>
                                                    </c:forEach>


                                                </tbody>

                                            </table>
                                            <br/>
                                            <!--Automatic settlement form start-->
                                            <c:if test="${taskMode!='add'}">
                                                <div id="_autoSettle" style="display: none">
                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr>
                                                            <td style="width: 200px">Automatic Settlement</td>
                                                            <td>
                                                                <input type="checkbox" name="chbAutoSettle" id="_chbAutoSettle" onchange="showSettleForm()"
                                                                       <c:forEach var="bankDetailList" items="${sessionScope.SessionObject.sessionBankDetailList}">
                                                                           <c:if test="${bankDetailList.isAutoSettle=='YES'}">
                                                                               checked="true"
                                                                           </c:if>
                                                                       </c:forEach> 
                                                                       >
                                                            </td>

                                                        </tr>
                                                    </table>
                                                    <div ID="_inptAutoSettleData" style="display: none">
                                                        <table cellpadding="0" cellspacing="10">
                                                            <input type="hidden" name="configBankCode" value="${sessionScope.SessionObject.comConfigBean.bank}"/>
                                                            <tr>
                                                                <td style="width: 200px">Account Number</td>
                                                                <td>
                                                                    <select name="settleAccNum" id="_settleAccNum" >
                                                                        <option value="">-----SELECT-----</option>
                                                                        <c:forEach var="bankDetailList" items="${sessionScope.SessionObject.sessionBankDetailList}">
                                                                            <c:if test="${bankDetailList.bankCode==sessionScope.SessionObject.comConfigBean.bank}">
                                                                                <c:if test="${bankDetailList.isAutoSettle=='YES'}">
                                                                                    <option value="${bankDetailList.accountNumber}" selected="true">${bankDetailList.accountNumber}</option>
                                                                                </c:if>
                                                                                <c:if test="${bankDetailList.isAutoSettle=='NO'}">
                                                                                    <option value="${bankDetailList.accountNumber}">${bankDetailList.accountNumber}</option>
                                                                                </c:if>

                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>

                                                                </td>
                                                                <td style="width: 50px;">
                                                                    <label style="color: red">${invalidBankMsgBean.autoSettleAccNo}</label>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width: 200px">Percentage (5% - 100%)</td>
                                                                <td>
                                                                    <input type="text" name="settlePerValue" id="_settlePerValue" 
                                                                           <c:forEach var="bankDetailList" items="${sessionScope.SessionObject.sessionBankDetailList}">
                                                                               <c:if test="${bankDetailList.isAutoSettle=='YES'}">
                                                                                   value="${bankDetailList.autoSettlePerValue}"
                                                                               </c:if>
                                                                           </c:forEach>
                                                                           />

                                                                </td>

                                                                <td style="width: 50px;">
                                                                    <label style="color: red">${invalidBankMsgBean.autoSettlePerValue}</label>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${taskMode=='add'}">
                                                <div id="_autoSettle" style="display: none">
                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr>
                                                            <td style="width: 200px">Automatic Settlement</td>
                                                            <td>
                                                                <input type="checkbox" name="chbAutoSettle" id="_chbAutoSettle" onchange="showSettleForm()"

                                                                       <c:if test="${autoSettelmentDetailsBean.isAutoSettle=='on'}">
                                                                           checked="true"
                                                                       </c:if>

                                                                       >
                                                            </td>

                                                        </tr>
                                                    </table>
                                                    <div ID="_inptAutoSettleData" style="display: none">
                                                        <table cellpadding="0" cellspacing="10">
                                                            <input type="hidden" name="configBankCode" value="${sessionScope.SessionObject.comConfigBean.bank}"/>
                                                            <tr>
                                                                <td style="width: 200px">Account Number</td>
                                                                <td>
                                                                    <select name="settleAccNum" id="_settleAccNum" >
                                                                        <option value="">-----SELECT-----</option>
                                                                        <c:forEach var="bankDetailList" items="${sessionScope.SessionObject.sessionBankDetailList}">
                                                                            <c:if test="${bankDetailList.accountNumber==autoSettelmentDetailsBean.autoSettleAccNo}">
                                                                                <option value="${bankDetailList.accountNumber}" selected="true">${bankDetailList.accountNumber}</option>
                                                                            </c:if>
                                                                            <c:if test="${bankDetailList.accountNumber!=autoSettelmentDetailsBean.autoSettleAccNo}">
                                                                                <option value="${bankDetailList.accountNumber}">${bankDetailList.accountNumber}</option>
                                                                            </c:if>

                                                                        </c:forEach>

                                                                    </select>

                                                                </td>
                                                                <td style="width: 50px;">
                                                                    <label style="color: red">${invalidBankMsgBean.autoSettleAccNo}</label>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width: 200px">Percentage (5% - 10%)</td>
                                                                <td>
                                                                    <input type="text" name="settlePerValue" id="_settlePerValue" value="${autoSettelmentDetailsBean.autoSettlePerValue}"/>

                                                                </td>

                                                                <td style="width: 50px;">
                                                                    <label style="color: red">${invalidBankMsgBean.autoSettlePerValue}</label>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <!--Automatic settlement form end-->
                                            <br/>
                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td style="width: 200px"></td>
                                                        <td></td>
                                                        <td>
                                                            <input type="submit" name="next" value="Save & Next" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </form>    

                                    </div>



                                    <!--   tab 4 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                    <div id="tabs-4">
                                        <form name="uploadform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadDocumentServlet">

                                            <table  cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Verification Category </td>
                                                    <td><select name="vCategory" onchange="invorkSetDocumentTypes(value, '${sessionScope.SessionObject.applicationTypeCode}')">
                                                            <option value="">Select Category</option>
                                                            <c:forEach var="verificationList" items="${sessionScope.SessionObject.verificationCateLst}">
                                                                <c:if test="${category != verificationList.categoryCode}">
                                                                    <option value="${verificationList.categoryCode}">${verificationList.categoryName}</option>
                                                                </c:if>
                                                                <c:if test="${category == verificationList.categoryCode}">
                                                                    <option value="${verificationList.categoryCode}" selected="true">${verificationList.categoryName}</option>
                                                                </c:if>
                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red">${verificationCategory} </label></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Document Type </td>
                                                    <td><select name="dType" id="_dType">
                                                            <option value="">Select Type</option>
                                                            <c:forEach var="docTypeList" items="${sessionScope.SessionObject.documentTypeLst}">

                                                                <c:if test="${type != docTypeList.typeCode}">
                                                                    <option value="${docTypeList.typeCode}">${docTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${type == docTypeList.typeCode}">
                                                                    <option value="${docTypeList.typeCode}" selected="true">${docTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red">${docType} </label></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Choose File </td>
                                                    <td><input type="file" id="document_file" name="Upload" onchange="getFilePath()"><label id="document_file_error" style="color: red" />
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 


                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="button" name="upload"  style="width: 100px;"  value="Upload" onclick="checkForDocSelect(this, 'document_file')">
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr> 
                                            </table>
                                        </form>
                                        <form method="POST" action="<%=request.getContextPath()%>/UploadDocumentCompleteServlet">
                                            <table  cellpadding="0" cellspacing="10">

                                                <tr>

                                                    <td colspan="3">

                                                        <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview3">
                                                            <thead >
                                                                <tr  class="gradeB">
                                                                    <th width="50px;" scope="col">Application ID</th>
                                                                    <th width="100px;" scope="col">Document Category</th>
                                                                    <th width="100px;" scope="col">Document Type</th>
                                                                    <th width="100px;" scope="col">File Name</th>
                                                                    <th width="100px;" scope="col">Delete</th>


                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="documentList" items="${sessionScope.SessionObject.sessionDocumentList}">
                                                                    <tr class="gradeC">
                                                                        <td >                   
                                                                            ${documentList.applicationId}
                                                                        </td>
                                                                        <td >
                                                                            ${documentList.verificationCategory}
                                                                        </td>
                                                                        <td >
                                                                            ${documentList.documentType}
                                                                        </td>
                                                                        <td >
                                                                            ${documentList.fileName}
                                                                        </td>
                                                                        <td >
                                                                            <input type="button" value="Delete" onclick="invokeRemoveDocumentData('${documentList.fileName}')">
                                                                        </td>


                                                                    </tr>
                                                                </c:forEach>


                                                            </tbody>


                                                        </table>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Next" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>
                                        </form>    

                                    </div>     

                                    <!--            tab 6   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                                         -->
                                    <div id="tabs-5">
                                        <form name="signatureform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadSignatureServlet">
                                            <font style="color: #999"> Upload Signature</font>
                                            <table  cellpadding="0" cellspacing="10">

                                                <tr>
                                                    <td style="width: 200px;">Choose File </td>
                                                    <td><input type="file" id="signature_file" name="Upload" ><label id="signature_file_error" style="color: red" />
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 


                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="button" name="add"  style="width: 100px;"  value="Upload" onclick="checkForDocSelect(this, 'signature_file')">
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                <tr>

                                                    <td colspan="3">

                                                        <img src="<%=request.getContextPath()%>/SignatureLoadServlet?upload=${upload}" />
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>

                                            </table>
                                        </form>

                                        <form name="completeform" method="POST" action="<%=request.getContextPath()%>/ApplicationCompleteServlet">
                                            <table>
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td>

                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Complete" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>
                                        </form>    

                                    </div>    



                                </div>












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
