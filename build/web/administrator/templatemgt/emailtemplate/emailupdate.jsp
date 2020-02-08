<%-- 
    Document   : emailmgt
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
            
            function invokeUpdate()
            {

                document.updatecarddomainform.action="${pageContext.request.contextPath}/UpdateEmailConfServlet";
                document.updatecarddomainform.submit();

            }
            
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadEmailConfTempMgtServlet";

            }
            
            function invokeReset(id){
            
                window.location = "${pageContext.request.contextPath}/LoadUpdateEmailConfFormServlet?templatecode="+id;
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



        <title>EPIC EMAIL TEMPLATE MANAGEMENT</title>
    </head>
    <body>
        <script src="${pageContext.request.contextPath}/resources/js/nicEdit.js" type="text/javascript"></script>
        <script type="text/javascript">
        var myNicEditor;
        bkLib.onDomLoaded(function() {
                myNicEditor =new nicEditor({iconsPath : '${pageContext.request.contextPath}/resources/images/nicEditorIcons.gif',buttonList : ['fontSize','fontFamily','fontFormat','bold','italic','underline','left','center','right','justify','ol','indent','outdent','strikeThrough','subscript','superscript','removeformat','html','link','unlink','image','forecolor']}).panelInstance('body');
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


                                <form method="POST" action="" name="updatecarddomainform">
                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>                                            
                                            <tr >
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Template Code</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templatecode" value="${emailBean.templateCode}" maxlength="6"/>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Template Description</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="description" value="${emailBean.description}"/>
                                                </td>
                                                <td></td>
                                            </tr>
                                            
                                            <tr>
                                                <td>Email Subject</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="subject" value="${emailBean.subject}" size="98"/>
                                                </td>
                                                <td></td>
                                            </tr>
                                            
                                            <tr>
                                                <td>Email Body</td>
                                                <td><font style="color: red;padding-right: 5px;">*</font>&nbsp;
                                                    <div style="background-color: white;float: right;margin-right: 10px;">
                                                        <textarea name="body" id="body" cols="100" rows="15" style="width: 100%;">${emailBean.body}</textarea>
                                                    </div>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Status </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="status" style="width: 163px">
                                                        <option value="" >------SELECT------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${emailBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${emailBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                                <td></td>
                                                <td>&nbsp;&nbsp;&nbsp;
                                                    <input type="submit" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/>
                                                    <input type="button" value="Reset" name="reset"  class="defbutton" onclick="invokeReset('${emailBean.templateCode}')" />
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
                <div class="clearer"><span></span></div>
            </div>

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>



