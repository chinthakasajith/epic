<%-- 
    Document   : chequeprocess
    Created on : Jun 11, 2013, 4:21:26 PM
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
                { pagecode:'<%= PageVarList.BO_PAY_CHEQUE_REALISE%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }         
            
            
            function backcheq(){
                window.location="${pageContext.request.contextPath}/LoadChequeRealizeServlet";
                
            }
            function processcheq2(cheqnum,param){
                window.location="${pageContext.request.contextPath}/LoadChequeRealizeServlet?cheqnum="+cheqnum+"&param="+param;
                
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
                               
                                <form name="cheqprocessform" method="POST" action="" >
                                    
                                    <table border="0" cellpadding="0" cellspacing="10" >

                                        <tr>
                                            <td style="width: 200px;">Cheque Number </td>

                                            <td >:${bean2.cheqNum}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width: 100px;">Cheque Bank</td>

                                            <td>:${bean2.cheqBankName}</td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Payment Date</td>
                                                
                                            <td>:${bean2.payDate}</td>
                                        </tr>                                        
                                        
                                        <tr>
                                            <td style="width: 100px;">Currency Type</td>
                                            <td>:${bean2.curTypeDes} </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Amount</td>
                                            <td>:${bean2.amount}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width: 100px;">Status</td>
                                            <td>:${bean2.statusDes} </td>
                                        </tr>
                                        </table>
                                        <table border="0" cellpadding="0" cellspacing="10" >
                                        
                                        
                                        <tr>
                                            <td > <input type="button" onclick="processcheq2('${bean2.cheqNum}','realise')" value="Realise" class="defbutton"/></td>
                                            <td > <input type="button" onclick="processcheq2('${bean2.cheqNum}','return')" value="Return" class="defbutton"/></td>

                                            <td ><input type="button"  onclick="backcheq()" value="Back" class="defbutton"/> </td>
                                        </tr>
                                        

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
