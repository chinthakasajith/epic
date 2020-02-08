<%-- 
    Document   : leftmenu2
    Created on : Nov 20, 2012, 4:07:47 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%@ page  import="com.epic.cms.system.util.session.SessionUser" %>
<%@ page  import="com.epic.cms.system.util.session.SessionVarList" %>
<%@ page  import="com.epic.cms.system.util.session.SessionVarName" %>
<%@ page  import="java.util.*" %>
<%@ page  import="java.util.Collections" %>
<%--<%@ page  import="com.janaka.jsp.bean.common.CMSStatusBean" %>--%>
<%@ page  import="com.epic.cms.system.util.comparator.CMSSectionComparator" %>
<%@ page  import="com.epic.cms.system.util.comparator.CMSPageComparator" %>
<%--<%@ page  import="com.epic.ecdr.rec.bean.controlpanel.systemusrmgt.RECUserRoleBean" %>--%>

<%@ page  import="com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean" %>
<%@ page  import="com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mainstyle.css" media="screen"/>


        <script >
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/LoadSystemUserServlet";
            }
            function invokeReset(reset){
                window.location = "${pageContext.request.contextPath}/ManageSystemUserServlet?operation="+reset;
            }

        </script>


        <title>CMS USER MANAGEMENT</title>
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

                




                <div id="homecontent">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <div class="pageheader" > CMS HOME <label id="user_message" style="color: red;float:right">${userInitLoginMessage}</label></div>

                                <table>
                                    <tr>
                                        <td><font class="error"> ${errorMsg}</font> </td>
                                        <td><font class="information"> ${successMsg}</font> </td>
                                    </tr>
                                </table>


                                <!--   ------------------------- start operation add area  --------------------------------                      -->
                                <c:set var="count" value="0"/>
                                <c:forEach var="applicationLst" items="${applicationLst}">
                                   <c:set var="count" value="${count+1}" />
                                   <c:if test="${count <= 5}">
                                       
                                    <div class="contentrow1" >

                                        <jsp:include page="/moduals/${applicationLst.applicationCode}.jsp"/>



                                    </div>
                                   </c:if>   
                                   <c:if test="${count > 5 }">
                                    <div class="contentrow2" >

                                        <jsp:include page="/moduals/${applicationLst.applicationCode}.jsp"/>



                                    </div>
                                    </c:if>       
                                </c:forEach>



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