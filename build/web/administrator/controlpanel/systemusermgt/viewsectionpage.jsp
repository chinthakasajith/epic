<%-- 
    Document   : viewsectionpage
    Created on : Jan 17, 2012, 3:36:09 PM
    Author     : upul
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">






        <title>EPIC_CMS_HOME</title>

        <script language = "javascript">
            function invoke()
            {
                document.sectionpage.action = "${pageContext.request.contextPath}/LoadSectionPageMgtServlet";
                document.sectionpage.submit();
            }
        </script>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script >
            function settitle() {

             $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.SECTIONPAGE%>'
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

                                <form method="POST" action='<%=request.getContextPath()%>/ManageApplicationSectionServlet' name="sectionpage">
                                    <br>
                                    <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                    <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        <br>
                                        <table>
                                            <tr>
                                                <td>Application Module</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select onchange="invoke()"  name="applicationcodefield"  class="inputfield">
                                                        <option value="">--SELECT--</option>
                                                    <c:forEach var="applicationCodeLst" items="${sessionScope.SessionObject.applicationModuleList}">
                                                        <c:if test="${applicationCode==applicationCodeLst.applicationCode}">
                                                            <option value="${applicationCodeLst.applicationCode}" selected>${applicationCodeLst.description}</option>
                                                        </c:if>
                                                        <c:if test="${applicationCode!=applicationCodeLst.applicationCode}">
                                                            <option value="${applicationCodeLst.applicationCode}" >${applicationCodeLst.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    <br>
                                    <input type="radio" name="role" value="section">&nbsp;&nbsp;Section<br>
                                    <input type="radio" name="role" value="page">&nbsp;&nbsp;Page<br><br>

                                    <input type="submit" name="manageprivilages" value="Manage Application Privileges" class="">

                                    <!--      for history view start           -->

                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a  href="#"  onclick="invokeHistory('<%=PageVarList.SECTIONPAGE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a><br>
                                    <br/>

                                    <!--       for history view end           -->
                                </form>


                                <!--       view table form section page table-->
                                <c:if test="${sessionScope.SessionObject.sectionPageList!=null}">

                                    <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeA">

                                                <th class="view-table-header" >Section</th>
                                                <th class="view-table-header" > Page</th>



                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="sectionPageBean" items="${sessionScope.SessionObject.sectionPageList}">
                                                <tr class="gradeC"  >
                                                    <td class="view-table-tab" >${sectionPageBean.sectionCode}</td>
                                                    <td class="view-table-tab" >${sectionPageBean.pageCode}</td>



                                                </tr>

                                            </c:forEach>



                                        </tbody>
                                    </table>






                                </c:if>

                                <!--                                end of table view-->





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
