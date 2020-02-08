<%-- 
    Document   : eodprocessmgthome
    Created on : Oct 12, 2012, 3:04:38 PM
    Author     : nisansala
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

        <script  type="text/javascript" > 
            function invokeReset(ele){
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;                
                    }
                }
                
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'radio':
                            tags[i].checked = false;
                            break;                
                    }
                }
                
                tags = ele.getElementsByTagName('label');
                for(i = 0; i < tags.length; i++) {                    
                    tags[i].innerText = '';                    
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
            }
            
            function invokeAdd()
            {
                document.addform.action="${pageContext.request.contextPath}/AddEODProcessMgtServlet";
                document.addform.submit();

            }
            
            function invokeEnable(value){                              
                
                if(value == '1'){
                    $("#cft").attr("disabled",false);
                    $("#cf").attr("readonly",false);
                    $("#prev").attr("disabled",false)
                }
                else if(value == '0'){
                    $("#cft").attr("disabled",true);
                    $("#cf").attr("readonly",true);
                    $("#prev").attr("disabled",true)
                    
                    $("#cft").val('');
                    $("#cf").val('');
                    $("#prev").attr('checked', false);
                }
            }
            
            function invokeUpdate()
            {
                document.updateform.action="${pageContext.request.contextPath}/UpdateEODProcessMgtServlet";
                document.updateform.submit();

            } 
            
            function invokeBack(){
                
                window.location="${pageContext.request.contextPath}/LoadEODProcessMgtServlet";
                
            }
            
            //*************************************************************************************
            
             
            function invokeResetinUpdate(id)
            {
                //                document.updateform.action="${pageContext.request.contextPath}/LoadUpdateEODProcessMgtServlet?id="+id;
                //                document.updateform.submit();
                
                window.location="${pageContext.request.contextPath}/LoadUpdateEODProcessMgtServlet?id="+id;

            }
            
            
            
            function invokeDelete(value){
             
                answer = confirm("Are you sure you want to delete process '"+value+"' ?")
                   
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteEODProcessMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadEODProcessMgtServlet";
                }

            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.EOD_PROCESS%>'                                  
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
    <body onload="invokeEnable(${EODbean.frequencyType})">

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
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


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" name="addform" >
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>                                              

                                                <tr>
                                                    <td style="width: 150px">Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input type="text" name="description" value="${EODbean.description}" maxlength="64" style="width: 165px"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Critical Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="criticalstatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.criticalStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.criticalStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.criticalStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.criticalStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Rollback Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="rollbackstatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.rollbackStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.rollbackStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.rollbackStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.rollbackStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Schedule Date</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input  name="scheduledate" maxlength="16" readonly value="${EODbean.scheduledDate}" key="scheduledate" id="scheduledate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#scheduledate" ).datepicker({
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
                                                    <td>Schedule Time</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="scheduletime" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.scheduledTime == '1'}">
                                                                <option value="1" selected="">1</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '1'}">
                                                                <option value="1">1</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '2'}">
                                                                <option value="2" selected="">2</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '2'}">
                                                                <option value="2">2</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '3'}">
                                                                <option value="3" selected="">3</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '3'}">
                                                                <option value="3">3</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '4'}">
                                                                <option value="4" selected="">4</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '4'}">
                                                                <option value="4">4</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '5'}">
                                                                <option value="5" selected="">5</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '5'}">
                                                                <option value="5">5</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '6'}">
                                                                <option value="6" selected="">6</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '6'}">
                                                                <option value="6">6</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '7'}">
                                                                <option value="7" selected="">7</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '7'}">
                                                                <option value="7">7</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '8'}">
                                                                <option value="8" selected="">8</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '8'}">
                                                                <option value="8">8</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '9'}">
                                                                <option value="9" selected="">9</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '9'}">
                                                                <option value="9">9</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '10'}">
                                                                <option value="10" selected="">10</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '10'}">
                                                                <option value="10">10</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '11'}">
                                                                <option value="11" selected="">11</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '11'}">
                                                                <option value="11">11</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '12'}">
                                                                <option value="12" selected="">12</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '12'}">
                                                                <option value="12">12</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '13'}">
                                                                <option value="13" selected="">13</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '13'}">
                                                                <option value="13">13</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '14'}">
                                                                <option value="14" selected="">14</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '14'}">
                                                                <option value="14">14</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '15'}">
                                                                <option value="15" selected="">15</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '15'}">
                                                                <option value="15">15</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '16'}">
                                                                <option value="16" selected="">16</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '16'}">
                                                                <option value="16">16</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '17'}">
                                                                <option value="17" selected="">17</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '17'}">
                                                                <option value="17">17</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '18'}">
                                                                <option value="18" selected="">18</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '18'}">
                                                                <option value="18">18</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '19'}">
                                                                <option value="19" selected="">19</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '19'}">
                                                                <option value="19">19</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '20'}">
                                                                <option value="20" selected="">20</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '20'}">
                                                                <option value="20">20</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '21'}">
                                                                <option value="21" selected="">21</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '21'}">
                                                                <option value="21">21</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '22'}">
                                                                <option value="22" selected="">22</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '22'}">
                                                                <option value="22">22</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '23'}">
                                                                <option value="23" selected="">23</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '23'}">
                                                                <option value="23">23</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '24'}">
                                                                <option value="24" selected="">24</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '24'}">
                                                                <option value="24">24</option>
                                                            </c:if>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Frequency Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="frequencytype" style="width: 165px" id="ft" onchange="invokeEnable(ft.value)">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.frequencyType == '1'}">
                                                                <option value="1" selected="">Continuous</option>
                                                                <option value="0">Fixed</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.frequencyType == '0'}">
                                                                <option value="1" >Continuous</option>
                                                                <option value="0" selected="">Fixed</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.frequencyType == null}">
                                                                <option value="1" >Continuous</option>
                                                                <option value="0" >Fixed</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.frequencyType == ''}">
                                                                <option value="1" >Continuous</option>
                                                                <option value="0" >Fixed</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Continues Frequency Type</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="continuesfrequencytype" style="width: 165px" disabled="" id="cft">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.continueosFreqType == '2'}">
                                                                <option value="2" selected="">Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == '1'}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" selected="">Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == '0'}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0" selected="">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == null}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == ''}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Continues Frequency</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="continuesfrequency" value="${EODbean.continueosFrequency}" maxlength="3" style="width: 165px" readonly="" id="cf"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${EODbean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true" id="prev" />Previous Working Day</td>

                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" id="prev"/>Previous Working Day</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${EODbean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" checked="true"  />At Holiday</td>

                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" />At Holiday</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${EODbean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" checked="true" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${EODbean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" />Next Working Day</td>    
                                                        </c:if>



                                                </tr>
                                                <tr>
                                                    <td>Multiple Cycle Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="multiplecyclestatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.multiCycleStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.multiCycleStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.multiCycleStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.multiCycleStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Process Category</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>                                                       
                                                        <select name="processcategory" style="width: 165px;">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:forEach var="category" items="${processCategory}">
                                                                <c:if test="${EODbean.processCatId == category.key}">
                                                                    <option value="${category.key}" selected>${category.value}</option>
                                                                </c:if>
                                                                <c:if test="${EODbean.processCatId != category.key}">
                                                                    <option value="${category.key}" >${category.value}</option>
                                                                </c:if>

                                                            </c:forEach>                                                      

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Dependency Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="dependencystatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.dependencyStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.dependencyStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.dependencyStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.dependencyStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Running on Main</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="main" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.onMain == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onMain == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onMain == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onMain == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Running on Sub</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="sub" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.onSub == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onSub == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onSub == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onSub == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Process Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="processtype" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.processType == '2'}">
                                                                <option value="2" selected="">Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == '1'}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" selected="">Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == '0'}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0" selected="">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == null}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == ''}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="status"  style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${EODbean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${EODbean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Letter Generation Status :</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="letterGenStatus"  style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.letterGenStatus=='YES'}">                                                                
                                                                <option value="YES" selected>YES</option>
                                                                <option value="NO" >NO</option>                                                           
                                                            </c:if>
                                                            <c:if test="${EODbean.letterGenStatus=='NO'}">
                                                                <option value="YES" >YES</option>                                                                
                                                                <option value="NO" selected>NO</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.letterGenStatus!='YES' and EODbean.letterGenStatus!='NO' }">
                                                                <option value="YES" >YES</option>                                                                
                                                                <option value="NO" >NO</option>
                                                            </c:if> 
                                                        </select>
                                                    </td>
                                                </tr>                                                
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="button" class="defbutton" name="add" value="Add" onclick="invokeAdd()" /> 
                                                        <input type="button" class="defbutton" name="reset" value="Reset" onclick="invokeBack()"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" name="updateform" >
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>  
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Process Id</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input type="text" name="processid" value="${EODbean.processId}" maxlength="3" style="width: 165px" readonly="" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 150px">Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input type="text" name="description" value="${EODbean.description}" maxlength="64" style="width: 165px"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Critical Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="criticalstatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.criticalStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.criticalStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.criticalStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.criticalStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Rollback Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="rollbackstatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.rollbackStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.rollbackStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.rollbackStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.rollbackStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Schedule Date</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <input  name="scheduledate" maxlength="16" readonly value="${EODbean.scheduledDate}" key="scheduledate" id="scheduledate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#scheduledate" ).datepicker({
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
                                                    <td>Schedule Time</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="scheduletime" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.scheduledTime == '1'}">
                                                                <option value="1" selected="">1</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '1'}">
                                                                <option value="1">1</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '2'}">
                                                                <option value="2" selected="">2</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '2'}">
                                                                <option value="2">2</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '3'}">
                                                                <option value="3" selected="">3</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '3'}">
                                                                <option value="3">3</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '4'}">
                                                                <option value="4" selected="">4</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '4'}">
                                                                <option value="4">4</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '5'}">
                                                                <option value="5" selected="">5</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '5'}">
                                                                <option value="5">5</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '6'}">
                                                                <option value="6" selected="">6</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '6'}">
                                                                <option value="6">6</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '7'}">
                                                                <option value="7" selected="">7</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '7'}">
                                                                <option value="7">7</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '8'}">
                                                                <option value="8" selected="">8</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '8'}">
                                                                <option value="8">8</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '9'}">
                                                                <option value="9" selected="">9</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '9'}">
                                                                <option value="9">9</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '10'}">
                                                                <option value="10" selected="">10</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '10'}">
                                                                <option value="10">10</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '11'}">
                                                                <option value="11" selected="">11</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '11'}">
                                                                <option value="11">11</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '12'}">
                                                                <option value="12" selected="">12</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '12'}">
                                                                <option value="12">12</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '13'}">
                                                                <option value="13" selected="">13</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '13'}">
                                                                <option value="13">13</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '14'}">
                                                                <option value="14" selected="">14</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '14'}">
                                                                <option value="14">14</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '15'}">
                                                                <option value="15" selected="">15</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '15'}">
                                                                <option value="15">15</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '16'}">
                                                                <option value="16" selected="">16</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '16'}">
                                                                <option value="16">16</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '17'}">
                                                                <option value="17" selected="">17</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '17'}">
                                                                <option value="17">17</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '18'}">
                                                                <option value="18" selected="">18</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '18'}">
                                                                <option value="18">18</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '19'}">
                                                                <option value="19" selected="">19</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '19'}">
                                                                <option value="19">19</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '20'}">
                                                                <option value="20" selected="">20</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '20'}">
                                                                <option value="20">20</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '21'}">
                                                                <option value="21" selected="">21</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '21'}">
                                                                <option value="21">21</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '22'}">
                                                                <option value="22" selected="">22</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '22'}">
                                                                <option value="22">22</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '23'}">
                                                                <option value="23" selected="">23</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '23'}">
                                                                <option value="23">23</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime == '24'}">
                                                                <option value="24" selected="">24</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.scheduledTime != '24'}">
                                                                <option value="24">24</option>
                                                            </c:if>

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Frequency Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="frequencytype" style="width: 165px" id="ft" onchange="invokeEnable(ft.value)">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.frequencyType == '1'}">
                                                                <option value="1" selected="">Continuous</option>
                                                                <option value="0">Fixed</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.frequencyType == '0'}">
                                                                <option value="1" >Continuous</option>
                                                                <option value="0" selected="">Fixed</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.frequencyType == null}">
                                                                <option value="1" >Continuous</option>
                                                                <option value="0" >Fixed</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.frequencyType == ''}">
                                                                <option value="1" >Continuous</option>
                                                                <option value="0" >Fixed</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Continues Frequency Type</td>
                                                    <td></td>
                                                    <td>
                                                        <select name="continuesfrequencytype" style="width: 165px" disabled="" id="cft">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.continueosFreqType == '2'}">
                                                                <option value="2" selected="">Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == '1'}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" selected="">Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == '0'}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0" selected="">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == null}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.continueosFreqType == ''}">
                                                                <option value="2" >Days</option>
                                                                <option value="1" >Hours</option>
                                                                <option value="0">Minutes</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Continues Frequency</td>
                                                    <td></td>
                                                    <td>
                                                        <input type="text" name="continuesfrequency" value="${EODbean.continueosFrequency}" maxlength="3" style="width: 165px" readonly="" id="cf"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                        <c:if test="${EODbean.holidayAction == '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" checked="true" id="prev" />Previous Working Day</td>

                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction != '0'}">
                                                        <td><input type="radio" name="holidayAct" value="0" id="prev"/>Previous Working Day</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${EODbean.holidayAction == '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" checked="true"  />At Holiday</td>

                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction != '1'}">
                                                        <td><input type="radio" name="holidayAct" value="1" />At Holiday</td>

                                                    </c:if>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <c:if test="${EODbean.holidayAction == '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" checked="true" />Next Working Day</td>    
                                                        </c:if>
                                                        <c:if test="${EODbean.holidayAction != '2'}">
                                                        <td><input type="radio" name="holidayAct" value="2" />Next Working Day</td>    
                                                        </c:if>



                                                </tr>
                                                <tr>
                                                    <td>Multiple Cycle Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="multiplecyclestatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.multiCycleStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.multiCycleStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.multiCycleStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.multiCycleStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Process Category</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>                                                       
                                                        <select name="processcategory" style="width: 165px;">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:forEach var="category" items="${processCategory}">
                                                                <c:if test="${EODbean.processCatId == category.key}">
                                                                    <option value="${category.key}" selected>${category.value}</option>
                                                                </c:if>
                                                                <c:if test="${EODbean.processCatId != category.key}">
                                                                    <option value="${category.key}" >${category.value}</option>
                                                                </c:if>

                                                            </c:forEach>                                                      

                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Dependency Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="dependencystatus" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.dependencyStatus == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.dependencyStatus == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.dependencyStatus == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.dependencyStatus == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Running on Main</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="main" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.onMain == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onMain == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onMain == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onMain == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Running on Sub</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="sub" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.onSub == '1'}">
                                                                <option value="1" selected="">Yes</option>
                                                                <option value="0">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onSub == '0'}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" selected="">No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onSub == null}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.onSub == ''}">
                                                                <option value="1" >Yes</option>
                                                                <option value="0" >No</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Process Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select name="processtype" style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.processType == '2'}">
                                                                <option value="2" selected="">Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == '1'}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" selected="">Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == '0'}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0" selected="">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == null}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.processType == ''}">
                                                                <option value="2" >Other</option>
                                                                <option value="1" >Sub</option>
                                                                <option value="0">Main</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="status"  style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${EODbean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${EODbean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Letter Generation Status :</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="letterGenStatus"  style="width: 165px">
                                                            <option value="">----------SELECT----------</option>
                                                            <c:if test="${EODbean.letterGenStatus=='YES'}">                                                                
                                                                <option value="YES" selected>YES</option>
                                                                <option value="NO" >NO</option>                                                           
                                                            </c:if>
                                                            <c:if test="${EODbean.letterGenStatus=='NO'}">
                                                                <option value="YES" >YES</option>                                                                
                                                                <option value="NO" selected>NO</option>
                                                            </c:if>
                                                            <c:if test="${EODbean.letterGenStatus!='YES' and EODbean.letterGenStatus!='NO' }">
                                                                <option value="YES" >YES</option>                                                                
                                                                <option value="NO" >NO</option>
                                                            </c:if>                                                                
                                                        </select>
                                                    </td>
                                                </tr>                                                
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="button" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/>
                                                        <input type="reset" value="Reset" name="reset" class="defbutton" onclick="invokeResetinUpdate('${EODbean.processId}')"/>
                                                        <input type="button" class="defbutton" name="cancel" value="Cancel" onclick="invokeBack()"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>

                                </c:if>                                

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewform">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing ="10" cellpadding ="0">
                                            <tbody>
                                                <tr>
                                                    <td>Process Id </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${EODbean.processId}</td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${EODbean.description}</td>
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Critical Status </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.criticalStatus=='0'}">
                                                        <td>No</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.criticalStatus=='1'}">
                                                        <td>Yes</td>
                                                    </c:if>
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Rollback Status </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.rollbackStatus=='0'}">
                                                        <td>No</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.rollbackStatus=='1'}">
                                                        <td>Yes</td>
                                                    </c:if>
                                                    <td></td>                                                    
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Scheduled Date </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${EODbean.scheduledDate}</td>
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Scheduled Time </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${EODbean.scheduledTime}</td>
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Frequency Type </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.frequencyType=='0'}">
                                                        <td>Fixed</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.frequencyType=='1'}">
                                                        <td>Continuous</td>
                                                    </c:if>

                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td> Continuous Frequency Type</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.continueosFreqType=='0'}">
                                                        <td>Minutes</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.continueosFreqType=='1'}">
                                                        <td>Hours</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.continueosFreqType=='2'}">
                                                        <td>Days</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.continueosFreqType==null}">
                                                        <td>--</td>
                                                    </c:if>


                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Continuous Frequency </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.continueosFrequency != null}">
                                                        <td>${EODbean.continueosFrequency}</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.continueosFrequency == null}">
                                                        <td>--</td>
                                                    </c:if>

                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Holiday Action</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.holidayAction =='0'}">
                                                        <td>Previous Working day</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction =='1'}">
                                                        <td>At Holiday</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction =='2'}">
                                                        <td>Next Working Day</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.holidayAction == null}">
                                                        <td>--</td>
                                                    </c:if>

                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Multiple Cycle Status </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.multiCycleStatus=='0'}">
                                                        <td>No</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.multiCycleStatus=='1'}">
                                                        <td>Yes</td>
                                                    </c:if>                                                    
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Process Category </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${EODbean.processCatDes}</td>
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Dependency Status </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.dependencyStatus=='0'}">
                                                        <td>No</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.dependencyStatus=='1'}">
                                                        <td>Yes</td>
                                                    </c:if> 
                                                    <td></td>
                                                </tr>  
                                                <tr>
                                                    <td>Running on main </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.onMain=='0'}">
                                                        <td>No</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.onMain=='1'}">
                                                        <td>Yes</td>
                                                    </c:if>                                                     
                                                    <td></td>
                                                </tr> 
                                                <tr>
                                                    <td>Running on sub </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.onSub=='0'}">
                                                        <td>No</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.onSub=='1'}">
                                                        <td>Yes</td>
                                                    </c:if>                                                     
                                                    <td></td>
                                                </tr> 
                                                <tr>
                                                    <td>Process Type </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <c:if test="${EODbean.processType=='0'}">
                                                        <td>Main</td>
                                                    </c:if>
                                                    <c:if test="${EODbean.processType=='1'}">
                                                        <td>Sub</td>
                                                    </c:if> 
                                                    <c:if test="${EODbean.processType=='2'}">
                                                        <td>Other</td>
                                                    </c:if>                                                     
                                                    <td></td>
                                                </tr> 
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td>${EODbean.status}</td>
                                                    <td></td>
                                                </tr> 


                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" name="back" class="defbutton" onclick="invokeBack()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>


                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Process ID</th>
                                            <th>Description</th>
                                            <th>Process Type</th>
                                            <th>Scheduled Date</th>
                                            <th>Scheduled Time</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="process" items="${eodBeanList}">
                                            <tr>
                                                <td>${process.processId}</td>
                                                <td>${process.description}</td>
                                                <td>${process.processType}</td>
                                                <td>${process.scheduledDate}</td>
                                                <td>${process.scheduledTime}</td>                                                


                                                <td  ><a href='${pageContext.request.contextPath}/ViewEODProcessMgtServlet?id=<c:out value="${process.processId}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateEODProcessMgtServlet?id=<c:out value="${process.processId}"></c:out>'>Update</a></td> 
                                                <td ><a  href='#' onclick="invokeDelete('${process.processId}')">Delete</a></td>
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
