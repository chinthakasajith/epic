<%-- 
    Document   : accouonttemplatehome
    Created on : Jan 26, 2012, 5:26:00 PM
    Author     : janaka_hennadi
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACCOUNT TEMPLATE PAGE</title>


        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <script >

            function invokeCancel() {

                window.location = "${pageContext.request.contextPath}/LoadAccountTempalteServlet";
            }
            function invokeResett(reset) {

                window.location = "${pageContext.request.contextPath}/ManageAccountTempalteServlet?operation=" + reset;
            }

            function invokeResetInUpdate(tempCode, edit, cusTemp) {
                //        
                window.location = "${pageContext.request.contextPath}/ManageAccountTempalteServlet?templateCode=" + tempCode + "&operation=" + edit + "&cusTemplate=" + cusTemp;
            }

            function invokeNextTab(reset) {

                window.location = "${pageContext.request.contextPath}//administrator/templatemgt/accounttemplate/accouonttemplatehome.jsp?" + reset;
            }
            function loadAccounRskProf(cusTemp, opType) {

                document.createform.action = "${pageContext.request.contextPath}/SetAccountRiskProfileDropDownServlet?id=" + cusTemp + "&opType=" + opType;
                document.createform.submit();


            }

            function loadAccounRskProfOnUpdate(cusTemp, opType) {

                document.editform.action = "${pageContext.request.contextPath}/SetAccountRiskProfileDropDownServlet?id=" + cusTemp + "&opType=" + opType;
                document.editform.submit();


            }

            function loadCustomerTemplates(opType) {

                document.createform.action = "${pageContext.request.contextPath}/SetCustomerTemplateDropDownServlet?opType=" + opType;
                document.createform.submit();

            }

            function loadCustomerTemplatesUpdate(opType) {

                document.editform.action = "${pageContext.request.contextPath}/SetCustomerTemplateDropDownServlet?opType=" + opType;
                document.editform.submit();

            }

            function invokeAdd(cusTemp)
            {
                document.createform.action = "${pageContext.request.contextPath}/AddAccountTempalteServlet?cusTemplate=" + cusTemp;
                document.createform.submit();

            }

            //---------------------------------------
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
                    if (tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for (j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }


            }

        </script>    
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.ACCTEMPLATE%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

        </script>           
    </head>
    <body >



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

                                <c:if test="${operationtype=='add'}">   
                                    <form name="createform" method="post" >
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr>
                                                <td style="width: 200px;">
                                                    Template Code
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="tempaltcode"  maxlength="6" value="${templateBean.templateCode}"/>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Template Name
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="templatename"  maxlength="64" value="${templateBean.templateName}"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Valid From</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="fromdate" maxlength="16" readonly value="${templateBean.valiedFrom}" key="fromdate" id="fromdate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#fromdate").datepicker({
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
                                                <td style="width: 200px;">Valid To</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="todate" maxlength="16" readonly value="${templateBean.valiedTo}" key="todate" id="todate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#todate").datepicker({
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
                                                <td style="width: 200px;">
                                                    Total Credit Limit
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="creditlimit"  maxlength="13" value="${templateBean.totalCreditLimit}"/>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Billing Cycle  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="bill" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:forEach var="bill" items="${billList}">
                                                            <c:if test="${templateBean.billCycle == bill.key}">
                                                                <option value="${bill.key}" selected>${bill.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.billCycle != bill.key}">
                                                                <option value="${bill.key}" >${bill.value}</option>
                                                            </c:if>

                                                        </c:forEach>                                                      

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <c:if test="${templateBean.staff == 'YES'}">
                                                        <input type="radio" id="staff" name="staff" value="YES" checked="true" />Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'YES'}">
                                                        <input type="radio" id="staff" name="staff" value="YES" onchange="loadCustomerTemplates('add')"/>Staff

                                                    </c:if>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                    <c:if test="${templateBean.staff == 'NO'}">
                                                        <input type="radio" id="staff" name="staff" value="NO" checked="true"  />Non-Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'NO'}">
                                                        <input type="radio" id="staff" name="staff" value="NO" onchange="loadCustomerTemplates('add')"/>Non-Staff

                                                    </c:if>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Customer Template  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="cusTempCode" id="cusTemp"onchange="loadAccounRskProf(cusTemp.value, 'add')" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:if test="${templateBean.customerTemplateCode != null}">
                                                            <c:forEach var="cusTemplateList" items="${cusTemplateList}">
                                                                <c:if test="${templateBean.customerTemplateCode == cusTemplateList.templateCode}">
                                                                    <option value="${cusTemplateList.templateCode}" selected="true">${cusTemplateList.templateName}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="cusTemplateList" items="${cusTemplateList}">
                                                            <c:if test="${templateBean.customerTemplateCode != cusTemplateList.templateCode}">
                                                                <option value="${cusTemplateList.templateCode}">${cusTemplateList.templateName}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>                                            
                                            <tr>
                                                <td style="width: 200px;">
                                                    Card Type  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="cardType" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:forEach var="cardType" items="${cardType}">
                                                            <c:if test="${templateBean.cardType == cardType.key}">
                                                                <option value="${cardType.key}" selected>${cardType.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.cardType != cardType.key}">
                                                                <option value="${cardType.key}" >${cardType.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Currency Type  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="currency" style="width: 163px;">
                                                        <c:if test="${templateBean.currencyCode != null}">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="currencyList" items="${currencyList}">
                                                                <c:if test="${templateBean.currencyCode == currencyList.currencyCode}">
                                                                    <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="currencyList" items="${currencyList}">
                                                            <c:if test="${templateBean.currencyCode != currencyList.currencyCode}">
                                                                <c:if test="${currencyList.currencyCode eq '144'}">
                                                                    <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                </c:if>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>


                                            </tr> 

                                            <tr>
                                                <td style="width: 200px;">
                                                    Interest Profile 
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="interestProf" style="width: 163px;">
                                                        <option value="">---------SELECT---------</option>
                                                        <c:forEach var="interest" items="${interestProf}">
                                                            <c:if test="${templateBean.interestprofileCode == interest.key}">
                                                                <option value="${interest.key}" selected>${interest.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.interestprofileCode != interest.key}">
                                                                <option value="${interest.key}" >${interest.value}</option>
                                                            </c:if>

                                                        </c:forEach>                                                       

                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Status  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="status" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:if test="${templateBean.status != null}">
                                                            <c:forEach var="statusLst" items="${statusLst}">
                                                                <c:if test="${templateBean.status == statusLst.statusCode}">
                                                                    <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="statusLst" items="${statusLst}">
                                                            <c:if test="${templateBean.status != statusLst.statusCode}">
                                                                <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Account Risk Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="riskProf" style="width: 163px;">
                                                        <option value="">---------SELECT---------</option>
                                                        <c:forEach var="risk" items="${riskProfLst}">
                                                            <c:if test="${templateBean.accRskProf == risk.key}">
                                                                <option value="${risk.key}" selected>${risk.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.accRskProf != risk.key}">
                                                                <option value="${risk.key}" >${risk.value}</option>
                                                            </c:if>

                                                        </c:forEach> 
                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">Transaction Profile</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select style="width: 163px;" name="txnProfile">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="txn" items="${sessionScope.SessionObject.transactionProfileList}">
                                                            <c:if test="${templateBean.txnProfCode==txn.profileCode}">
                                                                <option value="${txn.profileCode}" selected>${txn.description}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.txnProfCode!=txn.profileCode}">
                                                                <option value="${txn.profileCode}" >${txn.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">
                                                    Billing and Statement Profile 
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="statement" style="width: 163px;">
                                                        <option value="">---------SELECT---------</option>
                                                        <c:forEach var="statement" items="${statementList}">
                                                            <c:if test="${templateBean.statementProf == statement.key}">
                                                                <option value="${statement.key}" selected>${statement.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.statementProf != statement.key}">
                                                                <option value="${statement.key}" >${statement.value}</option>
                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>

                                            </tr> 
                                            <tr></tr>
                                            <tr>
                                                <td style="width: 200px;">

                                                </td>
                                                <td>
                                                    <input type="button" name="add"  class="defbutton" value="Add" onclick="invokeAdd('${templateBean.customerTemplateCode}')"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeReset(this.form)" value="Reset"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeCancel()" value="Cancel"/>
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


                                    </form>
                                </c:if>                 
                                <c:if test="${operationtype=='edit'}">   
                                    <form name="editform" method="post" action="<%=request.getContextPath()%>/UpdateAccountTempalteServlet?">                                       
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr>
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">
                                                    Template Code
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="tempaltcode"  maxlength="6" value="${templateBean.templateCode}" readonly="true"/></td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Template Name
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="templatename"  maxlength="64" value="${templateBean.templateName}"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Valid From</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="fromdate" maxlength="16" readonly value="${templateBean.valiedFrom}" key="fromdate" id="fromdate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#fromdate").datepicker({
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
                                                <td style="width: 200px;">Valid To</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="todate" maxlength="16" readonly value="${templateBean.valiedTo}" key="todate" id="todate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#todate").datepicker({
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
                                                <td style="width: 200px;">
                                                    Total Credit Limit
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="creditlimit"  maxlength="13" value="${templateBean.totalCreditLimit}"/>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Billing Cycle  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="bill" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:forEach var="bill" items="${billList}">
                                                            <c:if test="${templateBean.billCycle == bill.key}">
                                                                <option value="${bill.key}" selected>${bill.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.billCycle != bill.key}">
                                                                <option value="${bill.key}" >${bill.value}</option>
                                                            </c:if>
                                                        </c:forEach>                                               

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <c:if test="${templateBean.staff == 'YES'}">
                                                        <input type="radio" id="staff" name="staff" value="YES" checked="true" />Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'YES'}">
                                                        <input type="radio" id="staff" name="staff" value="YES" onchange="loadCustomerTemplatesUpdate('edit')"/>Staff

                                                    </c:if>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                    <c:if test="${templateBean.staff == 'NO'}">
                                                        <input type="radio" id="staff" name="staff" value="NO" checked="true"  />Non-Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'NO'}">
                                                        <input type="radio" id="staff" name="staff" value="NO" onchange="loadCustomerTemplatesUpdate('edit')"/>Non-Staff
                                                    </c:if>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Customer Template  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="cusTempCode" id="cusTemp"onchange="loadAccounRskProfOnUpdate(cusTemp.value, 'edit')" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:if test="${templateBean.customerTemplateCode != null}">
                                                            <c:forEach var="cusTemplateList" items="${cusTemplateList}">
                                                                <c:if test="${templateBean.customerTemplateCode == cusTemplateList.templateCode}">
                                                                    <option value="${cusTemplateList.templateCode}" selected="true">${cusTemplateList.templateName}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="cusTemplateList" items="${cusTemplateList}">
                                                            <c:if test="${templateBean.customerTemplateCode != cusTemplateList.templateCode}">
                                                                <option value="${cusTemplateList.templateCode}">${cusTemplateList.templateName}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">
                                                    Card Type  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="cardType" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:forEach var="cardType" items="${cardType}">
                                                            <c:if test="${templateBean.cardType == cardType.key}">
                                                                <option value="${cardType.key}" selected>${cardType.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.cardType != cardType.key}">
                                                                <option value="${cardType.key}" >${cardType.value}</option>
                                                            </c:if>
                                                        </c:forEach>                                                      

                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Currency Type  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="" style="width: 163px;" disabled="true">
                                                        <option value="">------SELECT------</option>
                                                        <c:if test="${templateBean.currencyCode != null}">
                                                            <c:forEach var="currencyList" items="${currencyList}">
                                                                <c:if test="${templateBean.currencyCode == currencyList.currencyCode}">
                                                                    <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="currencyList" items="${currencyList}">
                                                            <c:if test="${templateBean.currencyCode != currencyList.currencyCode}">
                                                                <option value="${currencyList.currencyCode}">${currencyList.currencyDes}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                                <td>
                                                    <c:forEach var="currencyList" items="${currencyList}">
                                                        <c:if test="${templateBean.currencyCode == currencyList.currencyCode}">
                                                            <input type="hidden" name="currency" value="${currencyList.currencyCode}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">
                                                    Interest Profile 
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="interestProf" style="width: 163px;">
                                                        <option value="">---------SELECT---------</option>
                                                        <c:forEach var="interest" items="${interestProf}">
                                                            <c:if test="${templateBean.interestprofileCode == interest.key}">
                                                                <option value="${interest.key}" selected>${interest.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.interestprofileCode != interest.key}">
                                                                <option value="${interest.key}" >${interest.value}</option>
                                                            </c:if>

                                                        </c:forEach>                                                       

                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">
                                                    Status  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="status" style="width: 163px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:if test="${templateBean.status != null}">
                                                            <c:forEach var="statusLst" items="${statusLst}">
                                                                <c:if test="${templateBean.status == statusLst.statusCode}">
                                                                    <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="statusLst" items="${statusLst}">
                                                            <c:if test="${templateBean.status != statusLst.statusCode}">
                                                                <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Account Risk Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="riskProf" style="width: 163px;">
                                                        <option value="">---------SELECT---------</option>
                                                        <c:forEach var="risk" items="${riskProfLst}">
                                                            <c:if test="${templateBean.accRskProf == risk.key}">
                                                                <option value="${risk.key}" selected>${risk.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.accRskProf != risk.key}">
                                                                <option value="${risk.key}" >${risk.value}</option>
                                                            </c:if>

                                                        </c:forEach> 
                                                    </select>
                                                </td>
                                                <td></td>
                                                <td style="width: 200px;">Transaction Profile</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select style="width: 163px;" name="txnProfile">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="txn" items="${sessionScope.SessionObject.transactionProfileList}">
                                                            <c:if test="${templateBean.txnProfCode==txn.profileCode}">
                                                                <option value="${txn.profileCode}" selected>${txn.description}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.txnProfCode!=txn.profileCode}">
                                                                <option value="${txn.profileCode}" >${txn.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">
                                                    Billing and Statement Profile 
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="statement" style="width: 163px;">
                                                        <option value="">---------SELECT---------</option>
                                                        <c:forEach var="statement" items="${statementList}">
                                                            <c:if test="${templateBean.statementProf == statement.key}">
                                                                <option value="${statement.key}" selected>${statement.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.statementProf != statement.key}">
                                                                <option value="${statement.key}" >${statement.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">

                                                </td>
                                                <td style="width: 325px;">
                                                    <input type="submit" name="add"  class="defbutton" value="Save"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeResetInUpdate('${templateBean.templateCode}', 'edit', '${templateBean.customerTemplateCode}')"value="Reset"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeCancel()" value="Cancel"/>
                                                </td>
                                            </tr>                                                    
                                        </table>

                                    </form>
                                </c:if> 
                                <c:if test="${operationtype == 'view'}">
                                    <form name="viewform">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table cellspacing="10" cellpadding="0">
                                            <tr>
                                                <td>Template Code</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.templateCode}</td>
                                            </tr>
                                            <tr>
                                                <td>Template Name</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.templateName}</td>
                                            </tr>
                                            <tr>
                                                <td>Valid From</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.valiedFrom}</td>
                                            </tr>
                                            <tr>
                                                <td>Valid To</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.valiedTo}</td>
                                            </tr>
                                            <tr>
                                                <td>Total Credit Limit</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.totalCreditLimit}</td>
                                            </tr>
                                            <tr>
                                                <td>Billing Cycle</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.billCycle}</td>
                                            </tr>
                                            <tr>
                                                <td>Staff Status</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.staff}</td>
                                            </tr>
                                            <tr>
                                                <td>Customer Template</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.customerTemplateDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Type</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.cardTypeDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Currency Type</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.currencyDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Interest Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.interestprofileDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Account Risk Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.accRskProfDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Transaction Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.txnProfDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Billing and Statement Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.statementProfDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.statusDes}</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeCancel()" value="Cancel"/>
                                                </td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>


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

