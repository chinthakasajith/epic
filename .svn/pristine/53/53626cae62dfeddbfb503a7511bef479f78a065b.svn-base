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
            
            function updateDoc(value){
                window.location = "${pageContext.request.contextPath}/UpdateDocumentTypeMgtViewServlet?id="+value;
            }
            function viewDoc(value){
                window.location = "${pageContext.request.contextPath}/VeiwDocumentTypeMgtServlet?id="+value;
            }
            
            function deleteDoc(value)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete  "+value+" Document Type ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteDcoumentTypeMgtServlet?id="+value;
                        }
                    }
                });

            }
            function resetAdd(){
                window.location = "${pageContext.request.contextPath}/LoadDocumentTypeMgtServlet";
            }
            
     
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DOCUMENTTYPEMGT%>'                                  
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


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

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
                                <c:if test="${operationtype=='default'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/AddDocumentTypeMgtServlet">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Document Type Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="docCode" value="" maxlength="32"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Document Name </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="docName" value="" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Verification Category  </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="verifiCat">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.catList}">
                                                                <option value="${statusVal.vrifyCode}">${statusVal.descirption}</option>                       
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
                                                    <td>PostFix Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="postfix" value="" maxlength="16"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.secList}">
                                                                <option value="${statusVal.statusCode}">${statusVal.description}</option>
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
                                                    <td>Card Category</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="cardCategoty">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.cardCatList}">
                                                                <option value="${statusVal.categoryCode}">${statusVal.description}</option>                       
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
                                                    <td>Mandatory</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="radio" name="mandatory" value="true"/>YES&nbsp;&nbsp;
                                                        <input type="radio" name="mandatory" value="false" checked="true"/>NO 
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
                                                    <td style="width: 300px;"><input type="submit" value="Add" style="width: 100px;"/> &nbsp; <input type="reset" onclick="resetAdd()" value="Reset" style="width: 100px;" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DOCUMENTTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/LoadDocumentTypeMgtServlet">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Document Type Code </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.docCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Document Name </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.docName}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Verification Category  </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.verifyCatDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>PostFix Code </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.postFix}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.status}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Card Category </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.cardCategoryDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Mandatory </td>
                                                    <td>&nbsp;</td>
                                                    <td>${dataBean.isMandatoryDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Back" style="width: 100px;"/> </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DOCUMENTTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/AddDocumentTypeMgtServlet">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Document Type Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="docCode" value="${dataBean.docCode}" maxlength="32"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Document Name </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="docName" value="${dataBean.docName}" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Verification Category  </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="verifiCat">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.catList}">

                                                                <c:if test="${statusVal.vrifyCode ==dataBean.verifyCat}">
                                                                    <option selected="" value="${statusVal.vrifyCode}">${statusVal.descirption}</option>                       
                                                                </c:if>

                                                                <c:if test="${statusVal.vrifyCode !=dataBean.verifyCat}">
                                                                    <option  value="${statusVal.vrifyCode}">${statusVal.descirption}</option>                       
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
                                                    <td>PostFix Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="postfix" value="${dataBean.postFix}" maxlength="16"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.secList}">
                                                                <c:if test="${statusVal.statusCode ==dataBean.status}">
                                                                    <option selected="" value="${statusVal.statusCode}">${statusVal.description}</option>
                                                                </c:if>
                                                                <c:if test="${statusVal.statusCode !=dataBean.status}">
                                                                    <option value="${statusVal.statusCode}">${statusVal.description}</option>
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
                                                    <td>Card Category</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="cardCategoty">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.cardCatList}">
                                                                <c:choose>
                                                                    <c:when test="${statusVal.categoryCode ==dataBean.cardCategory}">
                                                                        <option  selected="" value="${statusVal.categoryCode}">${statusVal.description}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${statusVal.categoryCode}">${statusVal.description}</option>
                                                                    </c:otherwise>
                                                                </c:choose>

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
                                                    <td>Mandatory</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${dataBean.isMandatory!=null && dataBean.isMandatory} ">
                                                                <input type="radio" name="mandatory" value="1" checked="true"/>YES&nbsp;&nbsp;
                                                                <input type="radio" name="mandatory" value="0" />NO                                                         </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="mandatory" value="1" />YES&nbsp;&nbsp;
                                                                <input type="radio" name="mandatory" value="0" checked="true"/>NO 
                                                            </c:otherwise>
                                                        </c:choose>
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
                                                    <td style="width: 300px;"><input type="submit" value="Add" style="width: 100px;"/> &nbsp; <input type="reset" onclick="resetAdd()" value="Reset" style="width: 100px;" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DOCUMENTTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateDocumentTypeServlet">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Document Type Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" readonly="" name="docCode" value="${dataBean.docCode}" maxlength="32"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Document Name </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="docName" value="${dataBean.docName}" maxlength="64" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Verification Category  </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="verifiCat">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.catList}">

                                                                <c:if test="${statusVal.vrifyCode ==dataBean.verifyCat}">
                                                                    <option selected="" value="${statusVal.vrifyCode}">${statusVal.descirption}</option>                       
                                                                </c:if>

                                                                <c:if test="${statusVal.vrifyCode !=dataBean.verifyCat}">
                                                                    <option  value="${statusVal.vrifyCode}">${statusVal.descirption}</option>                       
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
                                                    <td>PostFix Code </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td><input type="text" name="postfix" value="${dataBean.postFix}" maxlength="16"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="status">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.secList}">
                                                                <c:if test="${statusVal.statusCode ==dataBean.status}">
                                                                    <option selected="" value="${statusVal.statusCode}">${statusVal.description}</option>
                                                                </c:if>
                                                                <c:if test="${statusVal.statusCode !=dataBean.status}">
                                                                    <option value="${statusVal.statusCode}">${statusVal.description}</option>
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
                                                    <td>Card Category</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <select name="cardCategoty">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="statusVal" items="${requestScope.cardCatList}">

                                                                <c:if test="${dataBean.cardCategory !=null && statusVal.categoryCode ==dataBean.cardCategory}">
                                                                    <option  selected="" value="${statusVal.categoryCode}">${statusVal.description}</option>
                                                                </c:if>
                                                                <c:if test="${statusVal.categoryCode != dataBean.cardCategory}">
                                                                    <option value="${statusVal.categoryCode}">${statusVal.description}</option>
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
                                                    <td>Mandatory</td>
                                                    <td><span class="required">*</span>&nbsp;</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when  test="${dataBean.isMandatory!=null && dataBean.isMandatory}"> 
                                                                <input type="radio" name="mandatory" value="1" checked="true"/>YES&nbsp;&nbsp;
                                                                <input type="radio" name="mandatory" value="0" />NO   
                                                            </c:when >
                                                            <c:otherwise>
                                                                <input type="radio" name="mandatory" value="true" />YES&nbsp;&nbsp;
                                                                <input type="radio" name="mandatory" value="false" checked="true"/>NO 
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td> 
                                                    <td style="width: 300px;"><input type="submit" value="Update" style="width: 100px;"/> &nbsp; <input type="reset" onclick="updateDoc('${dataBean.docCode}')" value="Reset" style="width: 100px;" /></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DOCUMENTTYPEMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
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
                                <table border="1" class="display" id="tableview3">
                                    <thead>
                                        <tr>
                                            <th>Document Type Code</th>
                                            <th>Document Type Name</th>
                                            <th>Verification Code</th>
                                            <th>PostFix code</th>
                                            <th>Status</th>
                                            <th>Card Category</th>
                                            <th>Mandatory</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.docAllList}" var="allList">
                                            <tr>
                                                <td>${allList.docCode}</td>
                                                <td>${allList.docName}</td>
                                                <td>${allList.verifyCat}</td>
                                                <td>${allList.postFix}</td>
                                                <td>${allList.status}</td>
                                                <td>${allList.cardCategoryDes}</td>
                                                <td>${allList.isMandatoryDes}</td>
                                                <td><a  href='#'onclick="viewDoc('${allList.docCode}')">View</a></td>
                                                <td><a  href='#'onclick="updateDoc('${allList.docCode}')">Update</a></td>
                                                <td><a  href='#'onclick="deleteDoc('${allList.docCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>

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
