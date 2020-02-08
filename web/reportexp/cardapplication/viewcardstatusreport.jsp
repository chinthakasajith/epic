<%-- 
    Document   : viewcardstatusreport
    Created on : Dec 6, 2012, 8:59:31 AM
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
                                    <table class="tit"> <tr> <td   class="center"> <b>CARD STATUS REPORT</b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="10" >
                                        <tr>
                                            <td>Application Id</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.applicationId}</td>
                                        </tr>
                                        <tr>
                                            <td>Card No</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.cardNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Branch</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.branchName}</td>
                                        </tr>
                                         <tr>
                                            <td>User</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.user}</td>
                                        </tr>
                                        <tr>
                                            <td>Priority Level</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.priorityLevelDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Application Domain</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.domainDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Card Type</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.typeDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Card Product</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.productDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Identification Type</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.idTypeDes}</td>
                                        </tr>
                                        <tr>
                                            <td>Identification Number</td>
                                            <td>:</td>
                                            <td></td>
                                            <td>${rejBean.idNo}</td>
                                        </tr>                                  
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

