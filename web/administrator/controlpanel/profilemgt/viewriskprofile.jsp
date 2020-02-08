<%-- 
    Document   : viewriskprofile
    Created on : May 23, 2012, 5:24:33 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>-->


        <script type="text/javascript">
            
            function invokeBack(){
                
                window.location = "${pageContext.request.contextPath}/LoadRiskProfileMgtServlet";
            }
             
            function invokeCancel(){
                
                window.location = "${pageContext.request.contextPath}/LoadRiskProfileMgtServlet";
            }
        </script>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.RISKPROFILE%>'                                  
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
            </div>
            <div class="main" >
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>
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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--        -----------------------------------------start view  ----------------------------------------- -->


                                    <form action="" method="POST" name="viewMerchantForm">
                                        <div class="selector" id="tabs">
                                            <ul>
                                                <li><a href="#tabs-1">General </a></li>
                                                <li><a href="#tabs-2">Country, MCC, Transaction & Currency</a></li>
                                            </ul>

                                            <!--  ////////////////////////      Tab Number1        /////////////////////                            -->
                                            <div id="tabs-1" >

                                                <table border="0">
                                                    <tbody>

                                                        <tr></tr>
                                                        <tr></tr>
                                                        <tr>
                                                            <td>Risk Profile Code</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${riskProfBean.riskProfCode}</td>
                                                    </tr>

                                                    <tr>
                                                        <td>Description</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.description}</td>
                                                    </tr>

                                                    <tr>
                                                        <td>Status</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.statusDes}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Profile Type</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.profileTypeDescription}</td>
                                                    </tr>
                                                    <c:if test="${riskProfBean.profileType =='RPACT'}">
                                                        <tr>
                                                            <td>Customer Risk Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${description}</td>
                                                        </tr>
                                                    </c:if>
                                                    <c:if test="${riskProfBean.profileType =='RPCRD'}">
                                                        <tr>
                                                            <td>Account Risk Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${description}</td>
                                                        </tr>
                                                    </c:if>
                                                    <c:if test="${riskProfBean.profileType =='RPTER'}">
                                                        <tr>
                                                            <td>Merchant Risk Profile</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${description}</td>
                                                        </tr>
                                                    </c:if>
                                                    <tr>
                                                        <td>Period (days)</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.peroid}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Minimum Single Transaction Limit</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.minSingleTxnLimit}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Maximum Single Transaction Limit</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.maxSingleTxnLimit}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Minimum Single Cash Transaction Limit</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.minSingleCashLimit}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Maximum Single Cash Transaction Limit</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.maxSingleCashLimit}</td>
                                                    </tr>
