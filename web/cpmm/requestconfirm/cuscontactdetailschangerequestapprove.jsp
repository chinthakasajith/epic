<%-- 
    Document   : cuscontactdetailschangerequestapprove
    Created on : Sep 13, 2016, 2:40:52 PM
    Author     : prageeth_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css"> 
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>


        <script type="text/javascript">
            function invokeSearch() {

                document.searchrequestapprove.action = "${pageContext.request.contextPath}/LoadCusContactDetailsReqApproveServlet?action=search";
                document.searchrequestapprove.submit();
            }

            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadCusContactDetailsReqApproveServlet";

            }

            function invokeCancel(form) {


                if (form == 'CDRP') {
                    document.cardreplace.action = "${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.cardreplace.submit();
                }
                if (form == 'CDRI') {
                    document.cardreissue.action = "${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.cardreissue.submit();
                }
                if (form == 'PIRI') {
                    document.pinreissue.action = "${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.pinreissue.submit();
                }


            }

            function invokeApproveCardReplace(status, operation) {
                document.cardreplace.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreplace.submit();
            }

            function invokeApproveCardReissue(status, operation) {
                document.cardreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreissue.submit();
            }

            function invokeApprovePINReissue(status, operation) {
                document.pinreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.pinreissue.submit();
            }

            function invokeActivate(operation) {
                document.cardactivate.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?operation=" + operation;
                document.cardactivate.submit();
            }

            function invokeRejectCardReplace(status, operation) {
                document.cardreplace.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreplace.submit();
            }

            function invokeRejectCardReissue(status, operation) {
                document.cardreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardreissue.submit();
            }

            function invokeRejectPINReissue(status, operation) {
                document.pinreissue.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.pinreissue.submit();
            }

            function invokeRejectActivate(status, operation) {
                document.cardactivate.action = "${pageContext.request.contextPath}/UpdateRequestApproveServlet?approveStatus=" + status + "&operation=" + operation;
                document.cardactivate.submit();
            }

            function invokeHistory(value) {

                $.post("${pageContext.request.contextPath}/ViewHistoryRequestApproveServlet", {id: value},
                function(data) {
                    if (data == "success") {

                        $.colorbox({href: "${pageContext.request.contextPath}/cpmm/requestconfirm/cardhistory.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }
                                });
                            }
                            //----------------------------------------------------------------------------------  
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CUSCONTACTCHANGEREQUESTAPPROVE%>'
                        },
                function(data) {

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

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                    <form method="POST" name="searchrequestapprove" >
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" value="${cardNo}" name="cardNo"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Identification Number</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" value="${identificationNumber}" name="identificationNumber"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Application Id</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" value="${applicationId}" name="applicationId"/>
                                                    </td>
                                                </tr>                                                  
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>   
                                                        <input type="submit" class="defbutton" name="search" value="Search" onclick="invokeSearch()"/>                                                         
                                                        <input type="reset" class="defbutton" name="reset" value="Reset" onclick="invokeReset()"/> 
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                

                                <!-- **************************************************start table view*******************************************************************************-->
                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Req Id</th>
                                            <th>Card No</th>
                                            <th>Application Id</th>
                                            <th>Identification Number</th>
                                            <th>View</th>                                            

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="requests" items="${searchedList}">
                                            <tr>
                                                <td>${requests.requestId}</td>
                                                <td>${requests.cardNo}</td>
                                                <td>${requests.applicationId}</td>
                                                <td>${requests.identificationNumber}</td>                                              
                                                <td ><a href='${pageContext.request.contextPath}/LoadUpdateCusCntDetailsReqApproveServlet?reqId=<c:out value="${requests.requestId}"></c:out>'>View</a></td>
                                                </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <br />


                                <!--   ------------------------- end developer area  --------------------------------                      -->
                                <!--<font style="color: red;">*</font>&nbsp;-->



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
