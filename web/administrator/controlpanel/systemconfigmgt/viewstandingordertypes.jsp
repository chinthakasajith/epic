<%-- 
    Document   : viewstandingordertypes
    Created on : Feb 7, 2013, 11:43:06 AM
    Author     : asitha_l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>STANDING ORDER TYPES</title>

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

            </div>
            <div class="main">
                <div class="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------    -->   
                                <form>
                                    <table class="tit"> <tr> <td class="center"> <b>STANDING ORDER TYPES</b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td><b>Type ID</b></td>                                           
                                            <td>${sotBean.orderID}</td>
                                            <td style="width: 100px"></td>  
                                            <td><b>Description</b></td>
                                            <td>${sotBean.orderName}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Fee Type</b></td>                                          
                                            <td>${sotBean.feeTypeDes}</td>
                                            <td style="width: 100px"></td>
                                            <td><b>Currency Type</b></td>
                                            <td>${sotBean.currencyType}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Minimum Transaction Amount</b></td>
                                            <td><fmt:formatNumber  type="number" minFractionDigits="2" value="${sotBean.minTransactionAmt}"/></td>
                                            <td style="width: 100px"></td>
                                            <td><b>Maximum Transaction Amount</b></td>
                                            <td><fmt:formatNumber  type="number" minFractionDigits="2" value="${sotBean.maxTransactionAmt}"/></td>
                                        </tr>
                                        <tr>
                                            <td><b>Category</b></td>
                                            <td>
                                                <c:if test="${sotBean.category=='CRDPYMNT'}">
                                                    Card Payment
                                                </c:if>
                                                <c:if test="${sotBean.category=='UTILITY'}">
                                                    Utility Payment
                                                </c:if>
                                            </td>

                                        </tr>
                                        <c:if test="${sotBean.category=='UTILITY'}">
                                            <tr>
                                                <td><b>Utility Provider</b></td>
                                                <td>${sotBean.utilityProviderDes}</td>
                                            </tr>
                                            <tr>
                                                <td><b>Account Number</b></td>                                           
                                                <td>${sotBean.accNumber}</td>
                                            </tr>
                                            <tr>
                                                <td><b>Bank Branch</b></td>
                                                <td>${sotBean.branchName}</td>
                                            </tr>
                                            <tr>
                                                <td><b>Contact Person</b></td>
                                                <td>${sotBean.contactPerson}</td>
                                            </tr>
                                            <tr>
                                                <td><b>Contact No</b></td>
                                                <td>${sotBean.contactNumber}</td>
                                            </tr>
                                        </c:if>                                   
                                        <tr>
                                            <td><b>Last Updated User</b></td>
                                            <td>${sotBean.lastUpdatedUser}</td>                          
                                        </tr> 
                                        <tr>
                                            <td><b>Last Updated Time</b></td>
                                            <td>${sotBean.lastUpdatedTime}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Created Time</b></td>
                                            <td>${sotBean.createdTime}</td>                                    
                                        </tr> 
                                        <tr>
                                            <td><b>Status</b></td>
                                            <td>${sotBean.status}</td>
                                        </tr>
                                    </table>
                                </form>
                                <!--   ------------------------- end developer area  --------------------------------        -->

                            </div>

                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>

    </body>
</html>
