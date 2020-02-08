<%-- 
    Document   : creditconfirmationschemamgt
    Created on : Jun 20, 2016, 11:31:42 AM
    Author     : chinthaka_r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>

<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
<!--        <style type="text/css">
            .ui-widget, .ui-widget button {
                font-size: 12px;
            }
        </style>-->
        <script>
            function invokAdd() {
                document.addSchemaForm.action = "${pageContext.request.contextPath}/AddCreditConfirmationSchemaMgtServlet";
                document.addSchemaForm.submit();
            }
            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadCreditConfirmationSchemaMgtServlet";

            }
        </script>
        <script>
            function ConfirmDelete(schemaCode)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+schemaCode+" schema ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                               window.location = "${pageContext.request.contextPath}/DeleteCreditConfirmationSchemaMgtServlet?schemaCode=" + schemaCode;
                        }
                    }
                });

            }
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CREDITCONFIRMSCEMAMGT%>'
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
        <div class="container" >
            <div class="header">

            </div>
            <div class="main" >
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>
                <div id="content1" >
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
                                <c:if test="${operationtype=='default'}" >
                                    <form method="POST" name="addSchemaForm">
                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Schema Code</td>
                                                    <td>&nbsp;<font style="color: red">*</font></td>
                                                    <td>
                                                        &nbsp;&nbsp;<input type="text" name="txtScemaCode" value="${schemaBean.schemaCode}" maxlength="32"/> 
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td><td></td><td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>&nbsp;<font style="color: red">*</font></td>
                                                    <td>
                                                        &nbsp;&nbsp;<input type="text" name="txtDescription" value="${schemaBean.description}" maxlength="32"/> 
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td><td></td><td></td>
                                                </tr>
                                                <tr>
                                                    <td>Min Limit</td>
                                                    <td>&nbsp;<font style="color: red">*</font></td>
                                                        <c:if test="${schemaBean.minLimit==null}">
                                                        <td>
                                                            &nbsp;&nbsp;<input type="text" name="" value="${lastMaxLimit}" maxlength="32" disabled/> 
                                                        </td>
                                                        <td>
                                                            &nbsp;&nbsp;<input type="hidden" name="txtMinLimit" value="${lastMaxLimit}" maxlength="32"/> 
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${schemaBean.minLimit!=null}">
                                                        <td>
                                                            &nbsp;&nbsp;<input type="text" name="" value="${schemaBean.minLimit}" maxlength="32" disabled/> 
                                                        </td>
                                                        <td>
                                                            &nbsp;&nbsp;<input type="hidden" name="txtMinLimit" value="${schemaBean.minLimit}" maxlength="32"/> 
                                                        </td>
                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td><td></td><td></td>
                                                </tr>
                                                <tr>
                                                    <td>Max Limit</td>
                                                    <td>&nbsp;<font style="color: red">*</font></td>
                                                    <td>
                                                        &nbsp;&nbsp;<input type="text" name="txtMaxLimit" value="${schemaBean.maxLimit}" maxlength="32"/> 
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td><td></td><td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        &nbsp;&nbsp;<input type="submit" name="btnAddSchema" value="Add" onclick="invokAdd()" style="width: 100px"/>&nbsp;<input type="button" name="btnResetScema" value="Reset" onclick="invokeReset()" style="width: 100px"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewSchemaform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"><b> ${errorMsg}</b></font> </td>
                                                <td><font color="green"><b> ${successMsg}</b></font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Schema Code</td>
                                                    <td></td>
                                                    <td> :&nbsp;&nbsp;</td>
                                                    <td></td>
                                                    <td>${schema.schemaCode}</td>
                                                </tr>
                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> :</td>
                                                    <td></td>
                                                    <td>${schema.description}</td>
                                                </tr>
                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td>Min Limit</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${schema.minLimit}</td>
                                                </tr>
                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td>Max Limit</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${schema.maxLimit}</td>
                                                </tr>
                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td> </td>
                                                    <td></td>
                                                    <td> </td>
                                                    <td></td>
                                                    <td><input type="reset" value="Back" name="back" onclick="invokeReset()" style="width: 100px;"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <table border="1" class="display" id="tableview3">
                                    <thead>
                                        <tr>
                                            <th>Schema Code</th>
                                            <th>Description</th>
                                            <th>Min Limit</th>
                                            <th>Max Limit</th>
                                            <th>View</th>
                                            <th>User Role Assign</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="schema" items="${sessionScope.SessionObject.creditLimitSchemaList}" varStatus="loop">
                                            <tr style="text-align: center">
                                                <td>${schema.schemaCode}</td>
                                                <td>${schema.description}</td>
                                                <td>${schema.minLimit}</td>
                                                <td>${schema.maxLimit}</td>
                                                <td><a href='${pageContext.request.contextPath}/ViewCreditConfirmationSchemaMgtServlet?schemaCode=<c:out value="${schema.schemaCode}"></c:out>'>View</a></td>
                                                <td><a href='${pageContext.request.contextPath}/LoadAssignSchemaToCrdtAppConfirmationUserRole?schemaCode=<c:out value="${schema.schemaCode}"></c:out>'>Assign</a></td>
                                                <td><a href="#" onclick="ConfirmDelete('${schema.schemaCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">
            
        </div>
        <!--Include Footer-->                                
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
