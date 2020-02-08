<%-- 
    Document   : numbergenformat
    Created on : Apr 4, 2012, 4:09:22 PM
    Author     : upul
--%>

<%@page import="com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean"%>
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


            function goToSelectOnChange(value,selectBox1,selectBox2,cardType,formatFieldNo)
            {
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
                
              
                
                if(value=="add"){
                    document.addcardnumberformatform.action="${pageContext.request.contextPath}/changeOnChangeNumberFormatServlet?operationtype="+value+"&assigned="+assigned+"&notassigned="+notassigned+"&cardType="+cardType+"&formatFieldNo="+formatFieldNo;
                    document.addcardnumberformatform.submit();
                }
                if(value=="update"){
                    document.updatecardnumberformatform.action="${pageContext.request.contextPath}/changeOnChangeNumberFormatServlet?operationtype="+value+"&assigned="+assigned+"&notassigned="+notassigned+"&cardType="+cardType+"&formatFieldNo="+formatFieldNo;
                    document.updatecardnumberformatform.submit();
                }
                
             
                
            }
            function goToSelectOnChangeNumber(value,selectBox1,selectBox2,cardType)
            {
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
                
               
                answer = confirm("This will reset the number format.Do you really want to Change the length.?")
                if (answer !=0)
                {
                    if(value=="add"){
                        document.addcardnumberformatform.action="${pageContext.request.contextPath}/ChangeOnChangeToNumberServlet?operationtype="+value+"&assigned="+assigned+"&notassigned="+notassigned+"&cardType="+cardType;
                        document.addcardnumberformatform.submit();
                    }
                    if(value=="update"){
                        document.updatecardnumberformatform.action="${pageContext.request.contextPath}/ChangeOnChangeToNumberServlet?operationtype="+value+"&assigned="+assigned+"&notassigned="+notassigned+"&cardType="+cardType;
                        document.updatecardnumberformatform.submit();
                    }
                
                }
                else{
                
                }
                
            }
            
            function goToResetPage()
            {   
                document.addcardnumberformatform.action="${pageContext.request.contextPath}/LoadNumberFormatGeneServlet";
                document.addcardnumberformatform.submit();
            }
            
            function goResetUpdate(value)
            {   
                window.location ="${pageContext.request.contextPath}/LoadUpdateNumberFormatServlet?formatCode="+value;

            }
            
            function goBack()
            {   
                document.viewcardnumberformatform.action="${pageContext.request.contextPath}/LoadNumberFormatGeneServlet";
                document.viewcardnumberformatform.submit();
            }
            
            function goBackFromUpdate(isBack)
            {   
                document.updatecardnumberformatform.action="${pageContext.request.contextPath}/LoadNumberFormatGeneServlet?isBack="+isBack;
                document.updatecardnumberformatform.submit();
            }
            
            function addNumberFormat(assigned,notassigned,cardType)
            {
   
                document.addcardnumberformatform.action="${pageContext.request.contextPath}/AddNewNumberFormatServlet?assigned="+assigned+"&notassigned="+notassigned+"&cardType="+cardType;
                document.addcardnumberformatform.submit();
            }
            
            function updateNumberFormat(assigned,notassigned,cardType)
            {   
                document.updatecardnumberformatform.action="${pageContext.request.contextPath}/UpdateNumberFormatServlet?assigned="+assigned+"&notassigned="+notassigned+"&cardType="+cardType;
                document.updatecardnumberformatform.submit();
            }
            
            function ConfirmDelete(formatCode)
            {
                answer = confirm("Do you really want to delete this Card Format?")
                if (answer !=0)
                {
                    window.location ="${pageContext.request.contextPath}/DeleteNumberFormatServlet?formatCode="+formatCode;
                }
                else
                {
                    window.location ="${pageContext.request.contextPath}/LoadNumberFormatGeneServlet";
                }
                
            }
            
            function selectAllForUpdate(opType,selectBox1,selectBox2,cardType) {
                
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
                    addNumberFormat(assigned,notassigned,cardType);
                }
                else if(opType=='update'){
                    updateNumberFormat(assigned,notassigned,cardType);                
                }
            }
           
            //load BINs according to the card type and Production mode in add
            function invokeLoadBIN(value,cardType)
            {
                
            
                document.addcardnumberformatform.action="${pageContext.request.contextPath}/ChangeBINToCardTypeServlet?operationtype="+value+"&cardType="+cardType;
                document.addcardnumberformatform.submit();
            
            }
            //load BINs according to the card type and Production mode in update
            function invokeLoadBINUpdate(value,cardType)
            {
            
                document.updatecardnumberformatform.action="${pageContext.request.contextPath}/ChangeBINToCardTypeServlet?operationtype="+value+"&cardType="+cardType;
                document.updatecardnumberformatform.submit();
            
            }
           
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDNUMBERGENE%>'                                  
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
                                <!--  ----------------------start  developer area  -----------------------------------          -->

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

                                    <form method="POST" name="addcardnumberformatform" id="addcardnumberformatform" >

                                        <table cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>  
                                                    <td><input type="hidden" name="operationtype" value="add"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Number-Format Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="numberFormatCode" maxlength="6" value="${formatBean.formatCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Number-Format Description  </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="numberFormatDesc" value="${formatBean.description}" maxlength="128"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Card-Type </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select  name="cardTypeVar"  id="cdType" onchange="invokeLoadBIN('add',cdType.value)">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardTypes" items="${cardTypeMgtBeanLst}">
                                                                <c:if test="${cardTypes.cardTypeCode==formatBean.cardType}">
                                                                    <option value="${cardTypes.cardTypeCode}" selected="">${cardTypes.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardTypes.cardTypeCode!=formatBean.cardType}">
                                                                    <option value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                </tr>
