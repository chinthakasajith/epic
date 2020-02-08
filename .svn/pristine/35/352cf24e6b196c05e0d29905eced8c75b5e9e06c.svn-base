<%-- 
    Document   : viewcasetype
    Created on : Jul 11, 2013, 2:11:26 PM
    Author     : asitha_l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>VIEW CASE TYPE</title>

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
                                    <table class="tit"> <tr> <td class="center"> <b>VIEW CASE TYPE</b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td><b>Case Type Code</b></td>                                           
                                            <td>${caseBean.caseTypeCode}</td>
                                            <td style="width: 100px"></td>                                             
                                            <td><b>Description</b></td>                                       
                                            <td>${caseBean.description}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Currency</b></td>                                          
                                            <td>${caseBean.currencyTypeDes}</td>
                                            <td style="width: 100px"></td>
                                            <td><b>Status</b></td>
                                            <td>${caseBean.statusDes}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Severity Value</b></td>
                                            <td>${caseBean.severityValue}</td>
                                            <td style="width: 100px"></td>
                                            <td><b>Criteria</b></td>                                           
                                            <td>${caseBean.entryCriteriaDes}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Entry Overdue Period</b></td>
                                            <td>
                                                <c:if test="${caseBean.entryOverDuePeriodCode!=null}">
                                                    ${caseBean.entryOverDuePeriodCode}
                                                </c:if>
                                                <c:if test="${caseBean.entryOverDuePeriodCode==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                            <td style="width: 100px"></td>
                                            <td><b>Overdue Amount Type</b></td>                                           
                                            <td>
                                                <c:if test="${caseBean.overDueAmountType!=null}">
                                                    ${caseBean.overDueAmountType}
                                                </c:if>
                                                <c:if test="${caseBean.overDueAmountType==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><b>Entry Overdue Amount</b></td>
                                            <td>
                                                <c:if test="${caseBean.entryOverDueAmount!=null}">
                                                    ${caseBean.entryOverDueAmount}
                                                </c:if>
                                                <c:if test="${caseBean.entryOverDueAmount==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                            <td style="width: 100px"></td> 
                                            <td><b>Over Limit Amount Type</b></td>
                                            <td>
                                                <c:if test="${caseBean.overLimitAmountType!=null}">
                                                    ${caseBean.overLimitAmountType}
                                                </c:if>
                                                <c:if test="${caseBean.overLimitAmountType==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><b>Entry Account Status</b></td>
                                            <td>${caseBean.entryAccStatusDes}</td>
                                            <td style="width: 100px"></td>
                                            <td><b>Entry Card Status</b></td>
                                            <td>${caseBean.entryCardStatusDes}</td>
                                        </tr>
                                        <tr>
                                            <td><b>Exit Overdue Amount</b></td>
                                            <td>
                                                <c:if test="${caseBean.exitOverDueAmount!=null}">
                                                    ${caseBean.exitOverDueAmount}
                                                </c:if>
                                                <c:if test="${caseBean.exitOverDueAmount==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                            <td style="width: 100px"></td>
                                            <td><b>Exit Overdue Period</b></td>
                                            <td>
                                                <c:if test="${caseBean.exitOverDuePeriodCode!=null}">
                                                    ${caseBean.exitOverDuePeriodCode}
                                                </c:if>
                                                <c:if test="${caseBean.exitOverDuePeriodCode==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><b>Exit Over Limit Amount</b></td>
                                            <td>
                                                <c:if test="${caseBean.exitOverLimitAmount!=null}">
                                                    ${caseBean.exitOverLimitAmount}
                                                </c:if>
                                                <c:if test="${caseBean.exitOverLimitAmount==null}">
                                                    N/A
                                                </c:if>
                                            </td>
                                            <td style="width: 100px"></td>
                                            <td><b>Exit Account Status</b></td>
                                            <td>${caseBean.exitAccStatusDes}</td>
                                        </tr>                                     
                                        <tr>
                                            <td><b>Exit Card Status</b></td>
                                            <td>${caseBean.exitCardStatusDes}</td>
                                            <td style="width: 100px"></td>
                                            <td><b>Process Automated</b></td>
                                            <td>
                                                <c:if test="${caseBean.processAutomatedStatus!=null}">
                                                    YES
                                                </c:if>
                                                <c:if test="${caseBean.processAutomatedStatus==null}">
                                                    NO
                                                </c:if>
                                            </td>
                                        </tr>                                      
                                        <tr>
                                            <td><b>Process Manual</b></td>
                                            <td>
                                                <c:if test="${caseBean.processManualStatus!=null}">
                                                    YES
                                                </c:if>
                                                <c:if test="${caseBean.processManualStatus==null}">
                                                    NO
                                                </c:if>
                                            </td>
                                            <td style="width: 100px"></td>
                                            <td><b>Remarks</b></td>
                                            <td>${caseBean.remarks}</td>                                         
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
