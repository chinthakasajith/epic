/* This script and many more are available free online at
The JavaScript Source!! http://javascript.internet.com
Created by: Benoit Asselin | http://www.ab-d.fr */

function rotate(p_deg) {
	if(document.getElementById('canvass')) {
		
		var image = document.getElementById('image');
		var canvas = document.getElementById('canvass');
		var canvasContext = canvas.getContext('2d');
		
		switch(p_deg) {
			default :
			case 0 :
				canvas.setAttribute('width', image.width);
				canvas.setAttribute('height', image.height);
				canvasContext.rotate(p_deg * Math.PI / 180);
				canvasContext.drawImage(image, 0, 0);
				break;
			case 90 :
				canvas.setAttribute('width', image.height);
				canvas.setAttribute('height', image.width);
				canvasContext.rotate(p_deg * Math.PI / 180);
				canvasContext.drawImage(image, 0, -image.height);
				break;
			case 180 :
				canvas.setAttribute('width', image.width);
				canvas.setAttribute('height', image.height);
				canvasContext.rotate(p_deg * Math.PI / 180);
				canvasContext.drawImage(image, -image.width, -image.height);
				break;
			case 270 :
			case -90 :
				canvas.setAttribute('width', image.height);
				canvas.setAttribute('height', image.width);
				canvasContext.rotate(p_deg * Math.PI / 180);
				canvasContext.drawImage(image, -image.width, 0);
				break;
		};
		
	} else {
		
		var image = document.getElementById('image');
		switch(p_deg) {
			default :
			case 0 :
				image.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=0)';
				break;
			case 90 :
				image.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=1)';
				break;
			case 180 :
				image.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=2)';
				break;
			case 270 :
			case -90 :
				image.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=3)';
				break;
		};
		
	};
};

// Multiple onload function created by: Simon Willison
// http://simonwillison.net/2004/May/26/addLoadEvent/
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}

addLoadEvent(function() {
	var image = document.getElementById('image');
	var canvas = document.getElementById('canvass');
	if(canvas.getContext) {
		image.style.visibility = 'hidden';
		image.style.position = 'absolute';
	} else {
		canvas.parentNode.removeChild(canvas);
	};
	
	rotate(0);
});

