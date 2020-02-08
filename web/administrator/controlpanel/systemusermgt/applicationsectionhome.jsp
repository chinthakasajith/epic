<%-- 
    Document   : sectionpagehome
    Created on : Jan 13, 2012, 9:33:52 AM
    Author     : upul
--%>


<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>

        <title>EPIC_CMS_HOME</title>
        <script language = "javascript">
              function invoke()
              {
                  document.applicationsectionmgthome.action="${pageContext.request.contextPath}/ManageApplicationSectionFormServlet";
                  document.applicationsectionmgthome.submit();
              }
              function selectAll(selectBox1,selectBox2) {
                  for (var i = 0; i < selectBox1.options.length; i++) {
                      selectBox1.options[i].selected = true;
                  }
                  for (var i = 0; i < selectBox2.options.length; i++) {
                      selectBox2.options[i].selected = true;
                  }
                  document.applicationsectionmgthome.action="${pageContext.request.contextPath}/AddUpdateApplicationSectionServlet";
                  document.applicationsectionmgthome.submit();
              }
            
              function invokeBack(){
                
                  window.location = "${pageContext.request.contextPath}/LoadSectionPageMgtServlet";
              }
        </script>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script >
            function settitle(){
               
                 $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SECTIONPAGE%>'                                  
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

                                <form name="applicationsectionmgthome"  action="${pageContext.request.contextPath}/AddUpdateApplicationSectionServlet" method="POST">
                                    <br>
                                    <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                    <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        <br>
                                        <table  border="0">


                                            <tr>
                                                <td>Application Module</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select onchange="invoke()"  name="applicationcodefield"  class="inputfield">
                                                        <option value="">--SELECT--</option>
                                                    <c:forEach var="applicationCodeLst" items="${sessionScope.SessionObject.applicationModuleList}">
                                                        <c:if test="${applicationCode==applicationCodeLst.applicationCode}">
                                                            <option value="${applicationCodeLst.applicationCode}" selected>${applicationCodeLst.description}</option>
                                                        </c:if>
                                                        <c:if test="${applicationCode!=applicationCodeLst.applicationCode}">
                                                            <option value="${applicationCodeLst.applicationCode}" >${applicationCodeLst.description}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>

                                    </table>   
                                    <table  border="0">

                                        <tr>
                                            <td>
                                                <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                    <c:forEach  var="section" items="${notassigned}">
                                                        <option value="${section.sectionCode}" >${section.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td >
                                                <input style="" type=button value=">" onclick=moveout()><br>
                                                <input type=button value="<" onclick=movein()><br>
                                                <input type=button value=">>" onclick=moveallout()><br>
                                                <input type=button value="<<" onclick=moveallin()>
                                            </td>
                                            <td>
                                                <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                    <c:forEach  var="aSection" items="${assigned}">
                                                        <option value="${aSection.sectionCode}" >${aSection.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><input type="submit" onclick="selectAll(notassignsectionlist,assignsectionlist)" style="width: 100px" name="assignsection" value="Assign">
                                                <input type="button"  name="back" value="Back" style="width: 100px" onclick="invokeBack()"></td>
                                            <td></td>
                                            <td>&nbsp;</td>
                                        </tr>






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
