<%-- 
    Document   : debitcardmgt
    Created on : Jan 31, 2012, 2:56:20 PM
    Author     : chanuka
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">


            function invokeAssignDetails()
            {
                document.debitaddcardform.action = "${pageContext.request.contextPath}/GetDetailsByDebitAccountTempServlet?operation=add";
                document.debitaddcardform.submit();
            }

            function invokeAdd()
            {

                document.debitaddcardform.action = "${pageContext.request.contextPath}/AddDebitCardTemplateServlet";
                document.debitaddcardform.submit();

            }

            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadDebitCardTemplateMgtServlet";

            }

            function invokeReset(ele) {
                tags = ele.getElementsByTagName('input');
                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                    }
                }

                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'radio':
                            tags[i].checked = false;
                            break;
                    }
                }

                tags = ele.getElementsByTagName('label');
                for (i = 0; i < tags.length; i++) {
                    tags[i].innerText = '';
                }

                tags = ele.getElementsByTagName('select');
                for (i = 0; i < tags.length; i++) {
                    if (tags[i].type === 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for (j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }

                ele['cashAdvanceRate'].value = '0';

            }

            function loadcproductList(product) {


                $('#cproduct').empty();

                var domain = $("#domain").val();
                var ctype = $("#cardType").val();

                $.getJSON("${pageContext.servletContext.contextPath}/GetCardProductsServlet",
                        {
                            cDomain: domain,
                            cType: ctype
                        },
                function (jsonobject) {

                    $.each(jsonobject.combo1, function (code, description) {

                        if (code === product) {

                            $('#cproduct').append(
                                    $('<option ></option>').val(code).html(description)

                                    );
                            //$(this).attr("selected",true);
                        }
                        if (code !== product) {
                            $('#cproduct').append(
                                    $('<option ></option>').val(code).html(description)

                                    );
                        }

                    });
                });


            }

            function loadcproductList2(val)
            {

                document.debitaddcardform.action = "${pageContext.request.contextPath}/GetCardProductsServlet?addorupdate=" + val;
                document.debitaddcardform.submit();

            }


            //             function GetDomainAndType(val)
            //            {
            //              
            //                $.getJSON("${pageContext.servletContext.contextPath}/GetCardDomainAndTypeServlet", 
            //                {                     
            //                    addorupdate : addpage,                    
            //                    code : $("#debitaccounttemplatecode").val()
            //                },
            //               function(jsonobject) {
            //                     
            //                    $.each(jsonobject.combo1, function(code, description) {
            //                         $('#domain').append(
            //                            $('<option selected ></option>').val(code).html(description)
            //                    );
            //                    $.each(jsonobject.combo2, function(code, description) {
            //                         $('#cardType').append(
            //                            $('<option selected ></option>').val(code).html(description)
            //                    );
            //                });
            //             
            //
            //            });
            //            }

            function setDomainAndType() {

                $.post("${pageContext.request.contextPath}/GetCardDomainAndTypeServlet",
                        {addorupdate: 'addpage',
                            code: $("#debitaccounttemplatecode").val()

                        },
                function (data) {

                    $('#domain option').each(function () {
                        $(this).remove();
                    });
                    $('#cardType option').each(function () {
                        $(this).remove();
                    });
                    $('#domain').html('<option value="" >------SELECT------</option>');
                    $('#cardType').html('<option value="" >------SELECT------</option>');


                    if (data !== '') {


                        var array = data.split(',');

                        if (array[0] !== 'null') {
                            $('#domain').append(
                                    $('<option selected ></option>').val(array[0]).html(array[1])


                                    );
                        }
                        if (array[2] !== 'null') {
                            $('#cardType').append(
                                    $('<option selected ></option>').val(array[2]).html(array[3])
                                    );
                        }
                    }
                    loadcproductList('${debitBean.productCode}');

                });


            }



        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.DEBITCARDHOME%>'
                        },
                function (data) {

                    if (data !== '') {
                        $('.center').text(data);
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

        </script>  



        <title>EPIC CMS DEBIT CARD TEMPLATE MANAGEMENT</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <form method="POST" action="" name="debitaddcardform">

                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr>
                                                <td style="height:20px;"></td>
                                            </tr>
                                            <tr>
                                                <td>Template Code</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templatecode" value="${debitBean.templateCode}" maxlength="6"/>
                                                </td>
                                                <td></td>                                            
                                                <td>Template Name</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templatename" value="${debitBean.templateName}" maxlength="64"/>
                                                </td>                                                
                                            </tr>
                                            <tr>
                                                <td>Valid From</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="validFrom" readonly maxlength="16" value="${debitBean.validFrom}" key="validTo" id="validFrom"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#validFrom").datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd",
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                        });
                                                    </script>
                                                </td> 
                                                <td></td>
                                                <td>Valid To</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="validTo" readonly maxlength="16" value="${debitBean.validTo}" key="validTo" id="validTo"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#validTo").datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd",
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                        });
                                                    </script>
                                                </td>
                                            </tr>                                           

                                            <tr>
                                                <td>Account Template </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="debitaccounttemplatecode" id="debitaccounttemplatecode" style="width: 163px" onchange="setDomainAndType()">
                                                        <option value="" >------SELECT------</option>

                                                        <c:forEach var="template" items="${debitAccountTemplateList}">
                                                            <c:if test="${debitBean.debitAccounttemplateCode==template.key}">
                                                                <option value="${template.key}" selected>${template.value}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.debitAccounttemplateCode!=template.key}">
                                                                <option value="${template.key}" >${template.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>                                            
                                                <td >Card Domain</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="domain" id="domain"  style="width: 163px">
                                                        <option value="" >------SELECT------</option>
                                                    </select>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Card Type</td>
                                                <td><font style="color: red;">*</font>&nbsp;

                                                    <select name="cardType" id="cardType"  style="width: 163px">
                                                        <option value="" >------SELECT------</option>
                                                    </select>

                                                </td>
                                                <td></td>

                                                <td>Card Product</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select id="cproduct" name="cproduct"  onchange="" class="inputfield-mandatory">
                                                        <option value="" >------SELECT------</option>                                      


                                                    </select>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>Currency Type </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="currencytype" style="width: 163px">
                                                        <!--<option value="" >------SELECT------</option>-->
                                                        <c:forEach var="currency" items="${currencyList}">
                                                            <c:if test="${debitBean.currencyCode==currency.key}">
                                                                <option value="${currency.key}" selected>${currency.value}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.currencyCode!=currency.key}">
                                                                <c:if test="${currency.key eq '144'}">
                                                                    <option value="${currency.key}" >${currency.value}</option>
                                                                </c:if>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>

                                                <td style="width: 200px;">Service Code</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select name="serviceCode" style="width: 163px">
                                                        <option value="" >------SELECT------</option>
                                                        <c:forEach var="service" items="${sessionScope.SessionObject.serviceCodeList}">
                                                            <c:if test="${debitBean.serviceCode==service.serviceCode}">
                                                                <option value="${service.serviceCode}" selected>${service.description}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.serviceCode!=service.serviceCode}">
                                                                <option value="${service.serviceCode}" >${service.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>


                                                <td style="width: 200px;">Cash Advanced Rate</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" id="cashAdvanceRate" name="cashAdvanceRate" value="${debitBean.cashAdvanceRate}" maxlength="3"/>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">Expiry Period</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="expiryPeriod" value="${debitBean.expPeriod}" maxlength="10"/></td>

                                            </tr>
                                            <tr>  

                                                <td>Fee Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="fee" style="width: 163px;">
                                                        <option value="" >---------SELECT---------</option>
                                                        <c:forEach var="fee" items="${feeProfiles}">
                                                            <c:if test="${debitBean.feeProfile==fee.key}">
                                                                <option value="${fee.key}" selected>${fee.value}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.feeProfile!=fee.key}">
                                                                <option value="${fee.key}" >${fee.value}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">Renew Period</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="renewPeriod" value="${debitBean.renewPeriod}" maxlength="10"/></td>

                                            </tr>
                                            <tr> 

                                                <td>Risk Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="risk" style="width: 163px;">
                                                        <option value="" >---------SELECT---------</option>

                                                        <c:forEach var="risk" items="${riskProfiles}">
                                                            <c:if test="${debitBean.riskProf==risk.key}">
                                                                <option value="${risk.key}" selected>${risk.value}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.riskProf!=risk.key}">
                                                                <option value="${risk.key}" >${risk.value}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">Re-Issue Threshold Period</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="reissuThrshPeriod" value="${debitBean.reissueThreshPeriod}" maxlength="10"/></td>

                                            </tr> 
                                            <tr>  

                                                <td>Transaction Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="transaction" style="width: 163px;">
                                                        <option value="" >---------SELECT---------</option>

                                                        <c:forEach var="transaction" items="${transactionProfiles}">
                                                            <c:if test="${debitBean.txnProf==transaction.key}">
                                                                <option value="${transaction.key}" selected>${transaction.value}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.txnProf!=transaction.key}">
                                                                <option value="${transaction.key}" >${transaction.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td></td>

                                                <td>Status </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="status" style="width: 163px">
                                                        <option value="" >------SELECT------</option>                                                                
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${debitBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${debitBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </tr>


                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="button" value="Add" name="add" class="defbutton" onclick="invokeAdd()"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeReset(this.form)" value="Reset"/>
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/></td>
                                                <td></td>
                                            </tr>
                                        </tbody>

                                    </table>

                                    <script>

                                        setDomainAndType();
                                    </script>
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




