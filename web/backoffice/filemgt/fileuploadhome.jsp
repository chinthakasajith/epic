<%-- 
    Document   : fileuploadhome
    Created on : Jan 11, 2013, 2:05:19 PM
    Author     : jeevan
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
            
            function resetCardTypeForm(){
                window.location = "${pageContext.request.contextPath}/LoadIncomingFileUploadServlet";
            }
            function viewCardType(value){
                window.location = "${pageContext.request.contextPath}/ViewCardTypeMgtServlet?id="+value;
            }
            function updateViewCardType(value){
                window.location = "${pageContext.request.contextPath}/UpdateCardTypeMgtViewServlet?id="+value;
            }
            //            function updateCardType(value){
            //                window.location = "${pageContext.request.contextPath}/UpdateBankBrachMgtLoadServlet?id="+value;
            //            }
            function deleteCardType(value){
                
                answer = confirm("Do you really want to delete "+value+" Crad Type ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteCardTypeMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCardTypeMgtServlet";
                }
            }
          
        </script>
        
        
         <style type="text/css">
            #load{
               
                position:absolute;
                z-index:1;
               
                background:#f7f7f7;
                width:450px;
                height:250px;
                margin-top:-150px;
                margin-left:-200px;
                top:38%;
                left:50%;
                text-align:center;
                line-height:300px;
                font-family:"Trebuchet MS", verdana, arial,tahoma;
                font-size:18pt;
                background-color: #66CCFF;
                background: url(${pageContext.request.contextPath}/resources/images/windowimages/ajax-loader.gif) no-repeat center;
              
              
                                   
            }
        </style>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.INCOMING_FILE_UPLOAD%>'                                  
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

                    <jsp:include page="/leftmenu.jsp"/>

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
                                    <div id="load" style="display:none;">File uploading .... Please wait</div>
                                    <form method="POST" action="${pageContext.request.contextPath}/AddFileUploadServletInfoServlet" enctype="multipart/form-data">

                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td>File Type</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td>

                                                        <select name="fileInfoFileType"  >
                                                            <option  value="">--SELECT--</option>
                                                            
                                                            <c:forEach var="fileTypeBean" items="${fileTypelist}">
                                                                <%--  <c:if test="${drpvalue!=fileTypeBean.fileTypeCode}">  --%>
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
                                                    <td>Select File</td>
                                                    <td><font style="color:red">*</font>&nbsp;</td>
                                                    <td><input type="file" class="inputfield-Description-mandatory" name="fileInfoFilePath" value="<c:out value="${fileTypeBean.filePath}"></c:out>" maxlength="1024"></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="submit" value="Upload" style="width: 100px;"/>
                                                        <input type="reset" value="Reset" onclick="resetCardTypeForm()" style="width: 100px;"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <br>            
                                <table border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>File ID</th>
                                            <th>File Name</th>
                                            <th>Uploaded Time</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.filellList}" var="filellList">
                                            <tr>

                                                <td>${filellList.fileId}</td>
                                                <td>${filellList.fileName}</td>
                                                <td>${filellList.uploadedTime}</td>
                                                <td>${filellList.status}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>



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
