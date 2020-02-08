<%-- 
    Document   : content
    Created on : Jan 27, 2012, 12:31:36 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="${pageContext.request.contextPath}/resources/css/tablecss/jquery-ui-1.8.2.custom_1.css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-ui-1.8.2.custom.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddaccordion.js"></script>

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/calender/js/calendar.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/calender/js/calendar-setup.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/calender/js/calendar-en.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/calender/css/calendar-white.css" type="text/css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/assigninglistbox.js"></script>
        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/tablecss/demo_table_jui.css";


        </style>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/tablejs/KeyTable.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.colorbox.js"></script>


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/colorbox.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mainstyle.css" media="screen"/>

        <script  type="text/javascript" charset="utf-8">




            ddaccordion.init({
                headerclass: "submenuheader", //Shared CSS class name of headers group
                contentclass: "submenu", //Shared CSS class name of contents group
                revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
                mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
                collapseprev: true, //Collapse previous content (so only one open at any time)? true/false
                defaultexpanded: [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
                onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
                animatedefault: false, //Should contents open by default be animated into view?
                persiststate: true, //persist state of opened contents within browser session?
                toggleclass: ["", ""], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
                togglehtml: ["suffix", "<img src='${pageContext.request.contextPath}/resources/images/plus.gif' class='statusicon' />", "<img src='${pageContext.request.contextPath}/resources/images/minus.gif' class='statusicon' />"], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
                animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
                oninit: function (headers, expandedindices) { //custom code to run when headers have initalized

                },
                onopenclose: function (header, index, state, isuseractivated) { //custom code to run whenever a header is opened or closed
                }
            })







            $(document).ready(function ()
            {





                var oTable = $('#tableview').dataTable({
                    "bJQueryUI": true,
                    "sScrollX": "100%",
                    "sScrollXInner": "100%",
                    "bAutoWidth": false,
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });

                var oTable = $('#tableview2').dataTable({
                    "bJQueryUI": true,
                    "sScrollX": "100%",
                    "sScrollXInner": "100%",
                    "bAutoWidth": true,
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#tableview3').dataTable({
                    "bJQueryUI": true,
                    "sScrollX": "100%",
                    "sScrollXInner": "100%",
                    "bAutoWidth": true,
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#scoreltableview').dataTable({
                    "bJQueryUI": true,
                    "sScrollY": "100%",
                    "sScrollX": "100%", //100
                    "sScrollXInner": "120%", //130
                    "bAutoWidth": true, //false         
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#scoreltableview2').dataTable({
                    "bJQueryUI": true,
                    "sScrollY": "100%",
                    "sScrollX": "100%", //100
                    "sScrollXInner": "140%", //130
                    "bAutoWidth": true, //false         
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#scoreltableview3').dataTable({
                    "bJQueryUI": true,
                    "sScrollY": "100%",
                    "sScrollX": "100%", //100
                    "sScrollXInner": "160%", //130
                    "bAutoWidth": true, //false         
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#scoreltableview4').dataTable({
                    "bJQueryUI": true,
                    "sScrollY": "100%",
                    "sScrollX": "100%", //100
                    "sScrollXInner": "180%", //130
                    "bAutoWidth": true, //false         
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });
                var oTable = $('#scoreltableview5').dataTable({
                    "bJQueryUI": true,
                    "sScrollY": "100%",
                    "sScrollX": "100%", //100
                    "sScrollXInner": "200%", //130
                    "bAutoWidth": true, //false         
                    "bScrollCollapse": true,
                    "sPaginationType": "full_numbers"
                });


            });



            function invokeHistory(value) {

                $.post("${pageContext.request.contextPath}/ViewSystemUserPageAuditHistory", {id: value},
                function (data) {
                    if (data == "success") {

                        $.colorbox({href: "${pageContext.request.contextPath}/administrator/controlpanel/systemusermgt/userpageaudittraceview.jsp", iframe: true, height: "80%", width: "80%", overlayClose: false});
                    }

                    else if (data == "session") {

                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                                    }
                                    else {
                                        alert("error on loading data.");
                                    }

                                });




                                //                                document.search.action="${pageContext.request.contextPath}/ViewSystemUserPageAuditHistory";
                                //                                document.search.submit();

                            }



                            $(window).load(function () {

                                var imgs = $("img");
                                $(imgs).each(function (index) {
                                    if ($(this).hasClass("ui-datepicker-trigger")) {
                                        $(this).addClass("datePickerAlign");
                                    }
                                });
                            });



        </script>
        <script language="javascript">


            $(function () {

                $("#tabs").tabs();

                $(".selector").tabs({selected: ${selectedtab}});
                $(".nexttab").click(function () {
                    var selected = $("#tabs").tabs("option", "selected");
                    $("#tabs").tabs("option", "selected", selected + 1);
                });
                $(".previoustab").click(function () {
                    var selected = $("#tabs").tabs("option", "selected");
                    $("#tabs").tabs("option", "selected", selected - 1);
                });
                $("#tabs").tabs("option", "disabled", [${disableTabIndex}]);
            });


            function allowOnlyNumbers(event) {
                if (event.which != 8 && isNaN(String.fromCharCode(event.which))) {
                    event.preventDefault(); //stop character from entering input
                }
            }

            function convertInputToUpper(protectedValues) {

                var protectedValuesArray = protectedValues.split(',');

                $("input").blur(function () {

                    if ($.inArray($(this).attr('name'), protectedValuesArray) == -1) {
                        $(this).val($(this).val().toUpperCase());
                    }

                });
            }

        </script>
        <!--    put all the select tags in the page to readonly
                usage: setReadonly()-->
        <script language="javascript">
            function setReadonly() {
                var selects = document.getElementsByTagName('select')
                for (i = 0; i < selects.length; i++) {

                    selectElement = selects[i];

                    if (selectElement) {
                        var parent = selectElement.parentElement;
                        var textValue = selectElement.options[selectElement.options.selectedIndex].textContent;
                        if (!parent) {
                            parent = selectElement.parentNode;
                            textValue = selectElement.options[selectElement.options.selectedIndex].text;
                        }
                        var input = document.createElement("input");
                        input.setAttribute("id", selectElement.id);
                        input.setAttribute("type", "text");
                        input.setAttribute("value", textValue);
                        input.style.background = "#ffffff";
                        input.readOnly = true;
                        parent.appendChild(input);
                    }
                    selectElement.style.display = "none";
                }

            }

            //set all text and radio inputs in the page to Readonly
            function setAllTextInputToReadonly() {

                var inputs = document.getElementsByTagName('input');
                for (i = 0; i < inputs.length; i++) {
                    if (inputs[i].getAttribute('type') === 'text') {
                        inputs[i].readOnly = true;
                    }
                }

                $('input[type=radio]').click(function () {
                    return false;
                });
            }


//            function ConfirmDialogBox(dialBoxID,submitFrmID,btnName,msgBody,msgTitle)
//            { 
//                
//                var buttonsOpts = {};
//                buttonsOpts['Cancel'] = function() {$(this).dialog('close');}
//                buttonsOpts[btnName] = function() {$(submitFrmID).submit();}
//
//                $(dialBoxID).html(msgBody);
//                $(dialBoxID).dialog({
//                    resizable: false,
//                    title:msgTitle,
//                    height: 'auto',
//                    width: 500,
//                    modal: true,
//                    buttons: buttonsOpts
//                });
//
//            }
        </script>
        <!--function to allow text field only for numbers-->
        <script>
//            $(document).ready(function () {
//                //called when key is pressed in textbox
//                $("#noOfDependents").keypress(function (e) {
//                    //if the letter is not digit then display error and don't type anything
//                    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
//                        //display error message
//                        $("#errMsg").html("Invalid").show().fadeOut("slow");
//                        return false;
//                    }
//                });
//            });
            function checkIt(evt,errId) {
                evt = (evt) ? evt : window.event
                var charCode = (evt.which) ? evt.which : evt.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    //display error message
                    $("#"+errId).html("Invalid").show().fadeOut("slow");
                    return false
                }
                return true
            }
        </script>


    </head>
    <body>


    </body>
</html>
