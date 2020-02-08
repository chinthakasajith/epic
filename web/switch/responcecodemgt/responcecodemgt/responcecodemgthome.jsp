<%-- 
    Document   : responcecodemgthome
    Created on : Jul 19, 2012, 8:39:23 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />



        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";
        </style>

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>

        <title>EPIC_CMS_HOME</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addResponceForm.action="${pageContext.request.contextPath}/AddResponceCodeMgtServlet";
                document.addResponceForm.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadResponceCodeMgtServlet";

            }
            
            function invokeUpdate(responceCode)
            {
                window.location="${pageContext.request.contextPath}/LoadUpdateResponceCodeMgtServlet?responceCode="+responceCode;
               

            }
            function invokeUpdateReset(responceCode)
            {
                window.location="${pageContext.request.contextPath}/LoadUpdateResponceCodeMgtServlet?responceCode="+responceCode;
               

            }
            
            function invokeUpdateConfiremd()
            {

                document.updateResponceForm.action="${pageContext.request.contextPath}/UpdateResponceCodeMgtServlet";
                document.updateResponceForm.submit();

            }
            
            function invokeView(responceCode)
            {

                window.location="${pageContext.request.contextPath}/ViewChanelConfigurationServlet?responceCode="+responceCode;
                

            }
            
            function ConfirmDelete(responceCode)
            {
                answer = confirm("Are you sure you want to delete this channel?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteResponceCodeMgtServlet?responceCode="+responceCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadResponceCodeMgtServlet";
                }
                
            }
            
            
            
        </script>   
        <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.RESPONCE_CODE_MGT%>'                                  
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
            <c:redirect url="/controlpanel/login/login.jsp"/>
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




                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                    <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                    <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Add(also default) view  ///////////////////////////////////////////////////////////-->


                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                <c:if test="${operationtype=='ADD'}">
                                    <form action="" method="POST" name="addResponceForm" >

                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>

                                                <tr>
                                                    <td>Response Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="responceCode" class="inputfield-mandatory" maxlength="3" value='${responceBean.responceCode}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="100" value='${responceBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="add" value="Add" onclick="invokeAdd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset()"/>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                              

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='UPDATE'}" >
                                    <form method="POST" action="" name="updateResponceForm">

                                        <table  cellpadding="0" cellspacing="10">
                                            <tbody>

                                                <tr>
                                                    <td>Response Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input readonly="readonly" type="text" name="responceCode" class="inputfield-mandatory" maxlength="3" value='${responceBean.responceCode}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="100" value='${responceBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${responceBean.responceCode}')"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Response Code</th>
                                            <th>Description</th>

                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="res" items="${responceList}">
                                            <tr>

                                                <td >${res.responceCode}</td>
                                                <td >${res.description}</td>

                                                <td  ><a href='#' onclick="invokeUpdate('${res.responceCode}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${res.responceCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>     


                                <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////-->



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