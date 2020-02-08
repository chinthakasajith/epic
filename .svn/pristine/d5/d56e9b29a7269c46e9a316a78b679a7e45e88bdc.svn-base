<%-- 
    Document   : debitembossingsearchhome
    Created on : Sep 18, 2012, 9:16:42 AM
    Author     : chanuka
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

                document.searchcardembossingform.action="${pageContext.request.contextPath}/DebitSearchCardEmbossingServlet";
                document.searchcardembossingform.submit();

            }
            
            function getProductByType(value)
            {

                document.searchcardembossingform.action="${pageContext.request.contextPath}/GetProductByTypeServlet?cardtype="+value;
                document.searchcardembossingform.submit();

            }
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/DebitLoadEmbossingServlet";

            }
            
            function getVisacheckAll()
            {
                
                var boxes = document.visacardform.elements.length;
                var array = [];
                for (var i = 0; i < boxes; i++) {
                    if (document.visacardform.elements[i].checked) {
                        array.push(document.visacardform.elements[i].value);

                    }
                }
                $('#visacards').attr("value",array);
                //                document.getElementById("visacards").setAttribute("value", array);
                
                document.hiddenvisacards.action="${pageContext.request.contextPath}/DebitProcessGenerateVisaCardServlet";
                document.hiddenvisacards.submit();
            }
            
            function getMastercheckAll()
            {

                var boxes = document.mastercardform.elements.length;
                var array = [];
                for (var i = 0; i < boxes; i++) {
                    if (document.mastercardform.elements[i].checked) {
                        array.push(document.mastercardform.elements[i].value);

                    }
                }
                
                $('#mastercards').attr("value",array);
                //                document.getElementById("mastercards").setAttribute("value", array);
                
                document.hiddenmastercards.action="${pageContext.request.contextPath}/DebitProcessGenerateMasterCardServlet";
                document.hiddenmastercards.submit();
            }

            function invokeVisaGenerateAll()
            {
             
                window.location = "${pageContext.request.contextPath}/DebitProcessGenerateAllVisaCardServlet";
                
            }
            function invokeMasterGenerateAll()
            {
             
                window.location = "${pageContext.request.contextPath}/DebitProcessGenerateAllMasterCardServlet";
                
            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBIT_EMBOSS%>'                                  
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


        <title>EPIC CMS DEBIT CARD EMBOSSING ENCODING MANAGEMENT HOME</title>
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
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
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

                                            <tr> <td style="height:5px;"></td></tr>

                                            <tr>
                                                <td style="width: 174px">Emboss Type </td>

                                                <c:if test="${searchembossingbean.embossType =='qafail'}"> <td>  <input style="width: 25px" type="radio" name="embosstype" value="qafail" checked="true"/></td></c:if>
                                                <c:if test="${searchembossingbean.embossType !='qafail'}"> <td>  <input style="width: 25px" type="radio" name="embosstype" value="qafail" /></td></c:if>
                                                    <td>QA Fail</td>

                                                </tr><tr><td></td>
                                                <c:if test="${searchembossingbean.embossType =='ordinary'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="ordinary" checked="true"/></td></c:if>
                                                <c:if test="${searchembossingbean.embossType !='ordinary'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="ordinary" /></td></c:if>
                                                    <td>Ordinary</td>
                                                </tr><tr><td></td>
                                                <c:if test="${searchembossingbean.embossType =='renew'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="renew" checked="true"/></td></c:if>
                                                <c:if test="${searchembossingbean.embossType !='renew'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="renew" /></td></c:if>
                                                    <td>Renew</td>
                                                </tr><tr><td></td>
                                                <c:if test="${searchembossingbean.embossType =='reissue'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="reissue" checked="true"/></td></c:if>
                                                <c:if test="${searchembossingbean.embossType !='reissue'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="reissue" /></td></c:if>
                                                    <td>Reissue</td>
                                                </tr><tr><td></td>
                                                <c:if test="${searchembossingbean.embossType =='replace'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="replace" checked="true"/></td></c:if>
                                                <c:if test="${searchembossingbean.embossType !='replace'}"><td>  <input style="width: 25px" type="radio" name="embosstype" value="replace"/></td></c:if>
                                                    <td>Replace</td>


                                                    <td></td>
                                                </tr>


                                            </tbody>
                                        </table>

                                        <table>
                                            <tbody>



                                                <tr> <td style="height:12px;"></td></tr>

                                                <tr>
                                                    <td style="width: 180px"></td>
                                                    <td style="width: 300px"><input type="button" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DEBIT_EMBOSS%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                            </tr>

                                            <tr><td style="height: 10px"></td><input type="hidden" name="hiddencarddomain" value="DEBIT"/></tr>     

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>

                                <form method="POST" action="" name="visacardform">
                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>

                                                <td><input type="button" value="Generate" name="VisaGenerate" style="width: 100px" onclick="getVisacheckAll()">
                                                    <input type="button" value="Generate All" name="VisaGenerateAll" style="width: 100px" onclick="invokeVisaGenerateAll()"></td>

                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>


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
                                                <th>Select</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="visa" items="${cardEmbossingVISAList}">
                                                <tr>
                                                    <td >${visa.cardNumberHidden}</td>
                                                    <td >${visa.accNo}</td>
                                                    <td >${visa.customerId}</td>
                                                    <td >${visa.cardtypeDes}</td>
                                                    <td >${visa.cardProductDes}</td>
                                                    <td >${visa.expiryDate}</td>
                                                    <td >${visa.nameOnCard}</td>
                                                    <td >${visa.cardStatusDes}</td>
                                                    <td >${visa.embossStatusDes}</td>
                                                    <td  >
                                                        <input type="checkbox" name="checktovisaemboss" value=${visa.cardNumber} />
                                                        <input type="hidden" name="hiddencardtype" value="${visa.cardtype}">
                                                        <input type="hidden" name="hiddencardtypedes" value="${visa.cardtypeDes}">
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </form>
                                <!--                                <table>
                                                                    <tr ><td style="height: 20px"></td></tr>
                                                                </table>
                                
                                                                <table class=""> <tr> <td   class="center"> <B> MASTER CARD DETAILS </B></td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                
                                                                <form method="POST" action="" name="mastercardform">
                                
                                                                    <table>
                                                                        <tbody>
                                
                                
                                
                                                                            <tr> <td style="height:12px;"></td></tr>
                                
                                                                            <tr>
                                
                                                                                <td><input type="button" value="Generate" name="MasterGenerate" style="width: 100px" onclick="getMastercheckAll()">
                                                                                    <input type="button" value="Generate All" name="MasterGenerateAll" style="width: 100px" onclick="invokeMasterGenerateAll()"></td>
                                
                                                                            </tr>
                                
                                                                            <tr><td style="height: 10px"></td></tr>     
                                
                                                                        </tbody>
                                                                    </table>
                                
                                
                                
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
                                                                                <th>Select</th>
                                
                                
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                <c:forEach var="master" items="${cardEmbossingMASTERList}">
                                    <tr>
                                        <td >${master.cardNumberHidden}</td>
                                        <td >${master.accNo}</td>
                                        <td >${master.customerId}</td>
                                        <td >${master.cardtypeDes}</td>
                                        <td >${master.cardProductDes}</td>
                                        <td >${master.expiryDate}</td>
                                        <td >${master.nameOnCard}</td>
                                        <td >${master.cardStatusDes}</td>
                                        <td >${master.embossStatusDes}</td>
                                        <td>
                                            <input type="checkbox" name="checktomasteremboss" value=${master.cardNumber} />

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </form>-->




                                <form method="POST" action="#" name="hiddenvisacards">

                                    <input  id="visacards" name="visacards" type="hidden"/>

                                </form>

                                <form method="POST" action="#" name="hiddenmastercards">

                                    <input  id="mastercards" name="mastercards" type="hidden"/>

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


