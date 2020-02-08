<%-- 
    Document   : filetypemgthome
    Created on : Jan 22, 2013, 9:12:55 AM
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
           
            function backFileTypeForm(){
                window.location = "${pageContext.request.contextPath}/LoadEODFileTypeMgtServlet"
            }
                       
            function resetFileTypeForm() {
                window.location = "${pageContext.request.contextPath}/LoadEODFileTypeMgtServlet"
            }
            
            function viewBank(value){
                window.location = "${pageContext.request.contextPath}/ViewBankMgtServlet?id="+value;
            }
            
            function viewFileType(value) {
                window.location = "${pageContext.request.contextPath}/ViewFileTypeMgtServlet?id="+value;
            }
            
            function updateFileType(value) {
                document.viewTableForm.action = "${pageContext.request.contextPath}/UpdateEODFileTypeLoadServlet?id="+value;;
                //doucment.getElementById('id').value=value;
                document.viewTableForm.submit();
            }
            function updateBank(value){
                document.viewTableForm.action="${pageContext.request.contextPath}/UpdateBankMgtLoadServlet";           
                document.getElementById('id').value=value;    
                document.viewTableForm.submit();
                // window.location = "${pageContext.request.contextPath}/UpdateBankMgtLoadServlet?id="+value;
            }
            function deleteBank(value){
             
                answer = confirm("Do you really want to delete "+value+" Bank?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteBankMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadBankMgtServlet";
                }

            }
            
            function deleteFileType(value) {
                answer = confirm("Do you really want to delete "+value+" file?")
                if (answer != 0) {
                    window.location = "${pageContext.request.contextPath}/DeleteEODFileTypeMgtServlet?id="+value;
                } else {
                    window.location = "${pageContext.request.contextPath}/LoadEODFileTypeMgtServlet"
                }
            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.FILE_TYPE%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('?');
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
                                    <form method="post" action="${pageContext.request.contextPath}/AddEODFileTypeMgtServlet">
                                        <table border="0">
                                            <tbody>

                                                <tr>
                                                    <td width="120">File Type Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="filetypecode" maxlength="8" value="${fileBean.fileTypeCode}"/></td>
                                                </tr>

                                                <tr>
                                                    <td width="120">File Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="description" maxlength="64" value="${fileBean.description}"/></td>
                                                </tr>

                                                <tr> 
                                                    <td width="120">File Name Prefix</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="filenameprefix" maxlength="32" value="${fileBean.fileNamePrefix}"/></td>
                                                </tr>

                                                <tr>
                                                    <td width="120">File Name Postfix</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="filenamepostfix" maxlength="32" value="${fileBean.fileNamePostfix}"/></td>
                                                </tr>

                                                <tr>
                                                    <td width="120">File Extension</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="extension" maxlength="8" value="${fileBean.fileExtension}"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="cardType" style="width: 163px">
                                                            <option value="">--SELECT--</option>   

                                                            <c:forEach var="cardtype" items="${cardTypeList}">
                                                                <c:if test="${fileBean.cardTypeCode == cardtype.cardTypeCode}">
                                                                    <option value="${cardtype.cardTypeCode}" selected="true">${cardtype.cardTypeDescription}</option>
                                                                </c:if>
                                                                <c:if test="${fileBean.cardTypeCode != cardtype.cardTypeCode}">
                                                                    <option value="${cardtype.cardTypeCode}">${cardtype.cardTypeDescription}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="cardDomain" style="width: 163px">
                                                            <option value="">--SELECT--</option>   

                                                            <c:forEach var="domaintype" items="${cardDomainList}">
                                                                <c:if test="${fileBean.domainId == domaintype.domainId}">
                                                                    <option value="${domaintype.domainId}" selected="true">${domaintype.domainDescription}</option>
                                                                </c:if>
                                                                <c:if test="${fileBean.domainId != domaintype.domainId}">
                                                                    <option value="${domaintype.domainId}">${domaintype.domainDescription}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Sending Channels</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="sendingChannle" style="width: 163px">  
                                                            <option value="">--SELECT--</option>   

                                                            <c:forEach var="channel" items="${channelList}">
                                                                <c:if test="${fileBean.channelType == channel.channelType}">
                                                                    <option value="${channel.channelType}" selected="true">${channel.channelDescription}</option>
                                                                </c:if>
                                                                <c:if test="${fileBean.channelType != channel.channelType  }">
                                                                    <option value="${channel.channelType}">${channel.channelDescription}</option>
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
                                    <form method="post" action="${pageContext.request.contextPath}/UpdateEODFileTypeMgtServlet">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td style="width: 120px;">File Type Code</td>
                                                    <td></td>
                                                    <td><input type="text" name="filetypecode" readonly="true" maxlength="8" value="${fileBean.fileTypeCode}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Description</td>
                                                    <td></td>
                                                    <td><input type="text" name="description" maxlength="64" value="${fileBean.description}"/></td>
                                                </tr>

                                                <tr> 
                                                    <td style="width: 120px;">File Name Prefix</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="filenameprefix" maxlength="32" value="${fileBean.fileNamePrefix}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Name Postfix</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="filenamepostfix" maxlength="32" value="${fileBean.fileNamePostfix}"/></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 120px;">File Extension</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="text" name="extension" maxlength="8" value="${fileBean.fileExtension}"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="cardType" style="width: 163px">
                                                            <option value="">--SELECT--</option>   

                                                            <c:forEach var="cardtype" items="${cardTypeList}">
                                                                <c:if test="${fileBean.cardTypeCode == cardtype.cardTypeCode}">
                                                                    <option value="${cardtype.cardTypeCode}" selected="true">${cardtype.cardTypeDescription}</option>
                                                                </c:if>
                                                                <c:if test="${fileBean.cardTypeCode != cardtype.cardTypeCode}">
                                                                    <option value="${cardtype.cardTypeCode}">${cardtype.cardTypeDescription}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="cardDomain" style="width: 163px">
                                                            <option value="">--SELECT--</option>   

                                                            <c:forEach var="domaintype" items="${cardDomainList}">
                                                                <c:if test="${fileBean.domainId == domaintype.domainId}">
                                                                    <option value="${domaintype.domainId}" selected="true">${domaintype.domainDescription}</option>
                                                                </c:if>
                                                                <c:if test="${fileBean.domainId != domaintype.domainId}">
                                                                    <option value="${domaintype.domainId}">${domaintype.domainDescription}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Sending Channels</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="sendingChannle" style="width: 163px">  
                                                            <option value="">--SELECT--</option>   

                                                            <c:forEach var="channel" items="${channelList}">
                                                                <c:if test="${fileBean.channelType == channel.channelType}">
                                                                    <option value="${channel.channelType}" selected="true">${channel.channelDescription}</option>
                                                                </c:if>
                                                                <c:if test="${fileBean.channelType != channel.channelType  }">
                                                                    <option value="${channel.channelType}">${channel.channelDescription}</option>
                                                                </c:if>    
                                                            </c:forEach>
                                                        </select>
                                                    </td>
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
                                                <td><input type="submit" value="Update" onclick="updateFileType('${fileBean.fileTypeCode}')" class="defbutton"/></td>
                                                <td><input type="reset" value="Reset" onclick="updateFileType('${fileBean.fileTypeCode}')" class="defbutton"/></td>
                                                <td><input type="button" value="Back" onclick="backFileTypeForm()" class="defbutton"/></td>
                                            </tr>
                                        </table>
                                    </form>
                                </c:if>        

                                <c:if test="${operationtype=='view'}">
                                    <form method="post" action="${pageContext.request.contextPath}/ViewFileTypeMgtServlet">
                                        <table border="0">
                                            <tbody>

                                                <tr>
                                                    <td>File Type Code</td>
                                                    <td></td>
                                                    <td>:${fileBean.fileTypeCode}</td>
                                                </tr>

                                                <tr>
                                                    <td>File Description</td>
                                                    <td></td>
                                                    <td>:${fileBean.description}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>File Name Prefix</td>
                                                    <td></td>
                                                    <td>:${fileBean.fileNamePrefix}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>File Name Postfix</td>
                                                    <td></td>
                                                    <td>:${fileBean.fileNamePostfix}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>File Extension</td>
                                                    <td></td>
                                                    <td>:${fileBean.fileExtension}</td>
                                                </tr>

                                                <tr>
                                                    <td>Card Type</td>
                                                    <td></td>
                                                    <td>:${fileBean.cardTypeDescription}</td>
                                                </tr>

                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td></td>
                                                    <td>:${fileBean.domainDescription}</td>
                                                </tr>

                                                <tr>
                                                    <td>Sending Channel</td>
                                                    <td></td>
                                                    <td>:${fileBean.channelDescription}</td>
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
                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>File Type Code</th>
                                                <th>File Description</th>
                                                <th>File Type Prefix</th>
                                                <th>File Extension</th>
                                                <th>Card Type</th>
                                                <th>Card Domain</th>
<!--                                                <th>Sending Channels</th>-->
                                                <th>View</th>
                                                <th>Update</th>
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${fileList}" var="fileBean">
                                                <tr>
                                                    <td>${fileBean.fileTypeCode}</td>
                                                    <td>${fileBean.description}</td>
                                                    <td>${fileBean.fileNamePrefix}</td>
                                                    <td>${fileBean.fileExtension}</td>
                                                    <td>${fileBean.cardTypeDescription}</td>
                                                    <td>${fileBean.domainDescription}</td>
<!--                                                    <td>${fileBean.channelDescription}</td>-->
                                                    <td><a  href="#" onclick="viewFileType('${fileBean.fileTypeCode}')">View</a></td>
                                                    <td><a  href='#' onclick="updateFileType('${fileBean.fileTypeCode}')">Update</a></td>
                                                    <td><a  href='#' onclick="deleteFileType('${fileBean.fileTypeCode}')">Delete</a></td>
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

