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

                                <table class="tit"> <tr> <td   class="center">  FEE PROFILE MANAGEMENT VIEW </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table border="0">
                                    <tbody>
                                        <tr>
                                            <td>Fee Profile Code</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.feeProCode}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td> &nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Description</td>
                                            <td></td>
                                            <td><input type="text" readonly=""  value="${SessionObject.feeBean.feeProDes}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td> &nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Status</td>
                                            <td></td>
                                            <td><input type="text" readonly=""  value="${SessionObject.feeBean.stausDes}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Currency Code </td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.currencyDes}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Join Fee </td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.joinFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Annual Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.annualFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Renewal Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.renewalFee}" /></td>
                                        </tr>

                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Replace Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.replaceFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Late Payment Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.latePayFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Legal Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.legalFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Return Cheque Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.retunChgeFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Reject Auto Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.rejectAutoFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Limit Exceed Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.limitExceedFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Temporary Limit Change Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.tempLimitFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Permanent Limit Increase Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.perLimitIncreaseFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Card Type Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.cardTypeFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Statement Copy Fee</td>
                                            <td></td>
                                            <td><input type="text" readonly="" value="${SessionObject.feeBean.statementCopyFee}" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>

                                    </tbody>
                                </table>










                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>

                        </div>
                    </div>








                </div>
            </div>

        </div>
    </body>
</html>
