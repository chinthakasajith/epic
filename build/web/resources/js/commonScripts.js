function loadContainer(url){
    $('#containerDiv').load(url);
    
}


function showStep(step){
$(step).show();
}

function hideStep(step){
$(step).hide();
}



function showMessage(){
hideStep('#Step1');
hideStep('#Step2');
showStep('#msgDiv');
}




function loadDocketBody(url){

$('#docLoader').load(url);
}






function showPopup(){
$('#popDiv').show();
}

function hidePopUp(){
$('#popDiv').fadeOut('slow');
}