<!--                                                <tr>
                                                    <td>Production Mode </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select  name="productionMode" onchange="invokeLoadBIN('add',cdType.value,prdMode.value)" id="prdMode">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="prodMode" items="${productModes}">
                                                                <c:if test="${prodMode.key==formatBean.productMode}">
                                                                    <option value="${prodMode.key}" selected="">${prodMode.value}</option>
                                                                </c:if>
                                                                <c:if test="${prodMode.key!=formatBean.productMode}">
                                                                    <option value="${prodMode.key}">${prodMode.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>-->
                                                <tr>
                                                    <td>Select BIN</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <h4><b>Not Assigned BIN</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${NotAssignbinList}">
                                                                <option value="${notassign.key}" >${notassign.value}</option>
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
                                                                <option value="${assign.key}" >${assign.value}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card-Number Length </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><select onchange="goToSelectOnChangeNumber('add',assignlist,notassignlist,cdType.value)" name="numberLength" >
                                                            <option value="">SELECT</option>

                                                            <c:forEach var="i" begin="15" end="19" >
                                                                <c:if test="${formatBean.cardNumberLength==i}">
                                                                    <option value="${i}" selected="true">${i}</option>
                                                                </c:if>
                                                                <c:if test="${formatBean.cardNumberLength!=i}">
                                                                    <option value="${i}">${i}</option>
                                                                </c:if>

                                                            </c:forEach>


                                                        </select> 
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>


                                        <table cellspacing="10" >
                                            <tbody>
                                                <%

                                                    int length = 0;
                                                    int end = 0;

                                                    try {


                                                        NumberGenerationFormatBean formatBean = new NumberGenerationFormatBean();
                                                        formatBean = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                        length = Integer.parseInt(formatBean.getCardNumberLength());
                                                        end = length - 1;



                                                    } catch (Exception ex) {
                                                    }



                                                %>

                                                <tr>
                                                    <td style="width: 200px"></td>
                                                    <td>From </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="1" name="fromOne" readonly="true" /></td>

                                                    <td>To  </td>
                                                    <td>&nbsp;</td>

                                                    <td>
                                                        <input type="text" value="6" name="toOne"  readonly="true" />

                                                    </td>


                                                    <td>Format </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="FBIN" name="bin" readonly="true" /></td>


                                                </tr>

                                                <tr>
                                                    <td ></td>
                                                    <td>From  </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <input type="text" value="7" name="fromTwo" readonly="true" />
                                                    </td> 
                                                    <td>To  </td>
                                                    <td>&nbsp;</td>

                                                    <td>

                                                        <select  name="toTwo" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'2')" >
                                                            <option value="">SELECT</option>
                                                            <c:forEach var="i" begin="7" end="<%=end%>" >
                                                                <c:if test="${formatBean.toTwo==i}">
                                                                    <option value="${i}" selected="true">${i}</option>
                                                                </c:if>
                                                                <c:if test="${formatBean.toTwo!=i}">
                                                                    <option value="${i}" >${i}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>

                                                    <td>Format </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select name="formatTwo"   onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'2')" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="format" items="${formatFieldBeans}">

                                                                <c:if test="${format.formatCode ==formatBean.formatTwo}">
                                                                    <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                </c:if>
                                                                <c:if test="${format.formatCode !=formatBean.formatTwo}">
                                                                    <option value="${format.formatCode}">${format.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>





                                                <c:if test="${formatBean.toTwo!=null && formatBean.toTwo!=''&& formatBean.toTwo!=0}">
                                                    <tr>

                                                        <%

                                                            NumberGenerationFormatBean formatBean = new NumberGenerationFormatBean();
                                                            formatBean = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int value = formatBean.getToTwo();
                                                            ++value;

                                                            //int value = Integer.parseInt((String) request.getAttribute("toTwo"));
                                                            // ++value;
                                                        %>


                                                        <% if (value < length) {%>   


                                                        <td ></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= value%>" name="fromThree" readonly="true" />


                                                        </td>




                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>
                                                            <select  name="toThree" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'3')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= value%>" end="<%=end%>" >
                                                                    <c:if test="${formatBean.toThree==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toThree!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatThree"   onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'3')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatThree}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatThree}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toThree!=null && formatBean.toThree!='' && formatBean.toThree!=0}">
                                                    <tr>

                                                        <%

                                                            NumberGenerationFormatBean formatBeanToThree = new NumberGenerationFormatBean();
                                                            formatBeanToThree = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueToThree = formatBeanToThree.getToThree();
                                                            ++valueToThree;


                                                        %>


                                                        <% if (valueToThree < length) {%>   


                                                        <td ></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToThree%>" name="fromFour" readonly="true" />
                                                        </td>

                                                        <td>To </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select  name="toFour" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'4')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToThree%>" end="<%=end%>" >
                                                                    <c:if test="${formatBean.toFour==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toFour!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatFour" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'4')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatFour}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatFour}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toFour!=null && formatBean.toFour!='' && formatBean.toFour!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeanToFour = new NumberGenerationFormatBean();
                                                            formatBeanToFour = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueToFour = formatBeanToFour.getToFour();
                                                            ++valueToFour;


                                                        %>


                                                        <% if (valueToFour < length) {%>   


                                                        <td ></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToFour%>" name="fromFive" readonly="true" />
                                                        </td>

                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>
                                                            <select  name="toFive" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'5')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToFour%>" end="<%=end%>" >
                                                                    <c:if test="${formatBean.toFive==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toFive!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatFive" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'5')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatFive}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatFive}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toFive!=null && formatBean.toFive!='' && formatBean.toFive!=0}">
                                                    <tr>
                                                        <%
                                                            NumberGenerationFormatBean formatBeanToFive = new NumberGenerationFormatBean();
                                                            formatBeanToFive = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueToFive = formatBeanToFive.getToFive();
                                                            ++valueToFive;

                                                        %>


                                                        <% if (valueToFive < length) {%>   


                                                        <td ></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToFive%>" name="fromSix" readonly="true" />
                                                        </td>

                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>
                                                            <select  name="toSix" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'6')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToFive%>" end="<%=end%>" >
                                                                    <c:if test="${formatBean.toSix==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toSix!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatSix"   onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'6')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">
                                                                    <c:if test="${format.formatCode ==formatBean.formatSix}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatSix}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                            <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toSix!=null && formatBean.toSix!=''  && formatBean.toSix!=0}">
                                                    <tr>
                                                        <%
                                                            NumberGenerationFormatBean formatBeanToSix = new NumberGenerationFormatBean();
                                                            formatBeanToSix = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueToSix = formatBeanToSix.getToSix();
                                                            ++valueToSix;

                                                        %>


                                                        <% if (valueToSix < length) {%>   


                                                        <td ></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToSix%>" name="fromSeven" readonly="true" />
                                                        </td>
                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>

                                                            <select  name="toSeven" onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'7')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToSix%>" end="<%=end%>" >
                                                                    <c:if test="${formatBean.toSeven==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toSeven!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatSeven"   onchange="goToSelectOnChange('add',assignlist,notassignlist,cdType.value,'7')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatSeven}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatSeven}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                            <% }%> 
                                                    </tr>
                                                </c:if>

                                                <tr>
                                                    <td ></td>
                                                    <td>Last Digit </td>
                                                    <td>&nbsp;</td>
                                                    <% if (length > 0) {%>   
                                                    <td><input type="text" value="<%= length%>" name="lastDigit" readonly="true" /></td>
                                                        <% }%> 
                                                        <% if (length == 0) {%>   
                                                    <td><input type="text" value="" name="lastDigit" readonly="true" /></td>
                                                        <% }%> 
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td>
                                                        <input type="button" value="Add"  onclick="selectAllForUpdate('add',assignlist,notassignlist,cdType.value)" class="defbutton" /></td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="button" value="Reset" onclick="goToResetPage()" class="defbutton" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <!--
                                        <c:if test="${onLoad=='yes'}" >
                                            <script >  
                                            
                                                invokeLoadBIN('add','${cardType}',${productionMode},'yes');
           
    
                                            </script> 
                                        </c:if>
                                        -->
                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >

                                    <form name="updatecardnumberformatform" id="addcardnumberformatform" method="POST">
                                        <table cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>  
                                                    <td><input type="hidden" name="operationtype" value="update"/></td>
                                                </tr>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Number-Format Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="numberFormatCode" readonly value="${formatBean.formatCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Number-Format Description  </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="numberFormatDesc" value="${formatBean.description}" maxlength="128"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Card-Type </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select  name="cardTypeVar"  id="cdType" disabled="">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardTypes" items="${cardTypeMgtBeanLst}">
                                                                <c:if test="${cardTypes.cardTypeCode==formatBean.cardType}">
                                                                    <option value="${cardTypes.cardTypeCode}" selected="">${cardTypes.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardTypes.cardTypeCode!=formatBean.cardType}">
                                                                    <option value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Select BIN</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <h4><b>Not Assigned BIN</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${NotAssignbinList}">
                                                                <option value="${notassign.key}" >${notassign.value}</option>
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
                                                                <option value="${assign.key}" >${assign.value}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card-Number Length </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><select onchange="goToSelectOnChangeNumber('update',assignlist,notassignlist,cdType.value)" name="numberLength">
                                                            <option value="">SELECT</option>

                                                            <c:forEach var="i" begin="15" end="19" >
                                                                <c:if test="${formatBean.cardNumberLength==i}">
                                                                    <option value="${i}" selected="true">${i}</option>
                                                                </c:if>
                                                                <c:if test="${formatBean.cardNumberLength!=i}">
                                                                    <option value="${i}">${i}</option>
                                                                </c:if>

                                                            </c:forEach>


                                                        </select> 
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>


                                        <table cellspacing="10" >
                                            <tbody>
                                                <%

                                                    int lengthUp = 0;
                                                    int endUp = 0;

                                                    try {
                                                        NumberGenerationFormatBean formatBean = new NumberGenerationFormatBean();
                                                        formatBean = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                        lengthUp = Integer.parseInt(formatBean.getCardNumberLength());
                                                        endUp = lengthUp - 1;

                                                    } catch (Exception ex) {
                                                    }



                                                %>

                                                <tr>
                                                    <td style="width: 200px"></td>
                                                    <td>From </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="1" name="fromOne" readonly="true" /></td>

                                                    <td>To  </td>
                                                    <td>&nbsp;</td>

                                                    <td>
                                                        <input type="text" value="6" name="toOne"  readonly="true" />

                                                    </td>


                                                    <td>Format </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="FBIN" name="bin" readonly="true" ></td>


                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>From  </td>
                                                    <td>&nbsp;</td>

                                                    <td>
                                                        <input type="text" value="7" name="fromTwo" readonly="true" />

                                                    </td> 

                                                    <td>To  </td>
                                                    <td>&nbsp;</td>

                                                    <td>

                                                        <select  name="toTwo" onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'2')"  >
                                                            <option value="">SELECT</option>
                                                            <c:forEach var="i" begin="7" end="<%=endUp%>" >
                                                                <c:if test="${formatBean.toTwo==i}">
                                                                    <option value="${i}" selected="true">${i}</option>
                                                                </c:if>
                                                                <c:if test="${formatBean.toTwo!=i}">
                                                                    <option value="${i}" >${i}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>



                                                    <td>Format </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select name="formatTwo"   onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'2')" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="format" items="${formatFieldBeans}">

                                                                <c:if test="${format.formatCode ==formatBean.formatTwo}">
                                                                    <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                </c:if>
                                                                <c:if test="${format.formatCode !=formatBean.formatTwo}">
                                                                    <option value="${format.formatCode}">${format.description}</option>
                                                                </c:if>



                                                            </c:forEach>
                                                        </select></td>





                                                </tr>





                                                <c:if test="${formatBean.toTwo!=null && formatBean.toTwo!='' && formatBean.toTwo!=0}">
                                                    <tr>

                                                        <%


                                                            NumberGenerationFormatBean formatBean = new NumberGenerationFormatBean();
                                                            formatBean = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueUp = formatBean.getToTwo();
                                                            ++valueUp;
                                                        %>


                                                        <% if (valueUp < lengthUp) {%>   

                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueUp%>" name="fromThree" readonly="true" />


                                                        </td>




                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>

                                                            <select  name="toThree" onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'3')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueUp%>" end="<%=endUp%>" >
                                                                    <c:if test="${formatBean.toThree==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toThree!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatThree"   onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'3')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">
                                                                    <c:if test="${format.formatCode ==formatBean.formatThree}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatThree}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toThree!=null && formatBean.toThree!='' && formatBean.toThree!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeanToThree = new NumberGenerationFormatBean();
                                                            formatBeanToThree = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));

                                                            int valueToThreeUp = formatBeanToThree.getToThree();
                                                            ++valueToThreeUp;
                                                        %>


                                                        <% if (valueToThreeUp < lengthUp) {%>   

                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToThreeUp%>" name="fromFour" readonly="true" />
                                                        </td>

                                                        <td>To </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select  name="toFour" onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'4')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToThreeUp%>" end="<%=endUp%>" >
                                                                    <c:if test="${formatBean.toFour==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toFour!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatFour"   onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'4')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatFour}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatFour}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toFour!=null && formatBean.toFour!='' && formatBean.toFour!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeanToFour = new NumberGenerationFormatBean();
                                                            formatBeanToFour = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueToFourUp = formatBeanToFour.getToFour();
                                                            ++valueToFourUp;


                                                        %>


                                                        <% if (valueToFourUp < lengthUp) {%>   

                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToFourUp%>" name="fromFive" readonly="true" />
                                                        </td>

                                                        <td>To </td>
                                                        <td>&nbsp;</td>
                                                        <td>

                                                            <select  name="toFive" onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'5')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToFourUp%>" end="<%=endUp%>" >
                                                                    <c:if test="${formatBean.toFive==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toFive!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatFive"   onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'5')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">
                                                                    <c:if test="${format.formatCode ==formatBean.formatFive}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatFive}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toFive!=null && formatBean.toFive!='' && formatBean.toFive!=0}">
                                                    <tr>

                                                        <%

                                                            NumberGenerationFormatBean formatBeanToFive = new NumberGenerationFormatBean();
                                                            formatBeanToFive = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));

                                                            int valueToFiveup = formatBeanToFive.getToFive();

                                                            ++valueToFiveup;

                                                        %>


                                                        <% if (valueToFiveup < lengthUp) {%>   

                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToFiveup%>" name="fromSix" readonly="true" />
                                                        </td>
                                                        
                                                        <td>To </td>
                                                        <td>&nbsp;</td>
                                                        <td>

                                                            <select  name="toSix" onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'6')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToFiveup%>" end="<%=endUp%>" >
                                                                    <c:if test="${formatBean.toSix==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toSix!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatSix"   onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'6')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatSix}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatSix}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        <% }%> 
                                                    </tr>
                                                </c:if>

                                                <c:if test="${formatBean.toSix!=null && formatBean.toSix!='' && formatBean.toSix!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeantoSix = new NumberGenerationFormatBean();
                                                            formatBeantoSix = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));

                                                            int valueToSixUp = formatBeantoSix.getToSix();
                                                            System.out.println(valueToSixUp + "****************************");
                                                            ++valueToSixUp;


                                                        %>


                                                        <% if (valueToSixUp < lengthUp) {%>   

                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToSixUp%>" name="fromSeven" readonly="true" />
                                                        </td>

                                                        <td>To </td>
                                                        <td>&nbsp;</td>
                                                        <td>

                                                            <select  name="toSeven" onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'7')">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToSixUp%>" end="<%=endUp%>" >
                                                                    <c:if test="${formatBean.toSeven==i}" >
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toSeven!=i}" >
                                                                        <option value="${i}" >${i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatSeven"   onchange="goToSelectOnChange('update',assignlist,notassignlist,cdType.value,'7')" >
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatSeven}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatSeven}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>

                                                        <% }%> 
                                                    </tr>
                                                </c:if>
                                                <tr>
                                                    <td></td>
                                                    <td>Last Digit </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="${formatBean.checkDigit}" name="lastDigit" readonly="true" /></td>

                                                </tr>
                                                <tr>
                                                    <td></td>                                                    
                                                    <td>
                                                        <input type="button" value="Update" onclick="selectAllForUpdate('update',assignlist,notassignlist,cdType.value)" class="defbutton"/>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td  colspan="4">                                                        
                                                        <input type="reset" value="Reset" onclick="goResetUpdate('${formatBean.formatCode}')" class="defbutton" />
                                                        &nbsp;&nbsp;&nbsp;
                                                        <input type="reset" name="back" value="Back" onclick="goBackFromUpdate('yes')" class="defbutton"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST"name="viewcardnumberformatform" id="viewcardnumberformatform" >
                                        <table cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>  
                                                    <td><input type="hidden" name="operationtype" value="add" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Number-Format Code </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="numberFormatCode" readonly="" value="${formatBean.formatCode}" style="color:#949494"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Number-Format Description  </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="numberFormatDesc" readonly="" value="${formatBean.description}" style="color:#949494"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Card-Type </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select  name="cardTypeVar" disabled="">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardTypes" items="${cardTypeMgtBeanLst}">
                                                                <c:if test="${cardTypes.cardTypeCode==formatBean.cardType}">
                                                                    <option value="${cardTypes.cardTypeCode}" selected="">${cardTypes.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardTypes.cardTypeCode!=formatBean.cardType}">
                                                                    <option value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

