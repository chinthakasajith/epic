<%-- 
    Document   : bulkcardrequesthome
    Created on : Sep 7, 2012, 10:37:56 AM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>

<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            
            
            function invokeResetInUpdate(id,status)
            {
                
                window.location = "${pageContext.request.contextPath}/LoadUpdateBulkCardRequestServlet?id="+id+"&status="+status;
                
            }
            
            function loadCdProduct(value)
            {
                
                if($("#domain").val() != null && $("#type").val() != null){
                    if(value == 'add')
                    {
                        document.addform.action="${pageContext.request.contextPath}/SetCardProductServlet";
                        document.addform.submit();
                    }
                    if(value == 'update')
                    {
                        document.updateform.action="${pageContext.request.contextPath}/SetCardProductServlet";
                        document.updateform.submit();
                    }
                }else if($("#domain").val() == null || $("#type").val() == null){
                    
                    alert("You should select both");
                }
                
            }
            
            function invokeReset()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadBulkCardRequestServlet";
                
            }
            
            
            function loadCurrency(value)
            {
                
                if(value=='add')
                {
                    document.addform.action="${pageContext.request.contextPath}/SetCurrencyServlet";
                    document.addform.submit();
                }
                if(value=='update')
                {
                    document.updateform.action="${pageContext.request.contextPath}/SetCurrencyServlet";
                    document.updateform.submit();
                }
                
            }
            
