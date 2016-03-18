<?php


$json =file_get_contents('php://input');
$con = mysqli_connect("localhost", "root", "", "testdb");
mysqli_query($con , "SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'");

$con = mysqli_connect("localhost","root","", "testdb");

if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
$sql = "SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'";
mysqli_query($con, $sql) ;



$data  = json_decode($json,true);







function insert($table_name,$ignore="")
{
   
  $ignore_array= explode(",",$ignore.",");
  global $data;
   global $con;
   $sql="insert into ".$table_name." (";
   $keys="";
   $values="";
   $coma=false; 
   foreach ($data as $key => $value)
    {
         if(!in_array($key,$ignore_array))
         {
         if($coma)
         {
         $keys.=",";
         $values.=",";              
         }
     $coma=true;    
         $keys.=$key;
         
         if($key=='password')
         {$value=md5($value);}         
              
         $values.="'".$value."'";
         }
    }
  $sql.=$keys.") values (".$values.")";
    
  return generate_sql($sql,"insert",$con);

}


function select($table_name,$where="",$where_not="")
{
  global $data;
   global $con;
   $sql="select * from ".$table_name;
  $sql.=get_where_cases($where,$where_not);   
  return generate_sql($sql,"select",$con);
}



function delete($table_name,$where="",$where_not="")
{
 
   global $data;
   global $con;
   $sql="delete from ".$table_name;
  $sql.=get_where_cases($where,$where_not);   
 return generate_sql($sql,"delete",$con);
}





function update($table_name,$update_elements,$where="",$where_not="")
{
  global $data;
   global $con;
   $update_arr = explode(",", $update_elements);
   $where_arr = explode(",", $where);
   $sql="update ".$table_name.=" set ";
   $keys="";
   $values="";
   $coma=false;

 
 for($i = 0; $i < count($update_arr); $i++)
 {
   
   $key=$update_arr[$i]; 
   $value=$data[$key];
           
    if($coma)
         {
            $sql.=' , ';                     
         }
     $coma=true; 
  
    $sql.=$key." = ";  
    
        
         if($key=='password')
         {$value=md5($value);}

      $sql.="'".$value."'";
  }     
 
$sql.=get_where_cases($where,$where_not);   
 
  return generate_sql($sql,"update",$con);
}


function  get_where_cases($where,$where_not)
{

   $sql="";

  if($where!="")
   {
      $sql .=" where ";
      $sql .=generate_where($where," = ");
   }
  if($where_not!="")
  {
  $where_not_arr = explode(",", $where_not);
  if($where=="")
  $sql.=" where ";
  else $sql.=" and ";
    $sql .= generate_where($where_not," <> ");
   }

  return "".$sql;
}

function generate_where($where,$case)
{

 global $data;
   
   
   $where_arr = explode(",", $where);
   
   $keys="";
   $values="";
   $coma=false;

  $sql="";

   if($where!="")
 {
for($i = 0; $i < count($where_arr); $i++)
 {
   
   $key=$where_arr[$i]; 
   $value=$data[$key];
           
    if($coma)
         {
            $sql.=' and ';                     
         }
     $coma=true; 
  
    $sql.=$key.$case;  
    
        
         if($key=='password')
         {$value=md5($value);}

      $sql.="'".$value."'";
  }
 }  

return $sql;   
  
}


function generate_sql($sql,$operation,$con,$col_name="")
{
 
   $result = mysqli_query($con,$sql);

   if($operation=="insert"||$operation=="delete")
   {
    
   }
   else if($operation=="update")
   {
     
     
   }
  else if($operation=="select")
   {
        
    while($row = mysqli_fetch_assoc($result)) {
             $output[] = $row ;
             


     } 
      return json_encode($output) ;
     
   }
   else if($operation=="select_ids")
  {
      $ids=array();
      while($row = mysqli_fetch_assoc($result)) {
             array_push($ids, $row[$col_name]);  
             } 
      return $ids;
      
  }
  else {return "Wrong operation make sure that you inserted the correct operation [".$operation."] UnKnown"; }
  
   return mysqli_affected_rows($con);
}




