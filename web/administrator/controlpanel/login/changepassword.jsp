<%-- 
    Document   : changepassword
    Created on : Sep 6, 2012, 8:40:57 AM
    Author     : nisansala
--%>
<%@page import="com.epic.cms.system.util.session.SessionUser"%>
<%@page import="com.epic.cms.system.util.session.SessionVarName"%>
<%@page import="com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean"%>
<%@page import="com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.epic.cms.system.util.session.SessionVarList"%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
          
           
        </script>  
        <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );
                
            
                function Areset(){
                   
                    window.location = "${pageContext.request.contextPath}/ViewChangePasswordServlet";

                }
                
                function invokeResetSecondary(ele){
                              
                    tags = ele.getElementsByTagName('input');
                    for(i = 0; i < tags.length; i++) {
                        switch(tags[i].type) { case 'text':
                                tags[i].value = '';
                                break;
               
                        }
                    }
                }
                
                
                function invokeReset(ele){
                    tags = ele.getElementsByTagName('input');
                    for(i = 0; i < tags.length; i++) {
                        switch(tags[i].type) {
                            case 'password':
                                tags[i].value = '';
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
            
                function invokeUpdate()
                {

                    document.updateMerchant.action="${pageContext.request.contextPath}/UpdateConfirmedMerchantCategoryServlet";
                    document.updateMerchant.submit();

                }
                
                function invokeLoadUpdate(mcc){
                    document.updateMerchant.action="${pageContext.request.contextPath}/UpdateMerchantCategoryServlet?id="+mcc;
                    document.updateMerchant.submit();
                    
                }
            
            
                
                
                function invokeResetTest(){
                
                    //document.getElementById('form').reset();
                    
                    $('#form').get(0).reset();

                
                    //                document.form.reset();
                    //                document.forms.form.reset();
                    //                document.forms['form'].reset();

                }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SYSTEMUSER%>'                                  
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

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >
                    
                    
                    <%
                        SessionVarList sessinvarlist = null;
                        //List<TESTUserRoleBean>  userRoleList = null ;
                        Map<SectionBean, List<PageBean>> sectionPageList = null;

                        sessinvarlist = (SessionVarList) request.getSession().getAttribute(SessionVarName.SESSION_OBJ);
                        SessionUser user = null;
                        user = sessinvarlist.getCMSSessionUser();
                        if (user != null) {
                            // userRoleList=user.getUserRoleList();
                            sectionPageList = user.getSectionPageList();
                        }
                    %>

                    <td class="acstable-menubar">
                        
                    <% if(sectionPageList!=null) {  %> 
                        <jsp:include page="/leftmenu.jsp"/>
                    <% } %> 
                    
                    </td>

                </div>


                <div id="content1">


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

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                    <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->


                                    <form method="POST" id="form" name="form" action="${pageContext.request.contextPath}/UpdateChangePasswordServlet">
                                    <table border="0" cellspacing="10" cellpadding="0">

                                        <tbody>
                                            <tr>
                                                <td colspan="3">Note: you can't reuse your old password once you change it!</td>
                                            </tr>
                                            <tr></tr>
                                            <tr>
                                                <td>Current Password </td>
                                                <td>&nbsp;</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="password" name="current" value="${userBean.currentPassword}" maxlength="32" /></td>
                                                <td style="width: 300px;" > <label style="color: red">${invalidMsgBean.currentPassword} </label></td>
                                            </tr>
                                            <tr>
                                                <td>New Password </td>
                                                <td>&nbsp;</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="password" name="new" value="${userBean.password}" maxlength="32" /></td>
                                                <td style="width: 300px;" > <label style="color: red">${invalidMsgBean.password} </label></td>
                                            </tr>
                                            <c:if test="${invalidMsgBean.psWdMatch != null}">
                                                <tr>
                                                    <td></td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;&nbsp;&nbsp;
                                                        <label style="color: red"  name="match" >${invalidMsgBean.psWdMatch}</label>    
                                                    </td>
                                                </tr>

                                            </c:if>                                         

                                            <tr>
                                                <td>Confirm Password </td>
                                                <td>&nbsp;</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="password" name="confirm" value="${userBean.confirmPassword}" maxlength="32" /></td>
                                                <td style="width: 300px;" > <label style="color: red">${invalidMsgBean.confirmPassword} </label></td>
                                            </tr>
                                            <tr></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>

                                                <td>&nbsp;&nbsp;
                                                    <input type="submit" style=" width:100px" name="change" value="Change" /> 
                                                    <input type="button" style=" width:100px" name="next" value="Reset" onclick="invokeReset(this.form)"/></td>
                                                <td></td>

                                            </tr>

                                        </tbody>
                                    </table>  
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

