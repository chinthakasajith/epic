<%-- 
    Document   : holidaymanagementhome
    Created on : Feb 2, 2012, 8:49:21 AM
    Author     : upul
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@page  import="com.epic.cms.admin.controlpanel.systemconfigmgt.bean.HolidayManagementBean" %>
<%@ page  language="java" import="java.util.*,java.text.*"%>






<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">







        <title>EPIC_CMS_HOME</title>

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>



        <!--        include content.jsp for all js and css inclusion-->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles/styles.css" />


        <script language="javascript">
               
            function invokeAdd(){
                
           
                document.forms.addholidayform.action="${pageContext.request.contextPath}/AddSingleHolidayFromFormServlet";           
                document.forms.addholidayform.submit();
                
            }
            function invokeUpdate(){
               
                document.addholidayform.action="${pageContext.request.contextPath}/UpdateSingleHolidayFromFormServlet";           
              
                document.addholidayform.submit();
                
                
            }
            function invokeDelete(){
                
                document.addholidayform.action="${pageContext.request.contextPath}/DeleteSingleHolidayServlet";
                document.addholidayform.submit();
                
                
            }
            function invokeReset(){
                
                window.location = "${pageContext.request.contextPath}/LoadHolidayManagementServlet";
            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.HOLIDAYMGT%>'                                  
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



        <%!
            public int nullIntconv(String inv) {
                int conv = 0;

                try {
                    conv = Integer.parseInt(inv);
                } catch (Exception e) {
                }
                return conv;
            }
        %>
        <%
            int iYear = nullIntconv(request.getParameter("iYear"));
            int iMonth = nullIntconv(request.getParameter("iMonth"));

            Calendar ca = new GregorianCalendar();
            int iTDay = ca.get(Calendar.DATE);
            int iTYear = ca.get(Calendar.YEAR);
            int iTMonth = ca.get(Calendar.MONTH);

            if (iYear == 0) {
                iYear = iTYear;
                iMonth = iTMonth;
            }

            GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

            int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);

            cal = new GregorianCalendar(iYear, iMonth, days);
            int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);

        %>

        <script>
            function goTo()
            {
           
                document.onchangeviewholidayform.action="${pageContext.request.contextPath}/LoadOnChangeHolidayManagementServlet";
                document.onchangeviewholidayform.submit();
            }
            function goToRadioOnClick()
            {
           
                document.changetypeform.action="${pageContext.request.contextPath}/LoadOnChangeHolidayManagementServlet";
                document.changetypeform.submit();
            }

            function view_description(hday,description)
            {
    
                             
                var yr=document.getElementById('yearselectd').value;
      
                var mnth=document.getElementById('monthselectd').value;
                var intemonth=parseInt(mnth);
                var intemonthZero=zeroFill(intemonth+1, 2);
                var hldayDate=zeroFill(hday,2);
    
                var datetimeformat=yr+"-"+(intemonthZero)+"-"+hldayDate;
   
                document.getElementById('holiday').value=datetimeformat;    
     
   
     
                document.getElementById('description').value=description; 
     
                document.getElementById('buttonadd').style.visibility="hidden";
     
     
     
   
   
       
            }


            function zeroFill( number, width )
            {
                width -= number.toString().length;
                if ( width > 0 )
                {
                    return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( '0' ) + number;
                }
                return number;
            }


            function show_prompt(datetime)
            {
                var yr=document.getElementById('yearselectd').value;
      
                var mnth=document.getElementById('monthselectd').value;
                var intemonth=parseInt(mnth);
                var intemonthZero=zeroFill(intemonth+1, 2);
                var hldayDate=zeroFill(datetime,2);
   
 
                var datetimeformat=yr+"-"+(intemonthZero)+"-"+hldayDate;
    
                var reason=prompt("Date ="+datetimeformat+"\nPlease enter holiday reason .");


                if(reason.length > 256){
                    var ff=  alert("Inserted value is too large.Try with short one.");                    
                }else{
                if (reason!=null && reason!="")
                {
                    var yr=document.getElementById('yearselectd');
      
                    var mnth=document.getElementById('monthselectd');
                    document.getElementById('yeartext').value=yr.value;     
                    document.getElementById('monthtext').value=intemonthZero;
     
                    document.getElementById('datetext').value=hldayDate;
                    document.getElementById('reasontext').value=reason;
    
     
              
                    document.onchangeviewholidayform.action="${pageContext.request.contextPath}/AddSingleHolidayFromCalenderServlet";
                    document.onchangeviewholidayform.submit();
      
                }
                else{
                    var ff=  alert("No holiday reason found.");
    
                }
            }
            }



            function showBox(text, obj) {
                helpNode = document.createElement('div');
                helpNode.id = 'popBox';
                helpNode.setAttribute('class','popBox');
                helpNode.innerHTML = text;
                var len = text.length;
                len += ((len*2)/3)*2;  
               
                helpNode.style.width = len.toString() + "mm" ;
                helpNode.style.textAlign = "left";
                obj.appendChild(helpNode);
                
            }

            function hideBox() {
                node = document.getElementById('popBox');
                node.parentNode.removeChild(node);
            }


        </script>
        <style>
            body
            {
                font-family:Verdana, Arial, Helvetica, sans-serif
            }
            .dsb
            {
                //omitted for new UI
                //background-color:#EEEEEE;
            }
        </style>
        <style type="text/css">
            .popBox {
                position: absolute;
                z-index: 2;
                background: #EEEEEE;
                width: 250px;
                height:50px; 
                padding: 0.5em;
                border: 1px solid gray;
            }
            span {
                color: #000;
                font-weight: bold;
            }
            .tableHeader{
                border-radius: 15px;
                border-width: 1px;
                border-style: solid;
                background-color: #7f6c5d;
                color: white;    
                min-width: 100px;
            }
        </style>




    </head>
    <body>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <form action="" method="POST" name="changetypeform"   >

                                    <table>
                                        <tr>
                                            <c:if test="${operationtype=='fileuploading'}">
                                                <!--                           <td>  <br>
                                                                               <input type="radio"  name="type" value="selecteddate"  hidden="true" onclick="goToRadioOnClick()">&nbsp;&nbsp;Add with calender
                                                                           <input type="radio" name="type" value="fileuploading"  hidden="true"  checked onclick="goToRadioOnClick()">&nbsp;&nbsp;File Upload<br><br>  
                                                                           </td>-->
                                            </c:if>
                                            <c:if test="${operationtype=='selecteddate'}">
                                                <td>  <br>
                                                    <input type="hidden"  name="type" value="selecteddate" hidden  >
                                                    <!--                                  &nbsp;&nbsp;Add with calender-->
                                                    <!--                           <input type="radio" name="type"  value="fileuploading" hidden="true"  onclick="goToRadioOnClick()">&nbsp;&nbsp;File Upload<br><br>  -->
                                                </td> 
                                            </c:if>

                                        </tr>
                                    </table>
                                </form>



                                <c:if test="${operationtype=='fileuploading'}">
                                    <form action="" method="POST" name="addpageform"   enctype="multipart/form-data">

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
                                                        <td>select File</td>

                                                        <td><input type="file" class="inputfield-Description-mandatory" name="holidayfilepath" value="<c:out value="${FileInfoBean.filePath}"></c:out>" maxlength="1024"></td>
                                                        <td></td>
                                                    </tr>
                                                    <tr>
                                                        <td></td>
                                                        <td><input type="submit" class="" value="Upload" name="add">&nbsp;&nbsp;
                                                        </td>
                                                        <td></td>
                                                    </tr>

                                                </tbody>

                                            </table>
                                        </form>

                                </c:if>



                                <c:if test="${operationtype=='selecteddate'}">

                                    <form  method="POST" name="addholidayform" >

                                        <table>
                                            <tr>
                                                <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                    <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                                    </td>
                                                </tr>
                                            </table>

                                            <table> 
                                                <tr>
                                                    <td>Select Date</td>
                                                    <td>
                                                        <input  name="holiday" maxlength="16" readonly class="inputfeild"  key="holiday" id="holiday" <c:if test="${tstVal=='val'}"></c:if><c:if test="${tstVal!='val'}"> value="${param.holiday}"</c:if>  />
                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#holiday" ).datepicker({
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
                                                <td>&nbsp;</td>
                                            </tr>

                                            <tr>
                                                <td>Description</td>
                                                <td><input type="text" name="description" id="description" maxlength="256" class="inputfeild" <c:if test="${tstVal=='val'}"></c:if><c:if test="${tstVal!='val'}"> value="${param.description}"</c:if>/></td>
                                                    <td>&nbsp;</td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" id="buttonadd" style="width: 100px;" class="" value="Add" name="add" onclick="invokeAdd()">
                                                        <input type="button"  class=""  style="width: 100px;" value="Update" name="update" onclick="invokeUpdate()">
                                                        <input type="button" class=""  style="width: 100px;" value="Delete" name="delete" onclick="invokeDelete()">
                                                        <input type="button" class=""  style="width: 100px;" value="Reset" name="reset" onclick="invokeReset()">
                                                    </td>
                                                    <td>  <!--      for history view start           -->

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <a  href="#"  onclick="invokeHistory('<%=PageVarList.HOLIDAYMGT%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a><br>


                                                    <!--       for history view end           --></td>
                                            </tr>


                                        </table>
                                    </form>


                                </c:if>


                                <form id="frm" name="onchangeviewholidayform">
                                    
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="2%">&nbsp;</td>
                                            <td width="45%"><h2 align="center" style="color:#4E96DE"><%=new SimpleDateFormat("MMMM").format(new Date(2008, iMonth, 01))%> <%=iYear%></h2></td>
                                            <td width="30%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td> 
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                                                <tr>
                                                                    <td width="6%"><b>Year :</b></td>
                                                                    <td width="7%">
                                                                        <select id="yearselectd" name="iYear" onChange="goTo()" >
                                                                            <%
                                                                                // start year and end year in combo box to change year in calendar
                                                                                for (int iy = iTYear - 5; iy <= iTYear + 70; iy++) {
                                                                                    if (iy == iYear) {
                                                                            %>
                                                                            <option   value="<%=iy%>" selected="selected"><%=iy%></option>
                                                                            <%
                                                                            } else {
                                                                            %>
                                                                            <option value="<%=iy%>">  <%=iy%></option>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </select></td> 
                                                                    <td width="6%"><b>Month :</b></td>
                                                                    <td width="8%">
                                                                        <select name="iMonth" id="monthselectd" onChange="goTo()">
                                                                            <%
                                                                                // print month in combo box to change month in calendar
                                                                                for (int im = 0; im <= 11; im++) {
                                                                                    if (im == iMonth) {
                                                                            %>
                                                                            <option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(2008, im, 01))%></option>
                                                                            <%
                                                                            } else {
                                                                            %>
                                                                            <option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(2008, im, 01))%></option>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </select></td>
                                                                </tr>
                                                                <tr>
                                                                    <td><br></td>
                                                                </tr>

                                                            </table></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <table align="center" cellpadding="3" cellspacing="0" width="100%" >
                                                                <thead>
                                                                    <tr>
                                                                        <th class="tableHeader">Sun</th>
                                                                        <th class="tableHeader">Mon</th>
                                                                        <th class="tableHeader">Tue</th>
                                                                        <th class="tableHeader">Wed</th>
                                                                        <th class="tableHeader">Thu</th>
                                                                        <th class="tableHeader">Fri</th>
                                                                        <th class="tableHeader">Sat</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    
                                                                    <%
                                                                        int cnt = 1;
                                                                        List<HolidayManagementBean> list = new ArrayList<HolidayManagementBean>();

                                                                        list = (List<HolidayManagementBean>) request.getAttribute("holidayList");
                                                                        //String[][] hol={{"14","independant day"},{"24","public holiday"}};
                                                                        for (int i = 1; i <= iTotalweeks; i++) {
                                                                    %>
                                                                    <tr>
                                                                        <%
                                                                            for (int j = 1; j <= 7; j++) {
                                                                                if (cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
                                                                        %>
                                                                        <td align="center" height="35" class="dsb">&nbsp;</td>
                                                                        <%               } else {
                                                                            int today = cnt - weekStartDay + 1;
                                                                            boolean isHoliday = false;
                                                                            for (int a = 0; a < list.size(); a++) {
                                                                                int holiday = 0;
                                                                                try {
                                                                                    holiday = Integer.parseInt(list.get(a).getDay());
                                                                                } catch (Exception ex) {
                                                                                }

                                                                                if (today == holiday) {

                                                                        %>  
                                                                        <% if (j == 1 || j == 7) {%>

                                                                        <td onmouseover="showBox('<b><%=list.get(a).getHolidayReason()%></b><br/> ', this)" onmouseout="hideBox()" onclick="show_prompt(<%=(cnt - weekStartDay + 1)%>)"  align="center" height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span style="background-color:#FF9933;border-radius: 50%;"><%=(cnt - weekStartDay + 1)%></span><br><span><%=list.get(a).getHolidayReason()%></span></td> 
                                                                        <% } else {%>

                                                                        <td  onmouseover="showBox('<b><%=list.get(a).getHolidayReason()%></b><br/> ', this)" onmouseout="hideBox()" onclick="view_description(<%=(cnt - weekStartDay + 1)%>,'<%=list.get(a).getHolidayReason()%>')" align="center" height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span style="background-color:red;border-radius: 50%;"><%=(cnt - weekStartDay + 1)%></span><br><span><%=list.get(a).getHolidayReason()%></span></td> 
                                                                        <% }


                                                                                    isHoliday = true;
                                                                                    break;
                                                                                }
                                                                            }

                                                                            if (!isHoliday) {




                                                                        %>
                                                                        <% if (j == 1 || j == 7) {%>
                                                                        <td  onclick="show_prompt(<%=(cnt - weekStartDay + 1)%>)" align="center" height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span style="background-color:#FF9933;border-radius: 50%;"><%=(cnt - weekStartDay + 1)%></span></td>
                                                                        <% } else {%>
                                                                        <td  onclick="show_prompt(<%=(cnt - weekStartDay + 1)%>)" align="center" height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span style="background-color:#a0dcfc;border-radius: 50%;"><%=(cnt - weekStartDay + 1)%></span></td>
                                                                        <% }

                                                                                    }
                                                                                }
                                                                                cnt++;
                                                                            }
                                                                        %>
                                                                    </tr>
                                                                    <%
                                                                        }
                                                                    %>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table></td>
                                            <td>&nbsp;</td>
                                            <td><input type="hidden" id="yeartext"  name="yeartext" maxlength="16" ></td>
                                            <td><input type="hidden" id="monthtext" name="monthtext" maxlength="16" ></td>
                                            <td><input type="hidden" id="datetext"  name="datetext" maxlength="16" ></td>
                                            <td><input type="hidden" id="reasontext" name="reasontext" maxlength="16" ></td>

                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </form>

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
