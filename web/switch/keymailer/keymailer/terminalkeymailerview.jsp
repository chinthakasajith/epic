<%-- 
    Document   : terminalkeymailerview
    Created on : Oct 22, 2012, 1:26:36 PM
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
            

            function invokeReject()
            {

                window.location="${pageContext.request.contextPath}/LoadTerminalKeyMailerServlet";

            }
            
            function invokeTMKGenerate(){
                
                $("#TMKerror").text("");
                $("#TMKsuccess").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/GenerateTMKKeyServlet",
                    type: "POST",
                    data: $("#ST").serialize(),
                    success : function(data){
                        var ar=data.split(",", 3);
                        //  ar=data;
                        if(ar[0] == 'TMKsuccess'){
                            $("#TMKsuccess").text("TMK Succesfully genarated");
                            $("#tmk").val(ar[1]);
                            $("#tmkKVC").val(ar[2]);
                            $("#TPKGEN").attr("disabled",false);
                            $("#tmk").attr("disabled",false);
                             $("#tmkKVC").attr("disabled",false);
                        }else{
                            $("#TMKerror").text(data);
                        }
                        
                    }
                });
            
            }
            
            function invokeTPKGenerate(){
                
                $("#TPKerror").text("");
                $("#TPKsuccess").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/GenerateTPKKeyServlet",
                    type: "POST",
                    data: $("#TMKForm").serialize(),
                    success : function(data){
                        var ar=data.split(",", 3);
                        //  ar=data;
                        if(ar[0] == 'TPKsuccess'){
                            $("#TPKsuccess").text("TPK Succesfully genarated");
                            $("#tpk").val(ar[1]);
                            $("#tpkKVC").val(ar[2]);
                            $("#tpk").attr("disabled",false);
                             $("#tpkKVC").attr("disabled",false);
                           
                        }else{
                            $("#TPKerror").text(data);
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

                                <table class="tit"> <tr> <td   class="center">  KEY MAILER </td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchTerminal" id="ST">

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
                                                <td style="width: 200px;">Terminal ID(TID)</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.terminalID}</td>
                                                <td> <input type="hidden" value="${keyMailerBean.terminalID}" name="tId" id="tId" /></td>
                                                <td> <input type="hidden" value="${keyMailerBean.merchantID}" name="mId" id="mId" /></td>
                                            </tr> 

                                            <tr>
                                                <td style="width: 200px;">Terminal Name</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.terminalName}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Merchant Name</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.merchantDes}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Serial Number</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.serialNo}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Model</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.modelDes}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Manufacturer</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.manufactDes}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Merchant Category</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.mccDes}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Installation Date</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.installationDate}</td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Activation Date</td>
                                                <td>:</td>
                                                <td>${keyMailerBean.activationDate}</td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </form>


                                <br/><br/><br/>
                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > KEY MAILER</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <div id="TMK" class="outset" style="border-style:outset; background-color: #F0F0F0 ;  border-color: #999; width: 600px;">

                                    <table>
                                        <tr>
                                            <td colspan="3">
                                                <font color="#FF0000"><b><text id="TMKerror"></text></b></font>
                                                <font color="green"><b><text id="TMKsuccess"></text></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <form name="TMKForm" style="padding: 20px;" id="TMKForm">

                                        <table >
                                            <tr>
                                                <td><input type="button" value="Generate" name="generate" style="width: 100px;" id="TMKGEN" onclick="invokeTMKGenerate()"></td>
                                            </tr>
                                        </table>

                                        <br/>

                                        <table>
                                            <tr>
                                                <td>TMK</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="tmk" id="tmk" value="${keyBean.tmk}" disabled="" readonly="readonly" maxlength="32" size="42"></td>
                                                <td style="width: 15px;"></td>
                                                <td>KVC</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="tmkKVC" id="tmkKVC" value="${keyBean.tmkKVC}"  disabled="" readonly="readonly" size="6"></td>
                                            </tr>
                                            <tr style="height: 20px;"></tr>
                                        </table>

                                    </form>
                                </div>


                                <br/><br/><br/>
                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" >WORKING KEY GENERATION (TPK)</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <div id="TPK" class="outset" style="border-style:outset; background-color: #F0F0F0 ;  border-color: #999; width: 600px;">

                                    <table>
                                        <tr>
                                            <td colspan="3">
                                                <font color="#FF0000"><b><text id="TPKerror"></text></b></font>
                                                <font color="green"><b><text id="TPKsuccess"></text></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                    <form name="TPKForm" style="padding: 20px;" id="TPKForm">

                                        <table >
                                            <tr>
                                                <td><input type="button" value="Generate" name="edit" style="width: 100px;" id="TPKGEN" disabled="" onclick="invokeTPKGenerate()"></td>
                                            </tr>
                                        </table>

                                        <br/>

                                        <table>
                                            <tr>
                                                <td>TPK</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="zmk" value="${keyDetails.zmk}" disabled="" readonly="readonly" id="tpk" maxlength="32" size="42"></td>
                                                <td style="width: 15px;"></td>
                                                <td>KVC</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="" value="${keyDetails.zmkKVC}" id="tpkKVC" disabled="" readonly="readonly" size="6"></td>
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
