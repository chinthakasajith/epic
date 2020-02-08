<%-- 
    Document   : bulkpinmail
    Created on : Sep 20, 2012, 11:20:07 AM
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

                document.searchcardembossingform.action="${pageContext.request.contextPath}/SearchBulkPinMailServlet";
                document.searchcardembossingform.submit();

            }
            
            function getProductByType(value)
            {

                document.searchcardembossingform.action="${pageContext.request.contextPath}/GetCardProductByTypePinGen?cardtype="+value;
                document.searchcardembossingform.submit();

            }
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadBulkPinMailServlet";

            }
            
        
            function pinMail(batchNo)
            {
             
                window.location = "${pageContext.request.contextPath}/generateBulkPinMailServlet?batchNo="+batchNo;
                
            }
            
        </script>
     <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULK_PIN_MAIL%>'                                  
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


        <title>BULK PIN GENARATION</title>
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
                                            <td><font color="Red"><b> ${errorMsg} </b></font> </td>
                                            <td><font color="green"><b> ${successMsg}</b></font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td style="width: 100px">Batch ID</td>
                                                <td><input type="text"  value="${SearchBulkPingenBean.batchId}" name="batchId" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Card Domain</td>
                                                <td> 
                                                    <select style="width: 100px" name="cardDomain">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="domain" items="${bulkCardDomainList}">
                                                            <c:if test="${SearchBulkPingenBean.cardDomain==domain.domainId}">
                                                                <option value="${domain.domainId}" selected>${domain.description}</option>
                                                            </c:if>
                                                            <c:if test="${SearchBulkPingenBean.cardDomain != domain.domainId}">
                                                                <option value="${domain.domainId}" >${domain.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Production Mode</td>
                                                <td> 
                                                    <select style="width: 100px" name="productmode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="pmode" items="${productionModeList}">
                                                            <c:if test="${SearchBulkPingenBean.productionMode==pmode.productionModeCode}">
                                                                <option value="${pmode.productionModeCode}" selected>${pmode.description}</option>
                                                            </c:if>
                                                            <c:if test="${SearchBulkPingenBean.productionMode != pmode.productionModeCode}">
                                                                <option value="${pmode.productionModeCode}" >${pmode.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  
                                            <tr>
                                                <td style="width: 180px"> Card Type</td>
                                                <td>
                                                    <select style="width: 100px" name="cardType">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="ct" items="${cardTypeList}">
                                                            <c:if test="${SearchBulkPingenBean.cardType==ct.key}">
                                                                <option value="${ct.key}" selected>${ct.value}</option>
                                                            </c:if>
                                                            <c:if test="${SearchBulkPingenBean.cardType != ct.key}">
                                                                <option value="${ct.key}" >${ct.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  

                                            <tr>
                                                <td style="width: 180px"> Card Product</td>
                                                <td>
                                                    <select style="width: 100px" name="cardProduct">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="cp" items="${bulkCProductList}">
                                                            <c:if test="${SearchBulkPingenBean.cardProduct==cp.productCode}">
                                                                <option value="${cp.productCode}" selected>${cp.description}</option>
                                                            </c:if>
                                                            <c:if test="${SearchBulkPingenBean.cardProduct != cp.productCode}">
                                                                <option value="${cp.productCode}" >${cp.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  


                                            <tr>
                                                <td style="width: 180px">Generate User </td>
                                                <td>
                                                    <select style="width: 100px"  name="generateduser">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="user" items="${usersList}">
                                                            <c:if test="${SearchBulkPingenBean.genUser==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${SearchBulkPingenBean.genUser!=user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td style="width: 180px">Branch</td>
                                                <td>
                                                    <select style="width: 100px"  name="branch">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="branch" items="${branchesDeatilsList}">
                                                           <c:if test="${SearchBulkPingenBean.branchId==branch.key}">
                                                                <option value="${branch.key}" selected>${branch.value}</option>
                                                            </c:if>
                                                            <c:if test="${SearchBulkPingenBean.branchId != branch.key}">
                                                                <option value="${branch.key}" >${branch.value}</option>
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
                                                <th>Batch ID</th>
                                                <th>Card Domain</th>
                                                <th>Production Mode</th>
                                                <th>Card Type</th>
                                                <th>Card Product</th>
                                                <th>Card Status</th>
                                                <th>Generated user</th>
                                                <th>Branch</th>
                                                <th>Generate</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                             <c:forEach var="pin" items="${searchList}">
                                                <tr>
                                                    <td >${pin.batchId}</td>
                                                    <td >${pin.domainDes}</td>
                                                    <td >${pin.pmDes}</td>
                                                    <td >${pin.cardTypeDes}</td>
                                                    <td >${pin.cardProductDes}</td>
                                                    <td >${pin.statusDes}</td>
                                                    <td >${pin.genUser}</td>
                                                    <td >${pin.branchDes}</td>
                                                    
                                                    <td  >
                                                         <input type="button" name="pinGenarate" value="Generate" onclick="pinMail('${pin.batchId}')"/>

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

