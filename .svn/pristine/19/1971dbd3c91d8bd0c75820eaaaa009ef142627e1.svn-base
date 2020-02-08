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

        <jsp:include page="/content.jsp"/>

        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
            function invokeAdd()
            {

                document.addPostalCodesForm.action="${pageContext.request.contextPath}/AddPostalCodeServlet";
                document.addPostalCodesForm.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadPostalCodeMgtServlet";

            }
            
            function invokeResetUpdate(postalCode){

                window.location = "${pageContext.request.contextPath}/UpdatePostalCodeMgtServlet?postalCode ="+postalCode;

            }
            
            function invokeUpdate()
            {

                document.updatePostalCodeForm.action="${pageContext.request.contextPath}/UpdateConfiremedPostalCodeMgtServlet";
                document.updatePostalCodeForm.submit();

            }
            
            function ConfirmDelete(postalCode)
            {
                answer = confirm("Do you really want to delete this record ?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeletePostalCodeServlet?postalCode="+postalCode;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadPostalCodeMgtServlet";
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
                { pagecode:'<%= PageVarList.POSTALCODE%>'                                  
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
                                <c:if test="${operationtype=='default'}">
                                    <form action="" method="POST" name="addPostalCodesForm" >
                                        <table>
                                            <tr>
                                                <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                    <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table>


                                                <tbody>

                                                    <tr>
                                                        <td>Postal Code (Zip Code)</td>
                                                        <td><input type="text" name="postalCode" class="inputfield-Description-mandatory" maxlength="5" value='${postalCodeBean.postalCode}'></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td>Country Code</td>

                                                    <td>
                                                        <select  name="countryCode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="country" items="${countryCodeList}">

                                                                <c:if test="${postalCodeBean.countryCode==country.countryCode}">
                                                                    <option value="${country.countryCode}" selected>${country.description}</option>
                                                                </c:if>
                                                                <c:if test="${PostalCodeBean.countryCode!=country.countryCode}">
                                                                    <option value="${country.countryCode}">${country.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>
                                                </tr>
                                                <tr>
                                                    <td>City</td>
                                                    <td><input type="text" name="city" class="inputfield-Description-mandatory" maxlength="32" value='${postalCodeBean.city}'></td>
                                                    <td></td>
                                                </tr>




                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" name="add" value="Add" onclick="invokeAdd()" />
                                                        <input type="button" name="reset" value="Reset" onclick="invokeReset()"/>
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

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Country Code</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${postal.countryCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>City</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${postal.city}</td>
                                                </tr>
                                                <tr>
                                                    <td>Postal Code</td>
                                                    <td></td>
                                                    <td> - </td>
                                                    <td></td>
                                                    <td>${postal.postalCode}</td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>        


                                <!--/////////////////////////////////////////////End View records  ///////////////////////////////////////////////////////////-->

                                <!--/////////////////////////////////////////////Start Update records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updatePostalCodeForm">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>
                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td>Postal Code (Zip Code)</td>
                                                    <td><input type="text" name="postalCode" readonly="true" class="inputfield-Description-mandatory" maxlength="5" value='${postal.postalCode}'></td>
                                                    <td></td>
                                                </tr>


                                                <tr>
                                                    <td>Country Code</td>

                                                    <td>
                                                        <select  name="countryCode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="country" items="${countryCodeList}">

                                                                <c:if test="${postal.countryCode==country.countryCode}">
                                                                    <option value="${country.countryCode}" selected>${country.description}</option>
                                                                </c:if>
                                                                <c:if test="${postal.countryCode!=country.countryCode}">
                                                                    <option value="${country.countryCode}">${country.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>
                                                </tr>

                                                <tr>
                                                    <td>City</td>
                                                    <td><input type="text" name="city" class="inputfield-Description-mandatory" maxlength="32" value='${postal.city}'></td>
                                                    <td></td>
                                                </tr>



                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" value="Update" name="update" onclick="invokeUpdate()">
                                                        <input type="button" value="Reset" name="reset" onclick="invokeResetUpdate('${postal.countryCode}')"></td>
                                                        <%--    <input type="reset" value="Reset" name="reset"></td>
                                                        --%>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="example">
                                    <thead>
                                        <tr class="gradeB">

                                            <th>Postal Code</th>
                                            <th>Country Code</th>
                                            <th>City</th>



                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="postalCode" items="${sessionScope.SessionObject.postalCodeDetailList}">
                                            <tr>

                                                <td >${postalCode.postalCode}</td>
                                                <td >${postalCode.countryCode}</td>
                                                <td >${postalCode.city}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewPostalCodeMgtServlet?postalCode=<c:out value="${postalCode.postalCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdatePostalCodeMgtServlet?postalCode=<c:out value="${postalCode.postalCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${postalCode.postalCode}')">Delete</a></td>

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
