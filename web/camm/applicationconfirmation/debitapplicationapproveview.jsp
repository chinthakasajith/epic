<%-- 
    Document   : debitapplicationapproveview
    Created on : Sep 3, 2012, 1:52:05 PM
    Author     : badrika
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
            
            function LoadDebitBinProfiles(cdtype){
                
                $('#binProfile').empty();
                
                var ctyp=cdtype;
                var sval=$("#productionMode").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadDebitBinProfilesServlet",      
                { 
                    promode : sval,                
                    ctyp : ctyp
                },
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#binProfile').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
            
            function LoadDebitBinProfilesnew(cdtype){
                document.confirmForm.action="${pageContext.request.contextPath}/LoadDebitBinProfilesServlet?ctyp="+cdtype;
                document.confirmForm.submit();
               
              
            }
            
            function loadDebitCardTempList(){
             
        
                $('#cardtemplate').empty();             
                
                var pVal = $("#confirmCardProduct").val();                
                var aVal = $("#accountTemplates").val();
                
        
                $.getJSON("${pageContext.servletContext.contextPath}/LoadDebitCardTemplatesServlet", 
                {                     
                    aValue : aVal,                    
                    pValue : pVal
                },
                
                function(jsonobject) {
                    
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#cardtemplate').append(
                        $('<option></option>').val(code).html(description)
                    );  
                        
                    });
                });
              
            }
            
            function resetConfirmation(){
                              
                $("#confirmCardProduct").val("");
                $("#productionMode").val("");
                $("#binProfile").val("");                
                $("#accountTemplates").val("");
                $("#cardtemplate").val("");
              
            }
            
            function resetReject(){
              
                $("#remark").val("");
                $("#rejectReason").val("");
              
            }
            
            function Reject(applicationid)
            {
                answer = confirm("Are you sure you want to Reject this application?")
                if (answer !=0)
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/DebitApplicationRejectServlet?applicationid="+applicationid;
                }
                else
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/LoadDebitApproveDetailsServlet?applicationid="+applicationid;
                }
               
            }
            
            function Confirm(applicationid)
            {
                answer = confirm("Are you sure you want to confirm this application?")
                if (answer !=0)
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/DebitConfirmServlet?applicationid="+applicationid;
                }
                else
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/LoadDebitApproveDetailsServlet?applicationid="+applicationid;
                }
               
            }
            
            function disable(){
                document.confirmForm.getElementById('loyaltycard').disable = 'false'; 
            
            }
            
            function changeBinProfile(){
                
                $('#binProfile').empty();
                
                var productCode=$("#confirmCardProduct").val()
                $.getJSON("${pageContext.servletContext.contextPath}/ChangeDebitApproveDetailsServlet",      
                { 
                    productCode : productCode            
                },
                function(jsonobject) {
                    $("#binProfile").append("<option value=''>---------SELECT----------</option>");                     
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#binProfile').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }

            function changeProductionMode() {
                
                $('#productionMode').empty();
                
                var productCode=$("#confirmCardProduct").val()
                var binProfile = $("#binProfile").val()
                $.getJSON("${pageContext.servletContext.contextPath}/ChangeProductionModeServlet",      
                { 
                    productCode_ : productCode,   
                    binProfile_  : binProfile
                },
                function(jsonobject) {
                    $("#productionMode").append("<option value=''>---------SELECT----------</option>");                     
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#productionMode').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
            
            function ResetCardTemplt(){
                $("#accountTemplates").val("");
                $("#cardtemplate").val("");
            
            }
            
        </script>

        <title>EPIC CMS APPLICATION CONFIRMATION</title>

    </head>

    <body>



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

                                <table class="tit"> <tr> <td   class="center">  DEBIT CARD APPLICATION CONFIRMATION </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000" id="error"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>




                                <!--                                <form name="confirmForm" action="" method="POST">-->
                                <table cellpadding="0" cellspacing="10">
                                    <tr>
                                        <td style="width: 200px;"> Application ID</td>
                                        <td><b><font color="#000000"> ${cardApplicationList.applicationId}</font></b> </td>

                                        <td style="width: 240px;"></td>

                                        <td style="width: 200px;"> Application Type</td>
                                        <td><b><font color="#000000"> ${cardApplicationList.appTypeDes}</font></b> </td>
                                    </tr>


                                </table>
                                <br/>
                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Personal Information </a></li>
                                        <li><a href="#tabs-2">Contact Information</a></li>                                        

                                    </ul>            

                                    <div id="tabs-1" >   
                                        <table cellpadding="0" cellspacing="10" style="height: 200px;" >


                                            <tr>
                                                <td style="width: 200px;"> Identification Type </td>                                       

                                                <td>
                                                    <b><font color="#000000"> ${cardApplicationList.identificationType}</font></b>
                                                </td>

                                                <td style="width: 100px;"></td>

                                                <td style="width: 200px;">  Identification Number   </td>
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

                                                <td style="width: 200px;">Name on the Card</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.nameOnCard}</font></b>
                                                </td>

                                            </tr>


                                            <tr>
                                                <td style="width: 200px;">First Name</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.firstName}</font></b>
                                                </td>                                               

                                                <td style="width: 100px;"></td> 

                                                <td style="width: 200px;">Date of Birth</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.dateOfBirth}</font></b>
                                                </td>


                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Last Name</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.lastName}</font></b>
                                                </td>                                             

                                                <td style="width: 100px;"></td> 
                                                <td style="width: 200px;">Requested Card Type</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.reqCardTypeDes}</font></b>
                                                </td>


                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Requested Card Product</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.cardProductDes}</font></b>
                                                </td>                                             

                                                <td style="width: 100px;"></td> 
                                                <td style="width: 200px;"></td>
                                                <td>

                                                </td>


                                            </tr>


                                        </table>
                                    </div>


                                    <div id="tabs-2" >
                                        <table cellpadding="0" cellspacing="10" style="height: 200px;" >    


                                            <tr>
                                                <td style="width: 200px;">Address </td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address1}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td>

                                                <td style="width: 200px;"> Res. Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.homeTele}</font></b>
                                                </td>
                                            </tr>               
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address2}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td>

                                                <td style="width: 200px;"> Office Phone No</td>    
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.officeTele}</font></b>
                                                </td>
                                            </tr> 
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.address3}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td>

                                                <td style="width: 200px;"> Mobile Phone No</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.mobileTele}</font></b>
                                                </td>
                                            </tr>
                                            <tr>

                                                <td style="width: 200px;">Area</td>
                                                <td>
                                                    <b><font color="#000000"> ${personalDetail.areaDes}</font></b>
                                                </td>
                                                <td style="width: 100px;"></td>


                                            </tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>

                                        </table>
                                    </div>  


                                </div> 

                                <!--                                <iframe style="width: 0px; height: 0px; visibility: hidden;" name="downloadform" src="#"></iframe>-->

                                <form name="confirmForm" action="" method="POST">
                                    <div class="outset" style="border-style:outset; background-color: #B8B8B8 ;  border-color: #999; width: 100%">

                                        <table cellpadding="0" cellspacing="10"  >      
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Confirm</font></td>
                                            </tr>
                                            <!--                                    <form name="acceptForm" action="" method="POST" >-->


                                            <tr>
                                                <td style="width: 200px;">Card Product</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="confirmCardProduct" name="cardProduct" class="inputfield-mandatory" onchange="changeBinProfile()">
                                                        <option value="" style="width: 100px;" selected>--SELECT--</option>
                                                        <c:forEach var="card" items="${cardProductList}">                                                            
                                                            <c:if test="${confirmBean.cdProduct==card.cardProductCode}">
                                                                <option value="${card.cardProductCode}" selected="">${card.cardproductDescription}</option>

                                                            </c:if>
                                                            <c:if test="${confirmBean.cdProduct!=card.cardProductCode}">
                                                                <option value="${card.cardProductCode}">${card.cardproductDescription}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>
                                                    </td>
                                            </tr>

                                            <tr>
                                                <td><input type="hidden" name="redirectValue" value="add" id="redirectValue" /></td>
                                                <td></td>
                                                <td></td>
                                            </tr>

                                            </td> 
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Bin Profile</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="binProfile" name="binProfile"  class="inputfield-mandatory" onchange="changeProductionMode()">
                                                        <option value="" selected>--SELECT--</option>
                                                    </select>   

                                                </td> 
                                            </tr>                                            

                                            <tr>
                                                <td style="width: 200px;">Production Mode</td>
                                                <td><font style="color: red;">*</font></td>

                                                <td>
                                                    <select id="productionMode" name="productionMode"  class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>
                                                    </select>   

                                                </td> 
                                            </tr>                                            

                                            <tr>
                                                <td style="width: 200px;">Account Template</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="accountTemplates" name="accountTemplates"  onchange="loadDebitCardTempList()" class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="accTemp" items="${accountTempList}">

                                                            <c:if test="${confirmBean.accTemplt==accTemp.templateCode}">
                                                                <option value="${accTemp.templateCode}" selected>${accTemp.templateName}</option> 
                                                            </c:if>
                                                            <c:if test="${confirmBean.accTemplt!=accTemp.templateCode}">
                                                                <option value="${accTemp.templateCode}" >${accTemp.templateName}</option>

                                                            </c:if>


                                                        </c:forEach>


                                                    </select>

                                                </td> 
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Card Template</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="cardtemplate" name="cardtemplate"  class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>
                                                    </select>

                                                </td>                                                                                                
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Loyalty Card</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="loyaltycard" name="loyaltycard"  class="inputfield-mandatory" >
                                                        <!--                                                        <option value="" selected>--SELECT--</option>-->

                                                        <c:if test="${personalDetail.loyaltyReq=='NO'}">
                                                            <option value="NO" selected >NO</option> 
                                                        </c:if>


                                                        <c:if test="${personalDetail.loyaltyReq!='NO' && confirmBean.loyalty=='YES'}">
                                                            <option value="YES" selected >YES</option>
                                                        </c:if>
                                                        <c:if test="${personalDetail.loyaltyReq!='NO' && confirmBean.loyalty!='YES'}">
                                                            <option value="YES" >YES</option>
                                                        </c:if>
                                                        <c:if test="${personalDetail.loyaltyReq!='NO' && confirmBean.loyalty=='NO'}">
                                                            <option value="NO" selected >NO</option> 
                                                        </c:if>
                                                        <c:if test="${personalDetail.loyaltyReq!='NO' && confirmBean.loyalty!='NO'}">
                                                            <option value="NO" >NO</option> 
                                                        </c:if>

                                                    </select>

                                                </td> 
                                            </tr>

                                            <tr>

                                                <td>
                                                    <table>

                                                        <td><input type="submit" value="Confirm"  onclick="Confirm('${cardApplicationList.applicationId}')" style="width: 100px"></input></td>
                                                        <td><input type="button" value="Reset" style="width: 100px" onclick="resetConfirmation()"></input></td>

                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <!--                                </form>-->
                                    <!--                                        <tr>
                                                                                <td colspan="6" ><hr /></td>
                                                                            </tr>-->
                                    <br/>
                                    <div class="outset"    width: 100%">

                                         <table cellpadding="0" cellspacing="10"  >     
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Reject</font></td>
                                            </tr>

                                            <!--                                    <form name="rejectForm" action="" method="POST">-->
                                            <tr>
                                                <td style="width: 200px;">Reject Reason</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="rejectReason" name="rejectReason"  class="inputfield-mandatory">
                                                        <option value="" style="width: 100px;" selected>--SELECT--</option>
                                                        <c:forEach var="reject" items="${rejectReasons}">

                                                            <c:if test="${confirmBean.rejReason==reject.reasonCode}">
                                                                <option value="${reject.reasonCode}" >${reject.description}</option>
                                                            </c:if>
                                                            <c:if test="${confirmBean.rejReason!=reject.reasonCode}">
                                                                <option value="${reject.reasonCode}">${reject.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td> 
                                            </tr>
                                            <tr>
                                            <tr>
                                                <td style="width: 200px;">Remarks</td>
                                                <td></td>
                                                <td> <TEXTAREA id="remark" NAME="remark" ROWS="3" style="width: 350px;" ></TEXTAREA></td>
                                            </tr>


                                            </tr>
                                            <tr>

                                                <td>
                                                    <table>

                                                        <td><input type="submit" value="Reject" name="next" style="width: 100px" onclick="Reject('${cardApplicationList.applicationId}')"></input></td>
                                                        <td><input type="button" value="Reset" name="next" style="width: 100px" onclick="resetReject()"></input></td>
                                                        </form>
                                                    </table>
                                                </td>
                                            </tr>
                                            <script>
                                                LoadDebitBinProfiles('${personalDetail.reqCardType}');
                                                loadDebitCardTempList();                                                
                                            </script>
                                        </table>

                                    </div>

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
