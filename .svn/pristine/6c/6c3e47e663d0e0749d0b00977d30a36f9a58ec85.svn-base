<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>


<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.min.js"></script>


        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
            
            function invokeAssignTxn(opType)
            {
                if(opType=='ADD'){
                    
                    document.addCommissionProfile.action="${pageContext.request.contextPath}/AssignCommissionProfileTxnServlet?opType="+opType;
                    document.addCommissionProfile.submit();
                }
                if(opType=='UPDATE'){
                    document.updateCommissionProfile.action="${pageContext.request.contextPath}/AssignCommissionProfileTxnServlet?opType="+opType;
                    document.updateCommissionProfile.submit();  
                }

            }
            
            function invokeAdd()
            {

                document.addCommissionProfile.action="${pageContext.request.contextPath}/AddCommissionProfileServlet";
                document.addCommissionProfile.submit();

            }
            
            function invokeUpdate()
            {

                document.updateCommissionProfile.action="${pageContext.request.contextPath}/UpdateCommissionProfileServlet";
                document.updateCommissionProfile.submit();

            }
            
            function invokeEdit(txnCode,opType)
            {

                if(opType=='ADD'){
                    document.addCommissionProfile.action="${pageContext.request.contextPath}/LoadEditCommissionProfileTxnServlet?txnCode="+txnCode+"&opType="+opType;
                    document.addCommissionProfile.submit();
                }
                if(opType=='UPDATE'){
                    document.updateCommissionProfile.action="${pageContext.request.contextPath}/LoadEditCommissionProfileTxnServlet?txnCode="+txnCode+"&opType="+opType;
                    document.updateCommissionProfile.submit();
                }

            }
            function invokeTxnDelete(txnCode,opType)
            {

                window.location="${pageContext.request.contextPath}/RemoveCommissionProfileTxnServlet?txnCode="+txnCode+"&opType="+opType;

            }
            function invokeBack()
            {

                window.location="${pageContext.request.contextPath}/LoadCommisionProfileServlet";

            }
            function invokeUpdateReset(profileCode)
            {

                window.location="${pageContext.request.contextPath}/LoadUpdateCommissionProfileServlet?profileCode="+profileCode;

            }
            function invokeLoadUpdate(profileCode)
            {

                window.location="${pageContext.request.contextPath}/LoadUpdateCommissionProfileServlet?profileCode="+profileCode;

            }
            function invokeView(profileCode)
            {

                window.location="${pageContext.request.contextPath}/ViewCommisionProfileServlet?profileCode="+profileCode;

            }
            function ConfirmDelete(profileCode)
            {
                answer = confirm("Are you sure you want to delete this Profile?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteCommissionProfileServlet?profileCode="+profileCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCommisionProfileServlet";
                }
                
            }
            
        </script>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.COMMISSION_PROFILE%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                
           
                                            
        </script>  
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
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


                                <br/>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!-- ///////////////////////////////////////Start Add Area  ////////////////////////////////////////////////////////// -->

                                <c:if test="${operationtype=='ADD'}" >
                                    <form method="POST" name="addCommissionProfile" action="">

                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Commission Profile Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="comProfileCode" class="inputfield-mandatory" maxlength="8" value='${comisBean.comProfileCode}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-mandatory" maxlength="50" value='${comisBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="currencyCode"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">

                                                                <c:if test="${comisBean.currencyCode==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${comisBean.currencyCode!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${comisBean.crOrdr == 'CR'}">
                                                            <input type="radio" name="crOrdr" checked="true" value="CR" /> Credit 
                                                        </c:if>
                                                        <c:if test="${comisBean.crOrdr != 'CR'}">
                                                            <input type="radio" name="crOrdr" checked="true"  value="CR" /> Credit
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.crOrdr == 'DR'}">
                                                            <input type="radio" name="crOrdr" checked="true"  value="DR" /> Debit
                                                        </c:if>
                                                        <c:if test="${comisBean.crOrdr != 'DR'}">
                                                            <input type="radio" name="crOrdr"   value="DR" /> Debit
                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td>Flat Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="flatAmount" class="inputfield-mandatory" maxlength="11" value='${comisBean.flatAmount}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="percentage" class="inputfield-mandatory" maxlength="5" value='${comisBean.percentage}'> %</td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${comisBean.combination == 'MIN'}">
                                                            <input type="radio" name="combination" checked="true" value="MIN" /> Minimum
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'MIN'}">
                                                            <input type="radio" name="combination" checked="true"  value="MIN" /> Minimum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.combination == 'MAX'}">
                                                            <input type="radio" name="combination"  checked="true" value="MAX" /> Maximum 
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'MAX'}">
                                                            <input type="radio" name="combination"   value="MAX" /> Maximum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.combination == 'ADD'}">
                                                            <input type="radio" name="combination" checked="true"  value="ADD" /> ADD
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'ADD'}">
                                                            <input type="radio" name="combination"  value="ADD" /> ADD
                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td> <font style="color: red;">*</font>&nbsp;<select  name="status"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${comisBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${comisBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="assign" value="Assign TXN" onclick="invokeAssignTxn('${"ADD"}')" /></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>


                                    <!--/////////////////////////////////// Start Transaction Table Template  ///////////////////////////////////////////////////////////-->

                                    <br></br>
                                    <fieldset id="fieldset" style=" width:850px; " enabled>
                                        <legend> </legend> 
                                        <table  border="1"  class="display" id="tableview2">
                                            <thead>
                                                <tr class="gradeB">
                                                    <th>Transaction</th>
                                                    <th>Credit Or Debit</th>
                                                    <th>Flat Amount</th>
                                                    <th>Percentage (%)</th>
                                                    <th>Combination</th>

                                                    <th>Edit</th>
                                                    <th>Remove</th>
                                                </tr>
                                            </thead>
                                            <tbody >
                                                <c:forEach var="comisTxn" items="${sessionScope.SessionObject.commissionTxnList}">
                                                    <tr>
                                                        <td >${comisTxn.txnDes}</td>
                                                        <td ><c:if test="${comisTxn.crOrdr=='CR'}">Credit</c:if><c:if test="${comisTxn.crOrdr=='DR'}">Debit</c:if></td>
                                                        <td >${comisTxn.flatAmount}</td>
                                                        <td >${comisTxn.percentage}</td>
                                                        <td ><c:if test="${comisTxn.combination=='MIN'}">Minimum</c:if><c:if test="${comisTxn.combination=='MAX'}">Maximum</c:if><c:if test="${comisTxn.combination=='ADD'}">ADD</c:if></td>

                                                                    <td  ><a href='#' onclick="invokeEdit('${comisTxn.txnType}','${"ADD"}')">Edit</a></td>
                                                        <td ><a  href='#' onclick="invokeTxnDelete('${comisTxn.txnType}','${"ADD"}')">Remove</a></td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>  
                                    </fieldset>
                                    <!--////////////////////////////////// End Transaction Table Template  ///////////////////////////////////////////////////////////-->

                                    <table cellpadding="0" cellspacing="10">
                                        <tr></tr>
                                        <tr><td width ="200px;"></td>
                                            <td><input type="submit" style="width: 100px" name="add" value="Add" onclick="invokeAdd()" /></td>
                                            <td><input type="reset" style="width: 100px" name="reset" value="Reset" onclick="invokeBack()" /></td>
                                        </tr>
                                    </table>


                                </c:if>
                                <!--   ////////////////////////////  End Add Area  /////////////////////////////////////////////////////  -->


                                <!-- ///////////////////////////  Update Area /////////////////////////////////////////////////////////// -->

                                <c:if test="${operationtype=='UPDATE'}" >

                                    <form method="POST" name="updateCommissionProfile" action="">

                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Commission Profile Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input readonly="readonly" type="text" name="comProfileCode" class="inputfield-mandatory" maxlength="8" value='${comisBean.comProfileCode}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-mandatory" maxlength="50" value='${comisBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="currencyCode"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${comisBean.currencyCode==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${comisBean.currencyCode!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${comisBean.crOrdr == 'CR'}">
                                                            <input type="radio" name="crOrdr" checked="true" value="CR" /> Credit 
                                                        </c:if>
                                                        <c:if test="${comisBean.crOrdr != 'CR'}">
                                                            <input type="radio" name="crOrdr" checked="true"  value="CR" /> Credit
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.crOrdr == 'DR'}">
                                                            <input type="radio" name="crOrdr" checked="true"  value="DR" /> Debit
                                                        </c:if>
                                                        <c:if test="${comisBean.crOrdr != 'DR'}">
                                                            <input type="radio" name="crOrdr"   value="DR" /> Debit
                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td>Flat Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="flatAmount" class="inputfield-mandatory" maxlength="11" value='${comisBean.flatAmount}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="percentage" class="inputfield-mandatory" maxlength="5" value='${comisBean.percentage}'> %</td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${comisBean.combination == 'MIN'}">
                                                            <input type="radio" name="combination" checked="true" value="MIN" /> Minimum
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'MIN'}">
                                                            <input type="radio" name="combination" checked="true"  value="MIN" /> Minimum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.combination == 'MAX'}">
                                                            <input type="radio" name="combination"  checked="true" value="MAX" /> Maximum 
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'MAX'}">
                                                            <input type="radio" name="combination"   value="MAX" /> Maximum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.combination == 'ADD'}">
                                                            <input type="radio" name="combination" checked="true"  value="ADD" /> ADD
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'ADD'}">
                                                            <input type="radio" name="combination"  value="ADD" /> ADD
                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td> <font style="color: red;">*</font>&nbsp;<select name="status"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${comisBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${comisBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="assign" value="Assign TXN" onclick="invokeAssignTxn('${"UPDATE"}')" /></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>


                                    <!--/////////////////////////////////////////////Start Transaction Table Template  ///////////////////////////////////////////////////////////-->

                                    <br></br>
                                    <fieldset id="fieldset" style=" width:850px; " enabled>
                                        <legend> </legend> 
                                        <table  border="1"  class="display" id="tableview2">
                                            <thead>
                                                <tr class="gradeB">
                                                    <th>Transaction</th>
                                                    <th>Credit Or Debit</th>
                                                    <th>Flat Amount</th>
                                                    <th>Percentage (%)</th>
                                                    <th>Combination</th>

                                                    <th>Edit</th>
                                                    <th>Remove</th>
                                                </tr>
                                            </thead>
                                            <tbody >
                                                <c:forEach var="comisTxn" items="${sessionScope.SessionObject.commissionTxnList}">
                                                    <tr>
                                                        <td >${comisTxn.txnDes}</td>
                                                        <td ><c:if test="${comisTxn.crOrdr=='CR'}">Credit</c:if><c:if test="${comisTxn.crOrdr=='DR'}">Debit</c:if></td>
                                                        <td >${comisTxn.flatAmount}</td>
                                                        <td >${comisTxn.percentage}</td>
                                                        <td ><c:if test="${comisTxn.combination=='MIN'}">Minimum</c:if><c:if test="${comisTxn.combination=='MAX'}">Maximum</c:if><c:if test="${comisTxn.combination=='ADD'}">ADD</c:if></td>

                                                                    <td  ><a href='#' onclick="invokeEdit('${comisTxn.txnType}','${"UPDATE"}')">Edit</a></td>
                                                        <td ><a  href='#' onclick="invokeTxnDelete('${comisTxn.txnType}','${"UPDATE"}')">Remove</a></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>  
                                    </fieldset>
                                    <!--/////////////////////////////////////////////End Transaction Table Template  ///////////////////////////////////////////////////////////-->

                                    <table cellpadding="0" cellspacing="10">
                                        <tr></tr>
                                        <tr><td width ="200px;"></td>
                                            <td><input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdate()" /></td>
                                            <td><input type="reset" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${comisBean.comProfileCode}')" /></td>
                                        </tr>
                                    </table>

                                </c:if>


                                <!-- //////////////////////////////////// End Update Area  //////////////////////////////////////////////// -->


                                <!-- //////////////////////////////////// Start View Are  /////////////////////////////////////////////////  -->


                                <c:if test="${operationtype=='VIEW'}">

                                    <form name="viewCommissionProfileForm" method="POST">

                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Commission Profile Code</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.comProfileCode}</td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.description}</td>
                                                </tr>

                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.currencyDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Credit or Debit</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td><c:if test="${comisBean.crOrdr=='CR'}">Credit</c:if><c:if test="${comisBean.crOrdr=='DR'}">Debit</c:if></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Flat Amount</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                            <td>${comisBean.flatAmount}</td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.percentage} %</td>
                                                </tr>

                                                <tr>
                                                    <td>Combination</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td><c:if test="${comisBean.combination=='MIN'}">Minimum</c:if><c:if test="${comisBean.combination=='MAX'}">Maximum</c:if><c:if test="${comisBean.combination=='ADD'}">ADD</c:if></td>
                                                    </tr>

                                                    <tr>
                                                        <td>Status</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                                <td>${comisBean.statusDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Last Updated User</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.lastUpdatedUser}</td>
                                                </tr>

                                                <tr>
                                                    <td>Last Updated Date</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.lastUpdatedTime}</td>
                                                </tr>

                                                <tr>
                                                    <td>Created Date</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${comisBean.createdTime}</td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                    <!--/////////////////////////////////////////////Start Transaction Table Template  ///////////////////////////////////////////////////////////-->

                                    <br></br>
                                    <fieldset id="fieldset" style=" width:850px; " enabled>
                                        <legend> </legend> 
                                        <table  border="1"  class="display" id="tableview2">
                                            <thead>
                                                <tr class="gradeB">
                                                    <th>Transaction</th>
                                                    <th>Credit Or Debit</th>
                                                    <th>Flat Amount</th>
                                                    <th>Percentage (%)</th>
                                                    <th>Combination</th>

                                                </tr>
                                            </thead>
                                            <tbody >
                                                <c:forEach var="comisTxn" items="${sessionScope.SessionObject.commissionTxnList}">
                                                    <tr>
                                                        <td >${comisTxn.txnDes}</td>
                                                        <td ><c:if test="${comisTxn.crOrdr=='CR'}">Credit</c:if><c:if test="${comisTxn.crOrdr=='DR'}">Debit</c:if></td>
                                                        <td >${comisTxn.flatAmount}</td>
                                                        <td >${comisTxn.percentage}</td>
                                                        <td ><c:if test="${comisTxn.combination=='MIN'}">Minimum</c:if><c:if test="${comisTxn.combination=='MAX'}">Maximum</c:if><c:if test="${comisTxn.combination=='ADD'}">ADD</c:if></td>

                                                        </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>  
                                    </fieldset>
                                    <!--/////////////////////////////////////////////End Transaction Table Template  ///////////////////////////////////////////////////////////-->

                                    <table cellpadding="0" cellspacing="10">
                                        <tr></tr>
                                        <tr><td width ="200px;"></td>
                                            <td><input type="button" style="width: 100px" name="back" value="Back" onclick="invokeBack()" /></td> </tr>
                                    </table>

                                </c:if>


                                <!-- //////////////////////////////////// End View Are  /////////////////////////////////////////////////  -->

                                <!--//////////////////////////////////// Start Common Table Template  ///////////////////////////////////////////////////////////-->

                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Commission Profile Code</th>
                                            <th>Description</th>
                                            <th>Currency</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="comis" items="${comisList}">
                                            <tr>
                                                <td >${comis.comProfileCode}</td>
                                                <td >${comis.description}</td>
                                                <td >${comis.currencyDes}</td>
                                                <td >${comis.statusDes}</td>

                                                <td><a href='#' onclick="invokeView('${comis.comProfileCode}')">View</a></td>
                                                <td><a href='#' onclick="invokeLoadUpdate('${comis.comProfileCode}')">Update</a></td>
                                                <td><a href='#' onclick="ConfirmDelete('${comis.comProfileCode}')">Delete</a></td>

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
