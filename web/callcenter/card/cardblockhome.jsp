<%-- 
    Document   : cardblockhome
    Created on : Jul 24, 2012, 11:59:38 AM
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
            function invokeCancel(cardNo)
            {
                
                document.cardblock.action="${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=CCCARD";
                document.cardblock.submit();
                
                
            }
           
        </script>  
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDBLOCK%>'                                  
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
                                    <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                    <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                <c:if test="${operationtype=='block'}" >

                                    <form method="POST" name="cardblock" action="${pageContext.request.contextPath}/ManageCardBlockServlet">
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td>
                                                        : ${cardBean.cardNumber}
                                                        <input type="hidden" value="${cardBean.cardNumber}" name="cardno"/>
                                                        <input type="hidden" value="${cardBean.cardDomain}" name="carddomain">
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td>
                                                        : ${cardBean.cardTypeDes}
                                                        <input type="hidden" value="${cardBean.cardType}" name="cardtype"/>
                                                        <input type="hidden" value="${cardBean.cardTypeDes}" name="cardtypedes"/>
                                                    </td>                                            
                                                </tr>
                                                <tr>
                                                    <td>Card Category</td>
                                                    <td>
                                                        : ${cardBean.cardCatDes}
                                                        <input type="hidden" value="${cardBean.cardCategory}" name="cardcategory"/>
                                                        <input type="hidden" value="${cardBean.cardCatDes}" name="cardcategorydes"/>
                                                    </td>
                                                </tr>
                                                <c:if test="${cardBean.cardDomain =='CREDIT'}" > 
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
                                                </c:if>
                                                <tr>
                                                    <td>Current Status</td>
                                                    <td>
                                                        : ${cardBean.statusDes}
                                                        <input type="hidden" value="${cardBean.statusDes}" name="statusdes"/>
                                                        <input type="hidden" value="${cardBean.currentStatus}" name="currentstatus"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>New Status</td>
                                                    <td>: 
                                                        <select name="newstatus" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="new" items="${newstatus}">

                                                                <c:if test="${cardBean.newStatus==new.key}">
                                                                    <option value="${new.key}" selected>${new.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.newStatus!=new.key}">
                                                                    <option value="${new.key}">${new.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Block Reason</td>
                                                    <td>: 
                                                        <select name="blockreason">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="block"   items="${blockreason}">
                                                                <c:if test="${cardBean.blockReason==block.value}">
                                                                    <option value="${block.value}" selected>${block.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.blockReason!=block.value}">
                                                                    <option value="${block.value}">${block.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Remark</td>
                                                    <td>: 
                                                        <textarea name="remarks" maxlength="512">${cardBean.remark}</textarea>                                                
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td>&nbsp;  
                                                        <input type="submit" class="defbutton" name="block" value="Block" /> 
                                                        <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('${cardBean.cardNumber}')"/></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>

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
