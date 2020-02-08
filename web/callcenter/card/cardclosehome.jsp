<%-- 
    Document   : cardclosehome
    Created on : Aug 3, 2012, 11:51:42 AM
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
            function invokeReset(cardNo){
                window.location = "${pageContext.request.contextPath}/LoadCardCloseServlet?cardno="+cardNo;
            }
            
            function invokeClose(reasonCode)
            {
                
                document.cardclose.action="${pageContext.request.contextPath}/ManageCardCloseServlet?reasonCode="+reasonCode;
                document.cardclose.submit();
                
            }
            
            function invokeCancel(cardNo)
            {
                
                document.cardclose.action="${pageContext.request.contextPath}/ViewCustomerMgtServlet?id="+cardNo+"&section=CCCARD";
                document.cardclose.submit();
                
            }
           
        </script>  
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDCLOSE%>'                                  
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


                                <c:if test="${operationtype=='close'}" >

                                    <form method="POST" name="cardclose" action="${pageContext.request.contextPath}/ManageCardCloseServlet">
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td >           
                                                        : ${cardBean.cardNumber}
                                                        <input type="hidden" value="${cardBean.cardNumber}" name="cardno"/>
                                                        <input type="hidden" value="${cardBean.cardDomain}" name="carddomain">
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
                                                    <tr>
                                                        <td >Credit Available</td>
                                                        <td>
                                                            : ${cardBean.creditAvlbl}
                                                            <input type="hidden" value="${cardBean.creditAvlbl}" name="crdtavailable"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td >Cash Available</td>
                                                        <td>
                                                            : ${cardBean.cashAvlbl}
                                                            <input type="hidden" value="${cardBean.cashAvlbl}" name="cashavailable"/>
                                                        </td>
                                                    </tr>
                                                </c:if>   
                                                <tr>
                                                    <td>Status</td>
                                                    <td>
                                                        : ${cardBean.statusDes}
                                                        <input type="hidden" value="${cardBean.currentStatus}" name="currentstatus"/>
                                                        <input type="hidden" value="${cardBean.statusDes}" name="statusdes"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Reason</td>
                                                    <td>: 
                                                        <select name="reason">
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
                                                        <input type="submit" class="defbutton" name="close" value="Close" onclick="invokeClose('CDCL')"/>
                                                        <input type="reset" class="defbutton" name="reset" value="Reset" onclick="invokeReset('${cardBean.cardNumber}')"/> 
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
