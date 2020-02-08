<%-- 
    Document   : servermainconfighome
    Created on : Apr 25, 2012, 9:18:40 AM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
          
           
        </script>  
        <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );
                
            
                function invokeEnable(){
                    
                    $(".list").attr("disabled",false);
                    $(".defbutton").attr("disabled",false);
                    $(".nlist").attr("readonly",false);
                                          
                
                }
                
                function invokeReset(){

                    window.location = "${pageContext.request.contextPath}/LoadServermainConfigServlet";

                }
            
                function invokeUpdate()
                {
                    answer = confirm("Are you sure you want to update?")
                    
                    if (answer !=0)
                    {
                        document.viewServerMainConfiguration.action="${pageContext.request.contextPath}/UpdateServermainConfigServlet";
                        document.viewServerMainConfiguration.submit();

                        // window.location="${pageContext.request.contextPath}/UpdateServermainConfigServlet";
                    }
                    else
                    {
                        window.location="${pageContext.request.contextPath}/LoadServermainConfigServlet";
                    }

                   
                }
                
            
            
                

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SERVERMAINCONFIG%>'                                  
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



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">




                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <!--this will shown when the user given value is invalid  -->

                                <c:if test="${operationtype=='add'}" >
                                    <form action="" method="POST" name="viewServerMainConfiguration" >

                                        <table contenteditable="false" border="0" cellspacing="10" cellpadding="0" >
                                            <tbody>
                                                <tr>
                                                    <td>Operating System Type</td>
                                                    <td><input type="text" name="opSysType"  value="${srvrConfigBean.operateSystemType}" maxlength="32" class="nlist" readonly=""/></td>
                                                </tr>
                                                <!--                                                
                                                                                                <tr>
                                                                                                    <td>Running Mode</td>
                                                                                                    <td>
                                                                                                        <select  name="runMode"  maxlength="1" class="list" disabled="false" style="color: #000000; width: 163px">
                                                                                                            <option value="">--SELECT--</option>
                                                <c:if test="${srvrConfigBean.runningMode == '1'}">                                                                
                                                    <option value="1" selected="">Permanent</option>
                                                    <option value="2" >Temporary</option>
                                                    <option value="3" >Both</option>

                                                </c:if>
                                                <c:if test="${srvrConfigBean.runningMode == '2'}">
                                                    <option value="1" >Permanent</option>
                                                    <option value="2" selected="">Temporary</option>
                                                    <option value="3" >Both</option>

                                                </c:if>
                                                <c:if test="${srvrConfigBean.runningMode == '3'}">
                                                    <option value="1" >Permanent</option>
                                                    <option value="2" >Temporary</option>
                                                    <option value="3" selected="">Both</option>

                                                </c:if>

                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Permanent Running Port </td>
                                        <td><input type="text" name="runPort" value="${srvrConfigBean.permanentRunningPort}" maxlength="5" class="nlist" readonly=""/></td>
                                    </tr>
                                    <tr>
                                        <td>Terminated Running Port</td>
                                        <td><input type="text" name="terminateRunPort" value="${srvrConfigBean.terminatedRunningPort}" maxlength="5" class="nlist" readonly=""/></td>
                                    </tr>
                                                -->
                                                <!--                                                <tr>
                                                                                                    <td>Number of Connections</td>
                                                                                                    <td><input type="text" name="noOfCons" value="${srvrConfigBean.noOfConnections}" maxlength="2" class="nlist" readonly=""/></td>
                                                                                                </tr>-->
                                                <tr>
                                                    <td>Initial Vector</td>
                                                    <td><input type="text" name="iniVector" value="${srvrConfigBean.initialVector}" maxlength="2" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Request Buffer Size</td>
                                                    <td><input type="text" name="reqBufSize" value="${srvrConfigBean.reqBufSize}" maxlength="3" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Response Buffer Size</td>
                                                    <td><input type="text" name="resBufSize" value="${srvrConfigBean.resBufSize}" maxlength="3" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Alert Status</td>
                                                    <td>
                                                        <select  name="alertStatus"   maxlength="2" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="alert" items="${enable}">
                                                                <c:if test="${srvrConfigBean.alertStatus==alert.key}">
                                                                    <option value="${alert.key}" selected>${alert.value}</option>
                                                                </c:if>
                                                                <c:if test="${srvrConfigBean.alertStatus != alert.key}">
                                                                    <option value="${alert.key}" >${alert.value}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Fail Status</td>
                                                    <td>
                                                        <select  name="failStatus" maxlength="2" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="fail" items="${enable}">
                                                                <c:if test="${srvrConfigBean.failStatus==fail.key}">
                                                                    <option value="${fail.key}" selected>${fail.value}</option>
                                                                </c:if>
                                                                <c:if test="${srvrConfigBean.failStatus != fail.key}">
                                                                    <option value="${fail.key}" >${fail.value}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr> 
                                                <tr>
                                                    <td>HSM Type</td>
                                                    <td><select name="hsmType" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:if test="${srvrConfigBean.hsmType == '1'}">                                                                
                                                                <option value="1" selected="">Safenet</option>
                                                                <option value="2" >Thales</option>                                                                
                                                            </c:if>
                                                            <c:if test="${srvrConfigBean.hsmType == '2'}">
                                                                <option value="1" >Safenet</option>
                                                                <option value="2" selected="">Thales</option>                                                                
                                                            </c:if>                                                            
                                                        </select></td>
                                                </tr>
                                                <!--                                                <tr>
                                                                                                    <td>Temporary Connection Time Out</td>
                                                                                                    <td><input type="text" name="tempConTimeOut" value="${srvrConfigBean.tempConnectTimeOut}" maxlength="3" class="nlist" readonly=""/></td>
                                                                                                </tr>-->
                                                <tr>
                                                    <td>Maximum Pool Size</td>
                                                    <td><input type="text" name="maxPoolSize" value="${srvrConfigBean.maxPoolSize}" maxlength="3" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Pool Size</td>
                                                    <td><input type="text" name="minPoolSize" value="${srvrConfigBean.minPoolSize}" maxlength="3" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Queuing Size</td>
                                                    <td><input type="text" name="maxQueueSize" value="${srvrConfigBean.maxQueueSize}" maxlength="3" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Pool Back Log</td>
                                                    <td><input type="text" name="poolBackLog" value="${srvrConfigBean.poolBackLog}" maxlength="2" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>PVI</td>
                                                    <td><input type="text" name="pvi" value="${srvrConfigBean.pvi}" maxlength="1" class="nlist" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>PIN block formats</td>
                                                    <td>
                                                        <select  name="pinb" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="pinb" items="${pinbFormat}">
                                                                <c:if test="${pinb.key==srvrConfigBean.pinbFormat}">
                                                                    <option value="${pinb.key}" selected="">${pinb.value}</option>
                                                                </c:if>
                                                                <c:if test="${pinb.key!=srvrConfigBean.pinbFormat}">
                                                                    <option value="${pinb.key}">${pinb.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <!--                                                <tr>
                                                                                                    <td>EMV Verify Method</td>
                                                                                                    <td>
                                                                                                        <select  name="emv" class="list" disabled="" style="color: #000000; width: 163px">
                                                                                                            <option value="">--SELECT--</option>
                                                <c:forEach var="emv" items="${emvVerifyMethod}">
                                                    <c:if test="${emv.key==srvrConfigBean.emvVerifyMethod}">
                                                        <option value="${emv.key}" selected="">${emv.value}</option>
                                                    </c:if>
                                                    <c:if test="${emv.key!=srvrConfigBean.emvVerifyMethod}">
                                                        <option value="${emv.key}">${emv.value}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>-->
                                                <tr>
                                                    <td>Encryption mode</td>
                                                    <td>
                                                        <select  name="encr" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="encr" items="${encrMode}">
                                                                <c:if test="${encr.key==srvrConfigBean.enrcMode}">
                                                                    <option value="${encr.key}" selected="">${encr.value}</option>
                                                                </c:if>
                                                                <c:if test="${encr.key!=srvrConfigBean.enrcMode}">
                                                                    <option value="${encr.key}">${encr.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Default Channel Id</td>                                                
                                                    <td>
                                                        <select name="Channel" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="channel" items="${sessionScope.SessionObject.channelTypeList}">
                                                                <c:if test="${srvrConfigBean.channelId==channel.code}">
                                                                    <option value="${channel.code}" selected="true">${channel.description}</option>
                                                                </c:if>
                                                                <c:if test="${srvrConfigBean.channelId!=channel.code}">
                                                                    <option value="${channel.code}" >${channel.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status Of Default Channel</td>
                                                    <td>
                                                        <select  name="chanelStatus"   maxlength="2" class="list" disabled="" style="color: #000000; width: 163px">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="status" items="${enable}">
                                                                <c:if test="${srvrConfigBean.channelStatus==status.key}">
                                                                    <option value="${status.key}" selected>${status.value}</option>
                                                                </c:if>
                                                                <c:if test="${srvrConfigBean.channelStatus != status.key}">
                                                                    <option value="${status.key}" >${status.value}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>                                                
                                                    <td>
                                                        <input type="submit" value="Update" name="update" disabled="" class="defbutton" style=" width:100px" onclick="invokeUpdate() "/>
                                                        &nbsp;
                                                        <input type="button" value="Enable" name="enable" style=" width:100px" class="defbutton" onclick="invokeEnable()"/>
                                                    </td>
                                                    <td>
                                                        <input type="button" value="Reset" name="reset" style=" width:100px" class="defbutton" onclick="invokeReset()"/>

                                                    </td>
                                                    <td>

                                                    </td>

                                                </tr>


                                            </tbody>
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
