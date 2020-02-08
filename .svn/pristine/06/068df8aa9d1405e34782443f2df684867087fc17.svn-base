<%-- 
    Document   : templatemanagementhome
    Created on : Jan 24, 2012, 11:55:18 AM
    Author     : janaka_h
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title> TEMPLATE MANAGEMENT PAGE</title>
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <script language="javascript">
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
            function invokeCancel() {

                window.location = "${pageContext.request.contextPath}/LoadCustomerTempalteMgtServlet";
            }
            function invokeResetInUpdate(reset, id) {

                window.location = "${pageContext.request.contextPath}/ManageTemplateMgtServlet?operation=" + reset + "&templateCode=" + id;
            }
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CUSTEMPLATE%>'
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
    <body>
        <div class="container">
            <div class="header">               

            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <td class="acstable-menubar">
                        <jsp:include page="/leftmenu.jsp"/>
                    </td>

                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <c:if test="${operationtype=='add'}">

                                    <form name="createform" method="post" action="<%=request.getContextPath()%>/AddTemplateMgtServlet">
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
                                                    <input type="text" name="tempaltcode"  maxlength="6" value="${templateBean.templateCode}"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">
                                                    Template Name
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="templatename"  maxlength="64" value="${templateBean.templateName}"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Valid From</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="fromdate" maxlength="16" readonly value="${param.fromdate}" key="fromdate" id="fromdate"  />
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
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Valid To</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="todate" maxlength="16" readonly value="${param.todate}" key="todate" id="todate"  />
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
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">
                                                    Total Credit Limit
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="creditlimit"  maxlength="13" value="${templateBean.totalCreditLimit}"/></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <c:if test="${templateBean.staff == 'YES'}">
                                                        <input type="radio" name="staff" value="YES" checked="true" />Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'YES'}">
                                                        <input type="radio" name="staff" value="YES" />Staff

                                                    </c:if>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                    <c:if test="${templateBean.staff == 'NO'}">
                                                        <input type="radio" name="staff" value="NO" checked="true"  />Non-Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'NO'}">
                                                        <input type="radio" name="staff" value="NO" />Non-Staff

                                                    </c:if>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Customer Risk Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="riskProf" style="width: 160px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:forEach var="riskprof" items="${riskProfLst}">
                                                            <c:if test="${templateBean.cusRiskProfile == riskprof.key}">
                                                                <option value="${riskprof.key}" selected>${riskprof.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.cusRiskProfile != riskprof.key}">
                                                                <option value="${riskprof.key}" >${riskprof.value}</option>
                                                            </c:if>

                                                        </c:forEach> 
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
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
                                                    Currency Type  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="currency" style="width: 160px;">
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
                                                    Status  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="status" style="width: 160px;">
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
                                                <td style="width: 200px;">

                                                </td>
                                                <td>
                                                    &nbsp;&nbsp;
                                                    <input type="submit" name="add"  class="defbutton" value="Add"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeReset(this.form)" value="Reset"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeCancel()" value="Cancel"/></td>
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

                                    <form name="editform" method="post" action="<%=request.getContextPath()%>/UpdateTemplateMgtServlet">
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
                                                <td ><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" readonly="true" name="tempaltcode"  maxlength="16" value="${templateBean.templateCode}"/></td>
                                            </tr>

                                            <tr>
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
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
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
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">
                                                    Total Credit Limit
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="creditlimit"  maxlength="13" value="${templateBean.totalCreditLimit}"/></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <c:if test="${templateBean.staff == 'YES'}">
                                                        <input type="radio" name="staff" value="YES" checked="true" />Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'YES'}">
                                                        <input type="radio" name="staff" value="YES" />Staff

                                                    </c:if>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                    <c:if test="${templateBean.staff == 'NO'}">
                                                        <input type="radio" name="staff" value="NO" checked="true"  />Non-Staff

                                                    </c:if>
                                                    <c:if test="${templateBean.staff != 'NO'}">
                                                        <input type="radio" name="staff" value="NO" />Non-Staff

                                                    </c:if>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Customer Risk Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="riskProf" style="width: 160px;">
                                                        <option value="">------SELECT------</option>
                                                        <c:forEach var="riskprof" items="${riskProfLst}">
                                                            <c:if test="${templateBean.cusRiskProfile == riskprof.key}">
                                                                <option value="${riskprof.key}" selected>${riskprof.value}</option>
                                                            </c:if>
                                                            <c:if test="${templateBean.cusRiskProfile != riskprof.key}">
                                                                <option value="${riskprof.key}" >${riskprof.value}</option>
                                                            </c:if>

                                                        </c:forEach> 
                                                    </select></td>
                                            </tr>
                                            <tr>
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
                                                    Currency Type  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="" style="width: 160px;" disabled="true">
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
                                                    <c:if test="${templateBean.currencyCode != null}">
                                                        <c:forEach var="currencyList" items="${currencyList}">
                                                            <c:if test="${templateBean.currencyCode == currencyList.currencyCode}">
                                                                <input type="hidden" name="currency" value="${currencyList.currencyCode}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if> 
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">
                                                    Status  
                                                </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="status" style="width: 160px;">
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
                                                <td style="width: 200px;">

                                                </td>
                                                <td>&nbsp;&nbsp;
                                                    <input type="submit" name="save"  class="defbutton" value="Update"/>
                                                    <input type="button" name="reset"  class="defbutton" onclick="invokeResetInUpdate('edit', '${templateBean.templateCode}')" value="Reset"/>
                                                    <input type="button" name="cancel"  class="defbutton" onclick="invokeCancel()" value="Cancel"/>
                                                </td>
                                            </tr>

                                        </table>
                                    </form>  
                                </c:if>

                                <c:if test="${operationtype=='view'}">
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
                                                <td>Staff Status</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.staff}</td>
                                            </tr>
                                            <tr>
                                                <td>Customer Risk Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.cusRiskProfDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Transaction Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.txnProfDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Currency Type</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.currencyDes}</td>
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
                                                    <input type="button" name="cancel"  class="defbutton" onclick="invokeCancel()" value="Cancel"/>
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
