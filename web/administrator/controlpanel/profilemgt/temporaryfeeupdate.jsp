<%-- 
    Document   : temporaryfeeupdate
    Created on : Jun 7, 2012, 8:40:50 AM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            function feeResetBtn() {
                window.location = "${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode=" + feeCode + "&activity=" + activity + "&opType=" + opType + "&feeType=" + feeType;
            }

            function feeResetBtn1() {
                window.location = "${pageContext.request.contextPath}/TemporaryUpdateFeeServlet";
            }
            function backbtn(value) {
                window.location = "${pageContext.request.contextPath}/LoadFeeProfileAddServlet?remove=" + value + "&isDefault=no";
            }

            function backbtnInUpdate(value) {
                window.location = "${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?remove=" + value + "&isDefault=no";
            }
            function feeUpdateView(value) {
                window.location = "${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?id=" + value;
            }

            function invokeUpdate(feeCode, opType, feeType) {
                document.temporaryUpdateformADD.action = "${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode=" + feeCode + "&opType=" + opType + "&feeType=" + feeType;
                document.temporaryUpdateformADD.submit();

            }

            function invokeUpdateInUpdate(feeCode, opType, feeType) {
                document.temporaryUpdateformUPDATE.action = "${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode=" + feeCode + "&opType=" + opType + "&feeType=" + feeType;
                document.temporaryUpdateformUPDATE.submit();

            }
        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.FEEPROFILE%>'
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
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <!--add-->



                                <c:if test="${operationtype=='add'}">
                                    <form method="POST" name="temporaryUpdateformADD">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="hidden" name="operType"  value="add"  /></td>
                                                </tr>
                                                <tr>
                                                    <td>Fee Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="feeCode" maxlength="6" value="${sessionScope.SessionObject.realFeeBean.feeCode}" readonly="true" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="feeDes" maxlength="64" value="${sessionScope.SessionObject.realFeeBean.feeDes}" readonly="true"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Fee Category</td>
                                                    <td></td>
                                                    <td><input type="text" name="feeCategory" maxlength="64" value="${sessionScope.SessionObject.realFeeBean.feeCategory}" readonly="true"/>

                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>Fee Type</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="feeType" maxlength="64" value="${sessionScope.SessionObject.realFeeBean.feeType}" readonly="true"/>


                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Currency</td>
                                                    <td></td>
                                                    <td><select name="" disabled="true">
                                                            <option value="">--SELECT-- </option>
                                                            <c:if test="${sessionScope.SessionObject.realFeeBean.currency != null}">
                                                                <c:forEach var="currencyList" items="${currencyList}">
                                                                    <c:if test="${sessionScope.SessionObject.realFeeBean.currency == currencyList.currencyCode}">
                                                                        <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="currencyList" items="${currencyList}">
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.currency != currencyList.currencyCode}">
                                                                    <option value="${currencyList.currencyCode}">${currencyList.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.currency != null}">
                                                            <c:forEach var="currencyList" items="${currencyList}">
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.currency == currencyList.currencyCode}">
                                                                    <input type="hidden" value="${currencyList.currencyCode}" name="currency"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if> 
                                                    </td>
                                                    <td></td>
                                                    <td>

                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr == 'CR'}">
                                                            <input type="radio" name="crordr" value="CR" checked="true" on/>Credit
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr != 'CR'}">
                                                            <input type="radio" name="crordr" value="CR" />Credit
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr == 'DR'}">
                                                            <input type="radio" name="crordr" value="DR" checked="true"  />Debit
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr != 'DR'}">
                                                            <input type="radio" name="crordr" value="DR" />Debit
                                                        </c:if>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Flat Fee</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.flatFee}" name="flat" maxlength="13"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Percentage</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.percentage}" name="percentage" maxlength="6"/>&nbsp;<font><b>%</b></font></td>
                                                </tr>
                                                <tr>
                                                    <td>Option</td>
                                                    <td></td>
                                                    <td>

                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'ADD'}">
                                                            <input type="radio" name="option" value="ADD" checked="" on/>Add
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'ADD'}">
                                                            <input type="radio" name="option" value="ADD" />Add
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'MAX'}">
                                                            <input type="radio" name="option" value="MAX" checked=""  />Max
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'MAX'}">
                                                            <input type="radio" name="option" value="MAX" />Max
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'MIN'}">
                                                            <input type="radio" name="option" value="MIN" checked="" />Min
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'MIN'}">
                                                            <input type="radio" name="option" value="MIN" />Min
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'BOTH'}">
                                                            <input type="radio" name="option" value="BOTH" checked=""  />Both
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'BOTH'}">
                                                            <input type="radio" name="option" value="BOTH" />Both
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Min Amount</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.minAmount}" name="min" maxlength="13"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Max Amount</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.maxAmount}" name="max" maxlength="13"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td colspan="2">
                                                        <input type="submit" value="update" class="defbutton" name="Update" onclick="invokeUpdate('${sessionScope.SessionObject.realFeeBean.feeCode}', 'edit', '${sessionScope.SessionObject.realFeeBean.feeType}')"/>
                                                        <input type="reset" class="defbutton" onclick="feeResetBtn()" value="Reset" />
                                                        <input type="button" value="Back" class="defbutton" onclick="backbtn('cancel')"/>
                                                    </td>

                                                </tr>




                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}">
                                    <form method="POST" name="temporaryUpdateformUPDATE">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="hidden" name="operType"  value="update"  /></td>
                                                </tr>
                                                <tr>
                                                    <td>Fee Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="feeCode" maxlength="6" value="${sessionScope.SessionObject.realFeeBean.feeCode}" readonly="true" id="fc"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="feeDes" maxlength="64" value="${sessionScope.SessionObject.realFeeBean.feeDes}" readonly="true"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Fee Category</td>
                                                    <td></td>
                                                    <td><input type="text" name="feeCategory" maxlength="64" value="${sessionScope.SessionObject.realFeeBean.feeCategory}" readonly="true"/>

                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>Fee Type</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="feeType" maxlength="64" value="${sessionScope.SessionObject.realFeeBean.feeType}" readonly="true" id="ft"/>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Currency</td>
                                                    <td></td>
                                                    <td><select name="" disabled="true">
                                                            <option value="">--SELECT-- </option>
                                                            <c:if test="${sessionScope.SessionObject.realFeeBean.currency != null}">
                                                                <c:forEach var="currencyList" items="${currencyList}">
                                                                    <c:if test="${sessionScope.SessionObject.realFeeBean.currency == currencyList.currencyCode}">
                                                                        <option value="${currencyList.currencyCode}" selected="true">${currencyList.currencyDes}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="currencyList" items="${currencyList}">
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.currency != currencyList.currencyCode}">
                                                                    <option value="${currencyList.currencyCode}">${currencyList.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.currency != null}">
                                                            <c:forEach var="currencyList" items="${currencyList}">
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.currency == currencyList.currencyCode}">
                                                                    <input type="hidden" value="${currencyList.currencyCode}" name="currency"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>

                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr == 'CR'}">
                                                            <input type="radio" name="crordr" value="CR" checked="true" on/>Credit
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr != 'CR'}">
                                                            <input type="radio" name="crordr" value="CR" />Credit
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr == 'DR'}">
                                                            <input type="radio" name="crordr" value="DR" checked="true"  />Debit
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.crordr != 'DR'}">
                                                            <input type="radio" name="crordr" value="DR" />Debit
                                                        </c:if>
                                                    </td>
                                                </tr>                                     
                                                <tr>
                                                    <td>Flat Fee</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.flatFee}" name="flat" maxlength="13"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Percentage</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.percentage}" name="percentage" maxlength="6"/>&nbsp;<font><b>%</b></font></td>
                                                </tr>
                                                <tr>
                                                    <td>Option</td>
                                                    <td></td>
                                                    <td>

                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'ADD'}">
                                                            <input type="radio" name="option" value="ADD" checked="true" on/>Add
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'ADD'}">
                                                            <input type="radio" name="option" value="ADD" />Add
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'MAX'}">
                                                            <input type="radio" name="option" value="MAX" checked="true"  />Max
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'MAX'}">
                                                            <input type="radio" name="option" value="MAX" />Max
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'MIN'}">
                                                            <input type="radio" name="option" value="MIN" checked="true" on/>Min
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'MIN'}">
                                                            <input type="radio" name="option" value="MIN" />Min
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option == 'BOTH'}">
                                                            <input type="radio" name="option" value="BOTH" checked="true"  />Both
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.realFeeBean.option != 'BOTH'}">
                                                            <input type="radio" name="option" value="BOTH" />Both
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Min Amount</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.minAmount}" name="min" maxlength="13"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Max Amount</td>
                                                    <td></td>
                                                    <td><input type="text" value="${sessionScope.SessionObject.realFeeBean.maxAmount}" name="max" maxlength="13"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td colspan="2">
                                                        <input type="submit" value="update" class="defbutton" name="Update" onclick="invokeUpdateInUpdate('${sessionScope.SessionObject.realFeeBean.feeCode}', 'edit', '${sessionScope.SessionObject.realFeeBean.feeType}')"/>
                                                        <input type="reset" class="defbutton" onclick="feeResetBtn(fc.value, 'update', 'update', ft.value)" value="Reset" />
                                                        <input type="button" value="Back" class="defbutton" onclick="backbtnInUpdate('notcancel', '')"/></td>

                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>

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
