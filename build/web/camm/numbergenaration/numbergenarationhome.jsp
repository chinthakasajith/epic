<%-- 
    Document   : numbergenarationhome
    Created on : Mar 28, 2012, 11:51:59 AM
    Author     : janaka_h
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script language = "javascript">
            
            function checkAll(field)
            {

                if(field.length){
                    for (i = 0; i < field.length; i++)
                        field[i].checked = true ;
                }
                else
                    field.checked =true;
            }

            function uncheckAll(field)
            {
                if(field.length){
                    for (i = 0; i < field.length; i++)
                        field[i].checked = false ;
                }
                else
                    field.checked =false;
            }
            
            function changeProducts(cardType)
            {
                
                $.post("${pageContext.request.contextPath}/SetProductDropDownServlet",
                { cardType:$('#cardtype option:selected').val()
                                    
                },
                function(data) {
                    if(data!=''){
                                        
                        var array=data.split('|', 2)
                        var codes=array[0].split(',')
                        var descriptions=array[1].split(',')
                        
            
                            
                        $('#cardproduct option').each(function(){
                            $(this).remove()
                        });
                         
                        $('#cardproduct').append(
                        $('<option></option>').val('').html('--SELECT--')
                    );
                        for(var x=0;x < codes.length;x++){
                            $('#cardproduct').append(
                            $('<option></option>').val(codes[x]).html(descriptions[x])
                        );
                        }
                          
                        //                       
                                           
                    }
                                      
                                        
                });
                
             
                
                
               
            }
            
           
            function invokeReset(){
            
                window.location = "${pageContext.request.contextPath}/LoadCardNumberGenarationServlet";
            
            }
            
            

 

                
                
        </script>
        <script language = "javascript">
            function invokeGenarate(isAll){
                 
                if(isAll=='set'){
                     
                    var boxes = document.genarateanumberform.elements.length;
                    
                   
                    var array = [];
                    for (var i = 0; i < boxes; i++) {
                        if (document.genarateanumberform.elements[i].checked) {
                            ///alert(document.genarateanumberform.elements[i].value);
                            array.push(document.genarateanumberform.elements[i].value);
                            
                        }
                    }
                    
//                    document.getElementById("hiddenarray").setAttribute("value", array);
                    document.genarateanumberform.action="${pageContext.request.contextPath}/StartNumberGenarationServlet?hiddenarray="+array;
                    document.genarateanumberform.submit();
                }
                if(isAll=='all'){
                    
                    //alert(isAll);
                    
                    document.genarateanumberform.action="${pageContext.request.contextPath}/StartNumberGenarationServlet";
                    document.genarateanumberform.submit();
                }
            
            }
           
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.NUMBERGEN%>'                                  
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
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

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
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <form method="POST" action="<%=request.getContextPath()%>/SearchForNumberGenarationSevlet" name="searchuserassignform" >
                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td width="200px;">Application ID</td>
                                                <td><input type="text"  value="" name="appliactionid" maxlength="16"></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td width="200px;">Name on Card </td>
                                                <td><input type="text"  value="" name="nameoncard" maxlength="32"></td>
                                                <td></td>
                                            </tr>


                                            <tr>

                                                <td>Card Type </td>
                                                <td>
                                                    <select  name="cardtype" id="cardtype"  onchange="changeProducts(value)" onload="changeProducts('VISA')">

                                                        <c:forEach var="cardTypeList" items="${sessionScope.SessionObject.cardTypeList}" >

                                                            <option value="${cardTypeList.cardTypeCode}"  >${cardTypeList.description}</option>   

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td ></td>
                                            </tr>

                                            <tr>
                                                <td>Card Product </td>

                                                <td>
                                                    <select  name="cardproduct" id="cardproduct">


                                                    </select>
                                                </td>
                                                <td onload="changeProduct('VISA')"></td>
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








                                            <tr> <td style="height:12px;"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td><input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeAssignSearch()">
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                <td></td>
                                            </tr>

                                        </tbody>
                                    </table>
                                    <script >  
        
                                        changeProducts('${card}');
           
    
                                    </script>              
                                </form>
                                <form method="POST" action="<%=request.getContextPath()%>/SearchForNumberGenarationSevlet" name="genarateanumberform" >    
                                    <table  border="1"  class="display" id="tableview">
                                        <thead>
                                            <tr>

                                                <th>Select</th>
                                                <th>Application ID </th>
                                                <th>Name On Card</th>
                                                <th>Card Type</th>
                                                <th>Card Product ID</th>
                                                <th>Priority Level</th>
                                                <th>Created Date</th>


                                            </tr>
                                        </thead>
                                        <tbody>



                                            <c:forEach var="searchList" items="${searchList}">
                                                <tr>
                                                    <td ><input type="checkbox" name="check" value="${searchList.applicationId}"></td>
                                                    <td >${searchList.applicationId}</td>
                                                    <td >${searchList.nameOnCard}</td>
                                                    <td >${searchList.cardType}</td>
                                                    <td >${searchList.cardProduct}</td>
                                                    <td >${searchList.priorityLevel}</td>
                                                    <td >${searchList.createTime}</td>

                                                </tr>
                                            </c:forEach> 
                                        </tbody>
                                    </table>   
                                    <table cellpadding="0" cellspacing="10">
                                        <tr></tr>
                                        <tr><td ><input type="hidden" id="hiddenarray" name="hiddenarray"> </td></tr>
                                        <tr>

                                            <td></td><td ><input type="button" value="UnCheck All" onclick="uncheckAll(check)">&nbsp;<input type="button" value="Genarate" onclick="invokeGenarate('set')">&nbsp;<input type="button" value="Genarate All" onclick="invokeGenarate('all')"></td>

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
