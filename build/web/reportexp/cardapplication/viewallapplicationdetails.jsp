<%-- 
    Document   : viewallapplicationdetails
    Created on : Nov 30, 2012, 3:33:04 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script >
         
            function invokeBack()
            {
              
                  window.location = "${pageContext.request.contextPath}/SearchApplicationDetailsServlet?id=1";
  //              window.location = "${pageContext.request.contextPath}/LoadApplicationDetailsServlet";
               
               
            }
         
        </script>


        <title>CMS MERCHANT MANAGEMENT</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header">
            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center"><b> CARD APPLICATION DETAILS REPORT </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form name="allApplicationDetails" method="POST" action="">

                                    <table cellpadding="0" cellspacing="10"   >

                                        <tbody>
                                            <tr>
                                                <td>Application ID</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.applicationID}</td>
                                            </tr>

                                            <tr>
                                                <td>Identification Type</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.identificationType}</td>
                                            </tr>

                                            <tr>
                                                <td>Identification Number</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.identificationNo}</td>
                                            </tr>
                                            
                                            <tr>
                                                <td>Full Name</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.fullName}</td>
                                            </tr>

                                            <tr>
                                                <td>Currency</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.currencyDes}</td>
                                            </tr>

                                            <tr>
                                                <td>Credit Limit</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.creditLimit}</td>
                                            </tr>
                                            
                                            <tr>
                                                <td>Ref Emp No</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.refEmpNo}</td>
                                            </tr>                                            

                                            <tr>
                                                <td>Branch</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.branch}</td>
                                            </tr>

                                            <tr>
                                                <td>Assign User</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.assignUser}</td>
                                            </tr>

                                            <tr>
                                                <td>AssignStatus</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.assignStatus}</td>
                                            </tr>

                                            <tr>
                                                <td>Credit Score</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.creditScore}</td>
                                            </tr>

                                            <tr>
                                                <td>Staff Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.staffStatus}</td>
                                            </tr>

                                            <tr>
                                                <td>Email</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.email}</td>
                                            </tr>

                                            <tr>
                                                <td>Address</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.address1}, ${personalBean.address2}, ${personalBean.address3}</td>
                                            </tr>

                                            <tr>
                                                <td>Mothers Maiden Name</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.mothersMaidenName}</td>
                                            </tr>

                                            <tr>
                                                <td>Name On card</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${personalBean.nameOncard}</td>
                                            </tr>

<!--                                            <tr>
                                                <td>Cash Transaction Count</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cashTxnCount}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>Total Transaction Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.totTxnAmount}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>Total Cash Transaction Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.totCashTxnAmount}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>OTB Credit</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.otdCredit}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>OTB Cash</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.otdCash}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>Temporary Credit Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCreditAmount}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>Temporary Cash Amount</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCashAmount}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>Temporary Credit Limit Increment</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCreditLimitIncrment}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>Temporary Cash Limit Increment</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tempCashLimitIncrement}</td>
                                            </tr>-->

<!--                                            <tr>
                                                <td>TLE Start Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tleStartDate}</td>
                                            </tr>

                                            <tr>
                                                <td>TLE End Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tleEndDate}</td>
                                            </tr>
                                            <tr>
                                                <td>TLE Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.tleStatusDes}</td>
                                            </tr>
                                            <tr>
                                                <td>IBM Offset</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.ibmOffset}</td>
                                            </tr>
                                            <tr>
                                                <td>Pin Method</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.pinMethod}</td>
                                            </tr>
                                            <tr>
                                                <td>PVV</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.pvv}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Verification Method</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.cardVerificationMethod}</td>
                                            </tr>
                                            <tr>
                                                <td>EMV Method</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.emvMethod}</td>
                                            </tr>
                                            <tr>
                                                <td>Create Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.createTime}</td>
                                            </tr>
                                            <tr>
                                                <td>Last Updated User</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.lastUpdatedUser}</td>
                                            </tr>
                                            <tr>
                                                <td>Last Updated Time</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${cardBean.lastUpdatedTime}</td>
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${applicationBean.applicationStatus}</td>
                                            </tr>-->

                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <input type="button" name="back" value="Back" onclick="invokeBack()" style="width: 100px;"/>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>

                                </form>
                                <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->


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
