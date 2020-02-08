<%-- 
    Document   : audittraceview
    Created on : Oct 11, 2012, 8:48:26 AM
    Author     : nisansala
--%>

<%@page import="java.util.List"%>
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

                document.addcarddomainform.action="${pageContext.request.contextPath}/AddCardDomainServlet";
                document.addcarddomainform.submit();

            }
            
            function invokeBack()
            {

                window.location = "${pageContext.request.contextPath}/ProcessSearchSystemAuditServlet?isBack=yes";

            }
            
            //            function invokeReset(){
            //            
            //                window.location = "${pageContext.request.contextPath}/LoadAddCardDomainFormServlet";
            //            }
            //            
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
        <script >
            function settitle(){
               
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SYSTEMAUDIT%>'                                  
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



        <title>EPIC CMS CARD DOMAIN TEMPLATE MANAGEMENT</title>
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

                                <form method="POST" action="" name="addcarddomainform">
                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>

                                            <tr>
                                                <td>System Audit ID</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.systemAuditId}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>User Role</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.userRoleCode}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Description</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.description}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Application</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.applicationCode}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Section</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.sectionCode}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Page</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.pageCode}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Task</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.taskCode}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Remarks</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.remarks}</td>
                                                <td></td>
                                            </tr><tr>
                                                <td>IP</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.ip}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Unique Id</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.uniqueId}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Field Name</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.fieldName}</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>User Name</td>
                                                <td></td>
                                                <td> : </td>
                                                <td>${auditBean.userName}</td>
                                                <td></td>
                                            </tr>

                                            <tr><td></td>
                                                <td></td>
                                                <td></td><td>
                                                    <table  border="1" class="display" id="tableview2" >
                                                        <thead>
                                                            <tr >
                                                                <th>Old Value</th> 
                                                                <th>New Value</th> 

                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:if test="${oldValue != '[]' and newValue != '[]'}">
                                                                <c:forEach var="value1" items="${oldValue}" varStatus="row1"> 
                                                                    <c:forEach var="value2" items="${newValue}" varStatus="row2">
                                                                        <c:if test="${row1.count==row2.count}">
                                                                            <tr>
                                                                                <c:if test="${value1 == null}">  
                                                                                    <td></td> 
                                                                                    <td>${value2}</td> 
                                                                                </c:if>
                                                                                <c:if test="${value2 == null}">  
                                                                                    <td>${value1}</td> 
                                                                                    <td></td> 
                                                                                </c:if>  
                                                                                <c:if test="${value1 != null and value2 != null}">  
                                                                                    <td>${value1}</td> 
                                                                                    <td>${value2}</td> 
                                                                                </c:if>                                                                    
                                                                            </tr>                                                                                    
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </c:forEach>
                                                            </c:if>
                                                            <c:if test="${oldValue == '[]'}">
                                                                <c:forEach var="value2" items="${newValue}" varStatus="row2">
                                                                    <tr>
                                                                        <c:if test="${value2 == null}">
                                                                            <td></td> 
                                                                            <td></td>
                                                                        </c:if>

                                                                        <c:if test="${value2 != null}">
                                                                            <td>${value2}</td> 
                                                                            <td></td>
                                                                        </c:if> 
                                                                    </tr>
                                                                </c:forEach>
                                                            </c:if>
                                                            <c:if test="${newValue == '[]'}">
                                                                <c:forEach var="value1" items="${oldValue}" varStatus="row1">
                                                                    <tr>
                                                                        <c:if test="${value1 == null}">
                                                                            <td></td> 
                                                                            <td></td>
                                                                        </c:if>

                                                                        <c:if test="${value1 != null}">
                                                                            <td>${value1}</td> 
                                                                            <td></td>
                                                                        </c:if>                                                    

                                                                    </tr>
                                                                </c:forEach>
                                                            </c:if>
                                                        </tbody>
                                                    </table></td>

                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td><input type="button" value="Back" Name="back" class="defbutton" onclick="invokeBack()"/></td>
                                                <td></td>
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




