<%-- 
    Document   : establishcarddatacapture
    Created on : May 10, 2013, 10:32:01 AM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
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
                
            });
           
        </script>

        <script>
            
            function primaryBD()
            {
                newdate = primaryForm.elements["regDate"].value;
                today = new Date();
                yesterday = new Date(today.getTime()-(24*60*60*1000));
               
                if(Date.parse(newdate)>Date.parse(yesterday)){
                    alert("Enter Valid Date");
                    primaryForm.elements["regDate"].value = "";
                }
                     
            }
            function commenceDate()
            {
                newdate = primaryForm.elements["commenceDate"].value;
                today = new Date();
                yesterday = new Date(today.getTime()-(24*60*60*1000));
               
                if(Date.parse(newdate)>Date.parse(yesterday)){
                    alert("Enter Valid Date");
                    primaryForm.elements["commenceDate"].value = "";
                }
                     
            }
                    
            
            function invokeSave(){
                           
                document.companyForm.action="${pageContext.request.contextPath}/AddFirstEstablishCardFormServlet";
                document.companyForm.submit();
            }
         
            
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/SearchCorporateCardApplicationServlet";
            }
            
            function invokeReset(applicationid,cardcategory){
                
                window.location = "${pageContext.request.contextPath}/LoadEstablishCardFormServlet?applicationid="+applicationid+"&cardcategory="+cardcategory;
            }
            
            function invokeCancel2(){
                window.location = "${pageContext.request.contextPath}/SearchUserAssignDataServlet"; 
           
            }
            function invokeReset2(applicationId){
                window.location = "${pageContext.request.contextPath}/LoadDefaultDataServlet?appliactionid="+applicationId; 
           
            }
                
                
            
            function setDistrictAndProvince(value){               
                
                if(value=='comArea'){
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                    { area:$('#city1 option:selected').val()
                                    
                    },
                    function(data) {
                        if(data!=''){
                                        
                            var array=data.split(',', 2)
                        
                           
                            $("#district").val(array[0]);                       
                            $("#province").val(array[1]);                       
                                             
                        }
                                      
                                        
                    });
                }   
                
                if(value=='AudArea'){
                    $.post("${pageContext.request.contextPath}/SetDistrictAndProvinceServlet",
                    { area:$('#city2 option:selected').val()
                                    
                    },
                    function(data) {
                        if(data!=''){
                                        
                            var array=data.split(',', 2)
                        
                           
                            $("#district2").val(array[0]);                       
                            $("#province2").val(array[1]);                       
                                             
                        }
                                      
                                        
                    });
                } 
                 
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
           
            function invokeRemoveBankData(bank,acc){
               
                window.location = "${pageContext.request.contextPath}/RemoveEstBankFromSessionServlet?bank="+bank+"&account="+acc;
            }
            
            function invokeRemoveManagerData(fname,lname,phone){
               
                window.location = "${pageContext.request.contextPath}/RemoveManagerFromSessionServlet?fname="+fname+"&lname="+lname+"&phone="+phone;
            }

           
        </script>
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

                                <table>
                                    <tr >
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
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

                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Company </a></li>
                                        <li><a href="#tabs-2">Management</a></li>
                                        <li><a href="#tabs-3">Bank </a></li>
                                    <!--  <li><a href="#tabs-4">Assets</a></li>-->
                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >
                                        <form  name="companyForm" method="Post" action="<%=request.getContextPath()%>/AddFirstEstablishCardFormServlet">
                                            <table cellpadding="0" cellspacing="10"  >
                                                 
                                                <!------------------------------------------- Company particulars --->
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br />Company particulars</font></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Full Company Name</td>
                                                    <td><input type="text" name="companyName"  maxlength="128" value="${companyBean.companyName}" TABINDEX=1 /></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.companyName} </label></td>    

                                                </tr>
                                                
                                               <tr>
                                                    <td style="width: 200px;"> Address</td>    
                                                    <td><input type="text" name="address1"  maxlength="64" value="${companyBean.address1}" TABINDEX=2></td>    
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.address1} </label></td>
                                                    
                                                    <td style="width: 200px;"> Area</td>    
                                                    <td><select name="city1" TABINDEX=5 id="city1" onchange="setDistrictAndProvince('comArea')">
                                                            <option value="">--Select Area--</option>

                                                            <c:forEach var="area" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${companyBean.area==area.areaCode}">
                                                                    <option value="${area.areaCode}" selected="true" >${area.description}</option>
                                                                </c:if>
                                                                <c:if test="${companyBean.area !=area.areaCode}">
                                                                    <option value="${area.areaCode}">${area.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.area}</label></td>    

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="address2"  maxlength="64" value="${companyBean.address2}" TABINDEX=3></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.address2} </label></td> 
                                                    
                                                    <td style="width: 200px;">District</td>
                                                    <td>
                                                        <input type="text" name="district" id="district" readonly="true" maxlength="64" value="${companyBean.district}"> 
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;"> </td>    
                                                    <td><input type="text" name="address3"  maxlength="64" value="${companyBean.address3}" TABINDEX=4 >
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.address3} </label></td>
                                                    
                                                    <td style="width: 200px;">Province</td>
                                                    <td>
                                                        <input type="text" name="province" id="province" readonly="true" maxlength="64" value="${companyBean.province}">
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Office Phone No 1</td>
                                                    <td><input type="text" name="officeNumber1"  maxlength="20" value="${companyBean.officePhone1}" TABINDEX=6></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.officePhone1}</label></td>
                                                    
                                                     <td style="width: 200px;">Office Phone No 2</td>
                                                    <td><input type="text" name="officeNumber2"  maxlength="20" value="${companyBean.officePhone2}" TABINDEX=7></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.officePhone2}</label></td>
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Office Fax No</td>
                                                    <td><input type="text" name="officeFax"  maxlength="20" value="${companyBean.officeFax}" TABINDEX=8></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.officeFax}</label></td>
                                                    
                                                     <td style="width: 200px;">Office E-mail</td>
                                                    <td><input type="text" name="officeEmail"  maxlength="32" value="${companyBean.officeEmail}" TABINDEX=9></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.officeEmail}</label></td>
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Contact Person</td>
                                                    <td><input type="text" name="contactPerson"  maxlength="64" value="${companyBean.contactPerson}" TABINDEX=10></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.contactPerson}</label></td>
                                                    
                                                     <td style="width: 200px;">Contact Person Position</td>
                                                     <td>
                                                         <select name="conPerPosition" tabindex="11">
                                                             <option value="" >--SELECT--</option>
                                                             <c:if test="${companyBean.contactPerPosition != null}">
                                                                 <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                     <c:if test="${companyBean.contactPerPosition == occupationList.occupationTypeCode}">
                                                                         <option value="${occupationList.occupationTypeCode}" selected="true">${occupationList.description}</option>
                                                                     </c:if>
                                                                 </c:forEach>
                                                                                
                                                             </c:if> 
                                                             <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                 <c:if test="${companyBean.contactPerPosition != occupationList.occupationTypeCode}">
                                                                     <option value="${occupationList.occupationTypeCode}">${occupationList.description}</option>
                                                                 </c:if>
                                                             </c:forEach> 
                                                                            
                                                         </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.contactPerPosition}</label></td>
                                                    
                                                </tr>
                                                 <!------------------------------------------- Company Details --->
                                                <tr>
                                                    <td colspan="6" ><br/><hr /><font style="color: #999"> <br/>Company Details</font></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Company Registration No.</td>
                                                    <td><input type="text" name="regNo"  maxlength="32" value="${companyBean.brcNumber}" TABINDEX=12></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.brcNumber}</label></td>
                                                    
                                                     <td style="width: 200px;">Date of Registration</td>
                                                    <td>
                                                        <input  name="regDate" maxlength="32" readonly value="${companyBean.registerDate}" key="regDate" id="regDate" onchange="primaryBD()" TABINDEX=13 />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#regDate" ).datepicker({
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
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.registerDate} </label></td> 
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Place of Registration</td>
                                                    <td><input type="text" name="regPlace"  maxlength="32" value="${companyBean.registerPlace}" TABINDEX=14></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.registerPlace}</label></td>
                                                    
                                                     <td style="width: 200px;">Date of Business Commencement</td>
                                                   <td>
                                                        <input  name="commenceDate" maxlength="32" readonly value="${companyBean.commenceDate}" key="commenceDate" id="commenceDate" onchange="commenceDate()" TABINDEX=15 />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#commenceDate" ).datepicker({
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
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.commenceDate} </label></td> 
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Type of Company</td>
                                                    <td>
                                                        <select name="compType" tabindex="16">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${companyBean.typeOfCompany != null}">                                                                
                                                                <c:forEach var="empTypeList" items="${sessionScope.SessionObject.empTypeList}">
                                                                    <c:if test="${companyBean.typeOfCompany == empTypeList.typeCode}">
                                                                        <option value="${empTypeList.typeCode}" selected="true">${empTypeList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                                    
                                                            </c:if> 
                                                            <c:forEach var="empTypeList" items="${sessionScope.SessionObject.empTypeList}">
                                                                <c:if test="${companyBean.typeOfCompany != empTypeList.typeCode}">
                                                                    <option value="${empTypeList.typeCode}">${empTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                                
                                                                
                                                        </select>
                                                            
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.typeOfCompany}</label></td>
                                                    
                                                    <td style="width: 200px;">Nature of Business</td>
                                                    <td>
                                                        <select name="businessNature" tabindex="18">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${companyBean.businessNature != null}">
                                                                <c:forEach var="natureList" items="${sessionScope.SessionObject.natureList}">
                                                                    <c:if test="${companyBean.businessNature == natureList.natureCode}">
                                                                        <option value="${natureList.natureCode}" selected="true">${natureList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                                     
                                                            </c:if> 
                                                            <c:forEach var="natureList" items="${sessionScope.SessionObject.natureList}">
                                                                <c:if test="${companyBean.businessNature != natureList.natureCode}">
                                                                    <option value="${natureList.natureCode}">${natureList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                                 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.businessNature}</label></td>
                                                                                                      
                                                </tr>
                                                        
                                                <tr>
                                                    <td style="width: 200px;"><label>&nbsp; &nbsp;(Other,Please Specify) </lable></td>
                                                        
                                                    <td>
                                                        <input type="text" name="othercompType"  maxlength="32"  value="${companyBean.otherTypeOfCompany}" tabindex="17"/>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.otherTypeOfCompany}</label></td>
                                                    
                                                    <td style="width: 200px;"><label>&nbsp; &nbsp;(Other,Please Specify) </lable></td>                                                         
                                                    <td>                   
                                                        <input type="text" name="otherBusinessNature"  maxlength="32" value="${companyBean.otherBusinessNature}" tabindex="19"/>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.otherBusinessNature}</label></td>
                                                        
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Number of Employees</td>
                                                    <td><input type="text" name="numberOfEmployees"  maxlength="6" value="${companyBean.numberOfEmployees}" TABINDEX=20></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.numberOfEmployees}</label></td>
                                                    
                                                     <td style="width: 200px;">Capital Invested</td>
                                                    <td><input type="text" name="capitalInvested"  maxlength="10" value="${companyBean.capitalInvested}" TABINDEX=21></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.capitalInvested}</label></td>
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Annual Turnover </td>
                                                    <td><input type="text" name="anualTurnover"  maxlength="32" value="${companyBean.annualTurnover}" TABINDEX=22></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.annualTurnover}</label></td>
                                                    
                                                     <td style="width: 200px;">Annual Turnover Year</td>
                                                    <td><input type="text" name="anualTurnoverYear"  maxlength="10" value="${companyBean.annualTurnoverYear}" TABINDEX=23></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.annualTurnoverYear}</label></td>
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Income Category</td>
                                                    <td><input type="text" name="incomeCategory"  maxlength="20" value="" TABINDEX=24></td>
                                                    <td style="width: 100px;"><label style="color: red">${sss}</label></td>
                                                    
                                                     <td style="width: 200px;">Incomes</td>
                                                    <td><input type="text" name="incomes"  maxlength="32" value="" TABINDEX=25></td>
                                                    <td style="width: 100px;"><label style="color: red">${sss}</label></td>
                                                    
                                                </tr> 
                                                
                                                  <!-------------------------------------------Company Auditor Details --->
                                                
                                                <tr>
                                                    <td colspan="6" ><br/><hr /><font style="color: #999"> <br/>Company Auditor Details</font></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Auditor Name</td>
                                                    <td><input type="text" name="auditName"  maxlength="64" value="${companyBean.auditName}" TABINDEX=26></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditName}</label></td>
                                                    
                                                    <td style="width: 200px;">Address</td>
                                                    <td><input type="text" name="auditAddress1"  maxlength="32" value="${companyBean.auditAddress1}" TABINDEX=28></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditAddress1}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Area</td>
                                                    <td><select name="audCity" id="city2" onchange="setDistrictAndProvince('AudArea')" tabindex="27" >
                                                            <option value="">--Select Area--</option>
                                                            <c:if test="${personalBean.permentCity != null}">
                                                                <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                    <c:if test="${companyBean.auditArea == areaList.areaCode}">
                                                                        <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                <c:if test="${companyBean.auditArea != areaList.areaCode}">
                                                                    <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                </c:if>
                                                            </c:forEach> 
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditArea}</label></td>
                                                    
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="auditAddress2"  maxlength="32" value="${companyBean.auditAddress2}" TABINDEX=29></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditAddress2}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">District</td>
                                                    <td>
                                                        <input type="text" name="audDistrict" id="district2" readonly="true" maxlength="32" value="${companyBean.auditDistrict}"> 
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                    
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="text" name="auditAddress3"  maxlength="32" value="${companyBean.auditAddress3}" TABINDEX=30></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditAddress3}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Province</td>
                                                    <td>
                                                        <input type="text" name="audProvince" id="province2" readonly="true" maxlength="32" value="${companyBean.auditProvince}"> 
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                    
                                                     <td style="width: 200px;">Office Phone Number 1</td>
                                                    <td><input type="text" name="audPhoNumber1"  maxlength="20" value="${companyBean.auditOficePhone1}" TABINDEX=33></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditOficePhone1}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Contact Person</td>
                                                    <td><input type="text" name="audContactPerson"  maxlength="32" value="${companyBean.auditContactPerson}" TABINDEX=31></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditContactPerson}</label></td>
                                                    
                                                     <td style="width: 200px;">Office Phone Number 2</td>
                                                    <td><input type="text" name="audPhoNumber2"  maxlength="20" value="${companyBean.auditOficePhone2}" TABINDEX=34></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditOficePhone2}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Contact Person Position</td>
                                                    <td>
                                                        <select name="audConPerPosition" tabindex="32">
                                                            <option value="" >--SELECT--</option>
                                                             <c:if test="${companyBean.auditContactPerPosition != null}">
                                                                 <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                     <c:if test="${companyBean.auditContactPerPosition == occupationList.occupationTypeCode}">
                                                                         <option value="${occupationList.occupationTypeCode}" selected="true">${occupationList.description}</option>
                                                                     </c:if>
                                                                 </c:forEach>
                                                                                
                                                             </c:if> 
                                                             <c:forEach var="occupationList" items="${sessionScope.SessionObject.occupationList}">
                                                                 <c:if test="${companyBean.auditContactPerPosition != occupationList.occupationTypeCode}">
                                                                     <option value="${occupationList.occupationTypeCode}">${occupationList.description}</option>
                                                                 </c:if>
                                                             </c:forEach> 
                                                                            
                                                         </select>
                                                    </td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditContactPerPosition}</label></td>
                                                    
                                                     <td style="width: 200px;">Fax</td>
                                                    <td><input type="text" name="audFax"  maxlength="20" value="${companyBean.auditFax}" TABINDEX=35></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditFax}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td></td>
                                                    <td style="width: 100px;"></td>
                                                    
                                                     <td style="width: 200px;">E-mail</td>
                                                    <td><input type="text" name="audEmail"  maxlength="32" value="${companyBean.auditEmail}" TABINDEX=36></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean.auditEmail}</label></td>
                                                    
                                                </tr>
                                                
                                                
                                            </table>      

                                            <table cellpadding="0" cellspacing="10" >
                                                <tr >
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td colspan="3">
                                                        <input type="submit" name="next" value="Save & Next" style="width: 100px;"/>&nbsp;
                                                        <input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}','${sessionScope.SessionObject.cardCategory}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                        <input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </table>
                                                        
                                        </form>
                                       </div>
                                                        
                                        <!-- ----------------- tab 2   ---------------------------------------------- -->                 
                                                        
                                        <div id="tabs-2" >
                                            <form  name="managersessionForm" method="Post" action="<%=request.getContextPath()%>/SetManagerDataToSessionServlet">
                                            <table cellpadding="0" cellspacing="10"  >
                                                        
                                                
                                                
                                                 <!-------------------------------------------management details --->
                                                 
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br/>Company Management Details</font></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Title</td>
                                                    <td><select name="title" TABINDEX=1>
                                                            <option value="">--Select Title--</option>

                                                            <c:if test="${managerBean.title =='Mr'}"> <option selected="true" value="Mr">Mr</option></c:if>
                                                            <c:if test="${managerBean.title !='Mr'}"> <option value="Mr">Mr</option></c:if>
                                                            <c:if test="${managerBean.title =='Mrs'}"> <option selected="true" value="Mrs">Mrs</option></c:if>
                                                            <c:if test="${managerBean.title !='Mrs'}"> <option value="Mrs">Mrs</option></c:if>
                                                            <c:if test="${managerBean.title =='Ms'}"> <option selected="true" value="Ms">Ms</option></c:if>
                                                            <c:if test="${managerBean.title !='Ms'}"> <option value="Ms">Ms</option></c:if>
                                                            <c:if test="${managerBean.title =='Dr'}"> <option selected="true" value="Dr">Dr</option></c:if>
                                                            <c:if test="${managerBean.title !='Dr'}"> <option value="Dr">Dr</option></c:if>                                                
                                                            <c:if test="${managerBean.title =='Prof'}"> <option selected="true" value="Prof">Prof</option></c:if>
                                                            <c:if test="${managerBean.title !='Prof'}"> <option value="Prof">Prof</option></c:if>
                                                            <c:if test="${managerBean.title =='Rev'}"> <option selected="true" value="Rev">Rev</option></c:if>
                                                            <c:if test="${managerBean.title !='Rev'}"> <option value="Rev">Rev</option></c:if>
                                                            <c:if test="${managerBean.title =='Hon'}"> <option selected="true" value="Hon">Hon</option></c:if>
                                                            <c:if test="${managerBean.title !='Hon'}"> <option value="Hon">Hon</option></c:if>
                                                            <c:if test="${managerBean.title =='Ven'}"> <option selected="true" value="Ven">Ven</option></c:if>
                                                            <c:if test="${managerBean.title !='Ven'}"> <option value="Ven">Ven</option></c:if>
                                                        </select>
                                                    </td>
                                                    <td style="width: 100px;"> <label style="color: red">${invalidMsgBean2.title} </label></td> 
                                                    
                                                     <td style="width: 200px;">Year of Service</td>
                                                    <td><input type="text" name="yearOfService"  maxlength="10" value="${managerBean.yearsOfService}" TABINDEX=6></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.yearsOfService}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Initials</td>
                                                    <td><input type="text" name="initials"  maxlength="10" value="${managerBean.initials}" TABINDEX=2></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.initials}</label></td>
                                                    
                                                     <td style="width: 200px;">Office Phone Number</td>
                                                    <td><input type="text" name="managerPhone"  maxlength="20" value="${managerBean.oficePhone}" TABINDEX=7></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.oficePhone}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">First Name</td>
                                                    <td><input type="text" name="managerFname"  maxlength="32" value="${managerBean.fname}" TABINDEX=3></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.fname}</label></td>
                                                    
                                                     <td style="width: 200px;">Mobile Phone Number</td>
                                                    <td><input type="text" name="managerMobile"  maxlength="20" value="${managerBean.mobilePhone}" TABINDEX=8></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.mobilePhone}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Middle Name</td>
                                                    <td><input type="text" name="managerMidname"  maxlength="32" value="${managerBean.midname}" TABINDEX=4></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.midname}</label></td>
                                                    
                                                     <td style="width: 200px;">Fax Number</td>
                                                    <td><input type="text" name="managerFax"  maxlength="32" value="${managerBean.fax}" TABINDEX=9></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.fax}</label></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Last Name</td>
                                                    <td><input type="text" name="managerLastname"  maxlength="32" value="${managerBean.lname}" TABINDEX=5></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.lname}</label></td>
                                                    
                                                     <td style="width: 200px;">E-mail</td>
                                                    <td><input type="text" name="managerEmail"  maxlength="32" value="${managerBean.email}" TABINDEX=10></td>
                                                    <td style="width: 100px;"><label style="color: red">${invalidMsgBean2.email}</label></td>
                                                    
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;"> </td>
                                                    <td><input type="submit" name="add"  style="width: 100px;"  value="Add" tabindex="11" />
                                                    </td>
                                                    <td style="width: 100px;"></td>



                                                </tr>
                                                                                              
                                            </table> 
                                                        
                                         </form>
                                                        
                                         <form  name="managersaveForm" method="Post" action="<%=request.getContextPath()%>/AddEstManagerDetailsServlet">                                            
                                          
                                            <table cellpadding="0" cellspacing="10">
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
                                                                    
                                                                    <th width="100px;" scope="col">Title</th>
                                                                    <th width="100px;" scope="col">Initials</th>
                                                                    <th width="100px;" scope="col">First Name</th>
                                                                    <th width="100px;" scope="col">Middle Name</th>
                                                                    <th width="100px;" scope="col">Last Name</th>
                                                                    <th width="100px;" scope="col">Year of Service</th>
                                                                    <th width="100px;" scope="col">Office Phone</th>
                                                                    <th width="100px;" scope="col">Mobile Phone</th>
                                                                    <th width="100px;" scope="col">Fax</th>
                                                                    <th width="100px;" scope="col">E-mail</th>
                                                                    
                                                                    <th width="100px;" scope="col">Delete</th>
                                                                        
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach var="managerDetailList" items="${sessionScope.SessionObject.sessionManagerDetailList}">
                                                                    <tr class="gradeC">
                                                                        <td >${managerDetailList.title} </td>
                                                                        <td > ${managerDetailList.initials}</td>
                                                                        <td >${managerDetailList.fname} </td>
                                                                        <td >${managerDetailList.midname} </td>
                                                                        <td >${managerDetailList.lname}  </td>
                                                                        <td > ${managerDetailList.yearsOfService}</td>
                                                                        <td > ${managerDetailList.oficePhone}</td>
                                                                        <td >${managerDetailList.mobilePhone} </td>
                                                                        <td >${managerDetailList.fax}  </td>
                                                                        <td >${managerDetailList.email}  </td>
                                                                        
                                                                        <td >
                                                                            <input type="button" value="Delete" onclick="invokeRemoveManagerData('${managerDetailList.fname}','${managerDetailList.lname}','${managerDetailList.oficePhone}')">
                                                                        </td>
                                                                            
                                                                    </tr>
                                                                </c:forEach>
                                                                    
                                                                    
                                                            </tbody>
                                                                
                                                                
                                                        </table>
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                        
                                                        
                                                        
                                                </tr>
                                                                                                    
                                            </table>
                                                                                                                                  

                                            <table cellpadding="0" cellspacing="10" >
                                                <tr >
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td colspan="3">
                                                        <input type="submit" name="next" value="Save & Next" style="width: 100px;"/>&nbsp;
                                                        <input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}','${sessionScope.SessionObject.cardCategory}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                        <input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </table>
                                                
                                        </form>                
                                                        
                                                        
                                                        
                                                        
                                    </div>

                                    <!-- ----------------- tab 3   ---------------------------------------------- -->

                                    <div id="tabs-3" >
                                        
                                        
                                        <form name="banksessionform" method="POST" action="<%=request.getContextPath()%>/SetBankDetailsToSessionServlet">
                                                    
                                                    <table  cellpadding="0" cellspacing="10">
                                                        
                                                         <!------------------------------------------- Company Bank Details --->
                                                         <tr>
                                                             <td colspan="6" ><font style="color: #999"> <br />Company Bank Details</font></td>
                                                         </tr>
                                                        
                                                        <tr>
                                                            <td style="width: 200px;">Name of the Bank </td>
                                                            <td><select name="bankName" id="bankName" onchange="changeBranches()" tabindex="1">
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
                                                            <td><select name="brachName" id="brachName" tabindex="2">
                                                                
                                                                </select>
                                                            </td>
                                                            <td style="width: 100px;"></td>
                                                                
                                                        </tr> 
                                                        <tr>
                                                            <td style="width: 200px;">Account Type </td>
                                                            <td><select name="accType" tabindex="3">
                                                                    <option value="">--Select Account--</option>
                                                                    <option value="Current Account">Current Account</option>
                                                                    <option value="Savings Account">Savings Account</option>
                                                                </select>
                                                            </td>
                                                            <td style="width: 100px;"></td>
                                                                
                                                        </tr> 
                                                        <tr>
                                                            <td style="width: 200px;">Account Number  </td>
                                                            <td><input type="text" name="accNumber"  maxlength="16"  value="${bankDetailBean.accountNumber}" tabindex="4">
                                                            </td>
                                                            <td style="width: 100px;"> <label style="color: red">${invalidAccount} </label></td>
                                                                
                                                                
                                                                
                                                        </tr> 
                                                        <tr>
                                                            <td style="width: 200px;">Account Since  </td>
                                                            <td>years <select name ="year" tabindex="5">
                                                                    <!--                                                       <option value="">years</option>-->
                                                                    <c:forEach var="years" begin="0" end="50" step="1">
                                                                        
                                                                        <option value="${years}">${years}</option>
                                                                            
                                                                    </c:forEach> 
                                                                        
                                                                        
                                                                        
                                                                </select>
                                                                &nbsp;months <select name="month" tabindex="6">
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
                                                            <td><input type="submit" name="add"  style="width: 100px;"  value="Add" tabindex="7"/>
                                                            </td>
                                                            <td style="width: 100px;"></td>
                                                                
                                                                
                                                                
                                                        </tr>
                                                    </table>
                                                </form>
                                        
                                        
                                        <form   name="bankSaveForm" method="Post" action="<%=request.getContextPath()%>/AddEstBankDetailsServlet">                                            
                                          
                                            <table cellpadding="0" cellspacing="10">
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
                                                                        <td > ${bankDetailList.bankNameDes}   </td>
                                                                        <td > ${bankDetailList.branchName}   </td>
                                                                        <td >  ${bankDetailList.accountType} </td>
                                                                        <td > ${bankDetailList.accountNumber} </td>
                                                                        <td >  ${bankDetailList.accountSince}  </td>
                                                                        <td >
                                                                            <input type="button" value="Delete" onclick="invokeRemoveBankData('${bankDetailList.bankName}','${bankDetailList.accountNumber}')">
                                                                        </td>

                                                                    </tr>
                                                                </c:forEach>
                                                                    
                                                                    
                                                            </tbody>
                                                                
                                                                
                                                        </table>
                                                    </td>
                                                    <td style="width: 100px;"></td>
                                                        
                                                        
                                                        
                                                </tr>
                                                                                                    
                                            </table>
                                                                                                                                  

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td>
                                                        <input type="submit" name="next" value="Complete" style="width: 100px;"/>&nbsp;
                                                        <input type="button" onclick="invokeReset('${sessionScope.SessionObject.applicationId}','${sessionScope.SessionObject.cardCategory}')" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                        <input type="button" name="cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </table>
                                                
                                        </form>
                                    </div>
                                  
                                                            
                                <!-- ----------------- tab 4   ---------------------------------------------- -->
