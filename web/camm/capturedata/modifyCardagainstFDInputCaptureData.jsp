<%-- 
    Document   : modifyCardagainstFDInputCaptureData
    Created on : Jul 12, 2016, 2:10:53 PM
    Author     : prageeth_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
//            function invokeRese
//            (applicationId) {
//                window.location = "${pageContext.request.contextPath}/LoadApplicationModifyDataServlet?appliactionid=" + applicationId;
//
//            }
            function invokeReset(applicationId, cardCategory, applicationTypeCode) {
                window.location = "${pageContext.request.contextPath}/LoadApplicationModifyDataServlet?applicationid=" + applicationId + "&cardcategory=" + cardCategory + "&applicationTypeCode=" + applicationTypeCode;
            }
            function invokeRemoveDocumentData(filename) {

                window.location = "${pageContext.request.contextPath}/RemoveUpdateDocumentDataFromSessionServlet?filename=" + filename;
            }

            function invokeRemoveBankData(bank, acc) {

                window.location = "${pageContext.request.contextPath}/RemoveUpdateBankDataFromSessionServlet?bank=" + bank + "&account=" + acc;
            }

            function setResAddress() {

                var isCheck = $('#checkRes:checked').val();

                if (isCheck == 'on') {

                    $("#resAddress1").val($("#address1").val());
                    $("#resAddress2").val($("#address2").val());
                    $("#resAddress3").val($("#address3").val());
                    $('#ResCity option').each(function () {
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

                    $("#billAddress1").val($("#address1").val());
                    $("#billAddress2").val($("#address2").val());
                    $("#billAddress3").val($("#address3").val());
                    $('#billCity option').each(function () {
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
                    $('#billCity option').each(function () {
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
                if (value == 'perm') {
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                            {area: $('#city option:selected').val()

                            },
                    function (data) {
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
                    function (data) {
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
                function (jsonobject) {
                    $("#_dType").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject.combo1, function (code, description) {
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
                function (data) {
                    if (data != '') {

                        var array = data.split('|', 2)
                        var codes = array[0].split(',')
                        var descriptions = array[1].split(',')



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
        </script>
        <script type="text/javascript">

            $(document).ready(function () {
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

            $(document).ready(function () {
                var len = $("#_settleAccNum").children('option').length;
                if (len > 1) {
                    $("#_autoSettle").css('display', 'inline-block');

                }
            });

        </script>

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

                                <table class="tit"> <tr> <td   class="center">  APPLICATION DATA MODIFICATION </td> </tr><tr> <td>&nbsp;</td> </tr></table>
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
                                        <%-- <td><label> ${sessionScope.SessionObject.cardCategory} </label> </td> --%>
                                        <td><label> Card against FD </label> </td>
                                    </tr>
                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Bank</a></li>
                                        <li><a href="#tabs-3">Document</a></li>                                        
                                        <li><a href="#tabs-4">Signature</a></li>

                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form name="customerpersonalinfoform" method="POST" action="<%=request.getContextPath()%>/UpdatePersonalInfoServlet">

                                            <br /><font style="color: #999"> Personal Information</font>   
                                            <table cellpadding="0" cellspacing="10">


                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp;<select name="idType" disabled="true">
                                                            <option value="">--Select Nationality-- </option>

                                                            <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                                <c:if test="${personalBean.identificationType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.identificationType != identity.key}">
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
                                                    <td>&nbsp;&nbsp; <label name="identificationNo" >${personalBean.identificationNumber}</label></td>

                                                    <td style="width: 50px;" > <label style="color: red">${invalidMsgBean.nic} </label></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">
                                                        Expiry Date
                                                    </td>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp;&nbsp;<label name="passportExpDate">${personalBean.passportExpdate}</label></td>
                                                </tr>


                                                <tr>
                                                    <td style="width: 200px;">Title</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="title" tabindex="1">
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

                                                        <td style="width: 50px;">
                                                            <label style="color: red">${invalidMsgBean.title}</label>
                                                    </td> 
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"> Nationality</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="nationality" tabindex="12">
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
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.nationality}</label>
                                                    </td>     

                                                </tr>
                                                <!--new-->
                                            </table>
                                            <table cellpadding="0" cellspacing="10">            
                                                <tr>
                                                    <td style="width: 200px;">Name in Full</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="fullname"  maxlength="200" value="${personalBean.fullName}" style="width: 600px" ></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.fullName}</label>

                                                    </td> 
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Name on the Card (Max 22)</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="nameoncard" style="width: 300px;" maxlength="22" value="${personalBean.nameOncard}"></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.nameOncard}</label>
                                                    </td> 

                                                </tr>

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input  name="birthday" maxlength="16" readonly value="${personalBean.birthday}" key="birthday" id="birthday"/>

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
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.birthday}</label>
                                                    </td> 

                                                    <td style="width: 200px;">Place of Birth</td>
                                                    <td></td>
                                                    <td><input type="text" name="placeOfBirth"  maxlength="32" value="${personalBean.placeOfbirth}" tabindex="10"></td>
                                                    <td style="width: 50px;">
                                                        <c:if test="${invalidMsgBean.placeOfbirth=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.placeOfbirth=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>   
                                                </tr>

                                                <tr>
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->

                                                    <td style="width: 200px;"> Marital Status</td>   
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="marital" tabindex="11">
                                                            <c:if test="${personalBean.maritalStatus =='Single'}"> <option selected="true" value="Single">Single</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus !='Single'}"> <option  value="Single">Single</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus =='Married'}"> <option selected="true" value="Married">Married</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus !='Married'}"> <option  value="Married">Married</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus =='Divorced'}"> <option selected="true" value="Divorced">Divorced</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus !='Divorced'}"> <option  value="Divorced">Divorced</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus =='Widowed'}"> <option selected="true" value="Widowed">Widowed</option></c:if>
                                                            <c:if test="${personalBean.maritalStatus !='Widowed'}"> <option  value="Widowed">Widowed</option></c:if>
                                                            </select>
                                                        </td>
                                                        <td style="width: 50px;"></td>
                                                        <td style="width: 200px">No. of Dependents</td>
                                                        <td></td>
                                                        <td><input type="text" name="noOfDependents"  maxlength="32" value="${personalBean.noOfDependens}"></td>

                                                </tr>


                                                <tr>
                                                    <td style="width: 200px;"> Gender</td>  
                                                    <td></td>
                                                    <c:if test="${personalBean.gender =='MALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE" checked="true" tabindex="6">&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" tabindex="6"></td>
                                                        </c:if>

                                                    <c:if test="${personalBean.gender =='FEMALE' }">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE" tabindex="6" >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" checked="true" tabindex="6"></td>
                                                        </c:if>
                                                        <c:if test="${personalBean.gender !='MALE' && personalBean.gender !='FEMALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" checked="true" value="MALE"  tabindex="6">&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" tabindex="6"></td>
                                                        </c:if>

                                                    <td style="width: 50px;"></td> 
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->


                                                    <td style="width: 200px;"> Blood Group</td>    
                                                    <td></td>
                                                    <td><select name="bloodGroup" tabindex="13">
                                                            <option value="">--Select Blood Group--</option>
                                                            <c:if test="${personalBean.bloodgroup =='O+'}"> <option selected="true" value="O+">Group O+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='O+'}"> <option  value="O+">Group O+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='O-'}"> <option selected="true" value="O-">Group O-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='O-'}"> <option  value="O-">Group O-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='A+'}"> <option selected="true" value="A+">Group A+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='A+'}"> <option  value="A+">Group A+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='A-'}"> <option selected="true" value="A-">Group A-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='A-'}"> <option  value="A-">Group A-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='B+'}"> <option selected="true" value="B+">Group B+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='B+'}"> <option  value="B+">Group B+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='B-'}"> <option selected="true" value="B-">Group B-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='B-'}"> <option  value="B-">Group B-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='AB+'}"> <option selected="true" value="AB+">Group AB+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='AB+'}"> <option  value="AB+">Group AB+</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup =='AB-'}"> <option selected="true" value="AB-">Group AB-</option></c:if>
                                                            <c:if test="${personalBean.bloodgroup !='AB-'}"> <option  value="AB-">Group AB-</option></c:if>



                                                            </select>
                                                        </td>  
                                                        <td style="width: 50px;">
                                                            <label></label>
                                                        </td> 

                                                    </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>

                                                        <td style="width: 200px;">Name With Initials</td>
                                                        <td><font style="color: red;">*</font></td>
                                                        <td><input type="text" name="nameWithInitials" style="width: 300px;" maxlength="32" value="${personalBean.nameWithInitials}" tabindex="7"></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.nameWithInitials}</label>
                                                    </td> 


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
                                                    <td style="width: 200px;">Permanent Address </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="address1" id="address1" maxlength="32" value="${personalBean.permentAddress1}" tabindex="14"></td>
                                                    <td style="width: 50px;" >
                                                        <c:if test="${invalidMsgBean.permentAddress1=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.permentAddress1=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>    
                                                    <td style="width: 200px;"> Res. Phone No</td> 
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="resPhoneNo"  maxlength="20" value="${personalBean.homeTelNumber}" tabindex="18"></td>  
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.homeTelNumber}</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td><input type="text" name="address2" id="address2" maxlength="32" value="${personalBean.permentAddress2}" tabindex="15"></td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.permentAddress2=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.permentAddress2=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>    
                                                    <td style="width: 200px;"> Office Phone No</td>    
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="officePhoneNo"  maxlength="20" value="${personalBean.officeTelNumber}" tabindex="19"></td>  
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.officeTelNumber}</label>
                                                    </td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td><input type="text" name="address3" id="address3"  maxlength="32" value="${personalBean.permentAddress3}" tabindex="16"></td>

                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.permentAddress3=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.permentAddress3=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>  
                                                    <td style="width: 200px;"> Mobile Phone No</td>   
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="mobileNo"  maxlength="20" value="${personalBean.mobileNumber}" tabindex="20"></td>  
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.mobileNumber}</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 200px;">Free SMS alerts</td>    
                                                    <td></td>
                                                    <c:if test="${personalBean.smsAlertStatus=='YES'}">
                                                        <td>
                                                            <input type="radio" name="smsAlertStatus" value="YES" checked="true"/>&nbsp;Yes
                                                            <input type="radio" name="smsAlertStatus" value="NO"/>&nbsp;No
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${personalBean.smsAlertStatus=='NO'}">
                                                        <td>
                                                            <input type="radio" name="smsAlertStatus" value="YES" />&nbsp;Yes
                                                            <input type="radio" name="smsAlertStatus" value="NO" checked="true"/>&nbsp;No
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${personalBean.smsAlertStatus!='YES' && personalBean.smsAlertStatus!='NO'}">
                                                        <td>
                                                            <input type="radio" name="smsAlertStatus" value="YES" checked="true"/>&nbsp;Yes
                                                            <input type="radio" name="smsAlertStatus" value="NO" />&nbsp;No
                                                        </td>
                                                    </c:if>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Area</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="city" id="city" tabindex="17" onchange="setDistrictAndProvince('perm')" width="163px; ">
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.permentCity != null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                    <c:if test="${personalBean.permentCity == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${personalBean.permentCity != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 50px;" >

                                                        <c:if test="${invalidMsgBean.permentCity=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.permentCity=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="email"  maxlength="32" value="${personalBean.email}" tabindex="21"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.email}</label>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">District</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="district" id="district" readonly="true" maxlength="32" value="${personalBean.district}"> 
                                                    </td>
                                                    <td style="width: 50px;"></td>
                                                    <td style="width: 200px;">Residence Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="restype" tabindex="22">
                                                            <c:if test="${personalBean.residenceType =='Own'}"> <option selected="true" value="Own">Own</option></c:if>
                                                            <c:if test="${personalBean.residenceType !='Own'}"> <option  value="Own">Own</option></c:if>
                                                            <c:if test="${personalBean.residenceType =='Rented'}"> <option selected="true" value="Rented">Rented</option></c:if>
                                                            <c:if test="${personalBean.residenceType !='Rented'}"> <option  value="Rented">Rented</option></c:if>
                                                            <c:if test="${personalBean.residenceType =='Parents'}"> <option selected="true" value="Parents">Parents</option></c:if>
                                                            <c:if test="${personalBean.residenceType !='Parents'}"> <option  value="Parents">Parents</option></c:if>
                                                            <c:if test="${personalBean.residenceType =='Relatives'}"> <option selected="true" value="Relatives">Relatives</option></c:if>
                                                            <c:if test="${personalBean.residenceType !='Relatives'}"> <option  value="Relatives">Relatives</option></c:if>
                                                            <c:if test="${personalBean.residenceType =='Employers'}"> <option selected="true" value="Employers">Employers</option></c:if>
                                                            <c:if test="${personalBean.residenceType !='Employers'}"> <option  value="Employers">Employers</option></c:if>
                                                            <c:if test="${personalBean.residenceType =='Mortgaged'}"> <option selected="true" value="Mortgaged">Mortgaged</option></c:if>
                                                            <c:if test="${personalBean.residenceType !='Mortgaged'}"> <option  value="Mortgaged">Mortgaged</option></c:if>
                                                            </select>
                                                        </td>
                                                        <td style="width: 50px;"></td> 



                                                    </tr>
                                                    <tr>

                                                        <td style="width: 200px;">Province</td>
                                                        <td></td>
                                                        <td>
                                                            <input type="text" name="province" id="province" readonly="true" maxlength="32" value="${personalBean.province}">
                                                    </td>
                                                    <td style="width: 50px;"></td>  
                                                    <td style="width: 200px;">Duration of the Address</td>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp; years&nbsp;<select name="addressyear" tabindex="23">
                                                            <!--                                                       <option value="">years</option>-->
                                                            <c:forEach var="years" begin="0" end="50" step="1">
                                                                <c:if test="${personalBean.durationofTheAddress==years}">
                                                                    <option value="${years}" selected="">${years}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.durationofTheAddress!=years}">
                                                                    <option value="${years}">${years}</option>
                                                                </c:if>


                                                            </c:forEach> 



                                                        </select>

                                                    </td>



                                                </tr>




                                                <tr>
                                                    <td style="width: 200px;"><font style="color: #999"> <br />Residence Details</font> </td>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 50px;" > </label></td>    
                                                    <td style="width: 200px;"> <font style="color: #999"> <br />Billing Details</font></td>    
                                                    <td></td> 
                                                    <td></td>
                                                    <td style="width: 50px;" > </td>
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Same As Permanent Address</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="checkbox" name="checkRes" id="checkRes" onchange="setResAddress()" >
                                                    </td>
                                                    <td style="width: 50px;"></td>  
                                                    <td style="width: 200px;">Same As Permanent Address</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="checkbox" name="" id="checkBill" onchange="setBillAddress()">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Residence Address </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="resAddress1" id="resAddress1" maxlength="32" value="${personalBean.address1}" tabindex="24"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.address1}</label>
                                                    </td>    
                                                    <td style="width: 200px;"> Billing Address</td>   
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="billAddress1" id="billAddress1" maxlength="32" value="${personalBean.billAddress1}" tabindex="28"></td>  
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.billAddress1=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.billAddress1=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td><input type="text" name="resAddress2" id="resAddress2" maxlength="32" value="${personalBean.address2}" tabindex="25"></td>
                                                    <td style="width: 50px;" >
                                                        <c:if test="${invalidMsgBean.address2=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.address2=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>    
                                                    <td style="width: 200px;"> </td>   
                                                    <td></td>
                                                    <td><input type="text" name="billAddress2" id="billAddress2" maxlength="32" value="${personalBean.billAddress2}" tabindex="29"></td>  
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.billAddress2=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.billAddress2=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td><input type="text" name="resAddress3" id="resAddress3" maxlength="32" value="${personalBean.address3}" tabindex="26"></td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.address3=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.address3=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>  
                                                    <td style="width: 200px;"> </td> 
                                                    <td></td>
                                                    <td><input type="text" name="billAddress3" id="billAddress3" maxlength="32" value="${personalBean.billAddress3}" tabindex="30"></td>  
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.billAddress3=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.billAddress3=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Area</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="resCity" id="ResCity" onchange="setDistrictAndProvince('res')" width="163px;"tabindex="27">
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
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.city}</label>
                                                    </td>
                                                    <td style="width: 200px;">Area</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="billCity" id="billCity" onchange="setDistrictAndProvince('bill')" width="163px;" tabindex="31">
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.billCity != null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                    <c:if test="${personalBean.billCity == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${personalBean.billCity != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.billCity=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.billCity=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>

                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">District</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="resDistrict"  id="resDistrict" readonly="true"  value="${personalBean.resDistrict}"> 
                                                    </td>
                                                    <td style="width: 50px;"></td>
                                                    <td style="width: 200px;">District</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="billDistrict" id="billDistrict" readonly="true"  value="${personalBean.billDistrict}">
                                                    </td>
                                                    <td style="width: 50px;"></td> 



                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Province</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" id="resProvince" name="resProvince" readonly="true"  value="${personalBean.resProvince}">
                                                    </td>
                                                    <td style="width: 50px;"></td>  
                                                    <td style="width: 200px;">Province</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="billProvince" id="billProvince" readonly="true"  value="${personalBean.billProvince}">
                                                    </td>



                                                </tr>


                                            </table>

                                            <br/><hr /><font style="color: #999"> Spouse Information</font>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Name in Full </td>
                                                    <td><input type="text" name="spouseName"  maxlength="200" style="width: 600px;" value="${personalBean.spouseName}" tabindex="32"></td>
                                                    <td style="width: 50px;" >
                                                        <c:if test="${invalidMsgBean.spouseName=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spouseName=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Name/Business  </td>
                                                    <td><input type="text" name="spouseemployer"  maxlength="64" style="width: 300px;" value="${personalBean.spouseNameofEmployee}" tabindex="33"></td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.spouseNameofEmployee=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spouseNameofEmployee=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Designation</td>
                                                    <td><input type="text" name="spouseDesignation"  maxlength="64" value="${personalBean.spouseDesignation}" tabindex="34"></td>
                                                    <td style="width: 50px;" >
                                                        <c:if test="${invalidMsgBean.spouseDesignation=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spouseDesignation=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Address </td>
                                                    <td><input type="text" name="spouseCompanyAddress"  maxlength="150" style="width: 600px;" value="${personalBean.spousecompanyAddress}" tabindex="35"></td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.spousecompanyAddress=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spousecompanyAddress=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">NIC Number</td>
                                                    <td><input type="text" name="spouseNic"  maxlength="20" value="${personalBean.spouseNic}" tabindex="36"></td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.spouseNic=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spouseNic=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Phone Number</td>
                                                    <td><input type="text" name="spouseOfficephone"  maxlength="20" value="${personalBean.spousePhone}" tabindex="37"></td>
                                                    <td style="width: 50px;" >
                                                        <c:if test="${invalidMsgBean.spousePhone=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spousePhone=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td><input type="text" name="spouseMail"  maxlength="50" value="${personalBean.spouseMail}" tabindex="38"></td>
                                                    <td style="width: 50px;" >
                                                        <c:if test="${invalidMsgBean.spouseMail=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spouseMail=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Monthly Income</td>
                                                    <td><input type="text" name="spouseMonthIncome"  maxlength="16" value="${personalBean.spouseMonthlyIncome}" tabindex="39"></td>
                                                    <td style="width: 50px;" > 
                                                        <c:if test="${invalidMsgBean.spouseMonthlyIncome=='Requierd'}">
                                                            <img style="width: 20px ; height: 15px" src="<%=request.getContextPath()%>/resources/images/windowimages/right.jpg" />
                                                        </c:if>
                                                        <c:if test="${invalidMsgBean.spouseMonthlyIncome=='Invalid'}">
                                                            <img style="width: 20px ; height: 15px;" src="<%=request.getContextPath()%>/resources/images/windowimages/realcross.jpg" />
                                                        </c:if> 
                                                    </td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>


                                            </table>   

                                            <br /><hr /><font style="color: #999"> Referral Information</font>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Name </td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="relName"  maxlength="100" style="width: 600px;" value="${personalBean.relName}" tabindex="40"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.relName}</label>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Relationship </td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="relationship"  maxlength="64" style="width: 300px;" value="${personalBean.relationship}" tabindex="41"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.relationship}</label>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Name/Business  </td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="relemployer"  maxlength="64" style="width: 300px;" value="${personalBean.relCompany}" tabindex="42"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.relCompany}</label>
                                                    </td>

                                                </tr>
                                            </table> 
                                            <table cellpadding="0" cellspacing="10">   

                                                <tr>
                                                    <td style="width: 200px;">Address</td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="reladdress1"  maxlength="32" value="${personalBean.relAddress1}" tabindex="43"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.relAddress1}</label>
                                                    </td>
                                                    <td style="width: 200px;">Res. Phone Number</td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="relResphoneno"  maxlength="20" value="${personalBean.relResidencePhone}" tabindex="47"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.relResidencePhone}</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td></td>
                                                    <td><input type="text" name="reladdress2"  maxlength="32"  value="${personalBean.relAddress2}" tabindex="44"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.relAddress2}</label>
                                                    </td>
                                                    <td style="width: 200px;">Office Phone Number</td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="relOfficephoneNumber"  maxlength="20" value="${personalBean.relOfficeNumber}" tabindex="48"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.relOfficeNumber}</label>
                                                    </td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td></td>
                                                    <td><input type="text" name="reladdress3"  maxlength="32" value="${personalBean.relAddress3}" tabindex="45"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.relAddress3}</label>
                                                    </td>
                                                    <td style="width: 200px;">Mobile Number</td>
                                                    <td style="color: red"></td>
                                                    <td><input type="text" name="relmobile"  maxlength="20" value="${personalBean.relMobile}" tabindex="49"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.relMobile}</label>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Area </td>
                                                    <td style="color: red"></td>
                                                    <td><select name="relcity" id="relcity" width="163px;" onchange="setDistrictAndProvince('rel')" tabindex="46"> 
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.relCity!=null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                    <c:if test="${personalBean.relCity == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${personalBean.relCity != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.relCity}</label> 
                                                    </td>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td></td>
                                                    <td><input type="text" name="relmail"  maxlength="50" value="${personalBean.relEmail}" tabindex="50"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.relEmail}</label> 
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">District</td>
                                                    <td></td>
                                                    <td><input type="text" name="relDistrict" id="relDistrict" readonly="true" value=""></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Province</td>
                                                    <td></td>
                                                    <td><input type="text" name="relProvince" id="relProvince" readonly="true" value=""></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>


                                            </table> 
                                            <br /><hr /><font style="color: #999"> Other Information</font>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Mother's Madden Name </td>
                                                    <td><font style="color: red;"></font></td>
                                                    <td><input type="text" name="mothersMadden"  maxlength="60" style="width: 600px;" value="${personalBean.mothersMaidenName}" tabindex="51"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.mothersMaidenName}</label>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Academic Qualification </td>
                                                    <td><font style="color: red;"></font></td>
                                                    <td><select name="academicQualify" tabindex="52">
                                                            <c:if test="${personalBean.acedemicQualifications =='School Leaver'}"> <option selected="true" value="School Leaver">School Leaver</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='School Leaver'}"> <option  value="School Leaver">School Leaver</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Certificate'}"> <option selected="true" value="Certificate">Certificate</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Certificate'}"> <option  value="Certificate">Certificate</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Diploma'}"> <option selected="true" value="Diploma">Diploma</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Diploma'}"> <option  value="Diploma">Diploma</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Higher Diploma'}"> <option selected="true" value="Higher Diploma">Higher Diploma</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Higher Diploma'}"> <option  value="Higher Diploma">Higher Diploma</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Degree'}"> <option selected="true" value="Degree">Degree</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Degree'}"> <option  value="Degree">Degree</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Degree With honours'}"> <option selected="true" value="Degree With honours">Degree With honours</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Degree With honours'}"> <option  value="Degree With honours">Degree With honours</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Masters Degree'}"> <option selected="true" value="Masters Degree">Masters Degree</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Masters Degree'}"> <option  value="Masters Degree">Masters Degree</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Masters Degree With honours'}"> <option selected="true" value="Masters Degree With honours">Masters Degree With honours</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Masters Degree With honours'}"> <option  value="Masters Degree With honours">Masters Degree With honours</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications =='Postgraduate Honorary Doctorate'}"> <option selected="true" value="Postgraduate Honorary Doctorate">Postgraduate Honorary Doctorate</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='Postgraduate Honorary Doctorate'}"> <option  value="Postgraduate Honorary Doctorate">Postgraduate Honorary Doctorate</option></c:if>
                                                            </select>
                                                        </td>
                                                        <td style="width: 50px;" >
                                                            <label style="color: red">${invalidMsgBean.acedemicQualifications}</label>
                                                    </td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Professional Qualification</td>
                                                    <td></td>
                                                    <td><input type="text" name="personalQualify"  maxlength="100" style="width: 300px;" value="${personalBean.professionalQualifications}" tabindex="53"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.professionalQualifications}</label>
                                                    </td>


                                                </tr>

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Vehicle Type</td>
                                                    <td></td>
                                                    <td>
                                                        &nbsp;&nbsp;&nbsp;<select name="vehicleType" tabindex="54">
                                                            <option  value="">--SELECT--</option>
                                                            <c:if test="${personalBean.vehicalType =='Motorcycle'}"> <option selected="true" value="Motorcycle">Motorcycle</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Motorcycle'}"> <option  value="Motorcycle">Motorcycle</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Three Wheeler'}"> <option selected="true" value="Three Wheeler">Three Wheeler</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Three Wheeler'}"> <option  value="Three Wheeler">Three Wheeler</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Car'}"> <option selected="true" value="Car">Car</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Car'}"> <option  value="Car">Car</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Van'}"> <option selected="true" value="Van">Van</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Van'}"> <option  value="Van">Van</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Lorry Small'}"> <option selected="true" value="Lorry Small">Lorry Small</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Lorry Small'}"> <option  value="Lorry Small">Lorry Small</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Lorry Large'}"> <option selected="true" value="Lorry Large">Lorry Large</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Lorry Large'}"> <option  value="Lorry Large">Lorry Large</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Bus'}"> <option selected="true" value="Bus">Bus</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Bus'}"> <option  value="Bus">Bus</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Jeep'}"> <option selected="true" value="Jeep">Jeep</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Jeep'}"> <option  value="Jeep">Jeep</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Cab'}"> <option selected="true" value="Cab">Cab</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Cab'}"> <option  value="Cab">Cab</option></c:if>
                                                            <c:if test="${personalBean.vehicalType =='Double Cab'}"> <option selected="true" value="Double Cab">Double Cab</option></c:if>
                                                            <c:if test="${personalBean.vehicalType !='Double Cab'}"> <option  value="Double Cab">Double Cab</option></c:if>
                                                            </select>
                                                        </td>
                                                        <td style="width: 50px;" >
                                                            <label style="color: red">${invalidMsgBean.vehicalType}</label>
                                                    </td>
                                                    <td style="width: 200px;">Vehicle Number</td>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp;<input type="text" name="vehicleNumber"  maxlength="20" value="${personalBean.vehicalNo}" tabindex="55"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.vehicalNo}</label>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Ownership   </td>
                                                    <td></td>
                                                    <td>
                                                        &nbsp;&nbsp;&nbsp;<select name="ownership" tabindex="56">
                                                            <option  value="">--SELECT--</option>
                                                            <c:if test="${personalBean.ownership =='Own'}"> <option selected="true" value="Own">Own</option></c:if>
                                                            <c:if test="${personalBean.ownership !='Own'}"> <option  value="Own">Own</option></c:if>
                                                            <c:if test="${personalBean.ownership =='Rented'}"> <option selected="true" value="Rented">Rented</option></c:if>
                                                            <c:if test="${personalBean.ownership !='Rented'}"> <option  value="Rented">Rented</option></c:if>
                                                            <c:if test="${personalBean.ownership =='Leased'}"> <option selected="true" value="Leased">Leased</option></c:if>
                                                            <c:if test="${personalBean.ownership !='Leased'}"> <option  value="Leased">Leased</option></c:if>
                                                            <c:if test="${personalBean.ownership =='Loaned'}"> <option selected="true" value="Loaned">Loaned</option></c:if>
                                                            <c:if test="${personalBean.ownership !='Loaned'}"> <option  value="Loaned">Loaned</option></c:if>
                                                            <c:if test="${personalBean.ownership =='Parents'}"> <option selected="true" value="Parents">Parents</option></c:if>
                                                            <c:if test="${personalBean.ownership !='Parents'}"> <option  value="Parents">Parents</option></c:if>
                                                            <c:if test="${personalBean.ownership =='Relatives'}"> <option selected="true" value="Relatives">Relatives</option></c:if>
                                                            <c:if test="${personalBean.ownership !='Relatives'}"> <option  value="Relatives">Relatives</option></c:if>
                                                            </select>
                                                        </td>
                                                        <td style="width: 50px;" > 
                                                            <label style="color: red">${invalidMsgBean.ownership}</label>
                                                    </td>

                                                </tr>



                                                <tr>

                                                    <td style="width: 200px;">Requested Card Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="cardType" id ="cardType" tabindex="57" onchange="changeProducts('${personalBean.cardProduct}')">

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
                                                    <td style="width: 50px;"> </td>
                                                    <td style="width: 200px;">Requested Currency</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select name="cardCurency"  tabindex="59">
                                                            <c:if test="${personalBean.cardCurrency != null}">
                                                                <c:forEach var="currencyList" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                    <c:if test="${personalBean.cardCurrency == currencyList.currencyCode}">
                                                                        <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="currencyList" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${personalBean.cardCurrency != currencyList.currencyCode}">
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
                                                    <td><input type="text" name="creditLimit"  maxlength="32" value="${personalBean.creditLimit}" tabindex="60"></td>
                                                    <td style="width: 50px;" >
                                                        <label style="color: red">${invalidMsgBean.creditLimit}</label> 
                                                    </td>


                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">&nbsp;</td>
                                                    <td>&nbsp;</td>



                                                </tr>

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Add Remark</td>
                                                    <td>
                                                        <textarea id="remark" onfocus="this.rows = 25" cols="50" rows="15" name="remark" style="width: 200px; height: 60px;">
                                                            ${personalBean.remark}
                                                        </textarea>
                                                    </td>
                                                    <td></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td colspan="2"><input type="submit" name="savenext" value="Save"  style="width: 100px;" tabindex="61"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}', '${sessionScope.SessionObject.cardCategory}', '${sessionScope.SessionObject.applicationTypeCode}')" name="next" value="Reset" style="width: 100px;" tabindex="62"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;" tabindex="63"/></td>
                                                    <td>
                                                        <input type="hidden" value="${personalBean.identificationType}" name="hidIdType">
                                                        <input type="hidden" value="${personalBean.identificationNumber}" name="hidIdNumber">
                                                        <input type="hidden" value="${personalBean.passportExpdate}" name="hideExpDate">
                                                    </td>

                                                </tr>

                                            </table>

                                            <script>

                                                changeProducts('${personalBean.cardProduct}');



                                            </script>    


                                        </form>

                                    </div>

                                    <!--   tab 2 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                    <div id="tabs-2">
                                        <form name="banksessionform" method="POST" action="<%=request.getContextPath()%>/SetUpdateAccountDataToSessionServlet">

                                            <table  cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Name of the Bank </td>
                                                    <td><select name="bankName" id="bankName" >
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
                                                            <option>--Select Branch--</option>
                                                            <c:forEach var="bankBranchList" items="${sessionScope.SessionObject.bankBranchList}">

                                                                <option value="${bankBranchList.branchCode}">${bankBranchList.description}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Account Type </td>
                                                    <td><select name="accType">
                                                            <option value="Fixed Account">Fixed Account</option>
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
                                        <form name="bankform" method="POST" action="<%=request.getContextPath()%>/UpdateBankDetailsServlet">

                                            <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview">
                                                <thead >
                                                    <tr  class="gradeB">

                                                        <th width="100px;" scope="col">Name of the bank</th>
                                                        <th width="100px;" scope="col">Branch Name</th>
                                                        <th width="100px;" scope="col">Account Type</th>
                                                        <th width="100px;" scope="col">Account Number</th>
                                                        <th width="100px;" scope="col">Account Since</th>
                                                        <th width="100px;" scope="col">Delete</th>

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
                                                            <td >
                                                                <input type="button" value="Delete" onclick="invokeRemoveBankData('${bankDetailList.bankName}', '${bankDetailList.accountNumber}')">
                                                            </td>

                                                        </tr>
                                                    </c:forEach>


                                                </tbody>


                                            </table>
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
                                            <table>
                                                <tr>
                                                    <td style="width: 200px;">Add Remark</td>
                                                    <td>
                                                        <textarea id="remark4" onfocus="this.rows = 25" cols="50" rows="15" name="remark4" style="width: 200px; height: 60px;">
                                                            ${remark4}
                                                        </textarea>
                                                    </td>
                                                    <td></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}', '${sessionScope.SessionObject.cardCategory}', '${sessionScope.SessionObject.applicationTypeCode}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>
                                        </form>    

                                    </div>



                                    <!--   tab 3 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                    <div id="tabs-3">
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
                                                    <td style="width: 200px;">Add Remark</td>
                                                    <td><textarea id="remark5" onfocus="this.rows = 25" cols="50" rows="15" name="remark5" style="width: 200px; height: 60px;">
                                                            ${remark5}
                                                        </textarea>

                                                    </td>
                                                    <td></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}', '${sessionScope.SessionObject.cardCategory}', '${sessionScope.SessionObject.applicationTypeCode}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>
                                        </form>    

                                    </div>     

                                    <!--tab 4   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                                         -->
                                    <div id="tabs-4">
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
                                                    <td><input type="button" name="add"  style="width: 100px;"  value="Upload" onclick="checkForDocSelect(this, 'signature_file')">
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
                                                    <td style="width: 200px;">Add Remark</td>
                                                    <td><textarea id="remark6" onfocus="this.rows = 25" cols="50" rows="15" name="remark6" style="width: 200px; height: 60px;">
                                                            ${remark6}
                                                        </textarea></td>
                                                    <td></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}', '${sessionScope.SessionObject.cardCategory}', '${sessionScope.SessionObject.applicationTypeCode}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


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