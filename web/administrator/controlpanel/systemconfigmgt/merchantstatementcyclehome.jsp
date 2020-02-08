<%-- 
    Document   : merchantstatementcyclehome
    Created on : Nov 12, 2012, 10:27:13 AM
    Author     : nisansala
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
            
            function backToLoad(){
                window.location = "${pageContext.request.contextPath}/LoadMerchantStatementCycleServlet";
            }         
                        
            function deleteBillingCycle(value){
             
                answer = confirm("Do you really want to delete "+value+" Merchant Statement Cycle?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteMerchantStatementCycleServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadMerchantStatementCycleServlet";
                }

            }
            
            function backToLoadUpdate(billCode){
                document.updateform.action="${pageContext.request.contextPath}/LoadUpdateMerchantStatementCycleServlet?id="+billCode;
                document.updateform.submit();
            }
            
            function changeBillDate(value){
                
                var option = $("#option").val();
                if(option == ''){
                    $("#date").empty();                    
                    $("#date").append("<option value=''>----------SELECT----------</option>");                                                           
                }
                else if(option == '1'){
                    $("#date").append("<option value=''>----------SELECT----------</option>");
                    $("#date").attr("disabled",true);                                       
                }else if(option == '2'){
                    $("#date").attr("disabled",false); 
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>"); 
                    $("#date").append("<option value='1'>Sunday</option>");   
                    $("#date").append("<option value='2'>Monday</option>");   
                    $("#date").append("<option value='3'>Tuesday</option>");   
                    $("#date").append("<option value='4'>Wednesday</option>"); 
                    $("#date").append("<option value='5'>Thursday</option>");
                    $("#date").append("<option value='6'>Friday</option>");   
                    $("#date").append("<option value='7'>Saturday</option>");
                    //                    for(i=1 ; i<8 ;i++){                       
                    //                        $("#date").append("<option>"+i+"</option>");                        
                    //                    }                                        
                }else if(option == '3'){
                    $("#date").attr("disabled",false); 
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");    
                    for(i=1 ; i<29 ;i++){                       
                        $("#date").append("<option>"+i+"</option>");                        
                    }                
                }else if(option == '4'){
                    $("#date").attr("disabled",false); 
                    $("#date").empty();
                    $("#date").append("<option value=''>----------SELECT----------</option>");
                    $("#date").append("<option value='1'>January</option>");
                    $("#date").append("<option value='2'>February</option>");
                    $("#date").append("<option value='3'>March</option>");
                    $("#date").append("<option value='4'>April</option>");
                    $("#date").append("<option value='5'>May</option>");
                    $("#date").append("<option value='6'>June</option>");
                    $("#date").append("<option value='7'>July</option>");
                    $("#date").append("<option value='8'>August</option>");
                    $("#date").append("<option value='9'>September</option>");
                    $("#date").append("<option value='10'>October</option>");
                    $("#date").append("<option value='11'>November</option>");
                    $("#date").append("<option value='12'>December</option>");
                                    
                }
                $("#date").val(value);
            }
            
            function setSelectedIndex(s, v) {
                for ( var i = 0; i < s.options.length; i++ ) {
                    if ( s.options[i].value == v ) {
                        s.options[i].selected = true;
                        return;
                    }
                }
            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.MRCHNT_STATEMENT_CYCLE_MGT%>'                                  
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
    <body onload="changeBillDate(${stateBean.stateDate})">
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

                    <td class="menubar" > <jsp:include page="/leftmenu.jsp" /> </td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------    -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                </table>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" name="addform" action="${pageContext.request.contextPath}/AddMerchantStatementCycleServlet">
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px">Statement Cycle Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="billCycleCode" value="${stateBean.stateCycleCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Statement Option</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="option" id="option" style="width: 163px" onchange="changeBillDate()">
                                                            <option value="">------------SELECT------------</option>
                                                            <c:if test="${stateBean.stateOption == '1'}">
                                                                <option value="1" selected="">Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                                <c:set var="setoption" scope="request" value ="1" />
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == '2'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" selected="">Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == '3'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" selected="">Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == '4'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" selected="">Yearly</option>
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == null or stateBean.stateOption == ''}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Billing Date</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="date" value="" id="date" style="width: 163px">
                                                            <option value="">------------SELECT------------</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${stateBean.stateDescription}" maxlength="64"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${stateBean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true" />Previous Working Day</td>
                                                        </c:if>
                                                        <c:if test="${stateBean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" />Previous Working Day</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${stateBean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" checked="true"  />At Holiday</td>
                                                        </c:if>
                                                        <c:if test="${stateBean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" />At Holiday</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${stateBean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" checked="true" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${stateBean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" />Next Working Day</td>    
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="status" style="width: 163px">
                                                            <option value="" selected>------------SELECT------------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${stateBean.stateStatus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${stateBean.stateStatus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td > 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="backToLoad()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" name="updateform" action="${pageContext.request.contextPath}/UpdateMerchantStatementCycleServlet" >
                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Statement Cycle Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="billCycleCode" value="${stateBean.stateCycleCode}" readonly=""/></td>
                                                </tr>
                                                <tr>
                                                    <td>Statement Option</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="option" id="option" style="width: 163px" onchange="changeBillDate()">
                                                            <option value="">------------SELECT------------</option>
                                                            <c:if test="${stateBean.stateOption == '1'}">
                                                                <option value="1" selected="">Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                                <c:set var="setoption" scope="request" value ="1" />
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == '2'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" selected="">Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == '3'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" selected="">Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == '4'}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" selected="">Yearly</option>
                                                            </c:if>
                                                            <c:if test="${stateBean.stateOption == null or stateBean.stateOption == ''}">
                                                                <option value="1" >Daily</option>
                                                                <option value="2" >Weekly</option>
                                                                <option value="3" >Monthly</option>
                                                                <option value="4" >Yearly</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Billing Date</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="date" value="" id="date">
                                                            <option value="">--SELECT--</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Description </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="${stateBean.stateDescription}" maxlength="64"/></td>

                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${stateBean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true" />Previous Working Day</td>
                                                        </c:if>
                                                        <c:if test="${stateBean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" />Previous Working Day</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${stateBean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" checked="true"  />At Holiday</td>
                                                        </c:if>
                                                        <c:if test="${stateBean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" />At Holiday</td>
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${stateBean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" checked="true" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${stateBean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" />Next Working Day</td>    
                                                        </c:if>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="status" style="width: 163px">
                                                            <option value="" selected>------------SELECT------------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${stateBean.stateStatus==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${stateBean.stateStatus!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td > 
                                                        <input type="submit" value="update" name="Update" class="defbutton"/>
                                                        <input onclick="backToLoadUpdate('${stateBean.stateCycleCode}')" type="reset" value="Reset" class="defbutton"/>
                                                        <input onclick="backToLoad()" type="button" value="Cancel" class="defbutton"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>    

                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewHotlistReasonMgtServlet">
                                        <table border="0" cellpadding="0" cellspacing="10">
                                            <tr>
                                                <td>Billing Cycle Code</td>
                                                <td>:</td>
                                                <td>${billBean.stateCycleCode}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Billing Option</td>
                                                <td>:</td>
                                                <c:if test="${billBean.stateOption == '1'}">
                                                    <td>Daily</td>
                                                </c:if>
                                                <c:if test="${billBean.stateOption == '2'}">
                                                    <td>Weekly</td>
                                                </c:if>
                                                <c:if test="${billBean.stateOption == '3'}">
                                                    <td>Monthly</td>
                                                </c:if>
                                                <c:if test="${billBean.stateOption == '4'}">
                                                    <td>Yearly</td>
                                                </c:if>
                                                <c:if test="${billBean.stateOption == null or billBean.stateOption == ''}">
                                                    <td>--</td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <td>Billing Date</td>
                                                <td>:</td>
                                                <td>${billBean.stateDate}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Description</td>
                                                <td>:</td>
                                                <td>${billBean.stateDescription}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Holiday Action</td>
                                                <td>:</td>
                                                <td>${billBean.holidayAction}</td>                                                                     
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td>:</td>
                                                <td>${billBean.stateStatusDes}</td>                                                                     
                                            </tr> 
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td > 
                                                    <input onclick="backToLoad()" type="button" value="Back" style="width: 100px"/>
                                                </td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>


                                <!-- show table data -->
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview">
                                        <thead class="gradeB">
                                            <tr >
                                                <th>Cycle Code</th>
                                                <th>Option</th>
                                                <th>Billing Date</th>
                                                <th>Description</th>
                                                <th>Holiday Action</th>
                                                <th>Status</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${billingList}" var="bList">
                                                <tr>
                                                    <td>${bList.stateCycleCode}</td>
                                                    <c:if test="${bList.stateOption == '1'}">
                                                        <td>Daily</td>
                                                    </c:if>
                                                    <c:if test="${bList.stateOption == '2'}">
                                                        <td>Weekly</td>
                                                    </c:if>
                                                    <c:if test="${bList.stateOption == '3'}">
                                                        <td>Monthly</td>
                                                    </c:if>
                                                    <c:if test="${bList.stateOption == '4'}">
                                                        <td>Yearly</td>
                                                    </c:if>
                                                    <c:if test="${bList.stateOption == null or bList.stateOption == ''}">
                                                        <td>--</td>
                                                    </c:if>
                                                        
                                                    <c:if test="${bList.stateDate != null}">
                                                        <td>${bList.stateDate}</td>
                                                    </c:if>
                                                    <c:if test="${bList.stateDate == null or bList.stateDate == ''}">
                                                        <td>--</td>
                                                    </c:if>    
                                                        
                                                    
                                                    <td>${bList.stateDescription}</td>
                                                    
                                                    <c:if test="${bList.holidayAction == '0'}">
                                                        <td>Previous Working Day</td>
                                                    </c:if>
                                                    <c:if test="${bList.holidayAction == '1'}">
                                                        <td>At Holiday</td>
                                                    </c:if>
                                                    <c:if test="${bList.holidayAction == '2'}">
                                                        <td>Next Working Day</td>
                                                    </c:if>
                                                    <c:if test="${bList.holidayAction == null or bList.holidayAction == '' }">
                                                        <td>--</td>
                                                    </c:if>


                                                    <td>${bList.stateStatusDes}</td>

                                                    <td><a href='${pageContext.request.contextPath}/ViewMerchantStatementCycleServlet?id=<c:out value="${bList.stateCycleCode}"></c:out>'>View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/LoadUpdateMerchantStatementCycleServlet?id=<c:out value="${bList.stateCycleCode}"></c:out>'>Update</a></td>
                                                    <td><a  href='#' onclick="deleteBillingCycle('${bList.stateCycleCode}')">Delete</a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </form>
                                <!--   ------------------------- end developer area  --------------------------------  -->
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
