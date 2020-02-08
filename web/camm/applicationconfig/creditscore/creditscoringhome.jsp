<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
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
        <script type="text/javascript">
           
            function  update(value){
                window.location = "${pageContext.request.contextPath}/ViewSectionMgtUpdateServlet?id="+value;
            }
            
            function deleteRow(value){
                var txt = new String(value);
             
                answer = confirm("Do you really want to delete "+value+" Section?")
                if (answer !=0 )
                {
                    window.location="${pageContext.request.contextPath}/DeleteSectionMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadSectionMgtServlet";
                }
                //                document.adduserroleform.submit();
                

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CREDITSCORING%>'                                  
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
                    <!--  ----------------------start  developer area  -----------------------------------                           -->
                    <table>
                        <tr>
                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                            </td>
                        </tr>
                    </table>
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                    <c:if test="${operationtype=='default'}" >
                        <form method="POST" action="${pageContext.request.contextPath}/AddCreditScoreServlet"">
                            <table border="0">
                                <tbody>
                                    <tr>
                                        <td>Application Id</td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <select name="appID">
                                                 <option value="" >--SELECT--</option>
                                                <option>dfg</option>
                                                <option>dfgdfgdf</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>Priority Level</td>
                                        <td>&nbsp;</td>
                                        <td> 
                                            <select name="prioLevel">
                                                <option value="" >--SELECT--</option>
                                                <c:forEach var="prioList" items="${prioList}">
                                                    <option value="${prioList.key}">${prioList.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>Name</td>
                                        <td>&nbsp;</td>
                                        <td><input type="text" name="name" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>From Date</td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <input  name="fromdate" readonly maxlength="16" value="${searchappbean.fromDate}" key="fromdate" id="fromdate"  />
                                            
                                            <script type="text/javascript">
                                                $(function() {
                                                                $( "#fromdate" ).datepicker({
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
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>To Date</td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <input  name="todate" readonly maxlength="16" value="${searchappbean.toDate}" key="todate" id="todate"  />
                                            
                                            <script type="text/javascript">
                                                $(function() {
                                                                $( "#todate" ).datepicker({
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
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td style="width: 300px;"><input type="submit" value="Search" style="width: 100px"/><input type="reset" style="width: 100px" value="Reset" /></td>
                                        <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDITSCORING%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a> </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
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
