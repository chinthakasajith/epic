<%-- 
    Document   : saletxnview
    Created on : Dec 31, 2012, 2:23:10 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>EPIC_CMS_HOME</title>

        <jsp:include page="/content.jsp"/>
       
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
        
            </div>

            <div class="main">

                <div class="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <form>
                                    <table class="tit"> <tr> <td   class="center"> <b> TRANSACTION DETAILS </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                   
                                    <table cellpadding="0" cellspacing="10" >
                                       
                                        <tr>
                                            <td width ="200px;">Transaction ID</td>
                                            <td> : </td>
                                            <td>${detailsBean.txnId}</td>
                                        </tr>
                                        
                                         <tr>
                                            <td>MTI</td>
                                            <td> : </td>
                                            <td>${detailsBean.mti}</td>
                                        </tr>
                                        
                                         <tr>
                                            <td>Response MTI</td>
                                            <td> : </td>
                                            <td>${detailsBean.responceMti}</td>
                                        </tr>
                                        
                                         <tr>
                                            <td>Listener Type</td>
                                            <td> : </td>
                                            <td>${detailsBean.listnerType}</td>
                                        </tr>
                                        
                                         <tr>
                                            <td>Channel Type</td>
                                            <td> : </td>
                                            <td>${detailsBean.channelType}</td>
                                        </tr>
                                        
                                         <tr>
                                            <td>Pay Type</td>
                                            <td> : </td>
                                            <td>${detailsBean.payType}</td>
                                        </tr>
<!--                                        
                                         <tr>
                                            <td>Service Code</td>
                                            <td> : </td>
                                            <td>${detailsBean.txnId}</td>
                                        </tr>-->
                                        
                                         <tr>
                                            <td>Card Number</td>
                                            <td> : </td>
                                            <td>${detailsBean.cardNumber}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td>Expiry Date</td>
                                            <td> : </td>
                                            <td>${detailsBean.expiryDate}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td>Transaction Currency</td>
                                            <td> : </td>
                                            <td>${detailsBean.txnCurrency}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td>Transaction Amount</td>
                                            <td> : </td>
                                            <td>${detailsBean.txnAmount}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td>Transaction Status</td>
                                            <td> : </td>
                                            <td>${detailsBean.status}</td>
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