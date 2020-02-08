<%-- 
    Document   : logmanagementhome
    Created on : Jul 18, 2012, 10:56:52 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
                
            
                
                
                function invokeReset(){

                    window.location = "${pageContext.request.contextPath}/LoadLogMgtServlet";

                }
            
                function invokeUpdate()
                {
                    answer = confirm("Are you sure you want to update?")
                    
                    if (answer !=0)
                    {
                        document.logManagementForm.action="${pageContext.request.contextPath}/UpdateLogMgtServlet";
                        document.logManagementForm.submit();

                        // window.location="${pageContext.request.contextPath}/UpdateServermainConfigServlet";
                    }
                    else
                    {
                        window.location="${pageContext.request.contextPath}/LoadLogMgtServlet";
                    }

                   
                }
                
            
            
                

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LOGMGT%>'                                  
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



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">



                                    <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                    <script> settitle();</script>
                                    
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>
                                <!--this will shown when the user given value is invalid  -->

                                <c:if test="${operationtype=='ADD'}" >
                                    <form action="" method="POST" name="logManagementForm">

                                        <table border="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td>Log Level</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="logLevel" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="log" items="${logLevelList}">

                                                                <c:if test="${logBean.logLevel==log.code}">
                                                                    <option value="${log.code}" selected="true" >${log.description}</option>
                                                                </c:if>
                                                                <c:if test="${logBean.logLevel!=log.code}">
                                                                    <option value="${log.code}">${log.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Log File Name</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="logFileName" value="${logBean.logFileName}" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Log Backup Path</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="logBackUpPath" value="${logBean.logBackUpPath}" maxlength="100"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Log Backup Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="logBackUpStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${logBean.logBackUpStatus==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${logBean.logBackUpStatus!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Synchronization Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="synStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${logBean.synStatus==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${logBean.synStatus!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Synchronization Period</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="synPeriod" value="${logBean.synPeriod}" maxlength="2"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Number of Log File</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="numOfLogFile" value="${logBean.numOfLogFile}" maxlength="3"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                        <table border="0" cellspacing="10">
                                            <tbody>
                                                <tr>  
                                                    <td></td>
                                                    <td>
                                                        <input type="submit" value="Update" name="update" style=" width:100px" onclick="invokeUpdate()"/>

                                                    </td>
                                                    <td>
                                                        <input type="button" value="Reset" name="reset" style=" width:100px" onclick="invokeReset()"/>
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