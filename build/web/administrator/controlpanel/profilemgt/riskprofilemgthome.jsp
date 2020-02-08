<%-- 
    Document   : riskprofilemgthome
    Created on : May 23, 2012, 5:25:23 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>-->


        <script type="text/javascript">
            //            function feeResetBtn(){
            //                window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet";
            //            }
            function invokeDelete(riskProfCode) {


//                answer = confirm("Are you sure you want to delete "+riskProfCode+" Risk Profile ?")
//                if (answer !=0)
//                {
//                    window.location = "${pageContext.request.contextPath}/DeleteRiskProfileMgtServlet?riskProfCode="+riskProfCode;
//                }
//                else
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadRiskProfileMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Are you sure you want to delete " + riskProfCode + " Risk Profile ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadRiskProfileMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteRiskProfileMgtServlet?riskProfCode=" + riskProfCode;
                        }
                    }
                });

            }
            function invokeUpdate(riskProfCode) {
                window.location = "${pageContext.request.contextPath}/LoadUpdateRiskProfileMgtServlet?riskProfCode=" + riskProfCode;
            }


            function invokeView(riskProfCode)
            {

                window.location = "${pageContext.request.contextPath}/ViewRiskProfileMgtServlet?riskProfCode=" + riskProfCode;


            }


        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.RISKPROFILE%>'
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

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">


            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>

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


                                    <!--end view-->
                                    <form method="POST" action="${pageContext.request.contextPath}/LoadCreateRiskProfileMgtServlet">
                                    <input type="submit" value="Create Risk Profile" />
                                </form>


                                <!--display table-->
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;

                                <table border="1" class="display" id="scoreltableview">
                                    <thead>
                                        <tr>
                                            <th>Risk Profile Code</th>
                                            <th>Description</th>
                                            <th>Status</th>
                                            <th>Profile Type</th>
                                            <th>Period (day)</th>


                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.riskProfList}" var="risk" >

                                            <tr>
                                                <td>${risk.riskProfCode}</td>
                                                <td>${risk.description}</td>
                                                <td>${risk.statusDes}</td>
                                                <td>${risk.profileTypeDescription}</td>
                                                <td>${risk.peroid}</td>

                                                <td><a  href='#' onclick="invokeView('${risk.riskProfCode}')">View</a></td>
                                                <td><a  href='#' onclick="invokeUpdate('${risk.riskProfCode}')">Update</a></td>
                                                <td><a  href='#' onclick="invokeDelete('${risk.riskProfCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>



                                <!--display table end-->






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
