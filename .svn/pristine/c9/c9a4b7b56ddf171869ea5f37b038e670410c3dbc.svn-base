<%-- 
    Document   : cardrenewhome
    Created on : Aug 8, 2012, 2:41:45 PM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>
            function valueSet(){                
                document.cardRenewSearchForm.action="${pageContext.request.contextPath}/GetCardProductToRenewServlet";
                document.cardRenewSearchForm.submit();                    
            }
            
            function invokeSearch()
            {
                document.cardRenewSearchForm.action="${pageContext.request.contextPath}/SearchCardRenewServlet";
                document.cardRenewSearchForm.submit();
            }
            
            function invokeCheckAllCard(field)
            {            
                for (i = 0; i < field.length; i++)
                    field[i].checked = true ;
            }
            
            function invokeUnCheckAllCard(field)
            {            
                for (i = 0; i < field.length; i++)
                    field[i].checked = false ;
            }
            
            function invokeRenew(){                
                document.selectCard.action="${pageContext.request.contextPath}/ApprovedCardRenewMgtServlet";
                document.selectCard.submit();         
            }
            
            function invokeReset(){                
                window.location = "${pageContext.request.contextPath}/LoadCardRenewMgtServlet";               
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_RENEW%>'                                  
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



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------      -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3">
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--  --------------search form  ------------    -->           

                                    <form name="cardRenewSearchForm" method="post" >

                                        <table cellpadding="0" cellspacing="10"  >

                                            <tr>
                                                <td style="width: 200px;"> Card Number </td>
                                                <td><input type="text" name="cardNumber"  maxlength="20" value="${csBean.cNumber}"/></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"> Card Type </td>
                                            <td style="width: 200px;"><select style="width: 150px;" name="cardType" onchange="valueSet()"  >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="type" items="${sessionScope.SessionObject.cdTypeList}">

                                                        <c:if test="${csBean.cType==type.cardTypeCode}">
                                                            <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                        </c:if>
                                                        <c:if test="${csBean.cType!=type.cardTypeCode}">
                                                            <option value="${type.cardTypeCode}">${type.description}</option>

                                                        </c:if>


                                                    </c:forEach>
                                                </select></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"> Card Product </td>                                                    
                                            <td style="width: 200px;"><select style="width: 150px;" name="cardProduct"  >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="product" items="${cardProductList}">

                                                        <c:if test="${csBean.cProduct==product.key}">
                                                            <option value="${product.key}" selected="true" >${product.value}</option>
                                                        </c:if>
                                                        <c:if test="${csBean.cProduct!=product.key}">
                                                            <option value="${product.key}">${product.value}</option>

                                                        </c:if>


                                                    </c:forEach>
                                                </select></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"> Card Category </td>
                                            <td style="width: 200px;"><select style="width: 150px;" name="cardCategory" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="category" items="${sessionScope.SessionObject.cardCategoryLst}">

                                                        <c:if test="${csBean.cCategory==category.categoryCode}">
                                                            <option value="${category.categoryCode}" selected="true" >${category.description}</option>
                                                        </c:if>
                                                        <c:if test="${csBean.cCategory!=category.categoryCode}">
                                                            <option value="${category.categoryCode}">${category.description}</option>

                                                        </c:if>


                                                    </c:forEach>
                                                </select></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"> Card Expiry Date </td>
                                            <td><input type="text" name="expDate"  maxlength="16" value="${csBean.expDate}"/>
<!--                                            <input  name="expDate" readonly maxlength="16" value="${csBean.expDate}" key="fromdate" id="fromdate2"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate2" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>-->
                                            
                                            
                                            </td>
                                        </tr>

                                    </table>

                                    <table  cellpadding="0" cellspacing="10">

                                        <tr celspacing="5">
                                            <td style="width: 200px;">
                                            </td>
                                            <td><input type="button" name="search"  style="width: 100px;" onclick="invokeSearch()" value="Search">
                                                <input type="button" name="reset"  style="width: 100px;" onclick="invokeReset()" value="Reset">

                                            </td>
                                        </tr>

                                    </table>
                                </form>        

                                <!--  --------------table view  ------------    -->

                                <br></br>
                                <form name="selectCard" method="post">
                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Card Number</th>
                                                <th>Expiry Date</th>
                                                <th>Status</th>

                                                <th>Select</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="card" items="${cardList}">
                                                <tr>

                                                    <td>${card.cNumber}</td>
                                                    <td>${card.expDate}</td>
                                                    <td>${card.statusDes}</td>

                                                    <td><input type="checkbox" name="checkedVelue" value="${card.cNumber}"></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 

                                    <table  cellpadding="0" cellspacing="10">

                                        <tr celspacing="5">
                                            <td style="width: 200px;">
                                            </td>
                                            <td><input type="button" name="checkAll"  style="width: 100px;" onclick="invokeCheckAllCard(document.selectCard.checkedVelue)" value="Check All"/>
                                                <input type="button" name="uncheckAll"  style="width: 100px;" onclick="invokeUnCheckAllCard(document.selectCard.checkedVelue)" value="Uncheck All"/>
                                                <input type="button" name="renew"  style="width: 100px;" onclick="invokeRenew()" value="Renew"/>
                                                <input type="button" name="cancel"  style="width: 100px;" onclick="invokeReset()" value="Reset All"/>
                                            </td>
                                        </tr>

                                    </table></form>


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
