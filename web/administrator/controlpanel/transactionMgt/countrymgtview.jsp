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
                                                <td>Country Code</td>
                                                <td></td>
                                                <td><input type="text" name="" readonly="" value="${SessionObject.countryBean.countryCode}" /></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Alpha Code (2)</td>
                                                <td></td>
                                                <td><input type="text" name="" readonly="" value="${SessionObject.countryBean.alphaFirst}" /></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Alpha Code (3)</td>
                                                <td></td>
                                                <td><input type="text" name="" readonly="" value="${SessionObject.countryBean.alphaSecond}" /></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Description</td>
                                                <td></td>
                                                <td><input type="text" name="" readonly="" value="${SessionObject.countryBean.description}" /></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Fraud value</td>
                                                <td></td>
                                                <td><input type="text" name="" readonly="" value="${SessionObject.countryBean.frdVal}" /></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Region</td>
                                                <td></td>
                                                <td><input type="text" name="" readonly="" value="${SessionObject.countryBean.region}" /></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
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
