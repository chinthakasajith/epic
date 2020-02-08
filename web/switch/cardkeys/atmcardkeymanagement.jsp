<%-- 
    Document   : atmkeymanagement
    Created on : Apr 25, 2012, 7:21:28 PM
    Author     : mahesh_m
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
          
       
          
            function enablePVKA()
            {
                $("#pvkaEnable").attr("disabled",false);
                $("#pvkaUpdate").attr("disabled",false);

            }
            
            function enablePVKB()
            {

                $("#pvkbEnable").attr("disabled",false);
                $("#pvkbUpdate").attr("disabled",false);

            }
            
            function enablePVK()
            {

                $("#pvkEnable").attr("disabled",false);
                $("#pvkUpdate").attr("disabled",false);

            }
            
            function enableCVK1A()
            {
                $("#cvk1aEnable").attr("disabled",false);
                $("#cvk1aUpdate").attr("disabled",false);

            }
            
            function enableCVK1B()
            {
                $("#cvk1bEnable").attr("disabled",false);
                $("#cvk1bUpdate").attr("disabled",false);

            }
            
            function enableCVK1()
            {
                $("#cvk1Enable").attr("disabled",false);
                $("#cvk1Update").attr("disabled",false);

            }            
           
            function enableCVK2A()
            {
                $("#cvk2aEnable").attr("disabled",false);
                $("#cvk2aUpdate").attr("disabled",false);

            }
            
            function enableCVK2B()
            {
                $("#cvk2bEnable").attr("disabled",false);
                $("#cvk2bUpdate").attr("disabled",false);

            }
            
            function enableCVK2()
            {
                $("#cvk2Enable").attr("disabled",false);
                $("#cvk2Update").attr("disabled",false);

            }
            //
            function enableICVKA()
            {
                $("#icvkaEnable").attr("disabled",false);
                $("#icvkaUpdate").attr("disabled",false);

            }
            function enableICVKB()
            {
                $("#icvkbEnable").attr("disabled",false);
                $("#icvkbUpdate").attr("disabled",false);

            }
            function enableICVK()
            {
                $("#icvkEnable").attr("disabled",false);
                $("#icvkUpdate").attr("disabled",false);

            }        
            //
            //
            function enableCSCKA()
            {
                $("#csckaEnable").attr("disabled",false);
                $("#csckaUpdate").attr("disabled",false);

            }
            function enableCSCKB()
            {
                $("#csckbEnable").attr("disabled",false);
                $("#csckbUpdate").attr("disabled",false);

            }
            function enableCSCK()
            {
                $("#csckEnable").attr("disabled",false);
                $("#csckUpdate").attr("disabled",false);

            } 
            function enableIMKAC()
            {
                $("#imkacEnable").attr("disabled",false);
                $("#imkacUpdate").attr("disabled",false);

            }        
            function enableZCMK()
            {
                $("#zcmkEnable").attr("disabled",false);
                $("#zcmkUpdate").attr("disabled",false);

            } 
            function enableIMKSMI()
            {
                $("#imksmiEnable").attr("disabled",false);
                $("#imksmiUpdate").attr("disabled",false);

            }        
            //          
            function enableIMKSMC()
            {
                $("#imksmcEnable").attr("disabled",false);
                $("#imksmcUpdate").attr("disabled",false);

            } 
            function enablePPK()
            {
                $("#ppkEnable").attr("disabled",false);
                $("#ppkUpdate").attr("disabled",false);

            }         
            
            
            function invokeUpdate(){
                $("#error").text("");
                $("#success").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardPVKAPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#pvkaID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#success").text("KVC of the PVKA Succesfully genarated");
                            $("#pvkakvc").val(ar[1]);
                        }else{
                            $("#error").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdatePVKB(){
                $("#errorpvkb").text("");
                $("#successpvkb").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardPVKBPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#pvkbID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successpvkb").text("KVC of the PVKB Succesfully genarated");
                            $("#pvkbkvc").val(ar[1]);
                        }else{
                            $("#errorpvkb").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdatePVK(){
                $("#errorpvk").text("");
                $("#successpvk").text("");
                //                window.location = "${pageContext.request.contextPath}/RecalculateCreditScoreServlet?applicationId="+applicationId;
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardPVKPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#pvkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successpvk").text("KVC of the PVK Succesfully genarated");
                            $("#pvkkvc").val(ar[1]);
                        }else{
                            $("#errorpvk").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateCVK1A(){
                $("#errorcvk1a").text("");
                $("#successcvk1a").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCVK1APanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#cvk1aID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvk1a").text("KVC of the CVK1A Succesfully genarated");
                            $("#cvk1akvc").val(ar[1]);
                        }else{
                            $("#errorcvk1a").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateCVK1B(){
                $("#errorcvk1b").text("");
                $("#successcvk1b").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCVK1BPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#cvk1bID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvk1b").text("KVC of the CVK1B Succesfully genarated");
                            $("#cvk1bkvc").val(ar[1]);
                        }else{
                            $("#errorcvk1b").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateCVK1(){
                $("#errorcvk1").text("");
                $("#successcvk1").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCVK1PanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#cvk1ID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvk1").text("KVC of the CVK1 Succesfully genarated");
                            $("#cvk1kvc").val(ar[1]);
                        }else{
                            $("#errorcvk1").text(data);
                        }
                        
                    }
                });
              
            }
            
            ////
            function invokeUpdateCVK2A(){
                $("#errorcvk2a").text("");
                $("#successcvk2a").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCVK2APanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#cvk2aID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvk2a").text("KVC of the CVK2A Succesfully genarated");
                            $("#cvk2akvc").val(ar[1]);
                        }else{
                            $("#errorcvk2a").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateCVK2B(){
                $("#errorcvk2b").text("");
                $("#successcvk2b").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCVK2BPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#cvk2bID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvk2b").text("KVC of the CVK2B Succesfully genarated");
                            $("#cvk2bkvc").val(ar[1]);
                        }else{
                            $("#errorcvk2b").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateCVK2(){
                $("#errorcvk2").text("");
                $("#successcvk2").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCVK2PanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#cvk2ID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcvk2").text("KVC of the CVK2 Succesfully genarated");
                            $("#cvk2kvc").val(ar[1]);
                        }else{
                            $("#errorcvk2").text(data);
                        }
                        
                    }
                });
              
            }
            ////
            ////
            function invokeUpdateICVKA(){
                $("#erroricvka").text("");
                $("#successicvka").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardICVKAPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#icvkaID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successicvka").text("KVC of the ICVKA Succesfully genarated");
                            $("#icvkakvc").val(ar[1]);
                        }else{
                            $("#erroricvka").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateICVKB(){
                $("#erroricvkb").text("");
                $("#successicvkb").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardICVKBPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#icvkbID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successicvkb").text("KVC of the ICVKB Succesfully genarated");
                            $("#icvkbkvc").val(ar[1]);
                        }else{
                            $("#erroricvkb").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateICVK(){
                $("#erroricvk").text("");
                $("#successicvk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardICVKPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#icvkID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successicvk").text("KVC of the ICVK Succesfully genarated");
                            $("#icvkkvc").val(ar[1]);
                        }else{
                            $("#erroricvk").text(data);
                        }
                        
                    }
                });
              
            }
            ////
            ////
            function invokeUpdateCSCKA(){
                $("#errorcscka").text("");
                $("#successcscka").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCSCKAPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#csckaID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcscka").text("KVC of the CSCKA Succesfully genarated");
                            $("#csckakvc").val(ar[1]);
                        }else{
                            $("#errorcscka").text(data);
                        }
                        
                    }
                });
              
            }
            
            
            function invokeUpdateCSCKB(){
                $("#errorcsckb").text("");
                $("#successcsckb").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCSCKBPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#csckbID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcsckb").text("KVC of the CSCKB Succesfully genarated");
                            $("#csckbkvc").val(ar[1]);
                        }else{
                            $("#errorcsckb").text(data);
                        }
                        
                    }
                });
              
            }
            
            function invokeUpdateCSCK(){
                $("#errorcsck").text("");
                $("#successcsck").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardCSCKPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#csckID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successcsck").text("KVC of the CSCK Succesfully genarated");
                            $("#csckkvc").val(ar[1]);
                        }else{
                            $("#errorcsck").text(data);
                        }
                        
                    }
                });
              
            }
            ////           
            function invokeUpdateIMKAC(){
                $("#errorimkac").text("");
                $("#successimkac").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardIMKACPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#imkacID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successimkac").text("KVC of the IMKAC Succesfully genarated");
                            $("#csckkvc").val(ar[1]);
                        }else{
                            $("#errorimkac").text(data);
                        }
                        
                    }
                });
              
            }   
            ////           
            function invokeUpdateZCMK(){
                $("#errorzcmk").text("");
                $("#successzcmk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardZCMKPanelServlet?id=${keybean.id}",
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
            function invokeUpdateIMKSMI(){
                $("#errorimksmi").text("");
                $("#successimksmi").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardIMKSMIPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#imksmiID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successimksmi").text("KVC of the IMKSMI Succesfully genarated");
                            $("#imksmikvc").val(ar[1]);
                        }else{
                            $("#errorimksmi").text(data);
                        }
                        
                    }
                });
              
            } 
            //       
            function invokeUpdateIMKSMC(){
                $("#errorimksmc").text("");
                $("#successimksmc").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardIMKSMCPanelServlet?id=${keybean.id}",
                    type: "POST",
                    data: $("#imksmcID").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        //                        ar=data;
                        if(ar[0] == 'success'){
                            $("#successimksmc").text("KVC of the IMKSMC Succesfully genarated");
                            $("#imksmckvc").val(ar[1]);
                        }else{
                            $("#errorimksmc").text(data);
                        }
                        
                    }
                });
              
            }  
            //  
            function invokeUpdatePPK(){
                $("#errorppk").text("");
                $("#successppk").text("");

                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateAtmCardPPKPanelServlet?id=${keybean.id}",
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
            
            function invokeGoBack()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadCardKeyServlet";
                
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
                { pagecode:'<%= PageVarList.CARD_KEY_MANAGMENT%>'                                  
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
                                <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->

                                <div class="selector" id="tabs">
                                    <ul>
                                        <c:if test="${profilebean.pvk == '1'}">
                                            <li><a href="#tabs-1">PVK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.cvk1 == '1'}">                                        
                                            <li><a href="#tabs-2">CVK1 </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.cvk2 == '1'}">                                         
                                            <li><a href="#tabs-3">CVK2 </a></li> 
                                        </c:if>
                                        <c:if test="${profilebean.ICVK == '1'}">                                         
                                            <li><a href="#tabs-4">ICVK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.CSCK == '1'}"> 
                                            <li><a href="#tabs-5">CSCK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.IMKAC == '1'}"> 
                                            <li><a href="#tabs-6">IMKAC </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.ZCMK == '1'}"> 
                                            <li><a href="#tabs-7">ZCMK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.IMKSMI == '1'}"> 
                                            <li><a href="#tabs-8">IMKSMI </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.IMKSMC == '1'}"> 
                                            <li><a href="#tabs-9">IMKSMC </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.PPK == '1'}"> 
                                            <li><a href="#tabs-10">PPK </a></li>
                                        </c:if>


                                        <!--                                        <li><a href="#tabs-5">POS Verification Key </a></li>-->

                                    </ul>
                                    <c:if test="${profilebean.pvk == '1'}">                                    
                                        <div id="tabs-1" >
                                            <c:if test="${profilebean.isPVKpair == '1'}">
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PVKA Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="pvka" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">





                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="error"></text></b></font>
                                                                <font color="green"><b><text id="success"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>




                                                    <form name="pvkPanelForm" style="padding: 20px;" id="pvkaID">

                                                        <table>
                                                            <tr>
                                                                <td>PVKA</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="pvka" value="${keybean.pvka}" disabled="" id="pvkaEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.pvkaKVC}" id="pvkakvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePVKA()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="pvkaUpdate" disabled="" onclick="invokeUpdate()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PVKB Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                                <div id="pvkb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="pvkPanelForm" style="padding: 20px;" id="pvkbID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorpvkb"></text></b></font>
                                                                    <font color="green"><b><text id="successpvkb"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>PVKB</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="pvkb" value="${keybean.pvkb}" disabled="" id="pvkbEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.pvkbKVC}" id="pvkbkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePVKB()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="pvkbUpdate" disabled="" onclick="invokeUpdatePVKB()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>



                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isPVKpair == '0'}">                                                
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PVK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="pvk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="pvkPanelForm" style="padding: 20px;" id="pvkID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorpvk"></text></b></font>
                                                                    <font color="green"><b><text id="successpvk"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>PVK</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="pvk" value="${keybean.pvk}" disabled="" id="pvkEnable" maxlength="32" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.pvkKVC}" id="pvkkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePVK()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="pvkUpdate" disabled="" onclick="invokeUpdatePVK()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>
                                            </c:if>
                                        </div>
                                    </c:if>                 
                                    <!-- end of tab 1 -->
                                    <c:if test="${profilebean.cvk1 == '1'}">
                                        <div id="tabs-2" >
                                            <c:if test="${profilebean.isCVK1Pair == '1'}">                                           
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK1A Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk1a" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="cvk1PanelForm" style="padding: 20px;" id="cvk1aID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk1a"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk1a"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK1-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk1a" value="${keybean.cvk1a}" disabled="" id="cvk1aEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1aKVC}" id="cvk1akvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVK1A()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvk1aUpdate" disabled="" onclick="invokeUpdateCVK1A()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK1B Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk1b" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk1PanelForm" style="padding: 20px;" id="cvk1bID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk1b"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk1b"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK1-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk1b" value="${keybean.cvk1b}" disabled="" id="cvk1bEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1bKVC}" id="cvk1bkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVK1B()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvk1bUpdate" disabled="" onclick="invokeUpdateCVK1B()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isCVK1Pair == '0'}"> 
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK1 Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk1" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk1PanelForm" style="padding: 20px;" id="cvk1ID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk1"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk1"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK1</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk1" value="${keybean.cvk1}" disabled="" id="cvk1Enable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1KVC}" id="cvk1kvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVK1()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvk1Update" disabled="" onclick="invokeUpdateCVK1()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.cvk2 == '1'}"> 
                                        <div id="tabs-3" >
                                            <c:if test="${profilebean.isCVK2pair == '1'}">                                            
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK2A Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                        
                                                <div id="cvk2a" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="cvk2PanelForm" style="padding: 20px;" id="cvk2aID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk2a"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk2a"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK2-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk2a" value="${keybean.cvk2a}" disabled="" id="cvk2aEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk2aKVC}" id="cvk2akvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVK2A()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvk2aUpdate" disabled="" onclick="invokeUpdateCVK2A()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK2B Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk2b" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk2PanelForm" style="padding: 20px;" id="cvk2bID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk2b"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk2b"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK2-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk2b" value="${keybean.cvk2b}" disabled="" id="cvk2bEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1bKVC}" id="cvk2bkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVK2B()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvk2bUpdate" disabled="" onclick="invokeUpdateCVK2B()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isCVK2pair == '0'}">                                                 
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK2 Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk2" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk2PanelForm" style="padding: 20px;" id="cvk2ID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk2"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk2"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK2</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk2" value="${keybean.cvk2}" disabled="" id="cvk2Enable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk2KVC}" id="cvk2kvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCVK2()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="cvk2Update" disabled="" onclick="invokeUpdateCVK2()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.ICVK == '1'}">                                      
                                        <div id="tabs-4" >
                                            <c:if test="${profilebean.isICVKpair == '1'}">                                            
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ICVKA Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                        
                                                <div id="icvka" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="icvkPanelForm" style="padding: 20px;" id="icvkaID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="erroricvka"></text></b></font>
                                                                    <font color="green"><b><text id="successicvka"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>ICVK-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="icvka" value="${keybean.icvka}" disabled="" id="icvkaEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.icvkaKVC}" id="icvkakvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableICVKA()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="icvkaUpdate" disabled="" onclick="invokeUpdateICVKA()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ICVKB Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="icvkb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="icvkbPanelForm" style="padding: 20px;" id="icvkbID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="erroricvkb"></text></b></font>
                                                                    <font color="green"><b><text id="successicvkb"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>ICVK-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="icvkb" value="${keybean.icvkb}" disabled="" id="icvkbEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.icvkbKVC}" id="icvkbkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableICVKB()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="icvkbUpdate" disabled="" onclick="invokeUpdateICVKB()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isICVKpair == '0'}">                                                
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ICVK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="icvk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="icvkPanelForm" style="padding: 20px;" id="icvkID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="erroricvk"></text></b></font>
                                                                    <font color="green"><b><text id="successicvk"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>ICVK</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="icvk" value="${keybean.icvk}" disabled="" id="icvkEnable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.icvkKVC}" id="icvkkvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableICVK()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="icvkUpdate" disabled="" onclick="invokeUpdateICVK()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.CSCK == '1'}">
                                        <div id="tabs-5" >
                                            <c:if test="${profilebean.isCSCKpair == '1'}">                                            
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CSCKA Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                     
                                                <div id="cscka" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="csckPanelForm" style="padding: 20px;" id="csckaID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcscka"></text></b></font>
                                                                    <font color="green"><b><text id="successcscka"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CSCK-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cscka" value="${keybean.cscka}" disabled="" id="csckaEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.csckaKVC}" id="csckakvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCSCKA()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="csckaUpdate" disabled="" onclick="invokeUpdateCSCKA()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CSCKB Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="csckb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="csckbPanelForm" style="padding: 20px;" id="csckbID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcsckb"></text></b></font>
                                                                    <font color="green"><b><text id="successcsckb"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CSCK-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="csckb" value="${keybean.csckb}" disabled="" id="csckbEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.csckbKVC}" id="csckbkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCSCKB()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="csckbUpdate" disabled="" onclick="invokeUpdateCSCKB()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isCSCKpair == '0'}">                                                
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CSCK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="csck" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="csckPanelForm" style="padding: 20px;" id="csckID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcsck"></text></b></font>
                                                                    <font color="green"><b><text id="successcsck"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CSCK</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="csck" value="${keybean.csck}" disabled="" id="csckEnable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.csckKVC}" id="csckkvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                        <table>
                                                            <tr>

                                                                <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableCSCK()"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="button" value="Update" name="edit" style="width: 100px;" id="csckUpdate" disabled="" onclick="invokeUpdateCSCK()"></td>
                                                            </tr>
                                                        </table>
                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.IMKAC == '1'}">                                    
                                        <div id="tabs-6" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IMKAC Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                    
                                            <div id="imkac" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                <form name="imkacPanelForm" style="padding: 20px;" id="imkacID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorimkac"></text></b></font>
                                                                <font color="green"><b><text id="successimkac"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>IMKAC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="imkac" value="${keybean.imkac}" disabled="" id="imkacEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.imkacKVC}" id="imkackvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>

                                                    <table>
                                                        <tr>

                                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableIMKAC()"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="imkacUpdate" disabled="" onclick="invokeUpdateIMKAC()"></td>
                                                        </tr>
                                                    </table>
                                                </form>


                                            </div>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.ZCMK == '1'}"> 
                                        <div id="tabs-7" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ZCMK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                
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
                                                            <td><input type="text" name="zcmk" value="${keybean.zcmk}" disabled="" id="zcmkEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.zcmkKVC}" id="zcmkkvc" disabled="" size="6"></td>
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
                                    </c:if>
                                    <c:if test="${profilebean.IMKSMI == '1'}">                                    
                                        <div id="tabs-8" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IMKSMI Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                        
                                            <div id="imksmi" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                <form name="imksmiPanelForm" style="padding: 20px;" id="imksmiID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorimksmi"></text></b></font>
                                                                <font color="green"><b><text id="successimksmi"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>IMKSMI</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="imksmi" value="${keybean.imksmi}" disabled="" id="imksmiEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.imksmiKVC}" id="imksmikvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>

                                                    <table>
                                                        <tr>

                                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableIMKSMI()"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="imksmiUpdate" disabled="" onclick="invokeUpdateIMKSMI()"></td>
                                                        </tr>
                                                    </table>
                                                </form>


                                            </div>

                                        </div> 
                                    </c:if>
                                    <c:if test="${profilebean.IMKSMC == '1'}"> 
                                        <div id="tabs-9" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IMKSMC Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                  
                                            <div id="imksmc" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                <form name="imksmcPanelForm" style="padding: 20px;" id="imksmcID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorimksmc"></text></b></font>
                                                                <font color="green"><b><text id="successimksmc"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>IMKSMC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="imksmc" value="${keybean.imksmc}" disabled="" id="imksmcEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.imksmcKVC}" id="imksmckvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>

                                                    <table>
                                                        <tr>

                                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableIMKSMC()"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="imksmcUpdate" disabled="" onclick="invokeUpdateIMKSMC()"></td>
                                                        </tr>
                                                    </table>
                                                </form>


                                            </div>

                                        </div> 
                                    </c:if>
                                    <c:if test="${profilebean.PPK == '1'}">
                                        <div id="tabs-10" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PPK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                     
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
                                                            <td><input type="text" name="ppk" value="${keybean.ppk}" disabled="" id="ppkEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.ppkKVC}" id="ppkkvc" disabled="" size="6"></td>
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

                                        </div> 
                                    </c:if>
                                    <!--                                    <div id="tabs-5" >
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
                                    
                                    
                                                                        </div>-->

                                    <!--   ------------------------- end developer area  --------------------------------                      -->

                                </div>
                                <table>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td><input type="button" name="back" value="Back" onclick="invokeGoBack()" style="width: 100px"/></td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </div>
                    <div class="clearer"><span></span></div>
                </div>

            </div>
            <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
