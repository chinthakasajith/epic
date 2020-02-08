<%-- 
    Document   : fileinfomgthome
    Created on : Jan 24, 2013, 9:14:10 AM
    Author     : jeevan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            function setBox() {
	//document.getElementById('${fileInfoBean.fileId}').readOnly = (document.getElementById('optionsmenu').value == 'none')? 0 : 1;
        document.getElementById('optionsmenu').disabled=true;
        }

           
            function backFileTypeForm(){
                window.location = "${pageContext.request.contextPath}/LoadEODFileInfoMgtServlet"
            }
                       
            function resetFileTypeForm() {
                window.location = "${pageContext.request.contextPath}/LoadEODFileInfoMgtServlet"
            }
            
            function viewBank(value){
                window.location = "${pageContext.request.contextPath}/ViewBankMgtServlet?id="+value;
            }
            
            function viewFileInfo(value) {
                window.location = "${pageContext.request.contextPath}/ViewEODFileInfoMgtServlet?id="+value;
            }
                        
            function updateFileInfo(value) {
                document.viewTableForm.action = "${pageContext.request.contextPath}/UpdateEODFileInfoLoadServlet?id="+value;
                document.viewTableForm.submit();
            }
            
            function updateBank(value){
                document.viewTableForm.action="${pageContext.request.contextPath}/UpdateBankMgtLoadServlet";           
                document.getElementById('id').value=value;    
                document.viewTableForm.submit();
                // window.location = "${pageContext.request.contextPath}/UpdateBankMgtLoadServlet?id="+value;
            }
                       
            function deleteFileInfo(value) {
                answer = confirm("Do you really want to delete "+value+" file?")
                if (answer != 0) {
                    window.location = "${pageContext.request.contextPath}/DeleteEODFileInfoMgtServlet?id="+value;
                } else {
                    window.location = "${pageContext.request.contextPath}/LoadEODFileInfoMgtServlet"
                }
            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.FILE_INFO%>'                                  
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
    </head>
    <body>
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
                                <!--  ----------------------start  developer area  -----------------------------------    -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}">
                                    <form method="post" action="${pageContext.request.contextPath}/AddEODFileInfoMgtServlet">
                                        <table border="0">
                                            <tbody>

                                                <tr>
                                                    <td style="width: 120px;">File Info Code</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="fileinfocode" maxlength="12" value="${fileInfoBean.fileId}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Type</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                         <select name="fileInfoFileType"  >
                                                            <option  value="">--SELECT--</option>
                                                            
                                                            <c:forEach var="fileTypeBean" items="${fileTypelist}">
                                                                <c:if test="${fileInfoBean.fileType!=fileTypeBean.fileTypeCode}">
                                                                    <option value="${fileTypeBean.fileTypeCode}" >${fileTypeBean.description}</option>
                                                                </c:if>
                                                                <c:if test="${fileInfoBean.fileType==fileTypeBean.fileTypeCode}">
                                                                    <option value="${fileTypeBean.fileTypeCode}" selected="true" >${fileTypeBean.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr> 
                                                    <td style="width: 120px;">Description</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="64" value="${fileInfoBean.fileName}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Path</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="filepath" maxlength="1024" value="${fileInfoBean.filePath}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">Backup Path</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="backuppath" maxlength="1024" value="${fileInfoBean.backupPath}"/></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="submit" value="Add" style="width:100px;"/>
                                                        <input type="reset" value="Reset" style="width:100px;" onclick="resetFileTypeForm()"/>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>   

                                <c:if test="${operationtype=='update'}">
                                    <form method="post" action="${pageContext.request.contextPath}/UpdateEODFileInfoMgtServlet">
                                        <table border="0">
 
                                            <tbody>
                                              
                                                 <tr>
                                                    <td style="width: 120px;">File Info Code</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="fileinfocode" readonly="true" maxlength="12" value="${fileInfoBean.fileId}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Type</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="fileInfoFileType" disabled="true">
                                                            <option  value="">--SELECT--</option>
                                                            
                                                            <c:forEach var="fileTypeBean" items="${fileTypelist}">
                                                                <c:if test="${fileInfoBean.fileType!=fileTypeBean.fileTypeCode}">
                                                                    <option value="${fileTypeBean.fileTypeCode}" >${fileTypeBean.description}</option>
                                                                </c:if>
                                                                <c:if test="${fileInfoBean.fileType==fileTypeBean.fileTypeCode}">
                                                                    <option value="${fileTypeBean.fileTypeCode}" selected="true">${fileTypeBean.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td><input type="hidden" name="disabledFileType" value="${fileInfoBean.fileType}"/></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr> 
                                                    <td style="width: 120px;">Description</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="64" value="${fileInfoBean.fileName}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Path</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="filepath" maxlength="1024" value="${fileInfoBean.filePath}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">Backup Path</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="backuppath" maxlength="1024" value="${fileInfoBean.backupPath}"/></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>


                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldVal}"/></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>

                                        </table>

                                        <table border="0">
                                            <tr>
                                                <td style="width: 133px;"></td>
                                                <td><input type="submit" value="Update" onclick="updateFileInfo('${fileInfoBean.fileId}')" class="defbutton"/></td>
<!--                                                <td><input type="reset" value="Reset" onclick="resetFileTypeForm()" class="defbutton"/></td>-->
                                                <td><input type="reset" value="Reset" onclick="updateFileInfo('${fileInfoBean.fileId}')" class="defbutton"/></td>
                                                <td><input type="button" value="Back" onclick="backFileTypeForm()" class="defbutton"/></td>
                                            </tr>
                                        </table>
                                    </form>
                                </c:if>        

                                <c:if test="${operationtype=='view'}">
                                    <form method="post" action="${pageContext.request.contextPath}/ViewEODFileInfoMgtServlet">
                                        <table border="0">
                                            <tbody>

                                                <tr>
                                                    <td>File Info Code</td>
                                                    <td></td>
                                                    <td>:${fileInfoBean.fileId}</td>
                                                </tr>

                                                <tr>
                                                    <td>File Type</td>
                                                    <td></td>
                                                    <td>:${fileInfoBean.fileType}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>File Description</td>
                                                    <td></td>
                                                    <td>:${fileInfoBean.fileName}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>File Path</td>
                                                    <td></td>
                                                    <td>:${fileInfoBean.filePath}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Backup Path</td>
                                                    <td></td>
                                                    <td>:${fileInfoBean.backupPath}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" onclick="backFileTypeForm()" style="width: 100px"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>        



                                <!-- show table data -->


                                <br>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>File Info Code</th>
                                                <th>File Type</th>
                                                <th>Description</th>
                                                <th>File Path</th>
                                                <th>Backup Path Path</th>
                                                <th>View</th>
                                                <th>Update</th>
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${fileList}" var="fileInfoBean">
                                                <tr>
                                                    <td>${fileInfoBean.fileId}</td>
                                                    <td>${fileInfoBean.fileType}</td>
                                                    <td>${fileInfoBean.fileName}</td>
                                                    <td>${fileInfoBean.filePath}</td>
                                                    <td>${fileInfoBean.backupPath}</td>
                                                    <td><a  href="#" onclick="viewFileInfo('${fileInfoBean.fileId}')">View</a></td>
                                                    <td><a  href='#' onclick="updateFileInfo('${fileInfoBean.fileId}')">Update</a></td>
                                                    <td><a  href='#' onclick="deleteFileInfo('${fileInfoBean.fileId}')">Delete</a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </form>


                                <!--   ------------------------- end developer area  --------------------------------  -->

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

