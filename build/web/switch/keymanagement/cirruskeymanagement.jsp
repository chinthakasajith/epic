<%-- 
    Document   : cirruskeymanagement
    Created on : Dec 19, 2012, 8:28:38 AM
    Author     : asela
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
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
            function enablePVV()
            {
                $("#pvvEnable").attr("disabled",false);
                $("#pvvUpdate").attr("disabled",false);

            }            
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
            
            function enableIMK_AC()
            {
                $("#imk_acEnable").attr("disabled",false);
                $("#imk_acUpdate").attr("disabled",false);

            }
            function enableZCMK()
            {
                $("#zcmkEnable").attr("disabled",false);
                $("#zcmkUpdate").attr("disabled",false);

            }
            
            function enablePPK()
            {
                $("#ppkEnable").attr("disabled",false);
                $("#ppkUpdate").attr("disabled",false);

            }
            
            function enableTPK()
            {
                $("#tpkEnable").attr("disabled",false);
                $("#tpkUpdate").attr("disabled",false);

            }

            
            function invokeUpdate(){
                $("#error").text("");
                $("#success").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusZMKPanelServlet",
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
                    url: "${pageContext.request.contextPath}/UpdateCirrusAWKPanelServlet",
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
                    url: "${pageContext.request.contextPath}/UpdateCirrusIWKPanelServlet",
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
            
            
            function invokeUpdatePVVA(){
                $("#errorpvva").text("");
                $("#successpvva").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusPVVAPanelServlet",
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
                    url: "${pageContext.request.contextPath}/UpdateCirrusPVVBPanelServlet",
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
            
            function invokeUpdatePVV(){
                $("#errorpvv").text("");
                $("#successpvv").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusPVVPanelServlet",
                    type: "POST",
                    data: $("#pvvID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successpvv").text("KVC of the PVV Succesfully genarated");
                            $("#pvvkvc").val(ar[1]);
                        }else{
                            $("#errorpvv").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateCVVA(){
                
                $("#errorcvva").text("");
                $("#successcvva").text("");
               
                $.ajax({
                    
                    url: "${pageContext.request.contextPath}/UpdateCirrusCVVAServlet",
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
                    url: "${pageContext.request.contextPath}/UpdateCirrusCVVBPanelServlet",
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
            
            
            function invokeUpdateIMK_AC(){
                $("#errorimk_ac").text("");
                $("#successimk_ac").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusIMKPanelServlet",
                    type: "POST",
                    data: $("#imk_acID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successimk_ac").text("KVC of the DMK_AC Succesfully genarated");
                            $("#imk_ackvc").val(ar[1]);
                        }else{
                            $("#errorimk_ac").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateZCMK(){
                $("#errorzcmk").text("");
                $("#successzcmk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusZCMKPanelServlet",
                    type: "POST",
                    data: $("#zcmkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successzcmk").text("KVC of the ZCMK Succesfully genarated");
                            $("#zcmkkvc").val(ar[1]);
                        }else{
                            $("#errorzcmk").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdatePPK(){
                $("#errorppk").text("");
                $("#successppk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusPPKPanelServlet",
                    type: "POST",
                    data: $("#ppkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successppk").text("KVC of the PPK Succesfully genarated");
                            $("#ppkkvc").val(ar[1]);
                        }else{
                            $("#errorppk").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateTPK(){
                $("#errortpk").text("");
                $("#successtpk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateCirrusTPKPanelServlet",
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
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CIRRUS_KEY_MGT%>'                                  
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

                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Pin Translation </a></li>
                                        <li><a href="#tabs-2">Pin Verification Key</a></li>
                                        <li><a href="#tabs-3">CVC Verification Key</a></li>
                                        <li><a href="#tabs-4">ICC Verification Key</a></li>
                                        <li><a href="#tabs-5">POS Verification Key</a></li>
                                    </ul>
                                    <div id="tabs-1" >

                                        <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ZMK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                        <div id="zmk" class="outset" style="border-style:outset; background-color: #F0F0F0 ;  border-color: #999; width: 600px;">





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
                                                        <td><input type="text" name="zmk" value="${keyDetails.zmk}" disabled="" id="zmkEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.zmkKVC}" id="zmkkvc" disabled="" size="6"></td>
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

                                        <div id="awk" class="outset" style="border-style:outset;  background-color: #F0F0F0 ; border-color: #999; width: 600px;">




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
                                                        <td><input type="text" name="awk" value="${keyDetails.awk}" disabled="" id="awkEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.awkKVC}" id="awkkvc" disabled="" size="6"></td>
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
                                        <div id="iwk" class="outset" style="border-style:outset;  background-color: #F0F0F0 ; border-color: #999; width: 600px;">




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
                                                        <td><input type="text" name="iwk" value="${keyDetails.iwk}" disabled="" id="iwkEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.iwkKVC}" id="iwkkvc" disabled="" size="6"></td>
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

                                    </div><!-- end of tab 1 -->


                                    <div id="tabs-2" >
                                        <div id="pvva" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




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
                                                        <td><input type="text" name="pvva" value="${keyDetails.pvva}" disabled="" id="pvvaEnable" maxlength="16" size="20"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.pvvaKVC}" id="pvvakvc" disabled="" size="6"></td>
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


                                        <div id="pvvb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




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
                                                        <td><input type="text" name="pvvb" value="${keyDetails.pvvb}" disabled="" id="pvvbEnable" maxlength="16" size="20"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.pvvbKVC}" id="pvvbkvc" disabled="" size="6"></td>
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

                                        <div id="pvv" class="outset" style="border-style:outset;  background-color:#B0BED9 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">

                                            <form name="pvvPanelForm" style="padding: 20px;" id="pvvID">
                                                <table>
                                                    <tr>
                                                        <td colspan="3"><font color="#FF0000"><b><text id="errorpvv"></text></b></font>
                                                            <font color="green"><b><text id="successpvv"></text></b></font>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table>
                                                    <tr>
                                                        <td>PVK</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="pvv" value="${keyDetails.pvv}" disabled="" id="pvvEnable" maxlength="32" size="35"/></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.pvvKVC}" id="pvvkvc" disabled="" size="6"/></td>
                                                    </tr>
                                                    <tr style="height: 20px;"></tr>
                                                </table>

                                                <table>
                                                    <tr>

                                                        <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePVV()"/></td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="button" value="Update" name="edit" style="width: 100px;" id="pvvUpdate" disabled="" onclick="invokeUpdatePVV()"/></td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </div>

                                    </div>

                                    <div id="tabs-3" >
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
                                                        <td><input type="text" name="cvva" value="${keyDetails.cvva}" disabled="" id="cvvaEnable" maxlength="16" size="20"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.cvvaKVC}" id="cvvakvc" disabled="" size="6"></td>
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
                                                        <td><input type="text" name="cvvb" value="${keyDetails.cvvb}" disabled="" id="cvvbEnable" maxlength="16" size="20"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.cvvbKVC}" id="cvvbkvc" disabled="" size="6"></td>
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


                                    <div id="tabs-4" >
                                        <br/></br>
                                        <div id="imk_ac" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">
                                            <form name="imk_acPanelForm" style="padding: 20px;" id="imk_acID">
                                                <table>
                                                    <tr>
                                                        <td colspan="3"><font color="#FF0000"><b><text id="errorimk_ac"></text></b></font>
                                                            <font color="green"><b><text id="successimk_ac"></text></b></font>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table>
                                                    <tr>
                                                        <td>IMK<sub>AC</sub></td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="imk_ac" value="${keyDetails.imk_ac}" disabled="" id="imk_acEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.imk_acKVC}" id="imk_ackvc" disabled="" size="6"></td>
                                                    </tr>
                                                    <tr style="height: 20px;"></tr>


                                                </table>

                                                <table>
                                                    <tr>

                                                        <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableIMK_AC()"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="button" value="Update" name="edit" style="width: 100px;" id="imk_acUpdate" disabled="" onclick="invokeUpdateIMK_AC()"></td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </div>    

                                        <br/></br>
                                        <div id="zcmk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">
                                            <form name="zcmkPanelForm" style="padding: 20px;" id="zcmkID">
                                                <table>
                                                    <tr>
                                                        <td colspan="3"><font color="#FF0000"><b><text id="errorzcmk"></text></b></font>
                                                            <font color="green"><b><text id="successzcmk"></text></b></font>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table>
                                                    <tr>
                                                        <td>ZCMK</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="zcmk" value="${keyDetails.zcmk}" disabled="" id="zcmkEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.zcmkKVC}" id="zcmkkvc" disabled="" size="6"></td>
                                                    </tr>
                                                    <tr style="height: 20px;"></tr>


                                                </table>

                                                <table>
                                                    <tr>

                                                        <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableZCMK()"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="button" value="Update" name="edit" style="width: 100px;" id="zcmkUpdate" disabled="" onclick="invokeUpdateZCMK()"></td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </div>               

                                    </div>

                                    <div id="tabs-5" >
                                        <br/></br>
                                        <div id="ppk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">
                                            <form name="ppkPanelForm" style="padding: 20px;" id="ppkID">
                                                <table>
                                                    <tr>
                                                        <td colspan="3"><font color="#FF0000"><b><text id="errorppk"></text></b></font>
                                                            <font color="green"><b><text id="successppk"></text></b></font>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table>
                                                    <tr>
                                                        <td>PPK</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="ppk" value="${keyDetails.ppk}" disabled="" id="ppkEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.ppkKVC}" id="ppkkvc" disabled="" size="6"></td>
                                                    </tr>
                                                    <tr style="height: 20px;"></tr>


                                                </table>

                                                <table>
                                                    <tr>

                                                        <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePPK()"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="button" value="Update" name="edit" style="width: 100px;" id="ppkUpdate" disabled="" onclick="invokeUpdatePPK()"></td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </div>    

                                        <br/></br>
                                        <div id="tpk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">
                                            <form name="tpkPanelForm" style="padding: 20px;" id="tpkID">
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
                                                        <td><input type="text" name="tpk" value="${keyDetails.tpk}" disabled="" id="tpkEnable" maxlength="32" size="42"></td>
                                                        <td style="width: 15px;"></td>
                                                        <td>KVC</td>
                                                        <td style="width: 15px;"></td>
                                                        <td><input type="text" name="" value="${keyDetails.tpkKVC}" id="tpkkvc" disabled="" size="6"></td>
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
