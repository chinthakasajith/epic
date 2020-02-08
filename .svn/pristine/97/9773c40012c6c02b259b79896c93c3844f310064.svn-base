<%-- 
    Document   : feemgt
    Created on : Jun 7, 2012, 3:31:00 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />



        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";
        </style>

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>

        <title>EPIC_CMS_HOME</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addFeeForm.action = "${pageContext.request.contextPath}/AddFeeMgtServlet";
                document.addFeeForm.submit();

            }
            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadFeeMgtServlet";

            }
            function invokeUpdateCancel() {

                window.location = "${pageContext.request.contextPath}/LoadFeeMgtServlet";

            }

            function invokeUpdate(feeCode)
            {
                window.location = "${pageContext.request.contextPath}/LordUpdateFeeMgtServlet?feeCode=" + feeCode;


            }
            function invokeUpdateReset(feeCode)
            {
                window.location = "${pageContext.request.contextPath}/LordUpdateFeeMgtServlet?feeCode=" + feeCode;


            }

            function invokeUpdateConfiremd()
            {

                document.updatefeeForm.action = "${pageContext.request.contextPath}/UpdateFeeMgtServlet";
                document.updatefeeForm.submit();

            }

            function invokeView(feeCode)
            {

                window.location = "${pageContext.request.contextPath}/ViewFeeMgtServlet?feeCode=" + feeCode;


            }

            function ConfirmDelete(feeCode)
            {
                answer = confirm("Are you sure you want to delete this channel?")
                if (answer != 0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteFeeMgtServlet?feeCode=" + feeCode;
                }
                else
                {
                    window.location = "${pageContext.request.contextPath}/LoadFeeMgtServlet";
                }

            }
            function invokeBack() {

                window.location = "${pageContext.request.contextPath}/LoadFeeMgtServlet";

            }



        </script>   
        <script>


            $(document).ready(function () {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.FEEMGT%>'
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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                <c:if test="${operationtype=='ADD'}">
                                    <form action="" method="POST" name="addFeeForm" >

                                        <table cellpadding="0" cellspacing="10" >


                                            <tbody>

                                                <tr>
                                                    <td>Fee Name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="feeCode"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="fee" items="${sessionScope.SessionObject.feeTypeList}">

                                                                <c:if test="${feeBean.feeCode==fee.feeTypeCode}">
                                                                    <option value="${fee.feeTypeCode}" selected="true" >${fee.description}</option>
                                                                </c:if>
                                                                <c:if test="${feeBean.feeCode!=fee.feeTypeCode}">
                                                                    <option value="${fee.feeTypeCode}">${fee.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <!--                                                <tr>
                                                                                                    <td>Description</td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="feeDes" class="inputfield-Description-mandatory" maxlength="64" value='${feeBean.feeDes}'></td>
                                                                                                </tr>-->

                                                <tr>
                                                    <td width ="200px;">Category</td>

                                                    <td><font style="color: red;">*</font>&nbsp; <c:if test="${feeBean.feeCategory == 'MER'}">
                                                            <input type="radio" name="feeCategory" checked="true" value="MER" /> Merchant 
                                                        </c:if>

                                                        <c:if test="${feeBean.feeCategory != 'MER'}">
                                                            <input type="radio" name="feeCategory" checked="true"  value="MER" /> Merchant 

                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


                                                        <c:if test="${feeBean.feeCategory == 'CRD'}">
                                                            <input type="radio" name="feeCategory" checked="true"  value="CRD" /> Card
                                                        </c:if>

                                                        <c:if test="${feeBean.feeCategory != 'CRD'}">
                                                            <input type="radio" name="feeCategory"   value="CRD" /> Card

                                                        </c:if>
                                                    </td> 


                                                </tr>

                                                <tr>
                                                    <td width ="200px;">Fee Type</td>

                                                    <td><font style="color: red;">*</font>&nbsp; <c:if test="${feeBean.feeType == 'TXN'}">
                                                            <input type="radio" name="feeType" checked="true" value="TXN" /> Transaction 
                                                        </c:if>

                                                        <c:if test="${feeBean.feeType != 'TXN'}">
                                                            <input type="radio" name="feeType" checked="true"  value="TXN" /> Transaction

                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.feeType == 'SER'}">
                                                            <input type="radio" name="feeType" checked="true" value="SER" /> Service
                                                        </c:if>

                                                        <c:if test="${feeBean.feeType != 'SER'}">
                                                            <input type="radio" name="feeType"   value="SER" /> Service

                                                        </c:if>

                                                    </td> 

                                                <tr>
                                                    <td>Currency</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectCurrency"   class="inputfield-mandatory">
                                                            <!--<option value="" selected>--SELECT--</option>-->
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">

                                                                <c:if test="${feeBean.currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${feeBean.currency!=curr.currencyCode}">
                                                                    <c:if test="${curr.currencyCode eq '144'}">
                                                                        <option value="${curr.currencyCode}" selected="true">${curr.currencyDes}</option>
                                                                    </c:if>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>

                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>

                                                    <td><font style="color: red;">*</font>&nbsp;<c:if test="${feeBean.crordr == 'CR'}">
                                                            <input type="radio" name="crordr" checked="true" value="CR" /> Credit 
                                                        </c:if>


                                                        <c:if test="${feeBean.crordr != 'CR'}">
                                                            <input type="radio" name="crordr" checked="true"  value="CR" /> Credit

                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.crordr == 'DR'}">
                                                            <input type="radio" name="crordr" checked="true"  value="DR" /> Debit
                                                        </c:if>

                                                        <c:if test="${feeBean.crordr != 'DR'}">
                                                            <input type="radio" name="crordr"   value="DR" /> Debit

                                                        </c:if>
                                                    </td> 

                                                <tr>
                                                    <td>Flat Fee</td>
                                                    <td><font style="color: white;">*</font>&nbsp;<input type="text" name="flatFee" class="inputfield-mandatory" maxlength="11" value='${feeBean.flatFee}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td><font style="color: white;">*</font>&nbsp;<input type="text" name="percentage" class="inputfield-mandatory" maxlength="5" value='${feeBean.percentage}'> %</td>

                                                </tr>
                                                <tr>
                                                    <td width ="200px;"></td>

                                                    <td><font style="color: red;">*</font>&nbsp;<c:if test="${feeBean.option == 'MIN'}">
                                                            <input type="radio" name="option" checked="true" value="MIN" /> Minimum
                                                        </c:if>

                                                        <c:if test="${feeBean.option != 'MIN'}">
                                                            <input type="radio" name="option" checked="true"  value="MIN" /> Minimum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.option == 'MAX'}">
                                                            <input type="radio" name="option"  checked="true" value="MAX" /> Maximum 
                                                        </c:if>

                                                        <c:if test="${feeBean.option != 'MAX'}">
                                                            <input type="radio" name="option"   value="MAX" /> Maximum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.option == 'CMB'}">
                                                            <input type="radio" name="option" checked="true"  value="CMB" /> Combine
                                                        </c:if>

                                                        <c:if test="${feeBean.option != 'CMB'}">
                                                            <input type="radio" name="option"  value="CMB" /> Combine
                                                        </c:if>


                                                    </td> 

                                                </tr>
                                                <tr>
                                                    <td>Min Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="minAmount" class="inputfield-mandatory" maxlength="11" value='${feeBean.minAmount}'></td>
                                                </tr>
                                                <tr>
                                                    <td>Max Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxAmount" class="inputfield-mandatory" maxlength="11" value='${feeBean.maxAmount}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectStatusCode"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${feeBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${feeBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <!--                                            </tbody>
                                                                                        </table>
                                                
                                                                                        <table>
                                                                                            <tbody>-->
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="add" value="Add" onclick="invokeAdd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset()"/></td>

                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->

                                <c:if test="${operationtype=='VIEW'}" >
                                    <form action="" method="POST" name="viewFeeForm">



                                        <table cellpadding="0" cellspacing="10" >
                                            <tbody>
                                                <tr>
                                                    <td>Fee Name</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.feeDes}</td>
                                                </tr>

                                                <!--                                                <tr>
                                                                                                    <td>Fee Type Code</td>
                                                                                                    <td></td>
                                                                                                    <td> : </td>
                                                                                                    <td></td>
                                                                                                    <td>${feeBean.feeCode}</td>
                                                                                                </tr>-->

                                                <tr>
                                                    <td>Category</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td> <c:if test= "${feeBean.feeCategory == 'MER' }">Merchant </c:if> <c:if test= "${feeBean.feeCategory == 'CRD'}">Card </c:if></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Fee Type</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                            <td> <c:if test= "${feeBean.feeType == 'TXN'}">Transaction  </c:if> <c:if test= "${feeBean.feeType == 'SER'}">Service </c:if></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Currency</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                            <td>${feeBean.currencyDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Credit Or Debit </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td> <c:if test= "${feeBean.crordr == 'CR'}">Credit  </c:if> <c:if test= "${feeBean.crordr == 'DR'}">Debit </c:if></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Flat Fee</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                            <td>${feeBean.flatFee}</td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.percentage} %</td>

                                                </tr>

                                                <tr>
                                                    <td>Option</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td> <c:if test= "${feeBean.option == 'MIN'}">Minimum  </c:if> <c:if test= "${feeBean.option == 'MAX'}">Maximum </c:if><c:if test= "${feeBean.option == 'CMB'}">Combine </c:if></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Minimum Amount</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                                <td>${feeBean.minAmount}</td>
                                                </tr>

                                                <tr>
                                                    <td>Maximum Amount</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.maxAmount}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Last Updated User</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.lastUpdateUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Last Updated Time</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.lastUpdateDate}</td>
                                                </tr>
                                                <tr>
                                                    <td>Created Time</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${feeBean.createDate}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="reset" style="width: 100px" name="reset" value="Back" onclick="invokeBack()"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        


                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='UPDATE'}" >
                                    <form method="POST" action="" name="updatefeeForm">


                                        <table cellpadding="0" cellspacing="10" >


                                            <tbody>

                                                <tr>
                                                    <td>Fee Name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="feeCode"   class="inputfield-mandatory">

                                                            <option value="${feeBean.feeCode}" selected="true" >${feeBean.feeDes}</option>

                                                        </select><input type="hidden" name="feeDes" hidden="" class="inputfield-mandatory" maxlength="6" value='${feeBean.feeDes}'>
                                                    </td>
                                                </tr>

                                                <!--                                                <tr>
                                                                                                    <td>Description</td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="feeDes" class="inputfield-Description-mandatory" maxlength="64" value='${feeBean.feeDes}'></td>
                                                                                                </tr>-->

                                                <tr>
                                                    <td width ="200px;">Category</td>

                                                    <td><font style="color: red;">*</font>&nbsp; <c:if test="${feeBean.feeCategory == 'MER'}">
                                                            <input type="radio" name="feeCategory" checked="true" value="MER" /> Merchant 
                                                        </c:if>
                                                        <c:if test="${feeBean.feeCategory != 'MER'}">
                                                            <input type="radio" name="feeCategory" checked="true" value="MER" /> Merchant 

                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${feeBean.feeCategory == 'CRD'}">
                                                            <input type="radio" name="feeCategory" checked="true"  value="CRD" /> Card
                                                        </c:if>
                                                        <c:if test="${feeBean.feeCategory != 'CRD'}">
                                                            <input type="radio" name="feeCategory"   value="CRD" /> Card

                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">Fee Type</td>


                                                    <td><font style="color: red;">*</font>&nbsp; <c:if test="${feeBean.feeType == 'TXN'}">
                                                            <input type="radio" name="feeType" checked="true" value="TXN" /> Transaction 
                                                        </c:if>

                                                        <c:if test="${feeBean.feeType != 'TXN'}">
                                                            <input type="radio" name="feeType" checked="true"  value="TXN" /> Transaction

                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.feeType == 'SER'}">
                                                            <input type="radio" name="feeType" checked="true" value="SER" /> Service
                                                        </c:if>

                                                        <c:if test="${feeBean.feeType != 'SER'}">
                                                            <input type="radio" name="feeType"   value="SER" /> Service

                                                        </c:if>

                                                    </td> 

                                                <tr>
                                                    <td>Currency</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name=""  class="inputfield-mandatory" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">

                                                                <c:if test="${feeBean.currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${feeBean.currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                        <td>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${feeBean.currency==curr.currencyCode}">
                                                                    <input type="hidden" value="${curr.currencyCode}" name="selectCurrency" />
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td width ="200px;"></td>

                                                    <td><font style="color: red;">*</font>&nbsp;<c:if test="${feeBean.crordr == 'CR'}">
                                                            <input type="radio" name="crordr" checked="true" value="CR" /> Credit 
                                                        </c:if>


                                                        <c:if test="${feeBean.crordr != 'CR'}">
                                                            <input type="radio" name="crordr" checked="true"  value="CR" /> Credit

                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.crordr == 'DR'}">
                                                            <input type="radio" name="crordr" checked="true"  value="DR" /> Debit
                                                        </c:if>

                                                        <c:if test="${feeBean.crordr != 'DR'}">
                                                            <input type="radio" name="crordr"   value="DR" /> Debit

                                                        </c:if>
                                                    </td> 

                                                <tr>
                                                    <td>Flat Fee</td>
                                                    <td><font style="color: white;">&nbsp; </font>&nbsp;<input type="text" name="flatFee" class="inputfield-mandatory" maxlength="11" value='${feeBean.flatFee}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td><font style="color: white;">&nbsp; </font>&nbsp;<input type="text" name="percentage" class="inputfield-mandatory" maxlength="6" value='${feeBean.percentage}'> %</td>

                                                </tr>
                                                <tr>
                                                    <td width ="200px;"></td>

                                                    <td><font style="color: red;">*</font>&nbsp;<c:if test="${feeBean.option == 'MIN'}">
                                                            <input type="radio" name="option" checked="true" value="MIN" /> Minimum
                                                        </c:if>

                                                        <c:if test="${feeBean.option != 'MIN'}">
                                                            <input type="radio" name="option" checked="true"  value="MIN" /> Minimum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.option == 'MAX'}">
                                                            <input type="radio" name="option"  checked="true" value="MAX" /> Maximum 
                                                        </c:if>

                                                        <c:if test="${feeBean.option != 'MAX'}">
                                                            <input type="radio" name="option"   value="MAX" /> Maximum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${feeBean.option == 'CMB'}">
                                                            <input type="radio" name="option" checked="true"  value="CMB" /> Combine
                                                        </c:if>

                                                        <c:if test="${feeBean.option != 'CMB'}">
                                                            <input type="radio" name="option"  value="CMB" /> Combine
                                                        </c:if>


                                                    </td> 

                                                </tr>
                                                <tr>
                                                    <td>Min Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="minAmount" class="inputfield-mandatory" maxlength="11" value='${feeBean.minAmount}'></td>
                                                </tr>
                                                <tr>
                                                    <td>Max Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxAmount" class="inputfield-mandatory" maxlength="11" value='${feeBean.maxAmount}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select name="selectStatusCode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${feeBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${feeBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>  
                                                    <td><input type="hidden" name="oldValue" value='${feeBean.oldValue}' hidden="hidden"></td>
                                                </tr>
                                                <!--                                            </tbody>
                                                                                        </table>
                                                
                                                                                        <table>
                                                                                            <tbody>-->
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${fee.feeCode}')"/>
                                                        <input type="button" style="width: 100px" name="cancel" value="Cancel" onclick="invokeUpdateCancel()"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Fee Code</th>
                                            <th>Description</th>
                                            <th>Category</th>
                                            <th>Fee Type</th>
                                            <th>Currency</th>
                                            <th>Status</th>



                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="fee" items="${feeList}">
                                            <tr>

                                                <td >${fee.feeCode}</td>
                                                <td >${fee.feeDes}</td>
                                                <td ><c:if test= "${fee.feeCategory == 'MER'}">Merchant </c:if> <c:if test= "${fee.feeCategory == 'CRD'}">Card </c:if></td>
                                                <td ><c:if test= "${fee.feeType == 'TXN'}">Transaction  </c:if> <c:if test= "${fee.feeType == 'SER'}">Service </c:if></td>
                                                <td >${fee.currencyDes}</td>
                                                <td >${fee.statusDes}</td>




                                                <td  ><a href='#' onclick="invokeView('${fee.feeCode}')">View</a></td>

                                                <td  ><a href='#' onclick="invokeUpdate('${fee.feeCode}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${fee.feeCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>     


                                <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////-->



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