<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <!--        include content.jsp for all js and css inclusion-->
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



                <div class="content" >


                </div>


                <div class="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <c:if test="${SessionObject.operationtype=='edit'}" >



                                    <table border="0">

                                        <tbody>
                                            <tr>
                                                <td>From Currency </td>
                                                <td> </td>
                                                <td>
                                                    <input type="text" readonly="" name="viewfromcur" value="${SessionObject.exbBean.fromCurDes}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>To Currency </td>
                                                <td>  </td>
                                                <td>
                                                    <input type="text" readonly="" name="viewtocur" value="${SessionObject.exbBean.toCurDes}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Rate</td>
                                                <td>  </td>
                                                <td><input type="text" readonly="" name="viewrate" value="${SessionObject.exbBean.rate}" maxlength="4"/></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>  </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>








                                </c:if>


                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>

                        </div>
                    </div>








                </div>
            </div>

        </div>
    </body>
</html>
