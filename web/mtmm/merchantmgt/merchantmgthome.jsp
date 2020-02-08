<%-- 
    Document   : merchantmgthome
    Created on : Apr 27, 2012, 1:51:16 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->




        <script >
            function invokeBack(){
                
                window.location = "${pageContext.request.contextPath}/LoadMerchantCustomerServlet";
            }
            
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/LoadMerchantCustomerServlet";
            }
            
            function invokeCreate(){
                
                window.location = "${pageContext.request.contextPath}/LoadCreatMerchantCustomerServlet";
            }
            function invokeReset(reset){
                window.location = "${pageContext.request.contextPath}/LoadMerchantCustomerServlet";
            }
            function invokeSearch()
            {
              
                document.searchMerchantform.action="${pageContext.request.contextPath}/SearchMerchantCustomerServlet";
                document.searchMerchantform.submit();

            }
            function ConfirmDelete(merchantCustomerNumber)
            {
                answer = confirm("Are you sure you want to delete this Merchant Customer ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteMerchantCustomerServlet?merchantCustomerNumber="+merchantCustomerNumber;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadMerchantCustomerServlet";
                }
                
            }
            
            function invokeView(merchantCustomerNumber)
            {

                window.location="${pageContext.request.contextPath}/ViewMerchantCustomerServlet?merchantCustomerNumber="+merchantCustomerNumber;
                

            }
            
            function invokeLoadUpdate(merchantCustomerNumber,tab)
            {
                
                window.location="${pageContext.request.contextPath}/LoadUpdateMerchantCustomerServlet?merchantCustomerNumber="+merchantCustomerNumber+"&tab="+tab;
                

            }
            

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.MERCHANT_CUSTOMER%>'                                  
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

        <title>CMS MERCHANT MANAGEMENT</title>
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
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <c:if test="${operationtype=='search'}">

                                    <form name="searchMerchantform" method="post" action="<%=request.getContextPath()%>/SearchMerchantCustomerServlet">

                                        <table cellpadding="0" cellspacing="10"   >

                                            <tr>
                                                <td style="width: 200px;">Merchant Customer Number</td>
                                                <td><input type="text" name="merchantCustomerNumber"  maxlength="16" value="${merchantBean.merchantCustomerNumber}"></td>
<!--                                                <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.merchantCustomerNumber} </label></td>-->

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Merchant Name</td>
                                                <td><input type="text" name="merchantName"  maxlength="16" value="${merchantBean.merchantName}"></td>
<!--                                                <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.merchantName} </label></td>-->

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Legal Name</td>
                                                <td><input type="text" name="legalName"  maxlength="16" value="${merchantBean.legalName}"></td>
<!--                                                <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.legalName} </label></td>-->

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Area</td>
                                                <td><select  name="selectArea"  >
                                                        <option value="" selected>--SELECT--</option>

                                                        <c:forEach var="area" items="${sessionScope.SessionObject.areaList}">

                                                            <c:if test="${merchantBean.area==area.areaCode}">
                                                                <option value="${area.areaCode}" selected="true" >${area.description}</option>
                                                            </c:if>
                                                            <c:if test="${merchantBean.area!=area.areaCode}">
                                                                <option value="${area.areaCode}">${area.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>
<!--                                                <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.area} </label></td>-->

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><p>&nbsp;</p></td>
                                                <td></td>

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <input type="button" name="create" value="Create" onclick="invokeCreate()" style="width: 100px;"/>
                                                    <input type="submit" name="search" value="Search" onclick="invokeSearch()" style="width: 100px;"/>&nbsp;
                                                    <input type="button" onclick="invokeReset('add')" name="next" value="Reset" style="width: 100px;"/>&nbsp;
                                                </td>


                                            </tr>


                                        </table>

                                    </form>
                                    <!--/////////////////////////////////////////////End Search Form  ///////////////////////////////////////////////////////////-->
                                    <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                    <br></br>

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Merchant Customer Number</th>
                                                <th>Merchant Name</th>
                                                <th>Legal Name</th>
                                                <th>Area</th>




                                                <th>View</th>
                                                <th>Update</th>
                                                <th >Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="merchant" items="${beanList}">
                                                <tr>

                                                    <td >${merchant.merchantCustomerNumber}</td>
                                                    <td >${merchant.merchantName}</td>
                                                    <td >${merchant.legalName}</td>
                                                    <td >${merchant.areaDescription}</td>





                                                    <td  ><a href='#' onclick="invokeView('${merchant.merchantCustomerNumber}')">View</a></td>
                                                    <td  ><a href='#' onclick="invokeLoadUpdate('${merchant.merchantCustomerNumber}','${"one"}')">Update</a></td>
                                                    <td ><a  href='#' onclick="ConfirmDelete('${merchant.merchantCustomerNumber}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 

                                </c:if>



                                <!--/////////////////////////////////////////////Start view Form  ///////////////////////////////////////////////////////////-->

                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewMerchantForm">

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
                                                        <tr>
                                                            <td>Redemption Point </td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantBean.redempoint}</td>
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
                                                            <input type="button" name="Cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px; height: 30px;"/>
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
                                                            <input type="button" name="reset"  style="width: 100px;" onclick="invokeCancel()" value="Cancel">
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
                                    <br /><br />




                                </c:if>

                                <!--/////////////////////////////////////////////End View Template  ///////////////////////////////////////////////////////////-->




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