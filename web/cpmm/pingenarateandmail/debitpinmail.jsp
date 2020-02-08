<%-- 
    Document   : debitpinmail
    Created on : Sep 4, 2012, 1:37:48 PM
    Author     : mahesh_m
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeSearch()
            {

                document.searchcardembossingform.action="${pageContext.request.contextPath}/SearchDebitPinMailServlet";
                document.searchcardembossingform.submit();

            }
            
           
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadDebitPinMailServlet";

            }
            
          
            
            function pinMail(cardNo)
            {
             
                window.location = "${pageContext.request.contextPath}/GenerateDebitPinMailServlet?cardNo="+cardNo;
                
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBIT_PINMAIL%>'                                  
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


        <title>EPIC CMS DEBIT CARD PIN MAIL</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchcardembossingform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                            <td><font color="green"> <b>${successMsg} </b></font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td style="width: 180px">Card Number</td>
                                                <td><input type="text"  value="${searchembossingbean.cardNumber}" name="cardnumber" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Identification Type</td>
                                                <td> 
                                                    <select style="width: 100px" name="identitytype">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                            <c:if test="${searchembossingbean.identityType==identity.key}">
                                                                <option value="${identity.key}" selected>${identity.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.identityType != identity.key}">
                                                                <option value="${identity.key}" >${identity.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px"> Identification Number</td>
                                                <td><input type="text"  value="${searchembossingbean.identityNo}" name="identityno" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  



                                            <tr>
                                                <td style="width: 180px"> Priority Level </td>
                                                <td>
                                                    <select style="width: 100px" name="prioritycode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="priority" items="${sessionScope.SessionObject.priorityLevelList}">
                                                            <c:if test="${searchembossingbean.priorityLevel==priority.key}">
                                                                <option value="${priority.key}" selected>${priority.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.priorityLevel != priority.key}">
                                                                <option value="${priority.key}" >${priority.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>


                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Card Type</td>

                                                <td>
                                                    <select style="width: 100px" name="cardtypecode" onchange="getProductByType(cardtypecode.value)">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="cardtype" items="${sessionScope.SessionObject.cardTypeMap}">
                                                            <c:if test="${searchembossingbean.cardtype==cardtype.key}">
                                                                <option value="${cardtype.key}" selected>${cardtype.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.cardtype != cardtype.key}">
                                                                <option value="${cardtype.key}" >${cardtype.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </tr>


                                            <tr><td style="height: 5px"></td></tr>     


                                            <tr>
                                                <td style="width: 180px">Card Product</td>

                                                <td>
                                                    <select style="width: 100px" name="cardproductcode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="cardproduct" items="${sessionScope.SessionObject.cardProductList}">
                                                            <c:if test="${searchembossingbean.cardProduct==cardproduct.key}">
                                                                <option value="${cardproduct.key}" selected>${cardproduct.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.cardProduct != cardproduct.key}">
                                                                <option value="${cardproduct.key}" >${cardproduct.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </tr>


                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Generate User </td>
                                                <td>
                                                    <select style="width: 100px"  name="generateduser">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="user" items="${sessionScope.SessionObject.usersList}">
                                                            <c:if test="${searchembossingbean.generatedUser==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.generatedUser!=user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr> <td style="height:5px;"></td></tr>

                                            <tr>
                                                <td style="width: 180px">Status </td>
                                                <td>
                                                    <select style="width: 100px" name="statuscode" id="statuscode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${searchembossingbean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>



                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td style="width: 180px"></td>
                                                <td style="width: 300px"><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>

                                <form method="POST" action="" name="mastercardform">


                                    <table  border="1"  class="display" id="tableview2">
                                        <thead>
                                            <tr>
                                                <th>Card Number</th>
                                                <th>Account Number</th>
                                                <th>Customer ID</th>
                                                <th>Card Type</th>
                                                <th>Card Product</th>
                                                <th>Expiry Date</th>
                                                <th>Name On Card</th>
                                                <th>Card Status</th>
                                                <th>Emboss Status</th>
                                                <th>Generate</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="pinMail" items="${pingMailList}">
                                                <tr>
                                                    <td >${pinMail.cardNumberHidden}</td>
                                                    <td >${pinMail.accNo}</td>
                                                    <td >${pinMail.customerId}</td>
                                                    <td >${pinMail.cardtypeDes}</td>
                                                    <td >${pinMail.cardProductDes}</td>
                                                    <td >${pinMail.expiryDate}</td>
                                                    <td >${pinMail.nameOnCard}</td>
                                                    <td >${pinMail.cardStatusDes}</td>
                                                    <td >${pinMail.embossStatusDes}</td>
                                                    <td>
                                                        <input type="button" name="pinGenarate" value="Generate" onclick="pinMail('${pinMail.cardNumber}')"/>

                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </form>



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

