<%-- 
    Document   : securityquestionmgthome
    Created on : Apr 17, 2012, 11:50:47 AM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" >


        </script>  
        <script>
            function enableQuestionType(value) {
                if (value == 'Issue') {
                    $(".qutype").attr("disabled", false);
                }
                if (value == 'Acquire') {
                    $(".qutype").attr("disabled", true);
                }

            }

            function loadTables(value, opType) {
                document.addSecurityQuestion.action = "${pageContext.request.contextPath}/SetTableNameDropDownServlet?id=" + value + "&opType=" + opType;
                document.addSecurityQuestion.submit();

            }
            function loadUpdateTables(value, opType) {
                document.updateSecurityQuestion.action = "${pageContext.request.contextPath}/SetTableNameDropDownServlet?id=" + value + "&opType=" + opType;
                document.updateSecurityQuestion.submit();

            }

            function loadFields(value, opType) {

                document.addSecurityQuestion.action = "${pageContext.request.contextPath}/SetFieldNameDropDownServlet?id=" + value + "&opType=" + opType;
                document.addSecurityQuestion.submit();
            }

            function loadUpdateFields(value, opType) {

                document.updateSecurityQuestion.action = "${pageContext.request.contextPath}/SetFieldNameDropDownServlet?id=" + value + "&opType=" + opType;
                document.updateSecurityQuestion.submit();
            }

            function enableFields(value) {
                $(value).attr("disabled", false);
            }

            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadSequrityQuestionServlet";

            }

            function invokeUpdate(cardCat, table)
            {
                document.updateSecurityQuestion.action = "${pageContext.request.contextPath}/UpdateSecurityQuestionServlet?cardCat=" + cardCat + "&table=" + table;
                document.updateSecurityQuestion.submit();

            }
            function invokeResetinUpdate(Qid, cardCat, table)
            {
                document.updateSecurityQuestion.action = "${pageContext.request.contextPath}/LoadUpdateSecurityQuestionServlet?id=" + Qid + "&cardCat=" + cardCat + "&table=" + table;
                document.updateSecurityQuestion.submit();

            }

            function invokeAdd(cardCat, table)
            {
                document.addSecurityQuestion.action = "${pageContext.request.contextPath}/AddSecurityQuestionServlet?cardCat=" + cardCat + "&table=" + table;
                document.addSecurityQuestion.submit();

            }

            function deleteQuestion(value) {

//                answer = confirm("Are you sure you want to delete Question '"+value+"' ?")
//                   
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteSecurityQuestionServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadSequrityQuestionServlet";
//                }
                $("#dialog-confirm").html("<p>Are you sure you want to delete Question '" + value + "' ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadSequrityQuestionServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteSecurityQuestionServlet?id=" + value;
                        }
                    }
                });

            }

        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.SECURITY_QUES_MGT%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }



        </script>  

    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

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
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" name="addSecurityQuestion" onload="loadTables('${cardCategory}')">
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody >
                                                <tr>
                                                    <td width="150px;">Question No</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text"  value="${questionBean.questionNo}" name="questionid" maxlength="6" readonly="true"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Question</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" style="width: 450px" value="${questionBean.question}" name="question" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select style="width: 163px;" name="prioritycode">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="priority" items="${sessionScope.SessionObject.priorityLevelList}">
                                                                <c:if test="${questionBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <!--                                                <tr>
                                                                                                    <td>Issuing or Acquiring</td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;
                                                <c:if test="${questionBean.issueAcquireStatus == 'Issue'}">
                                                    <input type="radio" name="type" value="Issue" checked="true" id="issue" onchange="enableQuestionType(issue.value)"/>Issuing
                                                    &nbsp;&nbsp;&nbsp;
                                                </c:if>
                                                <c:if test="${questionBean.issueAcquireStatus != 'Issue'}">
                                                    <input type="radio" name="type" value="Issue" id="issue" onchange="enableQuestionType(issue.value)"/>Issuing
                                                    &nbsp;&nbsp;&nbsp;
                                                </c:if>                                                

                                                <c:if test="${questionBean.issueAcquireStatus == 'Acquire'}">
                                                    <input type="radio" name="type" value="Acquire" checked="true" id="acquire" onchange="enableQuestionType(acquire.value)"/>Acquiring

                                                </c:if>
                                                <c:if test="${questionBean.issueAcquireStatus != 'Acquire'}">
                                                    <input type="radio" name="type" value="Acquire" id="acquire" onchange="enableQuestionType(acquire.value)"/>Acquiring

                                                </c:if>
                                            </td>
                                        </tr>-->
                                                <tr>
                                                    <td>Question Type
                                                        <input type="hidden" name="issOracq" value="ISS" id="ISS" />
                                                    </td>
                                                    <td><font style="color: red;">*</font>&nbsp;

                                                        <c:if test="${questionBean.questionType == 'C'}">
                                                            <input type="radio" name="qtype" value="C" checked="true" class="qutype"/>Card

                                                        </c:if>
                                                        <c:if test="${questionBean.questionType != 'C'}">
                                                            <input type="radio" name="qtype" value="C" class="qutype"/>Card

                                                        </c:if>                                                

                                                        <c:if test="${questionBean.questionType == 'A'}">
                                                            <input type="radio" name="qtype" value="A" checked="true" class="qutype"/>Application

                                                        </c:if>
                                                        <c:if test="${questionBean.questionType != 'A'}">
                                                            <input type="radio" name="qtype" value="A" class="qutype"/>Application

                                                        </c:if>




                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card Category</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="cardcategory" id="cardcategory" onchange="loadTables(cardcategory.value, 'add')" style="width: 163px;" >


                                                            <c:forEach var="category" items="${sessionScope.SessionObject.cardCategoryLst}">
