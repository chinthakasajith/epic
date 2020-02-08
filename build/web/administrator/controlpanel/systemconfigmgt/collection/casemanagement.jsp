<%-- 
    Document   : casemanagement
    Created on : Jul 3, 2013, 11:31:47 AM
    Author     : asitha_l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>        

        <script >
            
            function deleteCase(value){
                
                answer = confirm("Do you really want to delete Case Type Code "+value+" ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteCaseTypeServlet?id="+value;
                }
                

            }
            
            function invokeView(value){

                $.post("${pageContext.request.contextPath}/ViewCaseTypeServlet", {id:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/collection/viewcasetype.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }

                });
            }
            
            function enableConditioned(value){
                
                if(value=='OVERDUE'){
                    document.getElementById("entryoverlimitamount").value = '';
                    document.getElementById("exitoverlimitamount").value = '';
                    document.getElementById("entryoverlimittype").checked = false;
                    document.getElementById('fieldst2').disabled = true;
                    document.getElementById('exitoverlimitamount').disabled = true;
                    document.getElementById('fieldst1').disabled = false;
                    document.getElementById('exitoverdueamount').disabled = false;
                    document.getElementById('entryoverdueperiod').disabled = false;
                    document.getElementById('exitoverdueperiod').disabled = false;
                }else if(value=='OVERLIMIT'){ 
                    document.getElementById("entryoverdueperiod").value = '';
                    document.getElementById("exitoverdueperiod").value = '';                   
                    document.getElementById("entryoverdueamount").value = '';
                    document.getElementById("exitoverdueamount").value = '';
                    document.getElementById("entryoverduetype").checked = false;                 
                    document.getElementById('fieldst1').disabled = true;
                    document.getElementById('exitoverdueamount').disabled = true;
                    document.getElementById('entryoverdueperiod').disabled = true;
                    document.getElementById('exitoverdueperiod').disabled = true;
                    document.getElementById('fieldst2').disabled = false;
                    document.getElementById('exitoverlimitamount').disabled = false;
                }else{
                    document.getElementById('fieldst1').disabled = false;
                    document.getElementById('exitoverdueamount').disabled = false;
                    document.getElementById('fieldst2').disabled = false;
                    document.getElementById('exitoverlimitamount').disabled = false;
                    document.getElementById('entryoverdueperiod').disabled = false;
                    document.getElementById('exitoverdueperiod').disabled = false;
                }                             
            }
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CASE_MANAGEMENT%>'                                  
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

        <title>CMS CASE MANAGEMENT</title>
    </head>
    <body <c:if test="${operationtype=='update' || addValidation=='fail'}"> onload="enableConditioned('${caseTypeBean.entryCriteriaCode}')"</c:if>>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>
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
                                <!--  ----------------------start  developer area  -----------------------------------                      -->

                                <!--                                <table class="tit"> <tr> <td   class="center"><b> STANDING ORDER TYPES </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <c:if test="${operationtype=='add'}" > 
                                    <form name="addcase" method="POST" action="${pageContext.request.contextPath}/AddCaseMgtServlet">

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Case Type Code</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="casetypecode"  id="casetypecode" maxlength="8" value="${caseTypeBean.caseTypeCode}"/></td>

                                                <td style="width: 200px;">Case Description</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="casetypedes"  maxlength="32" value="${caseTypeBean.description}"/></td>
                                            </tr>                                                                                      
                                            <tr>
                                                <td style="width: 200px;">Currency Type</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="currency"  >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="currency" items="${currencyMap}">                                                            
                                                            <option value="${currency.key}" 
                                                                    <c:if test="${caseTypeBean.currencyTypeCode==currency.key}">selected</c:if>
                                                                    >${currency.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="status" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${statusMap}">                                                            
                                                            <option value="${status.key}" 
                                                                    <c:if test="${caseTypeBean.statusCode==status.key}">selected</c:if>
                                                                    >${status.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Severity Value</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="severityval"  maxlength="3" value="${caseTypeBean.severityValue}" /></td>

                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td></td>
                                            </tr>
                                            <tr>                                                
                                                <td style="width: 200px;">Criteria</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entrycriteria" id="entrycriteria" onchange="enableConditioned(entrycriteria.value)">
                                                        <option value="" selected>---------SELECT----------</option>                                                            
                                                        <option value="OVERDUE" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERDUE'}">selected</c:if>
                                                                >Overdue
                                                        </option>                                                                                                             
                                                        <option value="OVERLIMIT" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERLIMIT'}">selected</c:if>
                                                                >Over Limit
                                                        </option>
                                                        <option value="OVERDUEOROVERLIMIT" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERDUEOROVERLIMIT'}">selected</c:if>
                                                                >Overdue or Over Limit
                                                        </option>                                                                                                             
                                                        <option value="OVERDUEANDOVERLIMIT" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERDUEANDOVERLIMIT'}">selected</c:if>
                                                                >Overdue and Over Limit
                                                        </option> 
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Entry Overdue Period</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entryoverdueperiod" id="entryoverdueperiod">
                                                        <option value="" selected>---------SELECT----------</option>                                                            
                                                        <option value="1" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='1'}">selected</c:if>
                                                                >1 Month
                                                        </option>                                                                                                             
                                                        <option value="2" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='2'}">selected</c:if>
                                                                >2 Month
                                                        </option>
                                                        <option value="3" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='3'}">selected</c:if>
                                                                >3 Month
                                                        </option>                                                                                                             
                                                        <option value="4" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='4'}">selected</c:if>
                                                                >4 Month
                                                        </option> 
                                                    </select>
                                                </td>
                                            </tr>                                           
                                            <tr>
                                                <td style="width: 200px;">Entry Overdue Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><fieldset id="fieldst1" style="border:0 none;">
                                                        <table>
                                                            <tr><td><input type="radio" name="entryoverduetype" id="entryoverduetype" value="FIXED" 
                                                                           <c:if test="${caseTypeBean.overDueAmountType=='FIXED'}">checked</c:if>
                                                                           />Fixed</td></tr>
                                                            <tr><td><input type="radio" name="entryoverduetype" id="entryoverduetype" value="PERCENTA" 
                                                                           <c:if test="${caseTypeBean.overDueAmountType=='PERCENTA'}">checked</c:if>
                                                                           />Percentage</td></tr>
                                                            <tr><td><input type="text" name="entryoverdueamount"  id="entryoverdueamount" maxlength="25" value="${caseTypeBean.entryOverDueAmount}" /> </td></tr>
                                                        </table>
                                                    </fieldset>
                                                </td> 
                                                <td style="width: 200px;">Entry Over Limit Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><fieldset id="fieldst2" style="border:0 none;">
                                                        <table>
                                                            <tr><td><input type="radio" name="entryoverlimittype" id="entryoverlimittype" value="FIXED" 
                                                                           <c:if test="${caseTypeBean.overLimitAmountType=='FIXED'}">checked</c:if> 
                                                                           />Fixed</td></tr>
                                                            <tr><td><input type="radio" name="entryoverlimittype" id="entryoverlimittype" value="PERCENTA" 
                                                                           <c:if test="${caseTypeBean.overLimitAmountType=='PERCENTA'}">checked</c:if>
                                                                           />Percentage</td></tr>
                                                            <tr><td><input type="text" name="entryoverlimitamount"  id="entryoverlimitamount" maxlength="25" value="${caseTypeBean.entryOverLimitAmount}" /> </td></tr>
                                                        </table>
                                                    </fieldset>
                                                </td>                                              
                                            </tr>                                                                                 
                                            <tr>
                                                <td style="width: 200px;">Entry Account Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entryaccstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${statusMap}">                                                            
                                                            <option value="${status.key}" 
                                                                    <c:if test="${caseTypeBean.entryAccStatusCode==status.key}">selected</c:if>
                                                                    >${status.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Entry Card Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entrycardstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="cardstatus" items="${cardStatusMap}">                                                            
                                                            <option value="${cardstatus.key}" 
                                                                    <c:if test="${caseTypeBean.entryCardStatusCode==cardstatus.key}">selected</c:if>
                                                                    >${cardstatus.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Exit Overdue Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="exitoverdueamount"  id="exitoverdueamount" maxlength="25" value="${caseTypeBean.exitOverDueAmount}" /> </td>

                                                <td style="width: 200px;">Exit Overdue Period</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="exitoverdueperiod" id="exitoverdueperiod">
                                                        <option value="" selected>---------SELECT----------</option>                                                            
                                                        <option value="1" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='1'}">selected</c:if>
                                                                >1 Month
                                                        </option>                                                                                                             
                                                        <option value="2" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='2'}">selected</c:if>
                                                                >2 Month
                                                        </option>
                                                        <option value="3" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='3'}">selected</c:if>
                                                                >3 Month
                                                        </option>                                                                                                             
                                                        <option value="4" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='4'}">selected</c:if>
                                                                >4 Month
                                                        </option> 
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Exit Over Limit Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="exitoverlimitamount" id="exitoverlimitamount" maxlength="25" value="${caseTypeBean.exitOverLimitAmount}" /> </td>
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td></td>                                           
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Exit Account Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="exitaccstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${statusMap}">                                                            
                                                            <option value="${status.key}" 
                                                                    <c:if test="${caseTypeBean.exitAccStatusCode==status.key}">selected</c:if>
                                                                    >${status.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Exit Card Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="exitcardstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="cardstatus" items="${cardStatusMap}">                                                            
                                                            <option value="${cardstatus.key}" 
                                                                    <c:if test="${caseTypeBean.exitCardStatusCode==cardstatus.key}">selected</c:if>
                                                                    >${cardstatus.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Process</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <table>
                                                        <tr><td><input type="checkbox" name="processautomated" value="AUTOMATE"
                                                                       <c:if test="${caseTypeBean.processAutomatedStatus=='AUTOMATE'}">checked</c:if>
                                                                       />Automated</td></tr>
                                                        <tr><td><input type="checkbox" name="processmanual" value="MANUAL"
                                                                       <c:if test="${caseTypeBean.processManualStatus=='MANUAL'}">checked</c:if>  
                                                                       />Manual</td></tr>                                      
                                                    </table>
                                                </td> 
                                                <td style="width: 200px;">Remarks</td>
                                                <td style="width: 20px;"></td>
                                                <td><textarea name="remarks" rows="2" cols="20"></textarea></td>                                              
                                            </tr>   

                                            <tr></tr>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="submit" value="Add" name="Add" class="defbutton"/>
                                                    <input onclick="window.location='${pageContext.request.contextPath}/LoadCaseMgtServlet'" type="reset" value="Reset" class="defbutton"/></td>                                               
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td ></td>
                                            </tr>

                                        </table>

                                    </form>
                                </c:if>  

                                <c:if test="${operationtype=='update'}" > 
                                    <form name="addcase" method="POST" action="${pageContext.request.contextPath}/UpdateCaseTypetServlet">

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Case Type Code</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input readonly="" type="text" name="casetypecode"  id="casetypecode" maxlength="8" value="${caseTypeBean.caseTypeCode}"/></td>

                                                <td style="width: 200px;">Case Description</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="casetypedes"  maxlength="32" value="${caseTypeBean.description}"/></td>
                                            </tr>                                                                                      
                                            <tr>
                                                <td style="width: 200px;">Currency Type</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="currency"  >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="currency" items="${currencyMap}">                                                            
                                                            <option value="${currency.key}" 
                                                                    <c:if test="${caseTypeBean.currencyTypeCode==currency.key}">selected</c:if>
                                                                    >${currency.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="status" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${statusMap}">                                                            
                                                            <option value="${status.key}" 
                                                                    <c:if test="${caseTypeBean.statusCode==status.key}">selected</c:if>
                                                                    >${status.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Severity Value</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="severityval"  maxlength="3" value="${caseTypeBean.severityValue}" /></td>

                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td></td>
                                            </tr>
                                            <tr>                                                
                                                <td style="width: 200px;">Criteria</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entrycriteria" id="entrycriteria" onchange="enableConditioned(entrycriteria.value)">
                                                        <option value="" selected>---------SELECT----------</option>                                                            
                                                        <option value="OVERDUE" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERDUE'}">selected</c:if>
                                                                >Overdue
                                                        </option>                                                                                                             
                                                        <option value="OVERLIMIT" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERLIMIT'}">selected</c:if>
                                                                >Over Limit
                                                        </option>
                                                        <option value="OVERDUEOROVERLIMIT" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERDUEOROVERLIMIT'}">selected</c:if>
                                                                >Overdue or Over Limit
                                                        </option>                                                                                                             
                                                        <option value="OVERDUEANDOVERLIMIT" 
                                                                <c:if test="${caseTypeBean.entryCriteriaCode=='OVERDUEANDOVERLIMIT'}">selected</c:if>
                                                                >Overdue and Over Limit
                                                        </option> 
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Entry Overdue Period</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entryoverdueperiod" id="entryoverdueperiod">
                                                        <option value="" selected>---------SELECT----------</option>                                                            
                                                        <option value="1" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='1'}">selected</c:if>
                                                                >1 Month
                                                        </option>                                                                                                             
                                                        <option value="2" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='2'}">selected</c:if>
                                                                >2 Month
                                                        </option>
                                                        <option value="3" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='3'}">selected</c:if>
                                                                >3 Month
                                                        </option>                                                                                                             
                                                        <option value="4" 
                                                                <c:if test="${caseTypeBean.entryOverDuePeriodCode=='4'}">selected</c:if>
                                                                >4 Month
                                                        </option> 
                                                    </select>
                                                </td>
                                            </tr>                                           
                                            <tr>
                                                <td style="width: 200px;">Entry Overdue Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><fieldset id="fieldst1" style="border:0 none;">
                                                        <table>
                                                            <tr><td><input type="radio" name="entryoverduetype" id="entryoverduetype" value="FIXED" 
                                                                           <c:if test="${caseTypeBean.overDueAmountType=='FIXED'}">checked</c:if>
                                                                           />Fixed</td></tr>
                                                            <tr><td><input type="radio" name="entryoverduetype" id="entryoverduetype" value="PERCENTA" 
                                                                           <c:if test="${caseTypeBean.overDueAmountType=='PERCENTA'}">checked</c:if>
                                                                           />Percentage</td></tr>
                                                            <tr><td><input type="text" name="entryoverdueamount"  id="entryoverdueamount" maxlength="25" value="${caseTypeBean.entryOverDueAmount}" /> </td></tr>
                                                        </table>
                                                    </fieldset>
                                                </td> 
                                                <td style="width: 200px;">Entry Over Limit Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><fieldset id="fieldst2" style="border:0 none;">
                                                        <table>
                                                            <tr><td><input type="radio" name="entryoverlimittype" id="entryoverlimittype" value="FIXED" 
                                                                           <c:if test="${caseTypeBean.overLimitAmountType=='FIXED'}">checked</c:if> 
                                                                           />Fixed</td></tr>
                                                            <tr><td><input type="radio" name="entryoverlimittype" id="entryoverlimittype" value="PERCENTA" 
                                                                           <c:if test="${caseTypeBean.overLimitAmountType=='PERCENTA'}">checked</c:if>
                                                                           />Percentage</td></tr>
                                                            <tr><td><input type="text" name="entryoverlimitamount"  id="entryoverlimitamount" maxlength="25" value="${caseTypeBean.entryOverLimitAmount}" /> </td></tr>
                                                        </table>
                                                    </fieldset>
                                                </td>                                              
                                            </tr>                                                                                 
                                            <tr>
                                                <td style="width: 200px;">Entry Account Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entryaccstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${statusMap}">                                                            
                                                            <option value="${status.key}" 
                                                                    <c:if test="${caseTypeBean.entryAccStatusCode==status.key}">selected</c:if>
                                                                    >${status.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Entry Card Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="entrycardstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="cardstatus" items="${cardStatusMap}">                                                            
                                                            <option value="${cardstatus.key}" 
                                                                    <c:if test="${caseTypeBean.entryCardStatusCode==cardstatus.key}">selected</c:if>
                                                                    >${cardstatus.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Exit Overdue Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="exitoverdueamount"  id="exitoverdueamount" maxlength="25" value="${caseTypeBean.exitOverDueAmount}" /> </td>

                                                <td style="width: 200px;">Exit Overdue Period</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="exitoverdueperiod" id="exitoverdueperiod">
                                                        <option value="" selected>---------SELECT----------</option>                                                            
                                                        <option value="1" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='1'}">selected</c:if>
                                                                >1 Month
                                                        </option>                                                                                                             
                                                        <option value="2" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='2'}">selected</c:if>
                                                                >2 Month
                                                        </option>
                                                        <option value="3" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='3'}">selected</c:if>
                                                                >3 Month
                                                        </option>                                                                                                             
                                                        <option value="4" 
                                                                <c:if test="${caseTypeBean.exitOverDuePeriodCode=='4'}">selected</c:if>
                                                                >4 Month
                                                        </option> 
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Exit Over Limit Amount</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" name="exitoverlimitamount" id="exitoverlimitamount" maxlength="25" value="${caseTypeBean.exitOverLimitAmount}" /> </td>
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td></td>                                           
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Exit Account Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="exitaccstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="status" items="${statusMap}">                                                            
                                                            <option value="${status.key}" 
                                                                    <c:if test="${caseTypeBean.exitAccStatusCode==status.key}">selected</c:if>
                                                                    >${status.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width: 200px;">Exit Card Status</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td><select  name="exitcardstatus" >
                                                        <option value="" selected>---------SELECT----------</option>
                                                        <c:forEach var="cardstatus" items="${cardStatusMap}">                                                            
                                                            <option value="${cardstatus.key}" 
                                                                    <c:if test="${caseTypeBean.exitCardStatusCode==cardstatus.key}">selected</c:if>
                                                                    >${cardstatus.value}</option> 
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Process</td>
                                                <td style="width: 20px;"><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <table>
                                                        <tr><td><input type="checkbox" name="processautomated" value="AUTOMATE"
                                                                       <c:if test="${caseTypeBean.processAutomatedStatus=='AUTOMATE'}">checked</c:if>
                                                                       />Automated</td></tr>
                                                        <tr><td><input type="checkbox" name="processmanual" value="MANUAL"
                                                                       <c:if test="${caseTypeBean.processManualStatus=='MANUAL'}">checked</c:if>  
                                                                       />Manual</td></tr>                                      
                                                    </table>
                                                </td> 
                                                <td style="width: 200px;">Remarks</td>
                                                <td style="width: 20px;"></td>
                                                <td><textarea name="remarks" rows="2" cols="20"></textarea></td>                                              
                                            </tr>   

                                            <tr></tr>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td style="width: 20px;"></td>
                                                <td><input type="submit" value="Update" name="update" class="defbutton"/>
                                                    <input onclick="window.location='${pageContext.request.contextPath}/LoadUpdateCaseTypeServlet'" type="reset" value="Reset" class="defbutton"/></td>                                               
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td ></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td style="width: 20px;"></td>
                                                <td><input onclick="window.location='${pageContext.request.contextPath}/LoadCaseMgtServlet'"  type="button" value="Cancel" class="defbutton"/></td>                                               
                                                <td style="width: 200px;"></td>
                                                <td style="width: 20px;"></td>
                                                <td ></td>
                                            </tr>
                                            

                                        </table>

                                    </form>
                                </c:if>       
                                <!-- show table data -->
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview">
                                        <thead class="gradeB">
                                            <tr >
                                                <th>Case Type Code</th>
                                                <th>Case Description</th>
                                                <th>Currency</th>                                               
                                                <th>Severity Value</th>
                                                <th>Case Criteria</th>
                                                <th>Status</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${caseList}" var="case">
                                                <tr>
                                                    <td>${case.caseTypeCode}</td>                                                    
                                                    <td>${case.description}</td>                                                   
                                                    <td>${case.currencyTypeDes}</td>                                                                                                  
                                                    <td>${case.severityValue}</td>                                                    
                                                    <td>${case.entryCriteriaDes}</td>
                                                    <td>${case.statusDes}</td>

                                                    <td><a  href='#' onclick="invokeView('${case.caseTypeCode}')">View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateCaseTypeServlet?id=<c:out value="${case.caseTypeCode}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteCase('${case.caseTypeCode}')">Delete</a></td>
                                                </tr>
                                            </c:forEach>
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