<%-- 
    Document   : eodprocesscategorymgthome
    Created on : Oct 12, 2012, 1:52:34 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />

        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addForm.action="${pageContext.request.contextPath}/AddEodProcessCategoryMgtServlet";
                document.addForm.submit();

            }
            function invokeReset(ele){

                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                        
                    }
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }
                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';

            }
            function invokeUpdateCancel(){

                window.location = "${pageContext.request.contextPath}/LoadEodProcessCategoryMgtServlet";

            }
            
            function invokeUpdate(categoryCode)
            {
                window.location="${pageContext.request.contextPath}/LoadUpdateEodProcessCategoryMgtServlet?categoryCode="+categoryCode;
               

            }
            function invokeUpdateReset(categoryCode)
            {
                window.location="${pageContext.request.contextPath}/LoadUpdateEodProcessCategoryMgtServlet?categoryCode="+categoryCode;
               

            }
            
            function invokeUpdateConfiremd()
            {

                document.updateForm.action="${pageContext.request.contextPath}/UpdateEodProcessCategoryMgtServlet";
                document.updateForm.submit();

            }
            
            function invokeView(categoryCode)
            {

                window.location="${pageContext.request.contextPath}/ViewEodProcessCategoryMgtServlet?categoryCode="+categoryCode;
                

            }
            
            function ConfirmDelete(categoryCode)
            {
                answer = confirm("Are you sure you want to delete this Category Id?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteEodProcessCategoryMgtServlet?categoryCode="+categoryCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadEodProcessCategoryMgtServlet";
                }
                
            }
            function invokeBack(){

                window.location = "${pageContext.request.contextPath}/LoadEodProcessCategoryMgtServlet";

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
                { pagecode:'<%= PageVarList.EOD_PROCESS_CATEGORY%>'                                  
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



                <div class="content" >

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
                                        <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                            <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                <c:if test="${operationtype=='ADD'}">
                                    <form action="" method="POST" name="addForm" >

                                        <table cellpadding="0" cellspacing="10" >

                                            <tbody>

                                                <!--                                                <tr>
                                                                                                    <td>Service Code</td>
                                                                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="serviceCode" class="inputfield-mandatory" maxlength="3" value='${serviceBean.serviceCode}'></td>
                                                                                                </tr>-->

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="50" value='${categoryBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">Dependency Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="dependancyStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:if test="${categoryBean.dependancyStatus=='1'}">
                                                                <option value="1" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${categoryBean.dependancyStatus!='1'}">
                                                                <option value="1" >YES</option>
                                                            </c:if>   
                                                            <c:if test="${categoryBean.dependancyStatus=='0'}">
                                                                <option value="0" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${categoryBean.dependancyStatus!='1'}">
                                                                <option value="0">NO</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="status" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${categoryBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${categoryBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table  cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td></td>
                                                    <td><input type="button" style="width: 100px" name="add" value="Add" onclick="invokeAdd()" /></td>
                                                    <td>  <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeReset(this.form)"/></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->

                                <c:if test="${operationtype=='VIEW'}" >
                                    <form action="" method="POST" name="viewForm">

                                        <table cellpadding="0" cellspacing="10" >
                                            <tbody>
                                                <tr>
                                                    <td>Process Category Code</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.categoryCode}</td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.description}</td>
                                                </tr>

                                                <tr>
                                                    <td>Dependency Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.dependancyStatus}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.statusDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Last Updated User</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.lastUpdatedUser}</td>
                                                </tr>

                                                <tr>
                                                    <td>Last Updated Time</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.lastUpdatedTime}</td>
                                                </tr>

                                                <tr>
                                                    <td>Created Time</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${categoryBean.createdTime}</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="reset" style="width: 100px" name="reset" value="Back" onclick="invokeBack()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        

                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='UPDATE'}" >
                                    <form method="POST" action="" name="updateForm">


                                        <table cellpadding="0" cellspacing="10" >

                                            <tbody>

                                                <tr>
                                                    <td>Process Category Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="categoryCode" class="inputfield-mandatory" maxlength="3" value='${categoryBean.categoryCode}' readonly="readonly"></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="50" value='${categoryBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">Dependency Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="dependancyStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:if test="${categoryBean.dependancyStatus=='1'}">
                                                                <option value="1" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${categoryBean.dependancyStatus!='1'}">
                                                                <option value="1" >YES</option>
                                                            </c:if>   
                                                            <c:if test="${categoryBean.dependancyStatus=='0'}">
                                                                <option value="0" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${categoryBean.dependancyStatus!='1'}">
                                                                <option value="0">NO</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select style="width: 160px;" name="status" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${categoryBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${categoryBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <!--                                                <tr>  
                                                                                                    <td><input type="hidden" name="oldValue" value='${serviceBean.oldValue}' hidden="hidden"></td>
                                                                                                </tr>-->
                                            </tbody>
                                        </table>

                                        <table  cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td></td>
                                                    <td><input type="button" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" /></td>
                                                    <td>  <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${categoryBean.categoryCode}')"/></td>
                                                    <td>  <input type="button" style="width: 100px" name="cancel" value="Cancel" onclick="invokeUpdateCancel()"/></td>
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
                                            <th>Process Category Code</th>
                                            <th>Description</th>
                                            <th>Dependency Status</th>
                                            <th>Status</th>
                                            <th>Last Updated User</th>
                                            <th>Last Updated TIme</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >

                                        <c:forEach var="category" items="${categoryList}">
                                            <tr>
                                                <td >${category.categoryCode}</td>
                                                <td >${category.description}</td>
                                                <td>${category.dependancyStatus}</td>
                                                <td >${category.statusDes}</td>
                                                <td >${category.lastUpdatedUser}</td>
                                                <td >${category.lastUpdatedTime}</td>

                                                <td  ><a href='#' onclick="invokeView('${category.categoryCode}')">View</a></td>
                                                <td  ><a href='#' onclick="invokeUpdate('${category.categoryCode}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${category.categoryCode}')">Delete</a></td>

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
