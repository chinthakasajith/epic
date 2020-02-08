<%-- 
    Document   : corporateetsablishmentverifyview
    Created on : May 15, 2013, 10:56:58 AM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page import="com.epic.cms.system.util.session.SessionVarList" %>
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
                
                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId="+applicationId+"&verificationCategory="+verificationCategory+"&documentType="+documentType+"&fileName="+fileName, identityType , "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");
                
            }          
            
            
            function invokeCancel(){
             
                window.location = "${pageContext.request.contextPath}/camm/documentverification/corporatecardverifyhome.jsp";
             
            }
            function invokeVerify()
            {
                
                document.applicantverifyform.action="${pageContext.request.contextPath}/VerifyCorporateEstAplicantServlet";
                document.applicantverifyform.submit();

            }
            function invokeReject()
            {
                
                document.applicantverifyform.action="${pageContext.request.contextPath}/RejectCorporateEstApplicationDetailsServlet";
                document.applicantverifyform.submit();

            }
            function invokeOnhold(){
                
                document.applicantverifyform.action="${pageContext.request.contextPath}/OnholdCorporateEstApplicationDetailsServlet";
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
                    window.location = "${pageContext.request.contextPath}/VerifyCorporateEstAplicantServlet?applicationid="+applicationId;
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

                                <table class="tit"> <tr> <td   class="center"> <B> CORPORATE VERIFICATION </B> </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <%-- -------------------------add form start -------------------------------- --%>

                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Company Verification</a></li>
                                        <li><a href="#tabs-2">Document Verification</a></li>
                                        <!--                                        <li><a href="#tabs-3">CRIB Report</a></li>
                                                                                <li><a href="#tabs-4">Application Summary</a></li>-->
                                    </ul>


                                    <form action="" name="applicantverifyform" method="POST">
                                        <div id="tabs-1" >

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
                                                    <td style="width: 150px">${sessionScope.SessionObject.corporateAppId}</td>
                                                    <td><input type="hidden" name="appliactionid" value="${sessionScope.SessionObject.corporateAppId}"/></td>
                                                </tr>
                                                <tr><td style="height:10px;"></td></tr>
                                            </table>

                                            <table class=""> <tr> <td  style="width: 160px" class="center"> <B> Company Particulars</B>  </td> <td style="width: 300px"></td><td  class="center"> <B> Company  Details  </B> </td></tr><tr> <td>&nbsp;</td> </tr></table>

                                            <table border="0" cellspacing="10" cellpadding="0">
                                                <tbody>

                                                    <tr>
                                                        <td style="width: 160px">Full Company Name </td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.companyName != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.companyName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.companyName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td>Company Reg Number</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.brc != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.brc}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.brc == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>


                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Address </td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.address1 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">
                                                                ${sessionScope.SessionObject.verifyBean.address1}<BR />
                                                                ${sessionScope.SessionObject.verifyBean.address2}<BR />
                                                                ${sessionScope.SessionObject.verifyBean.address3}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.address1 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td>Date Of Registration</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.registerDate != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.registerDate}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.registerDate == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>
                                                    </tr>

                                                    <tr>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td style="width: 150px"></td>

                                                        <td>Type Of Company</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.companyType != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.companyType}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.companyType == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>
                                                    </tr>



                                                    <tr>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>

                                                        <td>Number Of Employee</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.noOfEmployees != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.noOfEmployees}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.noOfEmployees == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Office Phone 1</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.officePhoneNo1 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.officePhoneNo1}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.officePhoneNo1 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td>Capital Invested</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.capitalInvested != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.capitalInvested}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.capitalInvested == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Office Phone 2</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.officePhoneNo2 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.officePhoneNo2}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.officePhoneNo2 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td>Annual Turnover</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.annualTurnover != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.annualTurnover}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.annualTurnover == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Contact Person</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.contactPerson != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.contactPerson}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.contactPerson == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>

                                                        <td>Annual Turnover Year</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.annualTurnoverYear != null}"><td><font>:&nbsp;</font></td><td>${sessionScope.SessionObject.verifyBean.annualTurnoverYear}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.annualTurnoverYear == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Contact Person Position</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.contactPersonPosition != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.contactPersonPosition}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.contactPersonPosition == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                </tbody>
                                            </table>

                                            <!-- ------------------------------------------- -------------------------------------------------- -->

                                            <table class=""> <tr> <td  style="width: 160px" class="center"> <B> Company Auditor Details</B>  </td> <td style="width: 300px"></td></tr></table>

                                            <table border="0" cellspacing="10" cellpadding="0">
                                                <tbody>

                                                    <tr>
                                                        <td style="width: 160px">Auditor Name</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorName != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorName}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorName == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Address</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorAddress1 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorAddress1}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorAddress1 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px"></td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorAddress2 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorAddress2}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorAddress2 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px"></td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorAddress3 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorAddress3}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorAddress3 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Office Phone1</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficePhone1 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorOfficePhone1}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficePhone1 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Office Phone2</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficePhone2 != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorOfficePhone2}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficePhone2 == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Contact Person</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficeContactPerson != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorOfficeContactPerson}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficeContactPerson == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                    <tr>
                                                        <td style="width: 160px">Contact Person Position</td>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficeContactPersonPosition != null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible">${sessionScope.SessionObject.verifyBean.auditorOfficeContactPersonPosition}</td></c:if>
                                                        <c:if test="${sessionScope.SessionObject.verifyBean.auditorOfficeContactPersonPosition == null}"><td><font>:&nbsp;</font></td><td style="width: 150px ; overflow: visible"><font>&nbsp;-</font></td></c:if>

                                                        <td style="width: 150px"></td>
                                                    </tr>

                                                </tbody>
                                            </table>

                                            <table class=""> <tr> <td  style="width: 160px" class="center"> <B> With Company</B>  </td> <td style="width: 300px"></td><td  class="center"> <B> With Auditor </B> </td></tr><tr> <td>&nbsp;</td> </tr></tr></table>

                                            <table border="0" cellspacing="10" cellpadding="0">
                                                <tbody>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='COMPANYNAME'}"> 
                                                                <c:if test="${checkedApplicantBean.companyName =='YES' || previousCheckedBean.companyName =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="companyName" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.companyName !='YES' && previousCheckedBean.companyName !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="companyName" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='AUDITORNAME'}"> 

                                                                <c:if test="${checkedApplicantBean.auditorName =='YES' || previousCheckedBean.auditorName =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="auditorName" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.auditorName !='YES' && previousCheckedBean.auditorName !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="auditorName" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>

                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>
                                                    </tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='BRC'}"> 
                                                                <c:if test="${checkedApplicantBean.brc =='YES' || previousCheckedBean.brc =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="brc" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.brc !='YES' && previousCheckedBean.brc !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="brc" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>

                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='AUDITORADDRESS'}"> 

                                                                <c:if test="${checkedApplicantBean.auditorAddress =='YES' || previousCheckedBean.auditorAddress =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="auditorAddress" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.auditorAddress !='YES' && previousCheckedBean.auditorAddress !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="auditorAddress" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>

                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px"><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>    
                                                            </c:forEach>
                                                    </tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='TYPEOFCOMPANY'}"> 
                                                                <c:if test="${checkedApplicantBean.typeOfCompany =='YES' || previousCheckedBean.typeOfCompany =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="typeOfCompany" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.typeOfCompany !='YES' && previousCheckedBean.typeOfCompany !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="typeOfCompany" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>
                                                    </tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='NUMBEROFEMPLOYEES'}"> 
                                                                <c:if test="${checkedApplicantBean.noOfEmployee =='YES' || previousCheckedBean.noOfEmployee =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="noOfEmployee" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.noOfEmployee !='YES' && previousCheckedBean.noOfEmployee !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="noOfEmployee" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>
                                                    </tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='CAPITALINVESTED'}"> 
                                                                <c:if test="${checkedApplicantBean.capitalInvested =='YES' || previousCheckedBean.capitalInvested =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="capitalInvested" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.capitalInvested !='YES' && previousCheckedBean.capitalInvested !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="capitalInvested" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>
                                                    </tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='ANNUALTURNOVER'}"> 
                                                                <c:if test="${checkedApplicantBean.annualTurnOver =='YES' || previousCheckedBean.annualTurnOver =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="annualTurnOver" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.annualTurnOver !='YES' && previousCheckedBean.annualTurnOver !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="annualTurnOver" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>
                                                    </tr>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='REGISTEDDATE'}"> 
                                                                <c:if test="${checkedApplicantBean.dateOfReg =='YES' || previousCheckedBean.dateOfReg =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="dateOfReg" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.dateOfReg !='YES' && previousCheckedBean.dateOfReg !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="dateOfReg" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>
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

                                            <table class=""> <tr> <td  class="center"> <B> DOCUMENT VERIFICATION </B> </td> <td style="width: 350px; height: 50px"></td></tr></table>

                                            <table border="0" cellspacing="10" cellpadding="0">
                                                <tbody>

                                                    <tr>
                                                        <c:forEach var="verifypoint" items="${sessionScope.SessionObject.corporateVerificationPointsList}">
                                                            <c:if test="${verifypoint.fieldName =='DOCBRC'}"> 
                                                                <c:if test="${checkedApplicantBean.docBrc =='YES' || previousCheckedBean.docBrc =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="docBrc" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                                <c:if test="${checkedApplicantBean.docBrc !='YES' && previousCheckedBean.docBrc !='YES'}"> <td style="width: 25px"> <input type="checkbox" name="docBrc" value="ON" style="width: 25px"/></td></c:if>
                                                                <td style="width: 300px">${verifypoint.description}</td>
                                                                <c:if test="${verifypoint.documentType ==null}"><td></td> </c:if>
                                                                <c:if test="${verifypoint.documentType !=null}">
                                                                    <c:if test="${verifypoint.documentExist =='YES'}">  <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.verifyingAppId}','${verifypoint.verificationCategory}','${verifypoint.documentType}','${verifypoint.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td></c:if>
                                                                    <c:if test="${verifypoint.documentExist =='NO'}">  <td style="width: 25px"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/realcross.jpg"/></td></c:if>                                                                
                                                                    </c:if>
                                                                </c:if>  
                                                            </c:forEach>

                                                        <td style="width: 110px"></td>
                                                    </tr>

                                                </tbody>
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



                                            </table>


                                        </div>

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

