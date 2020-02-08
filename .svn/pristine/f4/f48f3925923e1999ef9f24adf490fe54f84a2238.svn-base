<%-- 
    Document   : eodfilegeneratehome
    Created on : Aug 8, 2012, 11:00:44 AM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeGenerate()
            {

                document.eodfileform.action="${pageContext.request.contextPath}/GenerateEodFileServlet";
                document.eodfileform.submit();

            }
            

            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadEodFileGenerationServlet";

            }
            


        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.EOC_FILE_GENE%>'                                  
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


        <title>EPIC CMS EOD FILE GENERATION HOME</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="eodfileform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                            <td></td>
                                        </tr>
                                        <tr><td style="height: 5px"></td></tr>  
                                    </table>

                                    <table border="0">

                                        <tbody>

                                            <tr>
                                                <td style="width: 180px">Start Time</td>

                                                <td>
                                                    <input  name="stime" readonly maxlength="16" value="${stime}" key="stime" id="stime"  />

                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#stime" ).datepicker({
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
                                                    </script>

                                                </td>

                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>  

                                            <tr>
                                                <td style="width: 180px">End Time</td>

                                                <td>
                                                    <input  name="etime" readonly maxlength="16" value="${etime}" key="etime" id="etime"  />

                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#etime" ).datepicker({
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
                                                    </script>

                                                </td>

                                            </tr>



                                        </tbody>
                                    </table>


                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td style="width: 180px"></td>
                                                <td style="width: 300px"><input type="button" value="Generate" name="generate" style="width: 100px" onclick="invokeGenerate()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.EOC_FILE_GENE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>


                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>File Id</th>
                                                <th>File Name</th>
                                                <th>Status</th>
                                                <th>EOD Process</th>
                                                <th>Create User</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="eodfile" items="${requestScope.settlementFileBeanList}">
                                                <tr>
                                                    <td >${eodfile.fileId}</td>
                                                    <td >${eodfile.fileName}</td>
                                                    <td >${eodfile.statusDes}</td>
                                                    <td >${eodfile.isTakenToEod}</td>
                                                    <td >${eodfile.lastUpdatedUser}</td>
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