<!--                                                                <option value="${category.categoryCode}" >${category.description}</option>-->

                                                                <c:if test="${questionBean.cardCategory==category.categoryCode}">
                                                                    <option value="${category.categoryCode}" selected>${category.description}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.cardCategory!=category.categoryCode}">
                                                                    <option value="${category.categoryCode}">${category.description}</option>
                                                                </c:if>


                                                            </c:forEach>   
                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>Table Name</td>
                                                    <td colspan="2"><font style="color: red;">*</font>&nbsp;
                                                        <select name="tablename" id="tablename" onchange="loadFields(tablename.value, 'add')" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="tables"   items="${tableList}">

                                                                <c:if test="${questionBean.tableName==tables.value}">
                                                                    <option value="${tables.value}" selected>${tables.value}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.tableName!=tables.value}">
                                                                    <option value="${tables.value}">${tables.value}</option>
                                                                </c:if>


                                                            </c:forEach>

                                                        </select>
                                                    </td>

                                                </tr>

                                                <tr>
                                                    <td>Field 1</td>
                                                    <td colspan="2"><font style="color: red;">*</font>&nbsp;
                                                        <select name="field1" onchange="enableFields(field2)" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="field1"   items="${fieldList}">

                                                                <c:if test="${questionBean.field1==field1}">
                                                                    <option value="${field1}" selected>${field1}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.field1!=field1}">
                                                                    <option value="${field1}">${field1}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Field 2</td>
                                                    <td colspan="2">&nbsp;&nbsp;&nbsp;
                                                        <select name="field2" id="field2" disabled="" onchange="enableFields(field3)" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="field2"   items="${fieldList}">                                                                
                                                                <c:if test="${questionBean.field2==field2}">
                                                                    <option value="${field2}" selected>${field2}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.field2!=field2}">
                                                                    <option value="${field2}">${field2}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td>Field 3</td>
                                                    <td colspan="2">&nbsp;&nbsp;&nbsp;
                                                        <select name="field3" id="field3" disabled="" onchange="enableFields(field4)" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="field3"   items="${fieldList}">
                                                                <c:if test="${questionBean.field3==field3}">
                                                                    <option value="${field3}" selected>${field3}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.field3!=field3}">
                                                                    <option value="${field3}">${field3}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td>Field 4</td>
                                                    <td colspan="2">&nbsp;&nbsp;&nbsp;
                                                        <select name="field4" id="field4" disabled="" class="field" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="field4"   items="${fieldList}">                                                                
                                                                <c:if test="${questionBean.field4==field4}">
                                                                    <option value="${field4}" selected>${field4}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.field4!=field4}">
                                                                    <option value="${field4}">${field4}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>

                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="status"  class="inputfield-mandatory" style="width: 163px;">
                                                            <option value="">------SELECT------</option >
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${questionBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp;
                                                        <input type="button" class="defbutton" name="add" value="Add" onclick="invokeAdd('${questionBean.cardCategory}', '${questionBean.tableName}')" /> 
                                                        <input type="button" class="defbutton" name="reset" value="Reset" onclick="invokeReset()"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>

                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateSecurityQuestion">

                                        <table cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="150px;">Question No</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text"  value="${questionBean.questionNo}" name="questionid" maxlength="6" readonly="true"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Question</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" style="width: 450px" value="${questionBean.question}" name="question" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select style="width: 163px;" name="prioritycode" >
                                                            <option value="">------SELECT------</option>

                                                            <c:forEach var="priority" items="${sessionScope.SessionObject.priorityLevelList}">
                                                                <c:if test="${questionBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <!--                                                <tr>
                                                                                                    <td>Issuing or Acquiring</td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;
                                                <c:if test="${questionBean.issueAcquireStatus == 'Issue'}">
                                                    <input type="radio" name="type" value="Issue" checked="true" id="issue" onchange="enableQuestionType(issue.value)"/>Issuing
                                                    &nbsp;&nbsp;&nbsp;
                                                </c:if>
                                                <c:if test="${questionBean.issueAcquireStatus != 'Issue'}">
                                                    <input type="radio" name="type" value="Issue" id="issue" onchange="enableQuestionType(issue.value)"/>Issuing
                                                    &nbsp;&nbsp;&nbsp;
                                                </c:if>                                                

                                                <c:if test="${questionBean.issueAcquireStatus == 'Acquire'}">
                                                    <input type="radio" name="type" value="Acquire" checked="true" id="acquire" onchange="enableQuestionType(acquire.value)"/>Acquiring

                                                </c:if>
                                                <c:if test="${questionBean.issueAcquireStatus != 'Acquire'}">
                                                    <input type="radio" name="type" value="Acquire" id="acquire" onchange="enableQuestionType(acquire.value)"/>Acquiring

                                                </c:if>
                                            </td>
                                        </tr>-->
                                                <tr>
                                                    <td>Question Type
                                                        <input type="hidden" name="issOracq" value="ISS" id="ISS" />
                                                    </td>
                                                    <td><font style="color: red;">*</font>&nbsp;

                                                        <c:if test="${questionBean.questionType == 'C'}">
                                                            <input type="radio" name="qtype" value="C" checked="true" class="qutype"/>Card

                                                        </c:if>
                                                        <c:if test="${questionBean.questionType != 'C'}">
                                                            <input type="radio" name="qtype" value="C" class="qutype"/>Card

                                                        </c:if>                                                

                                                        <c:if test="${questionBean.questionType == 'A'}">
                                                            <input type="radio" name="qtype" value="A" checked="true" class="qutype"/>Application

                                                        </c:if>
                                                        <c:if test="${questionBean.questionType != 'A'}">
                                                            <input type="radio" name="qtype" value="A" class="qutype"/>Application

                                                        </c:if>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Card Category</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="cardcategory" id="cardcategory" onchange="loadUpdateTables(cardcategory.value, 'update')" style="width: 163px;" >


                                                            <c:forEach var="category" items="${sessionScope.SessionObject.cardCategoryLst}">
<!--                                                                <option value="${category.categoryCode}" >${category.description}</option>-->

                                                                <c:if test="${questionBean.cardCategory==category.categoryCode}">
                                                                    <option value="${category.categoryCode}" selected>${category.description}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.cardCategory!=category.categoryCode}">
                                                                    <option value="${category.categoryCode}">${category.description}</option>
                                                                </c:if>


                                                            </c:forEach>   
                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>Table Name</td>
                                                    <td colspan="2"><font style="color: red;">*</font>&nbsp;
                                                        <select name="tablename" id="tablename" onchange="loadUpdateFields(tablename.value, 'update')" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="tables"   items="${tableList}">

                                                                <c:if test="${questionBean.tableName==tables.value}">
                                                                    <option value="${tables.value}" selected>${tables.value}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.tableName!=tables.value}">
                                                                    <option value="${tables.value}">${tables.value}</option>
                                                                </c:if>


                                                            </c:forEach>

                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>Field 1</td>
                                                    <td colspan="2"><font style="color: red;">*</font>&nbsp;
                                                        <select name="field1" onchange="enableFields(field2)" style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="field1"   items="${fieldList}">

                                                                <c:if test="${questionBean.field1==field1}">
                                                                    <option value="${field1}" selected>${field1}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.field1!=field1}">
                                                                    <option value="${field1}">${field1}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Field 2</td>
                                                    <td colspan="2">&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${questionBean.field1!=null}">
                                                            <select name="field2" id="field2" onchange="enableFields(field3)" style="width: 163px;">
                                                                <option value="">------SELECT------</option>
                                                                <c:forEach var="field2"   items="${fieldList}">                                                                
                                                                    <c:if test="${questionBean.field2==field2}">
                                                                        <option value="${field2}" selected>${field2}</option>
                                                                    </c:if>
                                                                    <c:if test="${questionBean.field2!=field2}">
                                                                        <option value="${field2}">${field2}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${questionBean.field1==null}">
                                                            <select name="field2" id="field2" disabled="" onchange="enableFields(field3)" style="width: 163px;">
                                                                <option value="">------SELECT------</option>
                                                                <c:forEach var="field2"   items="${fieldList}">                                                                
                                                                    <c:if test="${questionBean.field2==field2}">
                                                                        <option value="${field2}" selected>${field2}</option>
                                                                    </c:if>
                                                                    <c:if test="${questionBean.field2!=field2}">
                                                                        <option value="${field2}">${field2}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td>Field 3</td>
                                                    <td colspan="2">&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${questionBean.field2!=null}">
                                                            <select name="field3" id="field3" onchange="enableFields(field4)" style="width: 163px;">
                                                                <option value="">------SELECT------</option>
                                                                <c:forEach var="field3"   items="${fieldList}">
                                                                    <c:if test="${questionBean.field3==field3}">
                                                                        <option value="${field3}" selected>${field3}</option>
                                                                    </c:if>
                                                                    <c:if test="${questionBean.field3!=field3}">
                                                                        <option value="${field3}">${field3}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${questionBean.field2==null}">
                                                            <select name="field3" id="field3" disabled="" onchange="enableFields(field4)" style="width: 163px;">
                                                                <option value="">------SELECT------</option>
                                                                <c:forEach var="field3"   items="${fieldList}">
                                                                    <c:if test="${questionBean.field3==field3}">
                                                                        <option value="${field3}" selected>${field3}</option>
                                                                    </c:if>
                                                                    <c:if test="${questionBean.field3!=field3}">
                                                                        <option value="${field3}">${field3}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td>Field 4</td>
                                                    <td colspan="2">&nbsp;&nbsp;&nbsp;
                                                        <c:if test="${questionBean.field3!=null}">
                                                            <select name="field4" id="field4" class="field" style="width: 163px;">
                                                                <option value="">------SELECT------</option>
                                                                <c:forEach var="field4"   items="${fieldList}">                                                                
                                                                    <c:if test="${questionBean.field4==field4}">
                                                                        <option value="${field4}" selected>${field4}</option>
                                                                    </c:if>
                                                                    <c:if test="${questionBean.field4!=field4}">
                                                                        <option value="${field4}">${field4}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${questionBean.field3==null}">
                                                            <select name="field4" id="field4" disabled="" class="field" style="width: 163px;">
                                                                <option value="">------SELECT------</option>
                                                                <c:forEach var="field4"   items="${fieldList}">                                                                
                                                                    <c:if test="${questionBean.field4==field4}">
                                                                        <option value="${field4}" selected>${field4}</option>
                                                                    </c:if>
                                                                    <c:if test="${questionBean.field4!=field4}">
                                                                        <option value="${field4}">${field4}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status </td>

                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="status"   style="width: 163px;">
                                                            <option value="">------SELECT------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${questionBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${questionBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>&nbsp;&nbsp;
                                                        <input type="button" value="Update" name="update" class="defbutton" onclick="invokeUpdate('${questionBean.cardCategory}', '${questionBean.tableName}')"/>
                                                        <input type="reset" value="Reset" name="reset" class="defbutton" onclick="invokeResetinUpdate('${questionBean.questionNo}', '${questionBean.cardCategory}', '${questionBean.tableName}')"/>
                                                        <input type="button" class="defbutton" name="cancel" value="Cancel" onclick="invokeReset()"/>
                                                    </td>

                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewsecurityquestion">
                                        <table border="0" cellspacing ="10" cellpadding ="0">
                                            <tbody>
                                                <tr>
                                                    <td>Question No </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${question.questionNo}</td>
                                                    <td></td>
                                                </tr>     
                                                <tr>
                                                    <td>Question</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${question.question}</td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${question.priorityDes}</td>
                                                    <td></td>
                                                </tr>
                                                <!--                                                <tr>
                                                                                                    <td>Issuing Acquiring Status</td>
                                                                                                    <td></td>
                                                                                                    <td> : </td>
                                                                                                    <td>${question.issueAcquireStatus}</td>
                                                                                                    <td></td>
                                                                                                </tr>-->
                                                <tr>
                                                    <td>Question Type</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${question.questionType == 'A'}">
                                                        <td>Application</td>
                                                    </c:if>
                                                    <c:if test="${question.questionType == 'C'}">
                                                        <td>Card</td>
                                                    </c:if>
                                                    <c:if test="${question.questionType == 'AC'}">
                                                        <td>Card & Application</td>
                                                    </c:if>
                                                    <c:if test="${question.questionType == null}">
                                                        <td>--</td>
                                                    </c:if>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Table Name</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${question.tableName}</td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Field 1</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${question.field1}</td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Field 2</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${question.field2!=null}">
                                                        <td>${question.field2}</td>
                                                    </c:if>
                                                    <c:if test="${question.field2==null}">
                                                        <td> -- </td>
                                                    </c:if>                                          


                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Field 3</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${question.field3!=null}">
                                                        <td>${question.field3}</td>
                                                    </c:if>
                                                    <c:if test="${question.field3==null}">
                                                        <td> -- </td>
                                                    </c:if>                                            


                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Field 4</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${question.field4!=null}">
                                                        <td>${question.field4}</td>
                                                    </c:if>
                                                    <c:if test="${question.field4==null}">
                                                        <td> -- </td>
                                                    </c:if>                                                    


                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${question.statusDes}</td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" name="back" class="defbutton" onclick="invokeReset()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>


                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="scoreltableview2">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Question No</th>
                                            <th>Question</th>
                                            <th>Priority Level</th>
                                            <!--                                            <th>Issue/Acquire Status</th>-->
                                            <th>Question Type</th>
                                            <th>Card Category</th>                                            
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="question" items="${questionList}">
                                            <tr>
                                                <td>${question.questionNo}</td>
                                                <td>${question.question}</td>
                                                <td>${question.priorityDes}</td>
<!--                                                <td>${question.issueAcquireStatus}</td>-->
                                                <c:if test="${question.questionType == 'A'}">
                                                    <td>Account</td>
                                                </c:if>
                                                <c:if test="${question.questionType == 'C'}">
                                                    <td>Card</td>
                                                </c:if>
                                                <c:if test="${question.questionType == 'AC'}">
                                                    <td>Card & Application</td>
                                                </c:if>
                                                <c:if test="${question.questionType != 'A' && question.questionType != 'C'}">
                                                    <td>--</td>
                                                </c:if>

                                                <td>${question.cardCategoryDes}</td>                                                
                                                <td>${question.statusDes}</td>

                                                <td  ><a href='${pageContext.request.contextPath}/ViewSecurityQuestionServlet?id=<c:out value="${question.questionNo}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateSecurityQuestionServlet?id=<c:out value="${question.questionNo}"></c:out>&cardCat=<c:out value="${question.cardCategory}"></c:out>&table=<c:out value="${question.tableName}"></c:out>'>Update</a></td> 
                                                <td ><a  href='#' onclick="deleteQuestion('${question.questionNo}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <br />


                                <!--   ------------------------- end developer area  --------------------------------                      -->




                            </div>

                        </div>
                    </div>

                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
                                              <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
