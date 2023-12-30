import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;

class DesignationManagerTitleExistsTestCase
{
public static void main(String gg[])
{
String title = "";
try
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter title : ");
title = br.readLine();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
try
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
System.out.println("Designation with title : "+title+" exists : "+designationManager.designationTitleExists(title));
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
