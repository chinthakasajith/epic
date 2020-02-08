<%-- 
    Document   : bulkcardview
    Created on : Jan 3, 2013, 11:04:49 AM
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
            
            
            function invokeAssignDetails()
            {
                document.debitaddcardform.action="${pageContext.request.contextPath}/GetDetailsByDebitAccountTempServlet?operation=add";
                document.debitaddcardform.submit();
            }

            function invokeAdd()
            {

                document.debitaddcardform.action="${pageContext.request.contextPath}/AddDebitCardTemplateServlet";
                document.debitaddcardform.submit();

            }
            
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadDebitCardTemplateMgtServlet";

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
    
                ele['cashAdvanceRate'].value='0';
    
            }

            

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.DEBITCARDHOME%>'                                  
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



        <title>EPIC CMS DEBIT CARD TEMPLATE MANAGEMENT</title>
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

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>



                                <form method="POST" action="" name="debitaddcardform">

                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>                                            
                                            <tr>
                                                <td>Template Code</td>
                                                <td>${templateBean.templateCode}</td>
                                                <td></td>                                            
                                                <td>Template Name</td>
                                                <td>${templateBean.templateName} </td>                                                
                                            </tr>
                                            <tr>
                                                <td>Valid From</td>
                                                <td>${templateBean.validFrom}</td> 
                                                <td></td>
                                                <td>Valid To</td>
                                                <td>${templateBean.validTo}</td>
                                            </tr>                                           

                                            <tr>
                                                <td>Account Template </td>
                                                <td>${templateBean.accTempName}</td>
                                                <td></td>                                            
                                                <td >Card Domain</td>
                                                <td>${templateBean.cardDomainDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Type</td>
                                                <td>${templateBean.cardTypeDes}</td>
                                                <td></td>
                                                <td>Card Product</td>
                                                <td>${templateBean.productDes}</td>
                                            </tr>
                                            <tr>   

                                                <td>Currency Type </td>
                                                <td>${templateBean.currencyDes}</td>
                                                <td></td>

                                                <td>Service Code</td>
                                                <td>
                                                    ${templateBean.serviceCodeDes}</td>
                                            </tr>
                                            <tr>  

                                                <td >Cash Advanced Rate</td>
                                                <td>${templateBean.cashAdvanceRate}</td>
                                                <td></td>
                                                <td>Expiry Period</td>
                                                <td>${templateBean.expPeriod}</td>
                                            </tr>
                                            <tr>    

                                                <td>Fee Profile</td>
                                                <td>${templateBean.feeProfDes}</td>
                                                <td></td>
                                                <td>Renew Period</td>
                                                <td>${templateBean.renewPeriod}</td>
                                            </tr>
                                            <tr>   

                                                <td>Risk Profile</td>
                                                <td>${templateBean.riskProfDes}</td>
                                                <td></td>
                                                <td >Re-Issue Threshold Period</td>
                                                <td>${templateBean.reissueThreshPeriod}</td>
                                            </tr> 
                                            <tr>

                                                <td>Transaction Profile</td>
                                                <td>${templateBean.txnProfDes}</td>
                                                <td></td>
                                                <td>Status </td>                                                
                                                <td>${templateBean.statusDes}</td>

                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/>
                                                </td>

                                            </tr>
                                        </tbody>

                                    </table>
                                </form>
                            </div>                           
                        </div>




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
