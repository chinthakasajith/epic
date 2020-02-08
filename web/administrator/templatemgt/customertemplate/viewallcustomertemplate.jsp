<%-- 
    Document   : viewallcustomertemplate
    Created on : Feb 1, 2012, 9:09:27 AM
    Author     : janaka_h
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <title>VIEW SYSTEM USER</title>
        <script>

            function ConfirmDelete(templateCode)
            {

//                answer = confirm("Do you really want to delete this template?")
//                if (answer !=0)
//                {
//                    
//                    window.location = "${pageContext.request.contextPath}/DeleteTemplateMgtServlet?templateCode="+templateCode;
//                }
//                else
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadCustomerTempalteMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + templateCode + " template?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadCustomerTempalteMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteTemplateMgtServlet?templateCode=" + templateCode;
                        }
                    }
                });

            }
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CUSTEMPLATE%>'
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

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form name="addform" action='<%=request.getContextPath()%>/ManageTemplateMgtServlet'>
                                    <input type="submit"  name="adduser" class="" value="Create Template" />
                                    <input type="hidden" name="operation" value="add">
                                    <p>&nbsp;</p>
                                </form>


                                <table  width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="scoreltableview2">
                                    <thead>
                                        <tr class="display">
                                            <th width="100px;" scope="col">Template Code </th>
                                            <th width="100px;" scope="col">Template Name</th>
                                            <th width="100px;" scope="col">Valid From </th>
                                            <th width="100px;" scope="col">Valid To</th>
                                            <!--                                        <th width="100px;" scope="col">Product Type</th>-->
                                            <th width="100px;" scope="col">Currency Type</th>
                                            <th width="100px;" scope="col">Total Credit Limit</th>
                                            <th width="100px;" scope="col">Status</th>
                                            <th width="100px;" scope="col">View</th>
                                            <th width="100px;" scope="col">Update</th>
                                            <th width="100px;" scope="col">Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="templateList" items="${requestScope.templateList}">
                                            <tr >
                                                <td>${templateList.templateCode}</td>
                                                <td>${templateList.templateName}</td>
                                                <td>${templateList.valiedFrom}</td>
                                                <td>${templateList.valiedTo}</td>
                                                <!-- <td>${templateList.productType}</td> -->
                                                <td>${templateList.currencyDes}</td>
                                                <td>${templateList.totalCreditLimit}</td>
                                                <td>${templateList.statusDes}</td>
                                                <td><a href='<%=request.getContextPath()%>/ViewCustomerTemplateServlet?templateCode=${templateList.templateCode}&operation=view' >View</a></td>
                                                <td><a href='<%=request.getContextPath()%>/ManageTemplateMgtServlet?templateCode=${templateList.templateCode}&operation=edit' >Update</a></td>
                                                <td><a href='#' onclick="ConfirmDelete('${templateList.templateCode}')">Delete</a></td>


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
