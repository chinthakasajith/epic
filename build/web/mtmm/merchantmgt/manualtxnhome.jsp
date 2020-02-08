<%-- 
    Document   : manualtxnhome
    Created on : Dec 18, 2012, 5:17:41 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Merchant Management Page</title>

        <jsp:include page="/content.jsp"/>

        <script >
            
            function selectAllTxn(selectBoxTxn) {
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                }       
                invokeAdd();
            }
            
            function invokeAdd()
            {

                document.manualTxnForm.action="${pageContext.request.contextPath}/AddMerchantLocationManualTxnServlet";
                document.manualTxnForm.submit();
            }
            
              
            function selectAllTxnUpdate(selectBoxTxn) {
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                }       
                invokeUpdate();
            }
            
            function invokeUpdate()
            {

                document.manualTxnFormUpdate.action="${pageContext.request.contextPath}/UpdateMerchantLocationManualTxnServlet";
                document.manualTxnFormUpdate.submit();
            }
            
            function invokeReset(ele){

                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                        
                    }
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }
                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';

            }
            
            function invokeResetUpdate(){
            
                window.location = "${pageContext.request.contextPath}/LoadUpdateMerchantLocationManualTxnServlet";
            }
            
            
        </script>    
    </head>
    <body >
        <div class="container">
            <div class="header">
            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>
                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>

                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  -----------------------------------------------------------------   start  developer area  ----------------------------------------------------------                          -->

                                <table class="tit"> <tr> <td   class="center"> <h3>MERCHANT LOCATION MANAGEMENT - MANUAL TRANSACTION</h3></td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <br/>
                                <c:if test="${operationtype=='ADD'}">   
                                    <form name="manualTxnForm" method="post" action="">

                                        <table>
                                            <tbody>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Risk Profile
                                                    </td>

                                                    <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select  name="riskProfCode"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="risk" items="${riskProfileList}">
                                                                <c:if test="${manualBean.riskProfCode==risk.riskProfCode}">
                                                                    <option value="${risk.riskProfCode}" selected="true" >${risk.description}</option>
                                                                </c:if>
                                                                <c:if test="${manualBean.riskProfCode!=risk.riskProfCode}">
                                                                    <option value="${risk.riskProfCode}">${risk.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Merchant Category
                                                    </td>

                                                    <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select  name="mcc"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="mcc" items="${merchantCategoryList}">
                                                                <c:if test="${manualBean.mcc==mcc.mCCCode}">
                                                                    <option value="${mcc.mCCCode}" selected="true" >${mcc.description}</option>
                                                                </c:if>
                                                                <c:if test="${manualBean.mcc!=mcc.mCCCode}">
                                                                    <option value="${mcc.mCCCode}">${mcc.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Currency
                                                    </td>

                                                    <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select  name="currency"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${currencyList}">
                                                                <c:if test="${manualBean.currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${manualBean.currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td colspan="0">Select Transaction Types <B> <c:out value="${txn}"/></B></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4><b>All Transaction Types</b></h4>
                                                    <select name="txnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                        <c:forEach  var="txn" items="${typeList}">
                                                            <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td align="center" >
                                                    <input type=button value=">" style="width: 50px;" onclick=moveout2() class="buttonsize"><br>
                                                    <input type=button value="<" style="width: 50px;" onclick=movein2() class="buttonsize"><br>
                                                    <input type=button value=">>" style="width: 75px;" onclick=moveallout2() class="buttonsize"><br>
                                                    <input type=button value="<<" style="width: 75px;" onclick=moveallin2() class="buttonsize">
                                                </td>
                                                <td>
                                                    <h4><b>Assigned Transaction Types</b></h4>
                                                    <select name="assignTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>

                                                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td><input type="submit" name="add"  style="width: 100px; height: 30px;" onclick="selectAllTxn(assignTxnTypeList)" value="Save">
                                                    <input type="button" value="Reset " name="backtab" style="width: 100px; height: 30px;" onclick="invokeReset(this.form)" >

                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='UPDATE'}">   
                                    <form name="manualTxnFormUpdate" method="post" action="">

                                        <table>
                                            <tbody>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Risk Profile
                                                    </td>

                                                    <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;" name="riskProfCode"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="risk" items="${riskProfileList}">
                                                                <c:if test="${manualBean.riskProfCode==risk.riskProfCode}">
                                                                    <option value="${risk.riskProfCode}" selected="true" >${risk.description}</option>
                                                                </c:if>
                                                                <c:if test="${manualBean.riskProfCode!=risk.riskProfCode}">
                                                                    <option value="${risk.riskProfCode}">${risk.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Merchant Category
                                                    </td>

                                                    <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;" name="mcc"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="mcc" items="${merchantCategoryList}">
                                                                <c:if test="${manualBean.mcc==mcc.mCCCode}">
                                                                    <option value="${mcc.mCCCode}" selected="true" >${mcc.description}</option>
                                                                </c:if>
                                                                <c:if test="${manualBean.mcc!=mcc.mCCCode}">
                                                                    <option value="${mcc.mCCCode}">${mcc.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Currency
                                                    </td>

                                                    <td style="width: 200px;"><font style="color: red;">*</font>&nbsp;<select style="width: 150px;" name="currency"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${currencyList}">
                                                                <c:if test="${manualBean.currency==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${manualBean.currency!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td> 
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td colspan="0">Select Transaction Types <B> <c:out value="${txn}"/></B></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4><b>All Transaction Types</b></h4>
                                                    <select name="txnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                        <c:forEach  var="txn" items="${notAssignedTxnTypeList}">
                                                            <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td align="center" >
                                                    <input type=button value=">" style="width: 50px;" onclick=moveout2() class="buttonsize"><br>
                                                    <input type=button value="<" style="width: 50px;" onclick=movein2() class="buttonsize"><br>
                                                    <input type=button value=">>" style="width: 75px;" onclick=moveallout2() class="buttonsize"><br>
                                                    <input type=button value="<<" style="width: 75px;" onclick=moveallin2() class="buttonsize">
                                                </td>
                                                <td>
                                                    <h4><b>Assigned Transaction Types</b></h4>
                                                    <select name="assignTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>
                                                        <c:forEach  var="txn" items="${assignedTxnTypeList}">
                                                            <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td><input type="submit" name="update"  style="width: 100px; height: 30px;" onclick="selectAllTxnUpdate(assignTxnTypeList)" value="Save">
                                                    <input type="button" value="Reset " name="backtab" style="width: 100px; height: 30px;" onclick="invokeResetUpdate()" >

                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </c:if>

                                <!------------------------------------------------  End Developer Area  --------------------------------------------------------------->
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