<%-- 
    Document   : corporatedocumentviewhome
    Created on : Jun 27, 2016, 2:45:23 PM
    Author     : prageeth_s
--%>

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



            function invokeWindow(identityType, applicationId, verificationCategory, documentType, fileName) {

                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId=" + applicationId + "&verificationCategory=" + verificationCategory + "&documentType=" + documentType + "&fileName=" + fileName, identityType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");

            }


            function invokeCancel() {

                window.location = "${pageContext.request.contextPath}/camm/documentverification/corporatedocumentviewhome.jsp";

            }
            function invokeVerify()
            {

                document.applicantverifyform.action = "${pageContext.request.contextPath}/CorporateVerifyApplicantDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeReject()
            {

                document.applicantverifyform.action = "${pageContext.request.contextPath}/CorporateRejectApplicationDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeOnhold() {

                document.applicantverifyform.action = "${pageContext.request.contextPath}/CorporateOnholdApplicationDetailsServlet";
                document.applicantverifyform.submit();
            }
            
            function invokeCheckIn(applicationId,type)
            {
                answer = confirm("Are you sure you want to check in this appliaction?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId="+applicationId+"&type="+type;
                }
                else
                {
                    window.location = "${pageContext.request.contextPath}/LoadCorporateVerifyFormServlet?applicationid="+applicationId;
                }
               
            }

        </script>



        <title>EPIC CMS DOCUMENT VERIFICATION</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

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

                                <table class="tit"> <tr> <td   class="center"> <B> DOCUMENT VERIFICATION </B> </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <%-- -------------------------add form start -------------------------------- --%>



                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Applicant Verification</a></li>
                                        <li><a href="#tabs-2">Document Verification</a></li>
                                        <li><a href="#tabs-3">Application Summary</a></li>
                                    </ul>



                                    <div id="tabs-1" >

                                        <form method="POST" action="${pageContext.request.contextPath}/CorporateUploadCribFileServlet" name="applicantverifyform" enctype="multipart/form-data">



                                            <table>
                                                <tr>
                                                    <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                    <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                </tr>
                                            </table>


                                            <table>
                                                <tr>
                                                    <td style="width: 160px">Application ID </td>
                                                    <td><font>:&nbsp;</font></td>
                                                    <td style="width: 150px">${sessionScope.SessionObject.verifyingAppId}</td>
                                                    <td><input type="hidden" name="appliactionid" value="${sessionScope.SessionObject.verifyingAppId}"/></td>

                                                </tr>

                                                <tr> <td style="height:10px;"></td></tr>
                                            </table>
                                            <table class=""> <tr> <td  style="width: 160px" class="center"> <B> Personal Details</B>  </td> <td style="width: 300px"></td><td  class="center"> <B>  </B> </td></tr><tr> <td>&nbsp;</td> </tr></table>


                                            <table>

                                                <tbody>


                                                    <tr>
                                                        <td style="width: 160px">Full Name </td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.fullName != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyCustomerBean.fullName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.fullName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>

                                                            <td></td>



                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td style="width: 160px">Identification Type</td>

                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationType != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.identificationType}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationType == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>

                                                            <td> </td>

                                                        
                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>                                                    
                                                        <tr>
                                                            <td style="width: 160px">Identification Number</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.identificationNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>
                                                            <td> </td>

                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                        <td style="width: 160px">Date of Birth</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.birthday != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.birthday}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.birthday == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>
                                                            <td></td>

                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>

                                                        <tr><td style="height: 5px"></td></tr>                                                   
                                                        <tr>
                                                            <td>Address</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.applicantAddress != null}"> <td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.applicantAddress}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.applicantAddress == null}"> <td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                            <td></td>
                                                        </tr>

                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td>Home Telephone No</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.homeTelNumber != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.homeTelNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.homeTelNumber == null}"><td><font>:&nbsp;</font></td><td><font>&nbsp;-</font> </td></c:if>

                                                            <td style="width: 150px"></td>
                                                            <td></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td>Mobile No</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.mobileNumber != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.mobileNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.mobileNumber == null}"><td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                            <td></td>
                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        
                                                        <tr><td style="height: 20px"></td></tr>

                                                    </tbody>
                                                </table>


                                            <table>
                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Next" name="next" style="width: 100px" class="nexttab">
                                                        <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invokeCancel()">

                                                    </td>
                                                    <td> </td>
                                                </tr>

                                            </table>





                                    </div>

                                    <div id="tabs-2">



                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg2}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg2}</font></b> </td>
                                            </tr>
                                        </table>

                                        <table class=""> <tr> <td  class="center"> <B> DOCUMENT VERIFICATION </B> </td> <td style="width: 350px; height: 50px"></td></tr></table>          


                                        <table>


                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='DOCIDENTIFICATION'}">
                                                        <c:if test="${checkedApplicantBean.docIdentification =='YES' || previousCheckedBean.docIdentification =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkidentificationcopy" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docIdentification !='YES' && previousCheckedBean.docIdentification !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkidentificationcopy" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                        <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                        <c:if test="${verifypoint.documentType !=null}">
                                                            <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Confirmation Letter', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                    return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                            <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></c:if>                                                                
                                                        </c:if> <td></td>
                                                    </c:if>    
                                                </c:forEach>

                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='BOARDRESOLUTION'}">
                                                        <c:if test="${checkedApplicantBean.boardResolutionForm =='YES' || previousCheckedBean.boardResolutionForm =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkboardResolutionForm" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.boardResolutionForm !='YES' && previousCheckedBean.boardResolutionForm !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkboardResolutionForm" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                        <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                        <c:if test="${verifypoint.documentType !=null}">
                                                            <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Confirmation Letter', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                    return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                            <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                        </c:if> <td></td>
                                                    </c:if>    
                                                </c:forEach>
                                            </tr> 
                                            
                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='SURETYAGREEMENTFORM'}">
                                                        <c:if test="${checkedApplicantBean.suretyAgreementForm =='YES' || previousCheckedBean.suretyAgreementForm =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksuretyAgreementForm" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.suretyAgreementForm !='YES' && previousCheckedBean.suretyAgreementForm !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksuretyAgreementForm" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                        <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                        <c:if test="${verifypoint.documentType !=null}">
                                                            <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Confirmation Letter', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                    return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                            <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                        </c:if> <td></td>
                                                    </c:if>    
                                                </c:forEach>
                                            </tr>
                                            
                                            
                                        </table>
                                        <table>

                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td><input type="button" value="Next" name="next" style="width: 100px" class="nexttab">
                                                    <input type="button" value="Previous" name="previous" style="width: 100px" class="previoustab">
                                                    <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invokeCancel()">

                                                </td>

                                                <td></td>
                                            </tr>



                                        </table>




                                    </div>

                                    <div id="tabs-3">


                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg4}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg4}</font></b> </td>
                                            </tr>
                                        </table>


                                        <table>


                                            <tr> <td style="height:20px;"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td><input type="button" value="Verify" name="verify" style="width: 100px" onclick="invokeVerify()">
                                                    <input type="button" value="Reject" name="reject" style="width: 100px" onclick="invokeReject()">
                                                    <input type="button" value="Onhold" name="onhold" style="width: 100px" onclick="invokeOnhold()">
                                                    <input type="button" value="Previous" name="previous" style="width: 100px" class="previoustab">
                                                    <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invokeCancel()">
                                                    <input type="button" name="checkIn" style="width: 100px" value="Re Check" onclick="invokeCheckIn('${sessionScope.SessionObject.verifyingAppId}','1')"
                                                </td>

                                                <td></td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>

                                        </table>


                                        <table class=""> <tr> <td   class="center"> <B> Previous Application History </B></td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                        <table  border="1"  class="display" id="tableview">
                                            <thead>
                                                <tr>
                                                    <th>Application ID </th>
                                                    <!--                                                    <th>Identification Type</th>-->
                                                    <th>Identification Number</th>
                                                    <th>Application Status</th>
                                                    <th>Mobile No</th>
                                                    <th>Residence Phone</th>
                                                    <th>Residence Address</th>




                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="previousapp" items="${sessionScope.SessionObject.previousDetailsBeanList}">
                                                    <tr>
                                                        <td >${previousapp.applicationId}</td>
<!--                                                        <td >${previousapp.identificationType}</td>-->
                                                        <td >${previousapp.identificationNumber}</td>
                                                        <td >${previousapp.applicationStatusDes}</td>
                                                        <td >${previousapp.mobileNumber}</td>
                                                        <td >${previousapp.homeTelNumber}</td>
                                                        <td >${previousapp.applicantAddress}</td>


                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                        </form>

                                    </div>         




                                </div>


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
