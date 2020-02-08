/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kushan_j
 * @date 22-06-2010
 * @Description javascript for listbox assigning...
 */

function moveout0()
{
	var sda = document.getElementById('in0');
	var len = sda.length;
	var sda1 = document.getElementById('out0');
	for(var j=0; j<len; j++)
	{
		if(sda[j].selected)
		{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
		}
	}
}

function movein0()
{
	var sda = document.getElementById('in0');
	var sda1 = document.getElementById('out0');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

		}
	}
}

function moveallin0()
{
	var sda = document.getElementById('in0');
	var sda1 = document.getElementById('out0');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

	}
}

function moveallout0()
{
	var sda = document.getElementById('in0');
	var len = sda.length;
	var sda1 = document.getElementById('out0');
	for(var j=0; j<len; j++)
	{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
	}
}

function moveout()
{
   
	var sda = document.getElementById('in');
	var len = sda.length;
	var sda1 = document.getElementById('out');
	for(var j=0; j<len; j++)
	{
		if(sda[j].selected)
		{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
		}
	}
}

function movein()
{
	var sda = document.getElementById('in');
	var sda1 = document.getElementById('out');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

		}
	}
}

function moveallin()
{
	var sda = document.getElementById('in');
	var sda1 = document.getElementById('out');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

	}
}

function moveallout()
{
	var sda = document.getElementById('in');
	var len = sda.length;
	var sda1 = document.getElementById('out');
	for(var j=0; j<len; j++)
	{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
	}
}

///////////////////////when two assign process in same jsp

function moveout2()
{
	var sda = document.getElementById('in2');
	var len = sda.length;
	var sda1 = document.getElementById('out2');
	for(var j=0; j<len; j++)
	{
		if(sda[j].selected)
		{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
		}
	}
}

function movein2()
{
	var sda = document.getElementById('in2');
	var sda1 = document.getElementById('out2');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

		}
	}
}

function moveallin2()
{
	var sda = document.getElementById('in2');
	var sda1 = document.getElementById('out2');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

	}
}

function moveallout2()
{
	var sda = document.getElementById('in2');
	var len = sda.length;
	var sda1 = document.getElementById('out2');
	for(var j=0; j<len; j++)
	{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
	}
}
/////////////////////////////////////////
function moveout3()
{
	var sda = document.getElementById('in3');
	var len = sda.length;
	var sda1 = document.getElementById('out3');
	for(var j=0; j<len; j++)
	{
		if(sda[j].selected)
		{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
		}
	}
}

function movein3()
{
	var sda = document.getElementById('in3');
	var sda1 = document.getElementById('out3');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

		}
	}
}

function moveallin3()
{
	var sda = document.getElementById('in3');
	var sda1 = document.getElementById('out3');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

	}
}

function moveallout3()
{
	var sda = document.getElementById('in3');
	var len = sda.length;
	var sda1 = document.getElementById('out3');
	for(var j=0; j<len; j++)
	{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
	}
}

/////////////////////////////////////////
function moveout4()
{
	var sda = document.getElementById('in4');
	var len = sda.length;
	var sda1 = document.getElementById('out4');
	for(var j=0; j<len; j++)
	{
		if(sda[j].selected)
		{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
		}
	}
}

function movein4()
{
	var sda = document.getElementById('in4');
	var sda1 = document.getElementById('out4');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

		}
	}
}

function moveallin4()
{
	var sda = document.getElementById('in4');
	var sda1 = document.getElementById('out4');
	var len = sda1.length;
	for(var j=0; j<len; j++)
	{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			sda1.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{
			sda.add(y,null);}
			catch(ex){
			sda.add(y);
			}

	}
}

function moveallout4()
{
	var sda = document.getElementById('in4');
	var len = sda.length;
	var sda1 = document.getElementById('out4');
	for(var j=0; j<len; j++)
	{
			var tmp = sda.options[j].text;
			var tmp1 = sda.options[j].value;
			sda.remove(j);
			j--;
			var y=document.createElement('option');
			y.text=tmp;
                        y.value=tmp1;
			try
			{sda1.add(y,null);
			}
			catch(ex)
			{
			sda1.add(y);
			}
	}
}