<!--                                <div id="tabs-4" >
                                        
                                        
                                        <form name="assetsessionform" method="POST" action="">
                                                    
                                         <table  cellpadding="0" cellspacing="10"> 
                                             
                                         ----------------------------------------- Assets -
                                                <tr>
                                                    <td colspan="6" ><font style="color: #999"> <br />Assets</font></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;"> Asset Type</td>    
                                                    <td><input type="text" name="assetType"  maxlength="32" value="" TABINDEX=7></td>
                                                    <td style="width: 100px;"><label style="color: red"> </label></td>
                                                    
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Description</td> 
                                                    <td><input type="text" name="assetDes"  maxlength="64" value="" TABINDEX=8></td>
                                                    <td style="width: 100px;"><label style="color: red"> </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"> Value</td> 
                                                    <td><input type="text" name="assetValue"  maxlength="32" value="" TABINDEX=9></td>
                                                    <td style="width: 100px;"><label style="color: red"> </label></td>
                                                </tr> 
                                                
                                                 <tr>
                                                            <td style="width: 200px;"> </td>
                                                            <td><input type="submit" name="add"  style="width: 100px;"  value="Add" >
                                                            </td>
                                                            <td style="width: 100px;"></td>
                                                                
                                                                
                                                                
                                                        </tr>
                                                    </table>
                                                </form>
                                                                                        
                                                
                                                
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
                                                
                                                
                                    </div>-->
                                    
                                                            
                                                         
                                                            
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

