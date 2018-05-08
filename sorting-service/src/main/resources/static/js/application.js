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
				if(prevObj.err === ""){
					$('#prevGenTd').text(prevObj.original);
					$('#prevSortedTd').text(prevObj.sorted);
				}else{
					$('#divError').text(prevObj.err);
					$('#divError').show();
				}
			}
			
		}
	});
	
	$('#btnSort').click(function(e){
		console.log('sort clicked');
		$('#divError').hide();
		var inputVal = $('#txtInput').val();
		
		if(inputVal !== ""){
			inputVal = inputVal.split(" ");
			$.ajax({
				url: "sortRandomNumbers",
				type: "POST",
				data:{randomObj: JSON.stringify(inputVal)},
				success : function(data){
					var sortedObj = JSON.parse(data);
					
					if(sortedObj.err === ''){
						$('<div>').css({'word-wrap':'break-word'}).text('Sorted Values : ' + sortedObj.value).appendTo('.form');
						$('<div>').css({'word-wrap':'break-word'}).text('Time Taken to Sort: ' + sortedObj.time).appendTo('.form');
						$('<div>').css({'word-wrap':'break-word'}).text('Iterations done: ' + sortedObj.itr).appendTo('.form');
					}else{
						$('#divError').text(sortedObj.err);
						$('#divError').show();
					}
				}
			});
		}
	});
	
});