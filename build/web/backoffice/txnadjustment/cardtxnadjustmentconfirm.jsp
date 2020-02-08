<%-- 
    Document   : cardtxnadjustmentconfirm
    Created on : Aug 12, 2016, 3:08:52 PM
    Author     : Badrika
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
            function BackToLoad() {
                window.location = "${pageContext.request.contextPath}/LoadEasyPaymentConfirmationServlet";
            }

            function viewCard(reqid) {
                window.location = "${pageContext.request.contextPath}/SearchCardTxnAdjustmentConfirm?reqid=" + reqid + "&view=view";
            }

            function ApproveInc() {
                document.approveform.action = "${pageContext.request.contextPath}/SearchCardTxnAdjustmentConfirm?view=approve";
                document.approveform.submit();
            }
            
            function Reject() {
                document.approveform.action = "${pageContext.request.contextPath}/SearchCardTxnAdjustmentConfirm?view=reject";
                document.approveform.submit();
            }
            
            function loadAdjustmentTypeList(){
                var optionVal = $('input[name=adjustmentType]:checked').val();
                $('#tx_type').empty();        
                $.getJSON("${pageContext.servletContext.contextPath}/GetAdjustmentTypeListServlet",      
                {              
                    adjustmentType : optionVal
                },
                function(jsonobject) {
                    $("#tx_type").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject, function(code, description) {
                        $('#tx_type').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CARD_TXN_ADJUSTMENT_CONFIRM%>'
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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='search'}" >
                                
                                 <!--<form method="POST" name="searchform" action="${pageContext.request.contextPath}/UpdateCardTxnAdjustment">-->
                                     <form method="POST" action="${pageContext.request.contextPath}/SearchCardTxnAdjustmentConfirm?view=search">
                                        <table border="0">


                                            <tbody>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Card Number </td>
                                                    <td><input type="text" maxlength="20" name="cardNumber" value="${taBean.uniqueId}" class="deftext"/></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td style="width: 200px;">Adjustment Type </td>

                                                    <td>                                      
                                                        <input type="radio" name="adjustmentType"  onclick="loadAdjustmentTypeList();" class="adjustmentType" value="1" 
                                                               <c:if test="${taBean.adjustmentType=='1'}">
                                                                   checked  
                                                               </c:if> />
                                                        Transaction
                                                        <input type="radio" name="adjustmentType" onclick="loadAdjustmentTypeList();" class="adjustmentType" value="2" 
                                                               <c:if test="${taBean.adjustmentType=='2'}">
                                                                   checked  
                                                               </c:if> />
                                                        Fee
                                                        <input type="radio" name="adjustmentType" onclick="loadAdjustmentTypeList();" class="adjustmentType" value="3" 
                                                               <c:if test="${taBean.adjustmentType=='3'}">
                                                                   checked  
                                                               </c:if> />
                                                        Interest
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Type </td>
                                                    <td>
                                                        <select  name="tx_type" id="tx_type" class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>

                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Amount </td>
                                                    <td><input type="text" maxlength="20" name="amount" value="${taBean.amount}" class="float_input"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 100px;">CR or DR</td>
                                                    <td>
                                                        <select  name="crOrdr"  class="inputfield-mandatory" style="width: 168px;">                                                         
                                                            <option value="" >--SELECT--</option>

                                                            <option value="CR"
                                                                    <c:if test="${taBean.crOrdr=='CR'}">
                                                                        selected
                                                                    </c:if>>CREDIT</option>

                                                            <option value="DR"
                                                                    <c:if test="${taBean.crOrdr=='DR'}">
                                                                        selected
                                                                    </c:if>>DEBIT</option>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 100px;">Currency Type </td>
                                                    <td>
                                                        <select name="currency">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="currency" items="${currencyList}">

                                                                <option value="${currency.currencyCode}" 
                                                                        <c:if test="${currency.currencyCode==taBean.currencyCode}">
                                                                            selected
                                                                        </c:if>>${currency.currencyDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Request Date From</td>
                                                    <td> <input  name="fromdate" readonly maxlength="16" value="${taBean.fromdate}" key="fromdate" id="fromdate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
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
                                                </tr>
                                                <tr>
                                                    <td>Request Date To</td>
                                                    <td><input  name="todate" readonly maxlength="16" value="${taBean.todate}" key="todate" id="todate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
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
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Search" name="search" class="defbutton"/>
                                                        <input onclick="resetForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                
                                
                                </c:if>

                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" name="approveform" action="${pageContext.request.contextPath}/SearchCardTxnAdjustmentConfirm?view=approve">
                                        <table border="0" cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Card Number </td>                                                    
                                                    <td>:${permBean2.uniqueId}</td>
                                                </tr>

                                                <tr>
                                                    <td>Adjustment Type</td>
                                                    <td>:${permBean2.adjustmentTypeDes}</td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Transaction Type</td>
                                                    <td>:${permBean2.transactionType}</td>
                                                </tr>

                                                <tr>
                                                    <td>Amount </td>                                  
                                                    <td>:${permBean2.amount}</td>
                                                </tr>

                                                <tr>
                                                    <td>CR or DR </td>                                  
                                                    <td>:${permBean2.crOrdr}</td>
                                                </tr>

                                                <tr>
                                                    <td>Requested User </td>                                  
                                                    <td>:${permBean2.requestedUser}</td>
                                                </tr>                                                
                                                
                                                <tr>
                                                    <td>Currency Type </td>                                  
                                                    <td>:${permBean2.currencyDes}</td>
                                                </tr> 
                                                
                                                <tr>
                                                        <td>Remarks</td>
                                                        <td><textarea id="remarks" style="" name="remarks" value="${permBean2.remarks}" >${permBean2.remarks}</textarea></td>
                                                    </tr>

                                                <tr hidden="">
                                                    <td><input type="text" name="cardNumber" value="${permBean2.uniqueId}" hidden=""/></td>                                                    
                                                    <td><input type="text" name="cardNumber" value="${permBean2.id}" hidden=""/></td>                                                    
                                                                                                          
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Approve" name="Approve" class="defbutton" onclick="ApproveInc()" />
                                                        <input type="button" value="Reject" name="reject" class="defbutton" onclick="Reject()"/>
                                                        <!--<input type="button" value="Back" name="Back" class="defbutton" onclick="BackToLoad()"/>-->
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>



                                <!-- show table data -->
                                <br/>
                                
                                <div id="demo_jui">
                                    <table  border="1"  class="display" id="scoreltableview5">
                                        <thead>
                                            <tr>
                                                <td>id</td>
                                                <td>Adjustment Party</td>
                                                <td>Amount</td>
                                                <td>Trace No</td>
                                                <td>Status</td>
                                                <td>Requested User</td>
                                                <td>Approved User</td>        
                                                <td>Updated User</td>
                                                <td>Card Number (UID)</td>
                                                <td>Expiry Date (VV)</td>
                                                <td>Currency Code</td>
                                                <td>Credit or Debit</td>
                                                <th>Adjustment Type</th>
                                                <th>Transaction Type</th>
                                                <th>View</th>
                                                <!--<th>Update</th>-->


                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="adjustment" items="${requestList}">
                                                <tr>
                                                    <td>${adjustment.id}</td>
                                                    <td>${adjustment.adjustmentParty}</td>
                                                    <td>${adjustment.amount}</td>
                                                    <td>${adjustment.traceNo}</td>
                                                    <td>${adjustment.statusDes}</td>
                                                    <td>${adjustment.requestedUser}</td>
                                                    <td>${adjustment.approvedUser}</td>        
                                                    <td>${adjustment.updatedUser}</td>
                                                    <td>${adjustment.uniqueId}</td>
                                                    <td>${adjustment.verificationValue}</td>
                                                    <td>${adjustment.currencyDes}</td>
                                                    <td>${adjustment.crOrdr}</td>
                                                    <td>${adjustment.adjustmentTypeDes}</td>
                                                    <td>${adjustment.transactionType}</td>
                                                    <td><a  href='#' onclick="viewCard('${adjustment.id}')">View</a></td>
                                                    <!--<td><a href='${pageContext.request.contextPath}/ViewTransactionAdjustmentServlet?id=<c:out value="${adjustment.id}"></c:out>'>View</a></td>-->
                                                    <!--<td><a href='${pageContext.request.contextPath}/LoadUpdateTxnAdjustmentServlet?id=<c:out value="${adjustment.id}"></c:out>'>Update</a></td>--> 
                                                    </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 
                                </div>


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



