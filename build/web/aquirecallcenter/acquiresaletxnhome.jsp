<%-- 
    Document   : acquiresaletxnhome
    Created on : Apr 17, 2013, 2:05:28 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>


        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
           
            
            function invokeTxnInfo()
            {

                document.saleTxnForm.action="${pageContext.request.contextPath}/LoadSaleTxnInfoServlet";
                document.saleTxnForm.submit();

            }
            
            function invokeProceed()
            {
                
                answer = confirm("Are you sure you want to Proceed this Transaction?")

                if(answer != 0){
                    document.saleTxnForm.action="${pageContext.request.contextPath}/ProceedCallCenterSaleTxnServlet";
                    document.saleTxnForm.submit();
                }
                else{
                    
                    
                }
            }
            
            function invokeReset(ele){

                tags = ele.getElementsByTagName('input');
                
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                    }
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                        tags }
                }
                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';
                
                document.getElementById('errorMsg2').innerHTML = '';
                document.getElementById('successMsg2').innerHTML = '';

            }
           
            
            function invokeTxnView(value,tid,mid){
                $.post("${pageContext.request.contextPath}/ViewSaleTxnServlet", {txnId:value,tid:tid,mid:mid},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/mtmm/manualtxn/saletxnview.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }

                });
            }
            
            function buttonDisable(){
                 
                var e = document.getElementById("selectRiskProfType");
                var strUser = e.options[e.selectedIndex].value;
              
                if(strUser!=""){
                    
                    document.addRiskProfForm.saveNext.disabled = false;
                   
                } 
            }
            
        </script>  
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_SALE_TRANSACTION%>'                                  
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

                <div class="content" >
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
                                        <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                            <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                        </td>
                                    </tr>
                                </table>


                                <form action="" method="POST" name="saleTxnForm" >

                                    <table cellpadding="0" cellspacing="10" >

                                        <tbody>


                                            <tr>
                                                <td width ="200px;">Card Number</td>
                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="cardNumber" class="inputfield-mandatory" maxlength="16" value='${saleTxnBean.cardNumber}'></td>
                                            </tr>

                                            <tr>
                                                <td>Card Type</td>
                                                <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="cardType"  class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>

                                                        <c:forEach var="card" items="${cardTypeList}">

                                                            <c:if test="${saleTxnBean.cardType==card.key}">
                                                                <option value="${card.key}" selected="true" >${card.value}</option>
                                                            </c:if>
                                                            <c:if test="${saleTxnBean.cardType!=card.key}">
                                                                <option value="${card.key}">${card.value}</option>
                                                            </c:if>

                                                        </c:forEach>

                                                    </select></td>
                                            </tr>

                                            <tr>
                                                <td width ="200px;">Expiry Date</td>

                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="expiryDate" class="inputfield-mandatory" maxlength="4" value='${saleTxnBean.expiryDate}'></td>
                                            </tr>

                                            <tr>
                                                <td width ="200px;">CVV Value</td>
                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="cvvValue" class="inputfield-mandatory" maxlength="3" value='${saleTxnBean.cvvValue}'></td>
                                            </tr>

                                            <tr>
                                                <td>Currency Type</td>
                                                <td><font style="color: red;">*</font>&nbsp;<select name="currencyType" class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="curr" items="${currencyList}">

                                                            <c:if test="${saleTxnBean.currencyType==curr.key}">
                                                                <option value="${curr.key}" selected="true" >${curr.value}</option>
                                                            </c:if>
                                                            <c:if test="${saleTxnBean.currencyType!=curr.key}">
                                                                <option value="${curr.key}">${curr.value}</option>
                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td width ="200px;">Transaction Amount</td>
                                                <td><font style="color: red;">*</font>&nbsp;<input type="text" name="txnAmount" class="inputfield-mandatory" maxlength="12" value='${saleTxnBean.txnAmount}'></td>
                                            </tr>

                                        </tbody>
                                    </table>

                                    <table  cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td width ="200px;"></td>
                                                <td></td>
                                                <td><input type="submit" style="width: 100px" name="Process" value="Process" onclick="invokeProceed()" /></td>
                                                <td>  <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset(this.form)"/></td>

                                            </tr>
                                        </tbody>
                                    </table>
                                            
                                    <br/><br/>

                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><label id="errorMsg2"><c:out value="${errorMsg2}"></c:out></label></b></font>
                                                <font color="green"><b><label id ="successMsg2"><c:out value="${successMsg2}"></c:out></label></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <br/><br/>

                                    <fieldset style="width: 450px" >
                                    <table cellpadding="0" cellspacing="10" >

                                        <tbody>

                                            <tr>
                                                <td  width ="200px;">RRN</td>
                                                <td><font style="color: red;"> </font>&nbsp;<input type="text" readonly name="rrn" class="inputfield-mandatory" maxlength="20" value='${saleTxnBean.rrn}'></td>
                                            </tr>

                                            <tr>
                                                <td  width ="200px;">Auth Code</td>
                                                <td><font style="color: red;"> </font>&nbsp;<input type="text" readonly name="authCode" class="inputfield-Description-mandatory" maxlength="50" value='${saleTxnBean.authCode}'></td>
                                            </tr>

                                            <tr>
                                                <td  width ="200px;">Responce Code</td>
                                                <td><font style="color: red;"> </font>&nbsp;<input type="text" readonly name="responceCode" class="inputfield-Description-mandatory" maxlength="50" value='${saleTxnBean.responceCode}'></td>
                                            </tr>

                                        </tbody>
                                    </table>
                                    </fieldset>
                                

                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                <br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Transaction ID</th>
                                            <th>CVV</th>
                                            <th>Currency Type</th>
                                            <th>Amount</th>
                                            <th>Auth Code</th>
                                            <th>Status</th>

                                            <th>View</th>

                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="sale" items="${saleTxnList}">
                                            <tr>

                                                <td >${sale.txnID}</td>
                                                <td >${sale.cvvValue}</td>
                                                <td >${sale.currencyTypeDes}</td>
                                                <td >${sale.txnAmount}</td>
                                                <td >${sale.authCode}</td>
                                                <td >${sale.statusDes}</td>

                                                <td  ><a href='#' onclick="invokeTxnView('${sale.txnID}','${sale.terminalID}','${sale.merchantID}')">View</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>     
</form>
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