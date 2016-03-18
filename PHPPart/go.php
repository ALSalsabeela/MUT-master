<?php

include 'MUT.php';

$result;

if($data['method']=="insert")
{
  $result=insert("testtable","method");
}
else if($data['method']=="select")
{
  $result= select("testtable","mail,password");
}
else if($data['method']=="update")
{
  $result= update("testtable","name","mail,password");
}
else if($data['method']=="delete")
{
  $result=delete("testtable","mail,password");
}
else if($data['method']=="upload_file")
{
  $file=$data['file'];
  $file_name=$data['file_name'];
  echo download_file($file,$file_name);
}
else
{
  $result="Error";
}


echo $result;


?>

