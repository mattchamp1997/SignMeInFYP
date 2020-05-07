<?php

    require "init.php";
	
	$classType = 'Lecture';
	$modId = '3';
	$courseCode = 'DT211';
	$room = 'KA3005';
	$signInCode = '1234';
	$lecturerID = 'C163';
	$startHr = '12';
	$startMin = '05';
	$finHr = '12';
	$finMin = '55';
	$lateHr = '12';
	$lateMin = '30';
	$year = '2020';
	$month = '04';
	$day = '20';
	
	$sql = "insert into class_details(classType,moduleID,courseCode,room,signInCode,lecID,startHr,
	startMin,finHr,finMin,lateHr,lateMin,year,month,day) VALUES ('$classType','$modId','$courseCode',
	'$room','$signInCode','$lecturerID','$startHr','$startMin','$finHr','$finMin','$lateHr','$lateMin',
	'$year','$month','$day')";
	
	$insertresult = mysqli_query($con,$sql);
	
	if($insertresult)
	{
		$response = array();
		$code = "class_created";
		$message = "Your new module was created successfully.";
					
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
		$response = array();
		$code = "class_creation_error";
		$message = "Server Error. Try again";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);
?>