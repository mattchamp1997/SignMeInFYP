<?php

    require "init.php"; 
    
    $idNum = $_POST["idNum"];
	
	$sql1 = "select accountType from user_info where idNum = '$idNum'";
	$result1 = mysqli_query($con,$sql1);
	$row1 = mysqli_fetch_row($result1);
	
	$response = array();
	
	if($row1[0] == 'Student')
	{
		$sql2 = "SELECT courseCode from user_info WHERE idNum = '$idNum'";
		$result2 = mysqli_query($con, $sql2);
		$row = mysqli_fetch_assoc($result2);
		$cCode = $row['courseCode'];
		
		$sql3 = "SELECT * FROM class_details JOIN module_details ON class_details.moduleID = module_details.moduleID WHERE courseCode = '$cCode'";
		$result3 = mysqli_query($con, $sql3);
			
		while($row2 = mysqli_fetch_array($result3))
		{
			array_push($response,array("classID"=>$row2[0],"classType"=>$row2[1],"moduleID"=>$row2[2],
			"courseCode"=>$row2[3],"room"=>$row2[4],"signInCode"=>$row2[5],"lecID"=>$row2[6],
			"startHr"=>$row2[7],"startMin"=>$row2[8],"finHr"=>$row2[9],"finMin"=>$row2[10],
			"lateHr"=>$row2[11],"lateMin"=>$row2[12],"year"=>$row2[13],"month"=>$row2[14],"day"=>$row2[15],"moduleName"=>$row2[18]));
		}
		
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
		$sql3 = "SELECT * FROM class_details JOIN module_details ON class_details.moduleID = module_details.moduleID WHERE lecturerID = '$idNum'";
		$result3 = mysqli_query($con, $sql3);
			
		while($row2 = mysqli_fetch_array($result3))
		{
			array_push($response,array("classID"=>$row2[0],"classType"=>$row2[1],"moduleID"=>$row2[2],
			"courseCode"=>$row2[3],"room"=>$row2[4],"signInCode"=>$row2[5],"lecID"=>$row2[6],
			"startHr"=>$row2[7],"startMin"=>$row2[8],"finHr"=>$row2[9],"finMin"=>$row2[10],
			"lateHr"=>$row2[11],"lateMin"=>$row2[12],"year"=>$row2[13],"month"=>$row2[14],"day"=>$row2[15],"moduleName"=>$row2[18]));
		}
		
		echo json_encode(array("server_response"=>$response));	
	}
	
	mysqli_close($con);
?>