//            function loadProductionMode(value)
//            {
//                
//                if(value=='add')
//            
//                {
//                    document.addform.action="${pageContext.request.contextPath}/SetProductionModeServlet";
//                    document.addform.submit();
//                }
//                if(value=='update')
//            
//                {
//                    document.updateform.action="${pageContext.request.contextPath}/SetProductionModeServlet";
//                    document.updateform.submit();
//                }
//                
//            }
            
            function invokeDelete(value1,value2)
            {             
                
                answer = confirm("Are you sure you want to delete Batch "+ value1 +"?")
                   
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteBulkCardRequestServlet?id="+value1+"&status="+value2;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadBulkCardRequestServlet";
                }

            }
            
            function invokeGoBack()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadBulkCardRequestServlet";
                
            }
            
            function invokeConfirmReceive(value)
            {               
                
                document.receiveconfirmform.action="${pageContext.request.contextPath}/ManageBulkCardRequestServlet?confirmStatus="+value;
                document.receiveconfirmform.submit();
                
                
            }
            
            
            
            
            
            
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULK_CD_REQ%>'                                  
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
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

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

                                    <form method="POST" name="addform" action="${pageContext.request.contextPath}/AddBulkCardRequestServlet">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody> 
                                                <tr>
                                                    <td></td>
                                                    <td><input type="hidden"  value="${operationtype}" name="operationtype" /></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Card Domain</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="domain" id="domain" onchange="loadCdProduct('add')" style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="domain" items="${cardDomainList}">
                                                                <c:if test="${bulkCdReqbean.cdDomain == domain.key}">
                                                                    <option value="${domain.key}" selected="true">${domain.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.cdDomain != domain.key}">
                                                                    <option value="${domain.key}" >${domain.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Card Type</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="type" id="type" onchange="loadCdProduct('add')" style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="type" items="${cardTypeList}">
                                                                <c:if test="${bulkCdReqbean.cdType == type.key}">
                                                                    <option value="${type.key}" selected="true">${type.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.cdType != type.key}">
                                                                    <option value="${type.key}" >${type.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Card Product</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="product" onchange="loadCurrency('add')" style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="product" items="${cdProductList}">
                                                                <c:if test="${bulkCdReqbean.cdProduct == product.key}">
                                                                    <option value="${product.key}" selected="true">${product.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.cdProduct != product.key}">
                                                                    <option value="${product.key}" >${product.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Currency Type</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="currency"  style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="currency" items="${currencyList}">
                                                                <c:if test="${bulkCdReqbean.currency == currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" selected="true">${currency.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.currency != currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <c:if test="${allowModify == 'NO'}">
                                                    <tr>
                                                        <td >Branch Name</td>
                                                        <td></td>
                                                        <td><font style="color: red;">*</font>&nbsp;
                                                            <input style="width:168px; " type="text" name="branchDes" readonly="true" value="${bulkCdReqbean.branchName}"/>
                                                    </td>
                                                    </tr>                                                    
                                                </c:if>
                                                <c:if test="${allowModify == 'YES'}">
                                                    <tr>
                                                        <td >Branch Name</td>
                                                        <td></td>
                                                        <td><font style="color: red;">*</font>&nbsp;
                                                            <select name="branch" style="width: 163px">
                                                                <option value="">--SELECT--</option>                                                                
                                                                <c:forEach var="branch" items="${bnkBranchList}">
                                                                    <c:if test="${bulkCdReqbean.branchCode == branch.key}">
                                                                        <option value="${branch.key}" selected="true">${branch.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${bulkCdReqbean.branchCode != branch.key}">
                                                                        <option value="${branch.key}" >${branch.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>                                                    
                                                </c:if>
                                                <tr hidden="">
                                                    <td>
                                                        <input type="hidden" name="bname" value="${bulkCdReqbean.branchCode}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="prioritycode" style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${sessionScope.SessionObject.priorityLevelList}">
                                                                <c:if test="${bulkCdReqbean.priorityLvl==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.priorityLvl != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Production Mode </td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="productionMode" onchange="invokeLoadBIN('add',cdType.value,prdMode.value)" id="prdMode" style="width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="prodMode" items="${productModes}">
                                                                <c:if test="${prodMode.key==bulkCdReqbean.productMode}">
                                                                    <option value="${prodMode.key}" selected="">${prodMode.value}</option>
                                                                </c:if>
                                                                <c:if test="${prodMode.key!=bulkCdReqbean.productMode}">
                                                                    <option value="${prodMode.key}">${prodMode.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>No of Cards</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text"  value="${bulkCdReqbean.reqNumOfCds}" name="reqCards" maxlength="4"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;" colspan="2">&nbsp;&nbsp;
                                                        <input type="submit" value="Add" class="defbutton" name="add" />
                                                        <input type="reset" value="Reset" class="defbutton" name="reset" onclick="invokeReset()"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" name="updateform" action="${pageContext.request.contextPath}/UpdateBulkCardRequestServlet">

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody> 
                                                <tr>
                                                    <td></td>
                                                    <td><input type="hidden"  value="${operationtype}" name="operationtype" /></td>
                                                </tr>
                                                <tr hidden="">
                                                    <td></td>
                                                    <td><input type="hidden"  value="${bulkCdReqbean.batchID}" name="batchId" /></td>
                                                </tr>
                                                <tr hidden="">
                                                    <td></td>
                                                    <td><input type="hidden"  value="${bulkCdReqbean.status}" name="batchStatus" /></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Card Domain</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="domain" id="domain" onchange="loadCdProduct('update')" style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="domain" items="${cardDomainList}">
                                                                <c:if test="${bulkCdReqbean.cdDomain == domain.key}">
                                                                    <option value="${domain.key}" selected="true">${domain.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.cdDomain != domain.key}">
                                                                    <option value="${domain.key}" >${domain.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="type" id="type" onchange="loadCdProduct('update')" style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="type" items="${cardTypeList}">
                                                                <c:if test="${bulkCdReqbean.cdType == type.key}">
                                                                    <option value="${type.key}" selected="true">${type.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.cdType != type.key}">
                                                                    <option value="${type.key}" >${type.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="product" onchange="loadCurrency('update')" style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="product" items="${cdProductList}">
                                                                <c:if test="${bulkCdReqbean.cdProduct == product.key}">
                                                                    <option value="${product.key}" selected="true">${product.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.cdProduct != product.key}">
                                                                    <option value="${product.key}" >${product.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="currency"  style="width: 163px">
                                                            <option value="">--SELECT--</option>                                                                
                                                            <c:forEach var="currency" items="${currencyList}">
                                                                <c:if test="${bulkCdReqbean.currency == currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" selected="true">${currency.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.currency != currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <c:if test="${allowModify == 'NO'}">
                                                    <tr>
                                                        <td>Branch Name</td>
                                                        <td></td>
                                                        <td><font style="color: red;">*</font>&nbsp;
                                                            <select name="branch" disabled="" style="width: 163px">
                                                                <option value="">--SELECT--</option>                                                                
                                                                <c:forEach var="branch" items="${bnkBranchList}">
                                                                    <c:if test="${bulkCdReqbean.branchCode == branch.key}">
                                                                        <option value="${branch.key}" selected="true">${branch.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${bulkCdReqbean.branchCode != branch.key}">
                                                                        <option value="${branch.key}" >${branch.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>                                                    
                                                </c:if>
                                                <c:if test="${allowModify == 'YES'}">
                                                    <tr>
                                                        <td>Branch Name</td>
                                                        <td></td>
                                                        <td><font style="color: red;">*</font>&nbsp;
                                                            <select name="branch" style="width: 163px">
                                                                <option value="">--SELECT--</option>                                                                
                                                                <c:forEach var="branch" items="${bnkBranchList}">
                                                                    <c:if test="${bulkCdReqbean.branchCode == branch.key}">
                                                                        <option value="${branch.key}" selected="true">${branch.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${bulkCdReqbean.branchCode != branch.key}">
                                                                        <option value="${branch.key}" >${branch.value}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>                                                    
                                                </c:if>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="prioritycode" style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${sessionScope.SessionObject.priorityLevelList}">
                                                                <c:if test="${bulkCdReqbean.priorityLvl==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCdReqbean.priorityLvl != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Production Mode </td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="productionMode" onchange="invokeLoadBIN('add',cdType.value,prdMode.value)" id="prdMode" style="width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="prodMode" items="${productModes}">
                                                                <c:if test="${prodMode.key==bulkCdReqbean.productMode}">
                                                                    <option value="${prodMode.key}" selected="">${prodMode.value}</option>
                                                                </c:if>
                                                                <c:if test="${prodMode.key!=bulkCdReqbean.productMode}">
                                                                    <option value="${prodMode.key}">${prodMode.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>No of Cards</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text"  value="${bulkCdReqbean.reqNumOfCds}" name="reqCards" maxlength="4"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td colspan="2">&nbsp;&nbsp;
                                                        <input type="submit" value="Update" class="defbutton" name="update" />
                                                        <input type="reset" value="Reset" class="defbutton" name="reset" onclick="invokeResetInUpdate('${bulkCdReqbean.batchID}','${bulkCdReqbean.status}')"/>
                                                        <input type="button" value="Cancel" class="defbutton" name="cancel" onclick="invokeReset()"/>
                                                    </td>
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
                                                    <td>Batch Id</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.batchID}</td>
                                                </tr>
                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.cdDomainDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.cdTypeDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.cdProductDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Branch Name</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.branchName}</td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.priorityLvlDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Production Mode</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.productModeDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Requested No of Cards</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.reqNumOfCds}</td>
                                                </tr>
                                                <tr>
                                                    <td>Approved No of Cards</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.apprvNumOfCds}</td>
                                                </tr>
                                                <tr>
                                                    <td>Requested User</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.reqUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Approved User</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.apprvUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Requested User's Branch</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.reqBranchDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Approved User's Branch</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.apprvBranchDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Batch Status</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Currency Code</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.currencyDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Last Updated User</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.lastUpdatedUser}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" name="back" value="Back" onclick="invokeGoBack()" style="width: 100px"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='receive'}" >
                                    <form method="POST" name="receiveconfirmform">
                                        <table border="0" cellspacing="10" cellpadding="0" >
                                            <tbody> 
                                                <tr hidden="">
                                                    <td ></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="hidden" value="${exchangeBean.batchID}" name="batchId"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Batch Id</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.batchID}</td>
                                                </tr>
                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.cdDomainDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.cdTypeDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.cdProductDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Branch Name</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.branchName}</td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.priorityLvlDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Production Mode</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.productModeDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Requested No of Cards</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.reqNumOfCds}</td>
                                                </tr>
                                                <tr>
                                                    <td>Approved No of Cards</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.apprvNumOfCds}</td>
                                                </tr>
                                                <tr>
                                                    <td>Requested User</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.reqUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Approved User</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.apprvUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Requested User's Branch</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.reqBranchDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Approved User's Branch</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.apprvBranchDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Batch Status</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Currency Code</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.currencyDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Last Updated User</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.lastUpdatedUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Remarks</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td><input type="text" value="" name="remarks"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="submit" name="confirm" value="Confirm" onclick="invokeConfirmReceive('BCRF')" class="defbutton"/>
                                                        <input type="submit" name="reject" value="Reject" onclick="invokeConfirmReceive('BCDR')" class="defbutton"/>
                                                        <input type="button" name="back" value="Back" onclick="invokeGoBack()" class="defbutton"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <!-- ----------------------------------------------------------------Start Data Table ---------------------------------------------------------------  -->            
                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Batch Id</th>
                                            <th>Card Domain</th>
                                            <th>Card Type</th>
                                            <th>Card Product</th>
                                            <th>Branch Name</th>
                                            <th>Production Mode</th> 
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                            <th>Received</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="bulk" items="${bulkCdReqList}">
                                            <tr>
                                                <td>${bulk.batchID}</td>
                                                <td >${bulk.cdDomainDes}</td>
                                                <td >${bulk.cdTypeDes}</td>
                                                <td >${bulk.cdProductDes}</td>
                                                <td >${bulk.branchName}</td>
                                                <td >${bulk.productModeDes}</td>
                                                <td >${bulk.statusDes}</td>    

                                                <td  ><a href='${pageContext.request.contextPath}/ViewBulkCardRequestServlet?id=<c:out value="${bulk.batchID}"></c:out>&opType=view'>View</a></td>
                                                <td ><a href='${pageContext.request.contextPath}/LoadUpdateBulkCardRequestServlet?id=<c:out value="${bulk.batchID}"></c:out>&status=<c:out value="${bulk.status}"></c:out>'/>Update</td>
                                                <td ><a href='#' onclick="invokeDelete('${bulk.batchID}','${bulk.status}')">Delete</td>
                                                <td  ><a href='${pageContext.request.contextPath}/ViewBulkCardRequestServlet?id=<c:out value="${bulk.batchID}"></c:out>&opType=receive'>Received</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

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
