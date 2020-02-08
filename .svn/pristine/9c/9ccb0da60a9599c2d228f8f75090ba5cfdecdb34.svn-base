<%-- 
    Document   : listenerconfighome
    Created on : Jul 17, 2012, 11:49:54 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
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

                document.addListenerForm.action="${pageContext.request.contextPath}/AddListenerConfigurationServlet";
                document.addListenerForm.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadListenerConfigurationServlet";

            }
            
            function invokeUpdate(listenerId)
            {
                window.location="${pageContext.request.contextPath}/LoadUpdateListenerConfigurationServlet?listenerId="+listenerId;
               

            }
            function invokeUpdateReset(listenerId)
            {
                window.location="${pageContext.request.contextPath}/LoadUpdateListenerConfigurationServlet?listenerId="+listenerId;
               

            }
            
            function invokeUpdateConfiremd()
            {

                document.updateListenerForm.action="${pageContext.request.contextPath}/UpdateListenerConfigurationServlet";
                document.updateListenerForm.submit();

            }
            
            function invokeView(listenerId)
            {

                window.location="${pageContext.request.contextPath}/ViewListenerConfigurationServlet?listenerId="+listenerId;
                

            }
            
            function ConfirmDelete(listenerId)
            {
                answer = confirm("Are you sure you want to delete this channel?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteListenerConfigurationServlet?listenerId="+listenerId;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadListenerConfigurationServlet";
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
                { pagecode:'<%= PageVarList.LISTENER%>'                                  
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
                                    <form action="" method="POST" name="addListenerForm" >

                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>

                                                <tr>
                                                    <td>Listener Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="listenerType" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="listenerType" items="${sessionScope.SessionObject.listenerTypeList}">

                                                                <c:if test="${listenerBean.listenerType==listenerType.typeCode}">
                                                                    <option value="${listenerType.typeCode}" selected="true" >${listenerType.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.listenerType!=listenerType.typeCode}">
                                                                    <option value="${listenerType.typeCode}">${listenerType.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Communication key ID</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyId" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="key" items="${sessionScope.SessionObject.keyId}">

                                                                <c:if test="${listenerBean.keyId==key.key}">
                                                                    <option value="${key.key}" selected="true" >${key.value}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.keyId!=key.key}">
                                                                    <option value="${key.key}">${key.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="100" value='${listenerBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Establish Port</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="stablishPort" class="inputfield-Description-mandatory" maxlength="5" value='${listenerBean.stablishPort}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Terminated Port</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="terminatedPort" class="inputfield-Description-mandatory" maxlength="5" value='${listenerBean.terminatedPort}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Key Exchanging Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyExchangeStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.keyExchangeStatus==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.keyExchangeStatus!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Security Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="sequirtyStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.sequirtyStatus==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.sequirtyStatus!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>  

                                                <tr>
                                                    <td>Number of Connection</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="noOfConnection" class="inputfield-Description-mandatory" maxlength="4" value='${listenerBean.noOfConnection}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Time out (Sec)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="timeout" class="inputfield-Description-mandatory" maxlength="8" value='${listenerBean.timeout}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Running Mode</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="runingMode" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="rmd" items="${sessionScope.SessionObject.runningModeList}">

                                                                <c:if test="${listenerBean.runingMode==rmd.key}">
                                                                    <option value="${rmd.key}" selected="true" >${rmd.value}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.runingMode!=rmd.key}">
                                                                    <option value="${rmd.key}">${rmd.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr> 

                                                <tr>
                                                    <td>Status of Acquiring</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="statusOfAquiring" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.statusOfAquiring==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.statusOfAquiring!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="status" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.status==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.status!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
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

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->

                                <c:if test="${operationtype=='VIEW'}" >
                                    <form action="" method="POST" name="viewListenerForm">



                                        <table cellpadding="0" cellspacing="10" >
                                            <tbody>
                                                <tr>
                                                    <td>Listener ID</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.listenerId}</td>
                                                </tr>


                                                <tr>
                                                    <td>Listener Type</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.listenerTypeDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Key ID</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.keyIdDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.description}</td>
                                                </tr>

                                                <tr>
                                                    <td>Stablish Port </td>

                                                    <td> : </td>

                                                    <td>${listenerBean.stablishPort}</td>
                                                </tr>

                                                <tr>
                                                    <td>Terminated Port</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.terminatedPort}</td>
                                                </tr>

                                                <tr>
                                                    <td>Key Exchange Status</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.keyExchangeStatus}</td>
                                                </tr>

                                                <tr>
                                                    <td>Security Status</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.sequirtyStatus}</td>
                                                </tr>

                                                <tr>
                                                    <td>Number of Connection</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.noOfConnection}</td>
                                                </tr>

                                                <tr>
                                                    <td>Time Out (Sec)</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.timeout}</td>
                                                </tr>

                                                <tr>
                                                    <td>Running Mode</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.runningModeDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status of Acquiring</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.statusOfAquiring}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td> : </td>

                                                    <td>${listenerBean.statusDes}</td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        


                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='UPDATE'}" >
                                    <form method="POST" action="" name="updateListenerForm">


                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>

                                                <tr>
                                                    <td>Listener ID</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" readonly name="listenerId" class="inputfield-mandatory" maxlength="6" value='${listenerBean.listenerId}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Listener Type</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="listenerType" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="listenerType" items="${sessionScope.SessionObject.listenerTypeList}">

                                                                <c:if test="${listenerBean.listenerType==listenerType.typeCode}">
                                                                    <option value="${listenerType.typeCode}" selected="true" >${listenerType.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.listenerType!=listenerType.typeCode}">
                                                                    <option value="${listenerType.typeCode}">${listenerType.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Key ID</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyId" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="key" items="${sessionScope.SessionObject.keyId}">

                                                                <c:if test="${listenerBean.keyId==key.key}">
                                                                    <option value="${key.key}" selected="true" >${key.value}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.keyId!=key.key}">
                                                                    <option value="${key.key}">${key.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="100" value='${listenerBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Stablish Port</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="stablishPort" class="inputfield-Description-mandatory" maxlength="5" value='${listenerBean.stablishPort}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Terminated Port</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="terminatedPort" class="inputfield-Description-mandatory" maxlength="5" value='${listenerBean.terminatedPort}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Key Exchanging Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="keyExchangeStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.keyExchangeStatus==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.keyExchangeStatus!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr> 

                                                <tr>
                                                    <td>Security Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="sequirtyStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.sequirtyStatus==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.sequirtyStatus!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Number of Connection</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="noOfConnection" class="inputfield-Description-mandatory" maxlength="4" value='${listenerBean.noOfConnection}'></td>
                                                </tr>
                                                <tr>
                                                    <td>Time out (Sec)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="timeout" class="inputfield-Description-mandatory" maxlength="8" value='${listenerBean.timeout}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Running Mode</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="runingMode" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="rmd" items="${sessionScope.SessionObject.runningModeList}">

                                                                <c:if test="${listenerBean.runingMode==rmd.key}">
                                                                    <option value="${rmd.key}" selected="true" >${rmd.value}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.runingMode!=rmd.key}">
                                                                    <option value="${rmd.key}">${rmd.value}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>  

                                                <tr>
                                                    <td>Status of Acquiring</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="statusOfAquiring" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.statusOfAquiring==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.statusOfAquiring!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="status" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusTypeList}">

                                                                <c:if test="${listenerBean.status==status.code}">
                                                                    <option value="${status.code}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${listenerBean.status!=status.code}">
                                                                    <option value="${status.code}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" />
                                                        <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${listenerBean.listenerId}')"/>
                                                    </td>

                                                </tr>


                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="scoreltableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Listener ID</th>
                                            <th>Listener Type</th>
                                            <th>Description</th>
                                            <th>Stablish Port</th>
                                            <th>Terminated Port</th>
                                            <th>Number of Connection</th>
                                            <th>Time Out (Sec)</th>
                                            <th>Running Mode</th>
                                            <th>Status</th>



                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="listener" items="${listenerList}">
                                            <tr>

                                                <td >${listener.listenerId}</td>
                                                <td >${listener.listenerTypeDes}</td>
                                                <td >${listener.description}</td>
                                                <td >${listener.stablishPort}</td>
                                                <td >${listener.terminatedPort}</td>
                                                <td >${listener.noOfConnection}</td>
                                                <td >${listener.timeout}</td>
                                                <td >${listener.runningModeDes}</td>
                                                <td >${listener.statusDes}</td>




                                                <td  ><a href='#' onclick="invokeView('${listener.listenerId}')">View</a></td>

                                                <td  ><a href='#' onclick="invokeUpdate('${listener.listenerId}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${listener.listenerId}')">Delete</a></td>

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
