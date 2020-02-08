<%-- 
    Document   : loginjsp
    Created on : Apr 4, 2013, 11:04:26 AM
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
            
            function NewBatch(val){
                window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param="+val;
                             
            }
            
            function ResumeBatch(val){              
                    window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param="+val;   
                             
            }
            
            function ResumeBatchCOPY(val){
                
                pass = prompt("Password:");
                window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=reschek&pass="+pass;
                                               
                <c:if test="${userok=='ok'}">
                    window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param="+val;
                </c:if>
                                
                
                             
            }
            
            function closefdf(val){
                
                window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param="+val;
                             
            }
            
             function ClosBatch(val,id,tc,ta){
                 
                answer = confirm("Do you really want to close the batch ID: " + id + "\n\nTotal transaction count: " + tc + "\n\nTotal transaction amount: " + ta)
                
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param="+val;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadPaymentAndBatchServlet?param=''";
                }
                

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

                                        <form>
                                <table border="0">

                                    <tr>  
                                        <td style="width: 400px;" >
                                            <fieldset id="fieldst">
                                                <table border="0">  
                                                    <tr style="height: 20px;" ></tr>
                                                    <tr>
                                                        <td style="width: 30px;"> </td>
                                                        <c:if test="${bean1.batchId != null}">
                                                            <td> Last Batch Id: ${bean1.batchId}</td>
                                                        </c:if>                                                        
                                                    </tr> 
                                                    <tr > <td> <input type="hidden" name="batchid" value="${bean1.batchId}" /></td></tr>
                                                </table>

                                                
                                                <table border="0">                                            
                                                    <tr style="height: 20px;" ></tr>

                                                    <tr>
                                                        <td style="width: 30px;"></td>

                                                        <c:if test="${bean1.status == 'BMRE' || bean1.status == null}">
                                                            <td><input type="button" value="New" name="new" class="defbutton" onclick="NewBatch('newBat')" /></td>
                                                            <td><input type="button" value="Resume" name="resume" class="defbutton" onclick="" disabled="true"/></td>
                                                            <td><input type="button" value="Close" name="close" class="defbutton" onclick="" disabled="true"/></td>
                                                            </c:if>

                                                        <c:if test="${bean1.status == 'BMOP'}">
                                                            <td><input type="button" value="New" name="new" class="defbutton" onclick="" disabled="true"/></td>
                                                            <td><input type="button" value="Resume" name="resume" class="defbutton" onclick="ResumeBatch('reschek')"/></td>
                                                            <td><input type="button" value="Close" name="close" class="defbutton" 
                                                                       onclick="ClosBatch('close','${bean1.batchId}','${bean1.totalTxnCount}','${bean1.totalTxnAmount}')"/></td>
                                                            </c:if>

                                                        <c:if test="${bean1.status == 'BMCL'}">
                                                            <td>Batch closed, not reconciled.</td>
                                                        </c:if>
                                                            
                                                        <c:if test="${bean1.status == 'UIN'}">
                                                            <td>User's previous batch in another machine not reconciled.</td>
                                                        </c:if> 
                                                        
                                                        <c:if test="${bean1.status == 'IPIN'}">
                                                            <td>Previous user's batch not reconciled.</td>
                                                        </c:if>    
                                                            
                                                        <c:if test="${exp == 'exp' && bean1.status == 'EXP'}">
                                                            <td><input type="button" value="New" name="new" class="defbutton" onclick="" disabled="true"/></td>
                                                            <td><input type="button" value="Resume" name="resume" class="defbutton" onclick="" disabled="true"/></td>   
                                                            <td><input type="button" value="Close" name="close" class="defbutton" 
                                                                       onclick="ClosBatch('close','${bean1.batchId}','${batchbean.totalTxnCount}','${batchbean.totalTxnAmount}')"/></td>
                                                        </c:if>

                                                    </tr>
                                                    <tr style="height: 40px;" ></tr>


                                                </table>
                                                
                                            </fieldset>
                                        </td>
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

