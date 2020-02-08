<%-- 
    Document   : viewmerchantlocationreport
    Created on : Jan 10, 2013, 5:02:14 PM
    Author     : asitha_l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>MERCHANT LOCATION MANAGEMENT</title>

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
                                    <table class="tit"> <tr> <td   class="center"> <b>MERCHANT LOCATION REPORT</b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="10" >
                                        <tr>
                                            <td>Merchant ID</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.merchantId}</td>
                                        </tr>
                                        <tr>
                                            <td>Merchant Customer</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.merchantCustomerNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Description</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.description}</td>
                                        </tr>
                                        <tr>
                                            <td>Contact Person Title</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.contactPersonTitle}</td>
                                        </tr>
                                        <tr>
                                            <td>Contact Person First Name</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.contactPersonFirstName}</td>
                                        </tr>
                                        <tr>
                                            <td>Contact Person Middle Name</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.contactPersonMiddleName}</td>
                                        </tr>
                                        <tr>
                                            <td>Contact Person Last Name</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.contactPersonLastName}</td>
                                        </tr>
                                        <tr>
                                            <td>Contact Person Position</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.contactPersonPosistion}</td>
                                        </tr>
                                        <tr>
                                            <td>Address 1</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.address1}</td>
                                        </tr>
                                        <tr>
                                            <td>Address 2</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.address2}</td>
                                        </tr>
                                        <tr>
                                            <td>Address 3</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.address3}</td>
                                        </tr>
                                        <tr>
                                            <td>City</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.city}</td>
                                        </tr>
                                        <tr>
                                            <td>Postal Code</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.postalCode}</td>
                                        </tr>
                                        <tr>
                                            <td>Country</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.country}</td>
                                        </tr>
                                        <tr>
                                            <td>Telephone</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.telephoneNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Fax</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.faxNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Email</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.email}</td>
                                        </tr>
                                        <tr>
                                            <td>Activation Date</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.activationDate}</td>
                                        </tr>
                                        <tr>
                                            <td>Status</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.status}</td>
                                        </tr>
                                        <tr>
                                            <td>Risk Profile</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.riskProfile}</td>
                                        </tr>
                                        <tr>
                                            <td>Fee Profile</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.feeProfile}</td>
                                        </tr>
                                        <tr>
                                            <td>Commission Profile</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.commissionProfile}</td>
                                        </tr>
                                        <tr>
                                            <td>Last Update User</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.lastUpdatedUser}</td>
                                        </tr>
                                        <tr>
                                            <td>Fee Profile</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.feeProfile}</td>
                                        </tr>
                                        <tr>
                                            <td>Commission Profile</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.commissionProfile}</td>
                                        </tr>
                                        <tr>
                                            <td>Last Update User</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.lastUpdatedUser}</td>
                                        </tr>
                                        <tr>
                                            <td>Last Updated Time</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.lastUpdatedTime}</td>
                                        </tr>
                                        <tr>
                                            <td>Created Time</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.createdTime}</td>
                                        </tr>
                                        <tr>
                                            <td>Merchant Account No</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.merchantAccountNo}</td>
                                        </tr>
                                        <tr>
                                            <td>Statement Cycle</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.statementCycle}</td>
                                        </tr>
                                        <tr>
                                            <td>Payment Cycle</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.paymentCycle}</td>
                                        </tr>
                                        <tr>
                                            <td>Payment Mode</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.paymentMode}</td>
                                        </tr>
                                        <tr>
                                            <td>Statement Maintenance Status</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.statementMaintenanceStatus}</td>
                                        </tr>
                                        <tr>
                                            <td>Bank Code</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.bankCode}</td>
                                        </tr>
                                        <tr>
                                            <td>Branch Name</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.branchName}</td>
                                        </tr>
                                        <tr>
                                            <td>Account Number</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.accountNumber}</td>
                                        </tr>
                                        <tr>
                                            <td>Account Type</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.accountType}</td>
                                        </tr>
                                        <tr>
                                            <td>Account Name</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.accountName}</td>
                                        </tr>
                                        <tr>
                                            <td>Currency Code</td>
                                            <td></td>
                                            <td>:</td>
                                            <td>${mlBean.currencyCode}</td>
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

