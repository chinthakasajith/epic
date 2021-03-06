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


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->



<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>-->



        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.min.js"></script>
        <title>EPIC_CMS_HOME</title>
        <script language = "javascript">
            function invoke(id)
            {

                $.post("${pageContext.request.contextPath}/ViewCountryMgtControllerServlet", {id: id},
                function (data) {
                    if (data == "success") {
                        $.colorbox({href: "${pageContext.request.contextPath}/administrator/controlpanel/transactionMgt/countrymgtview.jsp", iframe: false, height: "60%", width: "60%", overlayClose: false});
                    } else {
                        alert("can not view call");
                    }

                });


                //                document.infoTable.action="${pageContext.request.contextPath}/ViewCountryMgtControllerServlet?id="+id;
                //                document.infoTable.submit();
            }


            function cuResetBtn() {
                window.location = "${pageContext.request.contextPath}/LoadCountryMgtServlet";
            }
            //invokeView
            function invokeView(id)
            {
                window.location = "${pageContext.request.contextPath}/ViewCountryMgtControllerServlet?id=" + id;

            }

            function updateCountry(id)
            {
                document.infoTable.action = "${pageContext.request.contextPath}/viewCountryMgtUpdateServlet?id=" + id;
                document.infoTable.submit();
            }

            function deleteCountry(id)
            {
//                answer = confirm("Do you really want to delete  Country code "+id+"?")
//                if (answer !=0)
//                {
//                    document.infoTable.action="${pageContext.request.contextPath}/DeleteCountryMgtServlet?id="+id;
//                    document.infoTable.submit();
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadCountryMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete  Country code " + id + "?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadCountryMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteCountryMgtServlet?id="+id;
                        }
                    }
                });
            }

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.COUNTRY%>'
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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddCountryMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Country Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td> 
                                                        <c:if test="${cuBean.countryCode!='0'}" >
                                                        <td><input type="text" name="counrtCode" value="${cuBean.countryCode}" maxlength="3"/></td>
                                                        </c:if>
                                                        <c:if test="${cuBean.countryCode=='0'}" >
                                                        <td><input type="text" name="counrtCode" value="" maxlength="3"/></td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (2)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="AlpaFirstCode" value="${cuBean.alphaFirst}" maxlength="2" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (3)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="AlpaScndCode" value="${cuBean.alphaSecond}" maxlength="3"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${cuBean.description}" maxlength="48"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Fraud value</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>

                                                    <td><input type="text" name="frdValue" value="${cuBean.frdVal}" maxlength="3"/></td>


                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Region</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="region" value="${cuBean.region}" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" style="width: 100px" value="Add" name="add" width="100"/><input type="reset" onclick="cuResetBtn()" value="Reset" style="width: 100px" name="Reset" width="100"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.COUNTRY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View" /></a>  </td>
                                                </tr>
                                            </tbody>
                                        </table>



                                    </form>  

                                </c:if>




                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/AddCountryMgtServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Country Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="counrtCode" maxlength="3"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (2)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="AlpaFirstCode" value="" maxlength="2"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (3)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="AlpaScndCode" value="" maxlength="3"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="48"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Fraud value</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="frdValue" value="" maxlength="3" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Region</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="region" value="" maxlength="64" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" style="width: 100px" value="Add" name="add" width="100"/> <input type="reset" style="width: 100px" value="Reset" name="Reset" width="100"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.COUNTRY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View" /></a> </td>
                                                </tr>
                                            </tbody>
                                        </table>



                                    </form>  


                                </c:if>

                                <c:if test="${operationtype=='viewData'}" >

                                    <form method="POST" action="">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Country Code</td>
                                                    <td>:</td>
                                                    <td>${cuBean.countryCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (2)</td>
                                                    <td>:</td>
                                                    <td>${cuBean.alphaFirst}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (3)</td>
                                                    <td>:</td>
                                                    <td>${cuBean.alphaSecond}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>:</td>
                                                    <td>${cuBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Fraud value</td>
                                                    <td>:</td>
                                                    <td>${cuBean.frdVal}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Region</td>
                                                    <td>:</td>
                                                    <td>${cuBean.region}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="reset" onclick="cuResetBtn()" style="width: 100px" value="Back" name="Back" /></td>  

                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.COUNTRY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a> </td>
                                                </tr>
                                            </tbody>
                                        </table>



                                    </form>  


                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateCountryServlet">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Country Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" readonly="" name="counrtCode" value="${cuBean.countryCode}" maxlength="3" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (2)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="AlpaFirstCode" value="${cuBean.alphaFirst}" maxlength="2"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Alpha Code (3)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="AlpaScndCode" value="${cuBean.alphaSecond}" maxlength="3"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${cuBean.description}" maxlength="48"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Fraud value</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="frdValue" value="${cuBean.frdVal}" maxlength="3"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Region</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="region" value="${cuBean.region}" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <table>
                                            <tr>
                                                <td style="width: 110px;"></td>
                                                <td><input type="submit" style="width: 100px" value="Update" name="update" /></td>
                                                <td>  <input type="button" onclick="updateCountry('${cuBean.countryCode}')" style="width: 100px" value="Reset" name="Reset" width="100"/></td>
                                                <td style="width: 200px;"><input type="button" style="width: 100px" value="Back" name="back" onclick="cuResetBtn()"/></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.COUNTRY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View" /></a></td>
                                            </tr>

                                        </table>



                                    </form>  

                                </c:if>


                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;




                                <form name="infoTable" method="POST" action="${pageContext.request.contextPath}/ViewCountryMgtControllerServlet">


                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>Country Code</th>
                                                <th>Alpha Code (2)</th>
                                                <th>Alpha Code (3)</th>
                                                <th>Description</th>
                                                <th>Fraud Value</th>
                                                <th>Region</th>
                                                <th>View</th>
                                                <th>Update</th>
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="task" items="${requestScope.countryList}">
                                                <tr>
                                                    <td>${task.countryCode}</td>
                                                    <td>${task.alphaFirst}</td>
                                                    <td>${task.alphaSecond}</td>
                                                    <td>${task.description}</td>
                                                    <td>${task.frdVal}</td>
                                                    <td>${task.region}</td>
<!--                                                    <td ><a  href='#' onclick="invoke('V@${task.countryCode}')">View</a></td>-->
                                                    <td ><a  href='#' onclick="invokeView('${task.countryCode}')">View</a></td>
                                                    <td ><a  href='#' onclick="updateCountry('${task.countryCode}')">Update</a></td>
                                                    <td ><a  href='#' onclick="deleteCountry('${task.countryCode}')">Delete</a></td>
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
        <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
