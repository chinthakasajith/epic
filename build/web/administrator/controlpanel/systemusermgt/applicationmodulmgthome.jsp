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
        <jsp:include page="/content.jsp"/>

        <script type="text/javascript">

            function appView(value) {
                $.post("${pageContext.request.contextPath}/ViewApplicationMgtServlet", {id: value},
                function (data) {
                    if (data == "success") {
                        $.colorbox({href: "${pageContext.request.contextPath}/administrator/controlpanel/systemusermgt/applicationmoduleview.jsp", iframe: false, height: "60%", width: "60%", overlayClose: false});
                    } else {
                        alert("can not view call");
                    }

                });
                //                window.location = "${pageContext.request.contextPath}/ViewApplicationMgtServlet?id="+value;

            }
            function resetBtn() {
                window.location = "${pageContext.request.contextPath}/LoadAppLicationModuleServlet";
            }

            //View
            function View(value) {
                window.location = "${pageContext.request.contextPath}/ViewApplicationMgtServlet?id=" + value;
            }

            function appUpdate(value) {
                window.location = "${pageContext.request.contextPath}/ViewApplicationMgtUpdateServlet?id=" + value;
            }
            function appUpdateReset(value) {
                window.location = "${pageContext.request.contextPath}/ViewApplicationMgtUpdateServlet?id=" + value;
            }

            function appDelete(value) {

//                answer = confirm("Do you really want to delete  "+value+" Application ?")
//                if (answer !=0)
//                {
//                    window.location = "${pageContext.request.contextPath}/DeleteApplicationMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadAppLicationModuleServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete  " + value + " Application ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadAppLicationModuleServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteApplicationMgtServlet?id=" + value;
                        }
                    }
                });
            }
        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.APPLICATIONMGT%>'
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
        <title>EPIC_CMS_HOME</title>
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

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>



                                <c:if test="${operationtype=='view'}" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>
                                        <form method="POST" action="${pageContext.request.contextPath}/AddApplicationMgtServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Application Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="16" name="appCode" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="appdes" maxlength="50" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Application Category</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select name="category">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${addApp.cat =='ISS'}"> <option selected="true" value="ISS">Issuing</option></c:if>
                                                            <c:if test="${addApp.cat !='ISS'}"> <option  value="ISS">Issuing</option></c:if>
                                                            <c:if test="${addApp.cat =='ACQ'}"> <option selected="true" value="ACQ">Acquiring</option></c:if>
                                                            <c:if test="${addApp.cat !='ACQ'}"> <option  value="ACQ">Acquiring</option></c:if>
                                                            <c:if test="${addApp.cat =='COM'}"> <option selected="true" value="COM">Common</option></c:if>
                                                            <c:if test="${addApp.cat !='COM'}"> <option  value="COM">Common</option></c:if>


                                                            </select></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Status</td>
                                                        <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <td><select name="appStatus">
                                                                <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.secList}">
                                                                <option value="${vRule.statusCode}">${vRule.description}</option>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" style="width: 100px" width="100" value="Add" />&nbsp;<input type="reset" style="width: 100px" value="Reset" onclick="resetBtn()" width="100"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPLICATIONMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>

                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>  


                                <c:if test="${operationtype=='add'}" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>
                                        <form method="POST" action="${pageContext.request.contextPath}/AddApplicationMgtServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Application Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="16" name="appCode" value="${addApp.applicationCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="appdes" value="${addApp.description}" maxlength="50"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Application Category</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select name="category">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${addApp.cat =='ISS'}"> <option selected="true" value="ISS">Issuing</option></c:if>
                                                            <c:if test="${addApp.cat !='ISS'}"> <option  value="ISS">Issuing</option></c:if>
                                                            <c:if test="${addApp.cat =='ACQ'}"> <option selected="true" value="ACQ">Acquiring</option></c:if>
                                                            <c:if test="${addApp.cat !='ACQ'}"> <option  value="ACQ">Acquiring</option></c:if>
                                                            <c:if test="${addApp.cat =='COM'}"> <option selected="true" value="COM">Common</option></c:if>
                                                            <c:if test="${addApp.cat !='COM'}"> <option  value="COM">Common</option></c:if>


                                                            </select></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Status</td>
                                                        <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <td><select name="appStatus">
                                                                <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.secList}">

                                                                <c:if test="${vRule.statusCode ==addApp.status}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.statusCode !=addApp.status}">
                                                                    <option  value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" style="width: 100px" width="100" value="Add" />&nbsp;<input type="button" style="width: 100px" value="Reset" onclick="resetBtn()"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPLICATIONMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>
                                <!--edit-->
                                <c:if test="${operationtype=='update'}" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>
                                        <form action="${pageContext.request.contextPath}/UpdateApplicationMgtServlet" method="POST">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Application Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" readonly="" maxlength="16" id="editAppCode" name="editAppCode" value="${appBean.applicationCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="editAppdes" id="editAppdes" value="${appBean.description}" maxlength="50"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Application Category</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select name="category">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${appBean.cat =='ISS'}"> <option selected="true" value="ISS">Issuing</option></c:if>
                                                            <c:if test="${appBean.cat !='ISS'}"> <option  value="ISS">Issuing</option></c:if>
                                                            <c:if test="${appBean.cat =='ACQ'}"> <option selected="true" value="ACQ">Acquiring</option></c:if>
                                                            <c:if test="${appBean.cat !='ACQ'}"> <option  value="ACQ">Acquiring</option></c:if>
                                                            <c:if test="${appBean.cat =='COM'}"> <option selected="true" value="COM">Common</option></c:if>
                                                            <c:if test="${appBean.cat !='COM'}"> <option  value="COM">Common</option></c:if>


                                                            </select></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Status</td>
                                                        <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <td><select name="editStatus" id="editStatus">
                                                            <c:forEach var="vRule" items="${requestScope.secList}">
                                                                <c:if test="${vRule.statusCode ==appBean.status}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.statusCode !=appBean.status}">
                                                                    <option value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td> 
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" style="width: 100px" width="100" value="Update"/>&nbsp;<input type="reset" style="width: 100px" value="Reset" width="100" onclick="appUpdateReset('${appBean.applicationCode}')"/> </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPLICATIONMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>


                                <c:if test="${operationtype=='viewData'}" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>
                                        <form action="" method="POST">

                                            <table border="0">
                                                <tbody>
                                                    <tr>
                                                        <td>Application Code</td>
                                                        <td>:</td>
                                                        <td>${appBean.applicationCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>:</td>
                                                    <td>${appBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Application Category</td>
                                                    <td>:</td>
                                                    <td>${appBean.catDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td>:</td>
                                                    <td>${appBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td> 
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="reset" style="width: 100px" value="Back" width="100" onclick="resetBtn()"/> </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.APPLICATIONMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>



                                <!--                                    update-->



                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Application Code</th>
                                            <th>Description</th>
                                            <th>Category</th>
                                            <th>Status</th>
                                            <!--                                            <th>Last Update User</th>
                                                                                        <th>Last Update Time</th>
                                                                                        <th>Create Time</th>-->
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach  items="${requestScope.allsecList}" var="allsecList">
                                            <tr>
                                                <td>${allsecList.applicationCode}</td>
                                                <td>${allsecList.description}</td>
                                                <td>${allsecList.catDes}</td>
                                                <td>${allsecList.statusDes}</td>

<!--                                                <td>${allsecList.lastUpdateuser}</td>
<td>${allsecList.lastUpdatedTime}</td>
<td>${allsecList.createdTime}</td>-->
<!--                                                <td id="view"  ><a  href='#' onclick="appView('${allsecList.applicationCode}@${allsecList.description}@${allsecList.status}@E')"  >view</a></td>-->
                                                <td id="view"  ><a  href='#' onclick="View('${allsecList.applicationCode}')"  >view</a></td>
                                                <td id="view" ><a  href='#' onclick="appUpdate('${allsecList.applicationCode}')"  >Update</a></td>
                                                <td ><a  href='#' onclick="appDelete('${allsecList.applicationCode}')">delete</a></td>
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
