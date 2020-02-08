<%-- 
    Document   : corporateInputCaptureData
    Created on : Jun 23, 2016, 1:59:39 PM
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
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Document</a></li>                                        
                                        <li><a href="#tabs-3">Signature</a></li>

                                    </ul>
                                     <br>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form name="customerpersonalinfoform" method="POST" action="<%=request.getContextPath()%>/AddCustomerPersonalInfoServlet">

                                            <br /><font style="color: #999"> Personal Information</font>   
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">
                                                        Company
                                                    </td>
                                                    <td></td>
                                                    <td>
                                                        <select name="businessRegNumber" tabindex="12">
                                                            <option value="">--Select The Company-- </option>
                                                            <c:if test="${personalBean.businessRegNumber != null}">
                                                                <c:forEach var="establishedCompany" items="${sessionScope.SessionObject.establishedCompanyList}">
                                                                    <c:if test="${personalBean.businessRegNumber == establishedCompany.businessRegNumber}">
                                                                        <option value="${establishedCompany.businessRegNumber}" selected="true">${establishedCompany.companyName}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 

                                                            <c:forEach var="establishedCompany" items="${sessionScope.SessionObject.establishedCompanyList}">
                                                                <c:if test="${personalBean.businessRegNumber != establishedCompany.businessRegNumber}">
                                                                    <option value="${establishedCompany.businessRegNumber}">${establishedCompany.companyName}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select></td>





                                                    <td style="width: 50px;" > <label ></label></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Designation</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="designation"  maxlength="100" value="${personalBean.designation}" style="width: 200px" ></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.designation}</label>

                                                    </td> 
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>
                                                    <td></td>
                                                    <td><select name="idType" disabled="true">
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
                                                    <td style="width: 200px;">First Name</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="firstName"  maxlength="100" value="${personalBean.firstName}" style="width: 200px" ></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.firstName}</label>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Middle Name</td>
                                                    <td></td>
                                                    <td><input type="text" name="middleName"  maxlength="100" value="${personalBean.middleName}" style="width: 200px" ></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.middleName}</label>

                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Last Name</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="lastName"  maxlength="100" value="${personalBean.lastName}" style="width: 200px" ></td>
                                                    <td style="width: 50px;">
                                                        <label style="color: red">${invalidMsgBean.lastName}</label>

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
                                                            $(function() {
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


                                                    <td style="width: 200px;"> </td>    
                                                    <td></td>
                                                    <td>
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

                                            <br /><hr /><font style="color: #999"> Other Information</font>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Mother's Madden Name </td>
                                                    <td><font style="color: red;"></font></td>
                                                    <td><input type="text" name="mothersMadden"  maxlength="60" style="width: 500px;" value="${personalBean.mothersMaidenName}" tabindex="51"></td>
                                                    <td style="width: 50px;" > 
                                                        <label style="color: red">${invalidMsgBean.mothersMaidenName}</label>
                                                    </td>

                                                </tr>

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
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
                                                    <td style="width: 200px;"></td>
                                                    <td colspan="2"><input type="submit" name="savenext" value="Save & Next"  style="width: 100px;" tabindex="61"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;" tabindex="62"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;" tabindex="63"/></td>
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

                                    <!--tab 3   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                                         -->
                                    <div id="tabs-3">
                                        <form name="signatureform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadSignatureServlet">

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
