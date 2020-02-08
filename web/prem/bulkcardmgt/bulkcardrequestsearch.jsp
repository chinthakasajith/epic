<%-- 
    Document   : bulkcardrequestsearch
    Created on : Sep 7, 2012, 10:41:09 AM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script language="javascript">           
            
            function LoadCardProduct(){
                $('#cardProduct').empty();
                                
                var sval=$("#cardType").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadCardProductsServlet", 
                { 
                    cdtype : sval                
                    
                },
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#cardProduct').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
            
            function invokeSearch()
            {
                document.searchbulkcardform.action="${pageContext.request.contextPath}/SearchBulkCardServlet?back=no";
                document.searchbulkcardform.submit();
            }
            
            function invokeReset(){
                
//                $("#batchID").val("");
//                $("#cardDomain").val("");
//                $("#cardType").val("");                
//                $("#cardProduct").val("");
//                $("#branchName").val("");
//                $("#prioLevel").val("");
//                $("#proMode").val("");
//                $("#fromdate").val("");
//                $("#todate").val("");
                
               window.location = "${pageContext.request.contextPath}/LoadConfirmSearchServlet";
            }
            

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULK_CD_CONFIRM%>'                                  
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

                    <jsp:include page="/leftmenu.jsp"/>

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

                                <form method="POST" action="" name="searchbulkcardform">

                                    <table border="0" >

                                        <tr style="height: 25px;"> <td>Batch ID </td> 
                                            <td><input type="text" name="batchID" id="batchID" value="${searchBean.batchID}" maxlength="10" /></td>
                                        </tr>

                                        <tr style="height: 25px;"> <td style="width: 150px;">Card Domain </td>  
                                            <td><select id="cardDomain" name="cardDomain" class="inputfield-mandatory" style="width: 168px;">
                                                    <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="domain" items="${sessionScope.SessionObject.cardDomainsList}">

                                                        <c:if test="${searchBean.cdDomain==domain.domainID}">
                                                            <option value="${domain.domainID}" selected>${domain.descrip}</option>
                                                        </c:if>
                                                        <c:if test="${searchBean.cdDomain!=domain.domainID}">
                                                            <option value="${domain.domainID}">${domain.descrip}</option>

                                                        </c:if>


                                                    </c:forEach>


                                                </select>
                                            </td>
                                        </tr>

                                        <tr style="height: 25px;"> <td>Card Type </td>  
                                            <td><select id="cardType" name="cardType" class="inputfield-mandatory" onchange="LoadCardProduct()" style="width: 168px;">
                                                    <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="cardType" items="${cardTypeList}">
                                                        <c:if test="${searchBean.cdType==cardType.key}">
                                                            <option value="${cardType.key}" selected>${cardType.value}</option>
                                                        </c:if>
                                                        <c:if test="${searchBean.cdType!=cardType.key}">
                                                            <option value="${cardType.key}" >${cardType.value}</option>
                                                        </c:if>
                                                    </c:forEach>


                                                </select>
                                            </td>
                                        </tr>

                                        <tr style="height: 25px;"> <td>Card Product </td>  
                                            <td><select id="cardProduct" name="cardProduct" class="inputfield-mandatory" style="width: 168px;">
                                                    <option value="" selected>--SELECT--</option>

                                                </select>
                                            </td>
                                        </tr>

                                        <tr style="height: 25px;"> <td>Branch Name </td>  
                                            <td><select id="branchName" name="branchName" class="inputfield-mandatory" style="width: 168px;">
                                                    <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="Branch" items="${branchList}">
                                                        <c:if test="${searchBean.branchCode==Branch.key}">
                                                            <option value="${Branch.key}" selected>${Branch.value}</option>
                                                        </c:if>
                                                        <c:if test="${searchBean.branchCode != Branch.key}">
                                                            <option value="${Branch.key}" >${Branch.value}</option>
                                                        </c:if>
                                                    </c:forEach>


                                                </select>
                                            </td>
                                        </tr>

                                        <tr style="height: 25px;"> <td>Priority Level </td>  
                                            <td><select id="prioLevel" name="prioLevel" class="inputfield-mandatory" style="width: 168px;">
                                                    <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="priority" items="${priorityLevelList}">
                                                        <c:if test="${searchBean.priorityLvl==priority.key}">
                                                            <option value="${priority.key}" selected>${priority.value}</option>
                                                        </c:if>
                                                        <c:if test="${searchBean.priorityLvl != priority.key}">
                                                            <option value="${priority.key}" >${priority.value}</option>
                                                        </c:if>
                                                    </c:forEach>


                                                </select>
                                            </td>
                                        </tr>

                                        <tr style="height: 25px;"> <td>Production Mode </td>  
                                            <td><select id="proMode" name="proMode" class="inputfield-mandatory" style="width: 168px;">
                                                    <option value="" selected>--SELECT--</option>
                                                    <c:forEach var="pro" items="${sessionScope.SessionObject.productionModeList}">

                                                        <c:if test="${searchBean.productMode==pro.productionModeCode}">
                                                            <option value="${pro.productionModeCode}" selected>${pro.description}</option>
                                                        </c:if>
                                                        <c:if test="${searchBean.productMode!=pro.productionModeCode}">
                                                            <option value="${pro.productionModeCode}">${pro.description}</option>

                                                        </c:if>


                                                    </c:forEach>


                                                </select>
                                            </td>
                                        </tr>

                                        <tr style="height: 25px;">
                                            <td>From</td>

                                            <td>
                                                <input  name="fromdate" readonly maxlength="16" value="${searchBean.fromDate}" key="fromdate" id="fromdate"  />
                                                <img src="<%=request.getContextPath()%>/resources/calender/images/calendar.png" id="trigger1" alt="Use this calendar to select date" />
                                                <script type="text/javascript">
                                                    Calendar.setup({
                                                        inputField  : "fromdate",           // id of the input field
                                                        button      : "trigger1",        // trigger for the calendar (button ID)
                                                        ifFormat    : "%Y-%m-%d", // format of the input field
                                                        showsTime   : true,
                                                        timeFormat  : "12",
                                                        showOthers  : true
                                                    });
                                                </script>

                                            </td>

                                        </tr>

                                        <tr style="height: 25px;">
                                            <td>To</td>

                                            <td>
                                                <input  name="todate" readonly maxlength="16" value="${searchBean.toDate}" key="todate" id="todate"  />
                                                <img src="<%=request.getContextPath()%>/resources/calender/images/calendar.png" id="trigger2" alt="Use this calendar to select date" />
                                                <script type="text/javascript">
                                                    Calendar.setup({
                                                        inputField  : "todate",           // id of the input field
                                                        button      : "trigger2",        // trigger for the calendar (button ID)
                                                        ifFormat    : "%Y-%m-%d", // format of the input field
                                                        showsTime   : true,
                                                        timeFormat  : "12",
                                                        showOthers  : true
                                                    });
                                                </script>

                                            </td>

                                        </tr>

                                        <tr style="height: 50px;">
                                            <td></td>
                                            <td><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeSearch()">
                                                <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                            <td></td>
                                        </tr>

                                    </table>

                                </form>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Batch ID </th>
                                            <th>Card Domain</th>
                                            <th>Card Type</th>
                                            <th>Card Product</th>
                                            <th>Branch Name</th>
                                            <th>Priority Level</th>
                                            <th>Production Mode</th>

                                            <th>View</th>


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="list" items="${searchList}">
                                            <tr>
                                                <td >${list.batchID}</td>
                                                <td >${list.cdDomainDes}</td>
                                                <td >${list.cdTypeDes}</td>
                                                <td >${list.cdProductDes}</td>
                                                <td >${list.branchName}</td>
                                                <td >${list.priorityLvlDes}</td>
                                                <td >${list.productModeDes}</td>

                                                <td><a href='${pageContext.request.contextPath}/LoadBulkCardDetailsServlet?batchid=${list.batchID}'>View</a></td>

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
