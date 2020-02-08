<%-- 
    Document   : standingorder
    Created on : Jan 31, 2013, 2:10:51 PM
    Author     : badrika
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

        <script>

            function GetCardDetails() {
                document.addstandingorderform.action = "${pageContext.request.contextPath}/LoadCardStandingOrderServlet?paramet=details";
                document.addstandingorderform.submit();

            }

            function AddCSO() {
                document.addstandingorderform.action = "${pageContext.request.contextPath}/AddUpdateCardStandingOrderServlet?paramet=add";
                document.addstandingorderform.submit();

            }

            function UpdateCSO(value) {

                window.location = "${pageContext.request.contextPath}/ViewUpdateviewDeleteCardStandingOrderServlet?orderid=" + value + "&paramet=viewupdate";

            }
            function ViewCSO(value) {

                window.location = "${pageContext.request.contextPath}/ViewUpdateviewDeleteCardStandingOrderServlet?orderid=" + value + "&paramet=view";

            }
            function DeleteCSO(value) {

//                window.location = "${pageContext.request.contextPath}/ViewUpdateviewDeleteCardStandingOrderServlet?orderid="+value+"&paramet=delete";              
                $("#dialog-confirm").html("<p>Do you really want to delete standing order " + value + "?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/ViewUpdateviewDeleteCardStandingOrderServlet?orderid=" + value + "&paramet=delete";
                        }
                    }
                });

            }

            function UpdateConfirm() {
                document.updatestandingorderform.action = "${pageContext.request.contextPath}/ViewUpdateviewDeleteCardStandingOrderServlet?paramet=update";
                document.updatestandingorderform.submit();

            }

            function Cancel() {

                window.location = "${pageContext.request.contextPath}/LoadCardStandingOrderServlet";

            }


        </script>

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CARD_STADING_ORDER%>'
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
                                        <td style="width: 500px">
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addstandingorderform">



                                        <table cellpadding="0" cellspacing="10">

                                            <tr>
                                                <td style="width: 150px;">Standing Order ID</td>
                                                <td></td>
                                                <td><input type="text" value="${orderId}" name="orderId" readonly="true"/></td>
                                            </tr>                                            
                                            <tr>
                                                <td>Card Number</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" value="${cardnum}" name="cardNum" maxlength="20" onchange="GetCardDetails()"/></td>
                                            </tr>
                                        </table>

                                        <c:if test="${cardexist=='yes'}">

                                            <fieldset id="fieldst" style="width: 350px;"><legend><b>Card Details</b></legend>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td style="width: 150px;">Card Holder Name</td>
                                                        <td>:</td>
                                                        <td>${cardbean.cardHolderName}</td> 

                                                    </tr>

                                                    <tr>
                                                        <td >Card Expiry Date</td>
                                                        <td>:</td>
                                                        <td>${cardbean.expDate}</td> 

                                                    </tr>

                                                    <tr>
                                                        <td >Card Status</td>
                                                        <td>:</td>
                                                        <td>${cardbean.statusDes}</td> 

                                                    </tr>


                                                </table>

                                            </fieldset>



                                        </c:if>


                                        <table cellpadding="0" cellspacing="10">

                                            <tr>
                                                <td style="width: 150px;">Standing Order Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="orderType" id="orderType">
                                                        <option value="${bean.orderTypeId}" selected="">--SELECT--</option>
                                                        <c:forEach var="odty" items="${orderTypeList}">
                                                            <c:if test="${bean.orderTypeId==odty.key}">
                                                                <option value="${odty.key}" selected>${odty.value}</option>
                                                            </c:if>
                                                            <c:if test="${bean.orderTypeId != odty.key}">
                                                                <option value="${odty.key}" >${odty.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <!--                                            <tr>
                                                                                            <td>Amount</td>
                                                                                            <td><font style="color: red;">*</font></td>
                                                                                            <td><input type="text" value="${bean.ammount}" name="amount" maxlength="10"/></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>Currency Type</td>
                                                                                            <td><font style="color: red;">*</font></td>
                                                                                            <td>
                                                                                                <select name="currType" id="currType">
                                                                                                    <option value="${bean.currencyType}" selected>--SELECT--</option>
                                            
                                            <c:forEach var="curty" items="${currencyList}">
                                                <c:if test="${bean.currencyType == curty.key}">
                                                    <option value="${curty.key}" selected>${curty.value}</option>
                                                </c:if>
                                                <c:if test="${bean.currencyType != curty.key}">
                                                    <option value="${curty.key}" >${curty.value}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>-->
                                            <tr>
                                                <td>Account Number</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="accNum" value="${bean.accNum}" /></td>
                                            </tr>

                                            <tr>
                                                <td>Start Date</td>
                                                <td></td>
                                                <td><input type="text" value="${date}" name="startDate" maxlength="16" readonly="true"/></td>
                                            </tr>

                                            <tr>
                                                <td >End Date</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input  name="endDate" readonly maxlength="16" value="${bean.endDate}" key="endDate" id="endDate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#endDate").datepicker({
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
                                                <td>Pay Date</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="payDate" id="payDate">
                                                        <option value="" selected="">--SELECT--</option>

                                                        <c:if test="${bean.payDay == '1'}">
                                                            <option value="1" selected>1</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '1'}">
                                                            <option value="1" >1</option>                                                            
                                                        </c:if>


                                                        <c:if test="${bean.payDay == '2'}">
                                                            <option value="2" selected>2</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '2'}">
                                                            <option value="2" >2</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '3'}">
                                                            <option value="3" selected>3</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '3'}">
                                                            <option value="3" >3</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '4'}">
                                                            <option value="4" selected>4</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '4'}">
                                                            <option value="4" >4</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '5'}">
                                                            <option value="5" selected>5</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '5'}">
                                                            <option value="5" >5</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '6'}">
                                                            <option value="6" selected>6</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '6'}">
                                                            <option value="6" >6</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '7'}">
                                                            <option value="7" selected>7</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '7'}">
                                                            <option value="7" >7</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '8'}">
                                                            <option value="8" selected>8</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '8'}">
                                                            <option value="8" >8</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '9'}">
                                                            <option value="9" selected>9</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '9'}">
                                                            <option value="9" >9</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '10'}">
                                                            <option value="10" selected>10</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '10'}">
                                                            <option value="10" >10</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '11'}">
                                                            <option value="11" selected>11</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '11'}">
                                                            <option value="11" >11</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '12'}">
                                                            <option value="12" selected>12</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '12'}">
                                                            <option value="12" >12</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '13'}">
                                                            <option value="13" selected>13</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '13'}">
                                                            <option value="13" >13</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '14'}">
                                                            <option value="14" selected>14</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '14'}">
                                                            <option value="14" >14</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '15'}">
                                                            <option value="15" selected>15</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '15'}">
                                                            <option value="15" >15</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '16'}">
                                                            <option value="16" selected>16</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '16'}">
                                                            <option value="16" >16</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '17'}">
                                                            <option value="17" selected>17</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '17'}">
                                                            <option value="17" >17</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '18'}">
                                                            <option value="18" selected>18</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '18'}">
                                                            <option value="18" >18</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '19'}">
                                                            <option value="19" selected>19</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '19'}">
                                                            <option value="19" >19</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '20'}">
                                                            <option value="20" selected>20</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '20'}">
                                                            <option value="20" >20</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '21'}">
                                                            <option value="21" selected>21</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '21'}">
                                                            <option value="21" >21</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '22'}">
                                                            <option value="22" selected>22</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '22'}">
                                                            <option value="22" >22</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '23'}">
                                                            <option value="23" selected>23</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '23'}">
                                                            <option value="23" >23</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '24'}">
                                                            <option value="24" selected>24</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '24'}">
                                                            <option value="24" >24</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '25'}">
                                                            <option value="25" selected>25</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '25'}">
                                                            <option value="25" >25</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '26'}">
                                                            <option value="26" selected>26</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '26'}">
                                                            <option value="26" >26</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '27'}">
                                                            <option value="27" selected>27</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '27'}">
                                                            <option value="27" >27</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '28'}">
                                                            <option value="28" selected>28</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '28'}">
                                                            <option value="28" >28</option>                                                            
                                                        </c:if>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Frequency (month)</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="frequency" id="frequency">
                                                        <option value="" selected="">--SELECT--</option>

                                                        <c:if test="${bean.frequency == '1'}">
                                                            <option value="1" selected>1</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '1'}">
                                                            <option value="1" >1</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '2'}">
                                                            <option value="2" selected>2</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '2'}">
                                                            <option value="2" >2</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '3'}">
                                                            <option value="3" selected>3</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '3'}">
                                                            <option value="3" >3</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '4'}">
                                                            <option value="4" selected>4</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '4'}">
                                                            <option value="4" >4</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '5'}">
                                                            <option value="5" selected>5</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '5'}">
                                                            <option value="5" >5</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '6'}">
                                                            <option value="6" selected>6</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '6'}">
                                                            <option value="6" >6</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '7'}">
                                                            <option value="7" selected>7</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '7'}">
                                                            <option value="7" >7</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '8'}">
                                                            <option value="8" selected>8</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '8'}">
                                                            <option value="8" >8</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '9'}">
                                                            <option value="9" selected>9</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '9'}">
                                                            <option value="9" >9</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '10'}">
                                                            <option value="10" selected>10</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '10'}">
                                                            <option value="10" >10</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '11'}">
                                                            <option value="11" selected>11</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '11'}">
                                                            <option value="11" >11</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '12'}">
                                                            <option value="12" selected>12</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '12'}">
                                                            <option value="12" >12</option>                                                            
                                                        </c:if>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td >Initial Payment Date</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input  name="initPayDate" readonly maxlength="16" value="${bean.nextDate}" key="initPayDate" id="initPayDate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#initPayDate").datepicker({
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
                                                <td >Status </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                            <c:if test="${bean.status == status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${bean.status != status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>

                                                        </c:forEach>
                                                    </select>

                                                </td>
                                            </tr>

                                            <tr>
                                                <td >Remarks</td>
                                                <td></td>
                                                <td> <TEXTAREA id="remark" NAME="remark" ROWS="3" style="width: 350px;">${bean.description}</TEXTAREA></td>
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td ><input type="button" value="Add" style="width: 100px" onclick="AddCSO()"/>                                                        
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="Cancel()"/></td>
                                            </tr>


                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updatestandingorderform">



                                        <table cellpadding="0" cellspacing="10">

                                            <tr>
                                                <td style="width: 150px;">Standing Order ID</td>
                                                <td></td>
                                                <td><input type="text" value="${bean.standingOrderId}" name="orderId" readonly="true"/></td>
                                            </tr>                                            
                                            <tr>
                                                <td>Card Number</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" value="${bean.cardNumber}" name="cardNum" maxlength="20" onchange="GetCardDetails()"/></td>
                                            </tr>

                                            <!--                                            <tr>
                                                                                            <td><input type="button" value="Card Details" name="details" style="width: 100px" onclick=""/></td>
                                                                                            <td></td>
                                                                                            <td></td>
                                                                                        </tr>-->

                                        </table>

                                        <c:if test="${cardexist=='yes'}">

                                            <fieldset id="fieldst" style="width: 350px;"><legend><b>Card Details</b></legend>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td style="width: 150px;">Card Holder Name</td>
                                                        <td>:</td>
                                                        <td>${cardbean.cardHolderName}</td> 

                                                    </tr>

                                                    <tr>
                                                        <td >Card Expiry Date</td>
                                                        <td>:</td>
                                                        <td>${cardbean.expDate}</td> 

                                                    </tr>

                                                    <tr>
                                                        <td >Card Status</td>
                                                        <td>:</td>
                                                        <td>${cardbean.statusDes}</td> 

                                                    </tr>


                                                </table>

                                            </fieldset>



                                        </c:if>


                                        <table cellpadding="0" cellspacing="10">

                                            <tr>
                                                <td style="width: 150px;">Standing Order Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="orderType" id="orderType">
                                                        <option value="${bean.orderTypeId}" selected="">--SELECT--</option>
                                                        <c:forEach var="odty" items="${orderTypeList}">
                                                            <c:if test="${bean.orderTypeId==odty.key}">
                                                                <option value="${odty.key}" selected>${odty.value}</option>
                                                            </c:if>
                                                            <c:if test="${bean.orderTypeId != odty.key}">
                                                                <option value="${odty.key}" >${odty.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
<!--                                            <tr>
                                                <td>Amount</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" value="${bean.ammount}" name="amount" maxlength="10"/></td>
                                            </tr>
                                            <tr>
                                                <td>Currency Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="currType" id="currType">
                                                        <option value="${bean.currencyType}" selected>--SELECT--</option>

                                            <c:forEach var="curty" items="${currencyList}">
                                                <c:if test="${bean.currencyType == curty.key}">
                                                                <option value="${curty.key}" selected>${curty.value}</option>
                                                </c:if>
                                                <c:if test="${bean.currencyType != curty.key}">
                                                                <option value="${curty.key}" >${curty.value}</option>
                                                </c:if>
                                            </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>-->
                                            <tr>
                                                <td>Account Number</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="accNum" value="${bean.accNum}" /></td>
                                            </tr>

                                            <tr>
                                                <td>Start Date</td>
                                                <td></td>
                                                <td><input type="text" value="${bean.startDate}" name="startDate" maxlength="16" readonly="true"/></td>
                                            </tr>

                                            <tr>
                                                <td >End Date</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input  name="endDate" readonly maxlength="16" value="${bean.endDate}" key="endDate" id="endDate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#endDate").datepicker({
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
                                                <td>Pay Date</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="payDate" id="payDate">
                                                        <option value="" selected="">--SELECT--</option>

                                                        <c:if test="${bean.payDay == '1'}">
                                                            <option value="1" selected>1</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '1'}">
                                                            <option value="1" >1</option>                                                            
                                                        </c:if>


                                                        <c:if test="${bean.payDay == '2'}">
                                                            <option value="2" selected>2</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '2'}">
                                                            <option value="2" >2</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '3'}">
                                                            <option value="3" selected>3</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '3'}">
                                                            <option value="3" >3</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '4'}">
                                                            <option value="4" selected>4</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '4'}">
                                                            <option value="4" >4</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '5'}">
                                                            <option value="5" selected>5</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '5'}">
                                                            <option value="5" >5</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '6'}">
                                                            <option value="6" selected>6</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '6'}">
                                                            <option value="6" >6</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '7'}">
                                                            <option value="7" selected>7</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '7'}">
                                                            <option value="7" >7</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '8'}">
                                                            <option value="8" selected>8</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '8'}">
                                                            <option value="8" >8</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '9'}">
                                                            <option value="9" selected>9</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '9'}">
                                                            <option value="9" >9</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '10'}">
                                                            <option value="10" selected>10</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '10'}">
                                                            <option value="10" >10</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.payDay == '11'}">
                                                            <option value="11" selected>11</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '11'}">
                                                            <option value="11" >11</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '12'}">
                                                            <option value="12" selected>12</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '12'}">
                                                            <option value="12" >12</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '13'}">
                                                            <option value="13" selected>13</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '13'}">
                                                            <option value="13" >13</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '14'}">
                                                            <option value="14" selected>14</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '14'}">
                                                            <option value="14" >14</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '15'}">
                                                            <option value="15" selected>15</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '15'}">
                                                            <option value="15" >15</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '16'}">
                                                            <option value="16" selected>16</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '16'}">
                                                            <option value="16" >16</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '17'}">
                                                            <option value="17" selected>17</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '17'}">
                                                            <option value="17" >17</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '18'}">
                                                            <option value="18" selected>18</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '18'}">
                                                            <option value="18" >18</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '19'}">
                                                            <option value="19" selected>19</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '19'}">
                                                            <option value="19" >19</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '20'}">
                                                            <option value="20" selected>20</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '20'}">
                                                            <option value="20" >20</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '21'}">
                                                            <option value="21" selected>21</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '21'}">
                                                            <option value="21" >21</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '22'}">
                                                            <option value="22" selected>22</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '22'}">
                                                            <option value="22" >22</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '23'}">
                                                            <option value="23" selected>23</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '23'}">
                                                            <option value="23" >23</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '24'}">
                                                            <option value="24" selected>24</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '24'}">
                                                            <option value="24" >24</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '25'}">
                                                            <option value="25" selected>25</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '25'}">
                                                            <option value="25" >25</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '26'}">
                                                            <option value="26" selected>26</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '26'}">
                                                            <option value="26" >26</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '27'}">
                                                            <option value="27" selected>27</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '27'}">
                                                            <option value="27" >27</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay == '28'}">
                                                            <option value="28" selected>28</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.payDay != '28'}">
                                                            <option value="28" >28</option>                                                            
                                                        </c:if>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Frequency (month)</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="frequency" id="frequency">
                                                        <option value="" selected="">--SELECT--</option>

                                                        <c:if test="${bean.frequency == '1'}">
                                                            <option value="1" selected>1</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '1'}">
                                                            <option value="1" >1</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '2'}">
                                                            <option value="2" selected>2</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '2'}">
                                                            <option value="2" >2</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '3'}">
                                                            <option value="3" selected>3</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '3'}">
                                                            <option value="3" >3</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '4'}">
                                                            <option value="4" selected>4</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '4'}">
                                                            <option value="4" >4</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '5'}">
                                                            <option value="5" selected>5</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '5'}">
                                                            <option value="5" >5</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '6'}">
                                                            <option value="6" selected>6</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '6'}">
                                                            <option value="6" >6</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '7'}">
                                                            <option value="7" selected>7</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '7'}">
                                                            <option value="7" >7</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '8'}">
                                                            <option value="8" selected>8</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '8'}">
                                                            <option value="8" >8</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '9'}">
                                                            <option value="9" selected>9</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '9'}">
                                                            <option value="9" >9</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '10'}">
                                                            <option value="10" selected>10</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '10'}">
                                                            <option value="10" >10</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '11'}">
                                                            <option value="11" selected>11</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '11'}">
                                                            <option value="11" >11</option>                                                            
                                                        </c:if>

                                                        <c:if test="${bean.frequency == '12'}">
                                                            <option value="12" selected>12</option>                                                            
                                                        </c:if>
                                                        <c:if test="${bean.frequency != '12'}">
                                                            <option value="12" >12</option>                                                            
                                                        </c:if>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td >Initial Payment Date</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <input  name="initPayDate" readonly maxlength="16" value="${bean.nextDate}" key="initPayDate" id="initPayDate"  />
                                                    <script type="text/javascript">
                                                        $(function () {
                                                            $("#initPayDate").datepicker({
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
                                                <td >Status </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                            <c:if test="${bean.status == status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${bean.status != status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>

                                                        </c:forEach>
                                                    </select>

                                                </td>
                                            </tr>

                                            <tr>
                                                <td >Remarks</td>
                                                <td></td>
                                                <td> <TEXTAREA id="remark" NAME="remark" ROWS="3" style="width: 350px;">${bean.description}</TEXTAREA></td>
                                            </tr>

                                            <tr>
                                                <td><input type="hidden" value="${oldV}" name="oldV" /></td>
                                                <td></td>
                                                <td ><input type="button" value="Update" style="width: 100px" onclick="UpdateConfirm()"/>                                                        
                                                    <input type="Reset" value="Reset" name="reset" style="width: 100px" onclick="UpdateCSO(${bean.standingOrderId})"/>
                                                    <input type="button" value="Cancel" style="width: 100px" onclick="Cancel()"/>
                                                </td>
                                            </tr>


                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='view'}" >

                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td>Standing Order ID</td>
                                            <td>:${bean.standingOrderId}</td>
                                        </tr>
                                        <tr>
                                            <td>Card Number</td>
                                            <td>:${bean.cardNumber}</td>
                                        </tr>
                                        <tr>
                                            <td>Order Type</td>
                                            <td>:${bean.orderTypeIdDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Account Number</td>
                                            <td>:${bean.accNum}</td>
                                        </tr>
                                        <tr>
                                            <td>Start Date</td>
                                            <td>:${bean.startDate}</td>
                                        </tr>
                                        <tr>
                                            <td>End Date</td>
                                            <td>:${bean.endDate}</td>
                                        </tr>
<!--                                        <tr>
                                            <td>Amount</td>
                                            <td>:${bean.ammount}</td>
                                        </tr>
                                        <tr>
                                            <td>Currency Type</td>
                                            <td>:${bean.currencyDes}</td>
                                        </tr>-->
                                    
                                        <tr>
                                            <td>Pay Date</td>
                                            <td>:${bean.payDay}</td>
                                        </tr>
                                        <tr>
                                            <td>Frequency Type</td>
                                            <td>:${bean.frequency}</td>
                                        </tr>
                                        <tr>
                                            <td>Next Date</td>
                                            <td>:${bean.nextDate}</td>
                                        </tr>
                                        <tr>
                                            <td>Description/Remark</td>
                                            <td>:${bean.description}</td>
                                        </tr>
                                        <tr>
                                            <td>Status</td>
                                            <td>:${bean.statusDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Is Eligible BC</td>
                                            <td>:${bean.isEligibleBC}</td>
                                        </tr>
                                        <tr>
                                            <td>Last Updated User</td>
                                            <td>:${bean.lastUpdatedUser}</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><input type="button" style="width: 100px"value="Back" onclick="Cancel()" /></td>
                                        </tr>



                                    </table>

                                </c:if>

                                <!-- show table data -->
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview3">
                                        <thead>
                                            <tr>
                                                <th>Standing Order ID</th>
                                                <th>Card Number</th>
                                                <th>Order Type</th>
                                                <th>Account Number</th>
                                                <th>Start Date</th>
                                                <th>End Date</th>
<!--                                                <th>Amount</th>              
                                                <th>Currency</th>-->
                                                <th>Pay Date</th>
                                                <th>Frequency</th>
                                                <th>Next Date</th>
                                                <th>Status</th>

                                                <th>View</th>
                                                <th>Update</th>
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${orderList}" var="list">
                                                <tr>
                                                    <td>${list.standingOrderId}</td>
                                                    <td>${list.cardNumber}</td>
                                                    <td>${list.orderTypeIdDes}</td>
                                                    <td>${list.accNum}</td>
                                                    <td>${list.startDate}</td>
                                                    <td>${list.endDate}</td>
<!--                                                    <td>${list.ammount}</td>
                                                    <td>${list.currencyDes}</td>-->
                                                    <td>${list.payDay}</td>
                                                    <td>${list.frequency}</td>
                                                    <td>${list.nextDate}</td>
                                                    <td>${list.statusDes}</td>


                                                    <td><a  href='#' onclick="ViewCSO('${list.standingOrderId}')">View</a></td>
                                                    <td><a  href='#' onclick="UpdateCSO('${list.standingOrderId}')">Update</a></td>
                                                    <td><a  href='#' onclick="DeleteCSO('${list.standingOrderId}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>                                        
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

