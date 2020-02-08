<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>

<%@page import="com.epic.cms.system.util.variable.MessageVarList"%>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>


<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>-->


        <script type="text/javascript">
            
            
            function secUpdateReset(value){
                window.location = "${pageContext.request.contextPath}/ViewSectionMgtUpdateServlet?id="+value;
            }
     
            function view(value){
                $.post("${pageContext.request.contextPath}/ViewSectionMgtServlet", { id:value },
                function(data) {
                    //                    alert(data);
                    if(data === "success"){
                         
                        $.colorbox({href:"${pageContext.request.contextPath}/administrator/controlpanel/systemusermgt/sectionmgtview.jsp", iframe:false, height:"60%", width:"60%",overlayClose:false});
                    }else{
                        alert("can not view call");
                    }
               
                });
                                
                
                
                
                //                window.location = "${pageContext.request.contextPath}/ViewSectionMgtServlet?id="+value;

            }
            
            function dataView(value){
                
                window.location = "${pageContext.request.contextPath}/ViewSectionMgtServlet?id="+value;
                
            }
            function resetBtn(){
                window.location = "${pageContext.request.contextPath}/LoadSectionMgtServlet";
            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadSectionMgtServlet";

            }
            function update(value){
                window.location = "${pageContext.request.contextPath}/ViewSectionMgtUpdateServlet?id="+value;
            }
            
            function deleteRow(value){
                var txt = new String(value);
             
//                answer = confirm("Do you really want to delete "+value+" Section?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteSectionMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadSectionMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete "+value+" Section?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location="${pageContext.request.contextPath}/LoadSectionMgtServlet";
                        },
                        "Yes": function () {
                            window.location="${pageContext.request.contextPath}/DeleteSectionMgtServlet?id="+value;
                        }
                    }
                });
                

            }
        </script>
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.SECTION%>'                                  
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

        <title>EPIC_CMS_HOME</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
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




                                <!------------------------------------------------------------------------------------------------------------- -->
                                <!--error messages-->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <script> settitle();</script>

                                <c:if test="${operationtype=='add'}" >
                                    <!--title-->
                                    <form action="${pageContext.request.contextPath}/AddSectionMgtServlet" method="POST">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Section Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="16" name="secCode" value="${secBean.sectionCode}"  /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>

                                                        <c:if test="${secBean.sortKey!=0}" >
                                                            <input type="text" maxlength="5" name="sortKey" value="${secBean.sortKey}" />
                                                        </c:if>
                                                        <c:if test="${secBean.sortKey==0}" >
                                                            <input type="text" maxlength="5" name="sortKey" value="" />
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="64" name="description" value="${secBean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.secList}">
                                                                <c:if test="${vRule.statusCode ==secBean.statusCode}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>

                                                                <c:if test="${vRule.statusCode !=secBean.statusCode}">
                                                                    <option value="${vRule.statusCode}">${vRule.description}</option>
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
                                                    <td style="width: 300px;"><input type="submit" style="width: 100px" width="100" value="Add" name="sectionAdd" /><input type="reset" style="width: 100px" width="100" value="Reset" onclick="resetBtn()" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SECTION%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a> </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>
                                <c:if test="${operationtype=='view'}" >
                                    <!--title-->
                                    <form action="${pageContext.request.contextPath}/AddSectionMgtServlet" method="POST">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Section Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="16" name="secCode" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="sortKey" maxlength="5" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.secList}">
                                                                <option value="${vRule.statusCode}">${vRule.description}</option>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" style="width: 100px" value="Add" name="sectionAdd" /><input type="reset" style="width: 100px" value="Reset" onclick="resetBtn()" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SECTION%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a>  </td>
                                                </tr>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>

                                <c:if test="${operationtype=='update'}" >

                                    <form action="${pageContext.request.contextPath}/UpdateSectionMgtServlet" method="POST">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Section Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" class="inputfield-mandatory" readonly="" maxlength="16" name="editSecCode" value="${secBean.sectionCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>

                                                    <td>
                                                        <c:if test="${secBean.sortKey!=0}" >
                                                            <input type="text" name="editSortKey" value="${secBean.sortKey}" maxlength="5"/>
                                                        </c:if>
                                                        <c:if test="${secBean.sortKey==0}" >
                                                            <input type="text" name="editSortKey" value="" maxlength="5"/>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" class="inputfield-Description-mandatory" maxlength="64" name="editDescription" value="${secBean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><select name="editStatus">

                                                            <c:forEach var="vRule" items="${requestScope.secList}">
                                                                <c:if test="${vRule.statusCode ==secBean.statusCode}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.statusCode !=secBean.statusCode}">
                                                                    <option value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Update" style="width: 100px" name="sectionEdit" /><input type="reset" style="width: 100px" value="Reset" onclick="secUpdateReset('${secBean.sectionCode}')" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.SECTION%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a> </td>
                                                </tr>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>

                                <c:if test="${operationtype=='viewdata'}">

                                    <form action="${pageContext.request.contextPath}/LoadSectionMgtServlet" method="POST">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Section Code</td>
                                                    <td>&nbsp;</td>
                                                    <td>${SessionObject.secBean.sectionCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td>&nbsp;</td>
                                                    <td>${SessionObject.secBean.description}</td>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Sort Key</td>
                                                    <td>&nbsp;</td>
                                                    <td>${SessionObject.secBean.sortKey}</td>

                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>${SessionObject.secBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="reset" value="Back" name="back" onclick="invokeReset()" style="width: 100px;"/></td>
                                                <tr>
                                                    <td></td>
                                                    <td></td>

                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>


                                </c:if>



                                <table border="1" class="display" id="scoreltableview3">

                                    <thead>
                                        <tr >
                                            <th>Section Code</th>
                                            <th>Description</th>
                                            <th>Sort Key</th>
                                            <th>Status</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>

                                    <tbody>



                                        <c:forEach  items="${requestScope.allsecList}" var="allsecList">

                                            <tr>
                                                <td>${allsecList.sectionCode}</td>
                                                <td>${allsecList.description}</td>
                                                <td>${allsecList.sortKey}</td>
                                                <td>${allsecList.statusDes}</td>
                                                <td><a  href='#' onclick="dataView('${allsecList.sectionCode}@E')"  >View</a></td>
                                                <td><a  href='#' onclick="update('${allsecList.sectionCode}')" >Update</a></td>  
                                                <td><a  href='#' onclick="deleteRow('${allsecList.sectionCode}')" >Delete</a></td>
                                            </tr>

                                        </c:forEach>
                                    </tbody>

                                </table>










                                <!-- - ------------------------------------------------------------------------------------------------------------ -->




                            </div>

                        </div>
                    </div>








                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
                                              <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