function get_nearest($table_name,$lat,$lng,$distance)
{
   global $con;

  $sql="SELECT  *,
(3959*acos(cos(radians(".$lat."))*cos(radians(lat))*
 cos(radians(lng)-radians(".$lng."))+sin(radians(".$lat."))*sin(radians(lat)))) AS distance FROM ".$table_name." having distance <".$distance."1000 ORDER BY distance  ";
       
   

   return generate_sql($sql,"select",$con);

}


function send_android($notification_ids,$message)
{
  $url = 'https://android.googleapis.com/gcm/send';      
  $fields = array(
             'registration_ids' => $notification_ids,
             'data' => $message,
         );
   
         $headers = array(
             'Authorization: key=AIzaSyACmkYIcx7V4TPfFevBQ7kCYcZ8ieAeGTY',
             'Content-Type: application/json'
         );
         // Open connection
         $ch = curl_init();
         curl_setopt($ch, CURLOPT_URL, $url);   
         curl_setopt($ch, CURLOPT_POST, true);
         curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
         curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
         curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
   
         curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
         $result = curl_exec($ch);
         if ($result === FALSE) {
             die('Curl failed: ' . curl_error($ch));
         }
         curl_close($ch);
         
         return $result;
}



function send_all_android($message,$table_name,$col_name,$where="")
{   
    global $con;
    $sql="select ".$col_name." from ".$table_name." where ".$where ;
   
  return send_android(generate_sql($sql,"select_ids",$con,"name"),$message);
  
}

function send_ios($device_token,$alert,$message,$passphrase)
{
// Put your private key's passphrase here:



$ctx = stream_context_create();
stream_context_set_option($ctx, 'ssl', 'local_cert', 'iWantzzCK.pem');
stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);

// Open a connection to the APNS server
$fp = stream_socket_client(
	'ssl://gateway.sandbox.push.apple.com:2195', $err,
	$errstr, 60, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $ctx);

if (!$fp)
	exit("Failed to connect: $err $errstr" . PHP_EOL);

echo 'Connected to APNS' . PHP_EOL;

// Create the payload body
$body['aps'] = array(
	'alert' =>$alert ,
	'sound' => 'default'
	);
	$body['data'] = $message;

// Encode the payload as JSON
$payload = json_encode($body);

// Build the binary notification
$msg = chr(0) . pack('n', 32) . pack('H*', $device_token) . pack('n', strlen($payload)) . $payload;


// Send it to the server
$result = fwrite($fp, $msg, strlen($msg));

$finalresult='';

if (!$result)
	$final_result= 'Message not delivered' . PHP_EOL;
else
	$final_result= 'Message successfully delivered' . PHP_EOL;

// Close the connection to the server
fclose($fp);
return $final_result;
}





function json_to_array($data , $name)
{
 $ids=array();

  foreach ($data as $key => $value)
    {
      if($key==$name)
       {
          array_push($ids, $value);
       }  
    }
  return $ids;
}


function send_mail($mail, $subject, $message, $headers )
   {

     return mail($mail, $subject, $message, $headers); 
   }

function show_log($log)
{
$myfile = fopen("log_file.txt", "w") or die("Unable to open file!");
fwrite($myfile, $log);
fclose($myfile);
}

function download_file($base,$filename)
{
   $now=time();
   $exe=explode(".",$filename);
   $filetype=$exe[count($exe)-1];
   $new_filename=$now.".".$filetype;
   $binary=base64_decode($base);
    header('Content-Type: bitmap; charset=utf-8');
    $file = fopen('files/'.$new_filename, 'wb');
    fwrite($file, $binary);
    fclose($file);
 return "Succeed";
}


?>