<%-- 
    Document   : channelconfighome
    Created on : Apr 24, 2012, 2:41:47 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
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

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>

        <title>EPIC_CMS_HOME</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addChannelForm.action="${pageContext.request.contextPath}/AddChanelConfigurationServlet";
                document.addChannelForm.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadChanelConfigurationServlet";

            }
            
            function invokeUpdate(channelId)
            {
                window.location="${pageContext.request.contextPath}/UpdateChanelConfigurationServlet?channelId="+channelId;
               

            }
            function invokeUpdateReset(channelId)
            {
                window.location="${pageContext.request.contextPath}/UpdateChanelConfigurationServlet?channelId="+channelId;
               

            }
            
            function invokeUpdateConfiremd()
            {

                document.updateChannelForm.action="${pageContext.request.contextPath}/UpdateConfiremedChanelConfigurationServlet";
                document.updateChannelForm.submit();

            }
            
            function invokeView(channelId)
            {

                window.location="${pageContext.request.contextPath}/ViewChanelConfigurationServlet?channelId="+channelId;
                

            }
            
            function ConfirmDelete(channelId)
            {
                answer = confirm("Are you sure you want to delete this channel?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteChanelConfigurationServlet?channelId="+channelId;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadChanelConfigurationServlet";
                }
                
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
                { pagecode:'<%= PageVarList.CHANNEL%>'                                  
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
            <c:redirect url="/controlpanel/login/login.jsp"/>
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




                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                    <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                    <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Add(also default) view  ///////////////////////////////////////////////////////////-->


                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>


                                <c:if test="${operationtype=='ADD'}">
                                    <form action="" method="POST" name="addChannelForm" >

                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Channel ID</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="channelId" class="inputfield-mandatory" maxlength="2" value='${channelBean.channelId}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Channel Name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="channelName" class="inputfield-Description-mandatory" maxlength="20" value='${channelBean.channelName}'></td>
                                                </tr>

                                                 <tr>
                                                    <td>Communication Key Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyId" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="key" items="${sessionScope.SessionObject.keyId}">

                                                                <c:if test="${channelBean.keyId==key.key}">
                                                                    <option value="${key.key}" selected="true" >${key.value}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.keyId!=key.key}">
                                                                    <option value="${key.key}">${key.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>IP</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="ip" class="inputfield-Description-mandatory" maxlength="15" value='${channelBean.ip}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Port</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="port" class="inputfield-mandatory" maxlength="8" value='${channelBean.port}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Time Out (sec)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="timeOut" class="inputfield-mandatory" maxlength="6" value='${channelBean.timeOut}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Connection Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectConnectionType"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="connectionType" items="${sessionScope.SessionObject.connectionTypeList}">
                                                                <c:if test="${channelBean.connectionType==connectionType.code}">
                                                                    <option value="${connectionType.code}" selected="true" >${connectionType.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.connectionType!=connectionType.code}">
                                                                    <option value="${connectionType.code}">${connectionType.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Channel Type</td>
                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;<select  name="selectChannelType"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="chanelType" items="${sessionScope.SessionObject.channelTypeList}">
                                                                <c:if test="${channelBean.channelType==chanelType.code}">
                                                                    <option value="${chanelType.code}" selected="true" >${chanelType.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.channelType!=chanelType.code}">
                                                                    <option value="${chanelType.code}" >${chanelType.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Echo Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="selectEchoStatus"  class="inputfield-mandatory" maxlength="2"> 
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusEDTypeList" items="${statusEDList}">

                                                                <c:if test="${channelBean.echoStatus==statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" selected="true" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.echoStatus!=statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                </tr>    


                                                <tr>
                                                    <td>Signon Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectSingonStatus"  class="inputfield-mandatory" maxlength="2">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusEDTypeList" items="${statusEDList}">

                                                                <c:if test="${channelBean.signonStatus==statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" selected="true" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.signonStatus!=statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" >${statusEDTypeList.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>    

                                                <tr>
                                                    <td>Dynamic Key Exchange Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectDynamicKeyExchangeStatus"  maxlength="2" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusEDTypeList" items="${statusEDList}">
                                                                <c:if test="${channelBean.dynamicKeyExchangeStatus==statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" selected="true" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.dynamicKeyExchangeStatus!=statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Dynamic Key Exchange Period</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="dynamicKeyExchangePeriod" class="inputfield-mandatory" maxlength="4" value='${channelBean.dynamicKeyExchangePeriod}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Echo Period</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="echoPeriod" class="inputfield-mandatory" maxlength="4" value='${channelBean.echoPeriod}'></td>
                                                </tr>
                                                
                                                 <tr>
                                                    <td>Destination Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="hdesId" class="inputfield-mandatory" maxlength="6" value='${channelBean.hdesId}'></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Source Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="hsrcId" class="inputfield-mandatory" maxlength="6" value='${channelBean.hsrcId}'></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Acquire Institution Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="ascii32Id" class="inputfield-mandatory" maxlength="11" value='${channelBean.ascii32Id}'></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Forward Institution Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="fiic33Id" class="inputfield-mandatory" maxlength="11" value='${channelBean.fiic33Id}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Echo Direction</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="echoDirection"  maxlength="2" >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${channelBean.echoDirection=='1'}">
                                                                <option value="1" selected="true" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.echoDirection!='1'}">
                                                                <option value="1" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.echoDirection=='2'}">
                                                                <option value="2" selected="true" >Outgoing</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.echoDirection!='2'}">
                                                                <option value="2" >Outgoing</option>
                                                            </c:if>

                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Key Exchange Direction</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="keyExchangeDirection"  maxlength="2" >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${channelBean.keyExchangeDirection=='1'}">
                                                                <option value="1" selected="true" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.keyExchangeDirection!='1'}">
                                                                <option value="1" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.keyExchangeDirection=='2'}">
                                                                <option value="2" selected="true" >Outgoing</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.keyExchangeDirection!='2'}">
                                                                <option value="2" >Outgoing</option>
                                                            </c:if>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectStatusCode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusType" items="${sessionScope.SessionObject.statusTypeList}">
                                                                <c:if test="${channelBean.status==statusType.code}">
                                                                    <option value="${statusType.code}" selected="true" >${statusType.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.status!=statusType.code}">
                                                                    <option value="${statusType.code}">${statusType.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                    <td></td>
                                                </tr>


                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="add" value="Add" onclick="invokeAdd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset()"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->

                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewChannelForm">



                                        <table border="0" >
                                            <tbody>
                                                <tr>
                                                    <td>Channel Id</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.channelId}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Channel Name</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.channelName}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>IP</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.ip}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Port</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.port}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Time Out (sec)</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.timeOut}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Connection Type</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.connectionTypeDescription}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Channel Type</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.channelTypeDescription}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Echo Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.echoStatus}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Singon Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.signonStatus}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Dynamic Key Exchange Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.dynamicKeyExchangeStatus}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Dynamic Key Exchange Period</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.dynamicKeyExchangePeriod}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Echo Period</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.echoPeriod}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Echo Direction</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td><c:if test="${channelBean.echoDirection=='1'}">Incoming</c:if>
                                                        <c:if test="${channelBean.echoDirection=='2'}">Outgoing</c:if>
                                                    </td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Key Exchange Direction</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td><c:if test="${channelBean.keyExchangeDirection=='1'}">Incoming</c:if>
                                                        <c:if test="${channelBean.keyExchangeDirection=='2'}">Outgoing</c:if>
                                                    </td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${channelBean.statusDescription}</td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td>  <input type="button" style="width: 100px" name="reset" value="Back" onclick="invokeReset()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        


                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateChannelForm">


                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>

                                                <tr>
                                                    <td>Channel ID</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input readonly="readonly" type="text" name="channelId" class="inputfield-mandatory" maxlength="2" value='${channelBean.channelId}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Channel Name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="channelName" class="inputfield-Description-mandatory" maxlength="20" value='${channelBean.channelName}'></td>
                                                </tr>
                                                
                                                 <tr>
                                                    <td>Communication Key Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyId" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="key" items="${sessionScope.SessionObject.keyId}">

                                                                <c:if test="${channelBean.keyId==key.key}">
                                                                    <option value="${key.key}" selected="true" >${key.value}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.keyId!=key.key}">
                                                                    <option value="${key.key}">${key.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>IP</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="ip" class="inputfield-Description-mandatory" maxlength="21" value='${channelBean.ip}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Port</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="port" class="inputfield-mandatory" maxlength="5" value='${channelBean.port}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Time Out (sec)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="timeOut" class="inputfield-mandatory" maxlength="6" value='${channelBean.timeOut}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Connection Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectConnectionType"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="connectionType" items="${sessionScope.SessionObject.connectionTypeList}">

                                                                <c:if test="${channelBean.connectionType==connectionType.code}">
                                                                    <option value="${connectionType.code}" selected="true" >${connectionType.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.connectionType!=connectionType.code}">
                                                                    <option value="${connectionType.code}">${connectionType.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Channel Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectChannelType"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="chanelType" items="${sessionScope.SessionObject.channelTypeList}">

                                                                <c:if test="${channelBean.channelType==chanelType.code}">
                                                                    <option value="${chanelType.code}" selected="true" >${chanelType.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.channelType!=chanelType.code}">
                                                                    <option value="${chanelType.code}" >${chanelType.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Echo Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectEchoStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusEDTypeList" items="${statusEDList}">

                                                                <c:if test="${channelBean.echoStatus==statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" selected="true" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.echoStatus!=statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" >${statusEDTypeList.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Signon Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectSignonStatus"   >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusEDTypeList" items="${statusEDList}">

                                                                <c:if test="${channelBean.signonStatus==statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" selected="true" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.signonStatus!=statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" >${statusEDTypeList.description}</option>
                                                                </c:if>

                                                            </c:forEach>

                                                        </select></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Dynamic Key Exchange Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select name="selectDynamicKeyExchangeStatus"  class="inputfield-mandatory" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusEDTypeList" items="${statusEDList}">

                                                                <c:if test="${channelBean.dynamicKeyExchangeStatus==statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" selected="true" >${statusEDTypeList.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.dynamicKeyExchangeStatus!=statusEDTypeList.code}">
                                                                    <option value="${statusEDTypeList.code}" >${statusEDTypeList.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Dynamic Key Exchange Period</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="dynamicKeyExchangePeriod" class="inputfield-mandatory" maxlength="4" value='${channelBean.dynamicKeyExchangePeriod}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Echo Period</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="echoPeriod" class="inputfield-mandatory" maxlength="4" value='${channelBean.echoPeriod}'></td>
                                                </tr>
                                                
                                                 <tr>
                                                    <td>Destination Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="hdesId" class="inputfield-mandatory" maxlength="6" value='${channelBean.hdesId}'></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Source Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="hsrcId" class="inputfield-mandatory" maxlength="6" value='${channelBean.hsrcId}'></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Acquire Institution Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="ascii32Id" class="inputfield-mandatory" maxlength="11" value='${channelBean.ascii32Id}'></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Forward Institution Id</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="fiic33Id" class="inputfield-mandatory" maxlength="11" value='${channelBean.fiic33Id}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Echo Direction</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="echoDirection"  maxlength="2" >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${channelBean.echoDirection=='1'}">
                                                                <option value="1" selected="true" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.echoDirection!='1'}">
                                                                <option value="1" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.echoDirection=='2'}">
                                                                <option value="2" selected="true" >Outgoing</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.echoDirection!='2'}">
                                                                <option value="2" >Outgoing</option>
                                                            </c:if>

                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Key Exchange Direction</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyExchangeDirection"  maxlength="2" >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${channelBean.keyExchangeDirection=='1'}">
                                                                <option value="1" selected="true" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.keyExchangeDirection!='1'}">
                                                                <option value="1" >Incoming</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.keyExchangeDirection=='2'}">
                                                                <option value="2" selected="true" >Outgoing</option>
                                                            </c:if>
                                                            <c:if test="${channelBean.keyExchangeDirection!='2'}">
                                                                <option value="2" >Outgoing</option>
                                                            </c:if>
                                                        </select></td>

                                                </tr>


                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="selectStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="statusType" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${channelBean.status==statusType.code}">
                                                                    <option value="${statusType.code}" selected="true" >${statusType.description}</option>
                                                                </c:if>
                                                                <c:if test="${channelBean.status!=statusType.code}">
                                                                    <option value="${statusType.code}">${statusType.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>
                                                        <input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${channelBean.channelId}')"/>
                                                    </td>


                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Channel ID</th>
                                            <th>Channel Name</th>
                                            <th>IP</th>
                                            <th>Port</th>
                                            <th>Connection Type</th>
                                            <th>Channel Type</th>
                                            <th>Status</th>



                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="channel" items="${channelList}">
                                            <tr>

                                                <td >${channel.channelId}</td>
                                                <td >${channel.channelName}</td>
                                                <td >${channel.ip}</td>
                                                <td >${channel.port}</td>
                                                <td >${channel.connectionTypeDescription}</td>
                                                <td >${channel.channelTypeDescription}</td>
                                                <td >${channel.statusDescription}</td>
                                              <!--  <td >${channel.lastUpdatedTime}</td> -->



                                                <td  ><a href='#' onclick="invokeView('${channel.channelId}')">View</a></td>

                                                <td  ><a href='#' onclick="invokeUpdate('${channel.channelId}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${channel.channelId}')">Delete</a></td>

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

