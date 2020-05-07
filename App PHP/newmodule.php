<?php

    require "init.php";
    
	$lecturerID = $_POST["lecturerID"];
	$moduleName = $_POST["moduleName"];
	$courseCode = $_POST["courseCode"];
	$moduleID = NULL;
	
	$sql = "select * from user_info where idNum = '$lecturerID' AND accountType = 'Lecturer'";
	
	$sqlresp = mysqli_query($con,$sql);
	$rowcount = mysqli_num_rows($sqlresp);
	
	if($rowcount<1)
	{
		$response = array();
		$code = "module_creation_error";
		$message = "Please enter a valid Lecturer ID.";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
		//Inserting new module details into module table
		$sqlinsert = "insert into module_details(lecturerID,moduleName,classListCourseCode) VALUES ('$lecturerID', '$moduleName', '$courseCode')";
		
		$insertresult = mysqli_query($con,$sqlinsert);
		
		if($insertresult)
		{
		    $response = array();
			$code = "module_created";
			$message = "Your new module was created successfully.";
			
			$sqlmodID = mysqli_query($con,"select moduleID from module_details WHERE moduleName = '$moduleName' AND lecturerID = '$lecturerID'");
			$row = mysqli_fetch_assoc($sqlmodID);
			$modid = $row['moduleID'];
			
			$sqlstudent = "select * from user_info WHERE courseCode = '$courseCode'";			
			$result = mysqli_query($con, $sqlstudent);
			
			while($row = mysqli_fetch_assoc($result))
			{
				$sql = "insert into student_classlist VALUES ('$modid', '".$row['idNum']."')";
				$perform = mysqli_query($con,$sql);
			}
			
			//Push variables $code and $message into an array
			array_push($response,array("code"=>$code,"message"=>$message));
			
			//Put array into a JSON object to push to the app
			echo json_encode(array("server_response"=>$response));
		}
		else
		{
			$response = array();
			$code = "module_insert_error";
			$message = "Server Error. Try again";
			
			//Push variables $code and $message into an array
			array_push($response,array("code"=>$code,"message"=>$message));
			
			//Put array into a JSON object to push to the app
			echo json_encode(array("server_response"=>$response));
		}
	}	
	
	mysqli_close($con);
	
?>