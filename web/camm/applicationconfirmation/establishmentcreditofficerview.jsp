<%-- 
    Document   : establishmentcreditofficerview
    Created on : Jul 22, 2016, 10:36:56 AM
    Author     : chinthaka_r
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/windowcss/dhtmlwindow.css" type="text/css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/dhtmlwindow.js"></script>

        <script language="javascript">


            function invokeReset() {
				<%--
                window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet";
				--%>
				 $("#control").replaceWith("<input type='file' id='control' />");
				 $("#crdtOfficerRecCreditLimit").replaceWith("<input type='text' id='crdtOfficerRecCreditLimit' />");               
				 document.getElementById("crdtOfficerRecProduct").selectedIndex = 0;
            }

            function uploadLetters(applicationId)
            {

                document.recommendLetter.action = "${pageContext.request.contextPath}/UploadRcomondationLetterServlet?applicationId=" + applicationId + "&crdtOfficerRecCreditLimit=" + $("#crdtOfficerRecCreditLimit").val() + "&crdtOfficerRecProduct=" + $("#crdtOfficerRecProduct").val();
                document.recommendLetter.submit();

            }

            function resetLetterField()
            {

              
                $("#crdtOfficerRecCreditLimit").val("");


            }


            function invokeWindow(identityType, applicationId, verificationCategory, documentType, fileName) {

                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId=" + applicationId + "&verificationCategory=" + verificationCategory + "&documentType=" + documentType + "&fileName=" + fileName, identityType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");

            }
            function invokeBreakDown(value) {
                $.post("${pageContext.request.contextPath}/ViewBreakDownServlet", {breakDown: value},
                function (data) {
                    if (data == "success") {
                        $.colorbox({href: "${pageContext.request.contextPath}/camm/applicationconfirmation/breakdownview.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }

                                });

                            }

        </script>

        <title>EPIC CMS CREDIT OFFICER REVIEW</title>
    </head>
    <body>
        <div class="container">
            <div class="header">

            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>

                <div class="content" style="height: 400px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>

                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  ------------------------------------->
                                <table class="tit"> <tr> <td   class="center">  CREDIT OFFICER </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td style="width: 200px;"> Application ID</td>
                                            <td><b><font color="#000000"> ${cardApplicationList.applicationId}</font></b> </td>

                                        <td style="width: 135px;"></td> 

                                        <td style="width: 200px;"> Application Category</td>
                                        <td><b><font color="#000000">${cardApplicationList.appTypeDes}</font></b> </td>
                                    </tr>

                                </table>  
                                <form  ENCTYPE="multipart/form-data"  METHOD=POST name="recommendLetter">
                                    <table cellpadding="0" cellspacing="10"  >
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Company Information</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Identification Type</td>
                                            <td><b><font color="#000000">${cardApplicationList.identificationType}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Identification Number</td>
                                            <td><b><font color="#000000">${cardApplicationList.identificationNumber}</font></b></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Name of the company</td>
                                            <td><b><font color="#000000">${establishmentDetailsList.companyName}</font></b></td> 
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Nature of Business</td>
                                            <td><b><font color="#000000">${establishmentDetailsList.natureOfTheBusiness}</font></b></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td style="width: 200px;">Annual turnover</td>
                                            <td><b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.annualTurnover}"/></font></b></td>
                                            <td></td>
                                            <td style="width: 100px;">Share capital</td>
                                            <td><b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.shareCapital}"/></font></b></td>
                                            <td></td>
                                            <td style="width: 100px;">Net profit</td>
                                            <td><b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.netProfit}"/></font></b></td>
                                        </tr>
                                        <tr>
                                            <td colspan="10" ><hr /></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Verify Documents</font></td>
                                        </tr>
                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                            <tr>
                                                <td style="width: 300px">${verifypoint.description}</td>
                                                <c:if test="${verifypoint.documentType !=null}">
                                                    <c:if test="${verifypoint.documentExist =='YES'}">
                                                        <td style="width: 25px"><a href="#" onClick="invokeWindow('${verifypoint.description}', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td>
                                                            </c:if>
                                                            <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></c:if>  
                                                    </c:if>
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            <td colspan="10" ><hr /></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>Credit Score</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Score</td>
                                            <td>
                                                <b><font color="#000000">${cardApplicationList.creditScore}</font></b>
                                            </td>
                                            <td><a  href="#"  onclick="invokeBreakDown('${establishmentDetailsList.applicationId}')"><img src="<%=request.getContextPath()%>/resources/images/Break_down.jpg" width="100" height="40" /></a></td>
                                        </tr>
                                        <tr>
                                            <td colspan="10" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>System Recommended</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Products</td>
                                            <td>
                                                <c:set var="counter" value="${listsize-1}"/>
                                                <select  name="cardProducts"  class="inputfield-mandatory">
                                                    <option value="" style="width: 100px;">--SELECT--</option>
                                                    <c:forEach var="card" items="${cardProductList}">
                                                        <c:if test="${counter != 0}">    
                                                            <option value="${card.cardProductCode}">${card.cardproductDescription}</option>
                                                        </c:if> 
                                                        <c:if test="${counter == 0}">    
                                                            <option value="${card.cardProductCode}" selected>${card.cardproductDescription}</option>
                                                        </c:if>   
                                                        <c:set var="counter" value="${counter - 1}"/>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${currencyAlphaCode} ${creditLimitFomatted}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td clospan="6"><font style="color: #999"><br/>Applicant Requested</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Type</td>
                                            <td>
                                                <b><font color="#000000">${establishmentDetailsList.cardTypeDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Product</td>
                                            <td>
                                                <b><font color="#000000">${establishmentDetailsList.cardProductDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${currencyAlphaCode} <fmt:formatNumber type="number" value="${establishmentDetailsList.creditLimit}"/></font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br/>Credit Officer Recommendation</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Product</td>
                                            <td>
                                                <select  name="crdtOfficerRecProduct" id="crdtOfficerRecProduct" class="inputfield-mandatory">
                                                    <option value="" style="width: 100px;">--SELECT--</option>
                                                    <c:forEach var="products" items="${cardProducts}">
                                                        <c:if test="${cproduct != products.productCode}">
                                                            <option value="${products.productCode}">${products.description}</option>
                                                        </c:if>
                                                        <c:if test="${cproduct == products.productCode}">
                                                            <option value="${products.productCode}" selected="true">${products.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit (${currencyAlphaCode})</td>
                                            <td><input type="text" name="crdtOfficerRecCreditLimit" id="crdtOfficerRecCreditLimit" value="${climit}"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Choose the letter To Upload</td>
                                            <td><input id="control" name="recommendationLetter" type="file"/></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>
                                                <table>
                                                    <td><input type="submit" value="Submit" name="next" style="width: 100px" onclick="uploadLetters('${establishmentDetailsList.applicationId}')"> </ input></td>
                                                    <td><input type="button" value="Reset" name="next" style="width: 100px" onclick="resetLetterField()"> </ input></td>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
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
