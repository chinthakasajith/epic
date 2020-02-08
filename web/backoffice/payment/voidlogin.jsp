<%-- 
    Document   : voidlogin
    Created on : Apr 24, 2013, 1:59:34 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BO_PAYMENT%>'                                  
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
        
        <script>
            
     function backtores(){
        
        window.location="${pageContext.request.contextPath}//LoadPaymentAndBatchServlet?param=resume";
    
   
     }
     
     function backtologin(){
        
        window.location="${pageContext.request.contextPath}//LoadPaymentAndBatchServlet?param=no";
    
   
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

                                <br/>

                               
                                <c:if test="${operationtype == 'void'}">
                                    

                                    <form name="voidform" action="${pageContext.request.contextPath}/VoidPaymentServlet?param=voidconf" method="post" >

                                        <table border="0" cellpadding="0" cellspacing="10" >

                                            <tr>
                                                <td style="width: 150px;">Batch ID</td>
                                                <td >: </td>

                                                <td>${pay.batchId}</td>
                                            </tr>
                                            
                                            <tr>
                                                <td style="width: 100px;">Txn ID</td>
                                                <td >: </td>

                                                <td>${pay.txnId}</td>   
                                            </tr>
                                            
                                            <tr>
                                                <td style="width: 100px;">Card Holder Name</td>
                                                <td >: </td>

                                                <td>${pay.cardHolderName}</td>
                                            </tr>
                                            
                                            <tr>
                                                <td style="width: 100px;">Card Number</td>
                                                <td >: </td>

                                                <td>${pay.cardNumber}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Payment Type</td>
                                                <td >: </td>

                                                <td>${pay.paymentType}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Cheque Number</td>
                                                <td >: </td>

                                                <td>${pay.chequeNumber}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Cheque Bank</td>
                                                <td >: </td>

                                                <td>${pay.chequeBankName}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Payment Date</td>
                                                <td >: </td>

                                                <td>${pay.paymentDate}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Currency Type</td>
                                                <td >: </td>

                                                <td>${pay.curencyTypeDes}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Amount</td>
                                                <td >: </td>

                                                <td>${pay.amount}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Status</td>
                                                <td >: </td>

                                                <td>${pay.statusDes}</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Payment Status</td>
                                                <td >: </td>

                                                <td>${pay.payStatusDes}</td>
                                            </tr>                                                                             
                                            
                                            </table>
                                            <br/>
                                            <table border="0" cellpadding="0" cellspacing="10" >
                                                <tr> <td ><label>Do you want to continue payment void ?</label> </td> </tr> 
                                            </table>
                                            <br/>
                                            
                                            <table border="0" cellpadding="0" cellspacing="10" >

                                            <tr>
                                                <td style="width: 100px;">User Name</td>
                                                <td >: </td>

                                                <td><input type="text"  name="username" value="" style="width: 150px;"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Password</td>
                                                <td >: </td>

                                                <td><input type="password"  name="password" value="" style="width: 150px;"/></td>
                                            </tr>
                                            <tr><td > </td>
                                                <td > </td>
                                                <td><input type="hidden"  name="txnid" value="${txnid}" style="width: 150px;"/></td>
                                                
                                                
                                            </tr>
                                            <tr>
                                                <td > </td>
                                                <td > </td>
                                                <td><input type="hidden"  name="batid" value="${batid}" style="width: 150px;"/></td>
                                                
                                            </tr>
                                           
                                            <tr>
                                                <td><input type="submit" value="Ok" name="ok" class="defbutton" /></td>
                                                <td > </td>

                                                <td><input type="button" onclick="backtores()" value="Cancel" name="cancel" class="defbutton"/></td>

                                            </tr>


                                        </table>

                                    </form>
                                                
                                                
                                </c:if>  
                                
                                <c:if test="${operationtype == 'check'}">
                                    

                                    <form name="checkform" action="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=resconf" method="post" >

                                        <table border="0" cellpadding="0" cellspacing="10" >


                                            <tr>
                                                <td style="width: 100px;">User Name</td>
                                                <td >: </td>

                                                <td><input type="text" readonly="true" name="username" value="${user}" style="width: 150px;"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px;">Password</td>
                                                <td >: </td>

                                                <td><input type="password"  name="password" value="" style="width: 150px;"/></td>
                                            </tr>
                                            
                                            <tr style="height: 10px;"></tr>
                                            <tr>
                                                <td><input type="submit" value="Ok" name="ok" class="defbutton" /></td>
                                                <td > </td>

                                                <td><input type="button" onclick="backtologin()" value="Cancel" name="cancel" class="defbutton"/></td>

                                            </tr>


                                        </table>

                                    </form>
                                                
                                                
                              </c:if>
                              

                                
                                <br/>

                                







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



