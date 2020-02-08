<%-- 
    Document   : servicecodemgthome
    Created on : Jul 13, 2012, 11:46:06 AM
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

                document.addServiceCodeForm.action = "${pageContext.request.contextPath}/AddServiceCodeMgtServlet";
                document.addServiceCodeForm.submit();

            }
            //            function invokeReset(ele){
            //
            //                tags = ele.getElementsByTagName('input');
            //                for(i = 0; i < tags.length; i++) {
            //                    switch(tags[i].type) {
            //                        case 'text':
            //                            tags[i].value = '';
            //                            break;
            //                        
            //                    }
            //                }
            //                
            //                tags = ele.getElementsByTagName('select');
            //                for(i = 0; i < tags.length; i++) {
            //                    if(tags[i].type == 'select-one') {
            //                        tags[i].selectedIndex = 0;
            //                    }
            //                    else {
            //                        for(j = 0; j < tags[i].options.length; j++) {
            //                            tags[i].options[j].selected = false;
            //                        }
            //                    }
            //                }
            //                document.getElementById('errorMsg').innerHTML = '';
            //                document.getElementById('successMsg').innerHTML = '';
            //
            //            }
            function invokeReset(ele) {
                window.location = "${pageContext.request.contextPath}/LordServiceCodeMgtServlet";

            }

            function invokeUpdateCancel() {

                window.location = "${pageContext.request.contextPath}/LordServiceCodeMgtServlet";

            }

            function invokeUpdate(serviceCode)
            {
                window.location = "${pageContext.request.contextPath}/LordUpdateServiceCodeMgtServlet?serviceCode=" + serviceCode;


            }
            function invokeUpdateReset(serviceCode)
            {
                window.location = "${pageContext.request.contextPath}/LordUpdateServiceCodeMgtServlet?serviceCode=" + serviceCode;


            }

            function invokeUpdateConfiremd()
            {

                document.updateServiceCodeForm.action = "${pageContext.request.contextPath}/UpdateServiceCodeMgtServlet";
                document.updateServiceCodeForm.submit();

            }

            function invokeView(serviceCode)
            {

                window.location = "${pageContext.request.contextPath}/ViewServiceCodeMgtServlet?serviceCode=" + serviceCode;


            }

            function ConfirmDelete(serviceCode)
            {
//                answer = confirm("Are you sure you want to delete this Service Code?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteServiceCodeMgtServlet?serviceCode="+serviceCode;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LordServiceCodeMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Are you sure you want to delete Service Code "+serviceCode+" ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LordServiceCodeMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteServiceCodeMgtServlet?serviceCode=" + serviceCode;
                        }
                    }
                });

            }
            function invokeBack() {

                window.location = "${pageContext.request.contextPath}/LordServiceCodeMgtServlet";

            }



        </script>   
        <script>


            $(document).ready(function () {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.SERVICE_CODE%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

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
                                    <form action="" method="POST" name="addServiceCodeForm" >

                                        <table cellpadding="0" cellspacing="10" >


                                            <tbody>

                                                <tr>
                                                    <td>Service Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="serviceCode" class="inputfield-mandatory" maxlength="3" value='${serviceBean.serviceCode}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="50" value='${serviceBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">International Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="internationalStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>


                                                            <c:if test="${serviceBean.internationalStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.internationalStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.internationalStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.internationalStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>


                                                        </select></td>


                                                </tr>

                                                <tr>
                                                    <td width ="200px;">ATM Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select name="atmStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${serviceBean.atmStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.atmStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.atmStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.atmStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>

                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Pin Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="pinStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${serviceBean.pinStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.pinStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.pinStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.pinStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>

                                                        </select></td>

                                                </tr>

                                                <tr>
                                                    <td>Authorisation Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="authStatus"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${serviceBean.authStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.authStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.authStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.authStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>

                                                        </select></td>

                                                </tr>



                                                <tr>
                                                    <td>Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="status" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${serviceBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${serviceBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
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
                                    <form action="" method="POST" name="viewServiceCodeForm">



                                        <table cellpadding="0" cellspacing="10" >
                                            <tbody>
                                                <tr>
                                                    <td>Service Code</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.serviceCode}</td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.description}</td>
                                                </tr>

                                                <tr>
                                                    <td>International Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.internationalStatus}</td>

                                                </tr>

                                                <tr>
                                                    <td>ATM Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.atmStatus}</td>

                                                </tr>

                                                <tr>
                                                    <td>Pin Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.pinStatus}</td>

                                                </tr>

                                                <tr>
                                                    <td>Authorisation Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.authStatus}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Last Updated User</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.lastUpdatedUser}</td>
                                                </tr>
                                                <tr>
                                                    <td>Last Updated Time</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.lastUpdatedTime}</td>
                                                </tr>
                                                <tr>
                                                    <td>Created Time</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${serviceBean.createTime}</td>
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
                                    <form method="POST" action="" name="updateServiceCodeForm">


                                        <table cellpadding="0" cellspacing="10" >


                                            <tbody>

                                                <tr>
                                                    <td>Service Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" readonly name="serviceCode" class="inputfield-mandatory" maxlength="3" value='${serviceBean.serviceCode}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="50" value='${serviceBean.description}'></td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;">International Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="internationalStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>


                                                            <c:if test="${serviceBean.internationalStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.internationalStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.internationalStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.internationalStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>


                                                        </select></td>


                                                </tr>

                                                <tr>
                                                    <td width ="200px;">ATM Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="atmStatus"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${serviceBean.atmStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.atmStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.atmStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.atmStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>

                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td>Pin Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select name="pinStatus" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${serviceBean.pinStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.pinStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.pinStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.pinStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>

                                                        </select></td>

                                                </tr>

                                                <tr>
                                                    <td>Authorisation Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="authStatus"   class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <c:if test="${serviceBean.authStatus=='YES'}">
                                                                <option value="YES" selected="true" >YES</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.authStatus!='YES'}">
                                                                <option value="YES" >YES</option>
                                                            </c:if>   

                                                            <c:if test="${serviceBean.authStatus=='NO'}">
                                                                <option value="NO" selected="true">NO</option>
                                                            </c:if>
                                                            <c:if test="${serviceBean.authStatus!='NO'}">
                                                                <option value="NO">NO</option>
                                                            </c:if>

                                                        </select></td>

                                                </tr>



                                                <tr>
                                                    <td>Status</td>

                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="status" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${serviceBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected="true" >${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${serviceBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>  
                                                    <td><input type="hidden" name="oldValue" value='${serviceBean.oldValue}' hidden="hidden"></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <table  cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdateConfiremd()" /></td>
                                                    <td>  <input type="button" style="width: 100px" name="reset" value="Reset" onclick="invokeUpdateReset('${serviceBean.serviceCode}')"/></td>
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
                                            <th>Service Code</th>
                                            <th>Description</th>
                                            <th>Status</th>
                                            <th>Last Updated User</th>
                                            <th>Last Updated Date</th>



                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="service" items="${serviceList}">
                                            <tr>

                                                <td >${service.serviceCode}</td>
                                                <td >${service.description}</td>
                                                <td >${service.statusDes}</td>
                                                <td >${service.lastUpdatedUser}</td>
                                                <td >${service.lastUpdatedTime}</td>

                                                <td  ><a href='#' onclick="invokeView('${service.serviceCode}')">View</a></td>
                                                <td  ><a href='#' onclick="invokeUpdate('${service.serviceCode}')">Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${service.serviceCode}')">Delete</a></td>

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
                                            	  <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>