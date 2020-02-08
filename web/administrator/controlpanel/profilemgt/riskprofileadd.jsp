<%-- 
    Document   : riskprofileadd
    Created on : May 24, 2012, 9:04:04 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>-->


        <script type="text/javascript">
            
            function selectAllAssignList(selectBoxMcc,selectBoxTxn,selectBoxCountry,selectBoxCurrency,selectBoxBin) {
            alert("addd");
                for (var i = 0; i < selectBoxMcc.options.length; i++) {
                    selectBoxMcc.options[i].selected = true;
                }   
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCountry.options.length; i++) {
                    selectBoxCountry.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCurrency.options.length; i++) {
                    selectBoxCurrency.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxBin.options.length; i++) {
                    selectBoxBin.options[i].selected = true;
                } 
                invokeAdd();
            }
            
            function selectAllAssignListNotBin(selectBoxMcc,selectBoxTxn,selectBoxCountry,selectBoxCurrency) {
            
                for (var i = 0; i < selectBoxMcc.options.length; i++) {
                    selectBoxMcc.options[i].selected = true;
                }   
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCountry.options.length; i++) {
                    selectBoxCountry.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCurrency.options.length; i++) {
                    selectBoxCurrency.options[i].selected = true;
                }
                invokeAdd();
            }
            
            function invokeAdd()
            {

                document.addRiskProfForm.action="${pageContext.request.contextPath}/AddRiskProfileMgtAssignDataServlet";
                document.addRiskProfForm.submit();

            }
            function selectAllAssignListUpdate(selectBoxMcc,selectBoxTxn,selectBoxCountry,selectBoxCurrency,selectBoxBin) {
            
         alert("1");
                for (var i = 0; i < selectBoxMcc.options.length; i++) {
                    selectBoxMcc.options[i].selected = true;
                }   
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCountry.options.length; i++) {
                    selectBoxCountry.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCurrency.options.length; i++) {
                    selectBoxCurrency.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxBin.options.length; i++) {
                    selectBoxBin.options[i].selected = true;
                } 
               
                Update();
            }
            
            function selectAllAssignListUpdateNotBin(selectBoxMcc,selectBoxTxn,selectBoxCountry,selectBoxCurrency) {
            alert("1");
                for (var i = 0; i < selectBoxMcc.options.length; i++) {
                    selectBoxMcc.options[i].selected = true;
                }   
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCountry.options.length; i++) {
                    selectBoxCountry.options[i].selected = true;
                } 
                for (var i = 0; i < selectBoxCurrency.options.length; i++) {
                    selectBoxCurrency.options[i].selected = true;
                }
                
                Update();
            }
            
            function Update(){
                
                document.updateRiskProfForm.action="${pageContext.request.contextPath}/UpdateRiskProfileMgtAssignDataServlet";
                document.updateRiskProfForm.submit();

            }
            
            function invokeReset(){
            
                window.location = "${pageContext.request.contextPath}/LoadCreateRiskProfileMgtServlet";
            }

            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/LoadRiskProfileMgtServlet";
            }
            
            //            function feeResetBtn(){
            //                window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet";
            //            }
            
            function profileTypeSet(value,profileType){
               
                if(value == 'add'){
                    document.addRiskProfForm.action="${pageContext.request.contextPath}/OnChangeCreateRiskProfileMgtServlet?value="+value;
                    document.addRiskProfForm.submit();
                }
                if(value == 'update'){                   
                    document.updateRiskProfForm.action="${pageContext.request.contextPath}/OnChangeCreateRiskProfileMgtServlet?value="+value+"&profileType="+profileType;
                    document.updateRiskProfForm.submit();
                }
            }
            
            function velueSet(value){
                
                if(value == 'add'){
                    document.addRiskProfForm.action="${pageContext.request.contextPath}/OnChangeSetValueRiskProfileMgtServlet?value="+value;
                    document.addRiskProfForm.submit();
                }
                if(value == 'update'){
                    document.updateRiskProfForm.action="${pageContext.request.contextPath}/OnChangeSetValueRiskProfileMgtServlet?value="+value;
                    document.updateRiskProfForm.submit();
                }                             
            }
            
            function buttonDisable(){
                 
                var e = document.getElementById("selectRiskProfType");
                var strUser = e.options[e.selectedIndex].value;
              
                if(strUser!=""){
                    
                    document.addRiskProfForm.saveNext.disabled = false;
                   
                } 
            }
            
            
            //            function Delete(value){
            //                
            //                
            //                answer = confirm("Do you really want to delete "+value+" Fee Profile ?")
            //                if (answer !=0)
            //                {
            //                    window.location = "${pageContext.request.contextPath}/DeleteFeeProfileMgtServlet?id="+value;
            //                }
            //                else
            //                {
            //                    window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet?id="+value;
            //                }
            //                
            //            }
            //            function Update(value){
            //                window.location = "${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?id="+value;
            //            }
            //            
            //            //            
            //            //            function View(id){
            //            //                $.post("${pageContext.request.contextPath}/ViewFeeProfileServlet", { id:id },
            //            //                function(data) {
            //            //                    if(data == "success"){
            //            //                        $.colorbox({href:"${pageContext.request.contextPath}/administrator/controlpanel/profilemgt/feeprofileview.jsp", iframe:false, height:"80%", width:"60%",overlayClose:false});
            //            //                    }else{
            //            //                        alert("can not view call");
            //            //                    }
            //            //
            //            //                });
            //            //                
            //            //                
            //            //                
            //            //                //                window.location = "${pageContext.request.contextPath}/ViewExchangeRateServlet?id="+value;
            //            //            }
            //          
            
            
            function saveAndNext(){
                
                document.addRiskProfForm.action="${pageContext.request.contextPath}/AddRiskProfileMgtServlet";
                document.addRiskProfForm.submit();
            }
            
            function updateAndNext(){
                
                document.updateRiskProfForm.action="${pageContext.request.contextPath}/UpdateRiskProfileMgtServlet";
                document.updateRiskProfForm.submit();
            }
            function invokeUpdateReset(riskProfCode){
                window.location = "${pageContext.request.contextPath}/LoadUpdateRiskProfileMgtServlet?riskProfCode="+riskProfCode;
            }
            
            function checkPeriod(currentPeriod){
    
    
                var newPeriod = addRiskProfForm.elements["peroid"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newPeriod) > parseFloat(currentPeriod)){
        
                    if(profileType =='RPACT'){ 
                        alert("Customer risk profile period is "+currentPeriod + 
                            "\nAccount risk profile period must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){ 
                        alert("Account risk profile period is "+currentPeriod +
                            "\nCard risk profile period must be less than account risk profile");  
                    }
                    if(profileType=='RPTER'){ 
                        alert("Merchant risk profile period is "+currentPeriod +
                            "\nTerminal risk profile period must be less than merchant risk profile");
                    }
                    
                    addRiskProfForm.elements["peroid"].value = currentPeriod;
                   
                }
    
            }

            function checkMinimumSingleTxnLimit(currentMinimumSingleTxnLimit){
    
    
                var newMinimumSingleTxnLimit = addRiskProfForm.elements["minimumSingleTxnLimit"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMinimumSingleTxnLimit) < parseFloat(currentMinimumSingleTxnLimit)){
        
                    if(profileType =='RPACT'){ 
                        alert("Customer risk profile Minimum Single Transaction Limit is "+currentMinimumSingleTxnLimit +
                            "\nAccount risk profile Minimum Single Transaction Limit must be greater than customer risk profile" );
                    }
                    if(profileType =='RPCRD'){ 
                        alert("Account risk profile Minimum Single Transaction Limit is "+currentMinimumSingleTxnLimit +
                            "\nCard risk profile Minimum Single Transaction Limit must be greater than account risk profile" );
                    }
                    if(profileType =='RPTER'){ 
                        alert("Merchant risk profile Minimum Single Transaction Limit is "+currentMinimumSingleTxnLimit +
                            "\nTerminal risk profile Minimum Single Transaction Limit must be greater than merchant risk profile" );
                    }
                    addRiskProfForm.elements["minimumSingleTxnLimit"].value = currentMinimumSingleTxnLimit;
                   
                }
    
            }

            function checkMaxSingleTxnLimit(currentMaxSingleTxnLimit){
    
    
                var newMaxSingleTxnLimit = addRiskProfForm.elements["maxSingleTxnLimit"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxSingleTxnLimit) > parseFloat(currentMaxSingleTxnLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Single Trasaction Limit is "+currentMaxSingleTxnLimit+
                            "\nAccount risk profile Maximum Single Trasaction Limit must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Single Trasaction Limit is "+currentMaxSingleTxnLimit+
                            "\nCard risk profile Maximum Single Trasaction Limit must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Single Trasaction Limit is "+currentMaxSingleTxnLimit+
                            "\nTerminal risk profile Maximum Single Trasaction Limit must be less than merchant risk profile");
                    }
                    addRiskProfForm.elements["maxSingleTxnLimit"].value = currentMaxSingleTxnLimit;
                }
    
            }

            function checkMinSingleCashLimit(currentMinSingleCashLimit){
    
    
                var newMinSingleCashLimit = addRiskProfForm.elements["minSingleCashLimit"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMinSingleCashLimit) < parseFloat(currentMinSingleCashLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Minimum Single Cash Transaction Limit is "+currentMinSingleCashLimit+
                            "\nAccount risk profile Minimum Single Cash Transaction Limit must be greater than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Minimum Single Cash Transaction Limit is "+currentMinSingleCashLimit+
                            "\nAccount risk profile Minimum Single Cash Transaction Limit must be greater than customer risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Minimum Single Cash Transaction Limit is "+currentMinSingleCashLimit+
                            "\nTerminal risk profile Minimum Single Cash Transaction Limit must be greater than merchant risk profile");
                    }
                    addRiskProfForm.elements["minSingleCashLimit"].value = currentMinSingleCashLimit;
                }
    
            }

            function checkMaxSingleCashLimit(currentMaxSingleCashLimit){
    
    
                var newMaxSingleCashLimit = addRiskProfForm.elements["maxSingleCashLimit"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxSingleCashLimit) > parseFloat(currentMaxSingleCashLimit)){
                    
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Single Cash Transaction Limit is "+currentMaxSingleCashLimit+
                            "\nAccount risk profile Maximum Single Cash Transaction Limit must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Single Cash Transaction Limit is "+currentMaxSingleCashLimit+
                            "\nCard risk profile Maximum Single Cash Transaction Limit must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Single Cash Transaction Limit is "+currentMaxSingleCashLimit+
                            "\nTerminal risk profile Maximum Single Cash Transaction Limit must be less than merchant risk profile");
                    }
                    
                    addRiskProfForm.elements["maxSingleCashLimit"].value = currentMaxSingleCashLimit;
                }
    
            }

            function checkMaxTxnCount(currentMaxTxnCount){
        
                var newMaxTxnCount = addRiskProfForm.elements["maxTxnCount"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxTxnCount) > parseFloat(currentMaxTxnCount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Transaction Count is "+currentMaxTxnCount+
                            "\nAccount risk profile Maximum Transaction Count must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Transaction Count is "+currentMaxTxnCount+
                            "\nCard risk profile Maximum Transaction Count must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Transaction Count is "+currentMaxTxnCount+
                            "\nTerminal risk profile Maximum Transaction Count must be less than merchant risk profile");
                    }
                    
                    addRiskProfForm.elements["maxTxnCount"].value = currentMaxTxnCount;
                }
            }

            function checkMaxCashCount(currentMaxCashCount){
    
    
                var newMaxCashCount = addRiskProfForm.elements["maxCashCount"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxCashCount) > parseFloat(currentMaxCashCount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Cash Count is "+currentMaxCashCount+
                            "\nAccount risk profile Maximum Cash Count must be less than Customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Cash Count is "+currentMaxCashCount+
                            "\nCard risk profile Maximum Cash Count must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Cash Count is "+currentMaxCashCount+
                            "\nTerminal risk profile Maximum Cash Count must be less than merchant risk profile");
                    }
                    addRiskProfForm.elements["maxCashCount"].value = currentMaxCashCount;
                }
            }

            function checkMaxTotalTxnAmount(currentMaxTotalTxnAmount){
    
    
                var newMaxTotalTxnAmount = addRiskProfForm.elements["maxTotalTxnAmount"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxTotalTxnAmount) > parseFloat(currentMaxTotalTxnAmount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer Maximum Total Transaction Amount is "+currentMaxTotalTxnAmount+
                            "\nAccount risk profile Maximum Total Transaction Amount must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Total Transaction Amount is "+currentMaxTotalTxnAmount+
                            "\nCard risk profile Maximum Total Transaction Amount must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant Maximum Total Transaction Amount is "+currentMaxTotalTxnAmount+
                            "\nTerminal risk profile Maximum Total Transaction Amount must be less than merchant risk profile");
                    }
                    addRiskProfForm.elements["maxTotalTxnAmount"].value = currentMaxTotalTxnAmount;
                }   
            }

            function checkMaxTotalCashAmount(currentMaxTotalCashAmount){
    
    
                var newMaxTotalCashAmount = addRiskProfForm.elements["maxTotalCashAmount"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxTotalCashAmount) > parseFloat(currentMaxTotalCashAmount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Total Cash Transaction Amount is "+currentMaxTotalCashAmount+
                            "\nAccount risk profile Maximum Total Cash Transaction Amount must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Total Cash Transaction Amount is "+currentMaxTotalCashAmount+
                            "\nCard risk profile Maximum Total Cash Transaction Amount must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Total Cash Transaction Amount is "+currentMaxTotalCashAmount+
                            "\nTerminal risk profile Maximum Total Cash Transaction Amount must be less than merchant risk profile");
                    }
                    addRiskProfForm.elements["maxTotalCashAmount"].value = currentMaxTotalCashAmount;
                } 
            }

            function checkExtraAuthLimit(currentExtraAuthLimit){
    
    
                var newExtraAuthLimit = addRiskProfForm.elements["extraAuthLimit"].value;
                var profileType = addRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newExtraAuthLimit) > parseFloat(currentExtraAuthLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Extra Authorization Limit is "+currentExtraAuthLimit+
                            "\nAccount risk prfile Extra Authorization Limit must be less than customer risk profile" ); 
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Extra Authorization Limit is "+currentExtraAuthLimit+
                            "\nCard risk prfile Extra Authorization Limit must be less than account risk profile" ); 
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Extra Authorization Limit is "+currentExtraAuthLimit+
                            "\nTerminal risk prfile Extra Authorization Limit must be less than merchant risk profile" ); 
                    }
                    addRiskProfForm.elements["extraAuthLimit"].value = currentExtraAuthLimit;
                }
            }
            
            
            
            //            update validation

            function checkUpPeriod(currentPeriod){
    
    
                var newPeriod = updateRiskProfForm.elements["peroid"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newPeriod) > parseFloat(currentPeriod)){
        
                    if(profileType =='RPACT'){ 
                        alert("Customer risk profile period is "+currentPeriod + 
                            "\nAccount risk profile period must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){ 
                        alert("Account risk profile period is "+currentPeriod +
                            "\nCard risk profile period must be less than account risk profile");  
                    }
                    if(profileType=='RPTER'){ 
                        alert("Merchant risk profile period is "+currentPeriod +
                            "\nTerminal risk profile period must be less than merchant risk profile");
                    }
                    
                    updateRiskProfForm.elements["peroid"].value = currentPeriod;
                }
    
            }

            function checkUpMinimumSingleTxnLimit(currentMinimumSingleTxnLimit){
    
    
                var newMinimumSingleTxnLimit = updateRiskProfForm.elements["minimumSingleTxnLimit"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMinimumSingleTxnLimit) < parseFloat(currentMinimumSingleTxnLimit)){
        
                    if(profileType =='RPACT'){ 
                        alert("Customer risk profile Minimum Single Transaction Limit is "+currentMinimumSingleTxnLimit +
                            "\nAccount risk profile Minimum Single Transaction Limit must be greater than customer risk profile" );
                    }
                    if(profileType =='RPCRD'){ 
                        alert("Account risk profile Minimum Single Transaction Limit is "+currentMinimumSingleTxnLimit +
                            "\nCard risk profile Minimum Single Transaction Limit must be greater than account risk profile" );
                    }
                    if(profileType =='RPTER'){ 
                        alert("Merchant risk profile Minimum Single Transaction Limit is "+currentMinimumSingleTxnLimit +
                            "\nTerminal risk profile Minimum Single Transaction Limit must be greater than merchant risk profile" );
                    }
                    updateRiskProfForm.elements["minimumSingleTxnLimit"].value = currentMinimumSingleTxnLimit;
                }
    
            }

            function checkUpMaxSingleTxnLimit(currentMaxSingleTxnLimit){
    
    
                var newMaxSingleTxnLimit = updateRiskProfForm.elements["maxSingleTxnLimit"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxSingleTxnLimit) > parseFloat(currentMaxSingleTxnLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Single Trasaction Limit is "+currentMaxSingleTxnLimit+
                            "\nAccount risk profile Maximum Single Trasaction Limit must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Single Trasaction Limit is "+currentMaxSingleTxnLimit+
                            "\nCard risk profile Maximum Single Trasaction Limit must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Single Trasaction Limit is "+currentMaxSingleTxnLimit+
                            "\nTerminal risk profile Maximum Single Trasaction Limit must be less than merchant risk profile");
                    }
                    updateRiskProfForm.elements["maxSingleTxnLimit"].value = currentMaxSingleTxnLimit;
                }
    
            }

            function checkUpMinSingleCashLimit(currentMinSingleCashLimit){
    
    
                var newMinSingleCashLimit = updateRiskProfForm.elements["minSingleCashLimit"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMinSingleCashLimit) < parseFloat(currentMinSingleCashLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Minimum Single Cash Transaction Limit is "+currentMinSingleCashLimit+
                            "\nAccount risk profile Minimum Single Cash Transaction Limit must be greater than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Minimum Single Cash Transaction Limit is "+currentMinSingleCashLimit+
                            "\nAccount risk profile Minimum Single Cash Transaction Limit must be greater than customer risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Minimum Single Cash Transaction Limit is "+currentMinSingleCashLimit+
                            "\nTerminal risk profile Minimum Single Cash Transaction Limit must be greater than merchant risk profile");
                    }
                    updateRiskProfForm.elements["minSingleCashLimit"].value = currentMinSingleCashLimit;
                }
    
            }

            function checkUpMaxSingleCashLimit(currentMaxSingleCashLimit){
    
    
                var newMaxSingleCashLimit = updateRiskProfForm.elements["maxSingleCashLimit"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxSingleCashLimit) > parseFloat(currentMaxSingleCashLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Single Cash Transaction Limit is "+currentMaxSingleCashLimit+
                            "\nAccount risk profile Maximum Single Cash Transaction Limit must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Single Cash Transaction Limit is "+currentMaxSingleCashLimit+
                            "\nCard risk profile Maximum Single Cash Transaction Limit must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Single Cash Transaction Limit is "+currentMaxSingleCashLimit+
                            "\nTerminal risk profile Maximum Single Cash Transaction Limit must be less than merchant risk profile");
                    }
                    updateRiskProfForm.elements["maxSingleCashLimit"].value = currentMaxSingleCashLimit;
                }
    
            }

            function checkUpMaxTxnCount(currentMaxTxnCount){
        
                var newMaxTxnCount = updateRiskProfForm.elements["maxTxnCount"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxTxnCount) > parseFloat(currentMaxTxnCount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Transaction Count is "+currentMaxTxnCount+
                            "\nAccount risk profile Maximum Transaction Count must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Transaction Count is "+currentMaxTxnCount+
                            "\nCard risk profile Maximum Transaction Count must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Transaction Count is "+currentMaxTxnCount+
                            "\nTerminal risk profile Maximum Transaction Count must be less than merchant risk profile");
                    }
                    updateRiskProfForm.elements["maxTxnCount"].value = currentMaxTxnCount;
                }
            }

            function checkUpMaxCashCount(currentMaxCashCount){
    
    
                var newMaxCashCount = updateRiskProfForm.elements["maxCashCount"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxCashCount) > parseFloat(currentMaxCashCount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Cash Count is "+currentMaxCashCount+
                            "\nAccount risk profile Maximum Cash Count must be less than Customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Cash Count is "+currentMaxCashCount+
                            "\nCard risk profile Maximum Cash Count must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Cash Count is "+currentMaxCashCount+
                            "\nTerminal risk profile Maximum Cash Count must be less than merchant risk profile");
                    }
                    updateRiskProfForm.elements["maxCashCount"].value = currentMaxCashCount;
                }
            }

            function checkUpMaxTotalTxnAmount(currentMaxTotalTxnAmount){
    
    
                var newMaxTotalTxnAmount = updateRiskProfForm.elements["maxTotalTxnAmount"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxTotalTxnAmount) > parseFloat(currentMaxTotalTxnAmount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer Maximum Total Transaction Amount is "+currentMaxTotalTxnAmount+
                            "\nAccount risk profile Maximum Total Transaction Amount must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Total Transaction Amount is "+currentMaxTotalTxnAmount+
                            "\nCard risk profile Maximum Total Transaction Amount must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant Maximum Total Transaction Amount is "+currentMaxTotalTxnAmount+
                            "\nTerminal risk profile Maximum Total Transaction Amount must be less than merchant risk profile");
                    }
                    updateRiskProfForm.elements["maxTotalTxnAmount"].value = currentMaxTotalTxnAmount;
                }   
            }

            function checkUpMaxTotalCashAmount(currentMaxTotalCashAmount){
    
    
                var newMaxTotalCashAmount = updateRiskProfForm.elements["maxTotalCashAmount"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newMaxTotalCashAmount) > parseFloat(currentMaxTotalCashAmount)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Maximum Total Cash Transaction Amount is "+currentMaxTotalCashAmount+
                            "\nAccount risk profile Maximum Total Cash Transaction Amount must be less than customer risk profile");
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Maximum Total Cash Transaction Amount is "+currentMaxTotalCashAmount+
                            "\nCard risk profile Maximum Total Cash Transaction Amount must be less than account risk profile");
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Maximum Total Cash Transaction Amount is "+currentMaxTotalCashAmount+
                            "\nTerminal risk profile Maximum Total Cash Transaction Amount must be less than merchant risk profile");
                    }
                    updateRiskProfForm.elements["maxTotalCashAmount"].value = currentMaxTotalCashAmount;
                } 
            }

            function checkUpExtraAuthLimit(currentExtraAuthLimit){
    
    
                var newExtraAuthLimit = updateRiskProfForm.elements["extraAuthLimit"].value;
                var profileType = updateRiskProfForm.elements["selectRiskProfType"].value;
    
                if(profileType!='RPCUS' && profileType!='RPMER' && parseFloat(newExtraAuthLimit) > parseFloat(currentExtraAuthLimit)){
        
                    if(profileType =='RPACT'){
                        alert("Customer risk profile Extra Authorization Limit is "+currentExtraAuthLimit+
                            "\nAccount risk prfile Extra Authorization Limit must be less than customer risk profile" ); 
                    }
                    if(profileType =='RPCRD'){
                        alert("Account risk profile Extra Authorization Limit is "+currentExtraAuthLimit+
                            "\nCard risk prfile Extra Authorization Limit must be less than account risk profile" ); 
                    }
                    if(profileType =='RPTER'){
                        alert("Merchant risk profile Extra Authorization Limit is "+currentExtraAuthLimit+
                            "\nTerminal risk prfile Extra Authorization Limit must be less than merchant risk profile" ); 
                    }
                    updateRiskProfForm.elements["extraAuthLimit"].value = currentExtraAuthLimit;
                }
            }

        </script>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.RISKPROFILE%>'                                  
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

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td style="width: 500px"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--  -----------------------------------Start Add Area -----------------------------------------------          -->

                                <c:if test="${operationtype=='add'}"> 
                                    <form action="" method="POST" name="addRiskProfForm" >

                                        <div class="selector" id="tabs">
                                            <ul>
                                                <li><a href="#tabs-1">General</a></li>
                                                <li><a href="#tabs-2">Country, MCC, Transaction & Currency </a></li>
                                            </ul>

                                            <!--  ////////////////////////      Tab Number1        /////////////////////                            -->
                                            <div id="tabs-1" >

                                                <table  cellpadding="0" cellspacing="10">
                                                    <tbody>

                                                        <tr>
                                                            <td width ="280px;">Risk Profile Code</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<input type="text" name="riskProfCode" class="inputfield-mandatory" maxlength="8" value='${riskProfBean.riskProfCode}' <c:if  test="${readOnly=='true'}"> readonly="readonly" </c:if> <c:if  test="${readOnly!='true'}"></c:if>></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Description</td>
                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="64" value='${riskProfBean.description}'></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr>
                                                            <td width ="280px;">Status</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<select  name="selectstatuscode"  class="inputfield-mandatory">
                                                                    <option value="" selected>--SELECT--</option>
                                                                    <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                        <c:if test="${riskProfBean.status==status.statusCode}">
                                                                            <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                        </c:if>
                                                                        <c:if test="${riskProfBean.status!=status.statusCode}">
                                                                            <option value="${status.statusCode}">${status.description}</option>

                                                                        </c:if>

                                                                    </c:forEach>
                                                                </select></td>
                                                            <td></td>
                                                        </tr>

                                                        <tr>
                                                            <td width ="280px;">Profile Type</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<select  name="selectRiskProfType"  class="inputfield-mandatory" onchange="profileTypeSet('${"add"}','${riskProfBean.profileType}')">
                                                                    <option value="" selected>--SELECT--</option>
                                                                    <c:forEach var="profType" items="${sessionScope.SessionObject.riskTypeList}">

                                                                        <c:if test="${riskProfBean.profileType==profType.riskProfTypeCode}">
                                                                            <option value="${profType.riskProfTypeCode}" selected="true" >${profType.description}</option>
                                                                        </c:if>
                                                                        <c:if test="${riskProfBean.profileType!=profType.riskProfTypeCode}">
                                                                            <option value="${profType.riskProfTypeCode}">${profType.description}</option>

                                                                        </c:if>


                                                                    </c:forEach>
                                                                </select></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr>

                                                        </tr>
                                                        <!--                                </tbody>-->
                                                        <!--                            </table>-->

                                                        <c:if test="${profileType=='card'}">
                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Account Risk Profile</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<select  name="selectAccountProfType"  class="inputfield-mandatory" onchange="velueSet('${"add"}')">
                                                                        <option value="" selected>--SELECT--</option>
                                                                        <c:forEach var="riskType" items="${selectedRiskProfList}">

                                                                            <c:if test="${riskProfBean.accountProfCode==riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}" selected="true" >${riskType.description}</option>
                                                                            </c:if>
                                                                            <c:if test="${riskProfBean.accountProfCode!=riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}">${riskType.description}</option>

                                                                            </c:if>


                                                                        </c:forEach>
                                                                    </select></td>
                                                                <td></td>
                                                            </tr>
                                                            <!--                                    </tbody>-->

                                                            <!--                                </table>-->
                                                        </c:if>

                                                        <c:if test="${profileType=='account'}">
                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Customer Risk Profile</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<select  name="selectCustomerProfType"  class="inputfield-mandatory" onchange="velueSet('${"add"}')">
                                                                        <option value="" selected>--SELECT--</option>
                                                                        <c:forEach var="riskType" items="${selectedRiskProfList}">

                                                                            <c:if test="${riskProfBean.customerProfCode==riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}" selected="true" >${riskType.description}</option>
                                                                            </c:if>
                                                                            <c:if test="${riskProfBean.customerProfCode!=riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}">${riskType.description}</option>

                                                                            </c:if>


                                                                        </c:forEach>
                                                                    </select></td>
                                                                <td></td>
                                                            </tr>
                                                            <!--                                    </tbody>-->

                                                            <!--                                </table>-->
                                                        </c:if>
                                                        <c:if test="${profileType=='terminal'}">
                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Merchant Risk Profile</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<select  name="selectMerchantProfType"  class="inputfield-mandatory" onchange="velueSet('${"add"}')">
                                                                        <option value="" selected>--SELECT--</option>
                                                                        <c:forEach var="riskType" items="${selectedRiskProfList}">

                                                                            <c:if test="${riskProfBean.merchantProfCode==riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}" selected="true" >${riskType.description}</option>
                                                                            </c:if>
                                                                            <c:if test="${riskProfBean.merchantProfCode!=riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}">${riskType.description}</option>

                                                                            </c:if>


                                                                        </c:forEach>
                                                                    </select></td>
                                                                <td></td>
                                                            </tr>
                                                            <!--                                    </tbody>-->

                                                            <!--                                </table>-->
                                                        </c:if>

                                                        <c:if test="${otherRecord=='true'}">

                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Period (days)</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="peroid" class="inputfield-mandatory" maxlength="4"  value='${riskProfBean.peroid}' onchange="checkPeriod('${riskProfBean.peroid}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Minimum Single Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="minimumSingleTxnLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.minSingleTxnLimit}' onchange="checkMinimumSingleTxnLimit('${riskProfBean.minSingleTxnLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Maximum Single Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxSingleTxnLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxSingleTxnLimit}' onchange="checkMaxSingleTxnLimit('${riskProfBean.maxSingleTxnLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Minimum Single Cash Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="minSingleCashLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.minSingleCashLimit}' onchange="checkMinSingleCashLimit('${riskProfBean.minSingleCashLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Maximum Single Cash Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxSingleCashLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxSingleCashLimit}' onchange="checkMaxSingleCashLimit('${riskProfBean.maxSingleCashLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Transaction Count</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxTxnCount" class="inputfield-mandatory" maxlength="4" value='${riskProfBean.maxTxnCount}' onchange="checkMaxTxnCount('${riskProfBean.maxTxnCount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Cash Transaction Count</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxCashCount" class="inputfield-mandatory" maxlength="4" value='${riskProfBean.maxCashCount}' onchange="checkMaxCashCount('${riskProfBean.maxCashCount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Total Transaction Amount</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxTotalTxnAmount" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxTotTxnAmount}' onchange="checkMaxTotalTxnAmount('${riskProfBean.maxTotTxnAmount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Total Cash Transaction Amount</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxTotalCashAmount" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxTotCashTxnAmount}' onchange="checkMaxTotalCashAmount('${riskProfBean.maxTotCashTxnAmount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <c:if test="${profileType=='card'||profileType=='account'||profileType=='customer'}">

                                                                <tr>
                                                                    <td width ="280px;">Extra Authorization Limit</td>
                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="extraAuthLimit" class="inputfield-mandatory" maxlength="5" value='${riskProfBean.extraAuthLimit}' onchange="checkExtraAuthLimit('${riskProfBean.extraAuthLimit}')"></td>
                                                                    <td>%</td>
                                                                    <td></td>
                                                                </tr>

                                                            </c:if>
                                                            <c:if test="${profileType=='card'}">
                                                                <tr>
                                                                    <td width ="280px;">Maximum Pin Count</td>
                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxPinCount" class="inputfield-mandatory" maxlength="2" value='${riskProfBean.maxPinCount}'></td>
                                                                    <td></td>
                                                                </tr>
                                                            </c:if>


                                                        </tbody>                                  
                                                    </table>



                                                </c:if>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr><td width ="280px;"></td>
                                                        <td><input type="button" name="saveNext" id="saveNext"  style="width: 100px; height: 30px;" onclick="saveAndNext()" value="Save & Next" disabled="">
                                                            <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeReset()" value="Reset">
                                                            <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel()" value="Cancel">
                                                        </td>
                                                    </tr>
                                                </table>

                                            </div>
                                            <div id="tabs-2">

                                                <c:if test="${otherRecord=='true'}">

                                                    <script type="text/javascript">
                                                        document.addRiskProfForm.saveNext.disabled = false;
                                                    </script>

                                                    <table cellpadding="0" cellspacing="10"  >

                                                        <tr>
                                                            <td colspan="0">Select Countries <B> <c:out value="${country}"/></B></td>
                                                        </tr>

                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Countries&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                <select name="countryList" style="width: 200px"  id=in0 multiple="multiple"  size=10>
                                                                    <c:forEach  var="country" items="${notAssignedCountryList}">
                                                                        <option value="${country.alphaSecond}" >${country.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout0() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein0() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout0() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin0() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Countries</b></h4>
                                                                <select name="assignCountryList" style="width: 200px" id=out0 multiple="multiple"   size=10>
                                                                    <c:forEach  var="country" items="${assignedCountryList}">
                                                                        <option value="${country.alphaSecond}" >${country.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>


                                                    <table cellpadding="0" cellspacing="10"  >
                                                        <tr>
                                                            <td colspan="0">Select Merchant Categories <B> <c:out value="${merchant}"/></B></td>
                                                        </tr>

                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Merchant Categories</b></h4>
                                                                <select name="mccList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                    <c:forEach  var="merchant" items="${notAssignedMerchantCategoryList}">
                                                                        <option value="${merchant.mCCCode}" >${merchant.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Merchant Categories</b></h4>
                                                                <select name="assignMcclist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                                    <c:forEach  var="merchant" items="${assignedMerchantCategoryList}">
                                                                        <option value="${merchant.mCCCode}" >${merchant.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>


                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr>
                                                            <td colspan="0">Select Transaction Types <B> <c:out value="${txn}"/></B></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Transaction Types&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                <select name="txnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                                    <c:forEach  var="txn" items="${notAssignedTypeList}">
                                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout2() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein2() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout2() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin2() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Transaction Types</b></h4>
                                                                <select name="assignTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>
                                                                    <c:forEach  var="txn" items="${assignedTypeList}">
                                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>

                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr>
                                                            <td colspan="0">Select Currency Types <B> <c:out value="${currency}"/></B></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Currency Types&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                <select name="currencyList" style="width: 200px"  id=in3 multiple="multiple"  size=10>
                                                                    <c:forEach  var="currency" items="${notAssignedCurrencyList}">
                                                                        <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout3() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein3() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout3() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin3() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Currency Types</b></h4>
                                                                <select name="assignCurrencyList" style="width: 200px" id=out3 multiple="multiple"   size=10>
                                                                    <c:forEach  var="currency" items="${assignedCurrencyList}">
                                                                        <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>

                                                    <c:if test="${bin == 'bin'}">
                                                        <table cellpadding="0" cellspacing="10">
                                                            <tr>
                                                                <td colspan="0">Select Bin<B> </B></td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <h4><b>Allowed Bin&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                    <select name="binList" style="width: 200px"  id=in4 multiple="multiple"  size=10>
                                                                        <c:forEach  var="bin" items="${notAssignedBinList}">
                                                                            <option value="${bin.binCode}" >${bin.description}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td align="center" >
                                                                    <input type=button value=">" style="width: 50px;" onclick=moveout4() class="buttonsize"><br>
                                                                    <input type=button value="<" style="width: 50px;" onclick=movein4() class="buttonsize"><br>
                                                                    <input type=button value=">>" style="width: 75px;" onclick=moveallout4() class="buttonsize"><br>
                                                                    <input type=button value="<<" style="width: 75px;" onclick=moveallin4() class="buttonsize">
                                                                </td>
                                                                <td>
                                                                    <h4><b>Blocked Bin</b></h4>
                                                                    <select name="assignBinList" style="width: 200px" id=out4 multiple="multiple"   size=10>
                                                                        <c:forEach  var="bin" items="${assignedBinList}">
                                                                            <option value="${bin.binCode}" >${bin.description}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </c:if>

                                                    <table cellpadding="0" cellspacing="10">

                                                        <tr><td width ="100px;"></td>
                                                            <td><c:if test="${bin == 'bin' }"><input type="button" name="add"  style="width: 100px; height: 30px;" onclick="selectAllAssignList(assignCountryList,assignMcclist,assignTxnTypeList,assignCurrencyList,assignBinList)" value="Save"></c:if>
                                                                <c:if test="${bin != 'bin' }"><input type="button" name="add"  style="width: 100px; height: 30px;" onclick="selectAllAssignListNotBin(assignCountryList,assignMcclist,assignTxnTypeList,assignCurrencyList)" value="Save"></c:if>
                                                                    <input type="button" value="Back " name="backtab" style="width: 100px; height: 30px;" class="previoustab">
                                                                    <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel()" value="Cancel">
                                                                </td>
                                                            </tr>

                                                        </table>
                                                </c:if>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                                <!--         -----------------------------------End Add Area  -------------------------                      -->

                                <!--         ---------------------------Start Update Area  ----------------------------                      -->

                                <c:if test="${operationtype=='update'}"> 
                                    <form action="" method="POST" name="updateRiskProfForm" >

                                        <div class="selector" id="tabs">
                                            <ul>
                                                <li><a href="#tabs-1">General </a></li>
                                                <li><a href="#tabs-2">Country, MCC, Transaction & Currency</a></li>
                                            </ul>

                                            <!--  ////////////////////////      Tab Number1        /////////////////////                            -->
                                            <div id="tabs-1" >

                                                <table  cellpadding="0" cellspacing="10">
                                                    <tbody>

                                                        <tr>
                                                            <td width ="280px;">Risk Profile Code</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<input type="text" name="riskProfCode" class="inputfield-mandatory" maxlength="8" value='${riskProfBean.riskProfCode}'></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr>
                                                            <td width ="280px;">Description</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="64" value='${riskProfBean.description}'></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr>
                                                            <td width ="280px;">Status</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<select  name="selectstatuscode"  class="inputfield-mandatory">
                                                                    <option value="" selected>--SELECT--</option>
                                                                    <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                        <c:if test="${riskProfBean.status==status.statusCode}">
                                                                            <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                        </c:if>
                                                                        <c:if test="${riskProfBean.status!=status.statusCode}">
                                                                            <option value="${status.statusCode}">${status.description}</option>

                                                                        </c:if>

                                                                    </c:forEach>
                                                                </select></td>
                                                            <td></td>
                                                        </tr>

                                                        <tr>
                                                            <td width ="280px;">Profile Type</td>
                                                            <td><font style="color: red;">*</font>&nbsp;<select  name="selectRiskProfType"  class="inputfield-mandatory" onchange="profileTypeSet('${"update"}','${riskProfBean.profileType}')">
                                                                    <option value="" selected>--SELECT--</option>
                                                                    <c:forEach var="profType" items="${sessionScope.SessionObject.riskTypeList}">

                                                                        <c:if test="${riskProfBean.profileType==profType.riskProfTypeCode}">
                                                                            <option value="${profType.riskProfTypeCode}" selected="true" >${profType.description}</option>
                                                                        </c:if>
                                                                        <c:if test="${riskProfBean.profileType!=profType.riskProfTypeCode}">
                                                                            <option value="${profType.riskProfTypeCode}">${profType.description}</option>

                                                                        </c:if>


                                                                    </c:forEach>
                                                                </select></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr>

                                                        </tr>
                                                        <!--                                </tbody>-->
                                                        <!--                            </table>-->

                                                        <c:if test="${profileType=='card'}">
                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Account Risk Profile</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<select  name="selectAccountProfType"  class="inputfield-mandatory" onchange="velueSet('${"update"}')">
                                                                        <option value="" selected>--SELECT--</option>
                                                                        <c:forEach var="riskType" items="${selectedRiskProfList}">

                                                                            <c:if test="${riskProfBean.accountProfCode==riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}" selected="true" >${riskType.description}</option>
                                                                            </c:if>
                                                                            <c:if test="${riskProfBean.accountProfCode!=riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}">${riskType.description}</option>

                                                                            </c:if>


                                                                        </c:forEach>
                                                                    </select></td>
                                                                <td></td>
                                                            </tr>
                                                            <!--                                    </tbody>-->

                                                            <!--                                </table>-->
                                                        </c:if>

                                                        <c:if test="${profileType=='account'}">
                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Customer Risk Profile</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<select  name="selectCustomerProfType"  class="inputfield-mandatory" onchange="velueSet('${"update"}')">
                                                                        <option value="" selected>--SELECT--</option>
                                                                        <c:forEach var="riskType" items="${selectedRiskProfList}">

                                                                            <c:if test="${riskProfBean.customerProfCode==riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}" selected="true" >${riskType.description}</option>
                                                                            </c:if>
                                                                            <c:if test="${riskProfBean.customerProfCode!=riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}">${riskType.description}</option>

                                                                            </c:if>


                                                                        </c:forEach>
                                                                    </select></td>
                                                                <td></td>
                                                            </tr>
                                                            <!--                                    </tbody>-->

                                                            <!--                                </table>-->
                                                        </c:if>

                                                        <c:if test="${profileType=='terminal'}">
                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Merchant Risk Profile</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<select   name="selectMerchantProfType"  class="inputfield-mandatory" onchange="velueSet('${"update"}')">
                                                                        <option value="" selected>--SELECT--</option>
                                                                        <c:forEach var="riskType" items="${selectedRiskProfList}">

                                                                            <c:if test="${riskProfBean.merchantProfCode==riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}" selected="true" >${riskType.description}</option>
                                                                            </c:if>
                                                                            <c:if test="${riskProfBean.merchantProfCode!=riskType.riskProfCode}">
                                                                                <option value="${riskType.riskProfCode}">${riskType.description}</option>

                                                                            </c:if>


                                                                        </c:forEach>
                                                                    </select></td>
                                                                <td></td>
                                                            </tr>
                                                            <!--                                    </tbody>-->

                                                            <!--                                </table>-->
                                                        </c:if>

                                                        <c:if test="${otherRecord=='true'}">

                                                            <!--                                <table>-->
                                                            <!--                                    <tbody>-->
                                                            <tr>
                                                                <td width ="280px;">Period (days)</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="peroid" class="inputfield-mandatory" maxlength="4" value='${riskProfBean.peroid}' onchange="checkUpPeriod('${riskBean.peroid}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Minimum Single Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="minimumSingleTxnLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.minSingleTxnLimit}' onchange="checkUpMinimumSingleTxnLimit('${riskBean.minSingleTxnLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Maximum Single Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxSingleTxnLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxSingleTxnLimit}' onchange="checkUpMaxSingleTxnLimit('${riskBean.maxSingleTxnLimit}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr>
                                                                <td width ="280px;">Minimum Single Cash Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="minSingleCashLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.minSingleCashLimit}' onchange="checkUpMinSingleCashLimit('${riskBean.minSingleCashLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td width ="280px;">Maximum Single Cash Transaction Limit</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxSingleCashLimit" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxSingleCashLimit}' onchange="checkUpMaxSingleCashLimit('${riskBean.maxSingleCashLimit}')"></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Transaction Count</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxTxnCount" class="inputfield-mandatory" maxlength="4" value='${riskProfBean.maxTxnCount}' onchange="checkUpMaxTxnCount('${riskBean.maxTxnCount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Cash Transaction Count</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxCashCount" class="inputfield-mandatory" maxlength="4" value='${riskProfBean.maxCashCount}' onchange="checkUpMaxCashCount('${riskBean.maxCashCount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Total Transaction Amount</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxTotalTxnAmount" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxTotTxnAmount}' onchange="checkUpMaxTotalTxnAmount('${riskBean.maxTotTxnAmount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <tr style="display: none">
                                                                <td width ="280px;">Maximum Total Cash Transaction Amount</td>
                                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxTotalCashAmount" class="inputfield-mandatory" maxlength="10" value='${riskProfBean.maxTotCashTxnAmount}' onchange="checkUpMaxTotalCashAmount('${riskBean.maxTotCashTxnAmount}')"></td>
                                                                <td></td>
                                                            </tr>

                                                            <c:if test="${profileType=='card'||profileType=='account'||profileType=='customer'}">

                                                                <tr>
                                                                    <td width ="280px;">Extra Authorization Limit</td>
                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="extraAuthLimit" class="inputfield-mandatory" maxlength="5" value='${riskProfBean.extraAuthLimit}' onchange="checkUpExtraAuthLimit('${riskBean.extraAuthLimit}')"></td>
                                                                    <td>%</td>
                                                                    <td></td>
                                                                </tr>

                                                            </c:if>

                                                            <c:if test="${profileType=='card'}">
                                                                <tr>
                                                                    <td width ="280px;">Maximum Pin Count</td>
                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="maxPinCount" class="inputfield-mandatory" maxlength="2" value='${riskProfBean.maxPinCount}'></td>
                                                                    <td></td>
                                                                </tr>
                                                            </c:if>
                                                            <tr>  
                                                                <td><input type="hidden" name="oldValue" value='${riskProfBean.oldValue}' hidden="hidden"></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>

                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr><td width ="280px;"></td>
                                                            <td><input type="button" name="saveNext" id="saveNext"  style="width: 100px; height: 30px;" onclick="updateAndNext()" value="Save & Next" >
                                                                <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeUpdateReset('${riskProfBean.riskProfCode}')" value="Reset">
                                                                <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel()" value="Cancel">
                                                            </td>
                                                        </tr>
                                                    </table>

                                                </div>
                                                <!--    /////////////////    Tab Number2         ///////////////////// -->
                                                <div id="tabs-2">
                                                    <table cellpadding="0" cellspacing="10"  >
                                                        <tr>
                                                            <td colspan="0">Select Countries <B> <c:out value="${country}"/></B></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Countries&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                <select name="countryList" style="width: 200px"  id=in0 multiple="multiple"  size="10">
                                                                    <c:forEach  var="country" items="${notAssignedCountryList}">
                                                                        <option value="${country.alphaSecond}" >${country.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout0() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein0() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout0() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin0() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Countries</b></h4>
                                                                <select name="assignCountryList" style="width: 200px" id=out0 multiple="multiple"   size="10">
                                                                    <c:forEach  var="country" items="${assignedCountryList}">
                                                                        <option value="${country.alphaSecond}" >${country.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>


                                                    <table cellpadding="0" cellspacing="10"  >
                                                        <tr>
                                                            <td colspan="0">Select Merchant Categories <B> <c:out value="${merchant}"/></B></td>
                                                        </tr>

                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Merchant Categories</b></h4>
                                                                <select name="mccList" style="width: 200px"  id=in multiple="multiple"  size="10">
                                                                    <c:forEach  var="merchant" items="${notAssignedMerchantCategoryList}">
                                                                        <option value="${merchant.mCCCode}" >${merchant.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Merchant Categories</b></h4>
                                                                <select name="assignMcclist" style="width: 200px" id=out multiple="multiple"   size="10">
                                                                    <c:forEach  var="merchant" items="${assignedMerchantCategoryList}">
                                                                        <option value="${merchant.mCCCode}" >${merchant.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>


                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr>
                                                            <td colspan="0">Select Transaction Types <B> <c:out value="${txn}"/></B></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Transaction Types&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                <select name="txnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                                    <c:forEach  var="txn" items="${notAssignedTypeList}">
                                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout2() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein2() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout2() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin2() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Transaction Types</b></h4>
                                                                <select name="assignTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>
                                                                    <c:forEach  var="txn" items="${assignedTypeList}">
                                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table cellpadding="0" cellspacing="10">
                                                        <tr>
                                                            <td colspan="0">Select Currency Types <B> <c:out value="${currency}"/></B></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <h4><b>Allowed Currency Types&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                <select name="currencyList" style="width: 200px"  id=in3 multiple="multiple"  size=10>
                                                                    <c:forEach  var="currency" items="${notAssignedCurrencyList}">
                                                                        <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td align="center" >
                                                                <input type=button value=">" style="width: 50px;" onclick=moveout3() class="buttonsize"><br>
                                                                <input type=button value="<" style="width: 50px;" onclick=movein3() class="buttonsize"><br>
                                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout3() class="buttonsize"><br>
                                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin3() class="buttonsize">
                                                            </td>
                                                            <td>
                                                                <h4><b>Blocked Currency Types</b></h4>
                                                                <select name="assignCurrencyList" style="width: 200px" id=out3 multiple="multiple"   size=10>
                                                                    <c:forEach  var="currency" items="${assignedCurrencyList}">
                                                                        <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>

                                                    <c:if test="${bin == 'bin'}">
                                                        <table cellpadding="0" cellspacing="10">
                                                            <tr>
                                                                <td colspan="0">Select Bin <B> </B></td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <h4><b>Allowed Bin&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                                    <select name="binList" style="width: 200px"  id=in4 multiple="multiple"  size=10>
                                                                        <c:forEach  var="bin" items="${notAssignedBinList}">
                                                                            <option value="${bin.binCode}" >${bin.description}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td align="center" >
                                                                    <input type=button value=">" style="width: 50px;" onclick=moveout4() class="buttonsize"><br>
                                                                    <input type=button value="<" style="width: 50px;" onclick=movein4() class="buttonsize"><br>
                                                                    <input type=button value=">>" style="width: 75px;" onclick=moveallout4() class="buttonsize"><br>
                                                                    <input type=button value="<<" style="width: 75px;" onclick=moveallin4() class="buttonsize">
                                                                </td>
                                                                <td>
                                                                    <h4><b>Blocked Bin</b></h4>
                                                                    <select name="assignBinList" style="width: 200px" id=out4 multiple="multiple"   size=10>
                                                                        <c:forEach  var="bin" items="${assignedBinList}">
                                                                            <option value="${bin.binCode}" >${bin.description}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </c:if>


                                                </c:if>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr><td  width ="100px;"></td>
                                                        <td><c:if test="${bin == 'bin' }"><input type="button" name="update"  style="width: 100px; height: 30px;" onclick="selectAllAssignListUpdate('${assignedCountryList}','${assignMcclist}','${assignTxnTypeList}','${assignCurrencyList}','${assignBinList}')" value="Save"></c:if>
                                                        <td><c:if test="${bin != 'bin' }"><input type="button" name="update"  style="width: 100px; height: 30px;" onclick="selectAllAssignListUpdateNotBin('${assignCountryList}','${assignMcclist}','${assignTxnTypeList}','${assignCurrencyList}')" value="Save"></c:if>
                                                                <input type="button" value="Back " name="backtab" style="width: 100px; height: 30px;" class="previoustab">
                                                                <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel()" value="Cancel">
                                                            </td>
                                                        </tr>

                                                    </table>
                                                </div>
                                            </div>
                                        </form>
                                </c:if>

                                <!--   --------------------------------------End Update Area  -------------------------                      -->
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
