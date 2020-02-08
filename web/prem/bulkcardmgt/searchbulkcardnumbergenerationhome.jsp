<%-- 
    Document   : searchbulkcardnumbergenerationhome
    Created on : Sep 10, 2012, 9:22:12 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <jsp:include page="/content.jsp"/>


        <script >
            
            function invokeReset(ele){
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                        
                    }
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }
                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';

            }
            function invokeSearch()
            {

                document.searchBulkCardForm.action="${pageContext.request.contextPath}/SearchBulkCardNumberGenerationServlet";
                document.searchBulkCardForm.submit();

            }
      
            
            function invokeGenerate(batchID)
            {
                
                window.location="${pageContext.request.contextPath}/ViewBulkCardNumberGenerationServlet?batchID="+ batchID;
                

            }
            

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULK_CARD_NUMBER_GENERATION%>'                                  
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

        <title>BULK CARD NUMBER GENERATION</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>

            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>

                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"><label id="errorMsg"> ${errorMsg}</label></font></b> </td>
                                        <td><b><font color="Green"><label id ="successMsg"> ${successMsg}</label></font></b> </td>
                                    </tr>
                                </table>

                                <c:if test="${operationtype=='search'}">

                                    <form name="searchBulkCardForm" method="post" action="">

                                        <table cellpadding="0" cellspacing="10"   >

                                            <tr>
                                                <td style="width: 200px;">Batch ID</td>
                                                <td><input type="text" name="batchID"  maxlength="8" value="${numberGenBean.batchID}" style="width: 150px;"></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Card Domain</td>
                                                <td>
                                                    <select name="cdDomain" id="domainType"  >

                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="domain" items="${cardDomainList}">
                                                            <c:if test="${numberGenBean.cdDomain==domain.key}">
                                                                <option value="${domain.key}" selected>${domain.value}</option>
                                                            </c:if>
                                                            <c:if test="${numberGenBean.cdDomain != domain.key}">
                                                                <option value="${domain.key}" >${domain.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Card Type</td>
                                                <td  style="width: 200px;"><select  name="cdType" id="cardType" class="inputfield-mandatory"  onchange="lordCardProduct()"  >
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="type" items="${sessionScope.SessionObject.cardTypeList}">
                                                            <c:if test="${numberGenBean.cdType==type.cardTypeCode}">
                                                                <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                            </c:if>
                                                            <c:if test="${numberGenBean.cdType!=type.cardTypeCode}">
                                                                <option value="${type.cardTypeCode}">${type.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Card Product</td>
                                                <td style="width: 200px;"><select  id="cardProduct" class="inputfield-mandatory" name="cdProduct"  >
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="product" items="${sessionScope.SessionObject.cardProductMgtList}">
                                                            <c:if test="${numberGenBean.cdProduct==product.productCode}">
                                                                <option value="${product.productCode}" selected="true" >${product.description}</option>
                                                            </c:if>
                                                            <c:if test="${numberGenBean.cdProduct!=product.productCode}">
                                                                <option value="${product.productCode}">${product.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Branch Name</td>
                                                <td><select  name="branchCode"  >
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="branch" items="${branchList}">
                                                            <c:if test="${numberGenBean.branchCode==branch.key}">
                                                                <option value="${branch.key}" selected="true" >${branch.value}</option>
                                                            </c:if>
                                                            <c:if test="${numberGenBean.branchCode!=branch.key}">
                                                                <option value="${branch.key}">${branch.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Priority Level</td>
                                                <td>
                                                    <select  name="priorityLvl" >
                                                        <option value="" >--SELECT--</option>
                                                        <c:forEach var="priority" items="${priorityLevelList}">
                                                            <c:if test="${numberGenBean.priorityLvl==priority.key}">
                                                                <option value="${priority.key}" selected>${priority.value}</option>
                                                            </c:if>
                                                            <c:if test="${numberGenBean.priorityLvl != priority.key}">
                                                                <option value="${priority.key}" >${priority.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Requested User</td>
                                                <td><input style="width: 150px;" type="text" name="reqUser"  maxlength="32" value="${numberGenBean.reqUser}"></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><p>&nbsp;</p></td>
                                                <td></td>
                                            </tr>
                                        </table>
                                            
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr >
                                                <td style="width: 200px;"></td>
                                                <td><input type="submit" name="search" value="Search" onclick="invokeSearch()" style="width: 95px;"/></td>
                                                <td>  <input type="button" onclick="invokeReset(this.form)" name="next" value="Reset" style="width: 95px;"/></td>
                                            </tr>

                                        </table>

                                    </form>
                                    <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->
                                    <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                    <br></br>

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Batch ID</th>
                                                <th>Card Domain</th>
                                                <th>Card Type</th>
                                                <th>Card Product</th>
                                                <th>Priority Level</th>
                                                <th>Branch Name</th>
                                                <th>Requested User</th>


                                                <th>Generate</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="card" items="${cardList}">
                                                <tr>
                                                    <td >${card.batchID}</td>
                                                    <td >${card.cdDomainDes}</td>
                                                    <td >${card.cdTypeDes}</td>
                                                    <td >${card.cdProductDes}</td>
                                                    <td >${card.priorityLvlDes}</td>
                                                    <td >${card.branchName}</td>
                                                    <td >${card.reqUser}</td>


                                                    <td  ><a href='#' onclick="invokeGenerate('${card.batchID}')">Generate</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 

                                </c:if>
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