<%-- 
    Document   : cardAgainstFDapplicationapproveview
    Created on : May 17, 2013, 3:04:18 PM
    Author     : asela
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


            function displayMsg(msg) {
                $("#error").text(msg);
            }


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

            //            function downloadLetter(applicationId){
            //              
            //                window.location = "${pageContext.request.contextPath}/DownloadLetterServlet?applicationId="+applicationId;
            //              
            //            }

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


            //            function downloadLetter(applicationId){

            //                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
            //                $.ajax({
            //                    url: "${pageContext.request.contextPath}/DownloadLetterServlet",
            //                    type: "POST",
            //                    data: {applicationId : applicationId},
            //                    success : function(data){
            //                        $("#error").text(data);
            //                    }
            //                });

            //            }



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

//            function loadCustomerTemplates(){
//                $('#customerTemplate').empty();
//             
// 
//                  
//                var pVal = $("#confirmCardProduct").val();
//                    
//                $.getJSON("${pageContext.servletContext.contextPath}/LoadCustomerTemplatesServlet", 
//                { sValue : sval,
//                    pValue : pVal
//                },
//                function(jsonobject) {
//                    $.each(jsonobject.combo1, function(code, description) {
//                        $('#customerTemplate').append(
//                        $('<option></option>').val(code).html(description)
//                    );
//                    });
//                });
//              
//            }


            function loadAccountTemplates(applicationid, currency) {

                $('#accountTemplates').empty();


                var applicationId = applicationid;
                var cVal = $("#customerTemplate").val();
                var currency = currency;

                $.getJSON("${pageContext.servletContext.contextPath}/LoadAccountTemplatesServlet",
                        {
                            applicationId: applicationId,
                            cValue: cVal,
                            currency: currency
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

            //             function loadCusTemplates(){
            //                $('#customerTemplate').empty();
            //             
            //               
            //                var sval1=$("#staffYes").val();
            //                var sval2=$("#staffNo").val();
            //                
            //                if(sval1 == null){
            //                    var sval = sval2;
            //                }else{
            //                    var sval = sval1;
            //                }
            //                
            //                var pVal = $("#confirmCardProduct").val();
            //                    
            //                $.getJSON("${pageContext.servletContext.contextPath}/LoadCustomerTemplatesServlet", 
            //                { sValue : sval,
            //                  pValue : pVal
            //                },
            //                function(jsonobject) {
            //                    $.each(jsonobject.combo1, function(code, description) {
            //                        $('#customerTemplate').append(
            //                        $('<option></option>').val(code).html(description)
            //                    );
            //                    });
            //                });
            //              
            //            }



            //function Confirm(applicationid,currency)
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
                    document.confirmForm.action = "${pageContext.request.contextPath}/LoadCardAgainstFDApproveDetailsServlet?applicationid=" + applicationid;
                }

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
                <form name="sucMessage" id="succForm" action="${pageContext.request.contextPath}/LoadApplicationApproveTableServlet">
                    <input type="hidden" name="successMsg" id="succMes"></input>
                </form>

                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  APPLICATION CONFIRMATION </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000" id="error"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>




                                <!--                                <form name="confirmForm" action="" method="POST">-->
                                <table cellpadding="0" cellspacing="10">
                                    <tr>
                                        <td style="width: 200px;"> Application ID</td>
                                        <td><b><font color="#000000"> ${cardApplicationList.applicationId}</font></b> </td>

                                        <td style="width: 240px;"></td>

                                        <td style="width: 200px;"> Card Category</td>
                                        <td><b><font color="#000000"> Card Against FD</font></b> </td>
                                    </tr>


                                </table>
                                <br/>
                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Personal Information </a></li>
                                        <li><a href="#tabs-2">Contact Information</a></li>
                                        <li><a href="#tabs-3">Card Request Information</a></li>

                                    </ul>            

                                    <div id="tabs-1" >   
                                        <table cellpadding="0" cellspacing="10" style="height: 200px;" >


                                            <tr>
                                                <td style="width: 200px;">
                                                    Identification Type
                                                </td>

                                                <td>
                                                    <b><font color="#000000"> ${cardApplicationList.identificationType}</font></b>
                                                </td>


                                                <td style="width: 200px;"> Religion</td>    

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.religion}</font></b>

                                                </td>

                                                <td style="width: 100px;"></td> 

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">
                                                    Identification Number
                                                </td>
                                                <td>
                                                    <b><font color="#000000"> ${cardApplicationList.identificationNumber}</font></b>
                                                </td>

                                                <td style="width: 200px;"> Gender</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.gender}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                            </tr>


                                            <tr>
                                                <td style="width: 200px;">Title</td>

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.title}</font></b>
                                                </td>


                                                <td style="width: 200px;"> Full Name</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.fullName}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 


                                            </tr>

                                            <tr>

                                                <td style="width: 200px;">Date Of Birth</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.birthday}</font></b>
                                                </td>


                                                <td style="width: 200px;"> Marital Status</td>    

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.maritalStatus}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                            </tr>
                                            <tr>

                                                <td style="width: 200px;">Place of Birth</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.placeOfbirth}</font></b>
                                                </td>

                                                <td style="width: 200px;"> Nationality</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.nationality}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Name on the Card</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.nameOncard}</font></b>
                                                </td>



                                                <td style="width: 200px;"> Blood Group</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.bloodgroup}</font></b>
                                                </td>




                                            </tr>

                                        </table>
                                    </div>


                                    <div id="tabs-2" >
                                        <table cellpadding="0" cellspacing="10" style="height: 200px;" >    


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

                                                <td style="width: 200px;"> Office Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.officeTelNumber}</font></b>
                                                </td>
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address3}</font></b>
                                                </td>

                                                <td style="width: 200px;"> Mobile Phone No</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.mobileNumber}</font></b>
                                                </td>
                                            </tr>
                                            <tr>

                                                <td style="width: 200px;">City</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.city}</font></b>
                                                </td>

                                                <td style="width: 200px;">Email Address</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.email}</font></b>
                                                </td>
                                            </tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>

                                        </table>
                                    </div>  

                                    <div id="tabs-3" >            
                                        <table cellpadding="0" cellspacing="10" style="height: 200px;" >    

                                            <tr>
                                                <td style="width: 200px;">Credit Limit</td>
                                                <td>
                                                    <b><font color="#000000">${personalDetail.currencyTypeAlphaCode} ${requestedCreditLimit}</font></b>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Card Type</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardTypeDes}</font></b>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Card Product</td>

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardProductDes}</font></b>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Currency Type</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.currencyDes}</font></b>
                                                </td>
                                            </tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                        </table>
                                    </div>
                                </div> 

                                <!--                                <iframe style="width: 0px; height: 0px; visibility: hidden;" name="downloadform" src="#"></iframe>-->
                                <form method="POST" action="DownloadLetterServlet" target="downloadform">         
                                    <table cellpadding="0" cellspacing="10"  >                        
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Credit Officer Recommended</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${personalDetail.currencyTypeAlphaCode}&nbsp;<fmt:formatNumber type="number" value="${cardApplicationList.cOfficerRecCrditLimt}"/></font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Product</td>
                                            <c:forEach var="card" items="${cardProducts}">
                                                <c:if test="${card.productCode==cardApplicationList.cOfficerRecCardProduct}">
                                                    <td><b><font color="#000000">${card.description}</font></b></td> 
                                                        </c:if>
                                                    </c:forEach>
                                        </tr>
                                    </table>


                                    <table cellpadding="0" cellspacing="10"  >                                                                

                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br/>Recommendation Letter</font></td>
                                        </tr>
                                        <tr>
                                            <!--                                    <FORM  ENCTYPE="multipart/form-data" name="download" METHOD=POST>-->

                                        <tr>
                                            <td>
                                                <table>
                                                    <!--                                                    <td><input type="button" value="View" name="next" style="width: 100px"></input></td>-->

                                                    <td><input type="hidden" value="${cardApplicationList.applicationId}" name="applicationId" style="width: 100px"  ></input></td>
                                                    <td><input type="submit" value="Download" name="next" style="width: 100px"  ></input></td>
                                                </table>
                                            </td>
                                        </tr>

                                        <!--                                </form>-->

                                        </tr>

                                    </table>
                                </form> 
                                <form name="confirmForm" action="" method="POST" id="confirmForm">
                                    <table><td><b><font color="#FF0000" id="errorMsgcon"> ${errorMsgcon}</font></b> </td></table>
                                    <div class="outset" style="border-style:outset; background-color: #B8B8B8 ;  border-color: #999; width: 100%">

                                        <table cellpadding="0" cellspacing="10"  >      
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Confirm</font></td>
                                            </tr>
                                            <!--                                    <form name="acceptForm" action="" method="POST" >-->


                                            <tr>
                                                <td style="width: 200px;"><font style="color: red;">* </font>Card Products</td>
                                                <td>
                                                    <select id="confirmCardProduct" name="confirmCardProduct"  onchange="LoadBinProfiles()" class="inputfield-mandatory" >
                                                        <option value="" selected>-------------SELECT-------------</option>
                                                        <c:forEach var="card" items="${cardProducts}">

                                                            <option value="${card.productCode}">${card.description}</option>

                                                        </c:forEach>
                                                    </select>

                                                </td> 
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><font style="color: red;">* </font>Credit Limit in ${personalDetail.currencyTypeAlphaCode}</td>

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
                                                    <select id="customerTemplate" name="customerTemplate" onchange="loadAccountTemplates('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')"  class="inputfield-mandatory">
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
                                                    <select id="accountTemplates" name="accountTemplates"  onchange="loadCardTempList('${cardApplicationList.applicationId}', '${personalDetail.cardCurrency}')" class="inputfield-mandatory">
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

                                                <td><input type="hidden" name="applicationid" value="${cardApplicationList.applicationId}" hidden="true"></input></td>
                                                <td><input type="hidden" name="currency" value="${personalDetail.cardCurrency}" hidden="true"></input></td>
                                                <td><input type="hidden" id="cardCategoryCode" name="cardCategoryCode" value="F" hidden="true"></input></td>
                                                <!--Identification Number-->
                                                <td><input type="hidden" name="identificationNum" value="${cardApplicationList.identificationNumber}"/></td>
                                                <!--Identification Type-->
                                                <td><input type="hidden" name="identificationType" value="${cardApplicationList.identificationType}"/></td>

                                            </tr>

                                            <tr>

                                                <td>
                                                    <table>

<!--                                                        <td><input type="submit" value="Confirm"  onclick="Confirm('${cardApplicationList.applicationId}','${personalDetail.cardCurrency}')" style="width: 100px"></input></td>-->
                                                        <td><input type="button" value="Confirm"  onclick="Confirm()" style="width: 100px"></input></td>
                                                        <td><input type="button" value="Reset" style="width: 100px" onclick="resetConfirmation()"></input></td>

                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <!--                                </form>-->
                                    <!--                                        <tr>
                                                                                <td colspan="6" ><hr /></td>
                                                                            </tr>-->
                                    <br/>
                                    <div class="outset"   width: 100%">


                                         <table cellpadding="0" cellspacing="10"  >     
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Reject</font></td>
                                            </tr>

                                            <!--                                    <form name="rejectForm" action="" method="POST">-->
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
                                <!--   ------------------------- end developer area  --------------------------------                      -->
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

