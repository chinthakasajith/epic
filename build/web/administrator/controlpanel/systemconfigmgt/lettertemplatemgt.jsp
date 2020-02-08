<%-- 
    Document   : lettertemplatemgt
    Created on : Apr 2, 2013, 3:16:56 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
   
   
            function loadDynamicContent(value,index){

                if(value == 'TITLE'){
                    $('#letterformat'+index).attr('disabled',false);
                    $('#status'+index).attr('disabled',false);
                        
                    $('#body'+index).hide();
                    $('#text'+index).hide();
                    $('#title'+index).load('${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/includetitle.jsp');
                    $('#title'+index).show();
                }else if(value == 'BODY'){

                    $('#letterformat'+index).attr('disabled',true);
                    $('#status'+index).attr('disabled',true);
                        
                    $('#title'+index).hide();
                    $('#text'+index).hide();
                    $('#body'+index).load('${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/includeletterbody.jsp');
                    $('#body'+index).show();
                  
                }else if(value == 'TEXT'){
                        
                    $('#letterformat'+index).attr('disabled',false);
                    $('#status'+index).attr('disabled',false);
                        
                    $('#title'+index).hide();
                    $('#body'+index).hide();
                    //                    $('#text'+index).load('${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/includetext.jsp');
                    $('#text'+index).show();
                  
                }else{
                    $('#letterformat'+index).attr('disabled',false);
                    $('#status'+index).attr('disabled',false);
                        
                    $('#title'+index).hide();
                    $('#body'+index).hide();
                    $('#text'+index).hide();
                }
                    
                removeItems();              

                             
            }
            
            
            
            function disableListBoxs(index){
                $('#letterformat'+index).attr('disabled',true);
                $('#status'+index).attr('disabled',true);
            
            }
            
            function loadTitlePage(index){
                
                $('#title'+index).load('${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/includetitle.jsp');
                $('#title'+index).show();
            
            }
            
            function loadBodyPage(index){
                
                $('#body'+index).load('${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/includeletterbody.jsp');
                $('#body'+index).show();
            
            }
            function setVisibleText(index){
                
                $('#text'+index).show();
            
            }
            
            
            function removeItems(){
                
                
                
                var arr = $('#fields1').val().concat(',', $('#fields2').val(),',',$('#fields3').val(),',',$('#fields4').val(),',',$('#fields5').val(),',',$('#fields6').val(),',',$('#fields7').val());
                    
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/LoadAddRemoveFieldsServlet",
                    dataType: 'json', 
                    data: {testName:arr},                       
                    success:function(jsonobject) {
                            
                            
                            
                        for(var i = 1;i <=7;i++){

                            var selval2 = $("#fields"+i).val();
                                                                   
                            $("#fields"+i + " option").remove();
                            $("#fields"+i).append("<option value=''>--SELECT--</option>");                     
                            $.each(jsonobject.combo1, function(code, description) {
                                $('#fields'+i).append(
                                $('<option></option>').val(code).html(description)); 
                            });                            
                            
                            if(selval2 != ''){
                                
                                $('#fields'+i).append(
                                $('<option></option>').val(selval2).html(selval2));                                                                                                                               
                                $('#fields'+i).val(selval2);
                            }
   
                                
                        }
                       
                            
                    }
                });
                
                
                
                
            }
   
   
            function resetForm(){
                window.location = "${pageContext.request.contextPath}/LoadLetterTemplateMgtServlet";
            }
            function viewLetterTemplate(value){
                window.location = "${pageContext.request.contextPath}/ViewLetterTemplateMgtServlet?id="+value;
            }
            function updateLetterTemplate(value){
                window.location = "${pageContext.request.contextPath}/UpdateLetterTemplateMgtLoadServlet?id="+value;
            }
            function BackLetterTemplateForm(){
                window.location = "${pageContext.request.contextPath}/LoadLetterTemplateMgtServlet";
            }
            function deleteTemplate(value){
             
                answer = confirm("Do you really want to delete "+value+" Letter Template?")
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteLetterTemplateMgtServlet?id="+value;
                }
                else
                {
                   
                }

            }
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LETTERTEMPLATE%>'                                  
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

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------    -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font id="errormsg" color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green" id="successmsg"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/AddLetterTemplateMgtServlet">
                                        <table border="0">


                                            <tbody>


                                                <tr>
                                                    <td style="width: 100px;">Template Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="8" name="templatecode" value="${tmpLetterBean.templateCode}" class="deftext"/></td>
                                                </tr>

                                                <tr style="height: 5px"></tr>

                                                <tr>
                                                    <td style="width: 100px;">Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" maxlength="60" name="description" value="${tmpLetterBean.description}" class="deftext"/></td>
                                                </tr>

                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${tmpLetterBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td style="width: 100px;">Process Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="processtype"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="process" items="${sessionScope.SessionObject.letterProcessList}">

                                                                <c:if test="${tmpLetterBean.processId==process.key}">
                                                                    <option value="${process.key}" selected>${process.value}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.processId!=process.key}">
                                                                    <option value="${process.key}">${process.value}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr style="height: 5px"></tr>
                                            </tbody>

                                        </table>

                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 100px;">Template Format</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr style="height: 5px"></tr>

                                                <tr>
                                                    <td>        <select  name="fields1" id="fields1"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields1.value,'1')">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                                <c:if test="${tmpLetterBean.fieldSet1[0]==field}">
                                                                    <option value="${field}" selected>${field}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.fieldSet1[0]!=field}">
                                                                    <option value="${field}">${field}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select> </td>
                                                    <td>        <select  name="letterformat1" id="letterformat1" class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                                <c:if test="${tmpLetterBean.fieldSet1[1]==format.key}">
                                                                    <option value="${format.key}" selected>${format.value}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.fieldSet1[1]!=format.key}">
                                                                    <option value="${format.key}">${format.value}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>
                                                    <td>        <select  name="status1" id="status1" class="inputfield-mandatory" style="width: 168px;" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                                <c:if test="${tmpLetterBean.fieldSet1[2]==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.fieldSet1[2]!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>

                                                    <c:if test="${tmpLetterBean.fieldSet1[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('1');
                                                </script>
                                            </c:if>
                                            </tr>

                                        </table>
                                        <table> 
                                            <tr><td>
                                                    <div id="title1"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body1"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text1"></div> -->

                                                    <div id="text1" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode1"  class="deftext">${tmpLetterBean.fieldSet1[3]}</textarea>



                                                    </div>



                                                </td>
                                            </tr>
                                        </table>




                                        <table>



                                            <tr style="height: 5px"></tr>

                                            <tr>
                                                <td >        <select  name="fields2" id="fields2"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields2.value,'2')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet2[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet2[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> </td>
                                                <td>        <select  name="letterformat2" id="letterformat2"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet2[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet2[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status2" id="status2" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet2[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet2[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>

                                                <c:if test="${tmpLetterBean.fieldSet2[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('2');
                                                </script>
                                            </c:if>
                                            </tr>
                                        </table>

                                        <table> 
                                            <tr><td>
                                                    <div id="title2"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body2"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text2"></div> -->

                                                    <div id="text2" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode2"  class="deftext">${tmpLetterBean.fieldSet2[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>



                                        <table>
                                            <tr style="height: 5px"></tr>

                                            <tr>
                                                <td >        <select  name="fields3" id="fields3"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields3.value,'3')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet3[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet3[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> </td>
                                                <td>        <select  name="letterformat3" id="letterformat3" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet3[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet3[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status3" id="status3" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet3[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet3[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>

                                                <c:if test="${tmpLetterBean.fieldSet3[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('3');
                                                </script>
                                            </c:if>
                                            </tr>

                                        </table>


                                        <table> 
                                            <tr><td>
                                                    <div id="title3"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body3"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text3"></div> -->
                                                    <div id="text3" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode3"  class="deftext">${tmpLetterBean.fieldSet3[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>


                                        <table>




                                            <tr style="height: 5px"></tr>



                                            <tr>
                                                <td >        <select  name="fields4" id="fields4"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields4.value,'4')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet4[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet4[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> </td>
                                                <td>        <select  name="letterformat4" id="letterformat4"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet4[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet4[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status4"  id="status4" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet4[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet4[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>

                                                <c:if test="${tmpLetterBean.fieldSet4[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('4');
                                                </script>
                                            </c:if>
                                            </tr>


                                        </table>


                                        <table> 
                                            <tr><td>
                                                    <div id="title4"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body4"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text4"></div> -->
                                                    <div id="text4" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode4"  class="deftext">${tmpLetterBean.fieldSet4[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>   


                                        <table>
                                            <tr style="height: 5px"></tr>


                                            <tr>
                                                <td >        <select  name="fields5" id="fields5" class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields5.value,'5')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet5[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet5[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> </td>
                                                <td>        <select  name="letterformat5" id="letterformat5"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet5[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet5[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status5" id="status5" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet5[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet5[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet5[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('5');
                                                </script>
                                            </c:if>
                                            </tr>

                                        </table>
                                        <table> 
                                            <tr><td>
                                                    <div id="title5"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body5"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text5"></div> -->
                                                    <div id="text5" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode5"  class="deftext">${tmpLetterBean.fieldSet5[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>




                                        <table>
                                            <tr style="height: 5px"></tr>


                                            <tr>
                                                <td >        <select  name="fields6" id="fields6" class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields6.value,'6')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet6[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet6[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> </td>
                                                <td>        <select  name="letterformat6" id="letterformat6"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet6[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet6[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status6" id="status6" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet6[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet6[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet6[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('6');
                                                </script>
                                            </c:if>
                                            </tr>

                                            <tr style="height: 5px"></tr>

                                            </tbody>
                                        </table> 

                                        <table> 
                                            <tr><td>
                                                    <div id="title6"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body6"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text6"></div> -->

                                                    <div id="text6" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode6"  class="deftext">${tmpLetterBean.fieldSet6[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>  




                                        <table>
                                            <tr style="height: 5px"></tr>


                                            <tr>
                                                <td >        <select  name="fields7" id="fields7" class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields7.value,'7')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet7[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet7[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> </td>
                                                <td>        <select  name="letterformat7" id="letterformat7"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet7[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet7[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status7" id="status7" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet7[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet7[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet7[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('7');
                                                </script>
                                            </c:if>
                                            </tr>

                                            <tr style="height: 5px"></tr>

                                            </tbody>
                                        </table> 

                                        <table> 
                                            <tr><td>
                                                    <div id="title7"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body7"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text7"></div> -->

                                                    <div id="text7" style="background-color: wheat" hidden="true">

                                                        Type the Text :
                                                        <textarea type="text"  cols="76" rows="1" name="textcode7"  class="deftext">${tmpLetterBean.fieldSet7[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>        

                                        <script>
                                            <c:if test="${tmpLetterBean.fieldSet1[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('1');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet2[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('2');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet3[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('3');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet4[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('4');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet5[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('5');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet6[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('6');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet7[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('7');                                               
                                            </c:if>
                                                
                                                
                                                
                                            <c:if test="${tmpLetterBean.fieldSet1[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('1');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet2[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('2');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet3[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('3');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet4[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('4');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet5[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('5');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet6[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('6');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet7[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('7');                                               
                                            </c:if>
                                                
                                                
                                            <c:if test="${tmpLetterBean.fieldSet1[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('1');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet2[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('2');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet3[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('3');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet4[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('4');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet5[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('5');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet6[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('6');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet7[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('7');                                               
                                            </c:if>
                                                
                                                
                                                removeItems();      
                                                    
                                        </script>

                                        <table>
                                            <tbody>
                                                <tr>

                                                    <td> 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="resetForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

























                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateLetterTemplateMgtServlet">

                                        <table border="0">

                                            <tbody>


                                                <tr>
                                                    <td style="width: 100px;">Template Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="8" name="templatecode" value="${tmpLetterBean.templateCode}" class="deftext" readonly="true"/></td>
                                                </tr>

                                                <tr style="height: 5px"></tr>

                                                <tr>
                                                    <td style="width: 100px;">Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" maxlength="60" name="description" value="${tmpLetterBean.description}" class="deftext"/></td>
                                                </tr>

                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${tmpLetterBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr style="height: 5px"></tr>
                                                <tr>
                                                    <td style="width: 100px;">Process Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="processtype"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="process" items="${sessionScope.SessionObject.letterProcessList}">

                                                                <c:if test="${tmpLetterBean.processId==process.key}">
                                                                    <option value="${process.key}" selected>${process.value}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.processId!=process.key}">
                                                                    <option value="${process.key}">${process.value}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr style="height: 5px"></tr>
                                            </tbody>

                                        </table>

                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 100px;">Template Format</td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr style="height: 5px"></tr>

                                                <tr>
                                                    <td>        <select  name="fields1" id="fields1"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields1.value,'1')">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                                <c:if test="${tmpLetterBean.fieldSet1[0]==field}">
                                                                    <option value="${field}" selected>${field}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.fieldSet1[0]!=field}">
                                                                    <option value="${field}">${field}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select> 

                                                    </td>


                                                    <td>        <select  name="letterformat1" id="letterformat1" class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>



                                                            <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">



                                                                <c:if test="${tmpLetterBean.fieldSet1[1]==format.key}">
                                                                    <option value="${format.key}" selected>${format.value}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.fieldSet1[1]!=format.key}">
                                                                    <option value="${format.key}">${format.value}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select></td>
                                                    <td>        <select  name="status1" id="status1" class="inputfield-mandatory" style="width: 168px;" >
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                                <c:if test="${tmpLetterBean.fieldSet1[2]==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${tmpLetterBean.fieldSet1[2]!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select></td>

                                                    <c:if test="${tmpLetterBean.fieldSet1[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('1');
                                                </script>
                                            </c:if>
                                            </tr>

                                        </table>
                                        <table> 
                                            <tr><td>
                                                    <div id="title1"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body1"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text1"></div> -->

                                                    <div id="text1" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode1" class="deftext">${tmpLetterBean.fieldSet1[3]}</textarea>



                                                    </div>


                                                </td>
                                            </tr>
                                        </table>




                                        <table>



                                            <tr style="height: 5px"></tr>

                                            <tr>
                                                <td >        <select  name="fields2" id="fields2"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields2.value,'2')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet2[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet2[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>


                                                </td>

                                                <td>        <select  name="letterformat2" id="letterformat2"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet2[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet2[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status2" id="status2" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet2[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet2[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet2[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('2');
                                                </script>
                                            </c:if>

                                            </tr>
                                        </table>

                                        <table> 
                                            <tr><td>
                                                    <div id="title2"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body2"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text2"></div> -->

                                                    <div id="text2" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode2"  class="deftext">${tmpLetterBean.fieldSet2[3]} </textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>



                                        <table>
                                            <tr style="height: 5px"></tr>

                                            <tr>
                                                <td >        <select  name="fields3" id="fields3"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields3.value,'3')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet3[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet3[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td>        <select  name="letterformat3" id="letterformat3" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet3[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet3[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status3" id="status3" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet3[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet3[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>

                                                <c:if test="${tmpLetterBean.fieldSet3[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('3');
                                                </script>
                                            </c:if>
                                            </tr>

                                        </table>


                                        <table> 
                                            <tr><td>
                                                    <div id="title3"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body3"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text3"></div> -->
                                                    <div id="text3" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode3" class="deftext">${tmpLetterBean.fieldSet3[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>


                                        <table>




                                            <tr style="height: 5px"></tr>



                                            <tr>
                                                <td >        <select  name="fields4" id="fields4"  class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields4.value,'4')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet4[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet4[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td>

                                                <td>        <select  name="letterformat4" id="letterformat4"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet4[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet4[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status4"  id="status4" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet4[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet4[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet4[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('4');
                                                </script>
                                            </c:if>
                                            </tr>


                                        </table>


                                        <table> 
                                            <tr><td>
                                                    <div id="title4"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body4"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text4"></div> -->
                                                    <div id="text4" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode4" class="deftext">${tmpLetterBean.fieldSet4[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>   


                                        <table>
                                            <tr style="height: 5px"></tr>


                                            <tr>
                                                <td >        <select  name="fields5" id="fields5" class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields5.value,'5')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet5[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet5[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> 

                                                </td>

                                                <td>        <select  name="letterformat5" id="letterformat5"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet5[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet5[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status5" id="status5" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet5[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet5[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>

                                                <c:if test="${tmpLetterBean.fieldSet5[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('5');
                                                </script>
                                            </c:if> 
                                            </tr>

                                        </table>
                                        <table> 
                                            <tr><td>
                                                    <div id="title5"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body5"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text5"></div> -->
                                                    <div id="text5" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode5" class="deftext">${tmpLetterBean.fieldSet5[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>




                                        <table>
                                            <tr style="height: 5px"></tr>


                                            <tr>
                                                <td >        <select  name="fields6" id="fields6" class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields6.value,'6')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet6[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet6[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select> 

                                                </td>

                                                <td>        <select  name="letterformat6" id="letterformat6"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet6[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet6[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status6" id="status6" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet6[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet6[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet6[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('6');
                                                </script>
                                            </c:if>
                                            </tr>

                                            <tr style="height: 5px"></tr>

                                            </tbody>
                                        </table> 

                                        <table> 
                                            <tr><td>
                                                    <div id="title6"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body6"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text6"></div> -->

                                                    <div id="text6" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode6"  class="deftext">${tmpLetterBean.fieldSet6[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>  




                                        <table>
                                            <tr style="height: 5px"></tr>


                                            <tr>
                                                <td >        <select  name="fields7" id="fields7" class="inputfield-mandatory" style="width: 168px;" onchange="loadDynamicContent(fields7.value,'7')">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="field" items="${sessionScope.SessionObject.letterFieldsList}">

                                                            <c:if test="${tmpLetterBean.fieldSet7[0]==field}">
                                                                <option value="${field}" selected>${field}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet7[0]!=field}">
                                                                <option value="${field}">${field}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td>

                                                <td>        <select  name="letterformat7" id="letterformat7"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                                            <c:if test="${tmpLetterBean.fieldSet7[1]==format.key}">
                                                                <option value="${format.key}" selected>${format.value}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet7[1]!=format.key}">
                                                                <option value="${format.key}">${format.value}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                <td>        <select  name="status7" id="status7" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                                            <c:if test="${tmpLetterBean.fieldSet7[2]==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${tmpLetterBean.fieldSet7[2]!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select></td>
                                                    <c:if test="${tmpLetterBean.fieldSet7[0]=='BODY'}">

                                                <script>
                                                    disableListBoxs('7');
                                                </script>
                                            </c:if>
                                            </tr>

                                            <tr style="height: 5px"></tr>

                                            </tbody>
                                        </table> 

                                        <table> 
                                            <tr><td>
                                                    <div id="title7"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <div id="body7"></div> 
                                                </td>
                                            </tr>
                                            <tr><td>
                                                    <!--                                                    <div id="text7"></div> -->

                                                    <div id="text7" style="background-color: wheat" hidden="true">

                                                        Type the Text :

                                                        <textarea type="text"  cols="76" rows="1" name="textcode7"  class="deftext">${tmpLetterBean.fieldSet7[3]}</textarea>



                                                    </div>
                                                </td>
                                            </tr>
                                        </table>        
                                        <script>
                                            <c:if test="${tmpLetterBean.fieldSet1[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('1');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet2[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('2');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet3[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('3');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet4[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('4');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet5[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('5');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet6[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('6');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet7[0]=='TITLE'}">                                                                                                 
                                                loadTitlePage('7');                                               
                                            </c:if>
                                                
                                                
                                                
                                            <c:if test="${tmpLetterBean.fieldSet1[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('1');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet2[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('2');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet3[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('3');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet4[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('4');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet5[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('5');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet6[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('6');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet7[0]=='BODY'}">                                                                                                 
                                                loadBodyPage('7');                                               
                                            </c:if>
                                                
                                                
                                            <c:if test="${tmpLetterBean.fieldSet1[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('1');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet2[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('2');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet3[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('3');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet4[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('4');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet5[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('5');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet6[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('6');                                               
                                            </c:if>
                                            <c:if test="${tmpLetterBean.fieldSet7[0]=='TEXT'}">                                                                                                 
                                                setVisibleText('7');                                               
                                            </c:if>
                                                
                                                
                                                removeItems();      
                                                    
                                        </script>


                                        <table>
                                            <tbody>
                                                <tr>

                                                    <td> 
                                                        <input type="submit" value="Update" name="Update" class="defbutton"/>
                                                        <input onclick="resetForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>



                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" action="">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Template Code </td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${tmpLetterBean.templateCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td>:${tmpLetterBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>                                         
                                                    <td>:${tmpLetterBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Process Type </td>
                                                    <td></td>                                         
                                                    <td>:${tmpLetterBean.processDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Template Format </td>
                                                    <td></td>                                         
                                                    <td>:${tmpLetterBean.templateFormat}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="Back" class="defbutton" onclick="BackLetterTemplateForm()"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>


                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>




                                <br/>

                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview5">
                                        <thead>
                                            <tr>
                                                <th>Template Code</th>
                                                <th>Description</th>
                                                <th>Status</th>
                                                <th>Template Format</th>
                                                <th>Process Type</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${templateList}" var="templateBean">
                                                <tr>
                                                    <td>${templateBean.templateCode}</td>
                                                    <td>${templateBean.description}</td>
                                                    <td>${templateBean.statusDes}</td>
                                                    <td>${templateBean.templateFormat}</td>
                                                    <td>${templateBean.processDes}</td>
                                                    <td><a  href='#' onclick="viewLetterTemplate('${templateBean.templateCode}')">View</a></td>
                                                    <td><a  href='#' onclick="updateLetterTemplate('${templateBean.templateCode}')">Update</a></td>
                                                    <td><a  href='#' onclick="deleteTemplate('${templateBean.templateCode}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
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


