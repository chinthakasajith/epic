<%-- 
    Document   : merchantlocationhome
    Created on : May 11, 2012, 9:51:31 AM
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
                
                window.location = "${pageContext.request.contextPath}/LoadMerchantLocationServlet";
            }
             
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/LoadMerchantLocationServlet";
            }
            
            function invokeCreate(){
                
                window.location = "${pageContext.request.contextPath}/LoadCreateMerchantLocationServlet";
            }
            function invokeReset(reset){
                window.location = "${pageContext.request.contextPath}/LoadMerchantLocationServlet";
            }
            function invokeSearch()
            {

                document.searchMerchantform.action="${pageContext.request.contextPath}/SearchMerchantLocationServlet";
                document.searchMerchantform.submit();

            }
            function ConfirmDelete(merchantId)
            {
                answer = confirm("Are you sure you want to delete this Merchant Location ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteMerchantLocationServlet?merchantId="+merchantId;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadMerchantLocationServlet";
                }
                
            }
            
            function invokeView(merchantId)
            {

                window.location="${pageContext.request.contextPath}/ViewMerchantLocationServlet?merchantId="+merchantId;
                

            }
            
            function invokeLoadUpdate(merchantId,merchantCustomerNumber)
            {
                
                window.location="${pageContext.request.contextPath}/LoadUpdateMerchantLocationServlet?merchantId="+ merchantId + "&merchantCustomerNumber="+merchantCustomerNumber;
                

            }
            

        </script>
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.MERCHANT_LOCATION%>'                                  
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

                                    <form name="searchMerchantform" method="post" action="<%=request.getContextPath()%>/SearchMerchantLocationServlet">

                                        <table cellpadding="0" cellspacing="10"   >

                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><input type="text" name="merchantId"  maxlength="16" value="${merchantLocBean.merchantId}"></td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Description</td>
                                                <td><input type="text" name="description"  maxlength="16" value="${merchantLocBean.description}"></td>


                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Merchant Customer Name</td>
                                                <td><input type="text" name="merchantCustomerName"  maxlength="16" value="${merchantLocBean.merchantCustomerName}"></td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Area</td>
                                                <td><select  name="selectArea"  >
                                                        <option value="" selected>--SELECT--</option>

                                                        <c:forEach var="area" items="${sessionScope.SessionObject.areaList}">

                                                            <c:if test="${merchantLocBean.area==area.areaCode}">
                                                                <option value="${area.areaCode}" selected="true" >${area.description}</option>
                                                            </c:if>
                                                            <c:if test="${merchantLocBean.area!=area.areaCode}">
                                                                <option value="${area.areaCode}">${area.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;"><p>&nbsp;</p></td>
                                                <td></td>

                                            </tr>

                                            <tr >
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
                                                <th>Merchant Id</th>
                                                <th>Description</th>
                                                <th>Merchant Customer Number</th>
                                                <th>Merchant Customer Name</th>
                                                <th>Area</th>


                                                <th>View</th>
                                                <th>Update</th>
                                                <th >Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="merchantLoc" items="${beanList}">
                                                <tr>

                                                    <td >${merchantLoc.merchantId}</td>
                                                    <td >${merchantLoc.description}</td>
                                                    <td >${merchantLoc.merchantCustomerNumber}</td>
                                                    <td >${merchantLoc.merchantCustomerName}</td>
                                                    <td >${merchantLoc.areaDescription}</td>


                                                    <td  ><a href='#' onclick="invokeView('${merchantLoc.merchantId}')">View</a></td>
                                                    <td  ><a href='#' onclick="invokeLoadUpdate('${merchantLoc.merchantId}','${merchantLoc.merchantCustomerNumber}')">Update</a></td>
                                                    <td ><a  href='#' onclick="ConfirmDelete('${merchantLoc.merchantId}')">Delete</a></td>

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

                                                        <tr>
                                                            <td>Redemption Point </td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${merchantLocBean.redempoint}</td>
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
                                                                <input type="button" name="Cancel" value="Cancel" onclick="invokeCancel()" style="width: 100px; height: 30px;"/>
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
                                                            <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeCancel()" value="Cancel">
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
