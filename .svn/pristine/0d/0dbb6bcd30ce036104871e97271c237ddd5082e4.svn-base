<%-- 
    Document   : cardtemplateview
    Created on : Jan 3, 2013, 12:13:32 PM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            
            function fromDateValidation(oldDate)
            {      
                newdate = addcardform1.elements["validFrom"].value;
                today = new Date();
                yesterday = new Date(today.getTime()-(24*60*60*1000));
               
                if(Date.parse(newdate)<Date.parse(yesterday)){
                    alert("Enter Valid Date");
                    addcardform1.elements["validFrom"].value = oldDate;
                }
        
                if(Date.parse(newdate)<Date.parse(oldDate)){
                    alert("Enter Valid Date");
                    addcardform1.elements["validFrom"].value = oldDate; 
                }
            }
            
            function toDateValidation(oldDate)
            {
                newdate = addcardform1.elements["validTo"].value;
                today = new Date();
                yesterday = new Date(today.getTime()-(24*60*60*1000));
               
                if(Date.parse(newdate)<Date.parse(yesterday)){
                    alert("Enter Valid Date");
                    addcardform1.elements["validTo"].value = oldDate;
                }
                if(Date.parse(newdate)>Date.parse(oldDate)){
                    alert("Enter Valid Date");
                    addcardform1.elements["validTo"].value = oldDate;      
                }       
            }
             
            function invokeAssignDetails()
            {
                document.addcardform1.action="${pageContext.request.contextPath}/GetDetailsByAccountTemplateServlet?operation=add";
                document.addcardform1.submit();
            }

            function invokeAdd()
            {

                document.addcardform1.action="${pageContext.request.contextPath}/AddCardTemplateProfileServlet";
                document.addcardform1.submit();

            }
            
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardTemplateMgtServlet";

            }
            
            function saveAndNext()
            {

                document.addcardform1.action="${pageContext.request.contextPath}/AddCardTemplateServlet";
                document.addcardform1.submit();

            }
            function invokeReset(ele)
            {
                
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
                
                document.getElementById('errorMsg').innerHTML = '';
                document.getElementById('successMsg').innerHTML = '';
            }
            
             function loadCurrency(){
                $('#currencytype').empty();
                var sval=$("#producttype").val();
                $.getJSON("${pageContext.servletContext.contextPath}/LoadOnChangeCurrencyServlet", 
                { producttype : sval},
                function(jsonobject) {
                    $.each(jsonobject.combo1, function(code, description) {
                        $('#currencytype').append(
                        $('<option></option>').val(code).html(description)
                    );
                    });
                });
              
            }
         

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDTEMPLATEHOME%>'                                  
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



        <title>EPIC CMS CARD TEMPLATE MANAGEMENT</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <!--                                <div class="selector" id="tabs">-->
                                <!--                                    <ul>
                                                                        <li><a href="#tabs-1">General</a></li>
                                                                        <li><a href="#tabs-2">Profile</a></li>
                                                                             <li><a href="#tabs-3">Card Authentication</a></li>
                                                                                   <li><a href="#tabs-4">Embossing</a></li>        
                                                                    </ul>-->

                                <!-- -----------------------------General details start---------------------------------------->



                                <form method="POST" action="" name="viewform">
                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>  
                                            <tr>
                                                <td style="width: 200px;">Template Code</td>
                                                <td>:</td>
                                                <td>${templateBean.templateCode}</td>

                                                <td >Template Name</td>
                                                <td>:</td>
                                                <td>${templateBean.templateName}
                                                </td>
                                            </tr>   

                                            <tr>
                                                <td>Account Template Name</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.accountTempDes}
                                                </td>                                                
                                            </tr>

                                            <tr>
                                                <td>Valid From</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.validFrom}
                                                </td>

                                                <td >Valid To</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.validTo}
                                                </td>
                                            </tr>

                                            <tr>
                                                <td >Total Credit Limit</td>
                                                <td>:</td>
                                                <td>${templateBean.totalCreditLimit}</td>

                                                <td >Staff Status</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.staffStatus}
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Product Type</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.productCodeDes}</td>

                                                <td style="width: 200px;">Fee Profile</td>
                                                <td>:</td>
                                                <td>                                                    
                                                    ${templateBean.feeProfDes}</td>
                                            </tr>

                                            <tr>
                                                <td >Currency Type</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.currencyDes}
                                                </td>

                                                <td>Risk Profile</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.riskProfDes}
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Service Code</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.serviceCodeDes}
                                                </td>

                                                <td>Transaction Profile</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.txnProfDes}</td>
                                            </tr>

                                            <tr>
                                                <td >Expiry Period</td>
                                                <td>:</td>
                                                <td>${templateBean.expiryPeriod}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Renew Period</td>
                                                <td>:</td>
                                                <td>${templateBean.renewRequired}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Re-Issue Threshold Period</td>
                                                <td>:</td>
                                                <td>${templateBean.reissuThrshPeriod}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td >Cash Advanced Rate</td>
                                                <td>:</td>
                                                <td>${templateBean.cashAdvanceRate}</td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td >Status</td>
                                                <td>:</td>
                                                <td>
                                                    ${templateBean.statusDes}</td>                                               
                                            </tr>                                            
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td>
                                                    <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invokeCancel()">
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>

                                    <!--                                        </div>-->
                                </form>


                                <!-- -----------------------------Bank details start---------------------------------------->      
                                <!--      <div id="tabs-3">gdgd</div>
 
 
 
                                                     <div id="tabs-4"></div>             -->

                                <!-- -----------------------------Billing details end---------------------------------------->
                                <!--                                </div>-->




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





