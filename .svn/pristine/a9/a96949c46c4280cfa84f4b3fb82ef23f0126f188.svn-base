<%-- 
    Document   : acquiresettlementtxnhome
    Created on : Apr 18, 2013, 10:42:37 AM
    Author     : nalin
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
            
            function invokeGO()
            {
              
                document.settlementTxnForm.action="${pageContext.request.contextPath}/LoadSettlementMerchantDetailsServlet";
                document.settlementTxnForm.submit();

            }
            
            function invokeTxnInfo()
            {

                document.settlementTxnForm.action="${pageContext.request.contextPath}/LoadVoidTxnInfoServlet";
                document.settlementTxnForm.submit();

            }
            
            function invokeVoid(txnId)
            {
                
                answer = confirm("Are you sure you want to Proceed this Transaction?")

                if(answer != 0){
                    document.settlementTxnForm.action="${pageContext.request.contextPath}/ProceedVoidTxnServlet?txnId="+txnId;
                    document.settlementTxnForm.submit();
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

            }
           
            function invokeCheckAll(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = true ;
                
            }
            
            function invokeUnCheckAll(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = false ;
            
            }
            
            function invokeSettleTxn()
            {

                
                answer = confirm("Are you sure you want to continue this process?")
                    
                if(answer !=0){
                    document.settlementTxnForm.action="${pageContext.request.contextPath}/ProceedCallCenterSettlementTxnServlet";
                    document.settlementTxnForm.submit();
                }
                
            }
        </script>  
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_SETTLEMENT_TRANSACTION%>'                                  
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


                                <form method="POST" name="settlementTxnForm" >
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
                                                <td><font style="color: red;"> </font>&nbsp;<input type="text" readonly name="rrn" class="inputfield-mandatory" maxlength="20" value='${settlememtBean.rrn}'></td>
                                            </tr>

                                            <tr>
                                                <td  width ="200px;">Auth Code</td>
                                                <td><font style="color: red;"> </font>&nbsp;<input type="text" readonly name="authCode" class="inputfield-Description-mandatory" maxlength="50" value='${settlememtBean.authCode}'></td>
                                            </tr>

                                            <tr>
                                                <td  width ="200px;">Responce Code</td>
                                                <td><font style="color: red;"> </font>&nbsp;<input type="text" readonly name="responceCode" class="inputfield-Description-mandatory" maxlength="50" value='${settlememtBean.responceCode}'></td>
                                            </tr>
                                            <tr> <td></td><td>  <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset(this.form)"/></td></tr>
                                        </tbody>
                                       
                                    </table>

                                    </fieldset>

                                    <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                    <br></br>

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Transaction ID</th>
                                                <th>CVV</th>
                                                <th>Currency Type</th>
                                                <th>Amount</th>
                                                <th>Auth Code</th>
                                                <th>Status</th>

                                                <th>Select</th>


                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="settle" items="${settlememtList}">
                                                <tr>

                                                    <td >${settle.txnID}</td>
                                                    <td >${settle.cvvValue}</td>
                                                    <td >${settle.currencyTypeDes}</td>
                                                    <td >${settle.txnAmount}</td>
                                                    <td >${settle.authCode}</td>
                                                    <td >${settle.statusDes}</td>

                                                    <td  ><input type="checkbox" name="checkedVelue" value="${settle.txnID}"></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 

                                    <table  cellpadding="0" cellspacing="10">

                                        <tr celspacing="5">
                                            <td style="width: 200px;">
                                            </td>
                                            <td><input type="button" name="checkAll"  style="width: 100px; height: 30px;" onclick="invokeCheckAll(document.settlementTxnForm.checkedVelue)" value="Check All">
                                                <input type="button" name="uncheckAll"  style="width: 100px; height: 30px;" onclick="invokeUnCheckAll(document.settlementTxnForm.checkedVelue)" value="Uncheck All">
                                                <input type="button" name="settled"  style="width: 100px; height: 30px;" onclick="invokeSettleTxn()" value="Process">
                                                <!--                                                <input type="button" name="cancel"  style="width: 100px;height: 30px;" onclick="invokeCancel('card')" value="Cancel">-->
                                            </td>
                                        </tr>

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
