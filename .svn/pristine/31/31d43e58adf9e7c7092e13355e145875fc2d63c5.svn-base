<%-- 
    Document   : recoverycallcenter
    Created on : Jul 15, 2013, 2:16:56 PM
    Author     : chanuka
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeRecoverySearch()
            {

                document.searchuserassignform.action="${pageContext.request.contextPath}/SearchRecoveryCallCenter";
                document.searchuserassignform.submit();

            }
            
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadRecoveryCallCenter";

            }


    

        </script>
        <script>
            function settitle(){
                   
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.RECOVERY_CALL_CENTER%>'                                  
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


        <title>EPIC CMS SEARCH ASSIGNED CARD APPLICATIONS</title>
    </head>
    <body>



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height:500px">

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchuserassignform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                        </tr>
                                    </table>

                                    <table  cellpadding="0" cellspacing="10">

                                        <tbody>

                                            <tr>
                                                <td width="200px">Card Number</td>
                                                <td><input type="text"  value="${recoverySearchbean.cardNumber}" name="cardnumber" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td width="200px">Account Number</td>
                                                <td><input type="text"  value="${recoverySearchbean.accNumber}" name="accnumber" maxlength="16"></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Status </td>
                                                <td>
                                                    <select  name="statuscode"  class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                            <c:if test="${recoverySearchbean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${recoverySearchbean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeRecoverySearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </form>


                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Collection Id</th>
                                            <th>Account Number</th>
                                            <th>Card Number</th>
                                            <th>Status</th>
                                            <th>Case Type</th>
                                            <th>Assign Status</th>
                                            <th>Collect</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="recovery" items="${searchList}">
                                            <tr>
                                                <td >${recovery.collectionId}</td>
                                                <td >${recovery.accNumber}</td>
                                                <td >${recovery.cardNumber}</td>
                                                <td >${recovery.statusDes}</td>
                                                <td >${recovery.caseDes}</td>
                                                <td >${recovery.assignStatusDes}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadRecoveryCollectionServlet?collectionId=${recovery.collectionId}'>Collect</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
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
