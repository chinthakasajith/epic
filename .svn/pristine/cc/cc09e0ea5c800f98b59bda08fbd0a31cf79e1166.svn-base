<%-- 
    Document   : currencyexchangeratehome
    Created on : Jun 20, 2012, 9:35:19 AM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
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
            
            function invokeResetInUpdate(id,isDefault)
            {
                window.location = "${pageContext.request.contextPath}/LoadUpdateCurrencyExchangeRateServlet?id="+id+"isDefault"+isDefault;
            }
            
            function ConfirmDelete(value)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+value+" currency rate ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                            window.location="${pageContext.request.contextPath}/DeleteCurrencyExchangeRateServlet?id="+value;
                        }
                    }
                });

            }
            
            function invokeGoBack()
            {
                window.location = "${pageContext.request.contextPath}/LoadCurrencyExchangeRateServlet";
            }
            
            
            
            
            
            
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CURRENCY_EXCHANGE_RATE%>'                                  
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
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                
                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" name="addform" action="${pageContext.request.contextPath}/AddCurrencyExchangeRateServlet">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>                                                
                                                <tr>
                                                    <td style="width: 150px">Currency</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="currencycode" style="width: 163px">
                                                            <option value="">--Select Currency-- </option>                                                                
                                                            <c:forEach var="currency" items="${currencyList}">
                                                                <c:if test="${exchangeBean.currencyCode == currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" selected="true">${currency.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${exchangeBean.currencyCode != currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Selling Rate</td>
                                                    <td></td>
                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="sellrate" value="${exchangeBean.sellRate}" maxlength="15" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Buying Rate</td>
                                                    <td></td>
                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="buyrate" value="${exchangeBean.buyRate}" maxlength="15"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;" colspan="2">&nbsp;&nbsp;
                                                        <input type="submit" value="Add" style="width: 100px" name="add" />
<!--                                                        <input type="button" value="Reset" style="width: 100px" name="reset" onclick="invokeReset(this.form)"/>-->
<input type="button" value="Reset" style="width: 100px" name="reset" onclick="invokeGoBack()" class="defbutton"/>
                                                    </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CURRENCY_EXCHANGE_RATE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>

                                <!--                        strat update-->
                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" name="updateform" action="${pageContext.request.contextPath}/UpdateCurrencyExchangeRateServlet" >
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
                                                    <td style="width: 150px">Currency</td>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select name="currencycode" disabled="true" style="width: 163px">
                                                            <option value="">--Select Currency-- </option>                                                                
                                                            <c:forEach var="currency" items="${currencyList}">
                                                                <c:if test="${exchangeBean.currencyCode == currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" selected="true">${currency.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${exchangeBean.currencyCode != currency.currencyCode}">
                                                                    <option value="${currency.currencyCode}" >${currency.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Selling Rate</td>
                                                    <td></td>
                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="sellrate" value="${exchangeBean.sellRate}" maxlength="15" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Buying Rate</td>
                                                    <td></td>
                                                    <td>
                                                        <font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="buyrate" value="${exchangeBean.buyRate}" maxlength="15"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td colspan="3">&nbsp;&nbsp;
                                                        <input type="submit" value="Update" name="update" style="width: 100px"  />
                                                        <input type="reset" value="Reset" name="reset" style="width: 100px" onclick="invokeResetInUpdate('${exchangeBean.currencyCode}','no')" />
                                                        <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeGoBack()"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CURRENCY_EXCHANGE_RATE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='view'}" >
                                    <form>
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td>Currency</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.currencyDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Selling Rate</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.sellRate}</td>
                                                </tr>
                                                <tr>
                                                    <td>Buying Rate</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${exchangeBean.buyRate}</td>
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" name="back" value="Back" onclick="invokeGoBack()" style="width: 100px"/></td>
                                                </tr>
                                        </table>
                                    </form>
                                </c:if>
                                <!-- ----------------------------------------------------------------Start Data Table ---------------------------------------------------------------  -->            
                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Currency</th>
                                            <th>Selling Rate</th>
                                            <th>Buying Rate</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="exchange" items="${exchangeRateList}">
                                            <tr>
                                                <td >${exchange.currencyDes}</td>
                                                <td >${exchange.sellRate}</td>
                                                <td >${exchange.buyRate}</td>

                                                <td  ><a href='${pageContext.request.contextPath}/ViewCurrencyExchangeRateServlet?id=<c:out value="${exchange.currencyCode}"></c:out>'>View</a></td>
                                                <td ><a href='${pageContext.request.contextPath}/LoadUpdateCurrencyExchangeRateServlet?id=<c:out value="${exchange.currencyCode}"></c:out>'/>Update</td>
                                                <td ><a href='#' onclick="ConfirmDelete('${exchange.currencyCode}')">Delete</td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>
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
