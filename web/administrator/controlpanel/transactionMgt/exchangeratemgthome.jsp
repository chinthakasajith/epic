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


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>-->



        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <script type="text/javascript">
           
            function exViewOld(id){
                
                $.post("${pageContext.request.contextPath}/ViewExchangeRateServlet", { id:id },
                function(data) {
                    if(data == "success"){
                        $.colorbox({href:"${pageContext.request.contextPath}/administrator/controlpanel/transactionMgt/exchangerateview.jsp", iframe:false, height:"60%", width:"60%",overlayClose:false});
                    }else{
                        alert("can not view call");
                    }

                });
            }
            
            function exView(value){
                window.location = "${pageContext.request.contextPath}/ViewExchangeRateServlet?id="+value;
            }
            
            function exUpdate(value){
                window.location = "${pageContext.request.contextPath}/UpdateExchangeRateViewServlet?id="+value;
            }
            
            function exResetBtn(){
                window.location = "${pageContext.request.contextPath}/LoadExchangeRateMgtServlet";
            }
            
            function exDelete(value,id){
                        
                answer = confirm("Do you really want to delete  "+id+"  Exchange Rate ?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteExchangeRateServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadExchangeRateMgtServlet";
                }
            }
            
        </script>

        <title>EPIC_CMS_HOME</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  EXCHANGE RATE </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>
                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddExchangeRateServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>From Currency </td>
                                                    <td>  </td>
                                                    <td>
                                                        <select name="fromCur">
                                                            <c:forEach var="exchList" items="${requestScope.exchList}">

                                                                <option value="${exchList.currencyCode}">${exchList.currencyDes}</option>
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
                                                    <td>To Currency </td>
                                                    <td>  </td>
                                                    <td>
                                                        <select name="toCur">
                                                            <c:forEach var="exchList" items="${requestScope.exchList}">
                                                                <option value="${exchList.currencyCode}">${exchList.currencyDes}</option>
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
                                                    <td>Rate</td>
                                                    <td>  </td>
                                                    <td><input type="text" name="rate" value="" maxlength="8"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Invert Rate</td>
                                                    <td>  </td>
                                                    <td><input type="checkbox" name="invertRate" value="ON" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" style="width: 100px" value="Add" name="Add" /> <input type="reset" value="Reset" onclick="exResetBtn()" style="width: 100px" name="Reset" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.EXCHANGERATE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a>  </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!-- view-->
                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddExchangeRateServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>From Currency </td>
                                                    <td> </td>
                                                    <td>
                                                        <select name="fromCur">
                                                            <c:forEach var="exchList" items="${requestScope.exchList}">
                                                                <c:if test="${exchList.currencyCode ==exBean.fromCurrency}">
                                                                    <option selected="" value="${exchList.currencyCode}">${exchList.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${exchList.currencyCode !=exBean.fromCurrency}">
                                                                    <option value="${exchList.currencyCode}">${exchList.currencyDes}</option>
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
                                                    <td>To Currency </td>
                                                    <td>  </td>
                                                    <td>
                                                        <select name="toCur" contenteditable="true">
                                                            <c:forEach var="exchList" items="${requestScope.exchList}">

                                                                <c:if test="${exchList.currencyCode ==exBean.toCurrency}">
                                                                    <option selected="" value="${exchList.currencyCode}">${exchList.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${exchList.currencyCode !=exBean.toCurrency}">
                                                                    <option  value="${exchList.currencyCode}">${exchList.currencyDes}</option>
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
                                                    <td>Rate</td>
                                                    <td>  </td>
                                                    <c:if test="${exBean.rate !=0.0}">
                                                        <td><input type="text" name="rate" value="${exBean.rate}" maxlength="8"/></td>
                                                        </c:if>
                                                        <c:if test="${exBean.rate ==0.0}">
                                                        <td><input type="text" name="rate" value="" maxlength="4"/></td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Invert Rate</td>
                                                    <td>  </td>
                                                    <td><input type="checkbox" name="invertRate" value="ON" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td>  </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" style="width: 100px" value="Add" name="Add" /> <input type="reset" value="Reset" onclick="exResetBtn()" style="width: 100px" name="Reset" /> </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.EXCHANGERATE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <!------end view-->

                                <!-- update-->

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateExchangeRateServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>From Currency </td>
                                                    <td>  </td>
                                                    <td>
                                                        <select name="upfromCur">
                                                            <c:forEach var="exchList" items="${requestScope.exchList}">
                                                                <c:if test="${exchList.currencyCode ==exBean.fromCurrency}">
                                                                    <option selected="" value="${exchList.currencyCode}">${exchList.currencyDes}</option>
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
                                                    <td>To Currency </td>
                                                    <td>  </td>
                                                    <td>
                                                        <select name="uptoCur" contenteditable="true">
                                                            <c:forEach var="exchList" items="${requestScope.exchList}">

                                                                <c:if test="${exchList.currencyCode ==exBean.toCurrency}">
                                                                    <option selected="" value="${exchList.currencyCode}">${exchList.currencyDes}</option>
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
                                                    <td>Rate</td>
                                                    <td>  </td>
                                                    <c:if test="${exBean.rate !=0.0}">
                                                        <td><input type="text" name="uprate" value="${exBean.rate}" maxlength="8"/></td>
                                                        </c:if>
                                                        <c:if test="${exBean.rate ==0.0}">
                                                        <td><input type="text" name="uprate" value="" maxlength=""/></td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <c:if test="${flag =='Y'}">
                                                        <td>Invert Rate</td>
                                                        <td>  </td>
                                                        <td><input type="checkbox" checked="" name="invertRate" value="ON" /></td>
                                                        </c:if>


                                                </tr> 
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Update" name="update" style="width: 100px" /> <input type="reset" onclick="exUpdate('${exBean.fromCurrency}@${exBean.toCurrency}@${exBean.rate}')" style="width: 100px" value="Reset" name="Reset" width="100" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.EXCHANGERATE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /> </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='edit'}" >

                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td>From Currency </td>
                                                <td> :</td>
                                                <td>${SessionObject.exbBean.fromCurDes} </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>To Currency </td>
                                                <td> : </td>
                                                <td>${SessionObject.exbBean.toCurDes}</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Rate</td>
                                                <td> : </td>
                                                <td>${SessionObject.exbBean.rate}</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>  </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td><input type="reset" value="Back" onclick="exResetBtn()" style="width: 100px" name="Back" /></td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </c:if>


                                <!--end update-->
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;

                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>From Currency</th>
                                            <th>To Currency</th>
                                            <th>Rate</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.allExList}" var="allExList" >
                                            <tr>
                                                <td>${allExList.fromCurDes}</td>
                                                <td>${allExList.toCurDes}</td>
                                                <td>${allExList.rate}</td>
                                                <td ><a  href='#' onclick="exView('${allExList.fromCurrency}@${allExList.toCurrency}@${allExList.rate}')">View</a></td>
                                                <td ><a  href='#' onclick="exUpdate('${allExList.fromCurrency}@${allExList.toCurrency}@${allExList.rate}')">Update</a></td>
                                                <td ><a  href='#' onclick="exDelete('${allExList.fromCurrency}@${allExList.toCurrency}@${allExList.rate}','${allExList.fromCurDes} - ${allExList.toCurDes}')">Delete</a></td>
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
