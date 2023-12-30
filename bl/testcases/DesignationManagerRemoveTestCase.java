import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;

class DesignationManagerRemoveTestCase
{
public static void main(String gg[])
{
int code = 0;
try
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter code : ");
try
{
code = Integer.parseInt(br.readLine());
}catch(NumberFormatException nfe)
{
System.out.println(nfe.getMessage());
}
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
try
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
System.out.println("Designation removed with code as : "+code);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties = blException.getProperties();
for(String property : properties)
{
System.out.println(property+" : "+blException.getException(property));
}
}
}
}
