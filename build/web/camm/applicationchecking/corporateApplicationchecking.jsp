<%-- 
    Document   : cardAgainstFDApplicationchecking
    Created on : Jun 27, 2016, 11:32:26 AM
    Author     : prageeth_s
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            $(function() {
                
                $( "#tabs2" ).tabs();
               
                $( ".selector" ).tabs({ selected: ${selectedtab} });
                $(".nexttab").click(function() {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected + 1);
                });
                $(".previoustab").click(function() {
                    var selected = $("#tabs2").tabs("option", "selected");
                    $("#tabs2").tabs("option", "selected", selected - 1);
                });
                
                //                $("#tabs2").tabs("option", "disabled",[0,1,2,3,4]);
            });
           
            
            
            
            function invokeAcceptt(applicationId,type)
            {
                answer = confirm("Are you sure you want to accept this application?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/LoadApplicationSearchServlet?applicationId="+applicationId+"&type="+type;
                }
                else
                {
                    window.location = "${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid="+applicationid;
                }
               
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
                    window.location = "${pageContext.request.contextPath}/LoadApplicationCheckingServlet?applicationid="+applicationid;
                }
               
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

                document.updateTaskform.action="${pageContext.request.contextPath}/UpdateConfiremedTaskMgtServlet";
                document.updateTaskform.submit();

            }
            
            function addRemarksTab1(applicationId)
            {
              
                document.addRemarkTab1.action="${pageContext.request.contextPath}/AddRemarksServlet?tab=1&applicationId="+applicationId;
                document.addRemarkTab1.submit();

            }
           
            function addRemarksTab2(applicationId)
            {
                  
                document.addRemarkTab2.action="${pageContext.request.contextPath}/AddRemarksServlet?tab=2&applicationId="+applicationId;
                document.addRemarkTab2.submit();

            }
            
            function addRemarksTab3(applicationId)
            {
 
                document.addRemarkTab3.action="${pageContext.request.contextPath}/AddRemarksServlet?tab=3&applicationId="+applicationId;
                document.addRemarkTab3.submit();

            }
            
            function addRemarksTab4(applicationId)
            {
        
                document.addRemarkTab4.action="${pageContext.request.contextPath}/AddRemarksServlet?tab=4&applicationId="+applicationId;
                document.addRemarkTab4.submit();

            }
            function addRemarksTab5(applicationId)
            {
        
                document.addRemarkTab5.action="${pageContext.request.contextPath}/AddRemarksServlet?tab=5&applicationId="+applicationId;
                document.addRemarkTab5.submit();

            }
            function addRemarksTab6(applicationId)
            {
        
                document.addRemarkTab6.action="${pageContext.request.contextPath}/AddRemarksServlet?tab=6&applicationId="+applicationId;
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
                                        <td><b><font color="#000000"> Corporate</font></b> </td>
                                    </tr>

                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs2">
                                    <ul>
                                        <li><a href="#tabs-1">Personal </a></li>
                                        <li><a href="#tabs-2">Document</a></li>
                                        <li><a href="#tabs-3">Signature</a></li>
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


                                                <td style="width: 200px;">Company</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.companyName}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;">Business Registration Number</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.businessRegNumber}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
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


                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Religion</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.religion}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>

                                            <tr>


                                                <td style="width: 200px;">First Name</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.firstName}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Gender</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.gender}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr>
                                            <tr>

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
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Name on the Card</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.nameOncard}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 


                                                <td style="width: 200px;"> </td>    

                                                <td>
                                                    
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


                                                <td style="width: 200px;"> </td>    
                                                <td>
                                                    
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

                                                <td style="width: 200px;">City</td>
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
                                        <br /><hr /><font style="color: #999"> Other Information</font>
                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td style="width: 200px;">Mother's Maiden Name</td>

                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.mothersMaidenName}</font></b>
                                                </td>



                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"> Card type</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardType}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                                <td style="width: 200px;"> Card Product</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardProduct}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td> 
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"> Requested Credit Limit</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.creditLimit}</font></b>
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
                                                <td><b><font color="#FF0000"> ${errorRemark5}</font></b> </td>
                                                <td><b><font color="Green"> ${remarkTab5}</font></b> </td>
                                            </tr>
                                        </table>

                                             <br/>
                                            
                                        <table  border="1"  class="display" id="example">
                                            <thead>
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

                                    <div id="tabs-3">

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
                                        <td> <input type="button" name="accept" value="Accept" onclick="invokeAcceptt('${personalDetail.applicationId}','0')" style="width: 100px;"/></td>
                                        <td> <input type="button" name="checkIn" value="Re Check" onclick="invokeCheckIn('${personalDetail.applicationId}','1')" style="width: 100px;"/></td>
                                    </tr>
                                </table> 

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
