<?php

    require "init.php"; 
    
    $modID = $_POST["modID"];
	
	$sql = "select * from class_details where moduleID = '$modID'";
	$result = mysqli_query($con,$sql);
	
	$rowcount = mysqli_num_rows($result);	

	if($rowcount<1)
	{
		$response = array();
		
		$code = "class_fetch_error";
		$message = "Classes for this module do not exist or could not be fetched. Please try again.";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
		$response = array();
		
		while($row = mysqli_fetch_array($result))
		{
			array_push($response,array("classID"=>$row[0],"classType"=>$row[1],"moduleID"=>$row[2],
			"courseCode"=>$row[3],"room"=>$row[4],"signInCode"=>$row[5],"lecID"=>$row[6],
			"startHr"=>$row[7],"startMin"=>$row[8],"finHr"=>$row[9],"finMin"=>$row[10],
			"lateHr"=>$row[11],"lateMin"=>$row[12],"year"=>$row[13],"month"=>$row[14],"day"=>$row[15]));
		}
		
		echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);
?>