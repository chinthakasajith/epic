<%-- 
    Document   : approvechangeaddressandcontactdetails
    Created on : Sep 13, 2016, 5:45:59 PM
    Author     : prageeth_s
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <title>EPIC_CMS_HOME</title>

        <style type="text/css">
            .checkBoxChangecontactDetails{                
                    margin-left: 2 px;
                    float: left;
            }
            
        </style>
        
        <script  type="text/javascript" charset="utf-8">
            
            
            function invokeApprove() {
                
           

                document.updateCustomerContactDetailsServlet.action = "${pageContext.request.contextPath}/UpdateRequestCustomerContactDetailsServlet?action=approve";
                document.updateCustomerContactDetailsServlet.submit();
            }
            
            
            function invokeReject() {

                document.updateCustomerContactDetailsServlet.action = "${pageContext.request.contextPath}/UpdateRequestCustomerContactDetailsServlet?action=reject";
                document.updateCustomerContactDetailsServlet.submit();
            }

            function showUpdateObject(id){
                
                
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


            function invokeUpdate()
            {

                document.updateCustomerTxn.action = "${pageContext.request.contextPath}/UpdateCallCenterCustomerTxnUpdateServlet";
                document.updateCustomerTxn.submit();

            }

            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCallCenterCustomerTxnUpdateServlet";


            }
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=CCCUST";


            }

        </script>   

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CUSTOMER_CHANGE_CONTACT_DETAILS%>'
                        },
                function(data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }
                });
            }

        </script>  
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header">
            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>
                </div>

                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">


                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Add(also default) view  ///////////////////////////////////////////////////////////-->


                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                            <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                            </td>
                                        </tr>
                                        
                                    </table>

                                    <form method="POST" name="updateCustomerContactDetailsServlet" id="updateCustomerContactDetailsServlet">
                                        <font style="color: #999"> Addresses</font>
                                        <table style="width: 90%">
                                            <tbody><tr>

                                                <td style="width: 50%;">
                                                    
                                                    <c:if test="${isPermanentAddressChanged}">
                                                    
                                                    <div>
                                                        <font color="#3F2C1C"><b>Permanent Address</b> </font> 
                                                        <table id="permanentAddresstbl">
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>    
                                                                    <input type="text" name="address1" id="address1" maxlength="32" value="${personalBean.permentAddress1}" tabindex="14">
                                                                    <label style="color: red">${invalidMsgBean.permentAddress1}</label>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                            
                                                                    <input type="text" name="address2" id="address2" maxlength="32" value="${personalBean.permentAddress2}" tabindex="15">
                                                                    <label style="color: red">${invalidMsgBean.permentAddress2}</label>
                                                                </td>
                                                            </tr>            
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                                        
                                                                    <input type="text" name="address3" id="address3"  maxlength="32" value="${personalBean.permentAddress3}" tabindex="16">

                                                                   <label style="color: red">${invalidMsgBean.permentAddress3}</label>
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%">Area  <font style="color: red;">*</font><td> 
                                                                    
                                                                <td> 
                                                                    <select name="city" id="city" tabindex="17" onchange="setDistrictAndProvince('perm')" width="163px; ">
                                                                    <option value="">--SELECT--</option>
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
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td style="width:50%"> District <td> 
                                                                    
                                                                <td>
                                                                    <input type="text" name="district" id="district" readonly="true" maxlength="32" value="${personalBean.permentDistrict}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%"> Province <td> 
                                                                    
                                                                <td> 
                                                                     <input type="text" name="province" id="province" readonly="true" maxlength="32" value="${personalBean.permentProvince}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                        </table>                

                                                    </div>
                                                  </c:if>
                                                </td>
                                                <td> 
                                                    <c:if test="${isResidenceAddressChanged}">
                                                    <div>
                                                        <font color="#3F2C1C"><b>Residence Address</b> </font>
                                                        <table id="resAddresstbl">
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>    
                                                                    <input type="text" name="resAddress1" id="resAddress1" maxlength="32" value="${personalBean.resaddress1}" tabindex="14">
                                                                    <label style="color: red">${invalidMsgBean.resaddress1}</label>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                            
                                                                    <input type="text" name="resAddress2" id="resAddress2" maxlength="32" value="${personalBean.resaddress2}" tabindex="15">
                                                                    <label style="color: red">${invalidMsgBean.resaddress2}</label>
                                                                </td>
                                                            </tr>            
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                                        
                                                                    <input type="text" name="resAddress3" id="resAddress3"  maxlength="32" value="${personalBean.resaddress3}" tabindex="16">

                                                                    <label style="color: red">${invalidMsgBean.resaddress3}</label>
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%">Area  <font style="color: red;">*</font><td> 
                                                                    
                                                                <td> 
                                                                    <select name="resCity" id="ResCity" tabindex="17" onchange="setDistrictAndProvince('res')" width="163px; ">
                                                                    <option value="">--SELECT--</option>
                                                                    <c:if test="${personalBean.rescity != null}">
                                                                        <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                            <c:if test="${personalBean.rescity == areaList.areaCode}">
                                                                                <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                            </c:if>
                                                                        </c:forEach>

                                                                    </c:if> 
                                                                    <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                        <c:if test="${personalBean.rescity != areaList.areaCode}">
                                                                            <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                        </c:if>
                                                                    </c:forEach> 
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td style="width:50%"> District <td> 
                                                                    
                                                                <td>
                                                                    <input type="text" name="resDistrict" id="resDistrict" readonly="true" maxlength="32" value="${personalBean.resDistrict}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%"> Province <td> 
                                                                    
                                                                <td> 
                                                                     <input type="text" name="resProvince" id="resProvince" readonly="true" maxlength="32" value="${personalBean.resProvince}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                        </table>
                                                    </div>
                                                   </c:if>                     
                                                </td>


                                            </tr>
                                            <tr><td></td><td></td></tr>
                                            <tr>

                                                <td>
                                                    <c:if test="${isBillingAddressChanged}">
                                                    <div>
                                                        <font color="#3F2C1C"><b>Billing Address</b> </font>
                                                         <table id="billAddresstbl">
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>    
                                                                    <input type="text" name="billAddress1" id="billAddress1" maxlength="32" value="${personalBean.billAddress1}" tabindex="14">
                                                                    <label style="color: red">${invalidMsgBean.billAddress1}</label>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                            
                                                                    <input type="text" name="billAddress2" id="billAddress2" maxlength="32" value="${personalBean.billAddress2}" tabindex="15">
                                                                    <label style="color: red">${invalidMsgBean.billAddress2}</label>
                                                                </td>
                                                            </tr>            
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                                        
                                                                    <input type="text" name="billAddress3" id="billAddress3"  maxlength="32" value="${personalBean.billAddress3}" tabindex="16">
                                                                    <label style="color: red">${invalidMsgBean.billAddress3}</label>
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%">Area  <font style="color: red;">*</font><td> 
                                                                    
                                                                <td> 
                                                                    <select name="billCity" id="billCity" tabindex="17" onchange="setDistrictAndProvince('bill')" width="163px; ">
                                                                    <option value="">--SELECT--</option>
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
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td style="width:50%"> District <td> 
                                                                    
                                                                <td>
                                                                    <input type="text" name="billDistrict" id="billDistrict" readonly="true" maxlength="32" value="${personalBean.billDistrict}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%"> Province <td> 
                                                                    
                                                                <td> 
                                                                     <input type="text" name="billProvince" id="billProvince" readonly="true" maxlength="32" value="${personalBean.billProvince}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                        </table>
                                                    </div>
                                                    </c:if>            
                                                </td>
                                                <td> 
                                                    <c:if test="${isCompanyAddressChanged}"> 
                                                    <div>
                                                        <font color="#3F2C1C"><b>Company Address</b> </font>
                                                         <table id="compAddresstbl">
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>    
                                                                    <input type="text" name="companyAddress1" id="companyAddress1" maxlength="32" value="${personalBean.companyAddress1}" tabindex="14">
                                                                    <label style="color: red">${invalidMsgBean.companyAddress1}</label>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                            
                                                                    <input type="text" name="companyAddress2" id="companyAddress2" maxlength="32" value="${personalBean.companyAddress2}" tabindex="15">
                                                                    <label style="color: red">${invalidMsgBean.companyAddress2}</label>
                                                                </td>
                                                            </tr>            
                                                            <tr>
                                                                <td style="width:50%">  <td>
                                                                <td>                                                                        
                                                                    <input type="text" name="companyAddress3" id="companyAddress3"  maxlength="32" value="${personalBean.companyAddress3}" tabindex="16">
                                                                    <label style="color: red">${invalidMsgBean.companyAddress3}</label>
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%">Area  <font style="color: red;">*</font><td> 
                                                                    
                                                                <td> 
                                                                    <select name="companyCity" id="companyCity" tabindex="17" onchange="setDistrictAndProvince('perm')" width="163px; ">
                                                                    <option value="">--SELECT--</option>
                                                                    <c:if test="${personalBean.companyCity != null}">
                                                                        <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                            <c:if test="${personalBean.companyCity == areaList.areaCode}">
                                                                                <option value="${areaList.areaCode}" selected="true">${areaList.description}</option>
                                                                            </c:if>
                                                                        </c:forEach>

                                                                    </c:if> 
                                                                    <c:forEach var="areaList" items="${sessionScope.SessionObject.areaList}">
                                                                        <c:if test="${personalBean.companyCity != areaList.areaCode}">
                                                                            <option value="${areaList.areaCode}">${areaList.description}</option>
                                                                        </c:if>
                                                                    </c:forEach> 
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td style="width:50%"> District <td> 
                                                                    
                                                                <td>
                                                                    <input type="text" name="companyDistrict" id="companyDistrict" readonly="true" maxlength="32" value="${personalBean.companyDistrict}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                            <tr>
                                                                <td style="width:50%"> Province <td> 
                                                                    
                                                                <td> 
                                                                     <input type="text" name="companyProvince" id="companyProvince" readonly="true" maxlength="32" value="${personalBean.companyProvince}">
                                                                </td>
                                                            </tr>
                                                            
                                                            
                                                        </table>
                                                    </div>
                                                    </c:if>                
                                                </td>


                                            </tr>
                                            <tr><td></td><td></td></tr>
