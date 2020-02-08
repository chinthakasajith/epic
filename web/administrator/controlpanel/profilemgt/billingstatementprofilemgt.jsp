<%-- 
    Document   : billingstatementprofilemgt
    Created on : Jun 12, 2012, 5:17:03 PM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">

            function enableConditioned(value) {
                //   alert(value);
                if (value == 'COND') {

                    $(".field").attr("disabled", false);

                } else if (value == 'ALWAYS') {

                    $(".field").attr("disabled", true);
                }
            }

            function invokeReset() {
                window.location = "${pageContext.request.contextPath}/LoadBillingStatementServlet";
            }
            function invokeAdd()
            {

                document.addbillingstatementform.action = "${pageContext.request.contextPath}/AddBillingStatementServlet";
                document.addbillingstatementform.submit();

            }

            function invokeView(value) {
                window.location = "${pageContext.request.contextPath}/ViewBillingStatementServlet?id=" + value;
            }

            function invokeUpdate(value) {
                document.viewTableForm.action = "${pageContext.request.contextPath}/UpdateBillingStatementFormServlet";
                document.getElementById('id').value = value;
                document.viewTableForm.submit();
            }

            function invokeDelete(value) {

//                answer = confirm("Do you really want to delete "+value+" Billing Statement Profile?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteBillingStatementServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadBillingStatementServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + value + " Billing Statement Profile?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadBillingStatementServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteBillingStatementServlet?id=" + value;
                        }
                    }
                });

            }




        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.BILLINGSTATEMENTPROFILE%>'
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

                    <jsp:include page="/leftmenu.jsp"/>

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


                                <%--  start add form --%>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addbillingstatementform">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Profile Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="8" name="profileCode" value="${billingStmtProfbean.profileCode}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="64" value="${billingStmtProfbean.description}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Grace Period </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="10" name="gracePeroid" value="${billingStmtProfbean.gracePeroid}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Minimum Due: </td>
                                                    <td></td>
                                                    <td>Flat
                                                        <font style="color: red;">*</font>&nbsp;<input type="text" maxlength="11" name="flat" value="${billingStmtProfbean.minimumDueFlatAmount}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>Percentage
                                                        <font style="color: red;">*</font>&nbsp;<input type="text" maxlength="5" name="percentage" value="${billingStmtProfbean.minimumDuePercentage}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination=='COM'}">
                                                            <input type="radio" name="combination" value="COM" checked="true"/> Combine 
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination!='COM'}">
                                                            <input type="radio" name="combination" value="COM" /> Combine
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination=='MIN'}">
                                                            <input type="radio" name="combination" value="MIN" checked="true"/> Min 
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination!='MIN'}">
                                                            <input type="radio" name="combination" value="MIN" /> Min
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination=='MAX'}">
                                                            <input type="radio" name="combination" value="MAX" checked="true"/> Max 
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination!='MAX'}">
                                                            <input type="radio" name="combination" value="MAX" /> Max
                                                        </c:if>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Statement </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>

                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus=='ALWAYS'}">
                                                            <input type="radio" name="statement" value="ALWAYS" id="stat1" onchange="enableConditioned(stat1.value)" checked="true"/> Print Always
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus!='ALWAYS'}">
                                                            <input type="radio" name="statement" value="ALWAYS" id="stat1" onchange="enableConditioned(stat1.value)"/> Print Always
                                                        </c:if>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus=='COND'}">
                                                            <input type="radio" name="statement" value="COND" id="stat" onchange="enableConditioned(stat.value)" checked="true"/> Print Conditioned
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus!='COND'}">
                                                            <input type="radio" name="statement" value="COND" id="stat" onchange="enableConditioned(stat.value)"/> Print Conditioned
                                                        </c:if>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr><td></td>
                                                    <td></td>

                                                    <c:if test="${billingStmtProfbean.statementGenerationStatus!='COND'}">
                                                        <td style="width: 400px;"><fieldset id="fieldst"><legend>Conditioned</legend>
                                                                <br/>
                                                                Balance 
                                                                <input type="text" name="balance" maxlength="11" value="${billingStmtProfbean.conditionalBalance}" id="balance" class="field" disabled=""/>
                                                                <select name="CrOrDr" id="CrOrDr" class="field" disabled="">
                                                                    <option value="" selected>--SELECT--</option>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='CR'}">
                                                                        <option value="CR" selected="">CREDIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='CR'}">
                                                                        <option value="CR" >CREDIT</option>

                                                                    </c:if>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='DR'}">
                                                                        <option value="DR" selected="">DEBIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='DR'}">
                                                                        <option value="DR" >DEBIT</option>

                                                                    </c:if>

                                                                </select> 
                                                                <br/><br/>
                                                                Activity
                                                                <input type="text" name="activity" maxlength="10" value="${billingStmtProfbean.numberOfActivity}" id="activity" class="field" disabled=""/>
                                                                <br/><br/>

                                                            </fieldset>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${billingStmtProfbean.statementGenerationStatus=='COND'}">
                                                        <td style="width: 400px;"><fieldset id="fieldst"><legend>Conditioned</legend>
                                                                <br/>
                                                                Balance 
                                                                <input type="text" name="balance" maxlength="11" value="${billingStmtProfbean.conditionalBalance}" id="balance" class="field" />
                                                                <select name="CrOrDr" id="CrOrDr" class="field" >
                                                                    <option value="" selected>--SELECT--</option>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='CR'}">
                                                                        <option value="CR" selected="">CREDIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='CR'}">
                                                                        <option value="CR" >CREDIT</option>

                                                                    </c:if>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='DR'}">
                                                                        <option value="DR" selected="">DEBIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='DR'}">
                                                                        <option value="DR" >DEBIT</option>

                                                                    </c:if>

                                                                </select> 
                                                                <br/><br/>
                                                                Activity
                                                                <input type="text" name="activity" maxlength="10" value="${billingStmtProfbean.numberOfActivity}" id="activity" class="field" />
                                                                <br/><br/>

                                                            </fieldset>
                                                        </td>

                                                    </c:if>



                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select  name="statuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${billingStmtProfbean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${billingStmtProfbean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 15px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Add" name="Add" onclick="invokeAdd()" class="defbutton"/>
                                                        <input onclick="invokeReset()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <%--  start update form --%>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateBillingStatementServlet" name="updatebillingstatementform">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Profile Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="8" name="profileCode" value="${billingStmtProfbean.profileCode}" readonly="true"/></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="64" value="${billingStmtProfbean.description}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Grace Period </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="10" name="gracePeroid" value="${billingStmtProfbean.gracePeroid}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Minimum Due: </td>
                                                    <td></td>
                                                    <td>Flat
                                                        <font style="color: red;">*</font>&nbsp;<input type="text" maxlength="11" name="flat" value="${billingStmtProfbean.minimumDueFlatAmount}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>Percentage
                                                        <font style="color: red;">*</font>&nbsp;<input type="text" maxlength="5" name="percentage" value="${billingStmtProfbean.minimumDuePercentage}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination=='COM'}">
                                                            <input type="radio" name="combination" value="COM" checked="true"/> Combine 
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination!='COM'}">
                                                            <input type="radio" name="combination" value="COM" /> Combine
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination=='MIN'}">
                                                            <input type="radio" name="combination" value="MIN" checked="true"/> Min 
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination!='MIN'}">
                                                            <input type="radio" name="combination" value="MIN" /> Min
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination=='MAX'}">
                                                            <input type="radio" name="combination" value="MAX" checked="true"/> Max 
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.minimumDueCombination!='MAX'}">
                                                            <input type="radio" name="combination" value="MAX" /> Max
                                                        </c:if>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Statement </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>

                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus=='ALWAYS'}">
                                                            <input type="radio" name="statement" value="ALWAYS" id="stat1" onchange="enableConditioned(stat1.value)" checked="true"/> Print Always
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus!='ALWAYS'}">
                                                            <input type="radio" name="statement" value="ALWAYS" id="stat1" onchange="enableConditioned(stat1.value)"/> Print Always
                                                        </c:if>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus=='COND'}">
                                                            <input type="radio" name="statement" value="COND" id="stat" onchange="enableConditioned(stat.value)" checked="true"/> Print Conditioned
                                                        </c:if>

                                                        <c:if test="${billingStmtProfbean.statementGenerationStatus!='COND'}">
                                                            <input type="radio" name="statement" value="COND" id="stat" onchange="enableConditioned(stat.value)"/> Print Conditioned
                                                        </c:if>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr><td></td>
                                                    <td></td>

                                                    <c:if test="${billingStmtProfbean.statementGenerationStatus=='COND'}">
                                                        <td style="width: 400px;"><fieldset id="fieldst" ><legend>Conditioned</legend>
                                                                <br/>
                                                                Balance 

                                                                <input type="text" name="balance" id="balance" maxlength="11" value="${billingStmtProfbean.conditionalBalance}" class="field" />

                                                                <select name="CrOrDr" id="CrOrDr" class="field" >
                                                                    <option value="" selected>--SELECT--</option>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='CR'}">
                                                                        <option value="CR" selected="">CREDIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='CR'}">
                                                                        <option value="CR" >CREDIT</option>

                                                                    </c:if>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='DR'}">
                                                                        <option value="DR" selected="">DEBIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='DR'}">
                                                                        <option value="DR" >DEBIT</option>

                                                                    </c:if>

                                                                </select> 
                                                                <br/><br/>
                                                                Activity
                                                                <input type="text" name="activity" maxlength="10" value="${billingStmtProfbean.numberOfActivity}" id="activity" class="field" />
                                                                <br/><br/>

                                                            </fieldset>
                                                        </td>

                                                    </c:if>

                                                    <c:if test="${billingStmtProfbean.statementGenerationStatus!='COND'}">
                                                        <td style="width: 400px;"><fieldset id="fieldst" ><legend>Conditioned</legend>
                                                                <br/>
                                                                Balance 
                                                                <input type="text" name="balance" maxlength="11" value="${billingStmtProfbean.conditionalBalance}" class="field" disabled=""/>
                                                                <select name="CrOrDr" class="field" disabled="">
                                                                    <option value="" selected>--SELECT--</option>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='CR'}">
                                                                        <option value="CR" selected="">CREDIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='CR'}">
                                                                        <option value="CR" >CREDIT</option>

                                                                    </c:if>

                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr=='DR'}">
                                                                        <option value="DR" selected="">DEBIT</option>
                                                                    </c:if>
                                                                    <c:if test="${billingStmtProfbean.conditionalCrOrDr!='DR'}">
                                                                        <option value="DR" >DEBIT</option>

                                                                    </c:if>

                                                                </select> 
                                                                <br/><br/>
                                                                Activity
                                                                <input type="text" name="activity" maxlength="10" value="${billingStmtProfbean.numberOfActivity}" class="field" disabled=""/>
                                                                <br/><br/>

                                                            </fieldset>
                                                        </td>
                                                    </c:if>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select  name="statuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${billingStmtProfbean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${billingStmtProfbean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 15px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Update" name="Update" onclick="invokeUpdate('${billingStmtProfbean.profileCode}')" class="defbutton"/>
                                                        <input onclick="invokeUpdate('${billingStmtProfbean.profileCode}')" type="button" value="Reset" class="defbutton"/>
                                                        <input onclick="invokeReset()" type="reset" value="Back" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>




                                <%-- start view form   --%>

                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewform">


                                        <table border="0">

                                            <tr>
                                                <td>Profile Code</td>
                                                <td>:</td>
                                                <td>${billingStmtbean.profileCode}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>


                                            <tr>
                                                <td>Description</td>
                                                <td>:</td>
                                                <td>${billingStmtbean.description}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Grace Period</td>
                                                <td>:</td>
                                                <td>${billingStmtbean.gracePeroid}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Minimum Due Flat Amount</td>
                                                <td>:</td>
                                                <td>${billingStmtbean.minimumDueFlatAmount}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Minimum Due Percentage</td>
                                                <td>:</td>
                                                <td>${billingStmtbean.minimumDuePercentage}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>



                                            <tr>
                                                <td>Minimum Due Combination </td>
                                                <td>:</td>
                                                <td>${billingStmtbean.minimumDueCombination}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Statement Generation Status </td>
                                                <td>:</td>
                                                <td>${billingStmtbean.statementGenerationStatus}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>


                                            <tr>
                                                <td>Conditional Balance </td>
                                                <td>:</td>
                                                <td>${billingStmtbean.conditionalBalance}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Conditional Credit Or Debit </td>
                                                <td>:</td>
                                                <td>${billingStmtbean.conditionalCrOrDr}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Number Of Activity </td>
                                                <td>:</td>
                                                <td>${billingStmtbean.numberOfActivity}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Status </td>
                                                <td>:</td>
                                                <td>${billingStmtbean.statusDes}</td> 
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>

                                                <td style="width: 300px;">
                                                    <input type="button" value="Back" name="Back" class="defbutton" onclick="invokeReset()"/>
                                                </td>


                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>
                                    </form>

                                </c:if>



                                <%-- show table data --%>
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>Profile Code</th>
                                                <th>Description</th>

                                                <th>Grace Period</th>
                                                <th>Minimum Due Flat Amount</th>
                                                <th>Minimum Due %</th>
                                                <th>Minimum Due Combination</th>
                                                <th>Statement Generation Status</th>                                                                                   
                                                <th>Status</th>

                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${billingStatementList}" var="list">
                                                <tr>
                                                    <td>${list.profileCode}</td>
                                                    <td>${list.description}</td>

                                                    <td>${list.gracePeroid}</td>
                                                    <td>${list.minimumDueFlatAmount}</td>
                                                    <td>${list.minimumDuePercentage}</td>
                                                    <td>${list.minimumDueCombination}</td>
                                                    <td>${list.statementGenerationStatus}</td>                                                    
                                                    <td>${list.statusDes}</td>


                                                    <td><a  href='#' onclick="invokeView('${list.profileCode}')">View</a></td>
                                                    <td><a  href='#' onclick="invokeUpdate('${list.profileCode}')">Update</a></td>
                                                    <td><a  href='#' onclick="invokeDelete('${list.profileCode}')">Delete</a></td>


                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
                                    </table>

                                </form>




                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
                                  <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
