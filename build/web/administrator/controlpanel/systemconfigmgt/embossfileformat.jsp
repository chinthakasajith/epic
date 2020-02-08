<%-- 
    Document   : embossfileformat
    Created on : Apr 23, 2012, 3:30:14 PM
    Author     : badrika
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">



            function setElementsEnable(value) {

                var i = 1;
                for (i = 1; i <= value; i++) {

                    $("#" + i).attr("disabled", false);
                }
                var j = parseInt(value) + 1;
                //alert(parseInt(value)+1);
                for (j; j <= 80; j++) {

                    $("#" + j).attr("disabled", true);

                }
            }

            function resetForm() {
                window.location = "${pageContext.request.contextPath}/LoadEmbossFileFormatMgtServlet";
            }
            function viewEmbossFileFormat(value) {
                window.location = "${pageContext.request.contextPath}/ViewEmbossFileFormatMgtServlet?id=" + value;
            }
            function updateEmbossFileFormat(value) {
                document.viewTableForm.action = "${pageContext.request.contextPath}/UpdateEmbossFileFormatMgtLoadServlet";
                document.getElementById('id').value = value;
                document.viewTableForm.submit();
            }
            function deleteEmbossFileFormat(value) {

//                answer = confirm("Do you really want to delete "+value+" Emboss File Format?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteEmbossFileFormatMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadEmbossFileFormatMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + value + " Emboss File Format?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadEmbossFileFormatMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteEmbossFileFormatMgtServlet?id=" + value;
                        }
                    }
                });

            }

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.EMBOSSFILEFORMAT%>'
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

    <body onload="setElementsEnable(${embean.recordCount})" >
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

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  ---------------------------- -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddEmbossFileFormatMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Card Type </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select  name="cardType">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cdTypeList}">

                                                                <c:if test="${embean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected>${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${embean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Format Code</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="formatCode" value="${embean.formatCode}" maxlength="16" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" maxlength="64" name="description" value="${embean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Record Count </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>

                                                        <select  name="recordCount" value="" id="recordCount" onchange="setElementsEnable(recordCount.value)">
                                                            <option value="" >--SELECT--</option>

                                                            
                                                            <c:forEach var="i" begin="1" end="40">
                                                                
                                                                <c:if test="${embean.recordCount== i*2}">
                                                                    <option value="${i*2}" selected>${i}</option>
                                                                </c:if>
                                                                <c:if test="${embean.recordCount!=i*2}">
                                                                    <option value="${i*2}">${i}</option>
                                                                </c:if>
                                                            </c:forEach>


                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>                                       

                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="statuscode">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${embean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${embean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>



                                                <tr>
                                                    <td>Record Format </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table border="0">
                                            <tbody>

                                                <tr>
                                                    <td style="width: 20px;">1:</td>
                                                    <td><select  name="1" id="1"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">


                                                                <c:if test="${embean.rfFeild1==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild1!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="2" id="2"value="${embean.rfSeperator1}" disabled="true" /></td>


                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">2:</td>
                                                    <td><select  name="3" id="3" disabled="true" >

                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild2==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild2!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="4" id="4"  value="${embean.rfSeperator2}" disabled="true"/></td>

                                                </tr>
                                                <tr style="width: 10px;"></tr>



                                                <tr>
                                                    <td style="width: 20px;">3:</td>
                                                    <td><select  name="5" id="5"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild3==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild3!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="6" id="6" value="${embean.rfSeperator3}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">4:</td>
                                                    <td><select  name="7" id="7" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild4==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild4!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="8" id="8"value="${embean.rfSeperator4}" disabled="true" /></td>
                                                </tr>


                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">5:</td>
                                                    <td><select  name="9" id="9" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild5==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild5!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="10" id="10" value="${embean.rfSeperator5}" disabled="true"/></td>


                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">6:</td>
                                                    <td><select  name="11" id="11" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild6==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild6!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="12" id="12"value="${embean.rfSeperator6}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">7:</td>
                                                    <td><select  name="13" id="13" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild7==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild7!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="14" id="14"  value="${embean.rfSeperator7}" disabled="true"/></td>


                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">8:</td>
                                                    <td><select  name="15" id="15" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild8==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild8!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="16" id="16"value="${embean.rfSeperator8}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>

                                                    <td style="width: 20px;">9:</td>
                                                    <td><select  name="17" id="17"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild9==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild9!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="18" id="18" value="${embean.rfSeperator9}" disabled="true"/></td>


                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">10:</td>
                                                    <td><select  name="19" id="19" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild10==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild10!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="20" id="20" value="${embean.rfSeperator10}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>

                                                    <td style="width: 20px;">11:</td>
                                                    <td><select  name="21" id="21" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild11==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild11!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="22" id="22"  value="${embean.rfSeperator11}" disabled="true"/></td>


                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">12:</td>
                                                    <td><select  name="23" id="23" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild12==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild12!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="24" id="24" value="${embean.rfSeperator12}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>

                                                    <td style="width: 20px;">13:</td>
                                                    <td><select  name="25" id="25" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild13==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild13!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="26" id="26"  value="${embean.rfSeperator13}" disabled="true"/></td>


                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">14:</td>
                                                    <td><select  name="27" id="27" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild14==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild14!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="28" id="28" value="${embean.rfSeperator14}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">15:</td>
                                                    <td><select  name="29" id="29"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild15==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild15!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="30" id="30" value="${embean.rfSeperator15}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">16:</td>
                                                    <td><select  name="31" id="31" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild16==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild16!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="32" id="32" value="${embean.rfSeperator16}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">17:</td>
                                                    <td><select  name="33" id="33" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild17==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild17!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="34" id="34" value="${embean.rfSeperator17}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">18:</td>
                                                    <td><select  name="35" id="35" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild18==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild18!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="36" id="36" value="${embean.rfSeperator18}" disabled="true" /></td>
                                                </tr>

                                                <tr style="width: 10px; "></tr>

                                                <tr>
                                                    <td style="width: 20px;">19:</td>
                                                    <td><select  name="37" id="37" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild19==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild19!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="38" id="38" value="${embean.rfSeperator19}" disabled="true" /></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">20:</td>
                                                    <td><select  name="39" id="39" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild20==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild20!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="40" id="40" value="${embean.rfSeperator20}" disabled="true" /></td>
                                                </tr>

                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">21:</td>
                                                    <td><select  name="41" id="41"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild21==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild21!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="42" id="42" value="${embean.rfSeperator21}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">22:</td>
                                                    <td><select  name="43" id="43" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild22==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild22!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="44" id="44"value="${embean.rfSeperator22}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">23:</td>
                                                    <td><select  name="45" id="45"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild23==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild23!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="46" id="46" value="${embean.rfSeperator23}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">24:</td>
                                                    <td><select  name="47" id="47" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild24==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild24!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="48" id="48"value="${embean.rfSeperator24}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">25:</td>
                                                    <td><select  name="49" id="49"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild25==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild25!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="50" id="50" value="${embean.rfSeperator25}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">26:</td>
                                                    <td><select  name="51" id="51" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild26==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild26!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="52" id="52"value="${embean.rfSeperator26}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">27:</td>
                                                    <td><select  name="53" id="53"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild27==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild27!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="54" id="54" value="${embean.rfSeperator27}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">28:</td>
                                                    <td><select  name="55" id="55" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild28==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild28!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="56" id="56"value="${embean.rfSeperator28}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">29:</td>
                                                    <td><select  name="57" id="57"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild29==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild29!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="58" id="58" value="${embean.rfSeperator29}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">30:</td>
                                                    <td><select  name="59" id="59" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild30==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild30!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="60" id="60"value="${embean.rfSeperator30}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">31:</td>
                                                    <td><select  name="61" id="61"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild31==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild31!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="62" id="62" value="${embean.rfSeperator31}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">32:</td>
                                                    <td><select  name="63" id="63" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild32==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild32!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="64" id="64"value="${embean.rfSeperator32}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">33:</td>
                                                    <td><select  name="65" id="65"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild33==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild33!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="66" id="66" value="${embean.rfSeperator33}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">34:</td>
                                                    <td><select  name="67" id="67" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild34==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild34!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="68" id="68"value="${embean.rfSeperator34}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">35:</td>
                                                    <td><select  name="69" id="69"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild35==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild35!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="70" id="70" value="${embean.rfSeperator35}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">36:</td>
                                                    <td><select  name="71" id="71" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild36==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild36!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="72" id="72"value="${embean.rfSeperator36}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">37:</td>
                                                    <td><select  name="73" id="73"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild37==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild37!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="74" id="74" value="${embean.rfSeperator37}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">38:</td>
                                                    <td><select  name="75" id="75" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild38==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild38!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="76" id="76"value="${embean.rfSeperator38}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">39:</td>
                                                    <td><select  name="77" id="77"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild39==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild39!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="78" id="78" value="${embean.rfSeperator39}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">40:</td>
                                                    <td><select  name="79" id="79" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild40==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild40!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="80" id="80"value="${embean.rfSeperator40}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                            </tbody>
                                        </table>

                                        <table border="0">

                                            <tbody>
                                                <tr></tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="resetForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>
                                                <tr></tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" name="updateTableForm" action="${pageContext.request.contextPath}/UpdateEmbossFileFormatMgtServlet">
                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Card Type </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select  name="cardType" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cdTypeList}">

                                                                <c:if test="${embean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected>${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${embean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Format Code</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" name="formatCode" readonly="true" value="${embean.formatCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" maxlength="64" name="description" value="${embean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Record Count </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="recordCount" value="" id="recordCount" onchange="setElementsEnable(recordCount.value)">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="i" begin="1" end="40">
                                                                
                                                                <c:if test="${embean.recordCount== i*2}">
                                                                    <option value="${i*2}" selected>${i}</option>
                                                                </c:if>
                                                                <c:if test="${embean.recordCount!=i*2}">
                                                                    <option value="${i*2}">${i}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                        

                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>                                       

                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="statuscode">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${embean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${embean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Record Format </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>

                                        </table>

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 20px;">1:</td>
                                                    <td><select  name="1" id="1"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">


                                                                <c:if test="${embean.rfFeild1==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild1!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="2" id="2"value="${embean.rfSeperator1}" disabled="true" /></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">2:</td>
                                                    <td><select  name="3" id="3" disabled="true" >

                                                            <option value="" selected>--SELECT--</option>

                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">

                                                                <c:if test="${embean.rfFeild2==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild2!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="4" id="4"  value="${embean.rfSeperator2}" disabled="true"/></td>

                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">3:</td>
                                                    <td><select  name="5" id="5"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild3==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild3!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="6" id="6" value="${embean.rfSeperator3}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">4:</td>
                                                    <td><select  name="7" id="7" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild4==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild4!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="8" id="8" value="${embean.rfSeperator4}" disabled="true" /></td>
                                                </tr>


                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">5:</td>
                                                    <td><select  name="9" id="9" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild5==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild5!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="10" id="10" value="${embean.rfSeperator5}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">6:</td>
                                                    <td><select  name="11" id="11" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild6==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild6!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="12" id="12" value="${embean.rfSeperator6}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">7:</td>
                                                    <td><select  name="13" id="13" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild7==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild7!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="14" id="14"  value="${embean.rfSeperator7}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">8:</td>
                                                    <td><select  name="15" id="15" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild8==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild8!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="16" id="16" value="${embean.rfSeperator8}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">9:</td>
                                                    <td><select  name="17" id="17"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild9==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild9!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="18" id="18" value="${embean.rfSeperator9}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">10:</td>
                                                    <td><select  name="19" id="19" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild10==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild10!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="20" id="20" value="${embean.rfSeperator10}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">11:</td>
                                                    <td><select  name="21" id="21" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild11==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild11!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="22" id="22"  value="${embean.rfSeperator11}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">12:</td>
                                                    <td><select  name="23" id="23" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild12==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild12!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="24" id="24" value="${embean.rfSeperator12}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>



                                                <tr>
                                                    <td style="width: 20px;">13:</td>
                                                    <td><select  name="25" id="25" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild13==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild13!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="26" id="26"  value="${embean.rfSeperator13}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">14:</td>
                                                    <td><select  name="27" id="27" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild14==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild14!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="28" id="28" value="${embean.rfSeperator14}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">15:</td>
                                                    <td><select  name="29" id="29"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild15==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild15!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="30" id="30" value="${embean.rfSeperator15}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">16:</td>
                                                    <td><select  name="31" id="31" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild16==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild16!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="32" id="32" value="${embean.rfSeperator16}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">17:</td>
                                                    <td><select  name="33" id="33" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild17==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild17!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="34" id="34" value="${embean.rfSeperator17}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">18:</td>
                                                    <td><select  name="35" id="35" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild18==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild18!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="36" id="36" value="${embean.rfSeperator18}" disabled="true" /></td>
                                                </tr>

                                                <tr style="width: 10px; "></tr>



                                                <tr>
                                                    <td style="width: 20px;">19:</td>
                                                    <td><select  name="37" id="37" disabled="true" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild19==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild19!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="38" id="38" value="${embean.rfSeperator19}" disabled="true" /></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">20:</td>
                                                    <td><select  name="39" id="39" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild20==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild20!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="40" id="40" value="${embean.rfSeperator20}" disabled="true" /></td>
                                                </tr>

                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">21:</td>
                                                    <td><select  name="41" id="41"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild21==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild21!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="42" id="42" value="${embean.rfSeperator21}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">22:</td>
                                                    <td><select  name="43" id="43" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild22==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild22!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="44" id="44"value="${embean.rfSeperator22}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">23:</td>
                                                    <td><select  name="45" id="45"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild23==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild23!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="46" id="46" value="${embean.rfSeperator23}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">24:</td>
                                                    <td><select  name="47" id="47" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild24==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild24!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="48" id="48"value="${embean.rfSeperator24}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">25:</td>
                                                    <td><select  name="49" id="49"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild25==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild25!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="50" id="50" value="${embean.rfSeperator25}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">26:</td>
                                                    <td><select  name="51" id="51" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild26==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild26!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="52" id="52"value="${embean.rfSeperator26}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">27:</td>
                                                    <td><select  name="53" id="53"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild27==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild27!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="54" id="54" value="${embean.rfSeperator27}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">28:</td>
                                                    <td><select  name="55" id="55" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild28==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild28!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="56" id="56"value="${embean.rfSeperator28}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                                <tr>
                                                    <td style="width: 20px;">29:</td>
                                                    <td><select  name="57" id="57"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild29==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild29!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="58" id="58" value="${embean.rfSeperator29}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">30:</td>
                                                    <td><select  name="59" id="59" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild30==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild30!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="60" id="60"value="${embean.rfSeperator30}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                
                                                <tr>
                                                    <td style="width: 20px;">31:</td>
                                                    <td><select  name="61" id="61"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild31==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild31!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="62" id="62" value="${embean.rfSeperator31}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">32:</td>
                                                    <td><select  name="63" id="63" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild32==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild32!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="64" id="64"value="${embean.rfSeperator32}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">33:</td>
                                                    <td><select  name="65" id="65"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild33==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild33!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="66" id="66" value="${embean.rfSeperator33}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">34:</td>
                                                    <td><select  name="67" id="67" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild34==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild34!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="68" id="68"value="${embean.rfSeperator34}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">35:</td>
                                                    <td><select  name="69" id="69"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild35==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild35!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="70" id="70" value="${embean.rfSeperator35}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">36:</td>
                                                    <td><select  name="71" id="71" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild36==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild36!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="72" id="72"value="${embean.rfSeperator36}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">37:</td>
                                                    <td><select  name="73" id="73"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild37==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild37!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="74" id="74" value="${embean.rfSeperator37}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">38:</td>
                                                    <td><select  name="75" id="75" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild38==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild38!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="76" id="76"value="${embean.rfSeperator38}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>
                                                <tr>
                                                    <td style="width: 20px;">39:</td>
                                                    <td><select  name="77" id="77"  disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild39==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild39!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="78" id="78" value="${embean.rfSeperator39}" disabled="true"/></td>

                                                    <td style="width: 20px;"></td>
                                                    <td style="width: 20px;">40:</td>
                                                    <td><select  name="79" id="79" disabled="true">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="formatdetail" items="${sessionScope.SessionObject.formatDetailList}">
                                                                <c:if test="${embean.rfFeild40==formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" selected>${formatdetail.fieldName}</option>
                                                                </c:if>
                                                                <c:if test="${embean.rfFeild40!=formatdetail.fieldName}">
                                                                    <option value="${formatdetail.fieldName}" >${formatdetail.fieldName}</option>

                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                    <td></td>
                                                    <td><input type="text"  name="80" id="80"value="${embean.rfSeperator40}" disabled="true" /></td>
                                                </tr>
                                                <tr style="width: 10px;"></tr>

                                            </tbody>

                                        </table>

                                        <table border="0">
                                            <tbody>


                                                <tr><input type="hidden" name="oldvalue" value="${oldval}" /></tr>
                                            <tr>
                                                <td></td>

                                                <td><input type="submit" value="Update" name="Update" class="defbutton"/></td>
                                                <td><input onclick="updateEmbossFileFormat('${embean.formatCode}')" type="reset" value="Reset" class="defbutton"/></td>
                                                <td><input onclick="resetForm()" type="reset" value="Back" class="defbutton"/></td>

                                            </tr>
                                            <tr></tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewEmbossFileFormatMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px">Card Type </td>
                                                    <td></td>
                                                    <td>:${embean.cardTypeDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Format Code</td>
                                                    <td></td>
                                                    <td>:${embean.formatCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Description </td>
                                                    <td></td>                                         
                                                    <td>:${embean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 150px">Record Length </td>
                                                    <td></td>                                         
                                                    <td>:${embean.recordLength}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>




                                                <tr>
                                                    <td style="width: 150px">Status </td>
                                                    <td></td>                                         
                                                    <td>:${embean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>



                                                <tr>

                                                    <td style="width: 150px">Record Format </td>
                                                    <td></td>                                         
                                                    <td>:${embean.rFormat1}</td>
                                                </tr>

                                                <tr>

                                                    <td style="width: 150px"> </td>
                                                    <td></td>                                         
                                                    <td>&nbsp;${embean.rFormat2}</td>
                                                </tr>

                                                <tr>

                                                    <td style="width: 150px"> </td>
                                                    <td></td>                                         
                                                    <td>&nbsp;${embean.rFormat3}</td>
                                                </tr>

                                                <tr>

                                                    <td style="width: 150px"> </td>
                                                    <td></td>                                         
                                                    <td>&nbsp;${embean.rFormat4}</td>
                                                </tr>

                                                <tr>

                                                    <td style="width: 150px"> </td>
                                                    <td></td>                                         
                                                    <td>&nbsp;${embean.rFormat5}</td>
                                                </tr>
                                                
                                                <tr>

                                                    <td style="width: 150px"> </td>
                                                    <td></td>                                         
                                                    <td>&nbsp;${embean.rFormat6}</td>
                                                </tr>
                                                
                                                <tr>

                                                    <td style="width: 150px"> </td>
                                                    <td></td>                                         
                                                    <td>&nbsp;${embean.rFormat7}</td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table>
                                            <tr>
                                                <td style="width: 150px"></td>
                                                <td></td>
                                                <td style="width: 300px;">
                                                    <input type="button" value="Back" name="Back" class="defbutton" onclick="resetForm()"/>
                                                </td>

                                            </tr>
                                        </table>


                                    </form>
                                </c:if>



                                <!-- show table data -->
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Card Type</th>
                                                <th>Format Code</th>
                                                <th>Description</th>                                
                                                <th>Status</th>
                                                <th>Record Format</th>

                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach  items="${formatList}" var="format">
                                                <tr>
                                                    <td>${format.cardType}</td>
                                                    <td>${format.formatCode}</td>
                                                    <td>${format.description}</td>
                                                    <td>${format.statusDes}</td>
                                                    <td>${format.recordFormatView}</td>

                                                    <td><a  href='#' onclick="viewEmbossFileFormat('${format.formatCode}')">View</a></td>
                                                    <td><a  href='#' onclick="updateEmbossFileFormat('${format.formatCode}')">Update</a></td>
                                                    <td><a  href='#' onclick="deleteEmbossFileFormat('${format.formatCode}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>





                                        </tbody>

                                    </table>
                                    <input type="hidden" id="id"  name="id" maxlength="16" />
                                </form>

                                <!--   ------------------------- end developer area  -------------------------  -->

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