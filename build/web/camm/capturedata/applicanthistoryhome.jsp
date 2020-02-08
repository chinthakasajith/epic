<%-- 
    Document   : applicanthistoryhome
    Created on : Feb 20, 2012, 1:33:54 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            
            
            function invokeProceed(applicationId)
            {

                document.applicanthistoryform.action="${pageContext.request.contextPath}/LoadDefaultDataServlet?appliactionid="+applicationId;
                document.applicanthistoryform.submit();

            }
            
            function invokeBack(value)
            {

                window.location = "${pageContext.request.contextPath}/LoadUserAssignDataServlet?back="+value;
            }


    

        </script>



        <title>EPIC CMS CARD APPLICATION ASSIGN</title>
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



                <div class="content" style="height: 500px">

                   <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  CARD APPLICATION CAPTURING </td> </tr><tr> <td>&nbsp;</td> </tr></table>





                                <form method="POST" action="" name="applicanthistoryform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> ${errorMsg}</font> </td>
                                            <td><font color="green"> ${successMsg}</font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td>Application ID</td>
                                                <td><font >:</font>&nbsp;${applicanthistorybean.applicationId}</td>
                                                <td></td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td>Identification Type</td>
                                                <td><font >:</font>&nbsp;${applicanthistorybean.identityType}</td>
                                                <td></td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td>Identification Number</td>
                                                <td><font >:</font>&nbsp;${applicanthistorybean.identityNo}</td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td>Customer ID</td>
                                                <td><font >:</font>&nbsp;${customerid}</td>
                                                <td></td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td>Customer Name</td>
                                                <td><font >:</font>&nbsp;${customername}</td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     
                                            

                                            <tr> <td style="height:12px;"></td></tr>

                                        </tbody>
                                    </table>
                                </form>




                                <table class="tit"> <tr> <td   class="center">  CUSTOMER ACCOUNT DETAILS </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Application ID </th>
                                            <th>Identification Type</th>
                                            <th>Identification Number</th>
                                            <th>Priority Level</th>
                                            <th>Employee Number</th>
                                            <th>Branch ID</th>
                                            <th>Assign User</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="assignapp" items="">
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>


                                <table class="tit">   <tr> <td style="height:12px;"></td></tr><tr> <td   class="center">  CUSTOMER CARD DETAILS </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table  border="1"  class="display" id="tableview2">
                                    <thead>
                                        <tr>
                                            <th>Application ID </th>
                                            <th>Identification Type</th>
                                            <th>Identification Number</th>
                                            <th>Priority Level</th>
                                            <th>Employee Number</th>
                                            <th>Branch ID</th>
                                            <th>Assign User</th>



                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="assignapp" items="">
                                            <tr>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>
                                                <td ></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>




                                <table>
                                    <tr> <td style="height:12px;"></td></tr>

                                    <tr>

                                        <td></td>
                                        <td><input type="submit" value="Proceed" name="proceed" style="width: 100px" onclick="invokeProceed('${applicanthistorybean.applicationId}')">
                                            <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeBack('back')">
                                        </td>
                                        <td></td>
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