<!--                                                <tr>
                                                    <td>Production Mode </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select  name="productionMode" disabled="">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="prodMode" items="${productModes}">
                                                                <c:if test="${prodMode.key==formatBean.productMode}">
                                                                    <option value="${prodMode.key}" selected="">${prodMode.value}</option>
                                                                </c:if>
                                                                <c:if test="${prodMode.key!=formatBean.productMode}">
                                                                    <option value="${prodMode.key}">${prodMode.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>-->
                                                <tr>
                                                    <td>Select BIN</td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <h4><b>Not Assigned BIN</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${NotAssignbinList}">
                                                                <option value="${notassign.key}" >${notassign.value}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>

                                                    <td >
                                                    </td>

                                                    <td>
                                                        <h4><b>Assigned BIN</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10 >
                                                            <c:forEach var="assign" items="${AssignbinList}">
                                                                <option value="${assign.key}" >${assign.value}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card-Number Length </td>
                                                    <td>&nbsp;</td>
                                                    <td><select  name="numberLength" disabled="">
                                                            <option value="">SELECT</option>

                                                            <c:forEach var="i" begin="15" end="19" >
                                                                <c:if test="${formatBean.cardNumberLength==i}">
                                                                    <option value="${i}" selected="true">${i}</option>
                                                                </c:if>
                                                                <c:if test="${formatBean.cardNumberLength!=i}">
                                                                    <option value="${i}">${i}</option>
                                                                </c:if>

                                                            </c:forEach>


                                                        </select> 
                                                    </td>
                                                </tr>                                


                                            </tbody>
                                        </table>


                                        <table cellspacing="10">
                                            <tbody>
                                                <%

                                                    int lengthUp = 0;
                                                    int endUp = 0;

                                                    try {
                                                        NumberGenerationFormatBean formatBean = new NumberGenerationFormatBean();
                                                        formatBean = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                        lengthUp = Integer.parseInt(formatBean.getCardNumberLength());
                                                        endUp = lengthUp - 1;

                                                    } catch (Exception ex) {
                                                    }



                                                %>

                                                <tr>
                                                    <td style="width: 200px"></td>
                                                    <td>From </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="1" name="fromOne" readonly="true" style="color:#949494"/></td>

                                                    <td>To  </td>
                                                    <td>&nbsp;</td>

                                                    <td>
                                                        <input type="text" value="6" name="toOne"  readonly="true" style="color:#949494"/>

                                                    </td>


                                                    <td>Format </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="FBIN" name="bin" readonly="true" style="color:#949494"/></td>


                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>From  </td>
                                                    <td>&nbsp;</td>

                                                    <td>
                                                        <input type="text" value="7" name="fromTwo" readonly="true" style="color:#949494"/>

                                                    </td> 

                                                    <td>To  </td>
                                                    <td>&nbsp;</td>

                                                    <td>

                                                        <select  name="toTwo" disabled=""  >
                                                            <option value="">SELECT</option>
                                                            <c:forEach var="i" begin="7" end="<%=endUp%>" >
                                                                <c:if test="${formatBean.toTwo==i}">
                                                                    <option value="${i}" selected="true">${i}</option>
                                                                </c:if>
                                                                <c:if test="${formatBean.toTwo!=i}">
                                                                    <option value="${i}" >${i}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>



                                                    <td>Format </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select name="formatTwo"   disabled=""  >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="format" items="${formatFieldBeans}">

                                                                <c:if test="${format.formatCode ==formatBean.formatTwo}">
                                                                    <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                </c:if>
                                                                <c:if test="${format.formatCode !=formatBean.formatTwo}">
                                                                    <option value="${format.formatCode}">${format.description}</option>
                                                                </c:if>



                                                            </c:forEach>
                                                        </select></td>  
                                                </tr>
                                                <c:if test="${formatBean.toTwo!=null && formatBean.toTwo!='' && formatBean.toTwo!=0}">
                                                    <tr>

                                                        <%

                                                            NumberGenerationFormatBean formatBean = new NumberGenerationFormatBean();
                                                            formatBean = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueUp = formatBean.getToTwo();
                                                            ++valueUp;
                                                        %>


                                                        <% if (valueUp < lengthUp) {%>   


                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueUp%>" name="fromThree" readonly="true" style="color:#949494"/>                                                          

                                                        </td>
                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>

                                                            <select  name="toThree"  disabled="">
                                                                <option value="">SELECT</option>

                                                                <c:forEach var="i" begin="<%= valueUp%>" end="<%=endUp%>" >


                                                                    <c:if test="${formatBean.toThree==i}" >

                                                                        <option value="${i}" selected="true">${i}</option>


                                                                    </c:if>
                                                                    <c:if test="${formatBean.toThree!=i}" >

                                                                        <option value="${i}" >${i}</option>


                                                                    </c:if>



                                                                </c:forEach>
                                                            </select>

                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatThree"   disabled="">
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">
                                                                    <c:if test="${format.formatCode ==formatBean.formatThree}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatThree}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>





                                                        <% }%> 
                                                    </tr>
                                                </c:if>





                                                <c:if test="${formatBean.toThree!=null && formatBean.toThree!='' && formatBean.toThree!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeanToThree = new NumberGenerationFormatBean();
                                                            formatBeanToThree = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));

                                                            int valueToThreeUp = formatBeanToThree.getToThree();
                                                            ++valueToThreeUp;
                                                        %>


                                                        <% if (valueToThreeUp < lengthUp) {%>   


                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>

                                                            <input type="text" value="<%= valueToThreeUp%>" name="fromFour" readonly="true" style="color:#949494"/>

                                                        </td>




                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>

                                                            <select  name="toFour"  disabled="">
                                                                <option value="">SELECT</option>

                                                                <c:forEach var="i" begin="<%= valueToThreeUp%>" end="<%=endUp%>" >


                                                                    <c:if test="${formatBean.toFour==i}" >

                                                                        <option value="${i}" selected="true">${i}</option>


                                                                    </c:if>
                                                                    <c:if test="${formatBean.toFour!=i}" >

                                                                        <option value="${i}" >${i}</option>


                                                                    </c:if>



                                                                </c:forEach>
                                                            </select>

                                                        </td>

                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatFour" disabled="">
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatFour}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatFour}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>



                                                                </c:forEach>
                                                            </select></td>




                                                        <% }%> 
                                                    </tr>
                                                </c:if>







                                                <c:if test="${formatBean.toFour!=null && formatBean.toFour!='' && formatBean.toFour!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeanToFour = new NumberGenerationFormatBean();
                                                            formatBeanToFour = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));
                                                            int valueToFourUp = formatBeanToFour.getToFour();
                                                            ++valueToFourUp;


                                                        %>


                                                        <% if (valueToFourUp < lengthUp) {%>   


                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>

                                                            <input type="text" value="<%= valueToFourUp%>" name="fromFive" readonly="true" style="color:#949494"/>

                                                        </td>




                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>

                                                            <select  name="toFive" disabled="">
                                                                <option value="">SELECT</option>

                                                                <c:forEach var="i" begin="<%= valueToFourUp%>" end="<%=endUp%>" >


                                                                    <c:if test="${formatBean.toFive==i}" >

                                                                        <option value="${i}" selected="true">${i}</option>


                                                                    </c:if>
                                                                    <c:if test="${formatBean.toFive!=i}" >

                                                                        <option value="${i}" >${i}</option>


                                                                    </c:if>



                                                                </c:forEach>
                                                            </select>

                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatFive" disabled="">
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatFive}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatFive}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>



                                                                </c:forEach>
                                                            </select></td>






                                                        <% }%> 
                                                    </tr>
                                                </c:if>





                                                <c:if test="${formatBean.toFive!=null && formatBean.toFive!='' && formatBean.toFive!=0}">
                                                    <tr>

                                                        <%

                                                            NumberGenerationFormatBean formatBeanToFive = new NumberGenerationFormatBean();
                                                            formatBeanToFive = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));

                                                            int valueToFiveup = formatBeanToFive.getToFive();

                                                            ++valueToFiveup;

                                                        %>


                                                        <% if (valueToFiveup < lengthUp) {%>   


                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>

                                                            <input type="text" value="<%= valueToFiveup%>" name="fromSix" readonly="true" style="color:#949494"/>

                                                        </td>




                                                        <td>To </td>
                                                        <td>&nbsp;</td>

                                                        <td>

                                                            <select  name="toSix" disabled="">
                                                                <option value="">SELECT</option>

                                                                <c:forEach var="i" begin="<%= valueToFiveup%>" end="<%=endUp%>" >


                                                                    <c:if test="${formatBean.toSix==i}" >

                                                                        <option value="${i}" selected="true">${i}</option>


                                                                    </c:if>
                                                                    <c:if test="${formatBean.toSix!=i}" >

                                                                        <option value="${i}" >${i}</option>


                                                                    </c:if>



                                                                </c:forEach>
                                                            </select>

                                                        </td>


                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatSix" disabled="">
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatSix}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatSix}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>



                                                                </c:forEach>
                                                            </select></td>



                                                        <% }%> 
                                                    </tr>
                                                </c:if>








                                                <c:if test="${formatBean.toSix!=null && formatBean.toSix!='' && formatBean.toSix!=0}">
                                                    <tr>

                                                        <%
                                                            NumberGenerationFormatBean formatBeantoSix = new NumberGenerationFormatBean();
                                                            formatBeantoSix = (NumberGenerationFormatBean) (request.getAttribute("formatBean"));

                                                            int valueToSixUp = formatBeantoSix.getToSix();
                                                            System.out.println(valueToSixUp + "****************************");
                                                            ++valueToSixUp;


                                                        %>

                                                        <% if (valueToSixUp < lengthUp) {%>                                                               

                                                        <td></td>
                                                        <td>From </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <input type="text" value="<%= valueToSixUp%>" name="fromSeven" readonly="true" style="color:#949494"/>
                                                        </td>
                                                        <td>To </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select  name="toSeven" disabled="">
                                                                <option value="">SELECT</option>
                                                                <c:forEach var="i" begin="<%= valueToSixUp%>" end="<%=endUp%>" >
                                                                    <c:if test="${formatBean.toSeven==i}" >                                                                                
                                                                        <option value="${i}" selected="true">${i}</option>
                                                                    </c:if>
                                                                    <c:if test="${formatBean.toSeven!=i}" >                                                                                
                                                                        <option value="${i}" >${i}</option>                                                                            

                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td>Format </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select name="formatSeven" disabled="">
                                                                <option value="" >--SELECT--</option>
                                                                <c:forEach var="format" items="${formatFieldBeans}">

                                                                    <c:if test="${format.formatCode ==formatBean.formatSeven}">
                                                                        <option selected="" value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                    <c:if test="${format.formatCode !=formatBean.formatSeven}">
                                                                        <option value="${format.formatCode}">${format.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>

                                                        <% }%> 
                                                    </tr>
                                                </c:if> 
                                                <tr>
                                                    <td></td>
                                                    <td>Last Digit </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" value="${formatBean.checkDigit}" name="lastDigit" readonly="true" style="color:#949494"/></td>

                                                </tr> 
                                                <tr>  
                                                    <td></td>

                                                    <td><input type="reset" name="back" value="Back" onclick="goBack()" class="defbutton"/></td>
                                                    <td></td>  
                                                    <td></td>

                                                </tr>                                                  
                                            </tbody>
                                        </table>
                                    </form>



                                </c:if>

                                <br></br>

                                <table  border="1"  class="display" id="scoreltableview2">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Format Code</th>
                                            <th>Description</th>
                                            <th>Card Type</th>
                                            <th>Length</th>


                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="formatCodeBean" items="${formatCodeBeans}">
                                            <tr>

                                                <td >${formatCodeBean.formatCode}</td>
                                                <td >${formatCodeBean.description}</td>
                                                <td >${formatCodeBean.cardType}</td>
                                                <td >${formatCodeBean.numberLength}</td>



                                                <td  ><a href='${pageContext.request.contextPath}/ViewNumberFormatServlet?formatCode=<c:out value="${formatCodeBean.formatCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateNumberFormatServlet?formatCode=<c:out value="${formatCodeBean.formatCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${formatCodeBean.formatCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <!--/////////////////////////////////////////////    End Table   //////////////////////////////////////////////////////////-->      









                                <!--   ------------------------- end developer area  --------------------------------            -->              

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
