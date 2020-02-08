<%-- 
    Document   : createfeeprofile
    Created on : Jan 10, 2012, 5:13:40 PM
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
            function feeResetBtn(isDefault,remove){
                window.location = "${pageContext.request.contextPath}/LoadFeeProfileAddServlet?isDefault="+isDefault+"&remove="+remove;
            }
            
            function invokeResetinUpdate(value,isDefault){
                window.location = "${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?id="+value+"&isDefault="+isDefault;
            }
            function backbtn(){
                window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet";
            }
            function feeUpdateView(value){
                window.location = "${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?id="+value;
            }
            
            function invokeRemove(feeCode,activity,opType,feeType){
                answer = confirm("Are you sure you want to remove?")
                   
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode="+feeCode+"&activity="+activity+"&opType="+opType+"&feeType="+feeType;
                }
                else
                {
                    document.updatefeeprofile.action="${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?remove="+value+"&isDefault=no";
                    document.updatefeeprofile.submit();
                    
                }
                
            }
            
            function invokeRemoveInAdd(feeCode,activity,opType,feeType){
                answer = confirm("Are you sure you want to remove?")
                   
                if (answer !=0)
                {
                    document.addfeeprofile.action="${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode="+feeCode+"&activity="+activity+"&opType="+opType+"&feeType="+feeType;
                    document.addfeeprofile.submit();
                    
                }
                else
                {
                    document.addfeeprofile.action="${pageContext.request.contextPath}/LoadFeeProfileAddServlet?feeCode="+feeCode+"&activity="+activity+"&opType="+opType+"&feeType="+feeType+"&remove=cancel&isDefault=no";
                    document.addfeeprofile.submit();
                }
                
            }
            
            function invokeUpdateTemp(feeCode,activity,opType,feeType){
                document.addfeeprofile.action="${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode="+feeCode+"&activity="+activity+"&opType="+opType+"&feeType="+feeType;
                document.addfeeprofile.submit();
                          
            }
            
            function invokeUpdateTempInUpdate(feeCode,activity,opType,feeType){
                document.updatefeeprofile.action="${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode="+feeCode+"&activity="+activity+"&opType="+opType+"&feeType="+feeType;
                document.updatefeeprofile.submit();
                          
            }
            
            function loadFeeTablesInAdd(){
                document.addfeeprofile.action="${pageContext.request.contextPath}/SetFeeTablesServlet";
                document.addfeeprofile.submit();
                
            }
            function loadFeeTablesInUpdate(){
                document.updatefeeprofile.action="${pageContext.request.contextPath}/SetFeeTablesServlet";
                document.updatefeeprofile.submit();
                
            }
            
            function addNewTxn(feeCode,opType,feeType){
                document.updatefeeprofile.action="${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode="+feeCode+"&opType="+opType+"&feeType="+feeType;
                document.updatefeeprofile.submit();
            }
            function addNewService(feeCode,opType,feeType){
                document.updatefeeprofile.action="${pageContext.request.contextPath}/TemporaryUpdateFeeServlet?feeCode="+feeCode+"&opType="+opType+"&feeType="+feeType;
                document.updatefeeprofile.submit();
            }
        </script>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.FEEPROFILE%>'                                  
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
        <div class="container" >
            <div class="header">
            </div>
            <div class="main" >
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>
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


                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" name="addfeeprofile" action="${pageContext.request.contextPath}/AddFeeProfileMgtServlet">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="hidden" value="add" name="opType"/></td>
                                                </tr>
                                                <tr>
                                                    <td >Fee Profile Code</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="feeProCode" maxlength="6" value="${sessionScope.SessionObject.feeBean.feeProCode}" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="feeProDes" value="${sessionScope.SessionObject.feeBean.feeProDes}" maxlength="64"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Fee Category</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;

                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory == 'MER'}">
                                                            <input type="radio" name="category" value="MER" checked="" />Merchant

                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory != 'MER'}">
                                                            <input type="radio" name="category" value="MER" onchange="loadFeeTablesInAdd()"/>Merchant

                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory == 'CRD'}">
                                                            <input type="radio" name="category" value="CRD" checked=""  />Card

                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory != 'CRD'}">
                                                            <input type="radio" name="category" value="CRD" onchange="loadFeeTablesInAdd()"/>Card

                                                        </c:if>

                                                    </td>

                                                </tr>
                                                
                                                <tr>
                                                    <td>Effective Date</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input  name="effectiveday" maxlength="16" readonly value="${sessionScope.SessionObject.feeBean.effectiveDate}" key="effectiveday" id="effectiveday"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#effectiveday").datepicker({
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
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="feeProStatus" style="width: 163px">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${sessionScope.SessionObject.feeBean.staus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${sessionScope.SessionObject.feeBean.staus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>

                                                <tr>
                                                    <td>Transaction</td>
                                                    <td></td>
                                                    <td >

                                                        <table border="1" class="display" id="tableview2" >
                                                            <thead >
                                                                <tr>
                                                                    <th>Fee Code</th>
                                                                    <th>Flat Fee</th>
                                                                    <th>Percentage</th>
                                                                    <th>Currency</th>
                                                                    <th>Update</th>
                                                                    <th>Remove</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${sessionScope.SessionObject.txnFeeList}" var="txnList" >
                                                                    <tr>
                                                                        <td>${txnList.feeDes}</td>
                                                                        <td>${txnList.flatFee}</td>
                                                                        <td>${txnList.percentage}</td>
                                                                        <td>${txnList.currency}</td>
                                                                        <td><a  href='#' onclick="invokeUpdateTemp('${txnList.feeCode}','update','add','${txnList.feeType}')">Update</a></td>
                                                                        <td><a  href='#' onclick="invokeRemoveInAdd('${txnList.feeCode}','remove','add','${txnList.feeType}')">Remove</a></td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Service</td>
                                                    <td></td>
                                                    <td>
                                                        <table border="1" class="display" id="tableview3">
                                                            <thead >
                                                                <tr>
                                                                    <th style="width: 300px;">Fee Code</th>
                                                                    <th>Flat Fee</th>
                                                                    <th>Percentage</th>
                                                                    <th>Currency</th>
                                                                    <th>Update</th>
                                                                    <th>Remove</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${sessionScope.SessionObject.serviceFeeList}" var="serviceList" >

                                                                    <tr>
                                                                        <td>${serviceList.feeDes}</td>
                                                                        <td>${serviceList.flatFee}</td>
                                                                        <td>${serviceList.percentage}</td>
                                                                        <td>${serviceList.currency}</td>
                                                                        <td><a  href='#' onclick="invokeUpdateTemp('${serviceList.feeCode}','update','add','${serviceList.feeType}')">Update</a></td>
                                                                        <td><a  href='#' onclick="invokeRemoveInAdd('${serviceList.feeCode}','remove','add','${serviceList.feeType}')">Remove</a></td>
                                                                    </tr>
                                                                </c:forEach>

                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;" colspan="2">
                                                        <input type="submit" value="Add" class="defbutton" name="Add" />
                                                        <input type="reset" class="defbutton" onclick="feeResetBtn('yes','nc')" value="Reset" />
                                                        <input type="button" value="Back" class="defbutton" onclick="backbtn()"/>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>

                                <!-- ------------------------------------------strat update--------------------------------------------------- -->
                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateFeeProfileMgtServlet" name="updatefeeprofile">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="hidden" name="opType" value="update"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Fee Profile Code</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" readonly="" name="feeProCode" maxlength="6" value="${sessionScope.SessionObject.feeBean.feeProCode}" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="feeProDes" value="${sessionScope.SessionObject.feeBean.feeProDes}" maxlength="64"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Fee Category</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory == 'MER'}">
                                                            <input type="radio" name="category" value="MER" checked="true" />Merchant
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory != 'MER'}">
                                                            <input type="radio" name="category" value="MER" onchange="loadFeeTablesInUpdate()"/>Merchant
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory == 'CRD'}">
                                                            <input type="radio" name="category" value="CRD" checked="true"  />Card
                                                        </c:if>
                                                        <c:if test="${sessionScope.SessionObject.feeBean.feeCategory != 'CRD'}">
                                                            <input type="radio" name="category" value="CRD" onchange="loadFeeTablesInUpdate()"/>Card
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Effective Date</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input  name="effectiveday" maxlength="16" readonly value="${sessionScope.SessionObject.feeBean.effectiveDate}" key="effectiveday" id="effectiveday"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#effectiveday").datepicker({
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
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="feeProStatus" style="width: 163px">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${sessionScope.SessionObject.feeBean.staus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${sessionScope.SessionObject.feeBean.staus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr></tr>

                                            </tbody>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>

                                                <tr>
                                                    <td>Transaction</td>
                                                    <td></td>
                                                    <td>
                                                        <table border="1" class="display" id="tableview2">
                                                            <thead>
                                                                <tr>
                                                                    <th>Fee Code</th>
                                                                    <th>Flat</th>
                                                                    <th>Percentage</th>
                                                                    <th>Currency</th>

                                                                    <th>Update</th>
                                                                    <th>Remove</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${sessionScope.SessionObject.txnFeeList}" var="txnList" >
                                                                    <tr>
                                                                        <td>${txnList.feeDes}</td>
                                                                        <td>${txnList.flatFee}</td>
                                                                        <td>${txnList.percentage}</td>
                                                                        <td>${txnList.currency}</td>

                                                                        <td><a  href='#' onclick="invokeUpdateTempInUpdate('${txnList.feeCode}','update','update','TXN')">Update</a></td>
                                                                        <td><a  href='#' onclick="invokeRemove('${txnList.feeCode}','remove','update','TXN')">Remove</a></td>
                                                                    </tr>
                                                                </c:forEach>

                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        Add New Transaction Fee &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <select name="unusedTxnFee" id="txn" style="width: 163px">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="txnfee" items="${unusedtxnFeeList}">
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.feeCode==txnfee.feeCode}">
                                                                    <option value="${txnfee.feeCode}" selected>${txnfee.feeDes}</option>
                                                                </c:if>
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.feeCode!=txnfee.feeCode}">
                                                                    <option value="${txnfee.feeCode}">${txnfee.feeDes}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button" value="Add" name="addnew" onclick="addNewTxn(txn.value,'newadd','TXN')" style="width: 100px"/>
                                                    </td>
                                                </tr>        
                                                <tr>
                                                    <td>Service</td>
                                                    <td></td>
                                                    <td>
                                                        <table border="1" class="display" id="tableview3">
                                                            <thead>
                                                                <tr>
                                                                    <th>Fee Code</th>
                                                                    <th>Flat</th>
                                                                    <th>Percentage</th>
                                                                    <th>Currency</th>

                                                                    <th>Update</th>
                                                                    <th>Remove</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${sessionScope.SessionObject.serviceFeeList}" var="serviceList" >

                                                                    <tr>
                                                                        <td>${serviceList.feeDes}</td>
                                                                        <td>${serviceList.flatFee}</td>
                                                                        <td>${serviceList.percentage}</td>
                                                                        <td>${serviceList.currency}</td>

                                                                        <td><a  href='#' onclick="invokeUpdateTempInUpdate('${serviceList.feeCode}','update','update','SER')">Update</a></td>
                                                                        <td><a  href='#' onclick="invokeRemove('${serviceList.feeCode}','remove','update','SER')">Remove</a></td>
                                                                    </tr>
                                                                </c:forEach>

                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        Add New Service Fee &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <select name="unusedServiceFee" id="ser" style="width: 163px">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="service" items="${unusedServiceFeeList}">
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.feeCode==service.feeCode}">
                                                                    <option value="${service.feeCode}" selected>${service.feeDes}</option>
                                                                </c:if>
                                                                <c:if test="${sessionScope.SessionObject.realFeeBean.feeCode!=service.feeCode}">
                                                                    <option value="${service.feeCode}">${service.feeDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button" value="Add" name="addnew" onclick="addNewService(ser.value,'newadd','SER')" style="width: 100px"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;" colspan="2">
                                                        <input type="submit" value="Update" class="defbutton" name="Update" />
                                                        <input type="reset" class="defbutton" onclick="invokeResetinUpdate('${sessionScope.SessionObject.feeBean.feeProCode}','yes')" value="Reset" />
                                                        <input type="button" value="Back" class="defbutton" onclick="backbtn()"/></td>                                                   
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='view'}" >
                                    <form>
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td>Fee Profile Code</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${feeProfBean.feeProCode}</td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${feeProfBean.feeProDes}</td>
                                                </tr>
                                                <c:if test="${feeProfBean.feeCategory=='CRD'}">
                                                    <tr>
                                                        <td>Fee Category</td>
                                                        <td></td>
                                                        <td>:</td>
                                                        <td>Card</td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${feeProfBean.feeCategory=='MER'}">
                                                    <tr>
                                                        <td>Fee Category</td>
                                                        <td></td>
                                                        <td>:</td>
                                                        <td>Merchant</td>
                                                    </tr>
                                                </c:if>
                                                
                                                <tr>
                                                    <td>Effective Date</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${feeProfBean.effectiveDate}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${feeProfBean.stausDes}</td>
                                                </tr>
                                                <tr>

                                                    <td colspan="4">
                                                        <table border="1" class="display" id="tableview1">
                                                            <thead>
                                                                <tr>
                                                                    <th>Fee Code</th>
                                                                    <th>Currency</th>
                                                                    <th>Cr/Dr</th>
                                                                    <th>Flat</th>
                                                                    <th>Percentage</th>
                                                                    <th>Combination</th>
                                                                    <th>Min Amount</th>
                                                                    <th>Max Amount</th>

                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${txnFeeBeanList}" var="feePfeeList" >
                                                                    <tr>
                                                                        <td>${feePfeeList.feeCode}</td>
                                                                        <td>${feePfeeList.currency}</td>
                                                                        <td>${feePfeeList.crordr}</td>
                                                                        <td>${feePfeeList.flatFee}</td>
                                                                        <td>${feePfeeList.percentage}</td>                                                      
                                                                        <td>${feePfeeList.option}</td>
                                                                        <td>${feePfeeList.minAmount}</td>
                                                                        <td>${feePfeeList.maxAmount}</td>
                                                                    </tr>
                                                                </c:forEach>

                                                            </tbody>
                                                        </table>

                                                    </td>
                                                </tr>
                                                <tr>

                                                    <td colspan="4">
                                                        <table border="1" class="display" id="tableview2">
                                                            <thead>
                                                                <tr>
                                                                    <th>Fee Code</th>
                                                                    <th>Currency</th>
                                                                    <th>Cr/Dr</th>
                                                                    <th>Flat</th>
                                                                    <th>Percentage</th>
                                                                    <th>Combination</th>
                                                                    <th>Min Amount</th>
                                                                    <th>Max Amount</th>

                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${serviceFeeBeanList}" var="feePfeeList" >
                                                                    <tr>
                                                                        <td>${feePfeeList.feeCode}</td>
                                                                        <td>${feePfeeList.currency}</td>
                                                                        <td>${feePfeeList.crordr}</td>
                                                                        <td>${feePfeeList.flatFee}</td>
                                                                        <td>${feePfeeList.percentage}</td>                                                      
                                                                        <td>${feePfeeList.option}</td>
                                                                        <td>${feePfeeList.minAmount}</td>
                                                                        <td>${feePfeeList.maxAmount}</td>
                                                                    </tr>
                                                                </c:forEach>

                                                            </tbody>
                                                        </table>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td align= "right"><input type="button" name="back" value="Back" onclick="backbtn()" class="defbutton" style=""/></td>
                                                </tr>
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
