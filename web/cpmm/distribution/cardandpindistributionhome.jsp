<%-- 
    Document   : cardandpindistributionhome
    Created on : Jul 26, 2012, 1:48:33 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Card and Pin Distribution Page</title>



        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <script>
            $(document).ready(function() {
                var oTable = $('#tableview').dataTable({
                    "bJQueryUI": true,
                    "sScrollX": "100%",
                    "bAutoWidth": true,
                    "bDestroy": true,
                    "sPaginationType": "full_numbers"
                });
            });
        </script>
        
        <script >           
           
            function invokeCancel(opType){
                
                window.location = "${pageContext.request.contextPath}/LoadSelectCardPinDistributionServlet?opType="+opType;
            }
            function invokeReset(opType){
                
                window.location = "${pageContext.request.contextPath}/LoadSelectCardPinDistributionServlet?opType="+opType;
               
            }
            
            function invokeCheckAllCard(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = true ;
            
               
                
            }
            
            function invokeCheckAllPin(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = true ;
            
               
                
            }
            
            function invokeUnCheckAllCard(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = false ;
            
               
                
            }
            
            function invokeUnCheckAllPin(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = false ;
            
               
                
            }
            
            function invokeDistribute(opType)
            {

                if(opType== 'card'){ 
                    answer = confirm("Are you sure you want to continue this process?")
                    
                    if(answer !=0){
                        document.selectCard.action="${pageContext.request.contextPath}/UpdateCardPinDistributionServlet?opType="+opType;
                        document.selectCard.submit();
                    }
                }
                
                if(opType== 'pin'){  
                    answer = confirm("Are you sure you want to continue this process?")
                
                    if(answer !=0){
                        document.selectPin.action="${pageContext.request.contextPath}/UpdateCardPinDistributionServlet?opType="+opType;
                        document.selectPin.submit();
                    }
                }

            }
            
            
            
            function invokeSearch(opType)
            {
                if(opType== 'card'){ 
                    document.cardDistributionSearchForm.action="${pageContext.request.contextPath}/SearchCardPinDistributionServlet?opType="+opType;
                    document.cardDistributionSearchForm.submit();
                }
                
                if(opType== 'pin'){  
                    document.pinDistributionSearchForm.action="${pageContext.request.contextPath}/SearchCardPinDistributionServlet?opType="+opType;
                    document.pinDistributionSearchForm.submit();
                }

            }
            
            
            function valueSet(opType){
                
                if(opType == 'card'){
                    document.cardDistributionSearchForm.action="${pageContext.request.contextPath}/GetCardProductByCardTypeServlet?opType="+opType;
                    document.cardDistributionSearchForm.submit();
                }
                if(opType == 'pin'){
                    
                    document.pinDistributionSearchForm.action="${pageContext.request.contextPath}/GetCardProductByCardTypeServlet?opType="+opType;
                    document.pinDistributionSearchForm.submit();
                }
            }
            
        </script> 
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_PIN_DISTRIBUTION%>'                                  
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
    <body >



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


                                <!--  -----------------------------------------------------------------   start  developer area  ----------------------------------------------------------                          -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <!-----------------------------------///////////////////////////////        Add Area       //////////////////////////////////------------------------------------->



                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Card Distribution</a></li>
                                        <li><a href="#tabs-2">Pin Distribution</a></li>
                                    </ul>

                                    <!--  ////////////////////////      Tab Number1        /////////////////////                            -->

                                    <div id="tabs-1" >


                                        <form name="cardDistributionSearchForm" method="post" >

                                            <table cellpadding="0" cellspacing="10"  >

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Number
                                                    </td>
                                                    <td><input type="text" name="cardNumber"  maxlength="16" value="${distCardBean.cardNumber}"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Type 
                                                    </td>
                                                    <td style="width: 200px;"><select  name="cardType" onchange="valueSet('${"card"}')"  >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cdTypeList}">

                                                                <c:if test="${distCardBean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${distCardBean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Product
                                                    </td>
                                                    <td style="width: 200px;"><select name="cardProduct"  >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="product" items="${sessionScope.SessionObject.cardProductList}">

                                                                <c:if test="${distCardBean.cardProduct==product.key}">
                                                                    <option value="${product.key}" selected="true" >${product.value}</option>
                                                                </c:if>
                                                                <c:if test="${distCardBean.cardProduct!=product.key}">
                                                                    <option value="${product.key}">${product.value}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Category 
                                                    </td>
                                                    <td style="width: 200px;"><select  name="cardCategory" >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="category" items="${sessionScope.SessionObject.cardCategoryLst}">

                                                                <c:if test="${distCardBean.cardCategory==category.categoryCode}">
                                                                    <option value="${category.categoryCode}" selected="true" >${category.description}</option>
                                                                </c:if>
                                                                <c:if test="${distCardBean.cardCategory!=category.categoryCode}">
                                                                    <option value="${category.categoryCode}">${category.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                            </table>

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="search"  style="width: 100px; height: 30px;" onclick="invokeSearch('card')" value="Search">
                                                        <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeReset('card')" value="Reset">

                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                        <br></br>
                                        <form name="selectCard" method="post">
                                            <table  border="1"  class="display" id="tableview">
                                                <thead>
                                                    <tr class="gradeB">
                                                        <th>Card Number</th>
                                                        <th>Card Type</th>
                                                        <th>Card Product</th>
                                                        <th>Card Category</th>


                                                        <th >Select</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <c:forEach var="dist" items="${distCardList}">
                                                        <tr>

                                                            <td >${dist.cardNumber}</td>
                                                            <td >${dist.cardTypeDes}</td>
                                                            <td >${dist.cardProductDes}</td>
                                                            <td >${dist.cardCategoryDes}</td>

                                                            <td  >
                                                                <input type="checkbox" name="checkedVelue" value="${dist.cardNumber}">
                                                                <input type="hidden" name="checkedAppId" value="${dist.applicationId}">
                                                            </td>

                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table> 

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="checkAll"  style="width: 100px; height: 30px;" onclick="invokeCheckAllCard(document.selectCard.checkedVelue)" value="Check All">
                                                        <input type="button" name="uncheckAll"  style="width: 100px; height: 30px;" onclick="invokeUnCheckAllCard(document.selectCard.checkedVelue)" value="Uncheck All">
                                                        <input type="button" name="distribute"  style="width: 100px; height: 30px;" onclick="invokeDistribute('card')" value="Distribute">
                                                        <input type="button" name="cancel"  style="width: 100px;height: 30px;" onclick="invokeCancel('card')" value="Reset All">
                                                    </td>
                                                </tr>

                                            </table></form>


                                        <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////--> 

                                    </div>














                                    <!--    /////////////////    Tab Number2         ///////////////////// -->

                                    <div id="tabs-2">

                                        <form name="pinDistributionSearchForm" method="post" >

                                            <table cellpadding="0" cellspacing="10"  >

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Number
                                                    </td>
                                                    <td><input type="text" name="cardNumber"  maxlength="16" value="${distPinBean.cardNumber}"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Type 
                                                    </td>
                                                    <td style="width: 200px;"><select name="cardType" onchange="valueSet('${"pin"}')" >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cdTypeList}">

                                                                <c:if test="${distPinBean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${distPinBean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Product 
                                                    </td>
                                                    <td style="width: 200px;"><select  name="cardProduct"  >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="product" items="${sessionScope.SessionObject.cardProductList}">

                                                                <c:if test="${distPinBean.cardProduct==product.key}">
                                                                    <option value="${product.key}" selected="true" >${product.value}</option>
                                                                </c:if>
                                                                <c:if test="${distPinBean.cardProduct!=product.key}">
                                                                    <option value="${product.key}">${product.value}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">
                                                        Card Category 
                                                    </td>
                                                    <td style="width: 200px;"><select  name="cardCategory"  >
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="category" items="${sessionScope.SessionObject.cardCategoryLst}">

                                                                <c:if test="${distPinBean.cardCategory==category.categoryCode}">
                                                                    <option value="${category.categoryCode}" selected="true" >${category.description}</option>
                                                                </c:if>
                                                                <c:if test="${distPinBean.cardCategory!=category.categoryCode}">
                                                                    <option value="${category.categoryCode}">${category.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="search"  style="width: 100px; height: 30px;" onclick="invokeSearch('pin')" value="Search">
                                                        <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeReset('pin')" value="Reset">

                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                        <br></br>
                                        <form name="selectPin" method="post">
                                            <table  border="1"  class="display" id="tableview">
                                                <thead>
                                                    <tr class="gradeB">
                                                        <th>Card Number</th>
                                                        <th>Card Type</th>
                                                        <th>Card Product</th>
                                                        <th>Card Category</th>
                                                        <th>Select</th>

                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <c:forEach var="dist" items="${distPinList}">
                                                        <tr>

                                                            <td >${dist.cardNumber}</td>
                                                            <td >${dist.cardTypeDes}</td>
                                                            <td >${dist.cardProductDes}</td>
                                                            <td >${dist.cardCategoryDes}</td>

                                                            <td  >
                                                                <input type="checkbox" name="checkedVelue" value="${dist.cardNumber}">
                                                            </td>

                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>  

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="checkAll"  style="width: 100px; height: 30px;" onclick="invokeCheckAllPin(document.selectPin.checkedVelue)" value="Check All">
                                                        <input type="button" name="uncheckAll"  style="width: 100px; height: 30px;" onclick="invokeUnCheckAllPin(document.selectPin.checkedVelue)" value="Uncheck All">
                                                        <input type="button" name="distribute"  style="width: 100px; height: 30px;" onclick="invokeDistribute('pin')" value="Distribute">
                                                        <input type="button" name="cancel"  style="width: 100px; height: 30px;" onclick="invokeCancel('pin')" value="Reset All">
                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////-->

                                    </div>

                                </div>


                                <!------------------------------------------------  End Developer Area  --------------------------------------------------------------->
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