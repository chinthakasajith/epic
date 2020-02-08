f<%-- 
    Document   : cardproductmgthome
    Created on : Mar 22, 2012, 12:16:34 PM
    Author     : chanuka
--%>

<%--<%@page import="com.epic.cms.system.util.session.SessionVarName"%>--%>
<%--<%@page import="org.jboss.weld.ejb.api.SessionObjectReference"%>--%>
<%--<%@page import="com.epic.cms.system.util.session.SessionVarList"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            function invokeLoadUpdate(prodCode){
                document.updatecardproductform.action="${pageContext.request.contextPath}/UpdateCardProductFormServlet?cardProductCode="+prodCode;
                document.updatecardproductform.submit();
            }
            
            function invokeUpdate(assigned,notassigned)
            {

                document.updatecardproductform.action="${pageContext.request.contextPath}/UpdateCardProductServlet?assigned="+assigned+"&notassigned="+notassigned;
                document.updatecardproductform.submit();

            }
            function invokeAdd(assigned,notassigned)
            {

                document.addcardproductform.action ="${pageContext.request.contextPath}/AddCardProductServlet?assigned="+assigned+"&notassigned="+notassigned;
                document.addcardproductform.submit();

            }
            
            function invokeResetCard()
            {
                             
                window.location = "${pageContext.request.contextPath}/LoadCardProductServlet?resetType=reset";

            }
            
            function ConfirmDelete(cardProductCode)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete this card product?</p>");
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
                            window.location ="${pageContext.request.contextPath}/DeleteCardProductServlet?cardProductCode="+cardProductCode;
                        }
                    }
                });

            }
            
            function invokeLoadBIN(value)
            {
                if($("#carddomain").val() != '' && $("#cdType").val() != ''){
            
                    document.addcardproductform.action="${pageContext.request.contextPath}/SetBinToCardTypeServlet?opType="+value;
                    document.addcardproductform.submit();
                }
                else if($("#carddomain").val() == null || $("#cdType").val() == null){
                    
                    alert("You should select both");
                }
            }
            
            function invokeLoadBINUpdate(value)
            {
                
                document.updatecardproductform.action="${pageContext.request.contextPath}/SetBinToCardTypeServlet?opType="+value;
                document.updatecardproductform.submit();
                
            }
            function selectAllForUpdate(opType,selectBox1,selectBox2) {
                
                var assigned = -1;
                var notassigned = -1;
                if(selectBox1.options.length != 0){
                
                    for (var i = 0; i < selectBox1.options.length; i++) {
                        selectBox1.options[i].selected = true;
                    }
                }else{
                    
                    assigned = "0";
                }
                if(selectBox2.options.length != 0){
                    for (var i = 0; i < selectBox2.options.length; i++) {
                        selectBox2.options[i].selected = true;
                    }
                }else{
                    notassigned = "0";
                }
               
                if(opType=='add'){         
                    invokeAdd(assigned,notassigned);
                }
                else{
                    invokeUpdate(assigned,notassigned);                
                }
            } 
    
            function waterMark(){
                $("#validity").watermark("First");
                
   
            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDPRODUCT%>'                                  
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

        <script>
            function loadAssignedBinList(operationType,assignType,selectBox1,selectBox2){
                if(selectBox1.options.length != 0){
                
                    for (var i = 0; i < selectBox1.options.length; i++) {
                        selectBox1.options[i].selected = true;
                    }
                }
                if(selectBox2.options.length != 0){
                    for (var i = 0; i < selectBox2.options.length; i++) {
                        selectBox2.options[i].selected = true;
                    }
                } 
                if(operationType=='add'){
                    document.addcardproductform.action="${pageContext.request.contextPath}/LoadCardProductServlet?assignType="+assignType+"&operationType="+operationType;
                    document.addcardproductform.submit();   
                }else if(operationType=='update'){
                    document.updatecardproductform.action="${pageContext.request.contextPath}/LoadCardProductServlet?assignType="+assignType+"&operationType="+operationType;
                    document.updatecardproductform.submit();                     
                }
            }
        </script>
        <script>
            function loadCardKeyList(operationType,assignType,selectBox1,selectBox2){
                if(selectBox1.options.length != 0){
                
                    for (var i = 0; i < selectBox1.options.length; i++) {
                        selectBox1.options[i].selected = true;
                    }
                }
                if(selectBox2.options.length != 0){
                    for (var i = 0; i < selectBox2.options.length; i++) {
                        selectBox2.options[i].selected = true;
                    }
                } 
                if(operationType=='add'){                
                    document.addcardproductform.action="${pageContext.request.contextPath}/LoadCardProductServlet?assignType="+assignType+"&operationType="+operationType;
                    document.addcardproductform.submit();
                } else if(operationType=='update'){
                    document.updatecardproductform.action="${pageContext.request.contextPath}/LoadCardProductServlet?assignType="+assignType+"&operationType="+operationType;
                    document.updatecardproductform.submit();                    
                }
            }
        </script>
        <script>
            function loadAssignedBinCardKeyList(operationType,assignType,selectBox1,selectBox2){
                if(selectBox1.options.length != 0){
                
                    for (var i = 0; i < selectBox1.options.length; i++) {
                        selectBox1.options[i].selected = true;
                    }
                }
                if(selectBox2.options.length != 0){
                    for (var i = 0; i < selectBox2.options.length; i++) {
                        selectBox2.options[i].selected = true;
                    }
                } 
                //                if(selectBox3.options.length != 0){
                //                
                //                    for (var i = 0; i < selectBox3.options.length; i++) {
                //                        selectBox3.options[i].selected = true;
                //                    }
                //                }
                //                if(selectBox4.options.length != 0){
                //                    for (var i = 0; i < selectBox4.options.length; i++) {
                //                        selectBox4.options[i].selected = true;
                //                    }
                //                }
                if(operationType=='add'){ 
                    document.addcardproductform.action="${pageContext.request.contextPath}/LoadCardProductServlet?assignType="+assignType+"&operationType="+operationType;
                    document.addcardproductform.submit(); 
                } else if(operationType=='update'){
                    document.updatecardproductform.action="${pageContext.request.contextPath}/LoadCardProductServlet?assignType="+assignType+"&operationType="+operationType;
                    document.updatecardproductform.submit();                
                }
            }
        </script>
        <script>
            function LoadCardList(){
                
                $('#cardKeyList').empty();
                

                var sval=$("#cardProductionMode").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadCardKeyListServlet",      
                { 
                    cardProductionMode : sval               
                },
                function(jsonobject) {
                    $("#cardKeyList").append("<option value=''>---------SELECT----------</option>");                    
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#cardKeyList').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
        </script> 
        <script>
            function removeValue(operationType,productCode,binDes,cardProductionModeDes,cardKeyDes,selectBox1,selectBox2,assignType){

                
                if(selectBox1.options.length != 0){
                
                    for (var i = 0; i < selectBox1.options.length; i++) {
                        selectBox1.options[i].selected = true;
                    }
                }
                if(selectBox2.options.length != 0){
                    for (var i = 0; i < selectBox2.options.length; i++) {
                        selectBox2.options[i].selected = true;
                    }
                } 
                if(operationType=='add'){                
                    document.addcardproductform.action="${pageContext.request.contextPath}/LoadCardProductBinServlet?operationType="+operationType+"&productCode="+productCode+"&binDes="+binDes+"&cardProductionModeDes="+cardProductionModeDes+"&cardKeyDes="+cardKeyDes+"&assignType="+assignType;
                    document.addcardproductform.submit();
                } else if(operationType=='update'){
                    document.updatecardproductform.action="${pageContext.request.contextPath}/LoadCardProductBinServlet?operationType="+operationType+"&productCode="+productCode+"&binDes="+binDes+"&cardProductionModeDes="+cardProductionModeDes+"&cardKeyDes="+cardKeyDes;
                    document.updatecardproductform.submit();                    
                }
                
            }
        </script>        

        <title>EPIC CMS CARD PRODUCT MANAGEMENT</title>
    </head>
    <body >


        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>
        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>



                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addcardproductform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                                <td><font color="green"><b> ${successMsg}</b></font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 200px">Product Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${cardProductBean.productCode}" name="productcode" maxlength="8"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="productcodedes" value="${cardProductBean.description}" maxlength="32"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Domain </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="carddomain" onchange="invokeLoadBIN('add')" id="carddomain" style="width: 163px">                                                           
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="domain" items="${cardDomainList}">
                                                                <c:if test="${cardProductBean.domainCode==domain.key}">
                                                                    <option value="${domain.key}" selected>${domain.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.domainCode!=domain.key}">
                                                                    <option value="${domain.key}" >${domain.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="cardtype" onchange="invokeLoadBIN('add')" id="cdType" style="width: 163px">                                                           
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardType" items="${cardTypeList}">
                                                                <c:if test="${cardProductBean.cardType==cardType.key}">
                                                                    <option value="${cardType.key}" selected>${cardType.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.cardType!=cardType.key}">
                                                                    <option value="${cardType.key}" >${cardType.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Validity Period (month)</td>
                                                    <td><font style="color: red;">*</font>
                                                        <input type="text" name="validity" id="validity" value="${cardProductBean.validityPeriod}" maxlength="2"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="statuscode" style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${cardProductBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}" >${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <!--<td>Display Digit</td>-->
                                                    <td></td>
                                                    
                                                    <!--<td><font style="color: red;">*</font>&nbsp;<input type="text"  name="displayDigit" value="${cardProductBean.displayDigit}" maxlength="3"/></td>-->
                                                    <td><font style="color: red;"></font>&nbsp;<input type="hidden"  name="displayDigit" value="${cardProductBean.displayDigit}" maxlength="3"/></td>
                                                    
                                                    <td></td>
                                                </tr>
                                                

                                                <tr>
                                                    <td >BIN</td>
                                                    <td>
                                                        <h4><b>Not Assigned BIN</b></h4>

                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${NotAssignbinList}">
                                                                <option value="${notassign.bin}">${notassign.binDes}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>

                                                    <td align="center" >
                                                        <input type=button value=">" onclick="moveout()" class="buttonsize"/><br>
                                                        <input type=button value="<" onclick="movein()" class="buttonsize"/><br>
                                                        <input type=button value=">>" onclick="moveallout()" class="buttonsize"/><br>
                                                        <input type=button value="<<" onclick="moveallin()" class="buttonsize"/>
                                                    </td>

                                                    <td>
                                                        <h4><b>Assigned BIN</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10 >
                                                            <c:forEach var="assign" items="${AssignbinList}">
                                                                <option value="${assign.bin}" >${assign.binDes}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Assign" class="defbutton" onclick="loadAssignedBinList('add','binAssign',assignlist,notassignlist)"/></td>
                                                    <td></td>
                                                </tr>    
                                                <tr>
                                                    <td>Assigned Bin</td>
                                                    <td><font style="color: red;">*</font>
                                                        <select name="assignedBin" onchange="loadCardKeyList('add','cardKeyAssign',assignlist,notassignlist)">
                                                            <option value="">SELECT</option>
                                                            <c:forEach var="assign" items="${AssignbinList}">
                                                                <c:if test="${assign.bin == cardProductBean.bin}">
                                                                    <option value="${assign.bin}" selected>${assign.binDes}</option>
                                                                </c:if>
                                                                <c:if test="${assign.bin != cardProductBean.bin}">
                                                                    <option value="${assign.bin}">${assign.binDes}</option>
                                                                </c:if>                                                                    
                                                            </c:forEach>                                      
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr> 
                                                <!--                                                <tr>
                                                                                                    <td >Card Key</td>
                                                                                                    <td>
                                                                                                        <h4><b>Not Assigned Card Key</b></h4>
                                                
                                                                                                        <select name="notassigncardkeylist" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                <c:forEach  var="notassigncardkey" items="${NotAssignCardKeyList}">
                                                    <option value="${notassigncardkey.cardKey}" >${notassigncardkey.cardKeyDes}</option>
                                                </c:forEach>
                                            </select>

                                        </td>

                                        <td align="center" >
                                            <input type=button value=">" onclick="moveout2()" class="buttonsize"/><br>
                                            <input type=button value="<" onclick="movein2()" class="buttonsize"/><br>
                                            <input type=button value=">>" onclick="moveallout2()" class="buttonsize"/><br>
                                            <input type=button value="<<" onclick="moveallin2()" class="buttonsize"/>
                                        </td>

                                        <td>
                                            <h4><b>Assigned Card Key</b></h4>
                                            <select name="assigncardkeylist" style="width: 200px" id=out2 multiple="multiple" size=10 >
                                                <c:forEach var="assigncardkey" items="${AssignCardKeyList}">
                                                    <option value="${assigncardkey.cardKey}" >${assigncardkey.cardKeyDes}</option>
                                                </c:forEach>
                                            </select>

                                        </td>
                                    </tr>-->
                                                <tr>
                                                    <td>Card Production Mode </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="cardProductionMode" id="cardProductionMode" style="width: 163px" onchange="LoadCardList()">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="ProductionMode" items="${cardProductionModeList}">
                                                                <c:if test="${cardProductBean.cardProductionMode==ProductionMode.cardProductionMode}">
                                                                    <option value="${ProductionMode.cardProductionMode}" selected>${ProductionMode.cardProductionModeDes}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.cardProductionMode!=ProductionMode.cardProductionMode}">
                                                                    <option value="${ProductionMode.cardProductionMode}" >${ProductionMode.cardProductionModeDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Key </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="cardKeyList" id="cardKeyList"  style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cardKey" items="${cardKeyList}">
                                                                <c:if test="${cardProductBean.cardKey==cardKey.cardKey}">
                                                                    <option value="${cardKey.cardKey}" selected>${cardKey.cardKeyDes}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.cardKey!=cardKey.cardKey}">
                                                                    <option value="${cardKey.cardKey}" selected>${cardKey.cardKeyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Assign" class="defbutton" onclick="loadAssignedBinCardKeyList('add','binCardKeyAssign',assignlist,notassignlist)"/></td>
                                                    <td></td>
                                                </tr>                                           
                                        </table>
                                        <!-- ******************************************************************** -->
                                        <c:if test="${sessionScope.SessionObject.cardProductBinBeanLst != null }">
                                            <table  border="1"  class="display" id="tableview2">
                                                <thead>
                                                    <tr>
                                                        <th style="width: 100px">Product Code</th>
                                                        <th style="width: 150px">Bin</th>
                                                        <th style="width: 150px">Production Mode</th>
                                                        <th style="width: 120px">Card Keys</th>
                                                        <th style="width: 120px">&nbsp;</th> 
                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <c:forEach var="cardproducttemp" items="${sessionScope.SessionObject.cardProductBinBeanLst}">
                                                        <tr>
                                                            <td id="productCode">${cardproducttemp.productCode}</td>
                                                            <td id="binDes">${cardproducttemp.binDes}</td>
                                                            <td id="cardProductionModeDes">${cardproducttemp.cardProductionModeDes}</td>                                                            
                                                            <td id="cardKeyDes">${cardproducttemp.cardKeyDes}</td>
                                                            <td id=""><input type="button"   onclick="removeValue('add','${cardproducttemp.productCode}','${cardproducttemp.binDes}','${cardproducttemp.cardProductionModeDes}','${cardproducttemp.cardKeyDes}',assignlist,notassignlist,'binCardKeyAssign')"  value="Remove" style="border: none;background-color: #0CF;"></td>                                                          
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>                                
                                        <!-- ******************************************************************** -->
                                        <table>
                                            <tr>
                                                <td style="width: 220px"></td>
                                                <td >
                                                    <input type="button" value="Add" name="add" class="defbutton" onclick="selectAllForUpdate('add',assignlist,notassignlist)"/>
                                                    <input type="button" value="Reset" name="reset" class="defbutton" onclick="invokeResetCard()"/>
                                                </td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDPRODUCT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>

                                            </tr>
                                        </table>

                                    </form>

                                </c:if>

                                <%-- -------------------------add form end -------------------------------- --%>

                                <%-- -------------------------update form start -------------------------------- --%>
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updatecardproductform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px">Product Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  readonly="true" value="${cardProductBean.productCode}" name="productcode" maxlength="8"/></td>
                                                    <td></td>
                                                </tr>



                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="productcodedes" value="${cardProductBean.description}" maxlength="32"/></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Domain </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="carddomain" onchange="invokeLoadBINUpdate('update')" id="carddomain" style="width: 163px">                                                           

                                                            <c:forEach var="domain" items="${cardDomainList}">
                                                                <c:if test="${cardProductBean.domainCode==domain.key}">
                                                                    <option value="${domain.key}" selected>${domain.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.domainCode!=domain.key}">
                                                                    <option value="${domain.key}" >${domain.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="cardtype" onchange="invokeLoadBINUpdate('update')" style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cardType" items="${cardTypeList}">
                                                                <c:if test="${cardProductBean.cardType==cardType.key}">
                                                                    <option value="${cardType.key}" selected>${cardType.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.cardType!=cardType.key}">
                                                                    <option value="${cardType.key}" >${cardType.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Validity Period (month)</td>
                                                    <td><font style="color: red;">*</font>
                                                        <input type="text" name="validity" value="${cardProductBean.validityPeriod}" maxlength="2"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="statuscode" style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${cardProductBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}" >${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>


                                                <tr>
                                                    <!--<td>Display Digit</td>-->
                                                    <td></td>
                                                    <!--<td><font style="color: red;">*</font>&nbsp;<input type="text"  name="displayDigit" value="${cardProductBean.displayDigit}" maxlength="3"/></td>-->
                                                    <td><font style="color: red;"></font>&nbsp;<input type="hidden"  name="displayDigit" value="${cardProductBean.displayDigit}" maxlength="3"/></td>
                                                    
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>BIN</td>

                                                    <td>
                                                        <h4><b>Not Assigned BIN</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${NotAssignbinList}">
                                                                <option value="${notassign.bin}" >${notassign.binDes}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>

                                                    <td align="center" >
                                                        <input type=button value=">" onclick="moveout()" class="buttonsize"/><br>
                                                        <input type=button value="<" onclick="movein()" class="buttonsize"/><br>
                                                        <input type=button value=">>" onclick="moveallout()" class="buttonsize"/><br>
                                                        <input type=button value="<<" onclick="moveallin()" class="buttonsize"/>
                                                    </td>

                                                    <td>
                                                        <h4><b>Assigned BIN</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10 >
                                                            <c:forEach var="assign" items="${AssignbinList}">
                                                                <option value="${assign.bin}" >${assign.binDes}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Assign" class="defbutton" onclick="loadAssignedBinList('update','binAssign',assignlist,notassignlist)"/></td>
                                                    <td></td>
                                                </tr>    
                                                <tr>
                                                    <td>Assigned Bin</td>
                                                    <td><font style="color: red;">*</font>
                                                        <select name="assignedBin" onchange="loadCardKeyList('update','cardKeyAssign',assignlist,notassignlist)">
                                                            <option value="">SELECT</option>
                                                            <c:forEach var="assign" items="${AssignbinList}">
                                                                <c:if test="${assign.bin == cardProductBean.bin}">
                                                                    <option value="${assign.bin}" selected>${assign.binDes}</option>
                                                                </c:if>
                                                                <c:if test="${assign.bin != cardProductBean.bin}">
                                                                    <option value="${assign.bin}">${assign.binDes}</option>
                                                                </c:if>                                                                    
                                                            </c:forEach>                                      
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr> 
                                                <!--                                                <tr>
                                                                                                    <td >Card Key</td>
                                                                                                    <td>
                                                                                                        <h4><b>Not Assigned Card Key</b></h4>
                                                
                                                                                                        <select name="notassigncardkeylist" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                <c:forEach  var="notassigncardkey" items="${NotAssignCardKeyList}">
                                                    <option value="${notassigncardkey.cardKey}" >${notassigncardkey.cardKeyDes}</option>
                                                </c:forEach>
                                            </select>

                                        </td>

                                        <td align="center" >
                                            <input type=button value=">" onclick="moveout2()" class="buttonsize"/><br>
                                            <input type=button value="<" onclick="movein2()" class="buttonsize"/><br>
                                            <input type=button value=">>" onclick="moveallout2()" class="buttonsize"/><br>
                                            <input type=button value="<<" onclick="moveallin2()" class="buttonsize"/>
                                        </td>

                                        <td>
                                            <h4><b>Assigned Card Key</b></h4>
                                            <select name="assigncardkeylist" style="width: 200px" id=out2 multiple="multiple" size=10 >
                                                <c:forEach var="assigncardkey" items="${AssignCardKeyList}">
                                                    <option value="${assigncardkey.cardKey}" >${assigncardkey.cardKeyDes}</option>
                                                </c:forEach>
                                            </select>

                                        </td>
                                    </tr>-->
                                                <tr>
                                                    <td>Card Production Mode </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="cardProductionMode" id="cardProductionMode"  style="width: 163px" onchange="LoadCardList()">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="ProductionMode" items="${cardProductionModeList}">
                                                                <c:if test="${cardProductBean.cardProductionMode==ProductionMode.cardProductionMode}">
                                                                    <option value="${ProductionMode.cardProductionMode}" selected>${ProductionMode.cardProductionModeDes}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.cardProductionMode!=ProductionMode.cardProductionMode}">
                                                                    <option value="${ProductionMode.cardProductionMode}" >${ProductionMode.cardProductionModeDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Key </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select  name="cardKeyList" id="cardKeyList" style="width: 163px">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cardKey" items="${cardKeyList}">
                                                                <c:if test="${cardProductBean.cardKey==cardKey.cardKey}">
                                                                    <option value="${cardKey.cardKey}" selected>${cardKey.cardKeyDes}</option>
                                                                </c:if>
                                                                <c:if test="${cardProductBean.cardKey!=cardKey.cardKey}">
                                                                    <option value="${cardKey.cardKey}" selected>${cardKey.cardKeyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Assign" class="defbutton" onclick="loadAssignedBinCardKeyList('update','binCardKeyAssign',assignlist,notassignlist)"/></td>
                                                    <td></td>
                                                </tr>                                           
                                        </table>
                                        <!-- ******************************************************************** -->
                                        <c:if test="${sessionScope.SessionObject.cardProductBinBeanLst != null }">
                                            <table  border="1"  class="display" id="tableview2">
                                                <thead>
                                                    <tr>
                                                        <th style="width: 100px">Product Code</th>
                                                        <th style="width: 150px">Bin</th>
                                                        <th style="width: 150px">Production Mode</th>                                                        
                                                        <th style="width: 120px">Card Keys</th>
                                                        <th style="width: 120px">&nbsp;</th>

                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <c:forEach var="cardproducttemp" items="${sessionScope.SessionObject.cardProductBinBeanLst}">
                                                        <tr>
                                                            <td id="productCode">${cardproducttemp.productCode}</td>
                                                            <td id="binDes">${cardproducttemp.binDes}</td>
                                                            <td id="cardProductionModeDes">${cardproducttemp.cardProductionModeDes}</td>                                                            
                                                            <td id="cardKeyDes">${cardproducttemp.cardKeyDes}</td>
                                                            <td id=""><input type="button"   onclick="removeValue('update','${cardproducttemp.productCode}','${cardproducttemp.binDes}','${cardproducttemp.cardProductionModeDes}','${cardproducttemp.cardKeyDes}',assignlist,notassignlist,'')" value="Remove" style="border: none;background-color: #0CF;"></td>  
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>                                
                                        <!-- ******************************************************************** -->

                                        <table>
                                            <tr>
                                                <td style="width: 220px"></td>
                                                <td >
                                                    <input type="button" value="Update" name="update" class="defbutton" onclick="selectAllForUpdate('update',assignlist,notassignlist)" />
                                                    <input type="button" value="Reset" name="reset" class="defbutton" onclick="invokeLoadUpdate('${cardProductBean.productCode}', '${cardProductBean.cardType}')" />
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeResetCard()"/></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDPRODUCT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>

                                                <td></td>
                                            </tr>
                                        </table>


                                    </form>
                                </c:if>
                                <%-- -------------------------update form end -------------------------------- --%>


                                <%-- -------------------------view form start -------------------------------- --%>
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewcardproductform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tr>
                                                <td>Card Product Code</td>
                                                <td>:</td>
                                                <td>${cardProductBean.productCode}</td>
                                            </tr>

                                            <tr>
                                                <td>Description</td>
                                                <td>:</td>
                                                <td>${cardProductBean.description}</td>
                                            </tr>

                                            <tr>
                                                <td>Card Domain </td>
                                                <td>:</td>
                                                <td>${cardProductBean.domainDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Type </td>
                                                <td>:</td>
                                                <td>${cardProductBean.cardTypeDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Validity Period</td>
                                                <td>:</td>
                                                <td>${cardProductBean.validityPeriod}</td>
                                            </tr>
                                            <tr>
                                                <td>Status </td>
                                                <td>:</td>
                                                <td>${cardProductBean.statusDes}</td>
                                            </tr>

                                            <!--
                                            <tr>
                                                <td>Display Digit </td>
                                                <td>:</td>
                                                <td>${cardProductBean.displayDigit}</td>
                                            </tr>
                                            -->
                                            <tr>
                                                <td>BIN</td>                                           
                                                <td></td>

                                                <td>
                                                    <h4><b>Not Assigned BIN</b></h4>
                                                    <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="notassign" items="${NotAssignbinList}">
                                                            <option value="${notassign.bin}" >${notassign.binDes}</option>
                                                        </c:forEach>
                                                    </select>

                                                </td> 
                                                <td>
                                                    <h4><b>Assigned BIN</b></h4>
                                                    <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10 >
                                                        <c:forEach var="assign" items="${AssignbinList}">
                                                            <option value="${assign.bin}" >${assign.binDes}</option>
                                                        </c:forEach>
                                                    </select>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td >
                                                    <input type="button" value="Back" name="reset" style="width: 100px" onclick="invokeResetCard()"> </td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDPRODUCT%>')" ><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>


                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>
                                    </form>

                                </c:if>
                                <%-- -------------------------view form end -------------------------------- --%>











                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th style="width: 100px">Product Code</th>
                                            <th style="width: 150px">Description</th>
                                            <th style="width: 120px">Card Type</th>
                                            <th style="width: 120px">Card Domain</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="cardproduct" items="${cardProductBeanLst}">
                                            <tr>
                                                <td >${cardproduct.productCode}</td>
                                                <td >${cardproduct.description}</td>
                                                <td >${cardproduct.cardTypeDes}</td>
                                                <td >${cardproduct.domainDes}</td>
                                                <td >${cardproduct.statusDes}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewCardProductServlet?cardProductCode=<c:out value="${cardproduct.productCode}"></c:out>&cardType=<c:out value="${cardproduct.cardType}"></c:out>&cardDomain=<c:out value="${cardproduct.domainCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdateCardProductFormServlet?cardProductCode=<c:out value="${cardproduct.productCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${cardproduct.productCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>
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

