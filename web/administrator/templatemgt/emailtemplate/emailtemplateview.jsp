<%-- 
    Document   : emailtemplateview
    Created on : Jun 15, 2016, 8:00:00 AM
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
            
      
            function invokeAdd()
            {

                document.addemailform.action="${pageContext.request.contextPath}/AddEmailConfServlet";
                document.addemailform.submit();

            }
            
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadEmailConfTempMgtServlet";

            }
                       
            function invokeReset(ele){
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;                
                    }
                }
                
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'radio':
                            tags[i].checked = false;
                            break;                
                    }
                }
                
                tags = ele.getElementsByTagName('label');
                for(i = 0; i < tags.length; i++) {                    
                    tags[i].innerText = '';                    
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }   
            }


        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.EMAILHOME%>'                                  
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


        <title>EPIC CMS EMAIL TEMPLATE MANAGEMENT</title>
    </head>
    <body onload="loadNiceditData();">
        <script src="${pageContext.request.contextPath}/resources/js/nicEdit.js" type="text/javascript"></script>
        <script type="text/javascript">
            var myNicEditor;
            bkLib.onDomLoaded(function() {
                    myNicEditor =new nicEditor({iconsPath : '${pageContext.request.contextPath}/resources/images/nicEditorIcons.gif',buttonList : []}).panelInstance('body');
            });
            
        </script>

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
                                <script type="text/javascript"> 
                                    settitle();
                                   
                                </script>

                                <form method="POST" action="" name="addemailform">
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
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.templateCode}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Template Description</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.description}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Email Subject</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.subject}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Email Body</td>
                                                <td></td>
                                                <td>:</td>
                                                <td><textarea name="body" id="body" cols="100" rows="15" disabled>${templateBean.body}</textarea></td>                                                
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.statusDes}</td>                                                
                                            </tr>
                                        
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>                                                    
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/>
                                                </td>

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

