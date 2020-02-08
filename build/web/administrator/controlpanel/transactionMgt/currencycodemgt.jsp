<%-- 
    Document   : currencycodemgt
    Created on : Jan 24, 2012, 8:56:43 AM
    Author     : mahesh_m
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

                document.addCurrencyForm.action="${pageContext.request.contextPath}/AddCurrencyServlet";
                document.addCurrencyForm.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadCurrencyMgtServlet";

            }
            function updateRequest(value){

                window.location = "${pageContext.request.contextPath}/UpdateCurrencyMgtServlet?currencyCode="+value;

            }
            
            function invokeUpdate()
            {

                document.updateCurrencyform.action="${pageContext.request.contextPath}/UpdateConfiremedCurrencyMgtServlet";
                document.updateCurrencyform.submit();

            }
            
            function ConfirmDelete(currencyCode,alphaCode)
            {
                answer = confirm("Do you really want to delete this Currency ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteCurrencyServlet?currencyCode="+currencyCode+"&currencyCodeAlpha="+alphaCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadCurrencyMgtServlet";
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
                { pagecode:'<%= PageVarList.CURRENCY%>'                                  
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

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">




                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>


                                <c:if test="${operationtype=='default'}">
                                    <form action="" method="POST" name="addCurrencyForm" >

                                        <table>


                                            <tbody>

                                                <tr>
                                                    <td>Currency Code Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="currencyCode" class="inputfield-mandatory" maxlength="3" value= "${currencyBean.currencyCode}"></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Currency Code Alpha</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="currencyCodeAlpha" class="inputfield-Description-mandatory" maxlength="3" value="${currencyBean.currencyAlphaCode}"></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Exponent</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="exponent" class="inputfield-Description-mandatory" maxlength="4" value="${currencyBean.exponent}"></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" class="inputfield-mandatory" maxlength="48" value="${currencyBean.currencyDes}"></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory">

                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${currencyBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${currencyBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr style="height: 12px;"></tr>



                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" name="add" value="Add" onclick="invokeAdd()" class="defbutton"/>
                                                        <input type="button" name="Reset" value="reset" onclick="invokeReset()" class="defbutton"/>
                                                    </td>
                                                    <td></td>

                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewCurrencyForm">                                     

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Currency Code Number</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${currency.currencyCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Currency Code Alpha</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${currency.currencyAlphaCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Exponent</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${currency.exponent}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${currency.currencyDes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${currency.statusDes}</td>
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" name="Back" onclick="invokeReset()" class="defbutton"/></td>

                                                </tr>


                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        


                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateCurrencyform">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>Currency Code Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="currencyCode" readonly="true" class="inputfield-mandatory" maxlength="3" value="${currency.currencyCode}"/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Currency Code Alpha</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="currencyCodeAlpha" class="inputfield-Description-mandatory" maxlength="3" value="${currency.currencyAlphaCode}"/></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Exponent</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="exponent" class="inputfield-mandatory" maxlength="4" value="${currency.exponent}"/></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" class="inputfield-mandatory"  maxlength="48" value="${currency.currencyDes}"/></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory">

                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${currency.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${currency.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                    <td></td>
                                                </tr>


                                            <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            </tbody>
                                        </table>

                                        <table>
                                            <tr>
                                                <td style="width: 130pt;"></td>
                                                <td><input type="button" value="Update" name="update" onclick="invokeUpdate()" class="defbutton"/></td>
                                                <td><input type="button" name="reset" value="Reset" onclick="updateRequest('${currency.currencyCode}')" class="defbutton"/></td>
                                                <td><input type="button" value="Back" name="back" onclick="invokeReset()" class="defbutton"/></td>


                                            </tr>

                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Currency Code Number</th>
                                            <th>Currency Code Alpha</th>
                                            <th>Exponent</th>
                                            <th>Description</th>
                                            <th>Status</th>


                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="currency" items="${sessionScope.SessionObject.currencyDetailList}">
                                            <tr>

                                                <td >${currency.currencyCode}</td>
                                                <td >${currency.currencyAlphaCode}</td>
                                                <td >${currency.exponent}</td>
                                                <td >${currency.currencyDes}</td>
                                                <td >${currency.statusDes}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewCurrencyMgtServlet?currencyCode=<c:out value="${currency.currencyCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdateCurrencyMgtServlet?currencyCode=<c:out value="${currency.currencyCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${currency.currencyCode}','${currency.currencyAlphaCode}')">Delete</a></td>

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
