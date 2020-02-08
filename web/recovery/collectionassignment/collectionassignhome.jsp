<%-- 
    Document   : collectionassignhome
    Created on : Jul 19, 2013, 4:15:50 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>
        <script type="text/javascript">
            
            function onchangeCollection(){
                
                document.collectionAssignForm.action="${pageContext.request.contextPath}/OnChangeCollectionServlet";
                document.collectionAssignForm.submit();

            }
            
            function invokeAssign(checkType){
                
                document.collectionAssignForm.action="${pageContext.request.contextPath}/CollectionAssignProcessServlet?checkType="+checkType;
                document.collectionAssignForm.submit();

            }
            
            function invokeReset(){
                
                window.location = "${pageContext.request.contextPath}/LoadCollectionAssignServlet";
               
            }
            
            
        </script>
        <script >
            function settitle(){
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.COLLECTION_ASSIGNMENT%>'                                  
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
            </div>
            <div class="main" >
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>
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

                                    <!--  ----------------------------------- Start Add Area -----------------------------------------------          -->

                                    <form action="" method="POST" name="collectionAssignForm" >

                                        <table cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td width ="100px;">Queue</td>
                                                <td><font style="color: red;">*</font>&nbsp;<select  name="queue" onchange="onchangeCollection()"  class="inputfield-mandatory">
                                                        <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="queue" items="${sessionScope.SessionObject.queueList}">

                                                        <c:if test="${queueId==queue.key}">
                                                            <option value="${queue.key}" selected="true" >${queue.value}</option>
                                                        </c:if>
                                                        <c:if test="${queueId!=queue.key}">
                                                            <option value="${queue.key}">${queue.value}</option>

                                                        </c:if>

                                                    </c:forEach>
                                                </select></td>
                                            <td></td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                    <br></br>

                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Collection ID</th>
                                                <th>Account NO</th>
                                                <th>Card NO</th>
                                                <th>Case</th>
                                                <th>Status</th>
                                                <th>IN Date</th>

                                                <th >Select</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="collection" items="${collectionList}">
                                                <tr>

                                                    <td >${collection.collectionId}</td>
                                                    <td >${collection.accountNo}</td>
                                                    <td >${collection.cardNo}</td>
                                                    <td >${collection.caseId}</td>
                                                    <td >${collection.status}</td>
                                                    <td >${collection.inDate}</td>


                                                    <td><input type="checkbox" name="checkedVelue" value="${collection.collectionId}" <c:if test="${collection.checked == true}"> checked="true"</c:if>></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 


                                    <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////--> 

                                    <table cellpadding="0" cellspacing="10">
                                        <tr>
                                            <td width ="100px;">Assign User</td>
                                            <td><font style="color: red;">*</font>&nbsp;<select  name="user"  class="inputfield-mandatory">
                                                    <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="user" items="${userList}">

                                                        <c:if test="${assignUser==user.key}">
                                                            <option value="${user.key}" selected="true" >${user.value}</option>
                                                        </c:if>
                                                        <c:if test="${assignUser!=user.key}">
                                                            <option value="${user.key}">${user.value}</option>

                                                        </c:if>

                                                    </c:forEach>
                                                </select></td>
                                            <td></td>
                                        </tr>
                                    </table>

                                    <table  cellpadding="0" cellspacing="10">

                                        <tr celspacing="5">
                                            <td style="width: 100px;">
                                            </td>
                                            <td><input type="button" name="assign"  style="width: 100px; height: 30px;" onclick="invokeAssign('1')" value="Assign">
                                                <input type="button" name="assignAll"  style="width: 100px; height: 30px;" onclick="invokeAssign('2')" value="Assign All">
                                                <input type="button" name="Reset"  style="width: 100px; height: 30px;" onclick="invokeReset()" value="Reset">

                                            </td>
                                        </tr>
                                    </table>
                                </form>

                                <!--  ----------------------------------- End developer area  --------------------------------                      -->

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