<%-- 
    Document   : bulkverifyviewhome
    Created on : Dec 5, 2012, 12:29:58 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/windowcss/dhtmlwindow.css" type="text/css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/dhtmlwindow.js"></script>
        <script>
            
                        
            function invokeWindow(identityType,applicationId,verificationCategory,documentType,fileName){           
                
                var googlewin = dhtmlwindow.open("googlebox", "iframe", "${pageContext.request.contextPath}/camm/documentverification/imageviewhome.jsp?applicationId="+applicationId+"&verificationCategory="+verificationCategory+"&documentType="+documentType+"&fileName="+fileName, identityType , "width=320px,height=435px,top=110px,left=750px,resize=1,scrolling=0,center=0", "recal");
                
            } 
            

            function updateVerify(){
                
                document.verifyform.action="${pageContext.request.contextPath}/VerifyBulkDetailsServlet?option=verify";
                document.verifyform.submit();
            }
            function updateReject(){
                
                document.verifyform.action="${pageContext.request.contextPath}/VerifyBulkDetailsServlet?option=reject";
                document.verifyform.submit();
            }
            

            function invokeBack(){
            
                window.location = "${pageContext.request.contextPath}/LoadBulkCardVerifySearchServlet";
            }
            
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">



                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <table cellpadding="0" cellspacing="10">
                                    <tr>
                                        <td style="width: 150px;"> Application ID :</td>
                                        <td><label> ${sessionScope.SessionObject.debitDetailsBean.applicationId} </label> </td>
                                        <td style="width: 100px;"></td> 
                                        <td style="width: 150px;"> Card Number :</td>
                                        <td><label> ${sessionScope.SessionObject.debitDetailsBean.cardNumber} </label> </td>
                                    </tr>
                                </table>

                                <br /><hr /><br />


                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Main </a></li>


                                    </ul>

                                    <!-- --------tab 1------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                                   -->

                                    <div id="tabs-1" >


                                        <table cellpadding="0" cellspacing="10"  >


                                            <tr>
                                                <td style="width: 200px;">
                                                    Identification Type
                                                </td>

                                                <td><select name="idType" disabled="true">
                                                        <option value="">--Select Identification Type-- </option>

                                                        <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                            <c:if test="${sessionScope.SessionObject.debitDetailsBean.identificationType==identity.key}">
                                                                <option value="${identity.key}" selected>${identity.value}</option>
                                                            </c:if>
                                                            <c:if test="${sessionScope.SessionObject.debitDetailsBean.identificationType != identity.key}">
                                                                <option value="${identity.key}" >${identity.value}</option>
                                                            </c:if>
                                                        </c:forEach>        


                                                    </select></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Identification Number</td>

                                                <td><label name="identificationNo" >${sessionScope.SessionObject.debitDetailsBean.identificationNo}</label></td>
                                                <td style="width: 100px;"> </td>
                                            </tr>


                                            <tr>
                                                <td style="width: 200px;">Title</td>
                                                <td><select name="title" disabled="true">
                                                        <option value="">--Select Title--</option>

                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Mr'}"> <option selected="true" value="Mr">Mr</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Mr'}"> <option value="Mr">Mr</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Mrs'}"> <option selected="true" value="Mrs">Mrs</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Mrs'}"> <option value="Mrs">Mrs</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Ms'}"> <option selected="true" value="Ms">Ms</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Ms'}"> <option value="Ms">Ms</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Dr'}"> <option selected="true" value="Dr">Dr</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Dr'}"> <option value="Dr">Dr</option></c:if>                                                
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Prof'}"> <option selected="true" value="Prof">Prof</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Prof'}"> <option value="Prof">Prof</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Rev'}"> <option selected="true" value="Rev">Rev</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Rev'}"> <option value="Rev">Rev</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Hon'}"> <option selected="true" value="Hon">Hon</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Hon'}"> <option value="Hon">Hon</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title =='Ven'}"> <option selected="true" value="Ven">Ven</option></c:if>
                                                        <c:if test="${sessionScope.SessionObject.debitDetailsBean.title !='Ven'}"> <option value="Ven">Ven</option></c:if>



                                                        </select>
                                                    </td><td style="width: 100px;"></td> 

                                                    <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->
                                                    <td style="width: 200px;">Date Of Birth</td>
                                                    <td><lable>${sessionScope.SessionObject.debitDetailsBean.dateOfBirth}</lable></td>
                                            <td style="width: 100px;"></td> 
                                            </tr>

                                            <tr>


                                                <td style="width: 200px;">First Name</td>
                                                <td><lable>${sessionScope.SessionObject.debitDetailsBean.firstName}</lable></td>  
                                            <td style="width: 100px;"> </td>
                                            <!--              ------------------------------------------------------------------------------------------------------------------------------------                       -->                                          


                                            <td style="width: 200px;"> Address</td>    
                                            <td>${sessionScope.SessionObject.debitDetailsBean.address}</td>    
                                            <td style="width: 100px;"> </td>



                                            </tr>
                                            <tr>

                                                <td style="width: 200px;"> Middle Name</td>    
                                                <td>${sessionScope.SessionObject.debitDetailsBean.middleName}</td>  
                                                <td style="width: 100px;"></td> 


                                            </tr>
                                            <tr>

                                                <td style="width: 200px;">Last name</td>
                                                <td>${sessionScope.SessionObject.debitDetailsBean.lastName}</td>
                                                <td style="width: 100px;"></td>  




                                            </tr>



                                            <tr>
                                                <td style="width: 200px;"> Mothers Maiden Name</td>    
                                                <td>${sessionScope.SessionObject.debitDetailsBean.mothersMaidenName}</td>

                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;"> Mobile Number</td>    
                                                <td>${sessionScope.SessionObject.debitDetailsBean.mobileNo}</td>


                                            </tr>



                                            <tr style="height: 10px"></tr>

                                        </table>


                                        <table class=""> <tr> <td   class="center"> <B> Account Details </B></td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                        <table  border="1"  class="display" id="tableview">
                                            <thead>
                                                <tr>
                                                    <th>Account Number</th>
                                                    <th>Account Type</th>
                                                    <th>Account Currency</th>
                                                    <th>Primary Status</th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="account" items="${sessionScope.SessionObject.verifyAccoutBeanLst}">
                                                    <tr>
                                                        <td >${account.accountNumber}</td>
                                                        <td >${account.accountType}</td>
                                                        <td >${account.accountCurrency}</td>
                                                        <td >${account.accountStatus}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>












                                        <table cellpadding="0" cellspacing="10" >

                                            <tr style="height: 10px"></tr>
                                            <tr>
                                                <td style="width: 200px;">signature</td>
                                                <td style="width: 25px" ><a href="#" onClick="invokeWindow('Scanned Document','${sessionScope.SessionObject.personalSignatureBean.applicationId}','${sessionScope.SessionObject.personalSignatureBean.verificationCategory}','${sessionScope.SessionObject.personalSignatureBean.documentType}','${sessionScope.SessionObject.personalSignatureBean.fileName}'); return false"><img  style="width: 20px ; height: 15px" src="${pageContext.request.contextPath}/resources/images/windowimages/right.jpg"/></a></td>
                                            </tr>

                                            <tr style="height: 12px"></tr>
                                        </table>



                                        <form method="POST" action="" name="verifyform" >


                                            <tr style="height: 12px"></tr>
                                            </table>

                                            <table class=""> <tr> <td  style="width: 200px" class="center"> <B> Account Holder Verification</B>  </td></tr></table>

                                            <table cellpadding="0" cellspacing="10">
                                                <tr style="height: 12px"></tr>
                                                <tr>
                                                    <c:if test="${verifyBean.accontNo =='NO' || verifyBean.accontNo == null}"><td style="width: 25px"> <input type="checkbox" name="checkaccno"  value="ON" style="width: 25px"/></td></c:if>
                                                    <c:if test="${verifyBean.accontNo =='YES'}"><td style="width: 25px"> <input type="checkbox" name="checkaccno" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <td style="width: 150px;">Account Number</td>


                                                    </tr>
                                                    <tr>
                                                    <c:if test="${verifyBean.id =='NO' || verifyBean.id == null}"> <td style="width: 25px"> <input type="checkbox" name="checkid"  value="ON" style="width: 25px"/></td></c:if>
                                                    <c:if test="${verifyBean.id =='YES'}"> <td style="width: 25px"> <input type="checkbox" name="checkid"  checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <td style="width: 150px;">ID</td>
                                                    </tr>
                                                    <tr>
                                                    <c:if test="${verifyBean.name =='NO' || verifyBean.name == null}"><td style="width: 25px"> <input type="checkbox" name="checkname"  value="ON" style="width: 25px"/></td></c:if>
                                                    <c:if test="${verifyBean.name =='YES'}"><td style="width: 25px"> <input type="checkbox" name="checkname" checked="true" value="ON" style="width: 25px"/></td></c:if>
                                                        <td style="width: 150px;">Name</td>

                                                        <td><input type="hidden" name="appliactionid"  value="${sessionScope.SessionObject.debitDetailsBean.applicationId}"/></td>
                                                </tr>

                                            </table>

                                        </form>
                                        <table cellpadding="0" cellspacing="10" >

                                            <tr>
                                                <td style="width: 200px;">

                                                </td>
                                                <td><input type="button" name="reject"  style="width: 100px;" onclick="updateReject()" value="Reject">
                                                    <input type="button" name="verify"  style="width: 100px;" onclick="updateVerify()" value="Verify">
                                                    <input type="button" name="cancel"  style="width: 100px;" onclick="invokeBack()" value="Cancel">
                                                </td>
                                            </tr>

                                        </table>

                                    </div>


                                </div>   
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
