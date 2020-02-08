<%-- 
    Document   : cardrequesthome
    Created on : Jul 26, 2012, 3:52:08 PM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" >
            function invokeReset(cardNo) {
                window.location = "${pageContext.request.contextPath}/LoadCardRequestServlet?cardno=" + cardNo;
            }

//            function invokeManageRequest(reasonCode, cardNo) {
//
//                answer = confirm("Do you really want to place this request?")
//
//                if (answer != 0)
//                {
//                    document.cardrequest.action = "${pageContext.request.contextPath}/ManageCardRequestServlet?reasonCode=" + reasonCode;
//                    document.cardrequest.submit();
//                }
//                else
//                {
//
//                    document.cardrequest.action = "${pageContext.request.contextPath}/LoadCardRequestServlet?cardno=" + cardNo;
//                    document.cardrequest.submit();
//
//                }
//            }

            function invokeManageRequest(reasonCode, cardNo) {

//                answer = confirm("Do you really want to delete  "+value+" Application ?")
//                if (answer !=0)
//                {
//                    window.location = "${pageContext.request.contextPath}/DeleteApplicationMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadAppLicationModuleServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to place this request?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadCardRequestServlet?cardno=" + cardNo;
                            document.cardrequest.action = "${pageContext.request.contextPath}/LoadCardRequestServlet?cardno=" + cardNo;
                            document.cardrequest.submit();
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/ManageCardRequestServlet?reasonCode=" + reasonCode;
                            document.cardrequest.action = "${pageContext.request.contextPath}/ManageCardRequestServlet?reasonCode=" + reasonCode;
                            document.cardrequest.submit();
                        }
                    }
                });
            }

            function invokeCancel(cardNo)
            {

                document.cardrequest.action = "${pageContext.request.contextPath}/ViewCustomerMgtServlet?id=" + cardNo + "&section=CCCARD";
                document.cardrequest.submit();

            }
        </script>  
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CARDREQUEST%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

        </script>  

        <style>
            .button {
                background-color: ForestGreen;  
                border-radius: 5px;
                color: white;
                padding: .5em;
                text-decoration: none;
            }

            .button:focus,
            .button:hover {
                background-color: DarkGreen;
            }
        </style>
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



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                <c:if test="${operationtype=='replace'}" >

                                    <form method="POST" name="cardrequest" action="${pageContext.request.contextPath}/ManageCardRequestServlet">
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td >           
                                                        : ${cardBean.cardNumber}
                                                        <input type="hidden" value="${cardBean.cardNumber}" name="cardno"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type </td>
                                                    <td>
                                                        : ${cardBean.cardTypeDes}
                                                        <input type="hidden" value="${cardBean.cardType}" name="cardtype"/>
                                                        <input type="hidden" value="${cardBean.cardTypeDes}" name="cardtypedes"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Category</td>
                                                    <td>
                                                        : ${cardBean.cardCatDes}
                                                        <input type="hidden" value="${cardBean.cardCategory}" name="cardcategory"/>
                                                        <input type="hidden" value="${cardBean.cardCatDes}" name="cardcategorydes"/>
                                                    </td>

                                                </tr>
                                                <c:if test="${cardBean.cardDomain == 'CREDIT'}" >
                                                    <tr>
                                                        <td >Credit Limit</td>
                                                        <td>
                                                            : ${cardBean.creditLimit}
                                                            <input type="hidden" value="${cardBean.creditLimit}" name="creditlimit"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td >Cash Limit</td>
                                                        <td>
                                                            : ${cardBean.cashLimit}
                                                            <input type="hidden" value="${cardBean.cashLimit}" name="cashlimit"/>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <tr>
                                                    <td>Expiry Date</td>
                                                    <td>
                                                        : ${cardBean.expDate}
                                                        <input type="hidden" value="${cardBean.expDate}" name="expirydate"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Eligibility for renewal</td>
                                                    <td>
                                                        : ${cardBean.renewEligible}
                                                        <input type="hidden" value="${cardBean.renewEligible}" name="reneweligible"/>
                                                    </td>
                                                </tr>

                                                <c:if test="${cardBean.renewEligible == 'YES'}">
                                                    <tr>
                                                        <td>Renewal Confirmation</td>                                               
                                                        <td>
                                                            <c:if test="${cardBean.renewConfirm == 'YES'}">
                                                                : <input type="radio" name="renewconfirm" value="YES" checked="" />Yes
                                                            </c:if>
                                                            <c:if test="${cardBean.renewConfirm != 'YES'}">
                                                                : <input type="radio" name="renewconfirm" value="YES" />Yes
                                                            </c:if>
                                                            <c:if test="${cardBean.renewConfirm == 'NO'}">
                                                                <input type="radio" name="renewconfirm" value="NO" checked=""  />No
                                                            </c:if>
                                                            <c:if test="${cardBean.renewConfirm != 'NO'}">
                                                                <input type="radio" name="renewconfirm" value="NO" />No
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${cardBean.renewEligible == 'NO'}">
                                                    <tr>
                                                        <td>Renewal Confirmation</td>                                               
                                                        <td>
                                                            <c:if test="${cardBean.renewConfirm == 'YES'}">
                                                                : <input type="radio" name="renewconfirm" value="YES" disabled=""/>Yes
                                                            </c:if>
                                                            <c:if test="${cardBean.renewConfirm != 'YES'}">
                                                                : <input type="radio" name="renewconfirm" value="YES" checked=""  disabled=""/>Yes
                                                            </c:if>
                                                            <c:if test="${cardBean.renewConfirm == 'NO'}">
                                                                <input type="radio" name="renewconfirm" value="NO" disabled=""/>No
                                                            </c:if>
                                                            <c:if test="${cardBean.renewConfirm != 'NO'}">
                                                                <input type="radio" name="renewconfirm" value="NO" checked=""  disabled=""/>No
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:if>


                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td>: 
                                                        <select style="width: 100px" name="prioritycode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Remark</td>
                                                    <td>: 
                                                        <textarea name="remarks" maxlength="512">${cardBean.remark}</textarea>                                                
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td>   
                                                        <input type="button" class="defbutton" name="activate" value="Activate" onclick="invokeManageRequest('ACTI', '${cardBean.cardNumber}')"/> 
                                                        <input type="button" class="defbutton" name="replace" value="Replace" onclick="invokeManageRequest('CDRP', '${cardBean.cardNumber}')"/> 
                                                        <input type="button" class="defbutton" name="pinreissue" value="PIN Reissue" onclick="invokeManageRequest('PIRI', '${cardBean.cardNumber}')"/>
                                                        <input type="submit" class="defbutton" name="cdreissue" value="Card Reissue" onclick="invokeManageRequest('CDRI', '${cardBean.cardNumber}')"/> 
                                                        <input type="reset" class="defbutton" name="reset" value="Reset" onclick="invokeReset('${cardBean.cardNumber}')"/> 
                                                        <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('${cardBean.cardNumber}')"/></td>
                                                    <td></td>
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
        <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
