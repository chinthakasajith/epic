<%-- 
    Document   : applicationchecking
    Created on : Feb 22, 2012, 10:26:52 AM
    Author     : mahesh_m
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            $(function () {

                $("#tabs2").tabs();

                $(".selector").tabs({selected: ${selectedtab}});
                $(".nexttab").click(function () {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected + 1);
                });
                $(".previoustab").click(function () {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected - 1);
                });

                //                $("#tabs2").tabs("option", "disabled",[0,1,2,3,4]);
            });




            function invokeAcceptt(applicationId, type)
            {
//                answer = confirm("Are you sure you want to accept this application?")
//                if (answer !=0)
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId="+applicationId+"&type="+type;
//                }
//                else
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid="+applicationid;
//                }
                $("#dialog-confirm").html("<p>Are you sure you want to accept this application ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid=" + applicationId;
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId=" + applicationId + "&type=" + type;
                        }

                    }
                });

            }

            function invokeCheckIn(applicationId, type)
            {
//                answer = confirm("Are you sure you want to check in this appliaction?")
//                if (answer != 0)
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId=" + applicationId + "&type=" + type;
//                }
//                else
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid=" + applicationid;
//                }
                $("#dialog-confirm2").html("<p>Are you sure you want to check in this appliaction?</p>");
                $("#dialog-confirm2").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "YES": function () {
                            window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId=" + applicationId + "&type=" + type;
                        },
                        Cancel: function () {
                            window.location = "${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid=" + applicationId;
                        }
                    }
                });

            }


            //            
            //            
            //            
            //            function invokeAcceptt(applicationId,type)
            //            {
            //              
            //                window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId="+applicationId+"&type="+type;
            //                                                                                              
            //            } 

            function invokeUpdate()
            {

                document.updateTaskform.action = "${pageContext.request.contextPath}/UpdateConfiremedTaskMgtServlet";
                document.updateTaskform.submit();

            }

            function addRemarksTab1(applicationId)
            {

                document.addRemarkTab1.action = "${pageContext.request.contextPath}/AddRemarksServlet?tab=1&applicationId=" + applicationId;
                document.addRemarkTab1.submit();

            }

            function addRemarksTab2(applicationId)
            {

                document.addRemarkTab2.action = "${pageContext.request.contextPath}/AddRemarksServlet?tab=2&applicationId=" + applicationId;
                document.addRemarkTab2.submit();

            }

            function addRemarksTab3(applicationId)
            {

                document.addRemarkTab3.action = "${pageContext.request.contextPath}/AddRemarksServlet?tab=3&applicationId=" + applicationId;
                document.addRemarkTab3.submit();

            }

            function addRemarksTab4(applicationId)
            {

                document.addRemarkTab4.action = "${pageContext.request.contextPath}/AddRemarksServlet?tab=4&applicationId=" + applicationId;
                document.addRemarkTab4.submit();

            }
            function addRemarksTab5(applicationId)
            {

                document.addRemarkTab5.action = "${pageContext.request.contextPath}/AddRemarksServlet?tab=5&applicationId=" + applicationId;
                document.addRemarkTab5.submit();

            }
            function addRemarksTab6(applicationId)
            {

                document.addRemarkTab6.action = "${pageContext.request.contextPath}/AddRemarksServlet?tab=6&applicationId=" + applicationId;
                document.addRemarkTab6.submit();

            }
        </script>

    </head>
    <body >



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">


                                <table class="tit"> <tr> <td   class="center">  APPLICATION CHECKING </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <table cellpadding="0" cellspacing="10">
                                    <tr>
                                        <td style="width: 200px;"> Application ID</td>
                                        <td><b><font color="#000000"> ${cardApplicationList.applicationId}</font></b> </td>
                                        <td style="width: 200px;"></td>
                                        <td style="width: 200px;"> Application Category</td>
                                        <td><b><font color="#000000"> Main</font></b> </td>
                                    </tr>

                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Employment</a></li>
                                        <li><a href="#tabs-3">Income/Expenses</a></li>
                                        <li><a href="#tabs-4">Bank</a></li>
                                        <li><a href="#tabs-5">Document</a></li>
                                        <li><a href="#tabs-6">Signature</a></li>
                                    </ul>
                                    <div id="tabs-1" >

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorRemark1}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab1}</font></b> </td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <br /><font style="color: #999"> Personal Information</font>   
                                        <table cellpadding="0" cellspacing="10"  >


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


                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp Religion</td>    
                                                <td>
                                                    <b><font color="#000000"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp ${personalDetail.religion}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>

                                            <tr>


                                                <!--                                                <td style="width: 200px;">First Name</td>
                                                                                                <td>
                                                                                                    <b><font color="#000000"> ${personalDetail.firstName}</font></b>
                                                                                                </td>
                                                                                                <td style="width: 100px;"></td> -->

                                                <td style="width: 200px;"> Gender</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.gender}</font></b>
                                                </td>

                                            </tr>
                                            <!--                                            <tr>
                                            
                                                                                            <td style="width: 200px;">Last Name</td>
                                                                                            <td>
                                                                                                <b><font color="#000000"> ${personalDetail.lastName}</font></b>
                                                                                            </td>
                                                                                            <td style="width: 100px;"></td> 
                                            
                                                                                            <td style="width: 200px;"> Middle Names</td>    
                                                                                            <td>
                                                                                                <b><font color="#000000"> ${personalDetail.middleName}</font></b>
                                                                                            </td>
                                                                                            <td style="width: 100px;"></td> 
                                                                                        </tr>-->
                                        </table>
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr>
                                                <td style="width: 200px">Full Name</td>  
                                                <td>
                                                    <b><font color="#000000">${personalDetail.fullName}</font></b>
                                                </td>
                                            </tr>
                                        </table>
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr>
                                                <td style="width: 200px;">Name on the Card</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.nameOncard}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 


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

                                                <td style="width: 100px;"></td> 
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
                                                <td style="width: 100px;"></td> 
                                                <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->


                                                <td style="width: 200px;"> Blood Group</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.bloodgroup}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td colspan="6" ><hr /></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br />Residential Information</font></td>
                                            </tr>


                                            <tr>
                                                <td style="width: 200px;">Address </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address1}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Res. Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.homeTelNumber}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address2}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Office Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.officeTelNumber}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address3}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Mobile Phone No</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.mobileNumber}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                            <tr>

                                                <td style="width: 200px;">Area</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.city}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;">Email Address</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.email}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>

                                            <tr>

                                                <td style="width: 200px;">Residence Type</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.residenceType}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;">Duration of the Address</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.durationofTheAddress}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 


                                            </tr>


                                        </table>

                                        <br /><hr /><font style="color: #999"> Spouse Information</font>

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Name in Full </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spouseName}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Company Name/Business </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spouseNameofEmployee}</font></b>
                                                </td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Designation</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spouseDesignation}</font></b>
                                                </td>




                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Company Address </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spousecompanyAddress}</font></b>
                                                </td>



                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">NIC Number </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spouseNic}</font></b>
                                                </td>



                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Phone Number</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spousePhone}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Email Address</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spouseMail}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Monthly Income</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.spouseMonthlyIncome}</font></b>
                                                </td>


                                            </tr>
                                        </table>
                                        <br /><hr /><font style="color: #999"> Referral Information</font>
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Name</td>

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relName}</font></b>
                                                </td>



                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Relationship</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relationship}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Company Name/Business</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relCompany}</font></b>
                                                </td>

                                            </tr> 


                                            <tr>
                                                <td style="width: 200px;">Address </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relAddress1}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Res. Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relResidencePhone}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relAddress2}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Office Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relOfficeNumber}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relAddress3}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Mobile Phone No</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relMobile}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                            <tr>

                                                <td style="width: 200px;">Area</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relCity}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;">Email Address</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.relEmail}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                        </table>


                                        <br /><hr /><font style="color: #999"> Other Information</font>
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Mother's Maiden Name</td>

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.mothersMaidenName}</font></b>
                                                </td>



                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Academic Qualification</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.acedemicQualifications}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Professional Qualification</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.professionalQualifications}</font></b>
                                                </td>

                                            </tr> 


                                            <tr>
                                                <td style="width: 200px;">Vehicle Type </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.vehicalType}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Vehicle Number</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.vehicalNo}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"> Card type</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardTypeDes}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                                <td style="width: 200px;"> Card Product</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardProductDes}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"> Requested Credit Limit</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber  type="number" minFractionDigits="2" value="${personalDetail.creditLimit}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>

                                        </table>             

                                        <br /><hr /><font style="color: #999"> Remarks</font>       
                                        <table>
                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td>&nbsp;</td>



                                            </tr>
                                            <form action="" method="POST" name="addRemarkTab1">
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA NAME="remark1" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="addRemarks1" value="Add Remarks" onclick="addRemarksTab1('${personalDetail.applicationId}')" style="width: 100px;"/></td>


                                                </tr>
                                            </form>
                                        </table>            



                                    </div>
                                    <div id="tabs-2">


                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorRemark2}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab2}</font></b> </td>
                                            </tr>
                                        </table>
                                            <br/>
                                        <table cellpadding="0" cellspacing="10"  >
                                            <tr>
                                                <td style="width: 200px;">
                                                    Employment Status
                                                </td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.employmentStatus}</font></b>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Employment Type </td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.employmentType}</font></b>
                                                </td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Name of Employer </td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.companyName}</font></b>
                                                </td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Company Address </td>
                                                <td><b><font color="#000000"> ${employementDetails.adress1}</font></b></td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td><b><font color="#000000"> ${employementDetails.adress2}</font></b></td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td><b><font color="#000000"> ${employementDetails.adress3}</font></b></td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Office Telephone </td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.officePhone}</font></b>
                                                </td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Occupation </td>
                                                <c:forEach var="Occupation" items="${occuptionList}">
                                                    <c:if test="${Occupation.occupationTypeCode==employementDetails.occupation}">
                                                    <td>
                                                        <b><font color="#000000"> ${Occupation.description}</font></b>
                                                    </td>
                                                    </c:if>
                                                </c:forEach>
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Designation </td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.designation}</font></b>
                                                </td>

                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Nature of business</td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.businessNature}</font></b>
                                                </td>

                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">Number of employees</td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.numberOfEmployees}</font></b>
                                                </td>
                                            </tr> 

                                        </table>
                                        <br /><hr /><font style="color: #999"> Self Employed Applicant Info:</font>

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Name of Company/Business </td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.companyName}</font></b>
                                                </td>

                                            </tr>
                                        </table>  
                                        <table  cellpadding="0" cellspacing="10"> 

                                            <tr>
                                                <td style="width: 200px;">Number of Employees</td>
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.numberOfEmployees}</font></b>
                                                </td>

                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Net Profit</td>    
                                                <td>
                                                    <b><font color="#000000">  <fmt:formatNumber  type="number" minFractionDigits="2" value="${employementDetails.netProfit}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Annual Turnover</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number"  minFractionDigits="2"value="${employementDetails.annualTurnOver}"/> &nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>

                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> In Business Since</td>    
                                                <td>
                                                    <b><font color="#000000"> ${employementDetails.busnessFrom}</font></b>
                                                </td>

                                            </tr>
                                        </table>
                                        <br /><hr /><font style="color: #999"> Remarks</font>    
                                        <table>

                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td>&nbsp;</td>

                                            </tr>
                                            <form action="" method="POST" name="addRemarkTab2" >
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA NAME="remark2" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="addRemarks2" value="Add Remarks"  onclick="addRemarksTab2('${personalDetail.applicationId}')" style="width: 100px;"/></td>


                                                </tr>

                                            </form>
                                        </table> 

                                    </div>
                                    <div id="tabs-3">


                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorRemark3}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab3}</font></b> </td>
                                            </tr>
                                        </table>

                                             <br/>

                                        <br /><font style="color: #999"> Income Information</font>

                                        <br></br>
                                        <table  border="1"  class="display" id="example">
                                            <thead style="text-align: left">
                                                <tr class="gradeB">
                                                    <th>Income Category</th>
                                                    <th>Amount</th>

                                                </tr>
                                            </thead>

                                            <tbody>
                                                <c:forEach var="incomeDetailslist" items="${incomeDetailslist}">
                                                    <tr>

                                                        <td>${incomeDetailslist.incomeCatogary}</td>
                                                        <td><fmt:formatNumber type="number" minFractionDigits="2" value="${incomeDetailslist.amount}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>   

                                        <br></br>

                                        <br /><hr /><font style="color: #999"> Expenses Information</font>
                                        <table  cellpadding="0" cellspacing="10"> 


                                            <tr>
                                                <td style="width: 200px;">Rent </td>
                                                <td>
                                                    <b><font color="#000000"><fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.rentAmount}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>


                                            </tr> 

                                            <tr>
                                                <td style="width: 200px;">Loan Installment</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.loanInstallmentAmount}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Lease rental</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.leaseAmount}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Credit Card Bill</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.creditCardbill}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Other Borrowings</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.otherBorrows}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Insurance Premiums</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number" minFractionDigits="2"  value="${expensesDetails.insuranceInstallment}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">House holder expenses</td>
                                                <td>
                                                    <b><font color="#000000"><fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.houseHolderExpenses}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Other Expenses</td>
                                                <td>
                                                    <b><font color="#000000"><fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.otherExpenses}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Total</td>
                                                <td>
                                                    <b><font color="#000000"> <fmt:formatNumber type="number" minFractionDigits="2" value="${expensesDetails.total}"/>&nbsp;${personalDetail.currencyTypeAlphaCode}</font></b>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td>&nbsp;</td>



                                            </tr>
                                            <form action="" method="POST" name="addRemarkTab3" >
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA NAME="remark3" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="addRemarks3" value="Add Remarks" onclick="addRemarksTab3('${personalDetail.applicationId}')" style="width: 100px;"/></td>


                                                </tr>

                                            </form>

                                        </table> 
                                    </div>
                                    <div id="tabs-4">


                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorRemark4}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab4}</font></b> </td>
                                            </tr>
                                        </table>

                                             <br/>
                                            
                                        <table  border="1"  class="display" id="example">
                                            <thead style="text-align: left">
                                                <tr class="gradeB">
                                                    <th>Name of the bank</th>
                                                    <th>Branch Name</th>
                                                    <th>Account Type</th>
                                                    <th>Account Number</th>
                                                    <th>Account Since</th>

                                                </tr>
                                            </thead>
                                            <tbody >
                                                <c:forEach var="bankDetails" items="${cardBankDetails}">
                                                    <tr>

                                                        <td >${bankDetails.bankName}</td>
                                                        <td >${bankDetails.branchName}</td>
                                                        <td >${bankDetails.accountType}</td>
                                                        <td >${bankDetails.accountNumber}</td>
                                                        <td >${bankDetails.accountSince}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>       

                                        <table>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td>
                                                </td>
                                                <td style="width: 100px;"></td>


                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td>&nbsp;</td>



                                            </tr>
                                            <form action="" method="POST" name="addRemarkTab4" >
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA NAME="remark4" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                                </tr>
                                                <tr style="height: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="addRemarks4" value="Add Remarks" onclick="addRemarksTab4('${personalDetail.applicationId}')" style="width: 100px;"/></td>
                                                </tr>
                                            </form>
                                        </table>

                                    </div>

                                    <div id="tabs-5">


                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorRemark5}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab5}</font></b> </td>
                                            </tr>
                                        </table>

                                             <br/>
                                            
                                        <table  border="1"  class="display" id="example">
                                            <thead style="text-align: left">
                                                <tr class="gradeB">
                                                    <th>Verification Category</th>
                                                    <th>Document Type</th>
                                                    <th>File Name</th>

                                                </tr>
                                            </thead>
                                            <tbody >
                                                <c:forEach var="documentDetails" items="${cardDocumentList}">
                                                    <tr>

                                                        <td >${documentDetails.verificationCategory}</td>
                                                        <td >${documentDetails.documentType}</td>
                                                        <td >${documentDetails.fileName}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>       

                                        <table>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td>
                                                </td>
                                                <td style="width: 100px;"></td>



                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td>&nbsp;</td>



                                            </tr>
                                            <form action="" method="POST" name="addRemarkTab5" >
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA NAME="remark5" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                                </tr>
                                                <tr style="height: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="addRemarks5" value="Add Remarks" onclick="addRemarksTab5('${personalDetail.applicationId}')" style="width: 100px;"/></td>
                                                </tr>
                                            </form>
                                        </table>

                                    </div>

                                    <div id="tabs-6">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorRemark6}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab6}</font></b> </td>
                                            </tr>
                                        </table>

                                            <br/>
                                            
                                        <table  border="0"  class="display" id="example">
                                            <tr>

                                                <td>

                                                    <img src="<%=request.getContextPath()%>/LoadSignatureForCheckingServlet?applicationId=${cardApplicationList.applicationId}" /> 
                                                </td>

                                            </tr>
                                        </table>       

                                        <table>
                                            <tr>
                                                <td style="width: 200px;"> </td>
                                                <td>
                                                </td>
                                                <td style="width: 100px;"></td>

                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td>&nbsp;</td>


                                            </tr>
                                            <form action="" method="POST" name="addRemarkTab6" >
                                                <tr>
                                                    <td style="width: 200px;">Remarks</td>
                                                    <td> <TEXTAREA NAME="remark6" ROWS="3" style="width: 350px;"></TEXTAREA></td>
                                                </tr>
                                                <tr style="height: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 200px;"></td>
                                                    <td><input type="submit" name="addRemarks6" value="Add Remarks" onclick="addRemarksTab6('${personalDetail.applicationId}')" style="width: 100px;"/></td>
                                                </tr>
                                            </form>
                                        </table>

                                    </div>


                                </div>

                                <table style="padding: 10px; margin-left: 400px;">
                                    <tr>
                                        <td> <input type="button" name="accept" value="Accept" onclick="invokeAcceptt('${personalDetail.applicationId}', '0')" style="width: 100px;"/></td>
                                        <td> <input type="button" name="checkIn" value="Re Check" onclick="invokeCheckIn('${personalDetail.applicationId}', '1')" style="width: 100px;"/></td>
                                    </tr>
                                </table> 

                            </div>

                        </div>
                    </div>



                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
                                     <!--confirmation dialog -->
        <div id="dialog-confirm" title="Accept Confirmation">

        </div>
        <div id="dialog-confirm2" title="Re Check Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

