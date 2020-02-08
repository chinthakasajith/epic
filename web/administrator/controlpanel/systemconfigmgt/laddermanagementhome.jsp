<%-- 
    Document   : laddermanagementhome
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
                { pagecode:'<%= PageVarList.LADDER_MANAGEMENT%>'                                  
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
                $('#succForm').text("");
                $('#errorMsg').text("");
                $.ajax({
                    url: "${pageContext.request.contextPath}/AddLadderServlet",
                    type: "POST",
                    data: $("#addLadderForm").serialize(),
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
                    url: "${pageContext.request.contextPath}/UpdateLadderServlet",
                    type: "POST",
                    data: $("#updateLadderForm").serialize(),
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
                window.location = "${pageContext.request.contextPath}/LoadLadderManagementServlet";               
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
            function loadCardProductList(cardType){
                
                var optionVal = $("#cardtype").val();
                $('#cardproduct').empty();

                if(optionVal=="ALL"){
                    document.getElementById("cardproduct").disabled=true;
                } else {
                    document.getElementById("cardproduct").disabled=false;
                
                    $.getJSON("${pageContext.servletContext.contextPath}/GetCardProductsByType",      
                    {              
                        cardType : optionVal
                    },
                    function(jsonobject) {
                        $("#cardproduct").append("<option value='ALL'>ALL</option>");
                        $.each(jsonobject, function(code, description) {
                            if(cardType == code){
                                $('#cardproduct').append(
                                $('<option selected></option>').val(code).html(description)
                            );
                            } else{
                                $('#cardproduct').append(
                                $('<option></option>').val(code).html(description)
                            )};
                        });
                    });     
                }         
            }
            
            function deleteLadder(value){
             
                answer = confirm("Do you really want to delete Ladder "+value+"?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteLadderServlet?code="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadLadderManagementServlet";
                }

            }
            
            

            function invokeReset(){
                window.location = "${pageContext.request.contextPath}/LoadLadderManagementServlet";
            }
        </script>
        <title>EPIC CMS LADDER MANAGEMENT</title>
    </head>
    <body <c:if test="${validate=='fail' || operationtype=='update'}"> onload="loadCardProductList('${ladderBean.cardType}')"</c:if>>
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

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
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

                                    <form name="sucMessage" id="succForm" action="${pageContext.request.contextPath}/LoadLadderManagementServlet">
                                    <input type="hidden" name="successMsg" id="succMes"></input>
                                </form>     

                                <form action="" method="POST" name="addLadderForm" id="addLadderForm">        
                                    <c:if test="${operationtype=='add'}" >
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>Code</td>
                                                    <td><input type="text" name="laddercode"  id="laddercode" class="inputfield-Description-mandatory" maxlength="8" value='${ladderBean.code}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><input type="text" name="ladderdescription"  id="ladderdescription" class="inputfield-Description-mandatory" maxlength="22" value='${ladderBean.description}'></td>
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
                                                    <td>Card Type</td>
                                                    <td>
                                                        <select name="cardtype" id="cardtype" class="inputfield-mandatory" onchange="loadCardProductList();">
                                                            <option value="" selected>--SELECT--</option>
                                                            <option value="ALL">ALL</option>
                                                            <c:forEach var="cardType" items="${cardTypeList}" >
                                                                <option value="${cardType.key}"
                                                                        <c:if test="${cardType.key==ladderBean.cardType}">                       
                                                                            selected="true"
                                                                        </c:if>>${cardType.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Product</td>
                                                    <td>
                                                        <select id="cardproduct" name="cardproduct"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <option value="ALL" selected>ALL</option>                                                            
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
                                                        <input type="button" name="Submit" value="Add" class="" style="width: 100px;" onclick="selectAll(assignsectionlist)"/>
                                                        <input type="button" name="Submit2" value="Reset" class="" onclick="invoke()" style="width: 100px;"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </c:if>
                                </form> 

                                <form action="" method="POST" name="updateLadderForm" id="updateLadderForm">         
                                    <c:if test="${operationtype=='update'}" >
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>Code</td>
                                                    <td><input readonly="" type="text" name="laddercode"  id="laddercode" class="inputfield-Description-mandatory" maxlength="8" value='${ladderBean.code}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><input type="text" name="ladderdescription"  id="ladderdescription" class="inputfield-Description-mandatory" maxlength="22" value='${ladderBean.description}'></td>
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
                                                    <td>Card Type</td>
                                                    <td>
                                                        <select name="cardtype" id="cardtype" class="inputfield-mandatory" onchange="loadCardProductList();">
                                                            <option value="" selected>--SELECT--</option>
                                                            <option value="ALL">ALL</option>
                                                            <c:forEach var="cardType" items="${cardTypeList}" >
                                                                <option value="${cardType.key}"
                                                                        <c:if test="${cardType.key==ladderBean.cardType}">                       
                                                                            selected="true"
                                                                        </c:if>>${cardType.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Card Product</td>
                                                    <td>
                                                        <select id="cardproduct" name="cardproduct"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <option value="ALL" selected>ALL</option>
                                                            <c:forEach items="${cardTypeList}" var="cardType">
                                                                <option value="${cardType.key}"
                                                                        <c:if test="${cardType.key==ladderBean.description}">                       
                                                                            selected="true"
                                                                        </c:if>>${cardType.value}</option>
                                                            </c:forEach>
                                                            <c:forEach var="cardProduct" items="${cardTypeList}">
                                                                <option value="${entry.key}">${entry.value}</option>
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
                                                        <input type="button" name="Submit2" value="Reset" class="" onclick="invoke()" style="width: 100px;"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </c:if>

                                </form>
                                <form action="" method="POST" name="viewLadderForm" id="viewLadderForm">    
                                    <c:if test="${operationtype=='view'}" >
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td><b>Code</b></td>
                                                    <td>:</td>
                                                    <td>${ladderBean.code}</td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td><b>Description</b></td>
                                                    <td>:</td>
                                                    <td>${ladderBean.description}</td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td><b>Status</b></td>
                                                    <td>:</td>
                                                    <td>${ladderBean.statusDesc}</td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td><b>Card Type</b></td>
                                                    <td>:</td>
                                                    <c:if test="${ladder.cardTypeDesc == null}">
                                                        <td>ALL</td>
                                                    </c:if>
                                                    <c:if test="${ladder.cardTypeDesc != null}">
                                                        <td>${ladder.cardTypeDesc}</td>
                                                    </c:if>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td><b>Card Product</b></td>
                                                    <td>:</td>
                                                    <c:if test="${ladder.cardProductDesc == null}">
                                                        <td>ALL</td>
                                                    </c:if>
                                                    <c:if test="${ladder.cardProductDesc != null}">
                                                        <td>${ladder.cardProductDesc}</td>
                                                    </c:if>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <br/>
                                        <br/>

                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td colspan="3"><h4><b>Assigned Case Types </b></h4></td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>All Cases</h4>
                                                        <select name="notassignsectionlist" style="width: 200px"  size=10 disabled="true">
                                                            <c:forEach  var="case" items="${caseList}">
                                                                <option value="${case.caseTypeCode}">${case.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <h4>Assigned Cases</h4>
                                                        <select name="assignsectionlist" style="width: 200px" size=10>
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
                                                        <input type="button" name="Back" value="Back" class="" style="width: 100px;" onclick="invokeBack()"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </c:if>


                                </form>


                                <!-- show table data -->
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview">
                                        <thead class="gradeB">
                                            <tr>
                                                <th>Code</th>
                                                <th>Description</th>
                                                <th>Status</th>                                               
                                                <th>Card Type</th>
                                                <th>Card Product</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${ladders}" var="ladder">
                                                <tr>
                                                    <td>${ladder.code}</td>                                                    
                                                    <td>${ladder.description}</td> 
                                                    <td>${ladder.statusDesc}</td>
                                                    <c:if test="${ladder.cardTypeDesc == null}">
                                                        <td>ALL</td>
                                                    </c:if>
                                                    <c:if test="${ladder.cardTypeDesc != null}">
                                                        <td>${ladder.cardTypeDesc}</td>
                                                    </c:if>
                                                    <c:if test="${ladder.cardProductDesc == null}">
                                                        <td>ALL</td>
                                                    </c:if>
                                                    <c:if test="${ladder.cardProductDesc != null}">
                                                        <td>${ladder.cardProductDesc}</td>
                                                    </c:if>
                                                    <td><a href='${pageContext.request.contextPath}/LoadViewLadderServlet?code=<c:out value="${ladder.code}"></c:out>'>View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateLadderServlet?code=<c:out value="${ladder.code}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteLadder('${ladder.code}')">Delete</a></td>
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

