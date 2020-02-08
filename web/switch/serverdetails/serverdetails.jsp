<%-- 
    Document   : serverdetails
    Created on : May 2, 2012, 11:30:33 AM
    Author     : badrika
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
        <!--        include content.jsp for all js and css inclusion-->
        <script type="text/javascript">
            
            function serverOperations(index){
                
                if(index == '0'){
                    answer = confirm("Are you sure you want to start the ECMS server ?")
                }else if(index == '1'){
                    answer = confirm("Are you sure you want to restart the ECMS server ?")
                }else if(index == '2'){
                    answer = confirm("Are you sure you want to stop the ECMS server ?")
                }else if(index == '3'){
                    answer = confirm("Are you sure you want to clear logs ?")
                }else if(index == '4'){
                    answer = confirm("Are you sure you want to clear all logs ?")
                }else if(index == '5'){
                    answer = confirm("Are you sure you want to backup logs ?")
                }else if(index == '6'){
                    answer = confirm("Are you sure you want to remove backups ?")
                }else if(index == '7'){
                    answer = confirm("Are you sure you want to restart the system ?")
                }else if(index == '8'){
                    answer = confirm("Are you sure you want to shutdown the system ?")
                }
                
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/ServerOperationsServlet?index="+index;
                }
                else
                {
                    
                }
                
                
            }
            
            function refreshPage(){
             window.location = "${pageContext.request.contextPath}/LoadServerDetailsServlet";
             
            }
            
            $(document).ready(function(){
                setTimeout(refreshPage,8000);
            });
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SERVERDETAILS%>'                                  
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



                <div class="content" style="height: 500px">

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------  -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form method="POST" action="">
                                    <br/>
                                    <table> <tr> <td> <b> Application Information </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                    <table cellpadding="0" cellspacing="10" >

                                        <tr>
                                            <td style="width: 180px;">ECMS Server Version</td>
                                            <td><b><i><font color="#000000" style="font-size: 12px;"> ${versionInfo.versionNo}</font> </i></b></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 180px;">Security Module Version</td>
                                            <td><b><i><font color="#000000" style="font-size: 12px;"> ${versionInfo.securityModuleNo}</font></i></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 180px;">Build Number</td>
                                            <td><b><i><font color="#000000" style="font-size: 12px;"> ${versionInfo.buildModuleNo}</font></i></b> </td>
                                        </tr>
                                    </table>

                                    <br/>

                                    <table> <tr> <td> <b> Server Status </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>                 

                                    <table cellpadding="0" cellspacing="10">

                                        <tr>
                                            <td style="width: 180px;">System Running Status</td>
                                                
                                            <c:if test="${status == '1'}">
                                                <td><b><i><font color="#009900"> Running</font></i></b> </td>   
                                            </c:if>
                                                            
                                            <c:if test="${status != '1'}">
                                                <td><b><i><font color="#FF0000"> Not Running</font></i></b> </td> 
                                            </c:if>
                                                            
                                                            
                                        </tr>
                                        <tr>
                                            <td style="width: 180px;">Total Fail Count</td>
                                            <td><b><i><font color="#000000" style="font-size: 12px;"> ${failCount}</font> </i></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 180px;">Total  Transaction Alert Count</td>
                                            <td><b><i><font color="#000000" style="font-size: 12px;"> ${tranCount}</font></b> </i></td>
                                        </tr>
                                    </table>

                                    <br/>



                                    <br/>

                                    <table> <tr> <td> <b> ECMS Server Properties </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>                    

                                    <table>

                                        <tr>
                                            <td style="width: 120px;"><input type="button" value="Start" name="start" style=" width:100px" onclick="serverOperations('0')" /></td>
                                            <td style="width: 120px;"><input type="button" value="Restart" name="restart" style=" width:100px" onclick="serverOperations('1')" /></td>
                                            <td style="width: 120px;"><input type="button" value="Stop" name="stop" style=" width:100px" onclick="serverOperations('2')"  /></td>
                                        </tr>                        

                                    </table>

                                    <br/>

                                    <table> <tr> <td> <b> ECMS Log Properties </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>                    

                                    <table>

                                        <tr>
                                            <td style="width: 120px;"><input type="button" value="Clear Logs" name="start" style=" width:120px" onclick="serverOperations('3')" /></td>
                                            <td style="width: 120px;"><input type="button" value="Clear All Logs" name="restart" style=" width:120px" onclick="serverOperations('4')" /></td>
                                            <td style="width: 120px;"><input type="button" value="Log Backup" name="stop" style=" width:120px" onclick="serverOperations('5')" /></td>
                                            <td style="width: 120px;"><input type="button" value="Remove Backup Logs" name="stop" style=" width:120px" onclick="serverOperations('6')" /></td>
                                        </tr>                        

                                    </table>

                                    <br/>

                                    <table> <tr> <td> <b> System Properties </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>                    

                                    <table>

                                        <tr>
                                            <td style="width: 120px;"><input type="button" value="Restart" name="mrestart" style=" width:100px" onclick="serverOperations('7')" /></td>
                                            <td style="width: 120px;"><input type="button" value="Shut Down" name="mstop" style=" width:100px" onclick="serverOperations('8')" /></td>
                                        </tr>                        

                                    </table>


                                    <br/>

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
