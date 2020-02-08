<%-- 
    Document   : viewchannelkeymailing
    Created on : Feb 18, 2013, 3:41:20 PM
    Author     : asitha_l
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
            
            function invokeZMKGenerate(){
                
                $("#ZMKerror").text("");
                $("#ZMKsuccess").text("");            
                $.ajax({
                    url: "${pageContext.request.contextPath}/GenerateZMKKeyServlet",
                    type: "POST",
                    data: $("#CKM").serialize(),
                    success : function(data){
                        var ar=data.split(",", 3);
                        //  ar=data;
                        if(ar[0] == 'ZMKsuccess'){
                            $("#ZMKsuccess").text("ZMK Succesfully genarated");
                            $("#zmk").val(ar[1]);
                            $("#zmkKVC").val(ar[2]);
                            $("#zmk").attr("disabled",false);
                             $("#zmkKVC").attr("disabled",false);
                        }else{
                            $("#ZMKerror").text(data);
                        }
                        
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

                                <table class="tit"> <tr> <td   class="center"> CHANNEL KEY MAILER </td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                <%-- -------------------------add form start -------------------------------- --%>
                                <form method="POST" action="" name="viewChannelKeyMailing" id="CKM">

                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                                <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                            </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>

                                            <tr>
                                                <td style="width: 200px;"><b>Channel ID</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.channelID}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>ECHO Time Period</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.ECHOimePeriod}</td>  
                                            </tr> 

                                            <tr>
                                                <td style="width: 200px;"><b>Channel Name</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.channelName}</td>
                                                
                                                <td style="width: 100px;"><input type="hidden" value="${channelKeyMailingBean.channelName}" name="cName" id="cName" /></td>
                                                
                                                <td style="width: 200px;"><b>Status</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.statusDesc}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>Channel Type</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.channelTypeDesc}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>DEK Time Period</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.DEKtimePeriod}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>Communication Key ID</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.communicationKeyDesc}</td>
                                                
                                                <td style="width: 100px;"><input type="hidden" value="${channelKeyMailingBean.communicationKeyCode}" name="cId" id="cId" /></td>
                                                                                               
                                                <td style="width: 200px;"><b>Interface Status</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.interfaceStatus}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>IP</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.ip}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>ECHO Direction</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.ECHOdirection}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>PORT</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.port}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>Key Exchange Direction</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.keyExchangeDirection}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>Time Out</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.timeout}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>FIIC33</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.FIIC33}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>Connection Type</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.connectionTypeDesc}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>HDESID</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.HDESID}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><b>ECHO Status</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.ECHOstatusDesc}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>HSRCID</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.HSRCID}</td>
                                            </tr>
                                            
                                            <tr>
                                                <td style="width: 200px;"><b>SIGNON Status</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.SIGNONstatusDesc}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"><b>AIIC32</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.AIIC32}</td>
                                            </tr>
                                            
                                            <tr>
                                                <td style="width: 200px;"><b>DKE Status</b></td>
                                                <td>:</td>
                                                <td>${channelKeyMailingBean.DKEstatusDesc}</td>
                                                
                                                <td style="width: 100px;"></td>
                                                
                                                <td style="width: 200px;"></td>
                                                <td></td>
                                                <td></td>
                                            </tr>                                                                                      

                                        </tbody>
                                    </table>
                                </form>
                                            
                               <br/><br/><br/>
                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CHANNEL KEY MAILER </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <div id="ZMK" class="outset" style="border-style:outset; background-color: #F0F0F0 ;  border-color: #999; width: 600px;">

                                    <table>
                                        <tr>
                                            <td colspan="3">
                                                <font color="#FF0000"><b><text id="ZMKerror"></text></b></font>
                                                <font color="green"><b><text id="ZMKsuccess"></text></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <form name="ZMKForm" style="padding: 20px;" id="ZMKForm">

                                        <table>
                                            <tr>
                                                <td><input type="button" value="Generate" name="generate" style="width: 100px;" id="ZMKGEN" onclick="invokeZMKGenerate()"></td>
                                            </tr>
                                        </table>

                                        <br/>

                                        <table>
                                            <tr>
                                                <td>ZMK</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="zmk" id="zmk" value="${keyBean.zmk}" disabled="" readonly="readonly" maxlength="32" size="42"></td>
                                                <td style="width: 15px;"></td>
                                                <td>KVC</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="zmkKVC" id="zmkKVC" value="${keyBean.zmkkvc}"  disabled="" readonly="readonly" size="6"></td>
                                            </tr>
                                            <tr style="height: 20px;"></tr>
                                        </table>

                                    </form>
                                </div>             
                            <!--  ------------------------- end developer area  --------------------------------                      -->
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
