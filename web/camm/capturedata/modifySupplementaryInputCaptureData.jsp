<%-- 
    Document   : modifySupplementaryInputCaptureData
    Created on : Aug 8, 2016, 11:09:07 AM
    Author     : chinthaka_r
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            $(function () {

                $("#tabs2").tabs();

                $(".selector").tabs({selected: ${selectedtab}});
                $(".nexttab").click(function () {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected + 1);
                });
                $(".previoustab").click(function () {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected - 1);
                });

                $("#tabs2").tabs("option", "disabled", [${loadTabIndex}]);
            });

        </script>
        <script language="javascript">


            function invokeCancel() {
                window.location = "${pageContext.request.contextPath}/SearchUserModifyDataServlet";

            }


            function invokeRemoveDocumentData(filename) {

                window.location = "${pageContext.request.contextPath}/RemoveUpdateDocumentDataFromSessionServlet?filename=" + filename;
            }


            function setDistrictAndProvince(value) {

                if (value == 'res') {


                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                            {area: $('#ResCity option:selected').val()

                            },
                    function (data) {
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
                    function (data) {
                        if (data != '') {

                            var array = data.split(',', 2)

                            $("#billDistrict").val(array[0]);
                            $("#billProvince").val(array[1]);

                        }


                    });
                }


            }

            function changeProducts(selected)
            {

                $.post("${pageContext.request.contextPath}/SetProductDropDownServlet",
                        {cardType: $('#cardType option:selected').val()

                        },
                function (data) {
                    if (data != '') {

                        var array = data.split('|', 2)
                        var codes = array[0].split(',', 3)
                        var descriptions = array[1].split(',', 3)



                        $('#cardProduct option').each(function () {
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

            function invorkSetDocumentTypes(varifyCategory, cardCategory) {

                //window.location = "${pageContext.request.contextPath}/SetDocumentTypesOnchangeServletSup?category="+category;
                $('#_dType').empty();


                $.getJSON("${pageContext.request.contextPath}/LoadIdentityTypeServlet",
                        {
                            cardCategory: cardCategory,
                            varifyCategory: varifyCategory,
                        },
                        function (jsonobject) {
                            $("#_dType").append("<option value=''>---------SELECT----------</option>");
                            $.each(jsonobject.combo1, function (code, description) {
                                $('#_dType').append(
                                        $('<option></option>').val(code).html(description)
                                        );
                            });
                        });

            }


            function setResAddress() {

                var isCheck = $('#checkRes:checked').val();

                if (isCheck == 'on') {

                    $.post("${pageContext.request.contextPath}/LoadPrimaryResidenceDetails",
                            {
                                primaryApplicationId: $('#_primaryApplicationId').val(),
                                primaryCardNumber:$('#_PrimaryCardNumber').val(),

                            },
                    function (data) {
                        if (data != '') {
                            var array = data.split('::', 6);

                            $("#resAddress1").val(array[0]);
                            $("#resAddress2").val(array[1]);
                            $("#resAddress3").val(array[2]);


                            $('#ResCity option').each(function () {
                                if ($(this).val() == array[3]) {
                                    $(this).attr("selected", true);
                                }
                            });

                            $("#resDistrict").val(array[4]);
                            $("#resProvince").val(array[5]);

                        }
                    });
                } else {

                    $("#resAddress1").val("");
                    $("#resAddress2").val("");
                    $("#resAddress3").val("");
                    $('#ResCity option').each(function () {
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

                    $.post("${pageContext.request.contextPath}/LoadPrimaryBillingDetails",
                            {
                                primaryApplicationId: $('#_primaryApplicationId').val(),
                                primaryCardNumber:$('#_PrimaryCardNumber').val(),

                            },
                    function (data) {
                        if (data != '') {
                            var array = data.split('::', 6);

                            $("#billAddress1").val(array[0]);
                            $("#billAddress2").val(array[1]);
                            $("#billAddress3").val(array[2]);


                            $('#billCity option').each(function () {
                                if ($(this).val() == array[3]) {
                                    $(this).attr("selected", true);
                                }
                            });

                            $("#billDistrict").val(array[4]);
                            $("#billProvince").val(array[5]);

                        }
                    });
                } else {

                    $("#billAddress1").val("");
                    $("#billAddress2").val("");
                    $("#billAddress3").val("");
                    $('#billCity option').each(function () {
                        if ($(this).val() == "") {
                            $(this).attr("selected", true);
                        }
                    });
                    $("#billDistrict").val("");
                    $("#billProvince").val("");
                }

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
            function invokeDCOM() {

                window.location = "${pageContext.request.contextPath}/ApplicationModifyCompleteServlet";

            }
            function invokeReset(applicationId) {
                
                window.location = "${pageContext.request.contextPath}/LoadApplicationModifyDataServlet?applicationid="+applicationId+"&cardcategory="+'${sessionScope.SessionObject.cardCategory}'+"&applicationTypeCode="+'${sessionScope.SessionObject.applicationTypeCode}';

            }

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
    <body>

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

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">


                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  APPLICATION DATA MODIFICATION </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr>
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
<!--                                        <td><label> ${sessionScope.SessionObject.cardCategory} </label> </td>-->
                                        <td><label>Supplementary</label></td>
                                    </tr>
                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Document</a></li>
                                        <li><a href="#tabs-3">Signature</a></li>

                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form name="customerpersonalinfoform" method="POST" action="<%=request.getContextPath()%>/UpdateSuplimentoryPersonalInfoServlet">

                                            <br /><font style="color: #999"> Primary Card Information</font>   

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Primary Identification Type </td>
                                                    <!--                                                        <input type="radio" name="idType"  value="NIC"> NIC &nbsp; <input type="radio" name="idType"  value="PASS">Passport &nbsp;-->
                                                    <c:if test="${personalBean.primaryIdType =='NIC'}">
                                                        <td><input type="radio" name="priIdType" checked="true" value="NIC"> NIC &nbsp; <input type="radio" name="priIdType"  value="PASS">Passport &nbsp;</td>
                                                        </c:if>

                                                    <c:if test="${personalBean.primaryIdType =='PASS' }">
                                                        <td><input type="radio" name="priIdType"  value="NIC"> NIC &nbsp; <input type="radio" name="priIdType" checked="true" value="PASS">Passport &nbsp;</td>
                                                        </c:if>
                                                        <c:if test="${personalBean.primaryIdType !='NIC' && personalBean.primaryIdType !='PASS'}">
                                                        <td><input type="radio" name="priIdType"  checked="ture" value="NIC"> NIC &nbsp; <input type="radio" name="priIdType"  value="PASS">Passport &nbsp;</td>
                                                        </c:if>


                                                    <td style="width: 100px;" > <label ></label></td>

                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Primary Identification Number</td>
                                                    <td><input type="text" name="primaryIdentifyNumber"  maxlength="32" value="${personalBean.primaryId}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.primaryId} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Primary Card Number</td>
                                                    <td><input type="text" name="PrimaryCardNumber" id="_PrimaryCardNumber" maxlength="50" value="${personalBean.primaryCardNumber}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.primaryCardNumber} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Primary Application ID</td>
                                                    <td><input type="text" name="primaryApplicationId"  id="_primaryApplicationId" maxlength="50" value="${personalBean.primaryCardApplicationId}" ></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.primaryCardApplicationId} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>


                                            </table> 

                                            <br /><hr /><font style="color: #999"> Personal Information</font>
                                            <table cellpadding="0" cellspacing="10"  >


                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>

                                                    <td><select name="idType" disabled="true">
                                                            <option value="">--Select Identity Type-- </option>

                                                            <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                                <c:if test="${personalBean.identificationType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.identificationType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>        


                                                        </select></td>





                                                    <td style="width: 100px;" > <label ></label></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Number
                                                    </td>
                                                    <td><input type="text" name="identificationNo"  maxlength="16" value="${personalBean.identificationNumber}"></td>

                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.nic} </label></td>


                                                </tr>


                                                <tr>
                                                    <td style="width: 200px;">Title</td>
                                                    <td><select name="title">
                                                            <option value="">--Select Title--</option>

                                                            <c:if test="${personalBean.title =='Mr'}"> <option selected="true" value="Mr">Mr</option></c:if>
                                                            <c:if test="${personalBean.title !='Mr'}"> <option value="Mr">Mr</option></c:if>
                                                            <c:if test="${personalBean.title =='Mrs'}"> <option selected="true" value="Mrs">Mrs</option></c:if>
                                                            <c:if test="${personalBean.title !='Mrs'}"> <option value="Mrs">Mrs</option></c:if>
                                                            <c:if test="${personalBean.title =='Ms'}"> <option selected="true" value="Ms">Ms</option></c:if>
                                                            <c:if test="${personalBean.title !='Ms'}"> <option value="Ms">Ms</option></c:if>
                                                            <c:if test="${personalBean.title =='Dr'}"> <option selected="true" value="Dr">Dr</option></c:if>
                                                            <c:if test="${personalBean.title !='Dr'}"> <option value="Dr">Dr</option></c:if>                                                
                                                            <c:if test="${personalBean.title =='Prof'}"> <option selected="true" value="Prof">Prof</option></c:if>
                                                            <c:if test="${personalBean.title !='Prof'}"> <option value="Prof">Prof</option></c:if>
                                                            <c:if test="${personalBean.title =='Rev'}"> <option selected="true" value="Rev">Rev</option></c:if>
                                                            <c:if test="${personalBean.title !='Rev'}"> <option value="Rev">Rev</option></c:if>
                                                            <c:if test="${personalBean.title =='Hon'}"> <option selected="true" value="Hon">Hon</option></c:if>
                                                            <c:if test="${personalBean.title !='Hon'}"> <option value="Hon">Hon</option></c:if>
                                                            <c:if test="${personalBean.title =='Ven'}"> <option selected="true" value="Ven">Ven</option></c:if>
                                                            <c:if test="${personalBean.title !='Ven'}"> <option value="Ven">Ven</option></c:if>




                                                            </select>
                                                        </td>

                                                        <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.title} </label></td>
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"> Gender</td>    
                                                    <c:if test="${personalBean.gender =='MALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE" checked="true" >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" ></td>
                                                        </c:if>

                                                    <c:if test="${personalBean.gender =='FEMALE' }">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" checked="true"></td>
                                                        </c:if>
                                                        <c:if test="${personalBean.gender !='MALE' && personalBean.gender !='FEMALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" checked="true" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" ></td>
                                                        </c:if>

                                                    <td style="width: 100px;"><label style="color: red"> </label></td> 

                                                </tr>

                                                <tr>


                                                    <td style="width: 200px;">First Name</td>
                                                    <td><input type="text" name="firstname"  maxlength="32" value="${personalBean.firstName}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.firstName} </label></td>    

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td>
                                                        <input  name="birthday" maxlength="16" readonly value="${personalBean.birthday}" key="birthday" id="birthday"  />

                                                        <script type="text/javascript">
                                                            $(function () {
                                                                $("#birthday").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1,
                                                                    maxDate: 0
                                                                });
                                                            });
                                                        </script>

                                                    </td>
                                                    <td style="width: 100px;"></td> 





                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;"> Middle Name</td>    
                                                    <td><input type="text" name="middlename"  maxlength="20" value="${personalBean.middleName}"></td>  
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.middleName} </label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          

                                                    <td style="width: 200px;"> Nationality</td>    
                                                    <td><select name="nationality">
                                                            <option value="">--Select Nationality-- </option>
                                                            <c:if test="${personalBean.nationality != null}">
                                                                <c:forEach var="nationality" items="${sessionScope.SessionObject.nationalityList}">
                                                                    <c:if test="${personalBean.nationality == nationality}">
                                                                        <option value="${nationality}" selected="true">${nationality}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 

                                                            <c:forEach var="nationality" items="${sessionScope.SessionObject.nationalityList}">
                                                                <c:if test="${personalBean.nationality != nationality}">
                                                                    <option value="${nationality}">${nationality}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select></td> 
                                                    <td style="width: 100px;"><label style="color: red"> </label></td> 
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Last name</td>
                                                    <td><input type="text" name="lastName"  maxlength="32" value="${personalBean.lastName}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.lastName} </label></td>  


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->

                                                    <td style="width: 200px;">Relationship</td>
                                                    <td><input type="text" name="relationship"  maxlength="32" value="${personalBean.relationShip}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.relationShip} </label></td>  



                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Name on the Card</td>
                                                    <td><input type="text" name="nameoncard" style="background: #C4D2E0" maxlength="64" value="${personalBean.nameOncard}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.nameOncard} </label></td> 




                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   


                                                </tr>


                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Name With Initials</td>
                                                    <td><input type="text" name="nameWithInitials" style="width: 300px;" maxlength="36" value="${personalBean.nameWithinitials}"></td>
                                                    <td style="width: 100px;"><label style="color: red"> </label></td> 


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   


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
                                                    <td style="width: 200px;"> Mobile Phone No</td>    
                                                    <td><input type="text" name="mobileNo"  maxlength="20" value="${personalBean.mobileNumber}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.mobileNumber} </label></td>  
                                                    <td style="width: 200px;"> Res. Phone No</td>    
                                                    <td><input type="text" name="resPhoneNo"  maxlength="20" value="${personalBean.homeTelNumber}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.homeTelNumber} </label></td>
                                                </tr>





                                                <tr>
                                                    <td style="width: 200px;"><font style="color: #999"> <br />Residence Details</font> </td>
                                                    <td></td>
                                                    <td style="width: 100px;" > </label></td>    
                                                    <td style="width: 200px;"> <font style="color: #999"> <br />Billing Details</font></td>    
                                                    <td></td>  
                                                    <td style="width: 100px;" > </td>
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Same As Primary Address</td>
                                                    <td>
                                                        <input type="checkbox" name="checkRes" id="checkRes" onchange="setResAddress()" >
                                                    </td>
                                                    <td style="width: 100px;"></td>  
                                                    <td style="width: 200px;">Same As Primary Address</td>
                                                    <td>
                                                        <input type="checkbox" name="" id="checkBill" onchange="setBillAddress()">
                                                    </td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Residence Address </td>
                                                    <td><input type="text" name="resAddress1" id="resAddress1" maxlength="32" value="${personalBean.address1}"></td>
                                                    <td style="width: 100px;" > <label style="color: red"> </label></td>    
                                                    <td style="width: 200px;"> Billing Address</td>    
                                                    <td><input type="text" name="billAddress1" id="billAddress1" maxlength="20" value="${personalBean.billingAdress1}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red"> </label></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="resAddress2" id="resAddress2" maxlength="32" value="${personalBean.address2}"></td>
                                                    <td style="width: 100px;" > <label style="color: red"> </label></td>    
                                                    <td style="width: 200px;"> </td>    
                                                    <td><input type="text" name="billAddress2" id="billAddress2" maxlength="20" value="${personalBean.billingAdress2}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red"> </label></td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="resAddress3" id="resAddress3" maxlength="32" value="${personalBean.address3}"></td>

                                                    <td style="width: 100px;" > <label style="color: red"> </label></td>  
                                                    <td style="width: 200px;"> </td>    
                                                    <td><input type="text" name="billAddress3" id="billAddress3" maxlength="20" value="${personalBean.billingAdress3}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red"></label></td>
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Area</td>
                                                    <td><select name="resCity" id="ResCity" onchange="setDistrictAndProvince('res')">
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.city != null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                    <c:if test="${personalBean.city == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${personalBean.city != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.city} </label></td>
                                                    <td style="width: 200px;">Area</td>
                                                    <td><select name="billCity" id="billCity" onchange="setDistrictAndProvince('bill')">
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.billingCity != null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                    <c:if test="${personalBean.billingCity == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${personalBean.billingCity != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red"> </label></td>



                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">District</td>
                                                    <td>
                                                        <input type="text" name="resDistrict"  id="resDistrict" readonly="true" maxlength="32" value="${personalBean.resDistrict}"> 
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                    <td style="width: 200px;">District</td>
                                                    <td>
                                                        <input type="text" name="billDistrict" id="billDistrict" readonly="true" maxlength="32" value="${personalBean.billDistrict}">
                                                    </td>
                                                    <td style="width: 100px;"></td> 



                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Province</td>
                                                    <td>
                                                        <input type="text" id="resProvince" name="resProvince" readonly="true" maxlength="32" value="${personalBean.resProvince}">
                                                    </td>
                                                    <td style="width: 100px;"></td>  
                                                    <td style="width: 200px;">Province</td>
                                                    <td>
                                                        <input type="text" name="billProvince" id="billProvince" readonly="true" maxlength="32" value="${personalBean.billProvince}">
                                                    </td>



                                                </tr>


                                            </table>


                                            <br /><hr /><font style="color: #999"> Other Information</font>


                                            <table cellpadding="0" cellspacing="10">

                                                <tr>

                                                    <td style="width: 200px;">Employment Type</td>
                                                    <td> <select name="empType">
                                                            <c:if test="${personalBean.employementType != null}">
                                                                <c:forEach var="empTypeList" items="${sessionScope.SessionObject.empTypeList}">
                                                                    <c:if test="${personalBean.employementType == empTypeList.typeCode}">
                                                                        <option value="${empTypeList.typeCode}" selected="true">${empTypeList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="empTypeList" items="${sessionScope.SessionObject.empTypeList}">
                                                                <c:if test="${personalBean.employementType != empTypeList.typeCode}">
                                                                    <option value="${empTypeList.typeCode}">${empTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 


                                                        </select></td>
                                                    <td style="width: 100px;"> </td>




                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Occupation</td>
                                                    <td><select name="occupation">
                                                            <c:if test="${personalBean.occupation != null}">
                                                                <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                    <c:if test="${personalBean.occupation == occupationList.occupationTypeCode}">
                                                                        <option value="${occupationList.occupationTypeCode}" selected="true">${occupationList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                <c:if test="${personalBean.occupation != occupationList.occupationTypeCode}">
                                                                    <option value="${occupationList.occupationTypeCode}">${occupationList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 

                                                        </select></td>



                                                </tr>
                                            </table >
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Requested Card Type</td>
                                                    <td><select name="cardType" id="cardType" onchange="changeProducts('${personalBean.cardProduct}')">
                                                            <c:if test="${personalBean.cardType != null}">
                                                                <c:forEach var="cardTypeList" items="${sessionScope.SessionObject.cardTypeList}">
                                                                    <c:if test="${personalBean.cardType == cardTypeList.cardTypeCode}">
                                                                        <option value="${cardTypeList.cardTypeCode}" selected="true">${cardTypeList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="cardTypeList" items="${sessionScope.SessionObject.cardTypeList}">
                                                                <c:if test="${personalBean.cardType != cardTypeList.cardTypeCode}">
                                                                    <option value="${cardTypeList.cardTypeCode}">${cardTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach>  
                                                        </select></td>
                                                    <td style="width: 100px;"> </td>
                                                    <td style="width: 200px;">Requested Card Product</td>
                                                    <td><select name="cardProduct" id="cardProduct">

                                                        </select></td>



                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Requested Credit Limit</td>
                                                    <td>
                                                        <c:if test="${personalBean.climitReqType=='FIXED'}">
                                                            Fixed&nbsp;<input type="radio" name="climitReqType" value="FIXED" checked="true"/>
                                                            Percentage(%)&nbsp;<input type="radio" name="climitReqType" value="PER"/>
                                                        </c:if>
                                                        <c:if test="${personalBean.climitReqType=='PER'}">
                                                            Fixed&nbsp;<input type="radio" name="climitReqType" value="FIXED" />
                                                            Percentage(%)&nbsp;<input type="radio" name="climitReqType" value="PER" checked="true"/>
                                                        </c:if>
                                                        <c:if test="${personalBean.climitReqType!='FIXED' && personalBean.climitReqType!='PER'}">
                                                            Fixed&nbsp;<input type="radio" name="climitReqType" value="FIXED" checked="true"/>
                                                            Percentage(%)&nbsp;<input type="radio" name="climitReqType" value="PER"/>
                                                        </c:if>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px"></td>
                                                    <c:if test="${personalBean.climitReqType=='FIXED'}">
                                                        <td><input type="text" name="creditLimit"  maxlength="32" value="${personalBean.creditLimit}"></td>
                                                        </c:if>
                                                        <c:if test="${personalBean.climitReqType=='PER'}">
                                                        <td><input type="text" name="creditLimit"  maxlength="32" value="${personalBean.percentageValue}"></td>
                                                        </c:if>
                                                        <c:if test="${personalBean.climitReqType!='FIXED' && personalBean.climitReqType!='PER'}">
                                                        <td><input type="text" name="creditLimit"  maxlength="32" value=""></td>
                                                        </c:if>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.creditLimit} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="savenext" value="Save"  style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>
                                                    <td><input type="hidden" value="${personalBean.identificationType}" name="hidIdType"><input type="hidden" value="${personalBean.identificationNumber}" name="hidIdNumber"></td>

                                                </tr>

                                            </table> 

                                            <script >

                                                changeProducts('${personalBean.cardProduct}');


                                            </script>    



                                        </form>

                                    </div>

                                    <!--    tab 2  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                           -->


                                    <div id="tabs-2">
                                        <form name="uploadform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadUpdateDocumentServlet">

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
                                                    <td><input type="file" id="document_file" name="Upload" ><label id="document_file_error" style="color: red" />
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
                                        <form method="POST" action="<%=request.getContextPath()%>/UpdateDocumentUploadServlet">
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
                                                                        <td>
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
                                                    <td><input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>
                                        </form>    

                                    </div>     

                                    <!--            tab 3   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                                         -->
                                    <div id="tabs-3">
                                        <form name="signatureform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadUpdateSignatureServlet">

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr>
                                                    <td style="width: 200px;">Choose File </td>
                                                    <td><input type="file" id="signature_file" name="Upload" ><label id="signature_file_error" style="color: red" />
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 


                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="button" name="add"  style="width: 100px;"  value="Upload" onclick="checkForDocSelect(this, 'signature_file')" >
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr>
                                            </table>
                                        </form>

                                        <form name="completeform" method="POST" action="<%=request.getContextPath()%>/UpdateSignatureServlet">
                                            <table>
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td>

                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr> 
                                                <tr>

                                                    <td colspan="3">

                                                        <img src="<%=request.getContextPath()%>/SignatureLoadServlet?upload=${upload}" />
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>
                                        </form>    

                                    </div>    



                                </div>
                                <table>
                                    <tr>
                                        <td style="width: 200px;">&nbsp;</td>
                                        <td>&nbsp;</td>



                                    </tr>
                                    <tr>
                                        <td style="width: 200px;">&nbsp;</td>
                                        <td>&nbsp;</td>



                                    </tr>
                                    <tr>
                                        <td style="width: 200px;"></td>
                                        <td><input type="button" name="complete" value="Complete ALL" style="width: 200px;" onclick="invokeDCOM()"/></td>


                                    </tr>

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
