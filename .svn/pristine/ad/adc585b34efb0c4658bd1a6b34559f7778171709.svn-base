<%-- 
    Document   : supplementaryverifyhome
    Created on : Apr 17, 2012, 2:06:37 PM
    Author     : chanuka
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
            

            function invokeWindow(identityType,applicationId,verificationCategory,documentType,fileName){           
                
                var googlewin = dhtmlwindow.open("identitybox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId="+applicationId+"&verificationCategory="+verificationCategory+"&documentType="+documentType+"&fileName="+fileName, identityType , "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");
                
            }
            
            
            
            function invokeCancel(){
             
                window.location = "${pageContext.request.contextPath}/camm/documentverification/supplementaryverifyhome.jsp";
             
            }
            function invokeVerify()
            {
                
                document.applicantverifyform.action="${pageContext.request.contextPath}/VerifySupplementaryDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeReject()
            {
                
                document.applicantverifyform.action="${pageContext.request.contextPath}/RejectSupplementaryDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeOnhold(){
                
                document.applicantverifyform.action="${pageContext.request.contextPath}/OnholdSupplementaryDetailsServlet";
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
                    window.location = "${pageContext.request.contextPath}/LoadSupplementaryUpdateVerifyFormServlet?applicationid="+applicationId;
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

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

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

                                        <form method="POST" action="" name="applicantverifyform">



                                            <table>
                                                <tr>
                                                    <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                    <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                </tr>
                                            </table>


                                            <table>
                                                <tr>
                                                    <td style="width: 160px">Application Id </td>
                                                    <td><font>:&nbsp;</font></td>
                                                    <td style="width: 150px">${sessionScope.SessionObject.verifyingAppId}</td>
                                                    <td><input type="hidden" name="appliactionid" value="${sessionScope.SessionObject.verifyingAppId}"/></td>

                                                </tr>

                                                <tr> <td style="height:10px;"></td></tr>
                                            </table>

                                            <table class="tit"> <tr> <td  style="width: 160px"class="center"> <B> Personal Details</B>  </td> <td style="width: 300px"></td><td  class="center"> <B> Supplementary Details </B> </td></tr><tr> <td>&nbsp;</td> </tr></table>

                                            <table>

                                                <tbody>


                                                    <tr>
                                                        <td style="width: 160px">Full Name </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.fullName != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.fullName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.fullName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td>Full Name </td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.fullName != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.fullName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.fullName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>
                                                    </tr>

                                                    <tr><td style="height: 5px"></td></tr>

                                                    <tr>
                                                        <td style="width: 160px">Identification Type</td>

                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.identificationType != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.identificationType}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.identificationType == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                        <td style="width: 150px"></td>

                                                        <td style="width: 160px">Identification Type</td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.identificationType != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.identificationType}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.identificationType == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>                                                    
                                                    <tr>
                                                        <td style="width: 160px">Identification Number</td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.identificationNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.identificationNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.identificationNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>



                                                        <td style="width: 150px"></td>

                                                        <td style="width: 160px">Identification Number</td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.identificationNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.identificationNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.identificationNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>

                                                    <tr>
                                                        <td style="width: 160px">Date of birth </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.birthday != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.birthday}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.birthday == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                        <td style="width: 150px"></td>

                                                        <td style="width: 160px">Date of birth </td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.birthday != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.birthday}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.birthday == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Marital Status </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.maritalStatus != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.maritalStatus}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.maritalStatus == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                        <td style="width: 150px"></td>
                                                        <td style="width: 160px">Address </td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.applicantAddress != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.applicantAddress}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.applicantAddress == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>                                                   
                                                    <tr>
                                                        <td style="width: 160px">Address </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.applicantAddress != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.applicantAddress}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.applicantAddress == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td style="width: 160px">Home Telephone No </td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.homeTelNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.homeTelNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.homeTelNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                    </tr>

                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Home Telephone No </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.homeTelNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.homeTelNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.homeTelNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                        <td style="width: 160px">Mobile No </td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.mobileNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.mobileNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.mobileNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Mobile No </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.mobileNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.mobileNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.mobileNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td style="width: 160px">Relationship </td>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.relationship != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.supplementaryDetailsBean.relationship}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.supplementaryDetailsBean.relationship == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Ownership </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.ownership != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.ownership}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.ownership == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                        <td style="width: 150px"></td>

                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Designation </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.designation != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.designation}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.designation == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                    </tr>

                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Company </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.company != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.company}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.company == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>


                                                        <td style="width: 150px"></td>

                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td style="width: 160px">Length of employment </td>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.serviceLength != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.mainDetailsBean.serviceLength}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.mainDetailsBean.serviceLength == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                    </tr>
                                                    <tr><td style="height: 20px"></td></tr>

                                                </tbody>
                                            </table>



                                            <table class="tit"> <tr> <td  class="center"> <B> APPLICANT VERIFICATION </B> </td> <td style="height: 50px"></td></tr></table>         




                                            <table>

                                                <tbody>

                                                    <tr>

                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='PERSONALDETAILSVERIFICATION'}"> 
                                                        <c:if test="${checkedApplicantBean.mainVerification =='YES' || previousCheckedBean.mainVerification =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkmain" value="ON" checked="true" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.mainVerification !='YES' && previousCheckedBean.mainVerification !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkmain" value="ON" style="width: 25px"/></td></c:if>
                                                            <td style="width: 300px">                                                                    
                                                            <c:if test="${verifypoint.isMandatory ==true}">
                                                                <font style="color: red;">*</font>
                                                            </c:if>
                                                            ${verifypoint.description}
                                                            </td>
                                                            <td></td> 
                                                            <c:if test="${verifypoint.documentType !=null}">
                                                                <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                            </c:if>
                                                    </c:if>  
                                                </c:forEach>
                                                        <td style="width: 110px"></td>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='SUPPLEMENTARYDETAILSVERIFICATION'}"> 
                                                        <c:if test="${checkedApplicantBean.supplentaryVerification =='YES' || previousCheckedBean.supplentaryVerification =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksupplementary" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.supplentaryVerification !='YES' && previousCheckedBean.supplentaryVerification !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksupplementary" value="ON" style="width: 25px"/></td></c:if>
                                                            <td style="width: 300px">                                                                    
                                                            <c:if test="${verifypoint.isMandatory ==true}">
                                                                <font style="color: red;">*</font>
                                                            </c:if>
                                                            ${verifypoint.description}
                                                            </td>
                                                            <td></td> 
                                                            <c:if test="${verifypoint.documentType !=null}">
                                                                <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                            </c:if>
                                                    </c:if>  
                                                </c:forEach>
                                                    </tr>


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

                                        <table class="tit"> <tr> <td  class="center"> <B> DOCUMENT VERIFICATION </B> </td> <td style="width: 350px; height: 50px"></td></tr></table>          


                                        <table>

                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='DOCIDENTIFICATION'}"> 
                                                        <c:if test="${checkedApplicantBean.docIdentification =='YES' || previousCheckedBean.docIdentification =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkidentificationcopy" value="ON" checked="true" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docIdentification !='YES' && previousCheckedBean.docIdentification !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkidentificationcopy" value="ON" style="width: 25px"/></td></c:if>
                                                            <td style="width: 300px">                                                                    
                                                            <c:if test="${verifypoint.isMandatory ==true}">
                                                                <font style="color: red;">*</font>
                                                            </c:if>
                                                            ${verifypoint.description}
                                                            </td>
                                                            <td></td> 
                                                            <c:if test="${verifypoint.documentType !=null}">
                                                                <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                            </c:if>
                                                    </c:if>  
                                                </c:forEach>

                                                <td></td>

                                            </tr> 
                                            
                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='DOCMARRIAGECERTIFICATE'}"> 
                                                        <c:if test="${checkedApplicantBean.docMarriageCertificate =='YES' || previousCheckedBean.docMarriageCertificate =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkmarriagecopy" value="ON" checked="true" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docMarriageCertificate !='YES' && previousCheckedBean.docMarriageCertificate !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkmarriagecopy" value="ON" style="width: 25px"/></td></c:if>
                                                            <td style="width: 300px">                                                                    
                                                            <c:if test="${verifypoint.isMandatory ==true}">
                                                                <font style="color: red;">*</font>
                                                            </c:if>
                                                            ${verifypoint.description}
                                                            </td>
                                                            <td></td> 
                                                            <c:if test="${verifypoint.documentType !=null}">
                                                                <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                            </c:if>
                                                    </c:if>  
                                                </c:forEach>

                                                <td></td>

                                            </tr> 

                                            <tr><td style="height: 10px"></td></tr>

                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${verifypoint.fieldName =='DOCBIRTHCERTIFICATE'}"> 
                                                        <c:if test="${checkedApplicantBean.docBirthCertificate =='YES' || previousCheckedBean.docBirthCertificate =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkbirthcertifycopy" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docBirthCertificate !='YES' && previousCheckedBean.docBirthCertificate !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkbirthcertifycopy" value="ON" style="width: 25px"/></td></c:if>
                                                            <td style="width: 300px">                                                                    
                                                            <c:if test="${verifypoint.isMandatory ==true}">
                                                                <font style="color: red;">*</font>
                                                            </c:if>
                                                            ${verifypoint.description}
                                                            </td>
                                                            <td></td> 
                                                            <c:if test="${verifypoint.documentType !=null}">
                                                                <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                            </c:if>
                                                    </c:if>  
                                                </c:forEach>

                                                <td></td>
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
                                                <td><b><font color="#FF0000"> ${errorMsg3}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg3}</font></b> </td>
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


                                        <table class="tit"> <tr> <td   class="center">  Previous Application History </td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                        <table  border="1"  class="display" id="tableview">
                                            <thead>
                                                <tr>
                                                    <th>Application ID </th>
                                                    <%--<th>Identification Type</th>--%>
                                                    <th>Identification Number</th>
                                                    <th>Application Status</th>
                                                    <th>Mobile No</th>
                                                    <th>Residence Phone</th>
                                                    <th>Residence Address</th>
                                                    <th>Company</th>
                                                    <th>Post</th>



                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="previousapp" items="${sessionScope.SessionObject.previousDetailsBeanList}">
                                                    <tr>
                                                        <td >${previousapp.applicationId}</td>
                                                        <%--<td >${previousapp.identificationType}</td>--%>
                                                        <td >${previousapp.identificationNumber}</td>
                                                        <td >${previousapp.applicationStatusDes}</td>
                                                        <td >${previousapp.mobileNumber}</td>
                                                        <td >${previousapp.homeTelNumber}</td>
                                                        <td >${previousapp.applicantAddress}</td>
                                                        <td >${previousapp.company}</td>
                                                        <td >${previousapp.designation}</td>

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



