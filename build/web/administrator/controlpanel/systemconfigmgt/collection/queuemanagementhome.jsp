<%-- 
    Document   : queuemanagementhome
    Created on : Jul 4, 2013, 9:44:00 AM
    Author     : ruwan_e
--%>


<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <script type="text/javascript">
            function settitle(){                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.QUEUE_MANAGEMENT%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);                                           
                    }                   
                });
            }
            
            
            function invokeAdd()
            {
              
                
                $('#errorMsg').text("");
                $.ajax({
                    url: "${pageContext.request.contextPath}/AddQueueServlet",
                    type: "POST",
                    data: $("#addQueueForm").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        if(ar[0] == 'success'){
                            $('#succMes').val(ar[1]);
                            $('#succForm').submit();
                        }else{
                            $("#errorMsg").text(data);
                        }
                        
                    }
                });
                
                 
                    
               
               
            }
            function invokeUpdate()
            {
                $('#errorMsg').text("");
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateQueueServlet",
                    type: "POST",
                    data: $("#updateQueueForm").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        if(ar[0] == 'success'){
                            $('#succMes').val(ar[1]);
                            $('#succForm').submit();
                        }else{
                            $("#errorMsg").text(data);
                        }
                        
                    }
                });
                    
               
               
            }
            
            function invokeBack(){                
                window.location = "${pageContext.request.contextPath}/LoadQueueManagementServlet";               
            }
            
            function selectAll(selectBox) {
                
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }       
                invokeAdd();
            }
            
            function selectAllUpdate(selectBox) {
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }       
                invokeUpdate();
            }
            
            function selectAllForUpdate(selectBox1,selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }     
                invokeUpdate();
            }
            
          
            
            //            function invokeUpdate()
            //            {
            //                $('#errorMsgcon').text("");
            //                $.ajax({
            //                    url: "${pageContext.request.contextPath}/UpdateConfiremedInterestprofileMgtServlet",
            //                    type: "POST",
            //                    data: $("#updateInterestProfile").serialize(),
            //                    success : function(data){
            //                        var ar=data.split(",", 2);
            //                        if(ar[0] == 'success'){
            //                            $('#succMes').val(ar[1]);
            //                            $('#succForm').submit();
            //                        }else{
            //                            $("#errorMsgcon").text(data);
            //                        }
            //                        
            //                    }
            //                });
            //
            //                //                document.updateInterestProfile.action="${pageContext.request.contextPath}/UpdateConfiremedInterestprofileMgtServlet";
            //                //                document.updateInterestProfile.submit();
            //
            //            }
            //            
            
            
            function loadLadderCases(){
                
                var optionVal = $("#ladder").val();
                $('#in').empty();
                $('#out').empty(); 
                
                $.getJSON("${pageContext.servletContext.contextPath}/GetCasesByLadder",      
                {              
                    ladderCode : optionVal
                },
                function(jsonobject) {               
                    $.each(jsonobject, function(code, description) {
                        $('#in').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });     
            }         
            
            
            function deleteQueue(value){
             
                answer = confirm("Do you really want to delete Queue "+value+"?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteQueueServlet?code="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadQueueManagementServlet";
                }

            }
            
            

            function invokeReset(){
                alert("asdasd");
                window.location = "${pageContext.request.contextPath}/LoadQueueManagementServlet";
            }
        </script>
        <title>EPIC CMS QUEUE MANAGEMENT</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container" >
            <div class="header">
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

                                <!------------------------start  developer area  ------------------------------------->
                                <table class="tit">
                                    <tr>
                                        <td class="heading"/>
                                    </tr>
                                </table>

                                <script> settitle();</script>                            



                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000" id="errorMsg"><b>
                                                <c:out value="${errorMsg}"></c:out>
                                            </b></font>
                                            <font color="green" id="successMsg"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <form name="sucMessage" id="succForm" action="${pageContext.request.contextPath}/LoadQueueManagementServlet">
                                    <input type="hidden" name="successMsg" id="succMes"/>
                                </form>     

                                <form action="" method="POST" name="addQueueForm" id="addQueueForm">        
                                    <c:if test="${operationtype=='add'}" >
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>ID</td>
                                                    <td><input type="text" name="queueid"  id="queueid" class="inputfield-Description-mandatory" maxlength="8" value='${ladderBean.code}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><input type="text" name="queuedescription"  id="queuedescription" class="inputfield-Description-mandatory" maxlength="22" value='${ladderBean.description}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td>
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="entry" items="${statusList}">
                                                                <option value="${entry.key}"<c:if test="${entry.key==ladderBean.status}">                       
                                                                        selected="true"
                                                                    </c:if>>${entry.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Assigned User Role</td>
                                                    <td>
                                                        <select name="userrole" id="userrole" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="userRole" items="${userRoleList}" >
                                                                <option value="${userRole.key}"
                                                                        <c:if test="${userRole.key==ladderBean.cardType}">                       
                                                                            selected="true"
                                                                        </c:if>>${userRole.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Ladder (Type)</td>
                                                    <td>
                                                        <select name="ladder" id="ladder" class="inputfield-mandatory" onchange="loadLadderCases();">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="ladder" items="${ladders}" >
                                                                <option value="${ladder.code}"
                                                                        >${ladder.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <br/>
                                        <br/>

                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td colspan="3"><h4><b>Assign Case Types </b></h4></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>All Cases</h4>
                                                        <select name="notassignsectionlist" style="width: 200px" id="in" multiple="multiple"  size=10> 

                                                        </select>
                                                    </td>
                                                    <td align="center">
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4>Assigned Cases</h4>
                                                        <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach  var="case" items="${assignedCaseList}">
                                                                <option value="${case.caseTypeCode}">${case.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <br></br>

                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td>                                                    
                                                        <input type="button" name="Submit" value="Add" class="" style="width: 100px;" onclick="selectAll(assignsectionlist)"/>
                                                        <input type="button" name="Submit2" value="Reset" class="" onclick="invokeReset()" style="width: 100px;"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </c:if>
                                </form> 

                                <form action="" method="POST" name="updateQueueForm" id="updateQueueForm">         
                                    <c:if test="${operationtype=='update'}" >
                                        <table>
                                            <tbody>

                                                <tr>
                                                    <td>ID</td>
                                                    <td><input readonly type="text" name="queueid"  id="queueid" class="inputfield-Description-mandatory" maxlength="8" value='${queueBean.code}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><input type="text" name="queuedescription"  id="queuedescription" class="inputfield-Description-mandatory" maxlength="22" value='${queueBean.description}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td>
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="entry" items="${statusList}">
                                                                <option value="${entry.key}"<c:if test="${entry.key==queueBean.status}">                       
                                                                        selected="true"
                                                                    </c:if>>${entry.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Assigned User Role</td>
                                                    <td>
                                                        <select name="userrole" id="userrole" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>                                                            
                                                            <c:forEach var="role" items="${userRoleList}" >
                                                                <option value="${role.key}"
                                                                        <c:if test="${role.key==queueBean.assignUserRole}">                       
                                                                            selected="true"
                                                                        </c:if>>${role.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Ladder (Type)</td>
                                                    <td>
                                                        <select name="ladder" id="ladder" class="inputfield-mandatory" onchange="loadLadderCases();">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="ladder" items="${ladders}" >
                                                                <option value="${ladder.code}"<c:if test="${ladder.code==queueBean.ladderCode}">                       
                                                                        selected="true"
                                                                    </c:if>
                                                                    >${ladder.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>


                                        <br/>

                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td colspan="3"><h4><b>Assign Case Types </b></h4></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>All Ladder Cases</h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="case" items="${caseList}">
                                                                <option value="${case.caseTypeCode}">${case.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center">
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                    </td>
                                                    <td>
                                                        <h4>Assigned Cases</h4>
                                                        <select name="assignsectionlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach  var="case" items="${assignedCaseList}">
                                                                <option value="${case.caseTypeCode}">${case.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <br></br>

                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td>                                                    
                                                        <input type="button" name="Submit" value="Update" class="" style="width: 100px;" onclick="selectAllUpdate(assignsectionlist)"/>
                                                        <input type="button" name="Submit2" value="Reset" class="" onclick="invokeReset()" style="width: 100px;"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </c:if>
                                </form> 

                                <c:if test="${operationtype=='view'}" >
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td><b>Queue Code</b></td>
                                                <td>:</td>
                                                <td>${queueBean.code}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td><b>Description</b></td>
                                                <td>:</td>
                                                <td>${queueBean.description}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td><b>Status</b></td>
                                                <td>:</td>
                                                <td>${queueBean.statusDesc}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td><b>Assigned User Role</b></td>
                                                <td>:</td>                                                    
                                                <td>${queueBean.assignUserRole}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td><b>Ladder</b></td>
                                                <td>:</td>                                                    
                                                <td>${queueBean.ladderCode}</td>
                                                <td></td>
                                            </tr>                                                

                                        </tbody>
                                    </table>

                                    <br/>
                                    <br/>

                                    <table>
                                        <tbody>                                           
                                            <tr>                                               
                                                <td>
                                                    <h4>Assigned Cases</h4>
                                                    <select name="assignsectionlist" style="width: 200px" size=10>
                                                        <c:forEach  var="case" items="${assignedCases}">
                                                            <option>${case}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>

                                    <br></br>

                                    <table>
                                        <tbody>
                                            <tr>
                                                <td></td>
                                                <td>                                                    
                                                    <input type="button" name="Back" value="Back" class="" style="width: 100px;" onclick="invokeBack()"/>
                                                </td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </c:if>



                                <!-- show table data -->
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview">
                                        <thead class="gradeB">
                                            <tr>
                                                <th>Code</th>
                                                <th>Description</th>
                                                <th>Status</th>
                                                <th>Assigned User Role</th>
                                                <th>Ladder Code</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${queues}" var="queue">
                                                <tr>
                                                    <td>${queue.code}</td>                                                    
                                                    <td>${queue.description}</td> 
                                                    <td>${queue.statusDesc}</td>
                                                    <td>${queue.assignUserRole}</td>
                                                    <td>${queue.ladderCode}</td>

                                                    <td><a href='${pageContext.request.contextPath}/ViewQueueServlet?code=<c:out value="${queue.code}"></c:out>'>View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateQueueServlet?code=<c:out value="${queue.code}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteQueue('${queue.code}')">Delete</a></td>
                                                </tr>
                                            </c:forEach>
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

