<%-- 
    Document   : bulkcardandpindistributionhome
    Created on : Sep 26, 2012, 9:41:53 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Card and Pin Distribution Page</title>



        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


        <script >
            
           
            function invokeCancel(opType){
                
                window.location = "${pageContext.request.contextPath}/LoadSelectBulkCardPinDistributionServlet?opType="+opType;
            }
            function invokeReset(opType){
                
                window.location = "${pageContext.request.contextPath}/LoadSelectBulkCardPinDistributionServlet?opType="+opType;
               
            }
            
            function invokeCheckAllCard(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = true ;
                
            }
            
            function invokeCheckAllPin(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = true ;
            
               
                
            }
            
            function invokeUnCheckAllCard(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = false ;  
                
            }
            
            function invokeUnCheckAllPin(field)
            {
            
                for (i = 0; i < field.length; i++)
                    field[i].checked = false ;  
                
            }
            
            function invokeDistribute(opType)
            {

                if(opType== 'card'){ 
                    answer = confirm("Are you sure you want to continue this process?")
                    
                    if(answer !=0){
                        document.selectCard.action="${pageContext.request.contextPath}/UpdateBulkCardPinDistributionServlet?opType="+opType;
                        document.selectCard.submit();
                    }
                }
                
                if(opType== 'pin'){  
                    answer = confirm("Are you sure you want to continue this process?")
                
                    if(answer !=0){
                        document.selectPin.action="${pageContext.request.contextPath}/UpdateBulkCardPinDistributionServlet?opType="+opType;
                        document.selectPin.submit();
                    }
                }

            }
            
            
            
            function invokeSearch(opType)
            {
                if(opType== 'card'){ 
                    document.cardDistributionSearchForm.action="${pageContext.request.contextPath}/SearchBulkCardPinDistributionServlet?opType="+opType;
                    document.cardDistributionSearchForm.submit();
                }
                
                if(opType== 'pin'){  
                    document.pinDistributionSearchForm.action="${pageContext.request.contextPath}/SearchBulkCardPinDistributionServlet?opType="+opType;
                    document.pinDistributionSearchForm.submit();
                }

            }
            
            
            function valueSet(opType){
                
                if(opType == 'card'){
                    document.cardDistributionSearchForm.action="${pageContext.request.contextPath}/GetCardProductByCardTypeServlet?opType="+opType;
                    document.cardDistributionSearchForm.submit();
                }
                if(opType == 'pin'){
                    
                    document.pinDistributionSearchForm.action="${pageContext.request.contextPath}/GetCardProductByCardTypeServlet?opType="+opType;
                    document.pinDistributionSearchForm.submit();
                }
            }
            
        </script> 
     <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BULK_CARD_PIN_DISTRIBUTION%>'                                  
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
    <body >

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


                                <!--  -----------------------------------------------------------------   start  developer area  ----------------------------------------------------------                          -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <div class="selector" id="tabs">
                                    <ul>
                                        <li><a href="#tabs-1">Card Distribution</a></li>
                                        <li><a href="#tabs-2">Pin Distribution</a></li>
                                    </ul>

                                    <!--  ////////////////////////      Tab Number1        /////////////////////                            -->

                                    <div id="tabs-1" >

                                        <form name="cardDistributionSearchForm" method="post" >

                                            <table cellpadding="0" cellspacing="10"  >

                                                <tr>
                                                    <td style="width: 200px;">Batch ID</td>
                                                    <td style="width: 200px;">
                                                        <input type="text" name="batchID"  maxlength="16" value="${bulkCardBean.batchID}" style="width: 150px;"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Card Domain</td>
                                                    <td style="width: 200px;">
                                                        <select name="cardDomain" id="domainType" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="domain" items="${cardDomainList}">
                                                                <c:if test="${bulkCardBean.cardDomain==domain.key}">
                                                                    <option value="${domain.key}" selected>${domain.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCardBean.cardDomain != domain.key}">
                                                                    <option value="${domain.key}" >${domain.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Card Type</td>
                                                    <td style="width: 200px;">
                                                        <select name="cardType"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cardTypeList}">
                                                                <c:if test="${bulkCardBean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCardBean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Card Product </td>
                                                    <td style="width: 200px;">
                                                        <select name="cardProduct"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="product" items="${sessionScope.SessionObject.cardProductMgtList}">
                                                                <c:if test="${bulkCardBean.cardProduct==product.productCode}">
                                                                    <option value="${product.productCode}" selected="true" >${product.description}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCardBean.cardProduct!=product.productCode}">
                                                                    <option value="${product.productCode}">${product.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Branch Name</td>
                                                    <td style="width: 200px;">
                                                        <select  name="branchCode"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="branch" items="${branchList}">
                                                                <c:if test="${bulkCardBean.branchCode==branch.key}">
                                                                    <option value="${branch.key}" selected="true" >${branch.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCardBean.branchCode!=branch.key}">
                                                                    <option value="${branch.key}">${branch.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Priority Level</td>
                                                    <td style="width: 200px;">
                                                        <select  name="priorityLvl" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${bulkCardBean.priorityLvl==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkCardBean.priorityLvl != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">From Date</td>
                                                    <td style="width: 200px;">
                                                        <input  name="fromDate" readonly="readonly" maxlength="16" value="${bulkCardBean.fromDate}" key="fromdate" id="fromdate1" style="width: 150px;" />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate1" ).datepicker({
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
                                                    <td style="width: 200px;">To Date</td> 
                                                    <td style="width: 200px;">
                                                        <input  name="toDate" readonly="readonly" maxlength="16" value="${bulkCardBean.toDate}" key="fromdate" id="fromdate2" style="width: 150px;" />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate2" ).datepicker({
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
                                                    <td style="width: 200px;">Requested User</td>
                                                    <td style="width: 200px;">
                                                        <input type="text" name="reqestedUser"  maxlength="16" value="${bulkCardBean.reqestedUser}" style="width: 150px;"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Requested Date</td> 
                                                    <td style="width: 200px;">
                                                        <input  name="createdTime" readonly maxlength="16" value="${bulkCardBean.createdTime}" key="fromdate" id="fromdate3" style="width: 150px;" />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate3" ).datepicker({
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

                                            </table>

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;"></td>
                                                    <td>
                                                        <input type="button" name="search"  style="width: 100px; height: 30px;" onclick="invokeSearch('card')" value="Search">
                                                        <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeReset('card')" value="Reset">
                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                        <br></br>
                                        <form name="selectCard" method="post">
                                            <table  border="1"  class="display" id="scoreltableview2">
                                                <thead>
                                                    <tr class="gradeB">
                                                        <th>Batch ID</th>
                                                        <th>Card Domain</th>
                                                        <th>Card Type</th>
                                                        <th>Card Product</th>
                                                        <th>Branch</th>
                                                        <th>Requested No of Cards</th>
                                                        <th>Approved No of Cards</th>
                                                        <th >Select</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <c:forEach var="dist" items="${bulkCardList}">
                                                        <tr>

                                                            <td >${dist.batchID}</td>
                                                            <td >${dist.cardDomainDes}</td>
                                                            <td >${dist.cardTypeDes}</td>
                                                            <td >${dist.cardProductDes}</td>
                                                            <td >${dist.branchName}</td>
                                                            <td >${dist.reqNumOfCds}</td>
                                                            <td >${dist.apprvNumOfCds}</td>

                                                            <td  ><input type="checkbox" name="checkedVelue" value="${dist.batchID}"></td>

                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table> 

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="checkAll"  style="width: 100px; height: 30px;" onclick="invokeCheckAllCard(document.selectCard.checkedVelue)" value="Check All">
                                                        <input type="button" name="uncheckAll"  style="width: 100px; height: 30px;" onclick="invokeUnCheckAllCard(document.selectCard.checkedVelue)" value="Uncheck All">
                                                        <input type="button" name="distribute"  style="width: 100px; height: 30px;" onclick="invokeDistribute('card')" value="Distribute">
                                                        <input type="button" name="cancel"  style="width: 100px; height: 30px;" onclick="invokeCancel('card')" value="Reset All">
                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////--> 

                                    </div>



                                    <!--    /////////////////    Tab Number2         ///////////////////// -->

                                    <div id="tabs-2">

                                        <form name="pinDistributionSearchForm" method="post" >

                                            <table cellpadding="0" cellspacing="10"  >

                                                <tr>
                                                    <td style="width: 200px;">Batch ID</td>
                                                    <td style="width: 200px;">
                                                        <input type="text" name="batchID"  maxlength="16" value="${bulkPinBean.batchID}" style="width: 150px;"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Card Domain</td>
                                                    <td style="width: 200px;">
                                                        <select name="cardDomain" id="domainType"  >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="domain" items="${cardDomainList}">
                                                                <c:if test="${bulkPinBean.cardDomain==domain.key}">
                                                                    <option value="${domain.key}" selected>${domain.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkPinBean.cardDomain != domain.key}">
                                                                    <option value="${domain.key}" >${domain.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Card Type</td>
                                                    <td style="width: 200px;">
                                                        <select  name="cardType"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="type" items="${sessionScope.SessionObject.cardTypeList}">
                                                                <c:if test="${bulkPinBean.cardType==type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}" selected="true" >${type.description}</option>
                                                                </c:if>
                                                                <c:if test="${bulkPinBean.cardType!=type.cardTypeCode}">
                                                                    <option value="${type.cardTypeCode}">${type.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Card Product </td>
                                                    <td style="width: 200px;">
                                                        <select  name="cardProduct"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="product" items="${sessionScope.SessionObject.cardProductMgtList}">
                                                                <c:if test="${bulkPinBean.cardProduct==product.productCode}">
                                                                    <option value="${product.productCode}" selected="true" >${product.description}</option>
                                                                </c:if>
                                                                <c:if test="${bulkPinBean.cardProduct!=product.productCode}">
                                                                    <option value="${product.productCode}">${product.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Branch Name</td>
                                                    <td style="width: 200px;">
                                                        <select  name="branchCode"  >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="branch" items="${branchList}">
                                                                <c:if test="${bulkPinBean.branchCode==branch.key}">
                                                                    <option value="${branch.key}" selected="true" >${branch.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkPinBean.branchCode!=branch.key}">
                                                                    <option value="${branch.key}">${branch.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Priority Level</td>
                                                    <td style="width: 200px;">
                                                        <select  name="priorityLvl" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${bulkPinBean.priorityLvl==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${bulkPinBean.priorityLvl != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">From Date</td>
                                                    <td style="width: 200px;">
                                                        <input  name="fromDate" readonly maxlength="16" value="${bulkPinBean.fromDate}" key="fromdate" id="fromdate4" style="width: 150px;" />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate4" ).datepicker({
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
                                                    <td style="width: 200px;">To Date</td> 
                                                    <td style="width: 200px;">
                                                        <input  name="toDate" readonly maxlength="16" value="${bulkPinBean.toDate}" key="fromdate" id="fromdate5" style="width: 150px;" />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate5" ).datepicker({
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
                                                    <td style="width: 200px;">Requested User</td>
                                                    <td style="width: 200px;">
                                                        <input type="text" name="reqestedUser"  maxlength="16" value="${bulkPinBean.reqestedUser}" style="width: 150px;"></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 200px;">Requested Date</td> 
                                                    <td style="width: 200px;">
                                                        <input  name="createdTime" readonly maxlength="16" value="${bulkPinBean.createdTime}" key="fromdate" id="fromdate6" style="width: 150px;"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate6" ).datepicker({
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

                                            </table>

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="search"  style="width: 100px; height: 30px;" onclick="invokeSearch('pin')" value="Search">
                                                        <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeReset('pin')" value="Reset">
                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////Start Table Template  ///////////////////////////////////////////////////////////-->

                                        <br></br>
                                        <form name="selectPin" method="post">
                                            <table  border="1"  class="display" id="scoreltableview">
                                                <thead>
                                                    <tr class="gradeB">
                                                        <th>Batch ID</th>
                                                        <th>Card Domain</th>
                                                        <th>Card Type</th>
                                                        <th>Card Product</th>
                                                        <th>Branch</th>
                                                        <th>Requested No of Cards</th>
                                                        <th>Approved No of Cards</th>

                                                        <th >Select</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <c:forEach var="dist" items="${bulkPinList}">
                                                        <tr>

                                                            <td >${dist.batchID}</td>
                                                            <td >${dist.cardDomainDes}</td>
                                                            <td >${dist.cardTypeDes}</td>
                                                            <td >${dist.cardProductDes}</td>
                                                            <td >${dist.branchName}</td>
                                                            <td >${dist.reqNumOfCds}</td>
                                                            <td >${dist.apprvNumOfCds}</td>

                                                            <td  ><input type="checkbox" name="checkedVelue" value="${dist.batchID}"></td>

                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table> 

                                            <table  cellpadding="0" cellspacing="10">

                                                <tr celspacing="5">
                                                    <td style="width: 200px;">
                                                    </td>
                                                    <td><input type="button" name="checkAll"  style="width: 100px; height: 30px;" onclick="invokeCheckAllPin(document.selectPin.checkedVelue)" value="Check All">
                                                        <input type="button" name="uncheckAll"  style="width: 100px; height: 30px;" onclick="invokeUnCheckAllPin(document.selectPin.checkedVelue)" value="Uncheck All">
                                                        <input type="button" name="distribute"  style="width: 100px; height: 30px;" onclick="invokeDistribute('pin')" value="Distribute">
                                                        <input type="button" name="cancel"  style="width: 100px; height: 30px;" onclick="invokeCancel('pin')" value="Reset All">
                                                    </td>
                                                </tr>

                                            </table>
                                        </form>

                                        <!--/////////////////////////////////////////////End Table Template  ///////////////////////////////////////////////////////////-->

                                    </div>
                                </div>

                                <!------------------------------------------------  End Developer Area  --------------------------------------------------------------->
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
