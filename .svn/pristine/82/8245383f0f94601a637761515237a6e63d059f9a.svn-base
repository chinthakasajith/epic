<%-- 
    Document   : bulkcarddatacapturehome
    Created on : Dec 5, 2012, 9:33:53 AM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script>
            
            function invokeSave(){
                
              
                document.bulkDataCaptureForm.action="${pageContext.request.contextPath}/AddBulkCardDataCaptureServlet";
                document.bulkDataCaptureForm.submit();
            }
            
            function invokeResetAll(){
                
                window.location = "${pageContext.request.contextPath}/LoadBulkCardDataCpatureServlet";
            }
            
            
            
            function invokeReset(ele){
                               
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                    }
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
               
                $("#control").val("");
                $("#control2").val("");
            }
            
        </script>
        <script>
            function settitle(){
                   
             $.post("${pageContext.request.contextPath}/SetNavigatio   nTitleServlet",
                { pagecode:'<%= PageVarList.BULK_CARD_DATA_CAPTURE%>'                                  
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
        <style type="text/css">
            input,  textarea {
                /*	float:left;*/
                font-family:Arial, Helvetica, sans-serif;
                font-size: 12px;
                padding:2px;
                border: 1px solid #DBD1C7;
                -moz-border-radius: 5px;
                -webkit-border-radius: 5px;
                margin: 3px 5px 0px 0px;
                text-transform: uppercase;
                //background-color: #FFFFFF;

            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>
        <div class="container" >
            <div class="header">             
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
                                    <tr >
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form ENCTYPE="multipart/form-data" name="bulkDataCaptureForm" method="Post" action="">
                                    <table cellpadding="0" cellspacing="10"  >

                                        <tr>
                                            <td style="width: 200px;">Identification Type</td>

                                            <td><select name="identificationType" >
                                                    <option value="">--Select Identification Type-- </option>

                                                    <c:forEach var="identity" items="${sessionScope.SessionObject.identityTypeList}">
                                                        <c:if test="${bulkBean.identificationType==identity.key}">
                                                            <option value="${identity.key}" selected>${identity.value}</option>
                                                        </c:if>
                                                        <c:if test="${bulkBean.identificationType != identity.key}">
                                                            <option value="${identity.key}" >${identity.value}</option>
                                                        </c:if>
                                                    </c:forEach>        
                                                </select>
                                            </td>

                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.identificationType}</label></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Identification Number</td>
                                            <td> <input type="text" name="IdentificationNumber" maxlength="32" value="${bulkBean.identificationNumber}" ></td>
                                            <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.identificationNumber} </label></td> 
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Title</td>
                                            <td><select name="title">
                                                    <option value="">--Select Title--</option>

                                                    <c:if test="${bulkBean.title =='Mr'}"> <option selected="true" value="Mr">Mr</option></c:if>
                                                    <c:if test="${bulkBean.title !='Mr'}"> <option value="Mr">Mr</option></c:if>
                                                    <c:if test="${bulkBean.title =='Mrs'}"> <option selected="true" value="Mrs">Mrs</option></c:if>
                                                    <c:if test="${bulkBean.title !='Mrs'}"> <option value="Mrs">Mrs</option></c:if>
                                                    <c:if test="${bulkBean.title =='Ms'}"> <option selected="true" value="Ms">Ms</option></c:if>
                                                    <c:if test="${bulkBean.title !='Ms'}"> <option value="Ms">Ms</option></c:if>
                                                    <c:if test="${bulkBean.title =='Dr'}"> <option selected="true" value="Dr">Dr</option></c:if>
                                                    <c:if test="${bulkBean.title !='Dr'}"> <option value="Dr">Dr</option></c:if>                                                
                                                    <c:if test="${bulkBean.title =='Prof'}"> <option selected="true" value="Prof">Prof</option></c:if>
                                                    <c:if test="${bulkBean.title !='Prof'}"> <option value="Prof">Prof</option></c:if>
                                                    <c:if test="${bulkBean.title =='Rev'}"> <option selected="true" value="Rev">Rev</option></c:if>
                                                    <c:if test="${bulkBean.title !='Rev'}"> <option value="Rev">Rev</option></c:if>
                                                    <c:if test="${bulkBean.title =='Hon'}"> <option selected="true" value="Hon">Hon</option></c:if>
                                                    <c:if test="${bulkBean.title !='Hon'}"> <option value="Hon">Hon</option></c:if>
                                                    <c:if test="${bulkBean.title =='Ven'}"> <option selected="true" value="Ven">Ven</option></c:if>
                                                    <c:if test="${bulkBean.title !='Ven'}"> <option value="Ven">Ven</option></c:if>
                                                    </select>
                                                </td>
                                                <td style="width: 100px;"> <label style="color: red">${invalidMsgBean.title} </label></td> 
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">First Name</td>
                                            <td><input type="text" name="firstName"  maxlength="50" value="${bulkBean.firstName}"></td>
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.firstName} </label></td>    
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"> Middle Name</td>    
                                            <td><input type="text" name="middleName"  maxlength="50" value="${bulkBean.middleName}"></td>  
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.middleName} </label></td> 
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Last name</td>
                                            <td><input type="text" name="lastName"  maxlength="50" value="${bulkBean.lastName}"></td>
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.lastName} </label></td>  
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;"> Mothers Maiden Name</td>    
                                            <td><input type="text" name="mothersMaidenName"  maxlength="50" value="${bulkBean.mothersMaidenName}"></td>                                              
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.mothersMaidenName}</label></td> 
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Primary Account No</td>
                                            <td><input type="text" name="primeryAccNo"  maxlength="12" value="${bulkBean.primeryAccNo}"></td>
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.primeryAccNo}</label></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Other Account No </td>
                                            <td><input type="text" name="otherAccNo1"  maxlength= "12" value="${bulkBean.otherAccNo1}"></td>
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.otherAccNo1}</label></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Card Number</td>
                                            <td><input type="text" name="cardNumber"  maxlength="16" value="${bulkBean.cardNumber}" placeholder="XXXX XXXX XXXX XXXX" ></td>
                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.cardNumber}</label></td>
                                        </tr>

                                        <tr>
                                            <td style="width: 200px;">Branch</td>

                                            <td><select name="branch" >
                                                    <option value="">--Select Branch-- </option>
                                                    <c:forEach var="branch" items="${sessionScope.SessionObject.branchNameList}">
                                                        <c:if test="${bulkBean.branch==branch.key}">
                                                            <option value="${branch.key}" selected>${branch.value}</option>
                                                        </c:if>
                                                        <c:if test="${bulkBean.branch != branch.key}">
                                                            <option value="${branch.key}" >${branch.value}</option>
                                                        </c:if>
                                                    </c:forEach>        
                                                </select>
                                            </td>

                                            <td style="width: 100px;"><label style="color: red">${invalidMsgBean.branch}</label></td>
                                        </tr>


                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br/>Signature</font></td>
                                        </tr>

                                        <tr>
                                            <td>Choose the file To Upload</td>
                                            <td><input id="control" name="signature" required="required" type="file"/></td>
                                        </tr>

                                        <tr>
                                            <td colspan="6" ><font style="color: #999"> <br/>National Identity Card</font></td>
                                        </tr>

                                        <tr>
                                            <td>Choose the file To Upload</td>
                                            <td><input id="control2" name="nic" type="file" required="required"/></td> 
                                        </tr>

                                    </table>

                                    <table cellpadding="0" cellspacing="10" >
                                        <tr >
                                            <td style="width: 200px;">
                                            </td>
                                            <td><input type="submit" name="save" id="save" style="width: 100px;" value="Save" onclick="invokeSave()"  >
                                                <input type="button" name="reset"  style="width: 100px;" onclick="invokeResetAll()" value="Reset">
                                            </td>
                                        </tr>
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
