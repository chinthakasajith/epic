<%-- 
    Document   : viewcardkeymanagment
    Created on : Jan 18, 2013, 4:20:22 PM
    Author     : asela
--%>


<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <title>EPIC_CMS_HOME</title>
        <script  type="text/javascript" charset="utf-8">
          
            function invokeGoBack()
            {
                
                window.location = "${pageContext.request.contextPath}/LoadCardKeyServlet";
                
            }
            
        </script>   
        <script> 
             
            
            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                    var oTable = $('#example').dataTable({
                        "bJQueryUI" : true,
                        "sPaginationType" :"full_numbers"
                    });
                } );

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_KEY_MANAGMENT%>'                                  
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
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">




                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Default view  ///////////////////////////////////////////////////////////-->

                                <div class="selector" id="tabs">
                                    <ul>
                                        <c:if test="${profilebean.pvk == '1'}">
                                            <li><a href="#tabs-1">PVK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.cvk1 == '1'}">                                        
                                            <li><a href="#tabs-2">CVK1 </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.cvk2 == '1'}">                                         
                                            <li><a href="#tabs-3">CVK2 </a></li> 
                                        </c:if>
                                        <c:if test="${profilebean.ICVK == '1'}">                                         
                                            <li><a href="#tabs-4">ICVK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.CSCK == '1'}"> 
                                            <li><a href="#tabs-5">CSCK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.IMKAC == '1'}"> 
                                            <li><a href="#tabs-6">IMKAC </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.ZCMK == '1'}"> 
                                            <li><a href="#tabs-7">ZCMK </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.IMKSMI == '1'}"> 
                                            <li><a href="#tabs-8">IMKSMI </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.IMKSMC == '1'}"> 
                                            <li><a href="#tabs-9">IMKSMC </a></li>
                                        </c:if>
                                        <c:if test="${profilebean.PPK == '1'}"> 
                                            <li><a href="#tabs-10">PPK </a></li>
                                        </c:if>


                                        <!--                                        <li><a href="#tabs-5">POS Verification Key </a></li>-->

                                    </ul>
                                    <c:if test="${profilebean.pvk == '1'}">                                    
                                        <div id="tabs-1" >
                                            <c:if test="${profilebean.isPVKpair == '1'}">
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PVKA Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="pvka" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">





                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="error"></text></b></font>
                                                                <font color="green"><b><text id="success"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>




                                                    <form name="pvkPanelForm" style="padding: 20px;" id="pvkaID">

                                                        <table>
                                                            <tr>
                                                                <td>PVKA</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="pvka" value="${keybean.pvka}" disabled="" id="pvkaEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.pvkaKVC}" id="pvkakvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PVKB Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                                <div id="pvkb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="pvkPanelForm" style="padding: 20px;" id="pvkbID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorpvkb"></text></b></font>
                                                                    <font color="green"><b><text id="successpvkb"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>PVKB</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="pvkb" value="${keybean.pvkb}" disabled="" id="pvkbEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.pvkbKVC}" id="pvkbkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>



                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isPVKpair == '0'}">                                                
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PVK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="pvk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="pvkPanelForm" style="padding: 20px;" id="pvkID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorpvk"></text></b></font>
                                                                    <font color="green"><b><text id="successpvk"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>PVK</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="pvk" value="${keybean.pvk}" disabled="" id="pvkEnable" maxlength="32" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.pvkKVC}" id="pvkkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>
                                            </c:if>
                                        </div>
                                    </c:if>                 
                                    <!-- end of tab 1 -->
                                    <c:if test="${profilebean.cvk1 == '1'}">
                                        <div id="tabs-2" >
                                            <c:if test="${profilebean.isCVK1Pair == '1'}">                                           
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK1A Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk1a" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="cvk1PanelForm" style="padding: 20px;" id="cvk1aID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk1a"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk1a"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK1-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk1a" value="${keybean.cvk1a}" disabled="" id="cvk1aEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1aKVC}" id="cvk1akvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK1B Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk1b" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk1PanelForm" style="padding: 20px;" id="cvk1bID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk1b"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk1b"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK1-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk1b" value="${keybean.cvk1b}" disabled="" id="cvk1bEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1bKVC}" id="cvk1bkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isCVK1Pair == '0'}"> 
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK1 Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk1" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk1PanelForm" style="padding: 20px;" id="cvk1ID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk1"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk1"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK1</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk1" value="${keybean.cvk1}" disabled="" id="cvk1Enable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1KVC}" id="cvk1kvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.cvk2 == '1'}"> 
                                        <div id="tabs-3" >
                                            <c:if test="${profilebean.isCVK2pair == '1'}">                                            
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK2A Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                        
                                                <div id="cvk2a" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="cvk2PanelForm" style="padding: 20px;" id="cvk2aID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk2a"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk2a"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK2-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk2a" value="${keybean.cvk2a}" disabled="" id="cvk2aEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk2aKVC}" id="cvk2akvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK2B Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk2b" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk2PanelForm" style="padding: 20px;" id="cvk2bID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk2b"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk2b"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK2-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk2b" value="${keybean.cvk2b}" disabled="" id="cvk2bEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk1bKVC}" id="cvk2bkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isCVK2pair == '0'}">                                                 
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CVK2 Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="cvk2" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="cvk2PanelForm" style="padding: 20px;" id="cvk2ID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcvk2"></text></b></font>
                                                                    <font color="green"><b><text id="successcvk2"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CVK2</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cvk2" value="${keybean.cvk2}" disabled="" id="cvk2Enable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.cvk2KVC}" id="cvk2kvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.ICVK == '1'}">                                      
                                        <div id="tabs-4" >
                                            <c:if test="${profilebean.isICVKpair == '1'}">                                            
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ICVKA Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                        
                                                <div id="icvka" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="icvkPanelForm" style="padding: 20px;" id="icvkaID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="erroricvka"></text></b></font>
                                                                    <font color="green"><b><text id="successicvka"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>ICVK-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="icvka" value="${keybean.icvka}" disabled="" id="icvkaEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.icvkaKVC}" id="icvkakvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ICVKB Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="icvkb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="icvkbPanelForm" style="padding: 20px;" id="icvkbID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="erroricvkb"></text></b></font>
                                                                    <font color="green"><b><text id="successicvkb"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>ICVK-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="icvkb" value="${keybean.icvkb}" disabled="" id="icvkbEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.icvkbKVC}" id="icvkbkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isICVKpair == '0'}">                                                
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ICVK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="icvk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="icvkPanelForm" style="padding: 20px;" id="icvkID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="erroricvk"></text></b></font>
                                                                    <font color="green"><b><text id="successicvk"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>ICVK</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="icvk" value="${keybean.icvk}" disabled="" id="icvkEnable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.icvkKVC}" id="icvkkvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>

                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.CSCK == '1'}">
                                        <div id="tabs-5" >
                                            <c:if test="${profilebean.isCSCKpair == '1'}">                                            
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CSCKA Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                     
                                                <div id="cscka" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                    <form name="csckPanelForm" style="padding: 20px;" id="csckaID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcscka"></text></b></font>
                                                                    <font color="green"><b><text id="successcscka"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CSCK-A</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="cscka" value="${keybean.cscka}" disabled="" id="csckaEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.csckaKVC}" id="csckakvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>

                                                <br/></br>

                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CSCKB Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="csckb" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="csckbPanelForm" style="padding: 20px;" id="csckbID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcsckb"></text></b></font>
                                                                    <font color="green"><b><text id="successcsckb"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CSCK-B</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="csckb" value="${keybean.csckb}" disabled="" id="csckbEnable" maxlength="16" size="42"></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.csckbKVC}" id="csckbkvc" disabled="" size="6"></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>









                                                <br/></br>
                                            </c:if>
                                            <c:if test="${profilebean.isCSCKpair == '0'}">                                                
                                                <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > CSCK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                                <div id="csck" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">




                                                    <form name="csckPanelForm" style="padding: 20px;" id="csckID">
                                                        <table>
                                                            <tr>
                                                                <td colspan="3"><font color="#FF0000"><b><text id="errorcsck"></text></b></font>
                                                                    <font color="green"><b><text id="successcsck"></text></b></font>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table>
                                                            <tr>
                                                                <td>CSCK</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="csck" value="${keybean.csck}" disabled="" id="csckEnable" maxlength="32" size="42"/></td>
                                                                <td style="width: 15px;"></td>
                                                                <td>KVC</td>
                                                                <td style="width: 15px;"></td>
                                                                <td><input type="text" name="" value="${keybean.csckKVC}" id="csckkvc" disabled="" size="6"/></td>
                                                            </tr>
                                                            <tr style="height: 20px;"></tr>


                                                        </table>


                                                    </form>


                                                </div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.IMKAC == '1'}">                                    
                                        <div id="tabs-6" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IMKAC Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                    
                                            <div id="imkac" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                <form name="imkacPanelForm" style="padding: 20px;" id="imkacID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorimkac"></text></b></font>
                                                                <font color="green"><b><text id="successimkac"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>IMKAC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="imkac" value="${keybean.imkac}" disabled="" id="imkacEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.imkacKVC}" id="imkackvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>


                                                </form>


                                            </div>

                                        </div>
                                    </c:if>
                                    <c:if test="${profilebean.ZCMK == '1'}"> 
                                        <div id="tabs-7" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > ZCMK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                
                                            <div id="zcmk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                <form name="zcmkPanelForm" style="padding: 20px;" id="zcmkID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorzcmk"></text></b></font>
                                                                <font color="green"><b><text id="successzcmk"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>ZCMK</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="zcmk" value="${keybean.zcmk}" disabled="" id="zcmkEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.zcmkKVC}" id="zcmkkvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>


                                                </form>


                                            </div>

                                        </div> 
                                    </c:if>
                                    <c:if test="${profilebean.IMKSMI == '1'}">                                    
                                        <div id="tabs-8" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IMKSMI Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                        
                                            <div id="imksmi" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                <form name="imksmiPanelForm" style="padding: 20px;" id="imksmiID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorimksmi"></text></b></font>
                                                                <font color="green"><b><text id="successimksmi"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>IMKSMI</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="imksmi" value="${keybean.imksmi}" disabled="" id="imksmiEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.imksmiKVC}" id="imksmikvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>


                                                </form>


                                            </div>

                                        </div> 
                                    </c:if>
                                    <c:if test="${profilebean.IMKSMC == '1'}"> 
                                        <div id="tabs-9" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > IMKSMC Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                                  
                                            <div id="imksmc" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                <form name="imksmcPanelForm" style="padding: 20px;" id="imksmcID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorimksmc"></text></b></font>
                                                                <font color="green"><b><text id="successimksmc"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>IMKSMC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="imksmc" value="${keybean.imksmc}" disabled="" id="imksmcEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.imksmcKVC}" id="imksmckvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>


                                                </form>


                                            </div>

                                        </div> 
                                    </c:if>
                                    <c:if test="${profilebean.PPK == '1'}">
                                        <div id="tabs-10" >
                                            <table> <tr> <td style="width: 250px"></td><td style="text-align: right" > <font style="font-size:medium; color: #999" > PPK Panel</td> </tr><tr> <td>&nbsp;</td> </tr></table>                     
                                            <div id="ppk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-top:  30px;">




                                                <form name="ppkPanelForm" style="padding: 20px;" id="ppkID">
                                                    <table>
                                                        <tr>
                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorppk"></text></b></font>
                                                                <font color="green"><b><text id="successppk"></text></b></font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table>
                                                        <tr>
                                                            <td>PPK</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="ppk" value="${keybean.ppk}" disabled="" id="ppkEnable" maxlength="32" size="42"></td>
                                                            <td style="width: 15px;"></td>
                                                            <td>KVC</td>
                                                            <td style="width: 15px;"></td>
                                                            <td><input type="text" name="" value="${keybean.ppkKVC}" id="ppkkvc" disabled="" size="6"></td>
                                                        </tr>
                                                        <tr style="height: 20px;"></tr>


                                                    </table>


                                                </form>


                                            </div>

                                        </div> 
                                    </c:if>
                                    <!--                                    <div id="tabs-5" >
                                                                            <br/></br>
                                                                            <div id="ppk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">
                                                                                <form name="ppkPanelForm" style="padding: 20px;" id="ppkID">
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td colspan="3"><font color="#FF0000"><b><text id="errorppk"></text></b></font>
                                                                                                <font color="green"><b><text id="successppk"></text></b></font>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                    
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td>PPK</td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td><input type="text" name="ppk" value="${keyDetails.ppk}" disabled="" id="ppkEnable" maxlength="32" size="42"></td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td>KVC</td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td><input type="text" name="" value="${keyDetails.ppkKVC}" id="ppkkvc" disabled="" size="6"></td>
                                                                                        </tr>
                                                                                        <tr style="height: 20px;"></tr>
                                    
                                    
                                                                                    </table>
                                    
                                                                                    <table>
                                                                                        <tr>
                                    
                                                                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enablePPK()"></td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="ppkUpdate" disabled="" onclick="invokeUpdatePPK()"></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form>
                                                                            </div>    
                                    
                                                                            <br/></br>
                                                                            <div id="tpk" class="outset" style="border-style:outset;  background-color: #E0E0E0 ; border-color: #999; width: 600px; margin-left: 90px; margin-bottom: 30px;">
                                                                                <form name="tpkPanelForm" style="padding: 20px;" id="tpkID">
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td colspan="3"><font color="#FF0000"><b><text id="errortpk"></text></b></font>
                                                                                                <font color="green"><b><text id="successtpk"></text></b></font>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                    
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td>TPK</td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td><input type="text" name="tpk" value="${keyDetails.tpk}" disabled="" id="tpkEnable" maxlength="32" size="42"></td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td>KVC</td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td><input type="text" name="" value="${keyDetails.tpkKVC}" id="tpkkvc" disabled="" size="6"></td>
                                                                                        </tr>
                                                                                        <tr style="height: 20px;"></tr>
                                    
                                    
                                                                                    </table>
                                    
                                                                                    <table>
                                                                                        <tr>
                                    
                                                                                            <td><input type="button" value="Edit" name="edit" style="width: 100px;" onclick="enableTPK()"></td>
                                                                                            <td style="width: 15px;"></td>
                                                                                            <td><input type="button" value="Update" name="edit" style="width: 100px;" id="tpkUpdate" disabled="" onclick="invokeUpdateTPK()"></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form>
                                                                            </div>
                                    
                                    
                                                                        </div>-->

                                    <!--   ------------------------- end developer area  --------------------------------                      -->

                                </div>
                                <table>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td><input type="button" name="back" value="Back" onclick="invokeGoBack()" style="width: 100px"/></td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </div>
                    <div class="clearer"><span></span></div>
                </div>

            </div>
            <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

