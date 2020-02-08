<%-- 
    Document   : includeletterbody
    Created on : Apr 4, 2013, 10:51:15 AM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <script type="text/javascript">
            function loadBodyDynamicContent(value,index){
              
                if(value == 'TEXT'){
                    //                        $('#bodytext'+index).load('${pageContext.request.contextPath}/administrator/controlpanel/systemconfigmgt/includetext.jsp');
                    $('#bodytext'+index).show();
                }else{
                    $('#bodytext'+index).hide();
                }
         
            
            }
            
            
            function setBodyVisibleText(index){
                
                $('#bodytext'+index).show();
            
            }
        </script>
    </head>
    <body>



        <div style="background-color: wheat; height: 330px" >

            <table border="0">

                <tbody>

                    <tr>
                        <td style="width: 100px;">Letter Body Format</td>
                        <td></td>
                        <td></td>
                    </tr>

                    <tr style="height: 5px"></tr>

                    <tr>
                        <td>        <select  name="bodyfields1"  class="inputfield-mandatory" style="width: 168px;" onchange="loadBodyDynamicContent(bodyfields1.value,'1')">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="field" items="${sessionScope.SessionObject.letterBodyFieldsList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[0]==field}">
                                        <option value="${field}" selected>${field}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[0]!=field}">
                                        <option value="${field}">${field}</option>

                                    </c:if>


                                </c:forEach>
                            </select> </td>
                        <td>        <select  name="bodyletterformat1"  class="inputfield-mandatory" style="width: 168px;">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[1]==format.key}">
                                        <option value="${format.key}" selected>${format.value}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[1]!=format.key}">
                                        <option value="${format.key}">${format.value}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                        <td>        <select  name="bodystatus1"  class="inputfield-mandatory" style="width: 168px;" >
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[2]==status.statusCode}">
                                        <option value="${status.statusCode}" selected>${status.description}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[2]!=status.statusCode}">
                                        <option value="${status.statusCode}">${status.description}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                    </tr>

            </table>

            <table> 
                <tr><td>
                        <!--                        <div id="bodytext1"></div> -->
                        <div id="bodytext1" style="background-color: wheat" hidden="true">

                            Type the Text :

                            <textarea type="text"  cols="76" rows="1" name="bodytextcode1"  class="deftext">${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[3]}</textarea>



                        </div>
                    </td>
                </tr>

            </table>




            <table border="0">

                <tbody>


                    <tr>
                        <td>        <select  name="bodyfields2"  class="inputfield-mandatory" style="width: 168px;" onchange="loadBodyDynamicContent(bodyfields2.value,'2')">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="field" items="${sessionScope.SessionObject.letterBodyFieldsList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[0]==field}">
                                        <option value="${field}" selected>${field}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[0]!=field}">
                                        <option value="${field}">${field}</option>

                                    </c:if>


                                </c:forEach>
                            </select> </td>
                        <td>        <select  name="bodyletterformat2"  class="inputfield-mandatory" style="width: 168px;">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[1]==format.key}">
                                        <option value="${format.key}" selected>${format.value}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[1]!=format.key}">
                                        <option value="${format.key}">${format.value}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                        <td>        <select  name="bodystatus2"  class="inputfield-mandatory" style="width: 168px;" >
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[2]==status.statusCode}">
                                        <option value="${status.statusCode}" selected>${status.description}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[2]!=status.statusCode}">
                                        <option value="${status.statusCode}">${status.description}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                    </tr>

            </table>

            <table> 
                <tr><td>
                        <!--                        <div id="bodytext2"></div> -->

                        <div id="bodytext2" style="background-color: wheat" hidden="true">

                            Type the Text :

                            <textarea type="text"  cols="76" rows="1" name="bodytextcode2"  class="deftext">${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[3]}</textarea>



                        </div>
                    </td>
                </tr>

            </table>



            <table border="0">

                <tbody>


                    <tr>
                        <td>        <select  name="bodyfields3"  class="inputfield-mandatory" style="width: 168px;" onchange="loadBodyDynamicContent(bodyfields3.value,'3')">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="field" items="${sessionScope.SessionObject.letterBodyFieldsList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[0]==field}">
                                        <option value="${field}" selected>${field}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[0]!=field}">
                                        <option value="${field}">${field}</option>

                                    </c:if>


                                </c:forEach>
                            </select> </td>
                        <td>        <select  name="bodyletterformat3"  class="inputfield-mandatory" style="width: 168px;">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[1]==format.key}">
                                        <option value="${format.key}" selected>${format.value}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[1]!=format.key}">
                                        <option value="${format.key}">${format.value}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                        <td>        <select  name="bodystatus3"  class="inputfield-mandatory" style="width: 168px;" >
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[2]==status.statusCode}">
                                        <option value="${status.statusCode}" selected>${status.description}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[2]!=status.statusCode}">
                                        <option value="${status.statusCode}">${status.description}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                    </tr>

            </table>

            <table> 
                <tr><td>
                        <!--                        <div id="bodytext3"></div> -->

                        <div id="bodytext3" style="background-color: wheat" hidden="true">

                            Type the Text :

                            <textarea type="text"  cols="76" rows="1" name="bodytextcode3"  class="deftext">${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[3]}</textarea>



                        </div>
                    </td>
                </tr>

            </table>



            <table border="0">

                <tbody>


                    <tr>
                        <td>        <select  name="bodyfields4"  class="inputfield-mandatory" style="width: 168px;" onchange="loadBodyDynamicContent(bodyfields4.value,'4')">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="field" items="${sessionScope.SessionObject.letterBodyFieldsList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[0]==field}">
                                        <option value="${field}" selected>${field}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[0]!=field}">
                                        <option value="${field}">${field}</option>

                                    </c:if>


                                </c:forEach>
                            </select> </td>
                        <td>        <select  name="bodyletterformat4"  class="inputfield-mandatory" style="width: 168px;">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[1]==format.key}">
                                        <option value="${format.key}" selected>${format.value}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[1]!=format.key}">
                                        <option value="${format.key}">${format.value}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                        <td>        <select  name="bodystatus4"  class="inputfield-mandatory" style="width: 168px;" >
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[2]==status.statusCode}">
                                        <option value="${status.statusCode}" selected>${status.description}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[2]!=status.statusCode}">
                                        <option value="${status.statusCode}">${status.description}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                    </tr>

            </table>

            <table> 
                <tr><td>
                        <!--                        <div id="bodytext4"></div> -->

                        <div id="bodytext4" style="background-color: wheat" hidden="true">

                            Type the Text :

                            <textarea type="text"  cols="76" rows="1" name="bodytextcode4"  class="deftext">${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[3]}</textarea>



                        </div>
                    </td>
                </tr>

            </table>





            <table border="0">

                <tbody>


                    <tr>
                        <td>        <select  name="bodyfields5"  class="inputfield-mandatory" style="width: 168px;" onchange="loadBodyDynamicContent(bodyfields5.value,'5')">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="field" items="${sessionScope.SessionObject.letterBodyFieldsList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[0]==field}">
                                        <option value="${field}" selected>${field}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[0]!=field}">
                                        <option value="${field}">${field}</option>

                                    </c:if>


                                </c:forEach>
                            </select> </td>
                        <td>        <select  name="bodyletterformat5"  class="inputfield-mandatory" style="width: 168px;">
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="format" items="${sessionScope.SessionObject.letterFormatList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[1]==format.key}">
                                        <option value="${format.key}" selected>${format.value}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[1]!=format.key}">
                                        <option value="${format.key}">${format.value}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                        <td>        <select  name="bodystatus5"  class="inputfield-mandatory" style="width: 168px;" >
                                <option value="" selected>--SELECT--</option>
                                <c:forEach var="status" items="${sessionScope.SessionObject.commonStatusList}">

                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[2]==status.statusCode}">
                                        <option value="${status.statusCode}" selected>${status.description}</option>
                                    </c:if>
                                    <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[2]!=status.statusCode}">
                                        <option value="${status.statusCode}">${status.description}</option>

                                    </c:if>


                                </c:forEach>
                            </select></td>
                    </tr>

            </table>

            <table> 
                <tr><td>
                        <!--                        <div id="bodytext5"></div> -->

                        <div id="bodytext5" style="background-color: wheat" hidden="true">

                            Type the Text :

                            <textarea type="text"  cols="76" rows="1" name="bodytextcode5"  class="deftext">${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[3]}</textarea>



                        </div>
                    </td>
                </tr>

            </table>



            <script>
                              
                <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody1[0]=='TEXT'}">                                                                                                 
                    setBodyVisibleText('1');                                               
                </c:if>
                <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody2[0]=='TEXT'}">                                                                                                 
                    setBodyVisibleText('2');                                               
                </c:if>
                <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody3[0]=='TEXT'}">                                                                                                 
                    setBodyVisibleText('3');                                               
                </c:if>
                <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody4[0]=='TEXT'}">                                                                                                 
                    setBodyVisibleText('4');                                               
                </c:if>
                <c:if test="${sessionScope.SessionObject.letterBodyFormatBean.fieldSetBody5[0]=='TEXT'}">                                                                                                 
                    setBodyVisibleText('5');                                               
                </c:if>
                                        
                                                 
                                                    
            </script>


        </div>




    </body>
</html>