<!--                                                    <tr>
                                                        <td>Maximum Transaction Count</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.maxTxnCount}</td>
                                                    </tr>

                                                    <tr>
                                                        <td>Maximum Cash Transaction Count</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.maxCashCount}</td>
                                                    </tr>

                                                    <tr>
                                                        <td>Maximum Total Transaction Amount</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.maxTotTxnAmount}</td>
                                                    </tr>


                                                    <tr>
                                                        <td>Maximum Total Cash Transaction Amount</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.maxTotCashTxnAmount}</td>
                                                    </tr>-->
                                                    
                                                    <c:if test="${riskProfBean.profileType =='RPCRD'||riskProfBean.profileType =='RPACT'||riskProfBean.profileType =='RPCUS'}">
                                                    
                                                    <tr>
                                                        <td>Extra Authorization Limit</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.extraAuthLimit} %</td>

                                                    </tr>
                                                    
                                                    </c:if>
                                                    
                                                    <c:if test="${riskProfBean.profileType =='RPCRD'}">
                                                        <tr>
                                                            <td>Maximum Pin Count</td>
                                                            <td></td>
                                                            <td> : </td>
                                                            <td></td>
                                                            <td>${riskProfBean.maxPinCount}</td>
                                                        </tr>
                                                    </c:if>
                                                    <tr>
                                                        <td>Created Date</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.createdDate}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Last Updated Date</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.lastUpdatedDate}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Last Updated User</td>
                                                        <td></td>
                                                        <td> : </td>
                                                        <td></td>
                                                        <td>${riskProfBean.lastUpdatedUser}</td>
                                                    </tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                    <tr></tr>
                                                </tbody>
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
                                        <!--    /////////////////    Tab Number2         ///////////////////// -->
                                        <div id="tabs-2">
                                            <table cellpadding="0" cellspacing="10"  >
                                                <tr>
                                                    <td colspan="0">Blocked Countries <B> <c:out value="${country}"/></B></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>Allowed Countries</b></h4>
                                                        <select name="countryList" style="width: 200px"  id=in0 multiple="multiple"  size=10>
                                                            <c:forEach  var="country" items="${notAssignedCountryList}">
                                                                <option value="${country.alphaSecond}" >${country.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <h4><b>Blocked Countries</b></h4>
                                                        <select name="assignCountryList" style="width: 200px" id=out0 multiple="multiple"   size=10>
                                                            <c:forEach  var="country" items="${assignedCountryList}">
                                                                <option value="${country.alphaSecond}" >${country.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table cellpadding="0" cellspacing="10"  >
                                                <tr>
                                                    <td colspan="0">Blocked Merchant Categories <B> <c:out value="${merchant}"/></B></td>
                                                </tr>

                                                <tr>
                                                    <td>
                                                        <h4><b>Allowed Merchant Categories</b></h4>
                                                        <select name="mccList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="merchant" items="${notAssignedMerchantCategoryList}">
                                                                <option value="${merchant.mCCCode}" >${merchant.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                    <td>
                                                        <h4><b>Blocked Merchant Categories</b></h4>
                                                        <select name="assignMcclist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach  var="merchant" items="${assignedMerchantCategoryList}">
                                                                <option value="${merchant.mCCCode}" >${merchant.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td colspan="0">Blocked Transaction Types <B> <c:out value="${txn}"/></B></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>Allowed Transaction Types</b></h4>
                                                        <select name="txnTypeList" style="width: 200px"  id=in2 multiple="multiple"  size=10>
                                                            <c:forEach  var="txn" items="${notAssignedTypeList}">
                                                                <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                    <td>
                                                        <h4><b>Blocked Transaction Types</b></h4>
                                                        <select name="assignTxnTypeList" style="width: 200px" id=out2 multiple="multiple"   size=10>
                                                            <c:forEach  var="txn" items="${assignedTypeList}">
                                                                <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td colspan="0">Blocked Currency Types <B> <c:out value="${currency}"/></B></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4><b>Allowed Currency Types</b></h4>
                                                        <select name="currencyList" style="width: 200px"  id=in3 multiple="multiple"  size=10>
                                                            <c:forEach  var="currency" items="${notAssignedCurrencyList}">
                                                                <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                    <td>
                                                        <h4><b>Blocked Currency Types</b></h4>
                                                        <select name="assignCurrencyList" style="width: 200px" id=out3 multiple="multiple"   size=10>
                                                            <c:forEach  var="currency" items="${assignedCurrencyList}">
                                                                <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </table>

                                            <c:if test="${bin == 'bin'}">
                                                <table cellpadding="0" cellspacing="10">
                                                    <tr>
                                                        <td colspan="0">Blocked Bin Types <B> </B></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>Allowed Bin&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h4>
                                                            <select name="binList" style="width: 200px"  id=in4 multiple="multiple"  size=10>
                                                                <c:forEach  var="bin" items="${notAssignedBinList}">
                                                                    <option value="${bin.binCode}" >${bin.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <td>
                                                            <h4><b>Blocked Bin</b></h4>
                                                            <select name="assignBinList" style="width: 200px" id=out4 multiple="multiple"   size=10>
                                                                <c:forEach  var="bin" items="${assignedBinList}">
                                                                    <option value="${bin.binCode}" >${bin.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </c:if>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr>
                                                    <td>

                                                        <input type="button" value="Back " name="backtab" style="width: 100px; height: 30px;" class="previoustab">
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

                                <!--                                              -->
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
