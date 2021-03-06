<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
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

                document.addTaskform.action="${pageContext.request.contextPath}/LoadInterestProfileServlet";
                //                document.addTaskform.action="${pageContext.request.contextPath}/administrator/controlpanel/profilemgt/interestprofilehome.jsp";
                document.addTaskform.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadTaskMgtServlet";

            }
            
            function invokeUpdate()
            {

                document.updateTaskform.action="${pageContext.request.contextPath}/UpdateConfiremedTaskMgtServlet";
                document.updateTaskform.submit();

            }
        </script>  
        <script>
            function ConfirmDelete(interestProfileCode)
            {
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
                               window.location="${pageContext.request.contextPath}/DeleteInterestProfileServlet?interestProfileCode="+interestProfileCode;
                        }
                    }
                });

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
        <script >
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.INTERESTPROFILE%>'                                  
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

                                <form action="" method="POST" name="addTaskform" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green" id="successMsg"><b><c:out value="${successMsg}"></c:out></b></font>
                                                </td>                                                      
                                            </tr>
                                        </table>

                                        <!--    ///////////////////////////////////////// Start Update /////////////////////////////////////////////// -->

                                    <c:if test="${operationtype=='default'}">
                                        <input type="submit" name="add" value="Create Interest Profile" onclick="invokeAdd()" />
                                    </c:if>

                                    <!--    ///////////////////////////////////////// End Update /////////////////////////////////////////////////  -->

                                    <!--     ///////////////////////////////////////// Start View ///////////////////////////////////////////////// -->

                                    <c:if test="${operationtype=='view'}" >
                                        <form action="" method="POST" name="viewinterestform">

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
                                                        <td>Interest Profile Code</td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.interestFrofileCode}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Description</td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.description}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Status</td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.statusDescription}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Interest Rate </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.interestRate}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Interest Type </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.interestPeriodValue}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Effect Date For New Accounts </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.effectiveDateForNewCustomer}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Effect Date For Exist Accounts 	 </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.effectiveDateForExistCustomer}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Starting From </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.interestCalStartFrom}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>To </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.interestCalStartTo}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Charge Type </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.chargeType}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Charge Type </td>
                                                        <td></td>
                                                        <td> - </td>
                                                        <td></td>
                                                        <td>${interest.chargeType}</td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                            <br></br>
                                            <table>
                                                </tbody>
                                                <tr>

                                                    <td>
                                                        <h4><b>Not Assigned</b></h4>
                                                        <select name="notassignsectionlist" style="width: 160px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notAssigne" items="${notAssigned}">
                                                                <option value="${notAssigne.transactionCode}" >${notAssigne.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                    <td style="width: 40px;"></td><td></td><td></td><td></td><td></td><td></td>


                                                    <td>
                                                        <h4><b>Assigned</b></h4>
                                                        <select name="assignsectionlist" style="width: 160px" id=out multiple="multiple"   size=10>
                                                            <c:forEach var="assig" items="${assigned}">
                                                                <option value="${assig.description}" >${assig.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                </tbody>
                                            </table>
                                        </form>

                                    </c:if>        


                                    <!--    //////////////////////////////////////// End View //////////////////////////////////////////////////// -->


                                    <br></br>

                                    <table  border="1"  class="display" id="example">
                                        <thead>
                                            <tr class="gradeB">
                                                <th>Interest Profile Code</th>
                                                <th>Description</th>
                                                <th>Interest Rate</th>
                                                <th>Interest Period value</th>
                                                <th>Charge Type</th>

                                                <th>View</th>
                                                <th>Update</th>
                                                <th >Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="interest" items="${sessionScope.SessionObject.interestProfileDetails}">
                                                <tr>

                                                    <td >${interest.interestFrofileCode}</td>
                                                    <td >${interest.description}</td>
                                                    <td >${interest.interestRate}</td>
                                                    <td >${interest.interestPeriodValue}</td>
                                                    <td >${interest.chargeType}</td>


                                                    <td  ><a href='${pageContext.request.contextPath}/ViewInterestProfileServlet?interestProfile=<c:out value="${interest.interestFrofileCode}"></c:out>'>View</a></td>
                                                    <td  ><a href='${pageContext.request.contextPath}/UpdateInterestProfileMgtServlet?interestProfileCode=<c:out value="${interest.interestFrofileCode}"></c:out>'>Update</a></td>
                                                    <td ><a  href='#' onclick="ConfirmDelete('${interest.interestFrofileCode}')">Delete</a></td>

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
                                                <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Do you really want to delete this Interest Profile?</p>
        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
