<%-- 
    Document   : carddomaintemplateview
    Created on : Jan 2, 2013, 4:43:18 PM
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
            
      
            function invokeAdd()
            {

                document.addcarddomainform.action="${pageContext.request.contextPath}/AddCardDomainServlet";
                document.addcarddomainform.submit();

            }
            
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardDomainTempMgtServlet";

            }
            
            //            function invokeReset(){
            //            
            //                window.location = "${pageContext.request.contextPath}/LoadAddCardDomainFormServlet";
            //            }
            //            
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


        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDDOMAINHOME%>'                                  
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


        <title>EPIC CMS CARD DOMAIN TEMPLATE MANAGEMENT</title>
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

                                <form method="POST" action="" name="addcarddomainform">
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
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.templateCode}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Template Name</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.templateName}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Customer Template</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.custemplateDes}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Valid From</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.validFrom}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Valid To</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.validTo}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Card Domain</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.cardDomainDes}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Card Type</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.cardTypeDes}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Currency Type</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.currencyDes}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Risk Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.riskProfDes}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Transaction Profile</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.txnProfDes}</td>                                                
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td></td>
                                                <td>:</td>
                                                <td>${templateBean.statusDes}</td>                                                
                                            </tr>


                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td>                                                    
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/></td>

                                            </tr>
                                        </tbody>
                                    </table>
                                </form>

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

