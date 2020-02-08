<%-- 
    Document   : emailhome
    Created on : Jun 15, 2016, 8:00:00 AM
    Author     : sajith
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



            function invokeCreate()
            {

                document.emailform.action = "${pageContext.request.contextPath}/LoadAddEmailConfFormServlet";
                document.emailform.submit();

            }



            function ConfirmDelete(templateCode)
            {
//                answer = confirm("Do you really want to delete this email template?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteEmailConfServlet?templatecode="+templateCode;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadEmailConfTempMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete email template "+templateCode+" ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadEmailConfTempMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteEmailConfServlet?templatecode=" + templateCode;
                        }
                    }
                });

            }



        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.EMAILHOME%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

        </script> 


        <title>EPIC CMS EMAIL TEMPLATE HOME</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

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


                                <form method="POST" action="" name="emailform">

                                    <table>
                                        <tr>
                                            <td><b><font color="Red"> ${errorMsg}</font> </b></td>
                                            <td><b><font color="green"> ${successMsg}</font> </b></td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table>

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td style="width: 300px"><input type="button" value="Create Template" name="createtemplate" style="width: 150px" onclick="invokeCreate()"></td>
                                            </tr>
                                            <tr> <td style="height:20px;"></td></tr>

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>





                                <table  border="1"  class="display" id="scoreltableview3">
                                    <thead>
                                        <tr>

                                            <th>Template Code</th>
                                            <th>Template Description</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="emaildomain" items="${searchList}">
                                            <tr>

                                                <td >${emaildomain.templateCode}</td>
                                                <td >${emaildomain.description}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/ViewEmailConfServlet?templatecode=<c:out value="${emaildomain.templateCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateEmailConfFormServlet?templatecode=<c:out value="${emaildomain.templateCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${emaildomain.templateCode}')">Delete</a></td>


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
                                  <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>


