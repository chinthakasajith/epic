<%-- 
    Document   : applicationsearch
    Created on : March 8, 2012, 2:16:08 PM
    Author     : mahesh_m
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/windowcss/dhtmlwindow.css" type="text/css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/dhtmlwindow.js"></script>

        <script language="javascript">


            function invokeSearch()
            {

                document.searchappassignform.action = "${pageContext.request.contextPath}/SearchApplicationServlet";
                document.searchappassignform.submit();

            }

            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/SearchApplicationServlet";

            }

            function uploadLetters(applicationId)
            {

                document.recommendLetter.action = "${pageContext.request.contextPath}/UploadRcomondationLetterServlet?applicationId=" + applicationId + "&crdtOfficerRecCreditLimit=" + $("#crdtOfficerRecCreditLimit").val() + "&crdtOfficerRecProduct=" + $("#crdtOfficerRecProduct").val();
                document.recommendLetter.submit();

            }

            function resetLetterField()
            {

                //               document.getElementById("letter").value='';

                //            $('#letter').attr("value","");


                $("#control").replaceWith("<input type='file' id='control' />");

            }


            function invokeWindow(viewType, documentType, applicationId) {


                var googlewin = dhtmlwindow.open("identitybox", "iframe", "${pageContext.request.contextPath}/camm/applicationconfirmation/imageviewhome.jsp?documentType=" + documentType + "&applicationId=" + applicationId, viewType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=1", "recal")
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




        <title>EPIC CMS UPDATE CARD APPLICATION ASSIGN</title>

    </head>
    <body>



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

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
                                        <td><b><font color="#000000"> Main</font></b> </td>
                                    </tr>

                                </table>     

                                <form  ENCTYPE="multipart/form-data"  METHOD=POST name="recommendLetter" id="recommendLetter">                  

                                    <table cellpadding="0" cellspacing="10"  >

                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Personal Information</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">
                                                Identification Type
                                            </td>

                                            <td>
                                                <b><font color="#000000"> ${cardApplicationList.identificationType}</font></b>
                                            </td>



                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">
                                                Identification Number
                                            </td>
                                            <td>
                                                <b><font color="#000000"> ${cardApplicationList.identificationNumber}</font></b>
                                            </td>

                                        </tr>


                                        <tr>
                                            <td style="width: 200px;">Title</td>

                                            <td>
                                                <b><font color="#000000"> ${personalDetail.title}</font></b>
                                            </td>




                                            <td style="width: 200px;"> Religion</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.religion}</font></b>
                                            </td>
                                            <td style="width: 100px;"></td> 
                                        </tr>

                                        <tr>

                                            <td style="width: 200px;">Full Name</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.fullName}</font></b>
                                            </td>


                                            <td style="width: 200px;"> Gender</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.gender}</font></b>
                                            </td>
                                            <td style="width: 100px;"></td> 
                                        </tr>
