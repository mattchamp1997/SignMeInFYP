<?php

    require "init.php";

	$fullName = $_POST["fullName"];
	$email = $_POST["email"];
	$idNum = $_POST["idNum"];
	$password = $_POST["password"];
	$accountType = $_POST["accountType"];
	$courseCode = $_POST["courseCode"];
	
	$sql = "select * from user_info where idNum = '$idNum'";
	
	$sqlresp = mysqli_query($con,$sql);
	$rowcount = mysqli_num_rows($sqlresp);
	
	//If idNumber returns a result eg: if it is already in use, run the following
	if($rowcount>0)
	{
		$response = array();
		$code = "reg_false";
		$message = "ID Number already in use.";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	//If the idNumber is not already in use, run following
	else
	{
		//Inserting registration form values into user info table in DB
		$sqlinsert = "insert into user_info values ('$fullName', '$email', '$idNum', '$password', '$accountType', '$courseCode')";
		$insertresult = mysqli_query($con,$sqlinsert);
		
		
		if(!$insertresult)
		{
			$response = array();
			$code = "reg_false";
			$message = "Server Error. Try again";
			
			//Push variables $code and $message into an array
			array_push($response,array("code"=>$code,"message"=>$message));
			
			//Put array into a JSON object to push to the app
			echo json_encode(array("server_response"=>$response));
		}
		else
		{
			$response = array();
			$code = "reg_true";
			$message = "Registration was completed successfully.";
			
			//Push variables $code and $message into an array
			array_push($response,array("code"=>$code,"message"=>$message));
			
			//Put array into a JSON object to push to the app
			echo json_encode(array("server_response"=>$response));
		}
	}	
	
	mysqli_close($con);
?>			