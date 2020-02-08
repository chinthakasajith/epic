<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
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
            function feeResetBtn() {
                window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet";
            }
            function feeDelete(value) {


//                answer = confirm("Are you sure you want to delete ?")
//                if (answer !=0)
//                {
//                    window.location = "${pageContext.request.contextPath}/DeleteFeeProfileMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet?id="+value;
//                }
                $("#dialog-confirm").html("<p>Are you sure you want to delete "+value+" fee profile?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadFeeProfileMgtServlet?id="+value;
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteFeeProfileMgtServlet?id="+value;
                        }
                    }
                });

            }
            function feeUpdate(value, isDefault) {
                window.location = "${pageContext.request.contextPath}/ViewFeeProfileMgtUpdateServlet?id=" + value + "&isDefault=" + isDefault;
            }


            function feeView(id) {
                $.post("${pageContext.request.contextPath}/ViewFeeProfileServlet?id", {id: id},
                function (data) {
                    if (data == "success") {
                        $.colorbox({href: "${pageContext.request.contextPath}/administrator/controlpanel/profilemgt/feeprofileview.jsp", iframe: false, height: "80%", width: "60%", overlayClose: false});
                    } else {
                        alert("can not view call");
                    }

                });



                //                window.location = "${pageContext.request.contextPath}/ViewExchangeRateServlet?id="+value;
            }


        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.FEEPROFILE%>'
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
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

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
                                    <form method="POST" action="${pageContext.request.contextPath}/LoadFeeProfileAddServlet?remove=nc&isDefault=yes">
                                    <input type="submit" value="Create Fee Profile" class="longbutton"/>
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
                                            <th>Fee Profile Code</th>
                                            <th>Description</th>
                                            <th>Fee Category</th>
                                            <th>Status</th>


                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${feeProfList}" var="feeList" >

                                            <tr>
                                                <td>${feeList.feeProCode}</td>
                                                <td>${feeList.feeProDes}</td>
                                                <c:if test="${feeList.feeCategory == 'MER'}">
                                                    <td>Merchant</td>
                                                </c:if>
                                                <c:if test="${feeList.feeCategory == 'CRD'}">
                                                    <td>Card</td>
                                                </c:if>
                                                <c:if test="${feeList.feeCategory == ''}">
                                                    <td>--</td>
                                                </c:if>
                                                <td>${feeList.stausDes}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/ViewFeeProfileServlet?id=<c:out value="${feeList.feeProCode}"></c:out>'>View</a></td>
                                                <td><a  href='#' onclick="feeUpdate('${feeList.feeProCode}', 'yes')">Update</a></td>
                                                <td><a  href='#' onclick="feeDelete('${feeList.feeProCode}')">Delete</a></td>
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
