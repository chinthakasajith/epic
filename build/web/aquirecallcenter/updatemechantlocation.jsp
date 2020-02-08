<%-- 
    Document   : updatemechantlocation
    Created on : Dec 7, 2012, 3:21:41 PM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Merchant Management Page</title>



        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <script >
            
            function selectAllUpdateMcc(selectBox1,selectBox2,selectBox3,selectBox4,selectBox5,selectBox6) {
               
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }     
                selectAllUpdateTxn(selectBox3,selectBox4,selectBox5,selectBox6);
            }
            
            function selectAllUpdateTxn(selectBox3,selectBox4,selectBox5,selectBox6) {
                 
                for (var i = 0; i < selectBox3.options.length; i++) {
                    selectBox3.options[i].selected = true;
                }
                for (var i = 0; i < selectBox4.options.length; i++) {
                    selectBox4.options[i].selected = true;
                } 
                for (var i = 0; i < selectBox5.options.length; i++) {
                    selectBox5.options[i].selected = true;
                }
                for (var i = 0; i < selectBox6.options.length; i++) {
                    selectBox6.options[i].selected = true;
                } 
                invokeUpdate();
            }
            
            
            function selectAllMcc(selectBoxMcc,selectBoxTxn,selectBoxCurrency) {
                for (var i = 0; i < selectBoxMcc.options.length; i++) {
                    selectBoxMcc.options[i].selected = true;
                }       
                selectAllTxn(selectBoxTxn,selectBoxCurrency);
            }
            function selectAllTxn(selectBoxTxn,selectBoxCurrency) {
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCurrency.options.length; i++) {
                    selectBoxCurrency.options[i].selected = true;
                } 
                
                invokeAdd();
            }
            
            function valueSet(opType){
                
                if(opType == 'ADD'){
                    document.createform.action="${pageContext.request.contextPath}/SetValueToMerchantLocationServlet?opType="+opType;
                    document.createform.submit();
                }
                if(opType == 'UPDATE'){
                    
                    document.updateForm.action="${pageContext.request.contextPath}/SetValueToMerchantLocationServlet?opType="+opType;
                    document.updateForm.submit();
                }
            }
            function ChangeBank(){
                //alert(bankCode);
                
                document.selectBank.action="${pageContext.request.contextPath}/LordMerchantCustomerBranchServlet";
                document.selectBank.submit();
            }
          
            function invokeCancel(val,val2){
                
                window.location = "${pageContext.request.contextPath}/ViewMerchantMgtServlet?section=ACCMLO&id="+val+"&merCusNo="+val2;
            }
            function invokeReset(reset){
            
                window.location = "${pageContext.request.contextPath}/LoadCreateMerchantLocationServlet?operation="+reset;
            }
            
            function invokeNextTab(reset){
            
                window.location = "${pageContext.request.contextPath}//administrator/templatemgt/accounttemplate/accouonttemplatehome.jsp?"+reset;
            }
            function invokeAdd()
            {

                document.createform.action="${pageContext.request.contextPath}/AddMerchantLocationAssignDataServlet";
                document.createform.submit();
            }

            function invokeUpdate()
            {
                
                document.updateForm.action="${pageContext.request.contextPath}/UpdateMerchantLocationAssignDataServlet";
                document.updateForm.submit();

            }
            
            function saveAndNext(){
                
                
                document.createform.action="${pageContext.request.contextPath}/AddMerchantLocationServlet";
                document.createform.submit();
            }
            
            function updateAndNext(){
                
                document.updateForm.action="${pageContext.request.contextPath}/UpdateMerchantLocationServlet";
                document.updateForm.submit();
            }
            function invokeUpdateReset(merchantId,merchantCustomerNumber){
                
                window.location="${pageContext.request.contextPath}/LoadUpdateMerchantLocationServlet?merchantId="+ merchantId + "&merchantCustomerNumber="+merchantCustomerNumber;
                
            }
            
            function lordBranch(){
                $('#selectBankBranch').empty();
                var sval=$("#selectBank").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LordMerchantCustomerBranchServlet", 
                { bankCode : sval},
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#selectBankBranch').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
            }
            
            function loadBank(){
                $('#selectBank').empty();
                var sval=$("#selectPaymentMode").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadMerchantCustomerBankServlet", 
                { paymentMode : sval},
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#selectBank').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
            }
             
        </script>  
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_MERCHANT_LOCATION_UPDATE%>'                                  
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
    </head>
    <body >



        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  -----------------------------------------------------------------   start  developer area  ----------------------------------------------------------                          -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <!-----------------------------------///////////////////////////////        Add Area       //////////////////////////////////------------------------------------->
                                

                                <!--//////////////////////////////////////////////           update Area             /////////////////////////////////////////////////////////                   -->

                                <c:if test="${operationtype=='update'}">   
                                    <form name="updateForm" method="post" >

                                        <div class="selector" id="tabs">
                                            <ul>
                                                <li><a href="#tabs-1">General </a></li>
                                                <li><a href="#tabs-2">MCC, Transaction & Currency </a></li>
                                            </ul>

                                            <!--  ////////////////////////      Tab Number1        /////////////////////                            -->
                                            <div id="tabs-1" >

                                                <!--  //////////////////////////////////////         Merchant Customer Details Area    //////////////////////////////////////////////////////////////////////////////////////////////////////                                     -->
                                                <br/>
                                                <p><b>Merchant Location Details</b></p> 
                                                <hr />
                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr></tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Merchant Id
                                                        </td>
                                                        <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<input readonly="readonly" type="text" name="merchantId"  maxlength="15" value="${merchantLocBean.merchantId}"></td>

                                                        <td style="width: 200px;">
                                                            Area 
                                                        </td>
                                                        <td style="width: 400px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectArea"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="area" items="${sessionScope.SessionObject.areaList}">

                                                                    <c:if test="${merchantLocBean.area==area.areaCode}">
                                                                        <option value="${area.areaCode}" selected="true" >${area.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.area!=area.areaCode}">
                                                                        <option value="${area.areaCode}">${area.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Description
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description"  maxlength="20" value="${merchantLocBean.description}"></td>

                                                        <td style="width: 200px;">
                                                            Country 
                                                        </td>
                                                        <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectCountry"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="country" items="${sessionScope.SessionObject.countryCodeList}">

                                                                    <c:if test="${merchantLocBean.country==country.alphaSecond}">
                                                                        <option value="${country.alphaSecond}" selected="true" >${country.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.country!=country.alphaSecond}">
                                                                        <option value="${country.alphaSecond}">${country.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select></td>
                                                        <!--                                                        <td style="width: 200px;">
                                                                                                                    Postal Code 
                                                                                                                </td>
                                                                                                                <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectPostalCode"  >
                                                                                                                        <option value="" selected>--SELECT--</option>
                                                        
                                                        <c:forEach var="postalCodeData" items="${sessionScope.SessionObject.postalCodeDetailList}">

                                                            <c:if test="${merchantLocBean.postalCode==postalCodeData.postalCode}">
                                                                <option value="${postalCodeData.postalCode}" selected="true" >${postalCodeData.postalCode}</option>
                                                            </c:if>
                                                            <c:if test="${merchantLocBean.postalCode!=postalCodeData.postalCode}">
                                                                <option value="${postalCodeData.postalCode}">${postalCodeData.postalCode}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>
                                                </td>-->
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Merchant Customer Name
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectMerchantCustomerName" class="inputfield-mandatory" onchange="valueSet('${"UPDATE"}')" >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="mcbl" items="${merchantCustomerList}">

                                                                    <c:if test="${merchantLocBean.merchantCustomerNumber==mcbl.merchantCustomerNumber}">
                                                                        <option value="${mcbl.merchantCustomerNumber}" selected="true" >${mcbl.merchantName}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.merchantCustomerNumber!=mcbl.merchantCustomerNumber}">
                                                                        <option value="${mcbl.merchantCustomerNumber}">${mcbl.merchantName}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td>


                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Address
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="address1"  maxlength="20" value="${merchantLocBean.address1}"></td>

                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">

                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="address2"  maxlength="20" value="${merchantLocBean.address2}"></td>

                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">

                                                        </td>
                                                        <td><font style="color: white;">*</font>&nbsp;<input type="text" name="address3"  maxlength="20" value="${merchantLocBean.address3}"></td>

                                                    </tr>
                                                </table>
                                                <!--  //////////////////////////////////////         Contact Person Details Area    //////////////////////////////////////////////////////////////////////////////////////////////////////                                     -->
                                                <br/>
                                                <p><b>Contact Person Details</b></p>
                                                <hr />

                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Title 
                                                        </td>

                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectTitle" value="" >
                                                                <option value="" selected >--SELECT--</option>



                                                                <c:if test="${merchantLocBean.cpTitle=='mr'}">
                                                                    <option value="mr" selected="true" >Mr</option>
                                                                </c:if>
                                                                <c:if test="${merchantLocBean.cpTitle!='mr'}">
                                                                    <option value="mr" >Mr</option>
                                                                </c:if>   

                                                                <c:if test="${merchantLocBean.cpTitle=='mrs'}">
                                                                    <option value="mrs" selected="true">Mrs</option>
                                                                </c:if>
                                                                <c:if test="${merchantLocBean.cpTitle!='mrs'}">
                                                                    <option value="mrs">Mrs</option>
                                                                </c:if>

                                                                <c:if test="${merchantLocBean.cpTitle=='miss'}">
                                                                    <option value="miss" selected="true">Miss</option>
                                                                </c:if>
                                                                <c:if test="${merchantLocBean.cpTitle!='miss'}">
                                                                    <option value="miss" >Miss</option>
                                                                </c:if>



                                                            </select></td>

                                                        <td style="width: 200px;">
                                                            TP Number
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="tpNumber"  maxlength="20" value="${merchantLocBean.tpNumber}"></td>

                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            First Name
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="cpFirstName"  maxlength="16" value="${merchantLocBean.cpFirstName}"></td>

                                                        <td style="width: 200px;">
                                                            Fax
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="fax"  maxlength="20" value="${merchantLocBean.fax}"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Middle Name
                                                        </td>
                                                        <td><font style="color: white;">*</font>&nbsp;<input type="text" name="cpMiddleName"  maxlength="16" value="${merchantLocBean.cpMiddleName}"></td>

                                                        <td style="width: 200px;">
                                                            E-mail
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="eMail"  maxlength="50" value="${merchantLocBean.eMail}"></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Last Name
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="cpLastName"  maxlength="16" value="${merchantLocBean.cpLastName}"></td>


                                                    </tr>
                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Position
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="cpPosotion"  maxlength="16" value="${merchantLocBean.cpPosotion}"></td>


                                                    </tr>
                                                </table>
                                                <!--  //////////////////////////////////////         Profile Details Area    //////////////////////////////////////////////////////////////////////////////////////////////////////                                     -->
                                                <br/>
                                                <p><b>Profile Details</b></p>
                                                <hr />
                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr>

                                                        <td style="width: 200px;">
                                                            Fee Profile
                                                        </td>
                                                        <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectFeeProfile"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="fee" items="${sessionScope.SessionObject.feeProfileList}">

                                                                    <c:if test="${merchantLocBean.feeProfile==fee.feeProCode}">
                                                                        <option value="${fee.feeProCode}" selected="true" >${fee.feeProDes}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.feeProfile!=fee.feeProCode}">
                                                                        <option value="${fee.feeProCode}">${fee.feeProDes}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select></td> 

                                                    </tr>
                                                    <tr>

                                                        <td style="width: 200px;">
                                                            Commission Profile
                                                        </td>
                                                        <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectCommisionProfile"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="comis" items="${sessionScope.SessionObject.commissionList}">

                                                                    <c:if test="${merchantLocBean.commissionProfile==comis.comProfileCode}">
                                                                        <option value="${comis.comProfileCode}" selected="true" >${comis.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.commissionProfile!=comis.comProfileCode}">
                                                                        <option value="${comis.comProfileCode}">${comis.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select></td> 

                                                    </tr>
                                                    <tr>

                                                        <td style="width: 200px;">
                                                            Risk Profile
                                                        </td>
                                                        <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectRiskProfile"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="risk" items="${sessionScope.SessionObject.riskProfileList}">

                                                                    <c:if test="${merchantLocBean.riskProfile==risk.riskProfCode}">
                                                                        <option value="${risk.riskProfCode}" selected="true" >${risk.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.riskProfile!=risk.riskProfCode}">
                                                                        <option value="${risk.riskProfCode}">${risk.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select></td> 

                                                    </tr>

                                                </table>

                                                <!--  //////////////////////////////////////         Payment & Statement  Area    //////////////////////////////////////////////////////////////////////////////////////////////////////                                     -->
                                                <br/>
                                                <p><b>Payment & Statement Details</b></p>
                                                <hr />
                                                <table cellpadding="0" cellspacing="10">

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Statement Cycle
                                                        </td> 

                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectStatementCycle"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="statment" items="${sessionScope.SessionObject.statementList}">

                                                                    <c:if test="${merchantLocBean.statementCycle==statment.billCycleCode}">
                                                                        <option value="${statment.billCycleCode}" selected="true" >${statment.billDescription}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.statementCycle!=statment.billCycleCode}">
                                                                        <option value="${statment.billCycleCode}">${statment.billDescription}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td> 

                                                        <td style="width: 200px;">
                                                            Generate Statement
                                                        </td> 

                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="statementStatus"  >

                                                                <c:forEach var="stStatus" items="${sessionScope.SessionObject.commonStatusList}">

                                                                    <c:if test="${merchantLocBean.statementStatus==stStatus.statusCode}">
                                                                        <option value="${stStatus.statusCode}" selected="true" >${stStatus.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.statementStatus!=stStatus.statusCode}">
                                                                        <option value="${stStatus.statusCode}">${stStatus.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Payment Cycle
                                                        </td> 

                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectPaymentCycle"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="payment" items="${sessionScope.SessionObject.paymentList}">

                                                                    <c:if test="${merchantLocBean.paymentCycle==payment.paymentCycleCode}">
                                                                        <option value="${payment.paymentCycleCode}" selected="true" >${payment.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.paymentCycle!=payment.paymentCycleCode}">
                                                                        <option value="${payment.paymentCycleCode}">${payment.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Currency Type</td>
                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="currencyType" class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="curr" items="${sessionScope.SessionObject.currencyTypeList}">

                                                                    <c:if test="${merchantLocBean.currencyType==curr.key}">
                                                                        <option value="${curr.key}" selected="true" >${curr.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.currencyType!=curr.key}">
                                                                        <option value="${curr.key}">${curr.value}</option>
                                                                    </c:if>

                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Payment Mode
                                                        </td>

                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;" id="selectPaymentMode"  name="selectPaymentMode"  onchange="loadBank()"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="payMode" items="${sessionScope.SessionObject.paymentModeList}">

                                                                    <c:if test="${merchantLocBean.paymentMode==payMode.paymentMode}">
                                                                        <option value="${payMode.paymentMode}" selected="true" >${payMode.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.paymentMode!=payMode.paymentMode}">
                                                                        <option value="${payMode.paymentMode}">${payMode.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <!--  //////////////////////////////////////         Bank & Account Details Area    //////////////////////////////////////////////////////////////////////////////////////////////////////                                     -->
                                                <br/>
                                                <p><b>Bank & Account Details</b></p>
                                                <hr />

                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr>

                                                        <td style="width: 200px;">
                                                            Bank Name
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectBank" id="selectBank" class="inputfield-mandatory" onchange="lordBranch()">
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="bank" items="${sessionScope.SessionObject.merchantBankList}">

                                                                    <c:if test="${merchantLocBean.bankCode==bank.bankCode}">
                                                                        <option value="${bank.bankCode}" selected="true" >${bank.bankName}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.bankCode!=bank.bankCode}">
                                                                        <option value="${bank.bankCode}">${bank.bankName}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td> 

                                                        <td style="width: 200px;">
                                                            Branch Name
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectBankBranch" id="selectBankBranch" class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="branch" items="${sessionScope.SessionObject.merchantBankBranchList}">
                                                                    <c:if test="${merchantLocBean.branchCode==branch.branchCode}">
                                                                        <option value="${branch.branchCode}" selected="true" >${branch.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.branchCode!=bank.branchCode}">
                                                                        <option value="${branch.branchCode}">${branch.description}</option>

                                                                    </c:if>
                                                                </c:forEach>

                                                            </select>
                                                        </td>

                                                    </tr>

                                                    <tr>

                                                        <td style="width: 200px;">
                                                            Account Number
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="accountNumber"  maxlength="20" value="${merchantLocBean.accountNumber}"></td> 
                                                        <td style="width: 200px;">
                                                            Account Type
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectAccountType"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="acc" items="${sessionScope.SessionObject.accountTypeList}">
                                                                    <c:if test="${merchantLocBean.accountType==acc.key}">
                                                                        <option value="${acc.key}" selected="true" >${acc.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.accountType!=acc.key}">
                                                                        <option value="${acc.key}">${acc.value}</option>

                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Account Name
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<input type="text" name="accountName"  maxlength="32" value="${merchantLocBean.accountName}"></td>  
                                                    </tr>

                                                </table>


                                                <!--  //////////////////////////////////////         Application Details Area    //////////////////////////////////////////////////////////////////////////////////////////////////////                                     -->
                                                <br/>
                                                <p><b>Activation Details</b></p>
                                                <hr />

                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Activation Date
                                                        </td> 
                                                        <td><font style="color: red;">*</font>&nbsp;<input  name="selectActivationDate" readonly maxlength="16" value="${merchantLocBean.activationDate}" key="fromdate" id="selectActivationDate"  />
                                                            
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                $( "#selectActivationDate" ).datepicker({
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

                                                    </tr> 
                                                    <tr>
                                                        <td style="width: 200px;">
                                                            Status
                                                        </td>
                                                        <td><font style="color: red;">*</font>&nbsp;<select style="width: 150px;"  name="selectStates"  >
                                                                <option value="" selected>--SELECT--</option>

                                                                <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                    <c:if test="${merchantLocBean.status==status.statusCode}">
                                                                        <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${merchantLocBean.status!=status.statusCode}">
                                                                        <option value="${status.statusCode}">${status.description}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>

                                                </table>

                                                <table>

                                                    <tr>

                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <td style="width: 200px;">

                                                    </td>
                                                    <td><input type="button" name="add"  style="width: 100px; height: 30px;" value="Save & Next" onclick="updateAndNext()">
                                                        <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeUpdateReset('${merchantLocBean.merchantId}','${merchantLocBean.merchantCustomerNumber}')" value="Reset">
                                                        <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel('${merchantLocBean.merchantId}','${merchantLocBean.merchantCustomerNumber}')" value="Cancel">
                                                    </td>
                                                    </tr>


                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                </table>

                                            </div>
                                            <!--    /////////////////    Tab Number2         ///////////////////// -->
                                            <div id="tabs-2">

                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr>
                                                        <td colspan="3">Select Merchant Categories  <B> <c:out value="${merchant}"/></B></td>
                                                    </tr>

                                                    <tr>
                                                        <td>
                                                            <h4><b>All Merchant Categories</b></h4>
                                                            <select name="notAssigndnedMerchantCatogoryList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="notAs" items="${notAssigndnedMerchantCatogoryList}">
                                                                    <option value="${notAs.mCCCode}" >${notAs.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >
                                                            <input type=button value=">" style="width: 50px;" onclick=moveout() class="buttonsize"><br>
                                                            <input type=button value="<" style="width: 50px;" onclick=movein() class="buttonsize"><br>
                                                            <input type=button value=">>" style="width: 75px;" onclick=moveallout() class="buttonsize"><br>
                                                            <input type=button value="<<" style="width: 75px;" onclick=moveallin() class="buttonsize">
                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Merchant Categories</b></h4>
                                                            <select name="assigndnedMerchantCatogoryList" style="width: 200px" id=out multiple="multiple"   size=10>

                                                                <c:forEach var="as" items="${assigndnedMerchantCatogoryList}">
                                                                    <option value="${as.mCCCode}" >${as.description}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10" >
                                                    <tr>
                                                        <td colspan="3">Select Transaction Types <B> <c:out value="${merchant}"/></B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Transaction Types</b></h4>
                                                            <select name="notAssigndnedTxnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                                <c:forEach  var="notAs" items="${notAssigndnedTxnTypeList}">
                                                                    <option value="${notAs.transactionTypeCode}" >${notAs.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >
                                                            <input type=button value=">" style="width: 50px;" onclick=moveout2() class="buttonsize"><br>
                                                            <input type=button value="<" style="width: 50px;" onclick=movein2() class="buttonsize"><br>
                                                            <input type=button value=">>" style="width: 75px;" onclick=moveallout2() class="buttonsize"><br>
                                                            <input type=button value="<<" style="width: 75px;" onclick=moveallin2() class="buttonsize">
                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Transaction Types</b></h4>
                                                            <select name="assigndnedTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>

                                                                <c:forEach var="as" items="${assigndnedTxnTypeList}">
                                                                    <option value="${as.transactionTypeCode}" >${as.description}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td colspan="0">Select Currency Type <B> <c:out value="${currency}"/></B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Currency Types</b></h4>
                                                            <select name="notAssignCurrencyList" style="width: 200px"  id=in0 multiple="multiple"  size=10>
                                                                <c:forEach  var="nas" items="${notAssignCurrencyList}">
                                                                    <option value="${nas.currencyCode}" >${nas.currencyDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >
                                                            <input type=button value=">" style="width: 50px;" onclick=moveout0() class="buttonsize"><br>
                                                            <input type=button value="<" style="width: 50px;" onclick=movein0() class="buttonsize"><br>
                                                            <input type=button value=">>" style="width: 75px;" onclick=moveallout0() class="buttonsize"><br>
                                                            <input type=button value="<<" style="width: 75px;" onclick=moveallin0() class="buttonsize">
                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Currency Types</b></h4>
                                                            <select name="assignCurrencyList" style="width: 200px" id=out0 multiple="multiple"   size=10>
                                                                <c:forEach var="as" items="${assignCurrencyList}">
                                                                    <option value="${as.currencyCode}" >${as.currencyDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td><input type="button" name="update"  style="width: 100px; height: 30px;" onclick="selectAllUpdateMcc(assigndnedMerchantCatogoryList,notAssigndnedMerchantCatogoryList,assigndnedTxnTypeList,notAssigndnedTxnTypeList,notAssignCurrencyList,assignCurrencyList)" value="Save">
                                                            <!--                                                            <input type="button" name="reset"  style="width: 100px;" value="Reset">-->
                                                            <input type="button" value="Back " name="backtab" style="width: 100px; height:30px;" class="previoustab">
                                                            <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel('${merchantLocBean.merchantId}','${merchantLocBean.merchantCustomerNumber}')" value="Cancel">
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>

                                                        </td>
                                                        <td>

                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>

                                                        </td>
                                                        <td>

                                                        </td>
                                                    </tr>
                                                </table>






                                            </div>

                                        </div>

                                    </form>
                                </c:if> 
                                <!------------------------------------------------  End Developer Area  --------------------------------------------------------------->
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
