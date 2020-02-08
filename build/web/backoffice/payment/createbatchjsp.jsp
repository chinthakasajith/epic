<%-- 
    Document   : createbatchjsp
    Created on : Apr 4, 2013, 2:36:51 PM
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
            
            function payreset(){
                
                window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=newBat";
            }
            
            function submitForm(){
                document.createbatchform.action="${pageContext.request.contextPath}/AddNewBatchServlet";
                document.createbatchform.submit();
                
               // window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=newBat";
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

                                <form name="createbatchform" method="POST" action="${pageContext.request.contextPath}/AddNewBatchServlet" >
                                    <table border="0" cellpadding="0" cellspacing="10">

                                        <tr>
                                            <td style="width: 200px;">Last Batch ID</td>
                                            <td>:</td>
                                            <td>${bean1.batchId}</td>

                                        </tr>
                                        
                                        <tr>                                            
                                            <td>Last Batch Created Date</td>
                                            <td>:</td>
                                            <td>${bean1.createdDate}</td>

                                        </tr>

                                        <tr><td><input type="hidden" name="statuss" value="${bean1.status}"/></td>
                                            <td><input type="hidden" name="lastbatchid" value="${bean1.batchId}"/></td></tr>
                                    

                                        <tr>
                                            <td style="width: 100px;">Date </td>
                                            <td >: </td>

                                            <td><input type="text" readonly name="date" value="${date}" style="width: 150px;"/> </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">User Name</td>
                                            <td >: </td>

                                            <td><input type="text" readonly name="username" value="${user}" style="width: 150px;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Password</td>
                                            <td >: </td>

                                            <td><input type="password"  name="password" value="${password}" style="width: 150px;"/></td>
                                        </tr>



                                        <tr style="height: 10px;"></tr>
                                       
                                        <tr>
                                            <td > </td>
                                            <td > </td>
                                            <td><input type="button" value="Create" name="create" class="defbutton" onclick="submitForm()" />
                                                <input type="button" value="Reset" name="reset" class="defbutton" onclick="payreset()"/></td>
                                        </tr>

                                    </table>

                                </form>

                                
                                <br/>

                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Batch ID</th>
                                                <th>Created Date</th>
                                                <th>Total Txn Count</th>                                
                                                <th>Total Txn Amount</th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach  items="${todBatchList}" var="bat">
                                                <tr>
                                                    <td>${bat.batchId}</td>
                                                    <td>${bat.batchCreatedDate}</td>
                                                    <td>${bat.totalTxnCount}</td>
                                                    <td>${bat.totalTxnAmount}</td>

                                                </tr>
                                            </c:forEach>

                                        </tbody>

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


