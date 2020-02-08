<%-- 
    Document   : usermanagement
    Created on : Jan 18, 2012, 8:34:37 AM
    Author     : janaka_hennadi
--%>


<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.system.util.variable.StatusVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mainstyle.css" media="screen"/>


        <script >
            function invokeCancel() {

                window.location = "${pageContext.request.contextPath}/LoadSystemUserServlet";
            }
            function invokeReset(reset) {
                window.location = "${pageContext.request.contextPath}/ManageSystemUserServlet?operation=" + reset;
            }

        </script>

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.SYSTEMUSER%>'
                        },
                function(data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

            $(document).ready(function() {
                $('input[name=gender]').click(function() {
                    return false;
                });
                $('input[name=title]').change(function() {
                    var val = $('input[name=title]:checked').val();
                    //alert(val);
                    if (val === "MRS" || val === "MS") {
                        $('input[name=gender][value=FEMALE]').attr('checked', 'checked');
                    }
                    if (val === "MR") {
                        $('input[name=gender][value=MALE]').attr('checked', 'checked');
                    }
                });

                //if this is  a request this will change all the input to readonly
                if ('${userBeans[0].statusCode}' === '<%= StatusVarList.DA_REQUSET_INITIATE%>' || '${userBeans[0].statusCode}' === '<%= StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT%>') {

                    //set all text fields to readonly
                    setAllTextInputToReadonly();

                    //remove readonly from reject remark text field
                    var rejectRemarkField = document.getElementById('rejectremark');
                    rejectRemarkField.readOnly = false;

                    //put every drop down select to readonly
                    setReadonly();

                    //disable datepickers
                    $("#expdate").prop("readonly", true).next("button").prop("disabled", true);

                }
                if ('${userBeans[0].statusCode}' === 'ACT' || '${userBeans[0].statusCode}' === 'DEA') {
                    //load dual auth user roles according to selected user role
                    loadAssignUsersForEdit(document.getElementById("userroal").value, document.getElementById("dualUserroal").value);


                }

            });

            function loadAssignUsers(userRole) {
                //alert(userRole);
                $('#dualUserroal').empty();
                $.getJSON("${pageContext.request.contextPath}/LoadDualAuthUserRoleServlet",
                        {
                            userrole: userRole
                        },
                function(jsonobject) {
                    //alert(jsonobject);
                    $("#dualUserroal").append("<option value=''>--Select User Role-- </option>");


                    $.each(jsonobject.dualAuthRole, function(code, description) {
                        //alert(description);
                        $("#dualUserroal").append(
                                $('<option></option>').val(code).html(description)
                                );
                        //assignusers.push(description);
                    });

                });

            }

            function loadAssignUsersForEdit(userRole, dualAuthUserRole) {
                //alert(userRole);
                $('#dualUserroal').empty();
                $("#dualUserroal").append("<option value=''>--Select User Role-- </option>");
                if (userRole != null) {
                    $.getJSON("${pageContext.request.contextPath}/LoadDualAuthUserRoleServlet",
                            {
                                userrole: userRole
                            },
                    function(jsonobject) {
                        //alert(jsonobject);

                        $.each(jsonobject.dualAuthRole, function(code, description) {
                            //alert(description);
                            if (dualAuthUserRole != null) {
                                if (code === dualAuthUserRole) {
                                    $("#dualUserroal").append(
                                            $('<option selected></option>').val(code).html(description)
                                            );
                                } else {
                                    $("#dualUserroal").append(
                                            $('<option></option>').val(code).html(description)
                                            );
                                }
                            } else {
                                $("#dualUserroal").append(
                                        $('<option></option>').val(code).html(description)
                                        );
                            }

                        });

                    });
                }

            }

        </script>

        <style type="text/css">
            .inputtrue
            {   
                background-color: #7ec2f7;  

            }

            .inputtrue[type="radio"]:checked:before {
                content: "";
                display: block;
                position: relative;
                top: 3px;
                left: 3px;
                width: 6px;
                height: 6px;
                border-radius: 50%;
                background: #7ec2f7;
            }
        </style>

        <title>CMS USER MANAGEMENT</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                        <td><font color="green"><b> ${successMsg}</b></font> </td>
                                    </tr>
                                </table>


                                <!--   ------------------------- start operation add area  --------------------------------                      -->
                                <c:if test="${operationtype=='add'}">
                                    <div class="formarea">
                                        <form name="adduserform" method="post" action="<%=request.getContextPath()%>/AddSystemUserServlet">






                                            <div class="subsectionheader" > Credential Details </div>
                                            <table class="formtable"  >  


                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Username</td>
                                                    <td class="inputsmall"><input type="text" name="username"  maxlength="32" value="${userBean.userName}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.userName} </label></td>

                                                </tr>
                                                <!--disabled this after generation random password-->
                                                <!--                                                <tr>
                                                                                                    <td class="labelsmall"><span class="required">*</span>Password</td>
                                                                                                    <td><input type="password" name="password"  maxlength="100" ></td>
                                                                                                    <td  > <label style="color: red">
                                                <%--${invalidMsgBean.password}--%>
                                                </label>
                                        </td>
                                    </tr>-->
                                                <!--                                                <tr>
                                                                                                    <td class="labelsmall"><span class="required">*</span>Confirm Password</td>
                                                                                                    <td><input type="password" name="confirmPassword"  maxlength="100" ></td>
                                                                                                    <td  > <label style="color: red">
                                                <%--${invalidMsgBean.confirmPassword}--%>
                                                </label>
                                        </td>
                                    </tr>-->
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>User Role</td>                                               
                                                    <td><select name="userroal" onchange="loadAssignUsers(this.value)">
                                                            <option value="">--Select User Role-- </option>
                                                            <c:if test="${userBean.userRoleCode != null}">
                                                                <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                    <c:if test="${userBean.userRoleCode == userRoleLst.userRoleCode}">
                                                                        <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBean.userRoleCode != userRoleLst.userRoleCode}">
                                                                    <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.userRoleCode} </label></td>

                                                </tr>

                                                <!--                                                <tr>
                                                                                                    <td class="labelsmall"><span class="required">*</span>Status</td>
                                                                                                    <td><select name="status">
                                                                                                            <option value="">--Select Status-- </option>
                                                <%--<c:if test="${userBean.statusCode != null}">
                                                    <c:forEach var="statusLst" items="${statusLst}">
                                                        <c:if test="${userBean.statusCode == statusLst.statusCode}">
                                                            <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                        </c:if>
                                                    </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="statusLst" items="${statusLst}">
                                                                <c:if test="${userBean.statusCode != statusLst.statusCode}">
                                                                    <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>--%>
                                                        </select></td>
                                                        <td  > <label style="color: red"><%--${invalidMsgBean.statusCode} --%></label></td>      
                                                </tr>-->
                                                <input type="hidden" id="status" name="status" value="<%= StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT%>"/>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Dual Auth User Role</td>
                                                    <td><select name="dualUserroal" id="dualUserroal">
                                                            <option value="">--Select User Role-- </option>
                                                            <c:if test="${userBean.dualUserRoleCode != null}">
                                                                <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                    <c:if test="${userBean.dualUserRoleCode == userRoleLst.userRoleCode}">
                                                                        <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBean.dualUserRoleCode != userRoleLst.userRoleCode}">
                                                                    <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                    <td > <label style="color: red">${invalidMsgBean.dualUserRoleCode} </label></td>
                                                </tr>
                                                <tr>
                                                    <td >&nbsp;</td>
                                                    <td></td>

                                                </tr>

                                            </table>
                                            <div class="subsectionheader" > Personal Details </div>
                                            <table class="formtable">




                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Title</td>
                                                    <c:if test="${userBean.title=='MR'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR" checked="true" >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS"  >&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS"  >
                                                        </td>
                                                    </c:if>

                                                    <c:if test="${userBean.title=='MRS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR"  >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS"  checked="true">&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS"  >
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBean.title=='MS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR"  >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS"  >&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS"  checked="true">
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBean.title !='MR' && userBean.title !='MRS' && userBean.title !='MS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR"  >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS"  >&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS"  >
                                                        </td>
                                                    </c:if>
                                                    <td style="width: 100px;" > <label style="color: red">${invalidMsgBean.title} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Initials</td>
                                                    <td class="inputsmall"><input type="text" name="initials"  maxlength="16" value="${userBean.initials}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.initials} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>First Name</td>
                                                    <td><input type="text" name="firstname"  maxlength="32" value="${userBean.firstName}"></td>
                                                    <td > <label style="color: red">${invalidMsgBean.firstName} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Last Name</td>
                                                    <td><input type="text" name="lastname"  maxlength="32" value="${userBean.lastName}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.lastName} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Bank Name </td>
                                                    <td><input type="text" name="bankname" readonly="true" maxlength="64" value="${userBean.bankname}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.bankname} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Branch Name</td>
                                                    <td><select name="branchname">
                                                            <option value="">--Select Branch-- </option>
                                                            <c:if test="${userBean.branchname != null}">
                                                                <c:forEach var="branchList" items="${branchList}">
                                                                    <c:if test="${userBean.branchname == branchList.branchCode}">
                                                                        <option value="${branchList.branchCode}" selected="true">${branchList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </c:if> 
                                                            <c:forEach var="branchList" items="${branchList}">
                                                                <c:if test="${userBean.branchname != branchList.branchCode}">
                                                                    <option value="${branchList.branchCode}">${branchList.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.branchname} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall">&nbsp;Designation</td>
                                                    <td><input type="text" name="designation"  maxlength="150" value="${userBean.designation}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.designation} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Service Id</td>
                                                    <td><input type="text" name="serviseid"  maxlength="100" value="${userBean.serviseid}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.serviseid} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Contact Number</td>
                                                    <td><input type="text" name="telephone"  maxlength="10" value="${userBean.telNo}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.telNo} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>NIC</td>
                                                    <td><input type="text" name="nic"  maxlength="12" value="${userBean.nic}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.nic} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Email Address</td>
                                                    <td><input type="text" name="email"  maxlength="100" value="${userBean.email}"></td>
                                                    <td  > <label style="color: red">${invalidMsgBean.email} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>Gender</td>
                                                    <c:if test="${userBean.gender =='MALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE" checked="true" >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" ></td>
                                                        </c:if>

                                                    <c:if test="${userBean.gender =='FEMALE' }">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" checked="true"></td>
                                                        </c:if>
                                                        <c:if test="${userBean.gender !='MALE' && userBean.gender !='FEMALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.gender} </label></td>
                                                </tr>
                                                <tr>
                                                    <td class="labelsmall">&nbsp;Date Of Birth</td>
                                                    <td>
                                                        <input  name="birthday" maxlength="16" readonly value="${param.birthday}" key="birthday" id="birthday"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#birthday").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1,
                                                                    maxDate: new Date()  
                                                                });
                                                            });
                                                        </script>

                                                    </td>
                                                    <td  > <label style="color: red">${invalidMsgBean.birthday} </label></td>

                                                </tr>
                                                <tr>
                                                    <td class="labelsmall"><span class="required">*</span>User Expiry Date</td>
                                                    <td>
                                                        <input  name="expdate" maxlength="16" readonly value="${param.expdate}" key="expdate" id="expdate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#expdate").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>

                                                    </td>
                                                    <td  > <label style="color: red">${invalidMsgBean.expiryDateToString} </label></td>
                                                </tr>




                                                <tr>
                                                    <td ><p>&nbsp;</p></td>
                                                    <td></td>

                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td class="labelsmall"></td>
                                                    <td class="largeinput"><input type="submit" name="next" value="Request Initiate"  class="buttonsarea"/>&nbsp;<input type="button" onClick="invokeReset('add')" name="next" value="Reset"   class="buttonsarea"/>&nbsp;<input type="button" name="cancel" value="Cancel" onClick="invokeCancel()"   class="buttonsarea"/></td>


                                                </tr>
                                            </table>

                                        </form>
                                    </div>        
                                </c:if>


                                <!--   ------------------------- end operation add  area  --------------------------------                      -->

                                <!--   ------------------------- start operation edit area  --------------------------------                      -->
                                <c:if test="${operationtype=='edit'}">
                                    <div class="formarea">
                                        <form name="edituserform" method="post" action="<%=request.getContextPath()%>/UpdateSystemUserServlet">
                                            <%
                                                pageContext.setAttribute("addStatus", StatusVarList.ADD_SYSTEMUSER_REQUEST_SENT);
                                                pageContext.setAttribute("editStatus", StatusVarList.DA_REQUSET_INITIATE);
                                            %>
                                            <table  class="formtable"  >
                                                <tr>
                                                    <td ><b><u>Credential Details</u></b></td>
                                                                <c:if test="${userBeans[1] != null}">
                                                        <td><b>New value</b></td>
                                                        <td><b>Old value</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr >
                                                    <td class="labelsmall"><span class="required">*</span>Username</td>
                                                    <td class="inputsmall">
                                                        <input type="text" name="username" readonly="" maxlength="32" value="${userBeans[0].userName}">
                                                    </td>
                                                    <%-- <c:if test="${userBeans[1] != null}">
                                                        <td class="inputsmall">
                                                            <input type="text" name="username1" maxlength="32" value="${userBeans[1].userName}">
                                                        </td>
                                                    </c:if> --%>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>User Role</td>                                               
                                                    <td>
                                                        <c:if test="${userBeans[1] == null}">
                                                            <select id="userroal" name="userroal" onchange="loadAssignUsers(this.value)">
                                                                <option value="">--Select User Role-- </option>
                                                                <c:if test="${userBeans[0].userRoleCode != null}">
                                                                    <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                        <c:if test="${userBeans[0].userRoleCode == userRoleLst.userRoleCode}">
                                                                            <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                    <c:if test="${userBeans[0].userRoleCode != userRoleLst.userRoleCode}">
                                                                        <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </select>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].userRoleCode ne null) && (userBeans[0].userRoleCode ne userBeans[1].userRoleCode)}">
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBeans[0].userRoleCode == userRoleLst.userRoleCode}">
                                                                    <input type="text" name="userroalDes"  maxlength="32" value="${userRoleLst.description}" class="inputtrue">
                                                                    <input type="hidden" name="userroal"  value="${userRoleLst.userRoleCode}">
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].userRoleCode ne null) && (userBeans[0].userRoleCode eq userBeans[1].userRoleCode)}">
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBeans[0].userRoleCode == userRoleLst.userRoleCode}">
                                                                    <input type="text" name="userroalDes"  maxlength="32" value="${userRoleLst.description}">
                                                                    <input type="hidden" name="userroal"  value="${userRoleLst.userRoleCode}">
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                    </td>
                                                    <c:if test="${userBeans[1] != null}">
                                                        <td><select id="userroal1" name="userroal1">
                                                                <option value="">--Select User Role-- </option>
                                                                <c:if test="${userBeans[1].userRoleCode != null}">
                                                                    <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                        <c:if test="${userBeans[1].userRoleCode == userRoleLst.userRoleCode}">
                                                                            <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                    <c:if test="${userBeans[1].userRoleCode != userRoleLst.userRoleCode}">
                                                                        <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                            </select></td>
                                                        </c:if>
                                                    <td > <label style="color: red">${invalidMsgBean.userRoleCode} </label></td>

                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Status</td>
                                                    <td>
                                                        <c:if test="${userBeans[1] == null}">
                                                            <select id="status" name="status">
                                                                <option value="">--Select Status-- </option>
                                                                <c:if test="${userBeans[0].statusCode != null}">
                                                                    <c:forEach var="statusLst" items="${statusLst}">
                                                                        <c:if test="${userBeans[0].statusCode == statusLst.statusCode}">
                                                                            <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="statusLst" items="${statusLst}">
                                                                    <c:if test="${userBeans[0].statusCode != statusLst.statusCode}">
                                                                        <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${userBeans[1] != null}">
                                                            <c:if test="${(userBeans[0].userstatus ne null) && (userBeans[0].userstatus ne userBeans[1].statusCode)}">
                                                                <c:forEach var="statusLst" items="${statusLst}">
                                                                    <c:if test="${userBeans[0].userstatus == statusLst.statusCode}">
                                                                        <input type="text" name="statusDes"  maxlength="32" value="${statusLst.description}" class="inputtrue">
                                                                        <%-- set RQIN Status --%>
                                                                        <input type="hidden" name="status"  value="${editStatus}">
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>
                                                            <c:if test="${(userBeans[0].userstatus ne null) && (userBeans[0].userstatus eq userBeans[1].statusCode)}">
                                                                <c:forEach var="statusLst" items="${statusLst}">
                                                                    <c:if test="${userBeans[0].userstatus == statusLst.statusCode}">
                                                                        <input type="text" name="statusDes"  maxlength="32" value="${statusLst.description}">
                                                                        <%-- set RQIN Status --%>
                                                                        <input type="hidden" name="status"  value="${editStatus}">
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>
                                                        </c:if>
                                                    </td>
                                                    <c:if test="${userBeans[1] != null}">
                                                        <td>
                                                            <select id="status1" name="status1">
                                                                <option value="">--Select Status-- </option>
                                                                <c:if test="${userBeans[1].statusCode != null}">
                                                                    <c:forEach var="statusLst" items="${statusLst}">
                                                                        <c:if test="${userBeans[1].statusCode == statusLst.statusCode}">
                                                                            <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="statusLst" items="${statusLst}">
                                                                    <c:if test="${userBeans[1].statusCode != statusLst.statusCode}">
                                                                        <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.statusCode} </label></td>      
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Dual Auth User Role</td>
                                                    <td>
                                                        <c:if test="${userBeans[1] == null}">
                                                            <select id="dualUserroal" name="dualUserroal">

                                                                <c:if test="${userBeans[0].dualUserRoleCode != null}">
                                                                    <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                        <c:if test="${userBeans[0].dualUserRoleCode == userRoleLst.userRoleCode}">
                                                                            <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                    <c:if test="${userBeans[0].dualUserRoleCode != userRoleLst.userRoleCode}">
                                                                        <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].dualUserRoleCode ne null) && (userBeans[0].dualUserRoleCode ne userBeans[1].dualUserRoleCode)}">
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBeans[0].dualUserRoleCode == userRoleLst.userRoleCode}">
                                                                    <input type="text" name="dualUserroalDes"  maxlength="32" value="${userRoleLst.description}" class="inputtrue">
                                                                    <input type="hidden" name="dualUserroal"  value="${userRoleLst.userRoleCode}">
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].dualUserRoleCode ne null) && (userBeans[0].dualUserRoleCode eq userBeans[1].dualUserRoleCode)}">
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBeans[0].dualUserRoleCode == userRoleLst.userRoleCode}">
                                                                    <input type="text" name="dualUserroalDes"  maxlength="32" value="${userRoleLst.description}">
                                                                    <input type="hidden" name="dualUserroal"  value="${userRoleLst.userRoleCode}">
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                    </td>
                                                    <c:if test="${userBeans[1] != null}">
                                                        <td><select id="dualUserroal1" name="dualUserroal1">
                                                                <option value="">--Select User Role-- </option>
                                                                <c:if test="${userBeans[1].dualUserRoleCode != null}">
                                                                    <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                        <c:if test="${userBeans[1].dualUserRoleCode == userRoleLst.userRoleCode}">
                                                                            <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                    <c:if test="${userBeans[1].dualUserRoleCode != userRoleLst.userRoleCode}">
                                                                        <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.dualUserRoleCode} </label></td>
                                                </tr>
                                                <tr>
                                                    <td >&nbsp;</td>
                                                    <td></td>

                                                </tr>
                                                <tr>
                                                    <td ><b><u>Personal Details</u></b></td>
                                                    <td></td>

                                                </tr>



                                                <tr>
                                                    <td ><span class="required">*</span>Title</td>
                                                    <c:if test="${userBeans[0].title=='MR'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR" checked="true" class="input${(userBeans[1].title ne null) && (userBeans[0].title ne userBeans[1].title)}">&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS">&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS">
                                                        </td>
                                                    </c:if>

                                                    <c:if test="${userBeans[0].title=='MRS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR" >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS"  checked="true" class="input${(userBeans[1].title ne null) && (userBeans[0].title ne userBeans[1].title)}">&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS" >
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBeans[0].title=='MS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR" >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS" >&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS"  checked="true" class="input${(userBeans[1].title ne null) && (userBeans[0].title ne userBeans[1].title)}">
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBeans[0].title !='MR' && userBeans[0].title !='MRS' && userBeans[0].title !='MS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title" value="MR"  >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title" value="MRS">&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title" value="MS" >
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBeans[1].title=='MR'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title1" value="MR" checked="true">&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title1" value="MRS">&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title1" value="MS">
                                                        </td>
                                                    </c:if>

                                                    <c:if test="${userBeans[1].title=='MRS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title1" value="MR" >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title1" value="MRS"  checked="true" >&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title1" value="MS" >
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBeans[1].title=='MS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title1" value="MR" >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title1" value="MRS" >&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title1" value="MS"  checked="true" >
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${userBeans[1] != null && userBeans[1].title !='MR' && userBeans[1].title !='MRS' && userBeans[1].title !='MS'}">
                                                        <td>Mr.&nbsp;<input type="radio" name="title1" value="MR"  >&nbsp;&nbsp;Mrs.&nbsp;
                                                            <input type="radio" name="title1" value="MRS">&nbsp;&nbsp;Ms.&nbsp;
                                                            <input type="radio" name="title1" value="MS" >
                                                        </td>
                                                    </c:if>
                                                    <td > <label style="color: red">${invalidMsgBean.title} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Initials</td>
                                                    <td><input type="text" name="initials"  maxlength="16" value="${userBeans[0].initials}" class="input${(userBeans[1].initials ne null) && (userBeans[0].initials ne userBeans[1].initials)}"></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="initials1"  maxlength="16" value="${userBeans[1].initials}" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.initials} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>First Name</td>
                                                    <td><input type="text" name="firstname"  maxlength="32" value="${userBeans[0].firstName}"  class="input${(userBeans[1].firstName ne null) && (userBeans[0].firstName ne userBeans[1].firstName)}"></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="firstname1"  maxlength="32" value="${userBeans[1].firstName}" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.firstName} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Last Name</td>
                                                    <td><input type="text" name="lastname"  maxlength="32" value="${userBeans[0].lastName}" class="input${(userBeans[1].lastName ne null) && (userBeans[0].lastName ne userBeans[1].lastName)}"></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="lastname1"  maxlength="32" value="${userBeans[1].lastName}" ></td>
                                                        </c:if>
                                                    <td > <label style="color: red">${invalidMsgBean.lastName} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Bank Name </td>
                                                    <td><input type="text" name="bankname"  maxlength="64" value="${userBeans[0].bankname}" readonly="true"></td>
                                                        <%-- <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="bankname1"  maxlength="64" value="${userBeans[1].bankname}" readonly="true"></td>
                                                        </c:if> --%>
                                                    <td  > <label style="color: red">${invalidMsgBean.bankname} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Branch Name</td>
                                                    <td>
                                                        <c:if test="${userBeans[1] == null}">
                                                            <select id="branchname" name="branchname">
                                                                <option value="">--Select Branch-- </option>
                                                                <c:if test="${userBeans[0].branchname != null}">
                                                                    <c:forEach var="branchList" items="${branchList}">
                                                                        <c:if test="${userBeans[0].branchname == branchList.branchCode}">
                                                                            <option value="${branchList.branchCode}" selected="true">${branchList.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="branchList" items="${branchList}">
                                                                    <c:if test="${userBeans[0].branchname != branchList.branchCode}">
                                                                        <option value="${branchList.branchCode}">${branchList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].branchname ne null) && (userBeans[0].branchname ne userBeans[1].branchname)}">
                                                            <c:forEach var="branchList" items="${branchList}">
                                                                <c:if test="${userBeans[0].branchname == branchList.branchCode}">
                                                                    <input type="text" name="branchnameDes"  maxlength="32" value="${branchList.description}" class="inputtrue">
                                                                    <input type="hidden" name="branchname"  value="${branchList.branchCode}">
                                                                </c:if>
                                                            </c:forEach>    
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].branchname ne null) && (userBeans[0].branchname eq userBeans[1].branchname)}">
                                                            <c:forEach var="branchList" items="${branchList}">
                                                                <c:if test="${userBeans[0].branchname == branchList.branchCode}">
                                                                    <input type="text" name="branchnameDes"  maxlength="32" value="${branchList.description}">
                                                                    <input type="hidden" name="branchname"  value="${branchList.branchCode}">
                                                                </c:if>
                                                            </c:forEach>    
                                                        </c:if>
                                                    </td>
                                                    <c:if test="${userBeans[1] != null}">
                                                        <td><select id="branchname1" name="branchname1" >
                                                                <option value="">--Select Branch-- </option>
                                                                <c:if test="${userBeans[1].branchname != null}">
                                                                    <c:forEach var="branchList" items="${branchList}">
                                                                        <c:if test="${userBeans[1].branchname == branchList.branchCode}">
                                                                            <option value="${branchList.branchCode}" selected="true">${branchList.description}</option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:if> 
                                                                <c:forEach var="branchList" items="${branchList}">
                                                                    <c:if test="${userBeans[1].branchname != branchList.branchCode}">
                                                                        <option value="${branchList.branchCode}">${branchList.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.branchname} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Designation</td>
                                                    <td><input type="text" name="designation" class="input${(userBeans[1].designation ne null) && (userBeans[0].designation ne userBeans[1].designation)}" maxlength="150" value="${userBeans[0].designation}"></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="designation1"  maxlength="150" value="${userBeans[1].designation}"></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.designation} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Service Id</td>
                                                    <td><input type="text" name="serviseid" class="input${(userBeans[1].serviseid ne null) && (userBeans[0].serviseid ne userBeans[1].serviseid)}" maxlength="100" value="${userBeans[0].serviseid}" ></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="serviseid1"  maxlength="100" value="${userBeans[1].serviseid}" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.serviseid} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Contact Number</td>
                                                    <td><input type="text" name="telephone" class="input${(userBeans[1].telNo ne null) && (userBeans[0].telNo ne userBeans[1].telNo)}" maxlength="20" value="${userBeans[0].telNo}" ></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="telephone1"  maxlength="20" value="${userBeans[1].telNo}" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.telNo} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>NIC</td>

                                                    <td><input type="text" name="nic"  maxlength="12" value="${userBeans[0].nic}" class="input${(userBeans[1].nic ne null) && (userBeans[0].nic ne userBeans[1].nic)}"></td>

                                                    <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="nic1"  maxlength="12" value="${userBeans[1].nic}"></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.nic} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Email Address</td>
                                                    <td><input type="text" name="email"  maxlength="100" value="${userBeans[0].email}" class="input${(userBeans[1].email ne null) && (userBeans[0].email ne userBeans[1].email)}"></td>
                                                        <c:if test="${userBeans[1] != null}">
                                                        <td><input type="text" name="email1"  maxlength="100" value="${userBeans[1].email}" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.email} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>Gender</td>
                                                    <c:if test="${userBeans[0].gender =='MALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE" checked="true" class="input${(userBeans[1].gender ne null) && (userBeans[0].gender ne userBeans[1].gender)}">&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" ></td>
                                                        </c:if>

                                                    <c:if test="${userBeans[0].gender =='FEMALE' }">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" checked="true" class="input${(userBeans[1].gender ne null) && (userBeans[0].gender ne userBeans[1].gender)}"></td>
                                                        </c:if>
                                                        <c:if test="${userBeans[0].gender !='MALE' && userBeans[0].gender !='FEMALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender"  value="FEMALE" ></td>
                                                        </c:if>
                                                        <c:if test="${userBeans[1].gender =='MALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender1" value="MALE" checked="true" >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender1"  value="FEMALE" ></td>
                                                        </c:if>

                                                    <c:if test="${userBeans[1].gender =='FEMALE' }">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender1" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender1"  value="FEMALE" checked="true" ></td>
                                                        </c:if>
                                                        <c:if test="${userBeans[1] != null && userBeans[1].gender !='MALE' && userBeans[1].gender !='FEMALE'}">
                                                        <td>Male&nbsp;&nbsp;<input type="radio" name="gender1" value="MALE"  >&nbsp;&nbsp;&nbsp;
                                                            Female&nbsp;&nbsp;<input type="radio" name="gender1"  value="FEMALE" ></td>
                                                        </c:if>
                                                    <td  > <label style="color: red">${invalidMsgBean.gender} </label></td>
                                                </tr>

                                                <tr>
                                                    <td>&nbsp;Date Of Birth</td>
                                                    <td>
                                                        <c:if test="${(userBeans[1].birthday eq null)}">
                                                            <input  name="birthday" maxlength="16" readonly class="inputfeild" value="${userBeans[0].birthday}" key="birthday" id="birthday"/>
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                    $("#birthday").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1,
                                                                        maxDate: new Date()
                                                                    });
                                                                });
                                                            </script>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].birthday ne null) && (userBeans[0].birthday eq userBeans[1].birthday)}">
                                                            <input  name="birthday" maxlength="16" readonly class="inputfeild" value="${userBeans[0].birthday}" key="birthday" id="birthday"/>
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                    $("#birthday").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1,
                                                                        maxDate: new Date()
                                                                    });
                                                                });
                                                            </script>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].birthday ne null) && (userBeans[0].birthday ne userBeans[1].birthday)}">
                                                            <input readonly="" name="birthday" maxlength="16" value="${userBeans[0].birthday}" key="birthday" id="birthday" class="inputtrue"/>
                                                        </c:if>
                                                    </td>
                                                    <c:if test="${userBeans[1] != null}">
                                                        <td>
                                                            <input  name="birthday1" maxlength="16" readonly class="inputfeild" value="${userBeans[1].birthday}" key="birthday1" id="birthday1" />
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                    $("#birthday1").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1
                                                                    });
                                                                });
                                                            </script>

                                                        </td>
                                                    </c:if> 
                                                    <td  > <label style="color: red">${invalidMsgBean.birthday} </label></td>
                                                </tr>
                                                <tr>
                                                    <td ><span class="required">*</span>User Expiry Date</td>
                                                    <td>
                                                        <c:if test="${(userBeans[1].expiryDateToString eq null)}">
                                                            <input  name="expdate" maxlength="16" readonly class="inputfeild" value="${userBeans[0].expiryDateToString}" key="expdate" id="expdate"  />
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                    $("#expdate").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1
                                                                    });
                                                                });
                                                            </script>
                                                        </c:if>    
                                                        <c:if test="${(userBeans[1].expiryDateToString ne null) && (userBeans[0].expiryDateToString eq userBeans[1].expiryDateToString)}">
                                                            <input  name="expdate" maxlength="16" readonly class="inputfeild" value="${userBeans[0].expiryDateToString}" key="expdate" id="expdate"  />
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                    $("#expdate").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1
                                                                    });
                                                                });
                                                            </script>
                                                        </c:if>
                                                        <c:if test="${(userBeans[1].expiryDateToString ne null) && (userBeans[0].expiryDateToString ne userBeans[1].expiryDateToString)}">
                                                            <input name="expdate" maxlength="16" value="${userBeans[0].expiryDateToString}" key="expdate" id="expdate" class="inputtrue" readonly=""/>
                                                        </c:if>    
                                                    </td>
                                                    <c:if test="${userBeans[1] != null}">
                                                        <td>
                                                            <input  name="expdate1" maxlength="16" readonly class="inputfeild" value="${userBeans[1].expiryDateToString}" key="expdate1" id="expdate1"  />
                                                            <script type="text/javascript">
                                                                $(function() {
                                                                    $("#expdate1").datepicker({
                                                                        showOn: "button",
                                                                        buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                        changeMonth: true,
                                                                        changeYear: true,
                                                                        buttonImageOnly: true,
                                                                        dateFormat: "yy-mm-dd",
                                                                        showWeek: true,
                                                                        firstDay: 1
                                                                    });
                                                                });
                                                            </script>

                                                        </td>
                                                    </c:if> 
                                                    <td  > <label style="color: red">${invalidMsgBean.expiryDateToString} </label></td>

                                                </tr>
                                                <c:if test="${userBeans[0].statusCode == addStatus || userBeans[0].statusCode == editStatus}">
                                                    <tr>
                                                        <td >Reject Remark</td>
                                                        <td><input type="text" id="rejectremark" name="rejectremark" value="${userBeans[0].rejectRemark}" ></td>
                                                            <%-- <c:if test="${userBeans[1] != null}">
                                                            <td><input type="text" id="rejectremark1" name="rejectremark1" value="${userBeans[1].rejectRemark}" ></td>
                                                            </c:if> --%>
                                                        <td><label style="color: red">${invalidMsgBean.rejectRemark} </label></td>
                                                    </tr>
                                                </c:if>




                                                <tr>
                                                    <td ><p>&nbsp;</p></td>
                                                    <td>
                                                        <input type="hidden" id="isemailsent" name="isemailsent" value="${userBeans[0].isEmailSent}"/>
                                                        <input type="hidden" id="islockedforauth" name="isemailsent" value="${userBeans[0].islockedforauth}"/>
                                                        <input type="hidden" id="userstatus" name="userstatus" value="${userBeans[0].userstatus}"/>
                                                    </td>

                                                </tr>
                                            </table>
                                            <table>
                                                <tr>
                                                    <td class="labelsmall"></td>
                                                    <td class="largeinput">
                                                        <c:if test="${userBeans[0].statusCode != addStatus and userBeans[0].statusCode != editStatus}">
                                                            <input type="submit" name="next" value="Save" class="buttonsarea" />&nbsp;<input type="button" name="next" onClick="invokeReset('edit')" value="Reset" class="buttonsarea" />&nbsp;<input type="button" name="cancel" value="Cancel" onClick="invokeCancel()" class="buttonsarea" />
                                                        </c:if>    
                                                        <c:if test="${userBeans[0].statusCode == addStatus or userBeans[0].statusCode == editStatus}">
                                                            <input type="submit" name="accept" value="<%=StatusVarList.DA_REQUEST_APPROVE_DES%>" class="buttonsarea"/>&nbsp;
                                                            <input type="submit" name="accept" value="<%=StatusVarList.DA_REQUEST_REJECT_DES%>" class="buttonsarea"/>
                                                        </c:if>
                                                    </td>


                                                </tr>
                                            </table>

                                        </form>
                                    </div>
                                </c:if>


                                <!--   ------------------------- end operation edit  area  --------------------------------                      -->

                                <!--   ------------------------- start operation delete area  --------------------------------                      -->
                                <c:if test="${operationtype=='delete'}">
                                    <%
                                        pageContext.setAttribute("statusVar", StatusVarList.DELETE_SYSTEMUSER_REQUEST_SENT);
                                    %>
                                    <div class="formarea">
                                        <form name="deleteform" method="post" action="<%=request.getContextPath()%>/DeleteSystemUserServlet">
                                            <table>

                                                <tr>
                                                    <td >Username</td>
                                                    <td><input type="text" name="username" readonly="" maxlength="16" value="${userBean.userName}"></td>

                                                </tr>

                                                <tr>
                                                    <td >User Role</td>
                                                    <td><input type="text" name="userrole" readonly="" maxlength="16" value="${userBean.userRoleDes}"></td>

                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><input type="text" name="status"  readonly="" maxlength="16" value="${userBean.statusDes}"></td>

                                                </tr>

                                                <c:if test="${userBean.statusCode == statusVar}">
                                                    <tr>
                                                        <td >Reject Remark</td>
                                                        <td><input type="text" id="rejectremark" name="rejectremark" value="${userBean.rejectRemark}" ></td>
                                                        <td><label style="color: red">${invalidMsgBean} </label></td>
                                                    </tr>
                                                </c:if>

                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;
                                                        <input type="hidden" name="statusCode" id="statusCode" value="${userBean.statusCode}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <c:if test="${userBean.statusCode != statusVar}">
                                                        <td>Confirm to delete this user&nbsp;&nbsp;</td>
                                                        <td><input type="submit" name="yes" value="YES"  class="buttonsarea"/>&nbsp;<input type="button" name="no" value="NO" onClick="invokeCancel()"  class="buttonsarea"/></td>
                                                        </c:if>
                                                        <c:if test="${userBean.statusCode == statusVar}">
                                                        <td>
                                                            <input type="submit" name="accept" value="<%=StatusVarList.DA_REQUEST_APPROVE_DES%>" class="buttonsarea"/>&nbsp;
                                                            <input type="submit" name="accept" value="<%=StatusVarList.DA_REQUEST_REJECT_DES%>" class="buttonsarea"/>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>            
                                </c:if>


                                <!--   ------------------------- end operation delete  area  --------------------------------                      -->



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
