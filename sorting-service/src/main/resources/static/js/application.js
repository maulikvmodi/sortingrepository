/**
 * 
 */
var randomNumberData;
$(document).ready(function () {
	$('#txtInput').val('');
	$.ajax({
		url: "/previousValues",
		type: "GET",
		success: function(data){
			if(data != null){
				var prevObj = JSON.parse(data);
				$('#prevGenTd').text(prevObj.original);
				$('#prevSortedTd').text(prevObj.sorted);
			}
			
		}
	});
	
	//$('#btnSort').hide();
	$('#generateRandom').click(function(e){
		console.log('button clicked');
		$.ajax({
			url: "getRandomNumbers",
			type: "GET"	,
			success : function(data){
				$('#divGeneratedNumbers').text(data);
				//randomNumberData = data;
				$('#btnSort').show();
			}
		});
	});
	
	$('#btnSort').click(function(e){
		console.log('sort clicked');
		var inputVal = $('#txtInput').val();
		
		if(inputVal !== ""){
			inputVal = inputVal.split(" ");
			//randomNumberData = JSON.stringify(inputVal);
			$.ajax({
				url: "sortRandomNumbers",
				type: "POST",
				data:{randomObj: JSON.stringify(inputVal)},
				success : function(data){
					var sortedObj = JSON.parse(data);
					
					$('<div>').css({'word-wrap':'break-word'}).text('Sorted Values : ' + sortedObj.value).appendTo('.form');
					$('<div>').css({'word-wrap':'break-word'}).text('Time Taken to Sort: ' + sortedObj.time).appendTo('.form');
					$('<div>').css({'word-wrap':'break-word'}).text('Iterations done: ' + sortedObj.itr).appendTo('.form');
				}
			});
		}
	});
	
});