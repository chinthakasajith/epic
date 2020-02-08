<%-- 
    Document   : billingcyclemgt
    Created on : May 9, 2012, 2:08:59 PM
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

            function backToLoad() {
                window.location = "${pageContext.request.contextPath}/LoadBillingCycleMgtServlet";
            }

            function deleteBillingCycle(value) {

//                answer = confirm("Do you really want to delete "+value+" billing cycle?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteBillingCycleMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadBillingCycleMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + value + " billing cycle?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadBillingCycleMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteBillingCycleMgtServlet?id=" + value;
                        }
                    }
                });

            }

            function backToLoadUpdate(billCode) {
                document.updateBillCycle.action = "${pageContext.request.contextPath}/LoadUpdateBillingCycleMgtServlet?id=" + billCode;
                document.updateBillCycle.submit();
            }

            function loadNextBillDate() {
                $('#nextbillingDate').empty();
                var sval = $("#billDate").val();
                var tval = $('input[@name="holidayAct"]:checked').val();
                $.getJSON("${pageContext.servletContext.contextPath}/SetNextBillingDateServlet",
                        {billDate: sval, holidayAct: tval},
                function (jsonobject) {
                    $('#nextbillingDate').val(jsonobject.combo1);
                });
            }

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.BILLING_CYCLE_MGT%>'
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



                <div class="content" >

                    <td class="menubar" > <jsp:include page="/leftmenu.jsp" /> </td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------    -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                           

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddBillingCycleMgtServlet">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px">Billing Cycle Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="billCycleCode" value="${billBean.billCycleCode}" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Billing Date</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="billDate" id="billDate" class="inputfield-mandatory" style="width: 163px" onchange="loadNextBillDate()">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${billBean.billDate=='1'}">
                                                                <option value="1" selected>1</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='1'}">
                                                                <option value="1">1</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='2'}">
                                                                <option value="2" selected>2</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='2'}">
                                                                <option value="2">2</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='3'}">
                                                                <option value="3" selected>3</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='3'}">
                                                                <option value="3">3</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='4'}">
                                                                <option value="4" selected>4</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='4'}">
                                                                <option value="4">4</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='5'}">
                                                                <option value="5" selected>5</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='5'}">
                                                                <option value="5">5</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='6'}">
                                                                <option value="6" selected>6</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='6'}">
                                                                <option value="6">6</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='7'}">
                                                                <option value="7" selected>7</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='7'}">
                                                                <option value="7">7</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='8'}">
                                                                <option value="8" selected>8</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='8'}">
                                                                <option value="8">8</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='9'}">
                                                                <option value="9" selected>9</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='9'}">
                                                                <option value="9">9</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='10'}">
                                                                <option value="10" selected>10</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='10'}">
                                                                <option value="10">10</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='11'}">
                                                                <option value="11" selected>11</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='11'}">
                                                                <option value="11">11</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='12'}">
                                                                <option value="12" selected>12</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='12'}">
                                                                <option value="12">12</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='13'}">
                                                                <option value="13" selected>13</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='13'}">
                                                                <option value="13">13</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='14'}">
                                                                <option value="14" selected>14</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='14'}">
                                                                <option value="14">14</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='15'}">
                                                                <option value="15" selected>15</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='15'}">
                                                                <option value="15">15</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='16'}">
                                                                <option value="16" selected>16</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='16'}">
                                                                <option value="16">16</option>

                                                            </c:if>

                                                            <c:if test="${billBean.billDate=='17'}">
                                                                <option value="17" selected>17</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='17'}">
                                                                <option value="17">17</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='18'}">
                                                                <option value="18" selected>18</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='18'}">
                                                                <option value="18">18</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='19'}">
                                                                <option value="19" selected>19</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='19'}">
                                                                <option value="19">19</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='20'}">
                                                                <option value="20" selected>20</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='20'}">
                                                                <option value="20">20</option>

                                                            </c:if>

                                                            <c:if test="${billBean.billDate=='21'}">
                                                                <option value="21" selected>21</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='21'}">
                                                                <option value="21">21</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='22'}">
                                                                <option value="22" selected>22</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='22'}">
                                                                <option value="22">22</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='23'}">
                                                                <option value="23" selected>23</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='23'}">
                                                                <option value="23">23</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='24'}">
                                                                <option value="24" selected>24</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='24'}">
                                                                <option value="24">24</option>

                                                            </c:if>

                                                            <c:if test="${billBean.billDate=='25'}">
                                                                <option value="25" selected>25</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='25'}">
                                                                <option value="25">25</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='26'}">
                                                                <option value="26" selected>26</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='26'}">
                                                                <option value="26">26</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='27'}">
                                                                <option value="27" selected>27</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='27'}">
                                                                <option value="27">27</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='28'}">
                                                                <option value="28" selected>28</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='28'}">
                                                                <option value="28">28</option>

                                                            </c:if>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${billBean.billDescription}" maxlength="64"/></td>

                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${billBean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="0" checked="true" onchange="loadNextBillDate()" />Previous Working Day</td>

                                                    </c:if>
                                                    <c:if test="${billBean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="0" onchange="loadNextBillDate()" />Previous Working Day</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${billBean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="1" checked="true" onchange="loadNextBillDate()"  />At Holiday</td>

                                                    </c:if>
                                                    <c:if test="${billBean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="1" onchange="loadNextBillDate()" />At Holiday</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${billBean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="2" checked="true" onchange="loadNextBillDate()" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${billBean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" id="holidayAct" onchange="loadNextBillDate()" />Next Working Day</td>    
                                                        </c:if>



                                                </tr>
                                                <!--  -------------    Next billing updated ---------------------------   -->
                                                <tr>
                                                    <td>Next Billing Date </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="nextbillingDate" id="nextbillingDate" value="${billBean.nextbillingDate}" maxlength="20" readonly="readonly"/></td>

                                                </tr>
                                                <!--  -------------   End  ---------------------------------------------   -->
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="status"  class="inputfield-mandatory" style="width: 163px">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${billBean.billStatus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${billBean.billStatus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td > 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="backToLoad()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" name="updateBillCycle" action="${pageContext.request.contextPath}/UpdateBillingCycleMgtServlet">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Billing Cycle Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="billCycleCode" value="${billBean.billCycleCode}" readonly="true"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Billing Date</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="billDate" id="billDate" class="inputfield-mandatory" style="width: 163px" onchange="loadNextBillDate()">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${billBean.billDate=='1'}">
                                                                <option value="1" selected>1</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='1'}">
                                                                <option value="1">1</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='2'}">
                                                                <option value="2" selected>2</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='2'}">
                                                                <option value="2">2</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='3'}">
                                                                <option value="3" selected>3</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='3'}">
                                                                <option value="3">3</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='4'}">
                                                                <option value="4" selected>4</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='4'}">
                                                                <option value="4">4</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='5'}">
                                                                <option value="5" selected>5</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='5'}">
                                                                <option value="5">5</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='6'}">
                                                                <option value="6" selected>6</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='6'}">
                                                                <option value="6">6</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='7'}">
                                                                <option value="7" selected>7</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='7'}">
                                                                <option value="7">7</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='8'}">
                                                                <option value="8" selected>8</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='8'}">
                                                                <option value="8">8</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='9'}">
                                                                <option value="9" selected>9</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='9'}">
                                                                <option value="9">9</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='10'}">
                                                                <option value="10" selected>10</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='10'}">
                                                                <option value="10">10</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='11'}">
                                                                <option value="11" selected>11</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='11'}">
                                                                <option value="11">11</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='12'}">
                                                                <option value="12" selected>12</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='12'}">
                                                                <option value="12">12</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='13'}">
                                                                <option value="13" selected>13</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='13'}">
                                                                <option value="13">13</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='14'}">
                                                                <option value="14" selected>14</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='14'}">
                                                                <option value="14">14</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='15'}">
                                                                <option value="15" selected>15</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='15'}">
                                                                <option value="15">15</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='16'}">
                                                                <option value="16" selected>16</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='16'}">
                                                                <option value="16">16</option>

                                                            </c:if>

                                                            <c:if test="${billBean.billDate=='17'}">
                                                                <option value="17" selected>17</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='17'}">
                                                                <option value="17">17</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='18'}">
                                                                <option value="18" selected>18</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='18'}">
                                                                <option value="18">18</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='19'}">
                                                                <option value="19" selected>19</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='19'}">
                                                                <option value="19">19</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='20'}">
                                                                <option value="20" selected>20</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='20'}">
                                                                <option value="20">20</option>

                                                            </c:if>

                                                            <c:if test="${billBean.billDate=='21'}">
                                                                <option value="21" selected>21</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='21'}">
                                                                <option value="21">21</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='22'}">
                                                                <option value="22" selected>22</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='22'}">
                                                                <option value="22">22</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='23'}">
                                                                <option value="23" selected>23</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='23'}">
                                                                <option value="23">23</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='24'}">
                                                                <option value="24" selected>24</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='24'}">
                                                                <option value="24">24</option>

                                                            </c:if>

                                                            <c:if test="${billBean.billDate=='25'}">
                                                                <option value="25" selected>25</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='25'}">
                                                                <option value="25">25</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='26'}">
                                                                <option value="26" selected>26</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='26'}">
                                                                <option value="26">26</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='27'}">
                                                                <option value="27" selected>27</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='27'}">
                                                                <option value="27">27</option>

                                                            </c:if>
                                                            <c:if test="${billBean.billDate=='28'}">
                                                                <option value="28" selected>28</option>
                                                            </c:if>
                                                            <c:if test="${billBean.billDate!='28'}">
                                                                <option value="28">28</option>

                                                            </c:if>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${billBean.billDescription}" maxlength="64"/></td>

                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${billBean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="0" checked="true" onchange="loadNextBillDate()" />Previous Working Day</td>

                                                    </c:if>
                                                    <c:if test="${billBean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="0" onchange="loadNextBillDate()" />Previous Working Day</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${billBean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="1" checked="true" onchange="loadNextBillDate()"  />At Holiday</td>

                                                    </c:if>
                                                    <c:if test="${billBean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="1" onchange="loadNextBillDate()" />At Holiday</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${billBean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" id="holidayAct" value="2" checked="true" onchange="loadNextBillDate()" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${billBean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" id="holidayAct" onchange="loadNextBillDate()" />Next Working Day</td>    
                                                        </c:if>



                                                </tr>

                                                <!--  -------------    Next billing updated ---------------------------   -->
                                                <tr>
                                                    <td>Next Billing Date </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="nextbillingDate" id="nextbillingDate" value="${billBean.nextbillingDate}" maxlength="20" readonly="readonly"/></td>

                                                </tr>
                                                <!--  -------------   End  ---------------------------------------------   -->

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="status"  class="inputfield-mandatory" style="width: 163px">

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${billBean.billStatus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${billBean.billStatus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td > 
                                                        <input type="submit" value="update" name="Update" class="defbutton"/>
                                                        <input onclick="backToLoadUpdate('${billBean.billCycleCode}')" type="reset" value="Reset" class="defbutton"/>
                                                        <input onclick="backToLoad()" type="button" value="Cancel" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewHotlistReasonMgtServlet">
                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td>Billing Cycle Code</td>
                                                <td>:</td>
                                                <td>${billBean.billCycleCode}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Billing Date</td>
                                                <td>:</td>
                                                <td>${billBean.billDate}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Description</td>
                                                <td>:</td>
                                                <td>${billBean.billDescription}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Holiday Action</td>
                                                <td>:</td>
                                                <td>${billBean.holidayAction}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Next Billing Date</td>
                                                <td>:</td>
                                                <td>${billBean.nextbillingDate}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td>:</td>
                                                <td>${billBean.billStatusDes}</td>                                                                     
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td > 
                                                    <input onclick="backToLoad()" type="button" value="Back" style="width: 100px"/>
                                                </td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>


                                <!-- show table data -->
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview3">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Billing Cycle Code</th>
                                                <th>Billing Date</th>
                                                <th>Description</th>
                                                <th>Holiday Action</th>
                                                <th>Next Billing Date</th>
                                                <th>Status</th>
                                                <th>Last Updated Time</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${billingList}" var="bList">
                                                <tr>
                                                    <td>${bList.billCycleCode}</td>
                                                    <td>${bList.billDate}</td>
                                                    <td>${bList.billDescription}</td>
                                                    <c:if test="${bList.holidayAction == '0'}">
                                                        <td>Previous Working Day</td>
                                                    </c:if>
                                                    <c:if test="${bList.holidayAction == '1'}">
                                                        <td>At Holiday</td>
                                                    </c:if>
                                                    <c:if test="${bList.holidayAction == '2'}">
                                                        <td>Next Working Day</td>
                                                    </c:if>
                                                    <c:if test="${bList.holidayAction == null or bList.holidayAction == '' }">
                                                        <td>--</td>
                                                    </c:if>
                                                    <td>${bList.nextbillingDate}</td>
                                                    <td>${bList.billStatusDes}</td>
                                                    <td>${bList.lastUpdatedTime}</td>

                                                    <td  ><a href='${pageContext.request.contextPath}/ViewBillingCycleMgtServlet?id=<c:out value="${bList.billCycleCode}"></c:out>'>View</a></td>
                                                    <td  ><a href='${pageContext.request.contextPath}/LoadUpdateBillingCycleMgtServlet?id=<c:out value="${bList.billCycleCode}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteBillingCycle('${bList.billCycleCode}')">Delete</a></td>


                                                </tr>
                                            </c:forEach>
                                        </tbody>

                                    </table>

                                </form>




                                <!--   ------------------------- end developer area  --------------------------------  -->

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
