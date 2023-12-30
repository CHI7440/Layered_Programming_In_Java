import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;

class DesignationManagerGetByTitleTestCase
{
public static void main(String gg[])
{
String title = "";
try
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter Title : ");
title = br.readLine();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
try
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
DesignationInterface designation = designationManager.getDesignationByTitle(title);
System.out.println("Designation with title : "+title+" has code : "+designation.getCode());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties = blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
}
}
}
}
