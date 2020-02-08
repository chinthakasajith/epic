<%-- 
    Document   : commonfiledownloadhome
    Created on : May 9, 2012, 3:51:23 PM
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
            

            function invokeSearch()
            {

                document.searchfiledownloadform.action="${pageContext.request.contextPath}/SearchCommonFileDownloadServlet";
                document.searchfiledownloadform.submit();

            }
            

            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCommonFileDownloadServlet";

            }
            

            function invokeDownload(fileType,fileName,cardType,cardProduct)
            {
                document.getElementById("errormsg").innerHTML='';
                document.getElementById("successmsg").innerHTML='';
                
                document.getElementById("filetype").setAttribute("value", fileType);
                document.getElementById("cardtype").setAttribute("value", cardType);
                document.getElementById("cardproduct").setAttribute("value", cardProduct);
                document.getElementById("filename").setAttribute("value", fileName);

                document.hiddendownloadfiles.action="${pageContext.request.contextPath}/DownloadCommonFilesServlet";
                document.hiddendownloadfiles.submit();

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.COMMONFILEDOWNLOAD%>'                                  
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



        <title>EPIC CMS COMMON FILE DOWNLOAD</title>
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

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchfiledownloadform">


                                    <table>
                                        <tr>
                                            <td><b><font color="Red"> <label id="errormsg">${errorMsg}</label></font></b> </td>
                                            <td><b><font color="green"> <label id="successmsg">${successMsg}</label></font> </b></td>
                                            <td style="height: 30px"></td>

                                        </tr>
                                    </table>

                                    <table border="0">

                                        <tbody>



                                            <tr>
                                                <td style="width: 180px">File Type</td>
                                                <td> 
                                                    <select style="width: 100px" name="filetype">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="file" items="${sessionScope.SessionObject.fileTypeList}">
                                                            <c:if test="${searchfilebean.fileType==file.key}">
                                                                <option value="${file.key}" selected>${file.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchfilebean.fileType != file.key}">
                                                                <option value="${file.key}" >${file.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     





                                            <tr>
                                                <td style="width: 180px"> File Name</td>
                                                <td><input type="text"  value="${searchfilebean.fileName}" name="filename" maxlength="20"></td>
                                                <td></td>
                                            </tr>



                                            <tr><td style="height: 5px"></td></tr>     




                                            <tr>
                                                <td style="width: 180px">Generate User </td>
                                                <td>
                                                    <select style="width: 100px"  name="generateduser">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="user" items="${sessionScope.SessionObject.usersList}">
                                                            <c:if test="${searchfilebean.generateUser==user}">
                                                                <option value="${user}" selected>${user}</option>
                                                            </c:if>
                                                            <c:if test="${searchfilebean.generateUser!=user}">
                                                                <option value="${user}" >${user}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     

                                            <tr>
                                                <td>From Date</td>
                                                <td>
                                                    <input  name="fromdate" maxlength="16" readonly class="inputfeild"value="${searchfilebean.fromDate}" key="fromdate" id="fromdate"  />
                                                    <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                    </script>

                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td>To Date</td>
                                                <td>
                                                    <input name="todate" maxlength="16"  readonly class="inputfeild"value="${searchfilebean.toDate}" key="todate" id="todate" />
                                                    <script type="text/javascript">
                                                            $(function() {
                                                                $( "#todate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                    </script>
                                                </td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>



                                    <table>
                                        <tbody>



                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td style="width: 180px"></td>
                                                <td style="width: 300px"><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.COMMONFILEDOWNLOAD%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     

                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>









                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>File Type</th>
                                            <th>File Name</th>
                                            <th>Card Type</th>
                                            <th>Card Product</th>
                                            <th>Generate User</th>
                                            <th>Letter Type</th>
                                            <th>Create Time</th>
                                            <th>Download</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="file" items="${searchList}">
                                            <tr>
                                                <td >${file.fileTypeDes}</td>
                                                <td >${file.fileName}</td>
                                                <td >${file.cardTypeDes}</td>
                                                <td >${file.cardProductDes}</td>
                                                <td >${file.generatedUser}</td>
                                                <td >${file.letterType}</td>
                                                <td >${file.createTime}</td>
                                                <td >
                                                    <input type="button" value="Download" name="Download" onclick="invokeDownload('${file.fileType}','${file.fileName}','${file.cardTypeDes}','${file.cardProductDes}')"/>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <form method="POST" action="#" name="hiddendownloadfiles">

                                    <input id="filetype" name="filetype" type="hidden"/>
                                    <input id="cardtype" name="cardtype" type="hidden"/>
                                    <input id="cardproduct" name="cardproduct" type="hidden"/>
                                    <input id="filename" name="filename" type="hidden"/>

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


