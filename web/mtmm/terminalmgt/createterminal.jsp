<%-- 
    Document   : terminalmgthome
    Created on : Apr 27, 2012, 1:54:33 PM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            //this method is calledwhen user tries to update if the terminal is allocated
            function selectAllForUpdate(id,selectBox1,selectBox2) {
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
                
                invokeUpdate(id,assigned,notassigned);
            }
            
            function selectAllForUpdateOnChange(selectBox1,selectBox2,value,opType) {
                var assigned = -1;
                var notassigned = -1;
                
                if(selectBox1.options.length != 0){
                    for (var i = 0; i < selectBox1.options.length; i++) 
                    {
                        selectBox1.options[i].selected = true;
                    }
                }else{
                    assigned = "0";
                }
                if(selectBox2.options.length != 0)
                {
                    for (var i = 0; i < selectBox2.options.length; i++)
                    {
                        selectBox2.options[i].selected = true;
                    }
                }else                {
                    notassigned = "0";
                }
                
                loadTerminalModelsOnManufactInUpdate(value,opType,assigned,notassigned)
            }

            function invokeUpdate(id,assigned,notassigned)
            {

                document.updateterminalform.action="${pageContext.request.contextPath}/UpdateTerminalDataCaptureServlet?id="+id+"&assigned="+assigned+"&notassigned="+notassigned;
                document.updateterminalform.submit();

            }
            
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCreateTerminaDataServlet";

            }
            
            function invokeResetInUpdate(id,status,manu,mid,isDefault)
            {

                window.location = "${pageContext.request.contextPath}/LoadUpdateTerminalDataServlet?id="+id+"&status="+status+"&manu="+manu+"&mid="+mid+"&isDefault="+isDefault;

            }
            function goBackLoad()
            {

                window.location = "${pageContext.request.contextPath}/LoadTerminalDataCaptureServlet";

            }
            
            function goBack(){
                window.location = "${pageContext.request.contextPath}/SearchTerminalDataCaptureServlet?isBack="+"back";
            }
            function invokeCreate(manufacturer){
                document.createterminalform.action="${pageContext.request.contextPath}/AddTerminalDataCaptureServlet?manufacturer="+manufacturer;
                document.createterminalform.submit();
            }
            function loadTerminalModelsOnManufact(value,opType){
                
                document.createterminalform.action="${pageContext.request.contextPath}/SetTerminalModelDropDownServlet?manufacturer="+value+"&opType="+opType;
                document.createterminalform.submit();
                
            }
            function loadTerminalModelsOnManufactInUpdate(value,opType,assigned,notassigned){
                
                
                document.updateterminalform.action="${pageContext.request.contextPath}/SetTerminalModelDropDownServlet?manufacturer="+value+"&opType="+opType+"&isAllocate=NO"+"&assigned="+assigned+"&notassigned="+notassigned;
                document.updateterminalform.submit();
                
            }
            
            function loadTerminalModelsOnManufactInUpdateAllocated(value,opType,selectBox1,selectBox2){
                
                var assigned = -1;
                var notassigned = -1;
                
                if(selectBox1.options.length != 0){
                
                    for (var i = 0; i < selectBox1.options.length; i++) {
                        selectBox1.options[i].selected = true;
                    }}else{
                    assigned = "0";
                }
                if(selectBox2.options.length != 0)
                {
                    for (var i = 0; i < selectBox2.options.length; i++) {
                        selectBox2.options[i].selected = true;
                    }
                }else                {
                    notassigned = "0";
                }
                document.updateterminalform.action="${pageContext.request.contextPath}/SetTerminalModelDropDownServlet?manufacturer="+value+"&opType="+opType+"&isAllocate=YES"+"&assigned="+assigned+"&notassigned="+notassigned;
                document.updateterminalform.submit();
                
            }
            


    

        </script>




    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">             

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>
                <div class="content" style="height: 500px">
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  TERMINAL DATA CAPTURING </td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                <%-- -------------------------add form start -------------------------------- --%>



                                <c:if test="${operationtype=='create'}" >
                                    <form method="POST" action="" name="createterminalform">
                                        <table>
                                           <tr>
                                        <td colspan="3">
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                        </table>

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr> <td style="height:20px;"></td></tr>
                                                <tr>
                                                    <td style="width: 200px;">Terminal ID(TID)</td>
                                                    <td><input type="text"  value="${terminalBean.terminalID}" name="terminalid" maxlength="8"/></td>
                                                    <td></td>
                                                </tr>                                          
                                                <tr>
                                                    <td style="width: 200px;">Name</td>
                                                    <td><input type="text"  value="${terminalBean.name}" name="name" maxlength="15"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td><label ><u> Hardware Details</u> </label></td></tr>
                                                <tr>
                                                    <td style="width: 200px;">Serial Number</td>
                                                    <td><input type="text"  value="${terminalBean.serialNo}" name="serialnumber" maxlength="16"/></td>
                                                    <td></td>

                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Manufacturer</td>
                                                    <td><select name="manufacturer" id="manufact" onchange="loadTerminalModelsOnManufact(manufact.value,'create')">
                                                            <option value="" selected="">--SELECT--</option>
                                                            <c:forEach var="manufacturer" items="${manufacturerList}">
                                                                <c:if test="${terminalBean.manufacturer==manufacturer.key}">
                                                                    <option value="${manufacturer.key}" selected>${manufacturer.value}</option>
                                                                </c:if>
                                                                <c:if test="${terminalBean.manufacturer != manufacturer.key}">
                                                                    <option value="${manufacturer.key}" >${manufacturer.value}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Model</td>
                                                    <td>
                                                        <select name="model">
                                                            <option value="" selected="">--SELECT--</option>
                                                            <c:forEach var="model" items="${difManufactermModels}">
                                                                <c:if test="${terminalBean.model==model.key}">
                                                                    <option value="${model.key}" selected>${model.value}</option>
                                                                </c:if>
                                                                <c:if test="${terminalBean.model != model.key}">
                                                                    <option value="${model.key}" >${model.value}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>

                                                <tr>
                                                    <td style="width: 200px;">Installation Date</td>
                                                    <td>
                                                        <input  name="installationDate" readonly maxlength="16" value="${terminalBean.installationDate}" key="installationDate" id="installationDate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#installationDate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px">Allocation Status</td>
                                                    <td>
                                                        <select name="allocatestatus" disabled="true">

                                                            <c:forEach var="allocate" items="${allocateList}">
                                                                <c:if test="${'ALNO'==allocate.key}">
                                                                    <option value="${allocate.key}"  selected>${allocate.value}</option>
                                                                </c:if>


                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px">Terminal Status</td>
                                                    <td>
                                                        <select name="terminalstatus" disabled="true">

                                                            <c:forEach var="terminal" items="${terminalStatusList}">
                                                                <c:if test="${'DEA'==terminal.key}">
                                                                    <option value="${terminal.key}" selected>${terminal.value}</option>
                                                                </c:if>

                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>



                                                <tr>
                                                    <td></td>
                                                    <td colspan="2"><input type="button" value="Add" style="width: 100px" onclick="invokeCreate('${terminalBean.manufacturer}')"/>

                                                        <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="goBackLoad()"/>
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateterminalform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>
                                            </tr>
                                        </table>

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr> <td style="height:20px;"></td></tr>
                                                <tr>
                                                    <td style="width: 200px;">Terminal ID(TID)</td>
                                                    <td><input type="text" style="color:#555555"  value="${terminalBean.terminalID}" name="terminalid" maxlength="8" readonly="true"/></td>
                                                    <td></td>
                                                </tr>                                          
                                                <tr>
                                                    <td style="width: 200px;">Name</td>
                                                    <td><input type="text"  value="${terminalBean.name}" name="name" maxlength="15"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Merchant ID</td>
                                                    <td><input type="text" style="color:#555555"  value="${terminalBean.merchantID}" name="mid" maxlength="15" readonly="true"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">MCC</td>
                                                    <td><input type="text" style="color:#555555"  value="${terminalBean.mcc}" name="mcc" maxlength="15" readonly="true"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Currency</td>
                                                    <td><input type="text"  style="color:#555555" value="${terminalBean.currency}" name="currency" maxlength="15" readonly="true"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td><label ><u> Hardware Details</u> </label></td></tr>
                                                <tr>
                                                    <td style="width: 200px;">Serial Number</td>
                                                    <td><input type="text"  value="${terminalBean.serialNo}" name="serialnumber" maxlength="16"/></td>
                                                    <td></td>

                                                </tr>
                                                <c:if test="${isAllocate == 'ALYES'}">
                                                    <tr>
                                                        <td style="width: 200px;">Manufacturer</td>
                                                        <td><select name="manufacturer" maxlength="32" id="manufact" onchange="loadTerminalModelsOnManufactInUpdateAllocated(manufact.value,'update',assignlist,notassignlist)" >
                                                                <option value="" selected="">--SELECT--</option>
                                                                <c:forEach var="manufacturer" items="${manufacturerList}">
                                                                    <c:if test="${terminalBean.manufacturer==manufacturer.key}">
                                                                        <option value="${manufacturer.key}" selected>${manufacturer.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${terminalBean.manufacturer != manufacturer.key}">
                                                                        <option value="${manufacturer.key}" >${manufacturer.value}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${isAllocate == 'ALNO'}">
                                                    <tr>
                                                        <td style="width: 200px;">Manufacturer</td>
                                                        <td><select name="manufacturer" maxlength="32" id="manufact" onchange="loadTerminalModelsOnManufactInUpdate(manufact.value,'update') ">
                                                                <option value="" selected="">--SELECT--</option>
                                                                <c:forEach var="manufacturer" items="${manufacturerList}">
                                                                    <c:if test="${terminalBean.manufacturer==manufacturer.key}">
                                                                        <option value="${manufacturer.key}" selected>${manufacturer.value}</option>
                                                                    </c:if>
                                                                    <c:if test="${terminalBean.manufacturer != manufacturer.key}">
                                                                        <option value="${manufacturer.key}" >${manufacturer.value}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:if>

                                                <tr>
                                                    <td style="width: 200px;">Model</td>
                                                    <td>
                                                        <select name="model" maxlength="16">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="model" items="${difManufactermModels}">
                                                                <c:if test="${terminalBean.model==model.key}">
                                                                    <option value="${model.key}" selected>${model.value}</option>
                                                                </c:if>
                                                                <c:if test="${terminalBean.model != model.key}">
                                                                    <option value="${model.key}" >${model.value}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td style="width: 200px;">Installation Date</td>
                                                    <td>
                                                        <input  name="installationDate" readonly maxlength="16" value="${terminalBean.installationDate}" key="installationDate" id="installationDate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#installationDate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
  
                                                        </script>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Activation Date</td>
                                                    <td>
                                                        <input  name="activationDate" readonly maxlength="16" value="${terminalBean.activationDate}" key="activationDate" id="activationDate"  />
<!--                                                        <img src="<%=request.getContextPath()%>/resources/calender/images/calendar.png" id="trigger2" alt="Use this calendar to select date" />
                                                        <script type="text/javascript">
                                                            Calendar.setup({
                                                                inputField  : "activationDate",           // id of the input field
                                                                button      : "trigger2",        // trigger for the calendar (button ID)
                                                                ifFormat    : "%Y-%m-%d", // format of the input field
                                                                showsTime   : true,
                                                                timeFormat  : "12",
                                                                showOthers  : true
                                                            });
                                                        </script>-->

                                                    </td>
                                                </tr>
                                                <!--                                                <tr>
                                                                                                    <td style="width: 200px">Allocation Status</td>
                                                                                                    <td>
                                                                                                        <select name="allocatestatus" disabled="true">
                                                
                                                <c:forEach var="allocate" items="${allocateList}">
                                                    <c:if test="${terminalBean.allocationStatus==allocate.key}">
                                                        <option value="${allocate.key}" readonly="true" selected>${allocate.value}</option>
                                                    </c:if>

                                                </c:forEach>

                                            </select>
                                        </td>
                                    </tr>-->
                                                <tr>
                                                    <td style="width: 200px;">Allocation Status</td>
                                                    <td><input type="text" style="color:#555555"  value="${terminalBean.alloStatus}" name="allocatestatus"  readonly="true"/></td>
                                                    <td><input type="hidden"  value="${terminalBean.allocationStatus}" name="allocatestatuscode"  /></td>
                                                    <td></td>
                                                </tr>

                                                <!--                                                <tr>
                                                                                                    <td style="width: 200px">Terminal Status</td>
                                                                                                    <td>
                                                                                                        <select name="terminalstatus" disabled="true">
                                                
                                                <c:forEach var="terminal" items="${terminalStatusList}">
                                                    <c:if test="${terminalBean.terminalStatus==terminal.key}">
                                                        <option value="${terminal.key}" selected>${terminal.value}</option>
                                                    </c:if>

                                                </c:forEach>

                                            </select>
                                        </td>
                                    </tr>-->
                                                <tr>
                                                    <td style="width: 200px;">Terminal Status</td>
                                                    <td><input style="color:#555555" type="text"  value="${terminalBean.terminalStatusDes}" name="terminalstatus"  readonly="true"/></td>
                                                    <td><input type="hidden"  value="${terminalBean.terminalStatus}" name="terminalstatuscode"  /></td>
                                                    <td></td>
                                                </tr>
                                                <c:if test="${isAllocate == 'ALYES'}">
                                                    <tr>
                                                        <td>Transactions</td>
                                                    </tr>
                                                    <tr>

                                                        <td>
                                                            <h4><b>Not Assigned Transactions</b></h4>
                                                            <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="notassign" items="${notAssignedTxn}">
                                                                    <option value="${notassign.transactions}" >${notassign.transactionDes}</option>
                                                                </c:forEach>
                                                            </select>

                                                            <select name="notassignlisthide" style="width: 200px;display: none"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="notassign" items="${notAssignedTxn}">
                                                                    <option value="${notassign.transactionDes}" ></option>
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
                                                            <h4><b>Assigned Transactions</b></h4>
                                                            <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10 >
                                                                <c:forEach var="assign" items="${assignedTxn}">
                                                                    <option value="${assign.transactions}" >${assign.transactionDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <select name="assignlisthide" style="width: 200px;display: none" id=out multiple="multiple" size=10 >
                                                                <c:forEach var="assign" items="${assignedTxn}">
                                                                    <option value="${assign.transactionDes}" ></option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <tr>
                                                    <td></td>
                                                    <td colspan="2">
                                                        <c:if test="${isAllocate == 'ALYES'}">
                                                            <input type="button" value="Update" name="update" style="width: 100px" onclick="selectAllForUpdate('${terminalBean.terminalID}',assignlist,notassignlist)"/>
                                                        </c:if>
                                                        <c:if test="${isAllocate == 'ALNO'}">
                                                            <input type="button" value="Update" name="update" style="width: 100px" onclick="invokeUpdate('${terminalBean.terminalID}','0','0')"/>
                                                        </c:if>
                                                        <input type="button" value="Cancel" name="back" style="width: 100px" onclick="goBack()"/>
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeResetInUpdate('${terminalBean.terminalID}','${terminalBean.terminalStatus}',manufact.value,'${terminalBean.merchantID}','no')"/></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewTerminal" readonly="true">
                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Terminal ID</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.terminalID}</td>
                                                </tr>     
                                                <tr>
                                                    <td>Terminal Name</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.name}</td>
                                                </tr>
                                                <tr>
                                                    <td>Merchant ID</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.merchantID == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.merchantID != null}">
                                                        <td>${trmnlBean.merchantID}</td>
                                                    </c:if>

                                                </tr> 
                                                <tr>
                                                    <td>Merchant</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>

                                                    <c:if test="${trmnlBean.merchantDes == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.merchantDes != null}">
                                                        <td>${trmnlBean.merchantDes}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>MCC</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.mcc == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.mcc != null}">
                                                        <td>${trmnlBean.mcc}</td>
                                                    </c:if>

                                                </tr> 
                                                <tr>
                                                    <td>Currency</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.currency == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.currency != null}">
                                                        <td>${trmnlBean.currency}</td>
                                                    </c:if>

                                                </tr> 
                                                <tr>
                                                    <td>Serial Number</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.serialNo}</td>
                                                </tr>
                                                <tr>
                                                    <td>Manufacturer</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.manufactDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Model</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.modelDes}</td>
                                                </tr><tr>
                                                    <td>Installation Date</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.installationDate}</td>
                                                </tr><tr>
                                                    <td>Activation Date</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.activationDate == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.activationDate != null}">
                                                        <td>${trmnlBean.activationDate}</td>
                                                    </c:if>

                                                </tr><tr>
                                                    <td>Allocation Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.alloStatus}</td>
                                                </tr>

                                                <tr>
                                                    <td>Terminal Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.terminalStatusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Transactions</td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>

                                                    <td>
                                                        <h4><b>Not Assigned Transactions</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${notAssignedTxn}">
                                                                <option value="${notassign.transactions}" >${notassign.transactionDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td><td></td>
                                                    <!--                                                    <td align="center" >
                                                                                                            <input type=button value=">" onclick="moveout()" class="buttonsize"/><br>
                                                                                                            <input type=button value="<" onclick="movein()" class="buttonsize"/><br>
                                                                                                            <input type=button value=">>" onclick="moveallout()" class="buttonsize"/><br>
                                                                                                            <input type=button value="<<" onclick="moveallin()" class="buttonsize"/>
                                                                                                        </td>-->
                                                    <td></td>


                                                    <td>
                                                        <h4><b>Assigned Transactions</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach var="assign" items="${assignedTxn}">
                                                                <option value="${assign.transactions}" >${assign.transactionDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" name="back" style=" width:100px " onclick="goBack()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>



                                <%-- -------------------------add form end -------------------------------- --%>




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



