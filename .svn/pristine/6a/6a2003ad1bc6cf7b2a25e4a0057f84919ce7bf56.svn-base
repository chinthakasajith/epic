<%-- 
    Document   : viewapplicationrejectreport
    Created on : Dec 3, 2012, 9:08:40 AM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>EPIC_CMS_HOME</title>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

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
                <div class="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <form>
                                    <table class="tit"> <tr> <td   class="center"> <b>APPLICATION REJECT REPORT</b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="10" >
                                        <tr>
                                            <td>Application Id</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.applicationId}</td>
                                        </tr>
                                        <tr>
                                            <td>Branch</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.branchName}</td>
                                        </tr>
                                        <tr>
                                            <td>Priority Level</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.priorityLevelDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Domain</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.domainDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Identification Type</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.idType}</td>
                                        </tr>
                                        <tr>
                                            <td>Identification Number</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.idNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Reject Reason</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.rejectDes}</td>
                                        </tr>
                                        <c:if test="${rejBean.domainCode == 'CREDIT'}">
                                            <tr>
                                                <td>Credit Score</td>
                                                <td>:</td>
                                                <td></td>
                                                <td>${rejBean.creditScore}</td>
                                            </tr> 
                                        </c:if>
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

    </body>
</html>

