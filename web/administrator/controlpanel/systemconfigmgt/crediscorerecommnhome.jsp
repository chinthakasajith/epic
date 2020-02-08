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

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script type="text/javascript">
            
            function resetRecommandForm(){
                window.location = "${pageContext.request.contextPath}/LoadCreditScoreRecommendationServlet";
            }
            function viewData(value){
                window.location = "${pageContext.request.contextPath}/ViewCreditScoreRecommendedServlet?id="+value;
            }
            function updateData(value){
                
                window.location = "${pageContext.request.contextPath}/UpdateCreditScoreRecoomedeViewServlet?id="+value;
            }
            function cardTypeSet(value){
                document.add.action="${pageContext.request.contextPath}/ProcessCreditCardRecommedCardProductServlet?id="+value;
                document.add.submit();
            }
            
            function deleteData(value){
                
                answer = confirm("Do you really want to delete "+value+" Recommended Credit Score ?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteCreditScoreRecommendedServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCreditScoreRecommendationServlet";
                }
            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CREDITRECOMMAN%>'                                  
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

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

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

                                    <form method="POST" name="add" action="${pageContext.request.contextPath}/AddCreditScoreRecommendedServlet">

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr>
                                                    <td>ID </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="recId" value="${recomBean.id}" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;" name="cardType" onchange="cardTypeSet(value+'@A')"> 
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="value" items="${requestScope.cardtypeList}">

                                                                <c:if test="${value.cardTypeCode ==recomBean.cardtype}">
                                                                    <option selected="" value="${value.cardTypeCode}">${value.description}</option>
                                                                </c:if>
                                                                <c:if test="${value.cardTypeCode !=recomBean.cardtype}">
                                                                    <option  value="${value.cardTypeCode}">${value.description}</option>
                                                                </c:if>

                                                            </c:forEach>

                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><select style="width: 160px;" name="cardProduct">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="value" items="${requestScope.cardproductList}">

                                                                <c:if test="${value.productCode ==recomBean.cardproduct}">
                                                                    <option selected="" value="${value.productCode}">${value.description}</option>
                                                                </c:if>
                                                                <c:if test="${value.productCode !=recomBean.cardproduct}">
                                                                    <option  value="${value.productCode}">${value.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Score Low Limit</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="scoreLow" value="${recomBean.lowLimit}" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Score High Limit</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="scorehigh" value="${recomBean.highLimit}" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Credit Limit</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="creditLimit" value="${recomBean.creditlimit}" maxlength="12"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Add" style="width: 100px;"/><input type="reset" value="Reset" onclick="resetRecommandForm()" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITRECOMMAN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>


                                </c:if>

                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateCreditScoreRecommededServlet">

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr>
                                                    <td>ID </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" readonly="" name="recId" value="${recomBean.id}" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;" name="cardType"  >

                                                            <c:forEach var="value" items="${requestScope.cardtypeList}">

                                                                <c:if test="${value.cardTypeCode ==recomBean.cardTypeCode}">
                                                                    <option selected="" value="${value.cardTypeCode}">${value.description}</option>
                                                                </c:if>


                                                            </c:forEach>

                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><select style="width: 160px;" name="cardProduct" >
                                                            <c:forEach var="value" items="${requestScope.cardproductList}">

                                                                <c:if test="${value.productCode ==recomBean.cardproductCode}">
                                                                    <option selected="" value="${value.productCode}">${value.description}</option>
                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Score Low Limit</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="scoreLow" value="${recomBean.lowLimit}" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Score High Limit</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="scorehigh" value="${recomBean.highLimit}" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Credit Limit</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="creditLimit" value="${recomBean.creditlimit}" maxlength="12"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Update" style="width: 100px;"/><input type="reset" value="Reset" onclick="updateData('${recomBean.id}')" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITRECOMMAN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>


                                </c:if>





                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/LoadCreditScoreRecommendationServlet">

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr>
                                                    <td>ID </td>
                                                    <td></td>
                                                    <td><input type="text" readonly="" name="recId" value="${recomBean.id}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Type</td>
                                                    <td></td>
                                                    <td><input type="text" readonly="" name="recId" value="${recomBean.cardtype}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product</td>
                                                    <td></td>
                                                    <td><input type="text" readonly="" name="recId" value="${recomBean.cardproduct}" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Score Low Limit</td>
                                                    <td></td>
                                                    <td><input type="text" readonly="" name="scoreLow" value="${recomBean.lowLimit}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Score High Limit</td>
                                                    <td></td>
                                                    <td><input type="text" readonly="" name="scorehigh" value="${recomBean.highLimit}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Credit Limit</td>
                                                    <td></td>
                                                    <td><input type="text" readonly="" name="creditLimit" value="${recomBean.creditlimit}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Back" style="width: 100px;"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITRECOMMAN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>


                                </c:if>


                                <table border="0" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>ID </th>
                                            <th>Card Type</th>
                                            <th>Card Product</th>
                                            <th>Score Low Limit</th>
                                            <th>Score High Limit</th>
                                            <th>Credit Limit</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.recomList}" var="recomList">
                                            <tr>
                                                <td>${recomList.id}</td>
                                                <td>${recomList.cardtype}</td>
                                                <td>${recomList.cardproduct}</td>
                                                <td>${recomList.lowLimit}</td>
                                                <td>${recomList.highLimit}</td>
                                                <td>${recomList.creditlimit}</td>
                                                <td><a  href='#'onclick="viewData('${recomList.id}')">View</a></td>
                                                <td><a  href='#'onclick="updateData('${recomList.id}')">Update</a></td>
                                                <td><a  href='#'onclick="deleteData('${recomList.id}')">Delete</a></td>
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
