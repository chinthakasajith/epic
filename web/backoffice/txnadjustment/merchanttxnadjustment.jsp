<%-- 
    Document   : merchanttxnadjustment
    Author     : ruwan_e
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

        <script type="text/javascript" src="jquery.numeric.js"></script>
        <script type="text/javascript">
     
            function validateAmount(amount) 
            { 
                $('#amt_msg').html(amount);
            }
            
            
            function loadAdjustmentTypeListUpdate(adjustmentType, transactionType){
                $('#tx_type').empty();        
                $.getJSON("${pageContext.servletContext.contextPath}/GetAdjustmentTypeListServlet",      
                {              
                    adjustmentType : adjustmentType
                },
                function(jsonobject) {
                    $("#tx_type").append("<option value=''>---------SELECT----------</option>");
                    $.each(jsonobject, function(code, description) {
            
                        if(transactionType == code){
                            $('#tx_type').append(
                            $('<option selected></option>').val(code).html(description)
                        );
                        } else{
                            $('#tx_type').append(
                            $('<option></option>').val(code).html(description)
                        );
                        }
                    });
                });
              
            }
            
            function loadAdjustmentTypeList(){
                document.getElementById("tx_type").disabled=false;
                $("#tx_type").empty();
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
            
            function disableTypeList(){
                $('#tx_type').empty(); 
                document.getElementById("tx_type").disabled=true;
            }
            
            $(document).ready(function () {
                
                $("#transaction_adjustments").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "sDom": 'W<"clear">lfrtip'
                    //                    "aoColumnDefs": [{'bSortable': false, 'aTargets': [0, 6, 7]}]
                });
                
                $(".ui-datepicker-calendar").hide();
                loadAdjustmentTypeList();
                
            });
        </script>

        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.MERCHANT_TXN_ADJUSTMENT%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
            
            function resetForm(){
                window.location = "${pageContext.request.contextPath}/LoadMerchantTxnAdjustment";
            }
        </script>


        <style>

        </style>
    </head>
    <body onload="
          <c:if test="${operation_type=='update' || !valid}">
              loadAdjustmentTypeListUpdate('${taBean.adjustmentType}','${taBean.transactionType}')
          </c:if>   
          ">
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >
            <div class="header">

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


                                <table class="tit"> 
                                    <tr> 
                                        <td   >MERCHANT TRANSACTION</td> 
                                    </tr>
                                    <tr> 
                                        <td>&nbsp;</td> 
                                    </tr>
                                </table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td><span id="amt_msg" style="color: red"/></td>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <!--  ----------------------ADD  ------------------------------------->
                                <c:if test="${operation_type=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddMerchantTxnAdjustment">
                                        <table border="0">


                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="15" name="merchatnID" value="${taBean.uniqueId}" class="deftext"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Terminal ID</td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="8" name="terminalID" value="${taBean.verificationValue}" class="deftext"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Adjustment Type </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>

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
                                                    <input id="commission" type="radio" name="adjustmentType" class="adjustmentType" onclick="disableTypeList();" value="4" 
                                                           <c:if test="${taBean.adjustmentType=='4'}">
                                                               checked  
                                                           </c:if> />
                                                    Commission
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Type </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="tx_type" id="tx_type" class="inputfield-mandatory" style="width: 168px;">

                                                    </select>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Amount </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="20" name="amount" value="${taBean.amount}" class="float_input"/></td>
                                            </tr>


                                            <tr>
                                                <td style="width: 100px;">CR or DR</td>
                                                <td><font style="color: red;">*</font></td>
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
                                                <td><font style="color: red;">*</font></td>
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
                                                <td style="width: 200px;">Trace Number </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="20" name="traceNumber" value="${taBean.traceNo}" class="deftext"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Remarks </td>
                                                <td>&nbsp;</td>
                                                <td><textarea rows="4" cols="50" maxlength="250" name="remarks" value="${taBean.remarks}" class="deftext"></textarea></td>
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> 
                                                    <input type="submit" value="Add" name="add" class="defbutton"/>
                                                    <input onclick="resetForm()" type="reset" value="Reset" class="defbutton"/>
                                                </td>

                                            </tr>
                                        </table>

                                    </form>
                                </c:if>

                                <!--  ----------------------UPDATE  ------------------------------------->
                                <c:if test="${operation_type=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateMerchantTxnAdjustment">
                                        <table border="0">

                                            <tr>
                                                <td style="width: 200px;">Adjustment ID</td>
                                                <td></td>
                                                <td><input type="text" disabled="true" maxlength="15"  value="${taBean.id}" class="deftext"/>
                                                    <input type="text" hidden="true" maxlength="15" name="adjustmentID" value="${taBean.id}" class="deftext"/>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="15" name="merchatnID" value="${taBean.uniqueId}" class="deftext"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Terminal ID</td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="8" name="terminalID" value="${taBean.verificationValue}" class="deftext"/></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Adjustment Type </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>

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
                                                    <input id="commission" type="radio" name="adjustmentType" class="adjustmentType" onclick="disableTypeList();" value="4" 
                                                           <c:if test="${taBean.adjustmentType=='4'}">
                                                               checked  
                                                           </c:if> />
                                                    Commission
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Type </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="tx_type" id="tx_type" class="inputfield-mandatory" style="width: 168px;">

                                                    </select>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Amount </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="20" name="amount" value="${taBean.amount}" class="float_input"/></td>
                                            </tr>


                                            <tr>
                                                <td style="width: 100px;">CR or DR</td>
                                                <td><font style="color: red;">*</font></td>
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
                                                <td style="width: 100px;">Status </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${cardTransactionStatus}">
                                                            <option value="${status.key}"
                                                                    <c:if test="${status.key==taBean.status}">
                                                                        selected
                                                                    </c:if>
                                                                    >${status.value}</option>
                                                        </c:forEach>
                                                    </select>

                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 100px;">Currency Type </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="currency">
                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="currency" items="${currencyList}">

                                                            <option value="${currency.currencyCode}" 
                                                                    <c:if test="${currency.currencyCode==taBean.currencyCode}">
                                                                        selected
                                                                    </c:if>
                                                                    >${currency.currencyDes}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Trace Number </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="20" name="traceNumber" value="${taBean.traceNo}" class="deftext"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Remarks </td>
                                                <td>&nbsp;</td>
                                                <td><textarea rows="4" cols="50" maxlength="250" name="remarks" value="${taBean.remarks}" class="deftext"></textarea></td>
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> 
                                                    <input type="submit" value="Update" name="update" class="defbutton"/>
                                                    <input onclick="resetForm()" type="reset" value="Reset" class="defbutton"/>
                                                </td>

                                            </tr>
                                        </table>

                                    </form>
                                </c:if>




                                <br/>

                                <div id="demo_jui">
                                    <table  border="1"  class="display" id="scoreltableview5">
                                        <thead>
                                            <tr>
                                                <td>ID</td>
                                                <td>Amount</td>
                                                <td>Trace No</td>
                                                <td>Status</td>
                                                <td>Requested User</td>
                                                <td>Approved User</td>        
                                                <td>Updated User</td>
                                                <td>Merchant ID (UID)</td>
                                                <td>Terminal ID (VV)</td>
                                                <td>Currency Code</td>
                                                <td>Credit or Debit</td>
                                                <th>Adjustment Type</th>
                                                <th>Transaction Type</th>
                                                <th>View</th>
                                                <th>Update</th>


                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="adjustment" items="${merchantTransactionAdjustments}">
                                                <tr>
                                                    <td>${adjustment.id}</td>
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
                                                    <td><a href='${pageContext.request.contextPath}/ViewMercTransactionAdjustmentServlet?id=<c:out value="${adjustment.id}"></c:out>'>View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateMercTxnAdjustmentServlet?id=<c:out value="${adjustment.id}"></c:out>'>Update</a></td> 
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