<!--                                        <tr>

                                            <td style="width: 200px;">Last Name</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.lastName}</font></b>
                                            </td>


                                            <td style="width: 200px;"> Middle Names</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.middleName}</font></b>
                                            </td>
                                            <td style="width: 100px;"></td> 
                                        </tr>-->
                                        <tr>
                                            <td style="width: 200px;">Name on the Card</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.nameOncard}</font></b>
                                            </td>



                                            <td style="width: 200px;"> Marital Status</td>    

                                            <td>
                                                <b><font color="#000000"> ${personalDetail.maritalStatus}</font></b>
                                            </td>
                                            <td style="width: 100px;"></td> 

                                        </tr>
                                        <tr>

                                            <td style="width: 200px;">Date Of Birth</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.birthday}</font></b>
                                            </td>


                                            <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                   
                                            <td style="width: 200px;"> Nationality</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.nationality}</font></b>
                                            </td>
                                            <td style="width: 100px;"></td> 
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Place of Birth</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.placeOfbirth}</font></b>
                                            </td>


                                            <td style="width: 200px;"> Blood Group</td>    
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.bloodgroup}</font></b>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Verify Documents</font></td>
                                        </tr>


                                        <tr>
                                            <td>Copy Of NIC/Passport/DLICEN</td>
                                            <td><c:if test="${Id == 'YES'}"><a href="#" onClick="invokeWindow('Scanned Document', '${"nic"}', '${cardApplicationList.applicationId}');
                                                    return false"><img  style="width: 25px ; height: 20px" src="${pageContext.request.contextPath}/resources/images/windowimages/magnify.gif"/></a>
                                                    </c:if>
                                                    <c:if test="${Id != 'YES'}"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/>
                                                </c:if>
                                            </td>
                                            <td></td>
                                        </tr>  
                                        <tr>
                                            <td>Salary Slip</td>
                                            <td><c:if test="${salery == 'YES'}"><a href="#" onClick="invokeWindow('Scanned Document', '${"Salary Slip"}', '${cardApplicationList.applicationId}');
                                                    return false"><img  style="width: 25px ; height: 20px" src="${pageContext.request.contextPath}/resources/images/windowimages/magnify.gif"/></a>
                                                    </c:if>
                                                    <c:if test="${salery != 'YES'}"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/>
                                                </c:if>
                                            </td>
                                            <td></td>
                                        </tr>  
                                        <tr>
                                            <td>Confirmation Of Employee</td>
                                            <td><c:if test="${confirmation == 'YES'}"><a href="#" onClick="invokeWindow('Scanned Document', '${"Confimation Letter"}', '${cardApplicationList.applicationId}');
                                                    return false"><img  style="width: 25px ; height: 20px" src="${pageContext.request.contextPath}/resources/images/windowimages/magnify.gif"/></a>
                                                    </c:if>
                                                    <c:if test="${confirmation != 'YES'}"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/>
                                                </c:if>
                                            </td>
                                            <td></td>
                                        </tr>  
                                        <tr>
                                            <td>Utility Bill</td>
                                            <td><c:if test="${bill == 'YES'}"><a href="#" onClick="invokeWindow('Scanned Document', '${"Copy of Utility Bill"}', '${cardApplicationList.applicationId}');
                                                    return false"><img  style="width: 25px ; height: 20px" src="${pageContext.request.contextPath}/resources/images/windowimages/magnify.gif"/></a>
                                                    </c:if>
                                                    <c:if test="${bill != 'YES'}"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/>
                                                </c:if>
                                            </td>
                                            <td></td>
                                        </tr>  
                                        <tr>
                                            <td>Marriage Certificate</td>
                                            <td><c:if test="${marriage == 'YES'}"><a href="#" onClick="invokeWindow('Scanned Document', '${"Copy Of Marriage Certificate"}', '${cardApplicationList.applicationId}');
                                                    return false"><img  style="width: 25px ; height: 20px" src="${pageContext.request.contextPath}/resources/images/windowimages/magnify.gif"/></a>
                                                    </c:if>
                                                    <c:if test="${marriage != 'YES'}"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/>
                                                </c:if>
                                            </td>
                                            <td></td>
                                        </tr>  
                                        <tr>
                                            <td>Birth Certificate</td>
                                            <td><c:if test="${birth == 'YES'}"><a href="#" onClick="invokeWindow('Scanned Document', '${"Copy Of Birth Certificate"}', '${cardApplicationList.applicationId}');
                                                    return false"><img  style="width: 25px ; height: 20px" src="${pageContext.request.contextPath}/resources/images/windowimages/magnify.gif"/></a>
                                                    </c:if>
                                                    <c:if test="${birth != 'YES'}"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/>
                                                </c:if>
                                            </td>
                                            <td></td>
                                        </tr>

                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>

                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />Credit Score</font></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Score</td>
                                            <td>
                                                <b><font color="#000000"> ${cardApplicationList.creditScore}</font></b>
                                            </td>
                                            <td><a  href="#"  onclick="invokeBreakDown('${personalDetail.applicationId}')"><img src="<%=request.getContextPath()%>/resources/images/Break_down.jpg" width="100" height="40" /></a></td>
                                        </tr>

                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br />System Recommended</font></td>
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
                                            <td colspan="6" ><font style="color: #999"> <br />Applicant Requested</font></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Card Type</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.cardTypeDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Card Product</td>
                                            <td>
                                                <b><font color="#000000"> ${personalDetail.cardProductDes}</font></b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;">Credit Limit</td>
                                            <td>
                                                <b><font color="#000000">${currencyAlphaCode} ${requestedCreditLimit}</font></b>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="6" ><hr /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br/>Credit Officer Recommendation</font></td>
                                        </tr>


                                        <!--                                    <form  ENCTYPE="multipart/form-data"  METHOD=POST name="recommendLetter">-->

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
                                            <td style="width: 200px;">Credit Limit (LKR)</td>
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
                                                    <td><input type="submit" value="Submit" name="next" style="width: 100px" onclick="uploadLetters('${personalDetail.applicationId}')"> </ input></td>
                                                    <td><input type="reset" value="Reset Form"> </ input></td>
                                                </table>
                                            </td>
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
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

