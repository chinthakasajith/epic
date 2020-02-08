<%-- 
    Document   : switchkeymanagement
    Created on : Apr 25, 2012, 9:51:33 PM
    Author     : mahesh_m
--%>

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
          
            function enableCVVA()
            {
                $("#cvvaEnable").attr("disabled",false);
                $("#cvvaUpdate").attr("disabled",false);

            }
            
            function enableCVVB()
            {
                $("#cvvbEnable").attr("disabled",false);
                $("#cvvbUpdate").attr("disabled",false);

            }
            
              function enablePVVA()
            {
                $("#pvvaEnable").attr("disabled",false);
                $("#pvvaUpdate").attr("disabled",false);

            }
            
               function enablePVVB()
            {
                $("#pvvbEnable").attr("disabled",false);
                $("#pvvbUpdate").attr("disabled",false);

            }
            
               function enableTPK()
            {
                $("#tpkEnable").attr("disabled",false);
                $("#tpkUpdate").attr("disabled",false);

            }
            
            
            function invokeUpdateCVVA(){
                
                $("#errorcvva").text("");
                $("#successcvva").text("");
               
                $.ajax({
                    
                    url: "${pageContext.request.contextPath}/UpdateCVVAServlet",
                    type: "POST",
                    data: $("#cvvaID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvva").text("KVC of the CVVA Succesfully genarated");
                            $("#cvvakvc").val(ar[1]);
                        }else{
                            $("#errorcvva").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateCVVB(){
                $("#errorcvvb").text("");
                $("#successcvvb").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCVVBPanelServlet",
                    type: "POST",
                    data: $("#cvvbID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvvb").text("KVC of the CVVB Succesfully genarated");
                            $("#cvvbkvc").val(ar[1]);
                        }else{
                            $("#errorcvvb").text(data);
                        }
                        
                    }
                });
              
            }
            
              function invokeUpdatePVVA(){
                $("#errorpvva").text("");
                $("#successpvva").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdatePVVAPanelServlet",
                    type: "POST",
                    data: $("#pvvaID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successpvva").text("KVC of the PVVA Succesfully genarated");
                            $("#pvvakvc").val(ar[1]);
                        }else{
                            $("#errorpvva").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdatePVVB(){
                $("#errorpvvb").text("");
                $("#successpvvb").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdatePVVBPanelServlet",
                    type: "POST",
                    data: $("#pvvbID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successpvvb").text("KVC of the PVVB Succesfully genarated");
                            $("#pvvbkvc").val(ar[1]);
                        }else{
                            $("#errorpvvb").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            
              function invokeUpdateTPK(){
                $("#errortpk").text("");
                $("#successtpk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateSWTPKPanelServlet",
                    type: "POST",
                    data: $("#tpkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successtpk").text("KVC of the TPK Succesfully genarated");
                            $("#tpkkvc").val(ar[1]);
                        }else{
                            $("#errortpk").text(data);
                        }
                        
                    }
                });
              
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
                                <table class="tit"> <tr> <td   class="center">  SWITCH KEY MANAGEMENT</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->

                                <table> <tr> <td style="width: 350px"></td><td style="text-align: right;" > <font style="font-size:medium; color: #999" > CVV A/B Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <div id="cvv" class="outset" style="border-style:outset;  background-color: #F0F0F0 ; border-color: #999; width: 800px; ">
                                    <div id="cvva" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                        <form name="cvvaPanelForm" style="padding: 20px;" id="cvvaID">
                                            <table>
                                                <tr>
                                                    <td colspan="3"><font color="#FF0000"><b><text id="errorcvva"></text></b></font>
                                                        <font color="green"><b><text id="successcvva"></text></b></font>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td>CVK-A</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="cvva" value="${onlineKeyDetails.cvva}" disabled="" id="cvvaEnable" maxlength="16" size="20"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td>KVC</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="" value="${onlineKeyDetails.cvvakvc}" id="cvvakvc" disabled="" size="6"></td>
                                                </tr>
                                                <tr style="height: 20px;"></tr>


                                            </table>

                                            <table>
                                                <tr>

                                                    <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVVA()"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvvaUpdate" disabled="" onclick="invokeUpdateCVVA()"></td>
                                                </tr>
                                            </table>
                                        </form>


                                    </div>

                                    <br/></br>


                                    <div id="cvvb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                        <form name="cvvbPanelForm" style="padding: 20px;" id="cvvbID">
                                            <table>
                                                <tr>
                                                    <td colspan="3"><font color="#FF0000"><b><text id="errorcvvb"></text></b></font>
                                                        <font color="green"><b><text id="successcvvb"></text></b></font>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td>CVK-B</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="cvvb" value="${onlineKeyDetails.cvvb}" disabled="" id="cvvbEnable" maxlength="16" size="20"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td>KVC</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="" value="${onlineKeyDetails.cvvbkvc}" id="cvvbkvc" disabled="" size="6"></td>
                                                </tr>
                                                <tr style="height: 20px;"></tr>


                                            </table>

                                            <table>
                                                <tr>

                                                    <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVVB()"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvvbUpdate" disabled="" onclick="invokeUpdateCVVB()"></td>
                                                </tr>
                                            </table>
                                        </form>


                                    </div>

                                </div> 


                                <br/></br> <br/></br>              


                                <table> <tr> <td style="width: 350px"></td><td style="text-align: right;" > <font style="font-size:medium; color: #999" > PVV A/B Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <div id="cvv" class="outset" style="border-style:outset;  background-color: #F0F0F0 ; border-color: #999; width: 800px; ">
                                    <div id="cvva" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                        <form name="pvvaPanelForm" style="padding: 20px;" id="pvvaID">
                                            <table>
                                                <tr>
                                                    <td colspan="3"><font color="#FF0000"><b><text id="errorpvva"></text></b></font>
                                                        <font color="green"><b><text id="successpvva"></text></b></font>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td>PVK-A</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="pvva" value="${onlineKeyDetails.pvva}" disabled="" id="pvvaEnable" maxlength="16" size="20"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td>KVC</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="" value="${onlineKeyDetails.pvvakvc}" id="pvvakvc" disabled="" size="6"></td>
                                                </tr>
                                                <tr style="height: 20px;"></tr>


                                            </table>

                                            <table>
                                                <tr>

                                                    <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePVVA()"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="button" value="Update" name="edit" style="width: 100px;" id="pvvaUpdate" disabled="" onclick="invokeUpdatePVVA()"></td>
                                                </tr>
                                            </table>
                                        </form>


                                    </div>

                                    <br/></br>


                                    <div id="cvvb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                        <form name="cvvbPanelForm" style="padding: 20px;" id="pvvbID">
                                            <table>
                                                <tr>
                                                    <td colspan="3"><font color="#FF0000"><b><text id="errorpvvb"></text></b></font>
                                                        <font color="green"><b><text id="successpvvb"></text></b></font>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td>PVK-B</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="pvvb" value="${onlineKeyDetails.pvvb}" disabled="" id="pvvbEnable" maxlength="16" size="20"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td>KVC</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="" value="${onlineKeyDetails.pvvbkvc}" id="pvvbkvc" disabled="" size="6"></td>
                                                </tr>
                                                <tr style="height: 20px;"></tr>


                                            </table>

                                            <table>
                                                <tr>

                                                    <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePVVB()"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="button" value="Update" name="edit" style="width: 100px;" id="pvvbUpdate" disabled="" onclick="invokeUpdatePVVB()"></td>
                                                </tr>
                                            </table>
                                        </form>


                                    </div>

                                </div>                  


                                <br/></br> <br/></br>              


                                <table> <tr> <td style="width: 350px"></td><td style="text-align: right;" > <font style="font-size:medium; color: #999" > POS TPK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <div id="cvv" class="outset" style="border-style:outset;  background-color: #F0F0F0 ; border-color: #999; width: 800px; ">
                                    <div id="cvva" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  20px; margin-bottom: 30px;">




                                        <form name="cvvaPanelForm" style="padding: 20px;" id="tpkID">
                                            <table>
                                                <tr>
                                                    <td colspan="3"><font color="#FF0000"><b><text id="errortpk"></text></b></font>
                                                        <font color="green"><b><text id="successtpk"></text></b></font>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td>TPK</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="tpk" value="${onlineKeyDetails.tpk}" disabled="" id="tpkEnable" maxlength="32" size="42"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td>KVC</td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="text" name="" value="${onlineKeyDetails.tpkkvc}" id="tpkkvc" disabled="" size="6"></td>
                                                </tr>
                                                <tr style="height: 20px;"></tr>


                                            </table>

                                            <table>
                                                <tr>

                                                    <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableTPK()"></td>
                                                    <td style="width: 15px;"></td>
                                                    <td><input type="button" value="Update" name="edit" style="width: 100px;" id="tpkUpdate" disabled="" onclick="invokeUpdateTPK()"></td>
                                                </tr>
                                            </table>
                                        </form>


                                    </div>


                                </div>                            

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
