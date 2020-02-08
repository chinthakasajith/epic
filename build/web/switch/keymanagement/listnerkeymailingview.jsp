<%-- 
    Document   : listnerkeymailingview
    Created on : Feb 15, 2013, 5:01:00 PM
    Author     : jeevan
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
            
            function invokeZMKGenerate(){
                $("#ZMKerror").text("");
                $("#ZMKsuccess").text("");
                $.ajax({ 
                    url: "${pageContext.request.contextPath}/GenerateZMKServlet?comkeyid=${searchedKeyMail.comKeyId}",
                    type: "POST",
                    data: $("#ZMKForm").serialize(),
                    success : function(data){
                        var ar=data.split(",", 3);
                        //  ar=data;
                        if(ar[0] == 'ZMKSuccess'){
                            $("#ZMKsuccess").text("ZMK Succesfully genarated");
                            $("#zmk").val(ar[1]);
                            $("#zmkKVC").val(ar[2]);
                            $("#TMKGEN").attr("disabled",false);
                            $("#zmk").attr("disabled",false);
                            $("#zmkKVC").attr("disabled",false);
                        }else{
                            $("#TMKerror").text(data);
                        }
                        
                    }
                });
            
            }
            

        </script>

        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LISTNER_KEY_MAILING%>'                                  
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

                                <form method="POST" action="" name="searchformView" id="ST">

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
                                                <td style="width: 130px;">Listener Id</td>
                                                <td>:${searchedKeyMail.listenerId}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 130px;">Listener Name</td>
                                                <td>:${searchedKeyMail.listnerTypeDesc}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 130px;">Listener Type</td>
                                                <td>:${searchedKeyMail.listnerTypeCode}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 130px;">Communication Key</td>
                                                <td>:${searchedKeyMail.comKeyDesc}</td>
                                                <td><input type="hidden" value="${searchedKeyMail.comKeyId}" name="comkeyid" id="comkeyid"></td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>         

                                </form>

                                <br/><br/><br/>
                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > LISTENER KEY MAILING </td> </tr><tr> <td>&nbsp;</td> </tr>
                                </table>

                                <div id="TMK" class="outset" style="border-style:outset; background-color: #F0F0F0 ;  border-color: #999; width: 600px;">

                                    <table>
                                        <tr>
                                            <td colspan="3">
                                                <font color="#FF0000"><b><text id="ZMKerror"></text></b></font>
                                                <font color="green"><b><text id="ZMKsuccess"></text></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <form name="ZMKForm" style="padding: 20px;" id="ZMKForm">

                                        <table >
                                            <tr>
                                                <td><input type="hidden" value="${searchedKeyMail.comKeyId}" name="comkeyid" id="comkeyid"></td>
                                                <td><input type="button" value="Generate" name="generate" style="width: 100px;" id="TMKGEN" onclick="invokeZMKGenerate()"></td>
                                            </tr>
                                        </table>

                                        <br/>

                                        <table>
                                            <tr>
                                                <td>ZMK</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="zmk" id="zmk" value="${searchedKeyMail.zmk}" disabled="" readonly="readonly" maxlength="32" size="42"></td>
                                                <td style="width: 15px;"></td>
                                                <td>KVC</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="zmkKVC" id="zmkKVC" value="${searchedKeyMail.zmk_kvc}"  disabled="" readonly="readonly" size="6"></td>
                                                <td>ComId</td>
                                                <td style="width: 15px;"></td>
                                                <td><input type="text" name="TT" id="TT" value="${searchedKeyMail.comKeyId}"  disabled="" readonly="readonly" size="6"></td>                                            
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
