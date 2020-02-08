<%-- 
    Document   : cardembossingfiledownload
    Created on : May 8, 2012, 11:21:10 AM
    Author     : chanuka
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
            

            function invokeBack()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardEmbossingSearchServlet";

                //                document.hiddendownloadfile.action="${pageContext.request.contextPath}/SearchCardEmbossingServlet";
                //                document.hiddendownloadfile.submit();

            }
            

            function invokeDownload(fileType,fileName,cardType,cardProduct)
            {
                document.getElementById("errormsg").innerHTML='';
                document.getElementById("successmsg").innerHTML='';
                
                document.getElementById("filetype").setAttribute("value", fileType);
                document.getElementById("cardtype").setAttribute("value", cardType);
                document.getElementById("cardproduct").setAttribute("value", cardProduct);
                document.getElementById("filename").setAttribute("value", fileName);

                document.hiddendownloadfile.action="${pageContext.request.contextPath}/DownloadCardEmbossingFileServlet";
                document.hiddendownloadfile.submit();

            }
            

        </script>



        <title>EPIC CMS CREDIT CARD EMBOSSING ENCODING FILE DOWNLOAD</title>
    </head>
    <body>

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

                                <table class="tit"> <tr> <td   class="center"> <B> CREDIT CARD EMBOSSING ENCODING MANAGEMENT </B></td> </tr><tr> <td>&nbsp;</td> </tr></table>


                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="filedownloadform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000" id="errormsg"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green" id="successmsg"> ${successMsg}</font></b> </td>
                                            <td></td>
                                        </tr>
                                    </table>


                                    <table class="tit"> <tr> <td   class="center"> DOWNLOAD EMBOSS FILES </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>File Name</th>
                                                <th>Card Type</th>
                                                <th>Card Product</th>
                                                <th>Download</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="file" items="${sessionScope.SessionObject.cardEmbossingFileList}">
                                                <tr>
                                                    <td >${file.fileName}</td>
                                                    <td >${file.cardType}</td>
                                                    <td >${file.cardProduct}</td>
                                                    <td>
                                                        <input type="button" value="Download" name="download" onclick="invokeDownload('${file.fileType}','${file.fileName}','${file.cardType}','${file.cardProduct}')" />

                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>



                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td style="width: 300px">
                                                    <input type="button" value="Back" name="Back" style="width: 100px" onclick="invokeBack()">
                                                </td>
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>

                                </form>

                                <form method="POST" action="#" name="hiddendownloadfile">

                                    <input id="filetype" name="filetype" type="hidden"/>
                                    <input id="cardtype" name="cardtype" type="hidden"/>
                                    <input id="cardproduct" name="cardproduct" type="hidden"/>
                                    <input id="filename" name="filename" type="hidden"/>

                                </form>        

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


