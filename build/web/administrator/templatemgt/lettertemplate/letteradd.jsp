<%-- 
    Document   : letteradd
    Created on : Aug 5, 2016, 8.00.00 AM
    Author     : sajith
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">

            function loadNiceditData() {
                var content = myNicEditor.instanceById('body').getContent();
                return content;
            }
            ;

            function checkParamsIncludedInLetterBody() {
                var letterBody = loadNiceditData();
                var selectedParams = document.getElementById("out");

                //get selected parameters
                var option = selectedParams.getElementsByTagName("option");

                //get selected parameter count
                var paramCount = option.length;

                //tokenized letter body
                var tokenizedStrings = letterBody.split("|");

                var paramInValues = [];

                if (paramCount > 0) {
                    for (i = 0; i < paramCount; i++) {
                        for (y = 0; y < tokenizedStrings.length; y++) {
                            if (option[i].label == tokenizedStrings[y]) {

                                paramInValues.push(option[i].label);

                            }
                        }
                    }
                }

                if (paramCount == paramInValues.length || paramCount == 0) {
                    return true;
                } else {
                    return false;
                }

            }

            function invokeAdd()
            {
                if (checkParamsIncludedInLetterBody()) {
                    document.getElementById('body').value = loadNiceditData();
                    document.addletterform.action = "${pageContext.request.contextPath}/AddLetterConfServlet";
                    document.addletterform.submit();
                } else {
                    paramValueError();
                }

            }

            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadLetterConfTempMgtServlet";

            }

            //            function invokeReset(){
            //            
            //                window.location = "${pageContext.request.contextPath}/LoadAddLetterConfFormServlet";
            //            }
            //            
            function invokeReset(ele) {
                tags = ele.getElementsByTagName('input');
                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                    }
                }

                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'radio':
                            tags[i].checked = false;
                            break;
                    }
                }

                tags = ele.getElementsByTagName('label');
                for (i = 0; i < tags.length; i++) {
                    tags[i].innerText = '';
                }

                tags = ele.getElementsByTagName('select');
                for (i = 0; i < tags.length; i++) {
                    if (tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    } else {
                        for (j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }
                nicEditors.findEditor("body").setContent('');
            }
            function paramValueError()
            {
                $("#dialog-confirm").html("<p>Letter Body does not contend all the parameter values</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "OK": function() {
                            $(this).dialog("close");
                        }
                    }
                });

            }


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.LETTERHOME%>'
                        },
                function(data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

        </script> 


        <title>EPIC CMS LETTER TEMPLATE MANAGEMENT</title>
    </head>
    <body>
        <script src="${pageContext.request.contextPath}/resources/js/nicEdit.js" type="text/javascript"></script>
        <script type="text/javascript">
            var myNicEditor;
            bkLib.onDomLoaded(function() {
                myNicEditor = new nicEditor({iconsPath: '${pageContext.request.contextPath}/resources/images/nicEditorIcons.gif', buttonList: ['fontSize', 'fontFamily', 'fontFormat', 'bold', 'italic', 'underline', 'left', 'center', 'right', 'justify', 'ol', 'ul', 'indent', 'outdent', 'strikeThrough', 'subscript', 'superscript', 'removeformat', 'html', 'link', 'unlink', 'image', 'forecolor']}).panelInstance('body');
            });
        </script>
        <style type="text/css">
            /*<![CDATA[*/
            .nicEdit-selected {
                border: 2px solid #EBF4FB !important;
            }

            .nicEdit-button {
                background-color: #fff !important;
            }
            .nicEdit-main ol{ 
                list-style:inside; 
                list-style-type: decimal !important; 
            }
            .nicEdit-main ul{ 
                list-style: inside; 
                list-style-type: circle !important; 
            }
            /*]]>*/
        </style>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
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

                                <form method="POST" action="" name="addletterform">
                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td>Template Code</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templateCode" value="${letterBean.templateCode}" maxlength="6"/>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Template Description</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="description" value="${letterBean.description}"/>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Letter Title</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="title" value="${letterBean.title}" size="15"/>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Status </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="status" style="width: 163px">
                                                        <option value="" >------SELECT------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${letterBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${letterBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                           
                                        </tbody>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td style="width: 140px;">Assign Parameters</td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td>
                                                    <h4><b>All Parameters</b></h4>
                                                    <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="task" items="${letterFieldList}">
                                                            <option value="${task.fieldName}" >${task.fieldName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td align="center">
                                                    <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                    <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                    <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                    <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                </td>
                                                <td>
                                                    <h4><b>Assigned Parameters</b></h4>
                                                    <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>

                                                    </select>
                                                </td>
                                                <!--confirmation dialog -->
                                                <div id="dialog-confirm" title="Insufficient Parameter Values"></div>

                                        </tr>
                                        </tbody>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td style="width:141px;">Letter Format</td>
                                                <td><font style="color: red;padding-right: 5px;">*</font>&nbsp;
                                                    <div style="background-color: white;float: right">
                                                        <textarea name="body" id="body" value="${letterBean.body}" style="width: 100%;" cols="100" rows="15">${letterBean.body}</textarea>
                                                    </div>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>&nbsp;&nbsp;&nbsp;
                                                    <input type="button" value="Add" name="add" class="defbutton" onclick="invokeAdd()"/>
                                                    <input type="button" value="Reset" name="reset"  class="defbutton" onclick="invokeReset(this.form)" />
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/></td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>

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




