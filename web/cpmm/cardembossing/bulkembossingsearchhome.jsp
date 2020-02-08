<%-- 
    Document   : bulkembossingsearchhome
    Created on : Sep 18, 2012, 2:26:42 PM
    Author     : chanuka
--%>

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

                document.searchcardembossingform.action="${pageContext.request.contextPath}/BulkSearchCardEmbossingServlet";
                document.searchcardembossingform.submit();

            }
            
            function getProductByType(value)
            {

                document.searchcardembossingform.action="${pageContext.request.contextPath}/GetProductByTypeServlet?cardtype="+value;
                document.searchcardembossingform.submit();

            }
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/BulkLoadEmbossingServlet";

            }
            

 

            function invokeBulkGenerateAll(batchId)
            {
             
                document.bulkcardform.action="${pageContext.request.contextPath}/BulkProcessGenerateAllBulkCardServlet?hiddenbatchid="+batchId;
                document.bulkcardform.submit();
                
            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULK_EMBOSS%>'                                  
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


        <title>EPIC CMS BULK CARD EMBOSSING ENCODING MANAGEMENT HOME</title>
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
                                            <td></td>
                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td style="width: 180px">Batch ID</td>
                                                <td><input type="text"  value="${searchembossingbean.cardNumber}" name="batchid" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td>Branch Name </td>
                                                <td>
                                                    <select  name="branchid">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="branch" items="${sessionScope.SessionObject.branchesMapList}">
                                                            <c:if test="${searchembossingbean.branchId==branch.key}">
                                                                <option value="${branch.key}" selected>${branch.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.branchId!=branch.key}">
                                                                <option value="${branch.key}" >${branch.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td>Application Domain </td>
                                                <td>
                                                    <select  name="domainid">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="domain" items="${sessionScope.SessionObject.applicationDomainList}">
                                                            <c:if test="${searchembossingbean.domainId==domain.key}">
                                                                <option value="${domain.key}" selected>${domain.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchembossingbean.domainId!=domain.key}">
                                                                <option value="${domain.key}" >${domain.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
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
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.BULK_EMBOSS%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                            </tr>

                                            <tr><td style="height: 10px"></td><input type="hidden" name="hiddencarddomain" value="BULK"/></tr>     

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>
                                
                                <form method="POST" action="" name="bulkcardform">

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Batch ID</th>
                                                <th>Branch Name</th>
                                                <th>Card Domain</th>
                                                <th>Card Type</th>
                                                <th>Card Product</th>
                                                <th>Card Status</th>
                                                <th>Generate</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="bulk" items="${bulkCardEmbossingList}">
                                                <tr>
                                                    <td >${bulk.batchId}</td>
                                                    <td >${bulk.branchName}</td>
                                                    <td >${bulk.cardDomainDes}</td>
                                                    <td >${bulk.cardtypeDes}</td>
                                                    <td >${bulk.cardProductDes}</td>
                                                    <td >${bulk.statusDes}</td>
                                                    <td >

                                                        <input type="hidden" name="hiddenbatchid" >
                                                        <input type="hidden" name="hiddencardtype" value="${bulk.cardtype}">
                                                        <input type="hidden" name="hiddencardtypedes" value="${bulk.cardtypeDes}">
                                                        <input type="hidden" name="hiddencardproduct" value="${bulk.cardProduct}">
                                                        <input type="hidden" name="hiddencardproductdes" value="${bulk.cardProductDes}">

                                                        <input type="button" name="bulkemboss" value="Generate" onclick="invokeBulkGenerateAll('${bulk.batchId}')">

                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </form>
                                <table>
                                    <tr ><td style="height: 20px"></td></tr>
                                </table>



                                <!--                                <form method="POST" action="#" name="hiddenmastercards">
                                
                                                                    <input  id="mastercards" name="mastercards" type="hidden"/>
                                
                                                                </form>-->
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



