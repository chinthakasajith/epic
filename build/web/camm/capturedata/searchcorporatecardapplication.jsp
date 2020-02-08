<%-- 
    Document   : searchcorporatecardapplication
    Created on : May 10, 2013, 3:49:55 PM
    Author     : badrika
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeCorporateSearch()
            {

                document.searchcorparatecard.action="${pageContext.request.contextPath}/SearchCorporateCardApplicationServlet";
                document.searchcorparatecard.submit();

            }
            
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadCorporateCardApplicationServlet";

            }


    

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CORPORATE_CARD_DATA%>'                                  
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


        <title>EPIC CMS</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchcorparatecard">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td width="200px">Application ID</td>
                                                <td><input type="text"  value="${userassignappbean.applicationId}" name="appliactionid" maxlength="16"></td>
                                                <td></td>
                                            </tr>


                                            <tr>
                                                <td>Priority Level </td>
                                                <td>
                                                    <select  name="prioritycode">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="priority" items="${priorityLevelList}">
                                                            <c:if test="${userassignappbean.priorityLevel==priority.key}">
                                                                <option value="${priority.key}" selected>${priority.value}</option>
                                                            </c:if>
                                                            <c:if test="${userassignappbean.priorityLevel != priority.key}">
                                                                <option value="${priority.key}" >${priority.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Card Category </td>
                                                <td>
                                                    <select  name="cardcategory">
                                                        <option value="" >--SELECT--</option>

                                                        <c:forEach var="cardCategoryLst" items="${sessionScope.SessionObject.cardCategoryLst}">

                                                            <option value="${cardCategoryLst.categoryCode}" >${cardCategoryLst.description}</option>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>




                                            <tr>
                                                <td>From</td>

                                                <td>
                                                    <input  name="fromdate" readonly maxlength="16" value="${userassignappbean.fromDate}" key="fromdate" id="fromdate"  />

                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#fromdate" ).datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd" ,
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                        });
                                                    </script>

                                                </td>

                                            </tr>





                                            <tr>
                                                <td>To</td>

                                                <td>
                                                    <input  name="todate" readonly maxlength="16" value="${userassignappbean.toDate}" key="todate" id="todate"  />

                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#todate" ).datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd" ,
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                        });
                                                    </script>

                                                </td>

                                            </tr>




                                            <tr>
                                                <td></td>
                                                <td style="width: 300px"><input type="button" value="Search" name="search" style="width: 100px" onclick="invokeCorporateSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.DATAASSIGNUPDATE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                            </tr>

                                            <tr><td style="height: 10px"></td></tr>     
                                        </tbody>
                                    </table>
                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>



                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Application Id </th>
                                            <th>Identification Type</th>
                                            <th>Identification No</th>
                                            <th>Priority Level</th>
                                            <th>Card Category</th>
                                            <th>Employee Number</th>
                                            <th>Branch Id</th>
                                            <th>Assign User</th>
                                            <th>Edit</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="assignapp" items="${sessionScope.SessionObject.userAssignDebitAppList}">
                                            <tr>
                                                <td >${assignapp.applicationId}</td>
                                                <td >${assignapp.identityType}</td>
                                                <td >${assignapp.identityNo}</td>
                                                <td >${assignapp.priorityDescription}</td>
                                                <td >${assignapp.cardCategory}</td>
                                                <td >${assignapp.referralEmpNo}</td>
                                                <td >${assignapp.referralBranchName}</td>
                                                <td >${assignapp.assignUser}</td>
                                                <c:if test="${assignapp.cardCategory == 'Establishment'}">
<!--                                                    <td  ><a href='${pageContext.request.contextPath}/LoadEstablishCardFormServlet?applicationid=${assignapp.applicationId}&cardcategory=${assignapp.cardCategory}'>Edit</a></td>-->
                                                        <td  ><a href='${pageContext.request.contextPath}/LoadDebitCardApplicationFormServlet?applicationid=${assignapp.applicationId}&cardcategory=${assignapp.cardCategory}'>Edit</a></td>                                                </c:if>
                                                <c:if test="${assignapp.cardCategory == 'Corporate'}">

                                                    <td  ><a href='${pageContext.request.contextPath}/LoadCorporateCustomerServlet?applicationid=${assignapp.applicationId}&cardcategory=${assignapp.cardCategory}'>Edit</a></td>

                                                    <td  ><a href=''>Edit</a></td>

                                                </c:if>                                                  

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

