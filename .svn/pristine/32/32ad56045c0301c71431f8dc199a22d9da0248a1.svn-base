<%-- 
    Document   : communicationkeyupdate
    Created on : Jan 16, 2013, 11:58:18 AM
    Author     : asela
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <title>EPIC_CMS_HOME</title>
        <script  type="text/javascript" charset="utf-8">
          
       
          
            function enableZMK()
            {
                $("#zmkEnable").attr("disabled",false);
                $("#zmkUpdate").attr("disabled",false);

            }
            
            function enableAWK()
            {

                $("#awkEnable").attr("disabled",false);
                $("#awkUpdate").attr("disabled",false);

            }
            
            function enableIWK()
            {

                $("#iwkEnable").attr("disabled",false);
                $("#iwkUpdate").attr("disabled",false);

            }
            
            
            function invokeUpdate(){
                $("#error").text("");
                $("#success").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateZMKKeyServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#xmkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#success").text("KVC of the ZMK Succesfully genarated");
                            $("#zmkkvc").val(ar[1]);
                        }else{
                            $("#error").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateAWK(){
                $("#errorawk").text("");
                $("#successawk").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAWKKeyServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#awkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successawk").text("KVC of the AWK Succesfully genarated");
                            $("#awkkvc").val(ar[1]);
                        }else{
                            $("#errorawk").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateIWK(){
                $("#erroriwk").text("");
                $("#successiwk").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateIWKKeyServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#iwkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successiwk").text("KVC of the IWK Succesfully genarated");
                            $("#iwkkvc").val(ar[1]);
                        }else{
                            $("#erroriwk").text(data);
                        }
                        
                    }
                });
              
            }
           
            function invokeGoBack()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadCommunicationKeyServlet";
                
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
                { pagecode:'<%= PageVarList.COMMUNICATION_KEYS%>'                                  
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

                <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

            </div>


            <div id="content1">


                <div id="content" align="center">
                    <div id="contenttext">
                        <div class="bodytext" style="padding:12px;" align="left">




                            <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                            <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->

                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ZMK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                            <div id="zmk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">





                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><text id="error"></text></b></font>
                                            <font color="green"><b><text id="success"></text></b></font>
                                        </td>
                                    </tr>
                                </table>




                                <form name="zmkPanelForm" style="padding: 20px;" id="xmkID">

                                    <table>
                                        <tr>
                                            <td>ZMK</td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="text" name="zmk" value="${keybean.zmk}" disabled="" id="zmkEnable" maxlength="32" size="42"></td>
                                            <td style="width: 15px;"></td>
                                            <td>KVC</td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="text" name="" value="${keybean.zmkkvc}" id="zmkkvc" disabled="" size="6"></td>
                                        </tr>
                                        <tr style="height: 20px;"></tr>


                                    </table>

                                    <table>
                                        <tr>

                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableZMK()"></td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="zmkUpdate" disabled="" onclick="invokeUpdate()"></td>
                                        </tr>
                                    </table>
                                </form>


                            </div>

                            <br/></br>

                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > AWK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                            <div id="awk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                <form name="zmkPanelForm" style="padding: 20px;" id="awkID">
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><text id="errorawk"></text></b></font>
                                                <font color="green"><b><text id="successawk"></text></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <table>
                                        <tr>
                                            <td>AWK</td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="text" name="awk" value="${keybean.awk}" disabled="" id="awkEnable" maxlength="32" size="42"></td>
                                            <td style="width: 15px;"></td>
                                            <td>KVC</td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="text" name="" value="${keybean.awkkvc}" id="awkkvc" disabled="" size="6"></td>
                                        </tr>
                                        <tr style="height: 20px;"></tr>


                                    </table>

                                    <table>
                                        <tr>

                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableAWK()"></td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="awkUpdate" disabled="" onclick="invokeUpdateAWK()"></td>
                                        </tr>
                                    </table>
                                </form>


                            </div>



                            <br/></br>

                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IWK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                            <div id="IWK" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">

                                <form name="zmkPanelForm" style="padding: 20px;" id="iwkID">
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><text id="erroriwk"></text></b></font>
                                                <font color="green"><b><text id="successiwk"></text></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <table>
                                        <tr>
                                            <td>IWK</td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="text" name="iwk" value="${keybean.iwk}" disabled="" id="iwkEnable" maxlength="32" size="42"></td>
                                            <td style="width: 15px;"></td>
                                            <td>KVC</td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="text" name="" value="${keybean.iwkkvc}" id="iwkkvc" disabled="" size="6"></td>
                                        </tr>
                                        <tr style="height: 20px;"></tr>


                                    </table>

                                    <table>
                                        <tr>

                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableIWK()"></td>
                                            <td style="width: 15px;"></td>
                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="iwkUpdate" disabled="" onclick="invokeUpdateIWK()"></td>
                                        </tr>
                                    </table>
                                </form>


                            </div>

                        </div>
                        <!-- end of tab 1 -->
                        <table>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td><input type="button" name="back" value="Back" onclick="invokeGoBack()" style="width: 100px"/></td>
                            </tr>
                        </table>
                        <!--   ------------------------- end developer area  --------------------------------                      -->

                    </div>

                </div>
            </div>

        </div>
        <div class="clearer"><span></span></div>
    </div>

    <div class="footer"><jsp:include page="/footer.jsp"/></div>
</body>
</html>

