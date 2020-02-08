<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script type="text/javascript">
            function selectFeildID(value){
                
                //                window.location = "${pageContext.request.contextPath}/ProccessCreditScoreDiffFileIDServlet?id="+value;
                document.add.action="${pageContext.request.contextPath}/ProccessCreditScoreDiffFileIDServlet?id="+value;
                document.add.submit();
            }
            
            function creditFeildDelete(value){
                //                window.location = "${pageContext.request.contextPath}/DeleteCreditScoreFeildDifServlet?id="+value;
                answer = confirm("Do you really want to delete  "+value+" Credit Score Field ?")
                if (answer !=0)
                {
                    window.location = "${pageContext.request.contextPath}/DeleteCreditScoreFeildDifServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCreditScoreFieldDefineServlet";
                }
        
            }


            function creditFeildReset(){
                window.location = "${pageContext.request.contextPath}/LoadCreditScoreFieldDefineServlet";
            }
            
            function creditFeildUpdateReset(value){
                window.location = "${pageContext.request.contextPath}/UpdateCreditScoreDifViewServlet?id="+value;
            }
           
            function creditFeildUpdate(value){
                window.location = "${pageContext.request.contextPath}/UpdateCreditScoreDifViewServlet?id="+value;
            }
            function creditFeildView(value){
                window.location = "${pageContext.request.contextPath}/ViewCreditScoreFieldDefineServlet?id="+value;
            }
            function viewCreditScore(value){
                window.location = "${pageContext.request.contextPath}/ViewCreditScoreFieldDefineServlet?id="+value;
            }
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CREDISCOREFIELDDIF%>'                                  
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
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main"  >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" style="height: 500px">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!-- <table>
                                                                    <tr>
                                                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                                            </td>
                                                                        </tr>
                                                                    </table>-->

                                    <!--  ----------------------start  developer area  -----------------------------------                           -->
                                    <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                    <script> settitle();</script>
                                    
                                <c:if test="${flag=='T'}" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>
                                            </tr>
                                        </table>
                                </c:if>
                                <c:if test="${operationtype=='add'}" >


                                    <form name="add" method="POST" action="${pageContext.request.contextPath}/AddCreditScoreFieldDifServlet">

                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Field Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="fieldCode" value="${bean.fieldCode}" maxlength="12"/></td>

                                                </tr>

                                                <tr>
                                                    <td>Field Description</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="fieldDes" value="${bean.fieldDes}" maxlength="32"/></td>
                                                </tr>


                                                <tr>
                                                    <td>Form Name </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="fromName" onchange ="selectFeildID(value+'@A')" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="formList" items="${formList}">
                                                                <c:if test="${formList==formName}" >
                                                                    <option selected="" value="${formList}">${formList}</option>
                                                                </c:if>
                                                                <c:if test="${formList!=formName}" >
                                                                    <option  value="${formList}">${formList}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Field Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="fieldName">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="fieldList" items="${fieldList}">
                                                                <c:if test="${fieldList==bean.fieldName}" >
                                                                    <option selected="" value="${fieldList}">${fieldList}</option>
                                                                </c:if>
                                                                <c:if test="${fieldList!=bean.fieldName}" >
                                                                    <option value="${fieldList}">${fieldList}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="secList" items="${secList}">
                                                                <c:if test="${secList.statusCode==bean.status}" >
                                                                    <option selected="" value="${secList.statusCode}">${secList.description}</option>
                                                                </c:if>
                                                                <c:if test="${secList.statusCode!=bean.status}" >
                                                                    <option value="${secList.statusCode}">${secList.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Data Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="datatype">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${bean.dataType=='STRING'}" >
                                                                <option selected="STRING" value="STRING">STRING</option>
                                                                <option value="NUMBER">NUMBER</option>
                                                                <option value="AMOUNT">AMOUNT</option>
                                                            </c:if>

                                                            <c:if test="${bean.dataType=='NUMBER'}" >
                                                                <option  value="STRING">STRING</option>
                                                                <option  value="AMOUNT">AMOUNT</option>
                                                                <option selected="" value="NUMBER">NUMBER</option>
                                                            </c:if>
                                                            <c:if test="${bean.dataType=='AMOUNT'}" >
                                                                <option  value="STRING">STRING</option>
                                                                <option selected="" value="AMOUNT">AMOUNT</option>
                                                                <option  value="NUMBER">NUMBER</option>
                                                            </c:if>
                                                            <c:if test="${bean.dataType==''}" >
                                                                <option  value="STRING">STRING</option>
                                                                <option  value="NUMBER">NUMBER</option>
                                                                <option  value="AMOUNT">AMOUNT</option>
                                                            </c:if>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit"  value="Add" style="width: 100px" /> <input type="reset" value="Reset" onclick="creditFeildReset()" style="width: 100px" /></td>
                                                    <td ><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDISCOREFIELDDIF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>




                                </c:if>
                                <c:if test="${operationtype=='view'}" >
                                    <form name="add" method="POST" action="${pageContext.request.contextPath}/LoadCreditScoreFieldDefineServlet">

                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Field Code </td>
                                                    <td>&nbsp;</td>
                                                    <td>: ${bean.fieldCode}</td>
                                                    
                                                </tr>

                                                <tr>
                                                    <td>Field Description</td>
                                                    <td>&nbsp;</td>
                                                    <td>: ${bean.fieldDes}</td>
                                                </tr>


                                                <tr>
                                                    <td>Form Name </td>
                                                    <td>&nbsp;</td>
                                                    <td>: ${bean.formName}</td>
                                                </tr>

                                                <tr>
                                                    <td>Field Name</td>
                                                    <td>&nbsp;</td>
                                                    <td>: ${bean.fieldName} </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td>&nbsp;</td>
                                                    <td>: ${bean.status}</td>
                                                </tr>


                                                <tr>
                                                    <td>Data Type</td>
                                                    <td>&nbsp;</td>
                                                    <td>: ${bean.dataType}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit"  value="Back" style="width: 100px" /></td>
                                                    <td > <a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDISCOREFIELDDIF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>




                                </c:if>


                                <c:if test="${operationtype=='default'}" >
                                    <form name="add" method="POST" action="${pageContext.request.contextPath}/AddCreditScoreFieldDifServlet">

                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Field Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="fieldCode" value="" maxlength="12"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Field Description</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="fieldDes" value="" maxlength="32" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Form Name </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select  style="width: 160px;"  name="fromName" onchange ="selectFeildID(value+'@A')"  >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="formList" items="${formList}">
                                                                <option  value="${formList}">${formList}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Field Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="fieldName">
                                                            <option value="" >--SELECT--</option>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="secList" items="${secList}">

                                                                <option value="${secList.statusCode}">${secList.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Data Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="datatype">
                                                            <option value="" >--SELECT--</option>
                                                            <option value="STRING">STRING</option>
                                                            <option value="NUMBER">NUMBER</option>
                                                            <option value="AMOUNT">AMOUNT</option>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit"  value="Add" style="width: 100px" /> <input type="reset" value="Reset" onclick="creditFeildReset()" style="width: 100px" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDISCOREFIELDDIF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>




                                </c:if>




                                <c:if test="${operationtype=='update'}" >
                                    <form name="add" method="POST" action="${pageContext.request.contextPath}/UpdateCreditScoreDefineServlet">

                                        <table cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Field Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" readonly="" name="fieldCode" value="${bean.fieldCode}" maxlength="12" /></td>
                                                </tr>

                                                <tr>
                                                    <td>Field Description</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="fieldDes" value="${bean.fieldDes}" maxlength="32"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Form Name </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="fromName" onchange ="selectFeildID(value+'@U')"  >

                                                            <c:forEach var="formList" items="${formList}">
                                                                <c:if test="${formList==bean.formName}" >
                                                                    <option selected="" value="${formList}">${formList}</option>
                                                                </c:if>
                                                                <c:if test="${formList!=bean.formName}" >
                                                                    <option  value="${formList}">${formList}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Field Name</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="fieldName">

                                                            <c:forEach var="fieldList" items="${fieldList}">
                                                                <c:if test="${fieldList==bean.fieldName}" >
                                                                    <option selected="" value="${fieldList}">${fieldList}</option>
                                                                </c:if>
                                                                <c:if test="${fieldList!=bean.fieldName}" >
                                                                    <option value="${fieldList}">${fieldList}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="status">

                                                            <c:forEach var="secList" items="${secList}">
                                                                <c:if test="${secList.description==bean.status}" >
                                                                    <option selected="" value="${secList.statusCode}">${secList.description}</option>
                                                                </c:if>
                                                                <c:if test="${secList.description!=bean.status}" >
                                                                    <option value="${secList.statusCode}">${secList.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Data Type</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select style="width: 160px;"  name="datatype">
                                                            <c:if test="${bean.dataType=='STRING'}" >
                                                                <option selected="" value="STRING">STRING</option>
                                                                <option value="NUMBER">NUMBER</option>
                                                                <option value="AMOUNT">AMOUNT</option>
                                                            </c:if>

                                                            <c:if test="${bean.dataType=='NUMBER'}" >
                                                                <option selected="" value="NUMBER">NUMBER</option>
                                                                <option value="STRING">STRING</option>
                                                                <option value="AMOUNT">AMOUNT</option>
                                                            </c:if>
                                                            <c:if test="${bean.dataType=='AMOUNT'}" >
                                                                <option  value="NUMBER">NUMBER</option>
                                                                <option value="STRING">STRING</option>
                                                                <option selected="" value="AMOUNT">AMOUNT</option>
                                                            </c:if>


                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit"  value="Update" style="width: 100px" /> <input type="reset" value="Reset" onclick="creditFeildUpdateReset('${bean.fieldCode}')" style="width: 100px" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CREDISCOREFIELDDIF%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>




                                </c:if>




                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;



                                <table  border="1" class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Field Code</th>
                                            <th>Field Description</th>
                                            <th>From Name</th>
                                            <th>Field Name</th>
                                            <th>Status</th>
                                            <th>Data Type</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.allList}" var="allList">
                                            <tr>
                                                <td>${allList.fieldCode}</td>
                                                <td>${allList.fieldDes}</td>
                                                <td>${allList.formName}</td>
                                                <td>${allList.fieldName}</td>
                                                <td>${allList.status}</td>
                                                <td>${allList.dataType}</td>
                                                <td><a  href='#' onclick="viewCreditScore('${allList.fieldCode}')">View</a></td>
                                                <td><a  href='#' onclick="creditFeildUpdate('${allList.fieldCode}')">Update</a></td>
                                                <td><a  href='#' onclick="creditFeildDelete('${allList.fieldCode}')">delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>





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
