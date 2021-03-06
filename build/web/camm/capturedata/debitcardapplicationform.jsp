<%-- 
    Document   : debitcardapplicationform
    Created on : Aug 22, 2012, 10:54:50 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/fileupload/ajaxfileupload.js"></script>
        <script>
            
            function primaryBD()
            {
                newdate = primaryForm.elements["birthday"].value;
                today = new Date();
                yesterday = new Date(today.getTime()-(24*60*60*1000));
               
                if(Date.parse(newdate)>Date.parse(yesterday)){
                    alert("Enter Valid Date");
                    primaryForm.elements["birthday"].value = "";
                }
                     
            }
            
            function secondaryBD()
            {
                newdate = seconderyForm.elements["birthdayS"].value;
                today = new Date();
                yesterday = new Date(today.getTime()-(24*60*60*1000));
               
                if(Date.parse(newdate)>Date.parse(yesterday)){
                    alert("Enter Valid Date");
                    seconderyForm.elements["birthdayS"].value = "";
                }
                     
            }
            
            function invokeSave(){
                
                if(document.getElementById('loyalityRequired').checked){
                    loyalityRequired= "YES";
                }else{                   
                    loyalityRequired = "NO";
                }
              
                document.primaryForm.action="${pageContext.request.contextPath}/AddPrimaryDebitCardApplicationFormServlet?loyalityRequired="+loyalityRequired;
                document.primaryForm.submit();
            }
            
            function lordCardProduct(){
                $('#cardProduct').empty();
                var sval=$("#cardType").val();
                $.getJSON("${pageContext.servletContext.contextPath}/SetCardProductOnchangeServlet", 
                { cardTypeCode : sval},
                function(jsonobject) {
                    $('#cardProduct option').each(function(){
                        $(this).remove()
                    });
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#cardProduct').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
            }
            
            function lordCardProductSecondary(){
                $('#cardProductS').empty();
                var sval=$("#cardTypeS").val();
                $.getJSON("${pageContext.servletContext.contextPath}/SetCardProductOnchangeServlet", 
                { cardTypeCode : sval},
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#cardProductS').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
            
            
            //            $('#cardProduct option').each(function(){
            //                           $(this).remove()
            //                       });
            
            
            //            function tabDisable(){
            //                
            //                if(${sessionScope.SessionObject.cardCategory} == 'Join'){
            //                    
            //                    //$("#tabs").tabs("option", "disabled",[${""}]);
            //                    document.primaryForm.save.disabled = true;
            //                    document.primaryForm.saveNext.disabled = false;
            //             
            //                }  else{  
            //                                    
            //                    //$("#tabs").tabs("option", "disabled",[${"1"}]);
            //                    document.primaryForm.save.disabled = false;
            //                    document.primaryForm.saveNext.disabled = true;
            //                    
            //                }
            //            }
            
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/LoadDebitCardApplicationServlet";
            }
            
            function invokeReset(applicationid,cardcategory){
                
                window.location = "${pageContext.request.contextPath}/LoadDebitCardApplicationFormServlet?applicationid="+applicationid+"&cardcategory="+cardcategory;
            }
            
            function invokeResetSecondary(ele){
                               
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                
                    }
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }
                
            }
            
           
            
            //////////////////////////////////////////////////////////////////////UploadPrimarySignatureServlet1048576
                
            function getSize(input)
            {
                var input,status;
                var maxfileSize = 1047552; // 1 Mb
                
                if (!window.FileReader) {
                    alert("The file API isn't supported on this browser yet.");
                    return;
                }
                //                input = document.getElementById('signature');
                if (!input) {
                    alert("Um, couldn't find the fileinput element.");
                    return;
                }
                else if (!input.files) {
                    alert("This browser doesn't seem to support the `files` property of file inputs.");
                    return;
                }
                else if (!input.files[0]) {
                    //                    alert("Please select a file before clicking 'Load'");
                    status =2;
                }
                else if(input.files[0].size > maxfileSize){
                    //                    alert("File size too much large");
                    status =1;
                }else{
                    //                    alert("Success");
                    status =0; 
                }
                return status;
            }
            ////////////////////////////////////////////////////////////////////////           
            
            function uploadPrimarySigneture(applicationId)
            {
                var status;
                input = document.getElementById('signature');
                status = getSize(input);
                if(status ==2){
                    document.getElementById('pSigErr').innerHTML = 'Please select a file before clicking load';
                }else if(status ==1){
                    document.getElementById('pSigErr').innerHTML = 'File size too much large';
                }else  if(status==0){
                    
                    if(document.getElementById('loyalityRequired').checked){
                        loyalityRequired= "YES";
                    }else{                   
                        loyalityRequired = "NO";
                    }
                    document.primaryForm.action="${pageContext.request.contextPath}/UploadPrimarySignatureServlet?applicationId="+applicationId+"&loyalityRequired="+loyalityRequired;
                    document.primaryForm.submit(); 
                }
            }  
            
            
            function uploadPrimaryNIC(applicationId)
            {
                var status;
                input = document.getElementById('nic');
                status = getSize(input);
                if(status ==2){
                    document.getElementById('pNicErr').innerHTML = 'Please select a file before clicking load';
                }else if(status ==1){
                    document.getElementById('pNicErr').innerHTML = 'File size too much large';
                }else  if(status==0){
                    if(document.getElementById('loyalityRequired').checked){
                        loyalityRequired= "YES";
                    }else{                   
                        loyalityRequired = "NO";
                    }

                    document.primaryForm.action="${pageContext.request.contextPath}/UploadPrimaryNicServlet?applicationId="+applicationId+"&loyalityRequired="+loyalityRequired;
                    document.primaryForm.submit();
                }
            }

            function uploadSecondrySigneture(applicationId)
            {
                var status;
                input = document.getElementById('signatureS');
                status = getSize(input);
                if(status ==2){
                    document.getElementById('sSigErr').innerHTML = 'Please select a file before clicking load';
                }else if(status ==1){
                    document.getElementById('sSigErr').innerHTML = 'File size too much large';
                }else  if(status==0){
                    if(document.getElementById('loyalityRequired').checked){
                        loyalityRequired= "YES";
                    }else{                   
                        loyalityRequired = "NO";
                    }
               
                    document.seconderyForm.action="${pageContext.request.contextPath}/UploadSecondarySignatureServlet?applicationId="+applicationId+"&loyalityRequired="+loyalityRequired;
                    document.seconderyForm.submit();
                }
            }
            
            function uploadSecondryNic(applicationId)
            {
                var status;
                input = document.getElementById('nicS');
                status = getSize(input);
                if(status ==2){
                    document.getElementById('sNicErr').innerHTML = 'Please select a file before clicking load';
                }else if(status ==1){
                    document.getElementById('sNicErr').innerHTML = 'File size too much large';
                }else  if(status==0){
                    if(document.getElementById('loyalityRequired').checked){
                        loyalityRequired= "YES";
                    }else{                   
                        loyalityRequired = "NO";
                    }
               
                    document.seconderyForm.action="${pageContext.request.contextPath}/UploadSecondryNicServlet?applicationId="+applicationId+"&loyalityRequired="+loyalityRequired;
                    document.seconderyForm.submit();
                }
            }
            
            function invokeSaveSecondary(){
                
                document.seconderyForm.action="${pageContext.request.contextPath}/AddSecondaryDebitCardApplicationFormServlet";
                document.seconderyForm.submit();
            }
            
           
            //            function clsarBH(){
            //                var Backlen=history.length;   history.go(-Backlen);
            ////                window.location.href="${pageContext.request.contextPath}/SetCardProductOnchangeServlet"
            //
            //            }
           
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBITDATACAPTURE%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
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
            </div>
            <div class="main" >
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <script> settitle();</script>
                                <table>
                                    <tr >
                                        <td><b><font color="#FF0000"><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <table cellpadding="0" cellspacing="10">

                                    <tr>
                                        <td style="width: 150px;"> Application ID :</td>
                                        <td><label>${sessionScope.SessionObject.applicationId}</label></td>
                                        <td style="width: 100px;"></td> 
                                        <td style="width: 150px;"> Application Type :</td>
                                        <td><label>${sessionScope.SessionObject.cardCategory}</label></td>
                                    </tr>
                                </table>

                                <br /><hr /><br />

                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Joint</a></li>
                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form ENCTYPE="multipart/form-data" name="primaryForm" id="primaryForm" method="post" action="">
                                            <table cellpadding="0" cellspacing="10"  >

                                                <tr>
                                                    <td style="width: 200px;">Identification Type</td>

                                                    <td><select name="identificationType" disabled="true">
                                                            <option value="">--Select Identification Type-- </option>

                                                            <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                                <c:if test="${sessionScope.SessionObject.idBean.idType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${sessionScope.SessionObject.idBean.idType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>        
                                                        </select>
                                                        <input type="hidden" name="identificationTypeHidden" value="${sessionScope.SessionObject.idBean.idType}" hidden="true" >
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Identification Number</td>
                                                    <td><label name="identificationNumber" >${sessionScope.SessionObject.idBean.idNumber}</label>
                                                        <input type="hidden" name="identificationNumber" value="${sessionScope.SessionObject.idBean.idNumber}" hidden="true" ></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Title</td>
                                                    <td><select name="title" TABINDEX=1>
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
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.title} </label></td> 
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td>
                                                        <input  name="birthday" maxlength="16" readonly value="${personalBean.birthday}" key="birthday" id="birthday" onchange="primaryBD()" TABINDEX=7 />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#birthday" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1,
                                                                    maxDate: 0
                                                                });
                                                            });
                                                        </script>
                                                    </td>
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.birthday} </label></td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">First Name</td>
                                                    <td><input type="text" name="firstName"  maxlength="32" value="${personalBean.firstName}" TABINDEX=2 ></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.firstName} </label></td>    

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;"> Address</td>    
                                                    <td><input type="text" name="address1"  maxlength="20" value="${personalBean.address1}" TABINDEX=8></td>    
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.address1} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Middle Name</td>    
                                                    <td><input type="text" name="middleName"  maxlength="20" value="${personalBean.middleName}" TABINDEX=3 ></td>  
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.middleName} </label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="address2"  maxlength="32" value="${personalBean.address2}" TABINDEX=9></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.address2} </label></td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Last name</td>
                                                    <td><input type="text" name="lastName"  maxlength="32" value="${personalBean.lastName}" TABINDEX=4></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.lastName} </label></td>  

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"> </td>    
                                                    <td><input type="text" name="address3"  maxlength="20" value="${personalBean.address3}" TABINDEX=10>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.address3} </label></td>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Name on the Card</td>
                                                    <td><input type="text" name="nameOncard" style="background: #C4D2E0" maxlength="64" value="${personalBean.nameOncard}" TABINDEX=5></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.nameOncard}</label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   
                                                    <td style="width: 200px;"> Area</td>    
                                                    <td><select name="city" TABINDEX=11>
                                                            <option value="">--Select Area--</option>

                                                            <c:forEach var="area" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${personalBean.city==area.areaCode}">
                                                                    <option value="${area.areaCode}" selected="true" >${area.description}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.city!=area.areaCode}">
                                                                    <option value="${area.areaCode}">${area.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.city}</label></td>    
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Mothers Maiden Name</td>    
                                                    <td><input type="text" name="mothersMaidenName"  maxlength="64" value="${personalBean.mothersMaidenName}" TABINDEX=6></td>                                              
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.mothersMaidenName}</label></td> 


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"> Mobile Number</td>    
                                                    <td><input type="text" name="mobileNumber"  maxlength="20" value="${personalBean.mobileNumber}" TABINDEX=12></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.mobileNumber}</label></td>
                                                </tr>

                                                <tr>
                                                    <td ></td>
                                                    <td></td>
                                                    <td></td>

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;">Res. Phone No</td>
                                                    <td><input type="text" name="homeTelNumber"  maxlength="20" value="${personalBean.homeTelNumber}" TABINDEX=13></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.homeTelNumber}</label></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 100px;"><label style="color: red"></label></td>

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td>Office Phone No</td>
                                                    <td><input type="text" name="officeTelNumber"  maxlength="20" value="${personalBean.officeTelNumber}" TABINDEX=14></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.officeTelNumber} </label></td>
                                                </tr>


                                                <tr>
                                                    <td style="width: 200px;">Required Card Type</td>

                                                    <td  style="width: 200px;"><select  name="cardType" id="cardType" class="inputfield-mandatory"  onchange="lordCardProduct()" TABINDEX=15 >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cardTypeList}">
                                                                <c:if test="${personalBean.cardType==type.cardTypeCode}">

                                                                    <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.cardType}</label></td>

                                                    <td style="width: 200px;">Required Card Product</td>
                                                    <td style="width: 200px;"><select  id="cardProduct" class="inputfield-mandatory" name="cardProduct" TABINDEX=16 >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="product" items="${sessionScope.SessionObject.cardProductMgtList}"> 
                                                                <c:if test="${personalBean.cardProduct==product.productCode}">
                                                                    <option value="${product.productCode}" selected="true" >${product.description}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.cardProduct!=product.productCode}">
                                                                    <option value="${product.productCode}">${product.description}</option>
                                                                </c:if>  
                                                            </c:forEach>  

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.cardProduct}</label></td>
                                                </tr>

                                                <!--       ///////////////////////Account Details///////////////////////////////////         -->
                                                <br/>
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Account Details</font></td>
                                                </tr>
                                                <hr />

                                                <tr>
                                                    <td style="width: 200px;">Primary Account Number</td>
                                                    <td style="width: 200px;"><input type="text" name="accountNo"  maxlength="12" value="${personalBean.accountNo}" TABINDEX=17></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.accountNo}</label></td>


                                                    <td style="width: 200px;">Secondary Account Number</td>
                                                    <td style="width: 200px;"><input type="text" name="accountNo2"  maxlength="12" value="${personalBean.accountNo2}" TABINDEX=20></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.accountNo2}</label></td>
                                                </tr> 

                                                <tr>
                                                    <td style="width: 200px;">Primary Account Type</td>
                                                    <td style="width: 200px;"><select   class="inputfield-mandatory" name="accNo1Type" TABINDEX=18 >

                                                            <c:forEach var="acc" items="${sessionScope.SessionObject.accountTypeList}"> 
                                                                <c:if test="${personalBean.accNo1Type==acc.key}">
                                                                    <option value="${acc.key}" selected="true" >${acc.value}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.accNo1Type!=acc.key}">
                                                                    <option value="${acc.key}">${acc.value}</option>
                                                                </c:if>  
                                                            </c:forEach>  

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                    <td style="width: 200px;">Secondary Account Type</td>
                                                    <td style="width: 200px;"><select   class="inputfield-mandatory" name="accNo2Type" TABINDEX=21 >

                                                            <c:forEach var="acc" items="${sessionScope.SessionObject.accountTypeList}"> 
                                                                <c:if test="${personalBean.accNo2Type==acc.key}">
                                                                    <option value="${acc.key}" selected="true" >${acc.value}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.accNo2Type!=acc.key}">
                                                                    <option value="${acc.key}">${acc.value}</option>
                                                                </c:if>  
                                                            </c:forEach>  

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Primary Currency</td>
                                                    <td style="width: 200px;"><select  name="accNo1Currency"   class="inputfield-mandatory" TABINDEX=19>

                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${personalBean.accNo1Currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.accNo1Currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                    <td style="width: 200px;">Secondary Currency</td>
                                                    <td style="width: 200px;"><select  name="accNo2Currency"   class="inputfield-mandatory" TABINDEX=22>

                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${personalBean.accNo2Currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.accNo2Currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                </tr>

                                                <tr></tr>
                                                <tr>                                   
                                                    <td style="width: 200px;">Loyality Required</td>

                                                    <td><input type="checkbox" name="loyalityRequired" id="loyalityRequired" TABINDEX=23 <c:if test="${personalBean.loyalityRequired=='YES'}"> checked="true" </c:if>>
                                                    </td> 
                                                </tr>

                                                <!--          ////////////////////End of Account Details/////////////////////////////////                                      -->

                                            </table>

                                            <table>

                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Signature</font></td>
                                                </tr>

                                                <tr></tr>

                                                <tr>
                                                    <td>Choose the file To Upload &nbsp; &nbsp;</td>
                                                    <td><input  name="signature" id="signature" type="file"  TABINDEX=24  /><input type="button" value="Upload" name="primerySigImg"  id="primerySigImg" style="width: 100px" onclick="uploadPrimarySigneture('${personalBean.applicationId}')"> </ input></td>
                                                    <td><b><font color="#FF0000"><label id="pSigErr"> ${invalidMsgBean.signatureFileName}</label></font></b> </td>
                                                </tr>

                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>National Identity Card</font></td>
                                                </tr>

                                                <tr></tr>

                                                <tr>
                                                    <td>Choose the file To Upload &nbsp; &nbsp;</td>
                                                    <td><input  name="nic"  id="nic"  type="file" TABINDEX=25    /><input type="button"  value="Upload" name="nic" style="width: 100px" onclick="uploadPrimaryNIC('${personalBean.applicationId}')"> </ input></td> 
                                                    <td><b><font color="#FF0000"><label id="pNicErr"> ${invalidMsgBean.nicFileName}</label></font></b> </td>
                                                </tr>

                                            </table>

                                            <br />

                                            <table cellpadding="0" cellspacing="10" >
                                                <tr >
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="submit" name="save" id="save" style="width: 100px;" value="Save" onclick="invokeSave()" <c:if test="${sessionScope.SessionObject.cardCategoryCode=='J'}"> disabled="true"</c:if> >
                                                        <input type="button" name="saveNext" id="saveNext" style="width: 100px;" value="Save & Next" <c:if test="${sessionScope.SessionObject.cardCategoryCode=='P'}"> disabled="true"</c:if> onclick="invokeSave()" >
                                                        <input type="button" name="reset"  style="width: 100px;" onclick="invokeReset('${sessionScope.SessionObject.applicationId}','${sessionScope.SessionObject.cardCategory}')" value="Reset">
                                                        <input type="button" name="reset"  style="width: 100px;" onclick="invokeCancel()" value="Cancel">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>

                                    <!-- ----------------- tab 2   ---------------------------------------------- -->

                                    <div id="tabs-2" >
                                        <form ENCTYPE="multipart/form-data"  name="seconderyForm" method="Post" action="">
                                            <table cellpadding="0" cellspacing="10"  >

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>
                                                    <td><select name="identificationTypeS" TABINDEX=1>
                                                            <option value="">--Select Identification Type-- </option>

                                                            <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                                <c:if test="${sPersonalBean.identificationType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${sPersonalBean.identificationType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>        

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBeanS.identificationType} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Identification Number</td>
                                                    <td><input type="text" name="identificationNumberS"  maxlength="14" value="${sPersonalBean.identificationNumber}" TABINDEX=2></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBeanS.identificationNumber} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Title</td>
                                                    <td><select name="titleS" TABINDEX=3>
                                                            <option value="">--Select Title--</option>

                                                            <c:if test="${sPersonalBean.title =='Mr'}"> <option selected="true" value="Mr">Mr</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Mr'}"> <option value="Mr">Mr</option></c:if>
                                                            <c:if test="${sPersonalBean.title =='Mrs'}"> <option selected="true" value="Mrs">Mrs</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Mrs'}"> <option value="Mrs">Mrs</option></c:if>
                                                            <c:if test="${sPersonalBean.title =='Ms'}"> <option selected="true" value="Ms">Ms</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Ms'}"> <option value="Ms">Ms</option></c:if>
                                                            <c:if test="${sPersonalBean.title =='Dr'}"> <option selected="true" value="Dr">Dr</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Dr'}"> <option value="Dr">Dr</option></c:if>                                                
                                                            <c:if test="${sPersonalBean.title =='Prof'}"> <option selected="true" value="Prof">Prof</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Prof'}"> <option value="Prof">Prof</option></c:if>
                                                            <c:if test="${sPersonalBean.title =='Rev'}"> <option selected="true" value="Rev">Rev</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Rev'}"> <option value="Rev">Rev</option></c:if>
                                                            <c:if test="${sPersonalBean.title =='Hon'}"> <option selected="true" value="Hon">Hon</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Hon'}"> <option value="Hon">Hon</option></c:if>
                                                            <c:if test="${sPersonalBean.title =='Ven'}"> <option selected="true" value="Ven">Ven</option></c:if>
                                                            <c:if test="${sPersonalBean.title !='Ven'}"> <option value="Ven">Ven</option></c:if>

                                                        </select>
                                                    </td>

                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBeanS.title} </label></td> 
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td>
                                                        <input  name="birthdayS" maxlength="16" readonly value="${sPersonalBean.birthday}" key="birthday2" id="birthday2"  onchange="secondaryBD()" TABINDEX=9 />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#birthday2" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>
                                                    </td> 
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.birthday} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">First Name</td>
                                                    <td><input type="text" name="firstNameS"  maxlength="32" value="${sPersonalBean.firstName}" TABINDEX=4></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.firstName} </label></td>    

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;"> Address</td>    
                                                    <td><input type="text" name="address1S"  maxlength="20" value="${sPersonalBean.address1}" TABINDEX=10></td>    
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBeanS.address1} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Middle Name</td>    
                                                    <td><input type="text" name="middleNameS"  maxlength="20" value="${sPersonalBean.middleName}" TABINDEX=5></td>  
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.middleName} </label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="address2S"  maxlength="32" value="${sPersonalBean.address2}" TABINDEX=11></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.address2} </label></td>   
                                                </tr>

                                                <tr>                                              
                                                    <td style="width: 200px;">Last name</td>
                                                    <td><input type="text" name="lastNameS"  maxlength="32" value="${sPersonalBean.lastName}" TABINDEX=6></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.lastName} </label></td>  
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="address3S"  maxlength="32" value="${sPersonalBean.address3}" TABINDEX=12></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.address3} </label></td>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Name on the Card</td>
                                                    <td><input type="text" name="nameOncardS" style="background: #C4D2E0" maxlength="64" value="${sPersonalBean.nameOncard}" TABINDEX=7></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.nameOncard} </label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   
                                                    <td style="width: 200px;"> Area</td>    
                                                    <td><select name="cityS" TABINDEX=13>
                                                            <option value="">--Select Area--</option>

                                                            <c:forEach var="areaS" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${sPersonalBean.city==areaS.areaCode}">
                                                                    <option value="${areaS.areaCode}" selected="true" >${areaS.description}</option>
                                                                </c:if>
                                                                <c:if test="${sPersonalBean.city!=areaS.areaCode}">
                                                                    <option value="${areaS.areaCode}">${areaS.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.city} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Mothers Maiden Name</td>    
                                                    <td><input type="text" name="mothersMaidenNameS"  maxlength="64" value="${sPersonalBean.mothersMaidenName}" TABINDEX=8></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.mothersMaidenName} </label></td>

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"> Mobile Number</td>    
                                                    <td><input type="text" name="mobileNumberS"  maxlength="20" value="${sPersonalBean.mobileNumber}" TABINDEX=14></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.mobileNumber} </label></td>
                                                    </td>  
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 100px;"><label style="color: red"></label></td>

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td>Res. Phone No</td>
                                                    <td><input type="text" name="homeTelNumberS"  maxlength="20" value="${sPersonalBean.homeTelNumber}" TABINDEX=15></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.homeTelNumber} </label></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 100px;"><label style="color: red"></label></td>

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td>Office Phone No</td>
                                                    <td><input type="text" name="officeTelNumberS"  maxlength="20" value="${sPersonalBean.officeTelNumber}" TABINDEX=16></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.officeTelNumber} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Required Card Type</td>

                                                    <td  style="width: 200px;"><select  disabled="disabled" name="cardType" id="cardType" class="inputfield-mandatory"  onchange="lordCardProduct()" TABINDEX=17 >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cardTypeList}">
                                                                <c:if test="${personalBean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                    <td style="width: 200px;">Required Card Product</td>
                                                    <td style="width: 200px;"><select disabled="disabled" id="cardProduct" class="inputfield-mandatory" name="cardProduct" TABINDEX=18 >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="product" items="${sessionScope.SessionObject.cardProductMgtList}"> 
                                                                <c:if test="${personalBean.cardProduct==product.productCode}">
                                                                    <option value="${product.productCode}" selected="true" >${product.description}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.cardProduct!=product.productCode}">
                                                                    <option value="${product.productCode}">${product.description}</option>
                                                                </c:if>  
                                                            </c:forEach>  

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>



                                                <!--       ///////////////////////Account Details///////////////////////////////////         -->
                                                <br/>
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Account Details</font></td>
                                                </tr>
                                                <hr />

                                                <tr>
                                                    <td style="width: 200px;">Primary Account Number</td>
                                                    <td style="width: 200px;"><input type="text" name="accountNo" disabled="disabled"  maxlength="16" value="${personalBean.accountNo}"></td>
                                                    <td style="width: 100px;"></td>

                                                    <td style="width: 200px;">Secondary Account Number</td>
                                                    <td style="width: 200px;"><input type="text" name="accountNo3"  maxlength="12" value="${sPersonalBean.accountNo3}" TABINDEX=19></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBeanS.accountNo3}</label></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Primary Account Type</td>
                                                    <td style="width: 200px;"><select  disabled="disabled"   class="inputfield-mandatory" name="accNo1Type"  >

                                                            <c:forEach var="acc" items="${sessionScope.SessionObject.accountTypeList}"> 
                                                                <c:if test="${personalBean.accNo1Type==acc.key}">
                                                                    <option value="${acc.key}" selected="true" >${acc.value}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.accNo1Type!=acc.key}">
                                                                    <option value="${acc.key}">${acc.value}</option>
                                                                </c:if>  
                                                            </c:forEach>  

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                    <td style="width: 200px;">Secondary Account Type</td>
                                                    <td style="width: 200px;"><select class="inputfield-mandatory" name="accNo3Type" TABINDEX=20 >

                                                            <c:forEach var="acc" items="${sessionScope.SessionObject.accountTypeList}"> 
                                                                <c:if test="${sPersonalBean.accNo3Type==acc.key}">
                                                                    <option value="${acc.key}" selected="true" >${acc.value}</option>
                                                                </c:if>
                                                                <c:if test="${sPersonalBean.accNo3Type!=acc.key}">
                                                                    <option value="${acc.key}">${acc.value}</option>
                                                                </c:if>  
                                                            </c:forEach>  

                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"></td>

                                                <tr>
                                                    <td style="width: 200px;">Primary Currency</td>
                                                    <td style="width: 200px;"><select name="accNo1Currency" disabled="disabled"   class="inputfield-mandatory">

                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${personalBean.accNo1Currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.accNo1Currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                        <input type="hidden" hidden="hidden" name="accountNo"  maxlength="16" value="${personalBean.accountNo}">
                                                        <input type="hidden" hidden="hidden" name="accNo1Type"  maxlength="16" value="${personalBean.accNo1Type}">
                                                        <input type="hidden" hidden="hidden" name="accNo1Currency"  maxlength="16" value="${personalBean.accNo1Currency}">
                                                    </td>
                                                    <td></td>

                                                    <td style="width: 200px;">Secondary Currency</td>
                                                    <td style="width: 200px;"><select  name="accNo3Currency"   class="inputfield-mandatory" TABINDEX=21>

                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">

                                                                <c:if test="${sPersonalBean.accNo3Currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${sPersonalBean.accNo3Currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>


                                                <!--          ////////////////////End of Account Details/////////////////////////////////                                      -->
                                            </table>

                                            <table>
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Signature</font></td>
                                                </tr>

                                                <tr></tr>

                                                <tr>
                                                    <td>Choose the file To Upload &nbsp; &nbsp;</td>
                                                    <td><input  name="signatureS" id="signatureS" type="file" TABINDEX=22  /><input type="button"  value="Upload" name="next" style="width: 100px" onclick="uploadSecondrySigneture('${sPersonalBean.applicationId}')"> </ input></td> 
                                                    <td><b><font color="#FF0000"><label id="sSigErr"> ${invalidMsgBean.signatureFileName}</label> </font></b> </td>
                                                </tr>

                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>National Identity Card</font></td>
                                                </tr>

                                                <tr></tr>

                                                <tr>
                                                    <td>Choose the file To Upload &nbsp; &nbsp;</td>
                                                    <td><input  name="nicS" id="nicS" type="file" TABINDEX=23  /><input type="button"  value="Upload" name="next" style="width: 100px" onclick="uploadSecondryNic('${sPersonalBean.applicationId}')"> </ input></td> 
                                                    <td><b><font color="#FF0000"><label id="sNicErr"> ${invalidMsgBean.nicFileName}</label></font></b> </td>
                                                </tr>

                                            </table>

                                            <br />

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="submit" name="add"  style="width: 100px;" onclick="invokeSaveSecondary()" value="Save" >
                                                        <input type="button" name="reset"  style="width: 100px;" onclick="invokeResetSecondary(this.form)" value="Reset">
                                                        <input type="button" value="Back " name="backtab" style="width: 100px" class="previoustab">
                                                        <input type="button" name="reset"  style="width: 100px;" onclick="invokeCancel()" value="Cancel">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </div>   
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
