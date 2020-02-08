<%-- 
    Document   : view_txn_adjustment
    Created on : Mar 6, 2013, 3:27:35 PM
    Author     : ruwan_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <script>
            function invokeBack()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardTxnAdjustment";

            }
            
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_TXN_ADJUSTMENT%>'                                  
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
        <style>
            .t{
                width: 200px;
                font-weight: bold;
            }
            tr{
                padding-bottom: 50px
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >
            <div class="header">
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
                                
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table border="0">
                                    <tr><td style="height: 15px"></td></tr>
                                    <tr>
                                        <td class="t">Adjustment ID </td>
                                        <td>${taBean.id}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Card Number </td>
                                        <td>${taBean.uniqueId}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Expiry Date </td>
                                        <td>${taBean.verificationValue}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Adjustment Type </td>
                                        <td>
                                            <c:if test="${taBean.adjustmentType=='1'}">
                                                Transaction
                                            </c:if> 
                                            <c:if test="${taBean.adjustmentType=='2'}">
                                                Fee
                                            </c:if>
                                            <c:if test="${taBean.adjustmentType=='3'}">
                                                Interest
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Type </td>
                                        <td>${taBean.transactionType}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Amount </td>
                                        <td>${taBean.amount}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">CR or DR</td>
                                        <td>
                                            <c:if test="${taBean.crOrdr=='CR'}">
                                                Credit
                                            </c:if> 
                                            <c:if test="${taBean.crOrdr=='DR'}">
                                                Debit
                                            </c:if></td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Currency Type </td>
                                        <td>${taBean.currencyCode}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Trace Number </td>
                                        <td>${taBean.traceNo}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Status </td>
                                        <td>${taBean.status}</td>
                                    </tr>
                                    <tr><td style="height: 5px"></td></tr>
                                    <tr>
                                        <td class="t">Remarks </td>
                                        <td><c:if test="${taBean.remarks==null}">
                                                <i>No Remarks Specified</i>
                                            </c:if>${taBean.remarks}</td>
                                    </tr>
                                    
                                    <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td style="width: 300px">
                                                    <input type="button" value="Back" name="Back" class="defbutton" onclick="invokeBack()"/> 
                                                </td>                                               
                                            </tr>

                                </table>

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
