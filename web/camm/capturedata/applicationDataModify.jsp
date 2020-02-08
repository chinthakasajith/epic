<%-- 
    Document   : applicationDataModify
    Created on : Mar 6, 2012, 3:45:24 PM
    Author     : janaka_h
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
                invorkeSumOfExpenses();


            });

        </script>
        <script language="javascript">


            function invokeCancel() {
                window.location = "${pageContext.request.contextPath}/SearchUserModifyDataServlet";

            }
            function invokeReset(applicationId, cardCategory, applicationTypeCode) {
                window.location = "${pageContext.request.contextPath}/LoadApplicationModifyDataServlet?applicationid=" + applicationId + "&cardcategory=" + cardCategory + "&applicationTypeCode=" + applicationTypeCode;
            }
            function invokeRemoveIncome(category) {
                window.location = "${pageContext.request.contextPath}/RemoveUpdateIncomeDataToSessionServlet?category=" + category;
            }
            function invokeRemoveBankData(bank, acc) {

                window.location = "${pageContext.request.contextPath}/RemoveUpdateBankDataFromSessionServlet?bank=" + bank + "&account=" + acc;
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

            }
            function invorkeSumOfExpenses() {
                var rent = $("#rent").val();
                if (rent == "") {
                    rent = "0";
                }

                var loanInstallment = $("#loanInstallment").val();
                if (loanInstallment == "") {
                    loanInstallment = "0";
                }
                var leaseRental = $("#leaseRental").val();
                if (leaseRental == "") {
                    leaseRental = "0";
                }
                var creditCardBill = $("#creditCardBill").val();
                if (creditCardBill == "") {
                    creditCardBill = "0";
                }
                var otherBorrowing = $("#otherBorrowing").val();
                if (otherBorrowing == "") {
                    otherBorrowing = "0";
                }
                var insurance = $("#insurance").val();
                if (insurance == "") {
                    insurance = "0";
                }
                var houseHolder = $("#houseHolder").val();
                if (houseHolder == "") {
                    houseHolder = "0";
                }
                var otherExpense = $("#otherExpense").val();
                if (otherExpense == "") {
                    otherExpense = "0";
                }
                var sum = parseInt(rent) + parseInt(loanInstallment) + parseInt(leaseRental) + parseInt(creditCardBill) + parseInt(otherBorrowing) + parseInt(insurance) + parseInt(houseHolder) + parseInt(otherExpense);
                $("#sumOfExpenses").val(sum);
                $("#sumOfExpenses1").val(sum);


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

            function changeBranches()
            {


                $.post("${pageContext.request.contextPath}/SetBankBranchDropDownServlet",
                        {bankcode: $('#bankName option:selected').val()

                        },
                function (data) {
                    if (data != '') {

                        var array = data.split('|', 2)
                        var codes = array[0].split(',')
                        var descriptions = array[1].split(',')



                        $('#brachName option').each(function () {
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

                    }


                });

            }


            function invorkSetDocumentTypes(category) {

                window.location = "${pageContext.request.contextPath}/SetDocumentTypesOnModifyServlet?category=" + category;

            }
            function invokeDCOM() {

                window.location = "${pageContext.request.contextPath}/ApplicationModifyCompleteServlet";

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



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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
                                    </tr>
                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Employment</a></li>
                                        <li><a href="#tabs-3">Income/Expenses</a></li>
                                        <li><a href="#tabs-4">Bank</a></li>
                                        <li><a href="#tabs-5">Document</a></li>
                                        <li><a href="#tabs-6">Signature/Add Remark</a></li>

                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form name="customerpersonalinfoform" method="POST" action="<%=request.getContextPath()%>/UpdatePersonalInfoServlet">


                                            <br /><font style="color: #999"> Personal Information</font>   
                                            <table cellpadding="0" cellspacing="10"  >


                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>

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





                                                    <td style="width: 100px;" > <label ></label></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Number
                                                    </td>

                                                    <td><label name="identificationNo" >${personalBean.identificationNumber}</label></td>

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

                                                        <td style="width: 100px;"></td> 
                                                        <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                        <td style="width: 200px;"> Religion</td>    
                                                        <td><input type="text" name="religion"  maxlength="20" value="${personalBean.religion}"></td>    

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
                                                    <td><input type="text" name="othername"  maxlength="20" value="${personalBean.middleName}"></td>  
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.middleName} </label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;">Place of Birth</td>
                                                    <td><input type="text" name="placeOfBirth"  maxlength="32" value="${personalBean.placeOfbirth}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.placeOfbirth} </label></td>   

                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Last name</td>
                                                    <td><input type="text" name="surname"  maxlength="32" value="${personalBean.lastName}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.lastName} </label></td>  


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->

                                                    <td style="width: 200px;"> Marital Status</td>    

                                                    <td><select name="marital">
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



                                                    </tr>
                                                    <tr>

                                                        <td style="width: 200px;">Name on the Card</td>
                                                        <td><input type="text" name="nameoncard" style="background: #C4D2E0" maxlength="64" value="${personalBean.nameOncard}"></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.nameOncard} </label></td> 




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
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->


                                                    <td style="width: 200px;"> Blood Group</td>    
                                                    <td><select name="bloodGroup">
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

                                                    </tr>
                                                    <tr>

                                                        <td colspan="6" ><hr /></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="6" ><font style="color: #999"> <br />Contact Details</font></td>
                                                    </tr>


                                                    <tr>
                                                        <td style="width: 200px;">Address </td>
                                                        <td><input type="text" name="address1"  maxlength="32" value="${personalBean.address1}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.address1} </label></td>    
                                                    <td style="width: 200px;"> Res. Phone No</td>    
                                                    <td><input type="text" name="resPhoneNo"  maxlength="20" value="${personalBean.homeTelNumber}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.homeTelNumber} </label></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="address2"  maxlength="32" value="${personalBean.address2}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.address2} </label></td>    
                                                    <td style="width: 200px;"> Office Phone No</td>    
                                                    <td><input type="text" name="officePhoneNo"  maxlength="20" value="${personalBean.officeTelNumber}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.officeTelNumber} </label></td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="address3"  maxlength="32" value="${personalBean.address3}"></td>

                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.address3} </label></td>  
                                                    <td style="width: 200px;"> Mobile Phone No</td>    
                                                    <td><input type="text" name="mobileNo"  maxlength="20" value="${personalBean.mobileNumber}"></td>  
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.mobileNumber} </label></td>
                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">City</td>
                                                    <td><input type="text" name="city"  maxlength="32" value="${personalBean.city}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.city} </label></td>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td><input type="text" name="email"  maxlength="32" value="${personalBean.email}" style="text-transform: none"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.email} </label></td>



                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">Residence Type</td>
                                                    <td>
                                                        <select name="restype">
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
                                                        <td style="width: 100px;"></td>  
                                                        <td style="width: 200px;">Duration of the Address</td>
                                                        <td>years <select name="addressyear">
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


                                            </table>

                                            <br /><hr /><font style="color: #999"> Spouse Information</font>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">Name in Full </td>
                                                    <td><input type="text" name="spouseName"  maxlength="200" style="width: 600px;" value="${personalBean.spouseName}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spouseName} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Name/Business  </td>
                                                    <td><input type="text" name="spouseemployer"  maxlength="100" style="width: 300px;" value="${personalBean.spouseNameofEmployee}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spouseNameofEmployee} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Designation</td>
                                                    <td><input type="text" name="spouseDesignation"  maxlength="32" value="${personalBean.spouseDesignation}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spouseDesignation} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Address </td>
                                                    <td><input type="text" name="spouseCompanyAddress"  maxlength="100" style="width: 600px;" value="${personalBean.spousecompanyAddress}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spousecompanyAddress} </label></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">NIC Number</td>
                                                    <td><input type="text" name="spouseNic"  maxlength="32" value="${personalBean.spouseNic}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spouseNic} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Phone Number</td>
                                                    <td><input type="text" name="spouseOfficephone"  maxlength="32" value="${personalBean.spousePhone}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spousePhone} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td><input type="text" name="spouseMail"  maxlength="50" value="${personalBean.spouseMail}" style="text-transform: none"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spouseMail} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Monthly Income</td>
                                                    <td><input type="text" name="spouseMonthIncome"  maxlength="50" value="${personalBean.spouseMonthlyIncome}" onKeyPress="return checkIt(event,'errMsgUpdatemonthlyIncome')">&nbsp;<span id="errMsgUpdatemonthlyIncome" style="color: red"></span></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.spouseMonthlyIncome} </label></td>


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
                                                    <td><input type="text" name="relName"  maxlength="100" style="width: 600px;" value="${personalBean.relName}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relName} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Relationship </td>
                                                    <td><input type="text" name="relationship"  maxlength="100" style="width: 300px;" value="${personalBean.relationship}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relationship} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Company Name/Business  </td>
                                                    <td><input type="text" name="relemployer"  maxlength="100" style="width: 300px;" value="${personalBean.relCompany}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relCompany} </label></td>

                                                </tr>
                                            </table> 
                                            <table cellpadding="0" cellspacing="10">   

                                                <tr>
                                                    <td style="width: 200px;">Address</td>
                                                    <td><input type="text" name="reladdress1"  maxlength="32" value="${personalBean.relAddress1}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relAddress1} </label></td>
                                                    <td style="width: 200px;">Res. Phone Number</td>
                                                    <td><input type="text" name="relResphoneno"  maxlength="32" value="${personalBean.relResidencePhone}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relResidencePhone} </label></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="text" name="reladdress2"  maxlength="32"  value="${personalBean.relAddress2}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relAddress2} </label></td>
                                                    <td style="width: 200px;">Office Phone Number</td>
                                                    <td><input type="text" name="relOfficephoneNumber"  maxlength="32" value="${personalBean.relOfficeNumber}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relOfficeNumber} </label></td>
                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="text" name="reladdress3"  maxlength="32" value="${personalBean.relAddress3}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relAddress3} </label></td>
                                                    <td style="width: 200px;">Mobile Number</td>
                                                    <td><input type="text" name="relmobile"  maxlength="32" value="${personalBean.relMobile}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relMobile} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Area </td>
                                                    <td><input type="text" name="relcity"  maxlength="32"  value="${personalBean.relCity}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relCity} </label></td>
                                                    <td style="width: 200px;">Email Address</td>
                                                    <td><input type="text" name="relmail"  maxlength="50" value="${personalBean.relEmail}" style="text-transform: none"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.relEmail} </label></td>
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
                                                    <td><input type="text" name="mothersMadden"  maxlength="60" style="width: 600px;" value="${personalBean.mothersMaidenName}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.mothersMaidenName} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Academic Qualification </td>
                                                    <td>
                                                        <select name="academicQualify">
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
                                                            <c:if test="${personalBean.acedemicQualifications =='NA'}"> <option selected="true" value="NA">NA</option></c:if>
                                                            <c:if test="${personalBean.acedemicQualifications !='NA'}"> <option  value="NA">NA</option></c:if>
                                                            </select>
                                                        </td>
                                                        <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.acedemicQualifications} </label></td>


                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Professional Qualification</td>
                                                    <td><input type="text" name="personalQualify"  maxlength="100" style="width: 300px;" value="${personalBean.professionalQualifications}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.professionalQualifications} </label></td>


                                                </tr>

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>

                                                    <td style="width: 200px;">Vehicle Type</td>
                                                    <td>
                                                        <select name="vehicleType">
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
                                                        <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.vehicalType} </label></td>
                                                    <td style="width: 200px;">Vehicle Number</td>
                                                    <td><input type="text" name="vehicleNumber"  maxlength="32" value="${personalBean.vehicalNo}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.vehicalNo} </label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Ownership   </td>
                                                    <td>
                                                        <select name="ownership">
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
                                                        <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.ownership} </label></td>

                                                </tr>



                                                <tr>

                                                    <td style="width: 200px;">Requested Card Type</td>
                                                    <td><select name="cardType" id ="cardType" onchange="changeProducts('${personalBean.cardProduct}')">

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
                                                    <td><select name="cardProduct" id="cardProduct" >

                                                        </select></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Requested Credit Limit</td>
                                                    <td><input type="text" name="creditLimit"  maxlength="32" value="${personalBean.creditLimit}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.creditLimit} </label></td>


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
                                                    <td><input type="submit" name="savenext" value="Save"  style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}', '${sessionScope.SessionObject.cardCategory}', '${sessionScope.SessionObject.applicationTypeCode}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>
                                                    <td><input type="hidden" value="${personalBean.identificationType}" name="hidIdType"><input type="hidden" value="${personalBean.identificationNumber}" name="hidIdNumber"></td>

                                                </tr>

                                            </table> 
                                            <script >

                                                changeProducts('${personalBean.cardProduct}');
                                                changeBranches();

                                            </script>          

                                        </form>

                                    </div>

                                    <!--    tab 2  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                           -->


                                    <div id="tabs-2">
                                        <form name="customeremploymentform" method="POST" action="<%=request.getContextPath()%>/UpdateEmployementDetailsServlet">
                                            <table cellpadding="0" cellspacing="10"  >
                                                <tr>
                                                    <td style="width: 200px;">

                                                    </td>

                                                    <!--                                                        <input type="radio" name="idType"  value="NIC"> NIC &nbsp; <input type="radio" name="idType"  value="PASS">Passport &nbsp;-->
                                                    <c:if test="${employmentBean.staffStatus =='YES'}">
                                                        <td><input type="radio" name="staff" checked="true" value="YES"> Staff &nbsp; <input type="radio" name="staff"  value="NO">Non-Staff &nbsp;</td>
                                                        </c:if>

                                                    <c:if test="${employmentBean.staffStatus =='NO' }">
                                                        <td><input type="radio" name="staff"  value="YES"> Staff &nbsp; <input type="radio" name="staff" checked="true" value="NO">Non-Staff &nbsp;</td>
                                                        </c:if>
                                                        <c:if test="${employmentBean.staffStatus !='YES' && employmentBean.staffStatus !='NO'}">
                                                        <td><input type="radio" name="staff"  value="YES"> Staff &nbsp; <input type="radio" name="staff" checked="ture" value="NO">Non-Staff &nbsp;</td>
                                                        </c:if>


                                                    <td style="width: 100px;" > <label ></label></td>



                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">

                                                    </td>


                                                    <td></td>




                                                    <td style="width: 100px;" > <label ></label></td>



                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Employment Status
                                                    </td>
                                                    <td>
                                                        <select name="empStatus">
                                                            <option value="">--Select-- </option>
                                                            <c:if test="${employmentBean.employmentStatus =='Employed'}"> <option selected="true" value="Employed">Employed</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus !='Employed'}"> <option  value="Employed">Employed</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus =='Retired'}"> <option selected="true" value="Retired">Retired</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus !='Retired'}"> <option  value="Retired">Retired</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus =='Unemployed'}"> <option selected="true" value="Unemployed">Unemployed</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus !='Unemployed'}"> <option  value="Unemployed">Unemployed</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus =='Student'}"> <option selected="true" value="Student">Student</option></c:if>
                                                            <c:if test="${employmentBean.employmentStatus !='Student'}"> <option  value="Student">Student</option></c:if>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Employment Type
                                                        </td >
                                                        <td >
                                                            <select name="empType">
                                                                <option value="">--Select-- </option>
                                                            <c:if test="${employmentBean.employmentType != null}">
                                                                <c:forEach var="empTypeList" items="${sessionScope.SessionObject.empTypeList}">
                                                                    <c:if test="${employmentBean.employmentType == empTypeList.typeCode}">
                                                                        <option value="${empTypeList.typeCode}" selected="true">${empTypeList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="empTypeList" items="${sessionScope.SessionObject.empTypeList}">
                                                                <c:if test="${employmentBean.employmentType != empTypeList.typeCode}">
                                                                    <option value="${empTypeList.typeCode}">${empTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 


                                                        </select>
                                                        <label>Other,Please Specify </lable>

                                                            <input type="text" name="otherEmpType"  maxlength="64"  value="${employmentBean.otherEmploymentType}">

                                                            </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width: 200px;">Name of Employer </td>
                                                                <td><input type="text" name="companyName"  maxlength="64" style="width: 600px;" value="${employmentBean.companyName}"></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;">Company Address </td>
                                                                <td><input type="text" name="address1"  maxlength="64" style="width: 300px;" value="${employmentBean.adress1}"></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;"> </td>
                                                                <td><input type="text" name="address2"  maxlength="64" style="width: 300px;" value="${employmentBean.adress2}"></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;"> </td>
                                                                <td><input type="text" name="address3"  maxlength="64" style="width: 300px;" value="${employmentBean.adress3}"></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;">Office Telephone </td>
                                                                <td><input type="text" name="officeTelphone"  maxlength="16" value="${employmentBean.officePhone}"></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;">Occupation </td>
                                                                <td><select name="occupation">
                                                                        <option value="">--Select-- </option>
                                                                        <c:if test="${employmentBean.occupation != null}">
                                                                            <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                                <c:if test="${employmentBean.occupation == occupationList.occupationTypeCode}">
                                                                                    <option value="${occupationList.occupationTypeCode}" selected="true">${occupationList.description}</option>
                                                                                </c:if>
                                                                            </c:forEach>

                                                                        </c:if> 
                                                                        <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                            <c:if test="${employmentBean.occupation != occupationList.occupationTypeCode}">
                                                                                <option value="${occupationList.occupationTypeCode}">${occupationList.description}</option>
                                                                            </c:if>
                                                                        </c:forEach> 

                                                                    </select></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;">Designation </td>
                                                                <td><input type="text" name="designation"  maxlength="16" value="${employmentBean.designation}"></td>


                                                            </tr> 
                                                            <tr>
                                                                <td style="width: 200px;">Nature of Business</td>
                                                                <td><select name="businessNature">
                                                                        <option value="">--Select-- </option>
                                                                        <c:if test="${employmentBean.businessNature != null}">
                                                                            <c:forEach var="natureList" items="${sessionScope.SessionObject.natureList}">
                                                                                <c:if test="${employmentBean.businessNature == natureList.natureCode}">
                                                                                    <option value="${natureList.natureCode}" selected="true">${natureList.description}</option>
                                                                                </c:if>
                                                                            </c:forEach>

                                                                        </c:if> 
                                                                        <c:forEach var="natureList" items="${sessionScope.SessionObject.natureList}">
                                                                            <c:if test="${employmentBean.businessNature != natureList.natureCode}">
                                                                                <option value="${natureList.natureCode}">${natureList.description}</option>
                                                                            </c:if>
                                                                        </c:forEach> 

                                                                    </select>
                                                                    <label>Other,Please Specify </lable>

                                                                        <input type="text" name="otherBusinessNature"  maxlength="64"  value="${employmentBean.otherBusinessNature}">

                                                                        </td>


                                                                        </tr> 
                                                                        <tr>
                                                                            <td style="width: 200px;">Number of Employees</td>
                                                                            <td><select name="noOfEmployeers">
                                                                                    <option value="">--Select-- </option>
                                                                                    <c:if test="${employmentBean.numberOfEmployees =='1'}"> <option selected="true" value="1">Up to 10</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees !='1'}"> <option  value="1">Up to 10</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees =='2'}"> <option selected="true" value="2">11 to 50</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees !='2'}"> <option  value="2">11 to 50</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees =='3'}"> <option selected="true" value="3">51 to 100</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees !='3'}"> <option  value="3">51 to 100</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees =='4'}"> <option selected="true" value="4">More than 100</option></c:if>
                                                                                    <c:if test="${employmentBean.numberOfEmployees !='4'}"> <option  value="4">More than 100</option></c:if>



                                                                                    </select></td>


                                                                            </tr> 

                                                                            </table>
                                                                            <br /><hr /><font style="color: #999"> Self Employed Applicant Information</font>

                                                                            <table cellpadding="0" cellspacing="10">
                                                                                <tr>
                                                                                    <td style="width: 200px;">Name of Company/Business </td>
                                                                                    <td><input type="text" name="selfemployeecompanyname" style="width: 600px;"  maxlength="100" value="${employmentBean.selfEmpCompanyname}"></td>



                                                                            </tr>
                                                                        </table>  
                                                                        <table  cellpadding="0" cellspacing="10"> 

                                                                            <tr>
                                                                                <td style="width: 200px;">Number of Employees</td>
                                                                                <td><select name="selfemployeeNoofemployee">
                                                                                        <option value="">--Select-- </option>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee =='1'}"> <option selected="true" value="1">Up to 10</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee !='1'}"> <option  value="1">Up to 10</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee =='2'}"> <option selected="true" value="2">11 to 50</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee !='2'}"> <option  value="2">11 to 50</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee =='3'}"> <option selected="true" value="3">51 to 100</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee !='3'}"> <option  value="3">51 to 100</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee =='4'}"> <option selected="true" value="4">More than 100</option></c:if>
                                                                                        <c:if test="${employmentBean.selfEmpNoOfEmployee !='4'}"> <option  value="4">More than 100</option></c:if>


                                                                                        </select></td>

                                                                                    <td style="width: 200px;"> In Business Since</td>    
                                                                                    <td colspan="2">years <select name="seviceYear" >
                                                                                            <!--                                                       <option value="">years</option>-->
                                                                                        <c:forEach var="years" begin="0" end="50" step="1">
                                                                                            <c:if test="${years==employmentBean.serviceYear}">
                                                                                                <option value="${years}" selected="">${years}</option> 
                                                                                            </c:if>
                                                                                            <c:if test="${years!=employmentBean.serviceYear}">
                                                                                                <option value="${years}">${years}</option>
                                                                                            </c:if>


                                                                                        </c:forEach> 



                                                                                    </select>
                                                                                    &nbsp;months <select name="seviceMonth">
                                                                                        <!--                                                         <option value="">months</option>-->
                                                                                        <c:forEach var="mounth" begin="0" end="12" step="1">
                                                                                            <c:if test="${mounth==employmentBean.serviceMonth}">
                                                                                                <option value="${mounth}" selected="">${mounth}</option> 
                                                                                            </c:if>
                                                                                            <c:if test="${mounth!=employmentBean.serviceMonth}">
                                                                                                <option value="${mounth}">${mounth}</option>
                                                                                            </c:if>



                                                                                        </c:forEach> 
                                                                                    </select>
                                                                                </td>    


                                                                            </tr>
                                                                            <tr>
                                                                                <td style="width: 200px;">Annual Turnover</td>
                                                                                <td><input type="text" name="annualturnover"  maxlength="32" value="${employmentBean.annualTurnOver}"></td>

                                                                                <td style="width: 200px;"> Net Profit</td>    
                                                                                <td><input type="text" name="netprofit"  maxlength="20" value="${employmentBean.netProfit}"></td>  



                                                                            </tr>
                                                                            <tr>
                                                                                <td style="width: 200px;">&nbsp;</td>
                                                                                <td>&nbsp;</td>



                                                                            </tr>
                                                                            <tr>
                                                                                <td style="width: 200px;">Add Remark</td>
                                                                                <td><textarea id="remark2" onfocus="this.rows = 25" cols="50" rows="15" name="remark2" style="width: 200px; height: 60px;">
                                                                                        ${employmentBean.remark}
                                                                                    </textarea></td>
                                                                                <td></td>

                                                                            </tr>
                                                                        </table>
                                                                        <table>

                                                                            <tr>
                                                                                <td style="width: 200px;"></td>
                                                                                <td><input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}', '${sessionScope.SessionObject.cardCategory}', '${sessionScope.SessionObject.applicationTypeCode}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                                            </tr>

                                                                        </table> 




                                                                        </form>   
                                                                        </div>
                                                                        <!--   tab 3 ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                  -->
                                                                        <div id="tabs-3">
                                                                            <form name="incomeform" method="POST" action="<%=request.getContextPath()%>/SetUpdateIncomeDataToSessionServlet">
                                                                                <br /><font style="color: #999"> Income Information</font>
                                                                                <table  cellpadding="0" cellspacing="10">
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Income Category </td>
                                                                                        <td><select name="incomeCategory">
                                                                                                <option>--Select Category--</option>                                                    

                                                                                                <c:forEach var="incomeTypeList" items="${sessionScope.SessionObject.incomeTypeList}">

                                                                                                    <option value="${incomeTypeList.incomeCode}">${incomeTypeList.description}</option>

                                                                                                </c:forEach> 

                                                                                            </select>
                                                                                        </td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidCategory} </label></td>

                                                                                    </tr> 
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Amount  </td>
                                                                                        <td><input type="text" name="amount"  maxlength="16"  value="" />
                                                                                        </td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidAmount} </label></td>



                                                                                    </tr> 
                                                                                    <tr>
                                                                                        <td style="width: 200px;"> </td>
                                                                                        <td><input type="submit" name="add"  style="width: 100px;"  value="Add">
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>

                                                                                        <td style="width: 200px;"></td>
                                                                                        <td>
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>

                                                                                    </tr> 
                                                                                </table>
                                                                            </form>
                                                                            <form name="incomeexpensesform" method="POST" action="<%=request.getContextPath()%>/UpdateIncomeExpensesServlet">

                                                                                <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview2">
                                                                                    <thead >
                                                                                        <tr  class="gradeB">
                                                                                            <th width="100px;" scope="col">Income Category</th>
                                                                                            <th width="100px;" scope="col">Amount</th>
                                                                                            <th width="100px;" scope="col">Delete</th>

                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                        <c:forEach var="incomeList" items="${sessionScope.SessionObject.sessionIncomeList}">
                                                                                            <tr class="gradeC">
                                                                                                <td >
                                                                                                    ${incomeList.incomeCatogary}
                                                                                                </td>
                                                                                                <td >
                                                                                                    ${incomeList.amount}
                                                                                                </td>
                                                                                                <td >
                                                                                                    <input type="button" value="Delete" onclick="invokeRemoveIncome('${incomeList.incomeCatogary}')">
                                                                                                </td>

                                                                                            </tr>
                                                                                        </c:forEach>


                                                                                    </tbody>


                                                                                </table>


                                                                                <table cellpadding="0" cellspacing="10">
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Income Sub Total</td>
                                                                                        <!--                                                                                        <td><input type="text" name="lastname"  maxlength="32" value=""></td>-->
                                                                                        <td><input type="text" name=""  maxlength="32" value="${sessionScope.SessionObject.sumOfIncome}" disabled="true"></td>
                                                                                        <td><input type="hidden" name="netincome"  maxlength="32" value="${sessionScope.SessionObject.sumOfIncome}"></td>
                                                                                    </tr>

                                                                                </table>
                                                                                <br /><hr /><font style="color: #999"> Expenses Information</font>
                                                                                <table  cellpadding="0" cellspacing="10"> 


                                                                                    <tr>
                                                                                        <td style="width: 200px;">Rent </td>
                                                                                        <td><input type="text" name="rent" id="rent" maxlength="16"  value="${expenseBean.rentAmount}" onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.rentAmount} </label></td>

                                                                                    </tr> 

                                                                                    <tr>
                                                                                        <td style="width: 200px;">Loan Installment</td>
                                                                                        <td><input type="text" name="loanInstallment" id="loanInstallment" maxlength="32" value="${expenseBean.loanInstallmentAmount}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.loanInstallmentAmount} </label></td>



                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Lease rental</td>
                                                                                        <td><input type="text" name="leaseRental" id="leaseRental"  maxlength="32" value="${expenseBean.leaseAmount}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.leaseAmount} </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Credit Card Bill</td>
                                                                                        <td><input type="text" name="creditCardBill" id="creditCardBill" maxlength="32" value="${expenseBean.creditCardbill}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.creditCardbill} </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Other Borrowings</td>
                                                                                        <td><input type="text" name="otherBorrowing" id="otherBorrowing" maxlength="32" value="${expenseBean.otherBorrows}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.otherBorrows} </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Insurance Premiums</td>
                                                                                        <td><input type="text" name="insurance" id="insurance"  maxlength="32" value="${expenseBean.insuranceInstallment}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.insuranceInstallment} </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">House holder expenses</td>
                                                                                        <td><input type="text" name="houseHolder" id="houseHolder" maxlength="32" value="${expenseBean.houseHolderExpenses}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.houseHolderExpenses} </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Other expenses</td>
                                                                                        <td><input type="text" name="otherExpense" id="otherExpense" maxlength="32" value="${expenseBean.otherExpenses}"  onkeyup="invorkeSumOfExpenses()"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red">${invalidExpenseBean.otherExpenses} </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Expenses Sub Total</td>
                                                                                        <td><input type="text" name="netexpenses1"  maxlength="18" id="sumOfExpenses1" value="" disabled="true"></td>
                                                                                        <td style="width: 100px;" > <label style="color: red"> </label></td>


                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td><input type="hidden" name="netexpenses"  maxlength="18" id="sumOfExpenses" value=""></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">&nbsp;</td>
                                                                                        <td>&nbsp;</td>



                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Add Remark</td>
                                                                                        <td><textarea id="remark3" onfocus="this.rows = 25" cols="50" rows="15" name="remark3" style="width: 200px; height: 60px;">
                                                                                                ${expenseBean.remark}
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

                                                                        <!--   tab 4 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                                                        <div id="tabs-4">
                                                                            <form name="banksessionform" method="POST" action="<%=request.getContextPath()%>/SetUpdateAccountDataToSessionServlet">

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
                                                                                        <td><input type="text" name="accNumber"  maxlength="16"  value="">
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>



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

                                                                                <table width="700px" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview">
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
                                                                        <!--   tab 5 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                                                        <div id="tabs-5">
                                                                            <form name="uploadform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadUpdateDocumentServlet">

                                                                                <table  cellpadding="0" cellspacing="10">
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Verification Category </td>
                                                                                        <td><select name="vCategory" onchange="invorkSetDocumentTypes(value)">
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
                                                                                        <td><select name="dType">
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
                                                                                        <td><input type="file" id="upload" name="Upload" >
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>

                                                                                    </tr> 


                                                                                    <tr>
                                                                                        <td style="width: 200px;"> </td>
                                                                                        <td><input type="submit" name="upload"  style="width: 100px;"  value="Upload" >
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
                                                                                <table>
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

                                                                        <!--            tab 6   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                                         -->
                                                                        <div id="tabs-6">
                                                                            <form name="signatureform" enctype="multipart/form-data" method="POST" action="<%=request.getContextPath()%>/UploadUpdateSignatureServlet">

                                                                                <table  cellpadding="0" cellspacing="10">

                                                                                    <tr>
                                                                                        <td style="width: 200px;">Choose File </td>
                                                                                        <td><input type="file" id="upload" name="Upload" >
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>

                                                                                    </tr> 


                                                                                    <tr>
                                                                                        <td style="width: 200px;"> </td>
                                                                                        <td><input type="submit" name="add"  style="width: 100px;"  value="Upload" >
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>



                                                                                    </tr>
                                                                                </table>
                                                                            </form>

                                                                            <form name="completeform" method="POST" action="<%=request.getContextPath()%>/UpdateSignatureServlet">
                                                                                <!--Recommendation-->
                                                                                <font style="color: #999">Recommendation</font>
                                                                                <table  cellpadding="0" cellspacing="10">
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Name</td>
                                                                                        <td>
                                                                                            <input type="text" name="recName" id="_recName" value="${personalBean.recName}"/>
                                                                                        </td>
                                                                                        <td style="width: 50px;">
                                                                                            <label style="color: red">${invalidRecBean.recName}</label>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Credit Card Number</td>
                                                                                        <td>
                                                                                            <input type="text" name="recCreditCardNum" id="_recCreditCardNum" value="${personalBean.recCreditCardnum}"/>
                                                                                        </td>
                                                                                        <td style="width: 50px;">
                                                                                            <label style="color: red">${invalidRecBean.recCreditCardnum}</label>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">TP</td>
                                                                                        <td>
                                                                                            <input type="text" name="recTpNum" id="_recTpNum" value="${personalBean.recPhone}"/>
                                                                                        </td>
                                                                                        <td style="width: 50px;">
                                                                                            <label style="color: red">${invalidRecBean.recPhone}</label>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">Date</td>
                                                                                        <td>
                                                                                            <input  name="recDate" id="_recDate" maxlength="16" readonly value="${personalBean.recDate}"/>

                                                                                            <script type="text/javascript">
                                                                                                $(function () {
                                                                                                    $("#_recDate").datepicker({
                                                                                                        showOn: "button",
                                                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                                                        changeMonth: true,
                                                                                                        changeYear: true,
                                                                                                        buttonImageOnly: true,
                                                                                                        dateFormat: "yy-mm-dd",
                                                                                                        showWeek: true,
                                                                                                        firstDay: 1,
                                                                                                    });
                                                                                                });
                                                                                            </script>

                                                                                        </td>
                                                                                    </tr
                                                                                    <tr>
                                                                                        <td style="width: 200px;"> </td>
                                                                                        <td>

                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>



                                                                                    </tr> 
                                                                                    <tr>

                                                                                        <td colspan="3">

                                                                                            <img src="<%=request.getContextPath()%>/SignatureLoadServlet" />
                                                                                        </td>
                                                                                        <td style="width: 100px;"></td>



                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="width: 200px;">&nbsp;</td>
                                                                                        <td>&nbsp;</td>



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