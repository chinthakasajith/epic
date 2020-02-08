<%-- 
    Document   : updateterminaldata
    Created on : Dec 10, 2012, 10:08:49 AM
    Author     : nalin
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

                document.updateterminalform.action="${pageContext.request.contextPath}/UpdateCallCenterTerminalDataServlet?id="+id+"&assigned="+assigned+"&notassigned="+notassigned;
                document.updateterminalform.submit();

            }
            
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCreateTerminaDataServlet";

            }
            
            function invokeResetInUpdate()
            {

                window.location = "${pageContext.request.contextPath}/LoadUpdateCallCenterTerminalDataServlet";

            }
            function goBackLoad()
            {

                window.location = "${pageContext.request.contextPath}/LoadTerminalDataCaptureServlet";

            }
            
            function goBack(val,mid){
                window.location = "${pageContext.request.contextPath}/ViewMerchantMgtServlet?section=ACCTER&id="+val+"&mid="+mid;
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
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_TERMINAL_UPDATE%>'                                  
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
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>

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
                                                <td><input type="text" style="color:#555555"  value="${terminalBean.terminalID}" name="terminalid" maxlength="8" readonly="true" disabled="true"/>
                                                    <input type="hidden" style="color:#555555"  value="${terminalBean.terminalID}" name="terminalid" maxlength="8" readonly="true"/></td>
                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td style="width: 200px;">Name</td>
                                                <td><input type="text"  value="${terminalBean.name}" name="name" maxlength="15" />
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><input type="text" style="color:#555555"  value="${terminalBean.merchantID}" name="mid" maxlength="15" readonly="true" disabled="true"/>
                                                    <input type="hidden" style="color:#555555"  value="${terminalBean.merchantID}" name="mid" maxlength="15" readonly="true"/></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">MCC</td>
                                                <td><input type="text" style="color:#555555"  value="${terminalBean.mcc}" name="mcc" maxlength="15" readonly="true" disabled="true"/>
                                                    <input type="hidden" style="color:#555555"  value="${terminalBean.mcc}" name="mcc" maxlength="15" readonly="true"/></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Currency</td>
                                                <td><input type="text"  style="color:#555555" value="${terminalBean.currency}" name="currency" maxlength="15" readonly="true" disabled="true"/>
                                                    <input type="hidden"  style="color:#555555" value="${terminalBean.currency}" name="currency" maxlength="15" readonly="true"/></td>
                                                <td></td>
                                            </tr>
                                            <tr><td><label ><u> Hardware Details</u> </label></td></tr>
                                            <tr>
                                                <td style="width: 200px;">Serial Number</td>
                                                <td><input type="text"  value="${terminalBean.serialNo}" name="serialnumber" readonly="readonly" maxlength="16" disabled="true"/>
                                                    <input type="hidden"  value="${terminalBean.serialNo}" name="serialnumber" readonly="readonly" maxlength="16"/></td>
                                                <td></td>

                                            </tr>
                                            <c:if test="${isAllocate == 'ALYES'}">
                                                <tr>
                                                    <td style="width: 200px;">Manufacturer</td>
                                                    <td><select name="manufacturer" maxlength="32" id="manufact" disabled="true" >
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
                                                        <input type="hidden"  style="color:#555555" value="${terminalBean.manufacturer}" name="manufacturer" maxlength="15" readonly="true"/>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${isAllocate == 'ALNO'}">
                                                <tr>
                                                    <td style="width: 200px;">Manufacturer</td>
                                                    <td><select name="manufacturer" maxlength="32" id="manufact" disabled="true">
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
                                                         <input type="hidden"  style="color:#555555" value="${terminalBean.manufacturer}" name="manufacturer" maxlength="15" readonly="true"/>
                                                    </td>
                                                </tr>
                                            </c:if>

                                            <tr>
                                                <td style="width: 200px;">Model</td>
                                                <td>
                                                    <select name="model" maxlength="16" disabled="true">
                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="model" items="${difManufactermModels}" >
                                                            <c:if test="${terminalBean.model==model.key}">
                                                                <option value="${model.key}" selected>${model.value}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.model != model.key}">
                                                                <option value="${model.key}" >${model.value}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                     <input type="hidden"  style="color:#555555" value="${terminalBean.model}" name="model" maxlength="15" readonly="true"/>
                                                </td>
                                            </tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr>
                                                <td style="width: 200px;">Installation Date</td>
                                                <td>
                                                    <input  name="installationDate" readonly maxlength="16" value="${terminalBean.installationDate}" key="installationDate" id="installationDate" disabled="true"  />
                                                    <input type="hidden"  name="installationDate" readonly maxlength="16" value="${terminalBean.installationDate}" key="installationDate" id="installationDate"  />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Activation Date</td>
                                                <td>
                                                    <input  name="activationDate" readonly maxlength="16" value="${terminalBean.activationDate}" key="activationDate" id="activationDate" disabled="true"  />
                                                    <input type="hidden" name="activationDate" readonly maxlength="16" value="${terminalBean.activationDate}" key="activationDate" id="activationDate"  /></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Allocation Status</td>
                                                <td><input type="text" style="color:#555555"  value="${terminalBean.alloStatus}" name="allocatestatus"  readonly="true"/>
                                                <input type="hidden"  value="${terminalBean.allocationStatus}" name="allocatestatuscode"  /></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Terminal Status</td>
                                                <td><input style="color:#555555" type="text"  value="${terminalBean.terminalStatusDes}" name="terminalstatus"  readonly="true"/>
                                                <input type="hidden"  value="${terminalBean.terminalStatus}" name="terminalstatuscode"  /></td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <br /><br />
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
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
                                        </tbody>
                                    </table>
                                    <br /><br />
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td></td>
                                                <td colspan="1">
                                                    <c:if test="${isAllocate == 'ALYES'}">
                                                        <input type="submit" value="Update" name="update" style="width: 100px" onclick="selectAllForUpdate('${terminalBean.terminalID}',assignlist,notassignlist)"/>
                                                    </c:if>
                                                    <c:if test="${isAllocate == 'ALNO'}">
                                                        <input type="submit" value="Update" name="update" style="width: 100px" onclick="invokeUpdate('${terminalBean.terminalID}','0','0')"/>
                                                    </c:if>
                                                    <input type="button" value="Cancel" name="back" style="width: 100px" onclick="goBack('${terminalBean.terminalID}','${terminalBean.merchantID}')"/>
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeResetInUpdate()"/></td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </form>


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
