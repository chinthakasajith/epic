<%-- 
    Document   : documentviewhome
    Created on : Feb 29, 2012, 11:13:42 AM
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
 
            $( document ).ready(function() {
                
                adjustApplicationVerificationPoints();
            });

            function invokeWindow(identityType, applicationId, verificationCategory, documentType, fileName) {

                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId=" + applicationId + "&verificationCategory=" + verificationCategory + "&documentType=" + documentType + "&fileName=" + fileName, identityType, "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");

            }


            function invokeCancel() {

                window.location = "${pageContext.request.contextPath}/camm/documentverification/documentverifyhome.jsp";

            }
            function invokeVerify()
            {

                document.applicantverifyform.action = "${pageContext.request.contextPath}/VerifyApplicantDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeReject()
            {

                document.applicantverifyform.action = "${pageContext.request.contextPath}/RejectApplicationDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeOnhold() {

                document.applicantverifyform.action = "${pageContext.request.contextPath}/OnholdApplicationDetailsServlet";
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
                    window.location = "${pageContext.request.contextPath}/LoadUpdateDocumentVerifyFormServlet?applicationid="+applicationId;
                }
               
            }
            
            
            function adjustApplicationVerificationPoints(){
                
                var hasemployerVerificationPoints=false; 
                var hasapplicantVerificationPoints=false; 
                
                
                
                 $("#_employerVerification_table tr:gt(0)").each(function () {
                   var tdCount=0;
                   $(this).find('td').each(function (key, val) {
                        tdCount++;                      
                        if(tdCount>2){
                            hasemployerVerificationPoints=true;
                             return false; 
                        }
                   });
                   
                    if(tdCount>2){
                             return false; 
                    }
                });
                
                
                 $("#_applicantVerification_table tr:gt(0)").each(function () {
                    var tdCount=0;
                   $(this).find('td').each(function (key, val) {
                        tdCount++;                      
                        if(tdCount>2){
                           hasapplicantVerificationPoints=true;
                             return false; 
                        }
                   });
                   
                    if(tdCount>2){
                             return false; 
                    }
                });
                
                
                
                
                if(!hasemployerVerificationPoints){
                    $('#_employerVerification_table').hide();
                    $('#_employerVerification_heading').hide();
                    $('#_residenceVerification_heading').hide();
                    
                }
                
                
                if(!hasapplicantVerificationPoints){
                    $('#_applicantVerification_table').hide();
                    
                    $('#_applicantVerification_heading').hide();
                    $('#_referenceVerification_heading').hide();
                    
                }
                
                if(!hasemployerVerificationPoints && !hasapplicantVerificationPoints){
                     $('#_applicationVerification_heading').hide();
                    
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
                                        <li><a href="#tabs-3">CRIB Report</a></li>
                                        <li><a href="#tabs-4">Application Summary</a></li>
                                    </ul>



                                    <div id="tabs-1" >

                                        <form method="POST" action="${pageContext.request.contextPath}/UploadCribFileServlet" name="applicantverifyform" enctype="multipart/form-data">


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
                                                    <td>
                                                        <input type="hidden" name="appliactionid" value="${sessionScope.SessionObject.verifyingAppId}"/>

                                                    </td>

                                                </tr>

                                                <tr> <td style="height:10px;"></td></tr>
                                            </table>

                                            <table class=""> <tr> <td  style="width: 160px" class="center"> <B> Personal Details</B>  </td> <td style="width: 300px"></td><td  class="center"> <B> Reference Details </B> </td></tr><tr> <td>&nbsp;</td> </tr></table>

                                            <table>

                                                <tbody>


                                                    <tr>
                                                        <td style="width: 160px">Full Name </td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.fullName != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyCustomerBean.fullName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.fullName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>

                                                            <td>Full Name</td>

                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeFullName != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.refereeFullName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeFullName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>


                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td style="width: 160px">Identification Type</td>

                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationType != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.identificationType}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationType == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>

                                                            <td>Relationship </td>

                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.relationship != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.relationship}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.relationship == null}"><td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>                                                    
                                                        <tr>
                                                            <td style="width: 160px">Identification Number</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationNumber != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.identificationNumber}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.identificationNumber == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>
                                                            <td>Address </td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeAddress != null}"> <td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.refereeAddress}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeAddress == null}"> <td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td style="width: 160px">Date of Birth</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.birthday != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.birthday}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.birthday == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>
                                                            <td>Home Telephone No </td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeHomeTel != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.refereeHomeTel}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeHomeTel == null}"><td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td style="width: 160px">Marital Status</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.maritalStatus != null}"><td><font>:&nbsp;</font></td><td style="width: 150px">${sessionScope.SessionObject.verifyCustomerBean.maritalStatus}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.maritalStatus == null}"><td><font>:&nbsp;</font></td><td style="width: 150px"><font>&nbsp;-</font></td></c:if>

                                                            <td style="width: 150px"></td>
                                                            <td>Mobile No</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeMobile != null}">  <td><font>:&nbsp;</font></td> <td>${sessionScope.SessionObject.verifyCustomerBean.refereeMobile}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.refereeMobile == null}">  <td><font>:&nbsp;</font></td> <td><font>&nbsp;-</font></td></c:if>


                                                        </tr>
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
                                                        <tr>
                                                            <td>Ownership</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.ownership != null}"> <td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.ownership}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.ownership == null}"> <td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                            <td></td>
                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td>Designation</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.designation != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.designation}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.designation == null}"><td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                            <td></td>
                                                        </tr>

                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td>Company</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.company != null}"> <td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.company}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.company == null}"> <td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                            <td></td>
                                                        </tr>
                                                        <tr><td style="height: 5px"></td></tr>
                                                        <tr>
                                                            <td style="width: 160px">Length of Employment</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.serviceLength != null}"> <td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyCustomerBean.serviceLength}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyCustomerBean.serviceLength == null}"> <td><font>:&nbsp;</font></td><td><font>&nbsp;-</font></td></c:if>

                                                            <td></td>
                                                        </tr>
                                                        <tr><td style="height: 20px"></td></tr>

                                                    </tbody>
                                                </table>



                                                <table class="" id="_applicationVerification_heading"> <tr> <td  class="center"> <B> APPLICANT VERIFICATION </B> </td> <td style="height: 50px"></td></tr></table>         

                                                <table class=""> <tr> <td id="_employerVerification_heading" style="width: 160px" class="center"> <B> With Employer  </B> </td> <td   style="width: 300px"></td><td id="_residenceVerification_heading"  class="center"> <B> With Residence</B>  </td></tr><tr> <td>&nbsp;</td> </tr></table>         







                                                <table id="_employerVerification_table">
                                                        <input type="hidden" name="staffStatus" value="${sessionScope.SessionObject.verifyCustomerBean.staffStatus}"/>

                                                    <tbody>


                                                        <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'EMPLOYMENTDETAILS'}"> 
                                                                <c:if test="${checkedApplicantBean.empDetails =='YES' || previousCheckedBean.empDetails =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkverify" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.empDetails !='YES' && previousCheckedBean.empDetails !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkverify" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">                                                                    
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>


                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'ADDRESSFROMRESI'}"> 

                                                                <c:if test="${checkedApplicantBean.addressFromResidence =='YES' || previousCheckedBean.addressFromResidence =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantaddress" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.addressFromResidence !='YES' && previousCheckedBean.addressFromResidence !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantaddress" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'SALARYCONFIRMATION'}"> 

                                                                <c:if test="${checkedApplicantBean.salaryConfirm =='YES' || previousCheckedBean.salaryConfirm =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksalary" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.salaryConfirm !='YES' && previousCheckedBean.salaryConfirm !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksalary" value="ON" style="width: 25px"/></td></c:if>

                                                                    <td style="width: 300px"> 
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'MOBILENO'}"> 
                                                                <c:if test="${checkedApplicantBean.mobileNo =='YES' || previousCheckedBean.mobileNo =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantmobile" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.mobileNo !='YES' && previousCheckedBean.mobileNo !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantmobile" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>

                                                            </c:if>    
                                                        </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'NICNUMBERFROMEMP'}"> 
                                                                <c:if test="${checkedApplicantBean.empNic =='YES' || previousCheckedBean.empNic =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkempnic" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.empNic !='YES' && previousCheckedBean.empNic !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkempnic" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                   <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'OWNERSHIP'}"> 
                                                                <c:if test="${checkedApplicantBean.owenership =='YES' || previousCheckedBean.owenership =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkownership" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.owenership !='YES' && previousCheckedBean.owenership !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkownership" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td> <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if> </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'LENGTHOFSERVICE'}"> 
                                                                <c:if test="${checkedApplicantBean.serviceLength =='YES' || previousCheckedBean.serviceLength =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkservicelength" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.serviceLength !='YES' && previousCheckedBean.serviceLength !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkservicelength" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td><c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if> </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'PLACEOFWORK'}"> 
                                                                <c:if test="${checkedApplicantBean.placeOfWork =='YES' || previousCheckedBean.placeOfWork =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkworkplace" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.placeOfWork !='YES' && previousCheckedBean.placeOfWork !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkworkplace" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td><c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if> </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'EMPLOYMENTSTATUS'}"> 
                                                                <c:if test="${checkedApplicantBean.empStatus =='YES' || previousCheckedBean.empStatus =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkemploymentstatus" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.empStatus !='YES' && previousCheckedBean.empStatus !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkemploymentstatus" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>  </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DETAILSOFREFERENCE'}"> 
                                                                <c:if test="${checkedApplicantBean.detailsOfReference =='YES' || previousCheckedBean.detailsOfReference =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkrefereedetalis" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.detailsOfReference !='YES' && previousCheckedBean.detailsOfReference !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkrefereedetalis" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>  </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <td colspan="3"></td>
                                                        
                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'VEHICLE'}">
                                                                <c:if test="${checkedApplicantBean.vehicle =='YES' || previousCheckedBean.vehicle =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkvehicletype" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.vehicle !='YES' && previousCheckedBean.vehicle !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkvehicletype" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>                                                   
                                                    <tr>
                                                        <td colspan="3"></td>
                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'PERSONALDETAILSATRESIDENT'}">
                                                                <c:if test="${checkedApplicantBean.applicantPersonal =='YES' || previousCheckedBean.applicantPersonal =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantpersonal" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.applicantPersonal !='YES' && previousCheckedBean.applicantPersonal !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantpersonal" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                   <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>
                                                    </tr>

                                                    <tr><td style="height: 20px"></td></tr>

                                                </tbody>
                                            </table>








                                            <table class=""> <tr> <td id="_applicantVerification_heading" style="width: 160px"  class="center"> <B> With Applicant  </B> </td> <td style="width: 300px"></td><td  id="_referenceVerification_heading" class="center">  <B>Reference Verification</B>  </td></tr><tr> <td>&nbsp;</td> </tr></table>         







                                            <table id="_applicantVerification_table">

                                                <tbody>


                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'FULLNAME'}">
                                                                <c:if test="${checkedApplicantBean.fullname =='YES' || previousCheckedBean.fullname =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantfullname" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.fullname !='YES' && previousCheckedBean.fullname !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantfullname" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>    </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'RELATIONSHIP'}">
                                                                <c:if test="${checkedApplicantBean.relatioship =='YES' || previousCheckedBean.relatioship =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkrelationtoapplicant" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.relatioship !='YES' && previousCheckedBean.relatioship !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkrelationtoapplicant" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>    </c:if>     
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">                                                           
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'NICNUMBER'}">
                                                               
                                                                <c:if test="${checkedApplicantBean.nic =='YES' || previousCheckedBean.nic =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantnic" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.nic !='YES' && previousCheckedBean.nic !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantnic" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>   </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'RESIDENCE'}">
                                                                <c:if test="${checkedApplicantBean.residence =='YES' || previousCheckedBean.residence =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantresidence" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.residence !='YES' && previousCheckedBean.residence !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantresidence" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>    </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'HOMETELEPHONENO'}">
                                                                <c:if test="${checkedApplicantBean.homeTelNo =='YES' || previousCheckedBean.homeTelNo =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicanthometel" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.homeTelNo !='YES' && previousCheckedBean.homeTelNo !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicanthometel" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>    </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'WORKPLACE'}">
                                                                <c:if test="${checkedApplicantBean.workPlace =='YES' || previousCheckedBean.workPlace =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkrefereeworkplace" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.workPlace !='YES' && previousCheckedBean.workPlace !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkrefereeworkplace" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>    </c:if>    
                                                            </c:forEach>
                                                    </tr>
                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'MAILINGADDRESS'}">
                                                                <c:if test="${checkedApplicantBean.mailingAddress =='YES' || previousCheckedBean.mailingAddress =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantmailing" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.mailingAddress !='YES' && previousCheckedBean.mailingAddress !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantmailing" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>   </c:if>    
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>


                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'PERSONALDETAILSATREFREE'}">
                                                                <c:if test="${checkedApplicantBean.personalDetailsReferee =='YES' || previousCheckedBean.personalDetailsReferee =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantpersonalbyreferee" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.personalDetailsReferee !='YES' && previousCheckedBean.personalDetailsReferee !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantpersonalbyreferee" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>   </c:if>    
                                                            </c:forEach>
                                                    </tr>

                                                    <tr><td style="height: 10px"></td></tr>
                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'REFERENCEDETAILS'}">
                                                                <c:if test="${checkedApplicantBean.detailsOfReference =='YES' || previousCheckedBean.detailsOfReference =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkreferencedetail" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.detailsOfReference !='YES' && previousCheckedBean.detailsOfReference !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkreferencedetail" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>   </c:if>    
                                                            </c:forEach>


                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                            <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'ADDRESSFROMREF'}">
                                                                <c:if test="${checkedApplicantBean.addressFromReferee =='YES' || previousCheckedBean.addressFromReferee =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantaddressbyreferee" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.addressFromReferee !='YES' && previousCheckedBean.addressFromReferee !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkapplicantaddressbyreferee" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                            return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>    </c:if>    
                                                        </c:forEach>
                                                    </tr>



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
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCIDENTIFICATION'}">
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
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCSALARYSLIP1'}">
                                                        <c:if test="${checkedApplicantBean.docSalarySlip1 =='YES' || previousCheckedBean.docSalarySlip1 =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksalarycopy1" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docSalarySlip1 !='YES' && previousCheckedBean.docSalarySlip1 !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksalarycopy1" value="ON" style="width: 25px"/></td></c:if>
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
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCSALARYSLIP2'}">
                                                        <c:if test="${checkedApplicantBean.docSalarySlip2 =='YES' || previousCheckedBean.docSalarySlip2 =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksalarycopy2" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docSalarySlip2 !='YES' && previousCheckedBean.docSalarySlip2 !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checksalarycopy2" value="ON" style="width: 25px"/></td></c:if>
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
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCBANKSTATEMENT'}">
                                                        <c:if test="${checkedApplicantBean.docBankStatement =='YES' || previousCheckedBean.docBankStatement =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkBankStatement" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docBankStatement !='YES' && previousCheckedBean.docBankStatement !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkBankStatement" value="ON" style="width: 25px"/></td></c:if>
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
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCSTAFFDECFORM'}">
                                                        <c:if test="${checkedApplicantBean.docStaffDecForm =='YES' || previousCheckedBean.docStaffDecForm =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkStaffDecForm" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docStaffDecForm !='YES' && previousCheckedBean.docStaffDecForm !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkStaffDecForm" value="ON" style="width: 25px"/></td></c:if>
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
                                            
                                            <tr><td style="height: 10px"></td></tr>
                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCCONFIRMATIONOFEMPLOYEE'}">
                                                        <c:if test="${checkedApplicantBean.docConfirmationLetter =='YES' || previousCheckedBean.docConfirmationLetter =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkconfirmationcopy" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docConfirmationLetter !='YES' && previousCheckedBean.docConfirmationLetter !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkconfirmationcopy" value="ON" style="width: 25px"/></td></c:if>
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

                                            <tr><td style="height: 10px"></td></tr>

                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCUTILITYBILL'}">
                                                        <c:if test="${checkedApplicantBean.docUtilityBill =='YES' || previousCheckedBean.docUtilityBill =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkutilitybillcopy" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docUtilityBill !='YES' && previousCheckedBean.docUtilityBill !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkutilitybillcopy" value="ON" style="width: 25px"/></td></c:if>
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

                                            <tr><td style="height: 10px"></td></tr>

                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCMARRIAGECERTIFICATE'}">
                                                        <c:if test="${checkedApplicantBean.docMarriageCertificate =='YES' || previousCheckedBean.docMarriageCertificate =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkmarriagecopy" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docMarriageCertificate !='YES' && previousCheckedBean.docMarriageCertificate !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkmarriagecopy" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                        <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                        <c:if test="${verifypoint.documentType !=null}">
                                                            <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Marriage Certificate', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
                                                                    return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                            <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                        </c:if> <td></td>
                                                    </c:if>    
                                                </c:forEach>
                                            </tr> 

                                            <tr><td style="height: 10px"></td></tr>

                                            <tr>
                                                <c:forEach var="verifypoint" items="${sessionScope.SessionObject.verificationPointsBeanList}">
                                                    <c:if test="${( verifypoint.isStaff.equals(sessionScope.SessionObject.verifyCustomerBean.staffStatus) ) && verifypoint.fieldName == 'DOCBIRTHCERTIFICATE'}">
                                                        <c:if test="${checkedApplicantBean.docBirthCertificate =='YES' || previousCheckedBean.docBirthCertificate =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkbirthcertifycopy" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <c:if test="${checkedApplicantBean.docBirthCertificate !='YES' && previousCheckedBean.docBirthCertificate !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkbirthcertifycopy" value="ON" style="width: 25px"/></td></c:if>
                                                                    <td style="width: 300px">
                                                                    <c:if test="${verifypoint.isMandatory ==true}">
                                                                        <font style="color: red;">*</font>
                                                                    </c:if>
                                                                    ${verifypoint.description}</td>                                                        <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                        <c:if test="${verifypoint.documentType !=null}">
                                                            <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Birth Certificate', '${sessionScope.SessionObject.verifyingAppId}', '${verifypoint.verificationCategory}', '${verifypoint.documentType}', '${verifypoint.fileName}');
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
                                                <td><b><font color="#FF0000"> ${errorMsg3}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg3}</font></b> </td>
                                            </tr>
                                        </table>



                                        <table>


                                            <tr> <td style="height:20px;"></td></tr>


                                            <tr>
                                                <td>Attach CRIB Report</td>
                                                <td><input type="file" name="cribfile" value="cribfile" width="20" /></td>
                                                <td><input type="submit"  value="Upload" name="upload" style="width: 100px" ></td>
                                            </tr> 

                                            <tr> <td style="height:60px;"></td></tr>

                                        </table> 



                                        <table>
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


                                    <div id="tabs-4">


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



