<%-- 
    Document   : debitcardpingeneration
    Created on : Sep 4, 2012, 10:18:54 AM
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

                document.searchcardembossingform.action="${pageContext.request.contextPath}/SearchDebitPingenerationServlet";
                document.searchcardembossingform.submit();

            }
            
            function getProductByType(value)
            {

                document.searchcardembossingform.action="${pageContext.request.contextPath}/GetCardProductByTypeDebitPinGen?cardtype="+value;
                document.searchcardembossingform.submit();

            }
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadDebitCardPinGenerationServlet";

            }
            
            function getVisacheckAll(value)
            {

                var boxes = document.visacardform.elements.length;
                var array = [];
                for (var i = 0; i < boxes; i++) {
                    if (document.visacardform.elements[i].checked) {
                        array.push(document.visacardform.elements[i].value);

                    }
                }
                document.getElementById("visacards").setAttribute("value", array);
                
                document.hiddenvisacards.action="${pageContext.request.contextPath}/ProcessGenerateVisaCardServlet";
                document.hiddenvisacards.submit();
            }
            
            function getMastercheckAll(value)
            {

                var boxes = document.mastercardform.elements.length;
                var array = [];
                for (var i = 0; i < boxes; i++) {
                    if (document.mastercardform.elements[i].checked) {
                        array.push(document.mastercardform.elements[i].value);

                    }
                }
                
                document.getElementById("mastercards").setAttribute("value", array);
                
                
                document.hiddenmastercards.action="${pageContext.request.contextPath}/ProcessGenerateMasterCardServlet";
                document.hiddenmastercards.submit();
            }

            function invokeVisaGenerateAll()
            {
             
                window.location = "${pageContext.request.contextPath}/ProcessGenerateAllVisaCardServlet";
                
            }
            function invokeMasterGenerateAll()
            {
             
                window.location = "${pageContext.request.contextPath}/ProcessGenerateAllMasterCardServlet";
                
            }
            
            function pinGenerate(cardNo)
            {
             
                window.location = "${pageContext.request.contextPath}/generateDebitPinServlet?cardNo="+cardNo;
                
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBITPINGENERATION%>'                                  
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


        <title>EPIC CMS DEBIT CARD PIN GENERATION</title>
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

                                <table class="tit"> <tr> <td   class="center"> <B> DEBIT CARD PIN GENERATION </B></td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchcardembossingform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                            <td><font color="green"><b> ${successMsg} </b></font> </td>
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

                                <form method="POST" action="" name="visacardform">
                                  


                                    <table  border="1"  class="display" id="tableview">
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
                                            <c:forEach var="pin" items="${cardEmbossingVISAList}">
                                                <tr>
                                                    <td >${pin.cardNumberHidden}</td>
                                                    <td >${pin.accNo}</td>
                                                    <td >${pin.customerId}</td>
                                                    <td >${pin.cardtypeDes}</td>
                                                    <td >${pin.cardProductDes}</td>
                                                    <td >${pin.expiryDate}</td>
                                                    <td >${pin.nameOnCard}</td>
                                                    <td >${pin.cardStatusDes}</td>
                                                    <td >${pin.embossStatusDes}</td>
                                                    <td  >
                                                        <input type="button" name="pinGenarate" value="Generate" onclick="pinGenerate('${pin.cardNumber}')"/>

                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </form>
                                <table>
                                    <tr ><td style="height: 20px"></td></tr>
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

