<?php

    require "init.php";
    
    $user = 'id';
    
    $sql1 = "select accountType from user_info where idNum = '$user'";
    $result1 = mysqli_query($con,$sql1);
    $row1 = mysqli_fetch_row($result1);
	
    if($row1[0] == 'Student')
    {
        $sql2 = "select courseCode from user_info where idNum = '$user'";
		$result2 = mysqli_query($con,$sql2);
		$row2 = mysqli_fetch_row($result2);
        
        $sql3 = "select * from module_details where classListCourseCode = '".$row2[0]."'";
		$result3 = mysqli_query($con,$sql3);
		
		$response = array();
		
		while($row = mysqli_fetch_array($result3))
		{
			array_push($response,array("moduleID"=>$row[0],"lecturerID"=>$row[1],"moduleName"=>$row[2],"classListCourseCode"=>$row[3]));
		}
		
		echo json_encode(array("server_response"=>$response));
    }
    else
    {
        $sql4 = "select * from module_details where lecturerID = '$user'";
		$result4 = mysqli_query($con,$sql4);
		
		$response = array();
		
		while($row = mysqli_fetch_array($result4))
		{
			array_push($response,array("moduleID"=>$row[0],"lecturerID"=>$row[1],"moduleName"=>$row[2],"classListCourseCode"=>$row[3]));
		}
		
		echo json_encode(array("server_response"=>$response));
    }
	
	mysqli_close($con);
?>