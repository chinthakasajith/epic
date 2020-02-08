<%-- 
    Document   : corporateassignhome
    Created on : May 9, 2013, 11:41:21 AM
    Author     : asela
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            
            

            function invokeAdd()
            {
                document.addappassignform.action="${pageContext.request.contextPath}/AddCorporateAssignServlet";
                document.addappassignform.submit();

            }
            function invokeConfirm()
            {

                document.addappassignform.action="${pageContext.request.contextPath}/ConfirmCorporateDataServlet";
                document.addappassignform.submit();

            }
            
            function invokeUpdate()
            {

                document.updateappassignform.action="${pageContext.request.contextPath}/UpdateCorporateDataServlet";
                document.updateappassignform.submit();

            }
            
            function updateReset(value)
            {

                window.location = "${pageContext.request.contextPath}/LoadUpdateCorporateFormServlet?applicationid="+value;

            }
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCorporateAssignServlet";

            }

            function invokeUpdateReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCorporateDataSearchServlet";

            }

            function LoadIdentificationTypes(){
                $('#identificationType').attr("disabled", false);
                $('#identificationType').empty();

                var cardcategory = $('input[name=cardcategory]:checked').val();                 

                $.getJSON("${pageContext.servletContext.contextPath}/LoadIdentificationTypeServlet", 
                { cardcategory : cardcategory },
                function(jsonobject) {
                    $("#identificationType").append("<option value=''>---------SELECT----------</option>");                   
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#identificationType').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
            }             
    

            function onloadChanges(){
            if($("#identificationType").val() == ""){
                $('#identificationType').attr("disabled", true);
            }
            }
        </script>
        <script>
            function settitle(){
                   
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CORPORATEASSIGN%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                             
        </script>


        <title>EPIC CMS CARD APPLICATION ASSIGN</title>
    </head>
    <body onload="onloadChanges()">

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

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>


                                <c:if test="${operationtype=='add'}">

                                    <form method="POST" action="" name="addappassignform">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                            </tr>
                                        </table>


                                        <table>

                                            <c:if test="${assignappbean.cardCategory !=null}">
                                                <tr class="cardcategory">
                                                    <td style="width: 180px">Application Type </td>

                                                    <c:if test="${assignappbean.cardCategory =='E'}"><td style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: red;">*</font>&nbsp;<input  type="radio" name="cardcategory" id="cardcategory" checked="true" value="E" onclick="LoadIdentificationTypes()" style="width: 25px"> Establishment</td></c:if><c:if test="${assignappbean.cardCategory !='E'}"> <td style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: red;">*</font>&nbsp;<input type="radio" name="cardcategory" id="cardcategory" value="E" onclick="LoadIdentificationTypes()" style="width: 25px"/>Establishment</td></c:if>
                                                    <c:if test="${assignappbean.cardCategory =='C'}"><td style="width: 180px"><input  type="radio" name="cardcategory" id="cardcategory" checked="true" value="C" onclick="LoadIdentificationTypes()" style="width: 25px"> Corporate</td></c:if><c:if test="${assignappbean.cardCategory !='C'}"> <td style="width: 180px"><input type="radio" name="cardcategory" id="cardcategory" value="C" onclick="LoadIdentificationTypes()" style="width: 25px" />Corporate</td></c:if>
                                                </tr>
                                            </c:if>

                                            <c:if test="${assignappbean.cardCategory ==null}">
                                                <tr class="cardcategory">
                                                    <td style="width: 180px">Application Type </td>

                                                    <td style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: red;">*</font>&nbsp;<input  type="radio" name="cardcategory" id="cardcategory" value="E" onclick="LoadIdentificationTypes()" style="width: 25px"> Establishment</td>
                                                    <td style="width: 180px"><input  type="radio" name="cardcategory" id="cardcategory" value="C" onclick="LoadIdentificationTypes()" style="width: 25px"> Corporate</td>
                                                </tr>
                                            </c:if>









                                            <tr><td style="height: 5px"></td></tr>  
                                        </table>

                                        <table border="0">

                                            <tbody>
                                                <tr>

                                                    <!--                                                <tr>
                                                                                                        <td>Identification Type </td>
                                                                                                        <td><font style="color: red;">*</font>&nbsp;
                                                                                                            <select name="identityType">
                                                                                                                <option value="" >--SELECT--</option>
                                                    
                                                    <c:forEach var="identity" items="${identificationList}">
                                                        <c:if test="${assignappbean.identityType==identity.key}">
                                                            <option value="${identity.key}" selected>${identity.value}</option>
                                                        </c:if>
                                                        <c:if test="${assignappbean.identityType != identity.key}">
                                                            <option value="${identity.key}" >${identity.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td></td>
                                        </tr>-->


                                                    <!--                                                <tr>
                                                                                                        <td style="width: 200px;">Identification Type </td>
                                                                                                        <td><font style="color: red;">*</font>&nbsp;
                                                                                                            <select id="identificationType" name="identificationType"  >
                                                                                                                <option value="" selected>-------------SELECT-------------</option>
                                                                                                            </select>
                                                    
                                                                                                        </td> 
                                                                                                    </tr>  -->

                                                <tr>
                                                    <td style="width: 200px;">Identification Type </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select id="identificationType" name="identificationType"  >
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                            <c:forEach var="identity" items="${identificationList}">
                                                                <c:if test="${assignappbean.identityType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.identityType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>                                                        
                                                        </select>

                                                    </td> 
                                                </tr> 

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Identification Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;&nbsp;<input  type="text"  value="${assignappbean.identityNo}" name="identityno" maxlength="16"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Priority Level </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="prioritycode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${assignappbean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td style="width: 180px">Referral Employee Number</td>
                                                    <td><font>&nbsp;&nbsp;&nbsp;</font>&nbsp;<input  type="text"  value="${assignappbean.referralEmpNo}" name="referemloyeeno" maxlength="16"></td>
                                                    <td></td>
                                                </tr>


                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Referral Branch ID </td>
                                                    <td><font>&nbsp;&nbsp;</font>&nbsp;
                                                        <select  name="referbranchcode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="branch" items="${branchesDeatilsList}">
                                                                <c:if test="${assignappbean.referralBranchId==branch.key}">
                                                                    <option value="${branch.key}" selected>${branch.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.referralBranchId!=branch.key}">
                                                                    <option value="${branch.key}" >${branch.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Assign User </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="assignuser">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="user" items="${usersList}">
                                                                <c:if test="${assignappbean.assignUser==user}">
                                                                    <option value="${user}" selected>${user}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.assignUser!=user}">
                                                                    <option value="${user}" >${user}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>



                                                <tr> <td style="height:12px;"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px"><font style="color: red;">&nbsp;&nbsp;&nbsp;</font>&nbsp;<input type="button" value="Add" name="add" style="width: 100px" onclick="invokeAdd()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CAMMDATAASSIGN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                </tr>

                                                <tr><td style="height: 10px"></td></tr>     
                                            </tbody>
                                        </table>
                                    </form>


                                    <%-- -------------------------add form end -------------------------------- --%>


                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Assign User </th>
                                                <th>Assigned Applications</th>
                                                <th>Pending Applications</th>



                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="userapps" items="${userAssignApps}">
                                                <tr>
                                                    <td>${userapps.userName}</td>
                                                    <td>${userapps.assignedApps}</td>
                                                    <td>${userapps.pendingApps}</td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>







                                <c:if test="${operationtype=='confirm'}">

                                    <form method="POST" action="" name="addappassignform">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>



                                        <table>

                                            <c:if test="${assignappbean.cardCategory !=null}">
                                                <tr class="cardcategory">
                                                    <td style="width: 180px">Application Type </td>

                                                    <c:if test="${assignappbean.cardCategory =='E'}"><td style="width: 180px"><font style="color: red;">*</font>&nbsp;<input  type="radio" name="cardcategory" checked="true" value="E" onclick="LoadIdentificationTypes()" style="width: 25px"> Establishment</td></c:if><c:if test="${assignappbean.cardCategory !='E'}"> <td style="width: 180px"><font style="color: red;">*</font>&nbsp;<input type="radio" name="cardcategory" value="E" onclick="LoadIdentificationTypes()" style="width: 25px"/>Establishment</td></c:if>
                                                    <c:if test="${assignappbean.cardCategory =='C'}"><td style="width: 180px"><input  type="radio" name="cardcategory" checked="true" value="C" onclick="LoadIdentificationTypes()"style="width: 25px"> Corporate</td></c:if><c:if test="${assignappbean.cardCategory !='C'}"> <td style="width: 180px"><input type="radio" name="cardcategory" value="C" onclick="LoadIdentificationTypes()" style="width: 25px" />Corporate</td></c:if>
                                                </tr>
                                            </c:if>

                                            <c:if test="${assignappbean.cardCategory ==null}">
                                                <tr class="cardcategory">
                                                    <td style="width: 180px">Application Type </td>

                                                    <td style="width: 180px"><font style="color: red;">*</font>&nbsp;<input  type="radio" name="cardcategory"  value="E" onclick="LoadIdentificationTypes()" style="width: 25px"> Establishment</td>
                                                    <td style="width: 180px"><input  type="radio" name="cardcategory"  value="C" onclick="LoadIdentificationTypes()" style="width: 25px"> Corporate</td>
                                                </tr>
                                            </c:if>



                                            <tr><td style="height: 5px"></td></tr>  
                                        </table>

                                        <table border="0">

                                            <tbody>


                                                <!--                                                <tr>
                                                                                                    <td>Identification Type </td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;
                                                                                                        <select name="identityType">
                                                
                                                
                                                <c:forEach var="identity" items="${identificationList}">
                                                    <c:if test="${assignappbean.identityType==identity.key}">
                                                        <option value="${identity.key}" selected>${identity.value}</option>
                                                    </c:if>

                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td></td>
                                    </tr>-->
                                                <tr>
                                                    <td style="width: 200px;">Identification Type </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select id="identificationType" name="identificationType"  >
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                            <c:forEach var="identity" items="${identificationList}">
                                                                <c:if test="${assignappbean.identityType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.identityType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>                                                        
                                                        </select>

                                                    </td> 
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Identification Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;&nbsp;<input  type="text"  readonly="true" value="${assignappbean.identityNo}" name="identityno" maxlength="16"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Priority Level </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="prioritycode">


                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${assignappbean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td style="width: 180px">Referral Employee Number</td>
                                                    <td><font>&nbsp;&nbsp;&nbsp;</font>&nbsp;<input  type="text"  readonly="true" value="${assignappbean.referralEmpNo}" name="referemloyeeno" maxlength="16"></td>
                                                    <td></td>
                                                </tr>


                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Referral Branch ID </td>
                                                    <td><font>&nbsp;&nbsp;</font>&nbsp;
                                                        <select  name="referbranchcode">

                                                            <c:if test="${assignappbean.referralBranchId==null || assignappbean.referralBranchId==''}">
                                                                <option value="" >--SELECT--</option>
                                                            </c:if>

                                                            <c:forEach var="branch" items="${branchesDeatilsList}">
                                                                <c:if test="${assignappbean.referralBranchId==branch.key}">
                                                                    <option value="${branch.key}" selected>${branch.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Assign User </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="assignuser">


                                                            <c:forEach var="user" items="${usersList}">
                                                                <c:if test="${assignappbean.assignUser==user}">
                                                                    <option value="${user}" selected>${user}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>



                                                <tr> <td style="height:12px;"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px"><input type="button" value="Confirm" name="Confirm" style="width: 100px" onclick="invokeConfirm()">
                                                        <input type="button" value="No" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CORPORATEASSIGN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                </tr>

                                                <tr><td style="height: 10px"></td></tr>     
                                            </tbody>
                                        </table>
                                    </form>


                                    <%-- -------------------------add form end -------------------------------- --%>

                                    <c:if test="${previousApplicationList != null}">
                                        <table  border="1"  class="display" id="tableview2">
                                            <thead>
                                                <tr>
                                                    <th>Application ID </th>
                                                    <th>Identification Type</th>
                                                    <th>Identification Number</th>
                                                    <th>Priority Level</th>
                                                    <th>Employee Number</th>
                                                    <th>Assign User</th>
                                                    <th>Assign Status</th>
                                                    <th>Application Type</th>


                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="assignapp" items="${previousApplicationList}">
                                                    <tr>
                                                        <td >${assignapp.applicationId}</td>
                                                        <td >${assignapp.identityType}</td>
                                                        <td >${assignapp.identityNo}</td>
                                                        <td >${assignapp.priorityDescription}</td>
                                                        <td >${assignapp.referralEmpNo}</td>
                                                        <td >${assignapp.assignUser}</td>
                                                        <td >${assignapp.assignStatusDescription}</td>
                                                        <td >${assignapp.cardCategoryDes}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                        <table><tr style="height: 20px"></tr></table>
                                    </c:if>


                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Assign User </th>
                                                <th>Assigned Applications</th>
                                                <th>Pending Applications</th>



                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="userapps" items="${userAssignApps}">
                                                <tr>
                                                    <td>${userapps.userName}</td>
                                                    <td>${userapps.assignedApps}</td>
                                                    <td>${userapps.pendingApps}</td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </c:if>
















                                <c:if test="${operationtype=='update'}">

                                    <form method="POST" action="" name="updateappassignform">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>

                                        <table>

                                            <c:if test="${assignappbean.cardCategory !=null}">
                                                <tr class="cardcategory">
                                                    <td style="width: 180px">Application Type </td>

                                                    <c:if test="${assignappbean.cardCategory =='E'}"><td style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: red;">*</font>&nbsp;<input  type="radio" name="cardcategory" checked="true" value="E" onclick="LoadIdentificationTypes()" style="width: 25px"> Establishment</td></c:if><c:if test="${assignappbean.cardCategory !='E'}"> <td style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: red;">*</font>&nbsp;<input type="radio" name="cardcategory" onclick="LoadIdentificationTypes()" value="E" style="width: 25px"/>Establishment</td></c:if>
                                                    <c:if test="${assignappbean.cardCategory =='C'}"><td style="width: 180px"><input  type="radio" name="cardcategory" checked="true" value="C" onclick="LoadIdentificationTypes()" style="width: 25px"> Corporate</td></c:if><c:if test="${assignappbean.cardCategory !='C'}"> <td style="width: 180px"><input type="radio" name="cardcategory" value="C" onclick="LoadIdentificationTypes()" style="width: 25px" />Corporate</td></c:if>
                                                </tr>
                                            </c:if>

                                            <c:if test="${assignappbean.cardCategory ==null}">
                                                <tr class="cardcategory">
                                                    <td style="width: 180px">Application Type </td>

                                                    <td style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;<font style="color: red;">*</font>&nbsp;<input  type="radio" name="cardcategory"  value="E" style="width: 25px"> Establishment</td>
                                                    <td style="width: 180px"><input  type="radio" name="cardcategory"  value="C" style="width: 25px"> Corporate</td>
                                                </tr>
                                            </c:if>



                                            <tr><td style="height: 5px"></td></tr>  
                                        </table>


                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td>Application ID</td>
                                                    <td><font >&nbsp;&nbsp;&nbsp;</font>&nbsp;<input type="text"  readonly="true "value="${assignappbean.applicationId}" name="appliactionid" maxlength="16"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     

                                                <!--                                                <tr>
                                                                                                    <td>Identification Type </td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;
                                                                                                        <select name="identityType">
                                                                                                            <option value="" >--SELECT--</option>
                                                
                                                <c:forEach var="identity" items="${identificationList}">
                                                    <c:if test="${assignappbean.identityType==identity.key}">
                                                        <option value="${identity.key}" selected>${identity.value}</option>
                                                    </c:if>
                                                    <c:if test="${assignappbean.identityType != identity.key}">
                                                        <option value="${identity.key}" >${identity.value}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td></td>
                                    </tr>-->
                                                <tr>
                                                    <td style="width: 200px;">Identification Type </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select id="identificationType" name="identificationType"  >
                                                            <option value="" selected>-------------SELECT-------------</option>
                                                            <c:forEach var="identity" items="${identificationList}">
                                                                <c:if test="${assignappbean.identityType==identity.key}">
                                                                    <option value="${identity.key}" selected>${identity.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.identityType != identity.key}">
                                                                    <option value="${identity.key}" >${identity.value}</option>
                                                                </c:if>
                                                            </c:forEach>                                                        
                                                        </select>

                                                    </td> 
                                                </tr>   
                                                <tr><td style="height: 5px"></td></tr>     

                                                <tr>
                                                    <td>Identification Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;&nbsp;<input type="text"  value="${assignappbean.identityNo}" name="identityno" maxlength="16"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     


                                                <tr>
                                                    <td>Priority Level </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="prioritycode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${assignappbean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     


                                                <tr>
                                                    <td style="width: 180px">Referral Employee Number</td>
                                                    <td><font >&nbsp;&nbsp;&nbsp;</font>&nbsp;<input type="text"  value="${assignappbean.referralEmpNo}" name="referemloyeeno" maxlength="16"></td>
                                                    <td></td>
                                                </tr>


                                                <tr><td style="height: 5px"></td></tr>     


                                                <tr>
                                                    <td>Referral branch ID </td>
                                                    <td><font >&nbsp;&nbsp;</font>&nbsp;
                                                        <select  name="referbranchcode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="branch" items="${branchesDeatilsList}">
                                                                <c:if test="${assignappbean.referralBranchId==branch.key}">
                                                                    <option value="${branch.key}" selected>${branch.value}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.referralBranchId!=branch.key}">
                                                                    <option value="${branch.key}" >${branch.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     


                                                <tr>
                                                    <td>Assign User </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="assignuser">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="user" items="${usersList}">
                                                                <c:if test="${assignappbean.assignUser==user}">
                                                                    <option value="${user}" selected>${user}</option>
                                                                </c:if>
                                                                <c:if test="${assignappbean.assignUser!=user}">
                                                                    <option value="${user}" >${user}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>     


                                                <tr> <td style="height:12px;"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font></font>&nbsp;&nbsp;&nbsp;<input type="button" value="Update" name="update" style="width: 100px" onclick="invokeUpdate()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="updateReset('${assignappbean.applicationId}')">
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 10px"></td></tr>  
                                            </tbody>
                                        </table>
                                    </form>






                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Application ID </th>
                                                <th>Identification Type</th>
                                                <th>Identification Number</th>
                                                <th>Priority Level</th>
                                                <th>Employee Number</th>
                                                <th>Assign User</th>
                                                <th>Assign Status</th>
                                                <th>Application Domain</th>
                                                <th>Application Type</th>
                                                <th>Edit</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="assignapp" items="${searchList}">
                                                <tr>
                                                    <td >${assignapp.applicationId}</td>
                                                    <td >${assignapp.identityType}</td>
                                                    <td >${assignapp.identityNo}</td>
                                                    <td >${assignapp.priorityDescription}</td>
                                                    <td >${assignapp.referralEmpNo}</td>
                                                    <td >${assignapp.assignUser}</td>
                                                    <td >${assignapp.assignStatusDescription}</td>
                                                    <td >${assignapp.applicationDomainDes}</td>
                                                    <td >${assignapp.cardCategoryDes}</td>
                                                    <td  ><a href='${pageContext.request.contextPath}/LoadUpdateCorporateFormServlet?applicationid=${assignapp.applicationId}'>Edit</a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>



                                </c:if>















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


