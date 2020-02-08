<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script  type="text/javascript">
            
            function selectAll(selectBox) {
                
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }       
                invokeAdd();
            }
            function invokeAdd()
            {
               
                document.addscorecardform.action="${pageContext.request.contextPath}/AddCardScoreMgtServlet";
                document.addscorecardform.submit();

            }
            function resetBtn(){
                window.location = "${pageContext.request.contextPath}/LoadCardScoreMgtServlet";
            }
            function cardView(value){
                window.location = "${pageContext.request.contextPath}/ViewCardScoreServlet?id="+value;
            }
            function cardUpdateView(value){
                window.location = "${pageContext.request.contextPath}/UpdateCardScoreMgtViewServlet?id="+value;
            }
            
            function selectAllForUpdate(selectBox1,selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }     
                cardUpdate();
            }
            function cardUpdate(){
                document.updatescorecardform.action="${pageContext.request.contextPath}/UpdateCardScoreMgtServlet";
                document.updatescorecardform.submit();
                //                window.location = "${pageContext.request.contextPath}/UpdateCardScoreMgtServlet?id="+value;
            }
            function cardDelete(value){
                        
                answer = confirm("Do you really want to delete  "+value+" Card Score ?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteCardScoreServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCardScoreMgtServlet";
                }
                //                document.adduserroleform.submit();
                        
                //                window.location = "${pageContext.request.contextPath}/DeleteApplicationMgtServlet?id="+value;

            }
        
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SCORECARD%>'                                  
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

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main"  >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" style="height:1000px;">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>            

                                    <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <c:if test="${operationtype=='add'}">
                                    <form method="POST" name="addscorecardform">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Score Card Code</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="scoreCradCode" value="${scoreBean.scoreCardCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Score Card Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="scoreCradName" value="${scoreBean.scoreCardName}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td> 
                                                        <select name="product" style="width: 200px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardTypes" items="${cardTypeList}">
                                                                <c:if test="${scoreBean.product==cardTypes.cardTypeCode}" >
                                                                    <option selected="" value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreBean.product!=cardTypes.cardTypeCode}" >
                                                                    <option  value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">Assign Rules<span class="required">*</span>&nbsp;</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>All Rules</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${ruleList}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="assignsectionlist" style="width: 200px"  id=out multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${asignedRule}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>
                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="minScore" value="${scoreBean.minScoreCard}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="maxScore" value="${scoreBean.maxScoreCard}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status" style="width: 200px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardScoreStatus" items="${requestScope.secList}">
                                                                <c:if test="${cardScoreStatus.statusCode==scoreBean.status}" >
                                                                    <option selected="" value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardScoreStatus.statusCode!=scoreBean.status}" >
                                                                    <option  value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="button" value="Add" onclick="selectAll(assignsectionlist)"  style="width: 100px;"/>&nbsp&nbsp<input type="reset" onclick="resetBtn()" value="Reset" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SCORECARD%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}">
                                    <form method="POST" name="updatescorecardform">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Score Card Code</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input readonly="" style="width: 200px;" type="text" name="scoreCradCode" value="${scoreBean.scoreCardCode}" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Score Card Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="scoreCradName" value="${scoreBean.scoreCardName}" maxlength="32"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="product" style="width: 200px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardTypes" items="${cardTypeList}">
                                                                <c:if test="${scoreBean.product==cardTypes.cardTypeCode}" >
                                                                    <option selected="" value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreBean.product!=cardTypes.cardTypeCode}" >
                                                                    <option  value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">Assign Rules&nbsp;<span class="required">*</span>&nbsp;</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>All Rules</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${ruleList}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="assignsectionlist" style="width: 200px"  id=out multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${asignedRule}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>
                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="minScore" value="${scoreBean.minScoreCard}" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="maxScore" value="${scoreBean.maxScoreCard}" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status" style="width: 200px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardScoreStatus" items="${requestScope.secList}">
                                                                <c:if test="${cardScoreStatus.description==scoreBean.status}" >
                                                                    <option selected="" value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardScoreStatus.description!=scoreBean.status}" >
                                                                    <option  value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="button" value="Update" onclick="selectAllForUpdate(assignsectionlist,notassignsectionlist)"  style="width: 100px;"/>&nbsp&nbsp<input type="reset" onclick="resetBtn()" value="Reset" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SCORECARD%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='defaultUp'}">
                                    <form method="POST" name="updatescorecardform">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Score Card Code</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input readonly="" style="width: 200px;" type="text" name="scoreCradCode" value="${scoreBean.scoreCardCode}" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Score Card Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="scoreCradName" value="${scoreBean.scoreCardName}" maxlength="32"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Product</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="product">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${scoreBean.product=='visa'}" >
                                                                <option selected="" value="visa">VISA</option>
                                                                <option value="master">MASTER</option>
                                                            </c:if>
                                                            <c:if test="${scoreBean.product=='master'}" >
                                                                <option  value="visa">VISA</option>
                                                                <option selected="" value="master">MASTER</option>
                                                            </c:if>
                                                            <c:if test="${scoreBean.product==null}" >
                                                                <option  value="visa">VISA</option>
                                                                <option  value="master">MASTER</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">Assign Rules&nbsp;<span class="required">*</span>&nbsp;</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>All Rules</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${ruleList}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="assignsectionlist" style="width: 200px"  id=out multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${asignedRule}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>
                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="minScore" value="${scoreBean.minScoreCard}" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="maxScore" value="${scoreBean.maxScoreCard}" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status" style="width: 200px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardScoreStatus" items="${requestScope.secList}">
                                                                <c:if test="${cardScoreStatus.statusCode==scoreBean.status}" >
                                                                    <option selected="" value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardScoreStatus.statusCode!=scoreBean.status}" >
                                                                    <option  value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Update" onclick="selectAllForUpdate(assignsectionlist,notassignsectionlist)"  style="width: 100px;"/>&nbsp&nbsp<input type="reset" onclick="resetBtn()" value="Reset" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SCORECARD%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='view'}">
                                    <form method="POST" action="${pageContext.request.contextPath}/LoadCardScoreMgtServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Score Card Code</td>
                                                    <td>&nbsp</td>
                                                    <td><input style="width: 200px;" readonly="" type="text" name="scoreCradCode" value="${scoreBean.scoreCardCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Score Card Name</td>
                                                    <td>&nbsp</td>
                                                    <td><input style="width: 200px;" readonly="" type="text" name="scoreCradName" value="${scoreBean.scoreCardName}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td>&nbsp</td>
                                                    <td><input style="width: 200px;" readonly="" type="text" name="scoreCradName" value="${scoreBean.cardTypeDes}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">Assign Rules</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>All Rules</b></h4>
                                                        <select   name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${ruleList}">
                                                                <option  value="${rule.ruleCode}" >${rule.ruleName}</option>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" > </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${asignedRule}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>

                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Score Card</td>
                                                    <td>&nbsp</td>
                                                    <td><input style="width: 200px;" readonly="" type="text" name="minScore" value="${scoreBean.minScoreCard}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Score Card</td>
                                                    <td>&nbsp</td>
                                                    <td><input style="width: 200px;" readonly="" type="text" name="maxScore" value="${scoreBean.maxScoreCard}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> 
                                                        <input style="width: 200px;" readonly="" type="text" name="maxScore" value="${scoreBean.status}" />
                                                    </td>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Back"  style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SCORECARD%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>

                                <c:if test="${operationtype=='default'}">
                                    <form method="POST" name="addscorecardform">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Score Card Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="scoreCradCode" value="" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Score Card Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="scoreCradName" value="" maxlength="32"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="product" style="width: 200px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="cardTypes" items="${cardTypeList}">
                                                                <c:if test="${scoreBean.product==cardTypes.cardTypeCode}" >
                                                                    <option selected="" value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreBean.product!=cardTypes.cardTypeCode}" >
                                                                    <option  value="${cardTypes.cardTypeCode}">${cardTypes.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">Assign Rules&nbsp;<span class="required">*</span>&nbsp;</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>All Rules</b></h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="rule" items="${ruleList}">
                                                                <option value="${rule.ruleCode}" >${rule.ruleName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned Tasks</b></h4>
                                                        <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="minScore" value="" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Score Card</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input style="width: 200px;" type="text" name="maxScore" value="" maxlength="20"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status" style="width: 200px;">
                                                            <option style="width: 200px;" value="" >--SELECT--</option>
                                                            <c:forEach var="cardScoreStatus" items="${requestScope.secList}">
                                                                <option style="width: 200px;" value="${cardScoreStatus.statusCode}">${cardScoreStatus.description}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Add" onclick="selectAll(assignsectionlist)"  style="width: 100px;"/>&nbsp&nbsp<input type="reset" onclick="resetBtn()" value="Reset" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SCORECARD%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>










                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;

                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Score Card Code</th>
                                            <th>Score Card Name</th>
                                            <th>Product</th>
                                            <th>Minimum Score Card</th>
                                            <th>Maximum Score Card</th>
                                            <th>Status</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.allList}" var="allList">
                                            <tr>
                                                <td>${allList.scoreCardCode}</td>
                                                <td>${allList.scoreCardName}</td>
                                                <td>${allList.cardTypeDes}</td>
                                                <td>${allList.minScoreCard}</td>
                                                <td>${allList.maxScoreCard}</td>
                                                <td>${allList.status}</td>
                                                <td><a  href='#'onclick="cardView('${allList.scoreCardCode}')">View</a></td>
                                                <td><a  href='#'onclick="cardUpdateView('${allList.scoreCardCode}')">Update</a></td>
                                                <td><a  href='#'onclick="cardDelete('${allList.scoreCardCode}')">delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>







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
