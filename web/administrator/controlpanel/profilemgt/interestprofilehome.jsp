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

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>


        <script  type="text/javascript" charset="utf-8">
            
            function customPeriod(){
        
                document.getElementById("periodCustom").style.visibility = 'visible';
            }
    
            function customPeriodHide(){
        
                document.getElementById("periodCustom").style.visibility = 'hidden';
            }
    
            
            function selectAll(selectBox) {
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }       
                invokeAdd();
            }
            
            
            function invokeAdd()
            {
                $('#errorMsgcon').text("");
                $.ajax({
                    url: "${pageContext.request.contextPath}/AddInterestProfileServlet",
                    type: "POST",
                    data: $("#addInterestProfile").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        if(ar[0] == 'success'){
                            $('#succMes').val(ar[1]);
                            $('#succForm').submit();
                        }else{
                            $("#errorMsgcon").text(data);
                        }
                        
                    }
                });
                //                document.addInterestProfile.action="${pageContext.request.contextPath}/AddInterestProfileServlet";
                //                document.addInterestProfile.submit();

            }
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadInterestProfileServlet";

            }
            
            function selectAllForUpdate(selectBox1,selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }     
                invokeUpdate();
            }
            
            function invokeUpdate()
            {
                $('#errorMsgcon').text("");
                $.ajax({
                    url: "${pageContext.request.contextPath}/UpdateConfiremedInterestprofileMgtServlet",
                    type: "POST",
                    data: $("#updateInterestProfile").serialize(),
                    success : function(data){
                        var ar=data.split(",", 2);
                        if(ar[0] == 'success'){
                            $('#succMes').val(ar[1]);
                            $('#succForm').submit();
                        }else{
                            $("#errorMsgcon").text(data);
                        }
                        
                    }
                });

                //                document.updateInterestProfile.action="${pageContext.request.contextPath}/UpdateConfiremedInterestprofileMgtServlet";
                //                document.updateInterestProfile.submit();

            }
            function invokeBackAdd()
            {
              
                document.addInterestProfile.action="${pageContext.request.contextPath}/ViewInterestProfileTableServlet";
                document.addInterestProfile.submit();

            }
            
            
            function invokeBackUpdate()
            {
              
                document.updateInterestProfile.action="${pageContext.request.contextPath}/ViewInterestProfileTableServlet";
                document.updateInterestProfile.submit();

            }
            
            
            
            function ConfirmDelete(taskCode)
            {
                answer = confirm("Do you really want to delete this Task ?")
                if (answer !=0)
                {
                    document.addInterestProfile.action="${pageContext.request.contextPath}/DeleteTaskServlet?taskCode="+taskCode;
                }
                else
                {
                    document.addInterestProfile.action="${pageContext.request.contextPath}/LoadInterestProfileServlet";
                }
                document.addInterestProfile.submit();
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

                                <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->

                                <form name="sucMessage" id="succForm" action="${pageContext.request.contextPath}/ViewInterestProfileTableServlet">
                                    <input type="hidden" name="successMsg" id="succMes"></input>
                                </form>                       

                                <table>
                                    <tr>                                          
                                        <td colspan="3"><b><font color="#FF0000" id="errorMsgcon"><c:out value="${errorMsg}"></c:out></font></b>
                                            <b><font color="green" id="succMes"><c:out value="${successMsg}" ></c:out></font></b>
                                        </td>
                                    </tr>
                                </table>

                                <c:if test="${operationtype=='default'}">
                                    <form  action="" method="POST" name="addInterestProfile" id="addInterestProfile">



                                        <table>

                                            <tbody >
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>

                                                <tr>
                                                    <td>Interest Profile Code</td>
                                                    <td style="width: 50px;">:</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="profileCode" class="inputfield-mandatory" maxlength="6" value='${interestProfileBean.interestFrofileCode}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>:</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="description" class="inputfield-Description-mandatory" maxlength="64" value='${interestProfileBean.description}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Status</td>


                                                    <td>:</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${interestProfileBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${interestProfileBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                <tr style="height:12px;"></tr>
                                                </tr>

                                                <tr>
                                                    <td>Interest Rate</td>
                                                    <td>:</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="interestRate" class="inputfield-mandatory" maxlength="6"  value='${interestProfileBean.interestRate}'></td>
                                                    <td>(Eg: 5.0) %</td>
                                                </tr>

                                                <tr style="height:12px;"></tr>

                                                <tr>
                                                    <td>Interest Period</td>
                                                    <td>:</td>
                                                    <td>

                                                        <table>
                                                            <c:if test="${interestProfileBean.interestPeriodValue == 'daily'}">
                                                                <tr><td><input type="radio" name="period" value="daily" checked="true" onchange="customPeriodHide()"/> Daily<br /></td></tr>   
                                                                <tr><td><input type="radio" name="period" value="annual" onchange="customPeriodHide()" /> Annual (365)<br /></td></tr>
                                                                <tr><td><input type="radio" name="period" value="custom" onchange="customPeriod()" /> Custom Date<br /></td></tr>
                                                                <td><input type="text" name="periodCustom"  maxlength="3"  value='${interestProfileBean.customValue}' style="width: 5 px; visibility: hidden " id="periodCustom"></td>
                                                                </c:if>

                                                            <c:if test="${interestProfileBean.interestPeriodValue == 'annual'}">
                                                                <tr><td><input type="radio" name="period" value="daily"  onchange="customPeriodHide()"/> Daily<br /></td></tr>   
                                                                <tr><td><input type="radio" name="period" value="annual" checked="true" onchange="customPeriodHide()" /> Annual (365)<br /></td></tr>
                                                                <tr><td><input type="radio" name="period" value="custom" onchange="customPeriod()" /> Custom Date<br /></td></tr>
                                                                <td><input type="text" name="periodCustom"  maxlength="3"  value='${interestProfileBean.customValue}' style="width: 5 px; visibility: hidden " id="periodCustom"></td>
                                                                </c:if> 

                                                            <c:if test="${!((interestProfileBean.interestPeriodValue == 'daily') || (InterestBean.interestPeriodValue == 'annual'))}">
                                                                <tr><td><input type="radio" name="period" value="daily" onchange="customPeriodHide()"/> Daily<br /></td></tr>   
                                                                <tr><td><input type="radio" name="period" value="annual" onchange="customPeriodHide()" /> Annual (365)<br /></td></tr>
                                                                <tr><td><input type="radio" name="period" value="custom"  checked="true" onchange="customPeriod()" /> Custom Date<br /></td></tr>
                                                                <td><input type="text" name="periodCustom"  maxlength="3" value='${InterestBean.customValue}' style="width: 5 px; visibility: visible " id="periodCustom"/></td>
                                                                </c:if>      

                                                        </table>


                                                    </td>

                                                </tr>
                                                <tr style="height:12px;"></tr>


                                                <tr>
                                                    <td>Effect Date For New Accounts</td>
                                                    <td>:</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input  name="effectDateNewAcc" readonly maxlength="16" value="${interestProfileBean.effectiveDateForNewCustomer}" key="validTo" id="validFrom1"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#validFrom1" ).datepicker({
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
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>

                                                <tr>
                                                    <td>Effect Date For Exist Accounts</td>
                                                    <td>:</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input  name="effectDateExistingAcc" readonly maxlength="16" value="${interestProfileBean.effectiveDateForExistCustomer}" key="validTo" id="validFrom2"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#validFrom2" ).datepicker({
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
                                                    <td></td>
                                                </tr>




                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Starting From</td>
                                                    <td>:</td>
                                                    <td>
                                                        <!--                                                        <table>
                                                        
                                                                                                                    <tr><td><input type="radio" name="startFrom" value="Statement Date" /> Statement Date<br /></td></tr>
                                                                                                                    <tr><td><input type="radio" name="startFrom" value="Transaction Date" /> Transaction Date<br /></td></tr>                                                                                       
                                                                                                                    <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                                                                    <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                                                                    <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                        
                                                                                                                </table>-->

                                                        <table>
                                                            <c:if test="${interestProfileBean.interestCalStartFrom == 'Statement Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date" checked="true" /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date" /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>

                                                            <c:if test="${interestProfileBean.interestCalStartFrom == 'Transaction Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date" checked="true" /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>   

                                                            <c:if test="${interestProfileBean.interestCalStartFrom == 'Due Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" checked="true"/> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>   

                                                            <c:if test="${interestProfileBean.interestCalStartFrom == 'Nearest Month'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" checked="true" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>   

                                                            <c:if test="${interestProfileBean.interestCalStartFrom == 'Post Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month"  /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" checked="true" /> Post Date<br /></td></tr>
                                                                    </c:if>  
                                                                    <c:if test="${!((interestProfileBean.interestCalStartFrom == 'Statement Date') || (interestProfileBean.interestCalStartFrom == 'Transaction Date') || (interestProfileBean.interestCalStartFrom == 'Due Date') || (interestProfileBean.interestCalStartFrom == 'Nearest Month') || (interestProfileBean.interestCalStartFrom == 'Post Date'))}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month"  /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>      

                                                        </table>



                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>To</td>
                                                    <td >:</td>
                                                    <td>
                                                        <table>

                                                            <c:if test="${interestProfileBean.interestCalStartTo == 'Statement Date'}">
                                                                <tr><td><input type="radio" name="to" value="Statement Date" checked="true" /> Statement Date<br /></td></tr>   
                                                                <tr><td><input type="radio" name="to" value="Due Date" /> Due Date<br /></td></tr>    
                                                                    </c:if>

                                                            <c:if test="${interestProfileBean.interestCalStartTo == 'Due Date'}">
                                                                <tr><td><input type="radio" name="to" value="Statement Date"  /> Statement Date<br /></td></tr>   
                                                                <tr><td><input type="radio" name="to" value="Due Date" checked="true" /> Due Date<br /></td></tr>    
                                                                    </c:if>  
                                                                    <c:if test="${!((interestProfileBean.interestCalStartTo == 'Statement Date') || (interestProfileBean.interestCalStartTo == 'Due Date'))}">
                                                                <tr><td><input type="radio" name="to" value="Statement Date"  /> Statement Date<br /></td></tr>   
                                                                <tr><td><input type="radio" name="to" value="Due Date" /> Due Date<br /></td></tr>    
                                                                    </c:if>  

                                                        </table>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Charge Type</td>
                                                    <td >:</td>
                                                    <td>
                                                        <table>

                                                            <c:if test="${interestProfileBean.chargeType == 'Paid by DueDate'}">
                                                                <tr><td><input type="radio" name="chargeType" checked="true" value="Paid by DueDate" /> Paid by Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="chargeType" value="Paid Not by Due Date" /> Paid Not by Due Date<br /></td></tr>                                       
                                                                    </c:if>

                                                            <c:if test="${interestProfileBean.chargeType == 'Paid Not by Due Date'}">
                                                                <tr><td><input type="radio" name="chargeType"  value="Paid by DueDate" /> Paid by Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="chargeType" checked="true" value="Paid Not by Due Date" /> Paid Not by Due Date<br /></td></tr>                                                                                       
                                                                    </c:if>
                                                                    <c:if test="${!((interestProfileBean.chargeType == 'Paid by DueDate') || (interestProfileBean.chargeType == 'Paid Not by Due Date'))}">
                                                                <tr><td><input type="radio" name="chargeType" value="Paid by DueDate" /> Paid by Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="chargeType" value="Paid Not by Due Date" /> Paid Not by Due Date<br /></td></tr>                                       
                                                                    </c:if>    
                                                        </table>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td><b>Transactions</b></td>

                                                <tr style="height:20 px;"></tr>
                                            <td>
                                                <!-- start add remove -->
                                            <tr>
                                                <td>    
                                                    <select name="notassignsectionlist" style="width: 175px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="transaction" items="${transactionDetails}">
                                                            <option value="${transaction.transactionCode}" >${transaction.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td align="center" >
                                                    <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                    <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                    <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                    <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                </td>
                                                <td>          
                                                    <select name="assignsectionlist" style="width: 175px" id=out multiple="multiple"   size=10> </select>
                                                </td>
                                            </tr>
                                            <!-- End Add remove -->
                                            </td>
                                            <td></td>
                                            </tr>

                                            </tbody>
                                        </table>

                                        <br></br>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" name="add" value="Add" onclick="selectAll(assignsectionlist)" style="width: 100px;" />
                                                        <input type="button" name="reset" value="Reset" onclick="invokeReset()" style="width: 100px;"/>
                                                        <input type="submit" name="back" value="Back" onclick="invokeBackAdd()" style="width: 100px;" />
                                                    </td>
                                                    <td></td>

                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <!--/////////////////////////////////////////////Start Update View  ///////////////////////////////////////////////////////////-->          
                                <c:if test="${operationtype=='update'}">
                                    <form  action="" method="POST" name="updateInterestProfile" id="updateInterestProfile" >
                                        <!--                                        <table>
                                                                                    <tr>
                                                                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>-->


                                        <table>

                                            <tbody >
                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>

                                                <tr>
                                                    <td>Interest Profile Code</td>
                                                    <td style="width: 50px;">:</td>
                                                    <td><input type="text" name="profileCode" class="inputfield-mandatory" maxlength="12" value='${InterestBean.interestFrofileCode}' readonly="true"></td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>:</td>
                                                    <td><input type="text" name="description" class="inputfield-Description-mandatory" maxlength="64" value='${InterestBean.description}'></td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Status</td>


                                                    <td>:</td>
                                                    <td>
                                                        <select  name="selectstatuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${InterestBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${InterestBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                <tr style="height:12px;"></tr>
                                                </tr>

                                                <tr>
                                                    <td>Interest Rate</td>
                                                    <td>:</td>
                                                    <td><input type="text" name="interestRate" class="inputfield-mandatory" maxlength="6"  value='${InterestBean.interestRate}'></td>
                                                    <td>%</td>
                                                </tr>

                                                <tr style="height:12px;"></tr>

                                                <tr>
                                                    <td>Interest Period</td>
                                                    <td>:</td>
                                                    <td>
                                                        <table>
                                                            <c:if test="${InterestBean.interestPeriodValue == '1'}">
                                                                <tr><td><input type="radio" name="period" value="daily" checked="true" onchange="customPeriodHide()"/> Daily<br /></td></tr>   
                                                                <tr><td><input type="radio" name="period" value="annual" onchange="customPeriodHide()" /> Annual (365)<br /></td></tr>
                                                                <tr><td><input type="radio" name="period" value="custom" onchange="customPeriod()" /> Custom Date<br /></td></tr>
                                                                <td><input type="text" name="periodCustom"  maxlength="3"  value='${interestProfileBean.customValue}' style="width: 5 px; visibility: hidden " id="periodCustom"></td>
                                                                </c:if>

                                                            <c:if test="${InterestBean.interestPeriodValue == '365'}">
                                                                <tr><td><input type="radio" name="period" value="daily"  onchange="customPeriodHide()"/> Daily<br /></td></tr>   
                                                                <tr><td><input type="radio" name="period" value="annual" checked="true" onchange="customPeriodHide()" /> Annual (365)<br /></td></tr>
                                                                <tr><td><input type="radio" name="period" value="custom" onchange="customPeriod()" /> Custom Date<br /></td></tr>
                                                                <td><input type="text" name="periodCustom"  maxlength="3"  value='${interestProfileBean.customValue}' style="width: 5 px; visibility: hidden " id="periodCustom"></td>
                                                                </c:if> 

                                                            <c:if test="${!((InterestBean.interestPeriodValue == '1') || (InterestBean.interestPeriodValue == '365'))}">
                                                                <tr><td><input type="radio" name="period" value="daily" onchange="customPeriodHide()"/> Daily<br /></td></tr>   
                                                                <tr><td><input type="radio" name="period" value="annual" onchange="customPeriodHide()" /> Annual (365)<br /></td></tr>
                                                                <tr><td><input type="radio" name="period" value="custom"  checked="true" onchange="customPeriod()" /> Custom Date<br /></td></tr>
                                                                <td><input type="text" name="periodCustom"  maxlength="3" value='${InterestBean.customValue}' style="width: 5 px; visibility: visible " id="periodCustom"/></td>
                                                                </c:if>      

                                                        </table>
                                                    </td>

                                                </tr>
                                                <tr style="height:12px;"></tr>


                                                <tr>
                                                    <td>Effect Date For New Accounts</td>
                                                    <td>:</td>
                                                    <td>
                                                        <input  name="effectDateNewAcc" readonly maxlength="16" value="${InterestBean.effectiveDateForNewCustomer}" key="validTo" id="validFrom1"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#validFrom1" ).datepicker({
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
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>

                                                <tr>
                                                    <td>Effect Date For Exist Accounts</td>
                                                    <td>:</td>
                                                    <td>
                                                        <input  name="effectDateExistingAcc" readonly maxlength="16" value="${InterestBean.effectiveDateForExistCustomer}" key="validTo" id="validFrom2"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#validFrom2" ).datepicker({
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
                                                    <td></td>
                                                </tr>




                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Starting From</td>
                                                    <td>:</td>
                                                    <td>
                                                        <table>
                                                            <c:if test="${InterestBean.interestCalStartFrom == 'Statement Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date" checked="true" /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date" /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>

                                                            <c:if test="${InterestBean.interestCalStartFrom == 'Transaction Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date" checked="true" /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>   

                                                            <c:if test="${InterestBean.interestCalStartFrom == 'Due Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" checked="true"/> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>   

                                                            <c:if test="${InterestBean.interestCalStartFrom == 'Nearest Month'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month" checked="true" /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" /> Post Date<br /></td></tr>
                                                                    </c:if>   

                                                            <c:if test="${InterestBean.interestCalStartFrom == 'Post Date'}">
                                                                <tr><td><input type="radio" name="startFrom" value="Statement Date"  /> Statement Date<br /></td></tr> 
                                                                <tr><td><input type="radio" name="startFrom" value="Transaction Date"  /> Transaction Date<br /></td></tr>                                                                                       
                                                                <tr><td><input type="radio" name="startFrom" value="Due Date" /> Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Nearest Month"  /> Nearest Month<br /></td></tr>
                                                                <tr><td><input type="radio" name="startFrom" value="Post Date" checked="true" /> Post Date<br /></td></tr>
                                                                    </c:if>       

                                                        </table>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>To</td>
                                                    <td >:</td>
                                                    <td>
                                                        <table>
                                                            <c:if test="${InterestBean.interestCalStartTo == 'Statement Date'}">
                                                                <tr><td><input type="radio" name="to" value="Statement Date" checked="true" /> Statement Date<br /></td></tr>   
                                                                <tr><td><input type="radio" name="to" value="Due Date" /> Due Date<br /></td></tr>    
                                                                    </c:if>

                                                            <c:if test="${InterestBean.interestCalStartTo == 'Due Date'}">
                                                                <tr><td><input type="radio" name="to" value="Statement Date"  /> Statement Date<br /></td></tr>   
                                                                <tr><td><input type="radio" name="to" value="Due Date" checked="true" /> Due Date<br /></td></tr>    
                                                                    </c:if>

                                                        </table>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td>Charge Type</td>
                                                    <td >:</td>
                                                    <td>
                                                        <table>
                                                            <c:if test="${InterestBean.chargeType == 'Paid by DueDate'}">
                                                                <tr><td><input type="radio" name="chargeType" checked="true" value="Paid by DueDate" /> Paid by Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="chargeType" value="Paid Not by Due Date" /> Paid Not by Due Date<br /></td></tr>                                       
                                                                    </c:if>

                                                            <c:if test="${InterestBean.chargeType == 'Paid Not by Due Date'}">
                                                                <tr><td><input type="radio" name="chargeType"  value="Paid by DueDate" /> Paid by Due Date<br /></td></tr>
                                                                <tr><td><input type="radio" name="chargeType" checked="true" value="Paid Not by Due Date" /> Paid Not by Due Date<br /></td></tr>                                                                                       
                                                                    </c:if>

                                                        </table>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr style="height:12px;"></tr>
                                                <tr>
                                                    <td><b>Transactions</b></td>

                                                <tr style="height:20 px;"></tr>
                                            <td>
                                                <!-- start add remove -->
                                            <tr>
                                                <td>    
                                                    <select name="notassignsectionlist" style="width: 175px"  id=in multiple="multiple"  size=10>
                                                        <c:forEach  var="notAss" items="${notAssignedTransactionTypes}">
                                                            <option value="${notAss.transactionCode}" >${notAss.description}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td align="center" >
                                                    <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                    <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                    <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                    <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                </td>
                                                <td>          
                                                    <select name="assignsectionlist" style="width: 175px" id=out multiple="multiple"   size=10>
                                                        <c:forEach var="assig" items="${assignedTransactions}">
                                                            <option value="${assig.transactionCode}" >${assig.description}</option>
                                                        </c:forEach> 
                                                    </select>
                                                </td>
                                            </tr>
                                            <!-- End Add remove -->
                                            </td>
                                            <td></td>
                                            </tr>



                                            </tbody>
                                        </table>

                                        <br></br>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" name="add" value="Update" onclick="selectAllForUpdate(notassignsectionlist,assignsectionlist)" style="width: 100px;" />
                                                        <input type="button" name="reset" value="Reset" onclick="invokeReset()" style="width: 100px;"/>
                                                        <input type="submit" name="back" value="Back" onclick="invokeBackUpdate()" style="width: 100px;" />
                                                    </td>

                                                    <td></td>

                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>   


                                <!--/////////////////////////////////////////////End Update View  ///////////////////////////////////////////////////////////-->



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
