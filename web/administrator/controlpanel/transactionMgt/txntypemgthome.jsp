<%-- 
    Document   : txntypemgthome
    Created on : Apr 17, 2012, 12:16:45 PM
    Author     : nalin
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />



        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";
        </style>

<!--        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>-->

        <title>EPIC_CMS_HOME</title>
<!--        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>-->
        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addTxnTypeForm.action="${pageContext.request.contextPath}/AddTxnTypeMgtServlet";
                document.addTxnTypeForm.submit();

            }
            function invokeAddChannel(){
               
                
                document.addTxnTypeForm.action="${pageContext.request.contextPath}/SetChannelDataToSessionServlet?operationtype=add";
                document.addTxnTypeForm.submit();
               
            }
            function invokeAddUpdateChannel(){
                
                
                document.updateTxnTypeform.action="${pageContext.request.contextPath}/SetChannelDataToSessionServlet?operationtype=update";
                document.updateTxnTypeform.submit();
               
            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadTxnTypeMgtServlet";

            }
            
            function invokeUpdateReset(transactionTypeCode){

                window.location = "${pageContext.request.contextPath}/UpdateTxnTypeMgtServlet?transactionTypeCode="+transactionTypeCode;

            }
            
            function invokeUpdate(transactionTypeCode)
            {
                
                window.location="${pageContext.request.contextPath}/UpdateTxnTypeMgtServlet?transactionTypeCode="+transactionTypeCode;
                

            }
            
            function invokeUpdateConfiremd()
            {

                document.updateTxnTypeform.action="${pageContext.request.contextPath}/UpdateConfiremedTxnTypeMgtServlet";
                document.updateTxnTypeform.submit();

            }
            
            function invokeView(transactionTypeCode)
            {

                window.location="${pageContext.request.contextPath}/ViewTxnTypeMgtServlet?transactionTypeCode="+transactionTypeCode;
                

            }
            
            function DeleteChannel(channelId){
            
                //window.location="${pageContext.request.contextPath}/RemoveChannelDataFromSessionServlet?channelId="+channelId;
                document.addTxnTypeForm.action="${pageContext.request.contextPath}/RemoveChannelDataFromSessionServlet?channelId="+channelId+"&operationtype=add";
                document.addTxnTypeForm.submit();
            
            }
            function DeleteUpdateChannel(channelId){

                document.updateTxnTypeform.action="${pageContext.request.contextPath}/RemoveChannelDataFromSessionServlet?channelId="+channelId+"&operationtype=update";
                document.updateTxnTypeform.submit();
            
            }
            function ConfirmDelete(transactionTypeCode)
            {
                $("#dialog-confirm").html("<p>Are you sure you want to delete this Transaction Type ?</p>");
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
                            window.location="${pageContext.request.contextPath}/DeleteTxnTypeMgtServlet?transactionTypeCode="+transactionTypeCode;
                        }
                    }
                });

            }
           
            
            
        </script>   
        <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.TXNTYPE%>'                                  
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

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Load Details Area  ///////////////////////////////////////////////////////////-->

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}"> 
                                    <form action="" method="POST" name="addTxnTypeForm" >
                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>

                                                <tr>
                                                    <td width ="200px;">Transaction Type Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="transactionTypeCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.transactionTypeCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="20" value='${txnTypeBean.description}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Financial Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp; <c:if test="${txnTypeBean.financialStatus == 'YES'}">
                                                            <input type="radio" name="financialStatus" checked="true" value="YES" /> Yes 
                                                        </c:if>

                                                        <c:if test="${txnTypeBean.financialStatus != 'YES'}">
                                                            <input type="radio" name="financialStatus" checked="true"  value="YES" />Yes 
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${txnTypeBean.financialStatus == 'NO'}">
                                                            <input type="radio" name="financialStatus" checked="true"  value="NO" /> No
                                                        </c:if>

                                                        <c:if test="${txnTypeBean.financialStatus != 'NO'}">
                                                            <input type="radio" name="financialStatus"  value="NO" /> No
                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">VISA Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="visaCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.visaCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">MASTER Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="masterCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.masterCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">AMEX Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="amexCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.amexCode}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">Transaction Types</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="selectOnlineTransactionType"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="txn" items="${sessionScope.SessionObject.onlineTxnTypeList}">

                                                                <c:if test="${txnTypeBean.onlineTxnCode==txn.txnCode}">
                                                                    <option value="${txn.txnCode}" selected="true" >${txn.description}</option>
                                                                </c:if>
                                                                <c:if test="${txnTypeBean.onlineTxnCode!=txn.txnCode}">
                                                                    <option value="${txn.txnCode}">${txn.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${txnTypeBean.trueStatusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${txnTypeBean.trueStatusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>


                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr></tr><tr></tr>
                                                <tr>
                                                    <td style="width: 200px"></td>

                                                    <td>
                                                        <input type="button" style="width: 100px" name="add" value="Add" onclick="invokeAdd()" />
                                                        <input type="reset" style="width: 100px" name="reset" value="Reset" onclick="invokeReset()"/>
                                                    </td>
                                                </tr>
                                                <tr></tr><tr></tr>
                                            </tbody>
                                        </table>  
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End lOAD Details Area  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewTxnTypeForm">

                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td width ="200px;">Transaction Code</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.transactionTypeCode}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Description</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.description}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Financial Status</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.financialStatus}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Status</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.statusCode}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Last Update User</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.lastUpdateUser}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Last Update Date</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.lastUpdateDate}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Create Date</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.createDate}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Visa Code</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.visaCode}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Master Code</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.masterCode}</td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Amex Code</td>
                                                    <td></td>
                                                    <td width ="5px;"> : </td>
                                                    <td></td>
                                                    <td>${txnType.amexCode}</td>
                                                </tr>                                               

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="reset" style="width: 100px" name="reset" value="Back" onclick="invokeReset()"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    <br /><br />

                                </c:if>      
                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateTxnTypeform" >

                                        <table border="0" cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr>
                                                    <td width ="200px;">Transaction Type Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input readonly="readonly" type="text" name="transactionTypeCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.transactionTypeCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="20" value='${txnTypeBean.description}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Financial Status</td>

                                                    <td> <font style="color: red;">*</font>&nbsp;<c:if test="${txnTypeBean.financialStatus == 'YES'}">
                                                            <input type="radio" name="financialStatus" checked="true" value="YES" /> Yes 
                                                        </c:if>

                                                        <c:if test="${txnTypeBean.financialStatus != 'YES'}">
                                                            <input type="radio" name="financialStatus" checked="true"  value="YES" />Yes 
                                                        </c:if>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${txnTypeBean.financialStatus == 'NO'}">
                                                            <input type="radio" name="financialStatus" checked="true"  value="NO" /> No
                                                        </c:if>

                                                        <c:if test="${txnTypeBean.financialStatus != 'NO'}">
                                                            <input type="radio" name="financialStatus"  value="NO" /> No
                                                        </c:if>
                                                    </td> 

                                                </tr>
                                                <tr>
                                                    <td width ="200px;">VISA Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="visaCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.visaCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">MASTER Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="masterCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.masterCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">AMEX Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="amexCode" class="inputfield-mandatory" maxlength="6" value='${txnTypeBean.amexCode}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">Transaction Types</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="selectOnlineTransactionType"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="txn" items="${sessionScope.SessionObject.onlineTxnTypeList}">

                                                                <c:if test="${txnTypeBean.onlineTxnCode==txn.txnCode}">
                                                                    <option value="${txn.txnCode}" selected="true" >${txn.description}</option>
                                                                </c:if>
                                                                <c:if test="${txnTypeBean.onlineTxnCode!=txn.txnCode}">
                                                                    <option value="${txn.txnCode}">${txn.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td width ="200px;">Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${txnTypeBean.trueStatusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${txnTypeBean.trueStatusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                    <td></td>
                                                </tr>
                                                <tr>  
                                                    <td><input type="hidden" name="oldValue" value='${txnTypeBean.oldValue}' hidden="hidden"></td>
                                                </tr>
                                            </tbody>
                                        </table>


                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr></tr><tr></tr>
                                                <tr>
                                                    <td style="width: 200px"></td>
                                                    <td><input type="button" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" />
                                                        <input type="reset" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${txnTypeBean.transactionTypeCode}')"/>
                                                    </td>
                                                </tr>
                                                <tr></tr><tr></tr>
                                            </tbody>
                                        </table>  
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->
                                <!--/////////////////////////////////////////////Start Table View  ///////////////////////////////////////////////////////////-->
                                <table border="1" class="display" id="tableview2">
                                    <thead>
                                        <tr>
                                            <th>Transaction Code</th>
                                            <th>Description</th>
                                            <th>Financial Status</th>
                                            <th>Status</th>
                                            <th>Last Update Date </th>
                                            <th>Visa Code</th>
                                            <th>Master Code</th>
                                            <th>Amex Code</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="task" items="${requestScope.typeList}">
                                            <tr>
                                                <td>${task.transactionTypeCode}</td>
                                                <td>${task.description}</td>
                                                <td>${task.financialStatus}</td>
                                                <td>${task.statusCode}</td>
                                                <td>${task.lastUpdateDate}</td>
                                                <td>${task.visaCode}</td>
                                                <td>${task.masterCode}</td>
                                                <td>${task.amexCode}</td>
                                                <td ><a  href='#' onclick="invokeView('${task.transactionTypeCode}')">View</a></td>
                                                <td ><a  href='#' onclick="invokeUpdate('${task.transactionTypeCode}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${task.transactionTypeCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>

                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>
                                
                                <!--/////////////////////////////////////////////End Table View  ///////////////////////////////////////////////////////////-->
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
