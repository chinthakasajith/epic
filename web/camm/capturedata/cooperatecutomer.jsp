<%-- 
    Document   : cooperatecutomer
    Created on : May 10, 2013, 2:05:40 PM
    Author     : jeevan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            $(function() {
                
                $( "#tabs2" ).tabs();
               
                $( ".selector" ).tabs({ selected: ${selectedtab} });
                $(".nexttab").click(function() {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected + 1);
                });
                $(".previoustab").click(function() {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected - 1);
                });
                
                $("#tabs2").tabs("option", "disabled",[${loadTabIndex}]);
                
                invorkeSumOfExpenses();
            });
           
        </script>
        <script language="javascript">
            

            function invokeCancel(){
                window.location = "${pageContext.request.contextPath}/SearchCorporateCardApplicationServlet"; 
           
            }
            function invokeReset(applicationId){
                window.location = "${pageContext.request.contextPath}/LoadCorporateCustomerServlet?appliactionid="+applicationId; 
           
            }
           
            function invokeRemoveIncome(category){
               
                window.location = "${pageContext.request.contextPath}/RemoveIncomeDataToSessionServlet?category="+category;
            }
            function invokeRemoveBankData(bank,acc){
               
                window.location = "${pageContext.request.contextPath}/RemoveBankDataFromSessionServlet?bank="+bank+"&account="+acc;
            }
            function invokeRemoveDocumentData(filename){
               
                window.location = "${pageContext.request.contextPath}/RemoveDocumentDataFromSessionServlet?filename="+filename;
            }
            function setResAddress(){
                
                var isCheck=$('#checkRes:checked').val();
                 
                if(isCheck=='on'){ 
                     
                    $("#resAddress1").val($("#address1").val());                       
                    $("#resAddress2").val($("#address2").val());
                    $("#resAddress3").val($("#address3").val());
                    $('#ResCity option').each(function(){
                        if ($(this).val() == $("#city option:selected").val()) {
                            $(this).attr("selected",true);
                        }
                    });
                    
                    $("#resDistrict").val($("#district").val());
                    $("#resProvince").val($("#province").val());
                }else{
                     
                    $("#resAddress1").val("");                       
                    $("#resAddress2").val("");   
                    $("#resAddress3").val("");
                    $('#ResCity option').each(function(){
                        if ($(this).val() == "") {
                            $(this).attr("selected",true);
                        }
                    });
                    $("#resDistrict").val("");   
                    $("#resProvince").val("");   
                }
                
                
            }
            
            function setBillAddress(){
                
                var isCheck=$('#checkBill:checked').val();
                 
                if(isCheck=='on'){
                     
                    $("#billAddress1").val($("#address1").val());                       
                    $("#billAddress2").val($("#address2").val());
                    $("#billAddress3").val($("#address3").val());
                    $('#billCity option').each(function(){
                        if ($(this).val() == $("#city option:selected").val()) {
                            $(this).attr("selected",true);
                        }
                    });
                    
                    $("#billDistrict").val($("#district").val());
                    $("#billProvince").val($("#province").val());
                }else{
                     
                    $("#billAddress1").val("");                       
                    $("#billAddress2").val("");   
                    $("#billAddress3").val(""); 
                    $('#billCity option').each(function(){
                        if ($(this).val() == "") {
                            $(this).attr("selected",true);
                        }
                    });
                    $("#billDistrict").val("");   
                    $("#billProvince").val("");   
                }
                 
            }
            
            function setDistrictAndProvince(value){
                
                
                if(value=='res'){
             
                               
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                    { area:$('#ResCity option:selected').val()
                                    
                    },
                    function(data) {
                        if(data!=''){
                                        
                            var array=data.split(',', 2)
                        
                            $("#resDistrict").val(array[0]);                       
                            $("#resProvince").val(array[1]); 
                                           
                        }
                                      
                                        
                    });
                }
                if(value=='bill'){
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                    { area:$('#billCity option:selected').val()
                                    
                    },
                    function(data) {
                        if(data!=''){
                                        
                            var array=data.split(',', 2)
                        
                            $("#billDistrict").val(array[0]);                       
                            $("#billProvince").val(array[1]);                       
                                             
                        }
                                      
                                        
                    });
                }
                if(value=='perm'){
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                    { area:$('#city option:selected').val()
                                    
                    },
                    function(data) {
                        if(data!=''){
                                        
                            var array=data.split(',', 2)
                        
                           
                            $("#district").val(array[0]);                       
                            $("#province").val(array[1]);                       
                                             
                        }
                                      
                                        
                    });
                }   
                 
            }
           
           
            function invorkSetDocumentTypes(category){
           
                window.location = "${pageContext.request.contextPath}/SetDocumentTypesOnchangeServlet?category="+category;
           
            }
           
            function invorkeSumOfExpenses(){
                var rent= $("#rent").val();
                if(rent==""){
                    rent="0";
                }
              
                var loanInstallment= $("#loanInstallment").val();
                if(loanInstallment==""){
                    loanInstallment="0";
                }
                var leaseRental= $("#leaseRental").val();
                if(leaseRental==""){
                    leaseRental="0";
                }
                var creditCardBill= $("#creditCardBill").val();
                if(creditCardBill==""){
                    creditCardBill="0";
                }
                var otherBorrowing= $("#otherBorrowing").val();
                if(otherBorrowing==""){
                    otherBorrowing="0";
                }
                var insurance= $("#insurance").val();
                if(insurance==""){
                    insurance="0";
                }
                var houseHolder= $("#houseHolder").val();
                if(houseHolder==""){
                    houseHolder="0";
                }
                var otherExpense= $("#otherExpense").val();
                if(otherExpense==""){
                    otherExpense="0";
                }
                var sum = parseInt(rent) + parseInt(loanInstallment)+ parseInt(leaseRental)+ parseInt(creditCardBill)+ parseInt(otherBorrowing)+ parseInt(insurance)+ parseInt(houseHolder)+ parseInt(otherExpense);
                $("#sumOfExpenses").val(sum);
              
               
            }
           
           
            function changeProducts(selected)
            {
               
                $.post("${pageContext.request.contextPath}/SetProductDropDownServlet",
                { cardType:$('#cardType option:selected').val()
                                    
                },
                function(data) {
                    if(data!=''){
                                        
                        var array=data.split('|', 2)
                        var codes=array[0].split(',')
                        var descriptions=array[1].split(',')
                        
            
                            
                        $('#cardProduct option').each(function(){
                            $(this).remove()
                        });
                         
                        $('#cardProduct').append(
                        $('<option></option>').val('').html('--SELECT--')
                    );
                        for(var x=0;x < codes.length;x++){
                            if(codes[x]==selected){
                                $('#cardProduct').append(
                                $('<option selected=""></option>').val(codes[x]).html(descriptions[x])
                            ); 
                            }else{
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
                { bankcode:$('#bankName option:selected').val()
                                    
                },
                function(data) {
                    if(data!=''){
                                        
                        var array=data.split('|', 2)
                        var codes=array[0].split(',')
                        var descriptions=array[1].split(',')
                        
            
                            
                        $('#brachName option').each(function(){
                            $(this).remove()
                        });
                         
                        $('#brachName').append(
                        $('<option></option>').val('').html('--SELECT--')
                    );
                        for(var x=0;x < codes.length;x++){
                           
                            $('#brachName').append(
                            $('<option ></option>').val(codes[x]).html(descriptions[x])
                        ); 
                            
                            
                        }
                          
                        //                       
                                           
                    }else{
                        $('#brachName option').each(function(){
                            $(this).remove()
                        });
                        
                        $('#brachName').append(
                        $('<option></option>').val('').html('--SELECT--')
                    );
                    }
                                      
                                        
                });
                
            }
            
            function changeBranches()
            {
                
               
                $.post("${pageContext.request.contextPath}/SetBankBranchDropDownServlet",
                { bankcode:$('#bankName option:selected').val()
                                    
                },
                function(data) {
                    if(data!=''){
                                        
                        var array=data.split('|', 2)
                        var codes=array[0].split(',')
                        var descriptions=array[1].split(',')
                        
            
                            
                        $('#brachName option').each(function(){
                            $(this).remove()
                        });
                         
                        $('#brachName').append(
                        $('<option></option>').val('').html('--SELECT--')
                    );
                        for(var x=0;x < codes.length;x++){
                           
                            $('#brachName').append(
                            $('<option ></option>').val(codes[x]).html(descriptions[x])
                        ); 
                            
                            
                        }
                          
                        //                       
                                           
                    }else{
                        $('#brachName option').each(function(){
                            $(this).remove()
                        });
                        
                        $('#brachName').append(
                        $('<option></option>').val('').html('--SELECT--')
                    );
                    }
                                      
                                        
                });
                
            }
            
            function changeDocumentTypeList() {
                $('#dtype').empty();
                var verificationCategory = $("#vcategory").val();
                
                $.getJSON("${pageContext.servletContext.contextPath}/ChangeDocTypeServlet", 
                {
                    verificationCategory1 : verificationCategory
                },
                function(jsonobject) {
                    $("#dtype").append("<option value=''>-----SELECT-----</option>");
                    $.each(jsonobject.combo1, function(code, description) {
                        $("#dtype").append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
                }
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

                                <table class="tit"> <tr> <td   class="center">  CORPORATE CUSTOMER </td> </tr><tr> <td>&nbsp;</td> </tr></table>
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
                                        <li><a href="#tabs-1">Company Particulars</a></li>
                                        <li><a href="#tabs-2">Bank Details</a></li>
                                        <li><a href="#tabs-3">Document</a></li>

                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form name="customerpersonalinfoform" method="POST" action="<%=request.getContextPath()%>/AddCorporateCustomerServlet">

                                            <br /><font style="color: #999"> Personal Information</font>   
                                            <table cellpadding="0" cellspacing="10"  >


                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Type
                                                    </td>

                                                    <td>&nbsp;&nbsp; <select name="idType" disabled="true">
                                                            <option value="">--Select Nationality-- </option>

                                                            <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeListCoCustomer}">
                                                                <c:if test="${personalBean.identificationCode==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${personalBean.identificationCode != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>        


                                                        </select>
                                                    </td>
                                                    <td style="width: 50px;" > <label ></label></td>
                                                    <td>
                                                        <input type="hidden" value="${personalBean.identificationCode}" name="hidIdType">
                                                    </td>


                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Identification Number
                                                    </td>

                                                    <td>&nbsp;&nbsp; <label name="identificationNumber">${personalBean.identificationNumber}</label></td>

                                                    <td style="width: 50px;" > <label style="color: red">${invalidMsgBean.nic} </label></td>

                                                    <td>
                                                        <input type="hidden" value="${personalBean.identificationNumber}" name="hidIdNumber">
                                                    </td>


                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Title</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="title">
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
                                                    <td> <label style="color: red">${invalidMsgBean.title} </label></td>
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;"> NIC</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="nic"  maxlength="20" value="${personalBean.nic}"></td>    
                                                    <td> <label style="color: red">${invalidMsgBean.nic} </label></td>
                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">First Name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="firstname"  maxlength="32" value="${personalBean.firstName}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.firstName} </label></td>   

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;"> Address1</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="address1"  maxlength="20" value="${personalBean.address1}"></td>    
                                                    <td> <label style="color: red">${invalidMsgBean.address1} </label></td>  
                                                    <td style="width: 50px;"> 
                                                    </td> 

                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;"> Middle Name</td>    
                                                    <td><font style="color: red;">&nbsp;&nbsp;</font>&nbsp;<input type="text" name="othername"  maxlength="32" value="${personalBean.middleName}"></td>  
                                                    <td> <label style="color: red">${invalidMsgBean.middleName} </label></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    <td style="width: 200px;"> Address2</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="address2"  maxlength="20" value="${personalBean.address2}"></td>    
                                                    <td> <label style="color: red">${invalidMsgBean.address2} </label></td>  

                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Last name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="lastName"  maxlength="32" value="${personalBean.lastName}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.lastName} </label></td>    



                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->

                                                    <td style="width: 200px;"> Address3</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="address3"  maxlength="20" value="${personalBean.address3}"></td>    
                                                    <td> <label style="color: red">${invalidMsgBean.address3} </label></td>




                                                </tr>
                                                <tr>

                                                    <td style="width: 200px;">Name as appeared on the card</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="nameoncard"  maxlength="64" value="${personalBean.nameoncard}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.nameoncard} </label></td>

                                                    <td style="width: 200px;">Basic Annual Salary</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="basicannualsalary"  maxlength="32" value="${personalBean.basicannualsalary}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.basicannualsalary} </label></td>


                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   
                                                    <!--*******************************              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   


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

                                                    <td> <label style="color: red">${invalidMsgBean.gender} </label></td>
                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->

                                                    <td style="width: 200px;"> Nationality</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="nationality">
                                                            <option value="">--Select Nationality-- </option>
                                                            <c:if test="${personalBean.nationality != null}">
                                                                <c:forEach var="nationality" items="${sessionScope.SessionObject.nationalityCoCustomerList}">
                                                                    <c:if test="${personalBean.nationality == nationality}">
                                                                        <option value="${nationality}" selected="true">${nationality}</option>   
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 

                                                            <c:forEach var="nationality" items="${sessionScope.SessionObject.nationalityCoCustomerList}">
                                                                <c:if test="${personalBean.nationality != nationality}">
                                                                    <option value="${nationality}">${nationality}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.nationality}</label></td>


                                                </tr>

                                                <tr>

                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input  name="birthday" maxlength="16" readonly value="${personalBean.birthday}" key="birthday" id="birthday"  />

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
                                                    <td> <label style="color: red">${invalidMsgBean.birthday} </label></td>

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          

                                                    <td style="width: 200px;"> Blood Group</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="bloodGroup">
                                                            <option value="">--Select Blood Group--</option>
                                                            <c:if test="${personalBean.bloodGroup =='O+'}"> <option selected="true" value="O+">Group O+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='O+'}"> <option  value="O+">Group O+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='O-'}"> <option selected="true" value="O-">Group O-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='O-'}"> <option  value="O-">Group O-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='A+'}"> <option selected="true" value="A+">Group A+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='A+'}"> <option  value="A+">Group A+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='A-'}"> <option selected="true" value="A-">Group A-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='A-'}"> <option  value="A-">Group A-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='B+'}"> <option selected="true" value="B+">Group B+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='B+'}"> <option  value="B+">Group B+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='B-'}"> <option selected="true" value="B-">Group B-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='B-'}"> <option  value="B-">Group B-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='AB+'}"> <option selected="true" value="AB+">Group AB+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='AB+'}"> <option  value="AB+">Group AB+</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup =='AB-'}"> <option selected="true" value="AB-">Group AB-</option></c:if>
                                                            <c:if test="${personalBean.bloodGroup !='AB-'}"> <option  value="AB-">Group AB-</option></c:if>



                                                        </select>
                                                    </td>  
                                                    <td> <label style="color: red">${invalidMsgBean.bloodGroup} </label></td>

                                                </tr> 

                                                <tr>
                                                    <td style="width: 200px;">Telephone </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="telphone"  maxlength="10" value="${personalBean.telphone}"></td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.telphone} </label></td>


                                                    <td style="width: 200px;">Educational Level</td>
                                                    <td><font style="color: red;">*</font>
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
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.acedemicQualifications} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Employment Duration (Yrs)</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="empDuration"  maxlength="20" value="${personalBean.empDuration}"></td>    
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.empDuration} </label></td>


                                                    <td style="width: 200px;">Area</td>
                                                    <td><font style="color: red;">*</font><select name="city" id="city" onchange="setDistrictAndProvince('perm')" width="163px;">
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.permentCity != null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaListCoCustomer}">
                                                                    <c:if test="${personalBean.permentCity == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaListCoCustomer}">
                                                                <c:if test="${personalBean.permentCity != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td> <label style="color: red">${invalidMsgBean.permentCity} </label></td>	



                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Religion</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="religion"  maxlength="20" value="${personalBean.religion}"></td>    
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.religion} </label></td>


                                                    <td style="width: 200px;">District</td>
                                                    <td>
                                                        &nbsp;&nbsp;<input type="text" name="district" id="district" readonly="true" maxlength="32" value="${personalBean.district}"> 
                                                    </td>
                                                    <td style="width: 50px;">

                                                    </td> 

                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Mother's Madden Name</td>    
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="mothersMadden"  maxlength="20" value="${personalBean.mothersMadden}"></td>											
                                                    <td> <label style="color: red">${invalidMsgBean.mothersMadden} </label></td>  


                                                    <td style="width: 200px;">Province</td>
                                                    <td>
                                                        &nbsp;&nbsp;<input type="text" name="province" id="province" readonly="true" maxlength="32" value="${personalBean.province}">
                                                    </td> 


                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Job Title</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="jobtitle"  maxlength="32" value="${personalBean.jobtitle}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.jobtitle} </label></td>

                                                    <td style="width: 200px;">Fixed Allowances</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="fixedallowance"  maxlength="32" value="${personalBean.fixedallowance}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.fixedallowance} </label></td> 


                                                </tr>

                                                <tr>




                                                </tr>
                                                <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          

                                            </table>

                                            <table cellpadding="0" cellspacing="10">

                                            </table>
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td colspan="2">
                                                        <input type="submit" name="savenext" value="Save & Next"  style="width: 100px;"/>&nbsp;
                                                        <input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                        <input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>
                                                    <td>
                                                        <input type="hidden" value="${personalBean.identificationType}" name="hidIdType">
                                                        <input type="hidden" value="${personalBean.identificationNumber}" name="hidIdNumber">
                                                    </td>

                                                </tr>

                                            </table>



                                        </form>

                                    </div>



                                    <!--    tab 2  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                           -->
                                    <div id="tabs-2">
                                        <form name="customeremploymentform" method="POST" action="<%=request.getContextPath()%>/AddCorporateCustomerBankDetailsServlet">
                                            <table cellpadding="0" cellspacing="10"  >
                                                <tr>
                                                    <td style="width: 200px;">

                                                    </td>
                                                </tr>  

                                                <tr>
                                                    <td style="width: 200px;">Bank Name</td>
                                                    <td><font style="color: red;">*</font>
                                                        <select name="bankName" id="bankName" onchange="changeBranches()">
                                                            <option value="">--Select Bank Name--</option>
                                                            <c:forEach var="bankList" items="${sessionScope.SessionObject.sessionBankDetailCoCustList}">

                                                                <option value="${bankList.bankCode}">${bankList.bankName}</option>

                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td> <label style="color: red">${invalidMsgBean.bankName} </label></td>

                                                </tr> 
                                                <tr>
                                                    <td style="width: 200px;">Branch Name</td>
                                                    <td><font style="color: red;">*</font>
                                                        <select name="branchName" id="brachName">
                                                            <option value="">--Select Branch Name--</option>
                                                        </select>
                                                    </td>
                                                    <td> <label style="color: red">${invalidMsgBean.branchName} </label></td>

                                                </tr> 


                                                <tr>
                                                    <td style="width: 200px;">Account Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="accountTpye"  maxlength="32" value="${personalBean.accountTpye}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.accountTpye} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Account Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="accountNo"  maxlength="32" value="${personalBean.accountNo}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.accountNo} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Account Since Years</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="accountSinceYears"  maxlength="32" value="${personalBean.accountSinceYears}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.accountSinceYears} </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Account Since Months</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="accountSinceMonths"  maxlength="32" value="${personalBean.accountSinceMonths}"></td>
                                                    <td> <label style="color: red">${invalidMsgBean.accountSinceMonths} </label></td>
                                                </tr>

                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td colspan="2">
                                                        <input type="submit" name="savenext" value="Save"  style="width: 100px;"/>&nbsp;
                                                        <input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                        <input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/></td>


                                                </tr>

                                            </table>  
                                        </form>
                                    </div>                                       
                                    <!--   tab 3 ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                  -->

                                    <div id="tabs-3">
                                        <form name="uploadform" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/UploadCorporateDocumentServlet" >
                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width:200px;">Verification Category</td>
                                                    <td>   
                                                        <select id="vcategory" name="vCategory" onchange="changeDocumentTypeList()">
                                                            <option value="">Select Category</option>
                                                            <c:forEach var="list" items="${sessionScope.SessionObject.verificatioCatCoList}">
                                                                <c:if test="${category == list.categoryCode}">
                                                                    <option value="${list.categoryCode}" selected="true">${list.categoryName}</option>
                                                                </c:if>
                                                                <c:if test="${category != list.categoryCode}">
                                                                    <option value="${list.categoryCode}">${list.categoryName}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${verificationCategory}</label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Document Type </td>
                                                    <td><select id="dtype" name="dType">
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
                                    </div>
                                    <!--   tab 4 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->

                                    <!--   tab 5 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                     -->


                                    <!--   tab 6   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                                         -->




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

