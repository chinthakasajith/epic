<%-- 
    Document   : channeladminmessages
    Created on : Sep 6, 2016, 4:00:55 PM
    Author     : nipun_t
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />



        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";
        </style>

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>

        <title>EPIC_CMS_HOME</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <script  type="text/javascript" charset="utf-8">
            function invokeSend()
            {
                document.sendChannelForm.action = "${pageContext.request.contextPath}/SendChannelAdminMessagesServlet";
                document.sendChannelForm.submit();



            }
            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadChannelAdminMessagesServlet";

            }




        </script>   
        <script>


            $(document).ready(function () {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CHANNELADMINMESSAGE%>'
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
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Add(also default) view  ///////////////////////////////////////////////////////////-->


                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                <c:if test="${operationtype=='ADD'}">
                                    <form action="" method="POST" name="sendChannelForm" >
                                        <br/>
                                        <table> <tr> <td> <b> Channel Information : VISA </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 180px;">IP</td>
                                                    <td><b><i><font color="#000000" style="font-size: 12px;"> ${channelAdminMessageInfo.ip}</font> </i></b></td>
                                                    <td><input type="hidden" value="${channelAdminMessageInfo.ip}" name="ip" /></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 180px;">Port</td>
                                                    <td><b><i><font color="#000000" style="font-size: 12px;"> ${channelAdminMessageInfo.port}</font></i></b></td>
                                                    <td><input type="hidden" value="${channelAdminMessageInfo.port}" name="port" /></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 180px;">Time Out(ms)</td>
                                                    <td><b><i><font color="#000000" style="font-size: 12px;"> ${channelAdminMessageInfo.timeOut}</font></i></b> </td>
                                                    <td><input type="hidden" value="${channelAdminMessageInfo.timeOut}" name="timeOut" /></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 180px;">Connection Type</td>
                                                    <td><b><i><font color="#000000" style="font-size: 12px;"> ${channelAdminMessageInfo.connectionType}</font></i></b> </td>
                                                    <td><input type="hidden" value="${channelAdminMessageInfo.connectionType}" name="connectionType" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Operation</td>
                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <select name="selectOperationType"  class="inputfield-mandatory">
                                                            <option value="">--SELECT--</option>

                                                            <option value="11">SIGN ON</option>
                                                            <option value="12">SIGN OFF</option>
                                                            <option value="10">ECHO</option>
                                                            <option value="13">ADVICE RETRIEVAL ON</option>
                                                            <option value="14">ADVICE RETRIEVAL OFF</option>
                                                            <option value="15">KEY EXCHANGE</option>
                                                            <option value="16">EXCEPTION FILE ADD</option>
                                                            <option value="17">EXCEPTION FILE UPDATE</option>
                                                            <option value="18">EXCEPTION FILE INQUIRY</option>
                                                            <option value="19">EXCEPTION FILE DELETE</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr></tr>
                                                <tr></tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="add" value="Send" onclick="invokeSend()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset()"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

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

