<%-- 
    Document   : acquiresearchadvancedview
    Created on : Dec 3, 2012, 4:15:48 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>



<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script lang="javascript">
            
            function invokeBackToSearch( )
            {
                window.location = "${pageContext.request.contextPath}/SearchMerchantMgtServlet";
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.AQCALLCENTERSEARCH%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                             
        </script> 

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3">
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table> 

                                <c:if test="${operationtype=='cus'}" >
                                    <form method="POST" action="">

                                        <div class="selector" id="tabs">
                                            <ul>
                                                <li><a href="#tabs-1">General </a></li>
                                                <li><a href="#tabs-2">MCC, Transaction & Currency</a></li>
                                            </ul>

                                            <!--  ////////////////////////      Tab Number1        /////////////////////                            -->
                                            <div id="tabs-1" >

                                                <table border="0">
                                                    <tbody>
                                                        <tr>
                                                            <td><b> Merchant Customer Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Merchant Customer Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.merchantCustomerNumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Merchant Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.merchantName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Legal Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.legalName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Address</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.address1}</td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.address2}</td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.address3}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>Area</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.areaDescription}</td>
                                                        </tr>
                                                        <!--                                                        <tr>
                                                                                                                    <td>Postal Code</td>
                                                                                                                    <td></td>
                                                                                                                    <td> : </td>
                                                                                                                    <td></td>
                                                                                                                    <td>${merchantBean.postalCode}</td>
                                                                                                                </tr>-->
                                                        <tr>
                                                            <td>Country</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.countryDescription}</td>
                                                        </tr>


                                                        <!--       /////////////////////////////////////////////////////////////////////////////////////////////////////                                         -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td><b> Contact Person Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Title</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.cpTitle}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.cpFirstName} ${merchantBean.cpMiddleName} ${merchantBean.cpLastName}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>TP Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.tpNumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Fax</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.fax}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Email</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.eMail}</td>
                                                        </tr>
                                                        <!--         /////////////////////////////////////////Profile Details//////////////////////////////////////////////////////////////////////                                       -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td><b> Profile Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Fee Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.feeProfDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Commission Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.commissionProfDes}</td>
                                                        </tr>
                                                        <!--                                                        <tr>
                                                                                                                    <td>Risk Profile</td>
                                                                                                                    <td></td>
                                                                                                                    <td> : </td>
                                                                                                                    <td></td>
                                                                                                                    <td>${merchantBean.riskProfDes}</td>
                                                                                                                </tr>-->
                                                        <!--  ////////////////////////////////Payment & Statement Details  ////////////////////////////    -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr> 
                                                            <td> <b>Payment & Statement Details </b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        
                                                        <tr>
                                                            <td>Statement Cycle</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.statementCycleDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Payment Cycle</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.paymentCycleDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Payment Mode</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.paymentModeDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Generate Statement </td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.statementStatus}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Payment maintenance </td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.paymentStatus}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Currency Type </td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.currencyTypeDes}</td>
                                                        </tr>
                                                        
                                                        

                                                        <!--  ////////////////////////////////////////////////////////Bank Details////////////////////////////////////////////////////////////////////////////                                          -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td> <b>Bank & Account Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Bank Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.bankName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Branch Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.branchName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Account Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.accountNumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Account Type</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.accountTypeDescription}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Account Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.accountName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Merchant Account Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.merchantAccountNo}</td>
                                                        </tr>
                                                        <!--   //////////////////////////////////////////////////Application Details/////////////////////////////////////////////////////////////////////////////                                             -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td><b> Application Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Application Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.applicationDate}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Activation Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.activationDate}</td>
                                                        </tr>
                                                        
                                                        <tr>
                                                            <td>Status</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.statusDescription}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>Last Updated User</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.lastUpdatedUser}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Last Updated Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.lastUpdatedTime}</td>
                                                        </tr>
                                                        <tr>
                                                            <td> Created Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.creatTime}</td>
                                                        </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10">

                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr>
                                                        <td>
                                                        <td><input type="button" name="add" class="nexttab" style="width: 100px; height: 30px;" value=" Next">
                                                            <input type="button" name="Cancel" value="Cancel" onclick="invokeBackToSearch()" style="width: 100px; height: 30px;"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>

                                            </div>

                                            <div id="tabs-2">

                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr>
                                                        <td colspan="3">Select Merchant Categories  <B> <c:out value="${merchant}"/></B></td>
                                                    </tr>

                                                    <tr>
                                                        <td>
                                                            <h4><b>All Merchant Categories</b></h4>
                                                            <select name="notAssigndnedMerchantCatogoryList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="notAs" items="${notAssigndnMerchantCatogoryList}">
                                                                    <option value="${notAs.mCCCode}" >${notAs.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >

                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Merchant Categories</b></h4>
                                                            <select name="assigndnedMerchantCatogoryList" style="width: 200px" id=out multiple="multiple"   size=10>

                                                                <c:forEach var="as" items="${assigndnMerchantCatogoryList}">
                                                                    <option value="${as.mCCCode}" >${as.description}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10" >
                                                    <tr>
                                                        <td colspan="3">Select Transaction Types <B> <c:out value="${merchant}"/></B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Transaction Types</b></h4>
                                                            <select name="notAssigndnedTxnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                                <c:forEach  var="notAs" items="${notAssigndnTxnTypeList}">
                                                                    <option value="${notAs.transactionTypeCode}" >${notAs.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >

                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Transaction Types</b></h4>
                                                            <select name="assigndnedTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>

                                                                <c:forEach var="as" items="${assigndnTxnTypeList}">
                                                                    <option value="${as.transactionTypeCode}" >${as.description}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td colspan="0">Select Currency Type <B> <c:out value="${currency}"/></B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Currency Types</b></h4>
                                                            <select name="notAssignCurrencyList" style="width: 200px"  id=in0 multiple="multiple"  size=10>
                                                                <c:forEach  var="nas" items="${notAssignCurrencyList}">
                                                                    <option value="${nas.currencyCode}" >${nas.currencyDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >

                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Currency Types</b></h4>
                                                            <select name="assignCurrencyList" style="width: 200px" id=out0 multiple="multiple"   size=10>
                                                                <c:forEach var="as" items="${assignCurrencyList}">
                                                                    <option value="${as.currencyCode}" >${as.currencyDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td>

                                                            <input type="button" value="Back " name="backtab" style="width: 100px" class="previoustab">
                                                            <input type="button" name="reset"  style="width: 100px;" onclick="invokeBackToSearch()" value="Cancel">
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>

                                                        </td>
                                                        <td>

                                                        </td>
                                                    </tr>

                                                </table>
                                            </div>

                                        </div>

                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='loc'}" >
                                    <form method="POST" action="">

                                        <div class="selector" id="tabs">
                                            <ul>
                                                <li><a href="#tabs-1">General </a></li>
                                                <li><a href="#tabs-2">MCC, Transaction & Currency </a></li>
                                            </ul>

                                            <!--  ////////////////////////      Tab Number1        /////////////////////                            -->
                                            <div id="tabs-1" >
                                                <table border="0">
                                                    <tbody>
                                                        <tr>
                                                            <td><b> Merchant Location Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Merchant Id</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.merchantId}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Description</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.description}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Merchant Customer Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.merchantCustomerName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Address</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.address1}</td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.address2}</td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.address3}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>Area</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.areaDescription}</td>
                                                        </tr>
                                                        <!--                                                        <tr>
                                                                                                                    <td>Postal Code</td>
                                                                                                                    <td></td>
                                                                                                                    <td> : </td>
                                                                                                                    <td></td>
                                                                                                                    <td>${merchantLocBean.postalCode}</td>
                                                                                                                </tr>-->
                                                        <tr>
                                                            <td>Country</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.countryDescription}</td>
                                                        </tr>


                                                        <!--       /////////////////////////////////////////////////////////////////////////////////////////////////////                                         -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td><b> Contact Person Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Title</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.cpTitle}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.cpFirstName} ${merchantLocBean.cpMiddleName} ${merchantLocBean.cpLastName} </td>
                                                        </tr>

                                                        <tr>
                                                            <td>Position</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.cpPosotion}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>TP Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.tpNumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Fax</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.fax}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Email</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.eMail}</td>
                                                        </tr>
                                                        <!--         /////////////////////////////////////////Profile Details//////////////////////////////////////////////////////////////////////                                       -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td><b> Profile Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Fee Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.feeProfDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Commission Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.commissionProfDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Risk Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.riskProfDes}</td>
                                                        </tr>

                                                        <!--  ////////////////////////////////Payment & Statement Details  ////////////////////////////    -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr> 
                                                            <td> <b>Payment & Statement Details </b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>

                                                        <tr>
                                                            <td>Statement Cycle</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.statementCycleDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Payment Cycle</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.paymentCycleDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Payment Mode</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.paymentModeDes}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Generate Statement</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.statementStatus}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Currency Type </td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.currencyTypeDes}</td>
                                                        </tr>



                                                        <!--  ////////////////////////////////////////////////////////Bank Details////////////////////////////////////////////////////////////////////////////                                          -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td> <b>Bank & Account Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Bank Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.bankName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Branch Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.branchName}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Account Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.accountNumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Account Type</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.accountTypeDescription}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Account Name</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.accountName}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>Merchant Account Number</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.merchantAccountNo}</td>
                                                        </tr>

                                                        <!--   //////////////////////////////////////////////////Application Details/////////////////////////////////////////////////////////////////////////////                                             -->
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td><b> Activation Details</b><hr /> </td>
                                                        </tr>
                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Activation Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.activationDate}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>Status</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.statusDescription}</td>
                                                        </tr>

                                                        <tr>
                                                            <td>Last Updated User</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.lastUpdatedUser}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Last Updated Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.lastUpdatedTime}</td>
                                                        </tr>
                                                        <tr>
                                                            <td> Created Date</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.creatTime}</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <table cellpadding="0" cellspacing="10" >
                                                    <tbody>                                                     
                                                        <tr>
                                                            <td><input type="button" name="add" class="nexttab" style="width: 100px; height: 30px;" value=" Next">
                                                                <input type="button" name="Cancel" value="Cancel" onclick="invokeBackToSearch()" style="width: 100px; height: 30px;"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                            </td>
                                                            <td>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>

                                            </div>
                                            <!--    /////////////////    Tab Number2         ///////////////////// -->
                                            <div id="tabs-2">

                                                <table cellpadding="0" cellspacing="10"  >

                                                    <tr>
                                                        <td colspan="3">Select Merchant Categories  <B> <c:out value="${merchant}"/></B></td>
                                                    </tr>

                                                    <tr>
                                                        <td>
                                                            <h4><b>All Merchant Categories</b></h4>
                                                            <select name="notAssigndnedMerchantCatogoryList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="notAs" items="${notAssigndnedMerchantCatogoryList}">
                                                                    <option value="${notAs.mCCCode}" >${notAs.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >

                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Merchant Categories</b></h4>
                                                            <select name="assigndnedMerchantCatogoryList" style="width: 200px" id=out multiple="multiple"   size=10>

                                                                <c:forEach var="as" items="${assigndnedMerchantCatogoryList}">
                                                                    <option value="${as.mCCCode}" >${as.description}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10" >
                                                    <tr>
                                                        <td colspan="3">Select Transaction Types <B> <c:out value="${merchant}"/></B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Transaction Types</b></h4>
                                                            <select name="notAssigndnedTxnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                                <c:forEach  var="notAs" items="${notAssigndnedTxnTypeList}">
                                                                    <option value="${notAs.transactionTypeCode}" >${notAs.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >

                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Transaction Types</b></h4>
                                                            <select name="assigndnedTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>

                                                                <c:forEach var="as" items="${assigndnedTxnTypeList}">
                                                                    <option value="${as.transactionTypeCode}" >${as.description}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>

                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td colspan="0">Select Currency Type <B> <c:out value="${currency}"/></B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Currency Types</b></h4>
                                                            <select name="notAssignCurrencyList" style="width: 200px"  id=in0 multiple="multiple"  size=10>
                                                                <c:forEach  var="nas" items="${notAssignCurrencyList}">
                                                                    <option value="${nas.currencyCode}" >${nas.currencyDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >

                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Currency Types</b></h4>
                                                            <select name="assignCurrencyList" style="width: 200px" id=out0 multiple="multiple"   size=10>
                                                                <c:forEach var="as" items="${assignCurrencyList}">
                                                                    <option value="${as.currencyCode}" >${as.currencyDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td>
                                                            <input type="button" value="Back " name="backtab" style="width: 100px; height:30px;" class="previoustab">
                                                            <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeBackToSearch()" value="Cancel">
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                        </td>
                                                        <td>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>

                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='ter'}" >
                                    <form method="POST" action="">

                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Terminal ID</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.terminalID}</td>
                                                </tr>     
                                                <tr>
                                                    <td>Terminal Name</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.name}</td>
                                                </tr>
                                                <tr>
                                                    <td>Merchant ID</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.merchantID == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.merchantID != null}">
                                                        <td>${trmnlBean.merchantID}</td>
                                                    </c:if>

                                                </tr> 
                                                <tr>
                                                    <td>Merchant</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>

                                                    <c:if test="${trmnlBean.merchantDes == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.merchantDes != null}">
                                                        <td>${trmnlBean.merchantDes}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>MCC</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.mcc == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.mcc != null}">
                                                        <td>${trmnlBean.mcc}</td>
                                                    </c:if>

                                                </tr> 
                                                <tr>
                                                    <td>Currency</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.currency == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.currency != null}">
                                                        <td>${trmnlBean.currency}</td>
                                                    </c:if>

                                                </tr> 
                                                <tr>
                                                    <td>Serial Number</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.serialNo}</td>
                                                </tr>
                                                <tr>
                                                    <td>Manufacturer</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.manufactDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Model</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.modelDes}</td>
                                                </tr><tr>
                                                    <td>Installation Date</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.installationDate}</td>
                                                </tr><tr>
                                                    <td>Activation Date</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <c:if test="${trmnlBean.activationDate == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <c:if test="${trmnlBean.activationDate != null}">
                                                        <td>${trmnlBean.activationDate}</td>
                                                    </c:if>

                                                </tr><tr>
                                                    <td>Allocation Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.alloStatus}</td>
                                                </tr>

                                                <tr>
                                                    <td>Terminal Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${trmnlBean.terminalStatusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Transactions</td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>

                                                    <td>
                                                        <h4><b>Not Assigned Transactions</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassign" items="${notAssignedTxn}">
                                                                <option value="${notassign.transactions}" >${notassign.transactionDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td><td></td>
                                                    <!--                                                    <td align="center" >
                                                                                                            <input type=button value=">" onclick="moveout()" class="buttonsize"/><br>
                                                                                                            <input type=button value="<" onclick="movein()" class="buttonsize"/><br>
                                                                                                            <input type=button value=">>" onclick="moveallout()" class="buttonsize"/><br>
                                                                                                            <input type=button value="<<" onclick="moveallin()" class="buttonsize"/>
                                                                                                        </td>-->
                                                    <td></td>


                                                    <td>
                                                        <h4><b>Assigned Transactions</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach var="assign" items="${assignedTxn}">
                                                                <option value="${assign.transactions}" >${assign.transactionDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" name="back" style=" width:100px " onclick="invokeBackToSearch()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>




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
