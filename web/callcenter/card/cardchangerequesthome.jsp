<%-- 
    Document   : cardchangehome
    Created on : Aug 14, 2012, 8:29:15 AM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" >
            function enableCardTp(id1)
            {
                
                $(id1).attr("disabled",false);              
                
            }
            
            function disableCardTp(id1){
                
                $(id1).attr("disabled",true);
                window.location = "${pageContext.request.contextPath}/LoadCardChangeRequestServlet";                 
                
            }
                        
            function loadCdProduct(cardType)
            {
                
                document.cardrequest.action="${pageContext.request.contextPath}/ChangeCardProductServlet?cardType="+cardType;
                document.cardrequest.submit();
            }            
                       
            function invokeReset()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadCardChangeRequestServlet";
                
            }
            
            function invokeChangeCard()
            {
                
                answer = confirm("Do you really want to place arequest?")
                   
                if (answer !=0)
                {
                    document.cardrequest.action="${pageContext.request.contextPath}/ManageCardChangeRequestServlet";
                    document.cardrequest.submit();
                }
                else
                {
                    
                    document.cardrequest.action="${pageContext.request.contextPath}/LoadCardChangeRequestServlet";
                    document.cardrequest.submit();
                    
                }
                 
//                document.cardrequest.action="${pageContext.request.contextPath}/ManageCardChangeRequestServlet";
//                document.cardrequest.submit();
//                
            }
            
            function invokeCancel(cardNo)
            {
                
                document.cardrequest.action="${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+cardNo+"&section=CCCARD";
                document.cardrequest.submit();
                
            }
        </script>  

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



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="center">  CARD CHANGE REQUEST </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                <c:if test="${operationtype=='cardchange'}" >

                                    <form method="POST" name="cardrequest" action="${pageContext.request.contextPath}/ManageCardRequestServlet">
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td on>           
                                                        : ${cardBean.cardNumber}
                                                        <input type="hidden" value="${cardBean.cardNumber}" name="cardno"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type </td>
                                                    <td>
                                                        : ${cardBean.cardTypeDes}
                                                        <input type="hidden" value="${cardBean.cardType}" name="cardtype"/>
                                                        <input type="hidden" value="${cardBean.cardTypeDes}" name="cardtypedes"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Product </td>
                                                    <td>
                                                        : ${cardBean.cardProdDes}
                                                        <input type="hidden" value="${cardBean.cardProduct}" name="cardproduct"/>
                                                        <input type="hidden" value="${cardBean.cardProdDes}" name="cardproductdes"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Category</td>
                                                    <td>
                                                        : ${cardBean.cardCatDes}
                                                        <input type="hidden" value="${cardBean.cardCategory}" name="cardcategory"/>
                                                        <input type="hidden" value="${cardBean.cardCatDes}" name="cardcategorydes"/>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td >Credit Limit</td>
                                                    <td>
                                                        : ${cardBean.creditLimit}
                                                        <input type="hidden" value="${cardBean.creditLimit}" name="creditlimit"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >Cash Limit</td>
                                                    <td>
                                                        : ${cardBean.cashLimit}
                                                        <input type="hidden" value="${cardBean.cashLimit}" name="cashlimit"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Expiry Date</td>
                                                    <td>
                                                        : ${cardBean.expDate}
                                                        <input type="hidden" value="${cardBean.expDate}" name="expirydate"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Request Type</td>
                                                    <td>:
                                                        <c:if test="${typeEnable == 'yes'}">
                                                            <input id="reason" type="radio" name="reason" value="TYPE" onchange="enableCardTp(cdType)" checked=""/>Change Card Type
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                            <input id="reason" type="radio" name="reason" value="PRODUCT" onchange="disableCardTp(cdType)" /> Change Card Product
                                                        </c:if>
                                                        <c:if test="${typeEnable == 'no'}">
                                                            <input id="reason" type="radio" name="reason" value="TYPE" onchange="enableCardTp(cdType)" />Change Card Type
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                            <input id="reason" type="radio" name="reason" value="PRODUCT" onchange="disableCardTp(cdType)" checked=""/> Change Card Product
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr><td></td></tr>
                                                <tr >
                                                    <td>Requested Card Type</td>
                                                    <td>: 
                                                        <select id="cdType" style="width: 100px" name="newcardtype" onchange="loadCdProduct(cdType.value)" disabled="">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="type" items="${cardTypeList}">
                                                                <c:if test="${cardBean.newCardType==type.key}">
                                                                    <option value="${type.key}" selected>${type.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.newCardType != type.key}">
                                                                    <option value="${type.key}" >${type.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr  >
                                                    <td>Requested Card Product</td>
                                                    <td>: 
                                                        <select style="width: 100px" name="newcardproduct" id="cdProduct" >
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="product" items="${cardProduct}">
                                                                <c:if test="${cardBean.newCardProduct==product.key}">
                                                                    <option value="${product.key}" selected>${product.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.newCardProduct != product.key}">
                                                                    <option value="${product.key}" >${product.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>


                                                <tr >
                                                    <td>Priority Level</td>
                                                    <td>: 
                                                        <select style="width: 100px" name="prioritycode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Remark</td>
                                                    <td>: 
                                                        <textarea name="remarks" maxlength="512" >${cardBean.remark}</textarea>                                                
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>   
                                                        <input type="submit" class="defbutton" name="replace" value="Change" onclick="invokeChangeCard()"/> 
                                                        <input type="reset" class="defbutton" name="reset" value="Reset" onclick="invokeReset()"/> 
                                                        <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('${cardBean.cardNumber}')"/></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>


                                </c:if>

                                <script>
                                        
                                    <c:if test="${typeEnable == 'yes'}">
                                        enableCardTp(cdType)
                                    </c:if>
                                        
                                </script>

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