<!--                                            <tr>

                                                <td>
                                                    <div>
                                                        <font color="#3F2C1C"><b>Address</b> </font>
                                                    </div>
                                                </td>
                                                <td> 
                                                    <div>
                                                        <font color="#3F2C1C"><b>Address</b> </font>
                                                    </div>

                                                </td>


                                            </tr>-->
                                            <tr><td></td><td></td></tr>
                                            <tr><td></td><td></td></tr>
                                        </tbody></table>


                                    <font style="color: #999">Contact Numbers</font>
                                    <table style="width: 90%">
                                        <tbody><tr>

                                                <td style="width: 50%;">
                                                    <c:if test="${isResPhoneNoChanged}">
                                                    <div>
                                                        
                                                        <table>
                                                            <tr>
                                                                <td style="width: 54%"><font color="#3F2C1C">Res. Phone No</font>
                                                                </td>
                                                                <td>
                                                                    <input type="text" name="resPhoneNo" id="resPhoneNo" maxlength="20" value="${personalBean.homeTelNumber}" tabindex="18">
                                                                    <label style="color: red">${invalidMsgBean.homeTelNumber}</label>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        
                                                    </div>
                                                    </c:if>            
                                                </td>
                                                <td> 
                                                    <c:if test="${isMobilePhoneNoChanged}">
                                                    <div>                                                        
                                                        <table>
                                                            <tr>
                                                                <td style="width: 54%"><font color="#3F2C1C">Mobile Phone No</font>
                                                                    
                                                                </td>
                                                                <td>
                                                                    <input type="text" name="mobileNo" id="mobileNo"  maxlength="20" value="${personalBean.mobileNumber}" tabindex="20">
                                                                    <label style="color: red">${invalidMsgBean.mobileNumber}</label>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    </c:if>            
                                                </td>


                                            </tr>

                                            <tr>

                                                <td>
                                                    <c:if test="${isOfficePhoneNoChanged}">                                        
                                                    <div>
                                                        <table>
                                                            <tr>
                                                                <td style="width: 54%"><font color="#3F2C1C">Office Phone No</font>
                                                                
                                                                </td>
                                                                <td >
                                                                    <input type="text" name="officePhoneNo" id="officePhoneNo" maxlength="20" value="${personalBean.officeTelNumber}" tabindex="19">
                                                                    <label style="color: red">${invalidMsgBean.officeTelNumber}</label>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    </c:if>            
                                                </td>
                                                <td> 
                                                    <c:if test="${isEstMobContactNoChanged}">
                                                    <div>
                                                    
                                                        <table>
                                                            <tr>
                                                                <td style="width: 54%"><font color="#3F2C1C">Est contact No(Mobile)</font>
                                                                
                                                                </td>
                                                                <td ><input type="text" name="estMobileNo" id="estMobileNo"  maxlength="20" value="${personalBean.estMobileNo}" tabindex="19">
                                                                    <label style="color: red">${invalidMsgBean.estMobileNo}</label></td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    </c:if>            
                                                </td>


                                            </tr>

                                            <tr>

                                                <td>
                                                    <c:if test="${isEstLandContactNoChanged}">
                                                    <div>
                                                        <table>
                                                            <tr>
                                                                
                                                                <td style="width: 54%"><font color="#3F2C1C">Est contact No(Land)</font>                                                                
                                                                </td>
                                                                <td >
                                                                    <input type="text" name="estLandNo" id="estLandNo" maxlength="20" value="${personalBean.estLandNo}" tabindex="19">
                                                                    <label style="color: red">${invalidMsgBean.estLandNo}</label>
                                                                </td>
                                                                <td ></td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    </c:if>            
                                                </td>
                                                <td> 
                                                    <c:if test="${isFaxNoChanged}">
                                                    <div>
                                                        
                                                        <table>
                                                            <tr>
                                                                <td style="width: 54%"><font color="#3F2C1C">Fax No</font>
                                                                
                                                                </td>
                                                                <td ><input type="text" name="faxNo" id="faxNo"  maxlength="20" value="${personalBean.faxNo}" tabindex="19">
                                                                    <label style="color: red">${invalidMsgBean.faxNo}</label>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    </c:if>            
                                                </td>


                                            </tr>
                                            
                                            <tr>

                                                <td>
                                                    <div>
                                                        <table>
                                                            <tr>
                                                                <td style="width: 47%"><font color="#3F2C1C">Remark</font>
                                                                    
                                                                
                                                                </td>
                                                                <td >
                                                                    <textarea id="remark" onfocus="this.rows = 25" cols="50" rows="15" name="remark" style="width: 200px; height: 60px;">                                                            
                                                                                                                            </textarea>                                                                    
                                                                </td>
                                                                <td ></td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </td>
                                                <td> 
                                                    <div>
                                                        
                                                        <table>
                                                            <tr>
                                                                <td style="width: 54%">
                                                                
                                                                </td>
                                                                <td >
                                                                    
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>

                                                </td>


                                            </tr>
                                            

                                        </tbody></table>  
                                                                
                                        <table style="width: 90%"><tr><td></td><td></td></tr></table>                       
                                        <table style="width: 90%"><tr><td></td><td></td></tr></table> 
                                        <table style="width: 90%"><tr>
                                            <td><input type="button"  value="Approve" onclick="invokeApprove()" style="width: 80px;" /></td>
                                            <td><input type="button"  value="Reject"  onclick="invokeReject()" style="width: 80px;" /></td>
                                            </tr></table> 
                                        
                                        
                                        
                                        <input type="hidden" name="isPermanentAddressChanged" value="${isPermanentAddressChanged}" />
                                        <input type="hidden" name="isResidenceAddressChanged" value="${isResidenceAddressChanged}" />
                                        <input type="hidden" name="isBillingAddressChanged" value="${isBillingAddressChanged}" />
                                        <input type="hidden" name="isCompanyAddressChanged" value="${isCompanyAddressChanged}" />
                                        <input type="hidden" name="isResPhoneNoChanged" value="${isResPhoneNoChanged}" />
                                        <input type="hidden" name="isMobilePhoneNoChanged" value="${isMobilePhoneNoChanged}" />
                                        <input type="hidden" name="isOfficePhoneNoChanged" value="${isOfficePhoneNoChanged}" />
                                        <input type="hidden" name="isEstMobContactNoChanged" value="${isEstMobContactNoChanged}" />
                                        <input type="hidden" name="isEstLandContactNoChanged" value="${isEstLandContactNoChanged}" />
                                        <input type="hidden" name="isFaxNoChanged" value="${isFaxNoChanged}" />
                                        
                                        <input type="hidden" name="applicationId" value="${personalBean.applicationId}" />
                                        <input type="hidden" name="cardCategory" value="${personalBean.cardCategory}" />
                                        <input type="hidden" name="requestId" value="${personalBean.requestId}" />

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
