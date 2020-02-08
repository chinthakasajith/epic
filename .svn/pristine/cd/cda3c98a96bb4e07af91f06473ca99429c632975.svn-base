<%-- 
    Document   : merchantpaymentcyclehome
    Created on : Nov 16, 2012, 11:18:59 AM
    Author     : admin
--%>

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
            $(document).ready(function() {
                var oTable = $('#tableview').dataTable({
                    "bJQueryUI": true,
                    "sScrollX": "100%",
                    "bDestroy": true,
                    "sPaginationType": "full_numbers"
                });
            });
        </script>
        <script type="text/javascript">

            function backToLoad() {
                window.location = "${pageContext.request.contextPath}/LoadMerchantPaymentCycleServlet";
            }

            function deleteBillingCycle(value) {

                answer = confirm("Do you really want to delete " + value + " Merchant Payment Cycle?")
                if (answer != 0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteMerchantPaymentCycleServlet?id=" + value;
                }
                else
                {
                    window.location = "${pageContext.request.contextPath}/LoadMerchantPaymentCycleServlet";
                }

            }

            function backToLoadUpdate(paymentCode) {
                document.updateform.action = "${pageContext.request.contextPath}/LoadUpdateMerchantPaymentCycleServlet?id=" + paymentCode;
                document.updateform.submit();
            }

            function changePaymentDate(value) {

                var option = $("#option").val();
                if (option == '') {
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");
                    $("#date").attr("disabled", true);
                }
                else if (option == '1') {
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");
                    $("#date").attr("disabled", true);
                } else if (option == '2') {
                    $("#date").attr("disabled", false);
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");
                    //       for(i=1 ; i<8 ;i++){                       
                    $("#date").append("<option value='1' <c:if test="${paymentBean.paymentDate == '1'}"> selected </c:if>>Sunday</option>");
                    $("#date").append("<option value='2' <c:if test="${paymentBean.paymentDate == '2'}"> selected </c:if>>Monday</option>");
                    $("#date").append("<option value='3' <c:if test="${paymentBean.paymentDate == '3'}"> selected </c:if>>Tuesday</option>");
                    $("#date").append("<option value='4' <c:if test="${paymentBean.paymentDate == '4'}"> selected </c:if>>wednesday</option>");
                    $("#date").append("<option value='5' <c:if test="${paymentBean.paymentDate == '5'}"> selected </c:if>>thursday</option>");
                    $("#date").append("<option value='6' <c:if test="${paymentBean.paymentDate == '6'}"> selected </c:if>>Friday</option>");
                    $("#date").append("<option value='7' <c:if test="${paymentBean.paymentDate == '7'}"> selected </c:if>>Saturday</option>");

                    //       }                                        
                } else if (option == '3') {
                    $("#date").attr("disabled", false);
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");

            <c:forEach var="dayList" items="${monthDayList}">
                    $("#date").append("<option <c:if test="${paymentBean.paymentDate == dayList}"> selected </c:if>>" +${dayList} + "</option>");
            </c:forEach>

                } else if (option == '4') {
                    $("#date").attr("disabled", false);
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");
                    //    for(i=1 ; i<13 ;i++){                       
                    $("#date").append("<option value='1'  <c:if test="${paymentBean.paymentDate == '1'}"> selected </c:if>>January</option>");
                    $("#date").append("<option value='2'  <c:if test="${paymentBean.paymentDate == '2'}"> selected </c:if>>February</option>");
                    $("#date").append("<option value='3'  <c:if test="${paymentBean.paymentDate == '3'}"> selected </c:if>>March</option>");
                    $("#date").append("<option value='4'  <c:if test="${paymentBean.paymentDate == '4'}"> selected </c:if>>April</option>");
                    $("#date").append("<option value='5'  <c:if test="${paymentBean.paymentDate == '5'}"> selected </c:if>>Mey</option>");
                    $("#date").append("<option value='6'  <c:if test="${paymentBean.paymentDate == '6'}"> selected </c:if>>June</option>");
                    $("#date").append("<option value='7'  <c:if test="${paymentBean.paymentDate == '7'}"> selected </c:if>>July</option>");
                    $("#date").append("<option value='8'  <c:if test="${paymentBean.paymentDate == '8'}"> selected </c:if>>August</option>");
                    $("#date").append("<option value='9'  <c:if test="${paymentBean.paymentDate == '9'}"> selected </c:if>>September</option>");
                    $("#date").append("<option value='10' <c:if test="${paymentBean.paymentDate == '10'}"> selected </c:if>>October</option>");
                    $("#date").append("<option value='11' <c:if test="${paymentBean.paymentDate == '11'}"> selected </c:if>>November</option>");
                    $("#date").append("<option value='12' <c:if test="${paymentBean.paymentDate == '12'}"> selected </c:if>>December</option>");

                    //    }                    
                }
                $("#date").val(value);
            }

            function setSelectedIndex(s, v) {
                for (var i = 0; i < s.options.length; i++) {
                    if (s.options[i].value == v) {
                        s.options[i].selected = true;
                        return;
                    }
                }
            }
            </script>
            <script>
                function settitle() {

                    $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                            {pagecode: '<%= PageVarList.MERCHANT_PAYMENT_CYCLE%>'
                            },
                    function(data) {

                        if (data != '') {
                            $('.center').text(data)
                            var heading = data.split('?');
                            $('.heading').text(heading[1]);

                        }


                    });

                }

        </script>             
    </head>
    <body onload="changePaymentDate(${stateBean.stateDate})">
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
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" name="addform" action="${pageContext.request.contextPath}/AddMerchantPaymentCycleServlet">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px">Payment Cycle Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="paymentCycleCode" value="${paymentBean.paymentCycleCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Payment Option</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="paymentOption" id="option" style="width: 163px" onchange="changePaymentDate()">
                                                            <option value="">------------SELECT------------</option>
                                                            <c:if test="${paymentBean.paymentOption == '1'}">
                                                                <option value="1" selected="">Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                                <c:set var="setPaymentOption" scope="request" value ="1" />
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == '2'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" selected="">Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == '3'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" selected="">Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == '4'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" selected="">Yearly</option>
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == null or paymentBean.paymentOption == ''}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Payment Date</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="date" value="" id="date" style="width: 163px">
                                                            <option value="">------------SELECT------------</option>
                                                            <c:if test="${paymentBean.paymentDate != null || paymentBean.paymentDate != ''}">
                                                                <option value="${paymentBean.paymentDate}" selected>${paymentBean.paymentDate}</option>
                                                            </c:if>                                                  
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="PaymentDescription" value="${paymentBean.paymentDescription}" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${paymentBean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true" />Previous Working Day</td>
                                                        </c:if>
                                                        <c:if test="${paymentBean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true"/>Previous Working Day</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${paymentBean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" checked="true"  />At Holiday</td>
                                                        </c:if>
                                                        <c:if test="${paymentBean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" />At Holiday</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${paymentBean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" checked="true" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${paymentBean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" />Next Working Day</td>    
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="status" style="width: 163px">
                                                            <option value="" selected>------------SELECT------------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${paymentBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${paymentBean.status!=status.statusCode}">
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
                                                        <a  href="#"  onclick="invokeHistory('<%=PageVarList.MERCHANT_PAYMENT_CYCLE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" />                                                       
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" name="updateform" action="${pageContext.request.contextPath}/UpdateMerchantPaymentCycleServlet" >
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px">Payment Cycle Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="paymentCycleCode" value="${paymentBean.paymentCycleCode}" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Payment Option</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="option" id="option" style="width: 163px" onchange="changePaymentDate()">
                                                            <option value="">------------SELECT------------</option>
                                                            <c:if test="${paymentBean.paymentOption == '1'}">
                                                                <option value="1" selected="">Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                                <c:set var="setoption" scope="request" value ="1" />
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == '2'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" selected="">Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == '3'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" selected="">Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == '4'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" selected="">Yearly</option>
                                                            </c:if>
                                                            <c:if test="${paymentBean.paymentOption == null or paymentBean.paymentOption == ''}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Payment Date</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="date" value="" id="date">
                                                            <option value="">--SELECT--</option>
                                                            <c:if test="${paymentBean.paymentDate != null || paymentBean.paymentDate != ''}">
                                                                <option value="${paymentBean.paymentDate}" selected>${paymentBean.paymentDate}</option>
                                                            </c:if>   
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${paymentBean.paymentDescription}" maxlength="64"/></td>

                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${paymentBean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true" />Previous Working Day</td>
                                                        </c:if>
                                                        <c:if test="${paymentBean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" />Previous Working Day</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${paymentBean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" checked="true"  />At Holiday</td>
                                                        </c:if>
                                                        <c:if test="${paymentBean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" />At Holiday</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${paymentBean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" checked="true" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${paymentBean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" />Next Working Day</td>    
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="status" style="width: 163px">
                                                            <option value="" selected>------------SELECT------------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${paymentBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${paymentBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="hidden" name="oldValue" value="${paymentBean.oldValue}"/></td>
                                                    <td > 
                                                        <input type="submit" value="update" name="Update" class="defbutton"/>
                                                        <input onclick="backToLoadUpdate('${paymentBean.paymentCycleCode}')" type="reset" value="Reset" class="defbutton"/>
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
                                                <td>Payment Cycle Code</td>
                                                <td>:</td>
                                                <td>${paymentBean.paymentCycleCode}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Payment Option</td>
                                                <td>:</td>
                                                <c:if test="${paymentBean.paymentOption == '1'}">
                                                    <td>Daily</td>
                                                </c:if>
                                                <c:if test="${paymentBean.paymentOption == '2'}">
                                                    <td>Weekly</td>
                                                </c:if>
                                                <c:if test="${paymentBean.paymentOption == '3'}">
                                                    <td>Monthly</td>
                                                </c:if>
                                                <c:if test="${paymentBean.paymentOption == '4'}">
                                                    <td>Yearly</td>
                                                </c:if>
                                                <c:if test="${paymentBean.paymentOption == null or paymentBean.paymentOption == ''}">
                                                    <td>--</td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <td>Payment Date</td>
                                                <td>:</td>
                                                <td>${paymentBean.paymentDate}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Description</td>
                                                <td>:</td>
                                                <td>${paymentBean.paymentDescription}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Holiday Action</td>
                                                <td>:</td>
                                                <c:if test="${paymentBean.holidayAction == '0'}">        
                                                    <td>Previous</td>
                                                </c:if> 
                                                <c:if test="${paymentBean.holidayAction == '1'}">        
                                                    <td>Next</td>
                                                </c:if> 
                                                <c:if test="${paymentBean.holidayAction == '2'}">        
                                                    <td>At holiday</td>
                                                </c:if>                                                       

                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td>:</td>
                                                <td>${paymentBean.statusDescripton}</td>                                                                     
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
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Cycle Code</th>
                                                <th>Option</th>
                                                <th>Payment Date</th>
                                                <th>Description</th>
                                                <th>Holiday Action</th>
                                                <th>Status</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${paymentList}" var="pList">
                                                <tr>
                                                    <td>${pList.paymentCycleCode}</td>
                                                    <c:if test="${pList.paymentOption == '1'}">
                                                        <td>Daily</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2'}">
                                                        <td>Weekly</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '3'}">
                                                        <td>Monthly</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4'}">
                                                        <td>Yearly</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == null or pList.paymentOption == ''}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <!-- start load payment date base on payment option -->     
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '1'}">    
                                                        <td>Sunday</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '2'}">    
                                                        <td>Monday</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '3'}">    
                                                        <td>Tuesday</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '4'}">    
                                                        <td>Wednesday</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '5'}">    
                                                        <td>Thursday</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '6'}">    
                                                        <td>Friday</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '2' && pList.paymentDate == '7'}">    
                                                        <td>Saturday</td>
                                                    </c:if>  

                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '1'}">    
                                                        <td>January</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '2'}">    
                                                        <td>February</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '3'}">    
                                                        <td>March</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '4'}">    
                                                        <td>April</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '5'}">    
                                                        <td>May</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '6'}">    
                                                        <td>June</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '7'}">    
                                                        <td>July</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '8'}">    
                                                        <td>August</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '9'}">    
                                                        <td>September</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '10'}">    
                                                        <td>October</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '11'}">    
                                                        <td>November</td>
                                                    </c:if>  
                                                    <c:if test="${pList.paymentOption == '4' && pList.paymentDate == '12'}">    
                                                        <td>December</td>
                                                    </c:if>
                                                    <c:if test="${pList.paymentOption == '1' || pList.paymentOption == '3' }">    
                                                        <td>${pList.paymentDate}</td>
                                                    </c:if>
                                                    <!-- end load payment date base on payment option -->                                                     
                                                    <td>${pList.paymentDescription}</td>
                                                    <c:if test="${pList.holidayAction == '0'}">        
                                                        <td>Previous</td>
                                                    </c:if> 
                                                    <c:if test="${pList.holidayAction == '1'}">        
                                                        <td>At holiday</td>
                                                    </c:if> 
                                                    <c:if test="${pList.holidayAction == '2'}">        
                                                        <td>Next</td>
                                                    </c:if>                                                       
                                                    <td>${pList.statusDescripton}</td>

                                                    <td  ><a href='${pageContext.request.contextPath}/ViewMerchantPaymentCycleServlet?id=<c:out value="${pList.paymentCycleCode}"></c:out>'>View</a></td>
                                                    <td  ><a href='${pageContext.request.contextPath}/LoadUpdateMerchantPaymentCycleServlet?id=<c:out value="${pList.paymentCycleCode}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteBillingCycle('${pList.paymentCycleCode}')">Delete</a></td>
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
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

