<%-- 
    Document   : creditscoreruledef
    Created on : Feb 17, 2012, 11:12:43 AM
    Author     : upul
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>


            function goToSelectOnChange()
            {
   
                document.addCreditScoreRuleForm.action="${pageContext.request.contextPath}/changeOnChangeOperatorServlet?check="+"check";
                document.addCreditScoreRuleForm.submit();
            }
            function goToSelectOnChangeField(value,optype)
            {
                if(optype=='add'){
                    document.addCreditScoreRuleForm.action="${pageContext.request.contextPath}/changeOnChangeOperatorServlet?check="+value;
                    document.addCreditScoreRuleForm.submit();
                }
                
                if(optype=='update'){
                    
                    document.updateCreditScoreRuleForm.action="${pageContext.request.contextPath}/changeOnChangeOperatorServlet?check="+value;
                    document.updateCreditScoreRuleForm.submit();
                }
            }
            function goToSelectOnChangeUpdate()
            {
   
                document.updateCreditScoreRuleForm.action="${pageContext.request.contextPath}/changeOnChangeOperatorServlet?check="+"check";
                document.updateCreditScoreRuleForm.submit();
            }
            //            function goToSelectOnChangeField()
            //            {
            //   
            //                document.addCreditScoreRuleForm.action="${pageContext.request.contextPath}/changeOnChangeRuleOneServlet";
            //                document.addCreditScoreRuleForm.submit();
            //            }
            function goToSelectOnChangeFieldUpdate()
            {
   
                document.updateCreditScoreRuleForm.action="${pageContext.request.contextPath}/changeOnChangeRuleOneServlet";
                document.updateCreditScoreRuleForm.submit();
            }

            function addCreditScore()
            {
   
                document.addCreditScoreRuleForm.action="${pageContext.request.contextPath}/AddCreditScoreRuleDefineServlet";
                document.addCreditScoreRuleForm.submit();
            }
            
            function resetCreditScore()
            {
   
                window.location="${pageContext.request.contextPath}/LoadCreditScoreRuleDefServlet";
            }
            function updateCreditScore()
            {
   
                document.updateCreditScoreRuleForm.action="${pageContext.request.contextPath}/UpdateCreditScoreRuleDefineServlet";
                document.updateCreditScoreRuleForm.submit();
            }
            
            
            function deleteCreditScore(value)
            {
    
                answer = confirm("Do you really want to delete  "+value+" Credit Score Rule ?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteCreditScoreRuleDefineServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCreditScoreRuleDefServlet";
                }
                
                //  document.addCreditScoreRuleForm.action="${pageContext.request.contextPath}/DeleteCreditScoreRuleDefineServlet?id="+value;
                //  document.addCreditScoreRuleForm.submit();
            }
           
 

            function updateViewCreditScore(value)

            {
              
                window.location ="${pageContext.request.contextPath}/UpdateCreditScoreRuleViewServlet?id="+value;
  
            }
            function backCreditScore()

            {
                window.location ="${pageContext.request.contextPath}/LoadCreditScoreRuleDefServlet";
  
            }
            function creditRuleView(value)
            {
   
                window.location ="${pageContext.request.contextPath}/ViewCreditScoreRuleDefineServlet?id="+value;
            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CREDITSCORERULEDEF%>'                                  
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



                <div class="content" style="height: 500px">

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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                <c:if test="${operationtype=='add'}" >

                                    <form name="addCreditScoreRuleForm" id="addCreditScoreRuleForm" method="POST">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>

                                                    <td><input type="hidden" name="operationtype" value="add" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Rule Code </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="ruleCode" value="${scoreRuleBean.ruleCode}" maxlength="16" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Rule Name </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="ruleName" value="${scoreRuleBean.ruleName}" maxlength="50" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Field Name </td>

                                                    <td>&nbsp;</td>   

                                                    <td>
                                                        <select  name="fieldName" style="width: 160px;"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="fieldBean" items="${sessionScope.SessionObject.fieldBeanLst}">

                                                                <c:if test="${scoreRuleBean.fieldName==fieldBean.fieldCode}">
                                                                    <option value="${fieldBean.fieldCode}" selected>${fieldBean.fieldDes}</option>
                                                                </c:if>
                                                                <c:if test="${scoreRuleBean.fieldName!=fieldBean.fieldCode}">
                                                                    <option value="${fieldBean.fieldCode}">${fieldBean.fieldDes}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>                    
                                                <tr>
                                                    <td>Condition </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select onchange="goToSelectOnChange()" style="width: 160px;" name="conditionVar">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="condition" items="${conditions}">
                                                                <c:if test="${scoreRuleBean.condition==condition.operatorCode}">
                                                                    <option value="${condition.operatorCode}" selected>${condition.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreRuleBean.condition!=condition.operatorCode}">
                                                                    <option value="${condition.operatorCode}">${condition.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <c:if test="${param.conditionVar!=7}">
                                                    <tr>
                                                        <td>Value </td>
                                                        <td>&nbsp;</td>
                                                        <td><input type="text" name="value" value="${scoreRuleBean.value}" maxlength="128" /></td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${param.conditionVar==6}">
                                                    <tr>
                                                        <td>Max Value </td>
                                                        <td>&nbsp;</td>

                                                        <td><input type="text" name="maxValue"  value="${param.maxValue}" maxlength="64" /></td>


                                                    </tr>
                                                </c:if>
                                                <c:if test="${param.conditionVar==7}">
                                                    <tr>
                                                        <td>Rule No:01 </td>

                                                        <td>&nbsp;</td>   

                                                        <td>
                                                            <select  style="width: 160px;" onchange="goToSelectOnChangeField('${"ruleNo2"}','${"add"}')" name="ruleNoOne"  class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="ruleLst" items="${activeScoreRuleLst}">

                                                                    <c:if test="${scoreRuleBean.ruleNoOne==ruleLst.ruleCode}">
                                                                        <option value="${ruleLst.ruleCode}" selected>${ruleLst.ruleName}</option>
                                                                    </c:if>
                                                                    <c:if test="${scoreRuleBean.ruleNoOne!=ruleLst.ruleCode}">
                                                                        <option value="${ruleLst.ruleCode}">${ruleLst.ruleName}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>

                                                        </td>
                                                        <td></td>
                                                    </tr>     


                                                </c:if>
                                                <c:if test="${ruleNoOne=='OK'}">
                                                    <tr>
                                                        <td>Rule No:02 </td>

                                                        <td>&nbsp;</td>   

                                                        <td>
                                                            <select style="width: 160px;" name="ruleNoTwo"  class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="ruleLst" items="${activeScoreRuleLst}">

                                                                    <c:if test="${scoreRuleBean.ruleNoTwo==ruleLst.ruleCode}">
                                                                        <option value="${ruleLst.ruleCode}" selected>${ruleLst.ruleName}</option>
                                                                    </c:if>
                                                                    <c:if test="${scoreRuleBean.ruleNoOne!=ruleLst.ruleCode}">


                                                                        <c:if test="${scoreRuleBean.ruleNoTwo!=ruleLst.ruleCode}">
                                                                            <option value="${ruleLst.ruleCode}">${ruleLst.ruleName}</option>

                                                                        </c:if>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>

                                                        </td>
                                                        <td></td>
                                                    </tr>    



                                                </c:if>
                                                <tr>
                                                    <td>Score </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="score" value="${scoreRuleBean.score}" maxlength="20" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td>&nbsp;</td>   

                                                    <td>
                                                        <select style="width: 160px;" name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${scoreRuleBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreRuleBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Add" style="width: 100px" onclick="addCreditScore()" /> <input type="reset" value="Reset" onclick="resetCreditScore()" style="width: 100px" /></td>

                                                    <!--      for history view start           -->

                                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITSCORERULEDEF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a><br>
                                                    </td>

                                                    <!--       for history view end           -->

                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>



                                </c:if>


                                <!--   ------------------------- end add form  --------------------------------                    -->

                                <!--   ------------------------- start update form  --------------------------------                  -->  

                                <c:if test="${operationtype=='update'}" >

                                    <form name="updateCreditScoreRuleForm" id="updateCreditScoreRuleForm" method="POST">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>                                               
                                                <tr>

                                                    <td><input type="hidden" name="operationtype" value="update" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Rule Code </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="ruleCode" readonly="readonly"  value="${scoreRuleBean.ruleCode}" maxlength="16" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Rule Name </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="ruleName" value="${scoreRuleBean.ruleName}" maxlength="50" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Field Name </td>

                                                    <td>&nbsp;</td>   

                                                    <td>
                                                        <select  name="fieldName" style="width: 160px;"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="fieldBean" items="${sessionScope.SessionObject.fieldBeanLst}">

                                                                <c:if test="${scoreRuleBean.fieldName==fieldBean.fieldCode}">
                                                                    <option value="${fieldBean.fieldCode}" selected>${fieldBean.fieldDes}</option>
                                                                </c:if>
                                                                <c:if test="${scoreRuleBean.fieldName!=fieldBean.fieldCode}">
                                                                    <option value="${fieldBean.fieldCode}">${fieldBean.fieldDes}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr> 
                                                <tr>
                                                    <td>Condition </td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;" onchange="goToSelectOnChangeUpdate()"  name="conditionVar">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="condition" items="${conditions}">
                                                                <c:if test="${scoreRuleBean.condition==condition.operatorCode}">
                                                                    <option value="${condition.operatorCode}" selected>${condition.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreRuleBean.condition!=condition.operatorCode}">
                                                                    <option value="${condition.operatorCode}">${condition.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <c:if test="${scoreRuleBean.condition!='7'}">
                                                    <tr>
                                                        <td>Value </td>
                                                        <td>&nbsp;</td>
                                                        <td><input type="text" name="value" value="${scoreRuleBean.value}" maxlength="128" /></td>
                                                    </tr>
                                                </c:if>

                                                <c:if test="${conditionVar=='6'}">
                                                    <tr>
                                                        <td>Max Value </td>
                                                        <td>&nbsp;</td>

                                                        <td><input type="text" name="maxValue"  value="${scoreRuleBean.maxValue}" maxlength="64" /></td>


                                                    </tr>
                                                </c:if>
                                                <c:if test="${scoreRuleBean.condition=='7'}">
                                                    <tr>
                                                        <td>Rule No:01 </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select  style="width: 160px;" onchange="goToSelectOnChangeField('${"ruleNo2"}','${"update"}')" name="ruleNoOne"  class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="ruleLst" items="${activeScoreRuleLst}">

                                                                    <c:if test="${scoreRuleBean.ruleNoOne==ruleLst.ruleCode}">
                                                                        <option value="${ruleLst.ruleCode}" selected>${ruleLst.ruleName}</option>
                                                                    </c:if>
                                                                    <c:if test="${scoreRuleBean.ruleNoOne!=ruleLst.ruleCode}">
                                                                        <option value="${ruleLst.ruleCode}">${ruleLst.ruleName}</option>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>

                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${scoreRuleBean.condition=='7'}">
                                                    <tr>
                                                        <td>Rule No:02 </td>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <select style="width: 160px;" name="ruleNoTwo"  class="inputfield-mandatory">
                                                                <option value="" selected>--SELECT--</option>
                                                                <c:forEach var="ruleLst" items="${activeScoreRuleLst}">

                                                                    <c:if test="${scoreRuleBean.ruleNoTwo==ruleLst.ruleCode}">
                                                                        <option value="${ruleLst.ruleCode}" selected>${ruleLst.ruleName}</option>
                                                                    </c:if>
                                                                    <c:if test="${scoreRuleBean.ruleNoOne!=ruleLst.ruleCode}">


                                                                        <c:if test="${scoreRuleBean.ruleNoTwo!=ruleLst.ruleCode}">
                                                                            <option value="${ruleLst.ruleCode}">${ruleLst.ruleName}</option>

                                                                        </c:if>

                                                                    </c:if>


                                                                </c:forEach>
                                                            </select>

                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <tr>
                                                    <td>Score </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="score" value="${scoreRuleBean.score}" maxlength="20" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td>&nbsp;</td>   

                                                    <td>
                                                        <select style="width: 160px;" name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${scoreRuleBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${scoreRuleBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Update" style="width: 100px"  onclick="updateCreditScore()" />
                                                        <input type="reset" value="Reset" onclick="backCreditScore()" style="width: 100px" />
                                                    </td>
                                                    <!--      for history view start           -->

                                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITSCORERULEDEF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a><br>
                                                    </td>

                                                    <!--       for history view end           -->
                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>



                                </c:if>
                                <!--   ------------------------- end update form  --------------------------------                    -->

                                <!--   -------------------------     start view --------------------------->
                                <c:if test="${operationtype=='view'}" >

                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td>Rule Code </td>
                                                <td>&nbsp;</td>
                                                <td><input type="text" readonly="" name="ruleCode" value="${scoreRuleBean.ruleCode}" /></td>
                                            </tr>
                                            <tr>
                                                <td>Rule Name </td>
                                                <td>&nbsp;</td>
                                                <td><input type="text"  readonly="" name="ruleName" value="${scoreRuleBean.ruleName}" /></td>
                                            </tr>
                                            <tr>
                                                <td>Field Name </td>
                                                <td>&nbsp;</td>
                                                <td><input type="text"  readonly="" name="fieldName" value="${scoreRuleBean.fieldNameDes}" /></td>
                                            </tr>
                                            <tr>
                                                <td>Condition </td>
                                                <td>&nbsp;</td>
                                                <td><input type="text"  readonly="" name="condition" value="${scoreRuleBean.conditionDes}" /></td>
                                            </tr>
                                            <c:if test="${scoreRuleBean.condition!='7'}">
                                                <tr>
                                                    <td>Value </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="value" value="${scoreRuleBean.value}" /></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${scoreRuleBean.maxValue!=null}">
                                                <tr>
                                                    <td>Max Value </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="maxValue"  value="${scoreRuleBean.maxValue}" /></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${scoreRuleBean.ruleNoOne!=null}">
                                                <tr>
                                                    <td>Rule No:01 </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" readonly="" name="ruleNoOne" value="${scoreRuleBean.ruleNoOne}" /></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${scoreRuleBean.ruleNoTwo!=null}">
                                                <tr>
                                                    <td>Rule No:02 </td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="ruleNoTwo" value="${scoreRuleBean.ruleNoTwo}" /></td>
                                                </tr>
                                            </c:if>
                                            <tr>
                                                <td>Score </td>
                                                <td>&nbsp;</td>
                                                <td><input type="text" name="score" value="${scoreRuleBean.score}" /></td>
                                            </tr>

                                            <tr>
                                                <td>Status</td>

                                                <td>&nbsp;</td>   

                                                <td>
                                                    <input type="text" name="score" readonly="" value="${scoreRuleBean.statusDes}" />
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td><input type="submit" onclick="backCreditScore()" value="Back" style="width: 100px" /></td>
                                                <!--      for history view start           -->

                                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITSCORERULEDEF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a><br>
                                                </td>

                                                <!--       for history view end           -->
                                            </tr>
                                        </tbody>
                                    </table>






                                </c:if>
                                <!-- -------------------------end view ------------------------- -->

                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;



                                <table  border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Rule Code</th>
                                            <th>Rule Description</th>
                                            <th>Field Name</th>
                                            <th>Condition</th>
                                            <th>Value</th>
                                            <th>Score</th>
                                            <th>Status</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.creditScoreRuleLst}" var="allList">
                                            <tr>
                                                <td>${allList.ruleCode}</td>
                                                <td>${allList.ruleName}</td>
                                                <td>${allList.fieldNameDes}</td>
                                                <td>${allList.condition}</td>

                                                <c:if test="${allList.value!=null}" >
                                                    <td>${allList.value}</td>
                                                </c:if>
                                                <c:if test="${allList.value==null}" >
                                                    <td>----</td>
                                                </c:if>

                                                <td>${allList.score}</td>
                                                <td>${allList.status}</td>
                                                <td><a  href='#' onclick="creditRuleView('${allList.ruleCode}')">View</a></td>
                                                <td><a  href='#' onclick="updateViewCreditScore('${allList.ruleCode}')">Update</a></td>
                                                <td><a  href='#' onclick="deleteCreditScore('${allList.ruleCode}')">delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>


                                <!--   ------------------------- end developer area  --------------------------------   -->

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
