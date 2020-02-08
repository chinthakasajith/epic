<%-- 
    Document   : acquireterminalallocation
    Created on : Dec 11, 2012, 3:50:15 PM
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
            

            function invokeAdd(tId,mId,selectBoxTxn,selectBoxnaTxn)
            {
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                }
                 for (var i = 0; i < selectBoxnaTxn.options.length; i++) {
                    selectBoxnaTxn.options[i].selected = true;
                }

                document.addterminalform.action="${pageContext.request.contextPath}/AddCallcenterTerminalServlet?tId="+tId+"&mId="+mId;
                document.addterminalform.submit();

            }
            
            function invokeCancel(id)
            {

                window.location = "${pageContext.request.contextPath}/LoadCallCenterMerchantSearchServlet?id="+id;

            }
           
         
          

    

        </script>

<script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_TERMINAL_ALLOCATION%>'                                  
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


                                <%-- -------------------------Start Add Form -------------------------------- --%>

                                <form method="POST" action="" name="addterminalform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> ${errorMsg}</font> </td>
                                            <td><font color="green"> ${successMsg}</font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td style="width: 200px;">Terminal ID(TID)</td>
                                                <td> <b><font color="#000000"> ${terminalId}</font></b></td>
                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td style="width: 200px;">Merchant ID(MID)</td>
                                                <td> <b><font color="#000000"> ${merchantId}</font></b></td>
                                                <td></td>
                                            </tr> 

                                            <tr>
                                                <td style="width: 200px;">Mcc</td>
                                                <td>
                                                    <select name="selectMcc" style="width: 150px;">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="mcc" items="${merchantCategoryList}">
                                                            <c:if test="${terminalBean.mcc==mcc.mCCCode}">
                                                                <option value="${mcc.mCCCode}" selected>${mcc.description}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.mcc != mcc.mCCCode}">
                                                                <option value="${mcc.mCCCode}" >${mcc.description}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Currency</td>
                                                <td>
                                                    <select name="selectCurrency" style="width: 150px;">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="curr" items="${currencyList}">
                                                            <c:if test="${terminalBean.currency==curr.currencyCode}">
                                                                <option value="${curr.currencyCode}" selected>${curr.currencyDes}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.currency != curr.currencyCode}">
                                                                <option value="${curr.currencyCode}" >${curr.currencyDes}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Risk Profile</td>
                                                <td>
                                                    <select name="selectRiskProfile" style="width: 150px;">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="risk" items="${riskProfileList}">
                                                            <c:if test="${terminalBean.riskProfileCode==risk.riskProfCode}">
                                                                <option value="${risk.riskProfCode}" selected>${risk.description}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.riskProfileCode != risk.riskProfCode}">
                                                                <option value="${risk.riskProfCode}" >${risk.description}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td colspan="0">Select Transaction Types <B> <c:out value="${txn}"/></B></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <h4><b>All Transaction Types</b></h4>
                                                <select name="txnTypeList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                    <c:forEach  var="txn" items="${notAssignTypeList}">
                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
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
                                                <h4><b>Assigned Transaction Types</b></h4>
                                                <select name="assignTxnTypeList" style="width: 200px" id=out multiple="multiple"   size=10>
                                                     <c:forEach  var="txn" items="${assignedTypeList}">
                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td></td>
                                                <td colspan="2">
                                                    <input type="submit" value="Allocate" name="allocate" style="width: 100px" onclick="invokeAdd('${terminalId}','${merchantId}',assignTxnTypeList,txnTypeList)"/>
                                                    <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invokeCancel('${terminalId}')"/></td>
                                            </tr>
                                        </tbody>
                                    </table>




                                </form>




                                <%--  ----------------------------------End ADD Form ------------------------- --%>


                            </div>
                        </div>



                    </div>
                    <div class="clearer"><span></span></div>
                </div>

            </div>
            <div class="footer"><jsp:include page="/footer.jsp"/></div>
        </div>
    </body>
</html>
